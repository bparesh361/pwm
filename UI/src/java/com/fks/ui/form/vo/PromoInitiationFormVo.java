/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.form.vo;

import java.io.File;

/**
 *
 * @author krutij
 */
public class PromoInitiationFormVo {

    private String categorysel, subcategorysel, marketingSel, eventSel, txtremarks, txtRequestName, txtReqId, txtExistingReq, isSubmit, promotionSel;
    private String errorfilePath;
    private File promotionFileUpload;
    private String promotionFileUploadFileContentType;
    private String promotionFileUploadFileFileName;
    private String hdnSubCategory, hdncategory;
    private String SessionmstPromoId;
    private String ToCategoryLB,ToSubCategoryLB;
    private String campaignSel;
    private String vendorbackingsel;

    public String getToCategoryLB() {
        return ToCategoryLB;
    }

    public void setToCategoryLB(String ToCategoryLB) {
        this.ToCategoryLB = ToCategoryLB;
    }

    public String getToSubCategoryLB() {
        return ToSubCategoryLB;
    }

    public void setToSubCategoryLB(String ToSubCategoryLB) {
        this.ToSubCategoryLB = ToSubCategoryLB;
    }
    

    public String getSessionmstPromoId() {
        return SessionmstPromoId;
    }

    public void setSessionmstPromoId(String SessionmstPromoId) {
        this.SessionmstPromoId = SessionmstPromoId;
    }

    public String getHdnSubCategory() {
        return hdnSubCategory;
    }

    public void setHdnSubCategory(String hdnSubCategory) {
        this.hdnSubCategory = hdnSubCategory;
    }

    public String getHdncategory() {
        return hdncategory;
    }

    public void setHdncategory(String hdncategory) {
        this.hdncategory = hdncategory;
    }

    public String getErrorfilePath() {
        return errorfilePath;
    }

    public void setErrorfilePath(String errorfilePath) {
        this.errorfilePath = errorfilePath;
    }

    public File getPromotionFileUpload() {
        return promotionFileUpload;
    }

    public void setPromotionFileUpload(File promotionFileUpload) {
        this.promotionFileUpload = promotionFileUpload;
    }

    public String getPromotionFileUploadFileContentType() {
        return promotionFileUploadFileContentType;
    }

    public void setPromotionFileUploadFileContentType(String promotionFileUploadFileContentType) {
        this.promotionFileUploadFileContentType = promotionFileUploadFileContentType;
    }

    public String getPromotionFileUploadFileFileName() {
        return promotionFileUploadFileFileName;
    }

    public void setPromotionFileUploadFileFileName(String promotionFileUploadFileFileName) {
        this.promotionFileUploadFileFileName = promotionFileUploadFileFileName;
    }

    public String getCategorysel() {
        return categorysel;
    }

    public void setCategorysel(String categorysel) {
        this.categorysel = categorysel;
    }

    public String getEventSel() {
        return eventSel;
    }

    public void setEventSel(String eventSel) {
        this.eventSel = eventSel;
    }

    public String getMarketingSel() {
        return marketingSel;
    }

    public void setMarketingSel(String marketingSel) {
        this.marketingSel = marketingSel;
    }   

    public String getSubcategorysel() {
        return subcategorysel;
    }

    public void setSubcategorysel(String subcategorysel) {
        this.subcategorysel = subcategorysel;
    }

    public String getTxtReqId() {
        return txtReqId;
    }

    public void setTxtReqId(String txtReqId) {
        this.txtReqId = txtReqId;
    }

    public String getTxtRequestName() {
        return txtRequestName;
    }

    public void setTxtRequestName(String txtRequestName) {
        this.txtRequestName = txtRequestName;
    }

    public String getTxtremarks() {
        return txtremarks;
    }

    public void setTxtremarks(String txtremarks) {
        this.txtremarks = txtremarks;
    }

    public String getTxtExistingReq() {
        return txtExistingReq;
    }

    public void setTxtExistingReq(String txtExistingReq) {
        this.txtExistingReq = txtExistingReq;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getPromotionSel() {
        return promotionSel;
    }

    public void setPromotionSel(String promotionSel) {
        this.promotionSel = promotionSel;
    }

    public String getCampaignSel() {
        return campaignSel;
    }

    public void setCampaignSel(String campaignSel) {
        this.campaignSel = campaignSel;
    }

    public String getVendorbackingsel() {
        return vendorbackingsel;
    }

    public void setVendorbackingsel(String vendorbackingsel) {
        this.vendorbackingsel = vendorbackingsel;
    }

    @Override
    public String toString() {
        return "PromoInitiationFormVo{" + "categorysel=" + categorysel + ", subcategorysel=" + subcategorysel + ", marketingSel=" + marketingSel + ", eventSel=" + eventSel + ", txtremarks=" + txtremarks + ", txtRequestName=" + txtRequestName + ", txtReqId=" + txtReqId + ", txtExistingReq=" + txtExistingReq + ", isSubmit=" + isSubmit + ", promotionSel=" + promotionSel + ", errorfilePath=" + errorfilePath + ", promotionFileUpload=" + promotionFileUpload + ", promotionFileUploadFileContentType=" + promotionFileUploadFileContentType + ", promotionFileUploadFileFileName=" + promotionFileUploadFileFileName + ", hdnSubCategory=" + hdnSubCategory + ", hdncategory=" + hdncategory + ", SessionmstPromoId=" + SessionmstPromoId + ", ToCategoryLB=" + ToCategoryLB + ", ToSubCategoryLB=" + ToSubCategoryLB + ", campaignSel=" + campaignSel + ", vendorbackingsel=" + vendorbackingsel + '}';
    }    
    
}
