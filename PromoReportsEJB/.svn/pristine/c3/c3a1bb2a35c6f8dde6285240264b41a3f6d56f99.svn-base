/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.excel;

import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstReport;
import com.fks.promo.entity.MstRole;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoStatus;
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
 * @author ajitn
 */
@Stateless
@LocalBean
public class PromoTeamDashboardReport {

    public static final int TICKET_REQUEST_NO = 0;
    public static final int ASSIGNED_DATE = 1;
    public static final int PROMO_STATUS = 2;
    public static final int LAST_UPDATED_DATE = 3;
    public static final int PROMO_START_DATE = 4;
    public static final int PROMO_END_DATE = 5;
    public static final int EXECUTOR_NAME = 6;
    public static final int LOCATION = 7;
    public static final int CATEGORY = 8;
    public static final int SUB_CATEGORY = 9;
    public static final int EVENT = 10;
    public static final int APPROVED_BY = 11;
    public static final int PROMO_ZONES = 12;
    public static final int PROMO_BONUS_BUY = 13;
    public static final int PROMO_DETAIL = 14;

    public String generateReport(List<TransPromo> transPromoList, MstReport report) {
        String filePath = null;
        try {
            String fileName = "PromoTeamDashboardReport_" + ReportCommonUtil.getCurrentTimeInMiliSeconds() + ".xlsx";
            filePath = PropertyReportUtil.getPropertyString(ReportTypeEnum.PROMO_TEAM_DASHBOARD_RPT) + fileName;


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheetName = workbook.createSheet("Promo_TeamDashboard_Report");
            XSSFRow dataRow = null;
            XSSFCell dataCell = null;

            XSSFFont font = ExcelUtil.getHeaderFont(workbook);
            XSSFCellStyle styleHeader = ExcelUtil.getHeaderStyle(workbook, font);
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(workbook);

            //set column width
            sheetName.setColumnWidth(CATEGORY, 10000);
            sheetName.setColumnWidth(SUB_CATEGORY, 10000);
            sheetName.setColumnWidth(PROMO_ZONES, 10000);

            XSSFRow newrow0 = sheetName.createRow(0);
            XSSFCell dataCell0 = ExcelUtil.getCell(newrow0, 0);
            dataCell0.setCellValue("Report : Promo TeamDashboard Report");
            dataCell0.setCellStyle(styleHeader);

            int rowNum = 2;

            generateReportHeader(rowNum, dataRow, dataCell, sheetName, styleHeader);

            rowNum++;
            generateReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, transPromoList, report);

            //Resizing Columns
            for (int i = TICKET_REQUEST_NO; i <= LOCATION; i++) {
                sheetName.autoSizeColumn(i);
            }
            for (int i = EVENT; i <= APPROVED_BY; i++) {
                sheetName.autoSizeColumn(i);
            }

            for (int i = PROMO_BONUS_BUY; i <= PROMO_DETAIL; i++) {
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
            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            temprowNum = rowNum;

            //Table Details
            MstPromo masterPromo = subPromo.getMstPromo();
            Collection<MapPromoZone> zoneList = masterPromo.getMapPromoZoneCollection();

            //Column Request No
            String requestNo = "T" + masterPromo.getPromoId() + "-R" + subPromo.getTransPromoId();
            ExcelUtil.fillCell(rowNum, TICKET_REQUEST_NO, requestNo, dataRow, cell, sheetName, cellStyle);

            if (subPromo.getTransPromoStatusCollection() != null && subPromo.getTransPromoStatusCollection().size() > 0) {
                for (TransPromoStatus staus : subPromo.getTransPromoStatusCollection()) {
                    MstRole role = staus.getMstEmployee().getMstRole();
                    if (role.getMapRoleProfileCollection() != null && role.getMapRoleProfileCollection().size() > 0) {
                        for (MapRoleProfile profileRole : role.getMapRoleProfileCollection()) {
                            if (profileRole.getMstProfile().getProfileName().equalsIgnoreCase("F5")) {
                                String initiationDate = ReportCommonUtil.dispayFileDateFormat(staus.getUpdatedDate());
                                ExcelUtil.fillCell(rowNum, ASSIGNED_DATE, initiationDate, dataRow, cell, sheetName, cellStyle);
                            }
                        }
                    }
                }
            }

//            else {
//                if (subPromo.getUpdatedDate() != null) {
//                    String initiationDate = ReportCommonUtil.dispayFileDateFormat(subPromo.getUpdatedDate());
//                    ExcelUtil.fillCell(rowNum, ASSIGNED_DATE, initiationDate, dataRow, cell, sheetName, cellStyle);
//                }
//            }


            if (subPromo.getMstStatus() != null) {
                String currentStatus = subPromo.getMstStatus().getStatusDesc();
                ExcelUtil.fillCell(rowNum, PROMO_STATUS, currentStatus, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getUpdatedDate() != null) {
                String updatedDate = ReportCommonUtil.dispayFileDateFormat(subPromo.getUpdatedDate());
                ExcelUtil.fillCell(rowNum, LAST_UPDATED_DATE, updatedDate, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getValidFrom() != null) {
                String validFrom = ReportCommonUtil.dispayFileDateFormat(subPromo.getValidFrom());
                ExcelUtil.fillCell(rowNum, PROMO_START_DATE, validFrom, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getValidTo() != null) {
                String validTo = ReportCommonUtil.dispayFileDateFormat(subPromo.getValidTo());
                ExcelUtil.fillCell(rowNum, PROMO_END_DATE, validTo, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getMstEmployee3() != null) {
                String executorName = subPromo.getMstEmployee3().getEmployeeName();
                ExcelUtil.fillCell(rowNum, EXECUTOR_NAME, executorName, dataRow, cell, sheetName, cellStyle);
            }

            String location = subPromo.getZoneName();
            ExcelUtil.fillCell(rowNum, LOCATION, location, dataRow, cell, sheetName, cellStyle);

            if (masterPromo.getCategory() != null) {
                String category = masterPromo.getCategory();
                ExcelUtil.fillCell(rowNum, CATEGORY, category, dataRow, cell, sheetName, cellStyle);
            }

            if (masterPromo.getSubCategory() != null) {
                String subCategory = masterPromo.getSubCategory();
                ExcelUtil.fillCell(rowNum, SUB_CATEGORY, subCategory, dataRow, cell, sheetName, cellStyle);
            }

            if (masterPromo.getMstEvent() != null) {
                String eventName = masterPromo.getMstEvent().getEventName();
                ExcelUtil.fillCell(rowNum, EVENT, eventName, dataRow, cell, sheetName, cellStyle);
            }

            String approverName = "";
            if (subPromo.getMstEmployee() != null) {
                approverName = subPromo.getMstEmployee().getEmployeeName();
            }
            if (subPromo.getMstEmployee1() != null) {
                approverName = subPromo.getMstEmployee1().getEmployeeName();
            }
            if (subPromo.getMstEmployee2() != null) {
                approverName = subPromo.getMstEmployee2().getEmployeeName();
            }
            ExcelUtil.fillCell(rowNum, APPROVED_BY, approverName, dataRow, cell, sheetName, cellStyle);

            if (subPromo.getBonusBuy() != null) {
                String bonusBuy = subPromo.getBonusBuy();
                ExcelUtil.fillCell(rowNum, PROMO_BONUS_BUY, bonusBuy, dataRow, cell, sheetName, cellStyle);
            }

            if (subPromo.getPromoDetails() != null) {
                String promoDetail = subPromo.getPromoDetails();
                ExcelUtil.fillCell(rowNum, PROMO_DETAIL, promoDetail, dataRow, cell, sheetName, cellStyle);
            }

            //Column Zone
            StringBuilder sbZone = new StringBuilder("");
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
//                    if (temprowNum != rowNum) {
//                        dataRow = sheetName.createRow(rowNum);
//                    }
                    String zoneDesc = zone.getMstZone().getZoneName();
                    sbZone.append(zoneDesc).append(",");
                    //ExcelUtil.fillCell(rowNum, PROMO_ZONES, zoneDesc, dataRow, cell, sheetName, cellStyle);
//                    if (zoneList.size() > 1) {
//                        rowNum++;
//                    }
                }
                String zone = sbZone.substring(0, sbZone.lastIndexOf(","));
                ExcelUtil.fillCell(rowNum, PROMO_ZONES, zone, dataRow, cell, sheetName, cellStyle);
            }

            rowNum++;
        }


    }

    private void generateReportHeader(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

        dataRow = hssfSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, TICKET_REQUEST_NO, "Ticket-Request No", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, ASSIGNED_DATE, "Assigned Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_STATUS, "Promo Status", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, LAST_UPDATED_DATE, "Last Updated Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_START_DATE, "Promo Start Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_END_DATE, "Promo End Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, EXECUTOR_NAME, "Executor Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, LOCATION, "Location", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, CATEGORY, "Category", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, SUB_CATEGORY, "Sub Category", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, EVENT, "Campaign", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, APPROVED_BY, "Approved By", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_ZONES, "Promo For Zones", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_BONUS_BUY, "Bonus Buy Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_DETAIL, "Promo Detail", dataRow, cell, hssfSheet, cellStyle);
    }
}
