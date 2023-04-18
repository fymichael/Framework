/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.framework;

/**
 *
 * @author P15B-79-FY
 */
public class Mapping {
    String ClassName;
    String Method;

    public Mapping(String ClassName, String Method) {
        this.ClassName = ClassName;
        this.Method = Method;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String Method) {
        this.Method = Method;
    }
    
    
}
