package edu.kennesaw.seniorproject.opensale.ui.beans.products;

import edu.kennesaw.seniorproject.opensale.entities.ProductEntity;
import edu.kennesaw.seniorproject.opensale.ui.beans.LoginBean;
import edu.kennesaw.seniorproject.opensale.ui.utilities.InPageMessage;
import edu.product.ProductObjects.Product;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Backing bean for Catalog management page
 * @author spencer
 */
@ManagedBean(name = "catalogManagementBean")
@SessionScoped
public class CatalogManagementBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction ut;
    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    
    @ManagedProperty("#{editProductBean}")
    private EditProductBean editProductBean;
    
    private Product editedProduct;
    private Integer editedProductUPC;
    
    /**
     * Creates a new instance of CatalogManagementBean
     */
    public CatalogManagementBean() {}

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public EditProductBean getEditProductBean() {
        return editProductBean;
    }

    public void setEditProductBean(EditProductBean editProductBean) {
        this.editProductBean = editProductBean;
    }

    public Product getEditedProduct() {
        return editedProduct;
    }

    public void setEditedProduct(Product editedProduct) {
        this.editedProduct = editedProduct;
    }

    public Integer getEditedProductUPC() {
        return editedProductUPC;
    }

    public void setEditedProductUPC(Integer editedProductUPC) {
        this.editedProductUPC = editedProductUPC;
    }        
    
    /**
     * Fetches a list of all products.
     * @return List<Product> of all products
     */
    public List<Product> getProductList() {  
        Query fetchProducts = entityManager.createNamedQuery("ProductEntity.listAllProducts");
        List<Product> productList = null;
        try {
            productList = (List<Product>) fetchProducts.getResultList();
        } catch (NoResultException ex) {
            InPageMessage.addInfoMessage("No products exist");
        }
        return productList;
    }
    
    /**
     * Creates a new Product and preps the editProductBean.
     * @return redirect to edit page or null for error.
     */
    public String addNewProduct() {
        String destinationPage = null;
  //      try {
            // Try to create a new ProductEntity
            Product newProduct = new ProductEntity();
            
            // Set the EditProductBean to edit this new product
            editProductBean.setProduct(newProduct);
            
            // Next stop: editProduct!
            destinationPage = "editProduct";
/*        } catch (InsufficentPermissionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("You're not allowed to do this.");
        } catch (NoCurrentSessionException ex) {
            Logger.getLogger(UserManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("No one is logged in?!?");
            destinationPage = "index";    
        }*/        
        return destinationPage;
    }
    
    /**
     * Selects a Product for editing and preps the editProductBean
     * @return redirect to edit page or null for error.
     */
    public String editProduct() {
        String destinationPage = null;
                
        // Look up product to edit by UPC       
        editedProduct = entityManager.find(ProductEntity.class, this.editedProductUPC);
        if (editedProduct == null) { // If we don't find one,  show an error message.
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");                        
        } else { // If we do find one,
            // set that as the product to be edited by editProductBean
            editProductBean.setProduct(editedProduct);                        
            // next stop: edit product page.
            destinationPage = "editProduct";        
        }   
        // reset the selected product UPC
        this.editedProductUPC = null;       
        return destinationPage;
    }
    
    
    /**
     * Deletes a Product from the database -- ONLY if they exist.
     * @return redirect to refresh the same page.
     */
    public String deleteProduct() {
        try {
            // Prep a transaction
            ut.begin();
            
            // Look up the product to delete in the database
            ProductEntity productToDelete = entityManager.find(
                    ProductEntity.class, this.editedProductUPC);
            
            // If the product already doesn't exist,
            if (productToDelete == null) {
                // notify the user of an error, but don't try to delete.
                InPageMessage.addErrorMessage("This product doesn't exist. Please refresh and try again.");
            } else { // but if it does exist,
                // attempt to delete the user
                entityManager.remove(productToDelete);   
                ut.commit();
            }                        
        } catch (NotSupportedException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (RollbackException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (SecurityException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (IllegalStateException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        } catch (SystemException ex) {
            Logger.getLogger(CatalogManagementBean.class.getName()).log(Level.SEVERE, null, ex);
            InPageMessage.addErrorMessage("Something went wrong. Please refresh and try again.");
        }        
        // Refresh catalog page
        return "catalog";
    }
}
