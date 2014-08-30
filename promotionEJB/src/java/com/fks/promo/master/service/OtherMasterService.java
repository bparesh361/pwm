/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.service;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.entity.MapPromoMch;
import com.fks.promo.entity.MapUserDepartment;
import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstCalender;
import com.fks.promo.entity.MstCampaign;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstEvent;
import com.fks.promo.entity.MstLeadTime;
import com.fks.promo.entity.MstMktg;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstReasonRejection;
import com.fks.promo.entity.MstRole;
import com.fks.promo.entity.MstSet;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstTask;
import com.fks.promo.entity.MstZone;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.facade.MstCalenderFacade;
import com.fks.promo.facade.MstCampaignFacade;
import com.fks.promo.facade.MstDepartmentFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstEventFacade;
import com.fks.promo.facade.MstMktgFacade;
import com.fks.promo.facade.MstProblemFacade;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promo.facade.MstPromotionTypeFacade;
import com.fks.promo.facade.MstReasonRejectionFacade;
import com.fks.promo.facade.MstRoleFacade;
import com.fks.promo.facade.MstSetFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.MstTaskFacade;
import com.fks.promo.facade.MstZoneFacade;
import com.fks.promo.master.util.VOUtilOtherMaster;
import com.fks.promo.master.vo.DepartmentVO;
import com.fks.promo.master.vo.MstCalendarVO;
import com.fks.promo.master.vo.MstCampaignVO;
import com.fks.promo.master.vo.MstEventVO;
import com.fks.promo.master.vo.MstMktgVO;
import com.fks.promo.master.vo.MstPromotionTypeVO;
import com.fks.promo.master.vo.MstReasonRejectionVO;
import com.fks.promo.master.vo.MstStatusVO;
import com.fks.promo.master.vo.MstTaskVO;
import com.fks.promo.master.vo.MstZoneVO;
import com.fks.promo.master.vo.ProblemMasterVO;
import com.fks.promo.master.vo.SetVO;
import com.fks.promo.master.vo.ValidateMCResp;
import com.fks.promotion.service.ArticleValidateService;
import com.fks.promotion.vo.ValidateArticleMCResp;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class OtherMasterService {

    private static final Logger logger = Logger.getLogger(OtherMasterService.class.getName());
    @EJB
    private MstMktgFacade mktgTypeDao;
    @EJB
    private CommonDAO commonDao;
    @EJB
    private MstReasonRejectionFacade reasonDao;
    @EJB
    private MstProblemFacade problemDao;
    @EJB
    private MstPromotionTypeFacade promotionDao;
    @EJB
    private MstTaskFacade taskDao;
    @EJB
    private MstStatusFacade statusDao;
    @EJB
    private MstDepartmentFacade departmentDao;
    @EJB
    private MstRoleFacade roleDao;
    @EJB
    private MstZoneFacade zoneDao;
    @EJB
    private MstCalenderFacade calendarDao;
    @EJB
    private MstEventFacade eventDao;
    @EJB
    private MchFacade mchFacade;
    @EJB
    private MstPromoFacade mstPromoFacade;
    @EJB
    ArticleValidateService odsArticleService;
    @EJB
    CommonPromotionDao commonPromotionDao;
    @EJB
    MstEmployeeFacade mstEmployeeFacade;
    @EJB
    MstSetFacade mstSetFacade;
    @EJB
    MstCampaignFacade mstCampaignFacade;

    public List<MstStatusVO> getALLStatus() {
        logger.info("---- inside getting all status ---");
        List<MstStatus> list = statusDao.findAll();
        return VOUtilOtherMaster.convertStatus(list);
    }

    public List<SetVO> getAllSet() {
        logger.info("----- Inside Getting All Sets -----");
        List<MstSet> list = mstSetFacade.findAll();
        return VOUtilOtherMaster.convertMstSet(list);
    }

    public List<MstMktgVO> getAllMarketingType() {
        logger.info("----- Inside Getting All Marketing Types -----");
        List<MstMktg> list = mktgTypeDao.findAll();
        return VOUtilOtherMaster.convertMstMktgType(list);
    }

    public List<MstCampaignVO> getAllCampaign() {
        logger.info("----- Inside Getting All Objective -----");
        List<MstCampaign> list = mstCampaignFacade.findAll();
        return VOUtilOtherMaster.convertMstCampaign(list);
    }

    public Resp createCampaign(MstCampaignVO vo) {
        logger.info("--- Creating Objective Master ---");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {

            MstCampaign campaign = null;
            if (vo.getCampaignID() == null) {
                logger.info("--- creating new Objective ---");
                List<MstCampaign> checkList = commonDao.checkCampaignByName(vo.getCampaignName());
                if (checkList != null && checkList.size() > 0) {
                    return (new Resp(RespCode.FAILURE, "Objective Name : " + vo.getCampaignName() + " Exists."));
                }
                campaign = new MstCampaign();
                campaign.setCampaignName(vo.getCampaignName());
                campaign.setIsActive(vo.isIsActive());
                mstCampaignFacade.create(campaign);
                return (new Resp(RespCode.SUCCESS, "Objective Created Successfully.", campaign.getCampaignId()));
            } else {
                logger.info("--- updating campaign ---");

                List<MstCampaign> checkList = commonDao.checkCampaignByName(vo.getCampaignName());
                if (checkList != null && checkList.size() > 0) {
                    if (!checkList.get(0).getCampaignId().equals(vo.getCampaignID())) {
                        return (new Resp(RespCode.FAILURE, "Objective Name : " + vo.getCampaignName() + " Exists."));
                    }
                }
                campaign = mstCampaignFacade.find(vo.getCampaignID());
                campaign.setCampaignName(vo.getCampaignName());
                campaign.setIsActive(vo.isIsActive());
                return (new Resp(RespCode.SUCCESS, "Objective Updated Successfully.", campaign.getCampaignId()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }
    }

    public Resp createDepartment(DepartmentVO vo) {
        logger.info("--- Creating Department Master ---");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {

            MstDepartment dept = null;
            if (vo.getDepartmentID() == null) {
                logger.info("--- creating new Department ---");
                List<MstDepartment> checkList = commonDao.checkDepartmentByName(vo.getDepartmentName());
                if (checkList != null && checkList.size() > 0) {
                    return (new Resp(RespCode.FAILURE, "Department Name : " + vo.getDepartmentName() + " Exists."));
                }
                dept = new MstDepartment();
                dept.setDeptName(vo.getDepartmentName());
                dept.setIsBlocked(vo.getIsActive());
                departmentDao.create(dept);
                return (new Resp(RespCode.SUCCESS, "Department Created Successfully.", dept.getMstDeptId()));
            } else {
                logger.info("--- updating Department ---");
                List<MstDepartment> checkList = commonDao.checkDepartmentByName(vo.getDepartmentName());
                if (checkList != null && checkList.size() > 0) {
                    if (!checkList.get(0).getMstDeptId().equals(vo.getDepartmentID())) {
                        return (new Resp(RespCode.FAILURE, "Department Name : " + vo.getDepartmentName() + " Exists."));
                    }
                }

                dept = departmentDao.find(vo.getDepartmentID());
                dept.setDeptName(vo.getDepartmentName());
                dept.setIsBlocked(vo.getIsActive());
                return (new Resp(RespCode.SUCCESS, "Department Updated Successfully.", dept.getMstDeptId()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }
    }

    public Resp createMartingType(MstMktgVO vo) {
        logger.info("----- Inside Creating Marketing Types -----");
        logger.info("Checking the Marketing Type Existing with Present Name : " + vo.getIsBlocked());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        List<MstMktg> list = commonDao.checkMarketingByName(vo.getMktgName(), vo.getIsBlocked());
        if (!list.isEmpty()) {
            for (MstMktg mktg : list) {
                if (mktg.getIsBlocked().equals(vo.getIsBlocked())) {
                    logger.info("Marketing Type with the same name exist.");
                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
                }
            }

        }
        if (vo.getMktgTyped() == null) {
            MstMktg mktg = new MstMktg();
            mktg.setMktgName(vo.getMktgName());
            mktg.setIsBlocked(vo.getIsBlocked());
            mktgTypeDao.create(mktg);
            logger.info("------  Marketing Type Created Successfully ----- Id " + mktg.getMktgId());
            return new Resp(RespCode.SUCCESS, "Marketing Type Created Successfully with Id : " + mktg.getMktgId(), mktg.getMktgId());
        } else {
            logger.info("--- Updating Marketing Type with Id " + vo.getMktgTyped() + " Value " + vo.getMktgName());
            MstMktg mktg = mktgTypeDao.find(vo.getMktgTyped());
            mktg.setMktgName(vo.getMktgName());
            mktg.setIsBlocked(vo.getIsBlocked());
            return new Resp(RespCode.SUCCESS, "Marketing Type Updated Successfully.");
        }
    }

    public List<MstReasonRejectionVO> getAllReasonForRejection(String isApprover) {
        logger.info("----- Inside Getting All Reason For Rejection ---- " + isApprover);
        List<MstReasonRejection> list = commonDao.getAllReasonRejection(isApprover);
        return VOUtilOtherMaster.convertMstReasonRejection(list);
    }

    public Resp createReasonForRejection(MstReasonRejectionVO vo) {
        logger.info("------- Creating ReasonForRejection ----- ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
//        List<MstReasonRejection> list = commonDao.checkReasonByName(vo.getReasonName(), vo.getIsBlocked());
//        if (!list.isEmpty()) {
//            for (MstReasonRejection mktg : list) {
//                if (mktg.getIsBlocked().equals(vo.getIsBlocked())) {
//                    logger.info("Reason Rejection Type with the same name exist.");
//                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
//                }
//            }
//        }
        if (vo.getReasonId() == null) {
            MstReasonRejection rejection = new MstReasonRejection();
            rejection.setReasonName(vo.getReasonName());
            rejection.setIsBlocked(vo.getIsBlocked());
            rejection.setIsApprover(vo.getIsApprover());
            reasonDao.create(rejection);
            logger.info("------  Reason Rejection Created Successfully ----- Id " + rejection.getReasonRejectionId());
            return new Resp(RespCode.SUCCESS, "Reason Rejection Creation Successfully With Id " + rejection.getReasonRejectionId(), rejection.getReasonRejectionId());
        } else {
            MstReasonRejection reason = reasonDao.find(vo.getReasonId());
            reason.setReasonName(vo.getReasonName());
            reason.setIsBlocked(vo.getIsBlocked());
            return new Resp(RespCode.SUCCESS, "Reason Rejection Updated Successfully ");
        }
    }

    public List<ProblemMasterVO> getAllProblemMaster() {
        logger.info("----- Inside Getting All Problem ------ ");
        List<MstProblem> list = problemDao.findAll();
        return VOUtilOtherMaster.convertMstProblem(list);
    }

    public Resp createProblem(ProblemMasterVO vo) {
        logger.info("------- Creating Problem Type ----- : " + vo.getProblemId() + " Is Blocked : " + vo.getIsBlocked());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        List<MstProblem> list = commonDao.checkProblemByName(vo.getProblemName(), vo.getIsBlocked());
        if (!list.isEmpty()) {
            for (MstProblem mktg : list) {
                if (mktg.getIsBlocked().equals(vo.getIsBlocked())) {
                    logger.info("Problem Type with the same name exist.");
                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
                }
            }
        }
        if (vo.getProblemId() == null) {
            MstProblem problem = new MstProblem();
            problem.setProblemName(vo.getProblemName());
            problem.setIsBlocked(vo.getIsBlocked());
            problemDao.create(problem);
            logger.info("------  Problem Type Created Successfully ----- Id " + problem.getProblemTypeId());
            return new Resp(RespCode.SUCCESS, "Problem Type Creation Successfully With Id " + problem.getProblemTypeId(), problem.getProblemTypeId());
        } else {
            MstProblem problem = problemDao.find(vo.getProblemId());
            problem.setProblemName(vo.getProblemName());
            problem.setIsBlocked(vo.getIsBlocked());
            logger.info("Problem Updated Sucessfully.");
            return new Resp(RespCode.SUCCESS, "Problem Type Updated Successfully.", problem.getProblemTypeId());
        }
    }

    public List<MstPromotionTypeVO> getAllPromotionMaster() {
        logger.info("----- Inside Getting All Promotion Type ------ ");
        List<MstPromotionType> list = promotionDao.findAll();
        return VOUtilOtherMaster.convertMstPromotion(list);
    }

    public Resp createPromotionType(MstPromotionTypeVO vo) {
        logger.info("------- Creating Promotion Type ----- Blocked :" + vo.getIsBlocked());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        List<MstPromotionType> list = commonDao.checkPromotionTypeByName(vo.getPromotionName(), vo.getIsBlocked());
        if (!list.isEmpty()) {
            for (MstPromotionType mktg : list) {
                logger.info("======== Status : " + mktg.getIsBlocked());
                if (mktg.getIsBlocked().equals(vo.getIsBlocked())) {
                    logger.info("Promotion Type with the same name exist.");
                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
                }
            }
        }
        if (vo.getId() == null) {
            MstPromotionType promotionType = new MstPromotionType();
            promotionType.setPromoTypeName(vo.getPromotionName());
            promotionType.setIsBlocked(vo.getIsBlocked());
            promotionDao.create(promotionType);
            logger.info("------  Promotion Type Created Successfully ----- Id " + promotionType.getPromoTypeId());
            return new Resp(RespCode.SUCCESS, "Problem Type Creation Successfully With Id " + promotionType.getPromoTypeId(), promotionType.getPromoTypeId());
        } else {
            MstPromotionType promotion = promotionDao.find(vo.getId());
            promotion.setIsBlocked(vo.getIsBlocked());
            promotion.setPromoTypeName(vo.getPromotionName());
            return new Resp(RespCode.SUCCESS, "Problem Type Updated Successfully ");
        }
    }

    public List<MstTaskVO> getAllTaskMaster() {
        logger.info(" ----- Inside Getting All Task Master Type ------ ");
        List<MstTask> list = taskDao.findAll();
        return VOUtilOtherMaster.convertTask(list);
    }

    public Resp createTaskMaster(MstTaskVO vo) {
        logger.info(" ----- Creating Task Master Type ----- ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        List<MstTask> list = commonDao.checkTaskkTypeByName(vo.getTaskName(), vo.getIsBlocked());
        if (!list.isEmpty()) {
            for (MstTask mktg : list) {
                if (mktg.getIsBlocked().equals(vo.getIsBlocked())) {
                    logger.info("Task Type with the same name exist.");
                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
                }
            }

        }
        if (vo.getTaskId() == null) {
            MstTask task = new MstTask();
            task.setTaskName(vo.getTaskName());
            task.setIsBlocked(vo.getIsBlocked());
            taskDao.create(task);
            logger.info(" ------   Task Type Created Successfully ---- Id " + task.getTaskName());
            return new Resp(RespCode.SUCCESS, "Task Type Creation Successfully With Id " + task.getTaskId(), task.getTaskId());
        } else {
            MstTask task = taskDao.find(vo.getTaskId());
            task.setTaskName(vo.getTaskName());
            task.setIsBlocked(vo.getIsBlocked());
            return new Resp(RespCode.SUCCESS, "Task Type Updation Completed Successfully.");
        }
    }

    public List<MstEventVO> getAllEventMaster() {
        logger.info(" ----- Inside Getting All Campaign Master Type ------ ");
        List<MstEvent> list = eventDao.findAll();
        return VOUtilOtherMaster.convertEvent(list);
    }

    public Resp createEventMaster(MstEventVO vo) {
        logger.info(" ----- Creating Campaign Master Type ----- ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        List<MstEvent> list = commonDao.checkEventTypeByName(vo.getEventName(), vo.getIsBlocked());
        if (!list.isEmpty()) {
            for (MstEvent event : list) {
                if (event.getIsBlocked().equals(vo.getIsBlocked())) {
                    logger.info("Campaign Type with the same name exist.");
                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
                }
            }
        }
        if (vo.getEventId() == null) {
            MstEvent event = new MstEvent();
            event.setEventName(vo.getEventName());
            event.setIsBlocked(vo.getIsBlocked());
            eventDao.create(event);
            logger.info(" ------   Campaign Type Created Successfully ---- Id " + event.getEventName());
            return new Resp(RespCode.SUCCESS, "Campaign Creation Successfully With Id " + event.getEventId(), event.getEventId());
        } else {
            MstEvent event = eventDao.find(vo.getEventId());
            event.setEventName(vo.getEventName());
            event.setIsBlocked(vo.getIsBlocked());
            return new Resp(RespCode.SUCCESS, "Campaign Updation Completed Successfully.");
        }
    }

    public List<MstStatusVO> getAllLeadTimeStatus() {
        logger.info("---- Getting All Status for Lead time ---");
        List<MstStatus> list = commonDao.getStatusForLeadTimes();
        return VOUtilOtherMaster.convertStatus(list);
    }

    public Resp updateStatus(MstStatusVO vo) {
        logger.info(" --- Updating Status --- ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        MstStatus status = statusDao.find(vo.getStatusId());
        if (status == null) {
            return new Resp(RespCode.FAILURE, "Response ID not Found.");
        }
        if (vo.getL1() != null) {
            status.setL1(vo.getL1());
        }
        if (vo.getL2() != null) {
            status.setL2(vo.getL2());
        }
        if (vo.getL5() != null) {
            status.setL5(vo.getL5());
        }
        return new Resp(RespCode.SUCCESS, "Status Successfully Updated.");
    }

    public MstLeadTime getMstLeadTime() {
        logger.info("--- Getting MST Lead Time ---- ");
        return commonDao.getMstLeadTime();
    }

    public Resp updateMstLeadTime(MstLeadTime leadTime) {
        logger.info(" ---- Update Mst Lead Time ---- ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(leadTime, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        MstLeadTime leadTimeDB = commonDao.getMstLeadTime();
        leadTimeDB.setValue(leadTime.getValue());
        return new Resp(RespCode.SUCCESS, "Lead time Updated Successfully.");
    }

    public List<DepartmentVO> getAllDepartments() {
        logger.info("--- get all Department ----");
        List<MstDepartment> departmentList = departmentDao.findAll();
        return VOUtilOtherMaster.convertMstDepartment(departmentList);
    }

    public List<DepartmentVO> getUserDepartments(String empID) {
        logger.info("--- get all Department ----");

        MstEmployee emp = mstEmployeeFacade.find(Long.valueOf(empID));
        Collection<MapUserDepartment> mapUserDeptList = emp.getMapUserDepartmentCollection();
        List<DepartmentVO> deptList = new ArrayList<DepartmentVO>();
        if (mapUserDeptList != null && mapUserDeptList.size() > 0) {
            for (MapUserDepartment userDept : mapUserDeptList) {
                deptList.add(new DepartmentVO(userDept.getMstDepartment().getMstDeptId(), userDept.getMstDepartment().getDeptName()));
            }
        }
        return deptList;
    }

    public void check() {
        MstRole role = roleDao.find(new Long("4"));
        System.out.println("role " + role);
    }

    public List<MstZoneVO> getAllZone() {
        logger.info("--- Getting all zone data --- ");
        List<MstZone> zones = zoneDao.findAll();
        return VOUtilOtherMaster.convertZone(zones);
    }

    public Resp createZoneMaster(MstZoneVO vo) {
        logger.info(" ----- Creating Zone Master Type ----- " + vo.getId());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        List<MstZone> list = commonDao.checkZoneByCode(vo.getZonecode());
        if (!list.isEmpty()) {
            for (MstZone mktg : list) {
                if (mktg.getIsBlocked().equals(vo.getIsBlocked())) {
                    logger.info("Zone Type with the same name exist.");
                    return new Resp(RespCode.FAILURE, "No Change In Value Or Data Exist.");
                }
            }

        }
        if (vo.getId() == null) {
            MstZone zone = new MstZone();
            zone.setZoneCode(vo.getZonecode());
            zone.setZoneName(vo.getZonename());
            zone.setIsBlocked(vo.getIsBlocked());
            zoneDao.create(zone);
            logger.info(" ------   Zone Created Successfully ---- Id " + zone.getZoneId());
            return new Resp(RespCode.SUCCESS, "Zone Creation Successful With Id " + zone.getZoneId(), zone.getZoneId());
        } else {
            MstZone zone = zoneDao.find(vo.getId());
            zone.setZoneCode(vo.getZonecode());
            zone.setZoneName(vo.getZonename());
            zone.setIsBlocked(vo.getIsBlocked());
            return new Resp(RespCode.SUCCESS, "Zone Updation Completed Successfully.");
        }
    }

    public List<MstCalendarVO> getAllCalendar() {
        logger.info("--- inside getting all calendar dates ---- ");
        List<MstCalender> list = calendarDao.findAll();
        return VOUtilOtherMaster.getCalendars(list);

    }

    public Resp insertCalendar(MstCalendarVO vo) {
        logger.info(" --- Inside Inserting Calendar Object --- Id : " + vo.getCalendarId());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(vo, buffer);
        //logger.info(" PAYLOAD ::::::\n " + buffer);
        if (vo.getCalendarId() == null) {
            List<MstCalender> cal = commonDao.getCaledarsByDate(vo.getDate());
            if (cal != null && !cal.isEmpty()) {
                return new Resp(RespCode.FAILURE, "Calender Exist With the same Date.");
            }
            MstCalender calender = new MstCalender();
            if (vo.getDate() == null) {
                return new Resp(RespCode.FAILURE, "Date Format Not Correct. Format is (yyyy-mm-dd");
            }
            calender.setCalDate(getDateFromStr(vo.getDate()));
            calender.setDateDescription(vo.getDescription());
            calendarDao.create(calender);
            return new Resp(RespCode.SUCCESS, "Calendar Object Inserted Successfully");
        } else {
            logger.info("Updating Calender Object.");
            MstCalender calendar = calendarDao.find(vo.getCalendarId());
            if (vo.getDate() == null) {
                return new Resp(RespCode.FAILURE, "Date Format Not Correct. Format is (yyyy-mm-dd");
            }
            calendar.setCalDate(getDateFromStr(vo.getDate()));
            calendar.setDateDescription(vo.getDescription());
            return new Resp(RespCode.SUCCESS, "Calendar Data Updated with Id " + vo.getCalendarId());
        }
    }

    public Date getDateFromStr(String str) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(str);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MstCalendarVO> getAllCalendarByYearMonth(String year, String month) {
        if (month == null) {
            // logger.info(" --- Searching Calendar By Year Only. --- " + year);
            List<MstCalender> list = commonDao.getCaledarsByYear(year);
            return VOUtilOtherMaster.getCalendars(list);
        } else {
            // logger.info(" --- Searching Calendar By Year And Month --- year " + year + " month " + month);
            List<MstCalender> list = commonDao.getCaledarsByYearAndMonth(year, month);
            return VOUtilOtherMaster.getCalendars(list);
        }
    }

    public Resp deleteCalanderValue(String calValID) {
        if (calValID != null) {
            MstCalender calender = calendarDao.find(new Long(calValID));
            if (calender != null) {
                int i = commonDao.deleteCalanderValueByID(calender.getMstCalenderId());
                return new Resp(RespCode.SUCCESS, "Calendar Value deleted succesfully.");
            } else {
                return new Resp(RespCode.FAILURE, "Invalid Calendar Value.");
            }
        } else {
            return new Resp(RespCode.FAILURE, "Invalid Calendar Value.");
        }
    }

    public ValidateMCResp validateMC(String mcCode, Long mstPromoId, String isInitiateFlag) {
        try {
            logger.info("------- Inside Validate MC : " + mcCode + " ----------");
            logger.info("------- isIntiailizedFlag : " + isInitiateFlag + ". ------- mstPromoId : " + mstPromoId);
            Mch mch = mchFacade.find(mcCode);
            if (mch == null) {
                return (new ValidateMCResp(new Resp(RespCode.FAILURE, "MC Code : " + mcCode + " not found.")));
            }
            MstPromo promo = null;
            if (isInitiateFlag.equalsIgnoreCase("1")) {
                boolean isFound = false;
                promo = mstPromoFacade.find(mstPromoId);
                Collection<MapPromoMch> mchList = promo.getMapPromoMchCollection();
                for (MapPromoMch promoMch : mchList) {
                    if (promoMch.getMch().getMcCode().equalsIgnoreCase(mcCode)) {
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    if (mch.getIsBlocked()) {
                        return (new ValidateMCResp(new Resp(RespCode.FAILURE, "Entered MC Code : " + mcCode + " Is blocked.")));
                    }
                    return (new ValidateMCResp(new Resp(RespCode.SUCCESS, "MC Code Detail."), mch.getMcCode(), CommonUtil.getStringByReplaceCommaWithSpace(mch.getMcName())));
                } else {
//                    return (new ValidateMCResp(new Resp(RespCode.FAILURE, "MC Code : " + mcCode + " Does Not Belong To Category Name / Sub Category Name : " + promo.getCategory() + " / " + promo.getSubCategory()), "-", "-"));
                    return (new ValidateMCResp(new Resp(RespCode.FAILURE, "MC Code : " + mcCode + " Does Not Belong To selected category/sub category of the request.")));
                }
            } else {
                if (mch.getIsBlocked()) {
                    return (new ValidateMCResp(new Resp(RespCode.FAILURE, "Entered MC Code : " + mcCode + " Is blocked.")));
                }
                return (new ValidateMCResp(new Resp(RespCode.SUCCESS, "MC Code Detail."), mch.getMcCode(), mch.getMcName()));
            }
        } catch (Exception ex) {
            logger.error("---- Error : " + ex.getMessage());
            ex.printStackTrace();
            return (new ValidateMCResp(new Resp(RespCode.FAILURE, "Error : " + ex.getMessage())));

        }
    }

    public ValidateArticleMCResp sendArticleMCFileForvalidate(String filePath, String empID, Long mstPromoId, boolean isQtyIncludedFile) {
        try {
            logger.info("---------- Inside Send Article File For Validate Service------");
            logger.info("----- Emp ID : " + empID + "----FilePath : " + filePath + " ------MstPromoId : " + mstPromoId);
            MstPromo promo = null;
            Map<String, String> mcMap = null;
            if (mstPromoId != null) {
                promo = mstPromoFacade.find(mstPromoId);
                Collection<MapPromoMch> mapPromoMchList = promo.getMapPromoMchCollection();
                if (mapPromoMchList != null && mapPromoMchList.size() > 0) {
                    mcMap = new HashMap<String, String>();
                    for (MapPromoMch mch : mapPromoMchList) {
                        mcMap.put(mch.getMch().getMcCode(), mch.getMch().getMcCode());
                    }
                    logger.info("------- MCH MAP SIze : " + mcMap.size());
                }

            }
            ValidateArticleMCResp validateResp = null;
            if (isQtyIncludedFile) {
                validateResp = odsArticleService.validateInitiationODSArticleListFromFile(filePath, empID, mcMap, promo);
            } else {
                validateResp = odsArticleService.validateInitiationODSArticleListFromFileWithoutQty(filePath, empID, mcMap, promo);
            }
            logger.info("-------- validate Article Resp Code : " + validateResp.getResp().getRespCode());
            return (validateResp);

        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Error :" + ex.getMessage()));
        }
    }
}
