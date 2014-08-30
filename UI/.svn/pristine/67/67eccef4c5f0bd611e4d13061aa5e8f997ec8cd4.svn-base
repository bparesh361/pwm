/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.CreateTransPromoReq;
import com.fks.promo.init.Resp;
import com.fks.promo.init.RespCode;
import com.fks.promo.init.TransPromoArticleVO;
import com.fks.promo.init.TransPromoConfigVO;
import com.fks.promo.init.TransPromoFileVO;
import com.fks.promo.init.TransPromoVO;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.form.vo.CreatePromoInitiateFormVO;
import java.text.ParseException;

/**
 *
 * @author ajitn
 */
public class SubPromoInitiationUtil {

    public static Resp getSubPromoRequest(String zoneId, String empId, CreatePromoInitiateFormVO formVO) {
        try {
            CreateTransPromoReq request = new CreateTransPromoReq();
            TransPromoVO promoVo = new TransPromoVO();

            setArticleDtl(promoVo, formVO);

            setDisconfigDtl(promoVo, formVO);

            promoVo.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
            promoVo.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
            promoVo.setRemark(formVO.getTxtRemarks());

            if (formVO.getPromoTypeId().equalsIgnoreCase("7")) {
                promoVo.setBuyQty(Integer.parseInt(formVO.getBuyQtyBXGY()));
                promoVo.setGetQty(Integer.parseInt(formVO.getGetQtyBXGY()));
            }
            request.setTransPromoVO(promoVo);

            request.setTypeId(Long.valueOf(formVO.getPromoTypeId()));

            request.setMstPromoId(Long.valueOf(formVO.getMstPromoId()));

            request.setZoneId(Long.valueOf(zoneId));

            request.setEmpId(Long.valueOf(empId));

            if (formVO.getIsManualEntry().equalsIgnoreCase("1")) {
                return ServiceMaster.getTransPromoService().createTransPromo(request);
            } else {
                return ServiceMaster.getTransPromoService().createTransPromoWithFile(request);
            }
        } catch (Exception ex) {
            System.out.println("-------- Exception IN getSubPromoRequest SubPromoInitiationUtil : " + ex.getMessage());
            ex.printStackTrace();
            Resp resp = new Resp();
            resp.setRespCode(RespCode.FAILURE);
            resp.setMsg("Error : " + ex.getMessage());
            return resp;
        }
    }

    private static void setArticleDtl(TransPromoVO promoVo, CreatePromoInitiateFormVO formVO) {
        if (formVO.getIsManualEntry().equalsIgnoreCase("1")) {
            TransPromoArticleVO article = null;
            if (formVO.getManualArticleData() != null) {
                String[] articleGridData = formVO.getManualArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 9; i++) {
                    article = new TransPromoArticleVO();
                    if (formVO.getPromoTypeId().equalsIgnoreCase("1") || formVO.getPromoTypeId().equalsIgnoreCase("2") || formVO.getPromoTypeId().equalsIgnoreCase("6")) {
                        article.setSetId(Integer.parseInt(articleGridData[i * 9]));
                    } else {
                        article.setSetId(1);
                    }
                    article.setArtCode(articleGridData[(i * 9) + 2]);
                    article.setArtDesc(articleGridData[(i * 9) + 3]);
                    article.setMcCode(articleGridData[(i * 9) + 4]);
                    article.setMcDesc(articleGridData[(i * 9) + 5]);
                    if (formVO.getPromoTypeId().equalsIgnoreCase("1") || formVO.getPromoTypeId().equalsIgnoreCase("2") || formVO.getPromoTypeId().equalsIgnoreCase("4")) {
                        article.setQty(Integer.parseInt(articleGridData[(i * 9) + 6]));
                    }
                    article.setBrandCode(articleGridData[(i * 9) + 7]);
                    article.setBrandDesc(articleGridData[(i * 9) + 8]);

                    promoVo.getTransPromoArticleList().add(article);
                }
            }
        } else {
            System.out.println("--------------- Inside Upload File Article Data");
            TransPromoFileVO articleFileVo = null;
            if (formVO.getManualArticleData() != null) {
                String[] articleGridData = formVO.getManualArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 3; i++) {
                    articleFileVo = new TransPromoFileVO();
                    articleFileVo.setSetId(Integer.parseInt(articleGridData[i * 3]));
                    articleFileVo.setFilePath(articleGridData[(i * 3) + 2]);
                    promoVo.getTransPromoFileList().add(articleFileVo);
                }
            }
        }
    }

    private static void setDisconfigDtl(TransPromoVO promoVo, CreatePromoInitiateFormVO formVO) throws ParseException {
        String promoTypeId = formVO.getPromoTypeId();
        TransPromoConfigVO config = null;
        if (formVO.getDiscountConfigGridData() != null && formVO.getDiscountConfigGridData().length() > 0) {
            System.out.println("--------- Inside Discount Config Grid Data Reading.");
            System.out.println("--------- Discount Config Grid Data " + formVO.getDiscountConfigGridData());
            String[] discountGridData = formVO.getDiscountConfigGridData().split(",");

            int qty = 0;
            double value = 0;
            for (int i = 0; i < discountGridData.length / 5; i++) {
                config = new TransPromoConfigVO();

                // promotype id = 1,2,3,4,6,7 common
                config.setDiscConfig(discountGridData[(i * 5) + 2]);

                value = Double.parseDouble(discountGridData[(i * 5) + 3]);
                config.setDiscValue(value);


                if (promoTypeId.equalsIgnoreCase("1") || promoTypeId.equalsIgnoreCase("2")) {
                    config.setSetId(Integer.parseInt(discountGridData[(i * 5)]));
                    qty = Integer.parseInt(discountGridData[(i * 5) + 4]);
                    config.setQty(qty);
                } else {
                    config.setSetId(1);
                }

                if (promoTypeId.equalsIgnoreCase("4") || promoTypeId.equalsIgnoreCase("6")) {
                    qty = Integer.parseInt(discountGridData[(i * 5) + 4]);
                    config.setQty(qty);
                }

                if (promoTypeId.equalsIgnoreCase("6")) {
                    config.setTicketWorthAmt(Double.parseDouble(formVO.getBuyWorthAmtTicketPool()));
                }

                config.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
                if (formVO.getTicketSizeGrowth() != null && formVO.getTicketSizeGrowth().length() > 0) {
                    config.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
                }
                config.setSellThruQty(Double.parseDouble(formVO.getSellQty()));
                if (formVO.getConversionGrowth() != null && formVO.getConversionGrowth().length() > 0) {
                    config.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
                }
                config.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
                config.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
                config.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));

                promoVo.getTransPromoConfigList().add(config);
            }
        } else if (promoTypeId.equalsIgnoreCase("5")) {
            System.out.println("------- dis config : " + formVO.getDiscountConfigTicketBill() + ". ------- worht amt : " + formVO.getBuyWorthAmtTicketBill());
            config = new TransPromoConfigVO();

            if (formVO.getDiscountConfigTicketBill().equalsIgnoreCase("0")) {
                config.setDiscConfig("Value off");
            } else if (formVO.getDiscountConfigTicketBill().equalsIgnoreCase("1")) {
                config.setDiscConfig("Percentage Off");
            } else if (formVO.getDiscountConfigTicketBill().equalsIgnoreCase("2")) {
                config.setDiscConfig("Flat Price");
            }

            config.setTicketWorthAmt(Double.parseDouble(formVO.getBuyWorthAmtTicketBill()));
            config.setTicketDiscAmt(Double.parseDouble(formVO.getDiscountAmtTicketBill()));
            config.setDiscValue(Double.parseDouble(formVO.getDiscountAmtTicketBill()));

            config.setSetId(1);

            config.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
            if (formVO.getTicketSizeGrowth() != null && formVO.getTicketSizeGrowth().length() > 0) {
                config.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
            }
            config.setSellThruQty(Double.parseDouble(formVO.getSellQty()));

            if (formVO.getConversionGrowth() != null && formVO.getConversionGrowth().length() > 0) {
                config.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
            }
            config.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
            config.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
            config.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));

            promoVo.getTransPromoConfigList().add(config);
        } else {
            System.out.println("----------------- No PromoType Found");
        }
    }
}
