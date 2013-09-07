/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author maycon
 */
public class PassThruAuthentication extends org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter{

    @Override
    public String getLoginUrl() {
        return "index.jsf";
    }


}
