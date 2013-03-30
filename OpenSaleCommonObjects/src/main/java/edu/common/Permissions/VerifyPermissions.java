/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.Permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author mcbeckler
 */
@Target(value ={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyPermissions {
}
