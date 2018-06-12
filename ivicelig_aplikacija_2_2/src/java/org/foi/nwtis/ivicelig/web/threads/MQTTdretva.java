/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.threads;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
/**
 *
 * @author Ivica
 */
public class MQTTdretva extends Thread{

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
         String user = "ivicelig";
        String password = "656989";
        String host = "nwtis.foi.hr";
        int port = 61613;
        final String destination = "/NWTiS/ivicelig";

        MQTT mqtt = new MQTT();
        try {
            mqtt.setHost(host, port);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MQTTdretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        mqtt.setUserName(user);
        mqtt.setPassword(password);

        final CallbackConnection connection = mqtt.callbackConnection();
        connection.listener(new org.fusesource.mqtt.client.Listener() {
            long count = 0;

            @Override
            public void onConnected() {
                System.out.println("Otvorena veza na MQTT");
            }

            @Override
            public void onDisconnected() {
                System.out.println("Prekinuta veza na MQTT");
                System.exit(0);
            }

            @Override
            public void onFailure(Throwable value) {
                System.out.println("Problem u vezi na MQTT");
                System.exit(-2);
            }

            @Override
            public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
                String body = msg.utf8().toString();
                System.out.println("Stigla poruka :"+body);
                count++;
            }
        });
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                Topic[] topics = {new Topic(destination, QoS.AT_LEAST_ONCE)};
                connection.subscribe(topics, new Callback<byte[]>() {
                    @Override
                    public void onSuccess(byte[] qoses) {
                        System.out.println("Pretplata na: " + destination);
                    }

                    @Override
                    public void onFailure(Throwable value) {
                        System.out.println("Problem kod pretplate na: " + destination);
                        System.exit(-2);
                    }
                });
            }

            @Override
            public void onFailure(Throwable value) {
                System.out.println("Neuspjela pretplata na: " + destination);
                System.exit(-2);
            }
        });

        // Wait forever..
        synchronized (MQTTdretva.class) {
            while (true) {
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
    
   
    
}
