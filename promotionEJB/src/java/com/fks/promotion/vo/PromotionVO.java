/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.vo;

/**
 *
 * @author krutij
 */
public class PromotionVO {

    private Long empId;
    private String empCode;
    private String ReqName;
    private String ReqId;
    private Long campaignId;
    private Long eventId;
    private Long MktgId;
    private String Category;
    private String subCategory;
    private String Remarks;
    private int operationCode;
    private String eventName;
    private String campaignName;
    private String MktgName;
    private String createdEmpName;
    private String createdDate;
    private String statusName;
    private Long statusID;
    private Long transPromoId;
    private String categorySub;
    private String subCategorySub;
    private String promoType;
    private String errorFilePath;
    private String errorFilePathRemarks;
    private String reasonRejection;
    private String rejectRemarks;
    private String updatedDate;
    private String uploadFilePath;
    private String vendorBacked;

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRejectRemarks() {
        return rejectRemarks;
    }

    public void setRejectRemarks(String rejectRemarks) {
        this.rejectRemarks = rejectRemarks;
    }

    public String getReasonRejection() {
        return reasonRejection;
    }

    public void setReasonRejection(String reasonRejection) {
        this.reasonRejection = reasonRejection;
    }

    public String getErrorFilePathRemarks() {
        return errorFilePathRemarks;
    }

    public void setErrorFilePathRemarks(String errorFilePathRemarks) {
        this.errorFilePathRemarks = errorFilePathRemarks;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public String getCategorySub() {
        return categorySub;
    }

    public void setCategorySub(String categorySub) {
        this.categorySub = categorySub;
    }

    public String getSubCategorySub() {
        return subCategorySub;
    }

    public void setSubCategorySub(String subCategorySub) {
        this.subCategorySub = subCategorySub;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public Long getMktgId() {
        return MktgId;
    }

    public void setMktgId(Long MktgId) {
        this.MktgId = MktgId;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getReqId() {
        return ReqId;
    }

    public void setReqId(String ReqId) {
        this.ReqId = ReqId;
    }

    public String getReqName() {
        return ReqName;
    }

    public void setReqName(String ReqName) {
        this.ReqName = ReqName;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getMktgName() {
        return MktgName;
    }

    public void setMktgName(String MktgName) {
        this.MktgName = MktgName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedEmpName() {
        return createdEmpName;
    }

    public void setCreatedEmpName(String createdEmpName) {
        this.createdEmpName = createdEmpName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getStatusID() {
        return statusID;
    }

    public void setStatusID(Long statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getTransPromoId() {
        return transPromoId;
    }

    public void setTransPromoId(Long transPromoId) {
        this.transPromoId = transPromoId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public String getVendorBacked() {
        return vendorBacked;
    }

    public void setVendorBacked(String vendorBacked) {
        this.vendorBacked = vendorBacked;
    }

    @Override
    public String toString() {
        return "PromotionVO{" + "empId=" + empId + ", empCode=" + empCode + ", ReqName=" + ReqName + ", ReqId=" + ReqId + ", campaignId=" + campaignId + ", eventId=" + eventId + ", MktgId=" + MktgId + ", Category=" + Category + ", subCategory=" + subCategory + ", Remarks=" + Remarks + ", operationCode=" + operationCode + ", eventName=" + eventName + ", campaignName=" + campaignName + ", MktgName=" + MktgName + ", createdEmpName=" + createdEmpName + ", createdDate=" + createdDate + ", statusName=" + statusName + ", statusID=" + statusID + ", transPromoId=" + transPromoId + ", categorySub=" + categorySub + ", subCategorySub=" + subCategorySub + ", promoType=" + promoType + ", errorFilePath=" + errorFilePath + ", errorFilePathRemarks=" + errorFilePathRemarks + ", reasonRejection=" + reasonRejection + ", rejectRemarks=" + rejectRemarks + ", updatedDate=" + updatedDate + ", uploadFilePath=" + uploadFilePath + ", vendorBacked=" + vendorBacked + '}';
    }
    
    
    
    
}
