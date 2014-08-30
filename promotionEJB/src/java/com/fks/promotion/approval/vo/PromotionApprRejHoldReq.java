/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.approval.vo;

/**
 *
 * @author nehabha
 */
public class PromotionApprRejHoldReq {

    private Long transPromoId;
    private Long empId;
    private Boolean isApproved;
    private Boolean isHold;
    private Boolean isRejected;
    private Boolean isChangedate;
    private PromotionStatusType statusType;
    private String fromDate;
    private String toDate;
    private String reasonForRejection;
    private String rejectionRemarks;

    public String getRejectionRemarks() {
        return rejectionRemarks;
    }

    public void setRejectionRemarks(String rejectionRemarks) {
        this.rejectionRemarks = rejectionRemarks;
    }

    public String getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(String reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }

    public Boolean getIsChangedate() {
        return isChangedate;
    }

    public void setIsChangedate(Boolean isChangedate) {
        this.isChangedate = isChangedate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public PromotionStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(PromotionStatusType statusType) {
        this.statusType = statusType;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsHold() {
        return isHold;
    }

    public void setIsHold(Boolean isHold) {
        this.isHold = isHold;
    }

    public Long getTransPromoId() {
        return transPromoId;
    }

    public void setTransPromoId(Long transPromoId) {
        this.transPromoId = transPromoId;
    }
}
