/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1998.wera;

import etu1998.framework.Annotation;
import etu1998.models.Employee;
import java.util.Vector;
/**
 *
 * @author Fy Botas
 */
public class Main {
    public static void main(String[] args) {
        Annotation a = new Annotation();
        
        Vector<Class> listeObject = new Vector<>();
        listeObject.add(Employee.class);
         
        System.out.println(a.getAllAnnotedSingletonClass(listeObject));
    }
}
