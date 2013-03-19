/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.Interfaces;

import edu.common.UserObjects.User;

/**
 *
 * @author mcbeckler
 */
public interface IUserVisitor {
    void visit(User user);

}
