/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.Payment;
import edu.payment.Exceptions.PaymentMethodMissingException;

/**
 *
 * @author mcbeckler
 */
public abstract class Payment
{ 
    public abstract boolean execute(LegalTender payment) throws PaymentMethodMissingException;
}
