/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.baza;

import java.util.List;
import org.foi.nwtis.ivicelig.web.podaci.Parkiraliste;

/**
 *
 * @author Ivica
 * @param <T>
 */
public interface TablicaInterface<T> {

    public boolean insert(T t);
    public boolean update(T t);
    public List<? extends Object> getAllRecords();
    public String createInsertQuery(T t);

}
