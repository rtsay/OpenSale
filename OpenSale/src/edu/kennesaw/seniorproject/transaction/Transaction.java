/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.transaction;

import edu.kennesaw.seniorproject.opensale.model.Item;
import edu.kennesaw.seniorproject.opensale.model.User;
import edu.kennesaw.seniorproject.opensale.payment.LegalTender;
import edu.kennesaw.seniorproject.opensale.payment.Payment;
import java.util.ArrayList;

/**
 *
 * @author Jacob
 */
public abstract class Transaction {
    
    private ArrayList<Item> items;
    
    private User User;
    
    private Payment paymentMethod;
    
    private LegalTender payment;
    
    
    public void addItem(Item item){
        this.items.add(item);
    }
    
//    public void addItem(int itemInt){
//        //todo once ItemLookup is completed
//    }
    
    public void removeItem(Item item){
        this.items.remove(item);
    }
    
//    public void removeItem(int itemInt){
//        //todo once ItemLookup is completed.
//    }
    
    public void setPayment(LegalTender legalTender){
        this.payment = legalTender;
    }
    
//    public double generateSubtotal(){      
//       //todo once we figure out we will calculate the total
//    }
    
    public abstract void applyOverride (double price);
    
    public abstract boolean processPayment(LegalTender legalTender);
    
    public abstract boolean verifyPermission(User user);
    
    
    
}
