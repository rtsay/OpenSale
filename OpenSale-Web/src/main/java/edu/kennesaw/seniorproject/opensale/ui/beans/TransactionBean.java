package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.kennesaw.seniorproject.opensale.entities.ItemEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.opensale.Payment.LegalTender;
import edu.product.ProductObjects.Product;
import edu.transaction.TransactionObjects.Item;
import edu.transaction.TransactionObjects.Payment;
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
    
    private Integer newItemUPC, newItemQuantity;
    private Double newItemWeight;
            
    
    /* TODO: figure out how to determine Payment Type, use PaymentFactory to 
     * create an appropriate Payment from given payment details.
     */
    private Double paymentAmount;

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

   
    /**
     * Adds a given item to the current transaction and redirects/refreshes the
     * Transaction View page
     * @return redirect to the Transaction View page.
     */
    public String addItem() {        
        Query q = em.createNamedQuery("ProductEntity.findProductByUPC");
        q.setParameter("upc", newItemUPC);
        try {
            Product p = (Product)q.getSingleResult();
            Item i = new ItemEntity();
            i.setProduct(p);
            i.setPurchasedWeight(newItemWeight);
            i.setQuantity(newItemQuantity);
            em.persist(i);
            this.currentTransaction.addItem(i);
        } catch(javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("Product does not exist.");
        } 
        return "currentTransaction";
    }
        
    /**
     * Voids a given item from the current transaction and redirects/refreshes
     * the Transaction View page
     * @param upc UPC of Item to void from the current transaction
     * @return redirect to the Transaction View page.
     */
    public String voidItem(Integer upc) {
        for (Item i : this.currentTransaction.getItems()) {
            if (i.getProduct().getUPC() == upc)
            {
                this.currentTransaction.voidItem(i);
            }
        }
        return "currentTransaction";
    }
    
    /**
     * Unvoids a given item from the current transaction and redirects/refreshes
     * the Transaction View page
     * @param upc UPC of Item to void from the current transaction
     * @return redirect to the Transaction View page.
     */
    public String unvoidItem(Integer upc) {
        for (Item i : this.currentTransaction.getItems()) {
            if (i.getProduct().getUPC() == upc)
            {
                this.currentTransaction.unvoidItem(i);
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
        
        /* 1. Get payment information as formdata -- done by having a "pay now" 
              primefaces dialog */
        
        /* 2. Create a Payment object -- will need to use PaymentFactory for 
              that.*/
        LegalTender lt = new LegalTender();
        
        /* 3. Process the payment. */
        currentTransaction.processPayment(lt);
                        
        /* 4. Render a view with the results -- we need to create a view for 
              this. */
        return "mainMenu";
    }
    
}
