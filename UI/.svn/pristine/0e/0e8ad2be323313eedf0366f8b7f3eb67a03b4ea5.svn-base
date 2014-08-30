/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.reports.action;

import com.fks.reports.service.ReportTypeEnum;
import com.fks.reports.service.ReportsRequest;
import com.fks.reports.service.ReportsResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.ReportsFormVo;
import com.fks.ui.master.vo.StausVO;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author javals
 */
public class VendorBackedReport extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(VendorBackedReport.class.getName());
    private HttpServletResponse resp;
    private HttpServletRequest request;
    private Map<String, String> eventMap, statutsMap, categoryMap, zoneMap, problemMap;
    private CachedMapsList maps;
    private List<String> subcategoryMap;
    private List<String> fillSubCategoryList = new ArrayList<String>();
    private ReportsFormVo formVo;

    public String viewVendorBackedReport() {
        logger.info("====================== Welcome Vendor Backed Report Action =====================");
        try {
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID);
            if (strUserID != null) {
                maps = new CachedMapsList();
                statutsMap = maps.getActiveMap(MapEnum.STATUS);
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                subcategoryMap = fillSubCategoryList;
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                return SUCCESS;
            } else {
                return LOGIN;
            }

        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public void getallRequestReportFieldsForVendorBacked() {
        logger.info("==== Inside  getallRequestReportFieldsForVendorBacked =====");
        try {
            String strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = resp.getWriter();
            List<ReportsRequest> list = null;
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
                list = ServiceMaster.getReportService().getAllReportsRequestList(Long.valueOf(strUserID), ReportTypeEnum.VENDOR_BACKED_PROMO_RPT, startPageIndex.intValue());
                if (list.size() > 0 && !list.isEmpty()) {
                    totalCount = list.get(0).getStartIndex();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }

            } else {
                list = Collections.<ReportsRequest>emptyList();
            }

            if (list.size() > 0 && !list.isEmpty()) {
                for (ReportsRequest vo : list) {

                    cellobj.put(WebConstants.ID, vo.getReportReqId());
                    cell.add("R" + vo.getReportReqId());
                    cell.add(vo.getCreatedDate());
                    cell.add(vo.getFromDate());
                    cell.add(vo.getToDate());
                    cell.add(vo.getCategoryName());
                    cell.add(vo.getSubCategoryName());
                    cell.add(vo.getEvent());
                    cell.add(vo.getTicketNo());
                    cell.add(vo.getStatus());
                    cell.add(vo.getReportStatus());
                    cell.add(vo.getRemarks());
                    if (vo.getFilePath() != null) {
                        String articleFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadfileartilcle?path=" + vo.getFilePath() + ">Download</a>";
                        cell.add(articleFile);
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String submitReportRequestVendorBacked() {
        logger.info("======== Inside submitReportRequestVendorBacked UI ======= ");
        try {
            String strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            ReportsResp Reportresp = ReportUtil.submitReportsRequest(formVo, Long.valueOf(strUserID), ReportTypeEnum.VENDOR_BACKED_PROMO_RPT);
            if (Reportresp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(Reportresp.getMsg());
                maps = new CachedMapsList();
                setStatutsMap(maps.getActiveMap(MapEnum.STATUS));
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                subcategoryMap = fillSubCategoryList;
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                return SUCCESS;
            } else {
                addActionError(Reportresp.getMsg());
                maps = new CachedMapsList();
                setStatutsMap(maps.getActiveMap(MapEnum.STATUS));
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                subcategoryMap = fillSubCategoryList;
                categoryMap = maps.getCategoryMapByUserSession(MapEnum.CATEGORY_LIST, strUserID.toString());
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public Map<String, String> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, String> eventMap) {
        this.eventMap = eventMap;
    }

    public Map<String, String> getStatutsMap() {
        return statutsMap;
    }

    public void setStatutsMap(Map<String, String> statutsMap) {
        this.statutsMap = statutsMap;
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

    public ReportsFormVo getFormVo() {
        return formVo;
    }

    public void setFormVo(ReportsFormVo formVo) {
        this.formVo = formVo;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.resp = hsr;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }
}
