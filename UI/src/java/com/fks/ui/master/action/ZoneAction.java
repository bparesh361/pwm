/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstTaskVO;
import com.fks.promo.master.service.MstZoneVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
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
public class ZoneAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String zonename;
    private String zonecode;
    private String txnzoneid;
    private String strUserID;
    private String selStatus;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Zone Action Page ----------------");
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
            logger.info("Exception in excute() of ZoneAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllZone() {
        logger.info("-----------  Inside Get All Zone Type Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstZoneVO> list = ServiceMaster.getOtherMasterService().getAllZone();
            logger.info(" Zones Successfully Fetched.");
            if (list != null) {
                for (MstZoneVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getId());
                    cell.add(vo.getId());
                    cell.add(vo.getZonename());
                    cell.add(vo.getZonecode());
                    if (vo.getIsBlocked() == null) {
                        cell.add("-");
                    } else {
                        if (vo.getIsBlocked().equals(WebConstants.ACTIVE_SHORT)) {
                            cell.add(WebConstants.ACTIVE_STR);
                        } else {
                            cell.add(WebConstants.BLOCKED_STR);
                        }
                    }
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);

                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("---- ZONE Mater Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllPromotionMaster() method of PromotionAction :");
            e.printStackTrace();
        }
    }

    public String submitZone() {
        logger.info("----- Inside Creating Master Type ----- " + zonecode);
        try {
            if (zonename != null && !zonename.isEmpty()) {
                zonename = zonename.trim();
                MstZoneVO vo = new MstZoneVO();
                if (txnzoneid != null && !txnzoneid.isEmpty()) {
                    vo.setId(new Long(new Long(txnzoneid)));
                }
                vo.setZonename(zonename);
                vo.setZonecode(zonecode);
                vo.setIsBlocked(Short.valueOf(selStatus));
                Resp resp = ServiceMaster.getOtherMasterService().createZoneMaster(vo);
                if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                    logger.info("Zone Created Successfully.");
                    if (txnzoneid != null && !txnzoneid.isEmpty()) {
                        if(Short.valueOf(selStatus)==1){
                            CacheMaster.ZoneMap.remove(txnzoneid);
                        }else{
                            CacheMaster.ZoneMap.put(txnzoneid, zonename);
                        }
                    }else{
                        CacheMaster.ZoneMap.put(resp.getPk().toString(), zonename);
                    }
                    addActionMessage(resp.getMsg());
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    logger.info("Error while creating Task Type.");
                    return INPUT;
                }
            } else {
                logger.info("---- Zone Can Not be Null or Blank ------- ");
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

    public String getSelStatus() {
        return selStatus;
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    public String getZonecode() {
        return zonecode;
    }

    public void setZonecode(String zonecode) {
        this.zonecode = zonecode;
    }

    public void setTxnzoneid(String txnzoneid) {
        this.txnzoneid = txnzoneid;
    }

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }
}
