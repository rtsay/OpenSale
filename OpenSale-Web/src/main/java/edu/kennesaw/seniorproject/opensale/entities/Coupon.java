/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Jacob
 */
@Entity
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean percentageIsTrue = false;
    private UserEntity user;
    private int couponCode;
    
    public void setCouponCode(int newCouponCode){
       this.couponCode=newCouponCode;
    }
    
    public int getCouponCode(){
        return couponCode;
    }
    
    public boolean percentageIsTrue(){
        return percentageIsTrue;
    }
    
    public void percentageIsTrue(boolean newPercentageIsTrue){
        this.percentageIsTrue = newPercentageIsTrue;
    }
    
     public boolean verifyCouponeCode(){
         return true;
    //todo
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Coupon)) {
            return false;
        }
        Coupon other = (Coupon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kennesaw.seniorproject.opensale.model.Coupon[ id=" + id + " ]";
    }
    
}
