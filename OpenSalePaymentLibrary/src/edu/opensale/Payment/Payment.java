/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.Payment;
import edu.opensale.PaymentTypes.IPaymentObject;

/**
 *
 * @author mcbeckler
 */
public abstract class Payment implements IPaymentObject
{
    protected String PaymentTypeName;
   
    public Payment(String name)
    {
        PaymentTypeName = name;
    }
    
    public void processPayment(LegalTender payment) throws PaymentMethodMissingException
    {
        if (this != null)
            this.execute(payment);
        throw new PaymentMethodMissingException();
    }
    
    public String getName()
    {
        return this.PaymentTypeName;
    }
    
    protected abstract void execute(LegalTender payment);
}
