/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstEventVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.EventTypeVO;
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
public class EventTypeAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String eventname;
    private String txEventID;
    private String selStatus;
    

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

    public void getAllEventType() {
        logger.info("-----------  Inside Get All Campaign Type Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstEventVO> list = ServiceMaster.getOtherMasterService().getAllEventMaster();
            logger.info(" Event Types Successfully Fetched.");
            if (list != null) {
                for (MstEventVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getEventId());
                    cell.add(vo.getEventId().toString());
                    cell.add(vo.getEventName());
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
                logger.info("---- Event Mater Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllEvent() method of PromotionAction :");
            e.printStackTrace();
        }
    }

    public String submitEventMaster() {
        logger.info("----- Inside Event Master Type ----- Event Id " + txEventID + " Event Name " + eventname);
        try {
            if (eventname != null && !eventname.isEmpty()) {
                eventname = eventname.trim();
                MstEventVO vo = new MstEventVO();
                if (txEventID != null && !txEventID.isEmpty()) {
                    vo.setEventId(new Long(txEventID));
                }
                vo.setEventName(eventname.trim());
                vo.setIsBlocked(new Short(selStatus));
                Resp resp = ServiceMaster.getOtherMasterService().createEventMaster(vo);
                if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                    logger.info(" Event Type Created Successfully. Event id: " + resp.getPk());
                    if (txEventID != null && !txEventID.isEmpty()) {
                        //Update
                        logger.info("Update Cache");
                        CacheMaster.EventMap.remove(txEventID);
                        CacheMaster.EventMap.put(txEventID, new EventTypeVO(new Long(txEventID), eventname.trim(), new Short(selStatus)));
                    } else {
                        logger.info("Create Cache");
                        CacheMaster.EventMap.put(resp.getPk().toString(), new EventTypeVO(resp.getPk(), eventname.trim(), new Short(selStatus)));
                       
                    }
                     addActionMessage(resp.getMsg());
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    logger.info(" Error while creating Event Type. " + resp.getMsg());
                    return INPUT;
                }
            } else {
                logger.info("---- Event Type Can Not be Null or Blank ------- ");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Event Type.");
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

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getTxEventID() {
        return txEventID;
    }

    public void setTxEventID(String txEventID) {
        this.txEventID = txEventID;
    }
}
