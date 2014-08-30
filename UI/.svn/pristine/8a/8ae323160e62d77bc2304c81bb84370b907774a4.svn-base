/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.article.service.RespCode;
import com.fks.promo.comm.service.Resp;
import com.fks.promo.master.service.DepartmentVO;
import com.fks.promo.master.service.MstCampaignVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
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
public class DepartmentMasterAction extends ActionBase implements ServletResponseAware {

    private static Logger logger = Logger.getLogger(DepartmentMasterAction.class);
    private HttpServletResponse resp;
    private String deptName, txtdeptID, selStatus;
    PrintWriter out;

    @Override
    public String execute() throws Exception {
        logger.info("------------------ Welcome DepartmentMasterAction Master Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of DepartmentMasterAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllDepartmentDetail() {
        logger.info("===== Inside getAllDepartmentDetail =====");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = resp.getWriter();
            List<DepartmentVO> list = ServiceMaster.getOtherMasterService().getAllDepartments();
            logger.info(" Department Successfully Fetched.");
            if (list != null) {
                for (DepartmentVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getDepartmentID());
                    cell.add(vo.getDepartmentID().toString());
                    cell.add(vo.getDepartmentName());
                    if (vo.isIsActive() == Boolean.FALSE) {
                        cell.add(WebConstants.ACTIVE_STR);
                    } else {
                        cell.add(WebConstants.BLOCKED_STR);
                    }
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);

                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
                logger.info("---- Department Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.error("exception in : " + e.getMessage());
        }
    }

    public String submitDepartmentMaster() {
        logger.info("----- Inside submitDepartmentMaster ----- Department Id " + txtdeptID + " Department Name " + deptName);
        try {
            if (deptName != null && !deptName.isEmpty()) {
                deptName = deptName.trim();
                DepartmentVO vo = new DepartmentVO();
                if (txtdeptID != null && !txtdeptID.isEmpty()) {
                    vo.setDepartmentID(Long.valueOf(txtdeptID));
                }
                vo.setDepartmentName(deptName);
                if (selStatus.equalsIgnoreCase("0")) {
                    vo.setIsActive(false);
                } else {
                    vo.setIsActive(true);
                }
                com.fks.promo.master.service.Resp deptResp = ServiceMaster.getOtherMasterService().createDepartment(vo);
                if (deptResp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    logger.info(" Dept Created Successfully. Dept id: " + deptResp.getPk());
                    if (txtdeptID != null && !txtdeptID.isEmpty()) {
                        //Update
                        logger.info("Update Cache");
                        if (selStatus.equalsIgnoreCase("0")) {
                            CacheMaster.DeptMap.remove(txtdeptID);
                            CacheMaster.DeptMap.put(txtdeptID, deptName);
                        } else {
                            CacheMaster.DeptMap.remove(txtdeptID);
                        }
                    } else {
                        logger.info("Create Cache");

                        if (selStatus.equalsIgnoreCase("0")) {
                            CacheMaster.DeptMap.remove(deptResp.getPk().toString());
                            CacheMaster.DeptMap.put(deptResp.getPk().toString(), deptName);
                        }
                        
                    }
                    addActionMessage(deptResp.getMsg());
                    return SUCCESS;
                } else {
                    addActionError(deptResp.getMsg());
                    logger.info(" Error while creating Campaign. " + deptResp.getMsg());
                    return INPUT;
                }
            } else {
                logger.info("---- Campagin Can Not be Null or Blank ------- ");
                return INPUT;
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Campagin Type.");
            return ERROR;
        }
    }

    public String getSelStatus() {
        return selStatus;
    }

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTxtdeptID() {
        return txtdeptID;
    }

    public void setTxtdeptID(String txtdeptID) {
        this.txtdeptID = txtdeptID;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.resp = hsr;
    }
}
