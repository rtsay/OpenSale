/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.objects;

import edu.kennesaw.seniorproject.opensale.interfaces.IUserVisitor;
import edu.kennesaw.seniorproject.opensale.model.Product;
import edu.kennesaw.seniorproject.opensale.model.User;
import edu.kennesaw.seniorproject.transaction.Transaction;

/**
 *
 * @author mcbeckler
 */
public class Permissions implements IUserVisitor {

    @Override
    public void visit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Transaction transaction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
    }

    @Override
    public void visit(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
