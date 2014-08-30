/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.jms;

import com.fks.promo.vo.ReportCriteriaEnum;
import com.fks.reports.vo.ReportTypeEnum;
import java.io.Serializable;

/**
 *
 * @author krutij
 */
public class JMSMessage implements Serializable {

    private Long searchReportID;
    private ReportTypeEnum reportTypeEnum;
    private ReportCriteriaEnum reportCriteriaEnum;

    public JMSMessage() {
    }

    public JMSMessage(Long searchArticleID) {
        this.searchReportID = searchArticleID;
    }

    public JMSMessage(Long searchReportID, ReportTypeEnum reportTypeEnum, ReportCriteriaEnum reportCriteriaEnum) {
        this.searchReportID = searchReportID;
        this.reportTypeEnum = reportTypeEnum;
        this.reportCriteriaEnum = reportCriteriaEnum;
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

    public Long getSearchReportID() {
        return searchReportID;
    }

    public void setSearchReportID(Long searchReportID) {
        this.searchReportID = searchReportID;
    }
}
