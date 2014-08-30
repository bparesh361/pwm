/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.login.action;

import com.fks.promo.master.service.GetOrgDtlResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UserCreateAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(UserLoginAction.class);
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private String strUserID;
    private Map<String, String> siteMap, HoSiteMap, ZoneSiteMap, roleMap;
    private String SiteCode;
    PrintWriter out;
    private String txtsitedesc, txtregion, txtformat, txtlocation, txtcity, txtzone, siteSel, hoSel, ZoneSel, txtempCode, txtreporting;
    private String txtempname, txttastmanager, txtcontact, txtemail, roleSel, SendSiteCode;

    public String CreateuserExecute() {
        try {

            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            logger.info("================Welcome to  Create User Action Page ======== !  " + strUserID);
            if (strUserID != null) {

                setSiteMap(CacheMaster.StoreMap);
                setHoSiteMap(CacheMaster.HoStoreMap);
                setZoneSiteMap(CacheMaster.ZoneStoreMap);
                setRoleMap(CacheMaster.RoleMap);
                return SUCCESS;
            } else {

                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of CreateuserExecute Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getOrganizationDtl() throws IOException {
        logger.info("Inside fetching dtl based on site Code ====== Site Code : " + SiteCode);
        out = resp.getWriter();
        JSONObject responcedata = null;
        try {

            responcedata = new JSONObject();

            GetOrgDtlResp dtlResp = ServiceMaster.getOrganizationMasterService().getOrganizationDtlBySiteCode(SiteCode);
            if (dtlResp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                responcedata.put("city", dtlResp.getStoreVO().getCity());
                responcedata.put("desc", dtlResp.getStoreVO().getStoreDesc());
                responcedata.put("format", dtlResp.getStoreVO().getFormat());
                responcedata.put("region", dtlResp.getStoreVO().getRegion());
                responcedata.put("location", dtlResp.getStoreVO().getLocationName());
                responcedata.put("zone", dtlResp.getStoreVO().getZoneName());
                out.println(responcedata);
            }
        } catch (Exception e) {
            logger.info("Exception in getOrganizationDtl() of CreateUserAction Page ----- ");
            e.printStackTrace();
        }
    }

    public String getSiteCode() {
        return SiteCode;
    }

    public void setSiteCode(String SiteCode) {
        this.SiteCode = SiteCode;
    }

    public Map<String, String> getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(Map<String, String> siteMap) {
        this.siteMap = siteMap;
    }

    public Map<String, String> getHoSiteMap() {
        return HoSiteMap;
    }

    public void setHoSiteMap(Map<String, String> HoSiteMap) {
        this.HoSiteMap = HoSiteMap;
    }

    public Map<String, String> getZoneSiteMap() {
        return ZoneSiteMap;
    }

    public void setZoneSiteMap(Map<String, String> ZoneSiteMap) {
        this.ZoneSiteMap = ZoneSiteMap;
    }

    public Map<String, String> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, String> roleMap) {
        this.roleMap = roleMap;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.req = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.resp = hsr;
    }
}
