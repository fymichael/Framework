/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1998.models;
import etu1998.AllAnnotations.Scope;
/**
 *
 * @author P15B-79-FY
 */
public class Dept {
    String nom;
    int nombreEmployer;

    public void insertDept(){
        System.out.println(" inserer dept ");
    }
    
    public void getAllDept(){
        System.out.println("get all dept");
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombreEmployer() {
        return nombreEmployer;
    }

    public void setNombreEmployer(int nombreEmployer) {
        this.nombreEmployer = nombreEmployer;
    }

    public Dept(String nom, int nombreEmployer) {
        this.nom = nom;
        this.nombreEmployer = nombreEmployer;
    }

    public Dept() {
    }
    
}
