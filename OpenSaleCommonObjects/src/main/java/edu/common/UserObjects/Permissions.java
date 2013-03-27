/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

import edu.common.Exceptions.InsufficentPermissionException;
import edu.common.Interfaces.IUserVisitor;
import edu.common.Static.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mcbeckler
 */
public class Permissions implements IUserVisitor, Serializable {

    private List<Class> permissions;

    public Permissions() {
        this.permissions = new ArrayList<Class>();
    }

    public void addPermission(Class permission, User manager, User userToModify) throws InsufficentPermissionException {
        if (manager.getUserType().getValue() > userToModify.getUserType().getValue()) {
            permissions.add(permission);
        } else {
            throw new InsufficentPermissionException();
        }
    }

    public void removePermission(Class permission, User manager, User userToModify) throws InsufficentPermissionException {
        if (manager.getUserType().getValue() > userToModify.getUserType().getValue()) {
            permissions.remove(permission);
        } else {
            throw new InsufficentPermissionException();
        }
    }

    public boolean isAllowed(Class currentProcess) {
        if (Session.getCurrentUser().getUserType() == EUserTypes.SuperUser) {
            return true;
        }
        return permissions.contains(currentProcess);
    }

    @Override
    public void visit(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
