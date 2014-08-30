/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MchVo;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.SubmitWorkflowResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.WorkFlowVo;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
public class WorkFlowMasterAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(WorkFlowMasterAction.class.getName());
    PrintWriter out;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private String strUserID;
    private WorkFlowVo formVo;

    public WorkFlowVo getFormVo() {
        return formVo;
    }

    public void setFormVo(WorkFlowVo formVo) {
        this.formVo = formVo;
    }

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome Organization Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of WorkFlowMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllWorkflowData() {
        logger.info("======== Inside getAllWorkflowData =========");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MchVo> listMCHvo = ServiceMaster.getCategoryMCHService().getAllCategoryMCHdtl();
            if (listMCHvo.size() > 0 && !listMCHvo.isEmpty()) {
                for (MchVo vo : listMCHvo) {
                    cell.add(vo.getCategoryName());
                    cell.add(vo.getSubCategoryName());
                    cell.add(vo.getMCCode());
                    cell.add(vo.getMCName());
                    if (vo.getLocationName() == null) {
                        cell.add("-");
                    } else {
                        cell.add(vo.getLocationName());
                    }
                    if (vo.getLocationID() == null) {
                        cell.add("-");
                    } else {
                        cell.add(vo.getLocationID());
                    }

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, listMCHvo.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("Response Sent!!");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllWorkflowData() of WorkFlowMasterAction Page ----- ");
            e.printStackTrace();
        }
    }
    private File workflowMCHFile;

    public File getWorkflowMCHFile() {
        return workflowMCHFile;
    }

    public void setWorkflowMCHFile(File workflowMCHFile) {
        this.workflowMCHFile = workflowMCHFile;
    }

    public String submitWorkflowMasterdata() {
        logger.info("===== Inside upload workflow  ======");
        try {

            List<MchVo> mchVosList = new ArrayList<MchVo>();
            MchVo mchVo = null;

            String cName = "", subcategoryName = "", Mccode = "", mcName = "", location = "";
            Set<String> mchIDSet = new HashSet<String>();
            FileInputStream fileIn = null;
            if (workflowMCHFile != null) {
                if (workflowMCHFile.length() == 0) {
                    addActionError("Empty file can not be uploded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(workflowMCHFile.toString());
                DataInputStream in = new DataInputStream(fileIn);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine = null;
                boolean checkHeader = false;
                int counter = 0;

                while ((strLine = br.readLine()) != null) {
                    String[] strValue = strLine.split(",");
                    if (checkHeader == false) {
                        if (strValue.length != 5) {
                            logger.info("No Of required header fields should be 5. ");
                            addActionError("No Of required header fields should be 5. Please Use template.");
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
                        } else if (!strValue[3].trim().equalsIgnoreCase("MC Decription")) {
                            logger.info("Invalid CSV Header: " + strValue[3]);
                            addActionError("Invalid CSV Header: '" + strValue[3] + "'.\n Header Name Should Be : MC Decription.");
                            return INPUT;
                        } else if (!strValue[4].trim().equalsIgnoreCase("Location")) {
                            logger.info("Invalid CSV Header: " + strValue[4]);
                            addActionError("Invalid CSV Header: '" + strValue[4] + "'.\n Header Name Should Be : Location.");
                            return INPUT;
                        }
                        checkHeader = true;
                    } else {
                        counter++;
                        if (strValue.length <= 4) {
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
                        location = strValue[4].toString();
                        if (!mchIDSet.add(Mccode)) {
                            logger.info("Duplicate MC Code : " + Mccode + " At Line NO : " + counter);
                            addActionError("Duplicate MC Code : " + Mccode + " found at line NO : " + counter);
                            return INPUT;
                        }
                        mchVo = new MchVo();
                        mchVo.setCategoryName(cName);
                        mchVo.setMCCode(Mccode);
                        mchVo.setMCName(mcName);
                        mchVo.setSubCategoryName(subcategoryName);
                        mchVo.setLocationName(location);
                        mchVosList.add(mchVo);
                    }
                }
                if (mchVosList.size() > 0 && mchVosList != null) {
                    logger.info("======= Inside Submitting File Records.");
                    SubmitWorkflowResp resp = ServiceMaster.getCategoryMCHService().submitWorkFlowDetail(mchVosList);
                    logger.info("File Upload Response : " + resp.getResp().getMsg() + " with Code : " + resp.getResp().getRespCode() + " === Resp File Path : " + resp.getFilePath());
                    if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                        addActionMessage(resp.getResp().getMsg().toString());
                        return SUCCESS;
                    } else {
                        formVo.setIsuploaderror("1");
                        formVo.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getFilePath());
                        addActionError("Unable to upload file . Please check Error File for details.");

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
            logger.info("Exception in submitworkflowMasterdata() of WorkFlowMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }

    }

    public void downloadWorkflowMasterData() {
        logger.info("===== Inside downloadWorkflowMasterData ======");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Workflow_Master_sample.csv";
            sb.append("Category");
            sb.append(",");
            sb.append("Sub Category");
            sb.append(",");
            sb.append("MC Code");
            sb.append(",");
            sb.append("MC Decription");
            sb.append(",");
            sb.append("Location");
            sb.append("\n");

            List<MchVo> listMCHvo = ServiceMaster.getCategoryMCHService().getAllCategoryMCHdtl();
            if (listMCHvo.size() > 0 && !listMCHvo.isEmpty()) {
                for (MchVo vo : listMCHvo) {
                    sb.append(vo.getCategoryName());
                    sb.append(",");
                    sb.append(vo.getSubCategoryName());
                    sb.append(",");
                    sb.append(vo.getMCCode());
                    sb.append(",");
                    sb.append(vo.getMCName());
                    sb.append(",");
                    if (vo.getLocationName() != null) {
                        sb.append(vo.getLocationName());
                    } else {
                        sb.append("");
                    }
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

        } catch (Exception e) {
            logger.info("Exception in downloadWorkflowMasterData() of WorkFlowMasterAction Page ----- ");
            e.printStackTrace();

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
}
