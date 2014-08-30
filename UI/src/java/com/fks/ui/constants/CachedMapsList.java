/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;

import com.fks.promo.comm.service.MstZoneVO;
import com.fks.promo.master.service.DepartmentVO;
import com.fks.promo.master.service.GetCategoryResp;
import com.fks.promo.master.service.GetCategorySubCategoryDtlReq;
import com.fks.promo.master.service.MchVo;
import com.fks.promo.master.service.MstReasonRejectionVO;
import com.fks.promo.master.service.MstTaskVO;
import com.fks.promo.master.service.UserMCSubCategoryResp;
import com.fks.promotion.service.OrganizationDtlResp;


import com.fks.ui.master.vo.EventTypeVO;
import com.fks.ui.master.vo.MktgVo;
import com.fks.ui.master.vo.ProblemTypeVO;
import com.fks.ui.master.vo.PromotionTypeVO;
import com.fks.ui.master.vo.StoreVoUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
public class CachedMapsList {
    private static final Logger logger = Logger.getLogger(CachedMapsList.class.getName());

    public Map<String, String> getActiveMap(MapEnum activeMapenum) {
        Map<String, String> activeMap = new HashMap<String, String>();
        try {
            if (activeMapenum == MapEnum.DEPARMENT) {
//                List<DepartmentVO> deptList = ServiceMaster.getOtherMasterService().getAllDepartments();
//                if (deptList != null) {
//                    for (DepartmentVO vo : deptList) {
//                        activeMap.put(vo.getDepartmentID().toString(), vo.getDepartmentName());
//                    }
//                }
                Iterator deptIterator = CacheMaster.DeptMap.entrySet().iterator();
                if (deptIterator.hasNext()) {
                    while (deptIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) deptIterator.next();
                        activeMap.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
               logger.info("----------- Department Map List : " + activeMap.size());
                return PromotionUtil.sortMapByValue(activeMap);
            }else if(activeMapenum==MapEnum.STATUS){
                Iterator statusIterator = CacheMaster.StatusMap.entrySet().iterator();
                if (statusIterator.hasNext()) {
                    while (statusIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) statusIterator.next();
                        activeMap.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                return PromotionUtil.sortMapByValue(activeMap);
            }else if(activeMapenum==MapEnum.CAMPAIGN){
                 Iterator campaignIterator = CacheMaster.CampaignMap.entrySet().iterator();
                if (campaignIterator.hasNext()) {
                    while (campaignIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) campaignIterator.next();
                        activeMap.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }               
                return PromotionUtil.sortMapByValue(activeMap);
            } else if (activeMapenum == MapEnum.PROBLEM_TYPE) {
                Iterator problemTypeIterator = CacheMaster.problemTypeMap.entrySet().iterator();
                if (problemTypeIterator.hasNext()) {
                    while (problemTypeIterator.hasNext()) {
                        Map.Entry problemTypeEntry = (Map.Entry) problemTypeIterator.next();
                        ProblemTypeVO vo = (ProblemTypeVO) problemTypeEntry.getValue();
                        if (vo.getIsBlocked() == 0) {
                            activeMap.put(vo.getProblemTypeId().toString(), vo.getProblemTypeDesc());
                        }

                    }
                }
                logger.info("----------- Problem Type Map List : " + activeMap.size());
                return PromotionUtil.sortMapByValue(activeMap);
            } else if (activeMapenum == MapEnum.PROMOTION_TYPE) {
                Iterator promotionTypeIterator = CacheMaster.promotionTypeMap.entrySet().iterator();
                if (promotionTypeIterator.hasNext()) {
                    while (promotionTypeIterator.hasNext()) {
                        Map.Entry promoTypeEntry = (Map.Entry) promotionTypeIterator.next();
                        PromotionTypeVO vo = (PromotionTypeVO) promoTypeEntry.getValue();
                        if (vo.getIsBlocked() == 0) {
                            activeMap.put(vo.getPromotionTypeID().toString(), vo.getPromotionTypeDesc());
                        }
                    }
                }
                logger.info("----------- Promotion Type Map List : " + activeMap.size());
                return PromotionUtil.sortMapByKey(activeMap);
            } else if (activeMapenum == MapEnum.EVENT) {
                Iterator EventTypeIterator = CacheMaster.EventMap.entrySet().iterator();
                if (EventTypeIterator.hasNext()) {
                    while (EventTypeIterator.hasNext()) {
                        Map.Entry EventTypeEntry = (Map.Entry) EventTypeIterator.next();
                        EventTypeVO vo = (EventTypeVO) EventTypeEntry.getValue();
                        if (vo.getIsBlocked() == 0) {
                            activeMap.put(vo.getEventId().toString(), vo.getEventName());
                        }
                    }
                }
                logger.info("----------- Event Map List : " + activeMap.size());
                return PromotionUtil.sortMapByValue(activeMap);
            } else if (activeMapenum == MapEnum.MARKETING_TYPE) {
                Iterator MarketingTypeIterator = CacheMaster.MarketingTypeMap.entrySet().iterator();
                if (MarketingTypeIterator.hasNext()) {
                    while (MarketingTypeIterator.hasNext()) {
                        Map.Entry EventTypeEntry = (Map.Entry) MarketingTypeIterator.next();
                        MktgVo vo = (MktgVo) EventTypeEntry.getValue();
                        if (vo.getIsBlocked() == 0) {
                            activeMap.put(vo.getMktgId().toString(), vo.getMktgName());
                        }
                    }
                }
                logger.info("----------- Marketting Type Map List : " + activeMap.size());
                return PromotionUtil.sortMapByValue(activeMap);
            } else if (activeMapenum == MapEnum.ZONE_TYPE) {
                Iterator ZoneIterator = CacheMaster.ZoneMap.entrySet().iterator();
                if (ZoneIterator.hasNext()) {
                    while (ZoneIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) ZoneIterator.next();
                        activeMap.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                logger.info("----------- Zone Type Map List : " + activeMap.size());
                return PromotionUtil.sortMapByValue(activeMap);
            } else if (activeMapenum == MapEnum.TASK_TYPE) {
                List<MstTaskVO> taskList = ServiceMaster.getOtherMasterService().getAllTaskMaster();
                if (taskList != null && taskList.size() > 0) {
                    for (MstTaskVO task : taskList) {
                        if (task.getIsBlocked() == 0) {
                            activeMap.put(task.getTaskId().toString(), task.getTaskName());
                        }
                    }
                }
                logger.info("----------- Task Type Map List : " + activeMap.size());
                return PromotionUtil.sortMapByValue(activeMap);
            } else if (activeMapenum == MapEnum.SET_LIST) {
                Iterator setIterator = CacheMaster.SetMap.entrySet().iterator();
                if (setIterator.hasNext()) {
                    while (setIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) setIterator.next();
                        activeMap.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                logger.info("----------- Set Map List : " + activeMap.size());
                return PromotionUtil.sortMapByKey(activeMap);
            } else if (activeMapenum == MapEnum.SET_NO_LIST) {
                Iterator setIterator = CacheMaster.SetMap.entrySet().iterator();
                if (setIterator.hasNext()) {
                    while (setIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) setIterator.next();
                        activeMap.put(entry.getKey().toString(), entry.getKey().toString());
                    }
                }
                logger.info("----------- Set NO Map List : " + activeMap.size());
                return PromotionUtil.sortMapByKey(activeMap);
            } else if (activeMapenum == MapEnum.APPROVER_REJECTION_REASON) {
                List<MstReasonRejectionVO> list = ServiceMaster.getOtherMasterService().getAllReasonForRejection("1");
                for (MstReasonRejectionVO mrrvo : list) {
                    if (mrrvo.getIsBlocked() == 0) {
                        activeMap.put(mrrvo.getReasonId().toString(), mrrvo.getReasonName());
                    }
                }
                //activeMap.put("10002", "Others");
                return activeMap;
            } else if (activeMapenum == MapEnum.HO_ZONEL_REJECTION_REASON) {
                List<MstReasonRejectionVO> list = ServiceMaster.getOtherMasterService().getAllReasonForRejection("0");
                for (MstReasonRejectionVO mrrvo : list) {
                    if (mrrvo.getIsBlocked() == 0) {
                        activeMap.put(mrrvo.getReasonId().toString(), mrrvo.getReasonName());
                    }
                }
                //activeMap.put("10001", "Others");
                 return activeMap;
            }

        } catch (Exception ex) {
            logger.info("---------------- Error In Maps :" + ex.getMessage());
            ex.printStackTrace();
        }

//        activeMap.put("-1", "---Select---");


        return PromotionUtil.sortMapByValue(activeMap);
        
    }

    public Map<String, String> getMapByUserSession(MapEnum activeMapenum, String sessionID) {
        Map<String, String> activeMap = new HashMap<String, String>();
//        activeMap.put("-1", "---Select---");

        if (activeMapenum == MapEnum.USER_DEPARTMENT) {
            List<DepartmentVO> deptList = ServiceMaster.getOtherMasterService().getUserDepartments(sessionID);
            if (deptList != null) {
                for (DepartmentVO vo : deptList) {
                    activeMap.put(vo.getDepartmentID().toString(), vo.getDepartmentName());
                }
            }
        } else if (activeMapenum == MapEnum.L1_USER_CATEGORY_LIST) {
            List<String> list = ServiceMaster.getCommonPromotionService().getCategoryListForLevel1Approver(new Long(sessionID));
            if (list != null && list.size() > 0) {
                for (String category : list) {
                    activeMap.put(category, category);
                }
            }
        } else if (activeMapenum == MapEnum.L2_USER_CATEGORY_LIST) {
            List<String> list = ServiceMaster.getCommonPromotionService().getCategoryListForLevel2Approver(new Long(sessionID));
            if (list != null && list.size() > 0) {
                for (String category : list) {
                    activeMap.put(category, category);
                }
            }
        }
        logger.info("------------ Active user Department Size : " + activeMap.size());
        return activeMap;
    }

    public Map<String, String> getCategoryMapByUserSession(MapEnum activeMapenum, String sessionID) {
        Map<String, String> activeMap = new HashMap<String, String>();

        if (activeMapenum == MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST) {
            UserMCSubCategoryResp resp = ServiceMaster.getCategoryMCHService().getUserSubcategoryList(new Long(sessionID),true, false,false);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                List<MchVo> list = resp.getSubCategoryNameList();
                if (list != null && list.size() > 0) {
                    for (MchVo category : list) {
                        activeMap.put(category.getSubCategoryName(), category.getSubCategoryName());
                    }
                }
            }

        }else if(activeMapenum == MapEnum.L1_USER_SUB_CATEGORY_LIST) {
            UserMCSubCategoryResp resp = ServiceMaster.getCategoryMCHService().getUserSubcategoryList(new Long(sessionID),false, true,false);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                List<MchVo> list = resp.getSubCategoryNameList();
                if (list != null && list.size() > 0) {
                    for (MchVo category : list) {
                        activeMap.put(category.getSubCategoryName(), category.getSubCategoryName());
                    }
                }
            }

        } else if (activeMapenum == MapEnum.L2_USER_SUB_CATEGORY_LIST) {
            UserMCSubCategoryResp resp = ServiceMaster.getCategoryMCHService().getUserSubcategoryList(new Long(sessionID), false,false,true);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                List<MchVo> list = resp.getSubCategoryNameList();
                if (list != null && list.size() > 0) {
                    for (MchVo category : list) {
                        activeMap.put(category.getSubCategoryName(), category.getSubCategoryName());
                    }
                }
            }
        } else if (activeMapenum == MapEnum.CATEGORY_LIST) {
            UserMCSubCategoryResp resp = ServiceMaster.getCategoryMCHService().getCategoryList();
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                List<MchVo> list = resp.getSubCategoryNameList();
                if (list != null && list.size() > 0) {
                    for (MchVo category : list) {
                        activeMap.put(category.getCategoryName(), category.getCategoryName());
                    }
                }
            }
        }
        logger.info("------------ Active user Department Size : " + activeMap.size());
        return PromotionUtil.sortMapByValue(activeMap);
    }

    public Map<String, String> getDept() {
        Map<String, String> activeMap = new HashMap<String, String>();
        activeMap = CacheMaster.DeptMap;
        return activeMap;
    }

    public List getActiveList(MapEnum mapEnum, Long EmpId, String cname) {
        List list = new ArrayList();

        GetCategorySubCategoryDtlReq req = new GetCategorySubCategoryDtlReq();
        req.setUserId(EmpId);
        req.setCategoryName(cname);

        if (mapEnum == mapEnum.CATEGORY_LIST) {
            GetCategoryResp resp = ServiceMaster.getCategoryMCHService().getCategoryDtl(req);
            if (resp.getResp().getRespCode().value() == null ? WebConstants.success == null : resp.getResp().getRespCode().value().equals(WebConstants.success)) {
                list = resp.getListCategory();
            }
        } else if (mapEnum == mapEnum.SUB_CATEGORY_LIST) {
            GetCategoryResp resp = ServiceMaster.getCategoryMCHService().getSubCategoryDtl(req);
            if (resp.getResp().getRespCode().value() == null ? WebConstants.success == null : resp.getResp().getRespCode().value().equals(WebConstants.success)) {
                list = resp.getListSubCategory();
            }
        }else if(mapEnum == mapEnum.SUB_CATEGORY){
            GetCategoryResp resp = ServiceMaster.getCategoryMCHService().getSubCategoryBycategory(req);
            if (resp.getResp().getRespCode().value() == null ? WebConstants.success == null : resp.getResp().getRespCode().value().equals(WebConstants.success)) {
                list = resp.getListSubCategory();
            }
        }

        Collections.sort(list);
        return list;
    }

    public List getActiveCommList(MapEnum mapEnum, String strMCorCategory) {
        List list = new ArrayList();
        if (mapEnum == mapEnum.CATEGORY_LIST) {
            list = ServiceMaster.getCommService().getAllCategoryFromMstPromo();
        } else if (mapEnum == mapEnum.MCH_LIST) {
            list = ServiceMaster.getCommService().getAllMchCategFromPromo(strMCorCategory);
        } else if (mapEnum == mapEnum.ARTICLE_LIST) {
            list = ServiceMaster.getCommService().getAllArticleMchWise(strMCorCategory);
        }else if(mapEnum == mapEnum.MC_LIST_SUB_CATEGORY){
            list = ServiceMaster.getCommService().getAllSubCategegoryMch(strMCorCategory);
        }
        Collections.sort(list);
        return list;
    }

    public Map<String, String> getZoneUserWiseForComm(String location, String zone) {
        Map<String, String> activeMap = new HashMap<String, String>();
        List<MstZoneVO> list = ServiceMaster.getCommService().getAllZoneUserWise(location, zone);
       
        if (!list.isEmpty() && list.size() > 0) {
            for (MstZoneVO vo : list) {
                activeMap.put(vo.getId().toString(), vo.getZonename());
            }
        }
        return PromotionUtil.sortMapByValue(activeMap);
    }

    public Map<String, String> getPromotionReqMap(Long EmpId) {
        Map<String, String> activeMap = new HashMap<String, String>();
        List<com.fks.promotion.service.PromotionVO> list = ServiceMaster.getPromotionInitiateService().getAllPromotionRequestdtl();
        if (!list.isEmpty() && list.size() > 0) {
            for (com.fks.promotion.service.PromotionVO vo : list) {
                //draft satatus and empid wise
                // System.out.println("Emp Id :"+EmpId +"Status Id :"+vo.getStatusID());
                if (vo.getEmpId().toString().equalsIgnoreCase(EmpId.toString())) {
                    // System.out.println("Inside Emp Id :"+vo.getEmpId());
                    if (vo.getStatusID() == 11) {
                        String strreqname;
                        strreqname = vo.getReqName().concat(" : ").concat("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getReqId()), 8));
                        activeMap.put(vo.getReqId().toString(), strreqname);

                    }
                }
            }
        }
        return PromotionUtil.sortMapByValue(activeMap);
    }

    public Map<String, String> getZoneMapBasedOnFormat(MapEnum mapEnum, String strvalue) {
        Map<String, String> activeMap = new HashMap<String, String>();
        Iterator iterator = CacheMaster.StoreVOMap.entrySet().iterator();
        if (mapEnum == mapEnum.ZONE_SITE) {
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    String storeDesc;
                    Map.Entry entry = (Map.Entry) iterator.next();
                    StoreVoUI vo = (StoreVoUI) entry.getValue();
                    if (vo.getFormat().toString().equalsIgnoreCase(strvalue)) {
                        if (vo.getLocationID().equals(WebConstants.ZONE_LOCATION)) {
                            storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                            activeMap.put(vo.getStoreID().toString(), storeDesc);
                        }
                    }
                }
            }
        } else if (mapEnum == mapEnum.ZONE_SITE_DESCRIPTION) {
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    String[] strSpilDesc = null;
                    Map.Entry entry = (Map.Entry) iterator.next();
                    StoreVoUI vo = (StoreVoUI) entry.getValue();
                    if (vo.getStoreID().toString().equalsIgnoreCase(strvalue)) {
                        String delimiter = "\\|";
                        if (vo.getStoreDesc().contains("|")) {
                            String strcode = vo.getStoreDesc().toString();
                            strSpilDesc = strcode.split(delimiter);
                            for (int i = 0; i < strSpilDesc.length; i++) {
                                activeMap.put(strSpilDesc[i], strSpilDesc[i]);
                            }
                        } else {
                            activeMap.put(vo.getStoreDesc(), vo.getStoreDesc());
                        }

                    }
                }
            }
        }
        return PromotionUtil.sortMapByValue(activeMap);
    }

    public List getActiveList(MapEnum mapEnum, List<String> listStrings) {
        List list = new ArrayList();
        if (mapEnum == mapEnum.STATE_LIST) {
            Iterator iterator = CacheMaster.StoreVOMap.entrySet().iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    StoreVoUI vo = (StoreVoUI) entry.getValue();
                    for (String cname : listStrings) {
                        if (vo.getZoneName().toString().equalsIgnoreCase(cname) && !vo.getState().equalsIgnoreCase("all")) {
                            CacheMaster.stateSet.add(vo.getState());
                        }
                    }
                }
            }
            Iterator stateIterator = CacheMaster.stateSet.iterator();
            if (stateIterator.hasNext()) {
                while (stateIterator.hasNext()) {
                    list.add(stateIterator.next().toString());
                }
            }
        } else if (mapEnum == mapEnum.FORMAT_LIST) {
            Iterator formatIterator = CacheMaster.formatSet.iterator();
            if (formatIterator.hasNext()) {
                while (formatIterator.hasNext()) {

                    list.add(formatIterator.next().toString());
                }
            }
        } else if (mapEnum == mapEnum.REGION_LIST) {
            Iterator iterator = CacheMaster.StoreVOMap.entrySet().iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    StoreVoUI vo = (StoreVoUI) entry.getValue();
                    for (String cname : listStrings) {
                        if (vo.getZoneName().equalsIgnoreCase(cname) && !vo.getRegion().equalsIgnoreCase("all")) {
                            CacheMaster.regionSet.add(vo.getRegion());
                        }
                    }
                }
            }
            Iterator regionIterator = CacheMaster.regionSet.iterator();
            if (regionIterator.hasNext()) {
                while (regionIterator.hasNext()) {
                    list.add(regionIterator.next().toString());
                }
            }
            
        } else if (mapEnum == mapEnum.CITY_lIST) {
            Iterator iterator = CacheMaster.StoreVOMap.entrySet().iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    StoreVoUI vo = (StoreVoUI) entry.getValue();
                    for (String cname : listStrings) {
                        if (vo.getState().equalsIgnoreCase(cname) && !vo.getCity().equalsIgnoreCase("all")) {
                            CacheMaster.citySet.add(vo.getCity());
                        }
                    }
                }
            }
            Iterator cityIterator = CacheMaster.citySet.iterator();
            if (cityIterator.hasNext()) {
                while (cityIterator.hasNext()) {
                    list.add(cityIterator.next().toString());
                }
            }
        } else if (mapEnum == mapEnum.FORMAT_LIST_ORG) {
            OrganizationDtlResp resp = ServiceMaster.getPromotionInitiateService().getAllFormatExceptZoneAndHo();
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                if (resp.getStateRegionCityList().size() > 0) {
                    for (String s : resp.getStateRegionCityList()) {
                        list.add(s);
                    }
                }
            }
        }
        Collections.sort(list);
        return list;
    }

//    public List<PromotionUIVO> getAllPromotionReqUserWise(String EmpId) {
//        List<PromotionUIVO> list = new ArrayList<PromotionUIVO>();
//        PromotionUIVO promotionUIVO = null;
//        List<com.fks.promotion.service.PromotionVO> listPROMO = ServiceMaster.getPromotionInitiateService().getAllPromotionRequestdtl();
//        if (!listPROMO.isEmpty() && listPROMO.size() > 0) {
//            for (com.fks.promotion.service.PromotionVO vo : listPROMO) {
//                //draft satatus and empid wise
//                //  System.out.println("Emp Id :"+EmpId +"Status Id :"+vo.getStatusID());
//                if (vo.getEmpId().toString().equalsIgnoreCase(EmpId)) {
//                    //    System.out.println("Inside Emp Id :"+vo.getEmpId());
//                    if (vo.getStatusID() == 11) {
//                        promotionUIVO = new PromotionUIVO();
//                        promotionUIVO.setCategory(vo.getCategory());
//                        promotionUIVO.setSubCategory(vo.getSubCategory());
//                        promotionUIVO.setCreatedDate(vo.getCreatedDate());
//                        promotionUIVO.setEventName(vo.getEventName());
//                        promotionUIVO.setMktgName(vo.getMktgName());
//                        promotionUIVO.setReqName(vo.getReqName());
//                        promotionUIVO.setReqId(vo.getReqId());
//                        list.add(promotionUIVO);
//                    }
//                }
//            }
//        }
//
//        return list;
//    }
    public static void main(String[] args) {
//        String strcode = "North-NCR";
//        String[] strSpilDesc = strcode.split("|");
//        System.out.println("Str : " + strSpilDesc.length);
//        for (int i = 0; i < strSpilDesc.length; i++) {
//            System.out.println(strSpilDesc[i]);
//        }
        /* String to split. */
        String str = "one|two|three";
        String[] temp;

        /* delimiter */
        String delimiter = "\\|";
        /* given string will be split by the argument delimiter provided. */
        temp = str.split(delimiter);
        /* print substrings */
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }

        /*
        IMPORTANT : Some special characters need to be escaped while providing them as
        delimiters like "." and "|".
         */

        System.out.println("");
        str = "one.two.three";
        delimiter = "\\.";
        temp = str.split(delimiter);
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }

        /*
        Using second argument in the String.split() method, we can control the maximum
        number of substrings generated by splitting a string.
         */

        System.out.println("");
        temp = str.split(delimiter, 2);
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }





    }
}
