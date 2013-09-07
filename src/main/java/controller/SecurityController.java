/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.User;
import security.UserNotLogged;
import security.UserSecurityUtils;

/**
 *
 * @author maycon
 */
@ManagedBean(name = "userSecurity")
@RequestScoped
public class SecurityController {

    public SecurityController() {
    }
    public User getUser(){
        try {
            return UserSecurityUtils.getUser();
        } catch (UserNotLogged ex) {
            Logger.getLogger(SecurityController.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return null;
    }
}
