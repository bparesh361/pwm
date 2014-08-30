/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.task.action;

import com.fks.promo.task.Resp;
import com.fks.promo.task.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.TaskFormVO;
import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author ajitn
 */
public class TaskAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(TaskAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID, strEmpID, storeCode;
    private Map<String, String> taskTypeMap;
    private Map<String, String> taskAssignToMap;
    CachedMapsList maps = new CachedMapsList();
    private TaskFormVO taskVO;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Map<String, String> getTaskTypeMap() {
        return taskTypeMap;
    }

    public void setTaskTypeMap(Map<String, String> taskTypeMap) {
        this.taskTypeMap = taskTypeMap;
    }

    public Map<String, String> getTaskAssignToMap() {
        return taskAssignToMap;
    }

    public void setTaskAssignToMap(Map<String, String> taskAssignToMap) {
        this.taskAssignToMap = taskAssignToMap;
    }

    public TaskFormVO getTaskVO() {
        return taskVO;
    }

    public void setTaskVO(TaskFormVO taskVO) {
        this.taskVO = taskVO;
    }

    @Override
    public String execute() {
        logger.info("------------------ Inside Task Creation Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                setTaskTypeMap(maps.getActiveMap(MapEnum.TASK_TYPE));
                storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
                setTaskAssignToMap(TaskUtil.getTeamMemberList(storeCode));
                taskVO = new TaskFormVO();
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Task Creation Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private File taskFileUpload;
    private String taskFileUploadFileName;

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

    public String createTask() {
        try {
            logger.info("------------------ Inside Creating Task Action ----------------");
            strEmpID = getSessionMap().get(WebConstants.EMP_ID).toString();
            storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
            String filePath = null;
            if (taskFileUpload != null) {
                if (taskFileUpload.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    setTaskTypeMap(maps.getActiveMap(MapEnum.TASK_TYPE));
                    setTaskAssignToMap(TaskUtil.getTeamMemberList(storeCode));
                    return INPUT;
                }
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


            Resp taskResp = TaskUtil.createTask(taskVO, strEmpID, storeCode, filePath);
            logger.info("------ Task Creation Resp : " + taskResp.getRespCode());
            if (taskResp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(taskResp.getMsg());
                setTaskTypeMap(maps.getActiveMap(MapEnum.TASK_TYPE));
                setTaskAssignToMap(TaskUtil.getTeamMemberList(storeCode));
                taskVO = new TaskFormVO();
                return SUCCESS;
            } else {
                addActionError(taskResp.getMsg());
                setTaskTypeMap(maps.getActiveMap(MapEnum.TASK_TYPE));
                setTaskAssignToMap(TaskUtil.getTeamMemberList(storeCode));
                return INPUT;
            }

        } catch (Exception ex) {
            logger.info("---- Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
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
