/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paypal.Payment;

/**
 *
 * @author Jacob
 */
public class CashFactory {
    
    public CashPayment create(){
        return new CashPayment();
    }
    
}
