/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.transaction.TransactionObjects;

import edu.opensale.Payment.LegalTender;
import edu.common.UserObjects.User;

/**
 *
 * @author Jacob
 */
public class PaymentTransaction extends Transaction {

    @Override
    public boolean processPayment(LegalTender legalTender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verifyPermission(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean applyOverride(User manager, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
