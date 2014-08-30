/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service.util;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.entity.Mch;

import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.TransProposal;
import com.fks.promo.facade.MchFacade;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author krutij
 */
@Stateless
public class ProposalDownloadFileUtil {

    @EJB
    private MchFacade mchfacade;

    public String downloadMultipleProposalFile(List<MstProposal> mstProposal, Long empId) {
        String filePath = null;
        WritableWorkbook book = null;
        try {
            String fileName = empId + "_Proposals_" + CommonUtil.getCurrentTimeInMiliSeconds() + ".xls";
            filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.MULTIPLE_PROPOSALS_DOWNLOAD_FILE) + fileName;
            book = getBlankWorkbook(filePath);
            WritableSheet sheet = book.getSheet(0);

            int promoRowCount = 1;
            int tempPromoRowCount = 1;

            for (MstProposal proposal : mstProposal) {

                //Restore Promo Row Count In Temp Promo Row Count For Sub Promo Index
                tempPromoRowCount = promoRowCount;
                Collection<TransProposal> proposalArticleList = proposal.getTransProposalCollection();
                //table details
                //Proposal Id
                String requestNo = "P" + proposal.getProposalId();
                //Req Date
                String reqDate;
                if (proposal.getCreatedDate() != null) {
                    reqDate = CommonUtil.dispayFileDateFormat(proposal.getCreatedDate());
                } else {
                    reqDate = "-";
                }
                String storeid, storename;
                if (proposal.getMstEmployee().getMstStore() != null) {
                    storeid = proposal.getMstEmployee().getMstStore().getMstStoreId();
                    storename = proposal.getMstEmployee().getMstStore().getSiteDescription();
                } else {
                    storeid = "-";
                    storename = "-";
                }
                //Req Name
                String empName, contactNo, empCode;
                if (proposal.getMstEmployee() != null) {
                    empName = proposal.getMstEmployee().getEmployeeName();
                    contactNo = proposal.getMstEmployee().getContactNo().toString();
                    empCode = proposal.getMstEmployee().getEmpCode().toString();
                } else {
                    empName = "-";
                    contactNo = "-";
                    empCode = "-";
                }

                // problem type
                String problemType;
                if (proposal.getMstProblem() != null) {
                    problemType = proposal.getMstProblem().getProblemName();
                } else {
                    problemType = "-";
                }

                //promo type
                String promoType;
                if (proposal.getMstPromotionType() != null) {
                    promoType = proposal.getMstPromotionType().getPromoTypeName();
                } else {
                    promoType = "-";
                }

                //status
                String status;
                if (proposal.getMstStatus() != null) {
                    status = proposal.getMstStatus().getStatusDesc();
                } else {
                    status = "-";
                }
                //remrks
                String remarks;
                if (proposal.getRemarks() != null) {
                    remarks = proposal.getRemarks();
                } else {
                    remarks = "-";
                }
                //PROBLEM DETAIL
                String problemDetail;
                if(proposal.getPromoDesc()!=null){
                    problemDetail= proposal.getPromoDesc();
                }else{
                    problemDetail= "-";
                }

                //SOLUTION DETAIL
                String solutionDesc;
                if(proposal.getSolutionDesc()!=null){
                    solutionDesc= proposal.getSolutionDesc();
                }else{
                    solutionDesc="-";
                }

                if (proposalArticleList != null && proposalArticleList.size() > 0) {
                    for (TransProposal article : proposalArticleList) {
                        sheet.addCell(new Label(DownloadProposalFileConstants.REQUEST_NO, promoRowCount, requestNo));
                        sheet.addCell(new Label(DownloadProposalFileConstants.REQUEST_DATE, promoRowCount, reqDate));
                        //site code
                        sheet.addCell(new Label(DownloadProposalFileConstants.SIE_CODE, promoRowCount, storeid));
                        //site Desc
                        sheet.addCell(new Label(DownloadProposalFileConstants.SITE_DESC, promoRowCount, storename));
                        sheet.addCell(new Label(DownloadProposalFileConstants.REQUESTOR_NAME, promoRowCount, empName));
                        sheet.addCell(new Label(DownloadProposalFileConstants.CONTACT_NO, promoRowCount, contactNo));
                        sheet.addCell(new Label(DownloadProposalFileConstants.EMP_CODE, promoRowCount, empCode));

                        sheet.addCell(new Label(DownloadProposalFileConstants.PROBLEM_TYPE, promoRowCount, problemType));
                        sheet.addCell(new Label(DownloadProposalFileConstants.PROMO_TYPE, promoRowCount, promoType));
                        sheet.addCell(new Label(DownloadProposalFileConstants.STATUS, promoRowCount, status));

                        String articleCode = article.getArtCode();
                        String articleDesc = article.getArtDesc();
                        String mcCode = article.getMcCode();
                        String mcDesc = article.getMcDesc();
                        String category;
                        if (mcCode != null) {
                            Mch mch = mchfacade.find(mcCode);
                            if (mch != null) {
                                category = mch.getCategoryName();
                            } else {
                                category = "-";
                            }
                        } else {
                            category = "-";
                        }

                        sheet.addCell(new Label(DownloadProposalFileConstants.CATEGORY, promoRowCount, category));
                        if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                            sheet.addCell(new jxl.write.Number(DownloadProposalFileConstants.ARTICLE, promoRowCount, Double.parseDouble(articleCode)));
                        }
                        sheet.addCell(new Label(DownloadProposalFileConstants.ARTICLE_DESC, promoRowCount, articleDesc));
                        sheet.addCell(new jxl.write.Number(DownloadProposalFileConstants.MC, promoRowCount, Double.parseDouble(mcCode)));
                        sheet.addCell(new Label(DownloadProposalFileConstants.MC_DESC, promoRowCount, mcDesc));

                        sheet.addCell(new Label(DownloadProposalFileConstants.REMARKS, promoRowCount, remarks));
                        sheet.addCell(new Label(DownloadProposalFileConstants.PROBLEM_DETAIL, promoRowCount, problemDetail));
                        sheet.addCell(new Label(DownloadProposalFileConstants.SOLUTION_DETAIL, promoRowCount, solutionDesc));
                        promoRowCount++;
                    }
                }


            }
            book.write();
        } catch (Exception ex) {
            System.out.println("------- error : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(ProposalDownloadFileUtil.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WriteException ex) {
                    java.util.logging.Logger.getLogger(ProposalDownloadFileUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        return filePath;
    }

    private static WritableWorkbook getBlankWorkbook(String filePath) throws IOException, WriteException {
        File errorFile = new File(filePath);

        WritableWorkbook downloadBook = Workbook.createWorkbook(errorFile);
        WritableSheet sheet = downloadBook.createSheet("Promotion Detail", 0);
        sheet.addCell(new Label(DownloadProposalFileConstants.REQUEST_NO, 0, "Proposal ID"));
        sheet.addCell(new Label(DownloadProposalFileConstants.REQUEST_DATE, 0, "Proposal Date"));
        sheet.addCell(new Label(DownloadProposalFileConstants.SIE_CODE, 0, "Site Code"));
        sheet.addCell(new Label(DownloadProposalFileConstants.SITE_DESC, 0, "Site Description"));
        sheet.addCell(new Label(DownloadProposalFileConstants.REQUESTOR_NAME, 0, "Requestor Name"));
        sheet.addCell(new Label(DownloadProposalFileConstants.CONTACT_NO, 0, "Contact No"));
        sheet.addCell(new Label(DownloadProposalFileConstants.EMP_CODE, 0, "Emp Code"));
        sheet.addCell(new Label(DownloadProposalFileConstants.CATEGORY, 0, "Department"));
        sheet.addCell(new Label(DownloadProposalFileConstants.PROBLEM_TYPE, 0, "Problem Type"));

        sheet.addCell(new Label(DownloadProposalFileConstants.PROMO_TYPE, 0, "Promo Type"));
        sheet.addCell(new Label(DownloadProposalFileConstants.STATUS, 0, "Status"));
        sheet.addCell(new Label(DownloadProposalFileConstants.ARTICLE, 0, "Article Code"));
        sheet.addCell(new Label(DownloadProposalFileConstants.ARTICLE_DESC, 0, "Article Description"));
        sheet.addCell(new Label(DownloadProposalFileConstants.MC, 0, "MC Code"));
        sheet.addCell(new Label(DownloadProposalFileConstants.MC_DESC, 0, "MC Description"));
             sheet.addCell(new Label(DownloadProposalFileConstants.PROBLEM_DETAIL, 0, "Promo Description"));
        sheet.addCell(new Label(DownloadProposalFileConstants.SOLUTION_DETAIL, 0, "Solution Description"));
        sheet.addCell(new Label(DownloadProposalFileConstants.REMARKS, 0, "Remarks"));


        sheet.setColumnView(DownloadProposalFileConstants.REQUEST_NO, 15);
        sheet.setColumnView(DownloadProposalFileConstants.REQUEST_DATE, 15);
        sheet.setColumnView(DownloadProposalFileConstants.SIE_CODE, 20);
        sheet.setColumnView(DownloadProposalFileConstants.SITE_DESC, 20);
        sheet.setColumnView(DownloadProposalFileConstants.REQUESTOR_NAME, 20);
        sheet.setColumnView(DownloadProposalFileConstants.CONTACT_NO, 11);
        sheet.setColumnView(DownloadProposalFileConstants.EMP_CODE, 10);
        sheet.setColumnView(DownloadProposalFileConstants.CATEGORY, 20);
        sheet.setColumnView(DownloadProposalFileConstants.PROBLEM_TYPE, 20);
        sheet.setColumnView(DownloadProposalFileConstants.PROMO_TYPE, 15);
        sheet.setColumnView(DownloadProposalFileConstants.STATUS, 20);
        sheet.setColumnView(DownloadProposalFileConstants.ARTICLE, 15);
        sheet.setColumnView(DownloadProposalFileConstants.ARTICLE_DESC, 15);
        sheet.setColumnView(DownloadProposalFileConstants.MC, 5);
        sheet.setColumnView(DownloadProposalFileConstants.MC_DESC, 15);
        sheet.setColumnView(DownloadProposalFileConstants.PROBLEM_DETAIL, 35);
        sheet.setColumnView(DownloadProposalFileConstants.SOLUTION_DETAIL, 35);
        sheet.setColumnView(DownloadProposalFileConstants.REMARKS, 30);


        return downloadBook;
    }
}
