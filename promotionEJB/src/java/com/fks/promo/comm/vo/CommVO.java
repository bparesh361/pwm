/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.comm.vo;

import com.fks.promo.master.vo.MCHVo;
import com.fks.promo.master.vo.StoreVO;

/**
 *
 * @author Paresb
 */
public class CommVO {
    
    private Long transPromoId;
    private String mstPromoId;
    private String eventType;
    private String promoType;
    private String promoDetails;
    private String validFrom;
    private String validTo;
    private String cashierTriggerCode;
    private String articleNumber;
    private String articleDesc;
    private String mcCode;
    private String mcDesc;
    private String subCategory;
    private String category;
    private String city;
    private String site;
    private String state;
    private String zone;
    private String region;
    private String bonusBy;

    public String getBonusBy() {
        return bonusBy;
    }

    public void setBonusBy(String bonusBy) {
        this.bonusBy = bonusBy;
    }
    

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMstPromoId() {
        return mstPromoId;
    }

    public void setMstPromoId(String mstPromoId) {
        this.mstPromoId = mstPromoId;
    }

    

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
    
    public Long getTransPromoId() {
        return transPromoId;
    }

    public void setTransPromoId(Long transPromoId) {
        this.transPromoId = transPromoId;
    }   
    
    public String getCashierTriggerCode() {
        return cashierTriggerCode;
    }

    public void setCashierTriggerCode(String cashierTriggerCode) {
        this.cashierTriggerCode = cashierTriggerCode;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPromoDetails() {
        return promoDetails;
    }

    public void setPromoDetails(String promoDetails) {
        this.promoDetails = promoDetails;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    
    
}
