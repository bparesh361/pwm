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
public class RoleProfileMappingReq {
    private Long RoleID;
    private Long LocationId;
    private Long ProfileID;
    private List<Long> profileList;

    public Long getLocationId() {
        return LocationId;
    }

    public void setLocationId(Long LocationId) {
        this.LocationId = LocationId;
    }

    public Long getProfileID() {
        return ProfileID;
    }

    public void setProfileID(Long ProfileID) {
        this.ProfileID = ProfileID;
    }

    public Long getRoleID() {
        return RoleID;
    }

    public void setRoleID(Long RoleID) {
        this.RoleID = RoleID;
    }

    public List<Long> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Long> profileList) {
        this.profileList = profileList;
    }
    

}
