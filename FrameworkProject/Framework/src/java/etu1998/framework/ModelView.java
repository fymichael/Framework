/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.framework;

import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author P15B-79-FY
 */
public class ModelView {

    String viewName;
    HashMap<String, Object> data;
    HashMap<String, Object> session;
    boolean isJson = true;
    boolean invalidateSession = false;
    Vector<String> listSession = new Vector<>();

    public Vector<String> getListSession() {
        return listSession;
    }

    public void setListSession(Vector<String> listSession) {
        this.listSession = listSession;
    }

    public boolean isInvalidateSession() {
        return invalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        this.invalidateSession = invalidateSession;
    }

    public boolean IsJson() {
        return isJson;
    }

    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    }
    
    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    public ModelView() {
    }

    public void addItems(String key, Object value) {
        HashMap<String, Object> has = new HashMap<>();
        has.put(key, value);
        this.setData(has);
    }

    public void addSession(String key, Object value) {
        HashMap<String, Object> sess = new HashMap<>();
        sess.put(key, value);
        this.setData(sess);
    }

}
