/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.payment;

/**
 *
 * @author Jacob
 */





public class LegalTender {

    
    
    private double amount;
    private char[] accountNbr;
    private char[] expDate;  
    private int verificationCode;
    
    public void LegalTender(double amount, char[] accountNbr, char[] expDate, int verificationCode){
        this.amount = amount;
        this.accountNbr = accountNbr;
        this.expDate = expDate;
        this.verificationCode = verificationCode;
    }   
    
    public double getAmount() {
        return amount;
    }

    public char[] getAccountNbr() {
        return accountNbr;
    }

    public char[] getExpDate() {
        return expDate;
    }
    
      public int getVerificationCode() {
        return verificationCode;
    }
}