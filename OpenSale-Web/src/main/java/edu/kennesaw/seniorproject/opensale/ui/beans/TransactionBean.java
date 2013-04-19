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
    
    @ManagedProperty("#{checkoutBean}")
    private CheckoutBean checkoutBean;
    
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

    public CheckoutBean getCheckoutBean() {
        return checkoutBean;
    }

    public void setCheckoutBean(CheckoutBean checkoutBean) {
        this.checkoutBean = checkoutBean;
    }    
   
    public boolean isTransactionInProgress() {
        return (this.currentTransaction != null);
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
            newItemQuantity = newItemQuantity == null ? 1 : newItemQuantity; // default quantity is 1
            newItemWeight = newItemWeight == null ? 0.0 : newItemWeight;  // default item weight is 0lbs                
            i.setPurchasedWeight(newItemWeight); // set the weight for the Item
            i.setQuantity(newItemQuantity); // set the Quantity
            this.currentTransaction.addItem(i); // add the item to the transaction. 
            
            ut.begin(); // open a UserTransaction to persist the Item
            em.persist(i); // persist the item                        
            ut.commit(); // commit the UserTransaction
            this.saveTransaction(); // save the current transaction
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
     * @param i Item to void from the current transaction
     * @return redirect to the Transaction View page.
     */
    public String voidItem(Item i) {
        int indexOfItem = this.currentTransaction.getItems().indexOf(i);
        if (indexOfItem > -1) {
            Item itemInList = this.currentTransaction.getItems().get(indexOfItem);
            itemInList.setIsVoided(true);
            this.currentTransaction.getItems().set(indexOfItem, itemInList);
        }
        return "transaction";
    }
    
     /**
     * Unvoids a given item from the current transaction and redirects/refreshes
     * the Transaction View page
     * @param i Item to void from the current transaction
     * @return redirect to the Transaction View page.
     */
    public String unvoidItem(Item i) {
        int indexOfItem = this.currentTransaction.getItems().indexOf(i);
        if (indexOfItem > -1) {
            Item itemInList = this.currentTransaction.getItems().get(indexOfItem);
            itemInList.setIsVoided(false);
            this.currentTransaction.getItems().set(indexOfItem, itemInList);
        }
        return "transaction";
    }
    
    public String checkout() {
        String destinationPage = null;
        if (this.currentTransaction != null) {
            this.saveTransaction(); // save the current transaction
            checkoutBean.setCurrentTransaction(currentTransaction); // send this transaction to the checkoutBean
            destinationPage = "checkout"; // FIXME: need to figure out correct view name
        } else {
            destinationPage = "mainMenu";
            InPageMessage.addErrorMessage("No transaction in progress!");
        }
        return destinationPage; // go to the next page
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
    
    /**
     * Helper method to persist/update the current transaction.
     */
    private void saveTransaction() {
        try {
            ut.begin();
            if (em.contains(this.currentTransaction)) { 
                 em.merge(this.currentTransaction); // if we've already persisted the Transaction, update it
            } else {
                 em.persist(this.currentTransaction); // if we haven't persist it now
            }
            ut.commit();
        } catch (RollbackException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
