/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;


import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.ivicelig.json.MQTTjson;
import org.foi.nwtis.ivicelig.json.MQTTkorisnik;
import org.foi.nwtis.ivicelig.web.websocket.MQTTmessage;

/**
 *
 * @author Ivica
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_ivicelig_2")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageDrivenBean implements MessageListener {

    @EJB
    private SingletonSessionBean singletonSessionBean;
    private Gson gson = new Gson();
    public MessageDrivenBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        
        MQTTjson mj = new MQTTjson();
        MQTTkorisnik mk = new MQTTkorisnik();
         try {
            System.out.println("JSM poruka: "+message.getBody(String.class));
        } catch (JMSException ex) {
           
        }
         String[] poruka = new String[]{};
        try {
             poruka = message.getBody(String.class).split(";");
             mj =  gson.fromJson(poruka[1], MQTTjson.class);
             
        mk.setMj(mj);
        mk.setKorisnik(poruka[0]);
        singletonSessionBean.dodajMqttPoruku(mk);
        } catch (JMSException ex) {
            Logger.getLogger(MessageDrivenBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //TODO dohvati korisnika i pripadno
       
             MQTTmessage.obavijestiPromjenu("Stigla nova poruka!");
        
        
    }
     
    
}

       
        
        
    