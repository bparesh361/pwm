/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

/**
 *
 * @author Paresb
 */
public class TransPromoArticleVO {

    private long transPromoArticleId;
    private String artCode;//article
    private String artDesc;//article description
    private String mcCode;
    private String mcDesc;
    private String brandCode;
    private String brandDesc;
    private int qty;//article qty
    private boolean isX;
    private boolean isY;
    private boolean isZ;
    private boolean isA;
    private boolean isB;
    private String group;
    private String mrp;
    private int setId;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getArtCode() {
        return artCode;
    }

    public void setArtCode(String artCode) {
        this.artCode = artCode;
    }

    public String getArtDesc() {
        return artDesc;
    }

    public void setArtDesc(String artDesc) {
        this.artDesc = artDesc;
    }

    public boolean isIsX() {
        return isX;
    }

    public void setIsX(boolean isX) {
        this.isX = isX;
    }

    public boolean isIsY() {
        return isY;
    }

    public void setIsY(boolean isY) {
        this.isY = isY;
    }

    public boolean isIsZ() {
        return isZ;
    }

    public void setIsZ(boolean isZ) {
        this.isZ = isZ;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getTransPromoArticleId() {
        return transPromoArticleId;
    }

    public void setTransPromoArticleId(long transPromoArticleId) {
        this.transPromoArticleId = transPromoArticleId;
    }

    public boolean isIsA() {
        return isA;
    }

    public void setIsA(boolean isA) {
        this.isA = isA;
    }

    public boolean isIsB() {
        return isB;
    }

    public void setIsB(boolean isB) {
        this.isB = isB;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
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
