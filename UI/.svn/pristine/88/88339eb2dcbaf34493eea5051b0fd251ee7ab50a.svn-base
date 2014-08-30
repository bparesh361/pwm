/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.CreateMultiPlePromoReq;
import com.fks.promo.init.CreateTransPromoWithFileReq;
import com.fks.promo.init.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.CreateSubPromoUsingFileFormVO;
import org.apache.log4j.Logger;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author krutij
 */
public class CreateSubPromotionUsingFile extends ActionBase {

    private static Logger logger = Logger.getLogger(CreateSubPromotionUsingFile.class.getName());
    private String strUserID;
    private CreateSubPromoUsingFileFormVO formVo;
    private File promotionFileUpload;
    private String promotionFileUploadFileName;

    @Override
    public String execute() {
        logger.info("------------------ Welcome CreateSubPromotionUsingFile Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.EMP_ID);
            if (strUserID != null) {
                formVo = new CreateSubPromoUsingFileFormVO();
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion BuyXGetYAtDiscountAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String submitSubpromoUsingFile() {
        logger.info("======== submitSubpromoUsingFile =========== ");
        try {
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            if (promotionFileUpload != null) {
                if (promotionFileUpload.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                    formVo = new CreateSubPromoUsingFileFormVO();
                    return INPUT;
                }
                String subPromoFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.SUB_PROMOTION_FILE_PATH);

                Calendar cal = new GregorianCalendar();
                Long time = cal.getTimeInMillis();

                String filePath = null;
                if (promotionFileUploadFileName.endsWith(".xls")) {
                    filePath = subPromoFilePath + "/H_Request_" + formVo.getTxtReqId() + "_" + time.toString() + ".xls";
                } else if (promotionFileUploadFileName.endsWith(".xlsx")) {
                    filePath = subPromoFilePath + "/H_Request_" + formVo.getTxtReqId() + "_" + time.toString() + ".xlsx";
                } else {
                    addActionError("Only xls/xlsx file can be uploaded.");
                    formVo = new CreateSubPromoUsingFileFormVO();
                    return INPUT;
                }
                File bfile = new File(filePath);
                FileUtils.copyFile(promotionFileUpload.getAbsoluteFile(), bfile);
                logger.info("Sub Promo File For Update is copied successfully.");

//                CreateTransPromoWithFileReq req = new CreateTransPromoWithFileReq();
//                req.setEmpId(Long.valueOf(strUserID));
//                req.setMstPromoId(Long.valueOf(formVo.getTxtReqId()));
//                req.setZoneId(Long.valueOf(zoneId));
//                req.setUploadFilePath(filePath);
//
//                Resp resp = ServiceMaster.getTransPromoService().createTransPromoForFileUpload(req);
                CreateMultiPlePromoReq req = new CreateMultiPlePromoReq();
                req.setMstPromoId(formVo.getTxtReqId());
                req.setEmpId(strUserID);
                req.setFilePath(filePath);
                req.setZoneId(zoneId);

                Resp resp = ServiceMaster.getTransPromoService().submitMultipleTransPromoFileUpload(req);
                if (resp.getRespCode().value().toString().equalsIgnoreCase(WebConstants.SUCCESS)) {
                    addActionMessage(resp.getMsg());
                    formVo = new CreateSubPromoUsingFileFormVO();
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    return INPUT;
                }
            } else {
                addActionError("No File Found. Please Upload Valid File.");
                return INPUT;
            }
        } catch (Exception e) {
            logger.info("Exception in submitSubpromoUsingFile() of Promotion Create Sub Promo Using File Page ----- ");
            e.printStackTrace();
            return ERROR;
        }

    }

    public File getPromotionFileUpload() {
        return promotionFileUpload;
    }

    public void setPromotionFileUpload(File promotionFileUpload) {
        this.promotionFileUpload = promotionFileUpload;
    }

    public String getPromotionFileUploadFileName() {
        return promotionFileUploadFileName;
    }

    public void setPromotionFileUploadFileName(String promotionFileUploadFileName) {
        this.promotionFileUploadFileName = promotionFileUploadFileName;
    }

    public CreateSubPromoUsingFileFormVO getFormVo() {
        return formVo;
    }

    public void setFormVo(CreateSubPromoUsingFileFormVO formVo) {
        this.formVo = formVo;
    }
}
