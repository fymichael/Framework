/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1966.framework;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author M.Andriamahery
 */
public class FrameMethodUtil {
     public static List<Class> getClassesInPackage(String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File directory = new File(classLoader.getResource(path).getFile());
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    String className = packageName + "." + file.substring(0, file.length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        } else {
            throw new ClassNotFoundException("Package " + packageName + " not found.");
        }
        return classes;
    }
    
    public static Method getSetMethod(Class c, String methodName){
        for(Method m : c.getDeclaredMethods()){
            if(m.getName().toLowerCase().equals(methodName)){
                return m;
            }
        }
        return null;
    }
    
    public static void setValeur(Field attribut,Class c,  Map<String, String[]> parameters, String key,Object object, String keyLetterOnly) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        String methodName = "set"+attribut.getName().toLowerCase();  //methode setter d'un attribut de classe
        Method setter = FrameMethodUtil.getSetMethod(c, methodName);
        String[] valeur = parameters.get(key); //       
        for(String value : valeur){
            System.out.println(">>>"+ value);
        }
        setter.invoke(object, attribut.getType().isArray()? valeur : valeur[0]); //verifier si la valeur donne est un [] 
    }

    //former les arguments dont une fonction a besoin
    public static String[] formMethodArgument(Parameter[] mParameters, Map<String, String[]> requestParameters) {
        List<String> arguments = new ArrayList<>();
        for(Parameter mParameter : mParameters ){
            for(String requestParameter : requestParameters.keySet()){
                if(mParameter.getName().equals(requestParameter)){
                    arguments.add(requestParameters.get(requestParameter)[0]);
                }
            }
        }
        String[] trueArguments = new String[arguments.size()];
        int indexTrueArguments = 0;
        for(String argument : arguments){
            trueArguments[indexTrueArguments] =argument;
            indexTrueArguments ++;
        }
        return trueArguments;
    }



    public static String formParamName(String key) {
        // Définition de l'expression régulière pour trouver les lettres et les chiffres (a-zA-Z0-9)
        String regex = "[a-zA-Z0-9]+";

        // Création du Pattern à partir de l'expression régulière
        Pattern pattern = Pattern.compile(regex);

        // Création du Matcher à partir de la clé fournie
        Matcher matcher = pattern.matcher(key);

        // Variable pour stocker le résultat
        StringBuilder result = new StringBuilder();

        // Recherche des correspondances dans la chaîne
        while (matcher.find()) {
            // Ajout de chaque correspondance (lettre ou chiffre) à la variable résultat
            result.append(matcher.group());
        }

        // Conversion du StringBuilder en chaîne de caractères et retour
        return result.toString();
    }

    public static void reinitialize(Class c,Object object) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field[] f = c.getDeclaredFields();
        Method[] allClassMethod = c.getDeclaredMethods();
        for(Field field : f){
            for(Method m : allClassMethod){
                String methodM = m.getName().toLowerCase();
                if(methodM.equals("set"+field.getName())){
                     if(field.getType() == int.class || field.getType() == double.class || field.getType() == float.class){
                        m.invoke(object,0);
                    }
                    else{
                        System.out.println(m.getName());
                        m.invoke(object, (Object)null);
                    }
                }
            }
        }
    }
}
