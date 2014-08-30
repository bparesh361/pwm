/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.excel;

import com.fks.promo.entity.MstReport;
import com.fks.promo.entity.TransTask;
import com.fks.reports.common.PropertyReportUtil;
import com.fks.reports.common.ReportCommonUtil;
import com.fks.reports.vo.ReportTypeEnum;
import java.io.File;
import java.io.FileOutputStream;
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
public class InternalTaskReport {

    public static final int TASK_NO = 0;
    public static final int TASK_ASSIGN_DATE = 1;
    public static final int TASK_ASSIGNER_NAME = 2;
    public static final int TASK_ASSIGNER_LOCATION = 3;
    public static final int TASK_ASSIGN_TO_NAME = 4;
    public static final int TASK_ASSIGN_TO_LOCATION = 5;
    public static final int DATE_CLOSURE = 6;
    public static final int TASK_TYPE = 7;
    public static final int PROMO_COUNT = 8;
    public static final int REMARKS = 9;

    public String generateReport(List<TransTask> taskList, MstReport report) {
        String filePath = null;
        try {
            String fileName = "InternalTaskReport_" + ReportCommonUtil.getCurrentTimeInMiliSeconds() + ".xlsx";
            filePath = PropertyReportUtil.getPropertyString(ReportTypeEnum.INTERNAL_TASK_RPT) + fileName;


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheetName = workbook.createSheet("Internal_Task_Report");
            XSSFRow dataRow = null;
            XSSFCell dataCell = null;

            XSSFFont font = ExcelUtil.getHeaderFont(workbook);
            XSSFCellStyle styleHeader = ExcelUtil.getHeaderStyle(workbook, font);
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(workbook);

            XSSFRow newrow0 = sheetName.createRow(0);
            XSSFCell dataCell0 = ExcelUtil.getCell(newrow0, 0);
            dataCell0.setCellValue("Report : Internal Task Report");
            dataCell0.setCellStyle(styleHeader);

            int rowNum = 2;

            generateReportHeader(rowNum, dataRow, dataCell, sheetName, styleHeader);

            rowNum++;
            generateReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, taskList, report);

            //Resizing Columns
            for (int i = TASK_NO; i <= PROMO_COUNT; i++) {
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

    private void generateReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, List<TransTask> taskList, MstReport report) {

        for (TransTask task : taskList) {

            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            String taskNo = "TR-" + task.getTransTaskId();
            ExcelUtil.fillCell(rowNum, TASK_NO, taskNo, dataRow, cell, sheetName, cellStyle);

            if (task.getCreatedTime() != null) {
                String createDate = ReportCommonUtil.dispayFileDateFormat(task.getCreatedTime());
                ExcelUtil.fillCell(rowNum, TASK_ASSIGN_DATE, createDate, dataRow, cell, sheetName, cellStyle);
            }

            if (task.getCreatedBy() != null) {
                String assignerName = task.getCreatedBy().getEmployeeName();
                ExcelUtil.fillCell(rowNum, TASK_ASSIGNER_NAME, assignerName, dataRow, cell, sheetName, cellStyle);

                String assignerLocation = task.getCreatedBy().getMstStore().getMstZone().getZoneName();
                ExcelUtil.fillCell(rowNum, TASK_ASSIGNER_LOCATION, assignerLocation, dataRow, cell, sheetName, cellStyle);
            }

            if (task.getAssignedTo() != null) {
                String assignToName = task.getAssignedTo().getEmployeeName();
                ExcelUtil.fillCell(rowNum, TASK_ASSIGN_TO_NAME, assignToName, dataRow, cell, sheetName, cellStyle);

                String assignToLocation = task.getAssignedTo().getMstStore().getMstZone().getZoneName();
                ExcelUtil.fillCell(rowNum, TASK_ASSIGN_TO_LOCATION, assignToLocation, dataRow, cell, sheetName, cellStyle);
            }

            if (task.getMstStatus() != null) {
                if (task.getMstStatus().getStatusId().equals(53l)) {
                    String closureDate = ReportCommonUtil.dispayFileDateFormat(task.getUpdatedTime());
                    ExcelUtil.fillCell(rowNum, DATE_CLOSURE, closureDate, dataRow, cell, sheetName, cellStyle);
                }
            }

            if (task.getMstTask() != null) {
                String taskType = task.getMstTask().getTaskName();
                ExcelUtil.fillCell(rowNum, TASK_TYPE, taskType, dataRow, cell, sheetName, cellStyle);
            }

            Integer promoCount = task.getPromoCount();
            if (promoCount != null) {
                ExcelUtil.fillCell(rowNum, PROMO_COUNT, promoCount.toString(), dataRow, cell, sheetName, cellStyle);
            }

            String reamrks = task.getRemarks();
            if (reamrks != null) {
                ExcelUtil.fillCell(rowNum, REMARKS, reamrks, dataRow, cell, sheetName, cellStyle);
            }

            rowNum++;
        }


    }

    private void generateReportHeader(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

        dataRow = hssfSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, TASK_NO, "Task No", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, TASK_ASSIGN_DATE, "Task Assign Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, TASK_ASSIGNER_NAME, "Task Assigner Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, TASK_ASSIGNER_LOCATION, "Task Assigner Location", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, TASK_ASSIGN_TO_NAME, "Task Assign To Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, TASK_ASSIGN_TO_LOCATION, "Task Assign To Location", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, DATE_CLOSURE, "Closure Date", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, TASK_TYPE, "Task Type", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_COUNT, "Promo Count", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, REMARKS, "Remarks", dataRow, cell, hssfSheet, cellStyle);
    }
}
