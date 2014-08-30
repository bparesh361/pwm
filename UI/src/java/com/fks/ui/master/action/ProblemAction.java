/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.ProblemMasterVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CacheMaster;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.ProblemTypeVO;
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
 * @author Paresb
 */
public class ProblemAction extends ActionBase implements ServletResponseAware, ServletRequestAware {

    private static Logger logger = Logger.getLogger(ProblemAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String problemname;
    private String txProblemID;
    private String selStatus;

    public void setSelStatus(String selStatus) {
        this.selStatus = selStatus;
    }
    private String strUserID;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Problem Action Page ----------------");
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
            logger.info("Exception in excute() of Problem Action Master Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllProblem() {
        logger.info("-----------  Inside Fetching Problem Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<ProblemMasterVO> list = ServiceMaster.getOtherMasterService().getAllProblemMaster();
            if (list != null) {
                for (ProblemMasterVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getProblemId());
                    cell.add(vo.getProblemId());
                    cell.add(vo.getProblemName());
                    if (vo.getIsBlocked().equals(WebConstants.ACTIVE_SHORT)) {
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
                logger.info("---- Problem Mater Type Request Received Successfully with List Size : " + list.size() + " -------- ");
            }
        } catch (Exception e) {
            logger.info("Exception in getAllRoles() method of ProblemAction :");
            e.printStackTrace();
        }

    }

    public String submitProblemMaster() {
        logger.info("----- Inside Creating Problem Type ----- " + txProblemID + "----- status :" + selStatus);
        try {
            if (problemname != null && !problemname.isEmpty()) {
                problemname = problemname.trim();
                ProblemMasterVO vo = new ProblemMasterVO();
                if (!txProblemID.isEmpty()) {
                    vo.setProblemId(new Long(txProblemID));
                }
                vo.setProblemName(problemname);
                vo.setIsBlocked(new Short(selStatus));
                Resp resp = ServiceMaster.getOtherMasterService().createProblem(vo);
                if (resp.getRespCode().equals(RespCode.SUCCESS)) {
                    logger.info("Problem Type Created Successfully.");

                    if (!txProblemID.isEmpty()) {
                        CacheMaster.problemTypeMap.remove(txProblemID);
                        //CacheMaster.problemTypeMap.put(resp.getPk().toString(),new ProblemTypeVO(resp.getPk(), problemname, Short.valueOf(selStatus)) );
                    }
                    CacheMaster.problemTypeMap.put(resp.getPk().toString(), new ProblemTypeVO(resp.getPk(), problemname, Short.valueOf(selStatus)));
                    addActionMessage(resp.getMsg());
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    logger.info("Error while creating Problem Type.");
                    return INPUT;
                }
            } else {
                logger.info("---- Problem Type Can Not be Null or Blank ------- ");
                return INPUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Error While Creating Problem Type.");
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

    public String getProblemname() {
        return problemname;
    }

    public void setProblemname(String problemname) {
        this.problemname = problemname;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ProblemAction.logger = logger;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getTxProblemID() {
        return txProblemID;
    }

    public void setTxProblemID(String txProblemID) {
        this.txProblemID = txProblemID;
    }
}
