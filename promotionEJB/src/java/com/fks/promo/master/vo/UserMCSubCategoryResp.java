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
public class UserMCSubCategoryResp {

    private Resp resp;
    private List<MCHVo> subCategoryNameList;

    public UserMCSubCategoryResp() {
    }

    public UserMCSubCategoryResp(Resp resp) {
        this.resp = resp;
    }

    public UserMCSubCategoryResp(Resp resp, List<MCHVo> subCategoryNameList) {
        this.resp = resp;
        this.subCategoryNameList = subCategoryNameList;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public List<MCHVo> getSubCategoryNameList() {
        return subCategoryNameList;
    }

    public void setSubCategoryNameList(List<MCHVo> subCategoryNameList) {
        this.subCategoryNameList = subCategoryNameList;
    }
}
