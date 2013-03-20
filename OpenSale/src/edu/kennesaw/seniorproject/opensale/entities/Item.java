
package edu.kennesaw.seniorproject.opensale.entities;

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
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Product product;
    
    private Integer quantity;
    private Integer UPC;
    private Double purchasedWeight;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kennesaw.seniorproject.opensale.model.Item[ id=" + id + " ]";
    }
    
}
