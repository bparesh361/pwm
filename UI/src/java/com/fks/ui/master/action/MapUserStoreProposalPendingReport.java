/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.reports.service.ReportEmailVo;
import com.fks.reports.service.ReportTypeEnum;
import com.fks.reports.service.ReportsResp;
import com.fks.reports.service.ReportsRespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.reports.action.ReportUtil;
import java.io.PrintWriter;
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
public class MapUserStoreProposalPendingReport extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private CachedMapsList maps;
    private static Logger logger = Logger.getLogger(MapUserStoreProposalPendingReport.class);
    private String tbEmailId, ZoneSel;
    private Map<String, String> zoneMap;

    public String viewMapUserStoreProposalPendingReport() {
        logger.info("@ =============== Inside viewMapUserStoreProposalPendingReport Action================@  ");
        try {
            Object strEmpId = getSessionMap().get(WebConstants.EMP_ID);
            if (strEmpId != null) {
                maps = new CachedMapsList();
                zoneMap = maps.getActiveMap(MapEnum.ZONE_TYPE);
                return SUCCESS;
            } else {
                addActionError("Your Login Session has expired, Please Re-Login.");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllUsersForProposalPendingReports() {
        logger.info("@ =============== Inside getAllUsersForProposalPendingReports Action================@  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = response.getWriter();
            List<ReportEmailVo> list = null;
            list = ServiceMaster.getReportService().getAllEmailIdsForStorePendingUserMapping(ReportTypeEnum.STORE_PROPOSAL_PENDING_AUTOMATED_RPT);

            //System.out.println("List Size : " + list.size());
            if (list.size() > 0 && !list.isEmpty()) {
                for (ReportEmailVo vo : list) {

                    cellobj.put(WebConstants.ID, vo.getReportTypeEmailID());
                    cell.add(vo.getReportTypeEmailID());
                    cell.add(vo.getEmailId());
                    cell.add(vo.getZoneName());
                    cell.add(vo.getZoneId());

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }

                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String hdzoneName, hdzoneIds;

    public String submitStoreProposalPendingReportUsers() {
        logger.info("@ =============== Inside submitStoreProposalPendingReportUsers Action================@  ");
        try {
            ReportsResp Reportresp = ReportUtil.submitReportsRequest(hdzoneIds, hdzoneName, tbEmailId, ReportTypeEnum.STORE_PROPOSAL_PENDING_AUTOMATED_RPT);
            if (Reportresp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(Reportresp.getMsg());
                maps = new CachedMapsList();
                zoneMap = maps.getActiveMap(MapEnum.ZONE_TYPE);
                return SUCCESS;
            } else {
                addActionError(Reportresp.getMsg());
                maps = new CachedMapsList();
                zoneMap = maps.getActiveMap(MapEnum.ZONE_TYPE);
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;

        }
    }

    public String deleteStoreProposalPendingReportUserID() {
        logger.info("--------- inside deleting deleteStoreProposalPendingReportUserID value.-----");
        try {

            String reportEmailID = request.getParameter("selIdToDelete");         
            ReportsResp resp = ServiceMaster.getReportService().deletePendingStoreProposalEmailID(reportEmailID);
            if (resp.getRespCode() == ReportsRespCode.FAILURE) {
                addActionError(resp.getMsg());
                maps = new CachedMapsList();
                zoneMap = maps.getActiveMap(MapEnum.ZONE_TYPE);
                return INPUT;
            } else {
                addActionMessage(resp.getMsg());
                maps = new CachedMapsList();
                zoneMap = maps.getActiveMap(MapEnum.ZONE_TYPE);
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return INPUT;
        }
    }

    public String getZoneSel() {
        return ZoneSel;
    }

    public void setZoneSel(String ZoneSel) {
        this.ZoneSel = ZoneSel;
    }

    public String getTbEmailId() {
        return tbEmailId;
    }

    public void setTbEmailId(String tbEmailId) {
        this.tbEmailId = tbEmailId;
    }

    public Map<String, String> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(Map<String, String> zoneMap) {
        this.zoneMap = zoneMap;
    }

    public String getHdzoneIds() {
        return hdzoneIds;
    }

    public void setHdzoneIds(String hdzoneIds) {
        this.hdzoneIds = hdzoneIds;
    }

    public String getHdzoneName() {
        return hdzoneName;
    }

    public void setHdzoneName(String hdzoneName) {
        this.hdzoneName = hdzoneName;
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
