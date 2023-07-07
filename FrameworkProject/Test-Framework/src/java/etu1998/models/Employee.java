/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.models;

import etu1998.AllAnnotations.Auth;
import etu1998.AllAnnotations.Method;
import etu1998.AllAnnotations.RestApi;
import etu1998.AllAnnotations.Scope;
import etu1998.AllAnnotations.Session;
import etu1998.framework.ModelView;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
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
    String[] langues = new String[3];
    HashMap<String, Object> sessions = new HashMap<>();

    public Employee() {
    }

    public HashMap<String, Object> getSessions() {
        return sessions;
    }

    public void setSessions(HashMap<String, Object> sessions) {
        this.sessions = sessions;
    }

    public String[] getLangues() {
        return langues;
    }

    public void setlangues(String[] langues) {
        this.langues = langues;
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

    @Session
    @Method(name_method = "testSession")
    public void testSession(HashMap map) {
        this.setSessions(map);
        for (Map.Entry<String, Object> entry : sessions.entrySet()) {
            System.out.println("lsdihf");
            Object key = entry.getKey();
            Object val = entry.getValue();

            System.out.println((String) key);

        }
    }

    @Method(name_method = "deleteAllSession")
    public ModelView deleteAllSession() {
        ModelView mv = new ModelView();
        mv.setInvalidateSession(true);
        return mv;
    }

    @Method(name_method = "deleteSpecificSession")
    public ModelView deleteSpecificSession() {
        ModelView mv = new ModelView();
        mv.getListSession().add("Usersession");
        return mv;
    }

    @Method(name_method = "connect")
    public void connect() {
        ModelView mv = new ModelView();
        mv.addSession("isConnected", true);
    }

    @Method(name_method = "login")
    public ModelView login() {
        ModelView mv = new ModelView();
        mv.setViewName("Login.jsp");
        return mv;
    }

    @Auth
    @Method(name_method = "deleteEmp")
    public ModelView deleteEmployee() {
        System.out.println(" delete employer ");
        ModelView mv = new ModelView();
        mv.setViewName("delete.jsp");
        return mv;
    }

    @Method(name_method = "getEmpForm")
    public ModelView getEmpForm() {
        ModelView mv = new ModelView();
        mv.setViewName("Form.jsp");
        return mv;
    }

    @Method(name_method = "getListeEmp")
    public ModelView listeEmploye() {
        Vector<String> liste_emp = new Vector<>();
        liste_emp.add("Rakoto");
        liste_emp.add("Rabe");
        liste_emp.add("Rasoa");
        ModelView mv = new ModelView();
        mv.setViewName("Liste.jsp");
        mv.setIsJson(true);
        mv.addItems("Liste_emp", liste_emp);
        return mv;
    }

    public String getNom() {
        return this.nom;
    }

    @RestApi
    @Method(name_method = "restApi")
    public Object restApi() {
        return this;
    }

    @Method(name_method = "emp-save")
    public void save() {
        System.out.println("l'id : " + this.getId());
        System.out.println("la date de naissance : " + this.getDateDeNaissance());
        System.out.println("le nom : " + this.getNom());
        System.out.println("le prenom : " + this.getPrenom());
        for (String langue : this.getLangues()) {
            System.out.println("vos langues : " + langue);
        }
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

    @Method(name_method = "emp-all")
    public ModelView emp_all(String nom) {
        System.out.println("le nom de l'employee = " + nom);
        ModelView mv = new ModelView();
        mv.setViewName("Succes.jsp");
        return mv;
    }
}
