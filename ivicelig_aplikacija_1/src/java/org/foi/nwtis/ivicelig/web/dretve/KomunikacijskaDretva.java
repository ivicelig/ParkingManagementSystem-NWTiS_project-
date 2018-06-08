package org.foi.nwtis.ivicelig.web.dretve;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivica
 */
public class KomunikacijskaDretva extends Thread {

    public volatile static boolean krajRada = false;
    ServerSocket serverSocket;
    

    @Override
    public void interrupt() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(KomunikacijskaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        krajRada = true;
    }

    @Override
    public void run() {
        pokreniPoslužiteljZaNaredbe();
    }

    @Override
    public synchronized void start() {
        super.start();
       
    }

    private void pokreniPoslužiteljZaNaredbe() {
        int port = 10001;
        int maxCekanje = 6;
        
        try {

            serverSocket = new ServerSocket(port, maxCekanje);
            while (!krajRada) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {

                }
                
                
                
                Socket socket = serverSocket.accept();

                
                //Starting of main work thread
                RadnaDretva rd = new RadnaDretva(socket);
                            rd.start();
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
