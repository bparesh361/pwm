/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.approval.action;

import com.fks.promo.init.ApprovalLevelDashboardSearchReq;
import com.fks.promo.init.ApprovalSearchType;
import com.fks.promo.init.SearchPromotionSearchResp;
import com.fks.promo.init.TransPromoVO;
import com.fks.promo.master.service.MstLeadTime;
import com.fks.promotion.approval.service.PromotionApprRejHoldReq;
import com.fks.promotion.approval.service.PromotionApprRejHoldRes;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.master.vo.TransPromotionUIVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nehabha
 */
public class ApprovalUtil {

    public List<TransPromotionUIVO> getAllTramsPromotionReq(String empId, String strZoneID, String storeCode, int page) {
        List<TransPromotionUIVO> list = new ArrayList<TransPromotionUIVO>();
        TransPromotionUIVO promotionUIVO = null;
        ApprovalLevelDashboardSearchReq req = new ApprovalLevelDashboardSearchReq();
        req.setEmpId(empId);
        req.setPage(page);
        req.setStorecode(storeCode);
        req.setZoneId(strZoneID);
        SearchPromotionSearchResp response = ServiceMaster.getTransPromoService().getallTransPromoforApproval(req);
        List<TransPromoVO> listPROMO = response.getTransPromoList();
        if (!listPROMO.isEmpty() && listPROMO.size() > 0) {
            for (TransPromoVO vo : listPROMO) {
                promotionUIVO = new TransPromotionUIVO();
                promotionUIVO.setTransPromoId(vo.getTransPromoId().toString());
                promotionUIVO.setDate(vo.getDate());
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
                promotionUIVO.setRemark(vo.getRemark());
                list.add(promotionUIVO);
            }
        }
        return list;
    }

    public SearchPromotionSearchResp getAllTramsPromotionReqlevel1(String empId, String strZoneID, String storeCode, int page, ApprovalSearchType searchType, String categoryName, String startDate, String endDate, boolean isL1ByPassForL2, String l2empId, String eventId, String mktgId, String promoId) {
        ApprovalLevelDashboardSearchReq req = new ApprovalLevelDashboardSearchReq();
        req.setEmpId(empId);
        req.setPage(page);
        req.setStorecode(storeCode);
        req.setZoneId(strZoneID);
        req.setType(searchType);
        if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
        } else if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE_EVENT_TYPE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setEventId(Long.valueOf(eventId));
        } else if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE_MARKETTING_TYPE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setMarketingTypeId(Long.valueOf(mktgId));
        } else if (searchType == ApprovalSearchType.ALL) {
            req.setIsL1ByPassForL2(isL1ByPassForL2);
            if (isL1ByPassForL2) {
                req.setL2EmpId(l2empId);
            }
        } else if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE_PROMO_TYPE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setPromotionTypeId(Long.valueOf(promoId));
        } else if (searchType == ApprovalSearchType.DATE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
        } else if (searchType == ApprovalSearchType.DATE_EVENT_TYPE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setEventId(Long.valueOf(eventId));
        } else if (searchType == ApprovalSearchType.DATE_MARKETTING_TYPE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setMarketingTypeId(Long.valueOf(mktgId));
        } else if (searchType == ApprovalSearchType.DATE_PROMO_TYPE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setPromotionTypeId(Long.valueOf(promoId));
        }

//        System.out.println("Category : " + categoryName);
//        System.out.println("From date : " + startDate + "=== To Date : " + endDate);
//        System.out.println("Event : " + eventId);
//        System.out.println("Mktg :" + mktgId);
//        System.out.println("Promo type : " + promoId);
//        System.out.println("Zone ID : " + strZoneID);
//        System.out.println("Emp ID : " + empId);
        SearchPromotionSearchResp response = ServiceMaster.getTransPromoService().getallTransPromoforApproval(req);
        return response;
    }

    public List<TransPromoVO> getAllTramsPromotionReqWithID(String transPromoid) {
        SearchPromotionSearchResp response = ServiceMaster.getTransPromoService().searchPromotionByTransReqId(new Long(transPromoid));
        List<TransPromoVO> listPROMO = response.getTransPromoList();
//        System.out.println("List size :" + listPROMO.size());
        return listPROMO;
    }

    public PromotionApprRejHoldRes createHoldAppRejTransPromoReq(List<PromotionApprRejHoldReq> request) throws com.fks.promotion.approval.service.Exception_Exception {
        PromotionApprRejHoldRes response = new PromotionApprRejHoldRes();
        try {
            response = ServiceMaster.getApprRejHoldPromotionReqService().createL1ApprRejHoldReqList(request);
        } catch (Exception ex) {
            Logger.getLogger(CachedMapsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public List<TransPromotionUIVO> getAllTramsPromotionReqLevel2(String empId, String strZoneID, String storeCode, int page) {
        List<TransPromotionUIVO> list = new ArrayList<TransPromotionUIVO>();
        TransPromotionUIVO promotionUIVO = null;
        ApprovalLevelDashboardSearchReq req = new ApprovalLevelDashboardSearchReq();
        req.setEmpId(empId);
        req.setPage(page);
        req.setStorecode(storeCode);
        req.setZoneId(strZoneID);
        SearchPromotionSearchResp response = ServiceMaster.getTransPromoService().getallTransPromoforApprovalforLevel2(req);
        List<TransPromoVO> listPROMO = response.getTransPromoList();
        if (!listPROMO.isEmpty() && listPROMO.size() > 0) {
            for (TransPromoVO vo : listPROMO) {
                promotionUIVO = new TransPromotionUIVO();
                promotionUIVO.setTransPromoId(vo.getTransPromoId().toString());
                promotionUIVO.setDate(vo.getDate());
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
                promotionUIVO.setRemark(vo.getRemark());
                list.add(promotionUIVO);
            }
        }
        return list;
    }

    public SearchPromotionSearchResp getAllTramsPromotionReqlevel2(String empId, String strZoneID, String storeCode, int page, ApprovalSearchType searchType, String categoryName, String startDate, String endDate, String eventId, String mktgId, String promoId) {
        ApprovalLevelDashboardSearchReq req = new ApprovalLevelDashboardSearchReq();
        req.setEmpId(empId);
        req.setPage(page);
        req.setStorecode(storeCode);
        req.setZoneId(strZoneID);
        req.setType(searchType);
//        if (searchType == ApprovalSearchType.CATEGORY) {
//            req.setCategoryName(categoryName);
//        } else if (searchType == ApprovalSearchType.DATE) {
//            req.setStartDate(startDate);
//            req.setEndDate(endDate);
//        } else if (searchType == ApprovalSearchType.EVENT) {
//            req.setEventId(Long.valueOf(eventId));
//        } else if (searchType == ApprovalSearchType.MARKETING_TYPE) {
//            req.setMarketingTypeId(Long.valueOf(mktgId));
//        } else if (searchType == ApprovalSearchType.PROMOTION_TYPE) {
//            req.setPromotionTypeId(Long.valueOf(promoId));
//        }
        if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
        } else if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE_EVENT_TYPE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setEventId(Long.valueOf(eventId));
        } else if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE_MARKETTING_TYPE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setMarketingTypeId(Long.valueOf(mktgId));
        } else if (searchType == ApprovalSearchType.SUB_CATEGORY_DATE_PROMO_TYPE) {
            req.setSubCategoryName(categoryName);
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setPromotionTypeId(Long.valueOf(promoId));
        } else if (searchType == ApprovalSearchType.DATE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
        } else if (searchType == ApprovalSearchType.DATE_EVENT_TYPE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setEventId(Long.valueOf(eventId));
        } else if (searchType == ApprovalSearchType.DATE_MARKETTING_TYPE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setMarketingTypeId(Long.valueOf(mktgId));
        } else if (searchType == ApprovalSearchType.DATE_PROMO_TYPE) {
            req.setStartDate(startDate);
            req.setEndDate(endDate);
            req.setPromotionTypeId(Long.valueOf(promoId));
        }
//        System.out.println("Category : " + categoryName);
//        System.out.println("From date : " + startDate + "=== To Date : " + endDate);
//        System.out.println("Event : " + eventId);
//        System.out.println("Mktg :" + mktgId);
//        System.out.println("Promo type : " + promoId);
        SearchPromotionSearchResp response = ServiceMaster.getTransPromoService().getallTransPromoforApprovalforLevel2(req);
        return response;
    }

    public PromotionApprRejHoldRes createHoldAppRejTransPromoReq2(List<PromotionApprRejHoldReq> request) {
        PromotionApprRejHoldRes response = new PromotionApprRejHoldRes();
        try {
            response = ServiceMaster.getApprRejHoldPromotionReqService().createL2ApprRejHoldReqList(request);
        } catch (Exception ex) {
            Logger.getLogger(CachedMapsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public SearchPromotionSearchResp getAllTramsPromotionBusinessExigency(ApprovalLevelDashboardSearchReq req) {

        SearchPromotionSearchResp response = ServiceMaster.getTransPromoService().getallTransPromoforApprovalBusinessEzigency(req);
        return response;
    }

    public PromotionApprRejHoldRes createHoldAppRejTransPromoBusinessExigency(List<PromotionApprRejHoldReq> request) {
        PromotionApprRejHoldRes response = new PromotionApprRejHoldRes();
        try {
            response = ServiceMaster.getApprRejHoldPromotionReqService().createBusinessExigencyApprRejHoldReqList(request);
        } catch (Exception ex) {
            Logger.getLogger(CachedMapsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public static boolean validatedate(String fromDate) {
        boolean dateValidated = false;
        try {
            MstLeadTime leadTime = ServiceMaster.getOtherMasterService().getMstLeadTime();
            int leadtime = leadTime.getValue();
            fromDate = PromotionUtil.getStringdate(fromDate) + " 00:00:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date validFromDate = format.parse(fromDate);

            int hoursDiff = ServiceMaster.getCommonPromotionService().getDiffHoursWithHolidays(format.format(validFromDate));
            System.out.println("---------- ****** Hours Diff : " + hoursDiff);
            if (hoursDiff < leadtime) {
                dateValidated = false;
            } else {
                dateValidated = true;
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Level1ApprovalAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateValidated;
    }
}
