/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.interfaces;

import edu.kennesaw.seniorproject.opensale.model.*;
import edu.kennesaw.seniorproject.transaction.Transaction;

/**
 *
 * @author mcbeckler
 */
public interface IUserVisitor {
    void visit(User user);
    void visit(Transaction transaction);
    void visit(Product product);
}
