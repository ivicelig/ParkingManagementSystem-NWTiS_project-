/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ivicelig.ejb.sb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 * @author Ivica
 */
//
@Singleton
@LocalBean
public class SingletonSB {

    @Resource(mappedName = "jms/NWTiS_ivicelig_2")
    private Queue nWTiS_ivicelig_2;

    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_ivicelig_2")
    private JMSContext context;

    private String status;


    public void sendJMSMessageToNWTiS_ivicelig_2(String messageData) {
        context.createProducer().send(nWTiS_ivicelig_2, messageData);
    }
  
  
}
