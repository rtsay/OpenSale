/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.PaymentTypes;

import edu.opensale.Payment.Payment;
import java.util.ArrayList;

/**
 *
 * @author mcbeckler
 */
public class PaymentTypes implements IPaymentVisitor {
    private ArrayList<String> types;
    
    public PaymentTypes()
    {
        types = new ArrayList<String>();
    }
    
    
    @Override
    public void visit(Payment payment) {
        this.types.add(payment.getName());
    }
    
}
