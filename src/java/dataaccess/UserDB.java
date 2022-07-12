package dataaccess;

import com.sun.prism.Texture;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

/**
 *
 * @author Alex Tompkins - 821984
 */
public class UserDB {

    /**
     * Get all users
     * @return
     * @throws Exception 
     */
    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
        List<User> usersList = em.createNamedQuery("User.findAll",User.class ).getResultList();
        return usersList;
        }finally{
            em.close();
        }
    }

    
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
        User user = em.find(User.class, email);
        return user;
        }finally{
            em.close();
        }
    }

    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            //retrieve the role so you can update the user list in the role object
            //bi-directional relationship
            Role role = user.getRole();
            role.getUserList().add(user);
            //start the transaction
            trans.begin();
            
            em.persist(user);
            //merge/update role object with the changes made(added the user to the role object)
            em.merge(role);
            trans.commit();
        
        }catch(Exception e){
        trans.rollback();
        }finally{
            em.close();
        }

    }

    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            //bi-directional relationship
            Role role = user.getRole();
            role.getUserList().add(user);
            //start transaction
            trans.begin();
            //update the user
            em.merge(user);
            //update the role
            em.merge(role);
            trans.commit();
        
        }catch(Exception e){
        trans.rollback();
        }finally{
            em.close();
        }
    }

    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            //bi-directional relationship
            Role role = user.getRole();
            role.getUserList().remove(user);
            //start stransaction
            trans.begin();
            //merging first ensures that the user that is passed to be removed, exists in the database
            em.remove(em.merge(user));
            em.merge(role);
            trans.commit();
        
        }catch(Exception e){
        trans.rollback();
        }finally{
            em.close();
        }

    }
}
