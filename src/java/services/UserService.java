/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.*;
import dataaccess.UserDB;
import java.util.List;

/**
 *
 * @author BTran
 */
public class UserService {
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }
    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }
    
    public void insert(String email, boolean activity, String first_name, String last_name, String password, Role role) throws Exception{
        User user = new User(email, activity, first_name, last_name, password);
        //bi-directional relationship
        user.setRole(role);
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }
    
    public void update(String email, boolean activity, String first_name, String last_name, String password, Role role) throws Exception{
        UserDB userDB = new UserDB();
        //retrieve the user that the user wants to update
        User user = userDB.get(email);
        //set the attributes of the user to the new values
        user.setActive(activity);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setRole(role);
        userDB.update(user);
    }
    
    public void delete(String email) throws Exception{
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        userDB.delete(user);
    }
}






