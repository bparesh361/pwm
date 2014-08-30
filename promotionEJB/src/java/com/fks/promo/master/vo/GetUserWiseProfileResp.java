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
public class GetUserWiseProfileResp {
    private Resp resp;
    private List<ProfileVO> profileVOLIst;
 private List<MCHVo> listUserMchF1;
    private List<MCHVo> listUserMchF2;
    private List<MCHVo> listUserMchF3;
    private List<MCHVo> listUserMchF5;
    public GetUserWiseProfileResp() {
    }

    public GetUserWiseProfileResp(Resp resp) {
        this.resp = resp;
    }

    public GetUserWiseProfileResp(Resp resp, List<ProfileVO> profileVOLIst) {
        this.resp = resp;
        this.profileVOLIst = profileVOLIst;
    }

    public GetUserWiseProfileResp(Resp resp, List<ProfileVO> profileVOLIst, List<MCHVo> listUserMchF1, List<MCHVo> listUserMchF2, List<MCHVo> listUserMchF3, List<MCHVo> listUserMchF5) {
        this.resp = resp;
        this.profileVOLIst = profileVOLIst;
        this.listUserMchF1 = listUserMchF1;
        this.listUserMchF2 = listUserMchF2;
        this.listUserMchF3 = listUserMchF3;
        this.listUserMchF5 = listUserMchF5;
    }

     
    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public List<ProfileVO> getProfileVOLIst() {
        return profileVOLIst;
    }

    public void setProfileVOLIst(List<ProfileVO> profileVOLIst) {
        this.profileVOLIst = profileVOLIst;
    }

    public List<MCHVo> getListUserMchF1() {
        return listUserMchF1;
    }

    public void setListUserMchF1(List<MCHVo> listUserMchF1) {
        this.listUserMchF1 = listUserMchF1;
    }

    public List<MCHVo> getListUserMchF2() {
        return listUserMchF2;
    }

    public void setListUserMchF2(List<MCHVo> listUserMchF2) {
        this.listUserMchF2 = listUserMchF2;
    }

    public List<MCHVo> getListUserMchF3() {
        return listUserMchF3;
    }

    public void setListUserMchF3(List<MCHVo> listUserMchF3) {
        this.listUserMchF3 = listUserMchF3;
    }

    public List<MCHVo> getListUserMchF5() {
        return listUserMchF5;
    }

    public void setListUserMchF5(List<MCHVo> listUserMchF5) {
        this.listUserMchF5 = listUserMchF5;
    }
    

}
