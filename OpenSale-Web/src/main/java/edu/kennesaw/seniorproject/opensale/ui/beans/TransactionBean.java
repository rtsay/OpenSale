package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.kennesaw.seniorproject.opensale.entities.ItemEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.product.ProductObjects.Product;
import edu.transaction.TransactionObjects.Item;
import edu.transaction.TransactionObjects.Transaction;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    public String addItem(Integer upc, Double weight, Integer quantity) {
        Query q = em.createNamedQuery("ProductEntity.findProductByUPC");
        q.setParameter("upc", upc);
        try {
            Product p = (Product)q.getSingleResult();
            Item i = new ItemEntity();
            i.setProduct(p);
            i.setPurchasedWeight(weight);
            i.setQuantity(quantity);
            em.persist(i);
            this.currentTransaction.addItem(i);
        } catch(javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("Product does not exist.");
        } 
        return "currentTransaction";
    }
    
    /**
     * Removes a given item from the current transaction and redirects/refreshes
     * the Transaction View page
     * @param i Item to remove from the current transaction
     * @return redirect to the Transaction View page.
     */
    public String removeItem(Integer upc) {
        for (Item i : this.currentTransaction.getItems()) {
            if (i.getProduct().getUPC() == upc)
            {
                this.currentTransaction.removeItem(i);
            }
        }
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
