
package edu.kennesaw.seniorproject.opensale.entities;

import edu.product.ProductObjects.Product;
import edu.transaction.TransactionObjects.Item;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity class for Item objects
 * @author spencer
 */
@Entity
public class ItemEntity extends Item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Product getProduct() {
       return this.product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Integer getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public Integer getUPC() {
        return this.UPC;
    }

    @Override
    public void setUPC(Integer UPC) {
        this.UPC = UPC;
    }

    @Override
    public Double getPurchasedWeight() {
        return this.purchasedWeight;
    }

    @Override
    public void setPurchasedWeight(Double purchasedWeight) {
        this.purchasedWeight = purchasedWeight;
    }
}
