/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoFile;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promo.facade.TransPromoFileFacade;
import com.fks.promo.init.TransPromoService;
import com.fks.promo.init.vo.CreateMultiPlePromoReq;
import com.fks.promo.init.vo.CreateTransPromoReq;
import java.util.Date;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
@MessageDriven(mappedName = "jms/subpromoqueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class SubPromoMDB implements MessageListener {

    private static final Logger logger = Logger.getLogger(SubPromoMDB.class.getName());
    @EJB
    private TransPromoFileFacade transPromoFileDao;
    @EJB
    private SubPromoFileValidationUtil subPromoUtil;
    @EJB
    private MultiplePromotionFileUploadUtil multiplePromoUtil;
    @EJB
    private MultiplePromotion_XLSX_FileUploadUtil multiplePromoXLSXUtil;
    @EJB
    private TransPromoService promoService;
    @EJB
    private MstPromoFacade promoDao;

    @Override
    public void onMessage(Message message) {
        logger.info("------- Inside Processing Sub Promo Update Message------");
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            NotificationMessage notificationMessage = (NotificationMessage) objectMessage.getObject();
            switch (notificationMessage.getNotificationType()) {
                case TRANS_PROMO_REQUEST_SUBMIT_FILE:
                    Long transPromoFileId = notificationMessage.getTransPromoFileId();
                    logger.info("-------Inside Update. Trans Promo File Id : " + transPromoFileId);
                    TransPromoFile promoFile = transPromoFileDao.find(transPromoFileId);
                    if (promoFile != null) {
                        String uploadFilePath = promoFile.getFilePath();
                        CreateTransPromoReq updateReq = subPromoUtil.validateSubPromoFile(uploadFilePath, promoFile.getTransPromo());
//                StringWriter buffer = new StringWriter();
//                JAXB.marshal(updateReq, buffer);
//                logger.info(" PAYLOAD ::::::\n " + buffer);
                        if (!updateReq.isIsError()) {
                            Resp resp = promoService.updateTransPromo(updateReq);
                            if (resp.getRespCode() == RespCode.FAILURE) {
                                TransPromo promo = promoFile.getTransPromo();
                                promo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));
                                promo.setUpdatedDate(new Date());
                                MstPromo mstPromo = promo.getMstPromo();
                                mstPromo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));
                            }

                        } else {
                            TransPromo promo = promoFile.getTransPromo();
                            promo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));
                            promo.setUpdatedDate(new Date());
                            MstPromo mstPromo = promo.getMstPromo();
                            mstPromo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));

                            promoFile.setErrorFilePath(updateReq.getErrorFilePath());
                        }
                    }
                    break;
                case MULTIPLE_PROMOTION_FILE:
                    MstPromo promo = promoDao.find(Long.valueOf(notificationMessage.getId()));
                    String filePath = promo.getFilePath();
                    System.out.println("--------------- File Path : " + filePath);
//                    CreateMultiPlePromoReq reqest = subPromoUtil.validateMultiplePromoFile(filePath, promo, notificationMessage.getEmpId(), notificationMessage.getZoneId().toString());

                    CreateMultiPlePromoReq reqest = null;
                    if (filePath.endsWith(".xls")) {                        
                        reqest = multiplePromoUtil.validateFile(filePath, promo, notificationMessage.getZoneId().toString(), notificationMessage.getEmpId());
                    } else if (filePath.endsWith(".xlsx")) {                        
                        reqest = multiplePromoXLSXUtil.validateFile(filePath, promo, notificationMessage.getZoneId().toString(), notificationMessage.getEmpId());
                    } else {
                    }

                    promo.setErrorFilePath(reqest.getErrorPath());
                    logger.info("----------- Resp : " + reqest.getErrorFlag());
                    if (!reqest.getErrorFlag()) {
                        Resp response = promoService.createTransPromoList(reqest);
                        if (response.getRespCode() == RespCode.FAILURE) {
                            promo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));
                            promo.setUpdatedDate(new Date());
                        } else {
                            logger.info("---- Inside Updating Master Promo Status -------");
                            promoService.updateMasterPromoStatus(promo.getPromoId());
                            logger.info("############# Master Promo updated ##########");
                        }
                    } else {
                        promo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));
                        promo.setUpdatedDate(new Date());
                    }
                    break;
            }


        } catch (Exception ex) {
            logger.error("------- Error While Processing Sub Promo Update Message : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
