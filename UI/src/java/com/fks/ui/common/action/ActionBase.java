/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.ui.common.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author javals
 * 
 */

public class ActionBase extends ActionSupport implements SessionAware {

    private Map<String,Object> sessionMap;
   

    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }


}
