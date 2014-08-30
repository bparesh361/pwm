/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.user.action;

import com.fks.promo.master.service.McUserResp;
import com.fks.promo.master.service.McUserVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.other.action.OtherAction;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class MCHWiseUserDownloadAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(MCHWiseUserDownloadAction.class.getName());
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Object strUserID;

    public String viewMCHUserDownloadjsp() {
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID);
            logger.info("================Welcome to  Search User Action Page ======== !  " + strUserID);
            if (strUserID != null) {                
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
    private String txtmchCode;

    public void getmchUserdata() {
        logger.info("====== inside getmchUserdata ======");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = resp.getWriter();
            String[] strMcCodes = txtmchCode.split(",");
            List<String> mcCodeLst = new ArrayList<String>();
            List<McUserVO> respmcCodeLst = new ArrayList<McUserVO>();
            mcCodeLst.addAll(Arrays.asList(strMcCodes));
            McUserResp respuser = ServiceMaster.getUserMasterService().getMCUsers(mcCodeLst);
            if (respuser.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                respmcCodeLst = respuser.getUserMCList();
                for (McUserVO vo : respmcCodeLst) {
                    cellobj.put(WebConstants.ID, vo.getEmpId());
                    cell.add(vo.getMccode());
                    if (vo.getEmpCode() != null) {
                        cell.add(vo.getEmpCode());
                    } else {
                        cell.add("-");
                    }
                    if (vo.getEmpName() != null) {
                        cell.add(vo.getEmpName());
                    } else {
                        cell.add("-");
                    }
                    if (vo.getEmpContactNo() != null) {
                        cell.add(vo.getEmpContactNo());
                    } else {
                        cell.add("-");
                    }
                    if (vo.getEmpEmailId() != null) {
                        cell.add(vo.getEmpEmailId());
                    } else {
                        cell.add("-");
                    }
                    cell.add(vo.isIsF2User());
                    cell.add(vo.isIsF3User());
                    if (vo.getZone() != null) {
                        cell.add(vo.getZone());
                    } else {
                        cell.add("-");
                    }
                    if (vo.getMcStatus() != null) {
                        cell.add(vo.getMcStatus());
                    } else {
                        cell.add("-");
                    }

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, respmcCodeLst.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloaduserMchdetail() {
        logger.info("@@@@@@@@@ Inside downloaduserMchdetail ======");
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("MC Code").append(",");
            sb.append("Name").append(",");
            sb.append("Email Id").append(",");
            sb.append("Zone").append(",");
            sb.append("Approver From").append(",");
            sb.append("MC Status");
            sb.append("\n");

            String[] strMcCodes = txtmchCode.split(",");
            List<String> mcCodeLst = new ArrayList<String>();
            List<McUserVO> respmcCodeLst = new ArrayList<McUserVO>();
            mcCodeLst.addAll(Arrays.asList(strMcCodes));
            McUserResp respuser = ServiceMaster.getUserMasterService().getMCUsers(mcCodeLst);
            if (respuser.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                respmcCodeLst = respuser.getUserMCList();
                for (McUserVO vo : respmcCodeLst) {

                    sb.append(vo.getMccode()).append(",");
                    //sb.append(vo.getEmpCode()).append(",");
                    if (vo.getEmpName() != null) {
                        sb.append(vo.getEmpName()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.getEmpEmailId() != null) {
                        sb.append(vo.getEmpEmailId()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.getZone() != null) {
                        sb.append(vo.getZone()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.isIsF2User()) {
                        sb.append("L 1").append(",");
                    } else if (vo.isIsF3User()) {
                        sb.append("L 2").append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.getMcStatus() != null) {
                        sb.append(vo.getMcStatus()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }

                    sb.append("\n");
                }
            }
            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
            String currentDate = OtherAction.getDateString(new Date());            
            String fileName = "MC_WISE_APPROVER_" + currentDate + ".csv";
            resp.setContentType("text/csv");
            resp.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) > 0) {
                resp.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            resp.getOutputStream().flush();
            logger.info("======file opening is completed=====");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTxtmchCode() {
        return txtmchCode;
    }

    public void setTxtmchCode(String txtmchCode) {
        this.txtmchCode = txtmchCode;
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
