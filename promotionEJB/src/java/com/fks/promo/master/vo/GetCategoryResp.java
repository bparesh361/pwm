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
public class GetCategoryResp {
    private List<String> listCategory;
    private List<String> listSubCategory;
    private Resp resp;

    public GetCategoryResp() {
    }

    public GetCategoryResp(List<String> listCategory, List<String> listSubCategory, Resp resp) {
        this.listCategory = listCategory;
        this.listSubCategory = listSubCategory;
        this.resp = resp;
    }

    public GetCategoryResp(Resp resp) {
        this.resp = resp;
    }

    
    public List<String> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<String> listCategory) {
        this.listCategory = listCategory;
    }

    public List<String> getListSubCategory() {
        return listSubCategory;
    }

    public void setListSubCategory(List<String> listSubCategory) {
        this.listSubCategory = listSubCategory;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
    

}
