/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstLeadTime;
import com.fks.promo.master.service.MstStatusVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Paresb
 */
public class LeadTimeAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(LeadTimeAction.class.getName());
    private HttpServletResponse response;
    private HttpServletRequest request;
    private PrintWriter out;
    private Long id;
    private String statusname;
    private String L1;
    private String L2;
    private String L5;
    private Integer leadTimeValue;
    private String strUserID;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Problem Action Page ----------------");
        try {
           Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Problem Action Master Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getStatusForLeadTime() {
        logger.info("Getting All Status for Lead Time");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstStatusVO> list = ServiceMaster.getOtherMasterService().getAllLeadTimeStatus();
            if (list != null) {
                for (MstStatusVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getStatusId());
                    cell.add(vo.getStatusId());
                    cell.add(vo.getStatusName());
                    cell.add(vo.getL1());
                    cell.add(vo.getL2());
                    cell.add(vo.getL5());
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);

                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("---- Status for Lead time Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getStatusForLeadTime() method of LeadTimeAction :");
            e.printStackTrace();
        }

    }

    public void getLeadTimeForPromoSetUp() {
        logger.info("Getting Lead Time Promo");
        try {
            JSONObject responcedata = new JSONObject();
            out = response.getWriter();
            MstLeadTime mstleadTime = ServiceMaster.getOtherMasterService().getMstLeadTime();
            responcedata.put("leadTimeValue", mstleadTime.getValue());
            logger.info("Lead Time Value for Promotion Set Up is " + mstleadTime.getValue());
            out.print(responcedata);
        } catch (Exception e) {
            logger.info("Exception in getStatusForLeadTime() method of LeadTimeAction :");
            e.printStackTrace();
        }

    }

    public String updateLeadTime() {
        logger.info("---- inside update lead time ---- ");
        MstStatusVO vo = new MstStatusVO();
        vo.setStatusId(id);
        vo.setL1(L1);
        vo.setL2(L2);
        vo.setL5(L5);
        Resp resp = ServiceMaster.getOtherMasterService().updateStatus(vo);
        if (resp.getRespCode() == RespCode.FAILURE) {
            logger.info("---- Error while updating Mst Status Data.");
            addActionError("Error While Updating Escalation / Lead Time configuration.");
            return ERROR;
        } else {
            logger.info(" ---- Mst Status Data Updated Successfully. ---- ");
            addActionMessage("Escalation / Lead Time configuration Updated Successfully.");
            return SUCCESS;
        }
    }

    public String updateLeadTimeForPromoSetUp() {
        logger.info(" --- Update Lead Time for Promotion Set Up. --- ");
        MstLeadTime leadTime = new MstLeadTime();
        leadTime.setId(new Long("1"));
        leadTime.setValue(leadTimeValue);
        Resp resp = ServiceMaster.getOtherMasterService().updateMstLeadTime(leadTime);
        if (resp.getRespCode() == RespCode.FAILURE) {
            logger.info("---- Error while updating Mst LEAD time for Promo.");
            addActionError("Error while updating Mst LEAD time for Promo..");
            return ERROR;
        } else {
            logger.info(" ---- Mst Lead Time Status Data Updated Successfully. ---- ");
            addActionMessage("Lead Time Data Updated Successfully.");
            return SUCCESS;
        }
    }

    
    
    public void downloadSampleCalendarFile(){
         logger.info("==== Inside Download Sample Calendar File..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Calendar_sample.csv";
            sb.append("Event Name");
            sb.append(",");
            sb.append("Start Date [yyyy-mm-dd]");
//            sb.append(",");
//            sb.append("End Date");            
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
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }
     }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    public String getL1() {
        return L1;
    }

    public void setL1(String L1) {
        this.L1 = L1;
    }

    public String getL2() {
        return L2;
    }

    public void setL2(String L2) {
        this.L2 = L2;
    }

    public String getL5() {
        return L5;
    }

    public void setL5(String L5) {
        this.L5 = L5;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public void setLeadTimeValue(Integer leadTimeValue) {
        this.leadTimeValue = leadTimeValue;
    }
    
}