/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstMktgVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.MktgVo;
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
public class MarketingMasterAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String mktgName;
    private String txMktgID;
    private String selStatus;
    private String strUserID;

    @Override
    public String execute() throws Exception {
        logger.info("-------- Inside Creating Marketing Master ------ ");
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
            logger.error("------- Error while processing Marketing Master Page.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllMarketingTypes() {
        logger.info("-----------  Inside Fetching Marketing Type Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstMktgVO> list = ServiceMaster.getOtherMasterService().getAllMarketingType();
            if (list != null) {
                for (MstMktgVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getMktgTyped());
                    cell.add(vo.getMktgTyped());
                    cell.add(vo.getMktgName());
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
                logger.info("---- Marketing Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllRoles() method of RoleAction :");
            e.printStackTrace();
        }

    }

    public String submitMarketingType() {
        logger.info("----- Inside Creating Marketing Type ----- " + selStatus + " : " + txMktgID);
        if (txMktgID != null && !txMktgID.isEmpty()) {
            logger.info("Updating Marketing Type with Id " + txMktgID);
        } else {
            logger.info("Creating New Marketing Type.");
        }
        try {
            if (mktgName != null && !mktgName.isEmpty()) {
                mktgName = mktgName.trim();
                MstMktgVO vo = new MstMktgVO();
                if (txMktgID != null && !txMktgID.isEmpty()) {
                    vo.setMktgTyped(new Long(txMktgID));
                }
                vo.setMktgName(mktgName.trim());
                vo.setIsBlocked(new Short(selStatus));
                Resp resp = ServiceMaster.getOtherMasterService().createMartingType(vo);
                if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                    logger.info("Marketing Type Created Successfully. Resp Key :"+resp.getPk());
                    addActionMessage(resp.getMsg());
                    if (txMktgID != null && !txMktgID.isEmpty()) {
                       logger.info("Update Marketing Type ");
                       CacheMaster.MarketingTypeMap.remove(txMktgID);
                       CacheMaster.MarketingTypeMap.put(txMktgID, new MktgVo(new Long(txMktgID),mktgName.trim(),new Short(selStatus)));
                    } else {
                        logger.info("Create Marketing Type");
                        CacheMaster.MarketingTypeMap.put(resp.getPk().toString(),  new MktgVo(resp.getPk(),mktgName.trim(),new Short(selStatus)));
                    }
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    logger.info("Error while creating Marketing Type.");
                    return INPUT;
                }
            } else {
                logger.info("---- Marketing Type Can Not be Null or Blank ------- ");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Marketing Type.");
            return ERROR;
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
        MarketingMasterAction.logger = logger;
    }

    public String getMktgName() {
        return mktgName;
    }

    public void setMktgName(String mktgName) {
        this.mktgName = mktgName;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
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

    public String getTxMktgID() {
        return txMktgID;
    }

    public void setTxMktgID(String txMktgID) {
        this.txMktgID = txMktgID;
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }
}
