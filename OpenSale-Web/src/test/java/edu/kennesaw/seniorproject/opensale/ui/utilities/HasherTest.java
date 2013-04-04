
package edu.kennesaw.seniorproject.opensale.ui.utilities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spencer
 */
public class HasherTest {
    
    public HasherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of hashPassword method, of class Hasher.
     */
    @Test
    public void testHashPassword() {
        System.out.println("hashPassword");
        String password = "test";
        String expResult = "/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=";
        String result = Hasher.hashPassword(password);
        assertEquals(expResult, result);
    }
}