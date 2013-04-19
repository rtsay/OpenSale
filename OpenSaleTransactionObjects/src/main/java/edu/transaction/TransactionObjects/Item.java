/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.transaction.TransactionObjects;

import edu.product.ProductObjects.Product;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author mcbeckler
 */
@MappedSuperclass
public abstract class Item implements Serializable{

    @Column(nullable = false)
    protected Product product;
    @Column(nullable = false)
    protected Integer quantity;
    @Column(nullable = true)
    protected Double purchasedWeight;
    @Column(nullable = false)
    protected Boolean isVoided = false;

    public Boolean getIsVoided() {        
        return isVoided;
    }

    public void setIsVoided(Boolean isVoided) {
        this.isVoided = isVoided;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPurchasedWeight() {
        return this.purchasedWeight;
    }

    public void setPurchasedWeight(Double purchasedWeight) {
        this.purchasedWeight = purchasedWeight;
    }
}
