/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.user.action;

import com.fks.promo.master.service.EmployeeSearchEnum;
import com.fks.promo.master.service.EmployeeVo;
import com.fks.promo.master.service.GetEmployeeDetailRequest;
import com.fks.promo.master.service.GetEmployeeDetailResponse;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.StoreVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class SearchUserAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(SearchUserAction.class.getName());
    private HttpServletResponse response;
    private HttpServletRequest request;
    PrintWriter out;
    private Object strUserID;
    private Map<String, String> locationMap, zoneMap, roleMap;
    private String idStore, idEmp, idZone, idLocation, idRole;
    private String selIdToDelete;

    public String viewSearchjsp() {
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID);
            logger.info("================Welcome to Search User Action Page ======== !  " + strUserID);
            if (strUserID != null) {
                setLocationMap(CacheMaster.LocationMap);
                setZoneMap(CacheMaster.ZoneMap);
                setRoleMap(CacheMaster.RoleMap);
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in viewSearchjsp() of SearchUserAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void flexBoxStores() {
        logger.info("=== Inside Flex box Store =====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        JSONArray cellArray = null;
        List<StoreVO> storeVOList = new ArrayList<StoreVO>();
        try {
            out = response.getWriter();
            cellArray = new JSONArray();
            responseData = new JSONObject();
            String storecode = request.getParameter("txtsitecode");
            List<StoreVO> lstStore = ServiceMaster.getOrganizationMasterService().getAllOrganizationDtl();
            String storeDesc;
            if (!lstStore.isEmpty() && lstStore != null) {
                storeVOList = lstStore;
                for (StoreVO vo : storeVOList) {
                    if (vo.isIsStoreBlocked() == Boolean.FALSE) {
                        storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                        if (storeDesc.toLowerCase().startsWith(storecode)) {
                            cellObject = new JSONObject();
                            cellObject.put("id", vo.getStoreID());
                            cellObject.put("name", storeDesc);
                            cellArray.add(cellObject);
                        }
                    }
                }
                responseData.put("results", cellArray);
                out.println(responseData);
            }
        } catch (Exception e) {
            logger.info("Exception in flexBoxStores() method of SearchUserAction :");
            e.printStackTrace();
        }
    }

    public void flexBoxEmployess() {
        logger.info("=== Inside Flex Box employee search ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        JSONArray cellArray = null;
        List<EmployeeVo> employeeVoList = new ArrayList<EmployeeVo>();
        try {
            out = response.getWriter();
            cellArray = new JSONArray();
            responseData = new JSONObject();
            String empname = request.getParameter("txtuserempName").toLowerCase();
            String empNameCode;
            List<EmployeeVo> lstEmployee = ServiceMaster.getUserMasterService().getAllEmployee();
            if (!lstEmployee.isEmpty() && lstEmployee != null) {
                employeeVoList = lstEmployee;
                for (EmployeeVo vo : employeeVoList) {
                    if (vo.isIsBlocked() == false) {
                        empNameCode = vo.getEmpName().concat(" : ").concat(vo.getEmpCode());
                        if (empNameCode.toLowerCase().startsWith(empname)) {
                            cellObject = new JSONObject();
                            cellObject.put("id", vo.getEmpId());
                            cellObject.put("name", empNameCode);
                            cellArray.add(cellObject);
                        }
                    }
                }
                responseData.put("results", cellArray);
                out.println(responseData);
            }
        } catch (Exception e) {
            logger.info("Exception in flexBoxRoles() method of SearchUserAction :");
            e.printStackTrace();
        }
    }

    public void SearchEmployeeDetails() {
        logger.info("======== Inside SearchEmployeeDetails =======");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();

            GetEmployeeDetailRequest detailRequest = new GetEmployeeDetailRequest();
            if ((idEmp == null || idEmp.equalsIgnoreCase("")) && (idLocation == null || idLocation.equalsIgnoreCase("")) && (idStore == null || idStore.equalsIgnoreCase("")) && (idZone == null || idZone.equalsIgnoreCase(""))) {
                detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.ALL);
            } else {
                if (idEmp != null && !idEmp.isEmpty()) {
                    detailRequest.setEmpName(idEmp.trim());
                    detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.EMP_ID);
                } else if ((idLocation == null ? "-1" != null : !idLocation.equals("-1")) && !idLocation.isEmpty()) {
                    detailRequest.setLocationID(idLocation.trim());
                    detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.EMP_LOCATION);
                } else if (idStore != null && !idStore.isEmpty()) {
                    detailRequest.setStoreCode(idStore.trim());
                    detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.EMP_SITE);
                } else if ((idZone == null ? "-1" != null : !idZone.equals("-1")) && !idZone.isEmpty()) {
                    detailRequest.setZoneId(idZone.trim());
                    detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.EMP_ZONE);
                } else if ((idRole == null ? "-1" != null : !idRole.equals("-1")) && !idRole.isEmpty()) {
                    detailRequest.setRoleId(idRole);
                    detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.EMP_ROLE);
                } else {
                    detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.ALL);
                }
            }

            List<EmployeeVo> lstSearchData = null;
            String rows = request.getParameter("rows");
            String pageno = request.getParameter("page");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");

            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            Integer pageNo = null;

            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);

                detailRequest.setPage(pageNo);
                detailRequest.setStartIndex(startPageIndex.intValue());



                GetEmployeeDetailResponse searchResponse = ServiceMaster.getUserMasterService().searchEmployeeDetail(detailRequest);
                logger.info("Get the Response : " + searchResponse.getResp().getMsg());
                if (searchResponse.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    lstSearchData = searchResponse.getEmployeeVoList();
                    totalCount = searchResponse.getEmployeeListCount();

                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            } else {
                lstSearchData = Collections.<EmployeeVo>emptyList();
            }
            if (sidx.equals("empcode")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getEmpCode().toString().compareToIgnoreCase(p2.getEmpCode().toString());
                    }
                });
            } else if (sidx.equals("empName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getEmpName().toString().compareToIgnoreCase(p2.getEmpName().toString());
                    }
                });
            } else if (sidx.equals("format")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getStoreVO().getFormat().toString().compareToIgnoreCase(p2.getStoreVO().getFormat().toString());
                    }
                });
            } else if (sidx.equals("number")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getContactno().toString().compareToIgnoreCase(p2.getContactno().toString());
                    }
                });
            } else if (sidx.equals("emailId")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getEmailId().compareToIgnoreCase(p2.getEmailId().toString());
                    }
                });
            } else if (sidx.equals("sitecode")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getStoreVO().getStoreID().toString().compareToIgnoreCase(p2.getStoreVO().getStoreID().toString());
                    }
                });
            } else if (sidx.equals("city")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getStoreVO().getCity().toString().compareToIgnoreCase(p2.getStoreVO().getCity().toString());
                    }
                });
            } else if (sidx.equals("region")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getStoreVO().getRegion().toString().compareToIgnoreCase(p2.getStoreVO().getRegion().toString());
                    }
                });
            } else if (sidx.equals("zone")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getStoreVO().getZoneName().toString().compareToIgnoreCase(p2.getStoreVO().getZoneName().toString());
                    }
                });
            } else if (sidx.equals("location")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getStoreVO().getLocationName().toString().compareToIgnoreCase(p2.getStoreVO().getLocationName().toString());
                    }
                });
            } else if (sidx.equals("repmanager")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getReportingTo().toString().compareToIgnoreCase(p2.getReportingTo().toString());
                    }
                });
            } else if (sidx.equals("taskmag")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getTaskmanager().toString().compareToIgnoreCase(p2.getTaskmanager().toString());
                    }
                });
            } else if (sidx.equals("role")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        EmployeeVo p1 = (EmployeeVo) o1;
                        EmployeeVo p2 = (EmployeeVo) o2;
                        return p1.getRoleName().toString().compareToIgnoreCase(p2.getRoleName().toString());
                    }
                });
            }
            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }


            for (EmployeeVo vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getEmpId());
                cell.add(vo.getEmpId());
                cell.add(vo.getEmpCode());
                cell.add(vo.getEmpName());
                cell.add(vo.getStoreVO().getFormat());
                cell.add(vo.getContactno());
                cell.add(vo.getEmailId());
                cell.add(vo.getStoreVO().getStoreID());
                cell.add(vo.getStoreVO().getCity());
                cell.add(vo.getStoreVO().getRegion());
                cell.add(vo.getStoreVO().getZoneName());
                cell.add(vo.getStoreVO().getLocationName());
                if (vo.getDeptName() == null) {
                    cell.add("-");
                } else {
                    cell.add(vo.getDeptName());
                }

                cell.add(vo.getReportingTo());
                cell.add(vo.getTaskmanager());
                cell.add(vo.getRoleName());

                if (vo.isIsBlocked() == Boolean.FALSE) {
                    cell.add("Active");
                } else if (vo.isIsBlocked() == Boolean.TRUE) {
                    cell.add("Blocked");
                } else {
                    cell.add("-");
                }
                cell.add("-");
                cell.add("-");
                cell.add("-");
                cell.add("-");

                cellobj.put(WebConstants.CELL, cell);
                cell.clear();
                cellarray.add(cellobj);
            }

            responcedata.put(WebConstants.TOTAL, pageCount);
            responcedata.put(WebConstants.PAGE, pageNo);
            responcedata.put(WebConstants.RECORDS, totalCount);
            responcedata.put(WebConstants.ROWS, cellarray);
            out.print(responcedata);
        } catch (Exception e) {
            logger.info("Exception in SearchEmployeeDetails() of SearchUserAction Page ----- ");
            e.printStackTrace();

        }
    }

    public void BlockEmployee() {
        logger.info("======== Inside updating user ========");
        try {
            String allgridData = getSelIdToDelete();
            String[] strArr = allgridData.split(",");

            List<EmployeeVo> list = new ArrayList<EmployeeVo>();
            EmployeeVo employeeVo = null;
            for (int i = 0; i < strArr.length; i++) {
                employeeVo = new EmployeeVo();
                employeeVo.setEmpId(strArr[i]);
                list.add(employeeVo);
            }
            Resp resp = ServiceMaster.getUserMasterService().deleteUser(list);
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                logger.info("Success..." + resp.getMsg());

            } else {
                logger.info("Failure.." + resp.getMsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception in BlockEmployee action : " + e.getMessage());

        }

    }

    public void downloadEmployeeMasterData() {
        logger.info("==== Inside downloadEmployeeMasterData..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Employee_Detail.csv";
            sb.append("Employees Details.");
            sb.append("\n");
            sb.append("\n");
            sb.append("Employee Code");
            sb.append(",");
            sb.append("Employee Name");
            sb.append(",");
            sb.append("Format");
            sb.append(",");
            sb.append("Contact No");
            sb.append(",");
            sb.append("Email Id");
            sb.append(",");
            sb.append("Site Code");
            sb.append(",");
            sb.append("City");
            sb.append(",");
            sb.append("Region");
            sb.append(",");
            sb.append("Zone");
            sb.append(",");
            sb.append("Location");
            sb.append(",");
            sb.append("Department");
            sb.append(",");
            sb.append("Reporting Manager");
            sb.append(",");
            sb.append("Task Manager");
            sb.append(",");
            sb.append("Role");
            sb.append(",");
            sb.append("Employee Status");
            sb.append("\n");
            GetEmployeeDetailRequest detailRequest = new GetEmployeeDetailRequest();
            detailRequest.setEmployeeSearchEnum(EmployeeSearchEnum.ALL_DOWNLOAD);
            List<EmployeeVo> lstSearchData = null;
            GetEmployeeDetailResponse searchResponse = ServiceMaster.getUserMasterService().searchEmployeeDetail(detailRequest);
            if (searchResponse.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                lstSearchData = searchResponse.getEmployeeVoList();
            } else {
                lstSearchData = Collections.<EmployeeVo>emptyList();
            }
            for (EmployeeVo vo : lstSearchData) {

                //sb.append(vo.getEmpId()).append(",");
                sb.append(vo.getEmpCode()).append(",");
                sb.append(vo.getEmpName()).append(",");
                sb.append(vo.getStoreVO().getFormat()).append(",");
                sb.append(vo.getContactno()).append(",");
                sb.append(vo.getEmailId()).append(",");
                sb.append(vo.getStoreVO().getStoreID()).append(",");
                sb.append(vo.getStoreVO().getCity()).append(",");
                sb.append(vo.getStoreVO().getRegion()).append(",");
                sb.append(vo.getStoreVO().getZoneName()).append(",");
                sb.append(vo.getStoreVO().getLocationName()).append(",");
                if (vo.getDeptName() == null) {
                    sb.append("-").append(",");
                } else {
                    String deptname = vo.getDeptName().replace(",", "|");
                    sb.append(deptname).append(",");
                }
                if (vo.getReportingTo() != null) {
                    sb.append(vo.getReportingTo()).append(",");
                } else {
                    sb.append("-").append(",");
                }
                if (vo.getTaskmanager() != null) {
                    sb.append(vo.getTaskmanager()).append(",");
                } else {
                    sb.append("-").append(",");
                }
                sb.append(vo.getRoleName()).append(",");
                if (vo.isIsBlocked() == Boolean.FALSE) {
                    sb.append("Active").append(",");
                } else if (vo.isIsBlocked() == Boolean.TRUE) {
                    sb.append("Blocked").append(",");
                } else {
                    sb.append("-").append(",");
                }
                sb.append("\n");
            }


            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

            response.setContentType("text/csv");

            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }

            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }
    }

    public String getSelIdToDelete() {
        return selIdToDelete;
    }

    public void setSelIdToDelete(String selIdToDelete) {
        this.selIdToDelete = selIdToDelete;
    }

    public String getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getIdZone() {
        return idZone;
    }

    public void setIdZone(String idZone) {
        this.idZone = idZone;
    }

    public Map<String, String> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<String, String> locationMap) {
        this.locationMap = locationMap;
    }

    public Map<String, String> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(Map<String, String> zoneMap) {
        this.zoneMap = zoneMap;
    }

    public Map<String, String> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, String> roleMap) {
        this.roleMap = roleMap;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }
}
