package edu.kennesaw.seniorproject.opensale.ui.beans;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import edu.common.Static.Session;
import edu.common.UserObjects.EUserTypes;
import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sun.misc.BASE64Encoder;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    /**
     * This is the "EntityManager." It's used to get at persistent storage
     * (database).
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    // Fields
    private String username;
    private String password;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User getCurrentUser() { 
       return Session.getCurrentUser();
    }

    private void setCurrentUser(User currentUser) {
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
    
    public boolean isLoggedIn() {
        return (Session.getCurrentUser() != null);
    }
    
    private boolean currentUserIsRole(EUserTypes role) {
        return (Session.getCurrentUser().getUserType() != null && 
                Session.getCurrentUser().getUserType() == role);
    }
    
    public boolean currentUserIsManager() {
        return this.currentUserIsRole(EUserTypes.Manager);
    }
    
    public boolean currentUserIsAdmin() {
        return this.currentUserIsRole(EUserTypes.Adminstrator);
    }
    
    public boolean currentUserIsSuperUser() {
        return this.currentUserIsRole(EUserTypes.SuperUser);
    }    

    private String hashPassword() {
        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.hashString(this.password);
        BASE64Encoder base64 = new BASE64Encoder();
        String hashedPassword = base64.encode(hc.asBytes());
       return hashedPassword;
    }
    
    /**
     * Checks to see if a user with the provided username exists. If so, checks
     * to see if the password matches. If all of that is successful, redirect
     * the user to the main menu. Otherwise, bounce back to the login page.
     * @return destinationPage
     */
    public String login() {
        // Default to bounce back to the login page
        String destinationPage = null;
        
        // First, prep the password by hashing it.
        
        // Search for a user with the username given           
        Query userSearch = entityManager.createNamedQuery("UserEntity.findUserByLogin");
        userSearch.setParameter("username", this.username);
        userSearch.setParameter("password", this.hashPassword());
        UserEntity searchedUser = null;
        try {
            searchedUser = (UserEntity) userSearch.getSingleResult();
        } catch(javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("Invalid username/password.");
            Logger.getLogger("LoginBean").log(Level.INFO, "Invalid login attempt for user: " + this.username);
        }

        // If we find a user with that username,
        if (searchedUser != null) {
            
           // Log the login
           Logger.getLogger("LoginBean").log(Level.INFO, "Logged in user: " + this.username);            
            
            /* Set current user as a property of this session 
             * bean so that we can access it later.  */
            this.setCurrentUser(searchedUser); 
            destinationPage = "mainMenu";                            
        } else { 
            InPageMessage.addErrorMessage("Invalid username/password."); 
        }       
        return destinationPage;
    }
}
