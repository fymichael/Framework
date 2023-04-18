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
public class Main {
    public static void main(String[] args) throws Exception{
        try{
            Annotation a = new Annotation();
            //a.getAllMethod(Employee.class);
            a.getClassFrom("etu1998.models");
        }
        catch(Exception e){
            throw e;
        }
    }
    
}
