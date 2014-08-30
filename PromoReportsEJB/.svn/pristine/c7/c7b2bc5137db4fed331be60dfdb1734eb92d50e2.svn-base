/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.jms;

import com.fks.promo.entity.MstReport;
import com.fks.promo.vo.ReportCriteriaEnum;
import com.fks.promo.vo.ReportInitiatorEnum;
import java.math.BigInteger;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
@Stateless
public class JMSUtil {

    private static String intiationFromDateMsg = "From Date Not Passed.";
    private static String intiationToDateMsg = "To Date Not Passed.";
    private static String categoryMsg = "Category Not Passed.";
    private static String subCategoryMsg = "Sub Category Not Passed.";
    private static String eventMsg = "Event Not Passed.";
    private static String statusMsg = "Status Not Passed.";
    private static String zoneMsg = "Zone Not Passed.";
    private static String problemTypeMsg = "Problem Type Not Passed.";
    private static String proposalIdMsg = "Proposal Id Not Passed.";
    private static String taskTypeMsg = "Task Type Not Passed.";
    private static String ticketNoMsg = "Ticket No Not Passed.";
    private static BigInteger reportFailStatusId = new BigInteger("0");

    public boolean validateCriteria(ReportCriteriaEnum searchEnum, MstReport report, Logger logger) {
        if(report==null){
            return false;
        }
        boolean isAllValidate = true;
        try {
            String initiatedFrom = report.getInitiatedFrom();
            if (initiatedFrom.equalsIgnoreCase(ReportInitiatorEnum.L1_ZONE.toString()) || initiatedFrom.equalsIgnoreCase(ReportInitiatorEnum.L2_ZONE.toString()) || initiatedFrom.equalsIgnoreCase(ReportInitiatorEnum.Promo_Manager_ZONE.toString())) {
                if (report.getZoneId() == null) {
                    report.setRemarks(zoneMsg);
                    isAllValidate = false;
                    return isAllValidate;
                }
            }
            switch (searchEnum) {
                case INITIATION_DATE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_CATEGORY:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getCategory() == null || report.getCategory().length() == 0) {
                        report.setRemarks(categoryMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_CATEGORY_EVENT:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getCategory() == null || report.getCategory().length() == 0) {
                        report.setRemarks(categoryMsg);
                        isAllValidate = false;
                    } else if (report.getEventId() == null) {
                        report.setRemarks(eventMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_CATEGORY_STATUS:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getCategory() == null || report.getCategory().length() == 0) {
                        report.setRemarks(categoryMsg);
                        isAllValidate = false;
                    } else if (report.getSearchStatusId() == null) {
                        report.setRemarks(statusMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_CATEGORY_TICKET_NO:
                    if (report.getTicketNumber() == null || report.getTicketNumber().length() == 0) {
                        report.setRemarks(ticketNoMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_EVENT:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getEventId() == null) {
                        report.setRemarks(eventMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_SUB_CATEGORY:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getCategory() == null || report.getCategory().length() == 0) {
                        report.setRemarks(categoryMsg);
                        isAllValidate = false;
                    } else if (report.getSubCategory() == null || report.getSubCategory().length() == 0) {
                        report.setRemarks(subCategoryMsg);
                        isAllValidate = false;
                    }
                    break;
                case INITIATION_DATE_ZONE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getZoneId() == null) {
                        report.setRemarks(zoneMsg);
                        isAllValidate = false;
                    }
                    break;
                case PROPOSAL_SEARCH_DATE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    }
                    break;
                case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getProposalProblemTypeId() == null) {
                        report.setRemarks(problemTypeMsg);
                        isAllValidate = false;
                    }
                    break;
                case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                    if (report.getProposalId() == null || report.getProposalId().length() == 0) {
                        report.setRemarks(proposalIdMsg);
                        isAllValidate = false;
                    }
                    break;
                case PROPOSAL_SEARCH_DATE_ZONE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getZoneId() == null) {
                        report.setRemarks(zoneMsg);
                        isAllValidate = false;
                    }
                    break;
                case TASK_ASSIGNED_DATE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    }
                    break;
                case TASK_ASSIGNED_DATE_TASK_TYPE:
                    if (report.getInitiationDateFrom() == null) {
                        report.setRemarks(intiationFromDateMsg);
                        isAllValidate = false;
                    } else if (report.getInitiationDateTo() == null) {
                        report.setRemarks(intiationToDateMsg);
                        isAllValidate = false;
                    } else if (report.getTaskTypeId() == null) {
                        report.setRemarks(taskTypeMsg);
                        isAllValidate = false;
                    }
                    break;
                case TICKET_NO:
                    if (report.getTicketNumber() == null || report.getTicketNumber().length() == 0) {
                        report.setRemarks(ticketNoMsg);
                        isAllValidate = false;
                    }
                    break;
            }
            if (!isAllValidate) {
                report.setReportStatusId(reportFailStatusId);
            }
            return isAllValidate;
        } catch (Exception ex) {
            logger.fatal("------- Error : " + ex.getMessage());
            report.setRemarks("Error");
            report.setReportStatusId(reportFailStatusId);
            ex.printStackTrace();
            return false;
        }

    }
}
