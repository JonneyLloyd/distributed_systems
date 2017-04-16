/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import POJO.LogMessage;
import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface LogUpdater {
    void addMessageToLog(LogMessage msg);
}
