/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.entities;

import edu.opensale.Payment.LegalTender;
import edu.transaction.TransactionObjects.Transaction;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Jacob
 */
@Entity
public class RefundTransaction extends Transaction implements Serializable{
    @Id
    private Long id;

    @Override
    public boolean processPayment(LegalTender legalTender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verifyPermission(edu.common.UserObjects.User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean applyOverride(edu.common.UserObjects.User manager, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
