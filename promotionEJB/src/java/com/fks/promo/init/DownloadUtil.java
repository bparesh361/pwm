/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.ExcelUtil;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.entity.MapPromoFormat;
import com.fks.promo.entity.MapPromoStore;
import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.promo.init.vo.DownloadEnum;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
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
@LocalBean
@Stateless
public class DownloadUtil {

    @EJB
    CommonPromotionDao commonPromoDao;

    public String downloadMultipleSubPromoExcel(List<TransPromo> transPromoList, Long empId, DownloadEnum downloadOpt, Logger logger) {
        String filePath = null;
        try {

            String fileName = empId + "_SubPromotionDtl_" + CommonUtil.getCurrentTimeInMiliSeconds() + ".xlsx";
            filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.MULTIPLE_SUB_PROMO_DOWNLOAD_FILE) + fileName;


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheetName = workbook.createSheet("Promotion Detail");
            XSSFRow dataRow = null;
            XSSFCell dataCell = null;

            XSSFFont font = ExcelUtil.getHeaderFont(workbook);
            XSSFCellStyle styleHeader = ExcelUtil.getHeaderStyle(workbook, font);
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(workbook);

            int rowNum = 0;

            generateReportHeader(rowNum, dataRow, dataCell, sheetName, styleHeader, downloadOpt);

            rowNum++;
            switch (downloadOpt) {
                case INITIATOR_TO_EXIGENCY_DOWNLOAD:
                    generateINITIATOR_TO_EXIGENCY_DOWNLOAD_ReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, transPromoList, empId, logger);
                    break;
                case MANAGER_EXECUTOR_DOWNLOAD:
                    generateMANAGER_EXECUTOR_DOWNLOAD_ReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, transPromoList, empId, logger);
                    break;
                case COMMUNICATION_DOWNLOAD:
                    generateCOMMUNICATION_DOWNLOAD_ReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, transPromoList, empId, logger);
                    break;
            }

            //create freepane
            sheetName.createFreezePane(0, 1);

            //resizing the columns
            autoSizeColumns(sheetName, downloadOpt);

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

    private void generateINITIATOR_TO_EXIGENCY_DOWNLOAD_ReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, List<TransPromo> transPromoList, Long empId, Logger logger) {
        int headerRowNum = 0;
        int maxRowCount = 0;
        int maxArticleSetNo = 0;
        for (TransPromo subPromo : transPromoList) {

            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            //header row number
            headerRowNum = rowNum;

            // Max Row Count
            maxRowCount = rowNum;

            //Table Details
            MstPromo masterPromo = subPromo.getMstPromo();
            Collection<MapPromoZone> zoneList = masterPromo.getMapPromoZoneCollection();
            Collection<MapPromoStore> storeList = masterPromo.getMapPromoStoreCollection();
            Collection<MapPromoFormat> formatList = masterPromo.getMapPromoFormatCollection();
            Collection<TransPromoArticle> articleList = subPromo.getTransPromoArticleCollection();
            List<TransPromoConfig> configList = (List<TransPromoConfig>) subPromo.getTransPromoConfigCollection();

            //Get Promo Type ID
            Long promoTypeId = subPromo.getMstPromotionType().getPromoTypeId();

            //Column Request No
            String requestNo = "T" + masterPromo.getPromoId() + "-R" + subPromo.getTransPromoId();
            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REQUEST_NO, requestNo, dataRow, cell, sheetName, cellStyle);

            //Column Remarks
            String remarks = subPromo.getRemarks();
            if (remarks != null) {
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REMARKS, remarks, dataRow, cell, sheetName, cellStyle);
            }

            //fill the zone from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Zone
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
                    String zoneDesc = zone.getMstZone().getZoneName();
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ZONE, zoneDesc, dataRow, cell, sheetName, cellStyle);
                    if (zoneList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the site from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Site , Site Desc
            if (storeList != null && storeList.size() > 0) {
                for (MapPromoStore store : storeList) {
                    String storeCode = store.getMstStore().getMstStoreId();
                    String storeDesc = store.getMstStore().getSiteDescription();

                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SIE_CODE, storeCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SITE_DESC, storeDesc, dataRow, cell, sheetName, cellStyle);
                    if (storeList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the format from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Format
            if (formatList != null && formatList.size() > 0) {

                for (MapPromoFormat format : formatList) {
                    String formatDesc = format.getFormatDesc();
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.FORMAT, formatDesc, dataRow, cell, sheetName, cellStyle);
                    if (formatList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the config detail header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Promotion Type
            String promoType = subPromo.getMstPromotionType().getPromoTypeName();
            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.PROMO_TYPE, promoType, dataRow, cell, sheetName, cellStyle);

            //Column valid Date From , valid Date To , Cashier Trigger , Growth In Ticket Size
            //Column Sell thru qty , Growth In Conversion , Sales Growth For Value Qty
            if (configList != null && configList.size() > 0) {

                TransPromoConfig configVO = null;
                if (promoTypeId == 1) {
                    if (configList.size() > 1) {
                        configVO = configList.get(1);
                    } else {
                        configVO = configList.get(0);
                    }
                } else {
                    configVO = configList.get(0);
                }
                String validFrom = CommonUtil.dispayFileDateFormat(configVO.getValidFrom());
                String validTo = CommonUtil.dispayFileDateFormat(configVO.getValidTo());
                double marginAchievement = configVO.getMarginAchivement();
                double growthTicketSize = configVO.getTicketSizeGrowth();
                double sellThruQty = configVO.getSellthruvsQty();
                double growthInConversion = configVO.getGrowthConversion();
                double salesGrowth = configVO.getSalesGrowth();

                // Valid From-to Dates
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_FROM, validFrom, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_TO, validTo, dataRow, cell, sheetName, cellStyle);

                // Column Margin Achivement
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, marginAchievement, dataRow, cell, sheetName, cellStyle);

                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE, growthTicketSize, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY, sellThruQty, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION, growthInConversion, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE, salesGrowth, dataRow, cell, sheetName, cellStyle);

            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);


            //fill the config detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Single Config Promo
            //Column buy worth amt For Promo Id 5 ,6
            //Coumn Config Qty For Promo ID 6
            //Column Discount Type , Discount Value , Discout Qty , Set
            if (configList != null && configList.size() > 0) {

                if (promoTypeId == 1 || promoTypeId == 2) {
                    for (TransPromoConfig configVO : configList) {
                        String discountType = configVO.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }

                        if (configVO.getSetId() != null) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);

                        double discountValue = configVO.getDiscountValue();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);
                        int qty = 0;
                        if (configVO.getQty() != null && configVO.getQty() != 0) {
                            qty = configVO.getQty();
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                        rowNum++;
                    }
                } else {
                    TransPromoConfig configVO = configList.get(0);

                    String discountType = configVO.getDiscountConfig();
                    if (discountType.equalsIgnoreCase("Percentage Off")) {
                        discountType = "%";
                    }
                    double discountValue = configVO.getDiscountValue();


                    if (promoTypeId == 3 || promoTypeId == 5) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 3) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 4) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        double buyWorthAmt = configVO.getTicketWorthAmt();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QUALIFYING_AMT, buyWorthAmt, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, 1, dataRow, cell, sheetName, cellStyle);


                        if (promoTypeId == 6) {
                            rowNum++;

                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);


                            if (configVO.getQty() != null && configVO.getQty() != 0) {
                                int qty = configVO.getQty();
                                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                            }
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, 2, dataRow, cell, sheetName, cellStyle);
                        }

                    }

                    if (promoTypeId == 7) {
                        int buyQty = subPromo.getBuyQty();
                        int getQty = subPromo.getGetQty();

                        if (buyQty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, buyQty, dataRow, cell, sheetName, cellStyle);
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, 1, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);

                        rowNum++;
                        if (getQty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, getQty, dataRow, cell, sheetName, cellStyle);
                        }
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the config detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Multiple Set , Discount Type , Discount Value  For PromoType Id 1
            //Column Repeating Set value And Multiple  Qty , Discount Value  For PromoType Id 4
//            if (configList != null && configList.size() > 0 && (promoTypeId == 1 || promoTypeId == 4)) {
            if (configList != null && configList.size() > 0 && (promoTypeId == 4)) {

                for (TransPromoConfig config : configList) {

                    double discountValue = config.getDiscountValue();

                    if (config.getSetId() != null) {
                        int set = config.getSetId();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, set, dataRow, cell, sheetName, cellStyle);
                    }
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);


                    if (promoTypeId == 1) {
                        String discountType = config.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);

                        maxArticleSetNo = commonPromoDao.getAllTransPromoMaxArticleSetNo(subPromo.getTransPromoId());
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, maxArticleSetNo, dataRow, cell, sheetName, cellStyle);

                    } else {
                        int qty = config.getQty();
                        if (qty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                        }
                    }
                    rowNum++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the article detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Article Qty , Set No , Article , Article Desc , MC Code , MC Desc
            if (articleList != null && articleList.size() > 0) {
                int setNo = 1;
                int tempSetNo = 0;

                for (TransPromoArticle article : articleList) {
                    if (article.getSetId() != null) {
                        setNo = article.getSetId();
                        if (setNo > maxArticleSetNo) {
                            maxArticleSetNo = setNo;
                        }
                    }

                    String articleCode = article.getArticle();
                    String articleDesc = article.getArticleDesc();
                    String mcCode = article.getMcCode();
                    String mcDesc = article.getMcDesc();
                    int qty = 0;

                    //For displaying set no one time
//                        if (tempSetNo != setNo) {
//                            tempSetNo = setNo;
//                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE_SET, promoRowCount, setNo));
//                        }

                    //For displaying set no in every row                    
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_SET, setNo, dataRow, cell, sheetName, cellStyle);

                    if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE, articleCode, dataRow, cell, sheetName, cellStyle);
                    }
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_DESC, articleDesc, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC, mcCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC_DESC, mcDesc, dataRow, cell, sheetName, cellStyle);

                    if (article.getQty() != null && article.getQty() != 0) {
                        qty = article.getQty();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                    }

                    rowNum++;
                }
            }

            /*
            //Generate File On DB Path
            String dbArticleFilePath = getArticleFileOnDBPath(subPromo.getTransPromoId(), empId, logger);

            //fill Article Details into Excel
            rowNum = fillArticleDetail(rowNum, dataRow, cell, sheetName, cellStyle, dbArticleFilePath, logger);

             */


            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            rowNum = maxRowCount + 1;

        }
    }

    private void generateMANAGER_EXECUTOR_DOWNLOAD_ReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, List<TransPromo> transPromoList, Long empId, Logger logger) {
        int headerRowNum = 0;
        int maxRowCount = 0;
        int maxArticleSetNo = 0;
        for (TransPromo subPromo : transPromoList) {

            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            //header row number
            headerRowNum = rowNum;

            // Max Row Count
            maxRowCount = rowNum;

            //Table Details
            MstPromo masterPromo = subPromo.getMstPromo();
            Collection<MapPromoZone> zoneList = masterPromo.getMapPromoZoneCollection();
            Collection<MapPromoStore> storeList = masterPromo.getMapPromoStoreCollection();
            Collection<MapPromoFormat> formatList = masterPromo.getMapPromoFormatCollection();
            Collection<TransPromoArticle> articleList = subPromo.getTransPromoArticleCollection();
            List<TransPromoConfig> configList = (List<TransPromoConfig>) subPromo.getTransPromoConfigCollection();

            //Get Promo Type ID
            Long promoTypeId = subPromo.getMstPromotionType().getPromoTypeId();

            //Column Request No
            String requestNo = "T" + masterPromo.getPromoId() + "-R" + subPromo.getTransPromoId();
            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REQUEST_NO, requestNo, dataRow, cell, sheetName, cellStyle);

            //Column Remarks
            String remarks = subPromo.getRemarks();
            if (remarks != null) {
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REMARKS - 5, remarks, dataRow, cell, sheetName, cellStyle);
            }

            //fill the zone from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Zone
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
                    String zoneDesc = zone.getMstZone().getZoneName();
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ZONE, zoneDesc, dataRow, cell, sheetName, cellStyle);
                    if (zoneList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the site from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Site , Site Desc
            if (storeList != null && storeList.size() > 0) {
                for (MapPromoStore store : storeList) {
                    String storeCode = store.getMstStore().getMstStoreId();
                    String storeDesc = store.getMstStore().getSiteDescription();

                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SIE_CODE, storeCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SITE_DESC, storeDesc, dataRow, cell, sheetName, cellStyle);
                    if (storeList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the format from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Format
            if (formatList != null && formatList.size() > 0) {

                for (MapPromoFormat format : formatList) {
                    String formatDesc = format.getFormatDesc();
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.FORMAT, formatDesc, dataRow, cell, sheetName, cellStyle);
                    if (formatList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the config detail header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Promotion Type
            String promoType = subPromo.getMstPromotionType().getPromoTypeName();
            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.PROMO_TYPE, promoType, dataRow, cell, sheetName, cellStyle);

            //Column valid Date From , valid Date To , Cashier Trigger , Growth In Ticket Size
            //Column Sell thru qty , Growth In Conversion , Sales Growth For Value Qty
            if (configList != null && configList.size() > 0) {

                TransPromoConfig configVO = null;
                if (promoTypeId == 1) {
                    if (configList.size() > 1) {
                        configVO = configList.get(1);
                    } else {
                        configVO = configList.get(0);
                    }
                } else {
                    configVO = configList.get(0);
                }
                String validFrom = CommonUtil.dispayFileDateFormat(configVO.getValidFrom());
                String validTo = CommonUtil.dispayFileDateFormat(configVO.getValidTo());


                // Valid From-to Dates
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_FROM, validFrom, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_TO, validTo, dataRow, cell, sheetName, cellStyle);


            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);


            //fill the config detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Single Config Promo
            //Column buy worth amt For Promo Id 5 ,6
            //Coumn Config Qty For Promo ID 6
            //Column Discount Type , Discount Value , Discout Qty , Set
            if (configList != null && configList.size() > 0) {

                if (promoTypeId == 1 || promoTypeId == 2) {
                    for (TransPromoConfig configVO : configList) {
                        String discountType = configVO.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }

                        if (configVO.getSetId() != null) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);

                        double discountValue = configVO.getDiscountValue();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);
                        int qty = 0;
                        if (configVO.getQty() != null && configVO.getQty() != 0) {
                            qty = configVO.getQty();
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                        rowNum++;
                    }
                } else {
                    TransPromoConfig configVO = configList.get(0);

                    String discountType = configVO.getDiscountConfig();
                    if (discountType.equalsIgnoreCase("Percentage Off")) {
                        discountType = "%";
                    }
                    double discountValue = configVO.getDiscountValue();


                    if (promoTypeId == 3 || promoTypeId == 5) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 2) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 3) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 4) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        double buyWorthAmt = configVO.getTicketWorthAmt();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QUALIFYING_AMT, buyWorthAmt, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, 1, dataRow, cell, sheetName, cellStyle);


                        if (promoTypeId == 6) {
                            rowNum++;

                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);


                            if (configVO.getQty() != null && configVO.getQty() != 0) {
                                int qty = configVO.getQty();
                                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                            }
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, 2, dataRow, cell, sheetName, cellStyle);
                        }

                    }

                    if (promoTypeId == 7) {
                        int buyQty = subPromo.getBuyQty();
                        int getQty = subPromo.getGetQty();

                        if (buyQty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, buyQty, dataRow, cell, sheetName, cellStyle);
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, 1, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);

                        rowNum++;
                        if (getQty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, getQty, dataRow, cell, sheetName, cellStyle);
                        }
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the config detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Multiple Set , Discount Type , Discount Value  For PromoType Id 1
            //Column Repeating Set value And Multiple  Qty , Discount Value  For PromoType Id 4
            if (configList != null && configList.size() > 0 && (promoTypeId == 1 || promoTypeId == 4)) {
//            if (configList != null && configList.size() > 0 && (promoTypeId == 4)) {

                for (TransPromoConfig config : configList) {

                    double discountValue = config.getDiscountValue();

                    if (config.getSetId() != null) {
                        int set = config.getSetId();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, set, dataRow, cell, sheetName, cellStyle);
                    }
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);


                    if (promoTypeId == 1) {
                        String discountType = config.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);

                        maxArticleSetNo = commonPromoDao.getAllTransPromoMaxArticleSetNo(subPromo.getTransPromoId());
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, maxArticleSetNo, dataRow, cell, sheetName, cellStyle);

                    } else {
                        int qty = config.getQty();
                        if (qty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                        }
                    }
                    rowNum++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the article detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Article Qty , Set No , Article , Article Desc , MC Code , MC Desc
            if (articleList != null && articleList.size() > 0) {
                int setNo = 1;
                int tempSetNo = 0;

                for (TransPromoArticle article : articleList) {
                    if (article.getSetId() != null) {
                        setNo = article.getSetId();
                        if (setNo > maxArticleSetNo) {
                            maxArticleSetNo = setNo;
                        }
                    }

                    String articleCode = article.getArticle();
                    String articleDesc = article.getArticleDesc();
                    String mcCode = article.getMcCode();
                    String mcDesc = article.getMcDesc();
                    int qty = 0;

                    //For displaying set no one time
//                        if (tempSetNo != setNo) {
//                            tempSetNo = setNo;
//                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE_SET, promoRowCount, setNo));
//                        }

                    //For displaying set no in every row                    
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_SET, setNo, dataRow, cell, sheetName, cellStyle);

                    if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE, articleCode, dataRow, cell, sheetName, cellStyle);
                    }
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_DESC, articleDesc, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC, mcCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC_DESC, mcDesc, dataRow, cell, sheetName, cellStyle);

                    if (article.getQty() != null && article.getQty() != 0) {
                        qty = article.getQty();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, qty, dataRow, cell, sheetName, cellStyle);
                    }

                    rowNum++;
                }
            }

            /*
            //Generate File On DB Path
            String dbArticleFilePath = getArticleFileOnDBPath(promoTypeId, empId, logger);

            //fill Article Details into Excel
            rowNum = fillArticleDetail(rowNum, dataRow, cell, sheetName, cellStyle, dbArticleFilePath, logger);
             */
            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            rowNum = maxRowCount + 1;

        }
    }

    private void generateCOMMUNICATION_DOWNLOAD_ReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, List<TransPromo> transPromoList, Long empId, Logger logger) {
        int headerRowNum = 0;
        int maxRowCount = 0;
        int maxArticleSetNo = 0;
        for (TransPromo subPromo : transPromoList) {

            //New Row Creation
            dataRow = sheetName.createRow(rowNum);

            //header row number
            headerRowNum = rowNum;

            // Max Row Count
            maxRowCount = rowNum;

            //Table Details
            MstPromo masterPromo = subPromo.getMstPromo();
            Collection<MapPromoZone> zoneList = masterPromo.getMapPromoZoneCollection();
            Collection<MapPromoStore> storeList = masterPromo.getMapPromoStoreCollection();
            Collection<MapPromoFormat> formatList = masterPromo.getMapPromoFormatCollection();
            Collection<TransPromoArticle> articleList = subPromo.getTransPromoArticleCollection();
            List<TransPromoConfig> configList = (List<TransPromoConfig>) subPromo.getTransPromoConfigCollection();

            //Get Promo Type ID
            Long promoTypeId = subPromo.getMstPromotionType().getPromoTypeId();

            //Column Request No
            String requestNo = "T" + masterPromo.getPromoId() + "-R" + subPromo.getTransPromoId();
            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_REQUEST_NO, requestNo, dataRow, cell, sheetName, cellStyle);

            //Column Remarks
            String remarks = subPromo.getRemarks();
            if (remarks != null) {
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_REMARKS, remarks, dataRow, cell, sheetName, cellStyle);
            }

            //column PromoDtl
            String promoDtl = subPromo.getPromoDetails();
            if (promoDtl != null) {
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_DTL, promoDtl, dataRow, cell, sheetName, cellStyle);
            }

            //column Cahier Trigger
            String cashierTrigger = subPromo.getCashierTrigger();
            if (cashierTrigger != null) {
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CASHIER_TRIGGER, cashierTrigger, dataRow, cell, sheetName, cellStyle);
            }

            //fill the zone from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Zone
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
                    String zoneDesc = zone.getMstZone().getZoneName();
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ZONE, zoneDesc, dataRow, cell, sheetName, cellStyle);
                    if (zoneList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the site from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Site , Site Desc
            if (storeList != null && storeList.size() > 0) {
                for (MapPromoStore store : storeList) {
                    String storeCode = store.getMstStore().getMstStoreId();
                    String storeDesc = store.getMstStore().getSiteDescription();

                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_SIE_CODE, storeCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_SITE_DESC, storeDesc, dataRow, cell, sheetName, cellStyle);
                    if (storeList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the format from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Format
            if (formatList != null && formatList.size() > 0) {

                for (MapPromoFormat format : formatList) {
                    String formatDesc = format.getFormatDesc();
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_FORMAT, formatDesc, dataRow, cell, sheetName, cellStyle);
                    if (formatList.size() > 1) {
                        rowNum++;
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the config detail header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Promotion Type
            String promoType = subPromo.getMstPromotionType().getPromoTypeName();
            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_TYPE, promoType, dataRow, cell, sheetName, cellStyle);

            //Column valid Date From , valid Date To , Cashier Trigger , Growth In Ticket Size
            //Column Sell thru qty , Growth In Conversion , Sales Growth For Value Qty
            if (configList != null && configList.size() > 0) {

                TransPromoConfig configVO = null;
                if (promoTypeId == 1) {
                    if (configList.size() > 1) {
                        configVO = configList.get(1);
                    } else {
                        configVO = configList.get(0);
                    }
                } else {
                    configVO = configList.get(0);
                }
                String validFrom = CommonUtil.dispayFileDateFormat(configVO.getValidFrom());
                String validTo = CommonUtil.dispayFileDateFormat(configVO.getValidTo());


                // Valid From-to Dates
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_VALID_FROM, validFrom, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_VALID_TO, validTo, dataRow, cell, sheetName, cellStyle);


            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);


            //fill the config detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Single Config Promo
            //Column buy worth amt For Promo Id 5 ,6
            //Coumn Config Qty For Promo ID 6
            //Column Discount Type , Discount Value , Discout Qty , Set
            if (configList != null && configList.size() > 0) {

                if (promoTypeId == 1 || promoTypeId == 2) {
                    for (TransPromoConfig configVO : configList) {
                        String discountType = configVO.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }

                        if (configVO.getSetId() != null) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);

                        double discountValue = configVO.getDiscountValue();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);
                        int qty = 0;
                        if (configVO.getQty() != null && configVO.getQty() != 0) {
                            qty = configVO.getQty();
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, qty, dataRow, cell, sheetName, cellStyle);
                        rowNum++;
                    }
                } else {
                    TransPromoConfig configVO = configList.get(0);

                    String discountType = configVO.getDiscountConfig();
                    if (discountType.equalsIgnoreCase("Percentage Off")) {
                        discountType = "%";
                    }
                    double discountValue = configVO.getDiscountValue();

                    if (promoTypeId != 1 && promoTypeId != 4 && promoTypeId != 6 && promoTypeId != 7) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 3) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, configVO.getSetId(), dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 4) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        double buyWorthAmt = configVO.getTicketWorthAmt();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT, buyWorthAmt, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, 1, dataRow, cell, sheetName, cellStyle);


                        if (promoTypeId == 6) {
                            rowNum++;

                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);


                            if (configVO.getQty() != null && configVO.getQty() != 0) {
                                int qty = configVO.getQty();
                                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, qty, dataRow, cell, sheetName, cellStyle);
                            }
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, 2, dataRow, cell, sheetName, cellStyle);
                        }

                    }

                    if (promoTypeId == 7) {
                        int buyQty = subPromo.getBuyQty();
                        int getQty = subPromo.getGetQty();

                        if (buyQty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, buyQty, dataRow, cell, sheetName, cellStyle);
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, 1, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);

                        rowNum++;
                        if (getQty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, getQty, dataRow, cell, sheetName, cellStyle);
                        }
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the config detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Multiple Set , Discount Type , Discount Value  For PromoType Id 1
            //Column Repeating Set value And Multiple  Qty , Discount Value  For PromoType Id 4
            if (configList != null && configList.size() > 0 && (promoTypeId == 1 || promoTypeId == 4)) {
//            if (configList != null && configList.size() > 0 && (promoTypeId == 4)) {

                for (TransPromoConfig config : configList) {

                    double discountValue = config.getDiscountValue();

                    if (config.getSetId() != null) {
                        int set = config.getSetId();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, set, dataRow, cell, sheetName, cellStyle);
                    }
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, discountValue, dataRow, cell, sheetName, cellStyle);


                    if (promoTypeId == 1) {
                        String discountType = config.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, discountType, dataRow, cell, sheetName, cellStyle);

                        maxArticleSetNo = commonPromoDao.getAllTransPromoMaxArticleSetNo(subPromo.getTransPromoId());
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, maxArticleSetNo, dataRow, cell, sheetName, cellStyle);

                    } else {
                        int qty = config.getQty();
                        if (qty != 0) {
                            ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, qty, dataRow, cell, sheetName, cellStyle);
                        }
                    }
                    rowNum++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            //fill the article detail from header row so set rownum to header row
            rowNum = headerRowNum;

            //Column Article Qty , Set No , Article , Article Desc , MC Code , MC Desc
            if (articleList != null && articleList.size() > 0) {
                int setNo = 1;
                int tempSetNo = 0;

                for (TransPromoArticle article : articleList) {
                    if (article.getSetId() != null) {
                        setNo = article.getSetId();
                        if (setNo > maxArticleSetNo) {
                            maxArticleSetNo = setNo;
                        }
                    }

                    String articleCode = article.getArticle();
                    String articleDesc = article.getArticleDesc();
                    String mcCode = article.getMcCode();
                    String mcDesc = article.getMcDesc();
                    String brandCode = "-";
                    if (article.getBrandCode() != null) {
                        brandCode = article.getBrandCode();
                    }
                    String brandDesc = "-";
                    if (article.getBrandDesc() != null) {
                        brandDesc = article.getBrandDesc();
                    }
                    int qty = 0;

                    //For displaying set no one time
//                        if (tempSetNo != setNo) {
//                            tempSetNo = setNo;
//                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE_SET, promoRowCount, setNo));
//                        }

                    //For displaying set no in every row                    
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, setNo, dataRow, cell, sheetName, cellStyle);

                    if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, articleCode, dataRow, cell, sheetName, cellStyle);
                    }
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, articleDesc, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_MC, mcCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, mcDesc, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, brandCode, dataRow, cell, sheetName, cellStyle);
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, brandDesc, dataRow, cell, sheetName, cellStyle);

                    if (article.getQty() != null && article.getQty() != 0) {
                        qty = article.getQty();
                        ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, qty, dataRow, cell, sheetName, cellStyle);
                    }

                    rowNum++;
                }
            }

            /*
            //Generate File On DB Path
            String dbArticleFilePath = getArticleFileOnDBPath(promoTypeId, empId, logger);

            //fill Article Details into Excel
            rowNum = fillCoomunicationArticleDetail(rowNum, dataRow, cell, sheetName, cellStyle, dbArticleFilePath, logger);


             */
            //get Max Row Count
            maxRowCount = getMaxRowNumber(maxRowCount, rowNum);

            rowNum = maxRowCount + 1;

        }
    }

    private int fillArticleDetail(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, String filePath, Logger logger) {
        BufferedReader br = null;
        try {
            //            String csvFileAddress = "c:\\article_one.csv"; //csv file address
            String csvFileAddress = filePath;
            String currentLine = null;
            br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                String setId = str[0];
                String article = str[1];
                String article_desc = str[2];
                String mc = str[3];
                String mc_desc = str[4];

                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_SET, setId, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE, article, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_DESC, article_desc, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC, mc, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC_DESC, mc_desc, dataRow, cell, sheetName, cellStyle);

                rowNum++;
            }
        } catch (Exception ex) {
            logger.info("---- error message : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(DownloadUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return rowNum;
    }

    private int fillCoomunicationArticleDetail(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, String filePath, Logger logger) {
        try {
            String csvFileAddress = "c:\\article_comm.csv"; //csv file address
            String currentLine = null;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                String setId = str[0];
                String article = str[1];
                String article_desc = str[2];
                String mc = str[3];
                String mc_desc = str[4];
                String brand = str[5];
                String brand_desc = str[6];
                String article_qty = str[7];

                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, setId, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, article, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, article_desc, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_MC, mc, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, mc_desc, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, brand, dataRow, cell, sheetName, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, brand_desc, dataRow, cell, sheetName, cellStyle);

                if (article_qty != null && (article_qty == null ? "0" != null : !article_qty.equals("0"))) {
                    ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, article_qty, dataRow, cell, sheetName, cellStyle);
                }
                rowNum++;
            }
        } catch (Exception ex) {
            logger.info("---- error message : " + ex.getMessage());
            ex.printStackTrace();
        }
        return rowNum;
    }

    private String getArticleFileOnDBPath(Long trans_promo_id, Long empId, Logger logger) {
        String filePath = null;
        try {
            String fileName = empId + "_article_" + CommonUtil.getCurrentTimeInMiliSeconds() + ".csv";
            filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.ARTICLE_DB_EXCEL_FILE) + fileName;
            System.out.println("#################### article file path : " + filePath);
            int status = commonPromoDao.getTransPromoArticleForDownload(trans_promo_id, filePath);
            logger.info("article dump query status : " + status);
            System.out.println("####### article dump query status : " + status);
            return filePath;
        } catch (Exception ex) {
            logger.error("--- error : " + ex.getMessage());
            return filePath;
        }
    }

    private void generateReportHeader(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle, DownloadEnum downloadOpt) {

        dataRow = hssfSheet.createRow(rowNum);

        switch (downloadOpt) {
            case INITIATOR_TO_EXIGENCY_DOWNLOAD:
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REQUEST_NO, "Request No", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ZONE, "Zone", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SIE_CODE, "Site Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SITE_DESC, "Site Description", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.FORMAT, "Format", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.PROMO_TYPE, "Promo Type", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, "Discount Type", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, "Discount Value", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, "Qty", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, "No Of Set", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QUALIFYING_AMT, "Qualifying Amt/Buy Worth", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_SET, "Sales Set No", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE, "Article", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_DESC, "Article Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC, "MC Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC_DESC, "MC Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_FROM, "Valid Date From", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_TO, "Valid Date To", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, "Expected Margin IN", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE, "Growth IN Ticket Size", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY, "Expected Sales Qty growth", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION, "Growth In Conversions", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE, "Expected Sales value growth", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REMARKS, "Remarks", dataRow, cell, hssfSheet, cellStyle);

                break;

            case MANAGER_EXECUTOR_DOWNLOAD:
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REQUEST_NO, "Request No", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ZONE, "Zone", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SIE_CODE, "Site Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.SITE_DESC, "Site Description", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.FORMAT, "Format", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.PROMO_TYPE, "Promo Type", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_TYPE, "Discount Type", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.DISCOUNT_VALUE, "Discount Value", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QTY, "Qty", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.CONFIG_SET, "No Of Set", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.QUALIFYING_AMT, "Qualifying Amt/Buy Worth", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_SET, "Sales Set No", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE, "Article", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.ARTICLE_DESC, "Article Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC, "MC Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.MC_DESC, "MC Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_FROM, "Valid Date From", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.VALID_TO, "Valid Date To", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.REMARKS - 5, "Remarks", dataRow, cell, hssfSheet, cellStyle);
                break;

            case COMMUNICATION_DOWNLOAD:
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_REQUEST_NO, "Request No", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ZONE, "Zone", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_SIE_CODE, "Site Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_SITE_DESC, "Site Description", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_FORMAT, "Format", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_TYPE, "Promo Type", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_DTL, "Promo Detail", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, "Discount Type", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, "Discount Value", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QTY, "Qty", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, "No Of Set", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT, "Qualifying Amt/Buy Worth", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, "Sales Set No", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, "Article", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, "Article Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_MC, "MC Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, "MC Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, "Brand Code", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, "Brand Desc", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_VALID_FROM, "Valid Date From", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_VALID_TO, "Valid Date To", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_REMARKS, "Remarks", dataRow, cell, hssfSheet, cellStyle);
                ExcelUtil.fillCell(rowNum, DownloadMulitiplePromoConstant.COMMUNICATION_CASHIER_TRIGGER, "Cashier Trigger", dataRow, cell, hssfSheet, cellStyle);
                break;
        }

    }

    private void autoSizeColumns(XSSFSheet hssfSheet, DownloadEnum downloadOpt) {
        try {
            switch (downloadOpt) {
                case INITIATOR_TO_EXIGENCY_DOWNLOAD:
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.REQUEST_NO, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ZONE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.SIE_CODE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.SITE_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.FORMAT, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.PROMO_TYPE, 9000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, 5000);

                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.QTY, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.CONFIG_SET, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.QUALIFYING_AMT, 7000);

                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ARTICLE_SET, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ARTICLE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ARTICLE_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.MC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.MC_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.VALID_FROM, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.VALID_TO, 5000);

                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE, 7000);

                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.REMARKS, 7000);
                    break;

                case MANAGER_EXECUTOR_DOWNLOAD:
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.REQUEST_NO, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ZONE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.SIE_CODE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.SITE_DESC, 10000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.FORMAT, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.PROMO_TYPE, 9000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.QTY, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.CONFIG_SET, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.QUALIFYING_AMT, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ARTICLE_SET, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ARTICLE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.ARTICLE_DESC, 8000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.MC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.MC_DESC, 8000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.VALID_FROM, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.VALID_TO, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.REMARKS - 5, 7000);
                    break;

                case COMMUNICATION_DOWNLOAD:
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_REQUEST_NO, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_ZONE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_SIE_CODE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_SITE_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_FORMAT, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_TYPE, 9000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_DTL, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, 5000);
                    //                    hssfSheet.autoSizeColumn(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE);
                    //                    hssfSheet.autoSizeColumn(DownloadMulitiplePromoConstant.COMMUNICATION_QTY);
                    //                    hssfSheet.autoSizeColumn(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET);
                    //                    hssfSheet.autoSizeColumn(DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_MC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_FROM, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_TO, 5000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_REMARKS, 7000);
                    hssfSheet.setColumnWidth(DownloadMulitiplePromoConstant.COMMUNICATION_CASHIER_TRIGGER, 5000);
                    break;
            }
        } catch (Exception ex) {
            System.out.println("---- error :" + ex.getMessage());
            ex.printStackTrace();
        }


    }

    private int getMaxRowNumber(int maxRowCount, int rowNumber) {
        if (maxRowCount > rowNumber) {
            return maxRowCount;
        }
        return rowNumber;
    }
}
