/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstPromotionTypeVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.PromotionTypeVO;
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
public class PromotionTypeAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PromotionTypeAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String promotiontypename;
    private String txPromotionID;
    private String selStatus;
    private String strUserID;

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    @Override
    public String execute() throws Exception {
        logger.info("-------- Inside Creating Promotion Type Master ------ ");
        try {
          Object  strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.error("------- Error while processing Promotion Type Master Page.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllPromotionType() {
        logger.info("-----------  Inside Fetching Promotion Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstPromotionTypeVO> list = ServiceMaster.getOtherMasterService().getAllPromotionMaster();
            if (list != null) {
                for (MstPromotionTypeVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getId());
                    cell.add(vo.getId());
                    cell.add(vo.getPromotionName());
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
                logger.info("---- Promotion Mater Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllPromotionMaster() method of PromotionAction :");
            e.printStackTrace();
        }
    }

    public String submitPromotionMaster() {
        logger.info("----- Inside Creating Promotion Type ----- : " + txPromotionID + " :  " + selStatus);
        try {
            if (promotiontypename != null && !promotiontypename.isEmpty()) {
                promotiontypename = promotiontypename.trim();
                MstPromotionTypeVO vo = new MstPromotionTypeVO();
                if (txPromotionID != null && !txPromotionID.isEmpty()) {
                    vo.setId(new Long(txPromotionID));
                }
                vo.setPromotionName(promotiontypename);
                vo.setIsBlocked(new Short(selStatus));
                Resp resp = ServiceMaster.getOtherMasterService().createPromotionType(vo);
                if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                    logger.info("Promotion Type Created Successfully.");
                    addActionMessage(resp.getMsg());
                    if (txPromotionID != null && !txPromotionID.isEmpty()) {
                        logger.info("==== Update ");
                        CacheMaster.promotionTypeMap.remove(txPromotionID);
                        CacheMaster.promotionTypeMap.put(txPromotionID,new PromotionTypeVO(new Long(txPromotionID), promotiontypename, new Short(selStatus)));
                    }else{
                        logger.info("====  Create ");
                        CacheMaster.promotionTypeMap.put(resp.getPk().toString(),new PromotionTypeVO(resp.getPk(), promotiontypename, new Short(selStatus)));
                    }
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    logger.info("Error while creating Promotion Type.");
                    return INPUT;
                }
            } else {
                logger.info("---- Promotion Type Can Not be Null or Blank ------- ");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Problem Type.");
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

    public String getPromotiontypename() {
        return promotiontypename;
    }

    public void setPromotiontypename(String promotiontypename) {
        this.promotiontypename = promotiontypename;
    }

    public void setTxPromotionID(String txPromotionID) {
        this.txPromotionID = txPromotionID;
    }
}
