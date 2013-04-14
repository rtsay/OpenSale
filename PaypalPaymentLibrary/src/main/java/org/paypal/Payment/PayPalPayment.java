/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paypal.Payment;

import edu.opensale.Payment.LegalTender;
import edu.opensale.Payment.Payment;
import edu.payment.Exceptions.PaymentMethodMissingException;

/**
 *
 * @author mcbeckler
 */
class PayPalPayment extends Payment {

    @Override
    public boolean execute(LegalTender payment) throws PaymentMethodMissingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
