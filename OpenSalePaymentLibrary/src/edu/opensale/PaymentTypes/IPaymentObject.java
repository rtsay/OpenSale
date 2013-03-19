/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.opensale.PaymentTypes;

import edu.opensale.Payment.Payment;

/**
 *
 * @author mcbeckler
 */
public interface IPaymentObject {
    public void accept(IPaymentVisitor visitor);
}
