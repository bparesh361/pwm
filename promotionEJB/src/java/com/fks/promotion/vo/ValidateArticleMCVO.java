/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.vo;

/**
 *
 * @author ajitn
 */
public class ValidateArticleMCVO {

    private boolean isErrorStatus;
    private String errorMsg;
    private String articleCode;
    private String articleDesc;
    private String mcCode;
    private String mcDesc;
    private String brandCode;
    private String brandDesc;
    private int qty;
    private String mrp;

    public ValidateArticleMCVO() {
    }

    public ValidateArticleMCVO(boolean isErrorStatus, String errorMsg) {
        this.isErrorStatus = isErrorStatus;
        this.errorMsg = errorMsg;
    }

    public ValidateArticleMCVO(boolean isErrorStatus, String errorMsg, String articleCode, String articleDesc, String mcCode, String mcDesc, String brandCode, String brandDesc) {
        this.isErrorStatus = isErrorStatus;
        this.errorMsg = errorMsg;
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
        this.brandCode = brandCode;
        this.brandDesc = brandDesc;
    }

    public ValidateArticleMCVO(boolean isErrorStatus, String errorMsg, String articleCode, String articleDesc, String mcCode, String mcDesc, int qty, String brandCode, String brandDesc) {
        this.isErrorStatus = isErrorStatus;
        this.errorMsg = errorMsg;
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
        this.qty = qty;
        this.brandCode = brandCode;
        this.brandDesc = brandDesc;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isIsErrorStatus() {
        return isErrorStatus;
    }

    public void setIsErrorStatus(boolean isErrorStatus) {
        this.isErrorStatus = isErrorStatus;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getMcDesc() {
        return mcDesc;
    }

    public void setMcDesc(String mcDesc) {
        this.mcDesc = mcDesc;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }
}
