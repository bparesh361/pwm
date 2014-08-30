/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.CreateTransPromoReq;
import com.fks.promo.init.Resp;
import com.fks.promo.init.TransPromoVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.FileResp;
import com.fks.ui.constants.PromotionFileUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.CreateBXGYFormVO;
import com.fks.ui.master.vo.ArticleMcVo;
import com.fks.ui.proposal.action.ProposalUtil;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class BuyXGetYAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(BuyXGetYAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private CreateBXGYFormVO createBXGYFormVO;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate BuyXGet set level discount Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                logger.info("Valid User .");
                getSessionMap().remove(WebConstants.X_ARTICLE);
                getSessionMap().remove(WebConstants.Y_ARTICLE);
                getSessionMap().remove(WebConstants.Z_ARTICLE);
                getSessionMap().remove(WebConstants.SET4_ARTICLE);
                getSessionMap().remove(WebConstants.SET5_ARTICLE);
                getSessionMap().put(WebConstants.X_ARTICLE, null);
                getSessionMap().put(WebConstants.Y_ARTICLE, null);
                getSessionMap().put(WebConstants.Z_ARTICLE, null);
                getSessionMap().put(WebConstants.SET4_ARTICLE, null);
                getSessionMap().put(WebConstants.SET5_ARTICLE, null);
                Object sPromoId = getSessionMap().get(WebConstants.PROMOTION_REQ_ID);
                logger.info("Promo Id :" + sPromoId);
                createBXGYFormVO = new CreateBXGYFormVO();
                if(sPromoId!=null){
                    createBXGYFormVO.setIsInitiatorRedirect("1");
                    createBXGYFormVO.setSessionmstPromoId(sPromoId.toString());
                }

                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion BuyXGetYAtDiscountAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String uploadXArticleFile() {
        logger.info("====== Inside Validating X Article File =====");
        logger.info("=== Group Size: " + createBXGYFormVO.getSelGroupX());
        logger.info("====== Promo Id :" + createBXGYFormVO.getIsMstPromoIdX());
        getSessionMap().remove(WebConstants.X_ARTICLE);
        getSessionMap().put(WebConstants.X_ARTICLE, null);
        Long mstPromoId = new Long(createBXGYFormVO.getIsMstPromoIdX());
        if (createBXGYFormVO.getBXarticleUploadFile() != null) {
            String fileName = createBXGYFormVO.getBXarticleUploadFile().toString();
            logger.info("----- File Name : " + fileName);
            FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(fileName);
            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                createBXGYFormVO.setIsXuploaderror("0");
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                return INPUT;
            }
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            com.fks.promo.master.service.ValidateArticleMCResp resp = PromotionFileUtil.validateODSArticleMC(fileName, empID, mstPromoId, true);
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsXuploaderror("2");
                getSessionMap().put(WebConstants.X_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(resp.getArticleMCList()));
                logger.info("=========== #### X Article : " + getSessionMap().get(WebConstants.X_ARTICLE).toString());
                return SUCCESS;
            } else {
                addActionError(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsXuploaderror("1");
                createBXGYFormVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getErrorFilePath());
                return INPUT;
            }
        } else {
            addActionError("Invalid File.");
            if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                createBXGYFormVO.setIsZuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                createBXGYFormVO.setIsYuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                createBXGYFormVO.setIsSet4uploaderror("2");
            }
             if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
            createBXGYFormVO.setIsXuploaderror("0");
            return INPUT;
        }
    }

    public String uploadYArticleFile() {
        logger.info("====== Inside Validating Y Article File =====");
        getSessionMap().remove(WebConstants.Y_ARTICLE);
        getSessionMap().put(WebConstants.Y_ARTICLE, null);
        Long mstPromoId = new Long(createBXGYFormVO.getIsMstPromoIdY());
        if (createBXGYFormVO.getGYarticleUploadFile() != null) {
            String fileName = createBXGYFormVO.getGYarticleUploadFile().toString();
            logger.info("----- File Name : " + fileName);
            FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(fileName);
            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                createBXGYFormVO.setIsYuploaderror("0");
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                return INPUT;
            }
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            com.fks.promo.master.service.ValidateArticleMCResp resp = PromotionFileUtil.validateODSArticleMC(fileName, empID, mstPromoId, true);
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsYuploaderror("2");
                getSessionMap().put(WebConstants.Y_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(resp.getArticleMCList()));
                logger.info("=========== #### Y Article : " + getSessionMap().get(WebConstants.Y_ARTICLE).toString());
                return SUCCESS;
            } else {
                addActionError(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsYuploaderror("1");
                createBXGYFormVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getErrorFilePath());
                return INPUT;
            }
        } else {
            addActionError("Invalid File.");
            createBXGYFormVO.setIsYuploaderror("0");
            return INPUT;
        }
    }

    public String uploadZArticleFile() {
        logger.info("====== Inside Validating Z Article File =====");
        getSessionMap().remove(WebConstants.Z_ARTICLE);
        getSessionMap().put(WebConstants.Z_ARTICLE, null);
        Long mstPromoId = new Long(createBXGYFormVO.getIsMstPromoIdZ());
        if (createBXGYFormVO.getBZarticleUploadFile() != null) {
            String fileName = createBXGYFormVO.getBZarticleUploadFile().toString();
            logger.info("----- File Name : " + fileName);
            FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(fileName);
            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                createBXGYFormVO.setIsZuploaderror("0");
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                return INPUT;
            }
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            com.fks.promo.master.service.ValidateArticleMCResp resp = PromotionFileUtil.validateODSArticleMC(fileName, empID, mstPromoId, true);
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsZuploaderror("2");
                getSessionMap().put(WebConstants.Z_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(resp.getArticleMCList()));
                logger.info("=========== #### Z Article : " + getSessionMap().get(WebConstants.Z_ARTICLE).toString());
                return SUCCESS;
            } else {
                addActionError(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsZuploaderror("1");
                createBXGYFormVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getErrorFilePath());
                return INPUT;
            }
        } else {
            addActionError("Invalid File.");
            if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                createBXGYFormVO.setIsYuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                createBXGYFormVO.setIsXuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                createBXGYFormVO.setIsSet4uploaderror("2");
            }
              if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
            createBXGYFormVO.setIsZuploaderror("0");
            return INPUT;
        }
    }

    public String uploadSet4ArticleFile() {
        logger.info("====== Inside Validating Set 4 Article File =====");
        getSessionMap().remove(WebConstants.SET4_ARTICLE);
        getSessionMap().put(WebConstants.SET4_ARTICLE, null);
        Long mstPromoId = new Long(createBXGYFormVO.getIsMstPromoIdSet4());
        if (createBXGYFormVO.getSet4articleUploadFile() != null) {
            String fileName = createBXGYFormVO.getSet4articleUploadFile().toString();
            logger.info("----- File Name : " + fileName);
            FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(fileName);
            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                createBXGYFormVO.setIsSet4uploaderror("0");
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }

                return INPUT;
            }
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            com.fks.promo.master.service.ValidateArticleMCResp resp = PromotionFileUtil.validateODSArticleMC(fileName, empID, mstPromoId, true);
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsSet4uploaderror("2");
                getSessionMap().put(WebConstants.SET4_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(resp.getArticleMCList()));
                logger.info("=========== #### Set 4 Article : " + getSessionMap().get(WebConstants.SET4_ARTICLE).toString());
                return SUCCESS;
            } else {
                addActionError(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
                createBXGYFormVO.setIsSet4uploaderror("1");
                createBXGYFormVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getErrorFilePath());
                return INPUT;
            }
        } else {
            addActionError("Invalid File.");
            if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                createBXGYFormVO.setIsZuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                createBXGYFormVO.setIsXuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                createBXGYFormVO.setIsYuploaderror("2");
            }
             if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet5uploaderror("2");
                }
            createBXGYFormVO.setIsSet4uploaderror("0");
            return INPUT;
        }
    }


    public String uploadSet5ArticleFile() {
        logger.info("====== Inside Validating Set 5 Article File =====");
        getSessionMap().remove(WebConstants.SET5_ARTICLE);
        getSessionMap().put(WebConstants.SET5_ARTICLE, null);
        Long mstPromoId = new Long(createBXGYFormVO.getIsMstPromoIdSet5());
        if (createBXGYFormVO.getSet5articleUploadFile() != null) {
            String fileName = createBXGYFormVO.getSet5articleUploadFile().toString();
            logger.info("----- File Name : " + fileName);
            FileResp fileValidateResp = PromotionFileUtil.validateIntiationArticleMCFile(fileName);
            if (fileValidateResp.getIsError() == true) {
                logger.info(fileValidateResp.getErrorMsg());
                addActionError(fileValidateResp.getErrorMsg());
                createBXGYFormVO.setIsSet5uploaderror("0");
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }

                return INPUT;
            }
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            com.fks.promo.master.service.ValidateArticleMCResp resp = PromotionFileUtil.validateODSArticleMC(fileName, empID, mstPromoId, true);
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                createBXGYFormVO.setIsSet5uploaderror("2");
                getSessionMap().put(WebConstants.SET5_ARTICLE, PromotionFileUtil.getValidatedArticleMcMap(resp.getArticleMCList()));
                logger.info("=========== #### Set 5 Article : " + getSessionMap().get(WebConstants.SET5_ARTICLE).toString());
                return SUCCESS;
            } else {
                addActionError(resp.getResp().getMsg());
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    createBXGYFormVO.setIsZuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    createBXGYFormVO.setIsXuploaderror("2");
                }
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    createBXGYFormVO.setIsYuploaderror("2");
                }
                 if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
                createBXGYFormVO.setIsSet5uploaderror("1");
                createBXGYFormVO.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + resp.getErrorFilePath());
                return INPUT;
            }
        } else {
            addActionError("Invalid File.");
            if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                createBXGYFormVO.setIsZuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                createBXGYFormVO.setIsXuploaderror("2");
            }
            if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                createBXGYFormVO.setIsYuploaderror("2");
            }
             if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    createBXGYFormVO.setIsSet4uploaderror("2");
                }
            createBXGYFormVO.setIsSet5uploaderror("0");
            return INPUT;
        }
    }

    public String createArticleInitiate() {
        logger.info("====== Inside creating manual article Initiate ===");
        try {

            // logger.info("=========== #### X Article : " + getSessionMap().get(WebConstants.X_ARTICLE).toString());
            //logger.info("=========== #### Y Article : " + getSessionMap().get(WebConstants.Y_ARTICLE).toString());
//            logger.info("From  : "+createBXGYFormVO.getTxtBXGYFrom());
//            logger.info("to  : "+createBXGYFormVO.getTxtBXGYTo());
//            logger.info("Mst_promo Id : "+createBXGYFormVO.getPromoreqID());
//            logger.info("Grid X :"+ createBXGYFormVO.getManualXArticleData());
//            logger.info("growth value :"+createBXGYFormVO.getTxtBXGYgrowth());
//            logger.info("dis value : "+createBXGYFormVO.getTxtdisValue());

            logger.info("--- X : " + createBXGYFormVO.getIsXManualEntry());
            logger.info("--- Y : " + createBXGYFormVO.getIsYManualEntry());
            logger.info("--- Z : " + createBXGYFormVO.getIsZManualEntry());
            logger.info("--- Set 4 : " + createBXGYFormVO.getIsSet4ManualEntry());

            //Set Data from Session file
            List<ArticleMcVo> list = new ArrayList<ArticleMcVo>();
            List<ArticleMcVo> Sendlist = new ArrayList<ArticleMcVo>();
            ArticleMcVo articleMcVo = null;
            if (createBXGYFormVO.getIsXManualEntry() == null ? "0" == null : createBXGYFormVO.getIsXManualEntry().equals("0")) {
                if (getSessionMap().get(WebConstants.X_ARTICLE) != null) {
                    list = (List<ArticleMcVo>) getSessionMap().get(WebConstants.X_ARTICLE);
                    System.out.println("List size : " + list.size());
                }
                if (list.size() > 0 && !list.isEmpty()) {
                    for (ArticleMcVo vo : list) {
                        articleMcVo = new ArticleMcVo();
                        articleMcVo.setArticleCode(vo.getArticleCode());
                        articleMcVo.setArticleDesc(vo.getArticleDesc());
                        articleMcVo.setMcCode(vo.getMcCode());
                        articleMcVo.setMcDesc(vo.getMcDesc());
                        articleMcVo.setQty(vo.getQty());
                        articleMcVo.setIs_x(true);
                        System.out.println("X value :" + articleMcVo.isIs_x());
                        articleMcVo.setIs_y(false);
                        articleMcVo.setIs_z(false);
                        articleMcVo.setIs_a(false);
                        articleMcVo.setIs_b(false);
                        Sendlist.add(articleMcVo);
                    }
                }
                System.out.println("List size : " + Sendlist.size());
            } else {
                getSessionMap().remove(WebConstants.X_ARTICLE);
                getSessionMap().put(WebConstants.X_ARTICLE, null);
            }
            if (createBXGYFormVO.getIsYManualEntry() == null ? "0" == null : createBXGYFormVO.getIsYManualEntry().equals("0")) {
                if (getSessionMap().get(WebConstants.Y_ARTICLE) != null) {
                    list = (List<ArticleMcVo>) getSessionMap().get(WebConstants.Y_ARTICLE);
                }
                if (list.size() > 0 && !list.isEmpty()) {
                    for (ArticleMcVo vo : list) {
                        articleMcVo = new ArticleMcVo();
                        articleMcVo.setArticleCode(vo.getArticleCode());
                        articleMcVo.setArticleDesc(vo.getArticleDesc());
                        articleMcVo.setMcCode(vo.getMcCode());
                        articleMcVo.setMcDesc(vo.getMcDesc());
                        articleMcVo.setQty(vo.getQty());
                        articleMcVo.setIs_x(false);
                        articleMcVo.setIs_y(true);
                        articleMcVo.setIs_z(false);
                        articleMcVo.setIs_a(false);
                        articleMcVo.setIs_b(false);
                        Sendlist.add(articleMcVo);
                    }
                }
                System.out.println("List size : " + Sendlist.size());
            } else {
                getSessionMap().remove(WebConstants.Y_ARTICLE);
                getSessionMap().put(WebConstants.Y_ARTICLE, null);
            }
            if (createBXGYFormVO.getIsZManualEntry() == null ? "0" == null : createBXGYFormVO.getIsZManualEntry().equals("0")) {
                if (getSessionMap().get(WebConstants.Z_ARTICLE) != null) {
                    list = (List<ArticleMcVo>) getSessionMap().get(WebConstants.Z_ARTICLE);
                }

                if (list.size() > 0 && !list.isEmpty()) {
                    for (ArticleMcVo vo : list) {
                        articleMcVo = new ArticleMcVo();
                        articleMcVo.setArticleCode(vo.getArticleCode());
                        articleMcVo.setArticleDesc(vo.getArticleDesc());
                        articleMcVo.setMcCode(vo.getMcCode());
                        articleMcVo.setMcDesc(vo.getMcDesc());
                        articleMcVo.setQty(vo.getQty());
                        articleMcVo.setIs_x(false);
                        articleMcVo.setIs_y(false);
                        articleMcVo.setIs_z(true);
                        articleMcVo.setIs_a(false);
                        articleMcVo.setIs_b(false);
                        Sendlist.add(articleMcVo);
                    }
                }
                System.out.println("List size : " + Sendlist.size());
            } else {
                getSessionMap().remove(WebConstants.Z_ARTICLE);
                getSessionMap().put(WebConstants.X_ARTICLE, null);
            }
            if (createBXGYFormVO.getIsSet4ManualEntry() == null ? "0" == null : createBXGYFormVO.getIsSet4ManualEntry().equals("0")) {
                if (getSessionMap().get(WebConstants.SET4_ARTICLE) != null) {
                    list = (List<ArticleMcVo>) getSessionMap().get(WebConstants.SET4_ARTICLE);
                    System.out.println("List size : " + list.size());
                }
                if (list.size() > 0 && !list.isEmpty()) {
                    for (ArticleMcVo vo : list) {
                        articleMcVo = new ArticleMcVo();
                        articleMcVo.setArticleCode(vo.getArticleCode());
                        articleMcVo.setArticleDesc(vo.getArticleDesc());
                        articleMcVo.setMcCode(vo.getMcCode());
                        articleMcVo.setMcDesc(vo.getMcDesc());
                        articleMcVo.setQty(vo.getQty());
                        articleMcVo.setIs_x(false);
                        System.out.println("X value :" + articleMcVo.isIs_x());
                        articleMcVo.setIs_y(false);
                        articleMcVo.setIs_z(false);
                        articleMcVo.setIs_a(true);
                        articleMcVo.setIs_b(false);
                        Sendlist.add(articleMcVo);
                    }
                }

                System.out.println("List size : " + Sendlist.size());
            } else {
                getSessionMap().remove(WebConstants.SET4_ARTICLE);
                getSessionMap().put(WebConstants.SET4_ARTICLE, null);
            }
             if (createBXGYFormVO.getIsSet5ManualEntry() == null ? "0" == null : createBXGYFormVO.getIsSet5ManualEntry().equals("0")) {
                if (getSessionMap().get(WebConstants.SET5_ARTICLE) != null) {
                    list = (List<ArticleMcVo>) getSessionMap().get(WebConstants.SET5_ARTICLE);
                    System.out.println("List size : " + list.size());
                }
                if (list.size() > 0 && !list.isEmpty()) {
                    for (ArticleMcVo vo : list) {
                        articleMcVo = new ArticleMcVo();
                        articleMcVo.setArticleCode(vo.getArticleCode());
                        articleMcVo.setArticleDesc(vo.getArticleDesc());
                        articleMcVo.setMcCode(vo.getMcCode());
                        articleMcVo.setMcDesc(vo.getMcDesc());
                        articleMcVo.setQty(vo.getQty());
                        articleMcVo.setIs_x(false);
                        articleMcVo.setIs_y(false);
                        articleMcVo.setIs_z(false);
                        articleMcVo.setIs_a(false);
                        articleMcVo.setIs_b(true);
                        Sendlist.add(articleMcVo);
                    }
                }

                System.out.println("List size : " + Sendlist.size());
            } else {
                getSessionMap().remove(WebConstants.SET5_ARTICLE);
                getSessionMap().put(WebConstants.SET5_ARTICLE, null);
            }
            CreateTransPromoReq req = new CreateTransPromoReq();
            TransPromoVO tpvo = ProposalUtil.getManualCreateBXGXRequest(createBXGYFormVO, Sendlist);
            req.setTransPromoVO(tpvo);
            req.setZoneId(new Long(getSessionMap().get(WebConstants.ZONE_ID).toString()));
            req.setEmpId(new Long(getSessionMap().get(WebConstants.EMP_ID).toString()));
            req.setMstPromoId(new Long(createBXGYFormVO.getPromoreqID()));
            req.setTypeId(WebConstants.BUY_X_GET_Y);
            Resp resp = ServiceMaster.getTransPromoService().createTransPromo(req);
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                createBXGYFormVO=new CreateBXGYFormVO();
                addActionMessage(resp.getMsg());
                getSessionMap().put(WebConstants.PROMOTION_REQ_ID, null);
                 getSessionMap().put(WebConstants.X_ARTICLE, null);
                getSessionMap().put(WebConstants.Y_ARTICLE, null);
                getSessionMap().put(WebConstants.Z_ARTICLE, null);
                getSessionMap().put(WebConstants.SET4_ARTICLE, null);
                getSessionMap().put(WebConstants.SET5_ARTICLE, null);
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }

        } catch (Exception e) {
            logger.info("Exception in createManualArticleInitiate of BuyXGetYAction :");
            e.printStackTrace();
            addActionError("Exception  :" + e.getMessage());
            return ERROR;

        }
    }

    public CreateBXGYFormVO getCreateBXGYFormVO() {
        return createBXGYFormVO;
    }

    public void setCreateBXGYFormVO(CreateBXGYFormVO createBXGYFormVO) {
        this.createBXGYFormVO = createBXGYFormVO;
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
