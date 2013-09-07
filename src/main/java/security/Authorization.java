package security;

import dao.UserDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author maycon
 */
public class Authorization extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(new HashSet<String>(((User) pc.getPrimaryPrincipal()).getRoles()));
        //simpleAuthorizationInfo.addObjectPermissions(((User) pc.getPrimaryPrincipal()).getWildPermissions());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) at;
        System.out.println("Login");
        System.out.println("Nome : " + token.getUsername());                

        User aUser = new UserDao().getUser(token.getUsername(), new String(token.getPassword()));
        if(aUser == null){
            throw new AuthenticationException("erro login");
        }
        

        return new SimpleAuthenticationInfo(aUser, aUser.getPassword(), aUser.getName());
    }
}
