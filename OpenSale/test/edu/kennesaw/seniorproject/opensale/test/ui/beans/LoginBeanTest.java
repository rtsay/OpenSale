/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.test.ui.beans;

import edu.common.UserObjects.EUserTypes;
import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import edu.kennesaw.seniorproject.opensale.ui.beans.LoginBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test LoginBean functions
 * @author spencer
 */
public class LoginBeanTest {    
    
    private EntityManagerFactory testEMF;
    private EntityManager em;
    
    public LoginBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testEMF = Persistence.createEntityManagerFactory("OpenSalePU");
        em = testEMF.createEntityManager();
        em.getTransaction().begin();
        User testUser = new UserEntity();
        testUser.setUserName("test");        
        testUser.setPassword("test");
        testUser.setUserType(EUserTypes.Cashier);
        em.persist(testUser);
        em.getTransaction().commit();
    }
    
    @After
    public void tearDown() {
    }   
    /**
     * Test of login method, of class LoginBean.
     */
    @Test
    public void testLogin() {
        // We expect a successful login
        String expectedResultPage = "mainMenu";
        
        // Try to log in
        LoginBean instance = new LoginBean();
        instance.setEntityManager(em);
        instance.setUsername("test");
        instance.setPassword("test");
        String resultPage = instance.login();
        
        assertEquals(expectedResultPage, resultPage);        
    }
}