/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kennesaw.seniorproject.opensale.ui.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * A utility class to wrap FacesMessage and other such overly-complex,
 * frequently-used parts of JSF.
 * @author spencer
 */
public class InPageMessage {
    
    public static void addErrorMessage(String message) {
        addMessage(message, FacesMessage.SEVERITY_ERROR);        
    }
    
    public static void addInfoMessage(String message) {
        addMessage(message, FacesMessage.SEVERITY_INFO);        
    }
    
    public static void addWarningMessage(String message) {
        addMessage(message, FacesMessage.SEVERITY_WARN);        
    }
    
    public static void addFatalErrorMessage(String message) {
        addMessage(message, FacesMessage.SEVERITY_FATAL);        
    }
    
    /**
     * The method that actually adds in-page messages, first checking to make 
     * sure we don't add the same message twice.
     * @param message
     * @param severity 
     */
    private static void addMessage(String message, Severity severity) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(severity);
        FacesContext currentFacesContext = FacesContext.getCurrentInstance();
        if(currentFacesContext.getMessageList(message).isEmpty()) {
            currentFacesContext.addMessage(message, fm);
        }
    }
}
