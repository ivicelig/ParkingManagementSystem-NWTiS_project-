/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import org.foi.nwtis.ivicelig.json.MQTTjson;
import org.foi.nwtis.ivicelig.json.MQTTkorisnik;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;

/**
 *
 * @author Ivica
 */
@Singleton
@Startup
@LocalBean
public class SingletonSessionBean {

    private Konfiguracija konfig;
    private List<MQTTkorisnik> mqttPoruke = new ArrayList<>();

    public List<MQTTkorisnik> getMqttPoruke() {
        return mqttPoruke;
    }

    public void setMqttPoruke(List<MQTTkorisnik> mqttPoruke) {
        this.mqttPoruke = mqttPoruke;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private String status;

    public void dodajMqttPoruku(MQTTkorisnik e) {
        mqttPoruke.add(e);
    }

    @PostConstruct
    void init() {
        try {
            DeSerijalizirajEvidenciju();
        } catch (IOException ex) {
            Logger.getLogger(SingletonSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        status = "Ready";
        System.out.println(status);
    }

    @PreDestroy
    public void preDestroy() {

        String nazivDatoteke = "NWTiS_ivicelig_evidencija_rada.bin";
        File datSer = new File(nazivDatoteke);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(datSer));

            oos.writeObject(mqttPoruke);

            oos.close();
          

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SingletonSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SingletonSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                oos.close();
            } catch (IOException ex) {

            }

        }
    }
    private void DeSerijalizirajEvidenciju() throws FileNotFoundException, IOException {
        File evidencija = new File("NWTiS_ivicelig_evidencija_rada.bin");
        System.out.println("DESERIJALIZACIJA");
        if (!evidencija.exists()) {
            System.out.println("Datoteka " + "NWTiS_ivicelig_evidencija_rada.bin" + " ne postoji!");
        } else {
            FileInputStream fis = new FileInputStream(evidencija);
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                
                try {
                    mqttPoruke = (List<MQTTkorisnik>) ois.readObject();
                } catch (EOFException a) {
                    System.err.println(a);
                }
                fis.close();
                ois.close();

            } catch (ClassNotFoundException ex) {
                
            }
        }
    }


}
