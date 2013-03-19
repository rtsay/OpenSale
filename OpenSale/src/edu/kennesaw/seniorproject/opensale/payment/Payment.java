/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.payment;

import edu.kennesaw.seniorproject.opensale.model.User;

/**
 *
 * @author Jacob
 */
public abstract class Payment {
    
    private User user;
    
    public abstract Boolean processPayment(LegalTender LegalTender);
    
    public abstract Boolean verifyPermission(User user);
    
    
    
}
