/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.entitytests;

import edu.kennesaw.seniorproject.opensale.bootloaderObjects.AnnotationFinder;
import edu.kennesaw.seniorproject.opensale.ui.beans.UserManagementBean;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcbeckler
 */
public class ReflectionTest {
    
    private AnnotationFinder a;
    
    public ReflectionTest() {
        a = new AnnotationFinder();
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test() {
        Set<Class<?>> stuff = a.getAllAvailablePermissions();
    }
}