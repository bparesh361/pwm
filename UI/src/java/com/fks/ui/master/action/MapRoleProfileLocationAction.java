/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.LocationVo;
import com.fks.promo.master.service.ProfileVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RoleProfileMappingReq;
import com.fks.promo.master.service.RoleProfileMappingResp;
import com.fks.promo.master.service.RoleVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
public class MapRoleProfileLocationAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class.getName());
    PrintWriter out;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private Map<String, String> locationMap;
    private String strUserID;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Role & Profile Mapping Action Page ----------------");
        try {
          Object  strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                setLocationMap(CacheMaster.LocationMap);
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of MapRoleProfileLocationAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllProfiles() throws IOException {
        logger.info("---- Inside getAllProfiles ---");

        JSONObject responcedata = new JSONObject();
        JSONArray cellarray = new JSONArray();
        JSONArray cell = new JSONArray();
        JSONObject cellobj = new JSONObject();
        out = response.getWriter();
        try {
            List<ProfileVO> profileVoList = ServiceMaster.getRoleMasterService().getAllProfiles();
            if (profileVoList.size() > 0 && !profileVoList.isEmpty()) {
                for (ProfileVO vo : profileVoList) {
                    cellobj.put(WebConstants.ID, vo.getProfileID());
                    cell.add(vo.getProfileID());
                    cell.add(vo.getProfileName());
                    cell.add(vo.getLevelAccessName());

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, profileVoList.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("Response Sent!!");
            }
        } catch (Exception e) {

            logger.info("Exception in getAllProfiles() method of MapRoleProfileLocationAction :");
            e.printStackTrace();
        }
    }

    public void flexBoxRoles() {
        logger.info("---- Inside Flex Box Roles");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        JSONArray cellArray = null;
        try {
            out = response.getWriter();
            cellArray = new JSONArray();
            responseData = new JSONObject();
            String rolename = request.getParameter("flexRoleName").toLowerCase();
            List<RoleVO> lstRoles = ServiceMaster.getRoleMasterService().getAllRoles();
            if (!lstRoles.isEmpty() && lstRoles != null) {
                for (RoleVO vo : lstRoles) {
                    if(vo.getIsBlocked() ==WebConstants.ACTIVE_SHORT){
                    if (vo.getRoleName().toLowerCase().startsWith(rolename)) {
                        cellObject = new JSONObject();
                        cellObject.put("id", vo.getRoleId());
                        cellObject.put("name", vo.getRoleName());
                        cellArray.add(cellObject);
                    }
                }
                }
                responseData.put("results", cellArray);
                out.println(responseData);
            }
        } catch (Exception e) {
            logger.info("Exception in flexBoxRoles() method of MapRoleProfileLocationAction :");
            e.printStackTrace();
        }
    }
    private String locationSel, SelectedIDs, txtroleID;

    public String CreateUpdateRoleProfileMapping() {
        logger.info("----- Inside CreateUpdateRoleProfileMapping ");
        try {
//            logger.info("Selected Profile ID : " + SelectedIDs);
//            logger.info("--- Location id : "+ locationSel);
//            logger.info("-- role id : "+ txtroleID);
            String allgridData = getSelectedIDs();
            String[] strArr = allgridData.split(",");
            System.out.println("Length : " + strArr.length);

            RoleProfileMappingReq mappingReq = new RoleProfileMappingReq();
            for (int i = 0; i < strArr.length; i++) {
                mappingReq.getProfileList().add(Long.valueOf(strArr[i]));
            }
            logger.info("Profile list size : " + mappingReq.getProfileList().size());
            mappingReq.setLocationId(Long.valueOf(locationSel));
            mappingReq.setRoleID(Long.valueOf(txtroleID));
            Resp resp = ServiceMaster.getRoleMasterService().submitRoleProfileMapping(mappingReq);
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());
                setLocationMap(CacheMaster.LocationMap);
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                setLocationMap(CacheMaster.LocationMap);
                return INPUT;
            }

        } catch (Exception e) {
            logger.info("Exception in CreateUpdateRoleProfileMapping() method of MapRoleProfileLocationAction :");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getRoleLocationProfileDtl() {
        logger.info("=== Inside  getRoleLocationProfileDtl=== ");
        JSONObject responseData = null;
        JSONObject cellObject = null;

        try {
            String SelidRole = request.getParameter("SelidRole").toLowerCase();
            RoleProfileMappingResp resp = ServiceMaster.getRoleMasterService().getRoleWiseLocationandProfile(Long.valueOf(SelidRole));
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                List<String> cellprofileIdArray = new ArrayList<String>();
                out = response.getWriter();
                cellObject = new JSONObject();
                responseData = new JSONObject();
                responseData.put("location", resp.getLocationId());
                if (resp.getProfileList().size() > 0) {
                    for (Long l : resp.getProfileList()) {
                        cellprofileIdArray.add(l.toString());
                    }
                    logger.info("----- Long List :  " + cellprofileIdArray.size());
                    cellObject.put("profileIdList", cellprofileIdArray);
                    responseData.put("rows", cellObject);
                } else {
                    cellprofileIdArray.add(String.valueOf("-1"));
                    cellObject.put("profileIdList", cellprofileIdArray);
                    responseData.put("rows", cellObject);
                }
                out.println(responseData);

            }
        } catch (Exception e) {
            logger.info("Exception in getRoleLocationProfileDtl() method of MapRoleProfileLocationAction :");
            e.printStackTrace();
        }
    }

    public String getSelectedIDs() {
        return SelectedIDs;
    }

    public void setSelectedIDs(String SelectedIDs) {
        this.SelectedIDs = SelectedIDs;
    }

    public String getLocationSel() {
        return locationSel;
    }

    public void setLocationSel(String locationSel) {
        this.locationSel = locationSel;
    }

    public String getTxtroleID() {
        return txtroleID;
    }

    public void setTxtroleID(String txtroleID) {
        this.txtroleID = txtroleID;
    }

    public Map<String, String> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<String, String> locationMap) {
        this.locationMap = locationMap;
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
