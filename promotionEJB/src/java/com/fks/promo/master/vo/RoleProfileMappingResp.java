/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.master.vo;

import com.fks.promo.common.Resp;
import java.util.List;

/**
 *
 * @author krutij
 */
public class RoleProfileMappingResp {

   
    private Long LocationId;
   
    private List<Long> profileList;
    private Resp resp;
    public RoleProfileMappingResp() {
    }

    public RoleProfileMappingResp(Resp resp) {
        this.resp = resp;
    }

    public RoleProfileMappingResp(Long LocationId, List<Long> profileList, Resp resp) {
        this.LocationId = LocationId;
        this.profileList = profileList;
        this.resp = resp;
    }

   

    public Long getLocationId() {
        return LocationId;
    }

    public void setLocationId(Long LocationId) {
        this.LocationId = LocationId;
    }

   

    public List<Long> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Long> profileList) {
        this.profileList = profileList;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    


}
