/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

import com.fks.promo.common.Resp;
import java.util.List;

/**
 *
 * @author ajitn
 */
public class MCUserResp {

    private Resp resp;
    private List<MCUserVO> userMCList;

    public MCUserResp() {
    }

    public MCUserResp(Resp resp) {
        this.resp = resp;
    }

    public MCUserResp(Resp resp, List<MCUserVO> userMCList) {
        this.resp = resp;
        this.userMCList = userMCList;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public List<MCUserVO> getUserMCList() {
        return userMCList;
    }

    public void setUserMCList(List<MCUserVO> userMCList) {
        this.userMCList = userMCList;
    }
}
