/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.models;

import etu1998.AllAnnotations.Method;
import etu1998.framework.ModelView;
import java.sql.Date;
import java.util.Vector;

/**
 *
 * @author P15B-79-FY
 */
public class Employee {
    
    int id;
    String nom;
    String prenom;
    Date dateDeNaissance;

    public Employee() {
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setdateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getId() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getNoms() {
        return this.nom;
    }

    //@Method(name_method = "getListeEmp")
    public ModelView listeEmploye() {
        Vector<String> liste_emp = new Vector<>();
        liste_emp.add("Rakoto");
        liste_emp.add("Rabe");
        liste_emp.add("Rasoa");
        ModelView mv = new ModelView();
        mv.setViewName("Liste.jsp");
        mv.addItems("Liste_emp", liste_emp);
        return mv;
    }

    public String getNom() {
        return this.nom;
    }

    @Method(name_method = "getEmpForm")
    public ModelView getForm() {
        ModelView mv = new ModelView();
        mv.setViewName("Form.jsp");
        return mv;
    }

    @Method(name_method = "emp-save")
    public void save() {
        System.out.println("l'id : " + this.getId());
        System.out.println("la date de naissance : " + this.getDateDeNaissance());
        System.out.println("le nom : " + this.getNom());
        System.out.println("le prenom : " + this.getPrenom());
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setprenom(String prenom) {
        this.prenom = prenom;
    }

//    @Method(name_method = "emp-add")
    public void emp_add() {
        System.out.println("emp-add");
    }

    // @Method(name_method = "emp-all")
    public void emp_all() {
        System.out.println("emp-all");
    }
}
