/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.approval.action;

import com.fks.promo.init.ApprovalLevelDashboardSearchReq;
import com.fks.promo.init.ApprovalSearchType;
import com.fks.promo.init.SearchPromotionSearchResp;
import com.fks.promo.init.TransPromoVO;
import com.fks.promotion.approval.service.PromotionApprRejHoldReq;
import com.fks.promotion.approval.service.PromotionApprRejHoldRes;
import com.fks.promotion.approval.service.PromotionStatusType;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author nehabha
 */
public class BusinessExigencyApprovalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(BusinessExigencyApprovalAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, String> mstReasonRejectionVOs;
    private CachedMapsList cachedMapsList;
    private Map<String, String> categoryMap;
    private Map<String, String> eventMap, mktgMap, promotionMap;
    private String manualRemarks;
    private ApprovalUtil maps;
    private List<String> subcategoryMap;
    private String actionName, transPromoID, rejReason;
    private String searchType, startDate, endDate, eventSel, promotionSel, marketingsel, categoryName, subcategoryName;
    private List<String> fillSubCategoryList = new ArrayList<String>();

    @Override
    public String execute() {
        logger.info("------------------ Welcome Trans Promotion Initiate BusinessExigencyApprovalAction Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {                
                cachedMapsList = new CachedMapsList();
                eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
                mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                subcategoryMap = fillSubCategoryList;
                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
                return SUCCESS;
            } else {                
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion BusinessExigencyApprovalAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    PrintWriter out;

    public void fillsubCategoryCategoryWise() {
        Object strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
        logger.info("=== inside get all subcategroy dtl catgory wise -- Emp Id =" + strUserID);
        try {
            JSONObject responseData = null;
            JSONObject cellObject = null;
            String txtcategoryname = request.getParameter("txtcategoryname").toLowerCase();            
            cachedMapsList = new CachedMapsList();
            subcategoryMap = cachedMapsList.getActiveList(MapEnum.SUB_CATEGORY, null, txtcategoryname);
            List<String> cellSubCategoryArray = new ArrayList<String>();
            cellSubCategoryArray = subcategoryMap;
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("subcategroylist", cellSubCategoryArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while fillsubCategoryCategoryWise : function fillsubCategoryCategoryWise() : Exception is :");
            e.printStackTrace();
        }
    }

    public void getAllTransPromotionDetailforBusinessExigency() {
        logger.info("======= Inside getAllTransPromotionDetailforBusinessExigency. Service : getTransPromoService().getallTransPromoforApprovalBusinessEzigency() ======");
        try {
            List<TransPromoVO> list = null;
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = response.getWriter();
            maps = new ApprovalUtil();
            String pageno = request.getParameter("page");
            String rows = request.getParameter("rows");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            Integer pageNo = null;
            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);                                

                ApprovalLevelDashboardSearchReq req = new ApprovalLevelDashboardSearchReq();

                 if (searchType == null ? ApprovalSearchType.DATE.toString() == null : searchType.equals(ApprovalSearchType.DATE.toString())) {
                    req.setStartDate(startDate);
                    req.setEndDate(endDate);
                    req.setType(ApprovalSearchType.DATE);
                } else if (searchType == null ? ApprovalSearchType.DATE_EVENT_TYPE.toString() == null : searchType.equals(ApprovalSearchType.DATE_EVENT_TYPE.toString())) {
                    req.setStartDate(startDate);
                    req.setEndDate(endDate);
                    req.setEventId(Long.valueOf(eventSel));
                    req.setType(ApprovalSearchType.DATE_EVENT_TYPE);
                } else if (searchType == null ? ApprovalSearchType.DATE_MARKETTING_TYPE.toString() == null : searchType.equals(ApprovalSearchType.DATE_MARKETTING_TYPE.toString())) {
                    req.setStartDate(startDate);
                    req.setEndDate(endDate);
                    req.setMarketingTypeId(Long.valueOf(marketingsel));
                    req.setType(ApprovalSearchType.DATE_MARKETTING_TYPE);
                } else if (searchType == null ? ApprovalSearchType.DATE_PROMO_TYPE.toString() == null : searchType.equals(ApprovalSearchType.DATE_PROMO_TYPE.toString())) {
                    req.setStartDate(startDate);
                    req.setEndDate(endDate);
                    req.setPromotionTypeId(Long.valueOf(promotionSel));
                    req.setType(ApprovalSearchType.DATE_PROMO_TYPE);
                } else if(searchType == null ? ApprovalSearchType.CATEGORY_DATE.toString() == null : searchType.equals(ApprovalSearchType.CATEGORY_DATE.toString())){
                    req.setStartDate(startDate);
                    req.setEndDate(endDate);
                     req.setSubCategoryName(categoryName);
                    req.setType(ApprovalSearchType.CATEGORY_DATE);
                    req.setCategoryName(categoryName);
                } else if(searchType == null ? ApprovalSearchType.SUB_CATEGORY_DATE.toString() == null : searchType.equals(ApprovalSearchType.SUB_CATEGORY_DATE.toString())){
                    req.setStartDate(startDate);
                    req.setEndDate(endDate);
                     req.setSubCategoryName(subcategoryName);
                    req.setType(ApprovalSearchType.SUB_CATEGORY_DATE);
                }
                else if (searchType.equalsIgnoreCase(ApprovalSearchType.ALL.value())) {
                    req.setType(ApprovalSearchType.ALL);
                } else {
                    req.setType(ApprovalSearchType.ALL);
                }
                req.setPage(Integer.parseInt(startPageIndex.toString()));

                SearchPromotionSearchResp resppromo = maps.getAllTramsPromotionBusinessExigency(req);
                
                if (resppromo.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    list = resppromo.getTransPromoList();
                    totalCount = resppromo.getTotalCount();
                    
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                    
                }
            } else {
                list = Collections.<TransPromoVO>emptyList();
            }
            
            if (sidx.equals("reqno")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getTransPromoId().toString().compareToIgnoreCase(p2.getTransPromoId().toString());
                    }
                });
            } else if (sidx.equals("ldate")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getUpdatedDate().toString().compareToIgnoreCase(p2.getUpdatedDate().toString());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("initiatorName")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getInitiatorName().toString().compareToIgnoreCase(p2.getInitiatorName().toString());
                    }
                });
            } else if (sidx.equals("contNo")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getContactNo().toString().compareToIgnoreCase(p2.getContactNo().toString());
                    }
                });
            } else if (sidx.equals("empCode")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getEmpCode().toString().compareToIgnoreCase(p2.getEmpCode().toString());
                    }
                });
            } else if (sidx.equals("initiatorlocation")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getInitiatorLocation().toString().compareToIgnoreCase(p2.getInitiatorLocation().toString());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getRequestName().toString().compareToIgnoreCase(p2.getRequestName().toString());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getEvent().toString().compareToIgnoreCase(p2.getEvent().toString());
                    }
                });
            } else if (sidx.equals("marketingtype")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getMarketingType().toString().compareToIgnoreCase(p2.getMarketingType().toString());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getCategory().toString().compareToIgnoreCase(p2.getCategory().toString());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getSubCategory().toString().compareToIgnoreCase(p2.getSubCategory().toString());
                    }
                });
            } else if (sidx.equals("promotype")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getPromotionType().toString().compareToIgnoreCase(p2.getPromotionType().toString());
                    }
                });
            } else if (sidx.equals("validfrom")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getValidFrom().toString().compareToIgnoreCase(p2.getValidFrom().toString());
                    }
                });
            } else if (sidx.equals("validto")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getValidTo().toString().compareToIgnoreCase(p2.getValidTo().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            } else if (sidx.equals("remark")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromoVO p1 = (TransPromoVO) o1;
                        TransPromoVO p2 = (TransPromoVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            }
            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(list);
            }

            if (list.size() > 0 && !list.isEmpty()) {
                for (TransPromoVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getTransPromoId());
                    //cell.add("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getTransPromoId().toString()), 8));
                    cell.add("T" + vo.getMstPromoId() + "-R" + vo.getTransPromoId());
                    cell.add(vo.getUpdatedDate());
                    cell.add(vo.getDate());
                    cell.add(vo.getInitiatorName());
                    cell.add(vo.getContactNo());
                    cell.add(vo.getEmpCode());
                    cell.add(vo.getInitiatorLocation());
                    cell.add(vo.getRequestName());
                    cell.add(vo.getEvent());
                    cell.add(vo.getMarketingType());
                    cell.add(vo.getCategory());
                    cell.add(vo.getSubCategory());
                    cell.add(vo.getPromotionType());
                    cell.add(vo.getValidFrom());
                    cell.add(vo.getValidTo());
                    cell.add(vo.getStatus());
                    cell.add(vo.getRemark());

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }

                responcedata.put(WebConstants.TOTAL, pageCount);
                responcedata.put(WebConstants.PAGE, pageNo);
                responcedata.put(WebConstants.RECORDS, totalCount);
                responcedata.put(WebConstants.ROWS, cellarray);

                out.print(responcedata);

            }
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while getAllTransPromotionDetailforBusinessExigency : function getAllTransPromotionDetailforBusinessExigency() : Exception is :");
            e.printStackTrace();
        }
    }

    public String getRejReason() {
        return rejReason;
    }

    public void setRejReason(String rejReason) {
        this.rejReason = rejReason;
    }

    public String holdAppRejTransPromoreqBusinessExigency() {
        logger.info("======= Inside holdAppRejTransPromoreqBusinessExigency business exigency. service :  getApprRejHoldPromotionReqService().createBusinessExigencyApprRejHoldReqList()======");
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();        
        List<PromotionApprRejHoldReq> reqList = new ArrayList<PromotionApprRejHoldReq>();
        maps = new ApprovalUtil();
        String[] idSplit = transPromoID.split(",");
        for (int i = 0; i < idSplit.length; i++) {
            PromotionApprRejHoldReq req = new PromotionApprRejHoldReq();
            if (actionName.equalsIgnoreCase("APPROVE")) {
                req.setIsApproved(true);
                req.setIsHold(false);
                req.setIsRejected(false);
                req.setIsChangedate(false);
                req.setStatusType(PromotionStatusType.APPROVED);
            } else if (actionName.equalsIgnoreCase("REJECT")) {                
                req.setIsRejected(true);
                req.setIsHold(false);
                req.setIsApproved(false);
                req.setIsChangedate(false);
                req.setReasonForRejection(rejReason);
                req.setRejectionRemarks(manualRemarks);
                req.setStatusType(PromotionStatusType.REJECTED);
            }
            req.setTransPromoId(new Long(idSplit[i]));
            req.setEmpId(new Long(empId));
            reqList.add(req);
        }
        try {
            PromotionApprRejHoldRes resp = maps.createHoldAppRejTransPromoBusinessExigency(reqList);            
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                cachedMapsList = new CachedMapsList();
                eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
                mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, null);
                promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);
                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
                subcategoryMap = fillSubCategoryList;
                addActionMessage(resp.getResp().getMsg());
                return SUCCESS;
            } else {
                cachedMapsList = new CachedMapsList();
                eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
                mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, null);
                promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);
                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
                subcategoryMap = fillSubCategoryList;
                addActionError(resp.getResp().getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal("Exception generated in function holdAppRejTransPromoreqBusinessExigency() : Exceptions is :" + e.getMessage());
            return ERROR;
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getManualRemarks() {
        return manualRemarks;
    }

    public void setManualRemarks(String manualRemarks) {
        this.manualRemarks = manualRemarks;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getTransPromoID() {
        return transPromoID;
    }

    public void setTransPromoID(String transPromoID) {
        this.transPromoID = transPromoID;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Map<String, String> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, String> eventMap) {
        this.eventMap = eventMap;
    }

    public Map<String, String> getMktgMap() {
        return mktgMap;
    }

    public void setMktgMap(Map<String, String> mktgMap) {
        this.mktgMap = mktgMap;
    }

    public Map<String, String> getPromotionMap() {
        return promotionMap;
    }

    public void setPromotionMap(Map<String, String> promotionMap) {
        this.promotionMap = promotionMap;
    }

    public String getEventSel() {
        return eventSel;
    }

    public void setEventSel(String eventSel) {
        this.eventSel = eventSel;
    }

    public String getMarketingsel() {
        return marketingsel;
    }

    public void setMarketingsel(String marketingsel) {
        this.marketingsel = marketingsel;
    }

    public String getPromotionSel() {
        return promotionSel;
    }

    public void setPromotionSel(String promotionSel) {
        this.promotionSel = promotionSel;
    }

    public Map<String, String> getMstReasonRejectionVOs() {
        return mstReasonRejectionVOs;
    }

    public void setMstReasonRejectionVOs(Map<String, String> mstReasonRejectionVOs) {
        this.mstReasonRejectionVOs = mstReasonRejectionVOs;
    }

    public Map<String, String> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<String, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public List<String> getSubcategoryMap() {
        return subcategoryMap;
    }

    public void setSubcategoryMap(List<String> subcategoryMap) {
        this.subcategoryMap = subcategoryMap;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }
}
