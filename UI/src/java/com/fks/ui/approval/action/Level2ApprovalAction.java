/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.approval.action;

import com.fks.promo.init.ApprovalSearchType;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.SearchPromotionSearchResp;
import com.fks.promo.init.SearchTeamMemberReq;
import com.fks.promo.init.SearchTeamMemberResp;
import com.fks.promo.init.TeamMember;
import com.fks.promo.init.TransPromoVO;
import com.fks.promotion.approval.service.PromotionApprRejHoldReq;
import com.fks.promotion.approval.service.PromotionApprRejHoldRes;
import com.fks.promotion.approval.service.PromotionStatusType;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
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
public class Level2ApprovalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(Level2ApprovalAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strZoneID;
    ApprovalUtil maps;
    private Map<String, String> zoneMap;
    Map<String, String> mstReasonRejectionVOs;
    Map<String, String> categoryMap;
    CachedMapsList cachedMapsList;
    private String manualRemarks;
    private Map<String, String> eventMap, mktgMap, promotionMap;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate Level2ApprovalAction Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID);
            cachedMapsList = new CachedMapsList();
            mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
            categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.L2_USER_SUB_CATEGORY_LIST, strUserID.toString());
            setZoneMap(CacheMaster.ZoneStoreCodeMap);
            eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
            mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
            promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);

            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion Level2ApprovalAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private String zoneId;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public void fillL1TeamMembers() {
        try {
            logger.info("@===== Inside Filling L1 Team Members. Service : getCommonPromotionService().searchL1TeamMemberForL1ByPass()====@");
            String storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            SearchTeamMemberReq req = new SearchTeamMemberReq();
            if (zoneId != null && zoneId.trim().length() > 0) {
                req.setStoreCode(zoneId);
            } else if (storeCode.equalsIgnoreCase("901")) {
                req.setStoreCode("ALL");
            } else {
                req.setStoreCode(storeCode);
            }
            req.setL2EmpId(empId);
            SearchTeamMemberResp resp = ServiceMaster.getCommonPromotionService().searchL1TeamMemberForL1ByPass(req);
            JSONObject responseData = new JSONObject();
            JSONObject cellObject = new JSONObject();
            out = response.getWriter();
            if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
                List<TeamMember> teamList = resp.getTeamlist();
                if (teamList != null && teamList.size() > 0) {
                    List<String> cellEmpNameArray = new ArrayList<String>();
                    List<String> cellEmpIdArray = new ArrayList<String>();
                    for (TeamMember member : teamList) {
                        cellEmpNameArray.add(member.getEmpName());
                        cellEmpIdArray.add(member.getEmpId().toString());
                    }
                    cellObject.put("ListSize", teamList.size());
                    cellObject.put("empNameList", cellEmpNameArray);
                    cellObject.put("empIDList", cellEmpIdArray);
                    responseData.put("rows", cellObject);
                } else {
                    cellObject.put("ListSize", "0");
                    cellObject.put("msg", resp.getResp().getMsg());
                }

            } else {
                cellObject.put("ListSize", "0");
                cellObject.put("msg", resp.getResp().getMsg());
            }
            out.println(responseData);

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.fatal("@===Exception generated in function fillL1TeamMembers() : Exceptions is :" + ex.getMessage());
        }
    }
    private String searchType, categoryName, startDate, endDate;

    public void getAllTransPromotionDetail2() {
        logger.info("2======= Inside getAllTransPromotionDetail level2. service :getTransPromoService().getallTransPromoforApprovalforLevel2() ======@");
        strZoneID = getSessionMap().get(WebConstants.ZONE_ID).toString();
        String storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            maps = new ApprovalUtil();
            String pageno = request.getParameter("page");
            String rows = request.getParameter("rows");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            Integer pageNo = null;
            float totalCount = 0;
            double pageCount = 1;
            List<TransPromoVO> list = null;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);

                ApprovalSearchType searchTypeEnum = null;

                logger.info("Search Type for L2 :" + searchType);
                if (searchType.equalsIgnoreCase("ALL")) {

                    searchTypeEnum = ApprovalSearchType.ALL;
                } else if (searchType.equalsIgnoreCase("DATE")) {
                    searchTypeEnum = ApprovalSearchType.DATE;

                } else if (searchType.equalsIgnoreCase("STATUS")) {
                    searchTypeEnum = ApprovalSearchType.STATUS;

                } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
                    searchTypeEnum = ApprovalSearchType.SUB_CATEGORY_DATE;

                } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.SUB_CATEGORY_DATE_EVENT_TYPE;

                } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.SUB_CATEGORY_DATE_MARKETTING_TYPE;

                } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.SUB_CATEGORY_DATE_PROMO_TYPE;

                } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.DATE_EVENT_TYPE;

                } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.DATE_MARKETTING_TYPE;

                } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.DATE_PROMO_TYPE;

                } else {

                    searchTypeEnum = ApprovalSearchType.ALL;
                }
                SearchPromotionSearchResp resppromo = maps.getAllTramsPromotionReqlevel2(empId, strZoneID, storeCode, startPageIndex.intValue(), searchTypeEnum, categoryName, startDate, endDate, eventSel, marketingsel, promotionSel);

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
                    cell.add("T" + vo.getMstPromoId().toString() + "-R" + vo.getTransPromoId().toString());
                    cell.add(vo.getUpdatedDate());
                    cell.add(vo.getDate());
                    cell.add(vo.getInitiatorName());
                    cell.add(vo.getContactNo());
                    cell.add(vo.getEmpCode());
                    cell.add(vo.getInitiatorLocation());
                    cell.add(vo.getRequestName());
                    cell.add(vo.getEvent());
                    cell.add(vo.getMarketingType());
                    cell.add(vo.getCampaignName());
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
                    "Exception generated while getAllTransPromotionDetail : function getAllTransPromotionDetail() : Exception is :");
            e.printStackTrace();
        }
    }
    private String l1EmpId, eventSel, promotionSel, marketingsel;

    public void getAllL1ByPassTransPromotionDetail() {
        logger.info("2======= Inside getAll L1 By Pass TransPromotionDetail. Service : getTransPromoService().getallTransPromoforApproval(req)======@");
        strZoneID = getSessionMap().get(WebConstants.ZONE_ID).toString();
        String storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
        String l2empId = getSessionMap().get(WebConstants.EMP_ID).toString();

        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            maps = new ApprovalUtil();
            String pageno = request.getParameter("page");
            String rows = request.getParameter("rows");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            Integer pageNo = null;
            float totalCount = 0;
            double pageCount = 1;
            List<TransPromoVO> list = null;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);

                ApprovalSearchType searchTypeEnum = null;
                if (searchType.equalsIgnoreCase("ALL")) {

                    searchTypeEnum = ApprovalSearchType.ALL;
                } else if (searchType.equalsIgnoreCase("STATUS")) {
                    searchTypeEnum = ApprovalSearchType.STATUS;

                } else if (searchType.equalsIgnoreCase("CATEGORY")) {
                    searchTypeEnum = ApprovalSearchType.CATEGORY;

                } else if (searchType.equalsIgnoreCase("DATE")) {
                    searchTypeEnum = ApprovalSearchType.DATE;

                } else if (searchType.equalsIgnoreCase("EVENT")) {
                    searchTypeEnum = ApprovalSearchType.EVENT;

                } else if (searchType.equalsIgnoreCase("MARKETING_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.MARKETING_TYPE;

                } else if (searchType.equalsIgnoreCase("PROMOTION_TYPE")) {
                    searchTypeEnum = ApprovalSearchType.PROMOTION_TYPE;

                } else {

                    searchTypeEnum = ApprovalSearchType.ALL;
                }
                SearchPromotionSearchResp resppromo = maps.getAllTramsPromotionReqlevel1(l1EmpId, strZoneID, storeCode, startPageIndex.intValue(), searchTypeEnum, categoryName, startDate, endDate, true, l2empId, eventSel, marketingsel, promotionSel);

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
                    cell.add("T" + vo.getMstPromoId().toString() + "-R" + vo.getTransPromoId().toString());
                    cell.add(vo.getUpdatedDate());
                    cell.add(vo.getDate());
                    cell.add(vo.getInitiatorName());
                    cell.add(vo.getContactNo());
                    cell.add(vo.getEmpCode());
                    cell.add(vo.getInitiatorLocation());
                    cell.add(vo.getRequestName());
                    cell.add(vo.getEvent());
                    cell.add(vo.getMarketingType());
                    cell.add(vo.getCampaignName());
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
                    "Exception generated while getAllTransPromotionDetail : function getAllTransPromotionDetail() : Exception is :");
            e.printStackTrace();
        }
    }
    private String actionName, transPromoID, rejReason;

    public String holdAppRejTransPromoreq2() {
        logger.info("@======= Inside holdAppRejTransPromoreq2 method. service :getApprRejHoldPromotionReqService().createL2ApprRejHoldReqList(); ======@");
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
        maps = new ApprovalUtil();

        List<PromotionApprRejHoldReq> reqList = new ArrayList<PromotionApprRejHoldReq>();
        maps = new ApprovalUtil();
        String[] idSplit = transPromoID.split(",");
        for (int i = 0; i < idSplit.length; i++) {
            PromotionApprRejHoldReq req = new PromotionApprRejHoldReq();
            if (actionName.equalsIgnoreCase("HOLD")) {
                req.setIsHold(true);
                req.setIsApproved(false);
                req.setIsRejected(false);
                req.setIsChangedate(false);
                req.setStatusType(PromotionStatusType.HOLD);
            } else if (actionName.equalsIgnoreCase("APPROVE")) {
                req.setIsApproved(true);
                req.setIsHold(false);
                req.setIsRejected(false);
                req.setIsChangedate(false);
                req.setStatusType(PromotionStatusType.APPROVED);
            } else if (actionName.equalsIgnoreCase("REJECT")) {
                String reson = request.getParameter("rejReason");
                req.setIsRejected(true);
                req.setIsHold(false);
                req.setIsApproved(false);
                req.setIsChangedate(false);
                req.setReasonForRejection(reson);
                req.setRejectionRemarks(manualRemarks);
                req.setStatusType(PromotionStatusType.REJECTED);
            } else if (actionName.equalsIgnoreCase("BUSSUNESSEXIGENCY")) {
                req.setIsRejected(false);
                req.setIsHold(false);
                req.setIsApproved(false);
                req.setIsChangedate(false);
                req.setStatusType(PromotionStatusType.BUSINESSEXIGENCY);
            } else if (actionName.equalsIgnoreCase("CHANGEDATE")) {
                String fromDate = request.getParameter("startDate");
                String todate = request.getParameter("endDate");
                req.setIsRejected(false);
                req.setIsHold(false);
                req.setIsApproved(false);
                req.setIsChangedate(true);
                req.setFromDate(PromotionUtil.getStringdate(fromDate));
                req.setToDate(PromotionUtil.getStringdate(todate));
                req.setStatusType(PromotionStatusType.CHANGEDATE);
            }
            req.setTransPromoId(new Long(idSplit[i]));
            req.setEmpId(new Long(empId));
            reqList.add(req);
        }
        try {
            PromotionApprRejHoldRes resp = maps.createHoldAppRejTransPromoReq2(reqList);


            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                cachedMapsList = new CachedMapsList();
                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.L2_USER_SUB_CATEGORY_LIST, empId.toString());
                setZoneMap(CacheMaster.ZoneStoreCodeMap);
                eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
                mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);
                addActionMessage(resp.getResp().getMsg());
                return SUCCESS;
            } else {
                cachedMapsList = new CachedMapsList();
                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.L2_USER_SUB_CATEGORY_LIST, empId.toString());
                setZoneMap(CacheMaster.ZoneStoreCodeMap);
                eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
                mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);
                String msg = resp.getResp().getMsg();
                if (msg.contains("<br/>")) {
                    msg = msg.replace("<br/>", "");
                }
                addActionError(msg);
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal("Exception generated in function holdAppRejTransPromoreq2() : Exceptions is :" + e.getMessage());
            return ERROR;
        }
    }

    public void approveRejectHoldSingleL2Request() {
        logger.info("======= Inside Single L2 Approve/Hold/Reject. Service :getApprRejHoldPromotionReqService().createL2ApprRejHoldReq(req) ======@");
        String transPromoid = request.getParameter("id");
        String status = request.getParameter("status");
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();

        PromotionApprRejHoldReq req = new PromotionApprRejHoldReq();
        maps = new ApprovalUtil();
        boolean isValidateDate = true;
        if (status.equalsIgnoreCase("HOLD")) {
            req.setIsHold(true);
            req.setIsApproved(false);
            req.setIsRejected(false);
            req.setIsChangedate(false);
            req.setStatusType(PromotionStatusType.HOLD);
        } else if (status.equalsIgnoreCase("APPROVE")) {
            req.setIsApproved(true);
            req.setIsHold(false);
            req.setIsRejected(false);
            req.setIsChangedate(false);
            req.setStatusType(PromotionStatusType.APPROVED);
        } else if (status.equalsIgnoreCase("REJECT")) {
            String rejReason = request.getParameter("rejReason");
            req.setIsRejected(true);
            req.setIsHold(false);
            req.setIsApproved(false);
            req.setIsChangedate(false);
            req.setReasonForRejection(rejReason);
            req.setRejectionRemarks(manualRemarks);
            req.setStatusType(PromotionStatusType.REJECTED);
        } else if (status.equalsIgnoreCase("BUSSUNESSEXIGENCY")) {
            req.setIsRejected(false);
            req.setIsHold(false);
            req.setIsApproved(false);
            req.setIsChangedate(false);
            req.setStatusType(PromotionStatusType.BUSINESSEXIGENCY);
        } else if (status.equalsIgnoreCase("CHANGEDATE")) {
            String fromDate = request.getParameter("startDate");
            String todate = request.getParameter("endDate");
            req.setIsRejected(false);
            req.setIsHold(false);
            req.setIsApproved(false);
            req.setIsChangedate(true);
            req.setFromDate(PromotionUtil.getStringdate(fromDate));
            req.setToDate(PromotionUtil.getStringdate(todate));
            req.setStatusType(PromotionStatusType.CHANGEDATE);

            isValidateDate = ApprovalUtil.validatedate(fromDate);
        }
        req.setTransPromoId(new Long(transPromoid));
        req.setEmpId(new Long(empId));
        JSONObject responseData = new JSONObject();
        try {
            out = response.getWriter();
            if (isValidateDate) {
                PromotionApprRejHoldRes resp = ServiceMaster.getApprRejHoldPromotionReqService().createL2ApprRejHoldReq(req);


                if (resp.getResp().getRespCode().equals(RespCode.SUCCESS)) {
                    responseData.put("status", "SUCCESS");
                    responseData.put("message", resp.getResp().getMsg());
                } else {
                    responseData.put("status", "FAILURE");
                    responseData.put("message", resp.getResp().getMsg());

                    if (resp.isIsLeadTimeFailed() != null && resp.isIsLeadTimeFailed()) {
                        responseData.put("leadTimeFailed", "true");
                        responseData.put("suggesteDate", resp.getSuggestedTime());
                    } else {
                        responseData.put("leadTimeFailed", "false");
                    }
                }
            } else {
                responseData.put("leadTimeFailed", "true");
                responseData.put("status", "FAILURE");
                responseData.put("message", "Please select proper startdate. ");
            }
            out.println(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal("Exception generated in function approveRejectHoldSingleL2Request() : Exceptions is :" + e.getMessage());
        }
    }

    public String getRejReason() {
        return rejReason;
    }

    public void setRejReason(String rejReason) {
        this.rejReason = rejReason;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getL1EmpId() {
        return l1EmpId;
    }

    public void setL1EmpId(String l1EmpId) {
        this.l1EmpId = l1EmpId;
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

    public String getManualRemarks() {
        return manualRemarks;
    }

    public void setManualRemarks(String manualRemarks) {
        this.manualRemarks = manualRemarks;
    }

    public ApprovalUtil getMaps() {
        return maps;
    }

    public void setMaps(ApprovalUtil maps) {
        this.maps = maps;
    }

    public String getStrZoneID() {
        return strZoneID;
    }

    public void setStrZoneID(String strZoneID) {
        this.strZoneID = strZoneID;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    public Map<String, String> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(Map<String, String> zoneMap) {
        this.zoneMap = zoneMap;
    }
}
