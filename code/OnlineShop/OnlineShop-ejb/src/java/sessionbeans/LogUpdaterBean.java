/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import POJO.LogMessage;
import entities.Log;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author louise
 */
@Stateless
@LocalBean
public class LogUpdaterBean implements LogUpdater {

    @PersistenceContext(unitName = "OnlineShop-ejbPU")
    private EntityManager em;
    
    /**
     * Adds passed LogMessage to Log database
     *
     * @param msg to write to log
     */
    @Override
    public void addMessageToLog(LogMessage msg) {
        Log logMsg;
        
        logMsg = new Log();
        logMsg.setUserId(msg.getUser());
        logMsg.setMessage(msg.getMessage());
        logMsg.setDateTime(new Date());
      
        //update database
        em.persist(logMsg);
        em.flush();
    }
}
