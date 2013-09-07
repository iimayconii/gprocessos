/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.application.FacesMessage;

/**
 *
 * @author Maycon
 */
public interface GenericControllerInterface {
    
    public void edit();
    
    public void delete();
    
    public void add();
    
    public void onSelectListener();
    
    public boolean getMenu();
    
    public void addFacesMsg(String msg, FacesMessage.Severity level);    
}
