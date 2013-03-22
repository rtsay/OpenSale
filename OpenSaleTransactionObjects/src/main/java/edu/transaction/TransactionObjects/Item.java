/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.transaction.TransactionObjects;

import edu.product.ProductObjects.Product;

/**
 *
 * @author mcbeckler
 */
public abstract class Item {

    protected Product product;
    protected Integer quantity;
    protected Integer UPC;
    protected Double purchasedWeight;

    public abstract Product getProduct();

    public abstract void setProduct(Product product);

    public abstract Integer getQuantity();

    public abstract void setQuantity(Integer quantity);

    public abstract Integer getUPC();

    public abstract void setUPC(Integer UPC);

    public abstract Double getPurchasedWeight();

    public abstract void setPurchasedWeight(Double purchasedWeight);
}
