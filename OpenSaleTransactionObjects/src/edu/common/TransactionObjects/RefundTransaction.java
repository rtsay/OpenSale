/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.TransactionObjects;

import edu.opensale.Payment.LegalTender;

/**
 *
 * @author Jacob
 */
public class RefundTransaction extends Transaction{

    @Override
    public boolean processPayment(LegalTender legalTender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verifyPermission(edu.common.UserObjects.User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean applyOverride(edu.common.UserObjects.User manager, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
