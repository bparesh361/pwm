/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.task.action;

import com.fks.promo.task.Resp;
import com.fks.promo.task.RespCode;
import com.fks.promo.task.SearchTaskTypeResp;
import com.fks.promo.task.TaskVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author ajitn
 */
public class InwardOutwardTaskAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(InwardOutwardTaskAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strEmpID, storeCode;

    public String viewInwardTask() {
        logger.info("------------------ Inside Inward Task Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Inward Task Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void viewInwardDashboardDtl() {
        try {
            logger.info("------------------ Inside Inward Task Dashboard Dtl ----------------");
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();


            List<TaskVO> lstSearchData = null;
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
                strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
                SearchTaskTypeResp searchResp = TaskUtil.searchTask(true, strEmpID, startPageIndex.intValue());
                if (searchResp.getResp().getRespCode() == RespCode.SUCCESS) {
                    lstSearchData = searchResp.getList();
                    totalCount = searchResp.getTotalCount();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            } else {
                lstSearchData = Collections.<TaskVO>emptyList();
            }
            if (sidx.equals("reqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getReqId().toString().compareToIgnoreCase(p2.getReqId().toString());
                    }
                });
            } else if (sidx.equals("assigner")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getCreatedByName().compareToIgnoreCase(p2.getCreatedByName().toString());
                    }
                });
            } else if (sidx.equals("zone")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getZoneName().toString().compareToIgnoreCase(p2.getZoneName().toString());
                    }
                });
            } else if (sidx.equals("assignto")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getAssignedName().toString().compareToIgnoreCase(p2.getAssignedName().toString());
                    }
                });
            } else if (sidx.equals("tasktype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getMstTaskName().toString().compareToIgnoreCase(p2.getMstTaskName().toString());
                    }
                });
            } else if (sidx.equals("remarks")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getRemarks().toString().compareToIgnoreCase(p2.getRemarks().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            } else if (sidx.equals("AssignedDate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getTaskAssignedDate().toString().compareToIgnoreCase(p2.getTaskAssignedDate().toString());
                    }
                });
            } else if (sidx.equals("updateDate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getTaskUpdatedDate().toString().compareToIgnoreCase(p2.getTaskUpdatedDate().toString());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }

            for (TaskVO vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getReqId());
                cell.add("TR-" + vo.getReqId());
                cell.add(vo.getCreatedByName());
                cell.add(vo.getZoneName());
                cell.add(vo.getAssignedName());
                cell.add(vo.getMstTaskName());
                cell.add(vo.getPromoCount());
                cell.add(vo.getRemarks());
                cell.add(vo.getStatus());
                cell.add(vo.getTaskAssignedDate());
                cell.add(vo.getTaskUpdatedDate());
                if (vo.getHeaderFilePath() != null) {
                    String attachFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadFileFromPath?filePath="
                            + vo.getHeaderFilePath() + ">Download</a>";
                    cell.add(attachFile);
                } else {
                    cell.add("-");
                }
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
            logger.fatal(
                    "Exception generated In View Inward Dashboard Dtl Execute Dashboard: function viewInwardDashboardDtl() : Exception is :");
            e.printStackTrace();
        }

    }
    private File taskFileUpload;
    private String trnsTaskId, statusId, taskFileUploadFileName;

    public File getTaskFileUpload() {
        return taskFileUpload;
    }

    public void setTaskFileUpload(File taskFileUpload) {
        this.taskFileUpload = taskFileUpload;
    }

    public String getTaskFileUploadFileName() {
        return taskFileUploadFileName;
    }

    public void setTaskFileUploadFileName(String taskFileUploadFileName) {
        this.taskFileUploadFileName = taskFileUploadFileName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getTrnsTaskId() {
        return trnsTaskId;
    }

    public void setTrnsTaskId(String trnsTaskId) {
        this.trnsTaskId = trnsTaskId;
    }

    public String updateTask() {
        try {
            logger.info("------------------ Inside Updating Task Action ----------------");
            strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String filePath = null;
            if (taskFileUpload != null) {
                String taskFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.TASK_FILE_PATH);
                String taskFileName = PromotionUtil.getFileNameWithoutSpace(taskFileUploadFileName);
                Calendar cal = new GregorianCalendar();
                long timestamp = cal.getTimeInMillis();
                filePath = taskFilePath + timestamp + "_" + taskFileName;


                /*Copy the File On Defined File Path
                 */
                File bfile = new File(filePath);
                FileUtils.copyFile(taskFileUpload.getAbsoluteFile(), bfile);
                logger.info("Task File is copied successful!");
            }

            Resp taskResp = TaskUtil.updateTask(filePath, trnsTaskId, strEmpID, statusId);
            logger.info("------ Task Creation Resp : " + taskResp.getRespCode());
            if (taskResp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(taskResp.getMsg());
                return SUCCESS;
            } else {
                addActionError(taskResp.getMsg());
                return INPUT;
            }
        } catch (Exception ex) {
            logger.info("---- Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String viewOutwardTask() {
        logger.info("------------------ Inside Outward Task Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Outward Task Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void viewOutwardDashboardDtl() {

        try {
            logger.info("------------------ Inside Outward Task Dashboard Dtl ----------------");
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<TaskVO> lstSearchData = null;
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

                strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
                SearchTaskTypeResp searchResponse = TaskUtil.searchTask(false, strEmpID, startPageIndex.intValue());
                if (searchResponse.getResp().getRespCode() == RespCode.SUCCESS) {
                    lstSearchData = searchResponse.getList();
                    totalCount = searchResponse.getTotalCount();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            } else {
                lstSearchData = Collections.<TaskVO>emptyList();
            }

            if (sidx.equals("reqno")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getReqId().toString().compareToIgnoreCase(p2.getReqId().toString());
                    }
                });
            } else if (sidx.equals("assigner")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getCreatedByName().compareToIgnoreCase(p2.getCreatedByName().toString());
                    }
                });
            } else if (sidx.equals("zone")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getZoneName().toString().compareToIgnoreCase(p2.getZoneName().toString());
                    }
                });
            } else if (sidx.equals("assignto")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getAssignedName().toString().compareToIgnoreCase(p2.getAssignedName().toString());
                    }
                });
            } else if (sidx.equals("tasktype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getMstTaskName().toString().compareToIgnoreCase(p2.getMstTaskName().toString());
                    }
                });
            } else if (sidx.equals("remarks")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getRemarks().toString().compareToIgnoreCase(p2.getRemarks().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            } else if (sidx.equals("AssignedDate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getTaskAssignedDate().toString().compareToIgnoreCase(p2.getTaskAssignedDate().toString());
                    }
                });
            } else if (sidx.equals("updateDate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TaskVO p1 = (TaskVO) o1;
                        TaskVO p2 = (TaskVO) o2;
                        return p1.getTaskUpdatedDate().toString().compareToIgnoreCase(p2.getTaskUpdatedDate().toString());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }

            for (TaskVO vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getReqId());
                cell.add("TR-" + vo.getReqId());
                cell.add(vo.getCreatedByName());
                cell.add(vo.getZoneName());
                cell.add(vo.getAssignedName());
                cell.add(vo.getMstTaskName());
                cell.add(vo.getPromoCount());
                cell.add(vo.getRemarks());
                cell.add(vo.getStatus());
                cell.add(vo.getTaskAssignedDate());
                cell.add(vo.getTaskUpdatedDate());
                if (vo.getHeaderFilePath() != null) {
                    String headerFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadFileFromPath?filePath="
                            + vo.getHeaderFilePath() + ">Download</a>";
                    cell.add(headerFile);
                } else {
                    cell.add("-");
                }
                if (vo.getFilePath() != null) {
                    String attachFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadFileFromPath?filePath="
                            + vo.getFilePath() + ">Download</a>";
                    cell.add(attachFile);
                } else {
                    cell.add("-");
                }
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
            logger.fatal(
                    "Exception generated In View Outward Dashboard Dtl  Dashboard: function viewOutwardDashboardDtl() : Exception is :");
            e.printStackTrace();
        }
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
