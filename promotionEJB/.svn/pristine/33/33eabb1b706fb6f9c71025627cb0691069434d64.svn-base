/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ods.vo;

import com.fks.promo.common.Resp;
import com.fks.promotion.vo.ArticleMCVO;
import java.util.List;

/**
 *
 * @author krutij
 */
public class ArticleSearchResp {

    private Resp resp;
    private List<ArticleMCVO> articleMCList;
    private int nextPageCount;
    private long totalRecordCount;
    private long startRange;
    private long endRange;
    private String filePath;

    public ArticleSearchResp() {
    }

    public ArticleSearchResp(Resp resp) {
        this.resp = resp;
    }

    public ArticleSearchResp(Resp resp, List<ArticleMCVO> articleMCList, long totalRecordCount) {
        this.resp = resp;
        this.articleMCList = articleMCList;
        this.totalRecordCount = totalRecordCount;
    }

    public ArticleSearchResp(Resp resp, String filePath) {
        this.resp = resp;
        this.filePath = filePath;
    }

    public ArticleSearchResp(Resp resp, List<ArticleMCVO> articleMCList, int nextPageCount, long totalRecordCount, long startRange, long endRange) {
        this.resp = resp;
        this.articleMCList = articleMCList;
        this.nextPageCount = nextPageCount;
        this.totalRecordCount = totalRecordCount;
        this.startRange = startRange;
        this.endRange = endRange;
    }

    public List<ArticleMCVO> getArticleMCList() {
        return articleMCList;
    }

    public void setArticleMCList(List<ArticleMCVO> articleMCList) {
        this.articleMCList = articleMCList;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public long getEndRange() {
        return endRange;
    }

    public void setEndRange(long endRange) {
        this.endRange = endRange;
    }

    public int getNextPageCount() {
        return nextPageCount;
    }

    public void setNextPageCount(int nextPageCount) {
        this.nextPageCount = nextPageCount;
    }

    public long getStartRange() {
        return startRange;
    }

    public void setStartRange(long startRange) {
        this.startRange = startRange;
    }

    public long getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(long totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
