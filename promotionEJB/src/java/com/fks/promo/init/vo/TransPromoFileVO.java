/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.init.vo;

/**
 *
 * @author krutij
 */
public class TransPromoFileVO {
    private String filePath;
    private Long transPromoFileId;
    private int SetId;
    private String errorFilePath;
    private String remarks;

    public int getSetId() {
        return SetId;
    }

    public void setSetId(int SetId) {
        this.SetId = SetId;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getTransPromoFileId() {
        return transPromoFileId;
    }

    public void setTransPromoFileId(Long transPromoFileId) {
        this.transPromoFileId = transPromoFileId;
    }

    

}
