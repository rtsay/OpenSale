package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.Exceptions.InsufficentPermissionException;
import edu.common.Exceptions.NoCurrentSessionException;
import edu.common.Static.Session;
import edu.common.UserObjects.EUserTypes;
import edu.common.UserObjects.Permissions;
import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * The bean (viewmodel) behind the User Management screen.
 *
 * @author Jacob
 */
@ManagedBean(name = "userManagementBean")
@SessionScoped
public class UserManagementBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction ut;
    @ManagedProperty("#{loginBean}")
    LoginBean loginBean;
    
    // Fields    
    private UserEntity editedUser = null;
    private Long editedUserId;
    private String newUserName, newPassword;
    private EUserTypes newUserType;

    //AN INFINITE LAND OF GETTER AND SETTERS!
    
    public int getNewUserType() {
        return newUserType.getValue();
    }

    public void setNewUserType(int newUserType) {        
        for (EUserTypes a : EUserTypes.values()) {
            if (a.getValue() == newUserType) {
                this.newUserType = a;
            }
        }
    }
   
    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public UserEntity getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(UserEntity editedUser) {
        this.editedUser = editedUser;
    }

    public Long getEditedUserId() {
        return editedUserId;
    }

    public void setEditedUserId(Long editedUserId) {
        this.editedUserId = editedUserId;
    }
   
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }           

    /**
     * Fetches users editable by the current user.
     *
     * @return List<UserEntity>
     */
    public List<UserEntity> getUsers() throws NoCurrentSessionException {
        // Fetch editable users with a Named Query
        Query userSearch = entityManager.createNamedQuery("UserEntity.getEditableUsers");
        userSearch.setParameter("loggedInUserType",
                this.loginBean.getCurrentUser().getUserType());

        List<UserEntity> searchedUser = null;
        try {
            searchedUser = (List<UserEntity>) userSearch.getResultList();
        } catch (javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("No Users exist.");
        }

        return searchedUser;
    }
    
    /**
     * Helper method to handle adding or updating user entities.
     * @param u UserEntity to save.
     * @return next page redirect.
     */
    private String saveUser(UserEntity u) {
        try {
            ut.begin();
            if (entityManager.contains(u)) {
                entityManager.merge(u);
            } else {
                entityManager.persist(u);
            }
            ut.commit();
            return "userList";            
        } catch (RollbackException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        } catch (SecurityException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        } catch (IllegalStateException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        } catch (NotSupportedException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        } catch (SystemException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage(("Something went wrong saving this user. Please refresh and try again."));
        }
        return null;
    }
    
    /**
     * Creates a new user and adds it to the databasssssss
     * @return NOTHING... except a direction
     */
    public String addNewUser() {
        UserEntity newUser;
        try {
            // Try to create a new user using provided details.
            newUser = new UserEntity(newUserName, newPassword, newUserType, new Permissions(), Session.getCurrentUser());            
            return saveUser(newUser);            
         } catch (InsufficentPermissionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("You're not allowed to do this.");
            return null;
        } catch (NoCurrentSessionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("No one is logged in?!?");
            return "index";    
        }        
    }

    /**
     * Selects a user from the User List for editing and redirects to the Edit User screen.
     * @return destination page name
     */
    public String editUser() {
        this.editedUser = entityManager.find(UserEntity.class, this.editedUserId);
        if (this.editedUser == null) {
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
            return null;
        }
        return "editUser";        
    }
    
    /**
     * Saves changes made in the Edit User screen and redirects to the User List screen.
     * @return destination page name
     */
    public String saveUserChanges() {
        
        String destinationPage = null;        
        
        // If there's no user selected for editing, something's broken. Bail!
        if (this.editedUser == null) {
            InPageMessage.addErrorMessage("Something went wrong. Please return to the User List and try again.");
            return null;
        }
            
        return saveUser(this.editedUser);                        
    }

    /**
     * Deletes a user from the database -- ONLY if they exist.
     * @return redirect to the appropriate page (usually the userList)
     */
    public String deleteUser() {
        try {
            // Start a UserTransaction to edit entities
            ut.begin();

            // Look up the user in the database by username
            UserEntity userToDelete = entityManager.find(UserEntity.class, this.editedUserId);
            
            // If we get no results, show an error message and return to the User List.
            if (userToDelete == null) {
                InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
                return null;
            }
                        
            // Delete the user
            entityManager.remove(userToDelete);

            // Commit the transaction
            ut.commit();

            // A giant stack of catch blocks
        } catch (RollbackException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (SecurityException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (IllegalStateException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (NotSupportedException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (SystemException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        }
        return null;
    }
}
