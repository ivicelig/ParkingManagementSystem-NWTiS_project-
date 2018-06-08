package org.foi.nwtis.ivicelig.web.dretve;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.regex.RegexProvjera;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;

class RadnaDretva extends Thread {

    private Socket socket;
    private Konfiguracija konfig;

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
                
                System.out.println("HI");
                if(rp.ulazZadovoljavavaRegex(sb.toString())){
                    ArrayList<String> komanda = rp.dohvatiRegexGrupe(sb.toString());
                    System.out.println(sb);
                    for (String string : komanda) {
                        System.out.println(string);
                    }
                }else{
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

    @Override
    public synchronized void start() {
        super.start();
        konfig = (Konfiguracija) SlusacAplikacije.sc.getAttribute("Konfiguracija");
    }
    
    private boolean provjeriKorisničkoImeIlozinku(String korime, String lozinka){
        
        //TODO provjeri kor ime i lozinku
        return false;
    }

}
