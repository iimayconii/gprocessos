/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author maycon
 */
public class UserNotLogged extends Exception {

    public UserNotLogged(String msg) {
        super(msg);
    }
    
}
