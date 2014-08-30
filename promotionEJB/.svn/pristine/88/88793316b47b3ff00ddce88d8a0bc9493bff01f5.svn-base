/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service.util;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransProposal;
import com.fks.promotion.vo.ArticleMCVO;
import com.fks.promotion.vo.ProposalEnum;
import com.fks.promotion.vo.ProposalSearchType;
import com.fks.promotion.vo.ProposalVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
public class PromotionProposalUtil {

    private static Logger logger = Logger.getLogger(PromotionProposalUtil.class);

    public static MstProposal getProposalCreation(MstProposal proposal, MstEmployee employee, MstDepartment department, MstProblem problem, MstPromotionType promoType, MstStatus status, ProposalVO request) {

        if (status != null) {
            proposal.setMstStatus(status);
        }
        if (department != null) {
            proposal.setMstDepartment(department);
        }
        if (employee != null) {
            proposal.setMstEmployee(employee);
        }
        if (problem != null) {
            proposal.setMstProblem(problem);
        }
        if (promoType != null) {
            proposal.setMstPromotionType(promoType);
        }
        if (request.getPromoDesc() != null) {
            proposal.setPromoDesc(request.getPromoDesc());
        }
        if (request.getRemarks() != null) {
            proposal.setRemarks(request.getRemarks());
        }
        if (request.getSolutionDesc() != null) {
            proposal.setSolutionDesc(request.getSolutionDesc());
        }
        if (request.getProEnum() == ProposalEnum.MANUAL_ENTRY || request.getProEnum() == ProposalEnum.UPLOAD_FILE) {
            proposal.setCreatedDate(new Date());
        }

        if (request.getZoneId() != null) {
            proposal.setZoneId(request.getZoneId());
        }

        return proposal;
    }

    public static TransProposal getProposalTrns(MstProposal proposal, ArticleMCVO vo) {
        TransProposal proposalTrns = new TransProposal();
        proposalTrns.setArtCode(vo.getArticleCode());
        proposalTrns.setArtDesc(vo.getArticleDesc());
        proposalTrns.setMcCode(vo.getMcCode());
        proposalTrns.setMcDesc(vo.getMcDesc());
        proposalTrns.setMstProposal(proposal);
        return proposalTrns;
    }

    public static List<ProposalVO> getProposalVOList(List<MstProposal> proposalList, ProposalSearchType type) {
        logger.info("Converting Proposal Vo : Search By " + type);
        List<ProposalVO> list = new ArrayList<ProposalVO>();
        for (MstProposal proposal : proposalList) {
            ProposalVO vo = new ProposalVO();
            vo.setProposalId(String.valueOf(proposal.getProposalId()));
            vo.setDate(CommonUtil.getDBDateformat(proposal.getCreatedDate()));
            vo.setSiteCode(proposal.getMstEmployee().getMstStore().getMstStoreId());
            vo.setSiteDesc(proposal.getMstEmployee().getMstStore().getSiteDescription());
            vo.setEmpId(proposal.getMstEmployee().getEmpCode().toString());
            vo.setUserName(proposal.getMstEmployee().getEmployeeName());
            vo.setContactNo(proposal.getMstEmployee().getContactNo().toString());
            if (proposal.getMstDepartment() != null) {
                vo.setDepartment(proposal.getMstDepartment().getDeptName());
            } else {
                vo.setDepartment("-");
            }
            vo.setProblemTypeDesc(proposal.getMstProblem().getProblemName());
            if (proposal.getMstPromotionType() != null) {
                vo.setPromotionTypeName(proposal.getMstPromotionType().getPromoTypeName());
            } else {
                vo.setPromotionTypeName("-");
            }
            vo.setRemarks(proposal.getRemarks());
            vo.setStatus(proposal.getMstStatus().getStatusDesc());
            vo.setStatusId(proposal.getMstStatus().getStatusId().toString());


            if (proposal.getMstPromotionType() != null) {
                vo.setPromoDesc(proposal.getMstPromotionType().getPromoTypeName());
            }

            if(proposal.getInitiatorRemarks()!=null){
                vo.setInitiatorRemarks(proposal.getInitiatorRemarks().trim());
            }else{
                vo.setInitiatorRemarks("-");
            }


            if (type.equals(ProposalSearchType.ID)) {
                logger.info("Fetching Trans Proposal Data" + proposal.getTransProposalCollection());
                List<ArticleMCVO> articleList = new ArrayList<ArticleMCVO>();
                if (proposal.getTransProposalCollection() != null) {
                    for (TransProposal transProposal : proposal.getTransProposalCollection()) {
                        ArticleMCVO articleMCVO = new ArticleMCVO(transProposal.getArtCode(), transProposal.getArtDesc(), transProposal.getMcCode(), transProposal.getMcDesc());
                        articleList.add(articleMCVO);
                    }
                }
                vo.setArticleList(articleList);
            }

            if(proposal.getOtherFilePath()!=null){
                vo.setOtherFilePath(proposal.getOtherFilePath());
            }
            list.add(vo);
        }
        return list;
    }
}
