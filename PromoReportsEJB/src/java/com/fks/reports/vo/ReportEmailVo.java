/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.reports.vo;

/**
 *
 * @author krutij
 */
public class ReportEmailVo {

    private String reportTypeEmailID;
    private String reportTypeId;
    private String zoneName;
    private String zoneId;
    private String emailId;
    private ReportTypeEnum reportTypeEnum;

    public ReportTypeEnum getReportTypeEnum() {
        return reportTypeEnum;
    }

    public void setReportTypeEnum(ReportTypeEnum reportTypeEnum) {
        this.reportTypeEnum = reportTypeEnum;
    }

    

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getReportTypeEmailID() {
        return reportTypeEmailID;
    }

    public void setReportTypeEmailID(String reportTypeEmailID) {
        this.reportTypeEmailID = reportTypeEmailID;
    }

    public String getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(String reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
    
}
