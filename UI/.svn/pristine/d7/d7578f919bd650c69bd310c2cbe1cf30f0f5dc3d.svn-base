/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.promo.master.service.StoreVO;
import com.fks.promo.master.service.SubmitOrganizationResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.RefreshCachedMapUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.OrgFormVo;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class OrganizationMasterAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(OrganizationMasterAction.class.getName());
    PrintWriter out;
    private HttpServletResponse response;
    private HttpServletRequest request;
    public String strUserID;
    private OrgFormVo formVo;
    private RefreshCachedMapUtil refCache;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Organization Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID);

            if (strUserID != null) {
                logger.info("Valid User .");
                getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                // formVo = new OrgFormVo();

                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of OrganizationMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getOrganizationDtl() {
        logger.info("====== Inside getOrganizationDtl =======");
        JSONObject responcedata = new JSONObject();
        JSONArray cellarray = new JSONArray();
        JSONArray cell = new JSONArray();
        JSONObject cellobj = new JSONObject();
        try {
            out = response.getWriter();
            List<StoreVO> listStoreVo = ServiceMaster.getOrganizationMasterService().getAllOrganizationDtl();
            if (listStoreVo.size() > 0 && !listStoreVo.isEmpty()) {
                for (StoreVO vo : listStoreVo) {
                    cellobj.put(WebConstants.ID, vo.getStoreID());
                    cell.add(vo.getZoneName());
                    cell.add(vo.getState());
                    cell.add(vo.getRegion());
                    cell.add(vo.getCity());
                    cell.add(vo.getStoreID());
                    cell.add(vo.getStoreDesc());
                    cell.add(vo.getLocationName());
                    cell.add(vo.getStoreClass());
                    cell.add(vo.getFormat());

                    if (vo.isIsStoreBlocked()== Boolean.TRUE) {
                        cell.add(WebConstants.BLOCKED_STR);
                    } else {
                        cell.add(WebConstants.ACTIVE_STR);
                    }

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, listStoreVo.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("Response Sent!!");
            }

        } catch (Exception e) {
            logger.info("Exception in getOrganizationDtl() of OrganizationMasterAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void downloadSampleOrgFile() {
        logger.info("==== Inside Download Sample Organization File..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Org_sample.csv";
            sb.append("Zone Code");
            sb.append(",");
            sb.append("State");
            sb.append(",");
            sb.append("Region");
            sb.append(",");
            sb.append("City");
            sb.append(",");
            sb.append("Site Code");
            sb.append(",");
            sb.append("Site Description");
            sb.append(",");
            sb.append("Location");
            sb.append(",");
            sb.append("Store Class");
            sb.append(",");
            sb.append("Format");
            sb.append("\n");

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

    public String downlodMasterOrgFile() {
        logger.info("==== Inside Download Sample Organization File..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Org_Master.csv";
            sb.append("Zone Code");
            sb.append(",");
            sb.append("State");
            sb.append(",");
            sb.append("Region");
            sb.append(",");
            sb.append("City");
            sb.append(",");
            sb.append("Site Code");
            sb.append(",");
            sb.append("Site Description");
            sb.append(",");
            sb.append("Location");
            sb.append(",");
            sb.append("Store Class");
            sb.append(",");
            sb.append("Format");
            sb.append("\n");
            List<StoreVO> listStoreVo = ServiceMaster.getOrganizationMasterService().getAllOrganizationDtl();
            if (listStoreVo.size() > 0 && !listStoreVo.isEmpty()) {
                for (StoreVO vo : listStoreVo) {
                    sb.append(vo.getZoneCode());
                    sb.append(",");
                    sb.append(vo.getState());
                    sb.append(",");
                    sb.append(vo.getRegion());
                    sb.append(",");
                    sb.append(vo.getCity());
                    sb.append(",");
                    sb.append(vo.getStoreID());
                    sb.append(",");
                    sb.append(vo.getStoreDesc());
                    sb.append(",");
                    sb.append(vo.getLocationName());
                    sb.append(",");
                    sb.append(vo.getStoreClass());
                    sb.append(",");
                    sb.append(vo.getFormat());
                    sb.append("\n");
                }
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
            return SUCCESS;
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
            return ERROR;
        }
    }
    private File orgFileName;

    public File getOrgFileName() {
        return orgFileName;
    }

    public void setOrgFileName(File orgFileName) {
        this.orgFileName = orgFileName;
    }

    public String uploadOrganizationDtl() {
        logger.info("==== Upload Organization Master File ====");
        try {
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            List<StoreVO> storeVosList = new ArrayList<StoreVO>();
            StoreVO storeVo = null;

            String fzone = "", fregion = "", fstate = "", fcity = "", fsitecode = "", fsitedesc = "", fLcoation = "", fstoreclass = "", fformat = "";
            Set<String> storeIDset = new HashSet<String>();
            FileInputStream fileIn = null;
            if (orgFileName != null) {
                if (orgFileName.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(orgFileName.toString());
                DataInputStream in = new DataInputStream(fileIn);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine = null;
                boolean checkHeader = false;
                int counter = 0;

                while ((strLine = br.readLine()) != null) {
                    String[] strValue = strLine.split(",");
                    if (checkHeader == false) {
                        if (strValue.length != 9) {
                            logger.info("No Of required header fields should be 9. ");
                            addActionError("No Of required header fields should be 9. Please Use template.");
                            return INPUT;
                        }
                        if (!strValue[0].trim().equalsIgnoreCase("Zone Code")) {
                            logger.info("Invalid CSV Header: " + strValue[0]);
                            addActionError("Invalid CSV Header: '" + strValue[0] + "'.\n Header Name Should Be : Zone.");
                            return INPUT;
                        } else if (!strValue[1].trim().equalsIgnoreCase("State")) {
                            logger.info("Invalid CSV Header: " + strValue[2]);
                            addActionError("Invalid CSV Header: '" + strValue[2] + "'.\n Header Name Should Be : State.");
                            return INPUT;
                        } else if (!strValue[2].trim().equalsIgnoreCase("Region")) {
                            logger.info("Invalid CSV Header: " + strValue[1]);
                            addActionError("Invalid CSV Header: '" + strValue[1] + "'.\n Header Name Should Be : Sub Region.");
                            return INPUT;
                        } else if (!strValue[3].trim().equalsIgnoreCase("City")) {
                            logger.info("Invalid CSV Header: " + strValue[3]);
                            addActionError("Invalid CSV Header: '" + strValue[3] + "'.\n Header Name Should Be : City.");
                            return INPUT;
                        } else if (!strValue[4].trim().equalsIgnoreCase("Site Code")) {
                            logger.info("Invalid CSV Header: " + strValue[4]);
                            addActionError("Invalid CSV Header: '" + strValue[4] + "'.\n Header Name Should Be : Site Code.");
                            return INPUT;
                        } else if (!strValue[5].trim().equalsIgnoreCase("Site Description")) {
                            logger.info("Invalid CSV Header: " + strValue[5]);
                            addActionError("Invalid CSV Header: '" + strValue[5] + "'.\n Header Name Should Be : Site Description.");
                            return INPUT;
                        } else if (!strValue[6].trim().equalsIgnoreCase("Location")) {
                            logger.info("Invalid CSV Header: " + strValue[6]);
                            addActionError("Invalid CSV Header: '" + strValue[6] + "'.\n Header Name Should Be : Location.");
                            return INPUT;
                        } else if (!strValue[7].trim().equalsIgnoreCase("Store Class")) {
                            logger.info("Invalid CSV Header: " + strValue[7]);
                            addActionError("Invalid CSV Header: '" + strValue[7] + "'.\n Header Name Should Be : Store Class.");
                            return INPUT;
                        } else if (!strValue[8].trim().equalsIgnoreCase("Format")) {
                            logger.info("Invalid CSV Header: " + strValue[8]);
                            addActionError("Invalid CSV Header: '" + strValue[8] + "'.\n Header Name Should Be : Format.");
                            return INPUT;
                        }
                        checkHeader = true;
                    } else {
                        counter++;
                        if (strValue.length <= 8) {
                            logger.info("Field should not be blank.");
                            addActionError("Field should not be blank at line No : " + counter);
                            return INPUT;
                        }
                        for (int i = 0; i < strValue.length; i++) {
                            if (strValue[i].isEmpty()) {
                                logger.info("Field should not be blank.");
                                addActionError("Field should not be blank at line No : " + counter);
                                return INPUT;
                            }
                        }

                        fzone = strValue[0].toString();
                        fstate = strValue[1].toString();
                        fregion = strValue[2].toString();

                        fcity = strValue[3].toString();
                        fsitecode = strValue[4].toString();
                        fsitedesc = strValue[5].toString();
                        fLcoation = strValue[6].toString();
                        fstoreclass = strValue[7].toString();
                        fformat = strValue[8].toString();

                        if (!storeIDset.add(fsitecode)) {
                            logger.info("Duplicate Store Code : " + fsitecode + " At Line NO : " + counter);
                            addActionError("Duplicate Store Code : " + fsitecode + " found at line NO : " + counter);
                            return INPUT;
                        }

                        storeVo = new StoreVO();
                        fcity = fcity.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        storeVo.setCity(fcity.toUpperCase().trim());
                        storeVo.setCreatedByID(new Long(strUserID));
                        fformat = fformat.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        storeVo.setFormat(fformat.toUpperCase().trim());
                        storeVo.setLocationName(fLcoation.trim());
                        fregion = fregion.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        storeVo.setRegion(fregion.toUpperCase().trim());
                        fstate = fstate.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        storeVo.setState(fstate.toUpperCase().trim());
                        fstoreclass = fstoreclass.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        storeVo.setStoreClass(fstoreclass.toUpperCase().trim());
                        fsitedesc = fsitedesc.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        storeVo.setStoreDesc(fsitedesc.toUpperCase().trim());
                        storeVo.setStoreID(fsitecode);
                        storeVo.setZoneName(fzone);
                        storeVosList.add(storeVo);

                    }
                }
                if (storeVosList.size() > 0 && storeVosList != null) {
                    logger.info("======= Inside Submitting File Records.");
                    SubmitOrganizationResp resp = ServiceMaster.getOrganizationMasterService().submitOrganizationDtl(storeVosList);
                    logger.info("File Upload Response : " + resp.getFilePath() + " with Code : " + resp.getResp().getRespCode());
                    if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                        addActionMessage(resp.getResp().getMsg().toString());
                        refCache = new RefreshCachedMapUtil();
                        refCache.refreshStoreMap();
                        return SUCCESS;
                    } else {
                        System.out.println("File Path : " + resp.getFilePath());
                        formVo.setIsuploaderror("1");
                        formVo.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getFilePath());
                        addActionError("Unable to upload file . Please check Error File for details.");
                        return INPUT;
                    }
                } else {
                    addActionError("No Organization record found in file.");
                    return INPUT;
                }
            } else {
                addActionError("No Organization record found in file.");
                return INPUT;
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error  : " + e.getMessage());
            return ERROR;
        }
    }

    public void blockUnblcokOrg() {
        try {
            PrintWriter printWriter = response.getWriter();
            JSONObject jSONObject = new JSONObject();
            logger.info("--------- inside blocking organization value.-----");
            String storeID = request.getParameter("selIdToDelete");
            String status = request.getParameter("status");
            // System.out.println("Store Id : " + storeID + "====== status : " + status);
            Boolean sendstatus;
            if (status.equalsIgnoreCase(WebConstants.ACTIVE_STR)) {
                sendstatus = Boolean.TRUE;
            } else {
                sendstatus = Boolean.FALSE;
            }            
            Resp resp = ServiceMaster.getOrganizationMasterService().updateStoreDetail(storeID, sendstatus);
            if (resp.getRespCode() == RespCode.FAILURE) {
                jSONObject.put("flag", resp.getRespCode().value().toString());
                jSONObject.put("msg", resp.getMsg());
            } else {
                jSONObject.put("flag", resp.getRespCode().value().toString());
                jSONObject.put("msg", resp.getMsg());
            }
            refCache = new RefreshCachedMapUtil();
            refCache.refreshStoreMap();
            printWriter.println(jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrgFormVo getFormVo() {
        return formVo;
    }

    public void setFormVo(OrgFormVo formVo) {
        this.formVo = formVo;
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
