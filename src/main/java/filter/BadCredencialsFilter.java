/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


/**
 * Filtro que vefica o sucesso do login. A exction BadCredentials e lançada pelo
 * SpringS Security quando falha no login.
 *
 * @author maycon
 * @version 1.0
 *
 */
public class BadCredencialsFilter implements PhaseListener {

    @Override
    public void beforePhase(final PhaseEvent arg0) {
        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ErrorLogin");

        if (e instanceof org.apache.shiro.authc.IncorrectCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ErrorLogin", null);
            FacesMessage t = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Matricula ou senha incorreto.", null);
            FacesContext.getCurrentInstance().addMessage(null, t);
        }
    }

    @Override
    public void afterPhase(final PhaseEvent arg0) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
