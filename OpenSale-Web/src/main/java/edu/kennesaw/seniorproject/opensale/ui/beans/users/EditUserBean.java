package edu.kennesaw.seniorproject.opensale.ui.beans.users;

import edu.common.UserObjects.EUserTypes;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.beans.LoginBean;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Backing bean for the Edit User and Create User pages.
 * @author spencer
 */
@ManagedBean(name="editUserBean")
@SessionScoped
public class EditUserBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction ut;
    @ManagedProperty("#{loginBean}")
    LoginBean loginBean;
    
    private UserEntity user;    

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }    
    
    
    /**
     * Creates a new instance of EditUserBean
     */
    public EditUserBean() { }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }        
        
    public EUserTypes[] getUserTypes() {
        return EUserTypes.values();
    }
    
    /**
     * Saves the edited UserEntity's current state.
     * @return a JSF navigation redirect.
     */
    public String save() {
        String destinationPage = null;
        try {
            // Open a Transaction
            ut.begin();
            
            // If the user doesn't have an Id, that means it's new.
            if (user.getId() == null) {
                entityManager.persist(user);
            } else { // if it does have an Id, it's an existing user.
                entityManager.merge(user);
            }
            
            // Commit transaction
            ut.commit();            
            
            // next stop: userList page
            destinationPage = "userList";
            
        } catch (RollbackException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        } catch (SecurityException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        } catch (IllegalStateException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        } catch (NotSupportedException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        } catch (SystemException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving user. Please return to the user list and try again.");
        }
        
        return destinationPage;
    }
}
