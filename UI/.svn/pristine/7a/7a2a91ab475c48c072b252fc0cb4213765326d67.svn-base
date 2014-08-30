/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.AddUpProfileModuleRequest;
import com.fks.promo.master.service.ModuleVo;
import com.fks.promo.master.service.ProfileVO;
import com.fks.promo.master.service.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class MapModuleProfileAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    PrintWriter out;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private Map<String, String> profileMap;
    private String profileId, profileSel;
    private String SelectedIDs;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Module & Profile Mapping Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                setProfileMap(PromotionUtil.sortMapByValue(CacheMaster.ProfileMap));
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }


        } catch (Exception e) {
            logger.info("Exception in excute() of MapModuleProfileAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllModules() throws IOException {
        logger.info("---- Inside getAllProfiles ---");

        JSONObject responseData = null;
        JSONObject cellObject = null;

        try {
            List<ModuleVo> moduleVoList = ServiceMaster.getRoleMasterService().getAllModule();
            if (moduleVoList.size() > 0 && !moduleVoList.isEmpty()) {
                out = response.getWriter();
                cellObject = new JSONObject();
                responseData = new JSONObject();
                List<String> cellmoduleIdArray = new ArrayList<String>();
                List<String> cellmodulenameArray = new ArrayList<String>();
                for (ModuleVo vo : moduleVoList) {
                    cellmoduleIdArray.add(vo.getModuleID().toString());
                    cellmodulenameArray.add(vo.getModuleDesc());
                }
                cellObject.put("moduleIdList", cellmoduleIdArray);
                cellObject.put("moduleNameList", cellmodulenameArray);
                responseData.put("rows", cellObject);
                out.println(responseData);
                logger.info("Response Sent!!");
            }
        } catch (Exception e) {

            logger.info("Exception in getAllModules() method of MapModuleProfileAction :");
            e.printStackTrace();
        }
    }

    public void getProfileWiseModule() {
        logger.info("================ Inside getProfileWiseModule  !" + "Existing Profile :" + profileId);
        try {
            JSONObject responseData = null;
            JSONObject cellObject = null;
            ProfileVO resp = ServiceMaster.getRoleMasterService().getModuleProfileMapping(Long.valueOf(profileId));

            List<String> cellmoduledecArray = new ArrayList<String>();
            List<String> cellmoduleIdArray = new ArrayList<String>();
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            if (resp.getModuleVo().size() > 0) {
                for (ModuleVo c : resp.getModuleVo()) {
                    cellmoduleIdArray.add(c.getModuleID().toString());
                    cellmoduledecArray.add(String.valueOf(c.getModuleDesc()));
                }
                cellObject.put("moduledecList", cellmoduledecArray);
                cellObject.put("moduleIdList", cellmoduleIdArray);
                responseData.put("rows", cellObject);
            } else {
                responseData.put("resp", "No Mapping found!");
            }

            out.println(responseData);

        } catch (Exception e) {
            logger.info(e);
            e.printStackTrace();
        }
    }

    public String submitProfileModuledtl() {
        logger.info("Inside submitProfileModuledtl =====");
        logger.info("Selected Module Iss : " + SelectedIDs);
        logger.info("Profile : " + profileSel);
        try {
            AddUpProfileModuleRequest req = new AddUpProfileModuleRequest();
            req.setProfileId(Long.valueOf(profileSel));
            ModuleVo moduleVo = null;

            String chkIDs = getSelectedIDs();
            logger.info(" IDs : " + chkIDs);
            String[] strArr = chkIDs.split(",");
            logger.info("Length : " + strArr.length);

            for (int i = 0; i < strArr.length; i++) {
                moduleVo = new ModuleVo();
                moduleVo.setModuleID(Long.valueOf(strArr[i]));
                req.getModuleList().add(moduleVo);
            }
            Resp resp = ServiceMaster.getRoleMasterService().submitProfileModuleDtl(req);
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());
                setProfileMap(PromotionUtil.sortMapByValue(CacheMaster.ProfileMap));
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                setProfileMap(PromotionUtil.sortMapByValue(CacheMaster.ProfileMap));
                return INPUT;
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Exception : " + e.getMessage());
            return ERROR;
        }
    }

    public String getProfileSel() {
        return profileSel;
    }

    public void setProfileSel(String profileSel) {
        this.profileSel = profileSel;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getSelectedIDs() {
        return SelectedIDs;
    }

    public void setSelectedIDs(String SelectedIDs) {
        this.SelectedIDs = SelectedIDs;
    }

    public Map<String, String> getProfileMap() {
        return profileMap;
    }

    public void setProfileMap(Map<String, String> profileMap) {
        this.profileMap = profileMap;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }
}
