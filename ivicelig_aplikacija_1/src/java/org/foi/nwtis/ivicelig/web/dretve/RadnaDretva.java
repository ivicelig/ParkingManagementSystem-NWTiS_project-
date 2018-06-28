package org.foi.nwtis.ivicelig.web.dretve;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.ivicelig.konfiguracije.Konfiguracija;
import org.foi.nwtis.ivicelig.web.baza.TablicaKorisnici;
import org.foi.nwtis.ivicelig.web.podaci.Korisnik;
import org.foi.nwtis.ivicelig.web.regex.RegexProvjera;
import org.foi.nwtis.ivicelig.web.slusaci.SlusacAplikacije;
import org.foi.nwtis.ivicelig.ws.klijenti.StatusKorisnika;
import java.util.List;
import org.foi.nwtis.ivicelig.web.baza.TablicaDnevnik;
import org.foi.nwtis.ivicelig.web.baza.TablicaGrupa;
import org.foi.nwtis.ivicelig.web.podaci.Dnevnik;
import org.foi.nwtis.ivicelig.web.podaci.Grupa;

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
                    boolean azuriranje = komandaAzuiranje(komanda);
                    if (provjeriDaliKorisnikPostoji(komanda.get(2))) {
                        
                        if (!(dodavanje)) {
                            if (!azuriranje) {
                                if (provjeriKorisnickoImeIlozinku(komanda.get(2), komanda.get(4))) {
                                    
                                    doAction(komanda, os);
                                    
                                } else {
                                    //Korisničko ime i lozinka ne odgovoaraju

                                    pisiUOutputStream(os, "ERR 11;");
                                }
                            } else {
                                //Ako je azuiranje, azuiraj
                                Korisnik k = new Korisnik(komanda.get(2), komanda.get(4), komanda.get(7), komanda.get(8));
                                TablicaKorisnici tk = new TablicaKorisnici();
                                tk.update(k);
                                pisiUOutputStream(os, "OK 10;");
                            }
                        } else {
                            //Ako je komanda DODAJ korisnika i korisničko ime postoji
                            pisiUOutputStream(os, "ERR 10;");
                        }
                        
                    } else {
                        //Korisnik ne postoji

                        if ("DODAJ".equals(komanda.get(6))) {
                            //TODO dodaj korisnika u sustav
                            Korisnik k = new Korisnik(komanda.get(2), komanda.get(4), komanda.get(7), komanda.get(8));
                            TablicaKorisnici tk = new TablicaKorisnici();
                            tk.insert(k);
                            TablicaGrupa tg = new TablicaGrupa();
                            Grupa grupa = new Grupa(komanda.get(2),"nwtis");
        
                            tg.insert(grupa);
                              pisiUOutputStream(os, "OK 10;");
                            
                        } else {
                            pisiUOutputStream(os, "ERR 11;");
                        }
                    }
                    
                } else {
                    if (rp.ulazZadovoljavavaRegexAutentifikacija(sb.toString())) {
                        ArrayList<String> komanda = rp.dohvatiRegexGrupeAutentifikacija(sb.toString());
                        if (provjeriDaliKorisnikPostoji(komanda.get(2))) {
                            if (provjeriKorisnickoImeIlozinku(komanda.get(2), komanda.get(4))) {
                                pisiUOutputStream(os, "OK 10;");
                            } else {
                                pisiUOutputStream(os, "ERR 11;");
                            }
                        } else {
                            pisiUOutputStream(os, "ERR 11;");
                        }
                        
                    } else {
                        pisiUOutputStream(os, "NAREDBA NIJE U ISPRAVNOM OBLIKU!");;
                        //TODO regex ne zadovoljava nijedan regex
                    }
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
    
    private boolean komandaAzuiranje(ArrayList<String> komanda) {
        //Test ako je komanda za dodavanje korisnika
        if (komanda.size() >= 7) {
            if (komanda.get(6).equals("AZURIRAJ")) {
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
                upisiUdnevnik("Poslužiteljska naredba", "pauza");
                staviUstanjePauze(kd, os);
                break;
            case "KRENI":
                staviUStanjeKreni(kd, os);
                upisiUdnevnik("Poslužiteljska naredba", "kreni");
                break;
            case "PASIVNO":
                staviUStanjePasivno(md, os);
                upisiUdnevnik("Poslužiteljska naredba", "pasivno");
                break;
            case "AKTIVNO":
                staviUStanjeAktivno(md, os);
                upisiUdnevnik("Poslužiteljska naredba", "aktivno");
                break;
            case "STANI":
                md.radi = false;
                kd.interrupt();
                upisiUdnevnik("Poslužiteljska naredba", "stani");
                break;
            case "STANJE":
                posaljiStanje(md, kd, os);
                upisiUdnevnik("Poslužiteljska naredba", "stanje");
                break;
            
            case "LISTAJ":
                posaljiKorisnikeIzBaze(os);
                upisiUdnevnik("Poslužiteljska naredba", "listaj");
                
                break;
            
            default:
                //GRUPA naredbe
                if (!kd.pauza) {
                    switch (komanda.get(7)) {
                        case "DODAJ":
                            registrirajGrupu(os);
                            upisiUdnevnik("Naredba grupe", "dodaj");
                            break;
                        case "PREKID":
                            deregistrirajGrupu(os);
                            upisiUdnevnik("Naredba grupe", "prekid");
                            break;
                        case "KRENI":
                            aktivirajGrupu(os);
                            upisiUdnevnik("Naredba grupe", "kreni");
                            break;
                        case "PAUZA":
                            blokirajGrupu(os);
                            upisiUdnevnik("Naredba grupe", "pauza");
                            break;
                        case "STANJE":
                            dajStanje(os);
                            upisiUdnevnik("Naredba grupe", "stanje");
                            
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
    
    private void dajStanje(OutputStream os) {
        StatusKorisnika sk = dajStatusGrupe(korime, lozinka);
        if (sk.value().equals(StatusKorisnika.AKTIVAN.toString())) {
            
            pisiUOutputStream(os, "OK 21; GRUPA JE AKTIVNA;");
        } else if (sk.value().equals(StatusKorisnika.BLOKIRAN.toString())) {
            pisiUOutputStream(os, "OK 22; GRUPA JE BLOKIRANA;");
        } else {
            pisiUOutputStream(os, "ERR 21;");
        }
    }
    
    private void blokirajGrupu(OutputStream os) {
        StatusKorisnika sk = dajStatusGrupe(korime, lozinka);
        if (sk.value().equals(StatusKorisnika.DEREGISTRIRAN.toString())) {
            pisiUOutputStream(os, "ERR 21; DEREGISTRIRANA JE;");
        } else if (!sk.value().equals(StatusKorisnika.AKTIVAN.toString())) {
            pisiUOutputStream(os, "ERR 22; NIJE AKTIVIRANA;");
        } else {
            blokirajGrupu(korime, lozinka);
            pisiUOutputStream(os, "OK 20;");
        }
    }
    
    private void aktivirajGrupu(OutputStream os) {
        StatusKorisnika sk = dajStatusGrupe(korime, lozinka);
        if (sk.value().equals(StatusKorisnika.AKTIVAN.toString())) {
            pisiUOutputStream(os, "ERR 22; AKTIVIRANA JE;");
        } else if (sk.value().equals(StatusKorisnika.DEREGISTRIRAN.toString())) {
            pisiUOutputStream(os, "ERR 21; DEREGISTRIRANA JE;");
        } else {
            
            aktivirajGrupu(korime, lozinka);
            pisiUOutputStream(os, "OK 20;");
        }
    }
    
    private void deregistrirajGrupu(OutputStream os) {
        StatusKorisnika sk = dajStatusGrupe(korime, lozinka);
        
        if (!sk.value().equals(StatusKorisnika.DEREGISTRIRAN.toString())) {
            deregistrirajGrupu(korime, lozinka);
            
            pisiUOutputStream(os, "OK 20; GRUPA JE DEREGISTRIRANA;");
        } else {
            pisiUOutputStream(os, "ERR 21;");
        }
    }
    
    private void registrirajGrupu(OutputStream os) {
        StatusKorisnika sk = dajStatusGrupe(korime, lozinka);
        
        if (sk.value().equals(StatusKorisnika.DEREGISTRIRAN.toString())) {
            registrirajGrupu(korime, lozinka);
            
            pisiUOutputStream(os, "OK 20; GRUPA JE REGISTRIRANA;");
        } else {
            pisiUOutputStream(os, "ERR 20;");
        }
    }
    
    private void staviUStanjeAktivno(final MeteoDretva md, OutputStream os) {
        if (!md.pasivno) {
            
            pisiUOutputStream(os, "ERR 15;");
        } else {
            md.pasivno = false;
            
            pisiUOutputStream(os, "OK 10;");
        }
    }
    
    private void staviUStanjePasivno(final MeteoDretva md, OutputStream os) {
        if (md.pasivno) {
            
            pisiUOutputStream(os, "ERR 14;");
        } else {
            md.pasivno = true;
            
            pisiUOutputStream(os, "OK 10;");
        }
    }
    
    private void staviUStanjeKreni(final KomunikacijskaDretva kd, OutputStream os) {
        if (!kd.pauza) {
            pisiUOutputStream(os, "ERR 13;");
            
        } else {
            kd.pauza = false;
            pisiUOutputStream(os, "OK 10;");
            
        }
    }
    
    private void staviUstanjePauze(final KomunikacijskaDretva kd, OutputStream os) {
        if (kd.pauza) {
            pisiUOutputStream(os, "ERR 12;");
            
        } else {
            kd.pauza = true;
            pisiUOutputStream(os, "OK 10;");
            
        }
    }
    
    private void posaljiKorisnikeIzBaze(OutputStream os) {
        TablicaKorisnici tk = new TablicaKorisnici();
        List<Korisnik> korisnici = tk.getAllRecords();
        if (korisnici.isEmpty()) {
            pisiUOutputStream(os, "ERR 17;");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("OK 10; [");
            for (int i = 0; i < korisnici.size(); i++) {
                if (i == (korisnici.size() - 1)) {
                    sb.append("{\"ki\":").append(korisnici.get(i).getKorIme()).append(", \"prezime\":").append(korisnici.get(i).getPrezime()).append(", \"ime\":").append(korisnici.get(i).getIme()).append("}");
                    break;
                }
                sb.append("{\"ki\":").append(korisnici.get(i).getKorIme()).append(", \"prezime\":").append(korisnici.get(i).getPrezime()).append(", \"ime\":").append(korisnici.get(i).getIme()).append("},");
            }
            sb.append("];");
            pisiUOutputStream(os, sb.toString());
        }
    }
    
    private void posaljiStanje(final MeteoDretva md, final KomunikacijskaDretva kd, OutputStream os) {
        if (!md.pasivno && !kd.pauza) {
            pisiUOutputStream(os, "OK 11; Preuzima sve meteo podatke i komande;");
        }
        if (md.pasivno && !kd.pauza) {
            pisiUOutputStream(os, "OK 12;Preuzima sve komande i ne preuzima meteo podatke;");
        }
        if (!md.pasivno && kd.pauza) {
            pisiUOutputStream(os, "OK 13;Preuzima samo poslužiteljske komande i meteo podatke;");
        }
        if (md.pasivno && kd.pauza) {
            pisiUOutputStream(os, "OK 14;Preuzima samo poslužiteljske komande i ne preuzima meteo podatke;");
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
    
    private static Boolean deregistrirajGrupu(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.deregistrirajGrupu(korisnickoIme, korisnickaLozinka);
    }
    
    private static Boolean aktivirajGrupu(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.aktivirajGrupu(korisnickoIme, korisnickaLozinka);
    }
    
    private static Boolean blokirajGrupu(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service service = new org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje_Service();
        org.foi.nwtis.ivicelig.ws.klijenti.Parkiranje port = service.getParkiranjePort();
        return port.blokirajGrupu(korisnickoIme, korisnickaLozinka);
    }
    
    private void pisiUOutputStream(OutputStream os, String poruka) {
        try {
            os.write(poruka.getBytes());
            os.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void upisiUdnevnik(String naziv, String opis){
         TablicaDnevnik td = new TablicaDnevnik();
                Dnevnik d = new Dnevnik();
               d.setNaziv(naziv);
               d.setOpis(opis);
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sCertDate = sdf.format(new Date());
               d.setVrijeme(sCertDate);
            td.insert(d);

    }
}
