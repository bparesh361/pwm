/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.task.vo;


import com.eks.ods.article.vo.ArticleVO;
import com.fks.promo.common.Resp;
import java.util.List;

/**
 *
 * @author Paresb
 */
public class SearchTaskTypeResp {
    
    private Resp resp;
    private List<TaskVO> list;
    private long totalCount;

    public List<TaskVO> getList() {
        return list;
    }

    public void setList(List<TaskVO> list) {
        this.list = list;
    }   
    
    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }    
    
}
