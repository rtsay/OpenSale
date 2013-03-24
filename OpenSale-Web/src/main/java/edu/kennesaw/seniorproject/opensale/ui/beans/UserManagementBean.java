
package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The bean (viewmodel) behind the User Management screen.
 * @author Jacob
 */

@ManagedBean(name="userManagementBean")
@SessionScoped
public class UserManagementBean {

    @PersistenceContext
    private EntityManager entityManager;
    
    // Fields
    private String username;
    private String password;
    private User currentUser;  

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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    private Collection<User> allUsers;
    
     public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }    
   
    
    public String createUser(User user){
        entityManager.persist(user);
        return "userManagement";
    }
    
    public List<User> getUsers(){
        //Need to correct the query.
        Query userSearch = entityManager.createNamedQuery("UserEntity.findByUsername");
        userSearch.setParameter("username", this.username);
        List<User> searchedUser  = null; 
         try {
            searchedUser = (List<User>) userSearch.getResultList();
        } catch(javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("No Users exist.");            
        }
        
        return searchedUser;        
    }
    
    public String modifyUser(User user){
        return "userManagement";
    }
    
    
    public String deleteUser(User user){
        return "userManagement";
    }
    
   

}
