/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1998.framework;

import etu1998.AllAnnotations.Fielder;
import etu1998.AllAnnotations.Modele;
import etu1998.AllAnnotations.Scope;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Vector;

/**
 *
 * @author P15B-79-FY
 */
public class Annotation {

    public Annotation() {
    }

    public boolean getAllAnnotedSingletonClass(Vector<Class> listeObject) {
        for (int i = 0; i < listeObject.size(); i++) {
            if(listeObject.get(i).getAnnotation(Scope.class) != null){
                return true;
            } 
        }
        return false;
    }

    public void getAllAnnotationField(Class<?> listeObject) {
        Field[] fields = listeObject.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getAnnotation(Fielder.class) != null) {
                System.out.println(fields[i].getAnnotation(Fielder.class).name());
            }
        }
    }
    
    
        public static Vector<Class> getClassFrom(String packages) throws Exception {
            String path = packages.replaceAll("[.]", "/");
            URL packagesUrl = Thread.currentThread().getContextClassLoader().getResource(path);
            File packDir = new File(packagesUrl.toURI());
            File[] inside = packDir.listFiles();
            Vector<Class> lists = new Vector<>();
            for (File f : inside) {
                String c = packages + "." + f.getName().substring(0, f.getName().lastIndexOf("."));
                System.out.println(c);
                lists.add(Class.forName(c));
            }
            return lists;
    }
    
    
//    public void getAllMethod(Class<?> listeObject) {
//            if (listeObject.getClass().getAnnotation(Method.class)) {
//                System.out.println(fields[i].getAnnotation(Fielder.class).name());
//            }
//        }
//    }
}
