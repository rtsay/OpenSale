/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.common.UserObjects;

/**
 *
 * @author mcbeckler
 */
public enum EUserTypes {
    Cashier(0),
    Manager(1),
    Adminstrator(2),
    SuperUser(3);
    private final int value;
    private EUserTypes(int value){
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}
