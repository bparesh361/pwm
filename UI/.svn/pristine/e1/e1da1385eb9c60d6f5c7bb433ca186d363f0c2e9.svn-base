/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.execution.action;

import com.fks.promo.init.ExecutePromoManagerEnum;
import com.fks.promo.task.FilePromoCloseResp;
import com.fks.promo.task.PromoCloseVO;
import com.fks.promo.task.Resp;
import com.fks.promo.task.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.PromoSetupFormVO;
import com.fks.ui.master.vo.StausVO;
import com.fks.ui.master.vo.TransPromotionUIVO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author ajitn
 */
public class PromoSetupApprovalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PromoSetupApprovalAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID, strEmpID, headerName, strLocation;
    private PromoSetupFormVO setupFormVo;
    private CachedMapsList maps;
    Map<String, String> mstReasonRejectionVOs;
    private Map<String, String> eventMap, mktgMap, promotionMap;
    private List<String> subcategoryMap;
    private Map<String, String> categoryMap;
    private List<String> fillSubCategoryList = new ArrayList<String>();
    private File promoCloserFileUpload;
    private String promoCloserFileUploadFileName;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promo Setup Dashboard Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            strLocation = getSessionMap().get(WebConstants.LOCATION).toString();
            if (strUserID != null) {
                setupFormVo = new PromoSetupFormVO();
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                subcategoryMap = fillSubCategoryList;
                if (strLocation.equalsIgnoreCase("HO")) {
                    setHeaderName("Promo Setup Approval :");
                } else {
                    setHeaderName("Promo Detailing :");
                }

                mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
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
    private String SearchType, startDate, endDate, eventSel, marketingsel, promotionSel, categoryName, subcategoryName;

    ;

    public void getAllTransPromotionDetail() {
        logger.info("======= Inside getAllTransPromotionDetail For Promo Setup DashBoard. Service :  getTransPromoService().getExecutePromoDashBoardDtl(req)======");
        String teamMemberId = getSessionMap().get(WebConstants.EMP_ID).toString();

        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();

            ExecutePromoManagerEnum execPromo = ExecutePromoManagerEnum.TEAM_MEMBER;
            String pageno = request.getParameter("page");
            String rows = request.getParameter("rows");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            Integer pageNo = null;
            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            List<TransPromotionUIVO> list = null;
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);
                //  pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                list = PromoExecutionUtil.getAllTramsPromotionExcuteDtl(teamMemberId, startDate, endDate, execPromo, startPageIndex, eventSel, marketingsel, promotionSel, SearchType, categoryName, subcategoryName);
                if (list != null && list.size() > 0) {
                    totalCount = list.get(0).getTotalCount();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }

                if (sidx.equals("reqno")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getTransPromoId().toString().compareToIgnoreCase(p2.getTransPromoId().toString());
                        }
                    });
                } else if (sidx.equals("ldate")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getLastUpdatedDate().compareToIgnoreCase(p2.getLastUpdatedDate().toString());
                        }
                    });
                } else if (sidx.equals("date")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                        }
                    });
                } else if (sidx.equals("time")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getTime().toString().compareToIgnoreCase(p2.getTime().toString());
                        }
                    });
                } else if (sidx.equals("initiatorName")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getInitiatorName().toString().compareToIgnoreCase(p2.getInitiatorName().toString());
                        }
                    });
                } else if (sidx.equals("contNo")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getContactNo().toString().compareToIgnoreCase(p2.getContactNo().toString());
                        }
                    });
                } else if (sidx.equals("empCode")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getEmpCode().toString().compareToIgnoreCase(p2.getEmpCode().toString());
                        }
                    });
                } else if (sidx.equals("initiatorlocation")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getInitiatorLocation().toString().compareToIgnoreCase(p2.getInitiatorLocation().toString());
                        }
                    });
                } else if (sidx.equals("reqName")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getRequestName().toString().compareToIgnoreCase(p2.getRequestName().toString());
                        }
                    });
                } else if (sidx.equals("event")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getEvent().toString().compareToIgnoreCase(p2.getEvent().toString());
                        }
                    });
                } else if (sidx.equals("marketingtype")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getMarketingType().toString().compareToIgnoreCase(p2.getMarketingType().toString());
                        }
                    });
                } else if (sidx.equals("category")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getCategory().toString().compareToIgnoreCase(p2.getCategory().toString());
                        }
                    });
                } else if (sidx.equals("subcategory")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getSubCategory().toString().compareToIgnoreCase(p2.getSubCategory().toString());
                        }
                    });
                } else if (sidx.equals("promotype")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getPromotionType().toString().compareToIgnoreCase(p2.getPromotionType().toString());
                        }
                    });
                } else if (sidx.equals("validfrom")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getValidFrom().toString().compareToIgnoreCase(p2.getValidFrom().toString());
                        }
                    });
                } else if (sidx.equals("validto")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getValidTo().toString().compareToIgnoreCase(p2.getValidTo().toString());
                        }
                    });
                } else if (sidx.equals("status")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                        }
                    });
                } else if (sidx.equals("approvername")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                        }
                    });
                } else if (sidx.equals("approvalfrom")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                        }
                    });
                } else if (sidx.equals("teamassignfrom")) {
                    Collections.sort(list, new Comparator() {

                        @Override
                        public int compare(Object o1, Object o2) {
                            TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                            TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                            return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                        }
                    });
                } else if (sidx.equals("assignby")) {
                    Collections.sort(list, new Comparator() {

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
                    Collections.reverse(list);
                }

                for (TransPromotionUIVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getTransPromoId());
                    //cell.add("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getTransPromoId()), 8));
                    cell.add("T" + vo.getMstPromoId() + "-R" + vo.getTransPromoId());
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
                    cell.add(vo.getApproverName());
                    cell.add(vo.getApproverFrom());
                    cell.add(vo.getTeamAssignmentLocation());
                    cell.add(vo.getTeamAssignedBy());
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
            }

        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while getAllTransPromotionDetail Setup Dashboard: function getAllTransPromotionDetail() : Exception is :");
            e.printStackTrace();
        }
    }
//    private File lsmwFileUpload;
//    private String lsmwFileUploadFileName;
//
//    public String getLsmwFileUploadFileName() {
//        return lsmwFileUploadFileName;
//    }
//
//    public void setLsmwFileUploadFileName(String lsmwFileUploadFileName) {
//        this.lsmwFileUploadFileName = lsmwFileUploadFileName;
//    }

    public String submitPromoSetupUsingFile() {
        try {
            logger.info("------------- Inside Approve Reject Hold Promo Setup. Service : getTaskService().statusChangeTransPromoByTeamMemberList -------");
            strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
            //   Resp setupResp = PromoExecutionUtil.submitPromoSetup(setupFormVo, strEmpID);

            if (promoCloserFileUpload != null) {
                if (promoCloserFileUpload.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }
                String promoClosePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROMO_CLOSE);

                Calendar cal = new GregorianCalendar();
                Long time = cal.getTimeInMillis();

                String filePath = promoClosePath + strEmpID + "_CLOSER_REQ_" + time.toString() + "_" + promoCloserFileUploadFileName;
                System.out.println("File : " + filePath);
                File bfile = new File(filePath);
                FileUtils.copyFile(promoCloserFileUpload.getAbsoluteFile(), bfile);
                FilePromoCloseResp setupResp = PromoExecutionUtil.submitPromoSetUpUsingFile(filePath, strEmpID);
                if (setupResp.getResp().getRespCode() == RespCode.SUCCESS) {
                    addActionMessage(setupResp.getResp().getMsg());
                    setupFormVo = new PromoSetupFormVO();
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return SUCCESS;
                } else {
                    List<PromoCloseVO> list = setupResp.getPromoCloseList();
                    if (list.size() > 0 && list != null) {
                        getSessionMap().put(WebConstants.PROMO_CLOSE, list);
                        setupFormVo.setIsuploaderror("1");
                    } else {
                        setupFormVo.setIsuploaderror("0");
                    }
                    setupFormVo.setIsFile("1");
                    addActionError(setupResp.getResp().getMsg());
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }
            } else {
                addActionError("Please Select File Properly!");
                maps = new CachedMapsList();
                mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strEmpID.toString());
                subcategoryMap = fillSubCategoryList;
                return INPUT;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.fatal("Exception generated in function submitPromoSetup() : Exceptions is :" + ex.getMessage());
            addActionError("Error : " + ex.getMessage());
            return ERROR;
        }
    }

    public void downloadPromoSetupErrorFile() {
        logger.info("===== Inside downloadPromoSetupErrorFile ======");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "PromoCloser.csv";
            sb.append("Request Number");
            sb.append(",");
            sb.append("Promo Detail");
            sb.append(",");
            sb.append("Remark");
            sb.append(",");
            sb.append("Bonus Buy");
            sb.append(",");
            sb.append("Cashier Trigger");
            sb.append(",");
            sb.append("Status");
            sb.append(",");
            sb.append("Message");
            sb.append("\n");

            List<PromoCloseVO> list = (List<PromoCloseVO>) getSessionMap().get(WebConstants.PROMO_CLOSE);
            if (list.size() > 0 && !list.isEmpty()) {
                for (PromoCloseVO vo : list) {
                    sb.append(vo.getLine());
                    sb.append(",");
                    sb.append("Failure");
                    sb.append(",");
                    sb.append(vo.getMsg());
                    sb.append("\n");
                }
            }
            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");

        } catch (Exception e) {
            logger.info("Exception in downloadPromoSetupErrorFile() of PromoSetup Page ----- ");
            e.printStackTrace();

        }

    }

    public void downloadPromoSetupSampleFile() {
        logger.info("===== Inside downloadPromoSetupSampleFile ======");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "PromoCloserSample.csv";
            sb.append("Request Number");
            sb.append(",");
            sb.append("Promotion Detail");
            sb.append(",");
            sb.append("Remark");
            sb.append(",");
            sb.append("Bonus Buy");
            sb.append(",");
            sb.append("Cashier Trigger");
            sb.append("\n");


            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");

        } catch (Exception e) {
            logger.info("Exception in downloadPromoSetupSampleFile() of PromoSetup Page ----- ");
            e.printStackTrace();

        }

    }

    public String submitPromoSetup() {
        try {
            logger.info("------------- Inside Approve Reject Hold Promo Setup. Service : getTaskService().statusChangeTransPromoByTeamMemberList -------");
            strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();


            if (setupFormVo.getStatus().equalsIgnoreCase("APPROVE")) {

                Resp setupResp = PromoExecutionUtil.submitPromoSetup(setupFormVo, strEmpID, null);

                if (setupResp.getRespCode() == RespCode.SUCCESS) {
                    addActionMessage(setupResp.getMsg());
                    setupFormVo = new PromoSetupFormVO();
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return SUCCESS;
                } else {
                    addActionError(setupResp.getMsg());
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }

            } else if (setupFormVo.getStatus().equalsIgnoreCase("HOLD")) {
                Resp setupResp = PromoExecutionUtil.submitPromoSetup(setupFormVo, strEmpID, null);
                if (setupResp.getRespCode() == RespCode.SUCCESS) {
                    addActionMessage(setupResp.getMsg());
                    setupFormVo = new PromoSetupFormVO();
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return SUCCESS;
                } else {
                    addActionError(setupResp.getMsg());
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }
            } else if (setupFormVo.getStatus().equalsIgnoreCase("REJECT")) {
                Resp setupResp = PromoExecutionUtil.submitPromoSetup(setupFormVo, strEmpID, null);
                if (setupResp.getRespCode() == RespCode.SUCCESS) {
                    addActionMessage(setupResp.getMsg());
                    setupFormVo = new PromoSetupFormVO();
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return SUCCESS;
                } else {
                    addActionError(setupResp.getMsg());
                    maps = new CachedMapsList();
                    mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                    eventMap = maps.getActiveMap(MapEnum.EVENT);
                    mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                    promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                    subcategoryMap = fillSubCategoryList;
                    return INPUT;
                }
            } else {
                addActionError("Invalid Action.");
                maps = new CachedMapsList();
                mstReasonRejectionVOs = maps.getActiveMap(MapEnum.HO_ZONEL_REJECTION_REASON);
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                mktgMap = maps.getActiveMap(MapEnum.MARKETING_TYPE);
                promotionMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                subcategoryMap = fillSubCategoryList;
                return INPUT;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.fatal("Exception generated in function submitPromoSetup() : Exceptions is :" + ex.getMessage());
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

    public File getPromoCloserFileUpload() {
        return promoCloserFileUpload;
    }

    public void setPromoCloserFileUpload(File promoCloserFileUpload) {
        this.promoCloserFileUpload = promoCloserFileUpload;
    }

    public String getPromoCloserFileUploadFileName() {
        return promoCloserFileUploadFileName;
    }

    public void setPromoCloserFileUploadFileName(String promoCloserFileUploadFileName) {
        this.promoCloserFileUploadFileName = promoCloserFileUploadFileName;
    }

//    public File getLsmwFileUpload() {
//        return lsmwFileUpload;
//    }
//
//    public void setLsmwFileUpload(File lsmwFileUpload) {
//        this.lsmwFileUpload = lsmwFileUpload;
//    }
    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public PromoSetupFormVO getSetupFormVo() {
        return setupFormVo;
    }

    public void setSetupFormVo(PromoSetupFormVO setupFormVo) {
        this.setupFormVo = setupFormVo;
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
}
