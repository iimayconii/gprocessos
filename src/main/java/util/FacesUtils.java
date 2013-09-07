/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author maycon
 * @version 1.0
 *
 */
public class FacesUtils {

    public static ExternalContext getExternalContext() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext != null) {
                return facesContext.getExternalContext();
            }
        } catch (Exception exc) {
            //Logger.getLogger(FacesUtils.class.getName()).log(Level.ERROR, null, exc);
        }
        return null;
    }

    public static Object getSessionAttribute(String attributeName) {
        try {
            ExternalContext externalContext = getExternalContext();
            if (externalContext != null) {
                Map map = externalContext.getSessionMap();
                if (map != null) {
                    return map.get(attributeName);
                }
            }
        } catch (Exception exc) {
           // Logger.getLogger(FacesUtils.class.getName()).log(Level.ERROR, null, exc);
        }
        return null;
    }
    
    public static Object getViewAttribute(String attributeName, FacesContext faces){
        Map<String, Object> viewMap = faces.getViewRoot().getViewMap();
        Object viewScopedBean = viewMap.get(attributeName);
        return viewScopedBean;
    }

    public static Object getViewAttribute(String attributeName){
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        Object viewScopedBean = viewMap.get(attributeName);
        return viewScopedBean;
    }

    public static Object getApplicationAttribute(String attributeName) {
        try {
            ExternalContext externalContext = getExternalContext();
            if (externalContext != null) {
                Map map = externalContext.getApplicationMap();
                if (map != null) {
                    return map.get(attributeName);
                }
            }
        } catch (Exception exc) {
           // Logger.getLogger(FacesUtils.class.getName()).log(Level.ERROR, null, exc);
        }
        return null;
    }

    public static void addFacesMsg(String msg, FacesMessage.Severity level) {
       FacesMessage t = new FacesMessage(level, msg, null);
       FacesContext.getCurrentInstance().addMessage(null, t);
    }
}
