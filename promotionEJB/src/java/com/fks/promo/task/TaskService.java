/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.task;

import com.fks.promo.common.CommonStatus;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstStore;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoStatus;
import com.fks.promo.entity.TransTask;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.MstStoreFacade;
import com.fks.promo.facade.MstTaskFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.facade.TransPromoStatusFacade;
import com.fks.promo.facade.TransTaskFacade;
import com.fks.promo.service.CommonPromoMailService;
import com.fks.promo.service.NotificationMessage;
import com.fks.promo.service.NotificationType;
import com.fks.promo.task.vo.SearchTaskReq;
import com.fks.promo.task.vo.SearchTaskTypeResp;
import com.fks.promo.task.vo.TaskVO;
import com.fks.promo.vo.TeamMemberVO;
import com.fks.promo.task.vo.UpdateTaskReq;
import com.fks.promotion.service.util.FilePromoCloseUtil;
import com.fks.promotion.service.util.StatusUpdateUtil;
import com.fks.promotion.vo.FilePromoCloseResp;
import com.fks.promotion.vo.PromoCloseVO;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
@WebService
public class TaskService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger(TaskService.class.getName());
    @EJB
    private TransTaskFacade transTaskDao;
    @EJB
    private MstEmployeeFacade empDao;
    @EJB
    private MstStatusFacade statusDao;
    @EJB
    private MstTaskFacade taskDao;
    @EJB
    private CommonDAO commonDao;
    @EJB
    private TransPromoFacade transPromoDao;
    @EJB
    private MstStoreFacade storeDao;
    @EJB
    private CommonPromoMailService mailService;
    @EJB
    private FilePromoCloseUtil promoCloseUtil;
    @EJB
    private TransPromoStatusFacade transPromoStatusFacade;

    public Resp creatTask(TaskVO vo) {
        logger.info("=== Inside Creating Task === ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info("------ Payload------ \n" + buffer);
        TransTask task = new TransTask();
        task.setCreatedTime(new Date());
        if (vo.getFilePath() != null) {
            task.setHeaderFilePath(vo.getFilePath());
        }
        task.setIsHo(vo.isIsHO());
        // created by
        task.setCreatedBy(empDao.find(vo.getCreatedBy()));
        MstEmployee emp = empDao.find(vo.getAssignedId());

        task.setAssignedTo(emp);
        task.setMstStatus(statusDao.find(new Long(51)));
        task.setMstTask(taskDao.find(vo.getMstTaskId()));
        task.setRemarks(vo.getRemarks());
        task.setPromoCount(vo.getPromoCount());
        transTaskDao.create(task);
        NotificationMessage msg = new NotificationMessage(NotificationType.TASK_ASSIGN, task.getTransTaskId().toString());
        mailService.sendNotificationMessage(msg);
        return new Resp(RespCode.SUCCESS, "Task Created Successfully with Id " + task.getTransTaskId());
    }

    public Resp updateTask(UpdateTaskReq request) {
        logger.info(" ==== Inside Updating Task ==== " + request.getType());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        TransTask task = transTaskDao.find(request.getTaskId());
        if (task == null) {
            return new Resp(RespCode.FAILURE, "Task Id Not Correct");
        }
        switch (request.getType()) {
            case STATUS_UPDATE:
                task.setMstStatus(statusDao.find(request.getStatusId()));
                break;
            case HEADER_FILE_UPLOAD:
                task.setFilePath(request.getHeaderFile());
                task.setMstStatus(statusDao.find(request.getStatusId()));
                break;
        }
        task.setUpdatedBy(empDao.find(request.getUpdateEmpId()));
        task.setUpdatedTime(new Date());
        return new Resp(RespCode.SUCCESS, "Task Updated Successfully.");
    }

    public SearchTaskTypeResp searchTask(SearchTaskReq request) {
        logger.info(" === Searching Request === " + request.getType());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        SearchTaskTypeResp response = new SearchTaskTypeResp();
        List<TaskVO> volist = new ArrayList<TaskVO>();
        List<TransTask> list = null;
        boolean isInward = true;
        long totalCount = 0;
        switch (request.getType()) {
            case BY_ASSIGNER_ID:
                list = commonDao.getTransTaskAssignerList(request.getStartIndex(), request.getAssignerId());
                totalCount = commonDao.getTransTaskAssignerListCount(request.getAssignerId());
                break;
            case BY_CREATOR_ID:
                list = commonDao.getTransTaskListByCreator(request.getStartIndex(), request.getCreatorId());
                totalCount = commonDao.getTransTaskListByCreatorCount(request.getCreatorId());
                isInward = false;
                break;
        }
        response.setTotalCount(totalCount);
        volist = CommonTaskUtil.convertTransTask(list, isInward);
        response.setList(volist);
        response.setResp(new Resp(RespCode.SUCCESS, "success"));
        return response;
    }

    public List<TeamMemberVO> getAllTeamMembers(String storeCode) {
        logger.info(" === Getting All Team Member by storecode === " + storeCode);
        MstStore store = storeDao.find(storeCode);
        List<MstEmployee> list = null;
        if (store.getMstLocation().getLocationId().toString().equalsIgnoreCase("1")) {
            logger.info("---- inside HO.");
            list = commonDao.getAllTeamMember();
            logger.info("----- Emp COunt In HO :" + list.size());
        } else {
            logger.info("---- inside zone.");
            list = commonDao.getAllTeamMemberByZone(store.getMstStoreId());
            logger.info("----- Emp COunt In Zone :" + list.size());
        }
        List<TeamMemberVO> voList = new ArrayList<TeamMemberVO>();
        if (list != null && !list.isEmpty()) {
            for (MstEmployee emp : list) {
                TeamMemberVO vo = new TeamMemberVO();
                vo.setEmpId(emp.getEmpId().toString());
                vo.setEmpCode(emp.getEmpCode().toString());
                vo.setEmpName(emp.getEmployeeName());
                voList.add(vo);
            }
        }
        return voList;
    }

    public Resp statusChangeTransPromoByTeamMember(Long transPromoId, String remarks, String bonusBuy, String promoDetails, Boolean isCashierTrigger, String statusName, Long empId, String reasonRejction) {
        logger.info(" === Inside Status Change Trans Promo By Team Member === ");

        TransPromo promo = transPromoDao.find(transPromoId);
        promo.setBonusBuy(bonusBuy);
        promo.setPromoDetails(promoDetails);
        if (isCashierTrigger == true) {
            promo.setCashierTrigger("1");
        } else {
            promo.setCashierTrigger("0");
        }
        promo.setRemarks(remarks);
        MstStatus status = null;
        if (statusName.equalsIgnoreCase("HOLD")) {
            status = statusDao.find(CommonStatus.TEAM_MEMBER_HOLD);
        } else if (statusName.equalsIgnoreCase("REJECT")) {
            status = statusDao.find(CommonStatus.TEAM_MEMBER_REJECTED);
            promo.setReasonForRejection(reasonRejction);
        } else {
            status = statusDao.find(CommonStatus.PROMO_CLOSED);
        }
        promo.setMstStatus(status);
        promo.setUpdatedDate(new Date());
        promo.setMstEmployee6(new MstEmployee(empId));
        int updateArticle = commonDao.updateTransPromoArticleStatus(transPromoId, status.getStatusId(), empId);
        logger.info("------- Update Article Count : " + updateArticle);

        if (statusName.equalsIgnoreCase("HOLD")) {
            return new Resp(RespCode.SUCCESS, "Selected Promotion Are Hold Successfully.");
        } else if (statusName.equalsIgnoreCase("REJECT")) {
            return new Resp(RespCode.SUCCESS, "Selected Promotion Are Rejected Successfully.");
        } else {
            return new Resp(RespCode.SUCCESS, "Selected Promotion Are Closed Successfully.");
        }

    }

    //Promo Closure Service
    public Resp statusChangeTransPromoByTeamMemberList(List<Long> transPromoIdList, String remarks, String bonusBuy, String promoDetails, String isCashierTrigger, String statusName, Long empId, String reasonRejction, String filePath, String rejectRemarks) {
        logger.info(" === Inside Status Change Trans Promo By Team Member === ");
        for (long transPromoId : transPromoIdList) {
            TransPromo promo = transPromoDao.find(transPromoId);
            promo.setBonusBuy(bonusBuy);
            promo.setPromoDetails(promoDetails);
            promo.setCashierTrigger(isCashierTrigger);
            //  promo.setRemarks(remarks);
            promo.setRejectionRemarks(rejectRemarks);
            MstStatus status = null;
            if (statusName.equalsIgnoreCase("HOLD")) {
                remarks="Request is on Hold by promo executor.";
                status = statusDao.find(CommonStatus.TEAM_MEMBER_HOLD);
            } else if (statusName.equalsIgnoreCase("REJECT")) {
                status = statusDao.find(CommonStatus.TEAM_MEMBER_REJECTED);
                promo.setReasonForRejection(reasonRejction);
            } else {
                status = statusDao.find(CommonStatus.PROMO_CLOSED);
            }
            //Phase 3 CR - Promo Req Status History 15-11-2013
            TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(new MstEmployee(empId), status, promo.getMstStatus(), remarks, promo);
            transPromoStatusFacade.create(promoStatus);
            promo.setMstStatus(status);
            promo.setUpdatedDate(new Date());
            promo.setMstEmployee6(new MstEmployee(empId));
            promo.setLsmwFilePath(filePath);
            int updateArticle = commonDao.updateTransPromoArticleStatus(transPromoId, status.getStatusId(), empId);
            logger.info("------- Update Article Count : " + updateArticle);
            NotificationMessage msg = new NotificationMessage();
            msg.setId(promo.getTransPromoId().toString());
            msg.setEmpId(empId.toString());
            msg.setNotificationType(NotificationType.PROMO_CLOSURE_MAIL);
            String strMsg = "";
            if (statusName.equalsIgnoreCase("REJECT")) {
                strMsg = "Request is Rejected.";
                msg.setMsg(strMsg);
                mailService.sendNotificationMessage(msg);
            } else {
                strMsg = "Promotion is configured in system and closed.";
                msg.setMsg(strMsg);
                mailService.sendNotificationMessage(msg);
            }

        }
        if (statusName.equalsIgnoreCase("HOLD")) {
            return new Resp(RespCode.SUCCESS, "Selected Promotion Are Hold Successfully.");
        } else if (statusName.equalsIgnoreCase("REJECT")) {
            return new Resp(RespCode.SUCCESS, "Selected Promotion Are Rejected Successfully.");
        } else {
            return new Resp(RespCode.SUCCESS, "Selected Promotion Are Closed Successfully.");
        }

    }

    // Promo Closure Service By File Upload
    public FilePromoCloseResp closePromotionByFile(String filePath, Long empId) {
        try {
            System.out.println("---- Inside Close Promo By File ----");
            MstEmployee emp = empDao.find(empId);
            Collection<TransPromo> executivePromoList = commonDao.getExecutorTransPromoForCloser(empId);
            if (executivePromoList == null || executivePromoList.isEmpty()) {
                logger.info("No Promotion Request Found For Employee.");
                return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "No Promotion Request Assigned To " + emp.getEmployeeName())));
            }
            Set<Long> promoSet = new HashSet<Long>();
            for (TransPromo promo : executivePromoList) {
                promoSet.add(promo.getTransPromoId());
            }

            //vlidating file
            FilePromoCloseResp resp = promoCloseUtil.validateFile(filePath, promoSet, emp, logger);

            //Delete the File From the server
            File closurFile = new File(filePath);
            if (closurFile.exists()) {
                boolean del = closurFile.delete();
                logger.info("--- File Deleted Status : " + del);
            }
            System.out.println("Resp =" + resp.getResp().getRespCode());

            if (resp.getResp().getRespCode() == RespCode.FAILURE) {
                return resp;
            }

            Iterator emailIterator = resp.getPromoCloseEmailIDList().entrySet().iterator();
            while (emailIterator.hasNext()) {
                Map.Entry<Long, Long> entry = (Entry) emailIterator.next();
                Long promoId = (Long) entry.getValue();
                NotificationMessage msg = new NotificationMessage();
                msg.setId(promoId.toString());
                msg.setEmpId(empId.toString());
                msg.setNotificationType(NotificationType.PROMO_CLOSURE_MAIL);
                String strMsg = "";
                strMsg = "Promotion is configured in system and closed.";
                msg.setMsg(strMsg);
                mailService.sendNotificationMessage(msg);
            }

//            for (Long lPromoId : resp.getPromoCloseEmailIDList()) {
//                System.out.println("Id =" + lPromoId);
//                NotificationMessage msg = new NotificationMessage();
//                msg.setId(lPromoId.toString());
//                msg.setEmpId(empId.toString());
//                msg.setNotificationType(NotificationType.PROMO_CLOSURE_MAIL);
//                String strMsg = "";
//                strMsg = "Promotion is configured in system and closed.";
//                msg.setMsg(strMsg);
//                mailService.sendNotificationMessage(msg);
//            }

            //return response
            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "Error : " + ex.getMessage())));
        }
    }
}
