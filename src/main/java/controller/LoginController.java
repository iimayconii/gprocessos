package controller;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.WebUtils;
import util.FacesUtils;

/**
 *
 * @author maycon
 */
@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController implements java.io.Serializable {

    String name;
    String password;

    public LoginController() {
        this.name = "";
        this.password = "";
    }

    public String login() throws ServletException {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            UsernamePasswordToken userToken = new UsernamePasswordToken(name, password);
            SecurityUtils.getSubject().login(userToken);
            try {
                SecurityUtils.getSubject().login(userToken);
            } catch (org.apache.shiro.authc.IncorrectCredentialsException exc) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ErrorLogin", exc);
            } catch (AuthenticationException e) {
                FacesUtils.addFacesMsg("Erro login", null);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ErrorLogin", e);
            }
        }
        return redirectPageAfterLogin();
    }

    public String redirectPageAfterLogin() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            if (SecurityUtils.getSubject().hasRole("administrador")) {
                return "pretty:admin";
            } else {
                return "pretty:user";
            }
        } else {
            return null;
        }
    }

    public String logout() throws ServletException, IOException {
        System.out.println("Chamou");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            SecurityUtils.getSubject().logout();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            WebUtils.redirectToSavedRequest((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse(), "/index.jsf");
            return "/index.jsf";
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
