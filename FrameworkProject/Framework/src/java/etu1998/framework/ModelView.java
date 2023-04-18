/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1998.framework;

import java.util.HashMap;

/**
 *
 * @author P15B-79-FY
 */
public class ModelView {
    String viewName;
    HashMap<String,Object> data;

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

    public ModelView() {
    }
    
    public void addItems(String key,Object value){
        HashMap<String,Object> has = new HashMap<>();
        has.put(key, value);
        this.setData(has);
    }
    
}
