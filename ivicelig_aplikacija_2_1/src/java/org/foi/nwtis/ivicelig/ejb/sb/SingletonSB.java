/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author Ivica
 */
@Startup
@Singleton
@LocalBean
public class SingletonSB {

    private String status;

  @PostConstruct
  void init() {
    status = "Ready";
      System.out.println(status);
  }
}
