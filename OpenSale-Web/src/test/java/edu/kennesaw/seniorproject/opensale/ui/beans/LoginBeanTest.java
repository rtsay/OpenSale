package edu.kennesaw.seniorproject.opensale.ui.beans;

import edu.common.UserObjects.EUserTypes;
import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for LoginBean.
 * @author spencer
 */
public class LoginBeanTest {
    
    private static LoginBean loginBean;
    
    public LoginBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
/*
        System.out.println("Testing loginBean");
        
        // Get the embedded glassfish server's context
        InitialContext ic = new InitialContext();      
        NamingEnumeration<NameClassPair> ncp = ic.list("");
        while(ncp.hasMore()) {
            System.out.println(ncp.next());
        }
        // Get a LoginBean from that context
//        loginBean = (LoginBean)ic.lookup("java:global/classes/LoginBean");        
        // get EntityManager from that LoginBean
        EntityManager em = loginBean.getEntityManager();
        
        //create a test user
        em.getTransaction().begin();
        User u = new UserEntity();
        u.setUserName("test");
        u.setPassword("test");
        u.setUserType(EUserTypes.Cashier);
        em.persist(u);
        em.getTransaction().commit();     
        */
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isLoggedIn method, of class LoginBean.
     *
    @Test
    public void testIsLoggedIn() {
        System.out.println("isLoggedIn");
        LoginBean instance = new LoginBean();
        boolean expResult = false;
        boolean result = instance.isLoggedIn();
        assertEquals(expResult, result);      
    }

    /**
     * Test of isManager method, of class LoginBean.
     *
    @Test
    public void testIsManager() {
        System.out.println("isManager");
        LoginBean instance = new LoginBean();
        boolean expResult = false;
        boolean result = instance.isManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAdmin method, of class LoginBean.
     *
    @Test
    public void testIsAdmin() {
        System.out.println("isAdmin");
        LoginBean instance = new LoginBean();
        boolean expResult = false;
        boolean result = instance.isAdmin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isSuperUser method, of class LoginBean.
     *
    @Test
    public void testIsSuperUser() {
        System.out.println("isSuperUser");
        LoginBean instance = new LoginBean();
        boolean expResult = false;
        boolean result = instance.isSuperUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class LoginBean.
     *
    @Test
    public void testLogin() {
        System.out.println("login"); 
        // we expect a successful login.
        String expResult = "mainMenu";
        
        // Set username and password, then login.
        loginBean.setUsername("test");
        loginBean.setPassword("test");
        String result = loginBean.login();
        assertEquals(expResult, result);
    }
    */
}
