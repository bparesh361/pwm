/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.task.vo;

/**
 *
 * @author Paresb
 */
public class TaskVO {
    
    private Long reqId;
    private Long assignedId;
    private String assignedName;
    private Long mstTaskId;
    private String mstTaskName;
    private String filePath;
    private String headerFilePath;
    private boolean isHO;
    private Long createdBy;
    private String createdByName;
    private String remarks;    
    private String zoneName;
    private Long statusCode;
    private String status;
    private String taskAssignedDate;
    private String taskUpdatedDate;
    private int promoCount;

    public String getTaskAssignedDate() {
        return taskAssignedDate;
    }

    public void setTaskAssignedDate(String taskAssignedDate) {
        this.taskAssignedDate = taskAssignedDate;
    }

    public String getTaskUpdatedDate() {
        return taskUpdatedDate;
    }

    public void setTaskUpdatedDate(String taskUpdatedDate) {
        this.taskUpdatedDate = taskUpdatedDate;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long statusCode) {
        this.statusCode = statusCode;
    }    

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
    
    public String getMstTaskName() {
        return mstTaskName;
    }

    public void setMstTaskName(String mstTaskName) {
        this.mstTaskName = mstTaskName;
    }   
    
    public String getAssignedName() {
        return assignedName;
    }

    public void setAssignedName(String assignedName) {
        this.assignedName = assignedName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }    
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }   
    
    public Long getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(Long assignedId) {
        this.assignedId = assignedId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHeaderFilePath() {
        return headerFilePath;
    }

    public void setHeaderFilePath(String headerFilePath) {
        this.headerFilePath = headerFilePath;
    }

    public boolean isIsHO() {
        return isHO;
    }

    public void setIsHO(boolean isHO) {
        this.isHO = isHO;
    }

    public Long getMstTaskId() {
        return mstTaskId;
    }

    public void setMstTaskId(Long mstTaskId) {
        this.mstTaskId = mstTaskId;
    }

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public int getPromoCount() {
        return promoCount;
    }

    public void setPromoCount(int promoCount) {
        this.promoCount = promoCount;
    }
    
}
