/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.opensale.Payment.LegalTender;
import edu.opensale.PaymentTypes.PaymentFactory;
import edu.transaction.TransactionObjects.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * Backing bean for the "finish and pay" process
 * @author spencer
 */
@ManagedBean
@SessionScoped
public class CheckoutBean {

    /**
     * Creates a new instance of CheckoutBean
     */
    public CheckoutBean() { }
    
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction ut;
    private Transaction currentTransaction;
    
    private String paymentType, creditCardNumber;

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }    
    
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    } 

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }        
    
    /**
     * Submits the payment for processing.
     * @return JSF redirect to the next page
     */
    public String submit() {
        String destinationPage = null;
        /**
         * This method needs to:
         *  1. get payment information as formdata
         *  2. Create a Payment object
         *  3. Process the payment
         *  4. Render a view with results
         */
        
        /* 1. Get payment information as formdata -- done in-page */
        
        /* 2. Create a Payment object -- will need to use PaymentFactory for 
              that.*/
        LegalTender lt = new LegalTender();
        
        /* 3. Process the payment. */
        try {
            // Get the appropriate PaymentFactory type
            PaymentFactory p = (PaymentFactory)Class.forName(paymentType + "Payment").newInstance();
            
            // Process the payment
            currentTransaction.processPayment(lt, p);
        } catch (Exception ex) {
            Logger.getLogger(CheckoutBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Payment failed.");
        }
                        
        /* 4. Render a view with the results -- we need to create a view for 
              this. */
        return destinationPage;
    }
    
}
