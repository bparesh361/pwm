/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.CreateTransPromoReq;
import com.fks.promo.init.TransPromoArticleVO;
import com.fks.promo.init.TransPromoConfigVO;
import com.fks.promo.init.TransPromoVO;
import com.fks.promo.master.service.ValidateArticleMCVO;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.BillLevelTicketSizeFormVO;
import com.fks.ui.form.vo.FlatDiscountFormVO;
import com.fks.ui.form.vo.PoolTicketSizeFormVO;
import com.fks.ui.form.vo.PowerPricingFormVO;
import com.fks.ui.master.vo.ArticleMcVo;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ajitn
 */
public class PromotionIntiationUtil {

    public static CreateTransPromoReq getTicketSizePoolRequest(FlatDiscountFormVO formVO, List<ValidateArticleMCVO> articleMCList, String zoneId, String empId) throws ParseException {
        CreateTransPromoReq request = new CreateTransPromoReq();

        TransPromoVO promoVo = new TransPromoVO();

        if (formVO.getIsManualEntry().equals("1")) {
            getpoolTicketArticleList(promoVo, formVO);
        } else {
            getUploadedFileArticleMCList(promoVo, articleMCList);
        }

        promoVo.getTransPromoConfigList().add(getFlatDiscountConfig(formVO));
        promoVo.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVo.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVo.setRemark(formVO.getTxtRemarks());

        request.setTransPromoVO(promoVo);

        request.setTypeId(WebConstants.TICKET_SIZE_POOL_REWARD);

        request.setMstPromoId(Long.valueOf(formVO.getMstPromoId()));

        request.setZoneId(Long.valueOf(zoneId));

        request.setEmpId(Long.valueOf(empId));

        return request;

    }

    public static void getpoolTicketArticleList(TransPromoVO promoVo, FlatDiscountFormVO formVO) {
        TransPromoArticleVO article = null;
        if (formVO.getManualArticleData() != null) {
            String[] articleGridData = formVO.getManualArticleData().split(",");
            for (int i = 0; i < articleGridData.length / 6; i++) {
                article = new TransPromoArticleVO();
                article.setArtCode(articleGridData[i * 6]);
                article.setArtDesc(articleGridData[(i * 6) + 1]);
                article.setMcCode(articleGridData[(i * 6) + 2]);
                article.setMcDesc(articleGridData[(i * 6) + 3]);
                article.setQty(Integer.parseInt(articleGridData[(i * 6) + 4]));
                article.setSetId(Integer.parseInt(articleGridData[(i * 6) + 5]));
                promoVo.getTransPromoArticleList().add(article);
            }
        }
    }

    public static CreateTransPromoReq getFlatDiscountRequest(FlatDiscountFormVO formVO, List<ValidateArticleMCVO> articleMCList, String zoneId, String empId) throws ParseException {
        CreateTransPromoReq request = new CreateTransPromoReq();

        TransPromoVO promoVo = new TransPromoVO();

        if (formVO.getIsManualEntry().equals("1")) {
            getFlatDiscountArticleList(promoVo, formVO);
        } else {
            getUploadedFileArticleMCList(promoVo, articleMCList);
        }

        promoVo.getTransPromoConfigList().add(getFlatDiscountConfig(formVO));
        promoVo.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVo.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVo.setRemark(formVO.getTxtRemarks());

        request.setTransPromoVO(promoVo);

        request.setTypeId(WebConstants.FLAT_DISCOUNT);

        request.setMstPromoId(Long.valueOf(formVO.getMstPromoId()));

        request.setZoneId(Long.valueOf(zoneId));

        request.setEmpId(Long.valueOf(empId));

        return request;

    }

    public static void getFlatDiscountArticleList(TransPromoVO promoVo, FlatDiscountFormVO formVO) {
        TransPromoArticleVO article = null;
        if (formVO.getManualArticleData() != null) {
            String[] articleGridData = formVO.getManualArticleData().split(",");
            for (int i = 0; i < articleGridData.length / 5; i++) {
                article = new TransPromoArticleVO();
                article.setArtCode(articleGridData[i * 5]);
                article.setArtDesc(articleGridData[(i * 5) + 1]);
                article.setMcCode(articleGridData[(i * 5) + 2]);
                article.setMcDesc(articleGridData[(i * 5) + 3]);
                article.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                article.setIsX(true);
                promoVo.getTransPromoArticleList().add(article);
            }
        }

    }

    public static TransPromoConfigVO getFlatDiscountConfig(FlatDiscountFormVO formVO) throws ParseException {
        TransPromoConfigVO config = new TransPromoConfigVO();
        if (formVO.getDiscountConfig().equalsIgnoreCase("0")) {
            config.setDiscConfig("Value off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("1")) {
            config.setDiscConfig("Percentage Off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("2")) {
            config.setDiscConfig("Flat Price");
        }
        //config.setDiscConfig(formVO.getDiscountConfig());
        config.setDiscValue(Double.parseDouble(formVO.getDiscountConfigValue()));
        config.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
        config.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
        config.setSellThruQty(Double.parseDouble(formVO.getSellQty()));
        config.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
        config.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        config.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        config.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        return config;
    }

    public static CreateTransPromoReq getPowerPricingRequest(PowerPricingFormVO formVO, List<ValidateArticleMCVO> articleMCList, String zoneId, String empId) throws ParseException {
        CreateTransPromoReq request = new CreateTransPromoReq();

        TransPromoVO promoVo = new TransPromoVO();
        promoVo.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVo.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVo.setRemark(formVO.getTxtRemarks());


        if (formVO.getIsManualEntry().equals("1")) {
            getPowerPriceArticleList(promoVo, formVO);
        } else {
            getUploadedFileArticleMCList(promoVo, articleMCList);
        }

        if (formVO.getDiscountConfigGridData() != null) {
            String[] discountGridData = formVO.getDiscountConfigGridData().split(",");
            int qty = 0;
            double value = 0;
            for (int i = 0; i < discountGridData.length / 2; i++) {
                value = Double.parseDouble(discountGridData[i * 2]);
                qty = Integer.parseInt(discountGridData[(i * 2) + 1]);
                promoVo.getTransPromoConfigList().add(getPowerPriceConfig(formVO, value, qty));
            }
        }


        request.setTransPromoVO(promoVo);

        request.setTypeId(WebConstants.POWER_PRICING);

        request.setMstPromoId(Long.valueOf(formVO.getMstPromoId()));

        request.setZoneId(Long.valueOf(zoneId));

        request.setEmpId(Long.valueOf(empId));

        return request;

    }

    public static void getPowerPriceArticleList(TransPromoVO promoVo, PowerPricingFormVO formVO) {
        TransPromoArticleVO article = null;
        if (formVO.getManualArticleData() != null) {
            String[] articleGridData = formVO.getManualArticleData().split(",");
            for (int i = 0; i < articleGridData.length / 5; i++) {
                article = new TransPromoArticleVO();
                article.setArtCode(articleGridData[i * 5]);
                article.setArtDesc(articleGridData[(i * 5) + 1]);
                article.setMcCode(articleGridData[(i * 5) + 2]);
                article.setMcDesc(articleGridData[(i * 5) + 3]);
                article.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                article.setIsX(true);
                promoVo.getTransPromoArticleList().add(article);
            }
        }

    }

    public static TransPromoConfigVO getPowerPriceConfig(PowerPricingFormVO formVO, double discValue, int qty) throws ParseException {
        TransPromoConfigVO config = new TransPromoConfigVO();
        if (formVO.getDiscountConfig().equalsIgnoreCase("0")) {
            config.setDiscConfig("Value off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("1")) {
            config.setDiscConfig("Percentage Off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("2")) {
            config.setDiscConfig("Flat Price");
        }
        //config.setDiscConfig(formVO.getDiscountConfig());
        config.setDiscValue(discValue);
        config.setQty(qty);
        config.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
        config.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
        config.setSellThruQty(Double.parseDouble(formVO.getSellQty()));
        config.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
        config.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        config.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        config.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        return config;
    }

    public static CreateTransPromoReq getBillTicketSizeRequest(BillLevelTicketSizeFormVO formVO, List<ValidateArticleMCVO> articleMCList, String zoneId, String empId) throws ParseException {
        CreateTransPromoReq request = new CreateTransPromoReq();

        TransPromoVO promoVo = new TransPromoVO();

        if (formVO.getIsManualEntry().equals("1")) {
            getBillTicketSizeArticleList(promoVo, formVO);
        } else {
            getUploadedFileArticleMCList(promoVo, articleMCList);
        }

        promoVo.getTransPromoConfigList().add(getBillTicketSizeConfig(formVO));
        promoVo.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVo.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVo.setRemark(formVO.getTxtRemarks());

        request.setTransPromoVO(promoVo);

        request.setTypeId(WebConstants.TICKET_SIZE_BILL_LEVEL);

        request.setMstPromoId(Long.valueOf(formVO.getMstPromoId()));

        request.setZoneId(Long.valueOf(zoneId));

        request.setEmpId(Long.valueOf(empId));

        return request;

    }

    public static void getBillTicketSizeArticleList(TransPromoVO promoVo, BillLevelTicketSizeFormVO formVO) {
        TransPromoArticleVO article = null;
        if (formVO.getManualArticleData() != null) {
            String[] articleGridData = formVO.getManualArticleData().split(",");
            for (int i = 0; i < articleGridData.length / 5; i++) {
                article = new TransPromoArticleVO();
                article.setArtCode(articleGridData[i * 5]);
                article.setArtDesc(articleGridData[(i * 5) + 1]);
                article.setMcCode(articleGridData[(i * 5) + 2]);
                article.setMcDesc(articleGridData[(i * 5) + 3]);
                article.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                article.setIsX(true);
                promoVo.getTransPromoArticleList().add(article);
            }
        }

    }

    public static TransPromoConfigVO getBillTicketSizeConfig(BillLevelTicketSizeFormVO formVO) throws ParseException {
        TransPromoConfigVO config = new TransPromoConfigVO();
        //config.setDiscConfig(formVO.getDiscountConfig());
        if (formVO.getDiscountConfig().equalsIgnoreCase("0")) {
            config.setDiscConfig("Value off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("1")) {
            config.setDiscConfig("Percentage Off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("2")) {
            config.setDiscConfig("Flat Price");
        }
        config.setTicketWorthAmt(Double.parseDouble(formVO.getBuyWorthAmt()));
        config.setTicketDiscAmt(Double.parseDouble(formVO.getDiscountAmt()));
        config.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
        config.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
        config.setSellThruQty(Double.parseDouble(formVO.getSellQty()));
        config.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
        config.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        config.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        config.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        return config;
    }

    public static CreateTransPromoReq getPoolTicketSizeRequest(PoolTicketSizeFormVO formVO, String zoneId, String empId, Map<String, Object> sessionMap) throws ParseException {
        CreateTransPromoReq request = new CreateTransPromoReq();

        TransPromoVO promoVo = new TransPromoVO();
        promoVo.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVo.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVo.setRemark(formVO.getTxtRemarks());

        if (formVO.getIsManualEntry() != null && formVO.getIsManualEntry().equals("1")) {
            getPoolTicketSizeArticleList(promoVo, formVO, true);
        } else {
            getPoolTicketSizeArticleListFromSession(promoVo, sessionMap, true);
        }

        if (formVO.getIsYManualEntry() != null && formVO.getIsYManualEntry().equals("1")) {
            getPoolTicketSizeArticleList(promoVo, formVO, false);
        } else {
            getPoolTicketSizeArticleListFromSession(promoVo, sessionMap, false);
        }

        promoVo.getTransPromoConfigList().add(getPoolTicketSizeConfig(formVO));

        request.setTransPromoVO(promoVo);

        request.setTypeId(WebConstants.TICKET_SIZE_POOL_REWARD);

        request.setMstPromoId(Long.valueOf(formVO.getMstPromoId()));

        request.setZoneId(Long.valueOf(zoneId));

        request.setEmpId(Long.valueOf(empId));

        return request;

    }

    public static void getPoolTicketSizeArticleListFromSession(TransPromoVO promoVo, Map<String, Object> sessionMap, boolean isXFile) {
        List<ArticleMcVo> list = null;
        if (isXFile == true) {
            if (sessionMap.get(WebConstants.X_ARTICLE) != null) {
                list = (List<ArticleMcVo>) sessionMap.get(WebConstants.X_ARTICLE);
                System.out.println("X List size : " + list.size());
            }
        } else {
            if (sessionMap.get(WebConstants.Y_ARTICLE) != null) {
                list = (List<ArticleMcVo>) sessionMap.get(WebConstants.Y_ARTICLE);
                System.out.println("Y List size : " + list.size());
            }
        }

        if (list.size() > 0 && !list.isEmpty()) {
            TransPromoArticleVO article = null;
            for (ArticleMcVo vo : list) {
                article = new TransPromoArticleVO();
                article.setArtCode(vo.getArticleCode());
                article.setArtDesc(vo.getArticleDesc());
                article.setMcCode(vo.getMcCode());
                article.setMcDesc(vo.getMcDesc());
                article.setQty(vo.getQty());
                if (isXFile == true) {
                    article.setIsX(true);
                } else {
                    article.setIsY(true);
                }
                promoVo.getTransPromoArticleList().add(article);
            }
        }
    }

    public static void getPoolTicketSizeArticleList(TransPromoVO promoVo, PoolTicketSizeFormVO formVO, boolean isXarticle) {
        TransPromoArticleVO article = null;
        String[] articleGridData = null;
        if (isXarticle == true) {
            if (formVO.getManualArticleData() != null) {
                articleGridData = formVO.getManualArticleData().split(",");
            }
        } else {
            if (formVO.getManualYArticleData() != null) {
                articleGridData = formVO.getManualYArticleData().split(",");
            }
        }
        for (int i = 0; i < articleGridData.length / 5; i++) {
            article = new TransPromoArticleVO();
            article.setArtCode(articleGridData[i * 5]);
            article.setArtDesc(articleGridData[(i * 5) + 1]);
            article.setMcCode(articleGridData[(i * 5) + 2]);
            article.setMcDesc(articleGridData[(i * 5) + 3]);
            article.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
            if (isXarticle == true) {
                article.setIsX(true);
            } else {
                article.setIsY(true);
            }

            promoVo.getTransPromoArticleList().add(article);
        }

    }

    public static TransPromoConfigVO getPoolTicketSizeConfig(PoolTicketSizeFormVO formVO) throws ParseException {
        TransPromoConfigVO config = new TransPromoConfigVO();
        if (formVO.getDiscConfig().equalsIgnoreCase("0")) {
            config.setDiscConfig("Value off");
        } else if (formVO.getDiscConfig().equalsIgnoreCase("1")) {
            config.setDiscConfig("Percentage Off");
        } else if (formVO.getDiscConfig().equalsIgnoreCase("2")) {
            config.setDiscConfig("Flat Price");
        }
        //config.setDiscConfig(formVO.getDiscConfig());
        config.setTicketWorthAmt(Double.parseDouble(formVO.getBuyWorthAmt()));
        config.setTicketWorthAmt(Double.parseDouble(formVO.getBuyWorthAmt()));
        config.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
        config.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
        config.setSellThruQty(Double.parseDouble(formVO.getSellQty()));
        config.setGrowthCoversion(Double.parseDouble(formVO.getGrowthConversion()));
        config.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        config.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        config.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        return config;
    }

    public static void getUploadedFileArticleMCList(TransPromoVO promoVo, List<ValidateArticleMCVO> articleMCList) {
        if (articleMCList != null && articleMCList.size() > 0) {
            TransPromoArticleVO article = null;
            for (ValidateArticleMCVO vo : articleMCList) {
                article = new TransPromoArticleVO();
                article.setArtCode(vo.getArticleCode());
                article.setArtDesc(vo.getArticleDesc());
                article.setMcCode(vo.getMcCode());
                article.setMcDesc(vo.getMcDesc());
                //       article.setQty(vo.getQty());
                article.setIsX(true);
                promoVo.getTransPromoArticleList().add(article);
            }
        }
    }
}
