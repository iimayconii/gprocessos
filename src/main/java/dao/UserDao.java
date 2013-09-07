/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.services.GenericDaoImpl;
import model.User;
import org.hibernate.criterion.Restrictions;
import java.util.List;

/**
 *
 * @author maycon
 */
public class UserDao extends GenericDaoImpl<User, Long>{
    public User getUser(String name, String password){
        List<User> findByCriteria = findByCriteria(Restrictions.eq("name", name), Restrictions.eq("password", password));
        if(findByCriteria == null || findByCriteria.isEmpty()){
            return null;
        }
        return findByCriteria.get(0);
    }        
}
