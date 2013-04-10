package edu.kennesaw.seniorproject.opensale.entities;

import edu.product.ProductObjects.Product;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Product
 * Represents the Product domain-model object.
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name="ProductEntity.findProductByUPC",
        query="select p from ProductEntity p where p.UPC = :UPC"
    ), // used for lookup by UPC; mostly just a meaningfully-named alias for using em.find
    @NamedQuery(
        name="ProductEntity.listProductsByName",
        query="select p from ProductEntity p where p.productName like :pattern"
    ), // used for product search by name
    @NamedQuery(
        name="ProductEntity.search",
        query="select p from ProductEntity p where p.UPC = :UPC or p.productName like :pattern"
    ), // used for product search by name or UPC
    @NamedQuery(
        name="ProductEntity.listAllProducts",
        query="select p from ProductEntity p"
    )
})
public class ProductEntity extends Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public ProductEntity() {
		super();
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
	public String getProductName() {
		return this.productName;
	}

        @Override
	public void setProductName(String productName) {
		this.productName = productName;
	}   
        @Override
	public String getProductDescription() {
		return this.productDescription;
	}

        @Override
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}   
        @Override
	public Double getPrice() {
		return this.price;
	}

        @Override
	public void setPrice(Double price) {
            this.price = price;
	}   
        @Override
	public String getMemberName() {
		return this.memberName;
	}

        @Override
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}   
        @Override
	public Double getWeight() {
		return this.weight;
	}

        @Override
	public void setWeight(Double weight) {
		this.weight = weight;
	}   
        @Override
	public Boolean getPriceByWeight() {
		return this.priceByWeight;
	}

        @Override
	public void setPriceByWeight(Boolean priceByWeight) {
		this.priceByWeight = priceByWeight;
	}
   
}
