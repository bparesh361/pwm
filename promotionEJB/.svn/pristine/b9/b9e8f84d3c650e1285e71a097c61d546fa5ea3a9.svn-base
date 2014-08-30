/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service;

import com.fks.promo.common.CommonStatus;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.PromotionDAO;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransProposal;
import com.fks.promo.facade.MstDepartmentFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstProblemFacade;
import com.fks.promo.facade.MstPromotionTypeFacade;
import com.fks.promo.facade.MstProposalFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.TransProposalFacade;
import com.fks.promo.init.vo.AcceptRejectProposalVO;
import com.fks.promotion.service.util.PromotionProposalUtil;
import com.fks.promotion.service.util.ProposalDownloadFileUtil;
import com.fks.promotion.vo.ArticleMCVO;
import com.fks.promotion.vo.CreateProposalResp;
import com.fks.promotion.vo.ErrorFileProposalResp;
import com.fks.promotion.vo.ProposalDtlResp;
import com.fks.promotion.vo.ProposalEnum;
import com.fks.promotion.vo.ProposalVO;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author ajitn
 */
@Stateless
@LocalBean
@WebService
public class PromotionProposalService {

    private static final Logger logger = Logger.getLogger(PromotionProposalService.class.getName());
    @EJB
    MstEmployeeFacade mstEmployeeFacade;
    @EJB
    MstDepartmentFacade mstDeptartmentFacade;
    @EJB
    MstProblemFacade mstProblemFacade;
    @EJB
    MstPromotionTypeFacade mstPromoTypeFacade;
    @EJB
    MstStatusFacade mstStatusFacade;
    @EJB
    MstProposalFacade mstProposalFacade;
    @EJB
    TransProposalFacade transProposalFacade;
    @EJB
    ArticleValidateService odsArticleService;
    @EJB
    PromotionDAO promotionDao;
    @EJB
    private ProposalDownloadFileUtil proposalDownloadFileUtil;

    public ProposalDtlResp getProposalDtl(Long proposalID, boolean isArticleDtl) {
        logger.info("----- Inside getting proposal dtl ------" + proposalID);
        try {
            MstProposal proposal = mstProposalFacade.find(proposalID);
            ProposalVO vo = new ProposalVO();

            if (isArticleDtl) {
                Collection<TransProposal> list = proposal.getTransProposalCollection();
                if (list != null && list.size() > 0) {
                    List<ArticleMCVO> articleList = new ArrayList<ArticleMCVO>();
                    for (TransProposal articleMc : list) {
                        articleList.add(new ArticleMCVO(articleMc.getArtCode(), articleMc.getArtDesc(), articleMc.getMcCode(), articleMc.getMcDesc()));
                    }
                    vo.setArticleList(articleList);
                }
                return new ProposalDtlResp(new Resp(RespCode.SUCCESS, "Article List"), vo);
            }
            if (proposal.getMstDepartment() != null) {
                vo.setDeptId(proposal.getMstDepartment().getMstDeptId().toString());
                vo.setDepartment(proposal.getMstDepartment().getDeptName());
            }


            vo.setProblemTypeId(proposal.getMstProblem().getProblemTypeId().toString());
            vo.setProblemTypeDesc(proposal.getMstProblem().getProblemName());

            vo.setRemarks(proposal.getRemarks());

            vo.setSolutionDesc(proposal.getSolutionDesc());

            if (proposal.getMstPromotionType() != null) {
                vo.setPromoType(proposal.getMstPromotionType().getPromoTypeId().toString());
                vo.setPromotionTypeName(proposal.getMstPromotionType().getPromoTypeName());
            }
            if (proposal.getPromoDesc() != null) {
                vo.setPromoDesc(proposal.getPromoDesc());
            }
            if (proposal.getOtherFilePath() != null) {
                vo.setOtherFilePath(proposal.getOtherFilePath());
            } 

            return new ProposalDtlResp(new Resp(RespCode.SUCCESS, "Proposal List"), vo);
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new ProposalDtlResp(new Resp(RespCode.FAILURE, "Error :" + ex.getMessage()));
        }
    }

    public CreateProposalResp createManualProposal(ProposalVO request) {
        try {
            logger.info("------- Creating Manual Promotion Proposal Service -------");
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            System.out.println("------ Payload------ \n" + buffer);

            MstEmployee employee = employee = mstEmployeeFacade.find(Long.parseLong(request.getEmpId()));
            MstDepartment department = department = mstDeptartmentFacade.find(Long.parseLong(request.getDeptId()));
            MstProblem problem = mstProblemFacade.find(Long.parseLong(request.getProblemTypeId()));
            MstPromotionType promoType = mstPromoTypeFacade.find(Long.parseLong(request.getPromoType()));

            MstStatus status = null;
            if (request.getIsSaveDraft().equalsIgnoreCase("1")) {
                //Save As Draft Status
                status = mstStatusFacade.find(CommonStatus.PROPOSAL_DRAFT);
            } else {
                //Submit Status
                status = mstStatusFacade.find(CommonStatus.PROPOSAL_SUBMIT);
            }
            MstProposal proposal = null;
            if (request.getProEnum() == ProposalEnum.MANUAL_ENTRY) {
                proposal = new MstProposal();
                proposal = PromotionProposalUtil.getProposalCreation(proposal, employee, department, problem, promoType, status, request);

                //CR2 Change Other File For Proposal Creation
                if (request.getOtherFilePath() != null) {
                    proposal.setOtherFilePath(request.getOtherFilePath());
                }
                //cr2 change finished
                mstProposalFacade.create(proposal);
            }

            if (request.getProEnum() == ProposalEnum.MANUAL_ENTRY) {
                //Manual Entry Proposal Article Creation
                List<ArticleMCVO> list = request.getArticleList();
                if (list != null && list.size() > 0) {
                    TransProposal proposalTrns = null;
                    for (ArticleMCVO vo : list) {
                        proposalTrns = PromotionProposalUtil.getProposalTrns(proposal, vo);
                        transProposalFacade.create(proposalTrns);
                    }
                }
            }
            return new CreateProposalResp(new Resp(RespCode.SUCCESS, "Promotion proposal created successfully with id  P" + proposal.getProposalId() + "."));
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new CreateProposalResp(new Resp(RespCode.FAILURE, "Error :" + ex.getMessage()));
        }

    }

    public CreateProposalResp createUploadFileProposal(ProposalVO request) {
        try {
            logger.info("------- Creating Promotion Proposal Service -------");
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            System.out.println("------ Payload------ \n" + buffer);
            MstEmployee employee = null;
            MstDepartment department = null;
            MstProblem problem = null;
            MstPromotionType promoType = null;
            MstStatus status = null;
            MstProposal proposal = null;
            String message = "";

            employee = mstEmployeeFacade.find(Long.parseLong(request.getEmpId()));
            department = mstDeptartmentFacade.find(Long.parseLong(request.getDeptId()));
            problem = mstProblemFacade.find(Long.parseLong(request.getProblemTypeId()));
            promoType = mstPromoTypeFacade.find(Long.parseLong(request.getPromoType()));

            if (request.getProEnum() == ProposalEnum.UPLOAD_FILE) {

                if (request.getFileName() == null || request.getFilePath() == null) {
                    return (new CreateProposalResp(new Resp(RespCode.FAILURE, "Please upload file.")));
                }

                status = mstStatusFacade.find(CommonStatus.PROPOSAL_FILE_UNDER_PROCESS);

                /*ON Previous Error FIle we Checked Proposal ID
                 * If Proposal ID Exists then We Update The details of Previous error file
                 * Else We Create New Proposal With Assumption that New Proposal Request made from user
                 */
                if (request.getProposalId() != null && request.getProposalId().length() > 0) {
                    logger.info("--------- Inside Update Proposal.");
                    proposal = mstProposalFacade.find(Long.parseLong(request.getProposalId()));
                    PromotionProposalUtil.getProposalCreation(proposal, employee, department, problem, promoType, status, request);
                    message = "Promotion Proposal Updated Successfully with id P" + proposal.getProposalId();
                } else {
                    logger.info("--------- Inside Create Proposal.");
                    proposal = new MstProposal();
                    proposal = PromotionProposalUtil.getProposalCreation(proposal, employee, department, problem, promoType, status, request);
                    mstProposalFacade.create(proposal);
                    message = "Promotion Proposal Created Successfully with id P" + proposal.getProposalId();
                }

                /*Set Proposal File Path Once It is created
                 * Return Proposal Id and File Path IN Response
                 */
                String fileName = proposal.getProposalId() + "_" + employee.getEmpId() + "_" + request.getFileName();
                String filePath = request.getFilePath() + fileName;
                proposal.setFilePath(filePath);

                //CR2 Change Other File For Proposal Creation
                if (request.getOtherFilePath() != null) {
                    proposal.setOtherFilePath(request.getOtherFilePath());
                }
                //cr2 change finished
                return (new CreateProposalResp(new Resp(RespCode.SUCCESS, message, proposal.getProposalId()), filePath));

            } else if (request.getProEnum() == ProposalEnum.UPDATE_PROPOSAL_WITHOUT_FILE) {
                if (request.getProposalId() != null && request.getProposalId().length() > 0) {
                    logger.info("--------- Inside Update Proposal.");
                    proposal = mstProposalFacade.find(Long.parseLong(request.getProposalId()));

                    if (request.getIsSaveDraft().equalsIgnoreCase("1")) {
                        //Save As Draft Status
                        status = mstStatusFacade.find(CommonStatus.PROPOSAL_DRAFT);
                    } else {
                        //Submit Status
                        status = mstStatusFacade.find(CommonStatus.PROPOSAL_SUBMIT);
                    }
                    PromotionProposalUtil.getProposalCreation(proposal, employee, department, problem, promoType, status, request);
                    //CR2 Change Other File For Proposal Creation
                    if (request.getOtherFilePath() != null) {
                        proposal.setOtherFilePath(request.getOtherFilePath());
                    }
                    //cr2 change finished
                    message = "Promotion Proposal Updated Successfully with id P" + proposal.getProposalId();
                    return (new CreateProposalResp(new Resp(RespCode.SUCCESS, message, proposal.getProposalId())));
                } else {
                    return (new CreateProposalResp(new Resp(RespCode.FAILURE, "Proposal Not Found For ID P" + request.getProposalId())));
                }

            } else {
                return new CreateProposalResp(new Resp(RespCode.SUCCESS, "Promotion proposal updated successfully with id P" + proposal.getProposalId()));
            }


        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new CreateProposalResp(new Resp(RespCode.FAILURE, "Error :" + ex.getMessage()));
        }

    }

    public CreateProposalResp sendArticleFileForvalidate(Long proposalId, String filePath, String isSaveDraft) {
        try {
            logger.info("---------- Inside Send Article File For Validate Service------");
            logger.info("---Proposal Id : " + proposalId + " ----FilePath : " + filePath + " ----- IsSaveDraft : " + isSaveDraft);
            MstProposal proposal = mstProposalFacade.find(proposalId);
            CreateProposalResp validateResp = odsArticleService.validateProposalArticleListFromFile(proposal, filePath);
            MstStatus status = null;

            logger.info("-------- validate Article Resp Code : " + validateResp.getResp().getRespCode());

            if (validateResp.getResp().getRespCode() == RespCode.FAILURE) {
                status = mstStatusFacade.find(CommonStatus.PROPOSAL_FILE_FAILURE);
            } else {
                if (isSaveDraft.equalsIgnoreCase("1")) {
                    //Save As Draft Status
                    status = mstStatusFacade.find(CommonStatus.PROPOSAL_DRAFT);
                } else {
                    //Submit Status
                    status = mstStatusFacade.find(CommonStatus.PROPOSAL_SUBMIT);
                }
            }
            proposal.setMstStatus(status);
            return (validateResp);
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new CreateProposalResp(new Resp(RespCode.FAILURE, "Error :" + ex.getMessage()));
        }
    }

    public ErrorFileProposalResp getLastErrorFileProposalDtl(String empId) {
        try {
            logger.info("--------- Inside Getting Error File Proposal Dtl --------- " + empId);

            MstProposal proposal = promotionDao.getFileFailureProposalByEmp(empId);
            if (proposal == null) {
                return (new ErrorFileProposalResp(new Resp(RespCode.SUCCESS, "No Error File Record Found.")));
            }

            String fileName = "Request_" + proposal.getProposalId() + "_ArticleCode_MCCode_Status_File.csv";
            String errorFileServerPath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ERROR_FILE);
            String filePath = errorFileServerPath + fileName;
            ProposalVO vo = new ProposalVO();

            vo.setProposalId(proposal.getProposalId().toString());
            vo.setDeptId(proposal.getMstDepartment().getMstDeptId().toString());
            vo.setProblemTypeId(proposal.getMstProblem().getProblemTypeId().toString());
            vo.setRemarks(proposal.getRemarks());
            vo.setSolutionDesc(proposal.getSolutionDesc());
            vo.setPromoType(proposal.getMstPromotionType().getPromoTypeId().toString());
            vo.setPromoDesc(proposal.getPromoDesc());

            vo.setErrorFilePath(filePath);

            return (new ErrorFileProposalResp(new Resp(RespCode.SUCCESS, "Proposal Dtl."), vo));

        } catch (Exception ex) {
            ex.printStackTrace();
            return (new ErrorFileProposalResp(new Resp(RespCode.FAILURE, "Error : " + ex.getMessage())));
        }
    }

    public Resp updateProposalStatusToSubmit(String proposalId) {
        try {
            logger.info("--------- Inside updating Drafted Proposal Status To Submit..");
            MstProposal proposal = mstProposalFacade.find(Long.valueOf(proposalId));
            MstStatus status = mstStatusFacade.find(CommonStatus.PROPOSAL_SUBMIT);
            proposal.setMstStatus(status);
            return (new Resp(RespCode.SUCCESS, "Proposal Submitted with id : " + proposalId));
        } catch (Exception ex) {
            logger.fatal("Error : " + ex.getMessage());
            ex.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }

    }

    public Resp updateProposalStatusToAcceptReject(List<AcceptRejectProposalVO> proposalList) {
        try {
            logger.info("--------- Inside updating  Proposal Status To Accept/Reject.....");
            boolean isAccept = true;
            for (AcceptRejectProposalVO vo : proposalList) {
                logger.info("--- is Accept Flag : " + vo.isIsAccept());
                MstProposal proposal = mstProposalFacade.find(Long.valueOf(vo.getProposalId()));
                MstStatus status = null;
                if (vo.getRemarks() != null) {
                    proposal.setInitiatorRemarks(vo.getRemarks());
                }
                proposal.setUpdatedDate(new Date());
                if (vo.isIsAccept()) {
                    status = mstStatusFacade.find(CommonStatus.PROPOSAL_ACCEPTED);
                    proposal.setMstStatus(status);

                } else {
                    isAccept = false;
                    status = mstStatusFacade.find(CommonStatus.PROPOSAL_REJECTED);
                    proposal.setMstStatus(status);

                }
            }
            if (isAccept) {
                return (new Resp(RespCode.SUCCESS, "Selected Proposals Are Accepted Successfully."));
            } else {
                return (new Resp(RespCode.SUCCESS, "Selected Proposals Are Rejected Successfully."));
            }
        } catch (Exception ex) {
            logger.fatal("Error : " + ex.getMessage());
            ex.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }
    }

    public Resp downloadMultipleProposalDtl(List<Long> transReqIdList, Long empId) {
        logger.info(" --- Downloading Trans Promotion Information --- ");
        List<MstProposal> ProposalList = new ArrayList<MstProposal>();
        Long tempReqId = 0l;
        if (transReqIdList != null && transReqIdList.size() > 0) {
            for (Long subPromoId : transReqIdList) {
                MstProposal promo = mstProposalFacade.find(subPromoId);
                if (promo == null) {
                    tempReqId = subPromoId;
                    break;
                }
                ProposalList.add(promo);
            }
        }
        if (tempReqId != 0) {
            return new Resp(RespCode.FAILURE, "Request No : R" + tempReqId + " Not Found.");
        }
        if (ProposalList.isEmpty()) {
            return new Resp(RespCode.FAILURE, "No Proposal Detail Found.");
        }

        String downloadPath = proposalDownloadFileUtil.downloadMultipleProposalFile(ProposalList, empId);
        if (downloadPath == null) {
            return new Resp(RespCode.FAILURE, "No Proposal Detail Found.");
        }
        return new Resp(RespCode.SUCCESS, downloadPath);
    }
}
