/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.ui.beans;


import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author spencer
 */
@ManagedBean
@ApplicationScoped
public class SeedDataBean {

    @PersistenceContext(unitName="OpenSalePU")
    private EntityManager entityManager;
    
    /**
     * Creates a new instance of SeedDataBean
     */
    public SeedDataBean() {
    }
    
    @PostConstruct
    private void createSeedData() {
        entityManager.getTransaction().begin();
        UserEntity u = new UserEntity();
        u.setUserName("test");
        u.setPassword("test");
        entityManager.persist(u);
        entityManager.getTransaction().commit();
    }
}
