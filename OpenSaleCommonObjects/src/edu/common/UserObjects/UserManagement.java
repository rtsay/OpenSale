/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

import edu.common.Interfaces.IVerifyPermissions;
import java.util.Collection;

/**
 *
 * @author Jacob
 */
public class UserManagement {
    
    User user;
    
    Collection<User> allUsers;
    
    public User getUser(){
        return this.user;
    }        
    
//    public boolean createUser(User user){  
//       TODO
//    }
//    
//    public boolean modifyUser(User user){
//        TODO
//    }
//    
//    public boolean deleteUser(User user){
//        TODO
//    }
//    
//    public boolean verifyPermissions(IVerifyPermissions iVerifyPermission){
//        TODO
//    }
    
    public void loadAllUsers(){
        
    }
    
}
