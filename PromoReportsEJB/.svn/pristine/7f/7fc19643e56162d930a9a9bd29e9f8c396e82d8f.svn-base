/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.excel;

import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.MstReport;
import com.fks.promo.entity.TransProposal;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.vo.ProposalReportVO;
import com.fks.reports.common.PropertyReportUtil;
import com.fks.reports.common.ReportCommonUtil;
import com.fks.reports.vo.ReportTypeEnum;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ajitn
 */
@Stateless
@LocalBean
public class ProposalArticleMcReport {

    @EJB
    private MchFacade mchfacade;
    public static final int PROPOSAL_NO = 0;
    public static final int PROPOSAL_CREATION_DATE = 1;
    public static final int PROPOSAL_CREATION_STORE = 2;
    public static final int PROPOSAL_CREATOR_NAME = 3;
    public static final int PROPOSAL_CREATOR_NUMBER = 4;
    public static final int PROBLEM_TYPE = 5;
    public static final int SELECTED_DEPARTMENT = 6;
    public static final int PROPOSAL_CATEGORY = 7;
    public static final int PROPOSAL_SUB_CATEGORY = 8;
    public static final int PROPOSAL_STATUS = 9;
    public static final int INITIATOR_REMARKS = 10;
    public static final int ZONAL_FEC_NAME = 11;
    public static final int PROPOSAL_UPDATED_DATE = 12;
    public static final int REMARKS = 13;
    public static final int SOLUTION_DESCRIPTION = 14;
    public static final int ARTICLE = 15;
    public static final int MC_CODE = 16;
    public static final int MC_DESC = 17;

    public String generateReport(List<ProposalReportVO> proposalList, MstReport report) {
        String filePath = null;
        try {
            String fileName = "ProposalArticleMCReport_" + ReportCommonUtil.getCurrentTimeInMiliSeconds() + ".xlsx";
            filePath = PropertyReportUtil.getPropertyString(ReportTypeEnum.STORE_PROPOSAL_RPT) + fileName;


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheetName = workbook.createSheet("Proposal_Article_MC_Report");
            XSSFRow dataRow = null;
            XSSFCell dataCell = null;

            XSSFFont font = ExcelUtil.getHeaderFont(workbook);
            XSSFCellStyle styleHeader = ExcelUtil.getHeaderStyle(workbook, font);
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(workbook);

            //set column width
            sheetName.setColumnWidth(PROPOSAL_CATEGORY, 10000);
            sheetName.setColumnWidth(PROPOSAL_SUB_CATEGORY, 10000);

            XSSFRow newrow0 = sheetName.createRow(0);
            XSSFCell dataCell0 = ExcelUtil.getCell(newrow0, 0);
            dataCell0.setCellValue("Report : Proposal Article MC Report");
            dataCell0.setCellStyle(styleHeader);

            int rowNum = 2;

            generateReportHeader(rowNum, dataRow, dataCell, sheetName, styleHeader);

            rowNum++;
            generateReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, proposalList, report);

            //Resizing Columns
            for (int i = PROPOSAL_NO; i <= SELECTED_DEPARTMENT; i++) {
                sheetName.autoSizeColumn(i);
            }

            for (int i = PROPOSAL_STATUS; i <= PROPOSAL_UPDATED_DATE; i++) {
                sheetName.autoSizeColumn(i);
            }

            for (int i = ARTICLE; i <= MC_DESC; i++) {
                sheetName.autoSizeColumn(i);
            }
            File tempFileLoc = new File(filePath);
            FileOutputStream foStream = new FileOutputStream(tempFileLoc);
            workbook.write(foStream);
            foStream.close();

        } catch (Exception ex) {
            System.out.println("------- error : " + ex.getMessage());
            ex.printStackTrace();
        }
        return filePath;
    }

    private void generateReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, List<ProposalReportVO> proposalList, MstReport report) {

        int temprowNum = 0;
        for (ProposalReportVO vo : proposalList) {
            //Table Details
            MstProposal proposal = vo.getProposal();
            Collection<TransProposal> articleList = proposal.getTransProposalCollection();
            String initiatorNames = vo.getZonalInitiatorName();

            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            temprowNum = rowNum;

            String requestNo = "P" + proposal.getProposalId();
            ExcelUtil.fillCell(rowNum, PROPOSAL_NO, requestNo, dataRow, cell, sheetName, cellStyle);

            if (proposal.getCreatedDate() != null) {
                String createDate = ReportCommonUtil.dispayFileDateFormat(proposal.getCreatedDate());
                ExcelUtil.fillCell(rowNum, PROPOSAL_CREATION_DATE, createDate, dataRow, cell, sheetName, cellStyle);
            }

            if (proposal.getMstEmployee() != null) {
                String storeCodeName = proposal.getMstEmployee().getMstStore().getMstStoreId() + "-" + proposal.getMstEmployee().getMstStore().getSiteDescription();
                ExcelUtil.fillCell(rowNum, PROPOSAL_CREATION_STORE, storeCodeName, dataRow, cell, sheetName, cellStyle);

                String proposerName = proposal.getMstEmployee().getEmployeeName();
                ExcelUtil.fillCell(rowNum, PROPOSAL_CREATOR_NAME, proposerName, dataRow, cell, sheetName, cellStyle);

                String proposerContactNo = proposal.getMstEmployee().getContactNo().toString();
                ExcelUtil.fillCell(rowNum, PROPOSAL_CREATOR_NUMBER, proposerContactNo, dataRow, cell, sheetName, cellStyle);
            }

            if (proposal.getMstProblem() != null) {
                String problemType = proposal.getMstProblem().getProblemName();
                ExcelUtil.fillCell(rowNum, PROBLEM_TYPE, problemType, dataRow, cell, sheetName, cellStyle);
            }

            if (proposal.getMstDepartment() != null) {
                String selectedDeparment = proposal.getMstDepartment().getDeptName();
                ExcelUtil.fillCell(rowNum, SELECTED_DEPARTMENT, selectedDeparment, dataRow, cell, sheetName, cellStyle);
            }

            if (proposal.getMstStatus() != null) {
                String status = proposal.getMstStatus().getStatusDesc();
                ExcelUtil.fillCell(rowNum, PROPOSAL_STATUS, status, dataRow, cell, sheetName, cellStyle);
            }

            ExcelUtil.fillCell(rowNum, ZONAL_FEC_NAME, initiatorNames, dataRow, cell, sheetName, cellStyle);

            if (proposal.getUpdatedDate() != null) {
                String updateDate = ReportCommonUtil.dispayFileDateFormat(proposal.getUpdatedDate());
                ExcelUtil.fillCell(rowNum, PROPOSAL_UPDATED_DATE, updateDate, dataRow, cell, sheetName, cellStyle);
            }

            String initiatorRemarks = proposal.getInitiatorRemarks();
            if (initiatorRemarks != null) {
                ExcelUtil.fillCell(rowNum, INITIATOR_REMARKS, initiatorRemarks, dataRow, cell, sheetName, cellStyle);
            }
            String remarks = proposal.getRemarks();
            if (remarks != null) {
                ExcelUtil.fillCell(rowNum, REMARKS, remarks, dataRow, cell, sheetName, cellStyle);
            }

            if (proposal.getSolutionDesc() != null) {
                String solutionDesc = proposal.getSolutionDesc();
                ExcelUtil.fillCell(rowNum, SOLUTION_DESCRIPTION, solutionDesc, dataRow, cell, sheetName, cellStyle);
            }

            Set<String> categorySet = new HashSet<String>();
            Set<String> subCategorySet = new HashSet<String>();
            StringBuilder sbCategory = new StringBuilder("");
            StringBuilder sbSubCategory = new StringBuilder("");

            //Article MC Loop Starts            
            for (TransProposal proposalArticle : articleList) {

                if (temprowNum != rowNum) {
                    dataRow = sheetName.createRow(rowNum);
                }
                String article = proposalArticle.getArtCode();
                String mcCode = proposalArticle.getMcCode();
                String mcDesc = proposalArticle.getMcDesc();

                if (article != null) {
                    ExcelUtil.fillCell(rowNum, ARTICLE, article, dataRow, cell, sheetName, cellStyle);
                }
                if (mcCode != null) {
                    ExcelUtil.fillCell(rowNum, MC_CODE, mcCode, dataRow, cell, sheetName, cellStyle);
                }
                if (mcDesc != null) {
                    ExcelUtil.fillCell(rowNum, MC_DESC, mcDesc, dataRow, cell, sheetName, cellStyle);
                }

                if (mcCode != null) {
                    Mch mch = mchfacade.find(mcCode);
                    if (mch != null && categorySet.add(mch.getCategoryName())) {
                        sbCategory.append(mch.getCategoryName()).append(",");
                    }
                    if (mch != null && subCategorySet.add(mch.getSubCategoryName())) {
                        sbSubCategory.append(mch.getSubCategoryName()).append(",");
                    }
                }

                if (articleList.size() > 1) {
                    rowNum++;
                }

            }

            if (articleList.size() > 1) {
                rowNum--;
            }


            //get the proposal row 
            dataRow = sheetName.getRow(temprowNum);
            String categoryList = "";
            if (sbCategory.length() > 0 && (sbCategory.toString().contains(","))) {
                categoryList = sbCategory.substring(0, sbCategory.lastIndexOf(","));
            }
            ExcelUtil.fillCell(temprowNum, PROPOSAL_CATEGORY, categoryList, dataRow, cell, sheetName, cellStyle);

            String subCategoryList = "";
            if (sbSubCategory.length() > 0 && (sbSubCategory.toString().contains(","))) {
                subCategoryList = sbSubCategory.substring(0, sbSubCategory.lastIndexOf(","));
            }
            ExcelUtil.fillCell(temprowNum, PROPOSAL_SUB_CATEGORY, subCategoryList, dataRow, cell, sheetName, cellStyle);

            rowNum++;
        }
    }

    private void generateReportHeader(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

        dataRow = hssfSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, PROPOSAL_NO, "Proposal No", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_CREATION_DATE, "Proposal Creation Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_CREATION_STORE, "Proposal Creation Store", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_CREATOR_NAME, "Proposal Creator Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_CREATOR_NUMBER, "Proposer Contact Number", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROBLEM_TYPE, "Problem Type", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, SELECTED_DEPARTMENT, "Department", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_CATEGORY, "Category", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_SUB_CATEGORY, "Sub Category", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_STATUS, "Proposal Status", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, INITIATOR_REMARKS, "Initiator Remarks", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, ZONAL_FEC_NAME, "Zonal Fec Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROPOSAL_UPDATED_DATE, "Updated Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, REMARKS, "Store Remarks", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, SOLUTION_DESCRIPTION, "Solution Description", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, ARTICLE, "Article", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, MC_CODE, "MC Code", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, MC_DESC, "MC Desc", dataRow, cell, hssfSheet, cellStyle);
    }
}
