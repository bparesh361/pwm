/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@Stateless
public class PromotionFileUtil {

    private static Logger logger = Logger.getLogger(PromotionFileUtil.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String COMMA_SEPARATOR = ",";

    public static FileResp validateArticleMCFile(String fileName) {
        try {
            String statusFilePath = "";
            FileResp fileErrorResp = new FileResp();
            boolean isError = false;
            FileInputStream fileIn = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            Set<String> articleCodeSet = new HashSet<String>();
            Set<String> mcCodeSet = new HashSet<String>();
            String articleCode, mcCode;
            boolean checkHeader = false;

            int counter = 3;
            //Skip First Message Line
            strLine = br.readLine();

            //Skip Space Line
            strLine = br.readLine();

            while ((strLine = br.readLine()) != null) {
                logger.info("Reading Line : " + strLine);
                String[] strValue = strLine.split(",");
                if (checkHeader == false) {
                    //Check Header Validations
                    if (strValue.length != 2) {
                        logger.info("No Of required header fields should be 2. ");
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("No Of required header fields should be 2.");
                        break;
                    } else if (!(strValue[0].trim().equalsIgnoreCase("Article Code"))) {
                        logger.info("Invalid CSV Header: " + strValue[0]);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Invalid CSV Header: '" + strValue[0] + "'.\n Header Name Should Be : Article Code.");
                        break;
                    } else if (!(strValue[1].trim().equalsIgnoreCase("MC Code"))) {
                        logger.info("Invalid CSV Header: " + strValue[1]);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Invalid CSV Header: '" + strValue[1] + "'.\n Header Name Should Be : MC Code.");
                        break;
                    }
                    checkHeader = true;
                } else {
                    //Check User Value Validations
                    counter++;
                    if (strValue.length == 0) {
                        logger.info("-------continue with No Line Found.");
                        continue;
                    } else if (strValue.length >= 2 && strValue[0] == null && strValue[1] == null) {
                        logger.info("-------continue with No Article Code OR MC CODE entered.");
                        continue;
                    } //                    if (strValue.length == 0 || strValue.length > 2) {
                    //                        logger.info("Please enter Article Code OR MC Code at line NO : " + counter);
                    //                        isError = true;
                    //                        fileErrorResp.setIsError(true);
                    //                        fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code at line NO : " + counter);
                    //                        break;
                    //                    }
                    else if (strValue.length == 1 && strValue[0] == null) {
                        logger.info("Please enter Article Code  at line NO : " + counter);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Please enter Article Code  at line NO : " + counter);
                        break;
                    } else if (strValue.length == 2 && strValue[0].trim().length() > 0 && strValue[1] != null && strValue[1].trim().length() > 0) {
                        logger.info("not null");
                        logger.info("Please enter Article Code OR MC Code at line NO : " + counter);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code at line NO : " + counter);
                        break;
                    }


                    if (strValue[0] != null && strValue[0].trim().length() > 0) {
                        articleCode = strValue[0].trim().toString();

                        if (articleCode.length() > 18) {
                            logger.info("Article code should not be more than 18 digit at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Article code should not be more than 18 digit at line NO : " + counter);
                            break;
                        } else if (!articleCode.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                            logger.info("Article code should be numeric at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Article code should be numeric at line NO : " + counter);
                            break;
                        }
                        if (!articleCodeSet.add(articleCode)) {
                            logger.info("Duplicate Article code found at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Duplicate Article code found at line NO : " + counter);
                            break;
                        }
                    } else if (strValue[1] != null && strValue[1].trim().length() > 0) {
                        mcCode = strValue[1].trim().toString();
                        if (mcCode.length() > 10) {
                            logger.info("MC code should not be more than 10 digit at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("MC code should not be more than 10 digit at line NO : " + counter);
                            break;
                        } else if (!mcCode.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                            logger.info("MC code should be numeric at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("MC code should be numeric at line NO : " + counter);
                            break;
                        }
                        if (!mcCodeSet.add(mcCode)) {
                            logger.info("Duplicate MC code found at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Duplicate MC code found at line NO : " + counter);
                            break;
                        }
                    } else {
                        logger.info("Please enter Article Code OR MC Code at line NO : " + counter);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code at line NO : " + counter);
                        break;
                    }
                }
            }

            if (counter == 3) {
                counter++;
                logger.info("Please enter Article Code OR MC Code at line NO : " + counter);
                isError = true;
                fileErrorResp.setIsError(true);
                fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code at line NO : " + counter);
            }


            if (isError == true) {
                logger.info("----------Creating Status File. --------");
                String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ERROR_FILE);                
                Calendar cal = new GregorianCalendar();
                long timestamp = cal.getTimeInMillis();                
                String statusfileName = "ArticleCode_MCCode_Status_File_" + timestamp + ".csv";
                statusFilePath = errorFilePath + statusfileName;
                logger.info("------------ Status File Path : " + statusFilePath);
                BufferedWriter writer = new BufferedWriter(new FileWriter(statusFilePath));
                File statusFile = new File(statusFilePath);
                statusFile.createNewFile();
                writer.write("Error Message :  Basic File Validation Fails..");
                writer.write(LINE_SEPARATOR);
                writer.write(LINE_SEPARATOR);
                writer.write(fileErrorResp.getErrorMsg());
                writer.write(LINE_SEPARATOR);
                writer.flush();
                if (writer != null) {
                    writer.close();
                }
                fileErrorResp.setErrorFilePath(statusFilePath);
                logger.info("====== File Writing is Completed ===== ");
                return fileErrorResp;

            }

            logger.info("--------- Proposal Article File Validated Successfully.");
            return new FileResp(false, "File Validated");
        } catch (Exception ex) {
            ex.printStackTrace();
            return (new FileResp(Boolean.FALSE, "Error : " + ex.getMessage()));
        }

    }

    public static FileResp validateIntiationArticleMCFile(String fileName) {
        try {
            String statusFilePath = "";
            FileResp fileErrorResp = new FileResp();
            boolean isError = false;
            FileInputStream fileIn = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine = null;
            Set<String> articleCodeSet = new HashSet<String>();
            Set<String> mcCodeSet = new HashSet<String>();
            String articleCode, mcCode, qty;
            boolean checkHeader = false;

            int counter = 3;
            //Skip First Message Line
            strLine = br.readLine();
            
            //Skip Space Line
            strLine = br.readLine();
            

            while ((strLine = br.readLine()) != null) {
                logger.info("Reding Line : " + strLine);
                String[] strValue = strLine.split(",");
                if (checkHeader == false) {
                    //Check Header Validations
                    if (strValue.length != 3) {
                        logger.info("No Of required header fields should be 3.");
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("No Of required header fields should be 3.");
                        break;
                    } else if (!(strValue[0].trim().equalsIgnoreCase("Article Code"))) {
                        logger.info("Invalid CSV Header: " + strValue[0]);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Invalid CSV Header: '" + strValue[0] + "'.\n Header Name Should Be : Article Code.");
                        break;
                    } else if (!(strValue[1].trim().equalsIgnoreCase("MC Code"))) {
                        logger.info("Invalid CSV Header: " + strValue[1]);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Invalid CSV Header: '" + strValue[1] + "'.\n Header Name Should Be : MC Code.");
                        break;
                    } else if (!(strValue[2].trim().equalsIgnoreCase("QTY"))) {
                        logger.info("Invalid CSV Header: " + strValue[2]);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Invalid CSV Header: '" + strValue[2] + "'.\n Header Name Should Be : QTY.");
                        break;
                    }
                    checkHeader = true;
                } else {
                    //Check User Value Validations
                    counter++;
                    logger.info("-------- Field Length : " + strValue.length + " at Line No : " + counter);
                    if (strValue.length == 0) {
                        logger.info("-------continue with No Line Found.");
                        continue;
                    } else if (strValue.length == 2 && strValue[0] == null && strValue[1] == null) {
                        logger.info("-------continue with No Article Code OR MC CODE entered.");
                        continue;
                    } else if (strValue.length >= 3 && strValue[0] == null && strValue[1] == null && strValue[2] == null) {
                        logger.info("-------continue with No Article Code OR MC CODE OR Qty entered.");
                        continue;
                    } else if (strValue.length <= 2 || strValue.length > 3) {
                        logger.info("------- Inside Length Condition.");
                        if (strValue.length == 2) {
                            logger.info("Please enter Qty at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Please enter Qty at line NO : " + counter);
                            break;
                        } else if (strValue.length < 2) {
                            logger.info("Please enter Qty at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Please enter Qty at line NO : " + counter);
                            break;
                        }
                        logger.info("Please enter Article Code OR MC Code AND Qty at line NO : " + counter);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code AND Qty at line NO : " + counter);
                        break;
                    } else if (strValue[0] != null && strValue[0].length() > 0 && strValue[1] != null && strValue[1].length() > 0) {                        
                        logger.info("Please enter Article Code OR MC Code at line NO : " + counter);
                        isError = true;
                        fileErrorResp.setIsError(true);
                        fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code at line NO : " + counter);
                        break;
                    }
                    if (strValue[0] != null && strValue[0].trim().length() > 0) {
                        articleCode = strValue[0].trim().toString();

                        if (articleCode.length() > 18) {
                            logger.info("Article code should not be more than 18 digit at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Article code should not be more than 18 digit at line NO : " + counter);
                            break;
                        } else if (!articleCode.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                            logger.info("Article code should be numeric at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Article code should be numeric at line NO : " + counter);
                            break;
                        }
                        if (!articleCodeSet.add(articleCode)) {
                            logger.info("Duplicate Article code found at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Duplicate Article code found at line NO : " + counter);
                            break;
                        }
                    } else if (strValue[1] != null && strValue[1].trim().length() > 0) {
                        mcCode = strValue[1].trim().toString();
                        if (mcCode.length() > 10) {
                            logger.info("MC code should not be more than 10 digit at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("MC code should not be more than 10 digit at line NO : " + counter);
                            break;
                        } else if (!mcCode.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                            logger.info("MC code should be numeric at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("MC code should be numeric at line NO : " + counter);
                            break;
                        }
                        if (!mcCodeSet.add(mcCode)) {
                            logger.info("Duplicate MC code found at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Duplicate MC code found at line NO : " + counter);
                            break;
                        }
                    }
                    if (strValue[2] != null && strValue[2].trim().length() > 0) {
                        qty = strValue[2].trim().toString();
                        if (!qty.matches("((-|\\+)?[0-9]+)")) {
                            logger.info("Qty should be numeric at line NO : " + counter);
                            isError = true;
                            fileErrorResp.setIsError(true);
                            fileErrorResp.setErrorMsg("Qty should be numeric at line NO : " + counter);
                            break;
                        }

                    }
                }
            }

            if (counter == 3) {
                counter++;
                logger.info("Please enter Article Code OR MC Code AND Qty at line NO : " + counter);
                isError = true;
                fileErrorResp.setIsError(true);
                fileErrorResp.setErrorMsg("Please enter Article Code OR MC Code AND Qty at line NO : " + counter);
            }

            if (isError == true) {
                logger.info("----------Creating Status File. --------");
                String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ERROR_FILE);                
                Calendar cal = new GregorianCalendar();
                long timestamp = cal.getTimeInMillis();
                logger.info("------- Time stamp : " + timestamp);
                String statusfileName = "ArticleCode_MCCode_Status_File_" + timestamp + ".csv";
                statusFilePath = errorFilePath + statusfileName;
                logger.info("------------ Status File Path : " + statusFilePath);
                BufferedWriter writer = new BufferedWriter(new FileWriter(statusFilePath));
                File statusFile = new File(statusFilePath);
                statusFile.createNewFile();
                writer.write("Error Message :  Basic File Validation Fails..");
                writer.write(LINE_SEPARATOR);
                writer.write(LINE_SEPARATOR);
                writer.write(fileErrorResp.getErrorMsg());
                writer.write(LINE_SEPARATOR);
                writer.flush();
                if (writer != null) {
                    writer.close();
                }
                fileErrorResp.setErrorFilePath(statusFilePath);
                logger.info("====== File Writing is Completed ===== ");
                return fileErrorResp;

            }
            logger.info("---------  Article File Validated Successfully.");
            return new FileResp(false, "File Validated");
        } catch (Exception ex) {
            ex.printStackTrace();
            return (new FileResp(Boolean.FALSE, "Error : " + ex.getMessage()));
        }
    }
//    public static FileResp returnArticleList(String fileName) {
//        try {
//            FileResp fileErrorResp = new FileResp();
//            boolean isError = false;
//            FileInputStream fileIn = new FileInputStream(fileName);
//            DataInputStream in = new DataInputStream(fileIn);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//            String strLine = null;
//            Set<String> articleCodeSet = new HashSet<String>();
//            Set<String> mcCodeSet = new HashSet<String>();
//            String articleCode, mcCode, qty;
//            boolean checkHeader = false;
//
//            int counter = 3;
//            //Skip First Message Line
//            strLine = br.readLine();
//            System.out.println("1st Line :" + strLine);
//            //Skip Space Line
//            strLine = br.readLine();
//            System.out.println("2nd Line :" + strLine);
//            TransPromoVO promoVO = new TransPromoVO();
//            TransPromoArticleVO articleVO = null;
//            while ((strLine = br.readLine()) != null) {
//                System.out.println("Reding Line : " + strLine);
//                String[] strValue = strLine.split(",");
//                articleVO = new TransPromoArticleVO();
//                articleVO.setArtCode(article.getArticle());
//                articleVO.setArtDesc(article.getArticleDesc());
//                articleVO.setMcCode(article.getMcCode());
//                articleVO.setMcDesc(article.getMcDesc());
//                if (article.getSetId() != null) {
//                    articleVO.setSetId(article.getSetId());
//                }
//                articleVO.setQty(article.getQty());
//                if (article.getIsA() != null) {
//                    articleVO.setIsA(article.getIsA());
//                }
//                if (article.getIsB() != null) {
//                    articleVO.setIsB(article.getIsB());
//                }
//                if (article.getIsX() != null) {
//                    articleVO.setIsX(article.getIsX());
//                }
//                if (article.getIsY() != null) {
//                    articleVO.setIsY(article.getIsY());
//                }
//                if (article.getIsZ() != null) {
//                    articleVO.setIsZ(article.getIsZ());
//                }
//
//            }
//
//
//
//
//            if (isError == true) {
//                return fileErrorResp;
//            }
//            logger.info("--------- Proposal Article File Validated Successfully.");
//            return new FileResp(false, "File Validated");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return (new FileResp(Boolean.FALSE, "Error : " + ex.getMessage()));
//        }
//    }
}
