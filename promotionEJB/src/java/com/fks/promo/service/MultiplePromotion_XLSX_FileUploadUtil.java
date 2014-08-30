/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.ods.service.ODSService;
import com.fks.promo.common.ExcelUtil;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.init.vo.CreateMultiPlePromoReq;
import com.fks.promo.init.vo.TransPromoArticleVO;
import com.fks.promo.init.vo.TransPromoConfigVO;
import com.fks.promo.init.vo.TransPromoVO;
import com.fks.promo.master.service.OtherMasterService;
import com.fks.promo.master.vo.ValidateMCResp;
import com.fks.promotion.vo.ValidateArticleMCVO;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ajitn
 */
@Stateless
public class MultiplePromotion_XLSX_FileUploadUtil {

    @EJB
    ODSService odsService;
    @EJB
    OtherMasterService otherService;
    @EJB
    MultiplePromotionFileUploadUtil xlsFileUtility;
    private static final Logger logger = Logger.getLogger(MultiplePromotion_XLSX_FileUploadUtil.class.getName());
    private static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
    private static SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd");
    private static int STATUS = 0;
    private static int STATUS_MESSAGE = 1;
    private static int REQUEST_NO = 2;
    private static int PROMO_TYPE = 3;
    private static int REMARKS = 4;
    private static int EXPECTED_MARGIN = 5;
    private static int EXPTECTED_SALES_QTY_GROWTH = 6;
    private static int EXPTECTED_SALES_VALUE_GROWTH = 7;
    private static int GROWTH_IN_TICKET_SIZE = 8;
    private static int GROWTH_IN_CONVERSIONS = 9;
    private static int VALID_FROM = 10;
    private static int VALID_TO = 11;
    private static int SET_NO = 3;
    private static int ARTICLE = 4;
    private static int MC = 5;
    private static int CONFIG_SET = 6;
    private static int QTY = 7;
    private static int QUALIFYING_AMT = 8;
    private static int DISCOUNT_TYPE = 9;
    private static int DISCOUNT_VALUE = 10;

    public CreateMultiPlePromoReq validateFile(String filePath, MstPromo mstPromo, String zoneId, String empId) {
        CreateMultiPlePromoReq req = new CreateMultiPlePromoReq();
        try {
            //generating error workbook
            String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.SUB_PROMO_REQUEST_FILE);

            Calendar cal = new GregorianCalendar();
            String errorFileName = errorFilePath + "/" + "status_T-" + mstPromo.getPromoId() + "_" + cal.getTimeInMillis() + ".xlsx";

            XSSFWorkbook errorWorkBook = new XSSFWorkbook();
            XSSFSheet errorHeaderSheet = errorWorkBook.createSheet("HeaderSheet");
            XSSFSheet errorDetailSheet = errorWorkBook.createSheet("DetailSheet");

            int errorBookRowNum = 0;
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(errorWorkBook);
            XSSFCellStyle headerStyle = ExcelUtil.getCellStyle(errorWorkBook);
            generateErrorBookHeader(errorBookRowNum, null, errorHeaderSheet, errorDetailSheet, headerStyle);

            //read and validate xlsx file 
            XSSFWorkbook wb = new XSSFWorkbook(filePath);
            XSSFSheet headerSheet = wb.getSheetAt(0);

            errorBookRowNum++;
            ValidatePromoHeaderFileDtl validateHeader = validateHeaderSheet(headerSheet, errorHeaderSheet, styleCenter, errorBookRowNum);

            if (validateHeader.isStatus()) {

                XSSFRow row = null;
                XSSFCell cell = null;
                ExcelUtil.fillCell(1, STATUS, "Success", row, cell, errorHeaderSheet, styleCenter);
                ExcelUtil.fillCell(1, STATUS_MESSAGE, "Success", row, cell, errorHeaderSheet, styleCenter);

                System.out.println("------------------- Header File Validated Successfully.");
                XSSFSheet detailSheet = wb.getSheetAt(1);
                errorBookRowNum = 1;
                if (validateDetailSheet(detailSheet, errorDetailSheet, styleCenter, validateHeader.getRequestList(), mstPromo.getPromoId(), errorBookRowNum)) {
                    req.setMstPromoId(mstPromo.getPromoId().toString());
                    req.setZoneId(zoneId);
                    req.setEmpId(empId);

                    ValidatePromoHeaderFileDtl headerFile = getPromoRequestList(headerSheet);
                    List<TransPromoVO> trasPromoList = getPromoArticleList(detailSheet, headerFile.getRequestNoData(), mstPromo.getPromoId());
                    req.setTransPromoVO(trasPromoList);
                    req.setErrorFlag(false);
                    req.setErrorPath(errorFileName);
                } else {
                    req.setErrorFlag(true);
                    req.setErrorPath(errorFileName);
                }

            } else {
                req.setErrorFlag(true);
                req.setErrorPath(errorFileName);
            }

            // write error workbook
            File tempFileLoc = new File(errorFileName);
            FileOutputStream foStream = new FileOutputStream(tempFileLoc);
            errorWorkBook.write(foStream);
            foStream.close();


        } catch (Exception ex) {
            logger.error("------------- Exception In Processing validateFile() MultiPromoFile : " + ex.getMessage());
            ex.printStackTrace();
            req.setErrorFlag(true);
        }
        return req;
    }

    private ValidatePromoHeaderFileDtl validateHeaderSheet(XSSFSheet headerSheet, XSSFSheet headerErrorSheet, XSSFCellStyle cellStyle, int errorBookRowNum) {
        boolean finalSuccessFlag = true;
        boolean successFlag = true;
        ValidatePromoHeaderFileDtl resp = new ValidatePromoHeaderFileDtl();
        try {
            XSSFRow row = null;
            XSSFCell cell = null;

            int maxRows = headerSheet.getPhysicalNumberOfRows() - 1;
            if (maxRows <= 0) {
                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, "Empty Header Sheet Found.", row, cell, headerErrorSheet, cellStyle);
                successFlag = false;
                resp.setStatus(false);
                return resp;
            }

            StringBuilder errorMessage = new StringBuilder("");
            Date fromDate = null;
            Long fromDateTime = null;
            Date toDate = null;
            Long toDateTime = null;
            Long currentDateTime = (new Date()).getTime();
            double tempValue = 0;
            Hashtable<String, Integer> requestList = new Hashtable<String, Integer>();


            //row loop
            Iterator rows = headerSheet.rowIterator();
            boolean isFirstRow = true;
            int errorRowCount = 1;
            int rowCount = 0;
            while (rows.hasNext()) {

                rowCount++;

                row = (XSSFRow) rows.next();

                //Skip First Header Row
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }


//                int maxCols = row.getPhysicalNumberOfCells();
//                int maxCols = row.getLastCellNum();
//                if (maxCols < 10) {
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, "Total No Of Columns Required Is 10.", row, cell, headerErrorSheet, cellStyle);
//                    successFlag = false;
//                    resp.setStatus(false);
//                    return resp;
//                }

                if (errorMessage.length() > 0) {
                    errorMessage.delete(0, errorMessage.length());
                }

                Iterator cells = row.cellIterator();

                String requestNo = "";
                String promoType = "";
                String remarks = "";
                String marginAchievement = "";
                String sellThruVsQty = "";
                String saleGrowthInQtyValue = "";
                String growthTicketSize = "";
                String growthInConversion = "";
                String validFrom = "";
                String validTo = "";

                // Column Loop
                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        // Request No Column
                        case 0:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Request No should not be blank.");
                                successFlag = false;
                            } else {
                                requestNo = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, REQUEST_NO, requestNo, row, cell, headerErrorSheet, cellStyle);
                            }
                            break;
                        // Promo Type Column
                        case 1:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Promo Type should not be blank.");
                                successFlag = false;
                            } else {
                                promoType = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, PROMO_TYPE, promoType, row, cell, headerErrorSheet, cellStyle);

                                int promoTypeId = getTransPromoType(promoType);
                                if (promoTypeId == 0) {
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Invalid Promotion Type Found.");
                                    successFlag = false;
                                }
                                requestList.put(requestNo, promoTypeId);
                                System.out.println("---- Promo Type Validated Successfully.");
                            }
                            break;
                        // Remarks Column
                        case 2:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Remarks should not be blank.");
                                successFlag = false;
                            } else {
                                remarks = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, REMARKS, remarks, row, cell, headerErrorSheet, cellStyle);
                                if (remarks.length() > 200) {
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }

                                    errorMessage.append("Remarks should not be more than 200 characters.");
                                    successFlag = false;
                                }

                                System.out.println("---- Remarks Validated Successfully.");
                            }
                            break;
                        // Expected Margin Column
                        case 3:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Expected Margin should not be blank.");
                                successFlag = false;
                            } else {
                                marginAchievement = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, EXPECTED_MARGIN, marginAchievement, row, cell, headerErrorSheet, cellStyle);
                                try {
                                    tempValue = Double.parseDouble(marginAchievement);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    errorMessage.append("Expected Margin Must Be Numeric.");
                                    successFlag = false;
                                }
                            }
                            break;
                        // Expected Sales Qty Growth Column
                        case 4:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Expected Sales Qty growth should not be blank.");
                                successFlag = false;
                            } else {
                                sellThruVsQty = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, EXPTECTED_SALES_QTY_GROWTH, sellThruVsQty, row, cell, headerErrorSheet, cellStyle);
                                try {
                                    tempValue = Double.parseDouble(sellThruVsQty);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    errorMessage.append("Expected Sales Qty growth Must Be Numeric.");
                                    successFlag = false;
                                }
                            }
                            break;
                        // Expected Sales Value Growth Column
                        case 5:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Expected Sales value growth should not be blank.");
                                successFlag = false;
                            } else {
                                saleGrowthInQtyValue = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, EXPTECTED_SALES_VALUE_GROWTH, saleGrowthInQtyValue, row, cell, headerErrorSheet, cellStyle);
                                try {
                                    tempValue = Double.parseDouble(saleGrowthInQtyValue);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    errorMessage.append("Expected Sales value growth Must Be Numeric.");
                                    successFlag = false;
                                }
                            }
                            break;
                        // Growth In Ticket Size Column
                        case 6:
                            if (isBlankCell(cell)) {
                            } else {
                                growthTicketSize = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, GROWTH_IN_TICKET_SIZE, growthTicketSize, row, cell, headerErrorSheet, cellStyle);
                                try {
                                    tempValue = Double.parseDouble(growthTicketSize);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    errorMessage.append("Growth in Ticket size Must Be Numeric.");
                                    successFlag = false;
                                }
                            }
                            break;
                        // Growth In Conversions Column
                        case 7:
                            if (isBlankCell(cell)) {
                            } else {
                                growthInConversion = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, GROWTH_IN_CONVERSIONS, growthInConversion, row, cell, headerErrorSheet, cellStyle);
                                try {
                                    tempValue = Double.parseDouble(growthInConversion);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
//                                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                    errorMessage.append("Growth in conversions Must Be Numeric.");
                                    successFlag = false;
                                }
                            }
                            break;
                        // Valid From Column
                        case 8:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Valid From should not be blank.");
                                successFlag = false;
                            } else {
                                try {
                                    validFrom = new SimpleDateFormat("dd-MMM-yyyy").format(cell.getDateCellValue());
//                                    ExcelUtil.fillCell(errorBookRowNum, VALID_FROM, validFrom, row, cell, headerErrorSheet, cellStyle);
                                    String[] dateParts;
                                    if (validFrom.contains("/")) {
                                        dateParts = validFrom.split("/");
                                    } else {
                                        dateParts = validFrom.split("-");
                                    }
                                    if (dateParts != null) {
                                        if (dateParts.length != 3) {
//                                            ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                            if (errorMessage.length() > 0) {
                                                errorMessage.append(" | ");
                                            }
                                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                                            successFlag = false;
                                        } else if (dateParts[2].length() != 4) {
//                                            ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                            if (errorMessage.length() > 0) {
                                                errorMessage.append(" | ");
                                            }
                                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                                            successFlag = false;
                                        } else {
                                            Resp dateResp = valdiateDate(dateParts[0], dateParts[1], dateParts[2], "Valid From");
                                            if (dateResp.getRespCode() == RespCode.FAILURE) {
//                                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                                if (errorMessage.length() > 0) {
                                                    errorMessage.append(" | ");
                                                }
                                                errorMessage.append(dateResp.getMsg());
                                                successFlag = false;
                                            }
                                        }
                                    } else {
//                                        ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                        if (errorMessage.length() > 0) {
                                            errorMessage.append(" | ");
                                        }
                                        errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                                        successFlag = false;
                                    }
                                    if (successFlag) {
                                        try {
                                            validFrom = validFrom.replaceAll("/", "-");
                                            fromDate = format.parse(validFrom);
                                            fromDateTime = fromDate.getTime();

                                            if (currentDateTime > fromDateTime) {
                                                if (errorMessage.length() > 0) {
                                                    errorMessage.append(" | ");
                                                }
//                                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                                errorMessage.append("Valid From Date Must Be Greater Than Today's Date.");
                                                successFlag = false;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            if (errorMessage.length() > 0) {
                                                errorMessage.append(" | ");
                                            }
//                                            ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                            errorMessage.append("Incorrect Date Format for Valid From.");
                                            successFlag = false;
                                        }
                                    }
                                } catch (IllegalStateException ex) {
                                    ex.printStackTrace();
                                    errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                                    successFlag = false;
                                }

                            }
                            break;
                        // Valid To Column
                        case 9:
                            if (isBlankCell(cell)) {
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Valid TO should not be blank.");
                                successFlag = false;
                            } else {
                                try {
                                    validTo = new SimpleDateFormat("dd-MMM-yyyy").format(cell.getDateCellValue());
//                                    ExcelUtil.fillCell(errorBookRowNum, VALID_TO, validTo, row, cell, headerErrorSheet, cellStyle);
                                    String[] dateParts;
                                    if (validTo.contains("/")) {
                                        dateParts = validTo.split("/");
                                    } else {
                                        dateParts = validTo.split("-");
                                    }
                                    if (dateParts != null) {
                                        if (dateParts.length != 3) {
//                                            ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                            if (errorMessage.length() > 0) {
                                                errorMessage.append(" | ");
                                            }
                                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid To 'dd-MMM-yyyy'.");
                                            successFlag = false;
                                        } else if (dateParts[2].length() != 4) {
//                                            ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                            if (errorMessage.length() > 0) {
                                                errorMessage.append(" | ");
                                            }
                                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid To 'dd-MMM-yyyy'.");
                                            successFlag = false;
                                        } else {
                                            Resp dateResp = valdiateDate(dateParts[0], dateParts[1], dateParts[2], "Valid To");
                                            if (dateResp.getRespCode() == RespCode.FAILURE) {
//                                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                                if (errorMessage.length() > 0) {
                                                    errorMessage.append(" | ");
                                                }
                                                errorMessage.append(dateResp.getMsg());
                                                successFlag = false;
                                            }
                                        }

                                    } else {
//                                        ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                        if (errorMessage.length() > 0) {
                                            errorMessage.append(" | ");
                                        }
                                        errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid To 'dd-MMM-yyyy'.");
                                        successFlag = false;
                                    }
                                    if (successFlag) {
                                        try {
                                            validTo = validTo.replaceAll("/", "-");
                                            toDate = format.parse(validTo);
                                            toDateTime = toDate.getTime();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            if (errorMessage.length() > 0) {
                                                errorMessage.append(" | ");
                                            }
//                                            ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                            errorMessage.append("Incorrect Date Format for Valid To.");
                                            successFlag = false;
                                        }
                                    }
                                    if (successFlag && validFrom != null && validTo != null && fromDateTime != null && toDateTime != null && (toDateTime < fromDateTime)) {
                                        if (errorMessage.length() > 0) {
                                            errorMessage.append(" | ");
                                        }
//                                        ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                                        errorMessage.append("Valid To Date Must Be Greater Than Valid From Date.");
                                        successFlag = false;
                                    }
                                } catch (IllegalStateException ex) {
                                    ex.printStackTrace();
                                    errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                                    successFlag = false;
                                }

                                System.out.println("---- All Header Validations Validated Successfully For Row : " + errorBookRowNum);
                            }
                            break;
                    }
                }

                if (successFlag) {
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Success", row, cell, headerErrorSheet, cellStyle);
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, "Success", row, cell, headerErrorSheet, cellStyle);
                } else {

                    int excelRowNumer = rowCount;

                    ExcelUtil.fillCell(errorRowCount, STATUS, "Failure", row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, STATUS_MESSAGE, "Row Num : " + excelRowNumer + "  " + errorMessage.toString(), row, cell, headerErrorSheet, cellStyle);

                    ExcelUtil.fillCell(errorRowCount, REQUEST_NO, requestNo, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, PROMO_TYPE, promoType, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, REMARKS, remarks, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, EXPECTED_MARGIN, marginAchievement, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, EXPTECTED_SALES_QTY_GROWTH, sellThruVsQty, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, EXPTECTED_SALES_VALUE_GROWTH, saleGrowthInQtyValue, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, GROWTH_IN_TICKET_SIZE, growthTicketSize, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, GROWTH_IN_CONVERSIONS, growthInConversion, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, VALID_FROM, validFrom, row, cell, headerErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, VALID_TO, validTo, row, cell, headerErrorSheet, cellStyle);

                    errorRowCount++;
                    successFlag = true;
                    finalSuccessFlag = false;
                }
                //increment error book row counter
                errorBookRowNum++;
            }
            resp.setRequestList(requestList);

        } catch (Exception ex) {
            logger.error("------------- Exception In Processing validateHeaderSheet() MultiPromoFile : " + ex.getMessage());
            ex.printStackTrace();
            finalSuccessFlag = false;
        }
        resp.setStatus(finalSuccessFlag);
        return resp;
    }

    private boolean validateDetailSheet(XSSFSheet detailSheet, XSSFSheet detailErrorSheet, XSSFCellStyle cellStyle, Hashtable<String, Integer> requestList, Long mstPromoId, int errorBookRowNum) {
        boolean finalSuccessFlag = true;
        boolean successFlag = true;

        try {
            XSSFRow row = null;
            XSSFCell cell = null;

            int maxRows = detailSheet.getPhysicalNumberOfRows() - 1;
            if (maxRows <= 0) {
                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, detailErrorSheet, cellStyle);
                ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, "Empty Detail Sheet Found.", row, cell, detailErrorSheet, cellStyle);

                return false;
            }

            StringBuilder errorMessage = new StringBuilder("");
            String requestNo = "";
            String tempRequestNo = "";
            int promoTypeId = 0;
            double article_mc = 0;
            int article_config_qty = 0;
            boolean isReqNoLine = false;
            double discConfigValue = 0;
            int validateSetNo = 0;
            //for reading the values after the request line
            int promotionRequestWiseRowCount = 1;

            /*Cr 2 change*/
            //following flags used in promo type 1
            boolean isDiscTypeFoundForPromo1 = false;
            int tempPromoType1LineNo = 0;
            int i = 1;

            int errorRowCount = 1;
            //row loop
            for (i = 1; i <= maxRows; i++) {
                row = detailSheet.getRow(i);

//                int maxCols = row.getPhysicalNumberOfCells();
                
//                int maxCols = row.getLastCellNum();
//                if (maxCols < 9) {
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, detailErrorSheet, cellStyle);
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, "Total No Of Columns Required Is 9.", row, cell, detailErrorSheet, cellStyle);
//                    return false;
//                }

                Iterator cells = row.cellIterator();

                boolean isRequestNoBlank = false;
                boolean isSetNoBlank = false;
                boolean isArticleBlank = false;
                boolean isMCBlank = false;
                boolean isConfigSetBlank = false;
                boolean isQtyBlank = false;
                boolean isQualifyingAmtBlank = false;
                boolean isDiscTypeBlank = false;
                boolean isDiscValueBlank = false;

                String articleSetNo = "";
                String article = "";
                String mc = "";
                String configSet = "";
                String qty = "";
                String qualifyingAmt = "";
                String discType = "";
                String discValue = "";

                // Column Loop
                //get the values from cell in to variables and write them back in to status file
                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        // Request No Column
                        case 0:
                            if (isBlankCell(cell)) {
                                isRequestNoBlank = true;
                            } else {
                                isRequestNoBlank = false;
                                tempRequestNo = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, REQUEST_NO, tempRequestNo, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Article Set No Column
                        case 1:
                            if (isBlankCell(cell)) {
                                isSetNoBlank = true;
                            } else {
                                isSetNoBlank = false;
                                articleSetNo = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, SET_NO, articleSetNo, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Article Column
                        case 2:
                            if (isBlankCell(cell)) {
                                isArticleBlank = true;
                            } else {
                                isArticleBlank = false;
                                article = getCellValue(cell);                                
//                                ExcelUtil.fillCell(errorBookRowNum, ARTICLE, article, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // MC Column
                        case 3:
                            if (isBlankCell(cell)) {
                                isMCBlank = true;
                            } else {
                                isMCBlank = false;
                                mc = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, MC, mc, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Config Set Column
                        case 4:
                            if (isBlankCell(cell)) {
                                isConfigSetBlank = true;
                            } else {
                                isConfigSetBlank = false;
                                configSet = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, CONFIG_SET, configSet, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Qty Column
                        case 5:
                            if (isBlankCell(cell)) {
                                isQtyBlank = true;
                            } else {
                                isQtyBlank = false;
                                qty = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, QTY, qty, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Qualifying Amt Column
                        case 6:
                            if (isBlankCell(cell)) {
                                isQualifyingAmtBlank = true;
                            } else {
                                isQualifyingAmtBlank = false;
                                qualifyingAmt = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, QUALIFYING_AMT, qualifyingAmt, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Discount Type Column
                        case 7:
                            if (isBlankCell(cell)) {
                                isDiscTypeBlank = true;
                            } else {
                                isDiscTypeBlank = false;
                                discType = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, DISCOUNT_TYPE, discType, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                        // Discount Value Column
                        case 8:
                            if (isBlankCell(cell)) {
                                isDiscValueBlank = true;
                            } else {
                                isDiscValueBlank = false;
                                discValue = getCellValue(cell);
//                                ExcelUtil.fillCell(errorBookRowNum, DISCOUNT_VALUE, discValue, row, cell, detailErrorSheet, cellStyle);
                            }
                            break;
                    }
                }
                System.out.println("-- Set No : " + articleSetNo + "-- Article : " + article + "-- MC : " + mc + "-- Set : " + configSet + "-- Qty : " + qty + "-- qualifyingAmt : " + qualifyingAmt + "-- discType : " + discType + "-- discValue : " + discValue);

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1036 to 1043
                if (isArticleBlank && isMCBlank && isConfigSetBlank && isQtyBlank && isQualifyingAmtBlank && isDiscTypeBlank && isDiscValueBlank) {
                    System.out.println("############################### break");
                    break;
                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1009 to 1023
                if (tempRequestNo != null && tempRequestNo.length() > 0 && !tempRequestNo.equalsIgnoreCase(requestNo)) {
                    requestNo = tempRequestNo;
                    isReqNoLine = true;
                    promotionRequestWiseRowCount = 1;

                    /*Cr 2 change*/
                    //following flags used in promo type 1
                    //Reset Flags to False for new Promo type
                    isDiscTypeFoundForPromo1 = false;
                    tempPromoType1LineNo = i;
                    // CR2 change finished
                } else {
                    isReqNoLine = false;
                    promotionRequestWiseRowCount++;
                }
                System.out.println("---- Is Req No Line Flag : " + isReqNoLine);

                //reset the error message for every row parsed
                if (errorMessage.length() > 0) {
                    errorMessage.delete(0, errorMessage.length());
                }

                //paste the every row into error file.
//                if (isReqNoLine) {
//                    detailErrorSheet.addCell(new Label(2, i, requestNo));
//                }

                /*Cr2 Chnage
                 * now we are having request no in every row and paste it to error file
                 * Above if condition code is now commented
                 */

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1067 to 1094
                if (xlsFileUtility.validateNullCellValue(requestNo)) {
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", row, cell, detailErrorSheet, cellStyle);
                    errorMessage.append("Request No should not be blank.");
                    successFlag = false;
                } else {
                    //Column Request No
                    //check request no exists in header file

                    /*CR2 change*/
                    /*Previously there is only one request no and its validated with header sheet request no
                     * Now its validated for every row.
                     * commenting following if condition
                     */
//                if (isReqNoLine) {

                    if (requestList.containsKey(requestNo)) {
                        promoTypeId = requestList.get(requestNo);
                        System.out.println("---- Promo Type ID : " + promoTypeId);
                    } else {
                        promoTypeId = 0;
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Request No Not Found In Header File.");
                        successFlag = false;
                    }
//                }
                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1096 to 1126
                /* for Promo Type Id 1,6,7 we required atleast two lines request
                 * if for these promo types there are no two lines found then we will give
                 * user validation abt the wrong configration done for these promo types
                 */

                if (maxRows == 1 && (promoTypeId == 1 || promoTypeId == 6 || promoTypeId == 7)) {

                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Discount configuration detail required in next line.");
                    successFlag = false;
                } else if (isReqNoLine && (promoTypeId == 1 || promoTypeId == 6 || promoTypeId == 7)) {
                    if (maxRows == i) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount configuration detail required in next line.");
                        successFlag = false;
                    } else {

                        int next_line_no = i + 1;
                        String nextRequestNo = null;
                        XSSFRow nextRow = detailSheet.getRow(next_line_no);
                        XSSFCell nextRequestNoCell = nextRow.getCell(0);

                        if (!isBlankCell(nextRequestNoCell)) {
                            nextRequestNo = getCellValue(nextRequestNoCell);
                        }
                        if (nextRequestNo == null || !(nextRequestNo.equalsIgnoreCase(requestNo))) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount configuration detail required in next line.");
                            successFlag = false;
                        }
                    }
                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1130 to 1166
                /* CR2 Change*/
                /* Article Set No is mandatory field for every promotion type request Line
                 * Now For Promo Type 3,4,5 & 7 It is not mandatory
                 * Add Condition into following code for the same
                 */
                /*CR2 Change finished*/

                if (promoTypeId != 3 && promoTypeId != 4 && promoTypeId != 5 && promoTypeId != 7) {
                    //Column Set No
                    //check Article Set no blank or numeric or range between 1-5
                    if (xlsFileUtility.validateNullCellValue(articleSetNo)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Set No should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            validateSetNo = Integer.parseInt(articleSetNo);
                            //detailErrorSheet.addCell(new jxl.write.Number(3, i, validateSetNo));
                        } catch (Exception ex) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Set No should  be numeric.");
                            successFlag = false;
                        }

                        if (validateSetNo < 1 && validateSetNo > 5) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Set No should  be between 1-5.");
                            successFlag = false;
                        }
                    }
                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1168 to 1370
                //Column Article , MC
                //check article or mc blank and length and numeric validations
                if (promoTypeId == 4) {
                    if (isReqNoLine) {
                        if (xlsFileUtility.validateNullCellValue(article) && xlsFileUtility.validateNullCellValue(mc)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Please Enter Article OR MC Code.");
                            successFlag = false;
                        } else {

                            if (!xlsFileUtility.validateNullCellValue(mc)) {
                                try {
                                    article_mc = Double.parseDouble(mc);
                                    //detailErrorSheet.addCell(new jxl.write.Number(5, i, article_mc));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("MC Code should be numeric.");
                                    successFlag = false;
                                }

                                if (mc.length() > 18) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("MC Code should be 18 characters.");
                                    successFlag = false;
                                } else {
                                    ValidateMCResp resp = otherService.validateMC(mc, mstPromoId, "1");
                                    if (resp.getResp().getRespCode() == RespCode.FAILURE) {
                                        if (errorMessage.length() > 0) {
                                            errorMessage.append(" | ");
                                        }
                                        errorMessage.append(resp.getResp().getMsg());
                                        successFlag = false;
                                    }
                                }

                            } else if (!xlsFileUtility.validateNullCellValue(article)) {
                                try {
                                    article_mc = Double.parseDouble(article);
                                    //detailErrorSheet.addCell(new jxl.write.Number(4, i, article_mc));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Article Code should be numeric.");
                                    successFlag = false;
                                }
                                if (article.length() > 18) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Article Code should be 18 characters.");
                                    successFlag = false;
                                } else {
                                    ValidateArticleMCVO odsResp = odsService.searchODSArticle(article, mstPromoId, "1");
                                    if (odsResp.isIsErrorStatus() == true) {
                                        if (errorMessage.length() > 0) {
                                            errorMessage.append(" | ");
                                        }
                                        errorMessage.append(odsResp.getErrorMsg());
                                        successFlag = false;
                                    }
                                }
                            }
                        }
                    } else {
                        if (!xlsFileUtility.validateNullCellValue(mc)) {
                            try {
                                article_mc = Double.parseDouble(mc);
                                //detailErrorSheet.addCell(new jxl.write.Number(5, i, article_mc));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("MC Code should be numeric.");
                                successFlag = false;
                            }

                            if (mc.length() > 18) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("MC Code should be 18 characters.");
                                successFlag = false;
                            } else {
                                ValidateMCResp resp = otherService.validateMC(mc, mstPromoId, "1");
                                if (resp.getResp().getRespCode() == RespCode.FAILURE) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append(resp.getResp().getMsg());
                                    successFlag = false;
                                }
                            }

                        } else if (!xlsFileUtility.validateNullCellValue(article)) {
                            try {
                                article_mc = Double.parseDouble(article);
                                //detailErrorSheet.addCell(new jxl.write.Number(4, i, article_mc));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Article Code should be numeric.");
                                successFlag = false;
                            }
                            if (article.length() > 18) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Article Code should be 18 characters.");
                                successFlag = false;
                            } else {
                                ValidateArticleMCVO odsResp = odsService.searchODSArticle(article, mstPromoId, "1");
                                if (odsResp.isIsErrorStatus() == true) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append(odsResp.getErrorMsg());
                                    successFlag = false;
                                }
                            }
                        }
                    }
                } else {
                    if (xlsFileUtility.validateNullCellValue(article) && xlsFileUtility.validateNullCellValue(mc)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Please Enter Article OR MC Code.");
                        successFlag = false;
                    } else {

                        if (!xlsFileUtility.validateNullCellValue(mc)) {
                            try {
                                article_mc = Double.parseDouble(mc);
                                //detailErrorSheet.addCell(new jxl.write.Number(5, i, article_mc));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("MC Code should be numeric.");
                                successFlag = false;
                            }

                            if (mc.length() > 18) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("MC Code should be 18 characters.");
                                successFlag = false;
                            } else {
                                ValidateMCResp resp = otherService.validateMC(mc, mstPromoId, "1");
                                if (resp.getResp().getRespCode() == RespCode.FAILURE) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append(resp.getResp().getMsg());
                                    successFlag = false;
                                }
                            }

                        } else if (!xlsFileUtility.validateNullCellValue(article)) {
                            try {
                                article_mc = Double.parseDouble(article);
                                //detailErrorSheet.addCell(new jxl.write.Number(4, i, article_mc));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Article Code should be numeric.");
                                successFlag = false;
                            }
                            if (article.length() > 18) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Article Code should be 18 characters.");
                                successFlag = false;
                            } else {
                                ValidateArticleMCVO odsResp = odsService.searchODSArticle(article, mstPromoId, "1");
                                if (odsResp.isIsErrorStatus() == true) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append(odsResp.getErrorMsg());
                                    successFlag = false;
                                }
                            }
                        }
                    }
                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1373 to 1437
                //Column Set
                //check config configSet no blank & numeric validation if current row is request no line
                if (isReqNoLine) {

                    /* CR2 Change*/
                    /* config Set No is mandatory field for every promotion type request Line
                     * Now For Promo Type 1,3,4,5 & 7 It is not mandatory
                     * Add Condition into following code for the same
                     */
                    /*CR2 Change finished*/

//                    if (promoTypeId != 1 && promoTypeId != 3 && promoTypeId != 4 && promoTypeId != 5 && promoTypeId != 7) {
                    if (promoTypeId == 1 || promoTypeId == 2 || promoTypeId == 6) {
                        if (xlsFileUtility.validateNullCellValue(configSet)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Set should not be blank.");
                            successFlag = false;
                        } else {
                            try {
                                validateSetNo = Integer.parseInt(configSet);
                                //detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
                            } catch (Exception ex) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Set should be numeric.");
                                successFlag = false;
                            }
                            if ((promoTypeId == 1 || promoTypeId == 2) && validateSetNo != 1) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Set should be 1.");
                                successFlag = false;
                            } else if (validateSetNo < 1 && validateSetNo > 5) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Set should be between 1-5.");
                                successFlag = false;
                            }
                        }
                    }
                } else if ((promoTypeId == 1 || promoTypeId == 2) && !xlsFileUtility.validateNullCellValue(configSet)) {
                    try {
                        validateSetNo = Integer.parseInt(configSet);
                        //detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
                    } catch (Exception ex) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Set should be numeric.");
                        successFlag = false;
                    }
                    if (validateSetNo < 2 && validateSetNo > 5) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Set should be between 2-5.");
                        successFlag = false;
                    }

                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1439 to 1467
                //Column Qty

                //for promotion type 1 & 2 config Qty null and numeric validations if config configSet is found not null
                //for promotion type 4 & 7 config Qty null and numeric validations if current row is request no line
                //for promotion type 3,5,6 Article Qty  OR Config Qty Not Applicable
                if (((promoTypeId == 1 || promoTypeId == 2) && !xlsFileUtility.validateNullCellValue(configSet)) || (isReqNoLine && (promoTypeId == 4 || promoTypeId == 7))) {

                    if (xlsFileUtility.validateNullCellValue(qty)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Qty should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            article_config_qty = Integer.parseInt(qty);
                            //detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qty should be numeric.");
                            successFlag = false;
                        }
                    }

                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1470 to 1546
                //Column Qualifying Amt , Discount Type , Discount Value
                //if current row is request no line
                if (isReqNoLine) {
                    //for promotion type 1-5 discount type  null validation with current row is request no line
                    //for promotion type 1-5 discount value null & numeric validation with current row is request no line
                    //for promotion type 2 discount type=flat price  validation with current row is request no line

                    if (promoTypeId >= 2 && promoTypeId <= 5) {
                        if (xlsFileUtility.validateNullCellValue(discType)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount Type should not be blank.");
                            successFlag = false;
                        } else {

                            if (promoTypeId == 2) {
                                if (!discType.equalsIgnoreCase("Flat Price") && !discType.equalsIgnoreCase("FlatPrice") && !discType.equalsIgnoreCase("FP")) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Discount Type should be Flat Price.");
                                    successFlag = false;
                                }
                            } else {
                            }
                        }

                        if (xlsFileUtility.validateNullCellValue(discValue)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount Value should not be blank.");
                            successFlag = false;
                        } else {
                            try {
                                discConfigValue = Double.parseDouble(discValue);
                                //detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Discount Value should be numeric.");
                                successFlag = false;
                            }

                        }

                    }

                    //for promotion type 5-6 qualifying amt null & numeric validation with current row is request no line
                    if (promoTypeId == 5 || promoTypeId == 6) {

                        if (xlsFileUtility.validateNullCellValue(qualifyingAmt)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qualifying Amt should not be blank.");
                            successFlag = false;
                        } else {
                            try {
                                discConfigValue = Double.parseDouble(qualifyingAmt);
                                //detailErrorSheet.addCell(new jxl.write.Number(8, i, discConfigValue));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Qualifying Amt should be numeric.");
                                successFlag = false;
                            }

                        }

                    }
                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1549 to 1701
                //for promotion type 6-7 check config configSet ,qty,discount type,discount value  null & numeric validation with current row is request no line
                /*CR 2 change
                 * for promo type id second row config configSet is not alloed now so removing condition
                || promoTypeId == 7 from below if statement
                 */
                // CR2 change finished
                //if (promotionRequestWiseRowCount == 2 && (promoTypeId == 6 || promoTypeId == 7)) {
                if (promotionRequestWiseRowCount == 2 && (promoTypeId == 6)) {
                    if (xlsFileUtility.validateNullCellValue(configSet)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Set should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            validateSetNo = Integer.parseInt(configSet);
                            //detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
                        } catch (Exception ex) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Set should be numeric.");
                            successFlag = false;
                        }

//
//                        if (!configSet.equalsIgnoreCase("2")) {
//                            if (errorMessage.length() > 0) {
//                                errorMessage.append(" | ");
//                            }
//                            errorMessage.append("Set value should be 2.");
//                            successFlag = false;
//                        }
                    }

                    if (xlsFileUtility.validateNullCellValue(qty)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Qty should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            article_config_qty = Integer.parseInt(qty);
                            //detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qty should be numeric.");
                            successFlag = false;
                        }
                    }


                    if (xlsFileUtility.validateNullCellValue(discType)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Type should not be blank.");
                        successFlag = false;
                    }

                    /* Following code is not required*/
//                    else {
//
//                        if (promoTypeId == 2) {
//
//                            if (!discType.equalsIgnoreCase("Flat Price") || !discType.equalsIgnoreCase("FlatPrice")) {
//                                if (errorMessage.length() > 0) {
//                                    errorMessage.append(" | ");
//                                }
//                                errorMessage.append("Discount Type should be Flat Price.");
//                                successFlag = false;
//                            }
//                        }
//                    }

                    if (xlsFileUtility.validateNullCellValue(discValue)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Value should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            discConfigValue = Double.parseDouble(discValue);
                            //detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount Value should be numeric.");
                            successFlag = false;
                        }

                    }
                } else if (promotionRequestWiseRowCount == 2 && (promoTypeId == 7)) {
                    if (xlsFileUtility.validateNullCellValue(qty)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Qty should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            article_config_qty = Integer.parseInt(qty);
                            //detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qty should be numeric.");
                            successFlag = false;
                        }
                    }


                    if (xlsFileUtility.validateNullCellValue(discType)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Type should not be blank.");
                        successFlag = false;
                    }


                    if (xlsFileUtility.validateNullCellValue(discValue)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Value should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            discConfigValue = Double.parseDouble(discValue);
                            //detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount Value should be numeric.");
                            successFlag = false;
                        }

                    }

                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1704 to 1863
                //for promotion type 1-4 check config configSet ,qty,discount type,discount value  null & numeric validation with current row is request no line
                if (promotionRequestWiseRowCount > 1 && (promoTypeId == 1 || promoTypeId == 4)) {


                    if (promoTypeId == 4) {
                        if (!xlsFileUtility.validateNullCellValue(qty)) {
                            try {
                                article_config_qty = Integer.parseInt(qty);
                                //detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Qty should be numeric.");
                                successFlag = false;
                            }
                            if (xlsFileUtility.validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount type.");
                                successFlag = false;
                            } else if (xlsFileUtility.validateNullCellValue(discValue)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount value.");
                                successFlag = false;
                            } else {
                                try {
                                    discConfigValue = Double.parseDouble(discValue);
                                    //detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Discount Value should be numeric.");
                                    successFlag = false;
                                }
                            }
                        } else {
                            if (!xlsFileUtility.validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter qty.");
                                successFlag = false;
                            } else if (!xlsFileUtility.validateNullCellValue(discValue)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter qty.");
                                successFlag = false;
                                try {
                                    discConfigValue = Double.parseDouble(discValue);
                                    //detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Discount Value should be numeric.");
                                    successFlag = false;
                                }
                            }
                        }

                    }
                    if (promoTypeId == 1) {
                        /* CR2 Change*/
                        //Config Set 1 not allowed for promo type 1
                        if (xlsFileUtility.validateNullCellValue(configSet)) {
                            if (!xlsFileUtility.validateNullCellValue(discValue) || !xlsFileUtility.validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter set between 2-5.");
                                successFlag = false;
                            }
                        } else {
                            try {
                                validateSetNo = Integer.parseInt(configSet);
                                //detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
                                if (validateSetNo < 2 && validateSetNo > 5) {
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Set should be between 2-5.");
                                    successFlag = false;
                                } else {
                                    isDiscTypeFoundForPromo1 = true;
                                }
                            } catch (Exception ex) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Set should  be numeric.");
                                successFlag = false;
                            }

                            if (!xlsFileUtility.validateNullCellValue(qty)) {
                                try {
                                    article_config_qty = Integer.parseInt(qty);
                                    //detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Qty should be numeric.");
                                    successFlag = false;
                                }
                            }

                            if (xlsFileUtility.validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount type.");
                                successFlag = false;
                            }

                            if (xlsFileUtility.validateNullCellValue(discValue)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount value.");
                                successFlag = false;
                            } else {
                                try {
                                    discConfigValue = Double.parseDouble(discValue);
                                    // detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Discount Value should be numeric.");
                                    successFlag = false;
                                }
                            }

                        }
                        int nextRowIndex = i + 1;
                        if (nextRowIndex <= maxRows) {

                            String nextLineRequestNo = null;
                            XSSFRow nextRow = detailSheet.getRow(nextRowIndex);
                            XSSFCell nextRequestNoCell = nextRow.getCell(0);

                            if (!isBlankCell(nextRequestNoCell)) {
                                nextLineRequestNo = getCellValue(nextRequestNoCell);
                            }

                            if (!requestNo.equalsIgnoreCase(nextLineRequestNo) && !isDiscTypeFoundForPromo1) {
                                System.out.println("-------- Inside last line of promotype 1");

//                                ExcelUtil.fillCell(errorBookRowNum, STATUS, "Failure", nextRow, nextRequestNoCell, detailErrorSheet, cellStyle);
//                                ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, errorMessage.toString(), nextRow, nextRequestNoCell, detailErrorSheet, cellStyle);

                                int excelRowNumer = i + 1;

                                ExcelUtil.fillCell(errorRowCount, STATUS, "Failure", row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, STATUS_MESSAGE, "Row Num : " + excelRowNumer + "  " + errorMessage.toString(), row, cell, detailErrorSheet, cellStyle);

                                ExcelUtil.fillCell(errorRowCount, REQUEST_NO, tempRequestNo, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, SET_NO, articleSetNo, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, ARTICLE, article, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, MC, mc, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, CONFIG_SET, configSet, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, QTY, qty, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, QUALIFYING_AMT, qualifyingAmt, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, DISCOUNT_TYPE, discType, row, cell, detailErrorSheet, cellStyle);
                                ExcelUtil.fillCell(errorRowCount, DISCOUNT_VALUE, discValue, row, cell, detailErrorSheet, cellStyle);
                                errorRowCount++;

                                successFlag = true;
                                finalSuccessFlag = false;
                                continue;
                            }
                        }

                        /*Cr2 Change finished*/
                    }

                }

                // by following line the code is same as multiplePromotionFileUploadUtil File from line 1865 to 1880
                System.out.println("--------------- All Detail Validations Validated Successfully For Row : " + i);

                System.out.println("---------- Sucess Flag : " + successFlag);

                System.out.println("---------- Error  Msg : " + errorMessage);

                System.out.println("---------- Request Line Flag : " + isReqNoLine);
                if (successFlag) {
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS, "Success", row, cell, detailErrorSheet, cellStyle);
//                    ExcelUtil.fillCell(errorBookRowNum, STATUS_MESSAGE, "Success", row, cell, detailErrorSheet, cellStyle);
                } else {
                    int excelRowNumer = i + 1;

                    ExcelUtil.fillCell(errorRowCount, STATUS, "Failure", row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, STATUS_MESSAGE, "Row Num : " + excelRowNumer + "  " + errorMessage.toString(), row, cell, detailErrorSheet, cellStyle);

                    ExcelUtil.fillCell(errorRowCount, REQUEST_NO, tempRequestNo, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, SET_NO, articleSetNo, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, ARTICLE, article, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, MC, mc, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, CONFIG_SET, configSet, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, QTY, qty, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, QUALIFYING_AMT, qualifyingAmt, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, DISCOUNT_TYPE, discType, row, cell, detailErrorSheet, cellStyle);
                    ExcelUtil.fillCell(errorRowCount, DISCOUNT_VALUE, discValue, row, cell, detailErrorSheet, cellStyle);
                    errorRowCount++;

                    successFlag = true;
                    finalSuccessFlag = false;
                }
                errorBookRowNum++;
            }


        } catch (Exception ex) {
            logger.error("------------- Exception In Processing validateDetailSheet() MultiPromoFile : " + ex.getMessage());
            ex.printStackTrace();
            finalSuccessFlag = false;
        }
        return finalSuccessFlag;


    }

    private ValidatePromoHeaderFileDtl getPromoRequestList(XSSFSheet headerSheet) {
        ValidatePromoHeaderFileDtl req = new ValidatePromoHeaderFileDtl();
        Hashtable<String, String> requestNoData = new Hashtable<String, String>();

        XSSFRow row = null;
        XSSFCell cell = null;
        //row loop
        Iterator rows = headerSheet.rowIterator();
        boolean isFirstRow = true;
        while (rows.hasNext()) {

            row = (XSSFRow) rows.next();

            //Skip First Header Row
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            Iterator cells = row.cellIterator();

            String requestNo = "";
            String promoType = "";
            String remarks = "";
            String marginAchievement = "";
            String sellThruVsQty = "";
            String saleGrowthInQtyValue = "";
            String growthTicketSize = "";
            String growthInConversion = "";
            String validFrom = "";
            String validTo = "";

            // Column Loop
            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    // Request No Column
                    case 0:
                        if (!isBlankCell(cell)) {
                            requestNo = getCellValue(cell);
                        }
                        break;
                    // Promo Type Column
                    case 1:
                        if (!isBlankCell(cell)) {
                            promoType = getCellValue(cell);
                        }
                        break;
                    // Remarks Column
                    case 2:
                        if (!isBlankCell(cell)) {
                            remarks = getCellValue(cell);
                            if (remarks.contains(",")) {
                                remarks = remarks.replaceAll(",", " ");
                            }
                        }
                        break;
                    // Expected Margin Column
                    case 3:
                        if (!isBlankCell(cell)) {
                            marginAchievement = getCellValue(cell);
                        }
                        break;
                    // Expected Sales Qty Growth Column
                    case 4:
                        if (!isBlankCell(cell)) {
                            sellThruVsQty = getCellValue(cell);
                        }
                        break;
                    // Expected Sales Value Growth Column
                    case 5:
                        if (!isBlankCell(cell)) {
                            saleGrowthInQtyValue = getCellValue(cell);
                        }
                        break;
                    // Growth In Ticket Size Column
                    case 6:
                        if (!isBlankCell(cell)) {
                            growthTicketSize = getCellValue(cell);
                        }
                        break;
                    // Growth In Conversions Column
                    case 7:
                        if (!isBlankCell(cell)) {
                            growthInConversion = getCellValue(cell);
                        }
                        break;
                    // Valid From Column
                    case 8:
                        if (!isBlankCell(cell)) {
                            validFrom = new SimpleDateFormat("dd-MMM-yyyy").format(cell.getDateCellValue());
                        }
                        break;
                    // Valid To Column
                    case 9:
                        if (!isBlankCell(cell)) {
                            validTo = new SimpleDateFormat("dd-MMM-yyyy").format(cell.getDateCellValue());
                        }
                        break;
                }
            }
            requestNoData.put(requestNo, promoType + "," + remarks + "," + marginAchievement + "," + sellThruVsQty + "," + saleGrowthInQtyValue + "," + growthTicketSize + "," + growthInConversion + "," + validFrom + "," + validTo);
        }

        req.setRequestNoData(requestNoData);

        return req;
    }

    private List<TransPromoVO> getPromoArticleList(XSSFSheet detailSheet, Hashtable<String, String> requestNoData, Long mstPromoId) throws ParseException {
        System.out.println("------ Inside Creating Promo Detail List Objects ------");
        List<TransPromoVO> promoList = new ArrayList<TransPromoVO>();

        Date dummyDate;
        int promotionRequestWiseRowCount = 1;
        int maxRows = detailSheet.getPhysicalNumberOfRows() - 1;
        int promoTypeId = 0;
        String requestNo = "";
        TransPromoVO vo = null;
        TransPromoArticleVO articleVO = null;
        List<TransPromoArticleVO> articleList = null;
        List<TransPromoConfigVO> configList = null;
        TransPromoConfigVO configVo = null;
        String tempDiscValueForPromo4 = "";

        //header file fields
        String promoType = "";
        String remarks = "";
        String marginAchievement = "";
        String growthTicketSize = "";
        String sellThruVsQty = "";
        String growthInConversion = "";
        String saleGrowthInQtyValue = "";
        String validFrom = "";
        String validTo = "";
        boolean isNewPromoCreation = false;

        XSSFRow row = null;
        XSSFCell cell = null;

        for (int i = 1; i <= maxRows; i++) {
            row = detailSheet.getRow(i);
            //reset articleVO
            articleVO = null;

            Iterator cells = row.cellIterator();

            boolean isRequestNoBlank = false;
            boolean isSetNoBlank = false;
            boolean isArticleBlank = false;
            boolean isMCBlank = false;
            boolean isConfigSetBlank = false;
            boolean isQtyBlank = false;
            boolean isQualifyingAmtBlank = false;
            boolean isDiscTypeBlank = false;
            boolean isDiscValueBlank = false;

            String tempRequestNo = "";
            String articleSetNo = "";
            String article = "";
            String mc = "";
            String configSet = "";
            String qty = "";
            String qualifyingAmt = "";
            String discType = "";
            String discValue = "";

            // Column Loop
            //get the values from cell in to variables and write them back in to status file
            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    // Request No Column
                    case 0:
                        if (isBlankCell(cell)) {
                            isRequestNoBlank = true;
                        } else {
                            isRequestNoBlank = false;
                            tempRequestNo = getCellValue(cell);
                        }
                        break;
                    // Article Set No Column
                    case 1:
                        if (isBlankCell(cell)) {
                            isSetNoBlank = true;
                        } else {
                            isSetNoBlank = false;
                            articleSetNo = getCellValue(cell);
                        }
                        break;
                    // Article Column
                    case 2:
                        if (isBlankCell(cell)) {
                            isArticleBlank = true;
                        } else {
                            isArticleBlank = false;
                            article = getCellValue(cell);
                        }
                        break;
                    // MC Column
                    case 3:
                        if (isBlankCell(cell)) {
                            isMCBlank = true;
                        } else {
                            isMCBlank = false;
                            mc = getCellValue(cell);
                        }
                        break;
                    // Config Set Column
                    case 4:
                        if (isBlankCell(cell)) {
                            isConfigSetBlank = true;
                        } else {
                            isConfigSetBlank = false;
                            configSet = getCellValue(cell);
                        }
                        break;
                    // Qty Column
                    case 5:
                        if (isBlankCell(cell)) {
                            isQtyBlank = true;
                        } else {
                            isQtyBlank = false;
                            qty = getCellValue(cell);
                        }
                        break;
                    // Qualifying Amt Column
                    case 6:
                        if (isBlankCell(cell)) {
                            isQualifyingAmtBlank = true;
                        } else {
                            isQualifyingAmtBlank = false;
                            qualifyingAmt = getCellValue(cell);
                        }
                        break;
                    // Discount Type Column
                    case 7:
                        if (isBlankCell(cell)) {
                            isDiscTypeBlank = true;
                        } else {
                            isDiscTypeBlank = false;
                            discType = getCellValue(cell);
                        }
                        break;
                    // Discount Value Column
                    case 8:
                        if (isBlankCell(cell)) {
                            isDiscValueBlank = true;
                        } else {
                            isDiscValueBlank = false;
                            discValue = getCellValue(cell);
                        }
                        break;
                }
            }
            // by following line the code is same as multiplePromotionFileUploadUtil File from line 158 to 495
            if (isArticleBlank && isMCBlank && isConfigSetBlank && isQtyBlank && isQualifyingAmtBlank && isDiscTypeBlank && isDiscValueBlank) {
                break;
            }

            /* CR2 Change
             * Previously Request Number comes in to firstline
             * Now it comes into every line.
             * following additional condition needed for track down of request
             * && !tempRequestNo.equalsIgnoreCase(requestNo)
             */
            if (tempRequestNo != null && tempRequestNo.length() > 0 && !tempRequestNo.equalsIgnoreCase(requestNo)) {
                isNewPromoCreation = true;
                requestNo = tempRequestNo;
                System.out.println("--- Request No :" + requestNo);
                tempDiscValueForPromo4 = discType;
                promotionRequestWiseRowCount = 1;

                String headerData = requestNoData.get(requestNo);
                System.out.println("--- Header DAta : " + headerData);
                String[] headerDataArray = headerData.split(",");

                promoType = headerDataArray[0];
                remarks = headerDataArray[1];
                marginAchievement = headerDataArray[2];
                sellThruVsQty = headerDataArray[3];
                saleGrowthInQtyValue = headerDataArray[4];
                growthTicketSize = headerDataArray[5];
                growthInConversion = headerDataArray[6];
                validFrom = headerDataArray[7];
                validTo = headerDataArray[8];

                promoTypeId = getTransPromoType(promoType);

                vo = new TransPromoVO();
                articleList = new ArrayList<TransPromoArticleVO>();
                vo.setPromoTypeId(Long.valueOf(String.valueOf(promoTypeId)));
                vo.setRemark(remarks);

                validFrom = validFrom.replaceAll("/", "-");
                dummyDate = format.parse(validFrom);
                validFrom = dbformat.format(dummyDate);
                vo.setValidFrom(validFrom);
                System.out.println("--- valid From : " + validFrom);

                validTo = validTo.replaceAll("/", "-");
                dummyDate = format.parse(validTo);
                validTo = dbformat.format(dummyDate);
                vo.setValidTo(validTo);
                System.out.println("--- valid TO : " + validTo);

                configList = new ArrayList<TransPromoConfigVO>();
                //single config Vo
                if (promoTypeId == 2 || promoTypeId == 3 || promoTypeId == 5 || promoTypeId == 6) {
                    configVo = new TransPromoConfigVO();
                    configVo.setMarginAchievement(Double.valueOf(marginAchievement));
                    if (growthTicketSize.trim().length() > 0) {
                        configVo.setTicketSizeGrowth(Double.valueOf(growthTicketSize));
                    } else {
                        configVo.setTicketSizeGrowth(0);
                    }
                    configVo.setSellThruQty(Double.valueOf(sellThruVsQty));
                    if (growthInConversion.trim().length() > 0) {
                        configVo.setGrowthCoversion(Double.valueOf(growthInConversion));
                    } else {
                        configVo.setGrowthCoversion(0);
                    }
                    configVo.setSalesGrowth(Double.valueOf(saleGrowthInQtyValue));
                    configVo.setValidFrom(validFrom);
                    configVo.setValidTo(validTo);

                    configVo.setSetId(1);

                    if (promoTypeId != 6 && promoTypeId != 7) {
                        configVo.setDiscConfig(discType);
                        configVo.setDiscValue(Double.parseDouble(discValue));
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        configVo.setTicketWorthAmt(Double.parseDouble(qualifyingAmt));
                    }

                    if (promoTypeId == 2) {
                        configVo.setQty(Integer.parseInt(qty));
                    }
                    configList.add(configVo);

                } else if (promoTypeId == 7) {
                    vo.setBuyQty(Integer.parseInt(qty));
                } else if (promoTypeId == 1) {
                    if (configSet != null && configSet.length() > 0 && qty != null && qty.length() > 0) {
                        configVo = new TransPromoConfigVO();
                        configVo.setMarginAchievement(0);
                        configVo.setTicketSizeGrowth(0);
                        configVo.setSellThruQty(0);
                        configVo.setGrowthCoversion(0);
                        configVo.setSalesGrowth(0);

                        configVo.setValidFrom(validFrom);
                        configVo.setValidTo(validTo);

                        configVo.setSetId(Integer.parseInt(configSet));
                        configVo.setQty(Integer.parseInt(qty));

                        configVo.setDiscConfig("-");
                        configVo.setDiscValue(0);

                        configList.add(configVo);
                    }


                }
            } else {
                promotionRequestWiseRowCount++;
                isNewPromoCreation = false;
            }
            //Article Started
            if (!xlsFileUtility.validateNullCellValue(article) || !xlsFileUtility.validateNullCellValue(mc)) {
                articleVO = new TransPromoArticleVO();
            }

            /* CR2 Change*/
            /* Article Set No is mandatory field for every promotion type request Line
             * Now For Promo Type 3,4,5 & 7 It is not mandatory
             * Add Condition into following code for the same
             */
            /*CR2 Change finished*/

            if (promoTypeId != 3 && promoTypeId != 4 && promoTypeId != 5 && promoTypeId != 7) {
                articleVO.setSetId(Integer.parseInt(articleSetNo));
            } else {
                if (articleVO != null) {
                    articleVO.setSetId(1);
                }
            }



            if (!xlsFileUtility.validateNullCellValue(article)) {
                ValidateArticleMCVO odsResp = odsService.searchODSArticle(article, mstPromoId, "1");
//                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  Article : " + (!validateNullCellValue(article)));
                if (odsResp.isIsErrorStatus() == false) {
                    System.out.println("--- MC Code : " + odsResp.getMcDesc());
                    articleVO.setArtCode(article);
                    articleVO.setArtDesc(odsResp.getArticleDesc());
                    articleVO.setMcCode(odsResp.getMcCode());
                    articleVO.setMcDesc(odsResp.getMcDesc());
                    articleVO.setBrandCode(odsResp.getBrandCode());
                    articleVO.setBrandDesc(odsResp.getBrandDesc());
                }
            }


            if (!xlsFileUtility.validateNullCellValue(mc)) {
                ValidateMCResp resp = otherService.validateMC(mc, mstPromoId, "1");
//                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  MC : " + (!validateNullCellValue(mc)));
                if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
                    System.out.println("--- MC Code : " + resp.getMcDesc());
                    articleVO.setArtCode("-");
                    articleVO.setArtDesc("-");
                    articleVO.setMcCode(mc);
                    articleVO.setMcDesc(resp.getMcDesc());
                    articleVO.setBrandCode("-");
                    articleVO.setBrandDesc("-");
                }
            }

            /* prevent article qty for promo type id 1  & 2
            if (promoTypeId == 1 || promoTypeId == 2) {
            articleVO.setQty(Integer.parseInt(qty));
            }
             */
            //Article Finished

            if (promoTypeId == 6 && promotionRequestWiseRowCount == 2) {
                configVo.setQty(Integer.parseInt(qty));
                configVo.setDiscConfig(discType);
                configVo.setDiscValue(Double.parseDouble(discValue));

            }

            System.out.println("------- Promo Request cOunt : " + promotionRequestWiseRowCount);
            if (promoTypeId == 7 && promotionRequestWiseRowCount == 2) {
                System.out.println("------- Inside PRomo Type ID 7 With Promo Request cOunt 2");
                vo.setGetQty(Integer.parseInt(qty));

                configVo = new TransPromoConfigVO();
                configVo.setMarginAchievement(Double.valueOf(marginAchievement));
                if (growthTicketSize.trim().length() > 0) {
                    configVo.setTicketSizeGrowth(Double.valueOf(growthTicketSize));
                } else {
                    configVo.setTicketSizeGrowth(0);
                }

                configVo.setSellThruQty(Double.valueOf(sellThruVsQty));
                if (growthInConversion.trim().length() > 0) {
                    configVo.setGrowthCoversion(Double.valueOf(growthInConversion));
                } else {
                    configVo.setGrowthCoversion(0);
                }
                configVo.setSalesGrowth(Double.valueOf(saleGrowthInQtyValue));
                configVo.setValidFrom(validFrom);
                configVo.setValidTo(validTo);

                configVo.setSetId(2);
                configVo.setDiscConfig(discType);
                configVo.setDiscValue(Double.parseDouble(discValue));

                configList.add(configVo);
            }

            if (promoTypeId == 1) {
                if (!isNewPromoCreation && configSet != null && configSet.length() > 0 && discType != null && discType.length() > 0 && discValue != null && discValue.length() > 0 && qty != null && qty.length() > 0) {
                    configVo = new TransPromoConfigVO();
                    configVo.setMarginAchievement(Double.valueOf(marginAchievement));
                    if (growthTicketSize.trim().length() > 0) {
                        configVo.setTicketSizeGrowth(Double.valueOf(growthTicketSize));
                    } else {
                        configVo.setTicketSizeGrowth(0);
                    }
                    configVo.setSellThruQty(Double.valueOf(sellThruVsQty));
                    if (growthInConversion.trim().length() > 0) {
                        configVo.setGrowthCoversion(Double.valueOf(growthInConversion));
                    } else {
                        configVo.setGrowthCoversion(0);
                    }
                    configVo.setSalesGrowth(Double.valueOf(saleGrowthInQtyValue));
                    configVo.setValidFrom(validFrom);
                    configVo.setValidTo(validTo);

                    configVo.setSetId(Integer.parseInt(configSet));
                    configVo.setQty(Integer.parseInt(qty));
                    configVo.setDiscConfig(discType);
                    configVo.setDiscValue(Double.parseDouble(discValue));

                    configList.add(configVo);
                }

            } else if (promoTypeId == 2) {
                if (!isNewPromoCreation && configSet != null && configSet.length() > 0 && qty != null && qty.length() > 0) {
                    configVo = new TransPromoConfigVO();
                    configVo.setMarginAchievement(0);
                    configVo.setTicketSizeGrowth(0);
                    configVo.setSellThruQty(0);
                    configVo.setGrowthCoversion(0);
                    configVo.setSalesGrowth(0);

                    configVo.setValidFrom(validFrom);
                    configVo.setValidTo(validTo);

                    configVo.setSetId(Integer.parseInt(configSet));
                    configVo.setQty(Integer.parseInt(qty));

                    configVo.setDiscConfig("-");
                    configVo.setDiscValue(0);

                    configList.add(configVo);
                }

            } else if (promoTypeId == 4) {
                if (qty != null && qty.length() > 0 && discValue != null && discValue.length() > 0) {
                    configVo = new TransPromoConfigVO();
                    System.out.println("--- Promo Id 4 : " + marginAchievement);
                    configVo.setMarginAchievement(Double.valueOf(marginAchievement));
                    if (growthTicketSize.trim().length() > 0) {
                        configVo.setTicketSizeGrowth(Double.valueOf(growthTicketSize));
                    } else {
                        configVo.setTicketSizeGrowth(0);
                    }
                    configVo.setSellThruQty(Double.valueOf(sellThruVsQty));
                    if (growthInConversion.trim().length() > 0) {
                        configVo.setGrowthCoversion(Double.valueOf(growthInConversion));
                    } else {
                        configVo.setGrowthCoversion(0);
                    }
                    configVo.setSalesGrowth(Double.valueOf(saleGrowthInQtyValue));
                    configVo.setValidFrom(validFrom);
                    configVo.setValidTo(validTo);

                    /*CR 2 Change
                     * Set No Not Mandatory
                     * removed condition (configSet != null && configSet.length() > 0) from above if statement
                     * add the same condition for configVo.setSetId(Integer.parseInt(configSet));
                     */
                    if (configSet != null && configSet.length() > 0) {
                        configVo.setSetId(Integer.parseInt(configSet));
                    } else {
                        configVo.setSetId(1);
                    }
                    // CR 2 Change finished
                    configVo.setQty(Integer.parseInt(qty));


                    //CR2 Change
                    /*Previously Discount Type is only in the request Line
                     * Now we configure disocunt type for every line so tempDiscValueForPromo4 is not needed
                     * instead we use discType direclty
                     */
//                    configVo.setDiscConfig(tempDiscValueForPromo4);
                    configVo.setDiscConfig(discType);
                    //CR2 change finished
                    configVo.setDiscValue(Double.parseDouble(discValue));

                    configList.add(configVo);
                }

            } else if (promoTypeId == 3 || promoTypeId == 5) {
                /*CR 2 Change
                 * Set No Not Mandatory
                 * removed condition (configSet != null && configSet.length() > 0) from above if statement
                 * add the same condition for configVo.setSetId(Integer.parseInt(configSet));
                 */
                if (configSet != null && configSet.length() > 0) {
                    configVo.setSetId(Integer.parseInt(configSet));
                } else {
                    configVo.setSetId(1);
                }
                // CR 2 Change finished
            }

            if (articleVO != null) {
                articleList.add(articleVO);
            }
            vo.setTransPromoArticleList(articleList);
            vo.setTransPromoConfigList(configList);

//            if (tempRequestNo != null && tempRequestNo.length() > 0) {
//                promoList.add(vo);
//            }

            /* CR2 Change
             * Previously Request Number comes in to firstline
             * Now it comes into every line.
             * comment above if code
             */
            if (isNewPromoCreation) {
                promoList.add(vo);
            }
            /* CR2 change Finished*/

        }

        System.out.println("------ Inside Creating Promo Detail List Objects ------" + promoList.size());
        return promoList;

    }

    private String getCellValue(XSSFCell cell) {
        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            System.out.println("--- getting string type cell...");
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            System.out.println("--- getting numeric type cell... converting to long value.");
            return String.valueOf((new Double(cell.getNumericCellValue())).longValue()).trim();            
//            return String.valueOf((new Double(cell.getNumericCellValue())).intValue()).trim();            
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim();
        }
        return null;
    }

    private boolean isBlankCell(XSSFCell cell) {
        if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
            return true;
        }
        return false;
    }

    private int getTransPromoType(String promoTypeName) {
        if (promoTypeName == null || promoTypeName.trim().length() == 0) {
            return 0;
        }
        if (promoTypeName.trim().equalsIgnoreCase("Buy 'X' Get 'X' at discount (B1G1 etc)")) {
            return 7;
        } else if (promoTypeName.trim().equalsIgnoreCase("BUY 'X' and GET 'Y' at discount (SET)")) {
            return 1;
        } else if (promoTypeName.trim().equalsIgnoreCase("Buy 'X' and 'Y' at Flat combi price (SET)")) {
            return 2;
        } else if (promoTypeName.trim().equalsIgnoreCase("FLAT DISCOUNT")) {
            return 3;
        } else if (promoTypeName.trim().equalsIgnoreCase("POWER-PRICING")) {
            return 4;
        } else if (promoTypeName.trim().equalsIgnoreCase("Ticket Size (Bill Level)")) {
            return 5;
        } else if (promoTypeName.trim().equalsIgnoreCase("Ticket Size discount at item level (SET)")) {
            return 6;
        }
        return 0;
    }

    private static Resp valdiateDate(String strDay, String strMonth, String strYear, String dateField) {
        int day = Integer.parseInt(strDay);
        int month = getMonthInNumeric(strMonth);
        int year = Integer.parseInt(strYear);

        if (year % 4 == 0) {
            if (month == 2 & (day < 0 || day > 29)) {
                return (new Resp(RespCode.FAILURE, "Wrong Day Provided In " + dateField + "."));
            }
        } else if (month == 2 & (day < 0 || day > 28)) {
            return (new Resp(RespCode.FAILURE, "Wrong Day Provided In " + dateField + "."));
        } else if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) & (day < 0 || day > 31)) {
            return (new Resp(RespCode.FAILURE, "Wrong Day Provided In " + dateField + "."));
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) & (day < 0 || day > 30)) {
            return (new Resp(RespCode.FAILURE, "Wrong Day Provided In " + dateField + "."));
        }
        if (month < 0 || month > 12) {
            return (new Resp(RespCode.FAILURE, "Wrong Month Provided In " + dateField + "."));
        }
        return (new Resp(RespCode.SUCCESS, "success"));
    }

    private static int getMonthInNumeric(String strMonth) {

        if (strMonth.equalsIgnoreCase("Jan")) {
            return 1;
        } else if (strMonth.equalsIgnoreCase("Feb")) {
            return 2;
        } else if (strMonth.equalsIgnoreCase("Mar")) {
            return 3;
        } else if (strMonth.equalsIgnoreCase("Apr")) {
            return 4;
        } else if (strMonth.equalsIgnoreCase("May")) {
            return 5;
        } else if (strMonth.equalsIgnoreCase("Jun")) {
            return 6;
        } else if (strMonth.equalsIgnoreCase("Jul")) {
            return 7;
        } else if (strMonth.equalsIgnoreCase("Aug")) {
            return 8;
        } else if (strMonth.equalsIgnoreCase("Sep")) {
            return 9;
        } else if (strMonth.equalsIgnoreCase("Oct")) {
            return 10;
        } else if (strMonth.equalsIgnoreCase("Nov")) {
            return 11;
        } else if (strMonth.equalsIgnoreCase("Dec")) {
            return 12;
        }
        return 0;
    }

    private void generateErrorBookHeader(int rowNum, XSSFCell cell, XSSFSheet errorHeaderSheet, XSSFSheet errorDetailSheet, XSSFCellStyle cellStyle) {

        XSSFRow dataRow = errorHeaderSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, STATUS, "Status", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, STATUS_MESSAGE, "Message", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, REQUEST_NO, "Request No", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, PROMO_TYPE, "Promo Type", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, REMARKS, "Remarks", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, EXPECTED_MARGIN, "Expected Margin", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, EXPTECTED_SALES_QTY_GROWTH, "Expected Sales Qty growth", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, EXPTECTED_SALES_VALUE_GROWTH, "Expected Sales value growth", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, GROWTH_IN_TICKET_SIZE, "Growth IN Ticket size", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, GROWTH_IN_CONVERSIONS, "Growth IN Conversions", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, VALID_FROM, "Valid From", dataRow, cell, errorHeaderSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, VALID_TO, "Valid to", dataRow, cell, errorHeaderSheet, cellStyle);

        errorHeaderSheet.setColumnWidth(STATUS, 5000);
        errorHeaderSheet.setColumnWidth(STATUS_MESSAGE, 15000);
        errorHeaderSheet.setColumnWidth(REQUEST_NO, 5000);
        errorHeaderSheet.setColumnWidth(PROMO_TYPE, 10000);
        errorHeaderSheet.setColumnWidth(REMARKS, 5000);
        errorHeaderSheet.setColumnWidth(EXPECTED_MARGIN, 5000);
        errorHeaderSheet.setColumnWidth(EXPTECTED_SALES_QTY_GROWTH, 8000);
        errorHeaderSheet.setColumnWidth(EXPTECTED_SALES_VALUE_GROWTH, 8000);
        errorHeaderSheet.setColumnWidth(GROWTH_IN_TICKET_SIZE, 5000);
        errorHeaderSheet.setColumnWidth(GROWTH_IN_CONVERSIONS, 8000);
        errorHeaderSheet.setColumnWidth(VALID_FROM, 5000);
        errorHeaderSheet.setColumnWidth(VALID_TO, 5000);

        dataRow = errorDetailSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, STATUS, "Status", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, STATUS_MESSAGE, "Message", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, REQUEST_NO, "Request No", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, SET_NO, "Set No", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, ARTICLE, "Article", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, MC, "MC", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, CONFIG_SET, "Set", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, QTY, "Qty", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, QUALIFYING_AMT, "Qualifying Amount", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, DISCOUNT_TYPE, "Discount type", dataRow, cell, errorDetailSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, DISCOUNT_VALUE, "Discount Value", dataRow, cell, errorDetailSheet, cellStyle);

        errorDetailSheet.setColumnWidth(STATUS, 5000);
        errorDetailSheet.setColumnWidth(STATUS_MESSAGE, 15000);
        errorDetailSheet.setColumnWidth(REQUEST_NO, 5000);
        errorDetailSheet.setColumnWidth(SET_NO, 5000);
        errorDetailSheet.setColumnWidth(ARTICLE, 5000);
        errorDetailSheet.setColumnWidth(MC, 5000);
        errorDetailSheet.setColumnWidth(CONFIG_SET, 5000);
        errorDetailSheet.setColumnWidth(QTY, 5000);
        errorDetailSheet.setColumnWidth(QUALIFYING_AMT, 5000);
        errorDetailSheet.setColumnWidth(DISCOUNT_TYPE, 5000);
        errorDetailSheet.setColumnWidth(DISCOUNT_VALUE, 5000);
    }
}
