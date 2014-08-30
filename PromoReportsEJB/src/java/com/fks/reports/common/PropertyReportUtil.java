/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.common;

import com.fks.reports.vo.ReportTypeEnum;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ajitn
 */
public class PropertyReportUtil {

    private static Properties pro = new Properties();

    static {
        InputStream is = null;
        try {
            is = PropertyReportUtil.class.getResourceAsStream(ReportCommonConstants.PROPERTY);
            pro.load(is);
        } catch (IOException ex) {
            System.out.println("error : " + ex.getMessage());
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

    public static String getPropertyString(ReportTypeEnum proEnum) throws IOException {

        String str_property = null;
        switch (proEnum) {
            case PROMOTION_LIFE_CYCLE_ARTICLE_MC_RPT:
                str_property = pro.getProperty("PromoLifeCycleArticleMCRept");
                break;
            case PROMOTION_LIFE_CYCLE_RPT:
                str_property = pro.getProperty("PromoLifeCycleRept");
                break;
            case INTERNAL_TASK_RPT:
                str_property = pro.getProperty("InternalTaskRept");
                break;
            case PROMO_TEAM_DASHBOARD_RPT:
                str_property = pro.getProperty("PromoTeamDashboardRept");
                break;
            case STORE_PROPOSAL_RPT:
                str_property = pro.getProperty("StoreProposalRept");
                break;
            case STORE_PROPOSAL_PENDING_AUTOMATED_RPT:
                str_property = pro.getProperty("StoreProposalPendingAutomatedRept");
                break;
        }
        return str_property;
    }
}
