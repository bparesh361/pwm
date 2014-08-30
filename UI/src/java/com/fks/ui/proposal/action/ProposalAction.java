/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.proposal.action;

import com.fks.promotion.service.CreateProposalResp;
import com.fks.promotion.service.ErrorFileProposalResp;
import com.fks.promotion.service.ProposalDtlResp;
import com.fks.promotion.service.ProposalVO;
import com.fks.promotion.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.FileResp;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionFileUtil;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.CreateProposalFormVO;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author ajitn
 */
public class ProposalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(ProposalAction.class.getName());
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Map<String, String> deptMap;
    private Map<String, String> problemMap;
    private Map<String, String> promoMap;
    private CreateProposalFormVO createProposalForm;

    @Override
    public String execute() {
        try {
            logger.info("--------------Welcome To Create Promotion Proposal---------------");
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            CachedMapsList maps = new CachedMapsList();
            // deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
            deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
            problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
            promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);

            String isUpdateFlag = req.getParameter("isUpdate");

            if (isUpdateFlag.equalsIgnoreCase("0") && isUpdateFlag != null) {
                logger.info("--------- Create Proposal Case.");

                createProposalForm = new CreateProposalFormVO();
                createProposalForm.setIsUpdateFlagForFileUpload("0");


                /*Restore Last Failure File Record For the logged in user On the Page
                 * Set the details into page
                 * and Resume the proposal process
                 * If user explicitly say new then delete request
                 */
                logger.info("Call Service to fetch previous file : getPromotionProposalService().getLastErrorFileProposalDtl");
                ErrorFileProposalResp errorResp = ServiceMaster.getPromotionProposalService().getLastErrorFileProposalDtl(empID);
                logger.info("-------- previous file Service Resp : " + errorResp.getResp().getMsg());

                if (errorResp.getResp().getRespCode() == RespCode.SUCCESS) {
                    if (errorResp.getProposalVo() != null) {
                        ProposalVO vo = errorResp.getProposalVo();

                        createProposalForm.setProposalID(vo.getProposalId());
                        createProposalForm.setUploadDeptId(vo.getDeptId());
                        createProposalForm.setUploadProblemTypeId(vo.getProblemTypeId());
                        createProposalForm.setUploadRemarks(vo.getRemarks());
                        createProposalForm.setUploadSolutionDesc(vo.getSolutionDesc());
                        createProposalForm.setUploadPromoType(vo.getPromoType());
                        createProposalForm.setUploadPromoDesc(vo.getPromoDesc());
                        createProposalForm.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + vo.getErrorFilePath());

                        addActionError("Previous Promotion Proposal With Article File Failure.");
                    }
                }
            } else {
                /*If isUpdateFlag = 1 Then Page Request Comes From Proposal Search Page For Modification
                 * Set Proposal Request View For Update With File Article Upload View
                 */
                String updateProposalId = req.getParameter("proposalId");
                logger.info("------- Update Proposal Case With ID : " + updateProposalId);
                createProposalForm = new CreateProposalFormVO();

                /*Get The Proposal Details TO be Updated
                 * Fill Details In to Form Controlls
                 */
                ProposalDtlResp proposalResp = ServiceMaster.getPromotionProposalService().getProposalDtl(Long.valueOf(updateProposalId), false);
                if (proposalResp.getResp().getRespCode() == RespCode.SUCCESS) {
                    ProposalVO vo = proposalResp.getProposal();

                    createProposalForm.setIsUpdateFlagForFileUpload("1");

                    createProposalForm.setProposalID(updateProposalId);
                    createProposalForm.setUploadDeptId(vo.getDeptId());
                    createProposalForm.setUploadProblemTypeId(vo.getProblemTypeId());
                    createProposalForm.setUploadRemarks(vo.getRemarks());
                    createProposalForm.setUploadSolutionDesc(vo.getSolutionDesc());
                    createProposalForm.setUploadPromoType(vo.getPromoType());
                    createProposalForm.setUploadPromoDesc(vo.getPromoDesc());

                    createProposalForm.setDownloadArticleFileForUpdate("downloadProposalArticleMCFile?proposalId=" + updateProposalId);


                    if (vo.getOtherFilePath() != null) {
                        createProposalForm.setDownloadOtherFile("downloadfile?path=" + vo.getOtherFilePath());
                    } else {
                        createProposalForm.setDownloadOtherFile("#");
                    }
                } else {
                    createProposalForm.setIsUpdateFlagForFileUpload("0");
                    addActionError(proposalResp.getResp().getMsg());
                }

            }
            return SUCCESS;
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String viewProposalSearchPage() {
        logger.info("------------- Inside Proposal Search Page -----------");
        String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
        if (empID != null) {
            return SUCCESS;
        } else {
            return LOGIN;
        }
    }
    private File otherFileUpload;
    private String otherFileUploadFileName;

    public File getOtherFileUpload() {
        return otherFileUpload;
    }

    public void setOtherFileUpload(File otherFileUpload) {
        this.otherFileUpload = otherFileUpload;
    }

    public String getOtherFileUploadFileName() {
        return otherFileUploadFileName;
    }

    public void setOtherFileUploadFileName(String otherFileUploadFileName) {
        this.otherFileUploadFileName = otherFileUploadFileName;
    }

    public String createManualProposal() {
        try {
            logger.info("--------------Creating Manual Promotion Proposal. service : getPromotionProposalService().createManualProposal()---------------");
            logger.info("isSaveDraft : " + createProposalForm.getIsSaveDraft());
            CachedMapsList maps = new CachedMapsList();
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            ProposalVO request = ProposalUtil.getManualCreateProposalRequest(createProposalForm, empID, zoneId);

            //Cr2 Change Upload Other File For Manual Proposal
            if (otherFileUpload != null) {
                if (otherFileUpload.length() == 0) {
                    addActionError("Empty Additional file can not be uploaded.");
                    //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                    deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                    problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                    promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    createProposalForm = new CreateProposalFormVO();
                    return INPUT;
                }
                System.out.println("----- length : " + otherFileUpload.length());
                if (otherFileUpload.length() > 5242880) {
                    addActionError("Additional File cannot be upload more than size 5 MB.");
                    //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                    deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                    problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                    promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    createProposalForm = new CreateProposalFormVO();
                    return INPUT;
                }
                String otherProposalFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_OTHER_FILE_PATH);
                String proposalFileName = PromotionUtil.getFileNameWithoutSpace(otherFileUploadFileName);
                Calendar cal = new GregorianCalendar();
                Long time = cal.getTimeInMillis();
                String filePath = otherProposalFilePath + empID + "_" + time.toString() + "_" + proposalFileName;
                System.out.println("File : " + filePath);
                File bfile = new File(filePath);
                FileUtils.copyFile(otherFileUpload.getAbsoluteFile(), bfile);
                logger.info("Proposal Other File copied succesfully : " + filePath);

                request.setOtherFilePath(filePath);
            }
            //Cr 2 change finished

            CreateProposalResp serviceResp = ServiceMaster.getPromotionProposalService().createManualProposal(request);

            logger.info("------- Service Resp Code : " + serviceResp.getResp().getRespCode().value().toString());

            if (serviceResp.getResp().getRespCode() == RespCode.SUCCESS) {
                addActionMessage(serviceResp.getResp().getMsg());
                //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                return SUCCESS;
            } else {
                addActionError(serviceResp.getResp().getMsg());
                //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                return INPUT;
            }

        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
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

    public String uploadProposalArticle() {
        try {
            logger.info("--------------Uploading Promotion Proposal Articles. Service :getPromotionProposalService().createUploadFileProposal ---------------");
            logger.info("isUploadSaveDraft : " + createProposalForm.getIsUploadSaveDraft());
            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            CachedMapsList maps = new CachedMapsList();
            ProposalVO request = null;
            if (articleFileUpload != null) {
                if (articleFileUpload.length() == 0) {
                    addActionError("Empty Article file can not be uploaded.");
                    //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                    deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                    problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                    promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    createProposalForm = new CreateProposalFormVO();
                    return INPUT;
                }
                String fileName = articleFileUpload.toString();
                logger.info("--------- File Reading Start.--------");
                FileResp fileValidateResp = PromotionFileUtil.validateArticleMCFile(fileName);

                if (fileValidateResp.getIsError() == true) {
                    logger.info(fileValidateResp.getErrorMsg());
                    addActionError(fileValidateResp.getErrorMsg());
                    //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                    deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                    problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                    promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    createProposalForm.setIsUploadFileError("1");

                    /*Set Download Article Link For PRoposal Update
                     * Local File Validation Gets Failed.
                     */
                    if (createProposalForm.getIsUpdateFlagForFileUpload().equalsIgnoreCase("1")) {
                        createProposalForm.setDownloadArticleFileForUpdate(createProposalForm.getDownloadArticleFileForUpdate());
                    }
                    return INPUT;
                }

                String proposalFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ARTICLE_FILE_PATH);
                String proposalFileName = PromotionUtil.getFileNameWithoutSpace(articleFileUploadFileName);


                /* Create Proposal Request For getting New Proposal Id And File Path
                 * In Response We get Proposal ID And Path
                 */

                request = ProposalUtil.getUploadFileCreateProposalRequest(createProposalForm, empID, proposalFilePath, proposalFileName, zoneId);
            } else {
//                if (createProposalForm.getIsUpdateFlagForFileUpload().equalsIgnoreCase("1")) {
                logger.info("-------------- Update Proposal Without File Upload");
                request = ProposalUtil.getProposalUploadFileUpdateRequest(createProposalForm, empID, zoneId);
            }
            /*Set Proposal ID ON Previuos Error File
             * If Page is loaded with previous error file status
             * then on save or submit we get the proposal ID             
             */
            if (createProposalForm.getProposalID() != null) {
                request.setProposalId(createProposalForm.getProposalID());
            }

            //Cr2 Change Upload Other File For Manual Proposal
            if (otherFileUpload != null) {
                if (otherFileUpload.length() == 0) {
                    addActionError("Empty Additional file can not be uploaded.");
                    //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                    deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                    problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                    promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    createProposalForm = new CreateProposalFormVO();
                    return INPUT;
                }
                if (otherFileUpload.length() > 5242880) {
                    addActionError("Additional File cannot be uploaded more than size 5 MB.");
                    //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                    deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                    problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                    promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                    createProposalForm = new CreateProposalFormVO();
                    return INPUT;
                }
                String otherProposalFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_OTHER_FILE_PATH);
                String proposalFileName = PromotionUtil.getFileNameWithoutSpace(otherFileUploadFileName);
                Calendar cal = new GregorianCalendar();
                Long time = cal.getTimeInMillis();
                String filePath = otherProposalFilePath + empID + "_" + time.toString() + "_" + proposalFileName;
                System.out.println("File : " + filePath);
                File bfile = new File(filePath);
                FileUtils.copyFile(otherFileUpload.getAbsoluteFile(), bfile);
                logger.info("Proposal Other File copied succesfully : " + filePath);

                request.setOtherFilePath(filePath);
            }
            //Cr 2 change finished

            CreateProposalResp serviceResp = ServiceMaster.getPromotionProposalService().createUploadFileProposal(request);
            logger.info("-------Upload File Service Resp Code : " + serviceResp.getResp().getRespCode().value().toString());

            if (serviceResp.getResp().getRespCode() == RespCode.SUCCESS) {

                logger.info("-------File Path : " + serviceResp.getFilePath());

                logger.info("------- Proposal Id : " + serviceResp.getResp().getPk());
                logger.info("------- server resp : " + serviceResp.getResp().getMsg());

                if (articleFileUpload != null) {
                    /*Copy the File On Defined File Path
                     */
                    String filePath = serviceResp.getFilePath();
                    File bfile = new File(filePath);
                    FileUtils.copyFile(articleFileUpload.getAbsoluteFile(), bfile);
                    logger.info("File is copied successful!");

                    /*Call Validate Service Which In turn Sent the File For DB Validations
                     */
                    CreateProposalResp validateResp = ServiceMaster.getPromotionProposalService().sendArticleFileForvalidate(serviceResp.getResp().getPk(), serviceResp.getFilePath(), createProposalForm.getIsUploadSaveDraft());
                    logger.info("-------Validate File Service Resp Code : " + validateResp.getResp().getMsg());

                    if (validateResp.getResp().getRespCode() == RespCode.SUCCESS) {
                        addActionMessage(serviceResp.getResp().getMsg());
                        deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                        //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                        problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                        promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);

                        createProposalForm = new CreateProposalFormVO();
                        return SUCCESS;
                    } else {
                        addActionError(validateResp.getResp().getMsg());
                        // deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                        deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                        problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                        promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);

                        createProposalForm.setIsUploadFileError("1");
                        createProposalForm.setProposalID(serviceResp.getResp().getPk().toString());
                        createProposalForm.setErrorfilePath("downloadErrorArticleMCFile?errorFilePath=" + validateResp.getFilePath());

                        /*Set Download Article Link For PRoposal Update
                         * ODS File Validation Gets Failed.
                         */
                        if (createProposalForm.getIsUpdateFlagForFileUpload().equalsIgnoreCase("1")) {
//                            createProposalForm.setDownloadArticleFileForUpdate(createProposalForm.getDownloadArticleFileForUpdate());
                            createProposalForm.setDownloadArticleFileForUpdate("downloadProposalArticleMCFile?proposalId=" + serviceResp.getResp().getPk().toString());
                        }
                        return INPUT;
                    }
                }

                /* if Proposal Update called then display the proposal search page
                 */
//                if (createProposalForm.getIsUpdateFlagForFileUpload().equalsIgnoreCase("1")) {
//                    addActionMessage(serviceResp.getResp().getMsg());
//                    createProposalForm = new CreateProposalFormVO();
//                    return "viewDashboard";
//                }

                addActionMessage(serviceResp.getResp().getMsg());
                //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                createProposalForm = new CreateProposalFormVO();
                return SUCCESS;
            } else {
                addActionError(serviceResp.getResp().getMsg());
                //deptMap = maps.getMapByUserSession(MapEnum.USER_DEPARTMENT, empID);
                deptMap = maps.getActiveMap(MapEnum.DEPARMENT);
                problemMap = maps.getActiveMap(MapEnum.PROBLEM_TYPE);
                promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                return INPUT;
            }

        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public Map<String, String> getDeptMap() {
        return deptMap;
    }

    public void setDeptMap(Map<String, String> deptMap) {
        this.deptMap = deptMap;
    }

    public Map<String, String> getProblemMap() {
        return problemMap;
    }

    public void setProblemMap(Map<String, String> problemMap) {
        this.problemMap = problemMap;
    }

    public Map<String, String> getPromoMap() {
        return promoMap;
    }

    public void setPromoMap(Map<String, String> promoMap) {
        this.promoMap = promoMap;
    }

    public CreateProposalFormVO getCreateProposalForm() {
        return createProposalForm;
    }

    public void setCreateProposalForm(CreateProposalFormVO createProposalForm) {
        this.createProposalForm = createProposalForm;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.req = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.resp = hsr;
    }
}
