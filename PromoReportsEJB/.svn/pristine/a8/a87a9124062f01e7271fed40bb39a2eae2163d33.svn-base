/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.excel;

import com.fks.promo.entity.MapPromoFormat;
import com.fks.promo.entity.MapPromoStore;
import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstReport;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.reports.common.PropertyReportUtil;
import com.fks.reports.common.ReportCommonUtil;
import com.fks.reports.vo.ReportTypeEnum;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;
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
 * @author javals
 */
@Stateless
@LocalBean
public class VendorBackedOfferReport {
    
    public static final int TICKET_REQUEST_NO = 0;
    public static final int INITIATION_DATE = 1;
    public static final int INITIATOR_NAME = 2;
    public static final int LOCATION = 3;
    public static final int REQUEST_NAME = 4;
    public static final int CAMPAIGN_NAME = 5;
    public static final int OBJECTIVE = 6;
    public static final int MARKETTING_TYPE = 7;
    public static final int CATEGORY = 8;
    public static final int SUB_CATEGORY = 9;
    public static final int PROMO_TYPE = 10;
    public static final int OFFER_DETAIL_INTIATOR = 11;
    public static final int APPROVAL_DATE = 12;
    public static final int APPROVAL_NAME = 13;
    public static final int BUSINESS_EXIGENCY_FLAG = 14;
    public static final int PROMO_VALIDITY_FROM = 15;
    public static final int PROMO_VALIDITY_TO = 16;
    public static final int PROMO_EXECUTOR = 17;
    public static final int PROMO_BONUS_BUY = 18;
    public static final int PROMO_DETAIL_EXEC = 19;
    public static final int ARTICLE = 20;
    public static final int MC_CODE = 21;
    public static final int MC_DESC = 22;
    public static final int BRAND_DESC = 23;
    public static final int PROMO_STATUS = 24;
    public static final int VENDOR_BACKED = 25;
    
    public String generateReport(List<TransPromo> transPromoList, MstReport report) {
        String filePath = null;
        try {
            String fileName = "VendorBackedOfferReport_" + ReportCommonUtil.getCurrentTimeInMiliSeconds() + ".xlsx";
            filePath = PropertyReportUtil.getPropertyString(ReportTypeEnum.PROMOTION_LIFE_CYCLE_ARTICLE_MC_RPT) + fileName;


            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheetName = workbook.createSheet("Vendor_Backed_Offer_RPT");
            XSSFRow dataRow = null;
            XSSFCell dataCell = null;

            XSSFFont font = ExcelUtil.getHeaderFont(workbook);
            XSSFCellStyle styleHeader = ExcelUtil.getHeaderStyle(workbook, font);
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(workbook);

            //set column width
            sheetName.setColumnWidth(CATEGORY, 10000);
            sheetName.setColumnWidth(SUB_CATEGORY, 10000);


            XSSFRow newrow0 = sheetName.createRow(0);
            XSSFCell dataCell0 = ExcelUtil.getCell(newrow0, 0);
            dataCell0.setCellValue("Report : Vendor Backed Offer Report");
            dataCell0.setCellStyle(styleHeader);

            int rowNum = 2;

            generateReportHeader(rowNum, dataRow, dataCell, sheetName, styleHeader);

            rowNum++;
            generateReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, transPromoList, report);

            //Resizing Columns
            for (int i = TICKET_REQUEST_NO; i <= MARKETTING_TYPE; i++) {
                sheetName.autoSizeColumn(i);
            }
            for (int i = PROMO_TYPE; i <= PROMO_STATUS; i++) {
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
    
    private void generateReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, List<TransPromo> transPromoList, MstReport report) {
        int temprowNum = 0;
        for (TransPromo subPromo : transPromoList) {
            // System.out.println("%%%%%%%%%% Row Num : " + rowNum);
            //Table Details
            MstPromo masterPromo = subPromo.getMstPromo();
            Collection<MapPromoZone> zoneList = masterPromo.getMapPromoZoneCollection();
            Collection<MapPromoStore> storeList = masterPromo.getMapPromoStoreCollection();
            Collection<MapPromoFormat> formatList = masterPromo.getMapPromoFormatCollection();
            Collection<TransPromoArticle> articleList = subPromo.getTransPromoArticleCollection();
            List<TransPromoConfig> configList = (List<TransPromoConfig>) subPromo.getTransPromoConfigCollection();

            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            temprowNum = rowNum;

            //Column Request No
            String requestNo = "T" + masterPromo.getPromoId() + "-R" + subPromo.getTransPromoId();
            ExcelUtil.fillCell(rowNum, TICKET_REQUEST_NO, requestNo, dataRow, cell, sheetName, cellStyle);

            if (masterPromo.getUpdatedDate() != null) {
                String initiationDate = ReportCommonUtil.dispayFileDateFormat(masterPromo.getUpdatedDate());
                ExcelUtil.fillCell(rowNum, INITIATION_DATE, initiationDate, dataRow, cell, sheetName, cellStyle);
            }

            if (masterPromo.getMstEmployee() != null) {
                String initiationName = masterPromo.getMstEmployee().getEmployeeName();
                ExcelUtil.fillCell(rowNum, INITIATOR_NAME, initiationName, dataRow, cell, sheetName, cellStyle);

                String location = masterPromo.getMstEmployee().getMstStore().getMstZone().getZoneName();
                ExcelUtil.fillCell(rowNum, LOCATION, location, dataRow, cell, sheetName, cellStyle);
            }

            if (masterPromo.getRequestName() != null) {
                String requestName = masterPromo.getRequestName().toString();
                ExcelUtil.fillCell(rowNum, REQUEST_NAME, requestName, dataRow, cell, sheetName, cellStyle);
            }
            if (masterPromo.getMstEvent() != null) {
                String campaignName = masterPromo.getMstEvent().getEventName().toString();
                ExcelUtil.fillCell(rowNum, CAMPAIGN_NAME, campaignName, dataRow, cell, sheetName, cellStyle);
            }
            if (masterPromo.getMstCampaign() != null) {
                String objective = masterPromo.getMstCampaign().getCampaignName().toString();
                ExcelUtil.fillCell(rowNum, OBJECTIVE, objective, dataRow, cell, sheetName, cellStyle);
            }
            if (masterPromo.getMstMktg() != null) {
                String markettingType = masterPromo.getMstMktg().getMktgName().toString();
                ExcelUtil.fillCell(rowNum, MARKETTING_TYPE, markettingType, dataRow, cell, sheetName, cellStyle);
            }
            if (masterPromo.getCategory() != null) {
                String category = masterPromo.getCategory();
                ExcelUtil.fillCell(rowNum, CATEGORY, category, dataRow, cell, sheetName, cellStyle);
            }

            if (masterPromo.getSubCategory() != null) {
                String subCategory = masterPromo.getSubCategory();
                ExcelUtil.fillCell(rowNum, SUB_CATEGORY, subCategory, dataRow, cell, sheetName, cellStyle);
            }
            if (subPromo.getMstPromotionType() != null) {
                String promotionType = subPromo.getMstPromotionType().getPromoTypeName();
                ExcelUtil.fillCell(rowNum, PROMO_TYPE, promotionType, dataRow, cell, sheetName, cellStyle);
            }
            if (subPromo.getRemarks() != null) {
                String remarks = subPromo.getRemarks();
                ExcelUtil.fillCell(rowNum, OFFER_DETAIL_INTIATOR, remarks, dataRow, cell, sheetName, cellStyle);
            }


//            if (subPromo.getMstEmployee3() != null) {
//                String approvalName = subPromo.getMstEmployee3().getEmployeeName();
//                ExcelUtil.fillCell(rowNum, APPROVAL_NAME, approvalName, dataRow, cell, sheetName, cellStyle);
//            }

            if (subPromo.getMstEmployee() != null) {
                String approvalName = subPromo.getMstEmployee().getEmployeeName();
                ExcelUtil.fillCell(rowNum, APPROVAL_NAME, approvalName, dataRow, cell, sheetName, cellStyle);

                if (subPromo.getUpdatedDate() != null) {
                    String approvalDate = ReportCommonUtil.dispayFileDateFormat(subPromo.getUpdatedDate());
                    ExcelUtil.fillCell(rowNum, APPROVAL_DATE, approvalDate, dataRow, cell, sheetName, cellStyle);
                }
            } else if (subPromo.getMstEmployee1() != null) {
                String approvalName = subPromo.getMstEmployee1().getEmployeeName();
                ExcelUtil.fillCell(rowNum, APPROVAL_NAME, approvalName, dataRow, cell, sheetName, cellStyle);

                if (subPromo.getUpdatedDate() != null) {
                    String approvalDate = ReportCommonUtil.dispayFileDateFormat(subPromo.getUpdatedDate());
                    ExcelUtil.fillCell(rowNum, APPROVAL_DATE, approvalDate, dataRow, cell, sheetName, cellStyle);
                }
            } else if (subPromo.getMstEmployee2() != null) {
                String approvalName = subPromo.getMstEmployee2().getEmployeeName();
                ExcelUtil.fillCell(rowNum, APPROVAL_NAME, approvalName, dataRow, cell, sheetName, cellStyle);

                if (subPromo.getUpdatedDate() != null) {
                    String approvalDate = ReportCommonUtil.dispayFileDateFormat(subPromo.getUpdatedDate());
                    ExcelUtil.fillCell(rowNum, APPROVAL_DATE, approvalDate, dataRow, cell, sheetName, cellStyle);
                }
            }

            if (subPromo.getMstEmployee2() != null) {
                ExcelUtil.fillCell(rowNum, BUSINESS_EXIGENCY_FLAG, "Yes", dataRow, cell, sheetName, cellStyle);
            } else {
                ExcelUtil.fillCell(rowNum, BUSINESS_EXIGENCY_FLAG, "No", dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getValidFrom() != null) {
                String validFrom = ReportCommonUtil.dispayFileDateFormat(subPromo.getValidFrom());
                ExcelUtil.fillCell(rowNum, PROMO_VALIDITY_FROM, validFrom, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getValidTo() != null) {
                String validTo = ReportCommonUtil.dispayFileDateFormat(subPromo.getValidTo());
                ExcelUtil.fillCell(rowNum, PROMO_VALIDITY_TO, validTo, dataRow, cell, sheetName, cellStyle);
            }


            if (subPromo.getMstEmployee3() != null) {
                String executiveName = subPromo.getMstEmployee3().getEmployeeName();
                ExcelUtil.fillCell(rowNum, PROMO_EXECUTOR, executiveName, dataRow, cell, sheetName, cellStyle);
            }

            String bonusBuy = subPromo.getBonusBuy();
            if (bonusBuy != null) {
                ExcelUtil.fillCell(rowNum, PROMO_BONUS_BUY, bonusBuy, dataRow, cell, sheetName, cellStyle);
            }
            if (subPromo.getPromoDetails() != null) {
                String promoDetail = subPromo.getPromoDetails();
                ExcelUtil.fillCell(rowNum, PROMO_DETAIL_EXEC, promoDetail, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getMstStatus() != null) {
                String currentStatus = subPromo.getMstStatus().getStatusDesc();
                ExcelUtil.fillCell(rowNum, PROMO_STATUS, currentStatus, dataRow, cell, sheetName, cellStyle);
            }
            
            if(masterPromo.getVendorBacked() != null){
                String vendorBacked = masterPromo.getVendorBacked();
                ExcelUtil.fillCell(rowNum, PROMO_STATUS, vendorBacked, dataRow, cell, sheetName, cellStyle);
            }

            //Article MC Loop Starts
            if (articleList != null && articleList.size() > 0) {
                for (TransPromoArticle promoArticle : articleList) {
                    if (temprowNum != rowNum) {
                        dataRow = sheetName.createRow(rowNum);
                    }
                    String article = promoArticle.getArticle();
                    String mcCode = promoArticle.getMcCode();
                    String mcDesc = promoArticle.getMcDesc();
                    String brandDesc = promoArticle.getBrandDesc();

                    if (article != null) {
                        ExcelUtil.fillCell(rowNum, ARTICLE, article, dataRow, cell, sheetName, cellStyle);
                    }
                    if (mcCode != null) {
                        ExcelUtil.fillCell(rowNum, MC_CODE, mcCode, dataRow, cell, sheetName, cellStyle);
                    }
                    if (mcDesc != null) {
                        ExcelUtil.fillCell(rowNum, MC_DESC, mcDesc, dataRow, cell, sheetName, cellStyle);
                    }
                    if (brandDesc != null) {
                        ExcelUtil.fillCell(rowNum, BRAND_DESC, brandDesc, dataRow, cell, sheetName, cellStyle);
                    }
//                    if (articleList.size() > 1) {
                    rowNum++;
//                    }
                }
            } else {
                rowNum++;
            }



        }
    }
    
    private void generateReportHeader(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

        dataRow = hssfSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, TICKET_REQUEST_NO, "Ticket-Request No", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, INITIATION_DATE, "Initiation Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, INITIATOR_NAME, "Initiator Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, LOCATION, "Location", dataRow, cell, hssfSheet, cellStyle);

        ExcelUtil.fillCell(rowNum, REQUEST_NAME, "Request Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, CAMPAIGN_NAME, "Campaign Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, OBJECTIVE, "Objective", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, MARKETTING_TYPE, "Marketing Type", dataRow, cell, hssfSheet, cellStyle);

        ExcelUtil.fillCell(rowNum, CATEGORY, "Category", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, SUB_CATEGORY, "Sub Category", dataRow, cell, hssfSheet, cellStyle);

        ExcelUtil.fillCell(rowNum, PROMO_TYPE, "Promo Type", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, OFFER_DETAIL_INTIATOR, "Offer Detail(Initiator)", dataRow, cell, hssfSheet, cellStyle);

        ExcelUtil.fillCell(rowNum, APPROVAL_DATE, "Approval Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, APPROVAL_NAME, "Approver Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, BUSINESS_EXIGENCY_FLAG, "Business Exigency Flag", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_VALIDITY_FROM, "Promo Validity From", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_VALIDITY_TO, "Promo Validity To", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_EXECUTOR, "Promo Executor", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_BONUS_BUY, "Promo Bonus Buy", dataRow, cell, hssfSheet, cellStyle);

        ExcelUtil.fillCell(rowNum, PROMO_DETAIL_EXEC, "Promo Detail", dataRow, cell, hssfSheet, cellStyle);

        ExcelUtil.fillCell(rowNum, ARTICLE, "Article", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, MC_CODE, "MC Code", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, MC_DESC, "MC Desc", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, BRAND_DESC, "Brand Desc", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_STATUS, "Promo Status", dataRow, cell, hssfSheet, cellStyle);
        
        ExcelUtil.fillCell(rowNum, VENDOR_BACKED, "Vendor Backed", dataRow, cell, hssfSheet, cellStyle);
        
    }
    
}
