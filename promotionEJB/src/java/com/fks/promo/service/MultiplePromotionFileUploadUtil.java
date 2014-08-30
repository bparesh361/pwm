/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.ods.service.ODSService;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
@Stateless
public class MultiplePromotionFileUploadUtil {

    @EJB
    ODSService odsService;
    @EJB
    OtherMasterService otherService;
    private static final Logger logger = Logger.getLogger(MultiplePromotionFileUploadUtil.class.getName());
    private static SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
    private static SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd");

    public CreateMultiPlePromoReq validateFile(String filePath, MstPromo mstPromo, String zoneId, String empId) {
//        System.out.println("------------- Inside Validating FIle : " + filePath);
        CreateMultiPlePromoReq req = new CreateMultiPlePromoReq();
        Workbook book = null;
        WritableWorkbook errorBook = null;
        try {
            String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.SUB_PROMO_REQUEST_FILE);
            Calendar cal = new GregorianCalendar();
//            String requestName = mstPromo.getRequestName().replaceAll("", "_");
            String errorFileName = errorFilePath + "/" + "status_T-" + mstPromo.getPromoId() + "_" + cal.getTimeInMillis() + ".xls";
            errorBook = getBlankErrorWorkbook(errorFileName);

            book = Workbook.getWorkbook(new File(filePath));
            Sheet headerSheet = book.getSheet(0);

            ValidatePromoHeaderFileDtl validateHeader = validateHeaderSheet(headerSheet, errorBook.getSheet(0));
            if (validateHeader.isStatus()) {

                WritableSheet headerErrorSheet = errorBook.getSheet(0);
                headerErrorSheet.addCell(new Label(0, 1, "Success"));
                headerErrorSheet.addCell(new Label(1, 1, "Success"));

                Sheet detailSheet = book.getSheet(1);
                if (validateDetailSheet(detailSheet, errorBook.getSheet(1), validateHeader.getRequestList(), mstPromo.getPromoId())) {

                    WritableSheet detailErrorSheet = errorBook.getSheet(1);
                    detailErrorSheet.addCell(new Label(0, 1, "Success"));
                    detailErrorSheet.addCell(new Label(1, 1, "Success"));

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
            errorBook.write();
        } catch (Exception ex) {
            logger.error("------------- Exception In Processing validateFile() MultiPromoFile : " + ex.getMessage());
            ex.printStackTrace();
            req.setErrorFlag(true);
        } finally {
            if (book != null) {
                book.close();
            }
            if (errorBook != null) {
                try {
                    errorBook.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(MultiplePromotionFileUploadUtil.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WriteException ex) {
                    java.util.logging.Logger.getLogger(MultiplePromotionFileUploadUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return req;
    }

    private List<TransPromoVO> getPromoArticleList(Sheet detailSheet, Hashtable<String, String> requestNoData, Long mstPromoId) throws ParseException {

        System.out.println("------ Inside Creating Promo Detail List Objects ------");
        List<TransPromoVO> promoList = new ArrayList<TransPromoVO>();

        Date dummyDate;
        int promotionRequestWiseRowCount = 1;
        int maxRows = detailSheet.getRows() - 1;
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
        for (int i = 1; i <= maxRows; i++) {
            //reset articleVO
            articleVO = null;
            //detail file fields
            String tempRequestNo = detailSheet.getCell(0, i).getContents().trim();
            String articleSetNo = detailSheet.getCell(1, i).getContents().trim();
            String article = detailSheet.getCell(2, i).getContents().trim();
            String mc = detailSheet.getCell(3, i).getContents().trim();
            String configSet = detailSheet.getCell(4, i).getContents().trim();
            String qty = detailSheet.getCell(5, i).getContents().trim();
            String qualifyingAmt = detailSheet.getCell(6, i).getContents().trim();
            String discType = detailSheet.getCell(7, i).getContents().trim();
            String discValue = detailSheet.getCell(8, i).getContents().trim();

            if (validateNullCellValue(article) && validateNullCellValue(mc) && validateNullCellValue(configSet) && validateNullCellValue(qty) && validateNullCellValue(qualifyingAmt) && validateNullCellValue(discType) && validateNullCellValue(discValue)) {
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
            if (!validateNullCellValue(article) || !validateNullCellValue(mc)) {
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



            if (!validateNullCellValue(article)) {
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


            if (!validateNullCellValue(mc)) {
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

    private ValidatePromoHeaderFileDtl getPromoRequestList(Sheet headerSheet) {
        ValidatePromoHeaderFileDtl req = new ValidatePromoHeaderFileDtl();

        Hashtable<String, String> requestNoData = new Hashtable<String, String>();
        int maxRows = headerSheet.getRows() - 1;
//        System.out.println("-------------------------- Max Row : " + maxRows);
        for (int i = 1; i <= maxRows; i++) {
            String requestNo = headerSheet.getCell(0, i).getContents().trim();
            String promoType = headerSheet.getCell(1, i).getContents().trim();
            String remarks = headerSheet.getCell(2, i).getContents().trim();
            String marginAchievement = headerSheet.getCell(3, i).getContents().trim();
            String sellThruVsQty = headerSheet.getCell(4, i).getContents().trim();
            String saleGrowthInQtyValue = headerSheet.getCell(5, i).getContents().trim();
            String growthTicketSize = headerSheet.getCell(6, i).getContents().trim();
            String growthInConversion = headerSheet.getCell(7, i).getContents().trim();
            String validFrom = headerSheet.getCell(8, i).getContents().trim();
            String validTo = headerSheet.getCell(9, i).getContents().trim();

            if (validateNullCellValue(requestNo) && validateNullCellValue(promoType)) {
                break;
            }

            if (remarks.contains(",")) {
                remarks = remarks.replaceAll(",", " ");
            }
//            System.out.println("i : " + i);
//            System.out.println(promoType + "," + remarks + "," + marginAchievement + "," + growthTicketSize + "," + sellThruVsQty + "," + growthInConversion + "," + saleGrowthInQtyValue + "," + validFrom + "," + validTo);
//            requestNoData.put(requestNo, promoType + "," + remarks + "," + marginAchievement + "," + growthTicketSize + "," + sellThruVsQty + "," + growthInConversion + "," + saleGrowthInQtyValue + "," + validFrom + "," + validTo);
            requestNoData.put(requestNo, promoType + "," + remarks + "," + marginAchievement + "," + sellThruVsQty + "," + saleGrowthInQtyValue + "," + growthTicketSize + "," + growthInConversion + "," + validFrom + "," + validTo);
        }
        req.setRequestNoData(requestNoData);
        return req;
    }

    private ValidatePromoHeaderFileDtl validateHeaderSheet(Sheet headerSheet, WritableSheet headerErrorSheet) {
        boolean finalSuccessFlag = true;
        boolean successFlag = true;
        ValidatePromoHeaderFileDtl resp = new ValidatePromoHeaderFileDtl();
        try {
//            System.out.println("---------- Header File Max Rows : " + headerSheet.getRows());

            int maxCols = headerSheet.getColumns();
            System.out.println("--- MAx Cols : " + maxCols);
            if (maxCols < 10) {
                headerErrorSheet.addCell(new Label(0, 1, "Failure"));
                headerErrorSheet.addCell(new Label(1, 1, "Total No Of Columns Required Is 10."));
                successFlag = false;
                resp.setStatus(false);
                return resp;
            }

            int maxRows = headerSheet.getRows() - 1;
            if (maxRows <= 0) {
                headerErrorSheet.addCell(new Label(0, 1, "Failure"));
                headerErrorSheet.addCell(new Label(1, 1, "Empty Header Sheet Found."));
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
            int errorRowCount = 1;
            for (int i = 1; i <= maxRows; i++) {

                String requestNo = headerSheet.getCell(0, i).getContents().trim();
                String promoType = headerSheet.getCell(1, i).getContents().trim();
                String remarks = headerSheet.getCell(2, i).getContents().trim();
                String marginAchievement = headerSheet.getCell(3, i).getContents().trim();

                String sellThruVsQty = headerSheet.getCell(4, i).getContents().trim();
                String saleGrowthInQtyValue = headerSheet.getCell(5, i).getContents().trim();
                String growthTicketSize = headerSheet.getCell(6, i).getContents().trim();
                String growthInConversion = headerSheet.getCell(7, i).getContents().trim();

                String validFrom = headerSheet.getCell(8, i).getContents().trim();
                String validTo = headerSheet.getCell(9, i).getContents().trim();

                if (validateNullCellValue(requestNo) && validateNullCellValue(promoType) && validateNullCellValue(remarks) && validateNullCellValue(marginAchievement) && validateNullCellValue(sellThruVsQty) && validateNullCellValue(saleGrowthInQtyValue) && validateNullCellValue(validFrom) && validateNullCellValue(validTo)) {
                    break;
                }
                if (errorMessage.length() > 0) {
                    errorMessage.delete(0, errorMessage.length());
                }

//                headerErrorSheet.addCell(new Label(0, i, "Success"));
//                headerErrorSheet.addCell(new Label(2, i, requestNo));
//                headerErrorSheet.addCell(new Label(3, i, promoType));
//                headerErrorSheet.addCell(new Label(4, i, remarks));
//                headerErrorSheet.addCell(new Label(5, i, marginAchievement));
//                headerErrorSheet.addCell(new Label(6, i, sellThruVsQty));
//                headerErrorSheet.addCell(new Label(7, i, saleGrowthInQtyValue));
//                headerErrorSheet.addCell(new Label(8, i, growthTicketSize));
//                headerErrorSheet.addCell(new Label(9, i, growthInConversion));
//                headerErrorSheet.addCell(new Label(10, i, validFrom));
//                headerErrorSheet.addCell(new Label(11, i, validTo));

                if (validateNullCellValue(requestNo)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    errorMessage.append("Request No should not be blank.");
                    successFlag = false;
                }

                if (validateNullCellValue(promoType)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Promo Type should not be blank.");
                    successFlag = false;
                } else {
                    int promoTypeId = getTransPromoType(promoType);
                    if (promoTypeId == 0) {
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Invalid Promotion Type Found.");
                        successFlag = false;
                    }
                    requestList.put(requestNo, promoTypeId);
                    System.out.println("---- Promo Type Validated Successfully.");
                }

                if (validateNullCellValue(remarks)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Remarks should not be blank.");
                    successFlag = false;
                } else {
                    if (remarks.length() > 200) {
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }

                        errorMessage.append("Remarks should not be more than 200 characters.");
                        successFlag = false;
                    }

                    System.out.println("---- Remarks Validated Successfully.");
                }

                if (validateNullCellValue(marginAchievement)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Expected Margin should not be blank.");
                    successFlag = false;
                } else {
                    try {
                        tempValue = Double.parseDouble(marginAchievement);
//                        headerErrorSheet.addCell(new jxl.write.Number(5, i, tempValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        errorMessage.append("Expected Margin Must Be Numeric.");
                        successFlag = false;
                    }
                }

                if (validateNullCellValue(sellThruVsQty)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Expected Sales Qty growth should not be blank.");
                    successFlag = false;
                } else {
                    try {
                        tempValue = Double.parseDouble(sellThruVsQty);
//                        headerErrorSheet.addCell(new jxl.write.Number(6, i, tempValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        errorMessage.append("Expected Sales Qty growth Must Be Numeric.");
                        successFlag = false;
                    }
                }

                if (validateNullCellValue(saleGrowthInQtyValue)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Expected Sales value growth should not be blank.");
                    successFlag = false;
                } else {
                    try {
                        tempValue = Double.parseDouble(saleGrowthInQtyValue);
//                        headerErrorSheet.addCell(new jxl.write.Number(7, i, tempValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        errorMessage.append("Expected Sales value growth Must Be Numeric.");
                        successFlag = false;
                    }
                }

                /*
                if (validateNullCellValue(growthTicketSize)) {
                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                if (errorMessage.length() > 0) {
                errorMessage.append(" | ");
                }
                errorMessage.append("Growth IN Ticket size should not be blank.");
                successFlag = false;
                } else {
                try {
                tempValue = Double.parseDouble(growthTicketSize);
                headerErrorSheet.addCell(new jxl.write.Number(6, i, tempValue));
                } catch (Exception e) {
                e.printStackTrace();
                if (errorMessage.length() > 0) {
                errorMessage.append(" | ");
                }
                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                errorMessage.append("Growth in Ticket size Must Be Numeric.");
                successFlag = false;
                }
                }
                 */

                /*CR2 Change Growth In Ticket Size can be blank
                 */
                if (!validateNullCellValue(growthTicketSize)) {
                    try {
                        tempValue = Double.parseDouble(growthTicketSize);
//                        headerErrorSheet.addCell(new jxl.write.Number(8, i, tempValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        errorMessage.append("Growth in Ticket size Must Be Numeric.");
                        successFlag = false;
                    }
                }

                /*
                if (validateNullCellValue(growthInConversion)) {
                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                if (errorMessage.length() > 0) {
                errorMessage.append(" | ");
                }
                errorMessage.append("Growth in conversions should not be blank.");
                successFlag = false;
                } else {
                try {
                tempValue = Double.parseDouble(growthInConversion);
                headerErrorSheet.addCell(new jxl.write.Number(8, i, tempValue));
                } catch (Exception e) {
                e.printStackTrace();
                if (errorMessage.length() > 0) {
                errorMessage.append(" | ");
                }
                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                errorMessage.append("Growth in conversions Must Be Numeric.");
                successFlag = false;
                }
                }
                 */

                /*CR2 Change Growth In Conversions can be blank
                 */
                if (!validateNullCellValue(growthInConversion)) {
                    try {
                        tempValue = Double.parseDouble(growthInConversion);
//                        headerErrorSheet.addCell(new jxl.write.Number(9, i, tempValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
//                        headerErrorSheet.addCell(new Label(0, i, "Failure"));
                        errorMessage.append("Growth in conversions Must Be Numeric.");
                        successFlag = false;
                    }
                }

                if (validateNullCellValue(validFrom)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Valid From should not be blank.");
                    successFlag = false;
                } else {
                    String[] dateParts;
                    if (validFrom.contains("/")) {
                        dateParts = validFrom.split("/");
                    } else {
                        dateParts = validFrom.split("-");
                    }

                    if (dateParts != null) {
                        if (dateParts.length != 3) {
//                            headerErrorSheet.addCell(new Label(0, i, "Failure"));
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                            successFlag = false;
                        } else if (dateParts[2].length() != 4) {
//                            headerErrorSheet.addCell(new Label(0, i, "Failure"));
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid From 'dd-MMM-yyyy'.");
                            successFlag = false;
                        } else {
                            Resp dateResp = valdiateDate(dateParts[0], dateParts[1], dateParts[2], "Valid From");
                            if (dateResp.getRespCode() == RespCode.FAILURE) {
//                                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append(dateResp.getMsg());
                                successFlag = false;
                            }
                        }

                    }
                    if (successFlag) {
                        try {
                            validFrom = validFrom.replaceAll("/", "-");
                            fromDate = format.parse(validFrom);
                            fromDateTime = fromDate.getTime();
//                        headerErrorSheet.addCell(new jxl.write.DateTime(10, i, fromDate));

                            if (currentDateTime > fromDateTime) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
//                                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                                errorMessage.append("Valid From Date Must Be Greater Than Today's Date.");
                                successFlag = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
//                            headerErrorSheet.addCell(new Label(0, i, "Failure"));
                            errorMessage.append("Incorrect Date Format for Valid From.");
                            successFlag = false;
                        }
                    }

                }

                if (validateNullCellValue(validTo)) {
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
                    errorMessage.append("Valid TO should not be blank.");
                    successFlag = false;
                } else {
                    String[] dateParts;
                    if (validTo.contains("/")) {
                        dateParts = validTo.split("/");
                    } else {
                        dateParts = validTo.split("-");
                    }
                    if (dateParts != null) {
                        if (dateParts.length != 3) {
//                            headerErrorSheet.addCell(new Label(0, i, "Failure"));
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid To 'dd-MMM-yyyy'.");
                            successFlag = false;
                        } else if (dateParts[2].length() != 4) {
//                            headerErrorSheet.addCell(new Label(0, i, "Failure"));
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Invalid Date Format.Please Change Excel Date Format For Valid To 'dd-MMM-yyyy'.");
                            successFlag = false;
                        } else {
                            Resp dateResp = valdiateDate(dateParts[0], dateParts[1], dateParts[2], "Valid To");
                            if (dateResp.getRespCode() == RespCode.FAILURE) {
//                                headerErrorSheet.addCell(new Label(0, i, "Failure"));
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append(dateResp.getMsg());
                                successFlag = false;
                            }
                        }

                    }
                    if (successFlag) {
                        try {
                            validTo = validTo.replaceAll("/", "-");
                            toDate = format.parse(validTo);
                            toDateTime = toDate.getTime();
//                        headerErrorSheet.addCell(new jxl.write.DateTime(11, i, toDate));
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
//                            headerErrorSheet.addCell(new Label(0, i, "Failure"));
                            errorMessage.append("Incorrect Date Format for Valid To.");
                            successFlag = false;
                        }
                    }

                }

                if (successFlag & !validateNullCellValue(validFrom) && !validateNullCellValue(validTo) && fromDateTime != null && toDateTime != null && (toDateTime < fromDateTime)) {
                    if (errorMessage.length() > 0) {
                        errorMessage.append(" | ");
                    }
//                    headerErrorSheet.addCell(new Label(0, i, "Failure"));
                    errorMessage.append("Valid To Date Must Be Greater Than Valid From Date.");
                    successFlag = false;
                }
                System.out.println("---- All Header Validations Validated Successfully For Row : " + i);


                if (successFlag) {
//                    headerErrorSheet.addCell(new Label(0, i, "Success"));
//                    headerErrorSheet.addCell(new Label(1, i, "Success"));
                } else {
                    int excelRowNumer = i + 1;
                    headerErrorSheet.addCell(new Label(0, errorRowCount, "Failure"));
                    headerErrorSheet.addCell(new Label(1, errorRowCount, "Row Num : " + excelRowNumer + "  " + errorMessage.toString()));
                    headerErrorSheet.addCell(new Label(2, errorRowCount, requestNo));
                    headerErrorSheet.addCell(new Label(3, errorRowCount, promoType));
                    headerErrorSheet.addCell(new Label(4, errorRowCount, remarks));
                    headerErrorSheet.addCell(new Label(5, errorRowCount, marginAchievement));
                    headerErrorSheet.addCell(new Label(6, errorRowCount, sellThruVsQty));
                    headerErrorSheet.addCell(new Label(7, errorRowCount, saleGrowthInQtyValue));
                    headerErrorSheet.addCell(new Label(8, errorRowCount, growthTicketSize));
                    headerErrorSheet.addCell(new Label(9, errorRowCount, growthInConversion));
                    headerErrorSheet.addCell(new Label(10, errorRowCount, validFrom));
                    headerErrorSheet.addCell(new Label(11, errorRowCount, validTo));
                    successFlag = true;
                    finalSuccessFlag = false;
                    errorRowCount++;
                }
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

    private boolean validateDetailSheet(Sheet detailSheet, WritableSheet detailErrorSheet, Hashtable<String, Integer> requestList, Long mstPromoId) {
        boolean finalSuccessFlag = true;
        boolean successFlag = true;
        try {
            int maxCols = detailSheet.getColumns();
            System.out.println("--- MAx Cols : " + maxCols);
            if (maxCols < 9) {
                detailErrorSheet.addCell(new Label(0, 1, "Failure"));
                detailErrorSheet.addCell(new Label(1, 1, "Total No Of Columns Required Is 9."));
                return false;
            }
            int maxRows = detailSheet.getRows() - 1;
            System.out.println("--- MAx Rows : " + maxRows);
            if (maxRows <= 0) {
                detailErrorSheet.addCell(new Label(0, 1, "Failure"));
                detailErrorSheet.addCell(new Label(1, 1, "Empty Detail Sheet Found."));
                return false;
            }

            StringBuilder errorMessage = new StringBuilder("");
            String requestNo = "";
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
            // CR2 change finished


            int errorRowCount = 1;
            // every validations are checked column by column manner with every sequantial row reading
            for (int i = 1; i <= maxRows; i++) {
                String tempRequestNo = detailSheet.getCell(0, i).getContents();

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
                //get the fields values
                String articleSetNo = detailSheet.getCell(1, i).getContents().trim();
                String article = detailSheet.getCell(2, i).getContents().trim();
                String mc = detailSheet.getCell(3, i).getContents().trim();
                String configSet = detailSheet.getCell(4, i).getContents().trim();
                String qty = detailSheet.getCell(5, i).getContents().trim();
                String qualifyingAmt = detailSheet.getCell(6, i).getContents().trim();
                String discType = detailSheet.getCell(7, i).getContents().trim();
                String discValue = detailSheet.getCell(8, i).getContents().trim();
                System.out.println("-- Set No : " + articleSetNo + "-- Article : " + article + "-- MC : " + mc + "-- Set : " + configSet + "-- Qty : " + qty + "-- qualifyingAmt : " + qualifyingAmt + "-- discType : " + discType + "-- discValue : " + discValue);

                if (validateNullCellValue(article) && validateNullCellValue(mc) && validateNullCellValue(configSet) && validateNullCellValue(qty) && validateNullCellValue(qualifyingAmt) && validateNullCellValue(discType) && validateNullCellValue(discValue)) {
                    System.out.println("############################### break");
                    break;
                }
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

                /*Cr2 Chnage Finished*/



                if (validateNullCellValue(requestNo)) {
//                    detailErrorSheet.addCell(new Label(0, i, "Failure"));
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
                        String nextRequestNo = detailSheet.getCell(0, next_line_no).getContents();
                        if (nextRequestNo == null || !(nextRequestNo.equalsIgnoreCase(requestNo))) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount configuration detail required in next line.");
                            successFlag = false;
                        }
                    }
                }



                /* CR2 Change*/
                /* Article Set No is mandatory field for every promotion type request Line
                 * Now For Promo Type 3,4,5 & 7 It is not mandatory
                 * Add Condition into following code for the same
                 */
                /*CR2 Change finished*/

                if (promoTypeId != 3 && promoTypeId != 4 && promoTypeId != 5 && promoTypeId != 7) {
                    //Column Set No
                    //check Article Set no blank or numeric or range between 1-5
                    if (validateNullCellValue(articleSetNo)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Set No should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            validateSetNo = Integer.parseInt(articleSetNo);
//                            detailErrorSheet.addCell(new jxl.write.Number(3, i, validateSetNo));
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

                //Column Article , MC
                //check article or mc blank and length and numeric validations
                if (promoTypeId == 4) {
                    if (isReqNoLine) {
                        if (validateNullCellValue(article) && validateNullCellValue(mc)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Please Enter Article OR MC Code.");
                            successFlag = false;
                        } else {

                            if (!validateNullCellValue(mc)) {
                                try {
                                    article_mc = Double.parseDouble(mc);
//                                    detailErrorSheet.addCell(new jxl.write.Number(5, i, article_mc));
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

                            } else if (!validateNullCellValue(article)) {
                                try {
                                    article_mc = Double.parseDouble(article);
//                                    detailErrorSheet.addCell(new jxl.write.Number(4, i, article_mc));
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
                        if (!validateNullCellValue(mc)) {
                            try {
                                article_mc = Double.parseDouble(mc);
//                                detailErrorSheet.addCell(new jxl.write.Number(5, i, article_mc));
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

                        } else if (!validateNullCellValue(article)) {
                            try {
                                article_mc = Double.parseDouble(article);
//                                detailErrorSheet.addCell(new jxl.write.Number(4, i, article_mc));
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
                    if (validateNullCellValue(article) && validateNullCellValue(mc)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Please Enter Article OR MC Code.");
                        successFlag = false;
                    } else {

                        if (!validateNullCellValue(mc)) {
                            try {
                                article_mc = Double.parseDouble(mc);
//                                detailErrorSheet.addCell(new jxl.write.Number(5, i, article_mc));
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

                        } else if (!validateNullCellValue(article)) {
                            try {
                                article_mc = Double.parseDouble(article);
//                                detailErrorSheet.addCell(new jxl.write.Number(4, i, article_mc));
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
                        if (validateNullCellValue(configSet)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Set should not be blank.");
                            successFlag = false;
                        } else {
                            try {
                                validateSetNo = Integer.parseInt(configSet);
//                                detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
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
                } else if ((promoTypeId == 1 || promoTypeId == 2) && !validateNullCellValue(configSet)) {
                    try {
                        validateSetNo = Integer.parseInt(configSet);
//                        detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
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

                //Column Qty


                //for promotion type 1 & 2 config Qty null and numeric validations if config configSet is found not null
                //for promotion type 4 & 7 config Qty null and numeric validations if current row is request no line
                //for promotion type 3,5,6 Article Qty  OR Config Qty Not Applicable
                if (((promoTypeId == 1 || promoTypeId == 2) && !validateNullCellValue(configSet)) || (isReqNoLine && (promoTypeId == 4 || promoTypeId == 7))) {

                    if (validateNullCellValue(qty)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Qty should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            article_config_qty = Integer.parseInt(qty);
//                            detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
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


                //Column Qualifying Amt , Discount Type , Discount Value
                //if current row is request no line
                if (isReqNoLine) {
                    //for promotion type 1-5 discount type  null validation with current row is request no line
                    //for promotion type 1-5 discount value null & numeric validation with current row is request no line
                    //for promotion type 2 discount type=flat price  validation with current row is request no line

                    if (promoTypeId >= 2 && promoTypeId <= 5) {
                        if (validateNullCellValue(discType)) {
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

                        if (validateNullCellValue(discValue)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Discount Value should not be blank.");
                            successFlag = false;
                        } else {
                            try {
                                discConfigValue = Double.parseDouble(discValue);
//                                detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
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

                        if (validateNullCellValue(qualifyingAmt)) {
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qualifying Amt should not be blank.");
                            successFlag = false;
                        } else {
                            try {
                                discConfigValue = Double.parseDouble(qualifyingAmt);
//                                detailErrorSheet.addCell(new jxl.write.Number(8, i, discConfigValue));
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


                //for promotion type 6-7 check config configSet ,qty,discount type,discount value  null & numeric validation with current row is request no line
                /*CR 2 change
                 * for promo type id second row config configSet is not alloed now so removing condition
                || promoTypeId == 7 from below if statement
                 */
                // CR2 change finished
                //if (promotionRequestWiseRowCount == 2 && (promoTypeId == 6 || promoTypeId == 7)) {
                if (promotionRequestWiseRowCount == 2 && (promoTypeId == 6)) {
                    if (validateNullCellValue(configSet)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Set should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            validateSetNo = Integer.parseInt(configSet);
//                            detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
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

                    if (validateNullCellValue(qty)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Qty should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            article_config_qty = Integer.parseInt(qty);
//                            detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qty should be numeric.");
                            successFlag = false;
                        }
                    }


                    if (validateNullCellValue(discType)) {
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

                    if (validateNullCellValue(discValue)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Value should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            discConfigValue = Double.parseDouble(discValue);
//                            detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
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
                    if (validateNullCellValue(qty)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Qty should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            article_config_qty = Integer.parseInt(qty);
//                            detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            if (errorMessage.length() > 0) {
                                errorMessage.append(" | ");
                            }
                            errorMessage.append("Qty should be numeric.");
                            successFlag = false;
                        }
                    }


                    if (validateNullCellValue(discType)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Type should not be blank.");
                        successFlag = false;
                    }


                    if (validateNullCellValue(discValue)) {
                        if (errorMessage.length() > 0) {
                            errorMessage.append(" | ");
                        }
                        errorMessage.append("Discount Value should not be blank.");
                        successFlag = false;
                    } else {
                        try {
                            discConfigValue = Double.parseDouble(discValue);
//                            detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
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


                //for promotion type 1-4 check config configSet ,qty,discount type,discount value  null & numeric validation with current row is request no line
                if (promotionRequestWiseRowCount > 1 && (promoTypeId == 1 || promoTypeId == 4)) {


                    if (promoTypeId == 4) {
                        if (!validateNullCellValue(qty)) {
                            try {
                                article_config_qty = Integer.parseInt(qty);
//                                detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Qty should be numeric.");
                                successFlag = false;
                            }
                            if (validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount type.");
                                successFlag = false;
                            } else if (validateNullCellValue(discValue)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount value.");
                                successFlag = false;
                            } else {
                                try {
                                    discConfigValue = Double.parseDouble(discValue);
//                                    detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
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
                            if (!validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter qty.");
                                successFlag = false;
                            } else if (!validateNullCellValue(discValue)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter qty.");
                                successFlag = false;
                                try {
                                    discConfigValue = Double.parseDouble(discValue);
//                                    detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
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
                        if (validateNullCellValue(configSet)) {
                            if (!validateNullCellValue(discValue) || !validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter set between 2-5.");
                                successFlag = false;
                            }
                        } else {
                            try {
                                validateSetNo = Integer.parseInt(configSet);
//                                detailErrorSheet.addCell(new jxl.write.Number(6, i, validateSetNo));
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

                            if (!validateNullCellValue(qty)) {
                                try {
                                    article_config_qty = Integer.parseInt(qty);
//                                    detailErrorSheet.addCell(new jxl.write.Number(7, i, article_config_qty));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    if (errorMessage.length() > 0) {
                                        errorMessage.append(" | ");
                                    }
                                    errorMessage.append("Qty should be numeric.");
                                    successFlag = false;
                                }
                            }

                            if (validateNullCellValue(discType)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount type.");
                                successFlag = false;
                            }

                            if (validateNullCellValue(discValue)) {
                                if (errorMessage.length() > 0) {
                                    errorMessage.append(" | ");
                                }
                                errorMessage.append("Please enter discount value.");
                                successFlag = false;
                            } else {
                                try {
                                    discConfigValue = Double.parseDouble(discValue);
//                                    detailErrorSheet.addCell(new jxl.write.Number(10, i, discConfigValue));
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
                            String nextLineRequestNo = detailSheet.getCell(0, nextRowIndex).getContents();
                            if (!requestNo.equalsIgnoreCase(nextLineRequestNo) && !isDiscTypeFoundForPromo1) {
                                System.out.println("-------- Inside last line of promotype 1");
//                                detailErrorSheet.addCell(new Label(0, tempPromoType1LineNo + 1, "Failure"));
//                                detailErrorSheet.addCell(new Label(1, tempPromoType1LineNo + 1, errorMessage.toString()));
                                int excelRowNumer = i + 1;
                                detailErrorSheet.addCell(new Label(0, errorRowCount, "Failure"));
                                detailErrorSheet.addCell(new Label(1, errorRowCount, "Row Num : " + excelRowNumer + "  " + errorMessage.toString()));
                                detailErrorSheet.addCell(new Label(2, errorRowCount, requestNo));
                                detailErrorSheet.addCell(new Label(3, errorRowCount, articleSetNo));
                                detailErrorSheet.addCell(new Label(4, errorRowCount, article));
                                detailErrorSheet.addCell(new Label(5, errorRowCount, mc));
                                detailErrorSheet.addCell(new Label(6, errorRowCount, configSet));
                                detailErrorSheet.addCell(new Label(7, errorRowCount, qty));
                                detailErrorSheet.addCell(new Label(8, errorRowCount, qualifyingAmt));
                                detailErrorSheet.addCell(new Label(9, errorRowCount, discType));
                                detailErrorSheet.addCell(new Label(10, errorRowCount, discValue));
                                errorRowCount++;
                                successFlag = true;
                                finalSuccessFlag = false;
                                continue;
                            }
                        }

                        /*Cr2 Change finished*/
                    }

                }

                System.out.println("--------------- All Detail Validations Validated Successfully For Row : " + i);

                System.out.println("---------- Sucess Flag : " + successFlag);

                System.out.println("---------- Error  Msg : " + errorMessage);

                System.out.println("---------- Request Line Flag : " + isReqNoLine);
                if (successFlag) {
//                    detailErrorSheet.addCell(new Label(0, i, "Success"));
//                    detailErrorSheet.addCell(new Label(1, i, "Success"));
                } else {
                    int excelRowNumer = i + 1;

                    detailErrorSheet.addCell(new Label(0, errorRowCount, "Failure"));
                    detailErrorSheet.addCell(new Label(1, errorRowCount, "Row Num : " + excelRowNumer + "  " + errorMessage.toString()));
                    detailErrorSheet.addCell(new Label(2, errorRowCount, requestNo));
                    detailErrorSheet.addCell(new Label(3, errorRowCount, articleSetNo));
                    detailErrorSheet.addCell(new Label(4, errorRowCount, article));
                    detailErrorSheet.addCell(new Label(5, errorRowCount, mc));
                    detailErrorSheet.addCell(new Label(6, errorRowCount, configSet));
                    detailErrorSheet.addCell(new Label(7, errorRowCount, qty));
                    detailErrorSheet.addCell(new Label(8, errorRowCount, qualifyingAmt));
                    detailErrorSheet.addCell(new Label(9, errorRowCount, discType));
                    detailErrorSheet.addCell(new Label(10, errorRowCount, discValue));
                    successFlag = true;
                    finalSuccessFlag = false;
                    errorRowCount++;
                }
            }

        } catch (Exception ex) {
            logger.error("------------- Exception In Processing validateDetailSheet() MultiPromoFile : " + ex.getMessage());
            ex.printStackTrace();
        }
        return finalSuccessFlag;
    }

    public boolean validateNullCellValue(String val) {
        if (val == null || val.length() == 0) {
            return true;
        }
        return false;
    }

    private WritableWorkbook getBlankErrorWorkbook(String errorFilePath) throws IOException, WriteException {
        File errorFile = new File(errorFilePath);

        WritableWorkbook errorBook = Workbook.createWorkbook(errorFile);
        WritableSheet headerErrorSheet = errorBook.createSheet("Header Sheet", 0);
        headerErrorSheet.addCell(new Label(0, 0, "Status"));
        headerErrorSheet.addCell(new Label(1, 0, "Message"));
        headerErrorSheet.addCell(new Label(2, 0, "Request No"));
        headerErrorSheet.addCell(new Label(3, 0, "Promo Type"));
        headerErrorSheet.addCell(new Label(4, 0, "Remarks"));
        headerErrorSheet.addCell(new Label(5, 0, "Expected Margin"));
        headerErrorSheet.addCell(new Label(6, 0, "Expected Sales Qty growth"));
        headerErrorSheet.addCell(new Label(7, 0, "Expected Sales value growth"));
        headerErrorSheet.addCell(new Label(8, 0, "Growth IN Ticket size"));
        headerErrorSheet.addCell(new Label(9, 0, "Growth IN Conversions"));
        headerErrorSheet.addCell(new Label(10, 0, "Valid From"));
        headerErrorSheet.addCell(new Label(11, 0, "Valid to"));


        WritableSheet detailErrorSheet = errorBook.createSheet("Detail Sheet", 1);
        detailErrorSheet.addCell(new Label(0, 0, "Status"));
        detailErrorSheet.addCell(new Label(1, 0, "Message"));
        detailErrorSheet.addCell(new Label(2, 0, "Request No"));
        detailErrorSheet.addCell(new Label(3, 0, "Set No"));
        detailErrorSheet.addCell(new Label(4, 0, "Article"));
        detailErrorSheet.addCell(new Label(5, 0, "MC"));
        detailErrorSheet.addCell(new Label(6, 0, "Set"));
        detailErrorSheet.addCell(new Label(7, 0, "Qty"));
        detailErrorSheet.addCell(new Label(8, 0, "Qualifying Amount"));
        detailErrorSheet.addCell(new Label(9, 0, "Discount type"));
        detailErrorSheet.addCell(new Label(10, 0, "Discount Value"));

        return errorBook;
    }

    public int getTransPromoType(String promoTypeName) {
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

    public static Resp valdiateDate(String strDay, String strMonth, String strYear, String dateField) {
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

    public static int getMonthInNumeric(String strMonth) {

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

    public static void main(String[] args) {
//        Resp resp = valdiateDate("29", "2", "2012", "Valid To");
//        System.out.println(resp.getMsg());
        MstPromo promo = new MstPromo(2429l);
        MultiplePromotionFileUploadUtil obj = new MultiplePromotionFileUploadUtil();
        CreateMultiPlePromoReq reqest = obj.validateFile("C:\\suggested_MultipleFileUpload.xls", promo, "3", "192941");
        System.out.println("---- " + reqest.getErrorFlag());
        System.out.println("---- " + reqest.getErrorPath());
    }
}
