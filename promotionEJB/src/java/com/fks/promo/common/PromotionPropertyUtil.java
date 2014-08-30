/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajitn
 */
public class PromotionPropertyUtil {

    private static Properties pro = new Properties();

    static {
        InputStream is = null;
        try {
            is = PromotionPropertyUtil.class.getResourceAsStream(CommonConstants.PROPERTY);
            pro.load(is);
        } catch (IOException ex) {
            Logger.getLogger(PromotionPropertyUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static String getPropertyString(PropertyEnum proEnum) throws IOException {

        String str_property = null;
        switch (proEnum) {
            case ERROR_FILE:
                str_property = pro.getProperty("errorFile");
                break;
            case PROPOSAL_ERROR_FILE:
                str_property = pro.getProperty("proposalErrorFile");
                break;
            case PROPOSAL_OTHER_FILE:
                str_property = pro.getProperty("proposalOtherFilePath");
                break;
            case PROPOSAL_ARCHIEVED_FILE:
                str_property = pro.getProperty("proposalArchivalFilePath");
                break;
            case ARTICLE_ERROR_FILE:
                str_property = pro.getProperty("articleErrorFile");
                break;
            case PORTAL_URL:
                str_property = pro.getProperty("portalurl");
                break;
            case SUB_PROMO_REQUEST_FILE:
                str_property = pro.getProperty("subPromoRequestFile");
                break;
            case COMM_FILE:
                str_property = pro.getProperty("communicationFile");
                break;
            case ARTICLE_DOWNLOAD_FILE:
                str_property = pro.getProperty("articleDownloadFile");
                break;
            case MULTIPLE_SUB_PROMO_DOWNLOAD_FILE:
                str_property = pro.getProperty("subPromoDownloadFile");
                break;
            case MULTIPLE_PROPOSALS_DOWNLOAD_FILE:
                str_property = pro.getProperty("proposalDownloadFile");
                break;
            case USER_MCH_MAPPING_EXCEl:
                str_property = pro.getProperty("userMchExcel");
                break;
            case ARTICLE_DB_EXCEL_FILE:
                str_property = pro.getProperty("articleDBExcel");
                break;


        }
        return str_property;
    }
}
