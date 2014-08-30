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
import com.fks.ui.form.vo.PoolTicketSizeFormVO;
import java.io.File;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class PoolRewardTicketAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PoolRewardTicketAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private PoolTicketSizeFormVO poolticketSizeVO;

    public PoolTicketSizeFormVO getPoolticketSizeVO() {
        return poolticketSizeVO;
    }

    public void setPoolticketSizeVO(PoolTicketSizeFormVO poolticketSizeVO) {
        this.poolticketSizeVO = poolticketSizeVO;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate PoolRewardTicketAction Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                logger.info("Valid User .");
                poolticketSizeVO = new PoolTicketSizeFormVO();
                Object sPromoId = getSessionMap().get(WebConstants.PROMOTION_REQ_ID);
                logger.info("Promo Id :" + sPromoId);
                if(sPromoId!=null){
                    poolticketSizeVO.setIsInitiatorRedirect("1");
                    poolticketSizeVO.setSessionmstPromoId(sPromoId.toString());
                }
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion PoolRewardTicketAction Page ----- ");
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

    public String uploadTicketPoolXArticleFile() {
        try {
            logger.info("--------------Inside Validate Pool Ticket Size X Article File---------------");
            logger.info("---- X promo ID : " + poolticketSizeVO.getxMstPromoId());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String fileName = articleFileUpload.toString();
            logger.info("--------- File Reading Start.--------");
            FileResp fileValidateResp = PromotionFileUtil.validateArticleMCFile(fileName);

            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                poolticketSizeVO.setIsUploadFileError("1");
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
            ValidateArticleMCResp articleValidateResp = PromotionFileUtil.validateODSArticleMC(filePath, empID, Long.valueOf(poolticketSizeVO.getxMstPromoId()),false);
            logger.info("---- upload Ticket Pool X Article FIle UPload Resp: " + articleValidateResp.getResp().getMsg());
            if (articleValidateResp.getResp().getRespCode() == com.fks.promo.master.service.RespCode.FAILURE) {
                addActionError(articleValidateResp.getResp().getMsg());
                poolticketSizeVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + articleValidateResp.getErrorFilePath());
                poolticketSizeVO.setIsUploadFileError("1");
//                poolticketSizeVO.setxMstPromoId(poolticketSizeVO.getxMstPromoId());
                return INPUT;
            }
            getSessionMap().put(WebConstants.X_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(articleValidateResp.getArticleMCList()));
            poolticketSizeVO.setIsUploadFileError("0");
            poolticketSizeVO.setIsXFileUploaded("1");
//            poolticketSizeVO.setxMstPromoId(poolticketSizeVO.getxMstPromoId());
            addActionMessage(articleValidateResp.getResp().getMsg());
            return SUCCESS;
        } catch (Exception ex) {
            logger.error("------ Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }

    }
    private File YarticleFileUpload;
    private String YarticleFileUploadFileName;

    public File getYarticleFileUpload() {
        return YarticleFileUpload;
    }

    public void setYarticleFileUpload(File YarticleFileUpload) {
        this.YarticleFileUpload = YarticleFileUpload;
    }

    public String getYarticleFileUploadFileName() {
        return YarticleFileUploadFileName;
    }

    public void setYarticleFileUploadFileName(String YarticleFileUploadFileName) {
        this.YarticleFileUploadFileName = YarticleFileUploadFileName;
    }

    public String uploadTicketPoolYArticleFile() {
        try {
            logger.info("--------------Inside Validate Pool Ticket Size Y Article File---------------");
            logger.info("------- Y Mst Promo Id : " + poolticketSizeVO.getyMstPromoId());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String fileName = YarticleFileUpload.toString();
            logger.info("--------- File Reading Start.--------");
            FileResp fileValidateResp = PromotionFileUtil.validateArticleMCFile(fileName);

            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                poolticketSizeVO.setIsUploadFileError("1");
                return INPUT;
            }

            String promotionFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ARTICLE_FILE_PATH);
            String promotionFileName = PromotionUtil.getFileNameWithoutSpace(articleFileUploadFileName);
            String filePath = promotionFilePath + promotionFileName;


            /*Copy the File On Defined File Path
             */
            File bfile = new File(filePath);
            FileUtils.copyFile(YarticleFileUpload.getAbsoluteFile(), bfile);
            logger.info("File is copied successful!");

            if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                poolticketSizeVO.setxFileHistoryFlag("1");
                poolticketSizeVO.setIsXFileUploaded("1");
            } else {
                poolticketSizeVO.setxFileHistoryFlag("0");
                poolticketSizeVO.setIsXFileUploaded("0");
            }
            poolticketSizeVO.setIsYFileUploaded("1");
            /* Calling ODS Article Validate Service
             */
            ValidateArticleMCResp articleValidateResp = PromotionFileUtil.validateODSArticleMC(filePath, empID, Long.valueOf(poolticketSizeVO.getyMstPromoId()),false);
            logger.info("---- upload Ticket Pool Y Article File UPload Resp: " + articleValidateResp.getResp().getMsg());
            if (articleValidateResp.getResp().getRespCode() == com.fks.promo.master.service.RespCode.FAILURE) {
                addActionError(articleValidateResp.getResp().getMsg());
                poolticketSizeVO.setErrorYfilePath("downloadErrorArticleMCFile?errorFilePath=" + articleValidateResp.getErrorFilePath());
                poolticketSizeVO.setIsUploadYFileError("1");
                return INPUT;
            }
            getSessionMap().put(WebConstants.Y_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(articleValidateResp.getArticleMCList()));
            poolticketSizeVO.setIsUploadYFileError("0");
            addActionMessage(articleValidateResp.getResp().getMsg());
            return SUCCESS;
        } catch (Exception ex) {
            logger.error("------ Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String createPoolTicketSize() {
        try {
            logger.info("--------------Creating Pool Ticket Size Promotion---------------");
            logger.info("---------Is X Manual Entry : " + poolticketSizeVO.getIsManualEntry());
            logger.info("---------Is Y Manual Entry : " + poolticketSizeVO.getIsYManualEntry());
            logger.info("---------Mst Promo iD : " + poolticketSizeVO.getMstPromoId());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            CreateTransPromoReq ticketSizeManualReq = PromotionIntiationUtil.getPoolTicketSizeRequest(poolticketSizeVO, zoneId, empID, getSessionMap());
            Resp flatDisResp = ServiceMaster.getTransPromoService().createTransPromo(ticketSizeManualReq);
            if (flatDisResp.getRespCode() == RespCode.SUCCESS) {
                poolticketSizeVO = new PoolTicketSizeFormVO();
                addActionMessage(flatDisResp.getMsg());
                getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                return SUCCESS;
            } else {
                addActionError(flatDisResp.getMsg());
                return INPUT;
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
