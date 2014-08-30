/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.ods.service.ODSService;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.init.vo.CreateMultiPlePromoReq;
import com.fks.promo.init.vo.CreateTransPromoReq;
import com.fks.promo.init.vo.TransPromoArticleVO;
import com.fks.promo.init.vo.TransPromoConfigVO;
import com.fks.promo.init.vo.TransPromoVO;
import com.fks.promo.master.service.OtherMasterService;
import com.fks.promo.master.vo.ValidateMCResp;
import com.fks.promotion.vo.ValidateArticleMCVO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
@Stateless
public class SubPromoFileValidationUtil {

    private static final Logger logger = Logger.getLogger(SubPromoFileValidationUtil.class.getName());
    @EJB
    private ODSService search;
    @EJB
    private OtherMasterService otherSearch;

    public CreateMultiPlePromoReq validateMultiplePromoFile(String fileName, MstPromo promo, String createdBy, String zoneId) {
        CreateMultiPlePromoReq req = new CreateMultiPlePromoReq();
        BufferedWriter writer = null;
        BufferedReader reader = null;
        try {
            int lastIndex = fileName.lastIndexOf("/");
            String dirName = fileName.substring(0, lastIndex);
            String file = fileName.substring(lastIndex + 1);
            int csvIndex = fileName.indexOf(".csv");
            System.out.println(" Directory Name " + dirName);
            System.out.println(" File Name " + file);
            String errorFileName = fileName.substring(lastIndex + 1, csvIndex) + "_error.csv";
            System.out.println(" Error File Name " + errorFileName);

            reader = new BufferedReader(new FileReader(fileName));
            String str = null;
            int counter = 1;
            boolean isError = false;
            String errorMessage = "";
            boolean skipHeader = true;
            List<TransPromoVO> promoVOList = new ArrayList<TransPromoVO>();            
            while ((str = reader.readLine()) != null) {
                System.out.println("Line " + str);
                counter++;

                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] lineSplit = str.split(",");
                if (lineSplit.length > 0 && lineSplit[0] == null) {
                    isError = true;
                    errorMessage = "Please fill promotion type at Line No : " + counter;
                    break;
                }

                String promoType = lineSplit[0];
                Integer promoTypeId = getTransPromoType(promoType);

                if (promoTypeId == 0) {
                    isError = true;
                    errorMessage = "Invalid promotion type at Line No : " + counter;
                    errorMessage += "\n Promotion Type Can Be : bxgx \n Buy X Get Y Discount At Set Level \n Buy X and Y @ Discounted price \n Flat Discount \n Power Pricing \n Ticket Size ( Bill Level) \n Ticket Size ( Pool Reward)";
                    break;
                }
                if (!validatePromoTypeFieldsCount(counter, lineSplit.length)) {
                    isError = true;
                    errorMessage = "Any field value missing at Line No : " + counter;
                    break;
                }

                TransPromoVO promoVO = new TransPromoVO();
                List<TransPromoArticleVO> articleMCList = new ArrayList<TransPromoArticleVO>();


                TransPromoArticleVO articleVO = getTransPromoArticleVOForMultipleFile(str, promoTypeId, promo);
                if (articleVO.getErrMsg() != null && articleVO.getErrMsg().length() > 0) {
                    errorMessage = articleVO.getErrMsg();
                    isError = true;
                    break;
                } else {
                    articleMCList.add(articleVO);

                }


                List<TransPromoConfigVO> configList = new ArrayList<TransPromoConfigVO>();
                TransPromoConfigVO configVO = getTransPromoConfigVOForMultipleFile(str, promoTypeId, promo, counter, promoVO);
                if (configVO.getErrorMessage() != null && configVO.getErrorMessage().length() > 0) {
                    errorMessage = configVO.getErrorMessage();
                    isError = true;
                    break;
                } else {
                    configList.add(configVO);
                }
                promoVO.setPromoTypeId(Long.valueOf(promoTypeId));
                promoVO.setTransPromoArticleList(articleMCList);
                promoVO.setTransPromoConfigList(configList);

                promoVOList.add(promoVO);
            }
            if (isError) {
                writer = new BufferedWriter(new FileWriter(dirName + "/" + errorFileName));
                if (writer == null) {
                    throw new Exception("File Name is Not Correct " + fileName);
                }
                writer.write(errorMessage);
                req.setErrorFlag(true);
                req.setErrorPath(dirName + "/" + errorFileName);
            } else {
                req.setTransPromoVO(promoVOList);
                req.setZoneId(zoneId);
                req.setEmpId(createdBy);
                req.setMstPromoId(promo.getPromoId().toString());
                req.setErrorFlag(false);
            }

        } catch (Exception ex) {
            logger.error("------ Error In SUbPromo File Util: " + ex.getMessage());
//            req.setErrorFlag(true);
//            req.setErrMsg("-----Exception : " + ex.getMessage());
            ex.printStackTrace();
        }
        return req;
    }

    public CreateTransPromoReq validateSubPromoFile(String fileName, TransPromo promo) {
        CreateTransPromoReq updateReq = new CreateTransPromoReq();
        BufferedWriter writer = null;
        BufferedReader reader = null;
        try {
            int lastIndex = fileName.lastIndexOf("/");
            String dirName = fileName.substring(0, lastIndex);
            String file = fileName.substring(lastIndex + 1);
            int csvIndex = fileName.indexOf(".csv");
            System.out.println(" Directory Name " + dirName);
            System.out.println(" File Name " + file);
            String errorFileName = fileName.substring(lastIndex + 1, csvIndex) + "_error.csv";
            System.out.println(" Error File Name " + errorFileName);


            reader = new BufferedReader(new FileReader(fileName));
            String str = null;
            List<String> lineCountList = new ArrayList<String>();
            while ((str = reader.readLine()) != null) {
                System.out.println("Line " + str);
                lineCountList.add(str);
            }

            int counter = 1;
            boolean isError = false;
            String errorMessage = "";

            TransPromoVO promoVO = new TransPromoVO();
            List<TransPromoArticleVO> articleMCList = new ArrayList<TransPromoArticleVO>();
            List<TransPromoConfigVO> configList = new ArrayList<TransPromoConfigVO>();
            Integer promoTypeId = null;

            if (lineCountList.size() < 4) {
                System.out.println("No. of Lines Must be atleast four");
                isError = true;
                errorMessage = "Each Promotion Must Have Atleast Header Promo Type Article/MC and Configuration Parameters";
            }

            if (!isError) {
                for (String line : lineCountList) {
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");
                    System.out.println("Checking Line " + line);
                    System.out.println(tokenizer.countTokens());
                    if (counter == 1 && tokenizer.countTokens() != 19) {
                        errorMessage = "Line No. " + counter + " is Not Correct";
                        System.out.println("Line No. " + counter + " is Not Correct");
                        isError = true;
                        break;
                    } else if (counter == 1 && tokenizer.countTokens() == 19) {
                        counter++;
                        continue;
                    } else if (counter == 2 && tokenizer.countTokens() != 2) {
                        errorMessage = "Line No. " + counter++ + " is Not Correct";
                        System.out.println("Line No. " + counter + " is Not Correct");
                        isError = true;
                        break;
                    } else if (counter == 2 && tokenizer.countTokens() == 2) {
                        String promoType = tokenizer.nextToken();
                        promoTypeId = getTransPromoType(promoType);
                        logger.info("########## Promo Type ID : " + promoTypeId);
                        if (promoTypeId == 0) {
                            errorMessage = "Line No. " + counter++ + " is Not Correct";
                            System.out.println("Line No. " + counter + " is Not Correct");
                            isError = true;
                            break;
                        } else {
                            updateReq.setTypeId(promoTypeId.longValue());
                            String remarks = tokenizer.nextToken();
                            promoVO.setRemark(remarks);
                        }
                    } else {
                        String[] splitter = line.split(",");
                        if (splitter.length >= 4 && splitter.length <= 6) {
                            logger.info("--------- Inside Article MC Validate.");
                            TransPromoArticleVO vo = getTransPromoArticleVO(line, promoTypeId, promo.getMstPromo());
                            if (vo.getErrMsg() != null && vo.getErrMsg().length() > 0) {
                                errorMessage = vo.getErrMsg();
                                isError = true;
                                break;
                            } else {
                                articleMCList.add(vo);

                            }
                        } else if (splitter.length >= 7 && splitter.length <= 20) {
                            logger.info("--------- Inside config Validate.");
                            TransPromoConfigVO vo = getTransPromoConfigVO(line, promoTypeId, promo.getMstPromo(), counter, promoVO);
                            if (vo.getErrorMessage() != null && vo.getErrorMessage().length() > 0) {
                                errorMessage = vo.getErrorMessage();
                                isError = true;
                                break;
                            } else {
                                configList.add(vo);
                            }
                        }
                    }

                    counter++;
                }
            }
            if (isError) {
                writer = new BufferedWriter(new FileWriter(dirName + "/" + errorFileName));
                if (writer == null) {
                    throw new Exception("File Name is Not Correct " + fileName);
                }
                writer.write(errorMessage);
                updateReq.setIsError(true);
                updateReq.setErrorFilePath(dirName + "/" + errorFileName);
            } else {
                promoVO.setTransPromoArticleList(articleMCList);
                promoVO.setTransPromoConfigList(configList);
                updateReq.setIsError(false);
                updateReq.setTransPromoVO(promoVO);
                promoVO.setTransPromoId(promo.getTransPromoId());
            }

        } catch (Exception ex) {
            logger.error("------ Error In SUbPromo File Util: " + ex.getMessage());
            updateReq.setIsError(true);
            updateReq.setErrorMsg("-----Exception : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(SubPromoFileValidationUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(SubPromoFileValidationUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return updateReq;
    }

    public static int getTransPromoType(String promoTypeName) {
        if (promoTypeName == null || promoTypeName.trim().length() == 0) {
            return 0;
        }
        if (promoTypeName.trim().equalsIgnoreCase("bxgx")) {
            return 7;
        } else if (promoTypeName.trim().equalsIgnoreCase("Buy X Get Y Discount At Set Level")) {
            return 1;
        } else if (promoTypeName.trim().equalsIgnoreCase("Buy X and Y @ Discounted price")) {
            return 2;
        } else if (promoTypeName.trim().equalsIgnoreCase("Flat Discount")) {
            return 3;
        } else if (promoTypeName.trim().equalsIgnoreCase("Power Pricing")) {
            return 4;
        } else if (promoTypeName.trim().equalsIgnoreCase("Ticket Size ( Bill Level)")) {
            return 5;
        } else if (promoTypeName.trim().equalsIgnoreCase("Ticket Size ( Pool Reward)")) {
            return 6;
        }
        return 0;
    }

    public static boolean validatePromoTypeFieldsCount(int promoType, int fieldCount) {

        if (promoType == 7 && fieldCount != 19) {
            return false;
        } else if ((promoType == 1 || promoType == 2 || promoType == 3) && fieldCount < 15) {
            return false;
        } else if ((promoType == 4 || promoType == 6) && fieldCount < 16) {
            return false;
        } else if (promoType == 5 && fieldCount < 17) {
            return false;
        }
        return true;
    }

    public TransPromoArticleVO getTransPromoArticleVOForMultipleFile(String str, int transPromoType, MstPromo mstPromo) {
        TransPromoArticleVO vo = new TransPromoArticleVO();
        String[] line = str.split(",");
        String set = line[2];
        String article = line[3];
        String mcCode = line[4];
        String qty = line[5];

        System.out.println("--------- set : " + set);
        System.out.println("--------- article : " + article);
        System.out.println("--------- mc Code : " + mcCode);
        System.out.println("--------- Qty : " + qty);

        if (qty != null && qty.trim().length() > 0) {
            try {
                int q = Integer.parseInt(qty);
                vo.setQty(q);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Qty Must be Numberic");
            }
        }

        if (set != null && set.trim().length() > 0) {
            try {
                int q = Integer.parseInt(set);
                vo.setSetId(q);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Qty Must be Numberic");
            }
        }

        if (article != null && article.trim().length() > 0) {
            try {
                int a = Integer.parseInt(article);
                ValidateArticleMCVO articleMCVO = search.searchODSArticle(article, mstPromo.getPromoId(), "1");
                if (!articleMCVO.isIsErrorStatus()) {
                    vo.setArtCode(String.valueOf(a));
                    vo.setArtDesc(articleMCVO.getArticleDesc());
                    vo.setMcCode(articleMCVO.getMcCode());
                    vo.setMcDesc(articleMCVO.getMcDesc());
                } else {
                    vo.setErrMsg(articleMCVO.getErrorMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Article Code Must be Numberic");
            }
        } else {
            try {
                int m = Integer.parseInt(mcCode);
                ValidateMCResp mCResp = otherSearch.validateMC(mcCode, mstPromo.getPromoId(), "1");
                if (mCResp.getResp().getRespCode() == com.fks.promo.common.RespCode.SUCCESS) {
                    vo.setMcCode(mCResp.getMcCode());
                    vo.setMcDesc(mCResp.getMcDesc());
                } else {
                    vo.setErrMsg(mCResp.getResp().getMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Article Code Must be Numberic");
            }
        }
        return vo;

    }

    public TransPromoConfigVO getTransPromoConfigVOForMultipleFile(String str, int transPromoType, MstPromo mstPromop, int lineno, TransPromoVO transPromoVO) {
        TransPromoConfigVO vo = new TransPromoConfigVO();
        String[] tokenStrings = str.split(",");
        String validFrom = "";
        String validTo = "";
        String marginAchievement = "";
        String growthTicketSize = "";
        String sellThru = "";
        String growthInConversion = "";
        String sellGrowth = "";
        String discConfig = "";
        String discConfigValue = "";
        String discConfigQty = "";
        String buyWorthAmt = "";
        String buy = "";
        String get = "";
        String set = "";
        set = tokenStrings[2];
        validFrom = tokenStrings[6];
        validTo = tokenStrings[7];
        marginAchievement = tokenStrings[8];
        growthTicketSize = tokenStrings[9];
        sellThru = tokenStrings[10];
        growthInConversion = tokenStrings[11];
        sellGrowth = tokenStrings[12];

        if (tokenStrings.length == 19) {
            discConfig = tokenStrings[13];
            discConfigValue = tokenStrings[14];
            discConfigQty = tokenStrings[15];
            buyWorthAmt = tokenStrings[16];
            buy = tokenStrings[17];
            get = tokenStrings[18];
        } else if (tokenStrings.length >= 14) {
            discConfig = tokenStrings[13];
            discConfigValue = tokenStrings[14];
        }

        if (!set.trim().isEmpty()) {
            try {
                int setInt = Integer.parseInt(set);
                vo.setSetId(setInt);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Set Value must be Numberic.");
                return vo;
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd");

        Date fromDate = null;
        Long fromDateTime = null;
        if (!validFrom.trim().isEmpty()) {
            try {
                validFrom = validFrom.replaceAll("/", "-");
                fromDate = format.parse(validFrom);
                vo.setValidFrom(dbformat.format(fromDate));
                transPromoVO.setValidFrom(dbformat.format(fromDate));
                fromDateTime = fromDate.getTime();
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Incorrect Date Format for Valid From.");
                return vo;
            }
        }
        Date toDate = null;
        Long toDateTime = null;
        if (!validTo.trim().isEmpty()) {
            try {
                validTo = validTo.replaceAll("/", "-");
                toDate = format.parse(validTo);
                vo.setValidTo(dbformat.format(toDate));
                transPromoVO.setValidTo(dbformat.format(toDate));
                toDateTime = toDate.getTime();
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Incorrect Date Format for Valid To.");
                return vo;
            }
        }

        logger.info("-------- #######  From Date Time : " + fromDateTime);
        logger.info("-------- #######  To Date Time : " + toDateTime);

        if (toDateTime < fromDateTime) {
            vo.setErrorMessage("Valid To Date Must Be Greater Than Valid From Date.");
            return vo;
        }


        if (!marginAchievement.trim().isEmpty()) {
            try {
                double dMarginAchievement = Double.parseDouble(marginAchievement);
                vo.setMarginAchievement(dMarginAchievement);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Margin Achievement Must Be Numeric.");
                return vo;
            }
        }
        if (!growthTicketSize.trim().isEmpty()) {
            try {
                double dgrowthTicketSize = Double.parseDouble(growthTicketSize);
                vo.setTicketSizeGrowth(dgrowthTicketSize);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Growth Ticket Size Must Be Numeric.");
                return vo;
            }
        }
        if (!sellThru.trim().isEmpty()) {
            try {
                int dsellThru = Integer.parseInt(sellThru);
                vo.setSellThruQty(dsellThru);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Sell Thru V/S Qty Must Be Numeric.");
                return vo;
            }
        }
        if (!growthInConversion.trim().isEmpty()) {
            try {
                double dgrowthInConversion = Double.parseDouble(growthInConversion);
                vo.setGrowthCoversion(dgrowthInConversion);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Growth In Conversion Must Be Numeric.");
                return vo;
            }
        }
        if (!sellGrowth.trim().isEmpty()) {
            try {
                double dsellGrowth = Double.parseDouble(sellGrowth);
                vo.setSalesGrowth(dsellGrowth);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Sell In Growth Must Be Numeric.");
                return vo;
            }
        }

        if (!discConfig.trim().isEmpty()) {
            vo.setDiscConfig(discConfig);
        }

        if (!discConfigQty.trim().isEmpty()) {
            try {
                int idiscConfigQty = Integer.parseInt(discConfigQty);
                vo.setQty(idiscConfigQty);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Discount Config Must Be Numeric.");
                return vo;
            }
        }

        if (!discConfigValue.trim().isEmpty()) {
            try {
                double idiscConfigValue = Double.parseDouble(discConfigValue);
                vo.setDiscValue(idiscConfigValue);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Discount Config Value Must Be Numeric.");
                return vo;
            }
        }

        if (!buy.trim().isEmpty()) {
            try {
                int ibuy = Integer.parseInt(buy);
                transPromoVO.setBuyQty(ibuy);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Buy Must Be Numeric.");
                return vo;
            }
        }

        if (!get.trim().isEmpty()) {
            try {
                int iget = Integer.parseInt(get);
                transPromoVO.setGetQty(iget);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Get Must Be Numeric.");
                return vo;
            }
        }

        if (!buyWorthAmt.trim().isEmpty()) {
            try {
                double dbuyworth = Double.parseDouble(buyWorthAmt);
                vo.setTicketWorthAmt(dbuyworth);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Buy Worth Amt Must Be Numeric.");
                return vo;
            }
        }

        return vo;

    }

    public TransPromoArticleVO getTransPromoArticleVO(String str, int transPromoType, MstPromo mstPromo) {
        TransPromoArticleVO vo = new TransPromoArticleVO();
        String[] tokenizer = str.split(",");
        String article = null;
        String mcCode = null;
        String qty = null;
        String set = null;
        if (tokenizer.length == 4) {
            set = tokenizer[2];
            article = tokenizer[3];
        } else if (tokenizer.length == 5) {
            set = tokenizer[2];
            mcCode = tokenizer[4];
        } else if (tokenizer.length == 6) {
            set = tokenizer[2];
            article = tokenizer[3];
            mcCode = tokenizer[4];
            qty = tokenizer[5];
        }
        if (qty != null && qty.trim().length() > 0) {
            try {
                int q = Integer.parseInt(qty);
                vo.setQty(q);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Qty Must be Numberic");
            }
        }

        if (set != null && set.trim().length() > 0) {
            try {
                int q = Integer.parseInt(set);
                vo.setSetId(q);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Qty Must be Numberic");
            }
        }

        if (article != null && article.trim().length() > 0) {
            try {
                int a = Integer.parseInt(article);
                ValidateArticleMCVO articleMCVO = search.searchODSArticle(article, mstPromo.getPromoId(), "1");
                if (!articleMCVO.isIsErrorStatus()) {
                    vo.setArtCode(String.valueOf(a));
                    vo.setArtDesc(articleMCVO.getArticleDesc());
                    vo.setMcCode(articleMCVO.getMcCode());
                    vo.setMcDesc(articleMCVO.getMcDesc());
                } else {
                    vo.setErrMsg(articleMCVO.getErrorMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Article Code Must be Numberic");
            }
        } else {
            try {
                int m = Integer.parseInt(mcCode);
                ValidateMCResp mCResp = otherSearch.validateMC(mcCode, mstPromo.getPromoId(), "1");
                if (mCResp.getResp().getRespCode() == com.fks.promo.common.RespCode.SUCCESS) {
                    vo.setMcCode(mCResp.getMcCode());
                    vo.setMcDesc(mCResp.getMcDesc());
                } else {
                    vo.setErrMsg(mCResp.getResp().getMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Article Code Must be Numberic");
            }
        }
        return vo;

    }

    public TransPromoConfigVO getTransPromoConfigVO(String str, int transPromoType, MstPromo mstPromop, int lineno, TransPromoVO transPromoVO) {
        TransPromoConfigVO vo = new TransPromoConfigVO();
        String[] tokenStrings = str.split(",");
        String validFrom = "";
        String validTo = "";
        String marginAchievement = "";
        String growthTicketSize = "";
        String sellThru = "";
        String growthInConversion = "";
        String sellGrowth = "";
        String discConfig = "";
        String discConfigValue = "";
        String discConfigQty = "";
        String buyWorthAmt = "";
        String buy = "";
        String get = "";
        String set = "";
        set = tokenStrings[2];
        validFrom = tokenStrings[6];
        validTo = tokenStrings[7];
        marginAchievement = tokenStrings[8];
        growthTicketSize = tokenStrings[9];
        sellThru = tokenStrings[10];
        growthInConversion = tokenStrings[11];
        sellGrowth = tokenStrings[12];

        if (tokenStrings.length == 19) {
            discConfig = tokenStrings[13];
            discConfigValue = tokenStrings[14];
            discConfigQty = tokenStrings[15];
            buyWorthAmt = tokenStrings[16];
            buy = tokenStrings[17];
            get = tokenStrings[18];
        } else if (tokenStrings.length >= 14) {
            discConfig = tokenStrings[13];
            discConfigValue = tokenStrings[14];
        }

        if (!set.trim().isEmpty()) {
            try {
                int setInt = Integer.parseInt(set);
                vo.setSetId(setInt);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Set Value must be Numberic.");
                return vo;
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd");

        Date fromDate = null;
        Long fromDateTime = null;
        if (!validFrom.trim().isEmpty()) {
            try {
                validFrom = validFrom.replaceAll("/", "-");
                fromDate = format.parse(validFrom);
                vo.setValidFrom(dbformat.format(fromDate));
                transPromoVO.setValidFrom(dbformat.format(fromDate));
                fromDateTime = fromDate.getTime();
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Incorrect Date Format for Valid From.");
                return vo;
            }
        }
        Date toDate = null;
        Long toDateTime = null;
        if (!validTo.trim().isEmpty()) {
            try {
                validTo = validTo.replaceAll("/", "-");
                toDate = format.parse(validTo);
                vo.setValidTo(dbformat.format(toDate));
                transPromoVO.setValidTo(dbformat.format(toDate));
                toDateTime = toDate.getTime();
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Incorrect Date Format for Valid To.");
                return vo;
            }
        }

        logger.info("-------- #######  From Date Time : " + fromDateTime);
        logger.info("-------- #######  To Date Time : " + toDateTime);

        if (toDateTime < fromDateTime) {
            vo.setErrorMessage("Valid To Date Must Be Greater Than Valid From Date.");
            return vo;
        }


        if (!marginAchievement.trim().isEmpty()) {
            try {
                double dMarginAchievement = Double.parseDouble(marginAchievement);
                vo.setMarginAchievement(dMarginAchievement);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Margin Achievement Must Be Numeric.");
                return vo;
            }
        }
        if (!growthTicketSize.trim().isEmpty()) {
            try {
                double dgrowthTicketSize = Double.parseDouble(growthTicketSize);
                vo.setTicketSizeGrowth(dgrowthTicketSize);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Growth Ticket Size Must Be Numeric.");
                return vo;
            }
        }
        if (!sellThru.trim().isEmpty()) {
            try {
                int dsellThru = Integer.parseInt(sellThru);
                vo.setSellThruQty(dsellThru);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Sell Thru V/S Qty Must Be Numeric.");
                return vo;
            }
        }
        if (!growthInConversion.trim().isEmpty()) {
            try {
                double dgrowthInConversion = Double.parseDouble(growthInConversion);
                vo.setGrowthCoversion(dgrowthInConversion);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Growth In Conversion Must Be Numeric.");
                return vo;
            }
        }
        if (!sellGrowth.trim().isEmpty()) {
            try {
                double dsellGrowth = Double.parseDouble(sellGrowth);
                vo.setSalesGrowth(dsellGrowth);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Sell In Growth Must Be Numeric.");
                return vo;
            }
        }

        if (!discConfig.trim().isEmpty()) {
            vo.setDiscConfig(discConfig);
        }

        if (!discConfigQty.trim().isEmpty()) {
            try {
                int idiscConfigQty = Integer.parseInt(discConfigQty);
                vo.setQty(idiscConfigQty);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Discount Config Must Be Numeric.");
                return vo;
            }
        }

        if (!discConfigValue.trim().isEmpty()) {
            try {
                double idiscConfigValue = Double.parseDouble(discConfigValue);
                vo.setDiscValue(idiscConfigValue);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Discount Config Value Must Be Numeric.");
                return vo;
            }
        }

        if (!buy.trim().isEmpty()) {
            try {
                int ibuy = Integer.parseInt(buy);
                transPromoVO.setBuyQty(ibuy);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Buy Must Be Numeric.");
                return vo;
            }
        }

        if (!get.trim().isEmpty()) {
            try {
                int iget = Integer.parseInt(get);
                transPromoVO.setGetQty(iget);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Get Must Be Numeric.");
                return vo;
            }
        }

        if (!buyWorthAmt.trim().isEmpty()) {
            try {
                double dbuyworth = Double.parseDouble(buyWorthAmt);
                vo.setTicketWorthAmt(dbuyworth);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Buy Worth Amt Must Be Numeric.");
                return vo;
            }
        }

        return vo;

    }
}
