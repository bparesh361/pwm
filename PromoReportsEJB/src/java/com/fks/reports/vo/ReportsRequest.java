/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.vo;

import com.fks.promo.vo.ReportCriteriaEnum;

/**
 *
 * @author krutij
 */
public class ReportsRequest {

    //report -1 promo life cycle with article mc detail ---report-4
    private String fromDate;
    private String toDate;
    private String event;
    private String categoryName; //department
    private String subCategoryName; //sub dept
    private String status;
    private String ticketNo; //ticket No(T1-R11)
    private String createdDate;
    private String vendorBacked;

    public String getVendorBacked() {
        return vendorBacked;
    }

    public void setVendorBacked(String vendorBacked) {
        this.vendorBacked = vendorBacked;
    }
    
    // report -3 store proposal
    private String zone;
    private String proposalNo;
    private String problemType;
    private String initiatedFrom;
    private Long createdBy;
    private ReportCriteriaEnum reportCriteriaEnum;
    private ReportTypeEnum reportTypeEnum;
    private String filePath;
    private String ReportStatus;
    private String taskTypeID;
    private String reportReqId;

    private int StartIndex;
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getReportReqId() {
        return reportReqId;
    }

    public void setReportReqId(String reportReqId) {
        this.reportReqId = reportReqId;
    }

    

    public ReportsRequest() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getInitiatedFrom() {
        return initiatedFrom;
    }

    public void setInitiatedFrom(String initiatedFrom) {
        this.initiatedFrom = initiatedFrom;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ReportCriteriaEnum getReportCriteriaEnum() {
        return reportCriteriaEnum;
    }

    public void setReportCriteriaEnum(ReportCriteriaEnum reportCriteriaEnum) {
        this.reportCriteriaEnum = reportCriteriaEnum;
    }

    public ReportTypeEnum getReportTypeEnum() {
        return reportTypeEnum;
    }

    public void setReportTypeEnum(ReportTypeEnum reportTypeEnum) {
        this.reportTypeEnum = reportTypeEnum;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getReportStatus() {
        return ReportStatus;
    }

    public void setReportStatus(String ReportStatus) {
        this.ReportStatus = ReportStatus;
    }

    public String getTaskTypeID() {
        return taskTypeID;
    }

    public void setTaskTypeID(String taskTypeID) {
        this.taskTypeID = taskTypeID;
    }

    public int getStartIndex() {
        return StartIndex;
    }

    public void setStartIndex(int StartIndex) {
        this.StartIndex = StartIndex;
    }

   
}
