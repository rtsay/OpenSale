/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.bootloaderObjects;

import edu.common.Permissions.VerifyPermissions;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author mcbeckler
 */
public class AnnotationFinder {

    public Set<Class<?>> getAllAvailablePermissions() {
        Reflections reflector = new Reflections("edu");
        return reflector.getTypesAnnotatedWith(VerifyPermissions.class);
    }
}
