/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstCampaignVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class CampaignMasterAction extends ActionBase implements ServletResponseAware {

    private static Logger logger = Logger.getLogger(CampaignMasterAction.class);
    private HttpServletResponse resp;
    private String campaignName, txtCampID, selStatus;
    PrintWriter out;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Campaign Master Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID);
            if (strUserID != null) {                
                return SUCCESS;
            } else {                
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of CampaignMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllCampaignDetail() {
        logger.info("===== Inside getAllCampaignDetail =====");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = resp.getWriter();
            List<MstCampaignVO> list = ServiceMaster.getOtherMasterService().getAllCampaign();            
            if (list != null) {
                for (MstCampaignVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getCampaignID());
                    cell.add(vo.getCampaignID().toString());
                    cell.add(vo.getCampaignName());
                    if (vo.isIsActive() == Boolean.FALSE) {
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
                logger.info("---- Campaign Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.error("exception in : " + e.getMessage());
        }
    }

    public String submitCampaignMaster() {
        logger.info("----- Inside submitCampaignMaster ----- Campagin Id " + txtCampID + " Campagin Name " + campaignName);
        try {
            if (campaignName != null && !campaignName.isEmpty()) {
                campaignName = campaignName.trim();
                MstCampaignVO vo = new MstCampaignVO();
                if (txtCampID != null && !txtCampID.isEmpty()) {
                    vo.setCampaignID(Long.valueOf(txtCampID));
                }
                vo.setCampaignName(campaignName);
                if (selStatus.equalsIgnoreCase("0")) {
                    vo.setIsActive(false);
                } else {
                    vo.setIsActive(true);
                }
                com.fks.promo.master.service.Resp Champresp = ServiceMaster.getOtherMasterService().createCampaign(vo);
                if (Champresp.getRespCode().value().equalsIgnoreCase(WebConstants.success)){                    
                      if (txtCampID != null && !txtCampID.isEmpty() ) {
                        //Update
                        logger.info("Update Cache");
                        if(selStatus.equalsIgnoreCase("0")){
                             CacheMaster.CampaignMap.remove(txtCampID);
                        CacheMaster.CampaignMap.put(txtCampID, campaignName);
                        }else{
                             CacheMaster.CampaignMap.remove(txtCampID);
                        
                        }
                       
                    } else {
                        logger.info("Create Cache");
                        CacheMaster.CampaignMap.put(Champresp.getPk().toString(), campaignName);
                    }
                    addActionMessage(Champresp.getMsg());
                    return SUCCESS;
                }
                else {
                    addActionError(Champresp.getMsg());
                    logger.info(" Error while creating Campaign. " + Champresp.getMsg());
                    return INPUT;
                }
            }else {
                    logger.info("---- Campagin Can Not be Null or Blank ------- ");
                    return INPUT;
                }
            
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Campagin Type.");
            return ERROR;
        }
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getSelStatus() {
        return selStatus;
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    public String getTxtCampID() {
        return txtCampID;
    }

    public void setTxtCampID(String txtCampID) {
        this.txtCampID = txtCampID;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.resp = hsr;
    }
}
