/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.ejb.EJB;

import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import org.foi.nwtis.ivicelig.ejb.sb.StatefulSB;

/**
 *
 * @author Ivica
 */
@Named(value = "posluzitelj1")
@SessionScoped
public class Posluzitelj1 implements Serializable {

    @EJB
    private StatefulSB statefulSB1;

   
    
    /**
     * Creates a new instance of Test
     */
    public Posluzitelj1() {
    }
    
    public void registrirajGrupu(){
        try {
            
           //ControlerKomandi ck = new ControlerKomandi(komande);
            // socket = new Socket(adresa, port);
            //String komanda = ck.komandaAdmin();
            Socket socket = new Socket("127.0.0.1",10001);
            InputStream is= socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            os.write("KORISNIK ivicelig; LOZINKA 123456; GRUPA DODAJ;".getBytes());
            os.flush();
            socket.shutdownOutput();
            
            
            StringBuffer sb = new StringBuffer();
            int znak;
            while((znak = is.read()) != -1){
                sb.append((char) znak);
            }
            
            System.out.println("Odgovor: "+ sb.toString());
        } catch (IOException ex) {
           
        }
    }
    
    
}
