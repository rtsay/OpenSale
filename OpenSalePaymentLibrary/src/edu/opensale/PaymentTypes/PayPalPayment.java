/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.PaymentTypes;

import edu.opensale.Payment.LegalTender;
import edu.opensale.Payment.Payment;

/**
 *
 * @author mcbeckler
 */
class PayPalPayment extends Payment {
    
    public PayPalPayment()
    {
        super("PayPal");
    }

    @Override
    protected void execute(LegalTender payment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void accept(IPaymentVisitor visitor) {
        visitor.visit(this);
    }
}
