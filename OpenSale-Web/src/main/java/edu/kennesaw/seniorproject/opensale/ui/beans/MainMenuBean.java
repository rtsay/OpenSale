
package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.Exceptions.NoCurrentSessionException;
import edu.common.UserObjects.EUserTypes;
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
        try {                        
            // Check to see if we have permission
            if (loginBean.getCurrentUser().getUserType().getValue() >= EUserTypes.Manager.getValue()) {
                
                // Create a new RefundTransaction and set it as the current transaction for transactionBean
                Transaction refundTransaction = new RefundTransaction();
                transactionBean.setCurrentTransaction(refundTransaction);
                
                // next stop: transaction page
                destinationPage = "transaction";            
            } else {
                InPageMessage.addErrorMessage("You are not authorized to create refund transactions.");
            }    
        } catch (NoCurrentSessionException ex) {
            Logger.getLogger(MainMenuBean.class.getName()).log(Level.SEVERE, null, ex);
            destinationPage = "index";
            InPageMessage.addErrorMessage("Please log in.");
        }
        return destinationPage;
    }
    
    
}
