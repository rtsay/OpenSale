/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.transaction.TransactionObjects;

import edu.common.UserObjects.User;
import edu.opensale.Payment.LegalTender;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jacob
 */
public abstract class Transaction {
    
    protected ArrayList<Item> items;
    protected User User;
    protected Payment paymentMethod;
    protected LegalTender payment;
    protected String transactionType;
    
    public void addItem(Item item){
        this.items.add(item);
    }
    
    public void removeItem(Item item){
        this.items.remove(item);
    }
    
    public void setPayment(LegalTender legalTender){
        this.payment = legalTender;
    }
    
    public double generateSubtotal() {  
       double amount = 0.0;
        for (Item temp : items) {
            if (temp.getProduct().getPriceByWeight())
            {
              amount += (temp.getPurchasedWeight() / temp.getProduct().getWeight()) * temp.getProduct().getPrice();
            }
            else
            {
                amount += temp.getProduct().getPrice() * temp.getQuantity();
            }
        }
       return amount;
    }
    
    public abstract boolean processPayment(LegalTender legalTender);
    
    public abstract boolean verifyPermission(User user);
    
    public abstract boolean applyOverride(User manager, double price);
}
