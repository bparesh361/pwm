/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
public class ExcelUtil {

    public static void fillCell(int rowNum, int colNum, String headerName, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

//        dataRow = hssfSheet.createRow(rowNum);

        cell = getCell(dataRow, colNum);
        if (cell == null) {
            cell = dataRow.createCell(colNum);
        }
        cell.setCellValue(headerName);
        cell.setCellStyle(cellStyle);
    }

    public static void fillCellWithNewRow(int rowNum, int colNum, String headerName, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

        dataRow = hssfSheet.createRow(rowNum);

        cell = getCell(dataRow, colNum);
        if (cell == null) {
            cell = dataRow.createCell(colNum);
        }
        cell.setCellValue(headerName);
        cell.setCellStyle(cellStyle);
    }

    public static XSSFCell getCell(XSSFRow row, int index) {
        XSSFCell cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }
        return cell;
    }

    public static void resetColumnSize(XSSFSheet hssfSheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            hssfSheet.autoSizeColumn(i);
        }
    }

    public static XSSFFont getHeaderFont(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
        font.setBoldweight(XSSFFont.DEFAULT_FONT_SIZE);
        font.setBold(true);
        return font;
    }

    public static XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook, XSSFFont font) {
        XSSFCellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        styleHeader.setFont(font);
        return styleHeader;
    }

    public static XSSFCellStyle getCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleCenter.setWrapText(true);
        styleCenter.setAlignment(HorizontalAlignment.CENTER);
        styleCenter.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        return styleCenter;
    }
}
