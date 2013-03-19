/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.objects;

import edu.kennesaw.seniorproject.opensale.model.User;
import java.util.Stack;

/**
 * Static session object.
 * Grabs the current user for internal objects.
 * Uses a stack to allow multiple users.
 * @author mcbeckler
 */
public class Session {
    private static Stack<User> currentUsers;
    
    public static User getCurrentUser()
    {
        return currentUsers.peek();
    }
    
    public static void Login(User currentUser)
    {
        if (currentUsers == null)
            currentUsers = new Stack<User>();
        currentUsers.push(currentUser);
    }
    
    public static void Logout()
    {
        currentUsers.pop();
    }
}
