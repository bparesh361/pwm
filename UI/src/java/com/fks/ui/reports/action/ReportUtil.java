/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.reports.action;

import com.fks.reports.service.ReportCriteriaEnum;
import com.fks.reports.service.ReportEmailVo;
import com.fks.reports.service.ReportTypeEnum;
import com.fks.reports.service.ReportsRequest;
import com.fks.reports.service.ReportsResp;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.form.vo.ReportsFormVo;

/**
 *
 * @author krutij
 */

public class ReportUtil {

    public static ReportsResp submitReportsRequest(ReportsFormVo formVo, Long createdBy, ReportTypeEnum reportTypeEnum) {

        ReportsRequest request = new ReportsRequest();
        if (formVo.getCategorySel() != null && !formVo.getCategorySel().equalsIgnoreCase("-1")) {
            request.setCategoryName(formVo.getCategorySel());
        }
        if (createdBy != null) {
            request.setCreatedBy(createdBy);
        }
        if (formVo.getEventSel() != null && !formVo.getEventSel().equalsIgnoreCase("-1")) {
            request.setEvent(formVo.getEventSel());
        }
        if (formVo.getStartDate() != null) {
            request.setFromDate(formVo.getStartDate());
        }
        if (formVo.getStatusSel() != null && !formVo.getStatusSel().equalsIgnoreCase("-1")) {
            request.setStatus(formVo.getStatusSel());
        }
        if (formVo.getSubcategorySel() != null && !formVo.getSubcategorySel().equalsIgnoreCase("-1")) {
            request.setSubCategoryName(formVo.getSubcategorySel());
        }
        if (formVo.getTbTicketNo() != null) {
            request.setTicketNo(formVo.getTbTicketNo());
        }
        if (formVo.getEndDate() != null) {
            request.setToDate(formVo.getEndDate());
        }
        if (formVo.getZoneSel() != null && !formVo.getZoneSel().equalsIgnoreCase("-1")) {
            request.setZone(formVo.getZoneSel());
        }
        if (formVo.getProblemType() != null && !formVo.getProblemType().equalsIgnoreCase("-1")) {
            request.setProblemType(formVo.getProblemType());
        }
        if (formVo.getTbProposalNo() != null) {
            request.setProposalNo(formVo.getTbProposalNo());
        }
        if (formVo.getTaskTypeSel() != null && !formVo.getTaskTypeSel().equalsIgnoreCase("-1")) {
            request.setTaskTypeID(formVo.getTaskTypeSel());
        }

        String searchtype = formVo.getReporttype();
//        System.out.println("Report Type : " + reportTypeEnum);
//        System.out.println("Search Type : " + searchtype);
        if (searchtype != null) {
            if (searchtype.equalsIgnoreCase("INITIATION_DATE_CATEGORY")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_CATEGORY);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE_CATEGORY_EVENT")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_CATEGORY_EVENT);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE_SUB_CATEGORY")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_SUB_CATEGORY);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE_CATEGORY_TICKET_NO")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.TICKET_NO);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE_CATEGORY_STATUS")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_CATEGORY_STATUS);
            } else if (searchtype.equalsIgnoreCase("PROPOSAL_SEARCH_DATE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.PROPOSAL_SEARCH_DATE);
            } else if (searchtype.equalsIgnoreCase("PROPOSAL_SEARCH_DATE_PROBLEM_TYPE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.PROPOSAL_SEARCH_DATE_PROBLEM_TYPE);
            } else if (searchtype.equalsIgnoreCase("PROPOSAL_SEARCH_DATE_PROPOSAL_NO")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.PROPOSAL_SEARCH_DATE_PROPOSAL_NO);
            } else if (searchtype.equalsIgnoreCase("PROPOSAL_SEARCH_DATE_ZONE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.PROPOSAL_SEARCH_DATE_ZONE);
            } else if (searchtype.equalsIgnoreCase("TASK_ASSIGNED_DATE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.TASK_ASSIGNED_DATE);
            } else if (searchtype.equalsIgnoreCase("TASK_ASSIGNED_DATE_TASK_TYPE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.TASK_ASSIGNED_DATE_TASK_TYPE);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE_ZONE")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_ZONE);
            } else if (searchtype.equalsIgnoreCase("INITIATION_DATE_EVENT")) {
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_EVENT);
            } else if(searchtype.equalsIgnoreCase("INITIATION_DATE_CATEGORY_VENDOR_BACKED_ALL")){
                request.setReportCriteriaEnum(ReportCriteriaEnum.INITIATION_DATE_CATEGORY_VENDOR_BACKED_ALL);
            }

        }
        request.setReportTypeEnum(reportTypeEnum);
        ReportsResp resp = ServiceMaster.getReportService().submitReportRequest(request);
        return resp;
    }

    public static ReportsResp submitReportsRequest(String zoneId, String zoneName, String emailId, ReportTypeEnum reportTypeEnum) {
        ReportEmailVo emailVo = new ReportEmailVo();
        emailVo.setEmailId(emailId);
        emailVo.setReportTypeEnum(reportTypeEnum);
        emailVo.setZoneId(zoneId);
        emailVo.setZoneName(zoneName);
        ReportsResp resp = ServiceMaster.getReportService().submitStoreUserMappingForPendingReport(emailVo);
        return resp;
    }
}