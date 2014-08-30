/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.user.action;

import com.fks.promo.master.service.DepartmentVO;
import com.fks.promo.master.service.EmployeeVo;
import com.fks.promo.master.service.GetOrgDtlResp;
import com.fks.promo.master.service.GetUserInfoResp;
import com.fks.promo.master.service.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.UserFormVo;
import com.fks.ui.login.action.UserLoginAction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
public class CreateUserAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(UserLoginAction.class.getName());
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Object strUserID;
    private Map<String, String> siteMap, HoSiteMap, ZoneSiteMap, roleMap, DepartmentMap, lstDept;
    private String SiteCode;
    private List<String> formatList;
    PrintWriter out;
    private CachedMapsList cachedMapsList;
    private String selempId;
    private UserFormVo formVo;

    public Map<String, String> getLstDept() {
        return lstDept;
    }

    public void setLstDept(Map<String, String> lstDept) {
        this.lstDept = lstDept;
    }

    public String CreateuserExecute() {
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID);
            logger.info("================Welcome to  Create User Action Page ======== !  " + strUserID);
            if (strUserID != null) {
                formVo = new UserFormVo();
                cachedMapsList = new CachedMapsList();
                setSiteMap(CacheMaster.StoreMap);
                setHoSiteMap(CacheMaster.HoStoreMap);
                setZoneSiteMap(CacheMaster.ZoneStoreMap);
                setRoleMap(CacheMaster.RoleMap);
                setLstDept(cachedMapsList.getDept());
                //setDepartmentMap(CacheMaster.DeptMap);
                formatList = cachedMapsList.getActiveList(MapEnum.FORMAT_LIST, null);
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

    public void getAllDepartment() {
        logger.info("====== Inside getall Department ====== ");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellDeptnamedecArray = new ArrayList<String>();
            List<String> celldeprtIdArray = new ArrayList<String>();
            List<DepartmentVO> lstDept = ServiceMaster.getOtherMasterService().getAllDepartments();
            if (lstDept.size() > 0 && !lstDept.isEmpty()) {
                out = resp.getWriter();
                cellObject = new JSONObject();
                responseData = new JSONObject();
                for (DepartmentVO vo : lstDept) {
                    cellDeptnamedecArray.add(vo.getDepartmentName());
                    celldeprtIdArray.add(vo.getDepartmentID().toString());
                }
                cellObject.put("deeptnameList", cellDeptnamedecArray);
                cellObject.put("deptIdList", celldeprtIdArray);
                responseData.put("rows", cellObject);
                out.println(responseData);
            } else {
                logger.info("No department Available..");
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of getAllDepartment Page ----- ");
            e.printStackTrace();
        }
    }

    public void getZoneBasedonFormat() {
        logger.info("====== Inside getZoneBasedonFormat ====== ");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            String selFormat = req.getParameter("format").toLowerCase();
            List<String> cellzoneArray = new ArrayList<String>();
            List<String> cellzoneIdArray = new ArrayList<String>();
            System.out.println("Selected Format : " + selFormat);
            cachedMapsList = new CachedMapsList();
            ZoneSiteMap = cachedMapsList.getZoneMapBasedOnFormat(MapEnum.ZONE_SITE, selFormat);
            Iterator iterator = ZoneSiteMap.entrySet().iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    cellzoneIdArray.add(entry.getKey().toString());
                    cellzoneArray.add(entry.getValue().toString());
                }
            }
            out = resp.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("zonenameList", cellzoneArray);
            cellObject.put("zoneIdList", cellzoneIdArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in excute() of getZoneBasedonFormat Page ----- ");
            e.printStackTrace();
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

    public String SubmitEmployeeDetail() {
        logger.info("==== Indide Submiting employee Detail ===== Emp Code : " + formVo.getTxtempCode());
        try {
            EmployeeVo employeeVo = new EmployeeVo();
            employeeVo.setContactno(formVo.getTxtcontact());
            employeeVo.setEmpName(formVo.getTxtempname());
            employeeVo.setEmailId(formVo.getTxtemail());
            employeeVo.setEmpId(formVo.getTxtempCode());
            employeeVo.setEmpCode(formVo.getTxtempCode());
            employeeVo.setPassword("india123");
            employeeVo.setStoreDesc(formVo.getZonedescSel());

            if (formVo.getTxtreporting().length() > 0) {
                employeeVo.setReportingTo(formVo.getTxtreporting());
            } else {
                employeeVo.setReportingTo(null);
            }
            if (formVo.getTxttastmanager().length() > 0) {
                employeeVo.setTaskmanager(formVo.getTxttastmanager());
            } else {
                employeeVo.setTaskmanager(null);
            }
            employeeVo.setRoleId(formVo.getRoleSel());
            employeeVo.setStoreId(formVo.getSendSiteCode());
            employeeVo.setUserID(formVo.getTxtempCode());

            String allgridData = formVo.getSendDeptCode();
            if (!allgridData.isEmpty() && allgridData != null) {
                String[] strArr = allgridData.split(",");
                System.out.println("Length of Department : " + strArr.length);

                for (int i = 0; i < strArr.length; i++) {
                    employeeVo.getDeptIdList().add(Long.valueOf(strArr[i]));
                }

            }
            Resp empresp = ServiceMaster.getUserMasterService().createUser(employeeVo);
            if (empresp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                formVo = new UserFormVo();
                addActionMessage(empresp.getMsg());
                cachedMapsList = new CachedMapsList();
                setSiteMap(CacheMaster.StoreMap);
                setHoSiteMap(CacheMaster.HoStoreMap);
                setRoleMap(CacheMaster.RoleMap);
                setLstDept(cachedMapsList.getDept());
                //setDepartmentMap(CacheMaster.DeptMap);
                formatList = cachedMapsList.getActiveList(MapEnum.FORMAT_LIST, null);
                if (empresp.isRedirect()) {
                    formVo.setIsredirect("1");
                }
                return SUCCESS;
            } else {
                addActionError(empresp.getMsg());
                cachedMapsList = new CachedMapsList();
                setSiteMap(CacheMaster.StoreMap);
                setHoSiteMap(CacheMaster.HoStoreMap);
                setRoleMap(CacheMaster.RoleMap);
                setLstDept(cachedMapsList.getDept());
                //setDepartmentMap(CacheMaster.DeptMap);
                formatList = cachedMapsList.getActiveList(MapEnum.FORMAT_LIST, null);
                return INPUT;
            }

        } catch (Exception e) {
            logger.info("Exception in excute() of CreateuserExecute Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String redirecrUser() {
        logger.info("====== Inside redirecting to  redirecrUser==  ");
        try {
            return SUCCESS;
        } catch (Exception e) {
            logger.fatal(
                    "Exception generated while redirecrUser : function redirecrUser() : Exception is :");
            e.printStackTrace();
            return INPUT;
        }

    }

    public void getUserdetailforUpdate() {
        logger.info("======== Inside getUserdetailforUpdate =====");
        try {
            JSONObject responseData = null;
            JSONObject cellObject = null;

            String selEmpId = req.getParameter("txtempid").toLowerCase();
            GetUserInfoResp Empresp = ServiceMaster.getUserMasterService().getUserDetailBasedOnId(Long.valueOf(selEmpId));
            if (Empresp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                out = resp.getWriter();
                cellObject = new JSONObject();
                responseData = new JSONObject();
                cellObject.put("empCode", Empresp.getEmployeeVo().getEmpCode());
                cellObject.put("srtEmpName", Empresp.getEmployeeVo().getEmpName());
                cellObject.put("strContactNo", Empresp.getEmployeeVo().getContactno());
                cellObject.put("strReportingTo", Empresp.getEmployeeVo().getReportingTo());
                cellObject.put("strTaskManager", Empresp.getEmployeeVo().getTaskmanager());
                cellObject.put("strEmail", Empresp.getEmployeeVo().getEmailId());
                cellObject.put("strLocationId", Empresp.getEmployeeVo().getStoreVO().getLocationID());
                cellObject.put("strStoreId", Empresp.getEmployeeVo().getStoreVO().getStoreID());
                cellObject.put("strStoreName", Empresp.getEmployeeVo().getStoreVO().getZoneName());
                cellObject.put("strZone", Empresp.getEmployeeVo().getStoreVO().getZoneName());
                cellObject.put("strformat", Empresp.getEmployeeVo().getStoreVO().getFormat());
                cellObject.put("strRegion", Empresp.getEmployeeVo().getStoreVO().getRegion());
                cellObject.put("strCity", Empresp.getEmployeeVo().getStoreVO().getCity());
                cellObject.put("strLocationName", Empresp.getEmployeeVo().getStoreVO().getLocationName());
                cellObject.put("strRoleId", Empresp.getEmployeeVo().getRoleId());
                cellObject.put("strPassword", Empresp.getEmployeeVo().getPassword());
                cellObject.put("strzonesitedesc", Empresp.getEmployeeVo().getStoreDesc());
                cellObject.put("deptIdlist", Empresp.getEmployeeVo().getDeptIdList());
                responseData.put("rows", cellObject);
                out.println(responseData);
            }

        } catch (Exception e) {
            logger.info("Exception in excute() of getUserdetailforUpdate Page ----- ");
            e.printStackTrace();

        }
    }

    public void getZoneSiteDescbasedonSiteCode() {
        logger.info("inside getZoneSiteDescbasedonSiteCode === ");

        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            String selStore = req.getParameter("store").toLowerCase();
            List<String> cellzoneArray = new ArrayList<String>();
            List<String> cellzoneIdArray = new ArrayList<String>();
            System.out.println("Selected Store : " + selStore);
            cachedMapsList = new CachedMapsList();
            ZoneSiteMap = cachedMapsList.getZoneMapBasedOnFormat(MapEnum.ZONE_SITE_DESCRIPTION, selStore);
            Iterator iterator = ZoneSiteMap.entrySet().iterator();
            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    cellzoneIdArray.add(entry.getKey().toString());
                    cellzoneArray.add(entry.getValue().toString());
                }
            }
            out = resp.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("zonenameList", cellzoneArray);
            cellObject.put("zoneIdList", cellzoneIdArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in excute() of getZoneSiteDescbasedonSiteCode Page ----- ");
            e.printStackTrace();
        }
    }

    public String userUpdatedetail() {
        logger.info("==== Indide userUpdatedetail employee Detail ===== Emp Code : " + selempId);

        try {
            EmployeeVo employeeVo = new EmployeeVo();
            employeeVo.setContactno(formVo.getTxtcontact());
            employeeVo.setEmpName(formVo.getTxtempname());
            employeeVo.setEmailId(formVo.getTxtemail());
            employeeVo.setEmpId(selempId);
            employeeVo.setEmpCode(formVo.getTxtempCode());
            employeeVo.setPassword("india123");
            employeeVo.setStoreDesc(formVo.getZonedescSel());

            if (formVo.getTxtreporting().length() > 0) {
                employeeVo.setReportingTo(formVo.getTxtreporting());
            } else {
                employeeVo.setReportingTo(null);
            }
            if (formVo.getTxttastmanager().length() > 0) {
                employeeVo.setTaskmanager(formVo.getTxttastmanager());
            } else {
                employeeVo.setTaskmanager(null);
            }
            employeeVo.setRoleId(formVo.getRoleSel());
            employeeVo.setStoreId(formVo.getSendSiteCode());
            employeeVo.setUserID(formVo.getTxtempCode());


            String allgridData = formVo.getSendDeptCode();
            if (!allgridData.isEmpty() && allgridData != null) {
                String[] strArr = allgridData.split(",");
                System.out.println("Length of Department : " + strArr.length);
                for (int i = 0; i < strArr.length; i++) {
                    employeeVo.getDeptIdList().add(Long.valueOf(strArr[i]));
                }

            }
            Resp empresp = ServiceMaster.getUserMasterService().updateUserInfo(employeeVo);
            if (empresp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                formVo = new UserFormVo();
                addActionMessage(empresp.getMsg());
                cachedMapsList = new CachedMapsList();
                setSiteMap(CacheMaster.StoreMap);
                setHoSiteMap(CacheMaster.HoStoreMap);
                setRoleMap(CacheMaster.RoleMap);
                setLstDept(cachedMapsList.getDept());
                //setDepartmentMap(CacheMaster.DeptMap);
                formatList = cachedMapsList.getActiveList(MapEnum.FORMAT_LIST, null);
                if (empresp.isRedirect()) {
                    formVo.setIsredirect("1");
                }
                return SUCCESS;
            } else {

                formVo.setTxtcontact(formVo.getTxtcontact());
                formVo.setTxtempname(formVo.getTxtempname());
                formVo.setTxtemail(formVo.getTxtemail());

                formVo.setTxtempCode(formVo.getTxtempCode());
//                formVo.setPassword("india123");
                formVo.setZonedescSel(formVo.getZonedescSel());
                formVo.setTxtreporting(formVo.getTxtreporting());
                formVo.setTxttastmanager(formVo.getTxttastmanager());
                formVo.setRoleSel(formVo.getRoleSel());
                formVo.setSendSiteCode(formVo.getSendSiteCode());
                formVo.setTxtempCode(formVo.getTxtempCode());


                addActionError(empresp.getMsg());
                cachedMapsList = new CachedMapsList();
                setSiteMap(CacheMaster.StoreMap);
                setHoSiteMap(CacheMaster.HoStoreMap);
                setRoleMap(CacheMaster.RoleMap);
                setLstDept(cachedMapsList.getDept());
                //setDepartmentMap(CacheMaster.DeptMap);
                formatList = cachedMapsList.getActiveList(MapEnum.FORMAT_LIST, null);
                return INPUT;
            }

        } catch (Exception e) {
            logger.info("Exception in excute() of CreateuserExecute Page ----- ");
            e.printStackTrace();
            return ERROR;
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

    public Map<String, String> getDepartmentMap() {
        return DepartmentMap;
    }

    public void setDepartmentMap(Map<String, String> DepartmentMap) {
        this.DepartmentMap = DepartmentMap;
    }

    public List<String> getFormatList() {
        return formatList;
    }

    public void setFormatList(List<String> formatList) {
        this.formatList = formatList;
    }

    public String getSelempId() {
        return selempId;
    }

    public void setSelempId(String selempId) {
        this.selempId = selempId;
    }

    public UserFormVo getFormVo() {
        return formVo;
    }

    public void setFormVo(UserFormVo formVo) {
        this.formVo = formVo;
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
