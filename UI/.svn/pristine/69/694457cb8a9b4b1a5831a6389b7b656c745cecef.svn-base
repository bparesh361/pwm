/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MchVo;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
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
public class CategoryMCHMasterAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(CategoryMCHMasterAction.class.getName());
    PrintWriter out;
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Category MCH Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of CategoryMCHMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getMCHCategoryDtl() {
        logger.info("====== Inside getMCHCategoryDtl =======");
        JSONObject responcedata = new JSONObject();
        JSONArray cellarray = new JSONArray();
        JSONArray cell = new JSONArray();
        JSONObject cellobj = new JSONObject();
        try {
            out = response.getWriter();
            List<MchVo> listMCHvo = ServiceMaster.getCategoryMCHService().getAllCategoryMCHdtl();
            if (listMCHvo.size() > 0 && !listMCHvo.isEmpty()) {
                for (MchVo vo : listMCHvo) {
                    cellobj.put(WebConstants.ID, vo.getMCCode());
                    cell.add(vo.getCategoryName());
                    cell.add(vo.getSubCategoryName());
                    cell.add(vo.getMCCode());
                    cell.add(vo.getMCName());
                    if (vo.isIsMCActive()== Boolean.TRUE) {
                        cell.add(WebConstants.BLOCKED_STR);
                    } else {
                        cell.add(WebConstants.ACTIVE_STR);
                    }
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, listMCHvo.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
            }

        } catch (Exception e) {
            logger.info("Exception in getMCHCategoryDtl() of CategoryMCHMasterAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void blockMCDetail() {
        System.out.println("==== Inside blockMCDetail Action ===== ");
        try {
            PrintWriter printWriter = response.getWriter();
            JSONObject jSONObject = new JSONObject();
            String mc_code = request.getParameter("selIdToDelete");
            String status = request.getParameter("status");
            Boolean sendstatus;
            if (WebConstants.ACTIVE_STR.equalsIgnoreCase(status)) {
                sendstatus = Boolean.TRUE;
            } else {
                sendstatus = Boolean.FALSE;
            }
            Resp resp = ServiceMaster.getCategoryMCHService().blockUnblockMCH(mc_code, sendstatus);
            if (resp.getRespCode() == RespCode.FAILURE) {
                jSONObject.put("flag", resp.getRespCode().value().toString());
                jSONObject.put("msg", resp.getMsg());
            } else {
                jSONObject.put("flag", resp.getRespCode().value().toString());
                jSONObject.put("msg", resp.getMsg());
            }
            printWriter.println(jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadSampleCategoryFile() {
        logger.info("==== Inside Download Sample Category MCH File..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Category_MCH_sample.csv";
            sb.append("Category");
            sb.append(",");
            sb.append("Sub Category");
            sb.append(",");
            sb.append("MC Code");
            sb.append(",");
            sb.append("MC Name");
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

    public void downloadMasterCategoryFile() {
        logger.info("==== Inside Download Master Category MCH File..");
        try {

            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Category_MCH_Master.csv";
            sb.append("Category");
            sb.append(",");
            sb.append("Sub Category");
            sb.append(",");
            sb.append("MC Code");
            sb.append(",");
            sb.append("MC Name");
            sb.append("\n");

            List<MchVo> listMCHvo = ServiceMaster.getCategoryMCHService().getAllCategoryMCHdtl();
            logger.info("Response List Size : " + listMCHvo.size());
            if (listMCHvo.size() > 0 && !listMCHvo.isEmpty()) {
                for (MchVo vo : listMCHvo) {
                    sb.append(vo.getCategoryName());
                    sb.append(",");
                    sb.append(vo.getSubCategoryName());
                    sb.append(",");
                    sb.append(vo.getMCCode());
                    sb.append(",");
                    sb.append(vo.getMCName());
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
            // logger.info("====== File Writing is Completed ===== ");
            // return SUCCESS;
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
            ex.printStackTrace();
            // return ERROR;
        }

    }
    private File categoryMCHFile;

    public File getCategoryMCHFile() {
        return categoryMCHFile;
    }

    public void setCategoryMCHFile(File categoryMCHFile) {
        this.categoryMCHFile = categoryMCHFile;
    }

    public String uploadCategoryDtl() {
        logger.info("==== Upload Category Master File ====");
        try {
            List<MchVo> mchVosList = new ArrayList<MchVo>();
            MchVo mchVo = null;

            String cName = "", subcategoryName = "", Mccode = "", mcName = "";
            Set<String> mchIDSet = new HashSet<String>();
            FileInputStream fileIn = null;
            if (categoryMCHFile != null) {
                if (categoryMCHFile.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(categoryMCHFile.toString());
                DataInputStream in = new DataInputStream(fileIn);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine = null;
                boolean checkHeader = false;
                int counter = 0;

                while ((strLine = br.readLine()) != null) {
                    String[] strValue = strLine.split(",");
                    if (checkHeader == false) {
                        if (strValue.length != 4) {
                            logger.info("No Of required header fields should be 4. ");
                            addActionError("No Of required header fields should be 4. Please Use template.");
                            return INPUT;
                        }
                        if (!strValue[0].trim().equalsIgnoreCase("Category")) {
                            logger.info("Invalid CSV Header: " + strValue[0]);
                            addActionError("Invalid CSV Header: '" + strValue[0] + "'.\n Header Name Should Be : Category.");
                            return INPUT;
                        } else if (!strValue[1].trim().equalsIgnoreCase("Sub Category")) {
                            logger.info("Invalid CSV Header: " + strValue[1]);
                            addActionError("Invalid CSV Header: '" + strValue[1] + "'.\n Header Name Should Be : Sub Category.");
                            return INPUT;
                        } else if (!strValue[2].trim().equalsIgnoreCase("MC Code")) {
                            logger.info("Invalid CSV Header: " + strValue[2]);
                            addActionError("Invalid CSV Header: '" + strValue[2] + "'.\n Header Name Should Be : MC Code.");
                            return INPUT;
                        } else if (!strValue[3].trim().equalsIgnoreCase("MC Name")) {
                            logger.info("Invalid CSV Header: " + strValue[3]);
                            addActionError("Invalid CSV Header: '" + strValue[3] + "'.\n Header Name Should Be : MC Name.");
                            return INPUT;
                        }
                        checkHeader = true;
                    } else {
                        counter++;
                        if (strValue.length <= 3) {
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

                        cName = strValue[0].toString();
                        subcategoryName = strValue[1].toString();
                        Mccode = strValue[2].toString();
                        mcName = strValue[3].toString();
                        if (!mchIDSet.add(Mccode)) {
                            logger.info("Duplicate MC Code : " + Mccode + " At Line NO : " + counter);
                            addActionError("Duplicate MC Code : " + Mccode + " found at line NO : " + counter);
                            return INPUT;
                        }
                        if (Mccode.length() > 10) {
                            logger.info("MC Code : " + Mccode + " Length Exceeded At Line NO : " + counter);
                            addActionError("MC Code Length Should Not Be Greater Than 10 Characters at line NO : " + counter);
                            return INPUT;
                        }


                        mchVo = new MchVo();
                        cName = cName.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        mchVo.setCategoryName(cName);
                        Mccode = Mccode.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        mchVo.setMCCode(Mccode);
                        mcName = mcName.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        mchVo.setMCName(mcName);
                        subcategoryName = subcategoryName.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
                        mchVo.setSubCategoryName(subcategoryName);
                        mchVosList.add(mchVo);
                    }
                }
                if (mchVosList.size() > 0 && mchVosList != null) {
                    Resp resp = ServiceMaster.getCategoryMCHService().submitCategoryMCHDetails(mchVosList);
                    logger.info("File Upload Response : " + resp.getMsg() + " with Code : " + resp.getRespCode());
                    if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                        addActionMessage(resp.getMsg().toString());
                        return SUCCESS;
                    } else {

                        addActionError(resp.getMsg().toString());
                        return INPUT;
                    }
                } else {
                    addActionError("No Category record found in file.");
                    return INPUT;
                }
            } else {
                addActionError("No Category record found in file.");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error  : " + e.getMessage());
            return ERROR;
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    public static void main(String[] args) {
        //String str = "Hello +&-^ my + - friends% ^&$#@~@#$%^&*()= ^^-- ^^^ +!";
        String str = "Test &,test>";
        System.out.println("str  1   " + str);
        str = str.replaceAll(WebConstants.REMOVE_SPECIAL_CHAR, "");
        System.out.println("str " + str);
    }
}
