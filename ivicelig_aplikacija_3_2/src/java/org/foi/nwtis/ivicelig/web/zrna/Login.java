/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.zrna;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.ivicelig.ejb.sb.StatefullSessionBean;


/**
 *
 * @author Ivica
 */
@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

    @EJB
    private StatefullSessionBean statefullSessionBean;

    private String korime;
    private String lozinka;
    
    
    
    public void login() {
        if (statefullSessionBean.provjeriLogin(korime, lozinka)) {
            System.out.println("Autentikacija uspješna!");
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session
                    = (HttpSession) context.getExternalContext().getSession(true);

            session.setAttribute("user", korime);
            session.setAttribute("lozinka", lozinka);

        } 

    }
    public void logout(){
        FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session
                    = (HttpSession) context.getExternalContext().getSession(true);

            session.removeAttribute("user");
            session.removeAttribute("lozinka");
    }

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

}
