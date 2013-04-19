/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.transaction.TransactionObjects.Transaction;
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

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }        
}
