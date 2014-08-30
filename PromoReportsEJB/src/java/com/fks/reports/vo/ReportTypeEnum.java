/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.vo;

/**
 *
 * @author krutij
 */
public enum ReportTypeEnum {

    PROMOTION_LIFE_CYCLE_ARTICLE_MC_RPT("1"),
    STORE_PROPOSAL_RPT("3"),
    PROMOTION_LIFE_CYCLE_RPT("2"),
    INTERNAL_TASK_RPT("4"),
    PROMO_TEAM_DASHBOARD_RPT("5"),
    STORE_PROPOSAL_PENDING_AUTOMATED_RPT("6"),
    VENDOR_BACKED_PROMO_RPT("7");
    

    public static ReportTypeEnum getReportTypeValueByID(String id) {
        for (ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()) {
            if (id.equalsIgnoreCase(reportTypeEnum.reportTypeId)) {
                return reportTypeEnum;
            }
        }
        return null;
    }

    public static String getReportTypeIDByName(ReportTypeEnum reportEnumName) {
        for (ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()) {
            if (reportEnumName == reportTypeEnum) {
                return reportTypeEnum.getReportTypeId();
            }
        }
        return null;
    }
    private String reportTypeId;

    private ReportTypeEnum(String reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getReportTypeId() {
        return reportTypeId;
    }
}
