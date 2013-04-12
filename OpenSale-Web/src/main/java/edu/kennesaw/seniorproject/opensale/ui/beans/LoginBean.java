package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.Exceptions.NoCurrentSessionException;
import edu.common.Static.Session;
import edu.common.UserObjects.EUserTypes;
import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.Hasher;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public User getCurrentUser() throws NoCurrentSessionException { 
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
    
    public boolean isLoggedIn() throws NoCurrentSessionException {
        return (Session.getCurrentUser() != null);
    }
    
    private boolean currentUserIsRole(EUserTypes role) throws NoCurrentSessionException {
        return (Session.getCurrentUser().getUserType() != null && 
                Session.getCurrentUser().getUserType() == role);
    }
    
    public boolean currentUserIsManager() throws NoCurrentSessionException {
        return this.currentUserIsRole(EUserTypes.Manager);
    }
    
    public boolean currentUserIsAdmin() throws NoCurrentSessionException {
        return this.currentUserIsRole(EUserTypes.Adminstrator);
    }
    
    public boolean currentUserIsSuperUser() throws NoCurrentSessionException {
        return this.currentUserIsRole(EUserTypes.SuperUser);
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
        
        // Search for a user with the given username and password (hashed).
        Query userSearch = entityManager.createNamedQuery("UserEntity.findUserByLogin");
        userSearch.setParameter("username", this.username);
        userSearch.setParameter("password", Hasher.hashPassword(this.password));
        UserEntity searchedUser = null;
        try {
            searchedUser = (UserEntity) userSearch.getSingleResult();
        } catch(javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("Invalid username/password.");
            Logger.getLogger("LoginBean").log(Level.INFO, "Invalid login attempt for user: {0}", this.username);
        }

        // If we find a user with that username,
        if (searchedUser != null) {
            
           // Log the login
           Logger.getLogger("LoginBean").log(Level.INFO, "Logged in user: {0}", this.username);            
            
            /* Set current user as a property of this session 
             * bean so that we can access it later.  */
            this.setCurrentUser(searchedUser); 
            destinationPage = "mainMenu";                            
        } else { 
            InPageMessage.addErrorMessage("Invalid username/password."); 
        }       
        return destinationPage;
    }
    
    /**
     * Logs out the current user (destroying the session in the process).
     * @return "index" redirect to login page.
     */
    public String logout() {
        String destination = null;
        try { 
            if (Session.getStackSize() > 1) { // IF there are multiple users logged in
                destination = "mainMenu"; // we're just popping the top one off, so go back to the Main Menu
                Session.Logout(); // logout the current user
            } else { // if there's only one user logged in.
                destination = "index"; // next stop is the index.
                Session.Logout(); // log out the current user
                // This is a magic thing I did have to look up to destroy the current browser session (just to be safe)
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            }                        
        } catch (NoCurrentSessionException ex) { // if for some reason someone tries to log out without having first logged in           
            destination = "index"; // eh, just bounce back to the login page.
        } finally {
            return destination;
        }                       
    }
    
    public void checkLogin() throws IOException {
        try {
            if(this.getCurrentUser() == null) {
                InPageMessage.addErrorMessage("Please log in.");
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
            }
        } catch (NoCurrentSessionException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Please log in.");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        }
    }
}
