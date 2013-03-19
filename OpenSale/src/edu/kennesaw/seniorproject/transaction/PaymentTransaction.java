/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.transaction;

import edu.kennesaw.seniorproject.opensale.model.User;
import edu.kennesaw.seniorproject.opensale.payment.LegalTender;

/**
 *
 * @author Jacob
 */
public class PaymentTransaction extends Transaction{

    @Override
    public void applyOverride(double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean processPayment(LegalTender legalTender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verifyPermission(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
