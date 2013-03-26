/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author mcbeckler
 */
@MappedSuperclass
public abstract class User implements Serializable {
    protected String userName, password;
            
    @Enumerated(EnumType.ORDINAL)
    protected EUserTypes userType;

    public abstract String getUserName();

    public abstract void setUserName(String userName);

    public abstract String getPassword();

    public abstract void setPassword(String password);

    public abstract EUserTypes getUserType();

    public abstract void setUserType(EUserTypes userType);
}
