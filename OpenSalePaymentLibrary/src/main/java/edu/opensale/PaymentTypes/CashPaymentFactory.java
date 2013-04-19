/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.PaymentTypes;

import edu.opensale.Payment.Payment;
import edu.opensale.PaymentMethods.CashPayment;

/**
 *
 * @author mcbeckler
 */
public class CashPaymentFactory implements PaymentFactory {

    public Payment create() {
        return new CashPayment();
    }
    
}
