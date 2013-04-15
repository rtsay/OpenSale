package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.kennesaw.seniorproject.opensale.entities.ItemEntity;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.opensale.Payment.LegalTender;
import edu.opensale.PaymentTypes.PaymentFactory;
import edu.product.ProductObjects.Product;
import edu.transaction.TransactionObjects.Item;
import edu.transaction.TransactionObjects.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
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
 * Backing bean for Transaction view pages.
 * @author spencer
 */
@ManagedBean(name="transactionBean")
@SessionScoped
public class TransactionBean {

    public TransactionBean() { }
    
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction ut;
    private Transaction currentTransaction;
    
    private Integer newItemUPC, newItemQuantity;
    private Double paymentAmount;
    private Double newItemWeight;
    private PaymentFactory paymentType;

     /* TODO: figure out how to determine Payment Type, use PaymentFactory to 
     * create an appropriate Payment from given payment details.
     */
    public PaymentFactory getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentFactory paymentType) {
        this.paymentType = paymentType;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public Integer getNewItemUPC() {
        return newItemUPC;
    }

    public void setNewItemUPC(Integer newItemUPC) {
        this.newItemUPC = newItemUPC;
    }

    public Integer getNewItemQuantity() {
        return newItemQuantity;
    }

    public void setNewItemQuantity(Integer newItemQuantity) {
        this.newItemQuantity = newItemQuantity;
    }

    public Double getNewItemWeight() {
        return newItemWeight;
    }

    public void setNewItemWeight(Double newItemWeight) {
        this.newItemWeight = newItemWeight;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }    
   
    /**
     * Adds a given item to the current transaction and redirects/refreshes the
     * Transaction View page
     * @return redirect to the Transaction View page.
     */
    public String addItem() {        
        Query q = em.createNamedQuery("ProductEntity.findProductByUPC");
        q.setParameter("UPC", newItemUPC);
        try {
            Product p = (Product)q.getSingleResult(); // look up product by UPC
                        
            Item i = new ItemEntity(); // Create a new Item
            i.setProduct(p); // set the Product for that Item
            i.setPurchasedWeight(newItemWeight); // set the weight for the Item
            i.setQuantity(newItemQuantity); // set the Quantity
            this.currentTransaction.addItem(i); // add the item to the transaction. 
            
            ut.begin(); // open a UserTransaction to persist the Item
            em.persist(i); // persist the item            
            ut.commit(); // commit the UserTransaction            
        } catch (RollbackException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch (SecurityException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch (IllegalStateException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch (NotSupportedException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch (SystemException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong; please try again.");
        } catch(javax.persistence.NoResultException e) {
            InPageMessage.addErrorMessage("Product does not exist with UPC " + newItemUPC + ".");
        } 
        return "transaction";
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
        return "transaction";
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
        return "transaction";
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
        try {
        currentTransaction.processPayment(lt, paymentType);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Payment failed.");
        }
                        
        /* 4. Render a view with the results -- we need to create a view for 
              this. */
        return "mainMenu";
    }
    
}
