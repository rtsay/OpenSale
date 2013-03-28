/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

import edu.common.Exceptions.InsufficentPermissionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mcbeckler
 */
public class Permissions implements Serializable {

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

    public boolean isAllowed(Class currentProcess, User currentUser) {
        if (currentUser.getUserType() == EUserTypes.SuperUser) {
            return true;
        }
        return permissions.contains(currentProcess);
    }
}
