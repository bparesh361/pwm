/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.PromoSearchType;
import com.fks.promo.init.PromotionVO;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.SearchPromoDashboardCriteria;
import com.fks.promo.init.SearchPromoDashboardEnum;
import com.fks.promo.init.SearchPromotionReq;
import com.fks.promo.init.SearchPromotionResp;
import com.fks.promo.init.UpdateTransPromoWithFileReq;
import com.fks.promotion.service.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class InitiataionDashboard extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(InitiataionDashboard.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private Object strUserID;
    CachedMapsList maps;
    private Map<String, String> eventMap, mktgMap, promotionMap, statutsMap;
    private String mstPromoId;
    private String mstSubPromoId;
    private Map<String, String> categoryMap;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate Dashboard Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.EMP_ID);
            if (strUserID != null) {                
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                return SUCCESS;
            } else {                
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion Initiate Dashboard Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private String searchType, startDate, endDate, eventSel, marketingsel, promotionSel, statusSel, categoryName;

    public void getAllPromoDetailDashboard() {
        logger.info("======= Inside getAllPromotionDetail for initiator dashboard ======");
        try {
            
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            SearchPromotionReq req = new SearchPromotionReq();
            req.setType(PromoSearchType.ALL);
            req.setUserId(new Long(empId));
            SearchPromoDashboardCriteria criteriaReq = new SearchPromoDashboardCriteria();
            if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.ALL.value())) {
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.ALL);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.DATE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.DATE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.EVENT.value())) {
                criteriaReq.setEventId(Long.valueOf(eventSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.EVENT);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.MARKETING_TYPE.value())) {
                criteriaReq.setMarketingTypeId(Long.valueOf(marketingsel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.MARKETING_TYPE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.PROMOTION_TYPE.value())) {
                criteriaReq.setPromotionTypeId(Long.valueOf(promotionSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.PROMOTION_TYPE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.DATE_EVENT.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setEventId(Long.valueOf(eventSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.DATE_EVENT);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.DATE_MARKETING_TYPE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setMarketingTypeId(Long.valueOf(marketingsel));
                
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.DATE_MARKETING_TYPE);
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.DATE_PROMOTION_TPYE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setPromotionTypeId(Long.valueOf(promotionSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.DATE_PROMOTION_TPYE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.STATUS_DATE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setStatusId(Long.valueOf(statusSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.STATUS_DATE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.STATUS_DATE_EVENT_TYPE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setEventId(Long.valueOf(eventSel));
                criteriaReq.setStatusId(Long.valueOf(statusSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.STATUS_DATE_EVENT_TYPE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.STATUS_DATE_MARKETTING_TYPE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setMarketingTypeId(Long.valueOf(marketingsel));
                criteriaReq.setStatusId(Long.valueOf(statusSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.STATUS_DATE_MARKETTING_TYPE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.STATUS_DATE_PROMO_TYPE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setPromotionTypeId(Long.valueOf(promotionSel));
                criteriaReq.setStatusId(Long.valueOf(statusSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.STATUS_DATE_PROMO_TYPE);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.STATUS_DATE_SUB_CATEGORY.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setSubCategoryName(categoryName);
                criteriaReq.setStatusId(Long.valueOf(statusSel));
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.STATUS_DATE_SUB_CATEGORY);
                
            } else if (searchType.equalsIgnoreCase(SearchPromoDashboardEnum.SUB_CATEGORY_DATE.value())) {
                criteriaReq.setEndDate(endDate);
                criteriaReq.setStartDate(startDate);
                criteriaReq.setSubCategoryName(categoryName);
                
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.SUB_CATEGORY_DATE);
            } else {
                req.setPromoDashboardEnum(SearchPromoDashboardEnum.ALL);
                
            }
            req.setPromoDashboardCriteria(criteriaReq);


            List<PromotionVO> lstSearchData = null;
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
                                
                req.setStartIdex(startPageIndex.intValue());
                SearchPromotionResp resp = ServiceMaster.getSearchPromotionService().getAllPromotionRequestdtl(req);
                
                if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    lstSearchData = resp.getList();
                    
                    totalCount = resp.getTotalCount();
                    
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                    
                }
            } else {
                lstSearchData = Collections.<PromotionVO>emptyList();
            }
            
            if (sidx.equals("reqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqId().compareToIgnoreCase(p2.getReqId());
                    }
                });
            } else if (sidx.equals("ldate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getUpdatedDate().toString().compareToIgnoreCase(p2.getUpdatedDate().toString());
                    }
                });
            } else if (sidx.equals("preqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqId().toString().compareToIgnoreCase(p2.getReqId().toString());
                    }
                });
            } else if (sidx.equals("rdate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getCreatedDate().toString().compareToIgnoreCase(p2.getCreatedDate().toString());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqName().toString().compareToIgnoreCase(p2.getReqName().toString());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getEventName().toString().compareToIgnoreCase(p2.getEventName().toString());
                    }
                });
            } else if (sidx.equals("mktgtype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getMktgName().toString().compareToIgnoreCase(p2.getMktgName().toString());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getCategory().toString().compareToIgnoreCase(p2.getCategory().toString());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getSubCategory().toString().compareToIgnoreCase(p2.getSubCategory().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getStatusName().toString().compareToIgnoreCase(p2.getStatusName().toString());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }


            //int cnt=0;
            for (PromotionVO vo : lstSearchData) {
                // cnt+=1;
                //System.out.println("counter : "+cnt);
                if (req.getType() == PromoSearchType.STATUS_USER) {
                    cellobj.put(WebConstants.ID, vo.getReqId());
                } else {
                    cellobj.put(WebConstants.ID, vo.getTransPromoId());
                }
                cell.add(vo.getReqId());
                cell.add("T" + PromotionUtil.zeroPad(Integer.parseInt(vo.getReqId()), 8));
                cell.add(vo.getTransPromoId());
                //cell.add("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getTransPromoId().toString()), 8));
                cell.add("T" + vo.getReqId() + "-R" + vo.getTransPromoId());
                cell.add(vo.getUpdatedDate());
                cell.add(vo.getCreatedDate());
                cell.add(vo.getReqName());
                if (vo.getPromoType() != null) {
                    cell.add(vo.getPromoType());
                } else {
                    cell.add("-");
                }
                cell.add(vo.getEventName());
                cell.add(vo.getMktgName());
                cell.add(vo.getCampaignName());
                cell.add(vo.getCategory());
                cell.add(vo.getSubCategory());
                cell.add(vo.getStatusName()); //-- status
                cell.add(vo.getReasonRejection());

                String downloadreq = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadMultipleExcel?transId="
                        + vo.getTransPromoId() + "&empID=" + empId + ">Download</a>";
                cell.add(downloadreq);
                cell.add(vo.getStatusID());
                String downloadreqForUpdate = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadTransPromoDtlForUpdate?id="
                        + vo.getTransPromoId() + ">Update</a>";
                cell.add(downloadreqForUpdate);
                if (vo.getErrorFilePath() != null) {
                    String errorUploadFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadfile?path=" + vo.getErrorFilePath() + ">Error</a>";
                    cell.add(errorUploadFile);
                } else {
                    cell.add("-");
                }
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
                    "Exception generated while getAllPromotionDetail : function getAllPromotionDetail() : Exception is :");
            e.printStackTrace();
        }
    }
    private String statusID;

    public String deleteSubRequest() {
        logger.info("======== Inside deleteing request =====");
        try {
            strUserID = getSessionMap().get(WebConstants.EMP_ID);
            
            Resp resp = ServiceMaster.getPromotionInitiateService().deletePromotionRequest(new Long(mstPromoId), new Long(mstSubPromoId), new Long(statusID));
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                return SUCCESS;
            } else {
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while deleteSubRequest : function deleteSubRequest() : Exception is :");
            e.printStackTrace();
            return ERROR;
        }

    }

    public String createCopyofSubRequest() {
        logger.info("======== Inside creating copy of sub req =====");
        try {
            
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            com.fks.promo.init.Resp resp = ServiceMaster.getTransPromoService().createCopyOFTransPromo(new Long(mstPromoId), new Long(mstSubPromoId), new Long(empId));
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                return SUCCESS;
            } else {
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while creating a copy of request : function createCopyofSubRequest() : Exception is :");
            e.printStackTrace();
            return ERROR;
        }

    }

    public String updateHeaderRequest() {
        logger.info("====== Inside redirecting to updateHeaderRequest ===  ");
        try {
            getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);            
            if (mstPromoId != null) {
                getSessionMap().put(WebConstants.PROMOTION_REQ_ID, mstPromoId);
                return WebConstants.REDIRECT_PROMO_HEADER;
            } else {
                return INPUT;
            }
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while updateHeaderRequest : function updateHeaderRequest() : Exception is :");
            e.printStackTrace();
            return INPUT;
        }
    }
    private File subPromoFileUpload;
    private String subPromoFileUploadName;

    public String updateSubPromoWithFileUpload() {
        logger.info("====== Inside Update SubPromo With File ===  ");
        try {
            strUserID = getSessionMap().get(WebConstants.EMP_ID);
            String subPromoFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.SUB_PROMOTION_FILE_PATH);

            Calendar cal = new GregorianCalendar();
            Long time = cal.getTimeInMillis();

            String filePath = subPromoFilePath + "/Request_" + mstSubPromoId + "_" + time.toString() + ".csv";

            File bfile = new File(filePath);
            FileUtils.copyFile(subPromoFileUpload.getAbsoluteFile(), bfile);
            logger.info("Sub Promo File For Update is copied successfully.");

            UpdateTransPromoWithFileReq req = new UpdateTransPromoWithFileReq();
            req.setTransPromoId(Long.valueOf(mstSubPromoId));
            req.setUploadFilePath(filePath);

            com.fks.promo.init.Resp resp = ServiceMaster.getTransPromoService().updateTransPromoForFileUpload(req);

            if (resp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(resp.getMsg());
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.INITIATOR_USER_SUB_CATEGORY_LIST, strUserID.toString());
                return INPUT;
            }

        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String getMstPromoId() {
        return mstPromoId;
    }

    public void setMstPromoId(String mstPromoId) {
        this.mstPromoId = mstPromoId;
    }

    public String getMstSubPromoId() {
        return mstSubPromoId;
    }

    public void setMstSubPromoId(String mstSubPromoId) {
        this.mstSubPromoId = mstSubPromoId;
    }

    public File getSubPromoFileUpload() {
        return subPromoFileUpload;
    }

    public void setSubPromoFileUpload(File subPromoFileUpload) {
        this.subPromoFileUpload = subPromoFileUpload;
    }

    public String getSubPromoFileUploadName() {
        return subPromoFileUploadName;
    }

    public void setSubPromoFileUploadName(String subPromoFileUploadName) {
        this.subPromoFileUploadName = subPromoFileUploadName;
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

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public Map<String, String> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<String, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public Map<String, String> getStatutsMap() {
        return statutsMap;
    }

    public void setStatutsMap(Map<String, String> statutsMap) {
        this.statutsMap = statutsMap;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatusSel() {
        return statusSel;
    }

    public void setStatusSel(String statusSel) {
        this.statusSel = statusSel;
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
