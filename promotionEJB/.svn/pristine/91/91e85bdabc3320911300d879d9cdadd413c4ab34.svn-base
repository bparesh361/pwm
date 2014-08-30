/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.approval.service;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonApprovalDao;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.promo.entity.TransPromoStatus;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.TransPromoConfigFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.facade.TransPromoStatusFacade;
import com.fks.promo.init.CommonPromotionService;
import com.fks.promo.service.CommonPromoMailService;
import com.fks.promo.service.NotificationMessage;
import com.fks.promo.service.NotificationType;
import com.fks.promotion.approval.vo.PromotionApprRejHoldReq;
import com.fks.promotion.approval.vo.PromotionApprRejHoldRes;
import com.fks.promotion.service.util.StatusUpdateUtil;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author nehabha
 */
@Stateless
@LocalBean
@WebService
public class ApprRejHoldPromotionReqService {

    private static final Logger logger = Logger.getLogger(ApprRejHoldPromotionReqService.class.getName());
    @EJB
    private MstEmployeeFacade userDao;
    @EJB
    private CommonApprovalDao commonDao;
    @EJB
    private MstStatusFacade statusDao;
    @EJB
    private TransPromoFacade transPromoDao;
    @EJB
    private CommonPromotionService commonPromotionService;
    @EJB
    private TransPromoConfigFacade transPromoConfigDao;
    @EJB
    private CommonPromoMailService mailService;
    @EJB
    TransPromoStatusFacade transPromoStatusFacade;

    public PromotionApprRejHoldRes createL1ApprRejHoldReqList(List<PromotionApprRejHoldReq> requestList) throws Exception {
        logger.info("-----Inside approved ,hold or reject promotion request service for level 1:------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(requestList, buffer);
        System.out.println(" PAYLOAD ::::::\n " + buffer);
        NotificationMessage msg = null;
        String strMsg = "";
        for (PromotionApprRejHoldReq request : requestList) {
            System.out.println("-------- Trans Promo ID : " + request.getTransPromoId());
            MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
            if (approver == null) {
                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "User is not valid."));
            }
            if (request.getStatusType() == null) {
                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Search type should not be null."));
            }
            TransPromo transPromo = null;
            if (request.getTransPromoId() != null) {
                transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                if (transPromo == null) {
                    return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                }
            }
            switch (request.getStatusType()) {
                case HOLD:
                    if (request.getIsHold() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Hold. "));

                    }
                    break;
                case APPROVED:
                    Resp rep = commonPromotionService.checkLeadTimeValidation(request.getTransPromoId());
                    if (!rep.getRespCode().toString().equalsIgnoreCase("SUCCESS")) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, rep.getMsg()));
                    }

                    if (request.getIsApproved() && !((transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)) || (transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)))) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Hold. "));

                    }
                    break;
                case BUSINESSEXIGENCY:
                    if (!(transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED) || transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD))) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted OR Hold Request Can be assigned to business exigency. "));
                    }
                    break;
                case CHANGEDATE:
                    if (request.getFromDate() == null || request.getToDate() == null) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "From date or Todate should not be null " + request.getTransPromoId()));
                    }
                    if (request.getIsChangedate() == true) {
                        if (request.getIsChangedate() && !(transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED) || (transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)))) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Change date time. "));
                        }
                    }
                    break;
            }

        }
        logger.info("###################### L1 Validation Completed For Hold/Reject/Approve #######################");
        int updateCount = 0;
        int holdReqCount = 0;
        MstStatus status = null;

        TransPromoStatus promoStatus;
        for (PromotionApprRejHoldReq request : requestList) {
            MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
            TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
            switch (request.getStatusType()) {
                case HOLD:
                    updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_HOLD);
                    logger.info("Trans Promo Article updateCount for hold request : " + updateCount);

                    status = statusDao.find(CommonStatusConstants.PROMO_L1_HOLD);
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is on HOLD By L1.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee1(approver);
                    commonDao.updateTransPromo(transPromo);

                    logger.info("Promotion hold successfully." + transPromo);
                    break;
                case APPROVED:
                    updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_APPROVE);
                    logger.info("Trans Promo Article updateCount for Approved request : " + updateCount);

                    holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_APPROVE);
                    logger.info("Search Trans Promo ArticleupdateCount For approved request : " + holdReqCount);

                    if (holdReqCount == 0) {
                        status = statusDao.find(CommonStatusConstants.PROMO_L1_APPROVE);
                        //Phase 3 CR - Promo Req Status History 15-11-2013
                        promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is Approved By L1.", transPromo);
                        transPromoStatusFacade.create(promoStatus);
                        transPromo.setMstStatus(status);
                        transPromo.setUpdatedDate(new Date());
                        transPromo.setMstEmployee1(approver);
                        commonDao.updateTransPromo(transPromo);
                        logger.info("Promotion approved successfully.");
                        msg = new NotificationMessage();
                        msg.setId(transPromo.getTransPromoId().toString());
                        msg.setEmpId(approver.getEmpId().toString());
                        msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                        strMsg = "Request is approved.";
                        msg.setMsg(strMsg);
                        mailService.sendNotificationMessage(msg);
                    }
                    break;
                case REJECTED:
                    updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_REJECT);
                    logger.info("Trans Promo Article updateCount for rejected request : " + updateCount);
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$ Rejected $$$$$$$$$$$$");
                    status = statusDao.find(CommonStatusConstants.PROMO_L1_REJECT);
                    //Phase 3 CR - Promo Req Status History 15-11-2013 , remove rejection remarks , and added field in status table
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), request.getRejectionRemarks(), transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    System.out.println("&&&&&&&&& Rejection REmarks : " + promoStatus.getTransPromoStatusId());
                    transPromo.setReasonForRejection(request.getReasonForRejection());
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee1(approver);
                    transPromo.setRejectionRemarks(request.getRejectionRemarks());
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Promotion rejected successfully.");
                    msg = new NotificationMessage();
                    msg.setId(transPromo.getTransPromoId().toString());
                    msg.setEmpId(approver.getEmpId().toString());
                    msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                    strMsg = "Request is rejected.";
                    msg.setMsg(strMsg);
                    mailService.sendNotificationMessage(msg);

                    break;
                case BUSINESSEXIGENCY:
                    updateCount = commonDao.updateTransPromoMCL1ToExigency(request.getTransPromoId(), CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING);
                    logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);
                    status = statusDao.find(CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING);
                    //Phase 3 CR - Promo Req Status History 15-11-2013
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is assigned to Business Exegency By L1.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee1(approver);
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Approved by business exigency");
                    break;
                case CHANGEDATE:
                    List<TransPromoConfig> transPromoConfigs = commonDao.selectTransConfigfortranspromoid(request.getTransPromoId());
                    logger.info("Trans Promo Confing change date  for  request : " + transPromoConfigs.size());
                    if (transPromoConfigs != null) {
                        for (TransPromoConfig promoConfig : transPromoConfigs) {
                            promoConfig.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                            promoConfig.setValidTo(CommonUtil.getDBDate(request.getToDate()));
                            transPromoConfigDao.edit(promoConfig);
                        }
                        logger.info("Trans config updated success fully");
                    }
                    transPromo.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                    transPromo.setValidTo(CommonUtil.getDBDate(request.getToDate()));

                    updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_APPROVE);
                    logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

                    holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_APPROVE);
                    logger.info("Search Trans Promo ArticleupdateCount For Bussiness exigency request : " + holdReqCount);

                    if (holdReqCount == 0) {
                        status = statusDao.find(CommonStatusConstants.PROMO_L1_APPROVE);
                        //Phase 3 CR - Promo Req Status History 15-11-2013
                        promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Change date Approved by L1 user.", transPromo);
                        transPromoStatusFacade.create(promoStatus);
                        transPromo.setMstStatus(status);
                        transPromo.setUpdatedDate(new Date());
                        transPromo.setMstEmployee1(approver);
                        // transPromo.setRemarks("Change date Approved by L1 user");
                        commonDao.updateTransPromo(transPromo);
                        logger.info("Change date Approved by L1 user");
                        msg = new NotificationMessage();
                        msg.setId(transPromo.getTransPromoId().toString());
                        msg.setEmpId(approver.getEmpId().toString());
                        msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                        strMsg = "Request is Approved.";
                        msg.setMsg(strMsg);
                        mailService.sendNotificationMessage(msg);

                    }
                    break;
            }
        }
        PromotionApprRejHoldReq request = requestList.get(0);
        if (request.getIsHold() == true) {
            logger.info("Promotion request is hold : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are on hold."));
        } else if (request.getIsApproved() == true) {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are approved."));
        } else if (request.getIsRejected() == true) {
            logger.info("Promotion request is rejected : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are rejected."));
        } else if (request.getIsChangedate() == true) {
            logger.info("Change date Approved by L1 user: ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion request dates are cahnged."));
        } else {
            logger.info("Approved by business exigency : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are assigned to business exigency."));
        }
    }

    public PromotionApprRejHoldRes createL1ApprRejHoldReq(PromotionApprRejHoldReq request) throws Exception {
        PromotionApprRejHoldRes response = new PromotionApprRejHoldRes();
        logger.info("-----Inside approved ,hold or reject promotion request service for level 1:------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
        if (approver == null) {
            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "User is not valid."));
        }
        if (request.getStatusType() == null) {
            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Search type should not be null."));
        }

        //NEHA : Request is on HOLD.
        if (request.getStatusType() != null) {
            switch (request.getStatusType()) {
                case HOLD:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                        }

                        if (request.getIsHold() == true) {
                            if (request.getIsHold() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)) {
                                logger.info(" Is Hold one Request is hold or not :" + request.getIsHold());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Hold. "));

                            } else {
                                logger.info("Hold Promotion request recevied for  request id : " + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }

                            int updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_HOLD);
                            logger.info("Trans Promo Article updateCount for hold request : " + updateCount);

//                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_HOLD);
//                            logger.info("Search Trans Promo ArticleupdateCount For hold request : " + holdReqCount);

//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
//                            logger.info("Trans promo list Size : " + transPromos.size());
//                            if (transPromos != null) {
                            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L1_HOLD);
//                            if (status != null) {
                            for (TransPromo trans : transPromos) {
                                //Phase 3 CR - Promo Req Status History 15-11-2013
                                TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, trans.getMstStatus(), "Request is on Hold by L1.", trans);
                                transPromoStatusFacade.create(promoStatus);

                                trans.setMstStatus(status);
                                trans.setUpdatedDate(new Date());
                                commonDao.updateTransPromo(trans);
                                logger.info("Promotion hold successfully.");
                            }
//                            }
//                            }

                        }
                        transPromo.setMstEmployee1(approver);
                    }//

                    break;
                case APPROVED:
                    if (request.getTransPromoId() != null) {
                        Resp rep = commonPromotionService.checkLeadTimeValidation(request.getTransPromoId());

                        logger.info("----------- Lead Time Validation : " + rep);
                        logger.info("Response from check lead time validation for request : " + rep.getRespCode() + " === " + rep.getMsg());
                        if (rep.getRespCode().toString().equalsIgnoreCase("SUCCESS")) {
                            List<TransPromo> transPromos = new ArrayList<TransPromo>();
                            TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                            if (transPromo == null) {
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                            }
                            if (request.getIsApproved() == true) {
                                if (request.getIsApproved() && !((transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)) || (transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)))) {
                                    logger.info(" Is Approved one Request is Approved or not :" + request.getIsApproved());
                                    return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Hold. "));

                                } else {
                                    logger.info("Approved Promotion request recevied for  request id" + transPromo.getTransPromoId());
                                    transPromos.add(transPromo);
                                }

                                int updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_APPROVE);
                                logger.info("Trans Promo Article updateCount for Approved request : " + updateCount);

                                int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_APPROVE);
                                logger.info("Search Trans Promo ArticleupdateCount For approved request : " + holdReqCount);
                                if (holdReqCount == 0) {
//                                    transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
//                                    logger.info("Trans promo list Size : " + transPromos.size());
//                                    if (transPromos != null) {
                                    MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L1_APPROVE);
                                    if (status != null) {
                                        for (TransPromo trans : transPromos) {
                                            //Phase 3 CR - Promo Req Status History 15-11-2013
                                            TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, trans.getMstStatus(), "Request is Approved By L1.", trans);
                                            transPromoStatusFacade.create(promoStatus);

                                            trans.setMstStatus(status);
                                            trans.setUpdatedDate(new Date());
                                            commonDao.updateTransPromo(trans);
                                            logger.info("Promotion approved successfully.");
                                            NotificationMessage msg = new NotificationMessage();
                                            msg.setId(transPromo.getTransPromoId().toString());
                                            msg.setEmpId(approver.getEmpId().toString());
                                            msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                                            String strMsg = "Request is Approved.";
                                            msg.setMsg(strMsg);
                                            mailService.sendNotificationMessage(msg);


                                        }
                                    }
//                                    }
                                }
                            }
                            transPromo.setMstEmployee1(approver);
                        } else {
                            System.out.println("------------------****** Res failure " + rep.getSuggestedDate());
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, rep.getMsg()), true, rep.getSuggestedDate());
                        }//end of if-else for check lead time validation....

                    }//
                    break;
                case REJECTED:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                        }

                        if (request.getIsRejected() == true) {
//                            if (request.getIsRejected() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)) {
//                                logger.info(" Is rejected one Request is Approved or not :" + request.getIsRejected());
//                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Hold. "));
//
//                            } else {
//                                logger.info("Rejected Promotion request recevied for  request id" + transPromo.getTransPromoId());
//                                transPromos.add(transPromo);
//                            }

                            logger.info("Rejected Promotion request recevied for  request id" + transPromo.getTransPromoId());
                            transPromos.add(transPromo);

//                            int updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_REJECT);
//                            int updateCount = commonDao.updateTransPromoArticleL1Reject(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_REJECT);
                            int updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_REJECT);

                            logger.info("Trans Promo Article updateCount for rejected request : " + updateCount);

//                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_REJECT);
//                            logger.info("Search Trans Promo ArticleupdateCount For rejected request : " + holdReqCount);
//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
//                            logger.info("Trans promo list Size : " + transPromos.size());
                            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L1_REJECT);
//                            if (status != null) {
                            for (TransPromo trans : transPromos) {
                                //Phase 3 CR - Promo Req Status History 15-11-2013
                                TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, trans.getMstStatus(), "Request is rejected by L1.", trans);
                                transPromoStatusFacade.create(promoStatus);
                                trans.setMstStatus(status);
                                trans.setReasonForRejection(request.getReasonForRejection());
                                trans.setUpdatedDate(new Date());
                                commonDao.updateTransPromo(trans);
                                logger.info("Promotion rejected successfully.");
                                NotificationMessage msg = new NotificationMessage();
                                msg.setId(transPromo.getTransPromoId().toString());
                                msg.setEmpId(approver.getEmpId().toString());
                                msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                                String strMsg = "Request is rejected.";
                                msg.setMsg(strMsg);
                                mailService.sendNotificationMessage(msg);
                            }
//                            }

                        }
                        transPromo.setMstEmployee1(approver);
                    }//
                    break;
                case BUSINESSEXIGENCY:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion request not found with id : " + request.getTransPromoId()));
                        }

                        if (!(transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED) || transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD))) {
                            logger.info(" Is bussiness exigency one Request is Approved or not :");
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted OR Hold Request Can be assigned to business exigency. "));
                        }
                        logger.info("Bussiness exigency request recevied for  request id : " + transPromo.getTransPromoId());
                        transPromos.add(transPromo);

//                        int updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING);
                        int updateCount = commonDao.updateTransPromoMCL1ToExigency(request.getTransPromoId(), CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING);

                        logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

//                        int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING);
//                        logger.info("Search Trans Promo ArticleupdateCount For Bussiness exigency request : " + holdReqCount);

//                        transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
//                        logger.info("Trans promo list Size : " + transPromos.size());
//                        if (transPromos != null) {
                        MstStatus status = statusDao.find(CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING);
//                        if (status != null) {
                        for (TransPromo trans : transPromos) {
                            //Phase 3 CR - Promo Req Status History 15-11-2013
                            TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, trans.getMstStatus(), "Request is assigned to Business Exigency by L1.", trans);
                            transPromoStatusFacade.create(promoStatus);
                            trans.setMstStatus(status);
                            trans.setUpdatedDate(new Date());
//                            trans.setRemarks("L1 assigned promotion to  business exigency");
                            commonDao.updateTransPromo(trans);
                            logger.info("L1 assigned promotion to  business exigency");
                            NotificationMessage msg = new NotificationMessage();
                            msg = new NotificationMessage();
                            msg.setId(transPromo.getTransPromoId().toString());
                            msg.setEmpId(approver.getEmpId().toString());
                            msg.setIsEscalated(false);
                            msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_MAIL);
                            mailService.sendNotificationMessage(msg);
                        }
//                        }
//                        }
                        transPromo.setMstEmployee1(approver);
                    }//


                    break;
                case CHANGEDATE:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion request not found with id : " + request.getTransPromoId()));
                        }
                        if (request.getFromDate() == null || request.getToDate() == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "From date or Todate should not be null " + request.getTransPromoId()));
                        }
                        if (request.getIsChangedate() == true) {
                            if (request.getIsChangedate() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED) && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                                logger.info(" Is Change date one Request is Approved or not :" + request.getIsChangedate());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only Submitted Request Can be Change date time. "));

                            } else {
                                logger.info("Change date Approved Promotion request recevied for  request id" + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }
                            logger.info("Change date request recevied for  request id : " + transPromo.getTransPromoId());
//                            transPromos.add(transPromo);
                            List<TransPromoConfig> transPromoConfigs = commonDao.selectTransConfigfortranspromoid(request.getTransPromoId());
                            logger.info("Trans Promo Confing change date  for  request : " + transPromoConfigs.size());
                            if (transPromoConfigs != null) {
                                for (TransPromoConfig promoConfig : transPromoConfigs) {
                                    promoConfig.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                                    promoConfig.setValidTo(CommonUtil.getDBDate(request.getToDate()));
                                    transPromoConfigDao.edit(promoConfig);
                                }
                                logger.info("Trans config updated success fully");
                            }
                            transPromo.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                            transPromo.setValidTo(CommonUtil.getDBDate(request.getToDate()));

                            int updateCount = commonDao.updateTransPromoMCL1(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L1_APPROVE);
                            logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_APPROVE);
                            logger.info("Search Trans Promo ArticleupdateCount For Bussiness exigency request : " + holdReqCount);
//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
//                            logger.info("Trans promo list Size : " + transPromos.size());
                            if (holdReqCount == 0) {
//                                if (transPromos != null) {
                                MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L1_APPROVE);
                                if (status != null) {

                                    for (TransPromo trans : transPromos) {
                                        //Phase 3 CR - Promo Req Status History 15-11-2013
                                        TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, trans.getMstStatus(), "L1 approved request by changing date.", trans);
                                        transPromoStatusFacade.create(promoStatus);
                                        trans.setMstStatus(status);
                                        trans.setUpdatedDate(new Date());
                                        // trans.setRemarks("Change date Approved by L1 user");
                                        commonDao.updateTransPromo(trans);
                                        logger.info("Change date Approved by L1 user");
                                        NotificationMessage msg = new NotificationMessage();
                                        msg.setId(transPromo.getTransPromoId().toString());
                                        msg.setEmpId(approver.getEmpId().toString());
                                        msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                                        String strMsg = "Request is Approved.";
                                        msg.setMsg(strMsg);
                                        mailService.sendNotificationMessage(msg);
                                    }
                                }
//                                }
                            }
                        }
                        transPromo.setMstEmployee1(approver);
                    }//
                    break;

            }
        }
        TransPromo newPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
        if (request.getIsHold() == true) {
            logger.info("Promotion request is hold : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is hold with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsApproved() == true) {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is approved with id :  T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsRejected() == true) {
            logger.info("Promotion request is rejected : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is rejected with id :  T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsChangedate() == true) {
            logger.info("Change date Approved by L1 user: ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request Change date Approved by L1 user with id :  T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else {
            logger.info("Approved by business exigency : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is assigned to business exigency with id :  T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        }

    }

    public PromotionApprRejHoldRes createL2ApprRejHoldReqList(List<PromotionApprRejHoldReq> requestList) throws Exception {
        logger.info("-----Inside approved ,hold or reject promotion request service Level 2:------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(requestList, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        for (PromotionApprRejHoldReq request : requestList) {
            MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
            if (approver == null) {
                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "User is not valid."));
            }
            if (request.getStatusType() == null) {
                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Search type should not be null."));
            }
            TransPromo transPromo = null;
            if (request.getTransPromoId() != null) {
                transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                if (transPromo == null) {
                    return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                }
            }
            switch (request.getStatusType()) {
                case HOLD:
                    if (request.getIsHold() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)) {
                        logger.info(" Is Hold one Request is hold or not  LEVEL@:" + request.getIsHold());
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be Hold. "));

                    }
                    break;
                case APPROVED:
                    Resp rep = commonPromotionService.checkLeadTimeValidation(request.getTransPromoId());
                    logger.info("Response from check lead time validation for request level 2 : " + rep.getRespCode() + " === " + rep.getMsg());
                    if (!rep.getMsg().equalsIgnoreCase("SUCCESS")) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, rep.getMsg()));
                    }
                    if (request.getIsApproved() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                        logger.info(" Is Approved one Request is Approved or not Level2 :" + request.getIsApproved());
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be Approve. "));

                    }
                    break;
                case REJECTED:
                    if (request.getIsRejected() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                        logger.info(" Is rejected one Request is Approved or not level 2 :" + request.getIsRejected());
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated , L1 hold escalated And L2 Hold Request Can be Rejected. "));

                    }
                    break;
                case BUSINESSEXIGENCY:
                    if (!transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                        logger.info(" Is bussiness exigency one Request is Approved or not :");
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be approved by business exigency. "));

                    }
                    break;
                case CHANGEDATE:
                    if (request.getFromDate() == null || request.getToDate() == null) {
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "From date or Todate should not be null " + request.getTransPromoId()));
                    }
                    if (request.getIsChangedate() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)) {
                        logger.info(" Is Change date one Request is LEVEL 2 Approved or not :" + request.getIsChangedate());
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be Change date time. "));

                    }
                    break;
            }
        }
        logger.info("###################### L2 Validation Completed For Hold/Reject/Approve #######################");
        int updateCount = 0;
        int holdReqCount = 0;
        MstStatus status = null;
        TransPromoStatus promoStatus = null;
        for (PromotionApprRejHoldReq request : requestList) {
            MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
            TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
            switch (request.getStatusType()) {
                case HOLD:
                    updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_HOLD);
                    logger.info("Trans Promo Article updateCount for Level 2 hold request : " + updateCount);

                    status = statusDao.find(CommonStatusConstants.PROMO_L2_HOLD);
                    //Phase 3 CR - Promo Req Status History 15-11-2013
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is on Hold By L2.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee(approver);
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Promotion level2 hold successfully.");
                    break;
                case APPROVED:
                    updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_APPROVE);
                    logger.info("Trans Promo Article updateCount for Approved request level2 : " + updateCount);

                    holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L2_APPROVE);
                    logger.info("Search Trans Promo ArticleupdateCount For approved request  level 2: " + holdReqCount);
                    if (holdReqCount == 0) {
                        status = statusDao.find(CommonStatusConstants.PROMO_L2_APPROVE);
                        //Phase 3 CR - Promo Req Status History 15-11-2013
                        promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is approved By L2.", transPromo);
                        transPromoStatusFacade.create(promoStatus);
                        transPromo.setMstStatus(status);
                        transPromo.setUpdatedDate(new Date());
                        transPromo.setMstEmployee(approver);
                        commonDao.updateTransPromo(transPromo);
                        logger.info("Promotion level2 Approve successfully.");
                        NotificationMessage msg = new NotificationMessage();
                        msg.setId(transPromo.getTransPromoId().toString());
                        msg.setEmpId(approver.getEmpId().toString());
                        msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                        String strMsg = "Request is Approved.";
                        msg.setMsg(strMsg);
                        mailService.sendNotificationMessage(msg);
                    }
                    break;
                case REJECTED:
                    updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_REJECT);
                    logger.info("Trans Promo Article updateCount for rejected request : " + updateCount);

                    status = statusDao.find(CommonStatusConstants.PROMO_L2_REJECT);
                    //Phase 3 CR - Promo Req Status History 15-11-2013
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is Rejected By L2.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setReasonForRejection(request.getReasonForRejection());
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee(approver);
                    transPromo.setRejectionRemarks(request.getRejectionRemarks());
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Promotion level2 Reject successfully.");
                    NotificationMessage msg = new NotificationMessage();
                    msg.setId(transPromo.getTransPromoId().toString());
                    msg.setEmpId(approver.getEmpId().toString());
                    msg.setNotificationType(NotificationType.INITIATOR_MAIL);
                    String strMsg = "Request is rejected.";
                    msg.setMsg(strMsg);
                    mailService.sendNotificationMessage(msg);
                    break;
                case BUSINESSEXIGENCY:
                    updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
                    logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

                    status = statusDao.find(CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
                    //Phase 3 CR - Promo Req Status History 15-11-2013
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is send to Business Exigency  By L2.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee(approver);
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Promotion level2 business exigency pending successfully.");
                    break;
                case CHANGEDATE:
                    List<TransPromoConfig> transPromoConfigs = commonDao.selectTransConfigfortranspromoid(request.getTransPromoId());
                    logger.info("Trans Promo Confing change date  for  request : " + transPromoConfigs.size());
                    if (transPromoConfigs != null) {
                        for (TransPromoConfig promoConfig : transPromoConfigs) {
                            promoConfig.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                            promoConfig.setValidTo(CommonUtil.getDBDate(request.getToDate()));
                            transPromoConfigDao.edit(promoConfig);
                        }
                        logger.info("Trans config updated success fully");
                    }
                    transPromo.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                    transPromo.setValidTo(CommonUtil.getDBDate(request.getToDate()));
                    updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_APPROVE);
                    logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

                    holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L2_APPROVE);
                    logger.info("Search Trans Promo ArticleupdateCount For Bussiness exigency request : " + holdReqCount);

                    if (holdReqCount == 0) {
                        status = statusDao.find(CommonStatusConstants.PROMO_L2_APPROVE);
                        //Phase 3 CR - Promo Req Status History 15-11-2013
                        promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is Aprrove after change in date L2.", transPromo);
                        transPromoStatusFacade.create(promoStatus);
                        transPromo.setMstStatus(status);
                        transPromo.setUpdatedDate(new Date());
                        transPromo.setMstEmployee(approver);
                        commonDao.updateTransPromo(transPromo);
                        logger.info("Promotion level2 dates changed successfully.");
                    }
                    break;
            }
        }
        PromotionApprRejHoldReq request = requestList.get(0);
        if (request.getIsHold() == true) {
            logger.info("Promotion request is hold : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are on hold."));
        } else if (request.getIsApproved() == true) {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are approved."));
        } else if (request.getIsRejected() == true) {
            logger.info("Promotion request is rejected : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are rejected."));
        } else if (request.getIsChangedate() == true) {
            logger.info("Change date Approved by L2 user: ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion request dates are cahnged."));
        } else {
            logger.info("Approved by business exigency : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are assigned to business exigency."));
        }
    }
//Lever 2 Approve hold and reject

    public PromotionApprRejHoldRes createL2ApprRejHoldReq(PromotionApprRejHoldReq request) throws Exception {
        PromotionApprRejHoldRes response = new PromotionApprRejHoldRes();

        logger.info("-----Inside approved ,hold or reject promotion request service Level 2:------");
        System.out.println("-----Inside approved ,hold or reject promotion request service Level 2:------");

        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
        if (approver == null) {
            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "User is not valid."));
        }
        if (request.getStatusType() == null) {
            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Search type should not be null."));
        }

        //NEHA : Request is on HOLD.
        TransPromoStatus promoStatus = null;
        if (request.getStatusType() != null) {
            switch (request.getStatusType()) {
                case HOLD:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                        }

                        if (request.getIsHold() == true) {
                            if (request.getIsHold() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)) {
                                logger.info(" Is Hold one Request is hold or not  LEVEL@:" + request.getIsHold());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be Hold. "));

                            } else {
                                logger.info("Hold Promotion request recevied for  level 2 request id : " + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }

                            int updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_HOLD);
                            logger.info("Trans Promo Article updateCount for Level 2 hold request : " + updateCount);

//                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L2_HOLD);
//                            logger.info("Search Trans Promo ArticleupdateCount For hold request Level2 : " + holdReqCount);
//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_ESCALATED, CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);
//                            logger.info("Trans promo list Size : " + transPromos.size());
//                            if (transPromos != null) {
                            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L2_HOLD);
//                            if (status != null) {

                            for (TransPromo trans : transPromos) {
                                //Phase 3 CR - Promo Req Status History 15-11-2013
                                promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is on Hold By L2.", transPromo);
                                transPromoStatusFacade.create(promoStatus);
                                trans.setMstStatus(status);
                                trans.setUpdatedDate(new Date());
                                commonDao.updateTransPromo(trans);
                                logger.info("Promotion level2 hold successfully.");
                            }
//                            }
//                            }
                        }
                        transPromo.setMstEmployee(approver);
                    }//
                    break;
                case APPROVED:
                    if (request.getTransPromoId() != null) {
                        Resp rep = commonPromotionService.checkLeadTimeValidation(request.getTransPromoId());
                        logger.info("Response from check lead time validation for request level 2 : " + rep.getRespCode() + " === " + rep.getMsg());
                        if (rep.getMsg().equalsIgnoreCase("SUCCESS")) {
                            List<TransPromo> transPromos = new ArrayList<TransPromo>();
                            TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                            if (transPromo == null) {
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                            }

                            if (request.getIsApproved() == true) {
                                if (request.getIsApproved() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                                        && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                                        && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)
                                        && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                                        && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                                    logger.info(" Is Approved one Request is Approved or not Level2 :" + request.getIsApproved());
                                    return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be Approve. "));

                                } else {
                                    logger.info("Approved Promotion request recevied for level2 request id" + transPromo.getTransPromoId());
                                    transPromos.add(transPromo);
                                }

                                int updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_APPROVE);
                                logger.info("Trans Promo Article updateCount for Approved request level2 : " + updateCount);

                                int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L2_APPROVE);
                                logger.info("Search Trans Promo ArticleupdateCount For approved request  level 2: " + holdReqCount);
                                if (holdReqCount == 0) {
//                                    transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_ESCALATED, CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);
//                                    logger.info("Trans promo list Size : " + transPromos.size());
//                                    if (transPromos != null) {
                                    MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L2_APPROVE);
                                    if (status != null) {
                                        for (TransPromo trans : transPromos) {
                                            //Phase 3 CR - Promo Req Status History 15-11-2013
                                            promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is approved By L2.", transPromo);
                                            transPromoStatusFacade.create(promoStatus);
                                            trans.setMstStatus(status);
                                            trans.setUpdatedDate(new Date());
                                            commonDao.updateTransPromo(trans);
                                            logger.info("Promotion level 2approved successfully.");
                                        }
                                    }
//                                    }
                                }
                            }
                            transPromo.setMstEmployee(approver);
                        } else {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, rep.getMsg()), true, rep.getSuggestedDate());
                        }//end of if-else for check lead time validation....

                    }//
                    break;
                case REJECTED:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id level2 : " + request.getTransPromoId()));
                        }

                        if (request.getIsRejected() == true) {
                            if (request.getIsRejected() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                                logger.info(" Is rejected one Request is Approved or not level 2 :" + request.getIsRejected());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated , L1 hold escalated And L2 Hold Request Can be Rejected. "));

                            } else {
                                logger.info("Rejected Promotion request recevied for  request id level2 " + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }

//                            int updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_REJECT);
//                            int updateCount = commonDao.updateTransPromoArticleL2Reject(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_REJECT);
                            int updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_REJECT);

                            logger.info("Trans Promo Article updateCount for rejected request : " + updateCount);

//                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L2_REJECT);
//                            logger.info("Search Trans Promo ArticleupdateCount For rejected request : " + holdReqCount);
//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_ESCALATED, CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);
//                            logger.info("Trans promo list Size : " + transPromos.size());
                            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L2_REJECT);
//                            if (status != null) {
                            for (TransPromo trans : transPromos) {
                                //Phase 3 CR - Promo Req Status History 15-11-2013
                                promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is rejected By L2.", transPromo);
                                transPromoStatusFacade.create(promoStatus);
                                trans.setMstStatus(status);
                                trans.setUpdatedDate(new Date());
                                trans.setReasonForRejection(request.getReasonForRejection());
                                commonDao.updateTransPromo(trans);
                                logger.info("Promotion rejected level 2 successfully.");
                            }
//                            }

                        }
                        transPromo.setMstEmployee(approver);
                    }//
                    break;
                case BUSINESSEXIGENCY:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion request not found with id level 2 : " + request.getTransPromoId()));
                        }

                        if (!transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                                && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                                && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)
                                && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                                && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)) {
                            logger.info(" Is bussiness exigency one Request is Approved or not :");
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be approved by business exigency. "));

                        }
                        logger.info("Bussiness exigency request recevied for  request id : " + transPromo.getTransPromoId());
                        transPromos.add(transPromo);

                        int updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
                        logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

//                        int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
//                        logger.info("Search Trans Promo ArticleupdateCount For Bussiness exigency request : " + holdReqCount);
//                        transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_ESCALATED, CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);
//                        logger.info("Trans promo list Size : " + transPromos.size());
                        if (transPromos != null) {
                            MstStatus status = statusDao.find(CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
//                            if (status != null) {
                            for (TransPromo trans : transPromos) {
                                //Phase 3 CR - Promo Req Status History 15-11-2013
                                promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is assigned to business exigency By L2.", transPromo);
                                transPromoStatusFacade.create(promoStatus);
                                trans.setMstStatus(status);
//                                trans.setRemarks("L2 assigned promotion to business exigency");
                                trans.setUpdatedDate(new Date());
                                commonDao.updateTransPromo(trans);

                                NotificationMessage msg = new NotificationMessage();
                                msg = new NotificationMessage();
                                msg.setId(transPromo.getTransPromoId().toString());
                                msg.setEmpId(approver.getEmpId().toString());
                                msg.setIsEscalated(false);
                                msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_MAIL);
                                mailService.sendNotificationMessage(msg);
                                logger.info("Approved by business exigency");
                            }
//                            }
                        }
                        transPromo.setMstEmployee(approver);
                    }//
                    break;
                case CHANGEDATE:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion request not found with id : " + request.getTransPromoId()));
                        }
                        if (request.getFromDate() == null || request.getToDate() == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "From date or Todate should not be null " + request.getTransPromoId()));
                        }
                        if (request.getIsChangedate() == true) {
                            if (request.getIsChangedate() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_SUBMITTED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_HOLD)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD)) {
                                logger.info(" Is Change date one Request is LEVEL 2 Approved or not :" + request.getIsChangedate());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 escalated and L1 hold escalated Request Can be Change date time. "));

                            } else {
                                logger.info("Change date Approved Promotion request recevied for level2 request id" + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }
                            logger.info("LEVEL2 Change date request recevied for  request id : " + transPromo.getTransPromoId());
//                            transPromos.add(transPromo);
                            List<TransPromoConfig> transPromoConfigs = commonDao.selectTransConfigfortranspromoid(request.getTransPromoId());
                            logger.info("Trans Promo Confing change date  for  request : " + transPromoConfigs.size());
                            if (transPromoConfigs != null) {
                                for (TransPromoConfig promoConfig : transPromoConfigs) {
                                    promoConfig.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                                    promoConfig.setValidTo(CommonUtil.getDBDate(request.getToDate()));
                                    transPromoConfigDao.edit(promoConfig);
                                }
                                logger.info("Trans config updated success fully");
                            }
                            transPromo.setValidFrom(CommonUtil.getDBDate(request.getFromDate()));
                            transPromo.setValidTo(CommonUtil.getDBDate(request.getToDate()));
                            int updateCount = commonDao.updateTransPromoMCL2(request.getTransPromoId(), request.getEmpId(), CommonStatusConstants.PROMO_L2_APPROVE);
                            logger.info("Trans Promo Article updateCount for Business exigenct request : " + updateCount);

                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L2_APPROVE);
                            logger.info("Search Trans Promo ArticleupdateCount For Bussiness exigency request : " + holdReqCount);
//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.PROMO_L1_ESCALATED, CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);
//                            logger.info("Trans promo list Size : " + transPromos.size());
                            if (holdReqCount == 0) {
                                if (transPromos != null) {
                                    MstStatus status = statusDao.find(CommonStatusConstants.PROMO_L2_APPROVE);
                                    if (status != null) {
                                        System.out.println("################ TransPromo Size : " + transPromos.size());
                                        for (TransPromo trans : transPromos) {
                                            System.out.println("######################### trans status history change.....");
                                            //Phase 3 CR - Promo Req Status History 15-11-2013
                                            promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request date is changed By L2.", transPromo);
                                            transPromoStatusFacade.create(promoStatus);
                                            trans.setMstStatus(status);
                                            //trans.setRemarks("Change date Approved by L2 user");
                                            trans.setUpdatedDate(new Date());
                                            commonDao.updateTransPromo(trans);
                                            logger.info("Change date Approved by L2 user");
                                        }
                                    }
                                }
                            }
                        }
                        transPromo.setMstEmployee(approver);
                    }//
                    break;

            }
        }
        TransPromo newPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
        if (request.getIsHold() == true) {
            logger.info("Promotion request is hold : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is hold with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsApproved() == true) {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is approved with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsRejected() == true) {
            logger.info("Promotion request is rejected : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is rejected with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsChangedate() == true) {
            logger.info("Change date Approved by L2 user: ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request Change date Approved by L2 user with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else {
            logger.info("Approved by business exigency : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is assigned to Business Exigency with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        }

    }

    public PromotionApprRejHoldRes createBusinessExigencyApprRejHoldReqList(List<PromotionApprRejHoldReq> requestList) throws Exception {
        logger.info("-----Inside approved ,hold or reject promotion request service Business Exigency:------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(requestList, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        for (PromotionApprRejHoldReq request : requestList) {
            MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
            if (approver == null) {
                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "User is not valid."));
            }
            if (request.getStatusType() == null) {
                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Search type should not be null."));
            }
            TransPromo transPromo = null;
            if (request.getTransPromoId() != null) {
                transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                if (transPromo == null) {
                    return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                }
            }
            switch (request.getStatusType()) {
                case REJECTED:
                    if (request.getIsRejected() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD_ESCALATED)) {
                        logger.info(" Is Hold one Request is hold or not for business exigency:" + request.getIsRejected());
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 buseness exigency pending and L1 buseness exigency pending Request Can be rejected. "));
                    }
                    break;
                case APPROVED:
                    if (request.getIsApproved() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_ESCALATED)
                            && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD_ESCALATED)) {
                        logger.info(" Is Approved one Request is Approved or not Business exigency :" + request.getIsApproved());
                        return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 business exigency pending and L2 hbusiness exigency pending Request Can be approved. "));

                    }
                    break;
            }
        }
        logger.info("###################### Business Exigency Validation Completed For Hold/Reject/Approve #######################");
        int updateCount = 0;
        int holdReqCount = 0;
        MstStatus status = null;
        TransPromoStatus promoStatus = null;
        String strMsg = "";
        NotificationMessage msg;
        for (PromotionApprRejHoldReq request : requestList) {
            MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
            TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));

            switch (request.getStatusType()) {
                case REJECTED:
                    updateCount = commonDao.updateTransPromoMCforbussnessexigency(request.getTransPromoId(), CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED);
                    logger.info("Trans Promo Article updateCount for Business exigency rejected request : " + updateCount);

                    status = statusDao.find(CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED);
                    //Phase 3 CR - Promo Req Status History 15-11-2013
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is rejected By Business Exigency.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setReasonForRejection(request.getReasonForRejection());
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee2(approver);
                    transPromo.setRejectionRemarks(request.getRejectionRemarks());
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Promotion business exigency  reject successfully.");
                    msg = new NotificationMessage();
                    msg.setId(transPromo.getTransPromoId().toString());
                    msg.setEmpId(approver.getEmpId().toString());
                    msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_ACTION);
                    strMsg = "Request is rejected.";
                    msg.setMsg(strMsg);
                    mailService.sendNotificationMessage(msg);
                    break;
                case APPROVED:
                    updateCount = commonDao.updateTransPromoMCforbussnessexigency(request.getTransPromoId(), CommonStatusConstants.BUSINESS_EXIGENCY_APPROVE);
                    logger.info("Trans Promo Article updateCount for Approved request Business exigency : " + updateCount);

                    status = statusDao.find(CommonStatusConstants.BUSINESS_EXIGENCY_APPROVE);
                    //Phase 3 CR - Promo Req Status History 15-11-2013
                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is approved By Business Exigency.", transPromo);
                    transPromoStatusFacade.create(promoStatus);
                    transPromo.setMstStatus(status);
                    transPromo.setUpdatedDate(new Date());
                    transPromo.setMstEmployee2(approver);
                    commonDao.updateTransPromo(transPromo);
                    logger.info("Promotion business exigency  Approve successfully.");
                    msg = new NotificationMessage();
                    msg.setId(transPromo.getTransPromoId().toString());
                    msg.setEmpId(approver.getEmpId().toString());
                    msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_ACTION);
                    strMsg = "Request is Approved.";
                    msg.setMsg(strMsg);
                    mailService.sendNotificationMessage(msg);
                    break;
            }
        }
        PromotionApprRejHoldReq request = requestList.get(0);
        if (request.getIsApproved() == true) {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are approved."));
        } else if (request.getIsRejected() == true) {
            logger.info("Promotion request is rejected : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are rejected."));
        } else {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Selected Promotion requests are approved."));
        }
    }
    //Business exigency...

    public PromotionApprRejHoldRes createBusinessExigencyApprRejHoldReq(PromotionApprRejHoldReq request) throws Exception {
        PromotionApprRejHoldRes response = new PromotionApprRejHoldRes();
        logger.info("-----Inside approved ,hold or reject promotion request service Business Exigency:------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        MstEmployee approver = userDao.find(new Long(request.getEmpId().toString()));
        if (approver == null) {
            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "User is not valid."));
        }
        if (request.getStatusType() == null) {
            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Search type should not be null."));
        }
        NotificationMessage msg = new NotificationMessage();
        String strMsg = "";
        TransPromoStatus promoStatus = null;
        //NEHA : Request is on Rejected or approve.
        if (request.getStatusType() != null) {
            switch (request.getStatusType()) {
                case REJECTED:
                    if (request.getTransPromoId() != null) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion request not found with id : " + request.getTransPromoId()));
                        }
                        if (request.getIsRejected() == true) {
                            if (request.getIsRejected() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD_ESCALATED)) {
                                logger.info(" Is Hold one Request is hold or not for business exigency:" + request.getIsRejected());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 buseness exigency pending and L1 buseness exigency pending Request Can be rejected. "));
                            } else {
                                logger.info("Rejected Promotion request recevied for  buseness exigency request id : " + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }
                            int updateCount = commonDao.updateTransPromoMCforbussnessexigency(request.getTransPromoId(), CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED);
                            logger.info("Trans Promo Article updateCount for Business exigency rejected request : " + updateCount);
//                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED);
//                            logger.info("Search Trans Promo ArticleupdateCount For Business exigency rejected request : " + holdReqCount);
//                            transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING, CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
//                            logger.info("Trans promo list Size : " + transPromos.size());
                            if (transPromos != null) {
                                MstStatus status = statusDao.find(CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED);
//                                if (status != null) {
                                for (TransPromo trans : transPromos) {
                                    //Phase 3 CR - Promo Req Status History 15-11-2013
                                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is rejected By Business Exigency.", transPromo);
                                    transPromoStatusFacade.create(promoStatus);
                                    trans.setMstStatus(status);
                                    trans.setReasonForRejection(request.getReasonForRejection());
                                    trans.setUpdatedDate(new Date());
                                    commonDao.updateTransPromo(trans);
                                    logger.info("Promotion business exigency  reject successfully.");
                                    msg.setId(trans.getTransPromoId().toString());
                                    msg.setEmpId(approver.getEmpId().toString());
                                    msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_ACTION);
                                    strMsg = "Request is rejected.";
                                    msg.setMsg(strMsg);
                                    mailService.sendNotificationMessage(msg);
                                }
//                                }
                            }
                        }
                        transPromo.setMstEmployee2(approver);
                    }//
                    break;
                case APPROVED:
                    if (request.getTransPromoId() != null) {
//                        Resp rep = commonPromotionService.checkLeadTimeValidation(request.getTransPromoId());
//                        logger.info("Response from check lead time validation for request level 2 : " + rep.getRespCode() + " === " + rep.getMsg());
//                        if (rep.getMsg().equalsIgnoreCase("SUCCESS")) {
                        List<TransPromo> transPromos = new ArrayList<TransPromo>();
                        TransPromo transPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
                        if (transPromo == null) {
                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Promotion requrst not found with id : " + request.getTransPromoId()));
                        }

                        if (request.getIsApproved() == true) {
                            if (request.getIsApproved() && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_ESCALATED)
                                    && !transPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_HOLD_ESCALATED)) {
                                logger.info(" Is Approved one Request is Approved or not Business exigency :" + request.getIsApproved());
                                return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, "Only L1 business exigency pending and L2 hbusiness exigency pending Request Can be approved. "));

                            } else {
                                logger.info("Approved Promotion request recevied for business exigency pending request id" + transPromo.getTransPromoId());
                                transPromos.add(transPromo);
                            }

                            int updateCount = commonDao.updateTransPromoMCforbussnessexigency(request.getTransPromoId(), CommonStatusConstants.BUSINESS_EXIGENCY_APPROVE);
                            logger.info("Trans Promo Article updateCount for Approved request Business exigency : " + updateCount);

//                            int holdReqCount = commonDao.searchTransMCStausReq(request.getTransPromoId(), CommonStatusConstants.BUSINESS_EXIGENCY_APPROVE);
//                            logger.info("Search Trans Promo ArticleupdateCount For approved request  BUSINESS_EXIGENCY : " + holdReqCount);
//                            if (holdReqCount == 0) {
//                                transPromos = commonDao.setStatusforTransPromoReq(request.getTransPromoId(), CommonStatusConstants.L1_BUSINESS_EXIGENCY_PENDING, CommonStatusConstants.L2_BUSINESS_EXIGENCY_PENDING);
//                                logger.info("Trans promo list Size : " + transPromos.size());
                            if (transPromos != null) {
                                MstStatus status = statusDao.find(CommonStatusConstants.BUSINESS_EXIGENCY_APPROVE);
//                                if (status != null) {
                                for (TransPromo trans : transPromos) {
                                    //Phase 3 CR - Promo Req Status History 15-11-2013
                                    promoStatus = StatusUpdateUtil.submitTransPromoStatus(approver, status, transPromo.getMstStatus(), "Request is approved By Business Exigency.", transPromo);
                                    transPromoStatusFacade.create(promoStatus);
                                    trans.setMstStatus(status);
                                    trans.setUpdatedDate(new Date());
                                    commonDao.updateTransPromo(trans);
                                    logger.info("Promotion business exigency approved successfully.");
                                    msg.setId(transPromo.getTransPromoId().toString());
                                    msg.setEmpId(approver.getEmpId().toString());
                                    msg.setNotificationType(NotificationType.BUSINESS_EXIGENCY_ACTION);
                                    strMsg = "Request is approved.";
                                    msg.setMsg(strMsg);
                                    mailService.sendNotificationMessage(msg);
                                }
//                                }
                            }
//                            }
                        }
                        transPromo.setMstEmployee2(approver);
//                        } else {
//                            return new PromotionApprRejHoldRes(new Resp(RespCode.FAILURE, rep.getMsg()));
//                        }//end of if-else for check lead time validation....


                    }//
                    break;


            }
        }
        TransPromo newPromo = transPromoDao.find(new Long(request.getTransPromoId().toString()));
        if (request.getIsApproved() == true) {
            logger.info("Promotion request is Approved : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is approved with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else if (request.getIsRejected() == true) {
            logger.info("Promotion request is rejected : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is rejected with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        } else {
            logger.info("Approved by business exigency : ");
            return new PromotionApprRejHoldRes(new Resp(RespCode.SUCCESS, "Promotion request is approved by business exigency with id : T" + newPromo.getMstPromo().getPromoId() + "-R" + request.getTransPromoId()), request.getTransPromoId());
        }

    }
}
