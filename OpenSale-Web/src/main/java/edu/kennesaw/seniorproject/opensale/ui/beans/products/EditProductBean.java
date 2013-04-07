
package edu.kennesaw.seniorproject.opensale.ui.beans.products;

import edu.kennesaw.seniorproject.opensale.ui.beans.LoginBean;
import edu.kennesaw.seniorproject.opensale.ui.beans.users.EditUserBean;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.product.ProductObjects.Product;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Backing bean for editProduct page
 * @author spencer
 */
@ManagedBean(name="editProductBean")
@SessionScoped
public class EditProductBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction ut;
    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    
    private Product product;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
        
    
    /**
     * Creates a new instance of EditProductBean
     */
    public EditProductBean() {
    }
    
    /**
     * Saves the edited Product's current state.
     * @return a JSF navigation redirect.
     */
    public String save() {
        String destinationPage = null;
        try {
            // Open a Transaction
            ut.begin();
            
            // If the product doesn't have an Id, that means it's new.
            if (product.getUPC() == null) {
                entityManager.persist(product);
            } else { // if it does have an Id, it's an existing product.
                entityManager.merge(product);
            }
            
            // Commit transaction
            ut.commit();            
            
            // next stop: productList page
            destinationPage = "catalog";
            
        } catch (RollbackException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        } catch (SecurityException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        } catch (IllegalStateException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        } catch (NotSupportedException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        } catch (SystemException ex) {
            Logger.getLogger(EditUserBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Error saving product. Please return to the product list and try again.");
        }
        
        return destinationPage;
    }
    
}
