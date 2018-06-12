package org.foi.nwtis.ivicelig.web.dretve;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.baza.TablicaKorisnici;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;
import org.foi.nwtis.ivicelig.web.regex.RegexProvjera;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;
import org.foi.nwtis.ivicelig.ws.klijenti.StatusKorisnika;
import java.util.List;

class RadnaDretva extends Thread {

    private Socket socket;
    private Konfiguracija konfig;
    private String korime;
    private String lozinka;

    public RadnaDretva(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public void run() {

        try {

            try {
                RegexProvjera rp = new RegexProvjera();
                long pocetak = System.currentTimeMillis();
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                StringBuffer sb = new StringBuffer();
                int znak;
                while ((znak = is.read()) != -1) {
                    sb.append((char) znak);
                }

                if (rp.ulazZadovoljavavaRegex(sb.toString())) {
                    ArrayList<String> komanda = rp.dohvatiRegexGrupe(sb.toString());
                    boolean dodavanje = komandaDodavanjeKorisnika(komanda);

                    if (provjeriDaliKorisnikPostoji(komanda.get(2))) {

                        if (!(dodavanje)) {
                            if (provjeriKorisnickoImeIlozinku(komanda.get(2), komanda.get(4))) {

                                doAction(komanda, os);

                            } else {
                                //Korisničko ime i lozinka ne odgovoaraju
                                System.out.println("ERROR 11;");
                            }
                        } else {
                            //Ako je komanda DODAJ korisnika i korisničko ime postoji
                            System.out.println("ERROR 10;");
                        }
                    } else {
                        //Korisnik ne postoji

                        if ("DODAJ".equals(komanda.get(6))) {
                            //TODO dodaj korisnika u sustav
                            Korisnik k = new Korisnik(komanda.get(2), komanda.get(4), komanda.get(7), komanda.get(8));
                            TablicaKorisnici tk = new TablicaKorisnici();
                            tk.insert(k);
                            System.out.println("OK 10;");

                        } else {
                            System.out.println("ERROR 11;");
                        }
                    }

                } else {
                    System.out.println("KOMANDA NIJE DOBRA!");
                    //TODO regex ne zadovoljava uvjete
                }
                long kraj = System.currentTimeMillis();
                long rad = kraj - pocetak;

            } catch (IOException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

            socket.shutdownOutput();

        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean komandaDodavanjeKorisnika(ArrayList<String> komanda) {
        //Test ako je komanda za dodavanje korisnika
        if (komanda.size() >= 7) {
            if (komanda.get(6).equals("DODAJ")) {
                return true;
            }
        }
        return false;
    }

    private void doAction(ArrayList<String> komanda, OutputStream os) {
        final KomunikacijskaDretva kd = SlusacAplikacije.kd;
        final MeteoDretva md = SlusacAplikacije.md;

        switch (komanda.get(5)) {
            case "PAUZA":

                if (kd.pauza) {
                    System.out.println("ERR 12;");
                } else {
                    kd.pauza = true;
                    System.out.println("OK 10;");
                }
                break;
            case "KRENI":
                if (!kd.pauza) {
                    System.out.println("ERR 13;");
                } else {
                    kd.pauza = false;
                    System.out.println("OK 10;");
                }
                break;
            case "PASIVNO":

                if (md.pasivno) {
                    System.out.println("ERR 14;");
                } else {
                    md.pasivno = true;
                    System.out.println("OK 10;");
                }
                break;
            case "AKTIVNO":
                if (!md.pasivno) {
                    System.out.println("ERR 15;");
                } else {
                    md.pasivno = false;
                    System.out.println("OK 10;");
                }
                break;
            case "STANI":
                md.radi = false;
                kd.interrupt();
                break;
            case "STANJE":
                if(md.radi && !kd.pauza){
                    pisiUOutputStream(os, "OK 11;");
                }
                if(!md.radi && !kd.pauza){
                    pisiUOutputStream(os, "OK 12;");
                }
                if(md.radi && kd.pauza){
                    pisiUOutputStream(os, "OK 13;");
                }
                if(!md.radi && kd.pauza){
                    pisiUOutputStream(os, "OK 14;");
                }

                break;
            case "LISTAJ":
                TablicaKorisnici tk = new TablicaKorisnici();
                List<Korisnik> korisnici = tk.getAllRecords();
                if(korisnici.isEmpty()){
                     pisiUOutputStream(os, "ERR 17;");
                }
                else{
                    StringBuilder sb = new StringBuilder();
                    sb.append("OK 10; [");
                    for (int i = 0; i < korisnici.size(); i++) {
                        if(i==(korisnici.size()-1)){
                            sb.append("{\"ki\":"+korisnici.get(i).getKorIme()+", \"prezime\":"+korisnici.get(i).getPrezime()+", \"ime\":"+korisnici.get(i).getIme()+"}");
                         break;   
                        }
                         sb.append("{\"ki\":"+korisnici.get(i).getKorIme()+", \"prezime\":"+korisnici.get(i).getPrezime()+", \"ime\":"+korisnici.get(i).getIme()+"},");
                    }
                    sb.append("];");
                    pisiUOutputStream(os, sb.toString());
                }
                
                break;

            default:
                //GRUPA naredbe
                if (!kd.pauza) {
                    switch (komanda.get(7)) {
                        case "DODAJ":
                            
                            if (registrirajGrupu(korime, lozinka)) {
                                System.out.println("TU SMO!");
                                pisiUOutputStream(os, "OK 20;");
                            } else {
                                pisiUOutputStream(os, "ERROR 21;");
                            }
                            break;
                        case "PREKID":
                            break;
                        case "KRENI":
                            break;
                        case "PAUZA":
                            break;
                        case "STANJE":
                            break;
                        default:

                            break;

                    }
                } else {
                    //Sustav je u stanju pauze; ne prihvaća naredbe
                    System.out.println("Sustav je u stanju pauze!");
                }
                break;
        }

    }

    @Override
    public synchronized void start() {
        super.start();
        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
        korime = konfig.dajPostavku("korIme");
        lozinka = konfig.dajPostavku("lozinka");
    }

    private boolean provjeriKorisnickoImeIlozinku(String korime, String lozinka) {
        TablicaKorisnici tk = new TablicaKorisnici();
        Korisnik k = tk.getByID(korime);

        return k.getKorIme().equals(korime) && k.getLozinka().equals(lozinka);
    }

    private boolean provjeriDaliKorisnikPostoji(String korime) {
        TablicaKorisnici tk = new TablicaKorisnici();
        Korisnik k = tk.getByID(korime);
        return k != null;
    }

    private static Boolean registrirajGrupu(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.registrirajGrupu(korisnickoIme, korisnickaLozinka);
    }
    
    private static StatusKorisnika dajStatusGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.dajStatusGrupe(korisnickoIme, korisnickaLozinka);
    }
    private void pisiUOutputStream(OutputStream os, String poruka) {
        try {
            os.write(poruka.getBytes());
            os.flush();

        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
