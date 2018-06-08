/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.web.baza;

/**
 *
 * @author Ivica
 * @param <T>
 */
public interface TablicaInterface<T> {

    public boolean insert(T t);

    public String createInsertQuery(T t);

}
