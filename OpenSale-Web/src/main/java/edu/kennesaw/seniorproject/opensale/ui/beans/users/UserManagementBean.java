package edu.kennesaw.seniorproject.opensale.ui.beans.users;

import edu.common.Exceptions.InsufficentPermissionException;
import edu.common.Exceptions.NoCurrentSessionException;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.beans.LoginBean;
import edu.kennesaw.seniorproject.opensale.ui.beans.users.EditUserBean;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
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
 * @author Jacob
 * @author spencer
 */
@ManagedBean(name = "userManagementBean")
@SessionScoped
public class UserManagementBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction ut;
    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    
    @ManagedProperty("#{editUserBean}")
    private EditUserBean editUserBean;
    
    // Fields    
    private UserEntity editedUser;
    private Long editedUserId; 

    //AN INFINITE LAND OF GETTER AND SETTERS!
        
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

    public EditUserBean getEditUserBean() {
        return editUserBean;
    }

    public void setEditUserBean(EditUserBean editUserBean) {
        this.editUserBean = editUserBean;
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

        // If all goes well, we'll have a List of UserEntities.
        List<UserEntity> userList = null;
        try {
            userList = (List<UserEntity>) userSearch.getResultList();
        } catch (javax.persistence.NoResultException e) {
            InPageMessage.addInfoMessage("No Users exist.");
        }

        return userList;
    }
   
    /**
     * Creates a new user and preps the EditUser page.
     * @return next page redirect
     */
    public String addNewUser() {
        String destinationPage = null;
//        try {
            // Try to create a new user using provided details.
            UserEntity newUser = new UserEntity();
            
            // Set the EditUserBean's user to edit as this one.
            editUserBean.setUser(newUser);
            
            // next stop: editUser!
            destinationPage = "editUser";
/*         } catch (InsufficentPermissionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("You're not allowed to do this.");
        } catch (NoCurrentSessionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("No one is logged in?!?");
            destinationPage = "index";    
        }*/
        return destinationPage;
    }

    /**
     * Selects a user from the User List for editing and redirects to the Edit User screen.
     * @return destination page name
     */
    public String editUser() {        
        String destinationPage = null;
                
        // Look up user to edit by Id.        
        editedUser = entityManager.find(UserEntity.class, this.editedUserId);
        if (editedUser == null) { // If we don't find one,  show an error message.
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");                        
        } else { // If we do find one,
            // set that as the user to be edited by editUserBean
            editUserBean.setUser(editedUser);                        
            // next stop: edit user page.
            destinationPage = "editUser";        
        }   
        return destinationPage;
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
