/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.TransactionObjects;

/**
 *
 * @author mcbeckler
 */
public class Item {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUPC() {
        return UPC;
    }

    public void setUPC(Integer UPC) {
        this.UPC = UPC;
    }

    public Double getPurchasedWeight() {
        return purchasedWeight;
    }

    public void setPurchasedWeight(Double purchasedWeight) {
        this.purchasedWeight = purchasedWeight;
    }
    private Integer quantity;
    private Integer UPC;
    private Double purchasedWeight;
}
