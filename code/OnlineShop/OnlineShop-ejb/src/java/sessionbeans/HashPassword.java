/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.Local;

/**
 *
 * @author louise
 */
@Local
public interface HashPassword {
    
    String hashPassword(String password);
    
    String hashPassword(String salt, String password);
    
}
