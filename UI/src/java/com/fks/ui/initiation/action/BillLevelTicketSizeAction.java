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
import com.fks.ui.form.vo.BillLevelTicketSizeFormVO;
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
public class BillLevelTicketSizeAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(BillLevelTicketSizeAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private BillLevelTicketSizeFormVO ticketSizeVO;

    public BillLevelTicketSizeFormVO getTicketSizeVO() {
        return ticketSizeVO;
    }

    public void setTicketSizeVO(BillLevelTicketSizeFormVO ticketSizeVO) {
        this.ticketSizeVO = ticketSizeVO;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate BillLevelTicketSizeAction Action Page ----------------");
        try {
           Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                ticketSizeVO = new BillLevelTicketSizeFormVO();
                Object sPromoId = getSessionMap().get(WebConstants.PROMOTION_REQ_ID);
                logger.info("Promo Id :" + sPromoId);
                if(sPromoId!=null){
                    ticketSizeVO.setIsInitiatorRedirect("1");
                    ticketSizeVO.setSessionmstPromoId(sPromoId.toString());
                }
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion BillLevelTicketSizeAction Page ----- ");
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

    public String createBillLevelTicketSize() {
        try {
            logger.info("--------------Creating Bill Level Ticket Size Promotion---------------");
            logger.info("---------Is Manual Entry : " + ticketSizeVO.getIsManualEntry());
            logger.info("---------Mst Promo iD : " + ticketSizeVO.getMstPromoId());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            if (ticketSizeVO.getIsManualEntry().equalsIgnoreCase("1") && articleFileUpload == null) {
                logger.info("--------- Article Dtl : " + ticketSizeVO.getManualArticleData());
                logger.info("----- Disconfig : " + ticketSizeVO.getDiscountConfig());
                CreateTransPromoReq ticketSizeManualReq = PromotionIntiationUtil.getBillTicketSizeRequest(ticketSizeVO, null, zoneId, empID);
                Resp flatDisResp = ServiceMaster.getTransPromoService().createTransPromo(ticketSizeManualReq);
                if (flatDisResp.getRespCode() == RespCode.SUCCESS) {
                    ticketSizeVO = new BillLevelTicketSizeFormVO();
                    addActionMessage(flatDisResp.getMsg());
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                    return SUCCESS;
                } else {
                    addActionError(flatDisResp.getMsg());
                    ticketSizeVO = new BillLevelTicketSizeFormVO();
                    return INPUT;
                }
            } else {
                String fileName = articleFileUpload.toString();
                logger.info("--------- File Reading Start.--------");
                FileResp fileValidateResp = PromotionFileUtil.validateArticleMCFile(fileName);

                if (fileValidateResp.getIsError() == true) {
                    logger.info(fileValidateResp.getErrorMsg());
                    addActionError(fileValidateResp.getErrorMsg());
                    ticketSizeVO.setIsUploadFileError("1");
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
                ValidateArticleMCResp articleValidateResp = PromotionFileUtil.validateODSArticleMC(filePath, empID,Long.valueOf(ticketSizeVO.getMstPromoId()),false);
                if (articleValidateResp.getResp().getRespCode() == com.fks.promo.master.service.RespCode.FAILURE) {
                    logger.info("---- Flat Discount FIle UPload Resp: " + articleValidateResp.getResp().getMsg());
                    addActionError(articleValidateResp.getResp().getMsg());
                    ticketSizeVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + articleValidateResp.getErrorFilePath());
                    ticketSizeVO.setIsUploadFileError("1");
                    return INPUT;
                }
                /* File is validated successfully with ODS
                 *Call Create TransPromo Service For Saving the Data
                 */

                CreateTransPromoReq ticketSizeFileReq = PromotionIntiationUtil.getBillTicketSizeRequest(ticketSizeVO, articleValidateResp.getArticleMCList(), zoneId, empID);
                Resp flatDisResp = ServiceMaster.getTransPromoService().createTransPromo(ticketSizeFileReq);
                if (flatDisResp.getRespCode() == RespCode.SUCCESS) {
                    ticketSizeVO = new BillLevelTicketSizeFormVO();
                    addActionMessage(flatDisResp.getMsg());
                    getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                    return SUCCESS;
                } else {
                    addActionError(flatDisResp.getMsg());
                    ticketSizeVO = new BillLevelTicketSizeFormVO();
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
