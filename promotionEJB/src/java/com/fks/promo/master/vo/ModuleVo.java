/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.master.vo;

/**
 *
 * @author krutij
 */
public class ModuleVo {
    private Long moduleID;
    private String moduleName;
    private String moduleDesc;

    public ModuleVo() {
    }

    public ModuleVo(Long moduleID, String moduleName, String moduleDesc) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.moduleDesc = moduleDesc;
    }
    

    public Long getModuleID() {
        return moduleID;
    }

    public void setModuleID(Long moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }



}
