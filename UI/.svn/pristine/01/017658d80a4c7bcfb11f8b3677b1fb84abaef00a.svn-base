/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.user.action;

import com.fks.promo.comm.service.Resp;
import com.fks.promo.master.service.CreateUserMCHProfileDtlResp;
import com.fks.promo.master.service.CreateUserMCHProfileReq;
import com.fks.promo.master.service.GetUserWiseProfileResp;
import com.fks.promo.master.service.MchVo;
import com.fks.promo.master.service.ProfileVO;

import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletOutputStream;
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
public class MapUserMCHAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(MapUserMCHAction.class.getName());
    private HttpServletResponse response;
    private HttpServletRequest request;
    PrintWriter out;
    Object strUserID;
    private String EmpID, sendEmpId, sendProfileId;
    private List<MchVo> ListMchF4 = new ArrayList<MchVo>();
    private File userMCHF1File, userMCHF2File, userMCHF3File, userMCHF5File;

    public String viewuserMapjsp() {
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            logger.info("================Welcome to  Map User MCH Action Page ======== !  " + strUserID);
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in viewSearchjsp() of MapUserMCHAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String uploadUserMCHdtl() throws FileNotFoundException, IOException {
        logger.info("========= Inside Upload UserMCH F1 File =========");
        try {
            if (sendProfileId == null && sendEmpId == null) {
                addActionError("Please Select Employee.");
                return INPUT;
            }
            CreateUserMCHProfileReq req = req = new CreateUserMCHProfileReq();
            // List<MchVo> mchVosList = new ArrayList<MchVo>();
            MchVo mchVo = null;
            FileInputStream fileIn = null;
            String mchCode = "";
            Set<String> mchIDSet = new HashSet<String>();
            if (sendProfileId.equalsIgnoreCase("2")) {
                if (userMCHF1File.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(userMCHF1File.toString());
            }
            if (sendProfileId.equalsIgnoreCase("3")) {
                if (userMCHF2File.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(userMCHF2File.toString());
            }
            if (sendProfileId.equalsIgnoreCase("4")) {
                if (userMCHF3File.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(userMCHF3File.toString());
            }
            if (sendProfileId.equalsIgnoreCase("6")) {
                if (userMCHF5File.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    return INPUT;
                }
                fileIn = new FileInputStream(userMCHF5File.toString());
            }
            if (fileIn == null) {
                addActionError("Please Select File to upload.");
                return INPUT;
            }


            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            boolean checkHeader = false;
            int counter = 0;
            while ((strLine = br.readLine()) != null) {
                String[] strValue = strLine.split(",");
                if (checkHeader == false) {
                    if (strValue.length != 1) {
                        addActionError("No Of required header fields should be 1. Please Use template.");
                        return INPUT;
                    }
                    if (!strValue[0].trim().equalsIgnoreCase("MC Code")) {
                        addActionError("Invalid CSV Header: '" + strValue[0] + "'.\n Header Name Should Be : MC Code.");
                        return INPUT;
                    }
                    checkHeader = true;
                } else {
                    counter++;
                    if (strValue.length == 0) {
                        addActionError("Field should not be blank at line No : " + counter);
                        return INPUT;
                    }
                    mchCode = strValue[0].toString();
                    if (!mchIDSet.add(mchCode)) {
                        addActionError("Duplicate MC Code : " + mchCode + " found at line NO : " + counter);
                        return INPUT;
                    }

                    mchVo = new MchVo();
                    mchVo.setMCCode(mchCode);
                    //mchVosList.add(mchVo);
                    req.getMchvoList().add(mchVo);

                }
            }

            req.setEmpID(sendEmpId);
            req.setProfileId(sendProfileId);

            if (req.getMchvoList().size() > 0 && req.getMchvoList() != null) {
                CreateUserMCHProfileDtlResp resp = ServiceMaster.getUserMasterService().submitUserMCHDetail(req);
                if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    addActionMessage(resp.getResp().getMsg());
                    return SUCCESS;
                } else {
                    addActionError("Error While Uploading File. Please Open File to Check Error.");
                    FileInputStream fileread = new FileInputStream(resp.getFilepath());
                    DataInputStream inread = new DataInputStream(fileread);
                    String fileName = sendEmpId + "_mch_error_file.csv";
                    response.setContentType("text/csv");
                    response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inread.read(buf)) > 0) {
                        response.getOutputStream().write(buf, 0, len);
                    }
                    inread.close();
                    response.getOutputStream().flush();
                    return INPUT;
                }
            } else {
                addActionError("No MCH record found in file.");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error  : " + e.getMessage());
            return ERROR;
        }
    }

    public void getUserProfileDtl() {
        logger.info("===== Inside getUserProfileDtl ======== ");
        JSONObject responseData = null;
        JSONObject cellObject = null;

        try {
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            List<String> cellProfileIdArray = new ArrayList<String>();
            GetUserWiseProfileResp resp = ServiceMaster.getUserMasterService().getUserWiseProfileDtl(EmpID);
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                if (!resp.getProfileVOLIst().isEmpty()) {
                    for (ProfileVO vo : resp.getProfileVOLIst()) {
                        cellProfileIdArray.add(vo.getProfileID().toString());
                    }
                    cellObject.put("profileIdList", cellProfileIdArray);
                    responseData.put("rows", cellObject);
                    out.println(responseData);
                }
            }
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }
    }

    public  void downloadUserMCHMappingFile(){
        logger.info("======= Inside downloadUserMCHMappingFile =========");
        try {
            com.fks.promo.master.service.Resp resp= ServiceMaster.getCategoryMCHService().getUserMCHDtlExcel();
            String filePath=resp.getMsg();
            System.out.println("&&&&& File Path : "+ filePath);
             File file = new File(filePath);
            byte[] buffer = new byte[1024];
            FileInputStream fin = new FileInputStream(file);
            DataInputStream din = new DataInputStream(fin);
            ServletOutputStream sout = response.getOutputStream();
            response.setContentType("application/csv");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
            while (din != null && din.read(buffer) != -1) {
                sout.write(buffer);
            }

            din.close();
            sout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void downloadUserMchF1File() {
        logger.info("==== Inside Download User MCH F1 File for ..User Id : " + sendEmpId);
        try {
            List<MchVo> ListMchF1 = new ArrayList<MchVo>();
            GetUserWiseProfileResp resp = ServiceMaster.getUserMasterService().getUserAndProfileWiseMchDtl(sendEmpId, "2");
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                if (!resp.getListUserMchF1().isEmpty()) {
                    ListMchF1 = resp.getListUserMchF1();
                }
            }
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = sendEmpId + "_MCH_F1.csv";
            sb.append("MC Code");

            sb.append("\n");
            for (MchVo s : ListMchF1) {
                sb.append(s.getMCCode());
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

    public void downloadUserMchF2File() {
        logger.info("==== Inside Download User MCH F2 File for ..User Id : " + sendEmpId);
        try {
            List<MchVo> ListMchF2 = new ArrayList<MchVo>();
            GetUserWiseProfileResp resp = ServiceMaster.getUserMasterService().getUserAndProfileWiseMchDtl(sendEmpId, "3");
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                if (!resp.getListUserMchF2().isEmpty()) {
                    ListMchF2 = resp.getListUserMchF2();
                }
            }
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = sendEmpId + "_MCH_F2.csv";
            sb.append("MC Code");

            sb.append("\n");
            for (MchVo s : ListMchF2) {
                sb.append(s.getMCCode());
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

    public void downloadUserMchF3File() {
        logger.info("==== Inside Download User MCH F3 File for ..User Id : " + sendEmpId);
        try {
            List<MchVo> ListMchF3 = new ArrayList<MchVo>();
            GetUserWiseProfileResp resp = ServiceMaster.getUserMasterService().getUserAndProfileWiseMchDtl(sendEmpId, "4");
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                if (!resp.getListUserMchF3().isEmpty()) {
                    ListMchF3 = resp.getListUserMchF3();
                }
            }
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = sendEmpId + "_MCH_F3.csv";
            sb.append("MC Code");

            sb.append("\n");
            for (MchVo s : ListMchF3) {
                sb.append(s.getMCCode());
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

    public void downloadUserMchF5File() {
        logger.info("==== Inside Download User MCH F5 File for ..User Id : " + sendEmpId);
        try {
            List<MchVo> ListMchF5 = new ArrayList<MchVo>();
            GetUserWiseProfileResp resp = ServiceMaster.getUserMasterService().getUserAndProfileWiseMchDtl(sendEmpId, "6");
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                if (!resp.getListUserMchF5().isEmpty()) {
                    ListMchF5 = resp.getListUserMchF5();
                }
            }
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = sendEmpId + "_MCH_F5.csv";
            sb.append("MC Code");

            sb.append("\n");
            for (MchVo s : ListMchF5) {
                sb.append(s.getMCCode());
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

    public String getSendProfileId() {
        return sendProfileId;
    }

    public void setSendProfileId(String sendProfileId) {
        this.sendProfileId = sendProfileId;
    }

    public String getSendEmpId() {
        return sendEmpId;
    }

    public void setSendEmpId(String sendEmpId) {
        this.sendEmpId = sendEmpId;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String EmpID) {
        this.EmpID = EmpID;
    }

    public File getUserMCHF1File() {
        return userMCHF1File;
    }

    public void setUserMCHF1File(File userMCHF1File) {
        this.userMCHF1File = userMCHF1File;
    }

    public File getUserMCHF2File() {
        return userMCHF2File;
    }

    public void setUserMCHF2File(File userMCHF2File) {
        this.userMCHF2File = userMCHF2File;
    }

    public File getUserMCHF3File() {
        return userMCHF3File;
    }

    public void setUserMCHF3File(File userMCHF3File) {
        this.userMCHF3File = userMCHF3File;
    }

    public File getUserMCHF5File() {
        return userMCHF5File;
    }

    public void setUserMCHF5File(File userMCHF5File) {
        this.userMCHF5File = userMCHF5File;
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
