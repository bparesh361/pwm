/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.task.action;

import com.fks.promo.init.SearchTeamMemberReq;
import com.fks.promo.task.Resp;
import com.fks.promo.task.SearchTaskReq;
import com.fks.promo.task.SearchTaskType;
import com.fks.promo.task.SearchTaskTypeResp;
import com.fks.promo.task.TaskUpdateType;
import com.fks.promo.task.TaskVO;
import com.fks.promo.task.TeamMemberVO;
import com.fks.promo.task.UpdateTaskReq;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.form.vo.TaskFormVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ajitn
 */
public class TaskUtil {

    public static Map<String, String> getTeamMemberList(String storeCode) {
        SearchTeamMemberReq req = new SearchTeamMemberReq();
        req.setStoreCode(storeCode);
        Map<String, String> teamMap = new HashMap<String, String>();
//        SearchTeamMemberResp resp = ServiceMaster.getCommonPromotionService().searchTeamMember(req);
        List<TeamMemberVO> list = ServiceMaster.getTaskService().getAllTeamMembers(storeCode);
        if (list != null && list.size() > 0) {
            for (TeamMemberVO member : list) {
                String membername = member.getEmpName() + " : " + member.getEmpCode();
                teamMap.put(member.getEmpId(), membername);

            }
        }
//        if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
//            if (resp.getTeamlist() != null && resp.getTeamlist().size() > 0) {
//                for (TeamMember member : resp.getTeamlist()) {
//                    teamMap.put(member.getEmpId().toString(), member.getEmpName());
//                }
//            }
//
//        }
        return teamMap;
    }

    public static Resp createTask(TaskFormVO taskVO, String empId, String storecode, String filePath) {
        TaskVO reqeust = new TaskVO();
        if (storecode.equalsIgnoreCase("901")) {
            reqeust.setIsHO(true);
        } else {
            reqeust.setIsHO(false);
        }
        if (filePath != null) {
            reqeust.setFilePath(filePath);
        }
        reqeust.setAssignedId(Long.valueOf(taskVO.getTaskAssignTo()));
        reqeust.setCreatedBy(Long.valueOf(empId));
        reqeust.setRemarks(taskVO.getRemarks());
        reqeust.setMstTaskId(Long.valueOf(taskVO.getTaskType()));        
        if (taskVO.getPromoCount() != null && taskVO.getPromoCount().trim().length()>0) {
            reqeust.setPromoCount(Integer.parseInt(taskVO.getPromoCount()));
        }
//        reqeust.setReqId(Long.MIN_VALUE);

        return ServiceMaster.getTaskService().creatTask(reqeust);
    }

    public static Resp updateTask(String filePath, String trnsTaskId, String empId, String statusId) {
        UpdateTaskReq req = new UpdateTaskReq();
        req.setTaskId(Long.valueOf(trnsTaskId));
        req.setUpdateEmpId(Long.valueOf(empId));
        if (filePath != null) {
            req.setHeaderFile(filePath);
            req.setType(TaskUpdateType.HEADER_FILE_UPLOAD);
        } else {
            req.setType(TaskUpdateType.STATUS_UPDATE);
        }
        req.setStatusId(Long.valueOf(statusId));
        return ServiceMaster.getTaskService().updateTask(req);
    }

    public static SearchTaskTypeResp searchTask(boolean isInward, String empId, int pageIndex) {
        SearchTaskReq req = new SearchTaskReq();
        if (isInward) {
            req.setType(SearchTaskType.BY_ASSIGNER_ID);
            req.setAssignerId(Long.valueOf(empId));
        } else {
            req.setType(SearchTaskType.BY_CREATOR_ID);
            req.setCreatorId(Long.valueOf(empId));
        }
        req.setStartIndex(pageIndex);
        return ServiceMaster.getTaskService().searchTask(req);

    }
}
