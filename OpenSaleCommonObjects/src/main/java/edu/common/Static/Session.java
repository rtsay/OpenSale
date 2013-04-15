/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.Static;

import edu.common.Exceptions.NoCurrentSessionException;
import java.util.Stack;
import edu.common.UserObjects.User;

/**
 * Static session object.
 * Grabs the current user for internal objects.
 * Uses a stack to allow multiple users.
 * @author mcbeckler
 */
public class Session {
    private Stack<User> currentUsers = new Stack<User>();
    
    public User getCurrentUser() throws NoCurrentSessionException
    {
        if (!currentUsers.empty())
            return currentUsers.peek();
        else
            throw new NoCurrentSessionException();
    }
    
    public void Login(User currentUser)
    {
        currentUsers.push(currentUser);
    }
    
    public void Logout() throws NoCurrentSessionException
    {
        if (!currentUsers.empty())
            currentUsers.pop();
        else
            throw new NoCurrentSessionException();
    }
    
    public Integer getStackSize() {
        return currentUsers.size();
    }
}
