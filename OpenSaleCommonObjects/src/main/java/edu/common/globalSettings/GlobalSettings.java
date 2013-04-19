/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.globalSettings;

/**
 *
 * @author mcbeckler
 */

public class GlobalSettings {
    private static double taxRate = 0.07; // defaults to GA sales tax rate for demo purposes

    public static double getTaxRate() {
        return taxRate;
    }

    public static void setTaxRate(double taxRate) {
        GlobalSettings.taxRate = taxRate;
    }

}
