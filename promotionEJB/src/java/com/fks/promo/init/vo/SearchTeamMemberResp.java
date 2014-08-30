/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

import com.fks.promo.common.Resp;
import java.util.List;

/**
 *
 * @author Paresb
 */
public class SearchTeamMemberResp {
    
    private Resp resp;
    private List<TeamMember> teamlist;

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public List<TeamMember> getTeamlist() {
        return teamlist;
    }

    public void setTeamlist(List<TeamMember> teamlist) {
        this.teamlist = teamlist;
    }

    public SearchTeamMemberResp() {
    }

    public SearchTeamMemberResp(Resp resp) {
        this.resp = resp;
    }   
    
    
    
}
