/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.framework.servlet;

import etu1998.AllAnnotations.Auth;
import etu1998.AllAnnotations.Method;
import etu1998.AllAnnotations.Session;
import etu1998.framework.Annotation;
import etu1998.framework.FileUpload;
import etu1998.framework.Mapping;
import etu1998.framework.ModelView;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author P15B-79-FY
 */
@MultipartConfig
public class FrontServlet extends HttpServlet {

    HashMap<String, Object> session = new HashMap<>();
    HashMap<String, Object> SingletonClass = new HashMap<>();

    public HashMap<String, Object> getSingletonClass() {
        return SingletonClass;
    }

    public void setSingletonClass(HashMap<String, Object> SingletonClass) {
        this.SingletonClass = SingletonClass;
    }

    HashMap<String, Mapping> MappingUrls = new HashMap<>();
    String[] url = null;

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

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
            for (HashMap.Entry<String, Object> en : this.SingletonClass.entrySet()) {
                out.println(" Le nom de la classe : " + en.getValue().getClass().getSimpleName());
            }

            if (getUrl().length >= 2 && getUrl()[2].equalsIgnoreCase("") == false) {
                Class c = getClassFromUrl(getUrl()[2]);

                java.lang.reflect.Method m = getMethodFromUrl(getUrl()[2]);

                if (m.isAnnotationPresent(Session.class)) {
                    Enumeration<String> attributeNames = request.getSession().getAttributeNames();
                    while (attributeNames.hasMoreElements()) {
                        System.out.println(" ic ");
                        String sessionValues = getInitParameter("session");
                        String session = attributeNames.nextElement();
                        this.session.put(sessionValues, session);
                        m.invoke(c, session);
                    }
                }

                if (m.isAnnotationPresent(Auth.class)) {
                    System.out.println(" Authentification requis ");
                    if (request.getSession().getAttribute("isConnected") != null) {
                        Object o = m.invoke(c.newInstance(), new Object[0]);
                    } else {
                        response.sendRedirect("ErrorAuth.jsp");
                    }
                } else {

                    Object o = m.invoke(c.newInstance(), new Object[0]);

                    if (this.getSingletonClass().containsValue(o)) {
                        reset(c, o);

                    }

                    //System.out.println("Objec : "+o.getClass().getSimpleName());
                    if (o instanceof ModelView) {
                        ModelView mv = (ModelView) o;
                        if (mv.getData() != null) {
                            for (Map.Entry<String, Object> entry : mv.getData().entrySet()) {
                                String key = entry.getKey();
                                Object val = entry.getValue();

                                request.setAttribute(key, val);
                                request.getAttribute(key);

                                RequestDispatcher dispatch = request.getRequestDispatcher(mv.getViewName());
                                dispatch.forward(request, response);
                            }
                        } else {
                            RequestDispatcher dispatch = request.getRequestDispatcher(mv.getViewName());
                            dispatch.forward(request, response);
                        }
                    } else if (getUrl()[2].equalsIgnoreCase("emp-save") == true) {
                        getRequestValues(request, response, m, c);
                    } else if (getUrl()[2].equalsIgnoreCase("emp-all") == true) {
                        String value = request.getQueryString();
                        String[] val = value.split("=");
                        getClassFromAnnotationUrl(getUrl()[2], val[1]);
                    } else if (getUrl()[2].equalsIgnoreCase("connect")) {
                        HttpSession session = request.getSession();
                        String sessionValues = getInitParameter("Usersession");
                        session.setAttribute(sessionValues, true);
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
            Annotation a = new Annotation();

            Vector<Class> vec = a.getClassFrom("etu1998.models");
            for (int i = 0; i < vec.size(); i++) {
                if (vec.get(i) != null) {
                    if (a.getAllAnnotedSingletonClass(vec)) {
                        insertHashMap(vec.get(i));
                        this.getSingletonClass().put(vec.get(i).getSimpleName(), (Object) vec.get(i));
                    }
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

    public void reset(Class c, Object o) throws Exception {
        Field[] listeAttribut = c.getDeclaredFields();
        for (Field f : listeAttribut) {
            if (f.getType().getSimpleName().equalsIgnoreCase("String")) {
                //System.out.println("Field : "+f.getName());
                String setMeth = "set" + f.getName();
                c.getDeclaredMethod(setMeth, String.class).invoke(o, "");
            }
            if (f.getType().getSimpleName().equalsIgnoreCase("int")) {
                String setMeth = "set" + f.getName();
                c.getDeclaredMethod(setMeth, Integer.class).invoke(o, 0);
            }
            if (f.getType().getSimpleName().equalsIgnoreCase("Date")) {
                String setMeth = "set" + f.getName();
                c.getDeclaredMethod(setMeth, Date.class).invoke(o, null);
            }
            if (f.getType().getSimpleName().equalsIgnoreCase("double")) {
                String setMeth = "set" + f.getName();
                c.getDeclaredMethod(setMeth, Double.class).invoke(o, 0);
            }
        }
    }

    public void getRequestValues(HttpServletRequest request, HttpServletResponse response, java.lang.reflect.Method m, Class c) throws InstantiationException, IllegalAccessException, Exception {
        Map<String, String[]> map = request.getParameterMap();
        Annotation anno = new Annotation();
        Object oj = c.newInstance();
        Field[] field = oj.getClass().getDeclaredFields();

        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String[] value = entry.getValue();
            String name = entry.getKey();
            String setMeth = null;
            for (int z = 0; z < field.length; z++) {
                if (field[z].getName().equalsIgnoreCase(name) == true) {
                    for (int a = 0; a < value.length; a++) {
                        if (field[z].getType().getSimpleName().equalsIgnoreCase("String")) {
                            setMeth = "set" + name;
                            c.getDeclaredMethod(setMeth, String.class).invoke(oj, value[a]);
                        } else if (field[z].getType().getSimpleName().equalsIgnoreCase("Date")) {
                            setMeth = "set" + name;
                            c.getDeclaredMethod(setMeth, Date.class).invoke(oj, Date.valueOf(value[a]));
                        } else if (field[z].getType().getSimpleName().equalsIgnoreCase("int")) {
                            setMeth = "set" + name;
                            c.getDeclaredMethod(setMeth, int.class).invoke(oj, Integer.valueOf(value[a]));
                        } else if (field[z].getType().getSimpleName().equalsIgnoreCase("double")) {
                            setMeth = "set" + name;
                            c.getDeclaredMethod(setMeth, double.class).invoke(oj, Double.valueOf(value[a]));
                        } else {
                            throw new Exception(" Type de variable inconnu");
                        }
                    }
                } else if (name.endsWith("[]")) {
                    String nameTab = name.substring(0, name.indexOf('['));
                    setMeth = "set" + nameTab;
                    String[] values = request.getParameterValues(name);
                    c.getDeclaredMethod(setMeth, String[].class).invoke(oj, (Object) values);
                } else if (request.getPart("fichier") != null) {
                    this.uploadFile("fichier", 100000, request, response);
                }
            }
        }
        c.getDeclaredMethod(m.getName(), new Class[0]).invoke(oj, new Object[0]);
    }

    public java.lang.reflect.Method getMethodFromUrl(String url) throws Exception {
        Annotation a = new Annotation();
        Vector<Class> vec = a.getClassFrom("etu1998.models");
        Mapping mapping = getMappingUrls().get(url);
        if (mapping != null) {
            String className = mapping.getClassName();
            String methodName = mapping.getMethod();

            for (Class<?> c : vec) {
                if (c.getSimpleName().equals(className)) {
                    java.lang.reflect.Method[] methods = c.getDeclaredMethods();
                    for (java.lang.reflect.Method m : methods) {
                        if (m.getName().equals(methodName)) {
                            return m;
                        }
                    }
                }
            }
        }

        throw new Exception("Method not found for URL: " + url);
    }

    public Class<?> getClassFromUrl(String url) throws Exception {
        Annotation a = new Annotation();
        Vector<Class> vec = a.getClassFrom("etu1998.models");
        Mapping mapping = getMappingUrls().get(url);
        if (mapping != null) {
            String className = mapping.getClassName();

            for (Class<?> c : vec) {
                if (c.getSimpleName().equals(className)) {
                    return c;
                }
            }
        }

        throw new Exception("Class not found for URL: " + url);
    }

    public void uploadFile(String nameFile, int maxSize, HttpServletRequest request, HttpServletResponse response) {
        try {
            Part filePart = request.getPart(nameFile);

            // Vérifier la taille du fichier
            long fileSize = filePart.getSize();
            if (fileSize > maxSize) {
                // Gérer l'erreur de dépassement de la taille maximale
                response.getWriter().println("La taille du fichier dépasse la limite maximale autorisée.");
                return;
            }
            FileUpload fp = new FileUpload();
            byte[] fileBytes = fp.readBytesFromInputStream(filePart.getInputStream());

            fp.setBytes(fileBytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
            if (className.getDeclaredMethods()[i].getAnnotation(Method.class
            ) != null) {
                System.out.println(className.getDeclaredMethods()[i].getAnnotation(Method.class
                ).name_method());
                String url = className.getDeclaredMethods()[i].getAnnotation(Method.class).name_method();

                this.MappingUrls.put(url,
                        new Mapping(className.getSimpleName(), className.getDeclaredMethods()[i].getName()));
            }
        }
    }

    public Object getClassFromAnnotation(String annotation) throws Exception {
        Annotation a = new Annotation();
        Object o = null;
        Vector<Class> vec = a.getClassFrom("etu1998.models");
        for (int e = 0; e < vec.size(); e++) {
            for (int i = 0; i < vec.get(e).getDeclaredMethods().length; i++) {
                if (vec.get(e).getDeclaredMethods()[i].getAnnotation(Method.class
                ) != null) {
                    if (vec.get(e)
                            .getDeclaredMethods()[i].getAnnotation(Method.class
                            ).name_method().equalsIgnoreCase(annotation) == true) {
                        if (vec.get(e)
                                .getDeclaredMethods()[i].getParameters().length == 0) {
                            o = vec.get(e).getDeclaredMethods()[i].invoke(vec.get(e).newInstance(), new Object[0]);
                            System.out.println("Object : " + o);
                        }
                    }
                }
            }
        }
        return o;
    }

    public Object getClassFromAnnotationUrl(String annotation, String value) throws Exception {
        Annotation a = new Annotation();
        Object o = null;
        Vector<Class> vec = a.getClassFrom("etu1998.models");
        for (int e = 0; e < vec.size(); e++) {
            for (int i = 0; i < vec.get(e).getDeclaredMethods().length; i++) {
                if (vec.get(e).getDeclaredMethods()[i].getAnnotation(Method.class
                ) != null) {
                    if (vec.get(e)
                            .getDeclaredMethods()[i].getAnnotation(Method.class
                            ).name_method().equalsIgnoreCase(annotation) == true) {
                        if (vec.get(e)
                                .getDeclaredMethods()[i].getParameters().length == 0) {
                            o = vec.get(e).getDeclaredMethods()[i].invoke(vec.get(e).newInstance(), new Object[0]);
                            System.out.println("Object : " + o);
                        } else {
                            for (int r = 0; r < vec.get(e).getDeclaredMethods()[i].getParameters().length; r++) {
                                if (vec.get(e).getDeclaredMethods()[i].getParameters()[r].getType() == String.class) {
                                    System.out.println("String : " + vec.get(e).getDeclaredMethods()[i].getParameters()[r].getName());
                                    o = vec.get(e).getDeclaredMethod(vec.get(e).getDeclaredMethods()[i].getName(), String.class).invoke(vec.get(e).newInstance(), value);
                                } else if (vec.get(e).getDeclaredMethods()[i].getParameters()[r].getType() == Date.class) {
                                    System.out.println("Date : " + vec.get(e).getDeclaredMethods()[i].getParameters()[r].getName());
                                    o = vec.get(e).getDeclaredMethod(vec.get(e).getDeclaredMethods()[i].getName(), Date.class).invoke(vec.get(e).newInstance(), Date.valueOf(value));
                                } else if (vec.get(e).getDeclaredMethods()[i].getParameters()[r].getType() == int.class) {
                                    System.out.println("Int : " + vec.get(e).getDeclaredMethods()[i].getParameters()[r].getName());
                                    o = vec.get(e).getDeclaredMethod(vec.get(e).getDeclaredMethods()[i].getName(), int.class).invoke(vec.get(e).newInstance(), Integer.valueOf(value));
                                } else if (vec.get(e).getDeclaredMethods()[i].getParameters()[r].getType() == double.class) {
                                    System.out.println("Double : " + vec.get(e).getDeclaredMethods()[i].getParameters()[r].getName());
                                    o = vec.get(e).getDeclaredMethod(vec.get(e).getDeclaredMethods()[i].getName(), double.class).invoke(vec.get(e).newInstance(), Double.valueOf(value));
                                }
                            }
                        }
                    }
                }
            }
        }
        return o;
    }
//       

    public void afficherHashMapSingleton() {
        for (HashMap.Entry<String, Object> en : this.SingletonClass.entrySet()) {
            System.out.println(" Le nom de la classe singleton : " + en.getValue().getClass().getSimpleName());
        }
    }

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
            Logger.getLogger(FrontServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FrontServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
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
