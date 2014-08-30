/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service;

import com.fks.promo.comm.service.CommService;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonProposalDAO;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstProposalFacade;
import com.fks.promotion.service.util.PromotionProposalUtil;
import com.fks.promotion.vo.ProposalSearchType;
import com.fks.promotion.vo.ProposalVO;
import com.fks.promotion.vo.SearchProposalReq;
import com.fks.promotion.vo.SearchProposalResp;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
@WebService
public class ProposalSearch {

    private static final Logger logger = Logger.getLogger(ProposalSearch.class.getName());
    @EJB
    MstProposalFacade proposalDao;
    @EJB
    CommonProposalDAO commonPropDao;
    @EJB
    MstEmployeeFacade employeeDao;

    public SearchProposalResp getAllProposals(SearchProposalReq request) {
        logger.info("=== Getting Proposals Service === " + request.getSearchType());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        System.out.println(" PAYLOAD ::::::\n " + buffer);
        List<MstProposal> list = null;
        SearchProposalResp response = new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"));
        List<ProposalVO> volist = null;
        MstEmployee employee = null;
        Long zoneid = null;
        if (request.getUserId() != null) {
            employee = employeeDao.find(Long.parseLong(request.getUserId()));
            if (employee == null) {
                return (new SearchProposalResp(new Resp(RespCode.FAILURE, "Emp ID Not Found.")));
            }
            zoneid = employee.getMstStore().getMstZone().getZoneId();
        } else {
            request.setSearchType(ProposalSearchType.ALL);
        }
        Long count = new Long("0");

        try {
            System.out.println("--------- start Date : " + request.getStartDate());
            System.out.println("--------- end Date : " + request.getEndDate());

//            if (request.getStartDate() != null) {
//                request.setStartDate(CommonUtil.addSubtractDaysInDate(request.getStartDate(), -1));
//            }
            if (request.getEndDate() != null) {
                request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
            }
//            if (request.getStartDate() != null && request.getEndDate() != null) {
//                int dayDiff = CommonUtil.getDayDifferenceBetweenStartEndDate(request.getStartDate(), request.getEndDate());
//                System.out.println("---- day diff : " + dayDiff);
//                if (dayDiff <= 2) {
//                    request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
//                }
//            }

        } catch (ParseException ex) {
            System.out.println("--------------Start/End Date Parse Exception In  getAllProposals ProposalSearch.");
            java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (request.getSearchType()) {
            case ALL:
                list = commonPropDao.getAllProposal(request.getStartIndex());
                count = commonPropDao.getAllProposalCount();
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
                break;
            case ID:
                logger.info("--------------- Inside ID ");
                list = commonPropDao.getAllProposalById(new Long(request.getId()), request.getStartIndex());
                count = commonPropDao.getAllProposalByIdCount(new Long(request.getId()));
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
                logger.info("--------------- Inside ID " + volist.size());
//                return new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"), volist);
                break;
            case DATE:
                list = commonPropDao.getAllProposalByDate(request.getStartDate(), request.getEndDate(), request.getStartIndex());
                count = commonPropDao.getAllProposalByDateCount(request.getStartDate(), request.getEndDate());
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
//                return new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"), volist);
                break;
            case DATE_ZONE:
                list = commonPropDao.getAllProposalByDateZone(request.getStartDate(), request.getEndDate(), zoneid, request.getStartIndex());
                count = commonPropDao.getAllProposalByDateZoneCount(request.getStartDate(), request.getEndDate(), zoneid);
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
//                return new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"), volist);
                break;
            case DATE_ZONE_STATUS:
                logger.info("--- Searching Proposal using Date Zone and Status --- ");
                list = commonPropDao.getAllProposalByDateZoneStatus(request.getStartDate(), request.getEndDate(), zoneid, request.getStatusId(), Long.valueOf(request.getUserId()), request.getStartIndex());
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
                count = commonPropDao.getAllProposalByDateZoneStatusCount(request.getStartDate(), request.getEndDate(), zoneid, request.getStatusId(), Long.valueOf(request.getUserId()));
//                return new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"), volist);
                break;
            case ZONE_STATUS:
                logger.info("Searching Proposal Based on Zone And Status Zone Id " + zoneid + " Status Id " + request.getStatusId());
                list = commonPropDao.getAllProposalByStatusZoneStatus(request.getStatusId(), zoneid, Long.valueOf(request.getUserId()), request.getStartIndex());
                count = commonPropDao.getAllProposalByStatusZoneStatusCount(request.getStatusId(), zoneid, Long.valueOf(request.getUserId()));
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
//                return new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"), volist);
                break;
            case USER:
                list = commonPropDao.getAllProposalByUser(request.getUserId(), request.getStartIndex());
                count = commonPropDao.getAllProposalByUserCount(request.getUserId());
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
                break;
            case USER_STATUS:
                list = commonPropDao.getAllProposalByUserStatus(request.getUserId(), request.getStatusId(), request.getStartIndex());
                count = commonPropDao.getAllProposalByUserStatusCount(request.getUserId(), request.getStatusId());
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
                break;
            case STATUS:
                list = commonPropDao.getAllProposalByStatus(request.getStatusId(), request.getStartIndex());
                count = commonPropDao.getAllProposalByStatusCount(request.getStatusId());
                volist = PromotionProposalUtil.getProposalVOList(list, request.getSearchType());
//                return new SearchProposalResp(new Resp(RespCode.SUCCESS, "success"), volist);
                break;
        }
        response.setList(volist);
        response.setTotalCount(count);
        return response;
    }

    public Resp deleteProposal(List<Long> proposalIdList) {
        for (Long proposalId : proposalIdList) {
            MstProposal proposal = proposalDao.find(proposalId);
            if (proposal == null) {
                return new Resp(RespCode.FAILURE, "Proposal Not Found with Id " + proposalId);
            }
        }
        for (Long proposalId : proposalIdList) {
            MstProposal proposal = proposalDao.find(proposalId);
            proposalDao.remove(proposal);
        }
        logger.info("--- Selected Proposals Removed Successfully.");
        return new Resp(RespCode.SUCCESS, "Selected Proposals Removed Successfully.");
    }
}
