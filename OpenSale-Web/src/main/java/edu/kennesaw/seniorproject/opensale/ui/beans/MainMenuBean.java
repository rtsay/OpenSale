
package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.kennesaw.seniorproject.opensale.entities.PaymentTransaction;
import edu.kennesaw.seniorproject.opensale.entities.RefundTransaction;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.Hasher;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.transaction.TransactionObjects.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The bean (viewmodel) behind the Main Menu screen.
 * @author spencer
 */
@ManagedBean(name="mainMenuBean")
@SessionScoped
public class MainMenuBean {

    @PersistenceContext
    private EntityManager em;
    
    /* The ManagedProperty decorator allows you to reference another ManagedBean
     * as a property of the current class. The "Value" field specifies the name
     * of the bean we're referencing (as it's known by JSF -- see the "name"
     * property in the @ManagedBean decorator in each bean class. */    
    @ManagedProperty(value="#{transactionBean}")
    private TransactionBean transactionBean;
    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    
    // Properties used for manager authorization
    private String authorizingManagerUsername, authorizingManagerPassword;
    
    /**
     * Creates a new instance of MainMenuBean
     */
    public MainMenuBean() {
    }

    public TransactionBean getTransactionBean() {
        return transactionBean;
    }

    public void setTransactionBean(TransactionBean transactionBean) {
        this.transactionBean = transactionBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }   

    public String getAuthorizingManagerUsername() {
        return authorizingManagerUsername;
    }

    public void setAuthorizingManagerUsername(String authorizingManagerUsername) {
        this.authorizingManagerUsername = authorizingManagerUsername;
    }

    public String getAuthorizingManagerPassword() {
        return authorizingManagerPassword;
    }

    public void setAuthorizingManagerPassword(String authorizingManagerPassword) {
        this.authorizingManagerPassword = authorizingManagerPassword;
    }        
    
    /**
     * Creates a new PaymentTransaction and redirects to the menu view.
     * @return 
     */
    public String createSale() {
        Transaction paymentTransaction = new PaymentTransaction();        
        transactionBean.setCurrentTransaction(paymentTransaction);                
        return "transaction";
    }
    
    public String createRefund() {   
        // Next-stop page, as a string
        String destinationPage = null;
        
        // Extra safety: check to make sure we actually got a username/password
        if (authorizingManagerUsername == null || authorizingManagerPassword == null) {
            InPageMessage.addErrorMessage("Please specify both username and password.");            
        } else {
        
            // Look up the authorizing manager by the given username and (hashed) password
            Query lookupAuthorizingManager = em.createNamedQuery("UserEntity.findManagerByLogin");
            lookupAuthorizingManager.setParameter("username", authorizingManagerUsername);
            lookupAuthorizingManager.setParameter("password", Hasher.hashPassword(authorizingManagerPassword));
            UserEntity authorizingManager;
            try { // anything inside this block assumes we found at least one result
                // store the authorizingManager -- TODO: have authorizing managers stored as part of RefundTransaction metadata.
                authorizingManager = (UserEntity) lookupAuthorizingManager.getSingleResult();            

                // Create a new RefundTransaction and set it as the current transaction for transactionBean
                Transaction refundTransaction = new RefundTransaction();
                transactionBean.setCurrentTransaction(refundTransaction);

                // next stop: transaction page
                destinationPage = "transaction";            
            } catch(javax.persistence.NoResultException e) { // in this block, we didn't find at least one result.
                InPageMessage.addErrorMessage("Authorizing manager not recognized");
                Logger.getLogger("MainMenuBean").log(Level.INFO, "Invalid refund authorization attempt for user: {0}", authorizingManagerUsername);
            }                
        }
        return destinationPage;
    }
    
    
}
