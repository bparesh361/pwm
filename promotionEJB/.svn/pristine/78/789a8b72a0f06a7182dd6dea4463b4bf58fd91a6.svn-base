/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promo.common.CommonConstants;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.commonDAO.PromotionDAO;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstProfile;
import com.fks.promo.entity.MstPromo;

import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import com.fks.promo.entity.TransPromoFile;
import com.fks.promo.entity.TransTask;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstProfileFacade;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promo.facade.MstRoleFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.TransPromoArticleFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.facade.TransTaskFacade;
import com.fks.promo.init.TransPromoUtil;
import com.fks.promo.init.vo.TransPromoArticleVO;
import com.fks.promo.init.vo.TransPromoVO;
import com.fks.promo.master.service.OtherMasterService;
import com.fks.promo.vo.RequestRespVO;

import com.fks.promotion.vo.ValidateArticleMCResp;
import com.fks.promotion.vo.ValidateArticleMCVO;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author krutij
 */
@LocalBean
@Stateless
public class NotificationUtil {

    private static final Logger logger = Logger.getLogger(NotificationUtil.class.getName());
    @EJB
    private TransPromoFacade transPromoFacade;
    @EJB
    private OtherMasterService otherMasterService;
    @EJB
    private MchFacade mchDao;
    @EJB
    private TransPromoArticleFacade transPromoArticleDao;
    @EJB
    private CommonDAO commonDao;
    @EJB
    private MstStatusFacade mstStatusDao;
    @EJB
    private PromotionDAO promotionDAO;
    @EJB
    private MstEmployeeFacade employeeFacade;
    @EJB
    private MstPromoFacade mstPromoFacade;
    @EJB
    private TransTaskFacade transTaskFacade;
    @EJB
    private MstProfileFacade mstProfile;

    public void genrateFileValidationMail(Long transPromoId) {
        try {
            ValidateFileResp resp = validateArticleandMcDetail(transPromoId);
            String strFileMessage;
            TransPromo promo = transPromoFacade.find(transPromoId);
            MstPromo mstPromo = promo.getMstPromo();
            MstStatus status = mstStatusDao.find(Long.valueOf("6"));
            Collection<TransPromo> existingSubPromoStatusList = mstPromo.getTransPromoCollection();
            if (existingSubPromoStatusList != null && existingSubPromoStatusList.size() > 0) {
                for (TransPromo subPromo : existingSubPromoStatusList) {
//                    System.out.println("%%%%%%%%%%%%%%%%%% SubPromo ID : " + subPromo.getTransPromoId() + " with Status ID : " + subPromo.getMstStatus().getStatusId());
                    if (subPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.FILE_FAILURE)) {
//                        System.out.println("Failure Trans Promo Found with ID : " + subPromo.getTransPromoId());
                        mstPromo.setMstStatus(status);
                        break;
                    }
                }
            }
            if (resp.getResp().getRespCode().equals(RespCode.FAILURE)) {
                promo.setMstStatus(status);
                mstPromo.setMstStatus(status);
                strFileMessage = getFileUploadMessageBody(resp.getResp().getMsg(), transPromoId.toString());
                sendMailNotificationForFileUpload(resp.getMailId(), strFileMessage, resp.getErrorFilePath(), transPromoId.toString());
            }

//            else {
//                strFileMessage = getMessageBody(resp.getResp().getMsg());
//                sendNotificationMailWithErrorFile(resp.getMailId(), strFileMessage, null, transPromoId.toString());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMailtoInitiatorOnStatusUpdate(String transPromo, String empId, String msg) {
        logger.log(Level.INFO, "========= Inside sendMailtoInitiatorOnStatusUpdate ============= trans Promo Id : {0}", transPromo + "Process Name :  " + empId);
        try {
            if (transPromo != null) {
                String strFileMessage, emailAddress = null, empName = null, newTrans, subline;
                TransPromo promo = transPromoFacade.find(Long.valueOf(transPromo));

                MstEmployee employee = promo.getMstEmployee4();
                if (employee != null) {
                    empName = employee.getEmployeeName();
                    emailAddress = employee.getEmailId();
                }
                MstPromo mstPromo = promo.getMstPromo();
                String Category = mstPromo.getCategory();
                String eventName = mstPromo.getMstEvent().getEventName();

                MstEmployee employee1 = employeeFacade.find(Long.valueOf(empId));
                String apprveRejId = employee1.getEmployeeName();

                newTrans = "T" + promo.getMstPromo().getPromoId() + "-R" + promo.getTransPromoId();
                strFileMessage = getInitiatorsMessageBody(newTrans, empName, Category, eventName, msg, apprveRejId);
                subline = "Status Change : Promotion Id -" + newTrans;
                sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMailtoApproverOnStatusUpdateByExigency(String transPromo, String exiempId, String msg) {
        logger.log(Level.INFO, "========= Inside sendMailtoInitiatorOnStatusUpdate ============= trans Promo Id : {0}", transPromo + "Process Name :  " + exiempId);
        try {
            if (transPromo != null) {
                String strFileMessage, emailAddress = null, empName = null, newTrans, subline, contactNo = null, empNameinitiated = null;
                TransPromo promo = transPromoFacade.find(Long.valueOf(transPromo));
                MstEmployee employeeInitiator = promo.getMstEmployee4();
                if (employeeInitiator != null) {
                    empNameinitiated = employeeInitiator.getEmployeeName();
                    contactNo = employeeInitiator.getContactNo().toString();
                }
                System.out.println("Send to business Exigency By L1 Or L2  ");
                MstEmployee employeeApprover1 = promo.getMstEmployee1();
                MstEmployee employeeApprover2 = promo.getMstEmployee();
                if (employeeApprover1 != null) {
                    empName = employeeApprover1.getEmployeeName();
                    emailAddress = employeeApprover1.getEmailId();
                } else if (employeeApprover2 != null) {
                    empName = employeeApprover2.getEmployeeName();
                    emailAddress = employeeApprover2.getEmailId();
                }
                if (employeeApprover1 != null || employeeApprover2 != null) {
                    MstPromo mstPromo = promo.getMstPromo();
                    String Category = mstPromo.getCategory();
                    String eventName = mstPromo.getMstEvent().getEventName();

                    MstEmployee employee1 = employeeFacade.find(Long.valueOf(exiempId));
                    String apprveRejId = employee1.getEmployeeName();

                    newTrans = "T" + promo.getMstPromo().getPromoId() + "-R" + promo.getTransPromoId();
                    strFileMessage = getApproverExigencyMessageBody(newTrans, empName, Category, eventName, msg, apprveRejId, empNameinitiated, contactNo);
                    subline = "Status Change : Promotion Id -" + newTrans;
                    sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
                } else {
                    System.out.println(" Request Approved By Exigency but NO L1 / L2 Found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ValidateFileResp validateArticleandMcDetail(Long transPromoId) throws Exception {

        TransPromoArticleVO articleVO = null;
        TransPromoVO promoVO = new TransPromoVO();
        List<TransPromoArticleVO> transArticlevoList = new ArrayList<TransPromoArticleVO>();
        boolean isHO = false;
        Set<String> mcset = new HashSet<String>();
        if (transPromoId == null) {
            return new ValidateFileResp(new Resp(RespCode.FAILURE, "Promo Id is blank."));
            //logger.info("Promo Id is Blank...");
        }
        TransPromo promo = transPromoFacade.find(transPromoId);
        if (promo == null) {
            //logger.info("Invalid Promo Id....");
            return new ValidateFileResp(new Resp(RespCode.FAILURE, "Invalid Promo Id"));
        }
        String emailId = promo.getMstPromo().getMstEmployee1().getEmailId();
        Long empId = promo.getMstPromo().getMstEmployee1().getEmpId();
        logger.log(Level.INFO, "Promo Type : {0}", promo.getMstPromotionType().getPromoTypeId().toString());
        Collection<TransPromoFile> transPromoFileList = promo.getTransPromoFileCollection();

        if (promo.getIsFileUpload() != null && promo.getIsFileUpload() && transPromoFileList != null && transPromoFileList.size() > 0) {
            for (TransPromoFile tpf : transPromoFileList) {
                /*if (promo.getMstPromotionType().getPromoTypeId() == 1 || promo.getMstPromotionType().getPromoTypeId() == 2) {
                logger.log(Level.INFO, "-- With qty validation----------- For Set : {0}", tpf.getSetId());
                //with qty
                FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(tpf.getFilePath());
                if (fileValidateResp.getIsError() == true) {
                logger.log(Level.INFO, "Basic File validation Resp : {0}", fileValidateResp.getErrorMsg());
                tpf.setRemarks("Basic Validation Failed.");
                tpf.setErrorFilePath(fileValidateResp.getErrorFilePath());
                //                        MstStatus status = mstStatusDao.find(Long.valueOf("6"));
                //                        promo.setMstStatus(status);
                //                        MstPromo mstPromo = promo.getMstPromo();
                //                        mstPromo.setMstStatus(status);
                return new ValidateFileResp(new Resp(RespCode.FAILURE, fileValidateResp.getErrorMsg()), emailId, fileValidateResp.getErrorFilePath());
                } else {
                logger.info("Ods File validation after basic validation is successfull.....");
                ValidateArticleMCResp resp = otherMasterService.sendArticleMCFileForvalidate(tpf.getFilePath(), empId.toString(), promo.getMstPromo().getPromoId(), true);
                if (resp.getResp().getRespCode().equals(RespCode.FAILURE)) {
                tpf.setRemarks("ODS File Validation Failed");
                tpf.setErrorFilePath(resp.getErrorFilePath());
                //                            MstStatus status = mstStatusDao.find(Long.valueOf("6"));
                //                            promo.setMstStatus(status);
                //                            MstPromo mstPromo = promo.getMstPromo();
                //                            mstPromo.setMstStatus(status);
                return new ValidateFileResp(new Resp(RespCode.FAILURE, "Article File Validation With ODS is Failed For Promotion Id : " + promo.getTransPromoId()), emailId, resp.getErrorFilePath());
                } else {
                //                            promoVO = new TransPromoVO();
                for (ValidateArticleMCVO mCVO : resp.getArticleMCList()) {
                articleVO = new TransPromoArticleVO();
                articleVO.setArtCode(mCVO.getArticleCode());
                articleVO.setArtDesc(mCVO.getArticleDesc());
                articleVO.setMcCode(mCVO.getMcCode());
                articleVO.setMcDesc(mCVO.getMcDesc());
                articleVO.setBrandCode(mCVO.getBrandCode());
                articleVO.setBrandDesc(mCVO.getBrandDesc());
                articleVO.setSetId(tpf.getSetId());
                articleVO.setQty(mCVO.getQty());
                transArticlevoList.add(articleVO);
                }
                promoVO.setTransPromoArticleList(transArticlevoList);
                }
                }
                } */
                //else {
                //without qty
                logger.info("-- Without qty validation----------- For Set : " + tpf.getSetId());
                FileResp fileValidateResp = PromotionFileUtil.validateArticleMCFile(tpf.getFilePath());
                if (fileValidateResp.getIsError() == true) {
                    logger.info("Basic File validation Resp : " + fileValidateResp.getErrorMsg());
                    tpf.setRemarks("Basic Validation Failed.");
                    tpf.setErrorFilePath(fileValidateResp.getErrorFilePath());
//                        MstStatus status = mstStatusDao.find(Long.valueOf("6"));
//                        promo.setMstStatus(status);
//                        MstPromo mstPromo = promo.getMstPromo();o
//                        mstPromo.setMstStatus(status);
                    return new ValidateFileResp(new Resp(RespCode.FAILURE, fileValidateResp.getErrorMsg()), emailId, fileValidateResp.getErrorFilePath());
                } else {
                    logger.info("Ods File validation after basic validation is successfull.....");
                    ValidateArticleMCResp resp = otherMasterService.sendArticleMCFileForvalidate(tpf.getFilePath(), empId.toString(), promo.getMstPromo().getPromoId(), false);
                    if (resp.getResp().getRespCode().equals(RespCode.FAILURE)) {
                        tpf.setRemarks("ODS File Validation Failed");
                        tpf.setErrorFilePath(resp.getErrorFilePath());
//                            MstStatus status = mstStatusDao.find(Long.valueOf("6"));
//                            promo.setMstStatus(status);
//                            MstPromo mstPromo = promo.getMstPromo();
//                            mstPromo.setMstStatus(status);
                        return new ValidateFileResp(new Resp(RespCode.FAILURE, "Article File Validation With ODS is Failed For Promotion Id : " + promo.getTransPromoId()), emailId, resp.getErrorFilePath());
                    } else {
//                            promoVO = new TransPromoVO();
                        for (ValidateArticleMCVO mCVO : resp.getArticleMCList()) {
                            articleVO = new TransPromoArticleVO();
                            articleVO.setArtCode(mCVO.getArticleCode());
                            articleVO.setArtDesc(mCVO.getArticleDesc());
                            articleVO.setMcCode(mCVO.getMcCode());
                            articleVO.setMcDesc(mCVO.getMcDesc());
                            articleVO.setBrandCode(mCVO.getBrandCode());
                            articleVO.setBrandDesc(mCVO.getBrandDesc());
                            articleVO.setSetId(tpf.getSetId());
                            articleVO.setQty(mCVO.getQty());
                            transArticlevoList.add(articleVO);
                        }
                        promoVO.setTransPromoArticleList(transArticlevoList);
                    }
                }
//                }
            }

            MstStatus status = mstStatusDao.find(Long.valueOf("11"));
            promo.setMstStatus(status);
            MstPromo mstPromo = promo.getMstPromo();
            mstPromo.setMstStatus(status);

            List<TransPromoArticle> transArticleList = TransPromoUtil.getTransPromoArticle(promoVO, promo, status, promo.getMstEmployee3());
            // TODO inserting in the batch.
            for (TransPromoArticle article : transArticleList) {
                transPromoArticleDao.create(article);
                if (!isHO) {
                    Mch mch = mchDao.find(article.getMcCode());
                    if (mch == null) {
                        return new ValidateFileResp(new Resp(RespCode.FAILURE, "MC Code " + article.getMcCode() + " Not Found In Database. Contact Work Flow Manager Admin."), emailId);
                    }
                    if (mch != null && mch.getMstLocation() != null && mch.getMstLocation().getLocationId() == 1) {
                        isHO = true;
                        promo.setIsHo(isHO);
                    }
                }
            }
            // creating set of mcs.

            for (TransPromoArticle article : transArticleList) {
                mcset.add(article.getMcCode());
            }

            for (String mc : mcset) {
                commonDao.insertTransPromoMC(promo.getTransPromoId(), mc, new Long("11"), empId, new Date());
            }
        }
        return new ValidateFileResp(new Resp(RespCode.SUCCESS, "Article File validated successfully."), emailId);
    }

    public static String getFileUploadMessageBody(String msg, String id) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Dear User ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left> Promotion Id : ").append(id).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>").append(msg).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static boolean sendMailNotificationForFileUpload(String emailAddr, String mes, String fileName, String transId) throws Exception {
        logger.log(Level.INFO, "Mail sending start.... Email Id :", emailAddr);
        Properties props = new Properties();
        props.put("mail.smtp.host", "10.0.28.31");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "AdmiP");
        props.put("mail.smtp.pwd", "future1");
//        }
        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("admin.promotionworkflow@futuregroup.in"));
        message.setSubject("Article Validation File Status : Promotion Id -" + transId);


        InputStream is = (InputStream) NotificationUtil.class.getResourceAsStream(CommonConstants.PROPERTY);
        Properties pro = new Properties();
        pro.load(is);
        //String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.ARTICLE_ERROR_FILE);
        //String errorFilePath = "C:/promotion/article/error/";

        Multipart mp = new MimeMultipart("mixed");
        BodyPart textPart = new MimeBodyPart();
        //textPart.setDisposition(MimeBodyPart.INLINE);
        textPart.setHeader("Content-Type", "text/html");
        textPart.setContent(mes, "text/html");
        mp.addBodyPart(textPart);
        if (fileName != null) {
            textPart = new MimeBodyPart();
            textPart = addAttachment(fileName, (MimeBodyPart) textPart);
            textPart.setDisposition(MimeBodyPart.ATTACHMENT);
            mp.addBodyPart(textPart);
        }
        //message.setText(mes);
        message.setContent(mp);

        message.setRecipients(Message.RecipientType.TO, emailAddr);
        message.setSentDate(new Date());

        Transport transport = session.getTransport("smtp");
        transport.connect("10.0.28.31", 587, "AdmiP", "future1");
        logger.info("Connection successful");
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
        is.close();

        return true;
    }

    private RequestResp getL1EmployeeDetailBasedonMC(String mstPromoId) {
        logger.info("========= Inside getL1EmployeeDetailBasedonMC =============");
        try {
            if (mstPromoId == null) {
                return new RequestResp(new Resp(RespCode.FAILURE, "Promo Request Id is Blank."));
            }
            // get L1 Approver Employee  IDs
            // Get only those L1 approver Ids whose MCs are mapped with the sub promotions created against the Master Promo Id
            List<BigInteger> mstEmpList = promotionDAO.getEmployeeEmailIByPromoMC(mstPromoId);

            logger.info("List Size : " + mstEmpList.size());
            ReqRespVO reqRespVO = null;
            List<ReqRespVO> listReqResp = new ArrayList<ReqRespVO>();

            StringBuilder transPromoExist = new StringBuilder("");

            // get L1 Approver Employee  Details For Email
            /* Get only those L1 approver Employee  Details
            whose MCs are mapped with the sub promotions created against
            the Master Promo Id and Whose zone is also mapped with the sub promotion zone
            that is promotion created by's zone
             */ for (BigInteger vo : mstEmpList) {
                transPromoExist = new StringBuilder("");
                List<RequestRespVO> msttransPromolist = promotionDAO.getEmployeeEmailIByPromoMCTransIds(mstPromoId, vo);
                for (RequestRespVO rrvo : msttransPromolist) {
                    reqRespVO = new ReqRespVO();
                    reqRespVO.setEmailID(rrvo.getEmailId());
                    reqRespVO.setEmpId(rrvo.getEmpId());
                    reqRespVO.setTransId("T" + mstPromoId + "-R" + rrvo.getTransPromoId());
                    transPromoExist.append("T").append(mstPromoId).append("-R").append(rrvo.getTransPromoId()).append("\n");
                    transPromoExist.append("\n<br />");
                    reqRespVO.setTransPromos(transPromoExist.toString());
                    listReqResp.add(reqRespVO);
                }
            }
            System.out.println("List REsp Size : " + listReqResp.size());

            return new RequestResp(new Resp(RespCode.SUCCESS, "Success"), listReqResp);
        } catch (Exception e) {
            e.printStackTrace();
            return new RequestResp(new Resp(RespCode.FAILURE, "failed"));
        }

    }

    public void getRequestSubmitListForL1(String mstPromoId) {

        logger.log(Level.INFO, "===== Inside getRequestSubmitListForL1 ================== Master Promo Id   :{0}", mstPromoId);
        try {
            MstPromo mstPromo = mstPromoFacade.find(Long.valueOf(mstPromoId));
            String Category = mstPromo.getCategory();
            String eventName = mstPromo.getMstEvent().getEventName();
            String initiatedId = mstPromo.getMstEmployee().getEmployeeName();
            String contactno = mstPromo.getMstEmployee().getContactNo().toString();

            List<ReqRespVO> list = new ArrayList<ReqRespVO>();
            RequestResp resp = getL1EmployeeDetailBasedonMC(mstPromoId);
            Map<String, String> mapTransIDandEmp = new HashMap<String, String>();
            if (resp.getResp().getRespCode().toString().equalsIgnoreCase("SUCCESS")) {
                list = resp.getList();
                logger.log(Level.INFO, "------------------- List Size {0}", list.size());
                for (ReqRespVO vo : list) {
                    mapTransIDandEmp.put(vo.getEmpId().toString(), vo.getTransPromos());
                }
            }
            // logger.log(Level.INFO, " @@@@@@@@@@@@@@@@@@@@ Result Map Value   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  {0}", mapTransIDandEmp.size());
            String strFileMessage, emailAddress, empName, subline;
            Iterator mapResultIterator = mapTransIDandEmp.entrySet().iterator();
            if (mapResultIterator.hasNext()) {
                while (mapResultIterator.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) mapResultIterator.next();
//                    logger.info("Key Values : " + mapEntry.getKey());
//                    logger.info("List Values : " + mapEntry.getValue().toString());
                    MstEmployee employee = employeeFacade.find(Long.parseLong(mapEntry.getKey().toString()));
                    empName = employee.getEmployeeName();
                    strFileMessage = getPromotionRequestSubmitBodyMsg(mapEntry.getValue().toString(), mstPromoId, empName, Category, eventName, initiatedId, contactno);
                    emailAddress = employee.getEmailId();
                    subline = "Request Initiated : Promotion Id -" + mstPromoId;
                    sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getL1EscalatedL2EmployeeDetailBasedonMC(String transPromo) {
        logger.log(Level.INFO, "========= Inside getL1EscalatedL2EmployeeDetailBasedonMC ============= trans Promo Id : {0}", transPromo);
        try {
            if (transPromo != null) {
                List<BigInteger> mstEmpList = promotionDAO.getL2EmployeeDtlByPromoMC(transPromo);
                logger.log(Level.INFO, "List Size : {0}", mstEmpList.size());
                String strFileMessage, emailAddress, empName, newTrans, subline;
                for (BigInteger vo : mstEmpList) {

                    TransPromo promo = transPromoFacade.find(Long.valueOf(transPromo));
                    MstPromo mstPromo = promo.getMstPromo();
                    String Category = mstPromo.getCategory();
                    String eventName = mstPromo.getMstEvent().getEventName();
                    String initiatedId = mstPromo.getMstEmployee().getEmployeeName();
                    String contactno = mstPromo.getMstEmployee().getContactNo().toString();
                    MstEmployee employee = employeeFacade.find(vo.longValue());
                    empName = employee.getEmployeeName();
                    newTrans = "T" + promo.getMstPromo().getPromoId() + "-R" + promo.getTransPromoId();
                    strFileMessage = getSubPromotionL1EscalatedBody(newTrans, empName, Category, eventName, initiatedId, contactno);
                    emailAddress = employee.getEmailId();
                    subline = "Request Escalated : Promotion Id -" + transPromo;
                    sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTeamMemberAssignedNotification(String transPromo, String empId) {
        logger.log(Level.INFO, "========= Inside getTeamMemberAssignedNotification ============= trans Promo Id : {0}", transPromo + "========== emp Id :" + empId);
        try {
            if (transPromo != null) {
                String strFileMessage, emailAddress, empName, newTrans, subline;
                MstEmployee employee = employeeFacade.find(Long.valueOf(empId));
                TransPromo promo = transPromoFacade.find(Long.valueOf(transPromo));
                MstPromo mstPromo = promo.getMstPromo();
                String Category = mstPromo.getCategory();
                String eventName = mstPromo.getMstEvent().getEventName();
                String initiatedId = mstPromo.getMstEmployee().getEmployeeName();
                String contactno = mstPromo.getMstEmployee().getContactNo().toString();
                empName = employee.getEmployeeName();
                emailAddress = employee.getEmailId();
                newTrans = "T" + promo.getMstPromo().getPromoId() + "-R" + promo.getTransPromoId();
                strFileMessage = getTeamMemberAssignedBody(newTrans, empName, Category, eventName, initiatedId, contactno);
                subline = "Team Member Assigned : Promotion Id -" + newTrans;
                sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMailNotificationToBusinessExigency(String transPromoId, Boolean isescalated) {
        logger.log(Level.INFO, "========= Inside sendMailNotificationToBusinessExigency ============= trans Promo Id : {0}", transPromoId);
        try {
            if (transPromoId != null) {
                String strFileMessage, emailAddress, empName, newTrans, subline;
                //find exigency employee
                MstProfile profile = mstProfile.find(CommonConstants.BUSINESS_EXIGTENCY_PROFILE);
                Collection<MapRoleProfile> mapRoleProfileList = profile.getMapRoleProfileCollection();

                if (isescalated) {
                    for (MapRoleProfile vo : mapRoleProfileList) {
                        Collection<MstEmployee> mstempcollection = vo.getMstRole().getMstEmployeeCollection();
                        System.out.println("==== No of emp collection : " + mstempcollection.size());
                        for (MstEmployee employee : mstempcollection) {
                            empName = employee.getEmployeeName();
                            emailAddress = employee.getEmailId();
                            newTrans = transPromoId;
                            strFileMessage = getSubPromotionBusinessExigencyBody(newTrans, empName, null, null, null, null, true, null, null);
                            subline = "Request Escalated for Business Exigency Approval";
                            sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
                        }
                    }
                } else {
                    TransPromo promo = transPromoFacade.find(Long.valueOf(transPromoId));
                    MstPromo mstPromo = promo.getMstPromo();
                    String Category = mstPromo.getCategory();
                    String eventName = mstPromo.getMstEvent().getEventName();
                    String initiatedId = mstPromo.getMstEmployee().getEmployeeName();
                    String contactno = mstPromo.getMstEmployee().getContactNo().toString();
                    String categoryUserName = null, CategoryUserContactNO = null;
                    if (promo.getMstEmployee() != null && promo.getMstEmployee1() == null && promo.getMstEmployee2() == null) {
                        //L2
                        categoryUserName = promo.getMstEmployee().getEmployeeName();
                        CategoryUserContactNO = promo.getMstEmployee().getContactNo().toString();
                    }
                    if (promo.getMstEmployee1() != null && promo.getMstEmployee2() == null && promo.getMstEmployee() == null) {
                        //L1
                        categoryUserName = promo.getMstEmployee1().getEmployeeName();
                        CategoryUserContactNO = promo.getMstEmployee1().getContactNo().toString();
                    }

                    for (MapRoleProfile vo : mapRoleProfileList) {
                        Collection<MstEmployee> mstempcollection = vo.getMstRole().getMstEmployeeCollection();
                        System.out.println("==== No of emp collection : " + mstempcollection.size());
                        for (MstEmployee employee : mstempcollection) {
                            empName = employee.getEmployeeName();
                            emailAddress = employee.getEmailId();
                            newTrans = "T" + promo.getMstPromo().getPromoId() + "-R" + promo.getTransPromoId();
                            strFileMessage = getSubPromotionBusinessExigencyBody(newTrans, empName, Category, eventName, initiatedId, contactno, false, categoryUserName, CategoryUserContactNO);
                            subline = "Business Exigency Approval Required : Promotion Id -" + newTrans;
                            sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean sendMailNotificationForRequestStatusChanged(String emailAddr, String mes, String subLine) {
        logger.log(Level.INFO, "Mail sending start.... Email Id : {0}", emailAddr);
        Properties props = new Properties();
        props.put("mail.smtp.host", "10.0.28.31");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "AdmiP");
        props.put("mail.smtp.pwd", "future1");
        try {
            Session session = Session.getInstance(props, null);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("admin.promotionworkflow@futuregroup.in"));
            message.setSubject(subLine);

            Multipart mp = new MimeMultipart("mixed");
            BodyPart textPart = new MimeBodyPart();
            textPart.setHeader("Content-Type", "text/html");
            textPart.setContent(mes, "text/html");
            mp.addBodyPart(textPart);
            message.setContent(mp);

            message.setRecipients(Message.RecipientType.TO, emailAddr);
            message.setSentDate(new Date());

            Transport transport = session.getTransport("smtp");
            transport.connect("10.0.28.31", 587, "AdmiP", "future1");
            logger.info("Connection successful");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception ex) {
            Logger.getLogger(NotificationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;

    }

    private static MimeBodyPart addAttachment(String filename, MimeBodyPart messageBodyPart) throws MessagingException {
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(source.getName());
        return messageBodyPart;

    }

    public void sendNotificationOnTaskAssignment(String taskId) {
        logger.info("=== Inside genrateTaskAssignmentMail ====");
        try {
            if (taskId != null) {
                TransTask task = transTaskFacade.find(Long.parseLong(taskId));
                if (task != null) {
                    String empName = task.getAssignedTo().getEmployeeName();
                    String id = task.getTransTaskId().toString();
                    String emailAddress = task.getAssignedTo().getEmailId();
                    String assignedBy, assignedLocation, taskName;
                    assignedBy = task.getCreatedBy().getEmployeeName();
                    assignedLocation = task.getCreatedBy().getMstStore().getMstLocation().getLocationName();
                    taskName = task.getMstTask().getTaskName();
                    String strFileMessage = genrateTaskAssignmentMsgBody(empName, assignedBy, assignedLocation, taskName);
                    String subline = "Task Assigned : Task Id - TR" + id;
                    sendMailNotificationForRequestStatusChanged(emailAddress, strFileMessage, subline);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String genrateTaskAssignmentMsgBody(String empName, String assignedBy, String assignedLocation, String taskType) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Internal Task is assigned to you.").append("</td></tr><br />");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Assigned by : </b>").append(assignedBy).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Assigned by Location  :</b>").append(assignedLocation).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Task Type :</b>").append(taskType).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static String getPromotionRequestSubmitBodyMsg(String msg, String id, String empName, String category, String event, String initiatorName, String conatcno) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("<tr><td align=left>Requests are initiated and pending for approval from your side.</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Promotion Id : T").append(id).append("</b></td></tr>");
        //buffer.append("\n<br />");
        buffer.append("<br />").append("Following are the request Numbers :</br>").append(msg).append("</td></tr>");
        buffer.append("\n<br />");
        if (category != null) {
            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign Type : </b>").append(event).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        if (initiatorName != null) {
            buffer.append("\n<tr><td align=left><b>Initiated by : </b>").append(initiatorName).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (conatcno != null) {
            buffer.append("\n<tr><td align=left><b>Initiators Contact Number : </b>").append(conatcno).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static String getSubPromotionL1EscalatedBody(String id, String empName, String category, String event, String initiatorName, String contactNo) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Requests escalated from L1 and pending for approval from your side. </td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Promotion Id :</b> ").append(id).append("</td></tr>");
        buffer.append("\n<br />");
        if (category != null) {
            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign Type : </b>").append(event).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        if (initiatorName != null) {
            buffer.append("\n<tr><td align=left><b>Initiated by : </b>").append(initiatorName).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (contactNo != null) {
            buffer.append("\n<tr><td align=left><b>Initiators Contact Number : </b>").append(contactNo).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static String getSubPromotionBusinessExigencyBody(String id, String empName, String category, String event, String initiatorName, String contactNo, boolean isEscalated, String categoryUser, String CategoryUserNo) throws IOException {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        String portalurl = PromotionPropertyUtil.getPropertyString(PropertyEnum.PORTAL_URL);
        if (isEscalated == Boolean.TRUE) {
            buffer.append("\n<br />");
            buffer.append("\n<tr><td align=left>Following promotion request are escalated from L2. </td></tr>");
            buffer.append("\n<br />");
            buffer.append("\n<tr><td align=left>You can Approve/Reject the request by login in PWM: ").append(portalurl).append("</td></tr>");
            buffer.append("\n<br />");
        } else {
            buffer.append("\n<br />");
            buffer.append("\n<tr><td align=left>As Promotion request has exceeded the lead time, thus the category approver has requested for business exigency approval. </td></tr>");
            buffer.append("\n<br />");
            buffer.append("\n<tr><td align=left>To action the request kindly log into PWM : ").append(portalurl).append("</td></tr>");
            buffer.append("\n<br />");
        }
        buffer.append("\n<tr><td align=left><b>Promotion Request Number: </b> ").append(id).append("</td></tr>");
        buffer.append("\n<br />");
        if (category != null) {
            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign Type : </b>").append(event).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        if (initiatorName != null) {
            buffer.append("\n<tr><td align=left><b>Initiated by : </b>").append(initiatorName).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (contactNo != null) {
            buffer.append("\n<tr><td align=left><b>Initiators Contact Number : </b>").append(contactNo).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        //buffer.append("\n<br />");
        buffer.append("\n<br />");
        if (categoryUser != null) {
            buffer.append("\n<tr><td align=left><b>Category Approver  : </b>").append(categoryUser).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (CategoryUserNo != null) {
            buffer.append("\n<tr><td align=left><b>Contact Number : </b>").append(CategoryUserNo).append("</td></tr>");
            buffer.append("\n<br />");
        }

        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static String getTeamMemberAssignedBody(String id, String empName, String category, String event, String initiatorName, String contactNo) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Promo Manager assigned request to you for execution.").append("</td></tr><br />");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Request Id : </b>").append(id).append("</td></tr>");
        buffer.append("\n<br />");
        if (category != null) {
            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign Type : </b>").append(event).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        if (initiatorName != null) {
            buffer.append("\n<tr><td align=left><b>Initiated by : </b>").append(initiatorName).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (contactNo != null) {
            buffer.append("\n<tr><td align=left><b>Initiators Contact Number : </b>").append(contactNo).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static String getInitiatorsMessageBody(String id, String empName, String category, String event, String msg, String aprroveRejBy) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>").append(msg).append("</td></tr><br />");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Request Id : </b>").append(id).append("</td></tr>");
        buffer.append("\n<br />");
        if (category != null) {
            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign Type : </b>").append(event).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        if (aprroveRejBy != null) {
            buffer.append("\n<tr><td align=left><b>Processed by : </b>").append(aprroveRejBy).append("</td></tr>");
            //buffer.append("\n<br />");
        }
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static String getApproverExigencyMessageBody(String id, String empName, String category, String event, String msg, String aprroveRejBy, String initiatorName, String contactNo) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>").append(msg).append("</td></tr><br />");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Request Id : </b>").append(id).append("</td></tr>");
        buffer.append("\n<br />");
        if (category != null) {
            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign Type : </b>").append(event).append("</td></tr>");
        }
        buffer.append("\n<br />");
        if (aprroveRejBy != null) {
            buffer.append("\n<tr><td align=left><b>Processed by : </b>").append(aprroveRejBy).append("</td></tr>");
        }
        buffer.append("\n<br />");
        if (initiatorName != null) {
            buffer.append("\n<tr><td align=left><b>Initiated by : </b>").append(initiatorName).append("</td></tr>");
        }
        if (contactNo != null) {
            buffer.append("\n<tr><td align=left><b>Initiators Contact Number : </b>").append(contactNo).append("</td></tr>");
        }
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public void sendPromoClosureMailNotificationToInitiatorAndApprover(String transPRomoID) {
        logger.info("====== Inside sendPromoClosureMailNotificationToInitiatorAndApprover =====");
        try {
            TransPromo transPromo = transPromoFacade.find(Long.valueOf(transPRomoID));
            Map<String, List<PromoClosureVO>> rejecedMap = new HashMap<String, List<PromoClosureVO>>();
            Map<String, List<PromoClosureVO>> ApprovedMap = new HashMap<String, List<PromoClosureVO>>();
            Map<String, List<PromoClosureVO>> L1Map = new HashMap<String, List<PromoClosureVO>>();
            Map<String, List<PromoClosureVO>> L2Map = new HashMap<String, List<PromoClosureVO>>();
            Map<String, List<PromoClosureVO>> BEMap = new HashMap<String, List<PromoClosureVO>>();
            Map<String, List<PromoClosureVO>> PMMap = new HashMap<String, List<PromoClosureVO>>();
            Map<String, List<PromoClosureVO>> PEMap = new HashMap<String, List<PromoClosureVO>>();
            List<PromoClosureVO> list = new ArrayList<PromoClosureVO>();
            List<PromoClosureVO> listL1 = null;
            List<PromoClosureVO> listL2 = null;
            List<PromoClosureVO> listBe = null;


            if (transPromo != null) {
                MstPromo mstPromo = transPromo.getMstPromo();
                Collection<TransPromo> transPromoList = mstPromo.getTransPromoCollection();
                boolean isAllPromoNotClose = false;
                for (TransPromo tp : transPromoList) {
                    if (!tp.getMstStatus().getStatusId().equals(CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED)
                            && !tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_REJECT)
                            && !tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_REJECT)
                            && !tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_MGR_REJECTED)
                            && !tp.getMstStatus().getStatusId().equals(CommonStatusConstants.TEAM_MEMBER_REJECT)
                            && !tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_CLOSE)) {
                        //System.out.println("Promo Id : "+tp.getTransPromoId()+"   Status :"+ tp.getMstStatus().getStatusDesc());
                        isAllPromoNotClose = true;
                        System.out.println("All Promos are not in close/ Rejection state... " + isAllPromoNotClose);
                        break;
                    }
                }
                if (isAllPromoNotClose) {
                    logger.info("==== Some Promotions are not closed ===Could not send mail Notification ====");
                } else {
                    logger.info("=== All Promos are closed or in rejected State. Consolidate mail need to be trigger to Initiator and Approvers(L1,L2,Business Exigency)");
                    PromoClosureVO closureVO = null;
                    for (TransPromo tp : transPromoList) {
                        String empId = null;
                        String empName = null;
                        String empEmail = null;
                        String promoId = null;
                        if ((tp.getMstStatus().getStatusId().equals(CommonStatusConstants.BUSINESS_EXIGENCY_REJECTED))
                                || (tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L1_REJECT))
                                || (tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_L2_REJECT))
                                || (tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_MGR_REJECTED))
                                || (tp.getMstStatus().getStatusId().equals(CommonStatusConstants.TEAM_MEMBER_REJECT))) {
                            closureVO = new PromoClosureVO();
                            //System.out.println("---- Inside Rejected ----");
                            if (tp.getMstEmployee() != null && tp.getMstEmployee1() == null && tp.getMstEmployee2() == null) {
                                empId = tp.getMstEmployee().getEmpId().toString();
                                closureVO.setApproverFrom("L2");
                                empName = tp.getMstEmployee().getEmployeeName();
                                empEmail = tp.getMstEmployee().getEmailId();
                            }
                            if (tp.getMstEmployee1() != null && tp.getMstEmployee2() == null && tp.getMstEmployee() == null) {
                                empId = tp.getMstEmployee1().getEmpId().toString();
                                closureVO.setApproverFrom("L1");
                                empName = tp.getMstEmployee1().getEmployeeName();
                                empEmail = tp.getMstEmployee1().getEmailId();
                            }
                            //if (tp.getMstEmployee2() != null && tp.getMstEmployee() == null && tp.getMstEmployee1() == null) {
                            if (tp.getMstEmployee2() != null) {
                                empId = tp.getMstEmployee2().getEmpId().toString();
                                closureVO.setApproverFrom("Business Exigency");
                                empName = tp.getMstEmployee2().getEmployeeName();
                                empEmail = tp.getMstEmployee2().getEmailId();
                            }
                            if (tp.getMstEmployee3() != null) {
                                empId = tp.getMstEmployee3().getEmpId().toString();
                                closureVO.setApproverFrom("Promo Executor");
                                empName = tp.getMstEmployee3().getEmployeeName();
                                empEmail = tp.getMstEmployee3().getEmailId();
                            }
                            if (tp.getMstEmployee5() != null) {
                                empId = tp.getMstEmployee5().getEmpId().toString();
                                closureVO.setApproverFrom("Promo Manager");
                                empName = tp.getMstEmployee5().getEmployeeName();
                                empEmail = tp.getMstEmployee5().getEmailId();
                            }

                            closureVO.setEmpId(empId);
                            promoId = "T" + tp.getMstPromo().getPromoId() + "-R" + tp.getTransPromoId() + "\n";
                            closureVO.setPromotionId(promoId);
                            closureVO.setStatusName(tp.getMstStatus().getStatusDesc());

                            closureVO.setEmailId(empEmail);
                            closureVO.setEmpName(empName);
                            rejecedMap.put(empId, list);
                            rejecedMap.get(empId).add(closureVO);

                        } else if (tp.getMstStatus().getStatusId().equals(CommonStatusConstants.PROMO_CLOSE)) {
                            closureVO = new PromoClosureVO();
                            promoId = "T" + tp.getMstPromo().getPromoId() + "-R" + tp.getTransPromoId() + "\n";
                            closureVO.setPromotionId(promoId);
                            closureVO.setStatusName(tp.getMstStatus().getStatusDesc());
                            if (tp.getMstEmployee() != null && tp.getMstEmployee1() == null && tp.getMstEmployee2() == null) {
                                System.out.println("--- L2 Approve Request Found");
                                empId = tp.getMstEmployee().getEmpId().toString();
                                closureVO.setApproverFrom("L2");
                                closureVO.setEmpId(empId);
                                empName = tp.getMstEmployee().getEmployeeName();
                                empEmail = tp.getMstEmployee().getEmailId();
                                closureVO.setEmailId(empEmail);
                                closureVO.setEmpName(empName);
                                if (!L2Map.containsKey(empId)) {
                                    listL2 = new ArrayList<PromoClosureVO>();
                                    L2Map.put(empId, listL2);
                                }

                                L2Map.get(empId).add(closureVO);
                            }
                            if (tp.getMstEmployee1() != null && tp.getMstEmployee2() == null && tp.getMstEmployee() == null) {
                                empId = tp.getMstEmployee1().getEmpId().toString();
                                closureVO.setApproverFrom("L1");
                                closureVO.setEmpId(empId);
                                empName = tp.getMstEmployee1().getEmployeeName();
                                empEmail = tp.getMstEmployee1().getEmailId();
                                closureVO.setEmailId(empEmail);
                                closureVO.setEmpName(empName);
                                if (!L1Map.containsKey(empId)) {
                                    listL1 = new ArrayList<PromoClosureVO>();
                                    L1Map.put(empId, listL1);

                                }
                                L1Map.get(empId).add(closureVO);

                            }
                            //if (tp.getMstEmployee2() != null && tp.getMstEmployee() == null && tp.getMstEmployee1() == null) 
                            if (tp.getMstEmployee2() != null) {
                                empId = tp.getMstEmployee2().getEmpId().toString();
                                closureVO.setEmpId(empId);
                                closureVO.setApproverFrom("Business Exigency");
                                empName = tp.getMstEmployee2().getEmployeeName();
                                empEmail = tp.getMstEmployee2().getEmailId();
                                closureVO.setEmailId(empEmail);
                                closureVO.setEmpName(empName);
                                if (!BEMap.containsKey(empId)) {
                                    listBe = new ArrayList<PromoClosureVO>();
                                    BEMap.put(empId, listBe);
                                }
                                BEMap.get(empId).add(closureVO);


                            }
//                            if (tp.getMstEmployee3() != null) {
//                                empId = tp.getMstEmployee3().getEmailId();
//                                closureVO.setEmpId(empId);
//                                closureVO.setApproverFrom("Promo Executor");
//                                PEMap.put(empId, closureVO);
//                                listPE.add(closureVO);
//                            }
//                            if (tp.getMstEmployee5() != null) {
//                                empId = tp.getMstEmployee5().getEmailId();
//                                closureVO.setEmpId(empId);
//                                closureVO.setApproverFrom("Promo Manager");
//                                PMMap.put(empId, closureVO);
//                                listPM.add(closureVO);
//                            }


                        } else {
                            System.out.println("---- Inside Else");
                        }
                    }
//                    System.out.println("#######  Rejected Map Detail  #######" + rejecedMap.size());
//                    Iterator rejIterator = rejecedMap.entrySet().iterator();
//                    if (rejIterator.hasNext()) {
//                        while (rejIterator.hasNext()) {
//                            Map.Entry entry = (Map.Entry) rejIterator.next();
//                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();
//                            for (PromoClosureVO vo : volist) {
//                                System.out.println("Entery Key : " + vo.getEmpId());
//                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
//                                System.out.println("Approver From : " + vo.getApproverFrom());
//                                System.out.println("Status : " + vo.getStatusName());
//                            }
//                        }
//                    }

//                    System.out.println("#######  L1 Map Detail  #######" + L1Map.size());
//                    Iterator L1Iterator = L1Map.entrySet().iterator();
//                    if (L1Iterator.hasNext()) {
//                        while (L1Iterator.hasNext()) {
//                            Map.Entry entry = (Map.Entry) L1Iterator.next();
//                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();
//                            for (PromoClosureVO vo : volist) {
//                                System.out.println("Entery Key : " + vo.getEmpId());
//                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
//                                System.out.println("Approver From : " + vo.getApproverFrom());
//                                System.out.println("Status : " + vo.getStatusName());
//                            }
//                        }
//                    }
//                    System.out.println("#######  L2 Map Detail  #######" + L2Map.size());
//                    Iterator L2Iterator = L2Map.entrySet().iterator();
//                    if (L2Iterator.hasNext()) {
//                        while (L2Iterator.hasNext()) {
//                            Map.Entry entry = (Map.Entry) L2Iterator.next();
//                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();
//                            for (PromoClosureVO vo : volist) {
//                                System.out.println("Entery Key : " + vo.getEmpId());
//                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
//                                System.out.println("Approver From : " + vo.getApproverFrom());
//                                System.out.println("Status : " + vo.getStatusName());
//                            }
//                        }
//                    }
                    System.out.println("#######  BE Map Detail  #######" + BEMap.size());
                    Iterator beIterator = BEMap.entrySet().iterator();
                    if (beIterator.hasNext()) {
                        while (beIterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) beIterator.next();
                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();
                            for (PromoClosureVO vo : volist) {
                                System.out.println("Entery Key : " + vo.getEmpId());
                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
                                System.out.println("Approver From : " + vo.getApproverFrom());
                                System.out.println("Status : " + vo.getStatusName());
                            }
                        }
                    }


                    StringBuilder approverbuffer = new StringBuilder("");
                    StringBuilder initiatorbuffer = new StringBuilder("");
                    System.out.println("#######  Iterate Approved Map Detail  #######" + ApprovedMap.size());


                    String subline = "Status Change : Promotion Id -T" + transPromo.getMstPromo().getPromoId().toString();
                    Iterator apIteratorpIterator = BEMap.entrySet().iterator();
                    if (apIteratorpIterator.hasNext()) {
                        approverbuffer = new StringBuilder("");
                        while (apIteratorpIterator.hasNext()) {
                            if (approverbuffer.length() > 0) {
                                approverbuffer.delete(0, approverbuffer.length());
                            }
                            approverbuffer.append("\n");
                            approverbuffer.append("<br/>");
                            Map.Entry entry = (Map.Entry) apIteratorpIterator.next();
                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();
                            for (PromoClosureVO vo : volist) {
//                                System.out.println("Entery Key : " + entry.getKey().toString());
//                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
                                approverbuffer.append(vo.getPromotionId()).append("<br/>");
                                initiatorbuffer.append(vo.getPromotionId()).append("<br/>");
                            }
                            String subpromoList = approverbuffer.toString();

                            MstEmployee employee = employeeFacade.find(Long.valueOf(entry.getKey().toString()));
                            String msgbody = getInitiatorAndApproverPromoRequestCloserMailBody(employee.getEmployeeName(), transPromo.getMstPromo().getPromoId().toString(), transPromo.getMstPromo().getMstEvent().getEventName(), transPromo.getMstPromo().getMstEmployee1().getEmployeeName(), subpromoList);
                            sendMailNotificationForRequestStatusChanged(employee.getEmailId(), msgbody, subline);

                        }
                    }
                    Iterator L1Interator = L1Map.entrySet().iterator();
                    if (L1Interator.hasNext()) {
                        approverbuffer = new StringBuilder("");
                        while (L1Interator.hasNext()) {
                            if (approverbuffer.length() > 0) {
                                approverbuffer.delete(0, approverbuffer.length());
                            }
                            approverbuffer.append("\n");
                            approverbuffer.append("<br/>");
                            Map.Entry entry = (Map.Entry) L1Interator.next();
                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();

                            for (PromoClosureVO vo : volist) {
//                                System.out.println("Entery Key : " + entry.getKey().toString());
//                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
                                approverbuffer.append(vo.getPromotionId()).append("<br/>");
                                initiatorbuffer.append(vo.getPromotionId()).append("<br/>");
                            }

                            String subpromoList = approverbuffer.toString();

                            MstEmployee employee = employeeFacade.find(Long.valueOf(entry.getKey().toString()));
                            String msgbody = getInitiatorAndApproverPromoRequestCloserMailBody(employee.getEmployeeName(), transPromo.getMstPromo().getPromoId().toString(), transPromo.getMstPromo().getMstEvent().getEventName(), transPromo.getMstPromo().getMstEmployee1().getEmployeeName(), subpromoList);
                            sendMailNotificationForRequestStatusChanged(employee.getEmailId(), msgbody, subline);

                        }
                    }

                    Iterator l2iterator = L2Map.entrySet().iterator();
//                    System.out.println("------------ L2 map Size : " + L2Map.size());
                    if (l2iterator.hasNext()) {
                        approverbuffer = new StringBuilder("");
                        while (l2iterator.hasNext()) {
                            if (approverbuffer.length() > 0) {
                                approverbuffer.delete(0, approverbuffer.length());
                            }
                            approverbuffer.append("\n");
                            approverbuffer.append("<br/>");
                            Map.Entry entry = (Map.Entry) l2iterator.next();
                            List<PromoClosureVO> volist = (List<PromoClosureVO>) entry.getValue();
                            for (PromoClosureVO vo : volist) {
//                                System.out.println("Entery Key : " + vo.getEmpId());
//                                System.out.println("Trans Promo Id : " + vo.getPromotionId());
                                approverbuffer.append(vo.getPromotionId()).append("<br/>");
                                initiatorbuffer.append(vo.getPromotionId()).append("<br/>");
                            }
                            String subpromoList = approverbuffer.toString();

                            MstEmployee employee = employeeFacade.find(Long.valueOf(entry.getKey().toString()));
                            String msgbody = getInitiatorAndApproverPromoRequestCloserMailBody(employee.getEmployeeName(), transPromo.getMstPromo().getPromoId().toString(), transPromo.getMstPromo().getMstEvent().getEventName(), transPromo.getMstPromo().getMstEmployee1().getEmployeeName(), subpromoList);
                            sendMailNotificationForRequestStatusChanged(employee.getEmailId(), msgbody, subline);

                        }
                    }
                    //Initiator Mail
                    if (initiatorbuffer.length() > 0) {
                        String initiatormsgbody = getInitiatorAndApproverPromoRequestCloserMailBody(transPromo.getMstEmployee4().getEmployeeName(), transPromo.getMstPromo().getPromoId().toString(), transPromo.getMstPromo().getMstEvent().getEventName(), transPromo.getMstPromo().getMstEmployee1().getEmployeeName(), initiatorbuffer.toString());
                        sendMailNotificationForRequestStatusChanged(transPromo.getMstEmployee4().getEmailId(), initiatormsgbody, subline);
                    }else{
                        System.out.println("#### No Request ####");
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getInitiatorAndApproverPromoRequestCloserMailBody(String empName, String mstReqId, String event, String initiatorName, String reqIds) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append(empName).append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>").append("Following Promotion requests are closed.").append("</td></tr><br />");
        buffer.append("\n<br />");
        if (mstReqId != null) {
            buffer.append("\n<tr><td align=left><b>Promotion Ticket Id: </b>").append("T").append(mstReqId).append("</td></tr>");
        }
        if (initiatorName != null) {
            buffer.append("\n<tr><td align=left><b>Initiated Name : </b>").append(initiatorName).append("</td></tr>");
        }
//        if (category != null) {
//            buffer.append("\n<tr><td align=left><b>Category : </b>").append(category).append("</td></tr>");
//        }
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Campaign  : </b>").append(event).append("</td></tr>");
        }

//        if (contactNo != null) {
//            buffer.append("\n<tr><td align=left><b>Intiators Contact Number : </b>").append(contactNo).append("</td></tr>");
//        }
        buffer.append("\n<br />");
        if (event != null) {
            buffer.append("\n<tr><td align=left><b>Promotion Request  : </b>").append(reqIds).append("</td></tr>");
        }
//        buffer.append("\n<tr><td align=left><b>Request Id with Status and Approver Detail</b>").append("</td></tr>");
//        buffer.append("\n<tr><td>").append("<table align=center>");
//        buffer.append("\n<tr><td><b>REQUEST ID </b></td><td><b>STATUS</b></td><td><b>APPROVER FROM</b></td><td><b>APPROVER NAME</b></td></tr>");

        buffer.append("\n<br />");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        String str;
        str = getSubPromotionBusinessExigencyBody("T-38-R114,T-38-R113,T-38-R112,T-38-R111,T-37-R110,T-37-R109,T-37-R108,T-37-R107,T-37-R106", "XYZ", "ABC", "ABC", "ABC", "ABC", false, null, null);

        //str = getInitiatorsMessageBody("T123", "Kruti", "Food", "Big Days", "Request Rejected", "Kruti ");
        sendMailNotificationForRequestStatusChanged("kruti.jani@futuregroup.in", str, "Subject line");
        logger.info("Mail send....");
    }
}
