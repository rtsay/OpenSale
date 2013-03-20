package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.Static.Session;
import edu.common.UserObjects.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean {
	
	/** 
         * This is the "EntityManager." 
         * It's used to get at persistent storage (database).
         **/
	@PersistenceContext
	private EntityManager em;
	
	private String username;
	private String password;

	public User getCurrentUser() {
            return Session.getCurrentUser();
	}

	public void setCurrentUser(User currentUser) {
            Session.Login(currentUser);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Checks to see if a user with the provided username exists.
	 * If so, checks to see if the password matches.
	 * If all of that is successful, redirect the user to the main menu.
	 * Otherwise, bounce back to the login page.
	 * @return destinationPage
	 */
	public String login() {
            // Default to bounce back to the login page
            String destinationPage = null;

            // Search for a user with the username given
            User searchedUser = em.find(User.class, this.username);

            /**
             * TODO: REMOVE THIS UGLY WORKAROUND! This is just a temporary
             * test thing.
             */
                setCurrentUser(searchedUser); 
                destinationPage = "mainMenu";
            
            // If we find a user with that username,
            if (searchedUser != null) {                
                try {
                    // hash the password we were given
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(this.password.getBytes("UTF-8"));
                    byte[] digest = md.digest();				
                    String hashedPassword = digest.toString(); 
                    Logger.getLogger(LoginBean.class.getName()).log(Level.INFO, "Found user " + searchedUser);

                    // and check to see if it matches the hashed password of the user we found.
                    if (searchedUser.getPassword().equals(hashedPassword)) {
                        // if it matches, we're headed to the main menu.
                        /* Set current user as a property of this session 
                        * bean so that we can access it later.  */
                        setCurrentUser(searchedUser);
                        destinationPage = "mainMenu";
                    }
                } catch (UnsupportedEncodingException ex) {  // getBytes might throw this
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) { // MessageDigest might throw this
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return destinationPage;
	}
		
}
