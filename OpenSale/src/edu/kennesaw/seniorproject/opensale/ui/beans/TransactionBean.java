package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.transaction.TransactionObjects.Transaction;
import edu.transaction.TransactionObjects.Item;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Backing bean for Transaction view pages.
 * @author spencer
 */
@ManagedBean(name="transactionBean")
@SessionScoped
public class TransactionBean {

    public TransactionBean() { }
    
    @PersistenceContext
    private EntityManager em;    
    private Transaction currentTransaction;

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
    
    /**
     * Adds a given item to the current transaction and redirects/refreshes the
     * Transaction View page
     * @param i Item to add to the current transaction
     * @return redirect to the Transaction View page.
     */
    public String addItem(Item i) {
        this.currentTransaction.addItem(i);
        return "currentTransaction";
    }
    
    /**
     * Removes a given item from the current transaction and redirects/refreshes
     * the Transaction View page
     * @param i Item to remove from the current transaction
     * @return redirect to the Transaction View page.
     */
    public String removeItem(Item i) {
        this.currentTransaction.removeItem(i);
        return "currentTransaction";
    }
    
    /**
     * TODO: This method
     * @return redirect to the appropriate page.
     */
    public String completeTransaction() {
        /**
         * This method needs to:
         *  1. get payment information as formdata
         *  2. Create a Payment object
         *  3. Process the payment
         *  4. Render a view with results
         */
        return "mainMenu";
    }
    
}
