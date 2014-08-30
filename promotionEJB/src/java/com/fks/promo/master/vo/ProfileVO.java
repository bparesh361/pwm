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
public class ProfileVO {

    private Long ProfileID;
    private String ProfileName;
    private String LevelAccessName;
    private List<ModuleVo> moduleVo;

    public ProfileVO() {
    }

    public ProfileVO(Long ProfileID, String ProfileName, List<ModuleVo> moduleVo) {
        this.ProfileID = ProfileID;
        this.ProfileName = ProfileName;
        this.moduleVo = moduleVo;
    }


    public String getLevelAccessName() {
        return LevelAccessName;
    }

    public void setLevelAccessName(String LevelAccessName) {
        this.LevelAccessName = LevelAccessName;
    }

    public Long getProfileID() {
        return ProfileID;
    }

    public void setProfileID(Long ProfileID) {
        this.ProfileID = ProfileID;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String ProfileName) {
        this.ProfileName = ProfileName;
    }

    public List<ModuleVo> getModuleVo() {
        return moduleVo;
    }

    public void setModuleVo(List<ModuleVo> moduleVo) {
        this.moduleVo = moduleVo;
    }
    

}
