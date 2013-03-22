/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.Payment;
import edu.opensale.PaymentTypes.PaymentFactory;
import edu.payment.Exceptions.PaymentMethodMissingException;

/**
 *
 * @author mcbeckler
 */
public abstract class Payment
{
    public void processPayment(LegalTender payment, PaymentFactory factory) throws PaymentMethodMissingException
    {
        if (factory != null)
            factory.create().execute(payment);
        throw new PaymentMethodMissingException();
    }

    protected abstract void execute(LegalTender payment);
}
