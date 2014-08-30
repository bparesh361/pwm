/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstReasonRejectionVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
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
public class ReasonRejectionAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String rejectionid;
    private String rejectionname;
    private String txRejectionID;
    private String selStatus;
    private String isApprover;
    private String rejectionid1;
    private String rejectionname1;
    private String txRejectionID1;
    private String selStatus1;
     private String strUserID;

    
    public String execute() throws Exception {
        logger.info("-------- Inside Rejection Master ------ ");
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
            logger.error("------- Error while processing Rejection Master Page.");
            e.printStackTrace();
            return ERROR;
        }
    }   
    
    public String submitRejectionMaster() throws Exception {
        logger.info("----- Inside Creating Rejection Type ----- : " + txRejectionID + " is approver " + isApprover);
        try {           
            
        if (rejectionname != null && !rejectionname.isEmpty()) {
            rejectionname = rejectionname.trim();
            MstReasonRejectionVO vo = new MstReasonRejectionVO();
            if(txRejectionID!=null && !txRejectionID.isEmpty()){
                vo.setReasonId(new Long(txRejectionID));
            }
            vo.setIsApprover(new Short(isApprover));
            vo.setReasonName(rejectionname);
            vo.setIsBlocked(new Short(selStatus));
            vo.setIsApprover(new Short(isApprover));
            Resp resp = ServiceMaster.getOtherMasterService().createReasonForRejection(vo);
            if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                logger.info("Reason Rejection Created Successfully.");
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                logger.info("Error while creating Rejection Type.");
                return INPUT;
            }
        } else {
            logger.info("---- Rejection Type Can Not be Null or Blank ------- ");
            return INPUT;
        }
        } catch(Exception e){
            e.printStackTrace();
            addActionError("Error While Creating Rejection Type .");
            return ERROR;
        }
    }

     public String submitRejectionMasterForApprover() throws Exception {
        logger.info("----- Inside Creating Rejection Type For Approver ----- : " + txRejectionID1 + " is approver " + isApprover + " rejection name "+ rejectionname1);
        try {           
            
        if (rejectionname1 != null && !rejectionname1.isEmpty()) {
            rejectionname1 = rejectionname1.trim();
            MstReasonRejectionVO vo = new MstReasonRejectionVO();
            if(txRejectionID1!=null && !txRejectionID1.isEmpty()){
                vo.setReasonId(new Long(txRejectionID1));
            }
            vo.setIsApprover(new Short(isApprover));
            vo.setReasonName(rejectionname1);
            vo.setIsBlocked(new Short(selStatus1));
            vo.setIsApprover(new Short(isApprover));
            Resp resp = ServiceMaster.getOtherMasterService().createReasonForRejection(vo);
            if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                logger.info("Reason Rejection Created Successfully.");
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                logger.info("Error while creating Rejection Type.");
                return INPUT;
            }
        } else {
            logger.info("---- Rejection Type Can Not be Null or Blank ------- ");
            return INPUT;
        }
        } catch(Exception e){
            e.printStackTrace();
            addActionError("Error While Creating Rejection Type .");
            return ERROR;
        }
    }
    
    public void getAllRejection() {        
        logger.info("-----------  Inside Fetching Reason Rejection Details : UI --------Is Approver :  "+isApprover);
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();                        
            List<MstReasonRejectionVO> list = ServiceMaster.getOtherMasterService().getAllReasonForRejection(isApprover);
            logger.info("No. of Records Retrieved : " + list.size());
            if (list != null) {
                for (MstReasonRejectionVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getReasonId());
                    cell.add(vo.getReasonId());
                    cell.add(vo.getReasonName());
                    if(vo.getIsBlocked().equals(WebConstants.ACTIVE_SHORT)){
                        cell.add(WebConstants.ACTIVE_STR);
                    } else  {
                        cell.add(WebConstants.BLOCKED_STR);
                    } 
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("----- Reason Rejection Request Received Successfully with List Size : " + list.size() + " ----- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllRoles() method of RoleAction :");
            e.printStackTrace();
        }

    }
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ReasonRejectionAction.logger = logger;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public String getRejectionid() {
        return rejectionid;
    }

    public void setRejectionid(String rejectionid) {
        this.rejectionid = rejectionid;
    }

    public String getRejectionname() {
        return rejectionname;
    }

    public void setRejectionname(String rejectionname) {
        this.rejectionname = rejectionname;
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

    public void setTxRejectionID(String txRejectionID) {
        this.txRejectionID = txRejectionID;
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    public String getIsApprover() {
        return isApprover;
    }

    public void setIsApprover(String isApprover) {
        this.isApprover = isApprover;
    }

    public String getRejectionid1() {
        return rejectionid1;
    }

    public void setRejectionid1(String rejectionid1) {
        this.rejectionid1 = rejectionid1;
    }

    public String getRejectionname1() {
        return rejectionname1;
    }

    public void setRejectionname1(String rejectionname1) {
        this.rejectionname1 = rejectionname1;
    }

    public String getSelStatus1() {
        return selStatus1;
    }

    public void setSelStatus1(String selStatus1) {
        this.selStatus1 = selStatus1;
    }

    public String getTxRejectionID1() {
        return txRejectionID1;
    }

    public void setTxRejectionID1(String txRejectionID1) {
        this.txRejectionID1 = txRejectionID1;
    }
    
    
    
}
