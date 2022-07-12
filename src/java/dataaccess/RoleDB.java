package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

/**
 *
 * @author Alex Tompkins - 821984
 */
public class RoleDB {

    public Role getRole(Integer roleId){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            Role role = em.find(Role.class, roleId);
            return role;
        }finally{
            em.close();
        }
    }
    public List<Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Role> rolesList = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return rolesList;
        } finally {
            em.close();
        }
    }
}
 
