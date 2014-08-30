/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;

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
            is = PromotionPropertyUtil.class.getResourceAsStream(WebConstants.PROPERTY);
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
            case PROPOSAL_ARTICLE_FILE_PATH:
                str_property = pro.getProperty("proposalArticleFilePath");
                break;
            case PROPOSAL_OTHER_FILE_PATH:
                str_property = pro.getProperty("proposalOtherFilePath");
                break;
            case COMMON_DIRECTORY_PATH:
                str_property = pro.getProperty("commondirectorypath");
                break;
            case INITIATE_ARTICLE_FILE_PATH:
                str_property = pro.getProperty("initiateArticleFilePath");
                break;
            case TASK_FILE_PATH:
                str_property = pro.getProperty("taskFilePath");
                break;
            case SUB_PROMOTION_FILE_PATH:
                str_property = pro.getProperty("subPromoRequestFile");
                break;
            case LSMW_FILE:
                str_property = pro.getProperty("lsmwFilePath");
                break;
            case SUB_PROMOTION_TEMPLATE_FILE_PATH:
                str_property = pro.getProperty("SubPromoTemplateFile");
                break;
            case PROMO_CLOSE:
                str_property = pro.getProperty("PromoClosePath");
                break;
            case SAP_WEB_UI:
                str_property = pro.getProperty("sapWebUI");
                break;
        }
        return str_property;
    }
}
