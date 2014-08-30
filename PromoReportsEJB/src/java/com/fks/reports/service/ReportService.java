/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.service;

import com.fks.promo.commonDAO.ReportsDao;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstEvent;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstReport;
import com.fks.promo.entity.MstReportEmail;
import com.fks.promo.entity.MstReportType;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstTask;
import com.fks.promo.entity.MstZone;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstEventFacade;
import com.fks.promo.facade.MstProblemFacade;
import com.fks.promo.facade.MstReportEmailFacade;
import com.fks.promo.facade.MstReportFacade;
import com.fks.promo.facade.MstReportTypeFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.MstTaskFacade;
import com.fks.promo.facade.MstZoneFacade;
import com.fks.promo.vo.ProposalReportVO;
import com.fks.promo.vo.ReportInitiatorEnum;
import com.fks.reports.common.ReportCommonConstants;
import com.fks.reports.common.ReportCommonUtil;
import com.fks.reports.common.ReportsResp;
import com.fks.reports.common.ReportsRespCode;
import com.fks.reports.excel.ProposalPendingAutomatedReport;
import com.fks.reports.jms.JMSMessage;
import com.fks.reports.jms.ReportsJMSService;
import com.fks.reports.mail.MailUtil;
import com.fks.reports.vo.ReportEmailVo;
import com.fks.reports.vo.ReportTypeEnum;
import com.fks.reports.vo.ReportsRequest;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@Stateless
@LocalBean
@WebService
public class ReportService {

    private static final Logger logger = Logger.getLogger(ReportService.class.getName());
    @EJB
    private MstReportFacade mstReportFacade;
    @EJB
    private ReportsDao reportsDao;
    @EJB
    private MstStatusFacade mstStatusFacade;
    @EJB
    private MstEmployeeFacade employeeFacade;
    @EJB
    private ReportsJMSService jMSService;
    @EJB
    private MstEventFacade eventFacade;
    @EJB
    private MstZoneFacade zoneFacade;
    @EJB
    private MstProblemFacade problemFacade;
    @EJB
    private MstTaskFacade taskFacade;
    @EJB
    private MstReportTypeFacade mstReportTypeFacade;
    @EJB
    private MstReportEmailFacade emailFacade;
    @EJB
    ProposalPendingAutomatedReport proposalPendingRPT;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void storeProposalPendingAutomatedReport() {
        try {
            logger.info("-------- Inside Store Proposal Pending Automated Report ------");
            //following is condition for report to be triggered on monday 7 o' clock morning
            Calendar c = Calendar.getInstance();
//            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && c.get(Calendar.HOUR_OF_DAY) == 7) {
            logger.info("--------  Store Proposal Pending Automated Report Fired At : " + new Date());
            MstReportType storeProposalPending = mstReportTypeFacade.find(ReportCommonConstants.STORE_PROPOSAL_PENDING_AUTOMATED_RPT_ID);
            String currDate = null, FromDate = null, toDate = null, zoneName = null;
            Collection<MstReportEmail> emailList = storeProposalPending.getMstReportEmailCollection();
            if (emailList != null && emailList.size() > 0) {

                for (MstReportEmail emailVo : emailList) {
                    String startDate = ReportCommonUtil.addSubtractDaysInDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), -28);
                    String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    List<ProposalReportVO> proposalPendingList = new ArrayList<ProposalReportVO>();
                    if (emailVo.getMstZoneName().equalsIgnoreCase("HO")) {
                        proposalPendingList = reportsDao.getStoreProposalPendingAutomationRpt(startDate, endDate, emailVo.getMstZoneId(), true);
                    } else {
                        proposalPendingList = reportsDao.getStoreProposalPendingAutomationRpt(startDate, endDate, emailVo.getMstZoneId(), false);
                    }

                    if (proposalPendingList != null && proposalPendingList.size() > 0) {

                        String filePath = proposalPendingRPT.generateReport(proposalPendingList);
                        currDate = ReportCommonUtil.dispayFileDateFormat(new Date());

                        SimpleDateFormat formatddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
                        FromDate = ReportCommonUtil.addSubtractDaysInDate(formatddMMyyyy, formatddMMyyyy.format(new Date()), -28);
                        toDate = ReportCommonUtil.addSubtractDaysInDate(formatddMMyyyy, formatddMMyyyy.format(new Date()), -1);
                        zoneName = emailVo.getMstZoneName();
                        String messageBody = MailUtil.genrateMessageBodyForAutomatedReportSendingForStoreProposal(currDate, FromDate, toDate, zoneName);
                        MailUtil.sendMailNotificationWithAttachment(emailVo.getEmailId(), messageBody, filePath, currDate);

                    } else {
                        logger.info("---------- No report Data Found.");
                    }
                }
            }
//            }

        } catch (Exception ex) {
            logger.fatal("---------- Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public ReportsResp submitReportRequest(ReportsRequest request) {
        logger.info("========== Inside submitReportRequest method ====");
        try {
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            System.out.println("------ Payload------ \n" + buffer);

            MstReport mstReport = new MstReport();
            if (request.getCategoryName() != null) {
                mstReport.setCategory(request.getCategoryName());
            }
            mstReport.setCreatedBy(BigInteger.valueOf(request.getCreatedBy()));
            if (request.getEvent() != null) {
                mstReport.setEventId(new BigInteger(request.getEvent()));
            }
            StringBuffer sbInitiatedFrom = new StringBuffer("");
            MstEmployee employee = employeeFacade.find(Long.valueOf(request.getCreatedBy()));
            if (employee != null) {
                if (employee.getMstRole().getMapRoleProfileCollection() != null && !employee.getMstRole().getMapRoleProfileCollection().isEmpty()) {
                    for (MapRoleProfile vo : employee.getMstRole().getMapRoleProfileCollection()) {
                        //System.out.println("---------------------------------------Profile Id  : " + vo.getMstProfile().getProfileId());
                        if (vo.getMstProfile().getProfileId() == 3) {
                            if (employee.getMstStore().getMstStoreId().toString().equalsIgnoreCase("901")) {
                                //mstReport.setInitiatedFrom(ReportInitiatorEnum.L1_HO.toString());
                                sbInitiatedFrom.append(ReportInitiatorEnum.L1_HO.toString()).append(",");
                            } else {
                                //mstReport.setInitiatedFrom(ReportInitiatorEnum.L1_ZONE.toString());
                                sbInitiatedFrom.append(ReportInitiatorEnum.L1_ZONE.toString()).append(",");

                            }
                        } else if (vo.getMstProfile().getProfileId() == 4) {
                            if (employee.getMstStore().getMstStoreId().toString().equalsIgnoreCase("901")) {
                                // mstReport.setInitiatedFrom(ReportInitiatorEnum.L2_HO.toString());
                                sbInitiatedFrom.append(ReportInitiatorEnum.L2_HO.toString()).append(",");
                            } else {
                                //mstReport.setInitiatedFrom(ReportInitiatorEnum.L2_ZONE.toString());
                                sbInitiatedFrom.append(ReportInitiatorEnum.L2_ZONE.toString()).append(",");
                            }
                        } else if (vo.getMstProfile().getProfileId() == 6) {
                            //mstReport.setInitiatedFrom(ReportInitiatorEnum.Promo_Manager.toString());
                            sbInitiatedFrom.append(ReportInitiatorEnum.Promo_Manager.toString()).append(",");
                            if (request.getReportTypeEnum() == ReportTypeEnum.PROMO_TEAM_DASHBOARD_RPT || request.getReportTypeEnum() == ReportTypeEnum.INTERNAL_TASK_RPT) {
                                if (employee.getMstStore().getMstStoreId().toString().equalsIgnoreCase("901")) {
                                    // mstReport.setInitiatedFrom(ReportInitiatorEnum.Promo_Manager_HO.toString());
                                    sbInitiatedFrom.append(ReportInitiatorEnum.Promo_Manager_HO.toString()).append(",");
                                } else {
                                    // mstReport.setInitiatedFrom(ReportInitiatorEnum.Promo_Manager_ZONE.toString());
                                    sbInitiatedFrom.append(ReportInitiatorEnum.Promo_Manager_ZONE.toString()).append(",");
                                }
                            }
                        } else if (vo.getMstProfile().getProfileId() == 2) {
                            if (!employee.getMstStore().getMstStoreId().toString().equalsIgnoreCase("901")) {
                                // mstReport.setInitiatedFrom(ReportInitiatorEnum.Zone_Initiator.toString());
                                sbInitiatedFrom.append(ReportInitiatorEnum.Zone_Initiator.toString()).append(",");
                            }
                        }
                        else {
                            sbInitiatedFrom = new StringBuffer("");
                             sbInitiatedFrom.append(ReportInitiatorEnum.Promo_Manager_HO.toString()).append(",");
                            //return new ReportsResp(ReportsRespCode.FAILURE, "No Report Genration Access for this Profile Users.");
                        }
                        //break;
                    }
                    String initiatorFrom = sbInitiatedFrom.substring(0, sbInitiatedFrom.lastIndexOf(","));
                    System.out.println("$$$$$$$$$$$$ : "+initiatorFrom);
                    mstReport.setInitiatedFrom(initiatorFrom);
                    mstReport.setZoneId(new BigInteger(employee.getMstStore().getMstZone().getZoneId().toString()));
                } else {
                    return new ReportsResp(ReportsRespCode.FAILURE, "No Profile Mapped with the User.");
                }
            } else {
                return new ReportsResp(ReportsRespCode.FAILURE, "Employee is incorrect.");
            }
            if (request.getFromDate() != null && request.getFromDate().length() > 0) {
                mstReport.setInitiationDateFrom(ReportCommonUtil.getDBDate(request.getFromDate()));
            }
            if (request.getToDate() != null && request.getToDate().length() > 0) {
                mstReport.setInitiationDateTo(ReportCommonUtil.getDBDate(request.getToDate()));
            }
            mstReport.setReportDate(new Date());
            if (request.getStatus() != null) {
                mstReport.setSearchStatusId(new BigInteger(request.getStatus()));
                MstStatus ms = mstStatusFacade.find(Long.valueOf(request.getStatus()));
                if (ms != null) {
                    mstReport.setSearchStatusName(ms.getStatusDesc());
                }
            }
            if (request.getSubCategoryName() != null) {
                mstReport.setSubCategory(request.getSubCategoryName());
            }
            if (request.getTicketNo() != null) {
                mstReport.setTicketNumber(request.getTicketNo());
            }
            if (request.getZone() != null) {
                mstReport.setZoneId(new BigInteger(request.getZone()));
                // mstReport.setZoneId(new BigInteger(employee.getMstStore().getMstZone().getZoneId().toString()));
            }
            if (request.getProposalNo() != null) {
                mstReport.setProposalId(request.getProposalNo());
            }
            if (request.getProblemType() != null) {
                mstReport.setProposalProblemTypeId(new BigInteger(request.getProblemType()));
            }
            if (request.getReportTypeEnum() != null) {
                MstReportType mstReportType = reportsDao.getMstReportType(request.getReportTypeEnum().name());
                mstReport.setMstReportType(mstReportType);
            }

            if (request.getTaskTypeID() != null) {
                mstReport.setTaskTypeId(new BigInteger(request.getTaskTypeID()));
            }
            mstReportFacade.create(mstReport);

            JMSMessage jMSMessage = new JMSMessage();
            jMSMessage.setReportCriteriaEnum(request.getReportCriteriaEnum());
            jMSMessage.setReportTypeEnum(request.getReportTypeEnum());
            jMSMessage.setSearchReportID(mstReport.getReportId());
            jMSService.sendReportRequestIntoQueue(jMSMessage);
            JAXB.marshal(jMSMessage, buffer);
            System.out.println("------JMS  Payload------ \n" + buffer);
            return new ReportsResp(ReportsRespCode.SUCCESS, "Report Request Successfully Submitted. Report Request Id : " + mstReport.getReportId());
        } catch (Exception e) {
            e.printStackTrace();
            return new ReportsResp(ReportsRespCode.FAILURE, e.getMessage());
        }
    }

    public List<ReportsRequest> getAllReportsRequestList(Long empId, ReportTypeEnum reportTypeEnum, int startIndex) {
        logger.info("===== Inside getAllReportsRequestList ====");
        try {

            String ReportTypeID = ReportTypeEnum.getReportTypeIDByName(reportTypeEnum);
            List<ReportsRequest> reportsRequestList = new ArrayList<ReportsRequest>();
            ReportsRequest reportsRequest = null;
            List<MstReport> mstReportList = reportsDao.getAllReportsEmpIdANDReportTypeWise(BigInteger.valueOf(empId), Long.parseLong(ReportTypeID), startIndex);
            Long reqCount = 0l;
            reqCount = reportsDao.getAllReportsEmpIdANDReportTypeWiseCount(BigInteger.valueOf(empId), Long.parseLong(ReportTypeID));
            //System.out.println("############## Report Count : " + reqCount + " ######################");
            if (mstReportList != null && mstReportList.size() > 0) {
                for (MstReport vo : mstReportList) {
                    reportsRequest = new ReportsRequest();
                    reportsRequest.setStartIndex(reqCount.intValue());
                    if (vo.getCategory() != null) {
                        reportsRequest.setCategoryName(vo.getCategory());
                    } else {
                        reportsRequest.setCategoryName("-");
                    }
                    reportsRequest.setReportReqId(vo.getReportId().toString());
                    reportsRequest.setCreatedBy(vo.getCreatedBy().longValue());

                    if (vo.getEventId() != null) {
                        MstEvent event = eventFacade.find(vo.getEventId().longValue());
                        reportsRequest.setEvent(event.getEventName());
                    } else {
                        reportsRequest.setEvent("-");
                    }
                    if (vo.getInitiationDateFrom() != null) {
                        reportsRequest.setFromDate(ReportCommonUtil.dispayDateFormat(vo.getInitiationDateFrom()));
                    } else {
                        reportsRequest.setFromDate("-");
                    }
                    if (vo.getInitiationDateFrom() != null) {
                        reportsRequest.setToDate(ReportCommonUtil.dispayDateFormat(vo.getInitiationDateTo()));
                    } else {
                        reportsRequest.setToDate("-");
                    }

                    if (vo.getSearchStatusName() != null) {
                        reportsRequest.setStatus(vo.getSearchStatusName());
                    } else {
                        reportsRequest.setStatus("-");
                    }
                    //reportsRequest.setInitiatedFrom(vo.getInitiatedFrom());

                    if (vo.getSubCategory() != null) {
                        reportsRequest.setSubCategoryName(vo.getSubCategory());
                    } else {
                        reportsRequest.setSubCategoryName("-");
                    }
                    if (vo.getTicketNumber() != null) {
                        reportsRequest.setTicketNo(vo.getTicketNumber());
                    } else {
                        reportsRequest.setTicketNo("-");
                    }
                    if (vo.getReportDate() != null) {
                        reportsRequest.setCreatedDate(ReportCommonUtil.dispayDateFormat(vo.getReportDate()));
                    } else {
                        reportsRequest.setCreatedDate("-");
                    }

                    if (vo.getZoneId() != null) {
                        MstZone zone = zoneFacade.find(vo.getZoneId().longValue());
                        reportsRequest.setZone(zone.getZoneName());
                    }
                    if (vo.getProposalProblemTypeId() != null) {
                        MstProblem problem = problemFacade.find(vo.getProposalProblemTypeId().longValue());
                        if (problem != null) {
                            reportsRequest.setProblemType(problem.getProblemName());
                        }
                    }
                    if (vo.getMstReportType() != null) {
                        reportsRequest.setReportTypeEnum(ReportTypeEnum.getReportTypeValueByID(vo.getMstReportType().getMstReportTypeId().toString()));
                    }

                    if (vo.getProposalId() != null) {
                        reportsRequest.setProposalNo(vo.getProposalId());
                    }

                    if (vo.getTaskTypeId() != null) {
                        MstTask task = taskFacade.find(vo.getTaskTypeId().longValue());
                        reportsRequest.setTaskTypeID(task.getTaskName());
                    }

                    if (vo.getRemarks() != null) {
                        reportsRequest.setRemarks(vo.getRemarks());
                    }
                    reportsRequest.setFilePath(vo.getFilePath());
                    if (vo.getReportStatusId() != null) {
                        if (vo.getReportStatusId().longValue() == 1l) {
                            reportsRequest.setReportStatus("Success");
                        } else if (vo.getReportStatusId().longValue() == 0) {
                            reportsRequest.setReportStatus("Failure");
                        } else {
                            reportsRequest.setReportStatus("-");
                        }
                    }
                    reportsRequestList.add(reportsRequest);
                }
            }
            return reportsRequestList;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return null;
        }

    }

    public List<ReportEmailVo> getAllEmailIdsForStorePendingUserMapping(ReportTypeEnum reportTypeEnum) {
        logger.info("@========= Inside getAllEmailIdsForStorePendingUserMapping ===========@");
        try {

            String ReportTypeID = ReportTypeEnum.getReportTypeIDByName(reportTypeEnum);
            List<ReportEmailVo> list = new ArrayList<ReportEmailVo>();
            ReportEmailVo emailVo;
            MstReportType mstReportType = mstReportTypeFacade.find(new Long(ReportTypeID));
            Collection<MstReportEmail> emailList = mstReportType.getMstReportEmailCollection();
            for (MstReportEmail vo : emailList) {
                emailVo = new ReportEmailVo();
                emailVo.setEmailId(vo.getEmailId());
                emailVo.setReportTypeEmailID(vo.getMstReportTypeEmailId().toString());
                emailVo.setZoneId(vo.getMstZoneId());
                emailVo.setZoneName(vo.getMstZoneName());
                list.add(emailVo);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ReportsResp submitStoreUserMappingForPendingReport(ReportEmailVo reportEmailVo) {
        logger.info("@========= Inside submitStoreUserMappingForPendingReport ============@");
        try {
            if (reportEmailVo != null) {
                MstReportEmail email = new MstReportEmail();
                email.setEmailId(reportEmailVo.getEmailId());
                if (reportEmailVo.getReportTypeEnum() != null) {
                    MstReportType mstReportType = reportsDao.getMstReportType(reportEmailVo.getReportTypeEnum().name());
                    email.setMstReportType(mstReportType);
                }

                email.setMstZoneId(reportEmailVo.getZoneId());
                email.setMstZoneName(reportEmailVo.getZoneName());
                emailFacade.create(email);
                return new ReportsResp(ReportsRespCode.SUCCESS, "Email address added successfully .");
            } else {
                return new ReportsResp(ReportsRespCode.FAILURE, "No Data To Insert.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReportsResp(ReportsRespCode.FAILURE, "Error while submiting Email address.");
        }

    }

    public ReportsResp deletePendingStoreProposalEmailID(String reportID){
        logger.info("@========= Inside deletePendingStoreProposalEmailID ============@");
        try{
            if(reportID!=null){
                MstReportEmail email = emailFacade.find(new Long(reportID));
                if(email!=null){
                    int i = reportsDao.deleteStoreProposalPendingReportEmailID(email.getMstReportTypeEmailId());
                    return new ReportsResp(ReportsRespCode.SUCCESS, "Email Id deleted successfully.");
                }else{
                    return new ReportsResp(ReportsRespCode.FAILURE, "Invalid Email Id.");
                }
            }else{
                return new ReportsResp(ReportsRespCode.FAILURE, "Invalid Email Id.");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ReportsResp(ReportsRespCode.FAILURE, e.getMessage());
        }

    }

    public static void main(String[] args) throws ParseException {
        String startDate = ReportCommonUtil.addSubtractDaysInDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), -28);
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println("---- " + startDate);
        System.out.println("---- " + endDate);
    }
}
