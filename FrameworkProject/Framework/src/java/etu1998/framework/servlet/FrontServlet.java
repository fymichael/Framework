/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.framework.servlet;

import etu1998.AllAnnotations.Method;
import etu1998.framework.Annotation;
import etu1998.framework.Mapping;
import etu1998.framework.ModelView;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author P15B-79-FY
 */
public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> MappingUrls = new HashMap<>();
    String[] url = null;

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> MappingUrls) {
        this.MappingUrls = MappingUrls;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        setUrl(request.getRequestURI().split("/"));
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FrontServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FrontServlet at " + request.getRequestURI() + "</h1>");
            for (HashMap.Entry<String, Mapping> en : this.MappingUrls.entrySet()) {
                out.println(" Le nom de la classe : " + en.getValue().getClassName());
                out.println(" La methode : " + en.getValue().getMethod());
            }
            afficherHashMap();
            if (getUrl()[2].equalsIgnoreCase("") == false) {
                Object o = getClassFromAnnotation(getUrl()[2]);
                //System.out.println("Objec : "+o.getClass().getSimpleName());
                if (o instanceof ModelView) {
                    //System.out.println("listelisisisnshshsh");
                    ModelView mv = (ModelView) o;
                    if (mv.getData() != null) {
                        for (Map.Entry<String, Object> entry : mv.getData().entrySet()) {
                            String key = entry.getKey();
                            Object val = entry.getValue();

                            System.out.println("Key :" + key);
                            System.out.println("Value :" + val);

                            request.setAttribute(key, val);
                            request.getAttribute(key);

                        }
                    } else {
                        RequestDispatcher dispatch = request.getRequestDispatcher(mv.getViewName());
                        dispatch.forward(request, response);
                    }
                } else if (getUrl()[2].equalsIgnoreCase("emp-save") == true) {
                    Map<String, String[]> map = request.getParameterMap();
                    Annotation anno = new Annotation();
                    Vector<Class> vec = anno.getClassFrom("etu1998.models");
                    for (int i = 0; i < vec.size(); i++) {
                        Object oj = vec.get(i).newInstance();
                        Field[] field = oj.getClass().getDeclaredFields();
                        for (Map.Entry<String, String[]> entry : map.entrySet()) {
                            String name = entry.getKey();
                            String[] value = entry.getValue();
                            String setMeth = null;
                            for (int z = 0; z < field.length; z++) {
                                if (field[z].getName().equalsIgnoreCase(name) == true) {
                                    for (int a = 0; a < value.length; a++) {
                                        if (field[z].getType().getSimpleName().equalsIgnoreCase("String")) {
                                            setMeth = "set" + name;
                                            //System.out.println(setMeth);
                                            vec.get(i).getDeclaredMethod(setMeth, String.class).invoke(oj, value[i]);
                                        }
                                        else if (field[z].getType().getSimpleName().equalsIgnoreCase("Date")) {
                                            setMeth = "set" + name;
                                            //System.out.println(setMeth);
                                            vec.get(i).getDeclaredMethod(setMeth, Date.class).invoke(oj, Date.valueOf(value[i]));
                                        }
                                        else if (field[z].getType().getSimpleName().equalsIgnoreCase("int")) {
                                            setMeth = "set" + name;
                                            //System.out.println(setMeth);
                                            vec.get(i).getDeclaredMethod(setMeth, int.class).invoke(oj, Integer.valueOf(value[i]));
                                        }
                                        else if (field[z].getType().getSimpleName().equalsIgnoreCase("double")) {
                                            setMeth = "set" + name;
                                            //System.out.println(setMeth);
                                            vec.get(i).getDeclaredMethod(setMeth, double.class).invoke(oj, Double.valueOf(value[i]));
                                        }
                                        else{
                                            System.out.println(field[z].getType().getSimpleName()+" Type de variable inconnu");
                                        }
                                    }
                                }
                            }
                        }
                        vec.get(i).getDeclaredMethod("save", new Class[0]).invoke(oj, new Object[0]);
                    }
                }
            }
            out.println("</body>");
            out.println("</html>");
        }

    }

    @Override
    public void init() {
        try {
            //System.out.println("etu1998.framework.servlet.FrontServlet.init()");
            Annotation a = new Annotation();

            Vector<Class> vec = a.getClassFrom("etu1998.models");
            for (int i = 0; i < vec.size(); i++) {
                if (vec.get(i) != null) {
                    insertHashMap(vec.get(i));
                }
            }
        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//       

    public Vector<String> getListeAttribute(Class<?> className) {
        Vector<String> liste = new Vector<>();
        for (int i = 0; i < className.getDeclaredFields().length; i++) {
            //           System.out.println(className.getDeclaredFields()[i]);
            System.out.println(className.getSimpleName());
            liste.add(className.getDeclaredFields()[i].getName());
        }
        return liste;
    }

    public void insertHashMap(Class<?> className) {
        for (int i = 0; i < className.getDeclaredMethods().length; i++) {
            //System.out.println(className.getDeclaredMethods()[i].getName());
            if (className.getDeclaredMethods()[i].getAnnotation(Method.class) != null) {
                System.out.println(className.getDeclaredMethods()[i].getAnnotation(Method.class).name_method());
                String url = className.getDeclaredMethods()[i].getAnnotation(Method.class).name_method();
                this.MappingUrls.put(url, new Mapping(className.getSimpleName(), className.getDeclaredMethods()[i].getName()));
            }
        }
    }

    public Object getClassFromAnnotation(String annotation) throws Exception {
        Annotation a = new Annotation();
        Object o = null;
        Vector<Class> vec = a.getClassFrom("etu1998.models");
        for (int e = 0; e < vec.size(); e++) {
            for (int i = 0; i < vec.get(e).getDeclaredMethods().length; i++) {
                if (vec.get(e).getDeclaredMethods()[i].getAnnotation(Method.class) != null) {
                    //System.out.println("etu1998.framework.servlet.FrontServlet.check1()"+vec.get(e).getDeclaredMethods()[i].getName());
                    if (vec.get(e).getDeclaredMethods()[i].getAnnotation(Method.class).name_method().equalsIgnoreCase(annotation) == true) {
                        //System.out.println("etu1998.framework.servlet.FrontServlet.check3()"+vec.get(e).getDeclaredMethods()[i].getName());
                        o = vec.get(e).getDeclaredMethods()[i].invoke(vec.get(e).newInstance(), new Object[0]);
                        System.out.println("Object : " + o);
                    }
                }
            }
        }
        return o;
    }
//       

    public void afficherHashMap() {
        for (HashMap.Entry<String, Mapping> en : this.MappingUrls.entrySet()) {
            System.out.println(" Le nom de la classe : " + en.getValue().getClassName());
            System.out.println(" La methode : " + en.getValue().getMethod());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
