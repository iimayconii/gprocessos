/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;


import model.User;
import org.apache.shiro.SecurityUtils;

/**
 * Classe de utilidades para o contexto de segurança.
 *
 * @author Maycon
 * @version 1.0
 */

public class UserSecurityUtils {

    /**
     * Verifica no contexto do Spring Security se o usuario está logado no
     * sistema
     * @since 1.0
     * @return se o usuario está logado
     */
    public static boolean isUserLogged() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    /**
     * Busca no contexto do Spring Security o obejto usuario que foi inserido
     * no metodo {@link CustomUserDetailsService#loadUserByUsername(java.lang.String) }
     * @see CustomUserDetailsService#loadUserByUsername(java.lang.String)
     *
     * @since 1.0
     * @exception UserNotLogged
     * @return o obejto usuario
     */
    public static User getUser() throws UserNotLogged{
        if(isUserLogged()){
            return (User) SecurityUtils.getSubject().getPrincipal();
        }
        throw new UserNotLogged("User not logged!");

    }


}
