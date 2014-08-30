package com.fks.promo.init;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.commonDAO.CommonProposalDAO;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstLeadTime;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.promo.entity.TransPromoStatus;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstLeadTimeFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.facade.TransPromoStatusFacade;
import com.fks.promo.init.vo.AssignTeamReq;
import com.fks.promo.init.vo.RejectTransPromoReq;
import com.fks.promo.init.vo.SearchTeamMemberReq;
import com.fks.promo.init.vo.SearchTeamMemberResp;
import com.fks.promo.init.vo.TeamMember;
import com.fks.promo.service.CommonPromoMailService;
import com.fks.promo.service.NotificationMessage;
import com.fks.promo.service.NotificationType;
import com.fks.promotion.service.util.StatusUpdateUtil;
import java.io.File;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
@WebService
public class CommonPromotionService {

    Logger logger = Logger.getLogger(CommonPromotionService.class.getName());
    @EJB
    private TransPromoFacade transPromoFacade;
    @EJB
    private MstLeadTimeFacade leadDao;
    @EJB
    private CommonDAO commonDao;
    @EJB
    private CommonPromotionDao commonPromoDao;
    @EJB
    private CommonProposalDAO proposalDao;
    @EJB
    private MstStatusFacade statusDao;
    @EJB
    private MstEmployeeFacade empDao;
    @EJB
    private CommonPromoMailService mailService;
    @EJB
    private TransPromoStatusFacade transPromoStatusFacade;

    public Resp checkLeadTimeValidation(Long transPromoId) {
        System.out.println("Checking Lead Time Validation for the Trans Promotion id " + transPromoId);
        Calendar c = Calendar.getInstance();
        TransPromo transPromo = transPromoFacade.find(transPromoId);
        if (transPromo == null) {
            return new Resp(RespCode.FAILURE, "Incorrect Trans Promo Id");
        }
        if (transPromo.getTransPromoConfigCollection() == null) {
            return new Resp(RespCode.FAILURE, "No Configuration Data Found");
        }
        MstLeadTime leadTime = leadDao.find(new Long("1"));
//        List<Date> list = commonDao.getAllHolidayFromCalendar();
//        System.out.println("List Of Holidays Are " + list);
        if (transPromo.getTransPromoConfigCollection() == null || transPromo.getTransPromoConfigCollection().isEmpty()) {
            return new Resp(RespCode.FAILURE, "Valid From and Valid To dates are not assigned to Request No : R" + transPromoId + ".");
        }
        for (TransPromoConfig config : transPromo.getTransPromoConfigCollection()) {
            Date fromDate = config.getValidFrom();
            System.out.println(" ===== Promotion Start Date ===== " + fromDate);
            if (fromDate == null) {
                return new Resp(RespCode.FAILURE, "Configuration Valid From Date Can Not be Null");
            }
            try {
                //adding list of holiday as per the requirement Date : 16-09-2013
                c.setTime(config.getValidFrom());
                c.add(Calendar.DATE, -1);
                String newValidFromDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime()) + " 23:59:59";
                Date newValidFromDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newValidFromDateStr);
//                int hoursPassed = getDiffHoursWithHolidays(CommonUtil.getDBDateTimeformat(config.getValidFrom()));
                int hoursPassed = getDiffHoursWithHolidays(CommonUtil.getDBDateTimeformat(newValidFromDate));
                System.out.println(" ---- Lead Time Hours is " + leadTime.getValue());
                System.out.println(" ---- Hours Passed is " + hoursPassed);

                if (hoursPassed < leadTime.getValue() || hoursPassed < 0) {
                    System.out.println(" ---- Lead Time Hours Passed. Can Not Approve the Request");
//                    return new Resp(RespCode.FAILURE, "Lead Time Hours Passed. Can Not Approve the Request for ID : R" + transPromoId);

                    System.out.println("---- current Date : " + c.getTime());
//                    double diff = (double) leadTime.getValue() / 24;
//                    int leadTimeDays = (int) Math.ceil(diff);
//                    System.out.println("----- leadTime Days : " + leadTimeDays);

                    int daysCounter = 1;

                    while (hoursPassed < leadTime.getValue()) {
                        //  c.setTime(new Date());
                        c.add(Calendar.DATE, daysCounter);
                        System.out.println("$$$$$$$$$$$$ check Date : " + c.getTime());
//                        String SuggestedDateNew = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(c.getTime());                        
                        String SuggestedDateNew = (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime()) + " 23:59:59";
                        System.out.println("---------- Suggested Date : " + SuggestedDateNew);
//                        Date dateSuggestedDate = new SimpleDateFormat("yyyy-MM-dd").parse(SuggestedDateNew);
                        Date dateSuggestedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(SuggestedDateNew);
                        System.out.println("%%%%%%%%%%%% suggested Date : " + dateSuggestedDate.toString());
                        hoursPassed = getDiffHoursWithHolidays(CommonUtil.getDBDateTimeformat(dateSuggestedDate));

                        System.out.println("----- new hours passed : " + hoursPassed);
                        //leadTimedaysCounter++;
                    }

                    c.add(Calendar.DATE, 1);
                    String suggestedDate = (new SimpleDateFormat("dd-MM-yyyy")).format(c.getTime());
                    System.out.println("---- Suggested Date : " + suggestedDate);
                    return new Resp(RespCode.FAILURE, "Promotion date not meeting the lead time.<br/> The minimum valid from date can be " + suggestedDate, suggestedDate);
                } else {
                    logger.info(" ---- Success");
                    return new Resp(RespCode.SUCCESS, "success");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int checkLeadTimeValidity(String validFrom) throws Exception {
        validFrom = validFrom + " 23:59:59";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date validFromDate = format.parse(validFrom);
        logger.info("Valid From Date " + validFromDate);
        Date currentDate = new Date();
        logger.info("Current Date " + currentDate);
        Period p = new Period(new Date().getTime(), validFromDate.getTime(), PeriodType.hours());
        int hoursDiff = p.getHours();
        logger.info("Hours Difference is " + hoursDiff);
        return hoursDiff;
    }

//    public int getDiffHoursWithHolidays(String validFrom, List<Date> holidayList) {
    public int getDiffHoursWithHolidays(String validFrom) {
        int hours = 0;
        try {
            List<Date> holidayList = commonDao.getAllHolidayFromCalendar();
            System.out.println("List Of Holidays Are " + holidayList);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date validFromDate = format.parse(validFrom);


//            System.out.println("*********** Valid Date : "+ validFromDate.toString());
            Period p = new Period(new Date().getTime(), validFromDate.getTime(), PeriodType.hours());
            hours = p.getHours();
//            System.out.println("********** No of Hours : "+ hours);
            double diff = (double) hours / 24;
            int days = (int) Math.ceil(diff);
//            System.out.println("No. of Days from Date is " + days);
            int holidays = 0;
//            for (int x = 0; x <= days; x++) {
            for (int x = 0; x < days; x++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, x);
                SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sFormat.format(cal.getTime());
                //System.out.println("Checking Date " + dateStr + " For Holiday");
                if (holidayList != null) {
                    for (Date holiday : holidayList) {
                        if (holiday.toString().equals(dateStr)) {
                            holidays += 1;
                            //System.out.println("Holiday Added");
                        }
                    }
                }
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    holidays += 1;
                    //System.out.println("Sunday : Holiday Added");
                }
            }
            //System.out.println("No. of HOliday Are " + holidays);
            hours = hours - (holidays * 24);
            //System.out.println("Difference Hours Is " + hours);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hours;
    }

    public int getDiffHoursWithHolidaysForEscalation(Date updatedDate, List<Date> holidayList, boolean isHold) {
        System.out.println("##################### inside  escalation");
        int hours = 0;
        try {
//            validFrom = validFrom + " 23:59:59";
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
//            Date updatedDate = format.parse(updatedDateStr);
            logger.info("Updated Date " + updatedDate);
            System.out.println("Updated Date " + updatedDate);
//            Date currentDate = new Date();
//            logger.info("Current Date " + currentDate);
            Period p = new Period(updatedDate.getTime(), new Date().getTime(), PeriodType.hours());
            hours = p.getHours();
            logger.info("Hours Passed is " + hours);
            System.out.println("Hours Passed is " + hours);
            if (hours <= 0) {
                return hours; // updated date is future date. No need to take any action.
            }
            double diff = (double) hours / 24;
//            logger.info(diff);
            int days = (int) Math.ceil(diff);
            logger.info("No. of Days from Date is " + days);
            System.out.println("No. of Days from Date is " + days);
            int holidays = 0;
            for (int x = 0; x <= days; x++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(updatedDate);
                cal.add(Calendar.DATE, x);
                SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sFormat.format(cal.getTime());
//                logger.info("Checking Date " + dateStr + " For Holiday");
                if (holidayList != null) {
                    for (Date holiday : holidayList) {
                        if (holiday.toString().equals(dateStr)) {
                            holidays += 1;
                            logger.info("Holiday Added");
                            System.out.println("holiday added");
                        }
                    }
                }
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    holidays += 1;
                    logger.info("Sunday : Holiday Added");
                    System.out.println("Sunday : Holiday Added");
                }
            }
            logger.info("No. of HOliday Are " + holidays);
            System.out.println("No. of HOliday Are " + holidays);
            hours = hours - (holidays * 24);
            logger.info("Difference Hours Is " + hours);
            System.out.println("Difference Hours Is " + hours);
            DateFormat format = new SimpleDateFormat("HH");
            String updateDateHour = format.format(updatedDate);
            logger.info("--- Update Date Hour : " + updateDateHour);
            System.out.println("--- Update Date Hour : " + updateDateHour);
            if (!isHold && (Integer.parseInt(updateDateHour)) >= 12) {
//                hours += 12;                
                hours -= 12;
                System.out.println("---------------- inside 12 hours passed");
            }
            logger.info("Difference Hours Is " + hours);
            System.out.println("Difference Hours Is " + hours);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hours;
    }

    public Resp escalateL1PendingTransPromoRequest() {
        logger.info("==== Finding L1 Pending Request for escalation ===== ");

        List<TransPromo> transpromolist = commonPromoDao.getAllTransPromotionByStatusId(CommonStatusConstants.PROMO_SUBMITTED);
        if (transpromolist == null || transpromolist.isEmpty()) {
            logger.info("No Trans Promo To Process");
            return new Resp(RespCode.SUCCESS, "No Request to Process");
        }
        MstStatus L1_ESCALATED_STATUS = statusDao.find(CommonStatusConstants.PROMO_L1_ESCALATED);


        MstStatus approvalStatus = statusDao.find(new Long("1"));
        if (approvalStatus == null) {
            return new Resp(RespCode.FAILURE, "L1 Status Can Not be Null for Status Id 1");
        }
        int hours = Integer.parseInt(approvalStatus.getL1());
        logger.info("---- Hours For L1 Approval is --- " + hours);
        TransPromoStatus promoStatus=null;
        if (transpromolist != null && !transpromolist.isEmpty()) {
            for (TransPromo transPromo : transpromolist) {
                logger.info("------ L1 Trans Promo ID : " + transPromo.getTransPromoId());
                logger.info("------ L1 Trans Promo Date : " + transPromo.getUpdatedDate());
                List<Date> list = commonDao.getAllHolidayFromCalendar(transPromo.getUpdatedDate());
                logger.info("List Of Holidays Are " + list);
//            if (list != null && !list.isEmpty()) {
                logger.info("Checking Lead Time Validation for the Trans Promotion id " + transPromo.getTransPromoId() + " Updated Date " + transPromo.getUpdatedDate());
                int hoursPassed = getDiffHoursWithHolidaysForEscalation(transPromo.getUpdatedDate(), list, false);
                if (hoursPassed <= 0) {
                    logger.info("Updated Date is Future Date. Do Not Escalate the Request");
                    continue;
                }
                if (hoursPassed >= hours) {
                    logger.info("Lead Time Hours Passed. Changing status to L1_ESCALATED.");
                       promoStatus = StatusUpdateUtil.submitTransPromoStatus(null, L1_ESCALATED_STATUS, transPromo.getMstStatus(), "Lead Time Hours Passed. Changing status to L1_ESCALATED", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(L1_ESCALATED_STATUS);
                    transPromo.setUpdatedDate(new Date());
                    // TODO change all trans_promo_article status
                    int i = commonPromoDao.changeAllTransPromoMCStatus(transPromo.getTransPromoId(), L1_ESCALATED_STATUS.getStatusId(), CommonStatusConstants.PROMO_SUBMITTED);
                    logger.info("=== No. of Trans Promo Articles Affected Are " + i);

                    NotificationMessage msg = new NotificationMessage(NotificationType.L1_ESCALATED, transPromo.getTransPromoId().toString());
                    mailService.sendNotificationMessage(msg);
                }
            }
        } else {
            logger.info("==== No Trans Promo To Proceed ==== ");
        }

        return new Resp(RespCode.SUCCESS, "Success");
    }

    public Resp escalateL1HoldTransPromoRequest() {
        logger.info("==== Escalating Request To L2 Level from L1 Level ===== ");
        logger.info("==== Esclating L1 Hold Request (Pending Request by L1) ====");
        List<TransPromo> transpromolist = commonPromoDao.getAllTransPromotionByStatusId(CommonStatusConstants.PROMO_L1_HOLD);
        MstStatus L1_HOLD_ESCALATED_STATUS = statusDao.find(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);

        MstStatus holdStatus = statusDao.find(new Long("2"));
        if (holdStatus == null) {
            return new Resp(RespCode.FAILURE, "Hold L1 Status Can Not be Null");
        }
        int hours = Integer.parseInt(holdStatus.getL1());
        logger.info("---- Hours For L1 Hold is " + hours);
         TransPromoStatus promoStatus=null;
        if (transpromolist != null && !transpromolist.isEmpty()) {
            for (TransPromo transPromo : transpromolist) {

                logger.info("------L1 Hold Trans Promo ID : " + transPromo.getTransPromoId());
                logger.info("------L1 Hold Trans Promo Date : " + transPromo.getUpdatedDate());

                List<Date> list = commonDao.getAllHolidayFromCalendar(transPromo.getUpdatedDate());
                logger.info("List Of Holidays Are For L1 Hold" + list);

                logger.info("Checking Lead Time Validation for the Trans Promotion id " + transPromo.getTransPromoId() + " Updated Date " + transPromo.getUpdatedDate());
                int hoursPassed = getDiffHoursWithHolidaysForEscalation(transPromo.getUpdatedDate(), list, true);
                if (hoursPassed <= 0) {
                    logger.info("Updated Date is Future Date. Do Not Escalate the Request");
                    continue;
                }
                if (hoursPassed >= hours) {
                    logger.info("Lead Time Hours Passed. Changing status to L1_HOLD_ESCALATED(21)");
                      promoStatus = StatusUpdateUtil.submitTransPromoStatus(null, L1_HOLD_ESCALATED_STATUS, transPromo.getMstStatus(), "Lead Time Hours Passed. Changing status to L1_HOLD_ESCALATED", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(L1_HOLD_ESCALATED_STATUS);
                    transPromo.setUpdatedDate(new Date());
                    // TODO change all trans_promo_article status
                    int i = commonPromoDao.changeAllTransPromoMCStatus(transPromo.getTransPromoId(), L1_HOLD_ESCALATED_STATUS.getStatusId(), CommonStatusConstants.PROMO_L1_HOLD);
                    logger.info("=== No. of Trans Promo Articles Affected Are " + i);

                }
            }
        } else {
            logger.info("==== No Trans Promo To Proceed ==== ");
        }

        return new Resp(RespCode.SUCCESS, "Success");
    }

    public Resp escalateL2PendingTransPromoRequest() {
        logger.info("==== Escalating Request To L5 Level from L2 Level ===== ");
        logger.info("==== Esclating Pending for Approval Request (Pending Request by L2) ====");

        List<TransPromo> transpromolist = commonPromoDao.getAllTransPromotionByStatusIdForL2PendingEscalation();
        MstStatus L2_ESCALATED_STATUS = statusDao.find(CommonStatusConstants.PROMO_L2_ESCALATED);
        List<Date> list = commonDao.getAllHolidayFromCalendar();
        NotificationMessage msg;
        String strTicketId;
        StringBuilder sb = new StringBuilder("");

        logger.info("List Of Holidays Are " + list);
        MstStatus approvalStatus = statusDao.find(new Long("1"));
        if (approvalStatus == null) {
            return new Resp(RespCode.FAILURE, "Status Can Not be Null");
        }
        System.out.println("==== No of Trans Promo : " + transpromolist.size());
        int hoursl2 = Integer.parseInt(approvalStatus.getL2());
        logger.info("---- Hours For L2 Approval is " + hoursl2);
        if (transpromolist != null && !transpromolist.isEmpty()) {
            logger.info("====== Inside processing list.....");
             TransPromoStatus promoStatus=null;
            for (TransPromo transPromo : transpromolist) {
                logger.info("Checking Lead Time Validation for the Trans Promotion id " + transPromo.getTransPromoId() + " Updated Date " + transPromo.getUpdatedDate());
                int hoursPassed = getDiffHoursWithHolidaysForEscalation(transPromo.getUpdatedDate(), list, false);
                if (hoursPassed <= 0) {
                    logger.info("Updated Date is Future Date. Do Not Escalate the Request");
                    continue;
                }
                if (hoursPassed >= hoursl2) {
                    logger.info("Lead Time Hours Passed. Changing status to L2_ESCALATED_STATUS(22)");
                      promoStatus = StatusUpdateUtil.submitTransPromoStatus(null, L2_ESCALATED_STATUS, transPromo.getMstStatus(), "Lead Time Hours Passed. Changing status to L2_ESCALATED_STATUS", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(L2_ESCALATED_STATUS);
                    transPromo.setUpdatedDate(new Date());
//                    transPromo.setRemarks("Lead Time for L2 Pending is Over. System is escalating promotion");
                    // TODO change all trans_promo_article status
                    int i = commonPromoDao.changeAllTransPromoMCStatus(transPromo.getTransPromoId(), L2_ESCALATED_STATUS.getStatusId(), CommonStatusConstants.PROMO_L1_ESCALATED);
                    logger.info("=== No. of Trans Promo Articles Affected Are " + i);


                    // consolidate Mail For Business Exigency.

                    if (i > 0) {
//                         sb.append("<br/>");
                        strTicketId = "T" + transPromo.getMstPromo().getPromoId() + "-R" + transPromo.getTransPromoId();
                        sb.append(strTicketId).append("<br/>");
                    }
                }
            }
            //System.out.println("Promotion ID : \n" + sb.toString());
            if (sb.toString().trim().length() > 0) {

                msg = new NotificationMessage();
                msg = new NotificationMessage();
                msg.setId(sb.toString());
                msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_MAIL);
                msg.setIsEscalated(true);
                mailService.sendNotificationMessage(msg);
            }

        } else {
            logger.info("==== No Trans Promo To Proceed ==== ");
        }
        return new Resp(RespCode.SUCCESS, "Success");
    }

    public Resp escalateL2HoldTransPromoRequest() {
        logger.info("==== Escalating Request To L5 Level from L2 Level ===== ");
        logger.info("==== Esclating L2 Hold Request (Pending Request by L1 HOLD) ====");
        List<TransPromo> transpromolist = commonPromoDao.getAllTransPromotionByStatusId(CommonStatusConstants.PROMO_L2_HOLD);
        MstStatus L2_HOLD_ESCALATED_STATUS = statusDao.find(CommonStatusConstants.PROMO_L2_HOLD_ESCALATED);
        List<Date> list = commonDao.getAllHolidayFromCalendar();
        logger.info("List Of Holidays Are " + list);
        MstStatus holdStatus = statusDao.find(new Long("2"));
        if (holdStatus == null) {
            return new Resp(RespCode.FAILURE, "Status Can Not be Null");
        }
        int hours = Integer.parseInt(holdStatus.getL2());
        logger.info("---- Hours For L2 Hold is " + hours);
        if (list != null) {
            TransPromoStatus promoStatus;
            for (TransPromo transPromo : transpromolist) {
                logger.info("Checking Lead Time Validation for the Trans Promotion id " + transPromo.getTransPromoId() + " Updated Date " + transPromo.getUpdatedDate());
                int hoursPassed = getDiffHoursWithHolidaysForEscalation(transPromo.getUpdatedDate(), list, true);
                if (hoursPassed <= 0) {
                    logger.info("Updated Date is Future Date. Do Not Escalate the Request");
                    continue;
                }
                if (hoursPassed >= hours) {
                    logger.info("Lead Time Hours Passed. Changing status to L2_HOLD_ESCALATED(23)");
                      promoStatus = StatusUpdateUtil.submitTransPromoStatus(null, L2_HOLD_ESCALATED_STATUS, transPromo.getMstStatus(), "Lead Time Hours Passed. Changing status to L2_HOLD_ESCALATED", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(L2_HOLD_ESCALATED_STATUS);
                    transPromo.setUpdatedDate(new Date());
//                    transPromo.setRemarks("Lead Time for L2 HOLD is Over. System is Rejecting Promotion");
                    // TODO change all trans_promo_article status
                    int i = commonPromoDao.changeAllTransPromoMCStatus(transPromo.getTransPromoId(), L2_HOLD_ESCALATED_STATUS.getStatusId(), CommonStatusConstants.PROMO_L1_HOLD);
                    logger.info("=== No. of Trans Promo Articles Affected Are " + i);
                }
            }
        }
        return new Resp(RespCode.SUCCESS, "Success");
    }

    public Resp escalateL5PendingTransPromoRequest() {
        logger.info("==== Escalating Request To Promo Manager Level from L5 Level  (Pending) ===== ");
        List<TransPromo> transpromolist = commonPromoDao.getAllTransPromotionByStatusId(CommonStatusConstants.TEAM_ASSIGNED);
        logger.info("==== No. of Request to Process are ==== " + transpromolist);
        if (transpromolist == null || transpromolist.isEmpty()) {
            logger.info("No Trans Promo To Process");
            return new Resp(RespCode.SUCCESS, "No Request to Process");
        }
        MstStatus L5_ESCALATED_STATUS = statusDao.find(CommonStatusConstants.PROMO_L5_ESCALATED);
        List<Date> list = commonDao.getAllHolidayFromCalendar();
        logger.info("List Of Holidays Are " + list);
        MstStatus approvalStatus = statusDao.find(new Long("1"));
        if (approvalStatus == null) {
            return new Resp(RespCode.FAILURE, "Status Can Not be Null");
        }
        int hoursl5 = Integer.parseInt(approvalStatus.getL5());
        logger.info("---- Hours For L5 Approval is " + hoursl5);
        if (transpromolist != null && !transpromolist.isEmpty()) {
            TransPromoStatus promoStatus;
            for (TransPromo transPromo : transpromolist) {
                logger.info("Lead Time Validation for the Trans Promotion id " + transPromo.getTransPromoId() + " Updated Date " + transPromo.getUpdatedDate());
                int hoursPassed = getDiffHoursWithHolidaysForEscalation(transPromo.getUpdatedDate(), list, false);
                if (hoursPassed <= 0) {
                    logger.info("Updated Date is Future Date. Do Not Escalate the Request");
                    continue;
                }
                if (hoursPassed >= hoursl5) {
                    logger.info("Lead Time Hours Passed. Changing status to L5_ESCALATED_STATUS(32)");
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(null, L5_ESCALATED_STATUS, transPromo.getMstStatus(), "Lead Time Hours Passed. Changing status to L5_ESCALATED_STATUS", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(L5_ESCALATED_STATUS);
                    transPromo.setUpdatedDate(new Date());
//                    transPromo.setRemarks("Lead Time for L5 Pending is Over. System is Rejecting Promotion");
                    // TODO change all trans_promo_article status
                    int i = commonPromoDao.changeAllTransPromoMCStatus(transPromo.getTransPromoId(), L5_ESCALATED_STATUS.getStatusId(), CommonStatusConstants.PROMO_L5_ESCALATED);
                    logger.info("=== No. of Trans Promo Articles Affected Are " + i);
                }
            }
        } else {
            logger.info("==== No Trans Promo To Proceed ==== ");
        }
        return new Resp(RespCode.SUCCESS, "Success");
    }

    public Resp escalateL5HoldTransPromoRequest() {
        logger.info("==== Escalating Request To L5 Level ===== ");
        List<TransPromo> transpromolist = commonPromoDao.getAllTransPromotionByStatusId(CommonStatusConstants.TEAM_MEMBER_HOLD);
        logger.info("==== No. of Request to Process are ==== " + transpromolist);
        if (transpromolist == null || transpromolist.isEmpty()) {
            logger.info("No Trans Promo To Process");
            return new Resp(RespCode.SUCCESS, "No Request to Process");
        }
        MstStatus L5_HOLD_ESCALATED_STATUS = statusDao.find(CommonStatusConstants.PROMO_L5_HOLD_ESCALATED);
        List<Date> list = commonDao.getAllHolidayFromCalendar();
        logger.info("List Of Holidays Are " + list);
        MstStatus holdStatus = statusDao.find(new Long("2"));
        if (holdStatus == null) {
            return new Resp(RespCode.FAILURE, "Status Can Not be Null");
        }
        int hours = Integer.parseInt(holdStatus.getL5());
        logger.info("---- Hours For L5 Hold is " + hours);
        if (list != null) {
            TransPromoStatus promoStatus;
            for (TransPromo transPromo : transpromolist) {
                logger.info("Checking Lead Time Validation for the Trans Promotion id " + transPromo.getTransPromoId() + " Updated Date " + transPromo.getUpdatedDate());
                int hoursPassed = getDiffHoursWithHolidaysForEscalation(transPromo.getUpdatedDate(), list, true);
                if (hoursPassed <= 0) {
                    logger.info("Updated Date is Future Date. Do Not Escalate the Request");
                    continue;
                }
                if (hoursPassed >= hours) {
                    logger.info("Lead Time Hours Passed. Changing status to L5_HOLD_ESCALATED(33)");
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(null, L5_HOLD_ESCALATED_STATUS, transPromo.getMstStatus(), "Lead Time Hours Passed. Changing status to L5_HOLD_ESCALATED", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(L5_HOLD_ESCALATED_STATUS);
                    transPromo.setUpdatedDate(new Date());
//                    transPromo.setRemarks("Lead Time for L5 HOLD is Over. System is Rejecting Promotion");
                    // TODO change all trans_promo_article status
                    int i = commonPromoDao.changeAllTransPromoMCStatus(transPromo.getTransPromoId(), L5_HOLD_ESCALATED_STATUS.getStatusId(), CommonStatusConstants.PROMO_L1_HOLD);
                    logger.info("=== No. of Trans Promo Articles Affected Are " + i);
                }
            }
        }
        return new Resp(RespCode.SUCCESS, "Success");
    }

    public SearchTeamMemberResp searchTeamMember(SearchTeamMemberReq request) {

        logger.info(" === Searching Team Member === " + request.getStoreCode());
        SearchTeamMemberResp response = new SearchTeamMemberResp(new Resp(RespCode.SUCCESS, "success"));
        List<TeamMember> memberList = new ArrayList<TeamMember>();
        List<MstEmployee> list = commonDao.searchEmployeeByStoreCode(request.getStoreCode());
        if (list != null && !list.isEmpty()) {
            for (MstEmployee emp : list) {
                TeamMember member = new TeamMember(emp.getEmpId(), emp.getEmployeeName());
                memberList.add(member);
            }
        }
        logger.info("Search Promotion Request completed. List Size " + memberList.size());
        response.setTeamlist(memberList);
        return response;
    }

    public SearchTeamMemberResp searchL1TeamMemberForL1ByPass(SearchTeamMemberReq request) {

        logger.info(" === Searching L1 Team Member === " + request.getStoreCode());
        SearchTeamMemberResp response = new SearchTeamMemberResp(new Resp(RespCode.SUCCESS, "success"));
        List<TeamMember> memberList = new ArrayList<TeamMember>();
        List<MstEmployee> list = new ArrayList<MstEmployee>();
        if (request.getStoreCode().equalsIgnoreCase("ALL")) {
            list = commonDao.l1TeamMemberForL1ByPass(request.getL2EmpId());
        } else {
            list = commonDao.l1TeamMemberForL1ByPass(request.getStoreCode(), request.getL2EmpId());
        }

        if (list != null && !list.isEmpty()) {
            for (MstEmployee emp : list) {
                TeamMember member = new TeamMember(emp.getEmpId(), emp.getEmployeeName());
                memberList.add(member);
            }
        }
        logger.info("Search Promotion Request completed. List Size " + memberList.size());
        response.setTeamlist(memberList);
        return response;
    }

    public Resp assignTeamMember(AssignTeamReq request) {
        logger.info(" ==== Assigning Member =======");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);

        int i = commonDao.assignTeamMember(request.getTransPromoId(), request.getPromoMgrId(), request.getTeamMemberId());
        logger.info("Request Updation Status : " + i);
        NotificationMessage msg = new NotificationMessage(NotificationType.TEAM_MEMBER_ASSIGNED, request.getTransPromoId().toString(), request.getTeamMemberId().toString());
        mailService.sendNotificationMessage(msg);

        return new Resp(RespCode.SUCCESS, "Request Assigned.");
    }

    public Resp assignTeamMemberList(AssignTeamReq request) {
        logger.info(" ==== Assigning Member =======");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        TransPromoStatus promoStatus = null;
        MstEmployee employee = null;
        TransPromo promo;
        for (Long transPromoId : request.getTransPromoIdList()) {
              employee = empDao.find(request.getPromoMgrId());
            promo= transPromoFacade.find(transPromoId);
            //Phase 3 CR - Promo Req Status History 15-11-2013
            promoStatus = StatusUpdateUtil.submitTransPromoStatus(employee, new MstStatus(CommonStatusConstants.TEAM_ASSIGNED), promo.getMstStatus(), "Request is assigned.", promo);

            transPromoStatusFacade.create(promoStatus);
            int i = commonDao.assignTeamMember(transPromoId, request.getPromoMgrId(), request.getTeamMemberId());
            logger.info("Request Updation Status : " + i);
            NotificationMessage msg = new NotificationMessage(NotificationType.TEAM_MEMBER_ASSIGNED, transPromoId.toString(), request.getTeamMemberId().toString());
            mailService.sendNotificationMessage(msg);
        }
        return new Resp(RespCode.SUCCESS, "Request Assigned.");
    }

    public Resp rejectTransPromo(RejectTransPromoReq request) {
        logger.info(" === Rejecting Trans Promo ===  ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);

        commonDao.rejectTransPromoByPromoMgr(request.getTransPromoId(), CommonStatusConstants.PROMO_MGR_REJECTED, request.getEmpId(), request.getReasonForRejection(), request.getRejectionRemarks());
        logger.info("Request Rejected Successfully");
        NotificationMessage msg = new NotificationMessage();
        msg.setId(request.getTransPromoId().toString());
        msg.setEmpId(request.getEmpId().toString());
        msg.setNotificationType(NotificationType.INITIATOR_MAIL);
        String strMsg = "Request is Rejected.";
        msg.setMsg(strMsg);
        mailService.sendNotificationMessage(msg);
        return new Resp(RespCode.SUCCESS, "Request Rejected Successfully.");
    }

    public Resp rejectTransPromoList(RejectTransPromoReq request) {
        logger.info(" === Rejecting Trans Promo ===  ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        TransPromoStatus promoStatus = null;
        MstEmployee employee = null;
        TransPromo promo;
        for (Long transPromoId : request.getTransPromoIdList()) {
            employee = empDao.find(request.getEmpId());
            promo= transPromoFacade.find(transPromoId);
            //Phase 3 CR - Promo Req Status History 15-11-2013
            promoStatus = StatusUpdateUtil.submitTransPromoStatus(employee, new MstStatus(CommonStatusConstants.PROMO_MGR_REJECTED), promo.getMstStatus(), request.getRejectionRemarks(), promo);
            transPromoStatusFacade.create(promoStatus);
            commonDao.rejectTransPromoByPromoMgr(transPromoId, CommonStatusConstants.PROMO_MGR_REJECTED, request.getEmpId(), request.getReasonForRejection(), request.getRejectionRemarks());
            logger.info("Request Rejected Successfully");

        }
        return new Resp(RespCode.SUCCESS, "Request Rejected Successfully.");
    }

    public List<String> getCategoryListForLevel1Approver(Long empId) {
        logger.info("===== Getting All category for level 1 Approver ===== ");
        return commonDao.getCategoryListForLevel1Approver(empId);
    }

    public List<String> getCategoryListForLevel2Approver(Long empId) {
        logger.info("===== Getting All category for level 2 Approver ===== ");
        return commonDao.getCategoryListForLevel2Approver(empId);
    }

    public Resp archiveProposalOtherFiles() {
        try {
            logger.info("---------- Proposal Additional File Archieval....");
            String fileLocation = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ARCHIEVED_FILE);
            List<MstProposal> proposalArchiveList = proposalDao.getProposalForArchival(CommonUtil.get_TwoMonths_OldDate_FromCurrentDate());
            if (proposalArchiveList != null && proposalArchiveList.size() > 0) {
                for (MstProposal proposal : proposalArchiveList) {
                    if (proposal.getOtherFilePath() != null) {
                        File f = new File(proposal.getOtherFilePath());
                        if (f.exists()) {
                            System.out.println("----- File Deleted for Id : " + proposal.getProposalId());
                            f.delete();
                        }
                        proposal.setOtherFilePath(fileLocation);
                    }
                }
                return new Resp(RespCode.SUCCESS, "Proposal Files Archieved Successfully. Size : " + proposalArchiveList.size());
            } else {
                logger.info("-------- No Proposal Files For Archival.");
                return new Resp(RespCode.SUCCESS, "No Proposal Files For Archival.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Resp(RespCode.FAILURE, ex.getMessage());
        }

    }

    public static void main(String[] args) throws ParseException {
        CommonPromotionService commonPromotionService = new CommonPromotionService();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date validFromDate = format.parse("2013-11-09 17:59:19");
        int i = commonPromotionService.getDiffHoursWithHolidaysForEscalation(validFromDate, null, false);
        System.out.println("i : " + i);
    }
}
