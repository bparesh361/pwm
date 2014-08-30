/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.form.vo;

/**
 *
 * @author ajitn
 */
public class TaskFormVO {

    private String taskNumber;
    private String taskAssignTo;
    private String taskType;
    private String remarks;
    private String promoCount;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTaskAssignTo() {
        return taskAssignTo;
    }

    public void setTaskAssignTo(String taskAssignTo) {
        this.taskAssignTo = taskAssignTo;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPromoCount() {
        return promoCount;
    }

    public void setPromoCount(String promoCount) {
        this.promoCount = promoCount;
    }
    
}
