/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.execution.action;

import com.fks.promo.init.ExecutePromoDashBaordReq;
import com.fks.promo.init.ExecutePromoDashBoardResp;
import com.fks.promo.init.ExecutePromoManagerEnum;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.SearchPromoDashboardCriteria;
import com.fks.promo.init.SearchPromoDashboardEnum;
import com.fks.promo.init.SearchTeamMemberReq;
import com.fks.promo.init.SearchTeamMemberResp;
import com.fks.promo.init.TeamMember;
import com.fks.promo.init.TransPromoVO;
import com.fks.promo.task.FilePromoCloseResp;
import com.fks.promo.task.Resp;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.form.vo.PromoSetupFormVO;
import com.fks.ui.master.vo.TransPromotionUIVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ajitn
 */
public class PromoExecutionUtil {

    // Category and Sub category Name added on 31-5-13
    public static List<TransPromotionUIVO> getAllTramsPromotionExcuteDtl(String empId, String startDate, String endDate, ExecutePromoManagerEnum executeEnum, Long startIndex, String eventId, String mktgId, String promoId, String searchType, String categoryName, String subCategoryName) {
        List<TransPromotionUIVO> list = new ArrayList<TransPromotionUIVO>();
        TransPromotionUIVO promotionUIVO = null;
        ExecutePromoDashBaordReq req = new ExecutePromoDashBaordReq();
        SearchPromoDashboardEnum dashboardEnum = null;
        SearchPromoDashboardCriteria criteriaReq = new SearchPromoDashboardCriteria();
        req.setEmpID(empId);
        req.setStartDate(startDate);
        req.setEndDate(endDate);
        req.setExecuteEnum(executeEnum);
        //DATE_EVENT_TYPE
        if (executeEnum == ExecutePromoManagerEnum.APPROVE_REJECT_DATA || executeEnum == ExecutePromoManagerEnum.TEAM_MEMBER) {
            if (searchType.equalsIgnoreCase("ALL")) {                
                dashboardEnum = SearchPromoDashboardEnum.ALL;
            } else if (searchType.equalsIgnoreCase("DATE")) {
                dashboardEnum = SearchPromoDashboardEnum.DATE;
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
            } else if (searchType.equalsIgnoreCase("EVENT")) {
                dashboardEnum = SearchPromoDashboardEnum.EVENT;
                criteriaReq.setEventId(Long.valueOf(eventId));
            } else if (searchType.equalsIgnoreCase("PROMOTION_TYPE")) {
                dashboardEnum = SearchPromoDashboardEnum.PROMOTION_TYPE;
                criteriaReq.setPromotionTypeId(Long.valueOf(promoId));
            } else if (searchType.equalsIgnoreCase("MARKETING_TYPE")) {
                dashboardEnum = SearchPromoDashboardEnum.MARKETING_TYPE;
                criteriaReq.setMarketingTypeId(Long.valueOf(mktgId));
            } else if (searchType.equalsIgnoreCase("DATE_EVENT")) {                
                dashboardEnum = SearchPromoDashboardEnum.DATE_EVENT;
                criteriaReq.setEventId(Long.valueOf(eventId));
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
            } else if (searchType.equalsIgnoreCase("DATE_MARKETING_TYPE")) {
                dashboardEnum = SearchPromoDashboardEnum.DATE_MARKETING_TYPE;
                criteriaReq.setMarketingTypeId(Long.valueOf(mktgId));
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
            } else if (searchType.equalsIgnoreCase("DATE_PROMOTION_TPYE")) {
                dashboardEnum = SearchPromoDashboardEnum.DATE_PROMOTION_TPYE;
                criteriaReq.setPromotionTypeId(Long.valueOf(promoId));
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
            } else if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
                dashboardEnum = SearchPromoDashboardEnum.CATEGORY_DATE;
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setSubCategoryName(categoryName);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setSubCategoryName(subCategoryName);
                dashboardEnum = SearchPromoDashboardEnum.SUB_CATEGORY_DATE;
            } else {
                dashboardEnum = SearchPromoDashboardEnum.ALL;                
            }
        }

        req.setPromoDashboardEnum(dashboardEnum);
        req.setPromoDashboardCriteria(criteriaReq);

        req.setStartIndex(Integer.parseInt(startIndex.toString()));

        ExecutePromoDashBoardResp response = ServiceMaster.getTransPromoService().getExecutePromoDashBoardDtl(req);
        List<TransPromoVO> listPROMO = response.getTransPromoList();
        // System.out.println("--------- Promo List : " + listPROMO);
        if (!listPROMO.isEmpty() && listPROMO.size() > 0) {
            //System.out.println("--------- Promo List : " + listPROMO.size());
            for (TransPromoVO vo : listPROMO) {
                promotionUIVO = new TransPromotionUIVO();
//                System.out.println("------------- ID : " + vo.getTransPromoId());
                promotionUIVO.setTransPromoId(vo.getTransPromoId().toString());
                promotionUIVO.setDate(vo.getDate());
                promotionUIVO.setTime(vo.getTime());
                promotionUIVO.setInitiatorName(vo.getInitiatorName());
                promotionUIVO.setContactNo(vo.getContactNo());
                promotionUIVO.setEmpCode(vo.getEmpCode());
                promotionUIVO.setInitiatorLocation(vo.getInitiatorLocation());
                promotionUIVO.setRequestName(vo.getRequestName());
                promotionUIVO.setEvent(vo.getEvent());
                promotionUIVO.setMarketingType(vo.getMarketingType());
                promotionUIVO.setCategory(vo.getCategory());
                promotionUIVO.setSubCategory(vo.getSubCategory());
                promotionUIVO.setPromotionType(vo.getPromotionType());
                promotionUIVO.setValidFrom(vo.getValidFrom());
                promotionUIVO.setValidTo(vo.getValidTo());
                promotionUIVO.setStatus(vo.getStatus());
                promotionUIVO.setStatusId(vo.getStatusId());
                promotionUIVO.setTeamAssignedDate(vo.getTeamMemberassigneDate());
                promotionUIVO.setLastUpdatedDate(vo.getUpdatedDate());

                promotionUIVO.setRemark(vo.getRemark());
                promotionUIVO.setMstPromoId(vo.getMstPromoId().toString());
                if (vo.getApproverFrom() != null) {
                    promotionUIVO.setApproverFrom(vo.getApproverFrom());
                } else {
                    promotionUIVO.setApproverFrom("-");
                }
                if (vo.getApproverName() != null) {
                    promotionUIVO.setApproverName(vo.getApproverName());
                } else {
                    promotionUIVO.setApproverName("-");
                }
                if (vo.getTeamAssignedBy() != null) {
                    promotionUIVO.setTeamAssignedBy(vo.getTeamAssignedBy());
                } else {
                    promotionUIVO.setTeamAssignedBy("-");
                }
                if (vo.getTeamAssignmentLocation() != null) {
                    promotionUIVO.setTeamAssignmentLocation(vo.getTeamAssignmentLocation());
                } else {
                    promotionUIVO.setTeamAssignmentLocation("-");
                }
                if (vo.getTeamAssignedToLocation() != null) {
                    promotionUIVO.setTeamAssignmentToLocation(vo.getTeamAssignedToLocation());
                } else {
                    promotionUIVO.setTeamAssignmentToLocation("-");
                }
                if (vo.getTeamAssignedTo() != null) {
                    promotionUIVO.setTeamAssignedTO(vo.getTeamAssignedTo());
                } else {
                    promotionUIVO.setTeamAssignedTO("-");
                }
                promotionUIVO.setTotalCount(response.getTotalCount());
                list.add(promotionUIVO);
            }
        }
        //System.out.println("----- List Size : " + list.size());
        return list;
    }

    public static Map<String, String> getTeamMemberList(String storeCode) {
        SearchTeamMemberReq req = new SearchTeamMemberReq();
        req.setStoreCode(storeCode);
        SearchTeamMemberResp resp = ServiceMaster.getCommonPromotionService().searchTeamMember(req);
        Map<String, String> teamMap = new HashMap<String, String>();
        if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
            if (resp.getTeamlist() != null && resp.getTeamlist().size() > 0) {
                for (TeamMember member : resp.getTeamlist()) {
                    teamMap.put(member.getEmpId().toString(), member.getEmpName());
                }
            }
        }
        return teamMap;
    }

    public static Resp submitPromoSetup(PromoSetupFormVO formVO, String empID, String filepath) {
        
        String[] transIds = formVO.getTrnsPromoId().split(",");
        List<Long> longTransId = new ArrayList<Long>();
        for (int i = 0; i < transIds.length; i++) {
            longTransId.add(Long.valueOf(transIds[i]));
        }
        return ServiceMaster.getTaskService().statusChangeTransPromoByTeamMemberList(longTransId, formVO.getRemarks(), formVO.getBonusBuy(), formVO.getPromoDtl(), formVO.getCashierTrigger(), formVO.getStatus(), Long.valueOf(empID), formVO.getReasonRejection(), filepath, formVO.getRemarks());
    }

    public static FilePromoCloseResp submitPromoSetUpUsingFile(String filePath, String empID){        
        return ServiceMaster.getTaskService().closePromotionByFile(filePath, Long.valueOf(empID));
    }
    
}
