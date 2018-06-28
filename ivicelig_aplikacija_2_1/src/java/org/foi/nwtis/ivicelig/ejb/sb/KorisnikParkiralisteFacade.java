/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.ivicelig.ejb.eb.Dnevnik;
import org.foi.nwtis.ivicelig.ejb.eb.Dnevnik_;
import org.foi.nwtis.ivicelig.ejb.eb.KorisnikParkiraliste;
import org.foi.nwtis.ivicelig.ejb.eb.KorisnikParkiraliste_;

/**
 *
 * @author Ivica
 */
@Stateless
public class KorisnikParkiralisteFacade extends AbstractFacade<KorisnikParkiraliste> {

    @PersistenceContext(unitName = "ivicelig_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KorisnikParkiralisteFacade() {
        super(KorisnikParkiraliste.class);
    }
     public List<KorisnikParkiraliste> getDataByKorisnik(String korisnik) {

        CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = qb.createQuery();
        Root<KorisnikParkiraliste> korisnikParkiraliste = cq.from(KorisnikParkiraliste.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<>();

        //Adding predicates in case of parameter not being null
        
        if (korisnik != null) {
            predicates.add(
                    qb.equal(korisnikParkiraliste.get(KorisnikParkiraliste_.korisnickoIme), korisnik));
        }
        
        //Final query
        cq.select(korisnikParkiraliste)
               .where(predicates.toArray(new Predicate[]{}));
        
        

        return getEntityManager().createQuery(cq).getResultList();
    }
}
