/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author Ivica
 */
@Stateful
@LocalBean
public class StatefulSB {
        private String status;
    @PostConstruct
  void init() {
    status = "Ready Stateful";
      System.out.println(status);
  }
  
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
