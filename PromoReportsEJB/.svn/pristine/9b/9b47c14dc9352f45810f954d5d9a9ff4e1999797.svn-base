/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.jms;

import com.fks.promo.commonDAO.ReportsDao;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstReport;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransTask;
import com.fks.promo.facade.MstReportFacade;
import com.fks.promo.vo.ProposalReportVO;
import com.fks.reports.excel.InternalTaskReport;
import com.fks.reports.excel.ProposalArticleMcReport;
import com.fks.reports.excel.PromoLifeCycleArticleMCReport;
import com.fks.reports.excel.PromoLifeCycleReport;
import com.fks.reports.excel.PromoTeamDashboardReport;
import com.fks.reports.excel.VendorBackedOfferReport;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@MessageDriven(mappedName = "jms/reportqueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ReportsJMSReciver implements MessageListener {

    private static final Logger logger = Logger.getLogger(ReportsJMSReciver.class.getName());
    @EJB
    MstReportFacade reportFacade;
    @EJB
    ReportsDao reportDao;
    @EJB
    JMSUtil reportValidateUtil;
    @EJB
    PromoLifeCycleReport promoLifeCycleRPT;
    
    @EJB
    VendorBackedOfferReport vendorBackedOfferRPT;
    
    @EJB
    PromoLifeCycleArticleMCReport promoLifeCycleArticleRPT;
    @EJB
    ProposalArticleMcReport proposalArticleRPT;
    @EJB
    InternalTaskReport internalTaskRPT;
    @EJB
    PromoTeamDashboardReport promoTeamRPT;

    public ReportsJMSReciver() {
    }

    public void onMessage(Message message) {
        System.out.println("----------- Inside generating reports------");
        try {
            ObjectMessage objectmessage = (ObjectMessage) message;
            JMSMessage msg = (JMSMessage) objectmessage.getObject();
            MstReport report = reportFacade.find(msg.getSearchReportID());
            List<TransPromo> promoList = null;
            List<MstPromo> reportList = null;
            String remarks = "No report Generated.Data Not Found.";
            String successRemarks = "Report Generated.";
            if (reportValidateUtil.validateCriteria(msg.getReportCriteriaEnum(), report, logger)) {
                switch (msg.getReportTypeEnum()) {
                    case PROMOTION_LIFE_CYCLE_ARTICLE_MC_RPT:
                        promoList = reportDao.promoLifeCycleRptQry(msg.getReportCriteriaEnum(), report);
                        if (promoList != null && promoList.size() > 0) {
                            //System.out.println(" ########### Resp List Size  :" + promoList.size());
                            String filePath = promoLifeCycleArticleRPT.generateReport(promoList, report);
                            if (filePath == null) {
                                report.setReportStatusId(new BigInteger("0"));
                                report.setRemarks("File Path Not Found.");
                            }
                            report.setFilePath(filePath);
                            report.setReportStatusId(new BigInteger("1"));
                            report.setRemarks(successRemarks);
                        } else {
                            report.setReportStatusId(new BigInteger("0"));
                            report.setRemarks(remarks);
                        }
                        break;
                    case PROMOTION_LIFE_CYCLE_RPT:
                        promoList = reportDao.promoLifeCycleRptQry(msg.getReportCriteriaEnum(), report);
                        if (promoList != null && promoList.size() > 0) {
                            //System.out.println(" ########### Resp List Size  :" + promoList.size());
                            String filePath = promoLifeCycleRPT.generateReport(promoList, report);
                            report.setFilePath(filePath);
                            report.setReportStatusId(new BigInteger("1"));
                            report.setRemarks(successRemarks);
                        } else {
                            report.setReportStatusId(new BigInteger("0"));
                            report.setRemarks(remarks);
                        }
                        break;
                        
                    case VENDOR_BACKED_PROMO_RPT:
                        promoList = reportDao.vendorBackedRptQry(msg.getReportCriteriaEnum(), report);
                        if (promoList != null && promoList.size() > 0) {
                            //System.out.println(" ########### Resp List Size  :" + promoList.size());
                            String filePath = vendorBackedOfferRPT.generateReport(promoList, report);
                            if (filePath == null) {
                                report.setReportStatusId(new BigInteger("0"));
                                report.setRemarks("File Path Not Found.");
                            }
                            report.setFilePath(filePath);
                            report.setReportStatusId(new BigInteger("1"));
                            report.setRemarks(successRemarks);
                        } else {
                            report.setReportStatusId(new BigInteger("0"));
                            report.setRemarks(remarks);
                        }
                        break;
                        
//                        
//                        
//                        reportList = reportDao.vendorBackedRptQry(msg.getReportCriteriaEnum(), report);
//                        if (promoList != null && promoList.size() > 0) {
//                            //System.out.println(" ########### Resp List Size  :" + promoList.size());
//                            String filePath = vendorBackedOfferRPT.generateReport(reportList, report);
//                            report.setFilePath(filePath);
//                            report.setReportStatusId(new BigInteger("1"));
//                            report.setRemarks(successRemarks);
//                        } else {
//                            report.setReportStatusId(new BigInteger("0"));
//                            report.setRemarks(remarks);
//                        }
//                        break;
                        
                    case STORE_PROPOSAL_RPT:
                        List<ProposalReportVO> proposalList = reportDao.proposalRPTQry(msg.getReportCriteriaEnum(), report);
                        if (proposalList != null && proposalList.size() > 0) {
                            //System.out.println(" ########### Resp List Size  :" + proposalList.size());
                            String filePath = proposalArticleRPT.generateReport(proposalList, report);
                            report.setFilePath(filePath);
                            report.setReportStatusId(new BigInteger("1"));
                            report.setRemarks(successRemarks);
                        } else {
                            report.setReportStatusId(new BigInteger("0"));
                            report.setRemarks(remarks);
                        }
                        break;
                    case INTERNAL_TASK_RPT:
                        List<TransTask> taskList = reportDao.taskRPTQry(msg.getReportCriteriaEnum(), report);
                        //System.out.println(" ########### Resp List Size  :" + taskList.size());
                        if (taskList != null && taskList.size() > 0) {

                            String filePath = internalTaskRPT.generateReport(taskList, report);
                            report.setFilePath(filePath);
                            report.setReportStatusId(new BigInteger("1"));
                            report.setRemarks(successRemarks);
                        } else {
                            report.setReportStatusId(new BigInteger("0"));
                            report.setRemarks(remarks);
                        }
                        break;
                    case PROMO_TEAM_DASHBOARD_RPT:
                        promoList = reportDao.promoTeamRptQry(msg.getReportCriteriaEnum(), report);

                        if (promoList != null && promoList.size() > 0) {
                            //System.out.println(" ########### Resp List Size  :" + promoList.size());
                            String filePath = promoTeamRPT.generateReport(promoList, report);
                            report.setFilePath(filePath);
                            report.setReportStatusId(new BigInteger("1"));
                            report.setRemarks(successRemarks);
                        } else {
                            report.setReportStatusId(new BigInteger("0"));
                            report.setRemarks(remarks);
                        }
                        break;
                }
            }

        } catch (Exception ex) {
            logger.info("------------ Exception In Report : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
