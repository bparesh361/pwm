/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.CreateTransPromoReq;
import com.fks.promo.init.Resp;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.TransPromoVO;
import com.fks.promo.master.service.ValidateArticleMCResp;
import com.fks.promo.master.service.ValidateArticleMCVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.FileResp;
import com.fks.ui.constants.PromotionFileUtil;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.BxGyFormVo;
import com.fks.ui.form.vo.FlatDiscountFormVO;
import com.fks.ui.master.vo.ArticleMcVo;
import com.fks.ui.proposal.action.ProposalUtil;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class BxGyAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(BxGyAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private BxGyFormVo formvo;

    public BxGyFormVo getFormvo() {
        return formvo;
    }

    public void setFormvo(BxGyFormVo formvo) {
        this.formvo = formvo;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate BXGY Promo Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                logger.info("Valid User .");
                formvo = new BxGyFormVo();
                Object sPromoId = getSessionMap().get(WebConstants.PROMOTION_REQ_ID);
                logger.info("Promo Id :" + sPromoId);
                if(sPromoId!=null){
                    formvo.setIsInitiatorRedirect("1");
                    formvo.setSessionmstPromoId(sPromoId.toString());
                }
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion BXGY Promo Page ----- ");
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

    public String createbxgydetail() {
        try {
            logger.info("--------------Creating BxGy Promotion---------------");
            logger.info("---------Is Manual Entry : " + formvo.getIsManualEntry());
            logger.info("---------Mst Promo iD : " + formvo.getMstPromoId());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            CreateTransPromoReq req = new CreateTransPromoReq();
            req.setZoneId(new Long(zoneId));
            req.setEmpId(new Long(empID));
            req.setMstPromoId(new Long(formvo.getMstPromoId()));
            req.setTypeId(WebConstants.BXGY);

            if (formvo.getIsManualEntry().equalsIgnoreCase("1") && articleFileUpload == null) {
                logger.info("--------- Article Dtl : " + formvo.getManualArticleData());
                logger.info("----- Disconfig : " + formvo.getDiscountConfig());
                TransPromoVO tpvo = ProposalUtil.getCreateManualBXGYPromo(formvo);
                req.setTransPromoVO(tpvo);

                Resp bxgyresp = ServiceMaster.getTransPromoService().createTransPromo(req);
                if (bxgyresp.getRespCode() == RespCode.SUCCESS) {
                    formvo = new BxGyFormVo();
                    addActionMessage(bxgyresp.getMsg());
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                    return SUCCESS;
                } else {
                    addActionError(bxgyresp.getMsg());
                    formvo = new BxGyFormVo();
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
                    formvo.setIsUploadFileError("1");
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
                ValidateArticleMCResp articleValidateResp = PromotionFileUtil.validateODSArticleMC(filePath, empID, Long.valueOf(formvo.getMstPromoId()), false);
                if (articleValidateResp.getResp().getRespCode() == com.fks.promo.master.service.RespCode.FAILURE) {
                    logger.info("---- BXGY  FIle UPload Resp: " + articleValidateResp.getResp().getMsg());
                    addActionError(articleValidateResp.getResp().getMsg());
                    formvo.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + articleValidateResp.getErrorFilePath());
                    formvo.setIsUploadFileError("1");
                    return INPUT;
                }
                /* File is validated successfully with ODS 
                 *Call Create TransPromo Service For Saving the Data
                 */
                 List<ValidateArticleMCVO> list=articleValidateResp.getArticleMCList();

               

                TransPromoVO tpvo = ProposalUtil.getCreateFileBXGYPromo(formvo, list);
                req.setTransPromoVO(tpvo);
                Resp resp = ServiceMaster.getTransPromoService().createTransPromo(req);
                if (resp.getRespCode() == RespCode.SUCCESS) {
                    formvo = new BxGyFormVo();
                    addActionMessage(resp.getMsg());
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                    return SUCCESS;
                } else {
                    addActionError(resp.getMsg());
                    formvo = new BxGyFormVo();
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
