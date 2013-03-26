
package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.kennesaw.seniorproject.opensale.entities.PaymentTransaction;
import edu.transaction.TransactionObjects.Transaction;
import edu.kennesaw.seniorproject.opensale.entities.RefundTransaction;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    @ManagedProperty(value="transactionBean")
    private TransactionBean transactionBean;
    
    /**
     * Creates a new instance of MainMenuBean
     */
    public MainMenuBean() {
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
        Boolean refundAuthorized = true;
        /** 
         * TODO: Manager authorization.
         * Ideas: for getting auth: 
         *  have a jQuery-appearing form submit manager username/password
         *  to properties of this bean, then look up the manager. If the lookup
         *  succeeds, the transaction's authorized. If not, return to main menu.
         */
        
        if(refundAuthorized) {
            Transaction refundTransaction = new RefundTransaction();
            transactionBean.setCurrentTransaction(refundTransaction);
            return "currentTransaction";
        } else {
            return "mainMenu";
        }
    }
    
    
}
