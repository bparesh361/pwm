/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstPromotionTypeVO;
import com.fks.promo.master.service.MstTaskVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class TaskTypeAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String taskname;
    private String txTaskID;
    private String selStatus;
    private String strUserID;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Task Action Page ----------------");
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
            logger.info("Exception in excute() of TaskMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    public void getAllTaskType() {
        logger.info("-----------  Inside Get All Task Type Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstTaskVO> list = ServiceMaster.getOtherMasterService().getAllTaskMaster();
            logger.info(" Task Types Successfully Fetched.");
            if (list != null) {
                for (MstTaskVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getTaskId());
                    cell.add(vo.getTaskId());
                    cell.add(vo.getTaskName());
                    if (vo.getIsBlocked().equals(WebConstants.ACTIVE_SHORT)) {
                        cell.add(WebConstants.ACTIVE_STR);
                    } else {
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
                logger.info("---- Task Mater Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllPromotionMaster() method of PromotionAction :");
            e.printStackTrace();
        }
    }

    public String submitTaskMaster() {
        logger.info("----- Inside Creating Master Type ----- TAsk Id : "+txTaskID+"--- Statsus: "+selStatus);
        try {
            if (!taskname.isEmpty()) {
                taskname = taskname.trim();
                MstTaskVO vo = new MstTaskVO();
                if (!txTaskID.isEmpty()) {
                    vo.setTaskId(new Long(txTaskID));
                }
                vo.setTaskName(taskname);
                vo.setIsBlocked(new Short(selStatus));
                Resp resp = ServiceMaster.getOtherMasterService().createTaskMaster(vo);
                if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                    logger.info("Task Type Created Successfully.");
                    addActionMessage(resp.getMsg());
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    logger.info("Error while creating Task Type.");
                    return INPUT;
                }
            } else {
                logger.info("---- Task Type Can Not be Null or Blank ------- ");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Task Type.");
            return ERROR;
        }
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setTxTaskID(String txTaskID) {
        this.txTaskID = txTaskID;
    }
}
