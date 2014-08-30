/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.MstReport;

import com.fks.promo.entity.MstReportType;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransTask;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.vo.ProposalReportVO;
import com.fks.promo.vo.ReportCriteriaEnum;
import com.fks.promo.vo.ReportInitiatorEnum;
import com.fks.promo.vo.ReportUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ajitn
 */
@Stateless
@LocalBean
public class ReportsDao {

    public static final Integer MAX_PAGE_RESULT = 30;
    private static String zoneErrorMsg = "No Zone Provided.";
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;
    @EJB
    TransPromoFacade transPromoDao;
    @EJB
    MstEmployeeFacade empFacade;
    
    public List<TransPromo> promoTeamRptQry(ReportCriteriaEnum searchEnum, MstReport report) {
        List<TransPromo> finalPromoList = new ArrayList<TransPromo>();
        Query qry = null;
        String toDate = null;
        if (report.getInitiationDateTo() != null) {
            toDate = ReportUtil.addSubtractDaysInDate(report.getInitiationDateTo(), 1);
            //System.out.println("------- end Date : " + toDate);
        }

        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Promo_Manager_HO.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " where t.status_id IN (27,29,30,31) AND t.updated_date>=? AND t.updated_date<=?  order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                            + " where t.status_id IN (27,29,30,31) AND mp.event_id=? AND t.updated_date>=? AND t.updated_date<=?  order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getEventId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_ZONE:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " where t.status_id IN (27,29,30,31) AND t.zone_id=? AND t.updated_date>=? AND t.updated_date<=?  order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
            }

        }
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Promo_Manager_ZONE.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " where t.status_id IN (27,29,30,31) AND t.zone_id=? AND t.updated_date>=? AND t.updated_date<=?  order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                            + " where t.status_id IN (27,29,30,31) AND t.zone_id=? AND  mp.event_id=? AND t.updated_date>=? AND t.updated_date<=?  order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getEventId());
                    qry.setParameter(3, report.getInitiationDateFrom());
                    qry.setParameter(4, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_ZONE:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " where t.status_id IN (27,29,30,31) AND t.zone_id=? AND t.updated_date>=? AND t.updated_date<=?  order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
            }
        }
        return finalPromoList;
    }

    public List<TransTask> taskRPTQry(ReportCriteriaEnum searchEnum, MstReport report) {
        List<TransTask> finalTaskList = new ArrayList<TransTask>();
        Query qry = null;
        String toDate = null;
        if (report.getInitiationDateTo() != null) {
            toDate = ReportUtil.addSubtractDaysInDate(report.getInitiationDateTo(), 1);
            //System.out.println("------- end Date : " + toDate);
        }
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Promo_Manager_ZONE.toString())) {
            switch (searchEnum) {
                case TASK_ASSIGNED_DATE:
                    qry = em.createNativeQuery("select * from trans_task t "
                            + " inner join mst_employee e on t.created_by=e.emp_id"
                            + " inner join mst_store s on s.mst_store_id=e.mst_store_id"
                            + " where s.mst_zone_id=? and t.created_time>=? and t.created_time<=? order by trans_task_id desc", TransTask.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalTaskList.addAll(qry.getResultList());
                    break;
                case TASK_ASSIGNED_DATE_TASK_TYPE:

                    qry = em.createNativeQuery("select * from trans_task t "
                            + " inner join mst_employee e on t.created_by=e.emp_id"
                            + " inner join mst_store s on s.mst_store_id=e.mst_store_id"
                            + " where s.mst_zone_id=? and t.mst_task_id=? and t.created_time>=? and t.created_time<=? order by trans_task_id desc", TransTask.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getTaskTypeId());
                    qry.setParameter(3, report.getInitiationDateFrom());
                    qry.setParameter(4, toDate);
                    finalTaskList.addAll(qry.getResultList());
                    break;
            }
        } else {
            switch (searchEnum) {
                case TASK_ASSIGNED_DATE:
                    qry = em.createNativeQuery("select * from trans_task where created_time>=? and created_time<=? order by trans_task_id desc", TransTask.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    finalTaskList.addAll(qry.getResultList());
                    break;
                case TASK_ASSIGNED_DATE_TASK_TYPE:
                    qry = em.createNativeQuery("select * from trans_task where mst_task_id=? and created_time>=? and created_time<=? order by trans_task_id desc", TransTask.class);
                    qry.setParameter(1, report.getTaskTypeId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalTaskList.addAll(qry.getResultList());
                    break;
            }
        }

        return finalTaskList;
    }

    private List<ProposalReportVO> proposalZoneInitiatorQry(List<MstProposal> proposalList, MstReport report, String automatedProposalZoneId) {
        List<ProposalReportVO> reportList = new ArrayList<ProposalReportVO>();
        StringBuilder strIntiatorNames = new StringBuilder();
        List<BigInteger> initiatorList = null;
        int i = 0;
        if (proposalList != null && proposalList.size() > 0) {
            //System.out.println("&&&&&&&&&&&&&&& Inside  &&&&&&&&&&&&&&&&&&");
            for (MstProposal proposal : proposalList) {
                ProposalReportVO vo = new ProposalReportVO();
                vo.setProposal(proposal);

                if (report != null) {
                    // Store Proposal Report Zonal FEC Name
                    if (!report.getInitiatedFrom().contains(ReportInitiatorEnum.Zone_Initiator.toString())) {
                        Query qry = em.createNativeQuery("select distinct(f1.emp_id) from map_user_MCH_F1 f1"
                                + " INNER JOIN trans_proposal tp ON tp.mc_code=f1.mc_code "
                                + " INNER JOIN mst_employee em ON f1.emp_id=em.emp_id"
                                + " INNER JOIN mst_store ms ON ms.mst_store_id=em.mst_store_id"
                                + " where tp.proposal_id= ? and ms.mst_zone_id= ?");

                        qry.setParameter(1, proposal.getProposalId());
                        qry.setParameter(2, proposal.getZoneId());
                        initiatorList = qry.getResultList();


                        if (strIntiatorNames.length() > 0) {
                            strIntiatorNames.delete(0, strIntiatorNames.length());
                        }

                        if (initiatorList != null && initiatorList.size() > 0) {
                            for (BigInteger empId : initiatorList) {
                                i++;

                                MstEmployee employee = empFacade.find(empId.longValue());
                                strIntiatorNames.append(employee.getEmployeeName());
                                if (i < initiatorList.size()) {
                                    strIntiatorNames.append(",");
                                }
                            }
                            i = 0;
                            vo.setZonalInitiatorName(strIntiatorNames.toString());
                        }
                    } else {
                        MstEmployee employee = empFacade.find(report.getCreatedBy().longValue());
                        if (employee != null) {
                            vo.setZonalInitiatorName(employee.getEmployeeName());
                        }

                    }
                } else {
                    //Store Proposal Automated Report Zonal FEC Name
                    Query qry = em.createNativeQuery("select distinct(f1.emp_id) from map_user_MCH_F1 f1"
                            + " INNER JOIN trans_proposal tp ON tp.mc_code=f1.mc_code "
                            + " INNER JOIN mst_employee em ON f1.emp_id=em.emp_id"
                            + " INNER JOIN mst_store ms ON ms.mst_store_id=em.mst_store_id"
                            + " where tp.proposal_id= ? and ms.mst_zone_id= ?");

                    qry.setParameter(1, proposal.getProposalId());
                    qry.setParameter(2, proposal.getZoneId());
                    initiatorList = qry.getResultList();

                    if (strIntiatorNames.length() > 0) {
                        strIntiatorNames.delete(0, strIntiatorNames.length());
                    }

                    if (initiatorList != null && initiatorList.size() > 0) {
                        for (BigInteger empId : initiatorList) {
                            i++;

                            MstEmployee employee = empFacade.find(empId.longValue());
                            strIntiatorNames.append(employee.getEmployeeName());
                            if (i < initiatorList.size()) {
                                strIntiatorNames.append(",");
                            }
                        }
                        i = 0;
                        vo.setZonalInitiatorName(strIntiatorNames.toString());
                    }
                }

                /*else {
                //Store Proposal Automated Report Zonal FEC Name
                Query qry = em.createNativeQuery("select distinct(f1.emp_id) from map_user_MCH_F1 f1"
                + " INNER JOIN trans_proposal tp ON tp.mc_code=f1.mc_code "
                + " INNER JOIN mst_employee em ON f1.emp_id=em.emp_id"
                + " INNER JOIN mst_store ms ON ms.mst_store_id=em.mst_store_id"
                + " where ms.mst_zone_id IN (" + automatedProposalZoneId + ")");

                initiatorList = qry.getResultList();


                if (initiatorList != null && initiatorList.size() > 0) {
                for (BigInteger empId : initiatorList) {
                i++;
                if (strIntiatorNames.length() > 0) {
                strIntiatorNames.delete(0, strIntiatorNames.length());
                }
                MstEmployee employee = empFacade.find(empId.longValue());
                strIntiatorNames.append(employee.getEmployeeName());
                if (i < initiatorList.size()) {
                strIntiatorNames.append(",");
                }
                }
                vo.setZonalInitiatorName(strIntiatorNames.toString());
                }
                }*/

                reportList.add(vo);
            }
        }

        return reportList;
    }

    public List<ProposalReportVO> proposalRPTQry(ReportCriteriaEnum searchEnum, MstReport report) {
        try {
            Query qry = null;
            String toDate = null;
            if (report.getInitiationDateTo() != null) {
                toDate = ReportUtil.addSubtractDaysInDate(report.getInitiationDateTo(), 1);
                // System.out.println("------- end Date : " + toDate);
            }
            boolean isChar = true;
            String requestNumber = "";
            if (report.getProposalId() != null && report.getProposalId().length() > 0) {
                String ticketNumber = report.getProposalId();
                ticketNumber = ticketNumber.toUpperCase();
                requestNumber = ticketNumber.substring(ticketNumber.indexOf("P") + 1, ticketNumber.length());
                //System.out.println("$$$$$$$$$$$$$$$$  Request Number : " + requestNumber);
                String line = requestNumber;

                for (int i = 0; i < requestNumber.length(); i++) {
                    //System.out.println("I : " + i + "   " + Character.isDigit(line.charAt(i)));
                    if (!Character.isDigit(line.charAt(i))) {
                        isChar = false;
                        break;
                    }
                }
            }


            List<MstProposal> proposalList = new ArrayList<MstProposal>();
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Zone_Initiator.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                                + "where p.zone_id=? and created_date>=? and created_date<=? and  f1.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                                + "where p.zone_id=? and created_date>=? and created_date<=? and  f1.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                                + "where p.zone_id=? and p.problem_type_id=? and created_date>=? and created_date<=? and  f1.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getProposalProblemTypeId());
                        qry.setParameter(3, report.getInitiationDateFrom());
                        qry.setParameter(4, toDate);
                        qry.setParameter(5, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
//                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
//                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
//                                + "INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
//                                + "where p.proposal_id=? and p.zone_id=? and created_date>=? and created_date<=? and  f1.emp_id=? order by p.proposal_id desc", MstProposal.class);

                        if (isChar) {
                            qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                    + " INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                    + " INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                                    + " where p.proposal_id=? and p.zone_id=? and  f1.emp_id=? order by p.proposal_id desc", MstProposal.class);
                            qry.setParameter(1, new Long(requestNumber));
                            qry.setParameter(2, report.getZoneId());
                            qry.setParameter(3, report.getCreatedBy());
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }

            }
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Promo_Manager.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select * from mst_proposal where  created_date>=? and created_date<=?  order by proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getInitiationDateFrom());
                        qry.setParameter(2, toDate);
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select * from mst_proposal where zone_id = ? and created_date>=? and created_date<=?  order by proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select * from mst_proposal where  problem_type_id=? and created_date>=? and created_date<=?  order by proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getProposalProblemTypeId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                        if (isChar) {
                            qry = em.createNativeQuery("select * from mst_proposal  where proposal_id=?  order by proposal_id desc", MstProposal.class);
                            qry.setParameter(1, requestNumber);
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }

            }
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Promo_Manager_HO.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select * from mst_proposal where  created_date>=? and created_date<=?  order by proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getInitiationDateFrom());
                        qry.setParameter(2, toDate);
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select * from mst_proposal where zone_id = ? and created_date>=? and created_date<=?  order by proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select * from mst_proposal where  problem_type_id=? and created_date>=? and created_date<=?  order by proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getProposalProblemTypeId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                        if (isChar) {
                            qry = em.createNativeQuery("select * from mst_proposal  where proposal_id=?  order by proposal_id desc", MstProposal.class);
                            qry.setParameter(1, requestNumber);
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }

            }
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L1_HO.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                + "where  created_date>=? and created_date<=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getInitiationDateFrom());
                        qry.setParameter(2, toDate);
                        qry.setParameter(3, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                + "where  created_date>=? and created_date<=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getInitiationDateFrom());
                        qry.setParameter(2, toDate);
                        qry.setParameter(3, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                + "where  p.problem_type_id=? and created_date>=? and created_date<=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getProposalProblemTypeId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                        if (isChar) {
                            qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                    + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                    + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                    + "where p.proposal_id=?  and   f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                            qry.setParameter(1, requestNumber);
                            qry.setParameter(2, report.getCreatedBy());
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }
            }
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L1_ZONE.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                + "where p.zone_id=? and created_date>=? and created_date<=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                + "where p.zone_id=? and created_date>=? and created_date<=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                + "where p.zone_id=? and p.problem_type_id=? and created_date>=? and created_date<=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getProposalProblemTypeId());
                        qry.setParameter(3, report.getInitiationDateFrom());
                        qry.setParameter(4, toDate);
                        qry.setParameter(5, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                        if (isChar) {
                            qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                    + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                    + "INNER JOIN map_user_MCH_F2 f2 ON t.mc_code=f2.mc_code "
                                    + "where p.proposal_id=? and p.zone_id=? and  f2.emp_id=? order by p.proposal_id desc", MstProposal.class);
                            qry.setParameter(1, requestNumber);
                            qry.setParameter(2, report.getZoneId());
                            qry.setParameter(3, report.getCreatedBy());
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }
            }
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L2_HO.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                + "where  created_date>=? and created_date<=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getInitiationDateFrom());
                        qry.setParameter(2, toDate);
                        qry.setParameter(3, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                + "where  created_date>=? and created_date<=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getInitiationDateFrom());
                        qry.setParameter(2, toDate);
                        qry.setParameter(3, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                + "where  p.problem_type_id=? and created_date>=? and created_date<=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getProposalProblemTypeId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                        if (isChar) {
                            qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                    + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                    + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                    + "where p.proposal_id=?  and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                            qry.setParameter(1, requestNumber);
                            qry.setParameter(2, report.getCreatedBy());
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }
            }
            if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L2_ZONE.toString())) {
                switch (searchEnum) {
                    case PROPOSAL_SEARCH_DATE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                + "where p.zone_id=? and created_date>=? and created_date<=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_ZONE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                + "where p.zone_id=? and created_date>=? and created_date<=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getInitiationDateFrom());
                        qry.setParameter(3, toDate);
                        qry.setParameter(4, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROBLEM_TYPE:
                        qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                + "where p.zone_id=? and p.problem_type_id=? and created_date>=? and created_date<=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                        qry.setParameter(1, report.getZoneId());
                        qry.setParameter(2, report.getProposalProblemTypeId());
                        qry.setParameter(3, report.getInitiationDateFrom());
                        qry.setParameter(4, toDate);
                        qry.setParameter(5, report.getCreatedBy());
                        proposalList.addAll(qry.getResultList());
                        break;
                    case PROPOSAL_SEARCH_DATE_PROPOSAL_NO:
                        if (isChar) {
                            qry = em.createNativeQuery("select distinct p.* from mst_proposal p "
                                    + "INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                                    + "INNER JOIN map_user_MCH_F3 f3 ON t.mc_code=f3.mc_code "
                                    + "where p.proposal_id=? and p.zone_id=? and  f3.emp_id=? order by p.proposal_id desc", MstProposal.class);
                            qry.setParameter(1, requestNumber);
                            qry.setParameter(2, report.getZoneId());
                            qry.setParameter(3, report.getCreatedBy());
                            proposalList.addAll(qry.getResultList());
                        }
                        break;
                }
            }
            return (proposalZoneInitiatorQry(proposalList, report, null));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TransPromo> promoLifeCycleRptQry(ReportCriteriaEnum searchEnum, MstReport report) {
        Query qry = null;
        String toDate = null;
        if (report.getInitiationDateTo() != null && report.getInitiationDateTo().toString().length() > 0) {
            toDate = ReportUtil.addSubtractDaysInDate(report.getInitiationDateTo(), 1);
            // System.out.println("------- end Date : " + toDate);
        }

        List<TransPromo> finalPromoList = new ArrayList<TransPromo>();
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.Promo_Manager.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mp.updated_date>=? AND mp.updated_date<=?  AND mp.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mp.updated_date>=? AND mp.updated_date<=? AND mp.event_id=? AND mp.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    qry.setParameter(3, report.getEventId());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_STATUS:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                            + " where t.status_id =? AND mp.updated_date>=? AND mp.updated_date<=?  AND mp.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getSearchStatusId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_SUB_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mp.updated_date>=? AND mp.updated_date<=?  AND  mp.sub_category LIKE '%" + report.getSubCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case TICKET_NO:
                    String ticketNumber = report.getTicketNumber();
                    ticketNumber = ticketNumber.toUpperCase();
                    String requestNumber = ticketNumber.substring(ticketNumber.indexOf("R") + 1, ticketNumber.length());
                    //System.out.println("$$$$$$$$$$$$$$$$  Request Number : " + requestNumber);
                    String line = requestNumber;
                    boolean isChar = true;
                    for (int i = 0; i < requestNumber.length(); i++) {
                        //System.out.println("I : " + i + "   " + Character.isDigit(line.charAt(i)));
                        if (!Character.isDigit(line.charAt(i))) {
                            isChar = false;
                            break;
                        }
                    }
                    if (isChar == true) {
                        TransPromo promo = transPromoDao.find(new Long(requestNumber));
                        if (promo != null) {
                            finalPromoList.add(promo);
                        }
                    }
                    break;
            }
        }
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L1_HO.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    qry.setParameter(3, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mc.event_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getEventId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_STATUS:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id =? AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getSearchStatusId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_SUB_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.sub_category LIKE '%" + report.getSubCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    qry.setParameter(3, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case TICKET_NO:
                    String ticketNumber = report.getTicketNumber();
                    ticketNumber = ticketNumber.toUpperCase();
                    String requestNumber = ticketNumber.substring(ticketNumber.indexOf("R") + 1, ticketNumber.length());
                    //System.out.println("$$$$$$$$$$$$$$$$  Request Number : " + requestNumber);
                    String line = requestNumber;
                    boolean isChar = true;
                    for (int i = 0; i < requestNumber.length(); i++) {
                        //System.out.println("I : " + i + "   " + Character.isDigit(line.charAt(i)));
                        if (!Character.isDigit(line.charAt(i))) {
                            isChar = false;
                            break;
                        }
                    }
                    if (isChar == true) {
//                        TransPromo promo = transPromoDao.find(new Long(requestNumber));
//                        if (promo != null) {
//                            finalPromoList.add(promo);
//                        }

                        qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                                + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                                + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                                + " where t.trans_promo_id=? AND t.status_id >=12 AND t.status_id <=31 AND f2.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);
                        qry.setParameter(1, new Long(requestNumber));
                        qry.setParameter(2, report.getCreatedBy());
                        finalPromoList.addAll(qry.getResultList());
                    }
                    break;
            }
        }
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L1_ZONE.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND  mz.zone_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND  mz.zone_id=? AND mc.event_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getEventId());
                    qry.setParameter(3, report.getInitiationDateFrom());
                    qry.setParameter(4, toDate);
                    qry.setParameter(5, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_STATUS:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id =? AND  mz.zone_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getSearchStatusId());
                    qry.setParameter(2, report.getZoneId());
                    qry.setParameter(3, report.getInitiationDateFrom());
                    qry.setParameter(4, toDate);
                    qry.setParameter(5, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_SUB_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND  mz.zone_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f2.emp_id=?  AND mc.sub_category LIKE '%" + report.getSubCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case TICKET_NO:
                    String ticketNumber = report.getTicketNumber();
                    ticketNumber = ticketNumber.toUpperCase();
                    String requestNumber = ticketNumber.substring(ticketNumber.indexOf("R") + 1, ticketNumber.length());
                    //System.out.println("$$$$$$$$$$$$$$$$  Request Number : " + requestNumber);
                    String line = requestNumber;
                    boolean isChar = true;
                    for (int i = 0; i < requestNumber.length(); i++) {
                        //System.out.println("I : " + i + "   " + Character.isDigit(line.charAt(i)));
                        if (!Character.isDigit(line.charAt(i))) {
                            isChar = false;
                            break;
                        }
                    }
                    if (isChar == true) {
//                        TransPromo promo = transPromoDao.find(new Long(requestNumber));
//                        if (promo != null) {
//                            if (promo.getMstZone().getZoneId() == report.getZoneId().longValue()) {
//                                finalPromoList.add(promo);
//                            }
//
//                        }
                        qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                                + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                                + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                                + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                                + " where t.trans_promo_id=? AND t.status_id >=12 AND t.status_id <=31 AND mz.zone_id=?  AND f2.emp_id=?   order by  t.trans_promo_id desc", TransPromo.class);
                        qry.setParameter(1, new Long(requestNumber));
                        qry.setParameter(2, report.getZoneId());
                        qry.setParameter(3, report.getCreatedBy());
                        finalPromoList.addAll(qry.getResultList());
                    }
                    break;
            }

        }
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L2_HO.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    qry.setParameter(3, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mc.event_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getEventId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_STATUS:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id =? AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getSearchStatusId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_SUB_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.sub_category LIKE '%" + report.getSubCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getInitiationDateFrom());
                    qry.setParameter(2, toDate);
                    qry.setParameter(3, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case TICKET_NO:
                    String ticketNumber = report.getTicketNumber();
                    ticketNumber = ticketNumber.toUpperCase();
                    String requestNumber = ticketNumber.substring(ticketNumber.indexOf("R") + 1, ticketNumber.length());
                    //System.out.println("$$$$$$$$$$$$$$$$  Request Number : " + requestNumber);
                    String line = requestNumber;
                    boolean isChar = true;
                    for (int i = 0; i < requestNumber.length(); i++) {
                        // System.out.println("I : " + i + "   " + Character.isDigit(line.charAt(i)));
                        if (!Character.isDigit(line.charAt(i))) {
                            isChar = false;
                            break;
                        }
                    }
                    if (isChar == true) {
//                        TransPromo promo = transPromoDao.find(new Long(requestNumber));
//                        if (promo != null) {
//                            finalPromoList.add(promo);
//                        }
                        qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                                + " INNER JOIN map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                                + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                                + " where t.trans_promo_id=? AND t.status_id >=12 AND t.status_id <=31 AND f2.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);
                        qry.setParameter(1, new Long(requestNumber));
                        qry.setParameter(2, report.getCreatedBy());
                        finalPromoList.addAll(qry.getResultList());
                    }
                    break;
            }

        }
        if (report.getInitiatedFrom().contains(ReportInitiatorEnum.L2_ZONE.toString())) {
            switch (searchEnum) {
                case INITIATION_DATE_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND  mz.zone_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_EVENT:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND  mz.zone_id=? AND mc.event_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getEventId());
                    qry.setParameter(3, report.getInitiationDateFrom());
                    qry.setParameter(4, toDate);
                    qry.setParameter(5, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_CATEGORY_STATUS:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id =? AND  mz.zone_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getSearchStatusId());
                    qry.setParameter(2, report.getZoneId());
                    qry.setParameter(3, report.getInitiationDateFrom());
                    qry.setParameter(4, toDate);
                    qry.setParameter(5, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case INITIATION_DATE_SUB_CATEGORY:
                    qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                            + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                            + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                            + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                            + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                            + " where t.status_id >=12 AND t.status_id <=31 AND  mz.zone_id=? AND mc.updated_date>=? AND mc.updated_date<=?  AND f3.emp_id=?  AND mc.sub_category LIKE '%" + report.getSubCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
                    qry.setParameter(1, report.getZoneId());
                    qry.setParameter(2, report.getInitiationDateFrom());
                    qry.setParameter(3, toDate);
                    qry.setParameter(4, report.getCreatedBy());
                    finalPromoList.addAll(qry.getResultList());
                    break;
                case TICKET_NO:
                    String ticketNumber = report.getTicketNumber();
                    ticketNumber = ticketNumber.toUpperCase();
                    String requestNumber = ticketNumber.substring(ticketNumber.indexOf("R") + 1, ticketNumber.length());
                    //System.out.println("$$$$$$$$$$$$$$$$  Request Number : " + requestNumber);
                    String line = requestNumber;
                    boolean isChar = true;
                    for (int i = 0; i < requestNumber.length(); i++) {
                        //System.out.println("I : " + i + "   " + Character.isDigit(line.charAt(i)));
                        if (!Character.isDigit(line.charAt(i))) {
                            isChar = false;
                            break;
                        }
                    }
                    if (isChar == true) {
//                        TransPromo promo = transPromoDao.find(new Long(requestNumber));
//                        if (promo != null) {
//                            finalPromoList.add(promo);
//                        }
                        qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                                + " INNER JOIN map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                                + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                                + " INNER JOIN map_promo_zone mz on mz.map_promo_id=mc.promo_id "
                                + " where t.trans_promo_id=? AND t.status_id >=12 AND t.status_id <=31 AND mz.zone_id=?  AND f2.emp_id=?   order by  t.trans_promo_id desc", TransPromo.class);
                        qry.setParameter(1, new Long(requestNumber));
                        qry.setParameter(2, report.getZoneId());
                        qry.setParameter(3, report.getCreatedBy());
                        finalPromoList.addAll(qry.getResultList());
                    }
                    break;
            }
        }
        return finalPromoList;
    }
    
    public List<TransPromo> vendorBackedRptQry(ReportCriteriaEnum searchEnum, MstReport report) {
        System.out.println("Executing Vendor Backed Report Query --- " + searchEnum);
        Query qry = null;
        //String toDate = null;
        //if (report.getInitiationDateTo() != null && report.getInitiationDateTo().toString().length() > 0) {
         //   toDate = ReportUtil.addSubtractDaysInDate(report.getInitiationDateTo(), 1);
        //}
        List<TransPromo> finalPromoList = new ArrayList<TransPromo>();
        switch (searchEnum) {
            /**
             * Query : select DISTINCT t.* from trans_promo t INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id where t.status_id >=12 AND t.status_id <=31 AND t.valid_from>='2014-05-23' and t.valid_from<='2014-05-28' and mp.category like '%Apparels%' order by  t.trans_promo_id desc 
             */
            case INITIATION_DATE_CATEGORY:
                System.out.println("Executing Query " + "select DISTINCT t.* from trans_promo t INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id where t.status_id >=12 AND t.status_id <=31 AND t.valid_from>='2014-05-23' and t.valid_from<='2014-05-28' and mp.category like '%Apparels%' order by  t.trans_promo_id desc");
                System.out.println("Generating Report for INITIATION_DATE_CATEGORY_VENDOR_BACKED_ALL  --- Start Date " + report.getInitiationDateFrom() + " to date " + report.getInitiationDateTo() + " category " + report.getCategory());
                qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id >=12 AND t.status_id <=31 AND t.valid_from>=? AND t.valid_from<=?  AND mp.category LIKE '%" + report.getCategory() + "%' AND mp.vendor_backed IS NOT NULL order by  t.trans_promo_id desc", TransPromo.class);
                qry.setParameter(1, report.getInitiationDateFrom());
                qry.setParameter(2, report.getInitiationDateTo());           
                finalPromoList.addAll(qry.getResultList());
                break;
//            case INITIATION_DATE_CATEGORY_EVENT:
//                qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
//                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
//                        + " where t.status_id >=12 AND t.status_id <=31 AND mp.updated_date>=? AND mp.updated_date<=? AND mp.event_id=? AND mp.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
//                qry.setParameter(1, report.getInitiationDateFrom());
//                qry.setParameter(2, toDate);
//                qry.setParameter(3, report.getEventId());
//                finalPromoList.addAll(qry.getResultList());
//                break;
//            case INITIATION_DATE_CATEGORY_STATUS:
//                qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
//                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
//                        + " where t.status_id =? AND mp.updated_date>=? AND mp.updated_date<=?  AND mp.category LIKE '%" + report.getCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
//                qry.setParameter(1, report.getSearchStatusId());
//                qry.setParameter(2, report.getInitiationDateFrom());
//                qry.setParameter(3, toDate);
//                finalPromoList.addAll(qry.getResultList());
//                break;
//            case INITIATION_DATE_SUB_CATEGORY:
//                qry = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
//                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
//                        + " where t.status_id >=12 AND t.status_id <=31 AND mp.updated_date>=? AND mp.updated_date<=?  AND  mp.sub_category LIKE '%" + report.getSubCategory() + "%' order by  t.trans_promo_id desc", TransPromo.class);
//                qry.setParameter(1, report.getInitiationDateFrom());
//                qry.setParameter(2, toDate);
//                finalPromoList.addAll(qry.getResultList());
//                break;
        }
        if(finalPromoList!=null){
        for(TransPromo transpromo : finalPromoList){
                System.out.println("Trans Promo of Query " + transpromo.getTransPromoId());
            }
            }
        return finalPromoList;
    }

    public List<ProposalReportVO> getStoreProposalPendingAutomationRpt(String startDate, String endDate, String zoneId, boolean isHO) {
        Query qry = null;
        if (isHO) {
            qry = em.createNativeQuery("select distinct p.* from mst_proposal p where p.status_id=4 and created_date>=? and created_date<=?  order by p.proposal_id desc", MstProposal.class);
        } else {
            qry = em.createNativeQuery("select distinct p.* from mst_proposal p where p.status_id=4 and created_date>=? and created_date<=? and p.zone_id IN (" + zoneId + ") order by p.proposal_id desc", MstProposal.class);
        }
        qry.setParameter(1, startDate);
        qry.setParameter(2, endDate);
        List<MstProposal> proposalList = qry.getResultList();
        return (proposalZoneInitiatorQry(proposalList, null, zoneId));
    }

    public List<MstReport> getAllReportsEmpIdANDReportTypeWise(BigInteger empId, Long reportType, int startIndex) {
        Query q = em.createNativeQuery("SELECT * FROM mst_report m WHERE m.created_by = ? and mst_report_type_id=? order by  m.report_id desc", MstReport.class);
        q.setParameter(1, empId);
        q.setParameter(2, reportType);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getAllReportsEmpIdANDReportTypeWiseCount(BigInteger empId, Long reportType) {
        Query q = em.createNativeQuery("SELECT count(*) FROM mst_report m WHERE m.created_by = ? and mst_report_type_id=?");
        q.setParameter(1, empId);
        q.setParameter(2, reportType);
        return new Long(q.getSingleResult().toString());
    }

    public MstReportType getMstReportType(String reporttypeName) {
        Query qry = em.createNativeQuery("select * from mst_report_type where mst_report_type_desc= ?", MstReportType.class);
        qry.setParameter(1, reporttypeName);
        List<MstReportType> mapList = qry.getResultList();
        if (mapList != null && mapList.size() > 0) {
            return (mapList.get(0));
        } else {
            return null;
        }
    }

    public int deleteStoreProposalPendingReportEmailID(Long emailID){
        Query qry = em.createNativeQuery("delete from mst_report_email where mst_report_type_email_id=?");
        qry.setParameter(1, emailID);
        return qry.executeUpdate();
    }
}