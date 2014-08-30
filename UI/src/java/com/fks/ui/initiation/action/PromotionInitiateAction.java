/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.PromoSearchType;
import com.fks.promo.init.PromotionVO;
import com.fks.promo.init.SearchPromotionReq;

import com.fks.promo.init.SearchPromotionResp;
import com.fks.promotion.service.GetPromotionReqResponse;

import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.PromoInitiationFormVo;
import com.fks.ui.master.vo.StausVO;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
 * @author krutij
 */
public class PromotionInitiateAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PromotionInitiateAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    CachedMapsList maps;
    private Map<String, String> eventMap, mktgMap, promotionMap, FillPromotionMap, campaignMap, vendorbackingMap;
    private List<String> CategoryList, SubCategoryList;
    private List<String> fillSubCategoryList = new ArrayList<String>();
    private PromoInitiationFormVo formVo;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate Page Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();

            if (strUserID != null) {

                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                CategoryList = maps.getActiveList(MapEnum.CATEGORY_LIST, new Long(strUserID), null);
                SubCategoryList = fillSubCategoryList;
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                campaignMap = maps.getActiveMap(MapEnum.CAMPAIGN);
                vendorbackingMap = new HashMap<String, String>();
                vendorbackingMap.put("0", "NO");
                vendorbackingMap.put("1", "YES");
                formVo = new PromoInitiationFormVo();
                Object sPromoId = getSessionMap().get(WebConstants.PROMOTION_REQ_ID);
                if (sPromoId != null) {
                    formVo.setSessionmstPromoId(sPromoId.toString());
                    formVo.setIsSubmit("0");
                } else {
                    formVo.setIsSubmit("1");
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                }
                Object newpromo = getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion Initiate Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void fillCategoryData() {
        strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
        logger.info("=== inside fillCategoryData Emp Id =" + strUserID);
        try {
            JSONObject responseData = null;
            JSONObject cellObject = null;

            maps = new CachedMapsList();
            CategoryList = maps.getActiveList(MapEnum.CATEGORY_LIST, new Long(strUserID), null);
            List<String> cellCategoryArray = new ArrayList<String>();
            cellCategoryArray = CategoryList;
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();

            cellObject.put("categroylist", cellCategoryArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while fillCategoryData : function fillCategoryData() : Exception is :");
            e.printStackTrace();
        }
    }

    public String submitPromotionInitiation() {
        strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
        logger.info("Inside Creating or Updating Promotion Initation Req. User : " + strUserID);
        logger.info(" --- promotion vo Vendor Category --- " + formVo); 
        try {
            com.fks.promotion.service.PromotionVO promotionVO = new com.fks.promotion.service.PromotionVO();            
            promotionVO.setCategory(formVo.getHdncategory());

            promotionVO.setCategorySub(formVo.getToCategoryLB());
            promotionVO.setEmpId(new Long(strUserID));
            promotionVO.setEventId(new Long(formVo.getEventSel()));
            promotionVO.setMktgId(new Long(formVo.getMarketingSel()));
            promotionVO.setRemarks(formVo.getTxtremarks());
            promotionVO.setReqName(formVo.getTxtRequestName());
            promotionVO.setSubCategory(formVo.getHdnSubCategory());
            promotionVO.setCampaignId(Long.valueOf(formVo.getCampaignSel()));

            promotionVO.setSubCategorySub(formVo.getToSubCategoryLB());
            promotionVO.setVendorBacked(formVo.getVendorbackingsel());

            if (!formVo.getTxtReqId().isEmpty() && formVo.getTxtReqId() != null) {
                promotionVO.setOperationCode(2);
                promotionVO.setReqId(formVo.getTxtReqId());
            } else {
                promotionVO.setOperationCode(1);
            }

            com.fks.promotion.service.Resp resp = ServiceMaster.getPromotionInitiateService().submitPromotionDetail(promotionVO);
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());

                formVo.setIsSubmit("1");
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                CategoryList = maps.getActiveList(MapEnum.CATEGORY_LIST, new Long(strUserID), null);
                SubCategoryList = fillSubCategoryList;
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                campaignMap = maps.getActiveMap(MapEnum.CAMPAIGN);
                formVo = new PromoInitiationFormVo();
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                CategoryList = maps.getActiveList(MapEnum.CATEGORY_LIST, new Long(strUserID), null);
                SubCategoryList = fillSubCategoryList;
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                campaignMap = maps.getActiveMap(MapEnum.CAMPAIGN);
                return INPUT;
            }
        } catch (Exception e) {
            logger.info("=== Exception in submitPromotionInitiation ");
            e.printStackTrace();
            addActionError("Exception : " + e.getMessage());
            return ERROR;
        }

    }

    public void getPromotionDtlPromoIDWise() {
        logger.info("======== getPromotionDtlPromoIDWise =======");
        JSONObject responseData = null;
        try {
            String selpromoId = request.getParameter("selpromoId").toLowerCase();
            GetPromotionReqResponse resp = ServiceMaster.getPromotionInitiateService().getPromoReqDtlPromoIDwise(new Long(selpromoId));
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                out = response.getWriter();
                responseData = new JSONObject();
                responseData.put("category", resp.getPromotionVO().getCategory().trim());
                responseData.put("subcategory", resp.getPromotionVO().getSubCategory());
                responseData.put("event", resp.getPromotionVO().getEventId());
                responseData.put("mktg", resp.getPromotionVO().getMktgId());
                responseData.put("remarks", resp.getPromotionVO().getRemarks());
                responseData.put("reqName", resp.getPromotionVO().getReqName());
                responseData.put("campaignData", resp.getPromotionVO().getCampaignId());
            }

            out.println(responseData);
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while getPromotionDtlPromoIDWise : function getPromotionDtlPromoIDWise() : Exception is :");
            e.printStackTrace();
        }
    }

    public void fillsubCategoryCategoryWise() {
        strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
        logger.info("=== inside get all subcategroy dtl catgory wise -- Emp Id =" + strUserID);
        try {
            JSONObject responseData = null;
            JSONObject cellObject = null;

            String txtcategoryname = request.getParameter("txtcategoryname").toLowerCase();
            maps = new CachedMapsList();
            SubCategoryList = maps.getActiveList(MapEnum.SUB_CATEGORY_LIST, new Long(strUserID), txtcategoryname);
            List<String> cellSubCategoryArray = new ArrayList<String>();
            cellSubCategoryArray = SubCategoryList;
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

    public void fillAllPromotionUserWise() {
        logger.info("Inside fillAllPromotionUserWise to Autocomplete box search...");
        try {
            JSONObject responseData = null;
            JSONObject cellObject = null;
            JSONArray cellArray = null;

            String txtExistingReq = request.getParameter("txtExistingReq").toLowerCase();

            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            maps = new CachedMapsList();
            FillPromotionMap = maps.getPromotionReqMap(new Long(strUserID));

            out = response.getWriter();
            cellArray = new JSONArray();
            responseData = new JSONObject();
            Iterator projectiterate = FillPromotionMap.entrySet().iterator();
            if (projectiterate.hasNext()) {
                while (projectiterate.hasNext()) {
                    Map.Entry entry = (Map.Entry) projectiterate.next();
                    if (entry.getValue().toString().toLowerCase().startsWith(txtExistingReq)) {

                        cellObject = new JSONObject();
                        cellObject.put("id", entry.getKey());
                        cellObject.put("name", entry.getValue());
                        cellArray.add(cellObject);
                    }
                }
            }

            responseData.put("results", cellArray);
            out.println(responseData);
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while fillAllPromotionUserWise : function fillAllPromotionUserWise() : Exception is :");
            e.printStackTrace();
        }
    }

    public void getAllPromotionDetail() {
        logger.info("======= Inside getAllPromotionDetail ======");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            SearchPromotionReq req = new SearchPromotionReq();
            req.setType(PromoSearchType.STATUS_USER);
            req.setUserId(new Long(empId));
            req.setStatusId(11l);

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
            } else if (sidx.equals("preqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqId().compareToIgnoreCase(p2.getReqId());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getCreatedDate().compareToIgnoreCase(p2.getCreatedDate());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqName().compareToIgnoreCase(p2.getReqName());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getEventName().compareToIgnoreCase(p2.getEventName());
                    }
                });
            } else if (sidx.equals("mktgtype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getMktgName().compareToIgnoreCase(p2.getMktgName());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getCategory().compareToIgnoreCase(p2.getCategory());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getSubCategory().compareToIgnoreCase(p2.getSubCategory());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getStatusName().compareToIgnoreCase(p2.getStatusName());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }

            for (PromotionVO vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getReqId());
                cell.add("T" + vo.getReqId());
                cell.add(vo.getCreatedDate());
                cell.add(vo.getReqName());
                cell.add(vo.getEventName());
                cell.add(vo.getMktgName());
                cell.add(vo.getCategory());
                cell.add(vo.getSubCategory());
                if(vo.getUploadFilePath()!=null) {
                    String uploadFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadExcelfile?path=" + vo.getUploadFilePath() + ">Uploaded</a>";
                    cell.add(uploadFile);
                }
                if (!vo.getErrorFilePath().equalsIgnoreCase("-")) {
                    String errorUploadFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadExcelfile?path=" + vo.getErrorFilePath() + ">Status</a>";
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

    public void getAllPromotionDetailforOrg() {
        logger.info("======= Inside getAllPromotionDetail ======");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            SearchPromotionReq req = new SearchPromotionReq();
            req.setType(PromoSearchType.ORGANIZATION_DATA);
            req.setUserId(new Long(empId));
            req.setStatusId(11l);

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
            } else if (sidx.equals("preqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqId().compareToIgnoreCase(p2.getReqId());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getCreatedDate().compareToIgnoreCase(p2.getCreatedDate());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getReqName().compareToIgnoreCase(p2.getReqName());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getEventName().compareToIgnoreCase(p2.getEventName());
                    }
                });
            } else if (sidx.equals("mktgtype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getMktgName().compareToIgnoreCase(p2.getMktgName());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getCategory().compareToIgnoreCase(p2.getCategory());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getSubCategory().compareToIgnoreCase(p2.getSubCategory());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        PromotionVO p1 = (PromotionVO) o1;
                        PromotionVO p2 = (PromotionVO) o2;
                        return p1.getStatusName().compareToIgnoreCase(p2.getStatusName());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }

            for (PromotionVO vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getReqId());
                cell.add("T" + vo.getReqId());
                cell.add(vo.getCreatedDate());
                cell.add(vo.getReqName());
                cell.add(vo.getEventName());
                cell.add(vo.getMktgName());
                cell.add(vo.getCategory());
                cell.add(vo.getSubCategory());
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

    public void getPromotiondtlPromoIdWise() {
        logger.info("Inside get alll promotion detail based on promotion id and promotion type");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            String promoId = request.getParameter("mstPromoId").toString();            
            SearchPromotionReq req = new SearchPromotionReq();
            req.setType(PromoSearchType.STATUS_USER);
            req.setUserId(new Long(empId));
            req.setStatusId(11l);
            SearchPromotionResp resp = ServiceMaster.getSearchPromotionService().getAllPromotionRequestdtl(req);
            if (!resp.getList().isEmpty() && resp.getList().size() > 0) {
                for (PromotionVO vo : resp.getList()) {
                    if (vo.getReqId().equalsIgnoreCase(promoId)) {
                        cellobj.put(WebConstants.ID, vo.getReqId());
                        cell.add("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getReqId()), 8));
                        cell.add(vo.getReqName());
                        cell.add(vo.getCreatedDate());
                        cell.add(vo.getEventName());
                        cell.add(vo.getMktgName());
                        cell.add(vo.getCategory());
                        cell.add(vo.getSubCategory());
                        cellobj.put(WebConstants.CELL, cell);
                        cell.clear();
                        cellarray.add(cellobj);
                    }

                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, "1");
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                
            }


        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while getPromotiondtlPromoIdWise : function getPromotiondtlPromoIdWise() : Exception is :");
            e.printStackTrace();
        }
    }

    public String redirectPromotiontype() {
        logger.info("====== Inside redirecting to promotion type ===  ");
        try {
            getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
            
            if (formVo.getPromotionSel() != null) {
                getSessionMap().put(WebConstants.PROMOTION_REQ_ID, formVo.getTxtReqId());
            }
            if (formVo.getPromotionSel().equalsIgnoreCase("1")) {
                return WebConstants.REDIRECT_BXGY_SET_LEVEL;
            } else if (formVo.getPromotionSel().equalsIgnoreCase("2")) {
                return WebConstants.REDIRECT_BXGY_DISC;
            } else if (formVo.getPromotionSel().equalsIgnoreCase("3")) {
                return WebConstants.REDIRECT_FLAT;
            } else if (formVo.getPromotionSel().equalsIgnoreCase("4")) {
                return WebConstants.REDIRECT_POWER;
            } else if (formVo.getPromotionSel().equalsIgnoreCase("5")) {
                return WebConstants.REDIRECT_TICKET_SIZE;
            } else if (formVo.getPromotionSel().equalsIgnoreCase("6")) {
                return WebConstants.REDIRECT_TICKET_SIZE_POOL;
            } else if (formVo.getPromotionSel().equalsIgnoreCase("7")) {
                return WebConstants.REDIRECT_BXGY;
            } else {
                return INPUT;
            }
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while redirectPromotiontype : function redirectPromotiontype() : Exception is :");
            e.printStackTrace();
            return INPUT;
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

    public List<String> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<String> CategoryList) {
        this.CategoryList = CategoryList;
    }

    public List<String> getSubCategoryList() {
        return SubCategoryList;
    }

    public void setSubCategoryList(List<String> SubCategoryList) {
        this.SubCategoryList = SubCategoryList;
    }

    public Map<String, String> getPromotionMap() {
        return promotionMap;
    }

    public void setPromotionMap(Map<String, String> promotionMap) {
        this.promotionMap = promotionMap;
    }

    public Map<String, String> getFillPromotionMap() {
        return FillPromotionMap;
    }

    public void setFillPromotionMap(Map<String, String> FillPromotionMap) {
        this.FillPromotionMap = FillPromotionMap;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        PromotionInitiateAction.logger = logger;
    }

    public CachedMapsList getMaps() {
        return maps;
    }

    public void setMaps(CachedMapsList maps) {
        this.maps = maps;
    }

    public PromoInitiationFormVo getFormVo() {
        return formVo;
    }

    public void setFormVo(PromoInitiationFormVo formVo) {
        this.formVo = formVo;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getStrUserID() {
        return strUserID;
    }

    public void setStrUserID(String strUserID) {
        this.strUserID = strUserID;
    }

    public Map<String, String> getCampaignMap() {
        return campaignMap;
    }

    public void setCampaignMap(Map<String, String> campaignMap) {
        this.campaignMap = campaignMap;
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
