/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.approval.action;

import com.fks.promo.init.ApprovalSearchType;
import com.fks.promo.init.MstZoneVO;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.SearchPromotionSearchResp;
import com.fks.promo.init.StoreVO;
import com.fks.promo.init.TransPromoArticleVO;
import com.fks.promo.init.TransPromoConfigVO;
import com.fks.promo.init.TransPromoVO;
import com.fks.promo.master.service.MstLeadTime;
import com.fks.promotion.approval.service.PromotionApprRejHoldReq;
import com.fks.promotion.approval.service.PromotionApprRejHoldRes;
import com.fks.promotion.approval.service.PromotionStatusType;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.Level1ApprovalFormVO;

import com.fks.ui.master.vo.StausVO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 *
 * @author nehabha
 */
public class Level1ApprovalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(Level1ApprovalAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    ApprovalUtil maps;
    private String strZoneID;
    Map<String, String> mstReasonRejectionVOs;
    Map<String, String> categoryMap;
    private Level1ApprovalFormVO lafvo;
    CachedMapsList cachedMapsList;
    private Map<String, String> eventMap, mktgMap, promotionMap;

    public Level1ApprovalFormVO getLafvo() {
        return lafvo;
    }

    public void setLafvo(Level1ApprovalFormVO lafvo) {
        this.lafvo = lafvo;
    }

    @Override
    public String execute() {
        logger.info("---------- Welcome Trans Promotion Initiate Level1ApprovalAction Action Page L1 ----------");
        try {

            MstLeadTime leadTime = ServiceMaster.getOtherMasterService().getMstLeadTime();
            lafvo = new Level1ApprovalFormVO();
            lafvo.setLeadTime(String.valueOf(leadTime.getValue()));
            cachedMapsList = new CachedMapsList();
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.L1_USER_SUB_CATEGORY_LIST, strUserID.toString());
            mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
            eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
            mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
            promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);

            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("@=== Exception in excute() of Promotion Level1ApprovalAction Page L1 ====@");
            e.printStackTrace();
            return ERROR;
        }
    }
    private String searchType, categoryName, startDate, endDate, eventSel, promotionSel, marketingsel;

    public void getAllTransPromotionDetail() {
        logger.info("@======= Inside getAllTransPromotionDetail L1. Service : Service : getTransPromoService().getallTransPromoforApproval()======@");
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

                SearchPromotionSearchResp resppromo = maps.getAllTramsPromotionReqlevel1(empId, strZoneID, storeCode, startPageIndex.intValue(), searchTypeEnum, categoryName, startDate, endDate, false, null, eventSel, marketingsel, promotionSel);

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
                    "L1 Exception generated while getAllTransPromotionDetail : function getAllTransPromotionDetail() : Exception is :");
            e.printStackTrace();
        }
    }
    private String actionName, transPromoID, rejReason;

    public String approveHoldRejectTransPromoRequest() {
        logger.info("@======= Inside Approve/Hold/Reject Request L1. service : getApprRejHoldPromotionReqService().createL1ApprRejHoldReqList()======");
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
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
                req.setIsRejected(true);
                req.setIsHold(false);
                req.setIsApproved(false);
                req.setIsChangedate(false);
                req.setReasonForRejection(rejReason);
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
            PromotionApprRejHoldRes resp = maps.createHoldAppRejTransPromoReq(reqList);

            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                cachedMapsList = new CachedMapsList();
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.L1_USER_SUB_CATEGORY_LIST, empId.toString());
                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
                eventMap = cachedMapsList.getActiveMap(MapEnum.EVENT);
                mktgMap = cachedMapsList.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = cachedMapsList.getActiveMap(MapEnum.PROMOTION_TYPE);
                addActionMessage(resp.getResp().getMsg());
                return SUCCESS;
            } else {
                cachedMapsList = new CachedMapsList();
                categoryMap = cachedMapsList.getCategoryMapByUserSession(MapEnum.L1_USER_SUB_CATEGORY_LIST, empId.toString());

                mstReasonRejectionVOs = cachedMapsList.getActiveMap(MapEnum.APPROVER_REJECTION_REASON);
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
            logger.fatal("@=== L1 Exception generated in function updatestatus() : Exceptions is :" + e.getMessage());
            return ERROR;
        }
    }
    private String manualRemarks;

    public void approveRejectHoldSingleL1Request() {
        logger.info("@======= Inside Single L1 Approve/Hold/Reject. Service : getApprRejHoldPromotionReqService().createL1ApprRejHoldReq()======@");
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
                PromotionApprRejHoldRes resp = ServiceMaster.getApprRejHoldPromotionReqService().createL1ApprRejHoldReq(req);

                if (resp.getResp().getRespCode().equals(RespCode.SUCCESS)) {
                    responseData.put("status", "SUCCESS");
                    responseData.put("message", resp.getResp().getMsg());
                } else {
                    responseData.put("status", "FAILURE");
                    responseData.put("message", resp.getResp().getMsg());

                    if (resp.isIsLeadTimeFailed() != null && resp.isIsLeadTimeFailed()) {
                        System.out.println("------- suggested date : " + resp.getSuggestedTime());
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
            logger.fatal("@===== L1Exception generated in function updatestatus() : Exceptions is :" + e.getMessage());
        }
    }

    public String getRejReason() {
        return rejReason;
    }

    public void setRejReason(String rejReason) {
        this.rejReason = rejReason;
    }

    public void downloadPromontiondtl() {
        logger.info("@======= Inside downloadPromontiondtl L1. Service : getTransPromoService().searchPromotionByTransReqId()======@");
        String transpromoID = request.getParameter("id");

        try {
            maps = new ApprovalUtil();
            List<TransPromoVO> listPromo = maps.getAllTramsPromotionReqWithID(transpromoID);
            StringBuffer sb = new StringBuffer();

            writeHeader(sb);//write the header

            writecontent(sb, listPromo);

            //opening the file
            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
            String currentDate = getDateString(new Date());
            String fileName = "PromotionArticalDetail" + currentDate + ".csv";
            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("======file opening is completed=====");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validatedate() {
        logger.info("@==== Inside Validating Lead Time. Service :  getOtherMasterService().getMstLeadTime() ===@");
        PrintWriter out = null;
        try {

            MstLeadTime leadTime = ServiceMaster.getOtherMasterService().getMstLeadTime();
            int leadtime = leadTime.getValue();
            String fromDate = request.getParameter("startDate");
            fromDate = PromotionUtil.getStringdate(fromDate) + " 00:00:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date validFromDate = format.parse(fromDate);

            int hoursDiff = ServiceMaster.getCommonPromotionService().getDiffHoursWithHolidays(format.format(validFromDate));
            System.out.println("---------- ****** Hours Diff : " + hoursDiff);
            out = response.getWriter();
            JSONObject responseData = new JSONObject();
            if (hoursDiff < leadtime) {
                responseData.put("status", "FAILURE");
                responseData.put("message", "Please select proper startdate. ");

            } else {
                responseData.put("status", "SUCCESS");
                responseData.put("message", "");
            }
            out.println(responseData);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Level1ApprovalAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Level1ApprovalAction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }

    }

    private void writeHeader(StringBuffer sb) throws Exception {
        sb.append("Zone Description ");//1
        sb.append(",");
        sb.append("Site code");//2
        sb.append(",");
        sb.append("Site Description");//3
        sb.append(",");
        sb.append("Format");//4
        sb.append(",");
        sb.append("Promotion Type");//5
        sb.append(",");
        sb.append("Promo Details");//6
        sb.append(",");
        sb.append("Group Details");//7
        sb.append(",");
        sb.append("Article QTY");//8
        sb.append(",");
        sb.append("Articles");//9
        sb.append(",");
        sb.append("Article Desc");//10
        sb.append(",");
        sb.append("MRP ");//11
        sb.append(",");
        sb.append("MC Code");//12
        sb.append(",");
        sb.append("MC description");//13
        sb.append(",");
        sb.append("Percentage/ Value discount for combination ");//14
        sb.append(",");
        sb.append("Validity Date FROM");//15
        sb.append(",");
        sb.append("Validity Date TO");//16
        sb.append(",");
        sb.append("Net Margin Achievement");//17
        sb.append(",");
        sb.append("Growth in Ticket size");//18
        sb.append(",");
        sb.append("Sell thru – v/s quantity");//19
        sb.append(",");
        sb.append("Growth in conversions");//20
        sb.append(",");
        sb.append("Sales Growth – for value");//21
        sb.append(",");
        sb.append("Sales Growth – for QTY");//22
        sb.append("\n");

        logger.info("Header write in a file is completed successfully.......");
    }

    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }

    private void writecontent(StringBuffer sb, List<TransPromoVO> listPromo) throws Exception {
        int lineCount = 1;
        for (TransPromoVO tpvo : listPromo) {
            //first coloum zine
            int x = 1;
            for (MstZoneVO zoneVo : tpvo.getZoneDesc()) {
                sb.append(zoneVo.getZonename());
                if (tpvo.getZoneDesc().size() != x) {
                    sb.append("|");
                }
                x++;
            }
            sb.append(",");
            //Second coloum sitecode
            x = 1;
            for (StoreVO store : tpvo.getLstStoreVOs()) {
                sb.append(store.getStoreID());
                if (tpvo.getLstStoreVOs().size() != x) {
                    sb.append("|");
                }
                x++;
            }
            sb.append(",");
            //Third coloum site desc
            x = 1;
            for (StoreVO store : tpvo.getLstStoreVOs()) {
                sb.append(store.getStoreDesc());
                if (tpvo.getLstStoreVOs().size() != x) {
                    sb.append("|");
                }
                x++;
            }
            sb.append(",");
            //Forth coloum  format
            x = 1;
            for (String format : tpvo.getFormat()) {
                sb.append(format);
                if (tpvo.getFormat().size() != x) {
                    sb.append("|");
                }
                x++;
            }

            sb.append(",");
            //fifth coloum  promotion dtl
            sb.append(tpvo.getPromotionType());
            sb.append(",");
            //six coloum promotion desc
            sb.append(tpvo.getPromoTypeDesc());
            sb.append(",");
//            }else{
//               sb.append("- ");//7: set group
//                sb.append(",");
//                sb.append("- ");//7: set group
//                sb.append(",");
//                sb.append(" -");//7: set group
//                sb.append(",");
//                sb.append(" -");//7: set group
//                sb.append(",");
//                sb.append("- ");//7: set group
//                sb.append(",");
//                sb.append(" -");//7: set group
//                sb.append(",");
//            }
            for (TransPromoArticleVO articleVO : tpvo.getTransPromoArticleList()) {

                if (lineCount > 1) {
                    sb.append(" ");//7: set group
                    sb.append(",");
                    sb.append(" ");//7: set group
                    sb.append(",");
                    sb.append(" ");//7: set group
                    sb.append(",");
                    sb.append(" ");//7: set group
                    sb.append(",");
                    sb.append(" ");//7: set group
                    sb.append(",");
                    sb.append(" ");//7: set group
                    sb.append(",");
                }

                sb.append(articleVO.getGroup());//7: set group
                sb.append(",");
                sb.append(articleVO.getQty());//8:articleqty
                sb.append(",");
                sb.append(articleVO.getArtCode());//9 article code
                sb.append(",");
                sb.append(articleVO.getArtDesc());//10 article disc
                sb.append(",");
                sb.append(articleVO.getMrp());//11: Mrp
                sb.append(",");
                sb.append(articleVO.getMcCode());//12: MC code
                sb.append(",");
                sb.append(articleVO.getMcDesc());// 13: MC desc
                sb.append(",");
                for (TransPromoConfigVO configVO : tpvo.getTransPromoConfigList()) {
                    sb.append(configVO.getDiscValue());//14disc value
                    sb.append(",");
                    sb.append(configVO.getValidFrom());//15 valid date
                    sb.append(",");
                    sb.append(configVO.getValidTo());// 16 valid to
                    sb.append(",");
                    sb.append(configVO.getMarginAchievement());//17 margin achiement
                    sb.append(",");
                    sb.append(configVO.getTicketSizeGrowth());//18 growth ticket size
                    sb.append(",");
                    sb.append(configVO.getSellThruQty());// 19 : sell thry qty
                    sb.append(",");
                    sb.append(configVO.getGrowthCoversion());// 20 :Growth conversion
                    sb.append(",");
                    sb.append(configVO.getSalesGrowth());// 21 : sell growth value
                    sb.append(",");
                    sb.append(configVO.getQty());//22 : sell growth qty
                }
                sb.append("\n");
                lineCount++;
            }

            sb.append("\n");
        }//end of TransPromoVO for loop
    }

    public ApprovalUtil getMaps() {
        return maps;
    }

    public void setMaps(ApprovalUtil maps) {
        this.maps = maps;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
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

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getManualRemarks() {
        return manualRemarks;
    }

    public void setManualRemarks(String manualRemarks) {
        this.manualRemarks = manualRemarks;
    }
}
