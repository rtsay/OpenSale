package edu.kennesaw.seniorproject.opensale.ui.beans.products;

import edu.kennesaw.seniorproject.opensale.entities.ProductEntity;
import edu.kennesaw.seniorproject.opensale.ui.beans.LoginBean;
import edu.kennesaw.seniorproject.opensale.ui.beans.users.EditUserBean;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
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
 *
 * @author spencer
 */
@ManagedBean(name = "editProductBean")
@SessionScoped
public class EditProductBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction ut;
    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    private ProductEntity product;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    /**
     * Creates a new instance of EditProductBean
     */
    public EditProductBean() {
    }

    /**
     * Saves the edited Product's current state.
     *
     * @return a JSF navigation redirect.
     */
    public String save() {
        String destinationPage = null;
        try {
            // Open a Transaction
            ut.begin();

            try {
                entityManager.find(ProductEntity.class, product.getUPC());
                entityManager.merge(product);
                ut.commit();
            } catch (javax.persistence.EntityNotFoundException e) {
                entityManager.persist(product);
                // Commit transaction
                ut.commit();
            } catch (Exception e) {
                InPageMessage.addErrorMessage(e.getMessage());
                InPageMessage.addErrorMessage("SOMETHING WENT HORRIBLY WRONG");
            } finally {
                // next stop: productList page
                destinationPage = "catalog";
            }
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
