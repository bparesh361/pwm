/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.Resp;

import com.fks.promo.master.service.RoleVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class RoleMasterAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(RoleMasterAction.class);
    PrintWriter out;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private String strUserID;

    @Override
    public String execute() throws Exception {
         try {
        logger.info("------------------ Welcome Role Master Action Page ----------------User Id :"+strUserID);
       
          Object  strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of RoleMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllRoles() throws IOException {
        logger.info("========== Inside Get Roles Details .... ");
        JSONObject responcedata = new JSONObject();
        JSONArray cellarray = new JSONArray();
        JSONArray cell = new JSONArray();
        JSONObject cellobj = new JSONObject();
        out = response.getWriter();
        try {


            List<RoleVO> lstRoles = ServiceMaster.getRoleMasterService().getAllRoles();
            if (!lstRoles.isEmpty() && lstRoles != null) {
                for (RoleVO vo : lstRoles) {
                    cellobj.put(WebConstants.ID, vo.getRoleId());
                    cell.add(vo.getRoleId());
                    cell.add(vo.getRoleName());
                    if (vo.getIsBlocked() == null) {
                        cell.add("-");
                    } else if (vo.getIsBlocked() == 1) {
                        cell.add("In-Active");
                    } else {
                        cell.add("Active");
                    }
                    if (vo.getCreatedDate() == null) {
                        cell.add("-");
                    } else {
                        cell.add(vo.getCreatedDate());
                    }
                    if (vo.getCreatedBy() == null) {
                        cell.add("-");
                    } else {
                        cell.add(vo.getCreatedBy());
                    }
                    if (vo.getUpdatedDate() == null) {
                        cell.add("-");
                    } else {
                        cell.add(vo.getUpdatedDate());
                    }
                    if (vo.getUpdatedBy() == null) {
                        cell.add("-");
                    } else {
                        cell.add(vo.getUpdatedBy());
                    }

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);

                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, lstRoles.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("Response Sent!!");
            }
        } catch (Exception e) {

            logger.info("Exception in getAllRoles() method of RoleAction :");
            e.printStackTrace();
        }
    }
    private String txtuserRole;
    private String selStatus;
    private String txtroleID;

    public String submitUpdateRoleDetail() {
        System.out.println("--- submitUpdateRoleDetail -------" + txtuserRole + " Status : " + selStatus + "  RoleId =  " + txtroleID);
        try {
             strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            RoleVO roleVO = new RoleVO();
            if (txtroleID.isEmpty()) {
                roleVO.setOperationCode(1);
                roleVO.setCreatedBy(strUserID);
            } else {
                roleVO.setRoleId(Long.valueOf(txtroleID));
                roleVO.setOperationCode(2);
                roleVO.setUpdatedBy(strUserID);
            }
            roleVO.setIsBlocked(Short.valueOf(selStatus));
            roleVO.setRoleName(txtuserRole.trim());
            Resp resp = ServiceMaster.getRoleMasterService().createUpdateRole(roleVO);
            System.out.println("resp : " + resp.getRespCode().value());
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());
                 if (txtroleID != null && !txtroleID.isEmpty()) {
                       logger.info("Update Role Type Cache");
                       CacheMaster.RoleMap.remove(txtroleID);
                       CacheMaster.RoleMap.put(txtroleID,txtuserRole);
                    } else {
                        logger.info("Create Role Type Cache");
                        CacheMaster.RoleMap.put(resp.getPk().toString(),txtuserRole);
                    }
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            logger.info("Exception in submitUpdateRoleDetail() method of RoleAction :");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getTxtroleID() {
        return txtroleID;
    }

    public void setTxtroleID(String txtroleID) {
        this.txtroleID = txtroleID;
    }

    public String getSelStatus() {
        return selStatus;
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    public String getTxtuserRole() {
        return txtuserRole;
    }

    public void setTxtuserRole(String txtuserRole) {
        this.txtuserRole = txtuserRole;
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
