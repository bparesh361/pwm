/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.CreateTransPromoReq;
import com.fks.promo.init.Resp;
import com.fks.promo.init.RespCode;
import com.fks.promo.master.service.ValidateArticleMCResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.FileResp;
import com.fks.ui.constants.PromotionFileUtil;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.FlatDiscountFormVO;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import java.io.File;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class FlatDiscountAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(FlatDiscountAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private FlatDiscountFormVO flatDiscountVO;

    public FlatDiscountFormVO getFlatDiscountVO() {
        return flatDiscountVO;
    }

    public void setFlatDiscountVO(FlatDiscountFormVO flatDiscountVO) {
        this.flatDiscountVO = flatDiscountVO;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate FlatDiscountAction Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                logger.info("Valid User .");
                flatDiscountVO=new FlatDiscountFormVO();
                Object sPromoId = getSessionMap().get(WebConstants.PROMOTION_REQ_ID);
                logger.info("Promo Id :" + sPromoId);
                if(sPromoId!=null){
                    flatDiscountVO.setIsInitiatorRedirect("1");
                    flatDiscountVO.setSessionmstPromoId(sPromoId.toString());
                }
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion FlatDiscountAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private File articleFileUpload;
    private String articleFileUploadFileName;

    public File getArticleFileUpload() {
        return articleFileUpload;
    }

    public void setArticleFileUpload(File articleFileUpload) {
        this.articleFileUpload = articleFileUpload;
    }

    public String getArticleFileUploadFileName() {
        return articleFileUploadFileName;
    }

    public void setArticleFileUploadFileName(String articleFileUploadFileName) {
        this.articleFileUploadFileName = articleFileUploadFileName;
    }

    public String createFlatDiscount() {
        try {
            logger.info("--------------Creating FlatDiscount Promotion---------------");
            logger.info("---------Is Manual Entry : " + flatDiscountVO.getIsManualEntry());
            logger.info("---------Mst Promo iD : " + flatDiscountVO.getMstPromoId());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            if (flatDiscountVO.getIsManualEntry().equalsIgnoreCase("1") && articleFileUpload == null) {
                logger.info("--------- Article Dtl : " + flatDiscountVO.getManualArticleData());
                logger.info("----- Disconfig : " + flatDiscountVO.getDiscountConfig());
                CreateTransPromoReq FlatDiscountManualReq = PromotionIntiationUtil.getFlatDiscountRequest(flatDiscountVO, null, zoneId, empID);
                Resp flatDisResp = ServiceMaster.getTransPromoService().createTransPromo(FlatDiscountManualReq);
                if (flatDisResp.getRespCode() == RespCode.SUCCESS) {
                    flatDiscountVO = new FlatDiscountFormVO();
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                    addActionMessage(flatDisResp.getMsg());
                    return SUCCESS;
                } else {
                    addActionError(flatDisResp.getMsg());
                    flatDiscountVO = new FlatDiscountFormVO();
                    return INPUT;
                }
            } else {
                String fileName = articleFileUpload.toString();
                logger.info("--------- File Reading Start.--------");
//                FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(fileName);
                FileResp fileValidateResp = PromotionFileUtil.validateArticleMCFile(fileName);
                if (fileValidateResp.getIsError() == true) {
                    logger.info(fileValidateResp.getErrorMsg());
                    addActionError(fileValidateResp.getErrorMsg());
                    flatDiscountVO.setIsUploadFileError("1");
                    return INPUT;
                }

                String promotionFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ARTICLE_FILE_PATH);
                String promotionFileName = PromotionUtil.getFileNameWithoutSpace(articleFileUploadFileName);
                String filePath = promotionFilePath + promotionFileName;


                /*Copy the File On Defined File Path
                 */
                File bfile = new File(filePath);
                FileUtils.copyFile(articleFileUpload.getAbsoluteFile(), bfile);
                logger.info("File is copied successful!");

                /* Calling ODS Article Validate Service
                 */
                ValidateArticleMCResp articleValidateResp = PromotionFileUtil.validateODSArticleMC(filePath, empID,Long.valueOf(flatDiscountVO.getMstPromoId()),false);
                if (articleValidateResp.getResp().getRespCode() == com.fks.promo.master.service.RespCode.FAILURE) {
                    logger.info("---- Flat Discount FIle UPload Resp: " + articleValidateResp.getResp().getMsg());
                    addActionError(articleValidateResp.getResp().getMsg());
                    flatDiscountVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + articleValidateResp.getErrorFilePath());
                    flatDiscountVO.setIsUploadFileError("1");
                    return INPUT;
                }
                /* File is validated successfully with ODS 
                 *Call Create TransPromo Service For Saving the Data
                 */

                CreateTransPromoReq FlatDiscountManualReq = PromotionIntiationUtil.getFlatDiscountRequest(flatDiscountVO, articleValidateResp.getArticleMCList(), zoneId, empID);
                Resp flatDisResp = ServiceMaster.getTransPromoService().createTransPromo(FlatDiscountManualReq);
                if (flatDisResp.getRespCode() == RespCode.SUCCESS) {
                    flatDiscountVO = new FlatDiscountFormVO();
                    addActionMessage(flatDisResp.getMsg());
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                    return SUCCESS;
                } else {
                    addActionError(flatDisResp.getMsg());
                    flatDiscountVO = new FlatDiscountFormVO();
                    return INPUT;
                }
            }

        } catch (Exception ex) {
            logger.error("------ Error : " + ex.getMessage());
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
