/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.comm.vo;

import com.fks.promo.common.Resp;
import java.util.List;

/**
 *
 * @author Paresb
 */
public class CommSearchResp {
    
    private Resp resp;
    private List<CommVO> list;
    private Long totalCount;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }    

    public CommSearchResp() {
    }

    public CommSearchResp(Resp resp) {
        this.resp = resp;
    }   
    

    public CommSearchResp(Resp resp, List<CommVO> list) {
        this.resp = resp;
        this.list = list;
    }    
    
    public List<CommVO> getList() {
        return list;
    }

    public void setList(List<CommVO> list) {
        this.list = list;
    }
    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }  
    
    
}
