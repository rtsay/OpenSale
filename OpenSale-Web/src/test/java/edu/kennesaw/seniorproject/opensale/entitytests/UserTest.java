/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.entitytests;

import edu.common.Exceptions.InsufficentPermissionException;
import edu.common.Static.Session;
import edu.common.UserObjects.EUserTypes;
import edu.common.Permissions.Permissions;
import edu.common.UserObjects.User;
import edu.kennesaw.seniorproject.opensale.entities.UserEntity;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mcbeckler
 */
public class UserTest {

    private User testCashier, testManager, testAdmin, testSuperUser;

    public UserTest() {
        //make a temp default user to login and create our entities
        User defaultuser = new UserEntity();
        defaultuser.setPermissions(new Permissions()); //required
        defaultuser.setUserType(EUserTypes.SuperUser); //required
        Session session = new Session();
        session.Login(defaultuser);

        try {
            testCashier = new UserEntity("cashier", "cashier", EUserTypes.Cashier, new Permissions(), defaultuser);
            testManager = new UserEntity("manager", "manager", EUserTypes.Manager, new Permissions(), defaultuser);
            //testAdmin = new UserEntity("admin", "admin", EUserTypes.Adminstrator, new Permissions());
            //testSuperUser = new UserEntity("superuser", "superuser", EUserTypes.SuperUser, new Permissions());
            session.Login(testCashier);
        } catch (InsufficentPermissionException ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test(expected = InsufficentPermissionException.class)
    public void CashierAddPermissionTestsToFail() throws InsufficentPermissionException {
        testCashier.addPermission(Permissions.class, testCashier);
    }

    @Test(expected = InsufficentPermissionException.class)
    public void CashierRemovePermissionTestsToFail() throws InsufficentPermissionException {
        testCashier.removePermission(Permissions.class, testCashier);
    }

    @Test
    public void CashierAddPermissionTestsToPass() throws InsufficentPermissionException {
        testCashier.addPermission(Permissions.class, testManager);
        Assert.assertTrue(testCashier.isAllowed(Permissions.class));
    }

    @Test
    public void CashierRemovePermissionTestsToPass() throws InsufficentPermissionException {
        testCashier.removePermission(Permissions.class, testManager);
        Assert.assertFalse(testCashier.isAllowed(Permissions.class));
    }
}