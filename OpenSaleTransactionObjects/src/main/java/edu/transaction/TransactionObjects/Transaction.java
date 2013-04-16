/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.transaction.TransactionObjects;

import edu.common.Permissions.VerifyPermissions;
import edu.common.UserObjects.User;
import edu.common.globalSettings.GlobalSettings;
import edu.opensale.Payment.LegalTender;
import edu.opensale.Payment.Payment;
import edu.opensale.PaymentTypes.PaymentFactory;
import edu.payment.Exceptions.PaymentMethodMissingException;
import java.util.ArrayList;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Jacob
 */
@VerifyPermissions
@MappedSuperclass
public abstract class Transaction {
    
    protected ArrayList<Item> items;
    protected User User;
    protected Payment paymentMethod;
    protected LegalTender payment;
    protected String transactionType;
    
    public Transaction() {        
        this.items = new ArrayList<Item>();
    }
    
    public void addItem(Item item){       
        this.items.add(item);               
    }
    
    public void voidItem(Item item){
        Item i = this.items.get(this.items.indexOf(item));
        i.setIsVoided(true);
        this.items.set(this.items.indexOf(item), i);
    }
    
    public void unvoidItem(Item item) {
        Item i = this.items.get(this.items.indexOf(item));
        i.setIsVoided(false);
        this.items.set(this.items.indexOf(item), i);
    }
    
    public void setPayment(LegalTender legalTender){
        this.payment = legalTender;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }        
    
    public double generateSubtotal() {  
       double amount = 0.0;
        for (Item temp : items) {
            if (temp.getIsVoided()) { // don't include voided items in total
                continue;
            }
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
    
    public double generateTotal() {
        return this.generateSubtotal() + (this.generateSubtotal() * GlobalSettings.getTaxRate());
    }
   
    public boolean processPayment(LegalTender legalTender, PaymentFactory factory) throws PaymentMethodMissingException
    {
        Payment p = factory.create();
        return p.execute(legalTender);
    }
    
    public abstract boolean verifyPermission(User user);
    
    public abstract boolean applyOverride(User manager, double price);
}
