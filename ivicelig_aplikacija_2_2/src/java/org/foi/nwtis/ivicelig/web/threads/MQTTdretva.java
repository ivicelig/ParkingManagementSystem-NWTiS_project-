/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.threads;

import com.google.gson.Gson;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.ivicelig.ejb.eb.KorisnikParkiraliste;
import org.foi.nwtis.ivicelig.ejb.eb.MqttPoruke;
import org.foi.nwtis.ivicelig.ejb.sb.KorisnikParkiralisteFacade;
import org.foi.nwtis.ivicelig.ejb.sb.MqttPorukeFacade;
import org.foi.nwtis.ivicelig.ejb.sb.SingletonSB;
import org.foi.nwtis.ivicelig.rest.json.MQTTjson;
import org.foi.nwtis.ivicelig.rest.json.OdgovorClass;
import org.foi.nwtis.ivicelig.rest.json.OdgovorParkiralista;
import org.foi.nwtis.ivicelig.rest.json.ParkiralistaForJson;
import org.foi.nwtis.ivicelig.rest.klijent.ParkiralistaIDREST;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.foi.nwtis.ivicelig.web.podaci.PracenjeMjesta;

/**
 *
 * @author Ivica
 */
public class MQTTdretva extends Thread {
    boolean radi = true;
    KorisnikParkiralisteFacade korisnikParkiralisteFacade = lookupKorisnikParkiralisteFacadeBean();
    private String korisnik;
    SingletonSB singletonSB = lookupSingletonSBBean();
    private List<ParkiralistaForJson> popisParkiralista = new ArrayList<>();
    private List<PracenjeMjesta> listaPrecenjaMjesta = new ArrayList<PracenjeMjesta>();
    MqttPorukeFacade mqttPorukeFacade = lookupMqttPorukeFacadeBean();
    private Gson gson = new Gson();
        private MQTT mqtt = new MQTT();
        private CallbackConnection connection;
    public MQTTdretva(String korisnik) {
        this.korisnik = korisnik;
       
    }

    @Override
    public void interrupt() {
       // radi = false;
        connection.disconnect(new Callback<Void>() {
            public void onSuccess(Void v) {
                System.out.println("Diskonekcija");
            }
            public void onFailure(Throwable value) {
              // Disconnects never fail.
            }
        });
        
    }

    @Override
    public void run() {
        String user = "ivicelig";
        String password = "656989";
        String host = "nwtis.foi.hr";
        int port = 61613;
        final String destination = "/NWTiS/ivicelig";


        try {
            mqtt.setHost(host, port);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MQTTdretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        mqtt.setUserName(user);
        mqtt.setPassword(password);
        dodajParkiralisteUPopisParkiralista();
        inicializirajPracenje();
        connection = mqtt.callbackConnection();
        connection.listener(new org.fusesource.mqtt.client.Listener() {
            long count = 0;

            @Override
            public void onConnected() {
                System.out.println("Otvorena veza na MQTT");
            }

            @Override
            public void onDisconnected() {
                System.out.println("Prekinuta veza na MQTT");
                
            }

            @Override
            public void onFailure(Throwable value) {
                System.out.println("Problem u vezi na MQTT");
                
            }
            Integer n;

            @Override
            public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
                String body = msg.utf8().toString();
                System.out.println("Stigla poruka :" + body);
                count++;
                MqttPoruke poruka = new MqttPoruke();

                MQTTjson md = gson.fromJson(body, MQTTjson.class);
                String korisnik = pronađiKorisnikaPremaParkiralistu(md.getParkiraliste());
                poruka.setPoruka(body);
                mqttPorukeFacade.create(poruka);
                try {
                    if (korisnik.equals(korisnik)) {
                        if (provjeri(md.getParkiraliste(), md.getAkcija())) {
                            singletonSB.sendJMSMessageToNWTiS_ivicelig_2(korisnik.concat(";").concat(body));
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }

            private boolean provjeri(int parkiraliste, int ulazIliIzlaz) {
                boolean provjera = true;
                for (PracenjeMjesta pracenjeMjesta : listaPrecenjaMjesta) {
                    if (pracenjeMjesta.getIdParkiralista() == parkiraliste) {
                        if (ulazIliIzlaz == 0) {
                            if (pracenjeMjesta.getKapacitet() == pracenjeMjesta.getZauzeto()) {
                                provjera = false;
                            } else {
                                pracenjeMjesta.ulaz();
                            }
                        } else {
                            if (pracenjeMjesta.getZauzeto() == 0) {
                                provjera = false;
                            } else {
                                pracenjeMjesta.izlaz();
                            }
                        }
                        break;
                    }
                }

                return provjera;

            }

        });
        connection.connect(
                new Callback<Void>() {
            @Override
            public void onSuccess(Void value
            ) {
                Topic[] topics = {new Topic(destination, QoS.AT_LEAST_ONCE)};
                connection.subscribe(topics, new Callback<byte[]>() {
                    @Override
                    public void onSuccess(byte[] qoses) {
                        System.out.println("Pretplata na: " + destination);
                    }

                    @Override
                    public void onFailure(Throwable value) {
                        System.out.println("Problem kod pretplate na: " + destination);
                        
                    }
                });
            }

            @Override
            public void onFailure(Throwable value
            ) {
                System.out.println("Neuspjela pretplata na: " + destination);
                
            }
        }
        );

        // Wait forever..
        synchronized (MQTTdretva.class) {
            while (radi) {
                try {
                    
                    MQTTdretva.class.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MQTTdretva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    private MqttPorukeFacade lookupMqttPorukeFacadeBean() {
        try {
            Context c = new InitialContext();
            return (MqttPorukeFacade) c.lookup("java:global/ivicelig_aplikacija_2/ivicelig_aplikacija_2_1/MqttPorukeFacade!org.foi.nwtis.ivicelig.ejb.sb.MqttPorukeFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private void dodajParkiralisteUPopisParkiralista() {
        popisParkiralista.clear();
        List<KorisnikParkiraliste> kp = korisnikParkiralisteFacade.getDataByKorisnik(korisnik);
        for (KorisnikParkiraliste korisnikParkiraliste : kp) {
            ParkiralistaIDREST pID = new ParkiralistaIDREST(Integer.toString(korisnikParkiraliste.getParkiralisteId()));
            String odgovor = pID.getJson(String.class
            );

            ParkiralistaForJson oc = gson.fromJson(odgovor, ParkiralistaForJson.class
            );
            popisParkiralista.add(oc);

        }
    }

    private void inicializirajPracenje() {
        popisParkiralista.get(0).getOdgovor();
        for (OdgovorParkiralista op : popisParkiralista.get(0).getOdgovor()) {
            PracenjeMjesta pm = new PracenjeMjesta(op.getId(), op.getKapacitet());
            listaPrecenjaMjesta.add(pm);
        }

    }

    private SingletonSB lookupSingletonSBBean() {
        try {
            Context c = new InitialContext();
            return (SingletonSB) c.lookup("java:global/ivicelig_aplikacija_2/ivicelig_aplikacija_2_1/SingletonSB!org.foi.nwtis.ivicelig.ejb.sb.SingletonSB");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private String pronađiKorisnikaPremaParkiralistu(int id) {
        KorisnikParkiraliste kp = korisnikParkiralisteFacade.find(id);

        return kp.getKorisnickoIme();
    }

    private KorisnikParkiralisteFacade lookupKorisnikParkiralisteFacadeBean() {
        try {
            Context c = new InitialContext();
            return (KorisnikParkiralisteFacade) c.lookup("java:global/ivicelig_aplikacija_2/ivicelig_aplikacija_2_1/KorisnikParkiralisteFacade!org.foi.nwtis.ivicelig.ejb.sb.KorisnikParkiralisteFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
