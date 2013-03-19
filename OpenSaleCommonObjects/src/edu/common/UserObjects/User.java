/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

import java.io.Serializable;

/**
 *
 * @author mcbeckler
 */
public abstract class User implements Serializable {
    protected String userName, password;
    protected EUserTypes userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EUserTypes getUserType() {
        return userType;
    }

    public void setUserType(EUserTypes userType) {
        this.userType = userType;
    }
}
