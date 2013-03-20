/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paypal.Payment;

import edu.opensale.Payment.Payment;
import edu.opensale.PaymentTypes.PaymentFactory;

/**
 *
 * @author mcbeckler
 */
public class PayPalFactory implements PaymentFactory {

    @Override
    public Payment create() {
        return new PayPalPayment();
    }
    
}
