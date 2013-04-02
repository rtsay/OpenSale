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

	@Id        
	private Integer UPC;
	
	private String productName;
	private String productDescription;
	private Double price;
	private String memberName;
	private Double weight;
	private Boolean priceByWeight;
	
	private static final long serialVersionUID = 1L;

	public ProductEntity() {
		super();
	}   
	public Integer getUPC() {
		return this.UPC;
	}

	public void setUPC(Integer UPC) {
		this.UPC = UPC;
	}   
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}   
	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}   
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}   
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}   
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}   
	public Boolean getPriceByWeight() {
		return this.priceByWeight;
	}

	public void setPriceByWeight(Boolean priceByWeight) {
		this.priceByWeight = priceByWeight;
	}
   
}
