/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.PaymentMethods;

import edu.opensale.Payment.LegalTender;
import edu.opensale.Payment.Payment;
import edu.payment.Exceptions.PaymentMethodMissingException;

/**
 *
 * @author mcbeckler
 */
public class CashPayment extends Payment {

    @Override
    public boolean execute(LegalTender payment) throws PaymentMethodMissingException {
        return true;
    }
    
}
