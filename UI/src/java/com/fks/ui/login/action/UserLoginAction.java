/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.login.action;

import com.fks.promo.master.service.AuthResponse;
import com.fks.promo.master.service.EmployeeVo;
import com.fks.promo.master.service.ModuleVo;
import com.fks.promo.master.service.ProfileVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author krutij
 */
public class UserLoginAction extends ActionBase implements ServletRequestAware {

    private static Logger logger = Logger.getLogger(UserLoginAction.class);
    private HttpServletRequest req;
    private String userName;
    private String pwd;

    public String userAuthenticate() {
        logger.info("================ Welcome To LogIN Action.");

        try {
            boolean isNewUser = false;
            AuthResponse response = ServiceMaster.getUserMasterService().authorizeUser(Long.valueOf(userName), pwd);
            if (response.getResp().getRespCode().toString().equalsIgnoreCase(WebConstants.success)) {
                EmployeeVo employeeVo = response.getEmployeeVo();
                if (pwd.equalsIgnoreCase("india123")) {
                    isNewUser = true;
                } else if (employeeVo.isIsPasswordChanged() == Boolean.TRUE) {
                    isNewUser = true;
                }

                getSessionMap().put("isNewUser", isNewUser);

                getSessionMap().put(WebConstants.ACCESS_MENU, getModuleMap(response.getModuleVos()));


                getSessionMap().put(WebConstants.USER_PROFILE, getProfileMap(response.getProfileVos()));

                getSessionMap().put(WebConstants.USER_ID, userName);

                getSessionMap().put(WebConstants.USER_NAME, employeeVo.getEmpName());

                getSessionMap().put(WebConstants.USER_ROLE, employeeVo.getRoleName());

                getSessionMap().put(WebConstants.EMP_ID, employeeVo.getEmpId());

                getSessionMap().put(WebConstants.EMP_NAME, employeeVo.getEmpName());

                if (employeeVo.getStoreVO() != null) {
                    getSessionMap().put(WebConstants.ZONE_NAME, employeeVo.getStoreVO().getZoneName());

                    getSessionMap().put(WebConstants.ZONE_ID, employeeVo.getStoreVO().getZoneId());

                    getSessionMap().put(WebConstants.LOCATION_ID, employeeVo.getStoreVO().getLocationID());

                    getSessionMap().put(WebConstants.LOCATION, employeeVo.getStoreVO().getLocationName());

                }

                getSessionMap().put(WebConstants.CONTACT_NO, employeeVo.getContactno());

                if (employeeVo.getStoreVO() != null) {
                    getSessionMap().put(WebConstants.STORE_CODE, employeeVo.getStoreVO().getStoreID());
                }
                getSessionMap().put(WebConstants.EMAIL_ID, employeeVo.getEmailId());

                if (employeeVo.getStoreVO() != null) {
                    getSessionMap().put(WebConstants.STORE_DESC, employeeVo.getStoreVO().getStoreDesc());
                }
                if (isNewUser == true) {
                    return WebConstants.CHANGEPASSWORD;
                }
                return SUCCESS;
            } else {
                addActionError(response.getResp().getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception while authenticating user ... " + e.getLocalizedMessage());
            return ERROR;
        }
    }

    public Map<String, String> getModuleMap(List<ModuleVo> moduleInfo) {
        Map<String, String> map = new HashMap<String, String>();
        for (ModuleVo module : moduleInfo) {
            map.put(module.getModuleID().toString(), module.getModuleName());
        }
        return map;
    }

    public Map<String, String> getProfileMap(List<ProfileVO> profileInfo) {
        Map<String, String> map = new HashMap<String, String>();
        for (ProfileVO profile : profileInfo) {
            map.put(profile.getProfileID().toString(), profile.getProfileName());
        }
        return map;
    }

    public String callwelcome() {
        Object strUserID = getSessionMap().get(WebConstants.USER_ID);
        logger.info("================Welcome/Home Page ======== !  " + strUserID);
        if (strUserID != null) {
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }

    public String logoutAction() throws Exception {
        getSessionMap().remove(WebConstants.USER_ROLE);
        getSessionMap().remove(WebConstants.STORE_CODE);
        getSessionMap().remove(WebConstants.ACCESS_MENU);
        getSessionMap().remove(WebConstants.USER_NAME);
        getSessionMap().remove(WebConstants.EMP_ID);
        getSessionMap().remove(WebConstants.USER_ID);
        getSessionMap().remove(WebConstants.PROMOTION_REQ_ID);
        getSessionMap().remove(WebConstants.CONTACT_NO);
        getSessionMap().remove(WebConstants.STORE_CODE);
        getSessionMap().remove(WebConstants.STORE_DESC);
        getSessionMap().remove(WebConstants.ZONE_ID);
        getSessionMap().remove(WebConstants.ZONE_NAME);
        getSessionMap().remove(WebConstants.EMAIL_ID);
        getSessionMap().remove(WebConstants.EMP_NAME);


        getSessionMap().remove("isNewUser");
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.req = hsr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void donothing() {
        logger.info("======= donothing called===");
    }
}
