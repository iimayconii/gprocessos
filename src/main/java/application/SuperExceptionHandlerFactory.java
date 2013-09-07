/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author maycon
 */
public class SuperExceptionHandlerFactory extends ExceptionHandlerFactory{
    private ExceptionHandlerFactory parent;
    private SuperExceptionHandler chaced;

    public SuperExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }        

    @Override
    public ExceptionHandler getExceptionHandler() {        
        if(chaced == null){            
            chaced = new SuperExceptionHandler(parent.getExceptionHandler());
        }        
        return chaced;
    }
    
    public void handler(){
        
    }
    
}
