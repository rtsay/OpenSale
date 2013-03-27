/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

import edu.common.Exceptions.InsufficentPermissionException;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author mcbeckler
 */
@MappedSuperclass
public abstract class User implements Serializable {

    @Column(nullable = false)
    protected String userName;
    @Column(nullable = false)
    protected String password;
    @Column(nullable = false)
    protected Permissions permissions;
    @Column(nullable = false)
    protected EUserTypes userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EUserTypes getUserType() {
        return this.userType;
    }

    public void setUserType(EUserTypes userType) {
        this.userType = userType;
    }

    public void setPermissions(Permissions pObject) {
        this.permissions = pObject;
    }

    public Permissions getPermissions() {
        return this.permissions;
    }

    public void addPermission(Class toAdd, User manager) throws InsufficentPermissionException {
        this.permissions.addPermission(toAdd, manager, this);
    }

    public void removePermission(Class toRemove, User manager) throws InsufficentPermissionException {
        this.permissions.removePermission(toRemove, manager, this);
    }

    public boolean isAllowed(Class toCheck) {
        return this.permissions.isAllowed(toCheck);
    }
}
