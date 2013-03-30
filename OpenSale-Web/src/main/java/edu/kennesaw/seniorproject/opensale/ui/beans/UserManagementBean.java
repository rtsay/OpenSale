package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.Exceptions.InsufficentPermissionException;
import edu.common.Exceptions.NoCurrentSessionException;
import edu.common.Static.Session;
import edu.common.UserObjects.EUserTypes;
import edu.common.Permissions.Permissions;
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
    private Collection<User> allUsers;
    private String editedUserName, newUserName, newPassword;
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

    public String getEditedUserName() {
        return editedUserName;
    }

    public void setEditedUserName(String editedUserName) {
        this.editedUserName = editedUserName;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String createUser(User user) {
        entityManager.persist(user);
        return "userManagement";
    }

    /**
     * Fetches users editable by the current user.
     *
     * @return List<User>
     */
    public List<User> getUsers() throws NoCurrentSessionException {
        // Fetch editable users with a Named Query
        Query userSearch = entityManager.createNamedQuery("UserEntity.getEditableUsers");
        userSearch.setParameter("loggedInUserType",
                this.loginBean.getCurrentUser().getUserType());

        List<User> searchedUser = null;
        try {
            searchedUser = (List<User>) userSearch.getResultList();
        } catch (javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("No Users exist.");
        }

        return searchedUser;
    }
    /**
     * Creates a new user and adds it to the databasssssss
     * @return NOTHING... except a direction
     */
    public String addNewUser() {
        User newUser;
        try {
            newUser = new UserEntity(newUserName, newPassword, newUserType, new Permissions(), Session.getCurrentUser());
        } catch (InsufficentPermissionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("You're not allowed to do this.");
            return null;
        } catch (NoCurrentSessionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("No one is logged in?!?");
            return null;
        }
        return "userManagement";
    }

    public String editUser() {
        Query lookupUser = entityManager.createNamedQuery("UserEntity.findUserByUsername");
        lookupUser.setParameter("username", this.editedUserName);

        try {
            this.editedUser = (UserEntity) lookupUser.getSingleResult();
            return "editUser";
        } catch (javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        }
        return null;
    }

    public String deleteUser() {
        try {
            // Start a UserTransaction to edit entities
            ut.begin();

            // Look up the user in the database by username
            Query lookupUser = entityManager.createNamedQuery("UserEntity.findUserByUsername");
            lookupUser.setParameter("username", this.editedUserName);
            UserEntity userToDelete = (UserEntity) lookupUser.getSingleResult();

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
