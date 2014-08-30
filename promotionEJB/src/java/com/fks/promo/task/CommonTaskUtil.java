/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.task;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.entity.TransTask;
import com.fks.promo.task.vo.TaskVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paresb
 */
public class CommonTaskUtil {

    public static List<TaskVO> convertTransTask(List<TransTask> list, boolean isInward) {
        List<TaskVO> volist = new ArrayList<TaskVO>();
        for (TransTask task : list) {
            TaskVO vo = new TaskVO();
            if(task!=null && task.getAssignedTo()!=null){
                vo.setAssignedId(task.getAssignedTo().getEmpId());
                vo.setAssignedName(task.getAssignedTo().getEmployeeName());
            }
            
            if (isInward) {
                vo.setZoneName(task.getCreatedBy().getMstStore().getMstZone().getZoneName());
            } else {
                vo.setZoneName(task.getAssignedTo().getMstStore().getMstZone().getZoneName());
            }
            vo.setCreatedBy(task.getCreatedBy().getEmpId());
            vo.setCreatedByName(task.getCreatedBy().getEmployeeName());
            if (task.getFilePath() != null) {
                vo.setFilePath(task.getFilePath());
            }
            if (task.getHeaderFilePath() != null) {
                vo.setHeaderFilePath(task.getHeaderFilePath());
            }
            vo.setIsHO(task.getIsHo());
            if(task.getPromoCount()!=null){
                vo.setPromoCount(task.getPromoCount());
            }
            vo.setMstTaskId(task.getMstTask().getTaskId());
            vo.setMstTaskName(task.getMstTask().getTaskName());
            vo.setRemarks(task.getRemarks());
            vo.setReqId(task.getTransTaskId());
            vo.setStatusCode(task.getMstStatus().getStatusId());
            vo.setStatus(task.getMstStatus().getStatusDesc());
            if(task.getCreatedTime()!=null){
                vo.setTaskAssignedDate(CommonUtil.dispayDateFormat(task.getCreatedTime()));
            }else{
                 vo.setTaskAssignedDate("-");
            }
            if(task.getUpdatedTime()!=null){
                vo.setTaskUpdatedDate(CommonUtil.dispayDateFormat(task.getUpdatedTime()));
            }else{
                 vo.setTaskUpdatedDate("-");
            }
            volist.add(vo);
        }
        return volist;
    }
}
