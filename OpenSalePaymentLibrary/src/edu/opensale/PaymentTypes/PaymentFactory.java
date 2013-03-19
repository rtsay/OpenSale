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
public interface PaymentFactory {
    public Payment create();
}
