/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.master.vo;

import java.util.List;

/**
 *
 * @author krutij
 */
public class AddUpProfileModuleRequest {
    private Long ProfileId;
    
    private List<ModuleVo> moduleList;

    public Long getProfileId() {
        return ProfileId;
    }

    public void setProfileId(Long ProfileId) {
        this.ProfileId = ProfileId;
    }

    public List<ModuleVo> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleVo> moduleList) {
        this.moduleList = moduleList;
    }

    

    
}
