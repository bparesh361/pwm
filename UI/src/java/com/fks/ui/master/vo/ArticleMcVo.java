/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.vo;

/**
 *
 * @author krutij
 */
public class ArticleMcVo {

    private boolean isErrorStatus;
    private String errorMsg;
    private String articleCode;
    private String articleDesc;
    private String mcCode;
    private String mcDesc;
    private int qty;
    private boolean  is_x;
    private boolean  is_y;
    private boolean  is_z;
    private boolean is_a;
    private boolean is_b;

    public ArticleMcVo(String articleCode, String articleDesc, String mcCode, String mcDesc, int qty, boolean is_x, boolean is_y, boolean is_z, boolean is_a, boolean is_b) {
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
        this.qty = qty;
        this.is_x = is_x;
        this.is_y = is_y;
        this.is_z = is_z;
        this.is_a = is_a;
        this.is_b = is_b;
    }

  

    public boolean isIs_x() {
        return is_x;
    }

    public void setIs_x(boolean is_x) {
        this.is_x = is_x;
    }

    public boolean isIs_y() {
        return is_y;
    }

    public void setIs_y(boolean is_y) {
        this.is_y = is_y;
    }

    public boolean isIs_z() {
        return is_z;
    }

    public void setIs_z(boolean is_z) {
        this.is_z = is_z;
    }

    public ArticleMcVo() {
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
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

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getMcDesc() {
        return mcDesc;
    }

    public void setMcDesc(String mcDesc) {
        this.mcDesc = mcDesc;
    }

    public boolean isIs_a() {
        return is_a;
    }

    public void setIs_a(boolean is_a) {
        this.is_a = is_a;
    }

    public boolean isIs_b() {
        return is_b;
    }

    public void setIs_b(boolean is_b) {
        this.is_b = is_b;
    }

}
