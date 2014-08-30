/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service.util;

import com.fks.promo.common.CommonStatus;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoStatus;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.facade.TransPromoStatusFacade;
import com.fks.promotion.vo.FilePromoCloseResp;
import com.fks.promotion.vo.PromoCloseVO;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
@Stateless
public class FilePromoCloseUtil {

    @EJB
    MstEmployeeFacade mstEmpFacade;
    @EJB
    TransPromoFacade transPromoFacade;
    @EJB
    TransPromoStatusFacade transPromoStatusFacade;

    public FilePromoCloseResp validateFile(String filePath, Set promoSet, MstEmployee emp, Logger logger) {

//        String alphaNumericPatternStr = "^[a-zA-Z0-9 &]*$";
//        Pattern anPattern = Pattern.compile(alphaNumericPatternStr);
//        Matcher anMatcher = null;
        List<PromoCloseVO> validationFailedList = new ArrayList<PromoCloseVO>();
        Map<Long, Long> promoCloseEmailIDList = new HashMap<Long, Long>();
        BufferedReader br = null;
        try {
            FileInputStream fi = new FileInputStream(filePath);
            DataInputStream di = new DataInputStream(fi);
            br = new BufferedReader(new InputStreamReader(di));

            boolean checkHeader = true;

//        boolean isError = false;

            String strLine = null;
//        StringBuilder msg = new StringBuilder("");

            int counter = 0;
            while ((strLine = br.readLine()) != null) {
                logger.info("Reading Line : " + strLine);
//            isError = false;

                String[] strValue = strLine.split(",");
                if (checkHeader) {
                    checkHeader = false;
                    //Check Header Validations
                    if (strValue.length != 5) {
                        logger.info("No Of required header fields should be 5.");
                        return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "No Of required header fields should be 5.")));

                    } else if (!(strValue[0].trim().equalsIgnoreCase("Request Number"))) {
                        logger.info("Invalid CSV Header: " + strValue[0]);
                        return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "Invalid CSV Header: '" + strValue[0] + "'.\n Header Name Should Be : Request Number.")));
                    } else if (!(strValue[1].trim().equalsIgnoreCase("Promotion Detail"))) {
                        logger.info("Invalid CSV Header: " + strValue[1]);
                        return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "Invalid CSV Header: '" + strValue[1] + "'.\n Header Name Should Be : Promotion Detail.")));
                    } else if (!(strValue[2].trim().equalsIgnoreCase("Remark"))) {
                        logger.info("Invalid CSV Header: " + strValue[2]);
                        return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "Invalid CSV Header: '" + strValue[2] + "'.\n Header Name Should Be : Remark.")));
                    } else if (!(strValue[3].trim().equalsIgnoreCase("Bonus Buy"))) {
                        logger.info("Invalid CSV Header: " + strValue[3]);
                        return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "Invalid CSV Header: '" + strValue[3] + "'.\n Header Name Should Be : Bonus Buy.")));
                    } else if (!(strValue[4].trim().equalsIgnoreCase("Cashier Trigger"))) {
                        logger.info("Invalid CSV Header: " + strValue[4]);
                        return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "Invalid CSV Header: '" + strValue[4] + "'.\n Header Name Should Be : Cashier Trigger.")));
                    }
                } else {
                    counter++;

                    //Check User Value Validations
                    if (strValue.length == 0) {
                        logger.info("Please enter required details at line NO : " + counter);
                        validationFailedList.add(new PromoCloseVO(strLine, "Please enter required details."));
                        continue;
                    } else if (strValue.length > 5) {
                        logger.info("Comma is not allowed in field values OR additional field values found. at line NO : " + counter);
                        validationFailedList.add(new PromoCloseVO(strLine, "Comma is not allowed in field values OR additional fields value found."));
                        continue;
                    } else {
                        logger.info("----- strvalue Length : " + strValue.length);
                        //Validations For Request No
                        String requestNo = null;
                        if (strValue.length > 0) {
                            requestNo = trimFieldSpaces(strValue[0]);
                        }
                        if (requestNo == null) {
                            logger.info("Request No should not be blank. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Request No should not be blank."));
                            continue;
                        }

                        String transPromoId = requestNo.substring(requestNo.indexOf("R") + 1, requestNo.length());
                        TransPromo promo = transPromoFacade.find(Long.valueOf(transPromoId));
                        //logger.info("------------- Promo Status : " + promo.getMstStatus().getStatusId().toString());
                        if (promo == null) {
                            logger.info("Invalid Request No Found. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Invalid Request No Found."));
                            continue;
                        } else if (!promo.getMstStatus().getStatusId().toString().equalsIgnoreCase(CommonStatus.TEAM_MEMBER_ASSIGNED.toString()) && !promo.getMstStatus().getStatusId().toString().equalsIgnoreCase(CommonStatus.TEAM_MEMBER_HOLD.toString())) {
                            logger.info("Invalid Status Found. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Invalid Request status found."));
                            continue;
                        } else if (!promoSet.contains(promo.getTransPromoId())) {
                            logger.info("Request is not assigned. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Request is not assigned to " + emp.getEmployeeName()));
                            continue;
                        }
                        logger.info("------ validations for request no compelted ");

                        //validations for promotion detail
                        String promoDtl = null;
                        if (strValue.length > 1) {
                            promoDtl = trimFieldSpaces(strValue[1]);
                        }
                        if (promoDtl == null || promoDtl.length() <= 0) {
                            logger.info("Promotion Detail should not be blank. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Promotion Detail should not be blank."));
                            continue;
                        } else if (promoDtl.length() > 200) {
                            logger.info("MAX length should be 200. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Promotion Detail max length should be 200 characters"));
                            continue;
                        }
//                        else {
//                            anMatcher = anPattern.matcher(promoDtl);
//                            if (!anMatcher.matches()) {
//                                logger.info("Promo Detail should be alpha numeric only at line NO : " + counter);
//                                validationFailedList.add(new PromoCloseVO(strLine, "Promo Detail should be alpha numeric only"));
//                                continue;
//                            }
//                        }
                        logger.info("------ validations for promotion detail compelted ");

                        //validations for remarks
                        String remarks = null;
                        if (strValue.length > 2) {
                            remarks = trimFieldSpaces(strValue[2]);
                        }
//                        if (remarks == null || remarks.length() <= 0) {
//                            logger.info("Remarks should not be blank. at line NO : " + counter);
//                            validationFailedList.add(new PromoCloseVO(strLine, "Remarks should not be blank."));
//                            continue;
//                        } else
                        if (remarks != null && remarks.length() > 200) {
                            logger.info("MAX length should be 200. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Remarks max length should be 200 characters"));
                            continue;
                        }
//                        else {
//                            anMatcher = anPattern.matcher(remarks);
//                            if (!anMatcher.matches()) {
//                                logger.info("Remarks should be alpha numeric only at line NO : " + counter);
//                                validationFailedList.add(new PromoCloseVO(strLine, "Remarks should be alpha numeric only"));
//                                continue;
//                            }
//                        }
                        logger.info("------ validations for remarks compelted ");

                        //validations for bonus buy
                        String bonusBuy = null;
                        if (strValue.length >= 4) {
                            bonusBuy = trimFieldSpaces(strValue[3]);
                        }
                        if (bonusBuy == null || bonusBuy.length() <= 0) {
                            logger.info("Bonus buy should not be blank. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Bonus Buy should not be blank."));
                            continue;
                        } else if (bonusBuy.length() > 10) {
                            logger.info("MAX length should be 10. at line NO : " + counter);
                            validationFailedList.add(new PromoCloseVO(strLine, "Bonus Buy max length should be 10 characters"));
                            continue;
                        }
//                        else {
//                            anMatcher = anPattern.matcher(bonusBuy);
//                            if (!anMatcher.matches()) {
//                                logger.info("Bonus Buy should be alpha numeric only at line NO : " + counter);
//                                validationFailedList.add(new PromoCloseVO(strLine, "Bonus Buy should be alpha numeric only"));
//                                continue;
//                            }
//                        }
                        logger.info("------ validations for bonus buy compelted ");

                        //validations for cashier trigger
                        String cashierTrigger = null;
                        if (strValue.length == 5) {

                            cashierTrigger = trimFieldSpaces(strValue[4]);

                            if (cashierTrigger != null) {
                                if (cashierTrigger.length() > 10) {
                                    logger.info("MAX length should be 10. at line NO : " + counter);
                                    validationFailedList.add(new PromoCloseVO(strLine, "cashierTrigger max length should be 10 characters"));
                                    continue;
                                }
                                try {
                                    logger.info("-----cashier trigger : " + cashierTrigger);
                                    Long.parseLong(cashierTrigger);
                                } catch (Exception ex) {
                                    logger.info("cashier trigger is not numeric at line No : " + counter);
                                    validationFailedList.add(new PromoCloseVO(strLine, "cashierTrigger should be numeric."));
                                    continue;
                                }
                            }

                        }
                        logger.info("------ validations for cashier trigger compelted ");
                        TransPromoStatus promoStatus = null;
                       // MstEmployee employee = null;
                       // TransPromo promo;
                        //Phase 3 CR - Promo Req Status History 15-11-2013
                        promoStatus = StatusUpdateUtil.submitTransPromoStatus(emp, new MstStatus(CommonStatusConstants.PROMO_CLOSE), promo.getMstStatus(),remarks, promo);
                        transPromoStatusFacade.create(promoStatus);

                        //Update Promotion Status to closure
                        promo.setPromoDetails(promoDtl);
//                        promo.setRemarks(remarks);
                        promo.setBonusBuy(bonusBuy);
                        if (cashierTrigger != null && cashierTrigger.length() > 0) {
                            promo.setCashierTrigger(cashierTrigger);
                        }
                        promo.setMstStatus(new MstStatus(CommonStatus.PROMO_CLOSED));
                        promo.setUpdatedDate(new Date());
                        promo.setMstEmployee6(emp);
//                        promoCloseEmailIDList.add(promo.getTransPromoId());
                        promoCloseEmailIDList.put(promo.getMstPromo().getPromoId(), promo.getTransPromoId());

                        logger.info("------ Promo is closed for Id : " + promo.getTransPromoId());

                    }
                }

            }



            //Return Failure Response
            if (validationFailedList != null && validationFailedList.size() > 0) {
                return new FilePromoCloseResp(new Resp(RespCode.FAILURE, "All Or Some Promotion Close Requests are failed."), validationFailedList, promoCloseEmailIDList);
            }

            //Return Success Response
            return new FilePromoCloseResp(new Resp(RespCode.SUCCESS, "Promotions are closed successfully."), promoCloseEmailIDList);
        } catch (Exception e) {
            e.printStackTrace();
            return (new FilePromoCloseResp(new Resp(RespCode.FAILURE, "error : " + e.getMessage())));
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(FilePromoCloseUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private String trimFieldSpaces(String field) {
        if (field != null && field.length() > 0) {
            return (field.trim());
        } else {
            return field;
        }
    }
}
