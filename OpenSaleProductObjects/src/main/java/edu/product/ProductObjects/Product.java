/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.product.ProductObjects;

import edu.common.Permissions.VerifyPermissions;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author mcbeckler
 */
@VerifyPermissions
@MappedSuperclass
public class Product implements Serializable {
    
    @Id
    @Column(nullable = false)
    protected Integer UPC;
    
    @Column(nullable = false)
    protected String productName;
    
    @Column(nullable = false)
    protected String productDescription;
    
    @Column(nullable = false)
    protected Double price;
    
    @Column(nullable = true)
    protected String memberName;
    
    @Column(nullable = true)
    protected Double weight;
    
    @Column(nullable = true)
    protected Boolean priceByWeight;
    
    public Integer getUPC() {
        return UPC;
    }

    public void setUPC(Integer UPC) {
        this.UPC = UPC;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getPriceByWeight() {
        return priceByWeight;
    }

    public void setPriceByWeight(Boolean priceByWeight) {
        this.priceByWeight = priceByWeight;
    }
}
