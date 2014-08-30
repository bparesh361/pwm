/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.execution.action;

import com.fks.promo.init.AssignTeamReq;
import com.fks.promo.init.ExecutePromoManagerEnum;
import com.fks.promo.init.RejectTransPromoReq;
import com.fks.promo.init.Resp;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.SearchTeamMemberReq;
import com.fks.promo.init.SearchTeamMemberResp;
import com.fks.promo.init.TeamMember;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.PromoManagerApproveRejectFormVO;
import com.fks.ui.master.vo.StausVO;
import com.fks.ui.master.vo.TransPromotionUIVO;
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
 * @author ajitn
 */
public class PromoManagerAprrovalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PromoManagerAprrovalAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strEmpID, categoryName, subcategoryName;
    private Map<String, String> zoneMap;
    private PromoManagerApproveRejectFormVO pmFormVO;
    private CachedMapsList maps;
    Map<String, String> mstReasonRejectionVOs;
    private Map<String, String> eventMap, mktgMap, promotionMap;
    private List<String> fillSubCategoryList = new ArrayList<String>();
    private List<String> subcategoryMap;
    private Map<String, String> categoryMap;

    public PromoManagerApproveRejectFormVO getPmFormVO() {
        return pmFormVO;
    }

    public void setPmFormVO(PromoManagerApproveRejectFormVO pmFormVO) {
        this.pmFormVO = pmFormVO;
    }

    public Map<String, String> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(Map<String, String> zoneMap) {
        this.zoneMap = zoneMap;
    }

    public Map<String, String> getMstReasonRejectionVOs() {
        return mstReasonRejectionVOs;
    }

    public void setMstReasonRejectionVOs(Map<String, String> mstReasonRejectionVOs) {
        this.mstReasonRejectionVOs = mstReasonRejectionVOs;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promo Manager Approval Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                maps = new CachedMapsList();
                setZoneMap(CacheMaster.ZoneStoreCodeMap);
                pmFormVO = new PromoManagerApproveRejectFormVO();
                maps = new CachedMapsList();
                mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                subcategoryMap = fillSubCategoryList;
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion Level1ApprovalAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private String SearchType, startDate, endDate, eventSel, marketingsel, promotionSel;

    public void getAllTransPromotionDetail() {
        logger.info("======= Inside getAllTransPromotionDetail For Approve Reject Promo Manger. service :ServiceMaster.getTransPromoService().getExecutePromoDashBoardDtl(req) ======");
        strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();

            List<TransPromotionUIVO> lstSearchData = null;
            String rows = request.getParameter("rows");

            String pageno = request.getParameter("page");

            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");

            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            Integer pageNo = null;
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);

                lstSearchData = PromoExecutionUtil.getAllTramsPromotionExcuteDtl(strEmpID, startDate, endDate, ExecutePromoManagerEnum.APPROVE_REJECT_DATA, startPageIndex, eventSel, marketingsel, promotionSel, SearchType, categoryName, subcategoryName);

                if (lstSearchData.size() > 0 && !lstSearchData.isEmpty()) {
                    totalCount = lstSearchData.get(0).getTotalCount();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            } else {
                lstSearchData = Collections.<TransPromotionUIVO>emptyList();
            }

            if (sidx.equals("reqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getTransPromoId().toString().compareToIgnoreCase(p2.getTransPromoId().toString());
                    }
                });
            } else if (sidx.equals("ldate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getLastUpdatedDate().compareToIgnoreCase(p2.getLastUpdatedDate().toString());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("time")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getTime().toString().compareToIgnoreCase(p2.getTime().toString());
                    }
                });
            } else if (sidx.equals("initiatorName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getInitiatorName().toString().compareToIgnoreCase(p2.getInitiatorName().toString());
                    }
                });
            } else if (sidx.equals("contNo")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getContactNo().toString().compareToIgnoreCase(p2.getContactNo().toString());
                    }
                });
            } else if (sidx.equals("empCode")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getEmpCode().toString().compareToIgnoreCase(p2.getEmpCode().toString());
                    }
                });
            } else if (sidx.equals("initiatorlocation")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getInitiatorLocation().toString().compareToIgnoreCase(p2.getInitiatorLocation().toString());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRequestName().toString().compareToIgnoreCase(p2.getRequestName().toString());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getEvent().toString().compareToIgnoreCase(p2.getEvent().toString());
                    }
                });
            } else if (sidx.equals("marketingtype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getMarketingType().toString().compareToIgnoreCase(p2.getMarketingType().toString());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getCategory().toString().compareToIgnoreCase(p2.getCategory().toString());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getSubCategory().toString().compareToIgnoreCase(p2.getSubCategory().toString());
                    }
                });
            } else if (sidx.equals("promotype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getPromotionType().toString().compareToIgnoreCase(p2.getPromotionType().toString());
                    }
                });
            } else if (sidx.equals("validfrom")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getValidFrom().toString().compareToIgnoreCase(p2.getValidFrom().toString());
                    }
                });
            } else if (sidx.equals("validto")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getValidTo().toString().compareToIgnoreCase(p2.getValidTo().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            } else if (sidx.equals("remark")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("approvername")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("approvalfrom")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("teamassignfrom")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("assignto")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            }
            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }
            for (TransPromotionUIVO vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getTransPromoId());
                cell.add("T" + vo.getMstPromoId() + "-R" + vo.getTransPromoId());
                // cell.add("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getTransPromoId()), 8));
                cell.add(vo.getLastUpdatedDate());
                cell.add(vo.getDate());
                cell.add(vo.getTime());
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
                cell.add(vo.getApproverName());
                cell.add(vo.getApproverFrom());
                cell.add(vo.getTeamAssignmentToLocation());
                cell.add(vo.getTeamAssignedTO());
                cell.add(vo.getStatusId());

//                    String reject = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=promoExecuteApprReject_action?isReject=1&transPromoId="
//                            + vo.getTransPromoId() + ">Reject</a>";
//                    cell.add(reject);
                cellobj.put(WebConstants.CELL, cell);
                cell.clear();
                cellarray.add(cellobj);

            }
            responcedata.put(WebConstants.TOTAL, pageCount);
            responcedata.put(WebConstants.PAGE, pageNo);
            responcedata.put(WebConstants.RECORDS, totalCount);
            responcedata.put(WebConstants.ROWS, cellarray);
            out.print(responcedata);

        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while getAllTransPromotionDetail Execute Dashboard: function getAllTransPromotionDetail() : Exception is :");
            e.printStackTrace();
        }
    }
    private String zoneId;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public void fillTeamMembers() {
        try {
            logger.info("-------- Inside Filling Team Members. Service : getCommonPromotionService().searchTeamMember(req)");
            String storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
            SearchTeamMemberReq req = new SearchTeamMemberReq();
            if (zoneId != null && zoneId.length() > 0) {                
                req.setStoreCode(zoneId);
            } else {
                req.setStoreCode(storeCode);
            }
            SearchTeamMemberResp resp = ServiceMaster.getCommonPromotionService().searchTeamMember(req);
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
            logger.fatal("Exception generated in function fillTeamMembers() : Exceptions is :" + ex.getMessage());
        }
    }

    public String promoManagerApproveReject() {
        try {
            logger.info("------------- Inside Approve Reject Promo Manager. Service:getCommonPromotionService().rejectTransPromoList(req)  -------");
            
            strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
            
            if (pmFormVO.getStatus().equalsIgnoreCase("1")) {
                RejectTransPromoReq req = new RejectTransPromoReq();

                req.setEmpId(Long.valueOf(strEmpID));

                String[] transIds = pmFormVO.getTransPromoId().split(",");
                for (int i = 0; i < transIds.length; i++) {
                    req.getTransPromoIdList().add(Long.valueOf(transIds[i]));
                }
                req.setReasonForRejection(pmFormVO.getReasonRejection());
                req.setRejectionRemarks(pmFormVO.getManualRemarks());
                Resp rejectResp = ServiceMaster.getCommonPromotionService().rejectTransPromoList(req);
                if (rejectResp.getRespCode() == RespCode.SUCCESS) {
                    addActionMessage(rejectResp.getMsg());
                    setZoneMap(CacheMaster.ZoneStoreCodeMap);
                    pmFormVO = new PromoManagerApproveRejectFormVO();
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return SUCCESS;
                } else {

                    setZoneMap(CacheMaster.ZoneStoreCodeMap);
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    addActionError(rejectResp.getMsg());
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }
            } else {
                AssignTeamReq assignReq = new AssignTeamReq();
                String[] transIds = pmFormVO.getTransPromoId().split(",");
                for (int i = 0; i < transIds.length; i++) {
                    assignReq.getTransPromoIdList().add(Long.valueOf(transIds[i]));
                }
                assignReq.setPromoMgrId(Long.valueOf(strEmpID));                
                assignReq.setTeamMemberId(Long.valueOf(pmFormVO.getTeamMemberId()));
                Resp assignResp = ServiceMaster.getCommonPromotionService().assignTeamMemberList(assignReq);

                if (assignResp.getRespCode().equals(RespCode.SUCCESS)) {
                    addActionMessage(assignResp.getMsg());
                    setZoneMap(CacheMaster.ZoneStoreCodeMap);
                    pmFormVO = new PromoManagerApproveRejectFormVO();
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return SUCCESS;
                } else {
                    addActionError(assignResp.getMsg());
                    setZoneMap(CacheMaster.ZoneStoreCodeMap);
                    maps = new CachedMapsList();
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.fatal("Exception generated in function promoManagerApproveReject() : Exceptions is :" + ex.getMessage());
            addActionError("Error : " + ex.getMessage());
            return ERROR;
        }
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

    public String getSearchType() {
        return SearchType;
    }

    public void setSearchType(String SearchType) {
        this.SearchType = SearchType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }
}
