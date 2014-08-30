/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MapPromoFormat;
import com.fks.promo.entity.MapPromoStore;
import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.MstCampaign;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstEvent;
import com.fks.promo.entity.MstMktg;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.promo.entity.TransPromoFile;
import com.fks.promo.init.vo.DownloadEnum;
import com.fks.promo.init.vo.TransPromoArticleVO;
import com.fks.promo.init.vo.TransPromoConfigVO;
import com.fks.promo.init.vo.TransPromoFileVO;
import com.fks.promo.init.vo.TransPromoVO;
import com.fks.promo.master.vo.MstZoneVO;
import com.fks.promo.master.vo.StoreVO;
import com.fks.promotion.vo.PromotionVO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFObjectData;

/**
 *
 * @author Paresb
 */
public class TransPromoUtil {

    private static final Logger logger = Logger.getLogger(TransPromoUtil.class);
    private static Calendar calender = Calendar.getInstance();

    public static List<TransPromoArticle> getTransPromoArticle(TransPromoVO vo, TransPromo transPromo, MstStatus status, MstEmployee emp) throws Exception {

        List<TransPromoArticle> promoArticleList = new ArrayList<TransPromoArticle>();
        if (vo.getTransPromoArticleList() != null) {
            for (TransPromoArticleVO articleVO : vo.getTransPromoArticleList()) {
                TransPromoArticle transArticle = new TransPromoArticle();
                transArticle.setArticle(articleVO.getArtCode());
                transArticle.setArticleDesc(articleVO.getArtDesc());
                transArticle.setMcCode(articleVO.getMcCode());
                transArticle.setMcDesc(articleVO.getMcDesc());
                if (articleVO.getBrandCode() != null) {
                    transArticle.setBrandCode(articleVO.getBrandCode());
                }
                if (articleVO.getBrandDesc() != null) {
                    transArticle.setBrandDesc(articleVO.getBrandDesc());
                }
                transArticle.setQty(articleVO.getQty());
                transArticle.setSetId(articleVO.getSetId());
                transArticle.setMstStatus(status);
                transArticle.setMstEmployee(emp);
                transArticle.setUpdatedDate(new Date());
                if (articleVO.isIsX()) {
                    transArticle.setIsX(Boolean.TRUE);
                }
                if (articleVO.isIsY()) {
                    transArticle.setIsY(Boolean.TRUE);
                }
                if (articleVO.isIsZ()) {
                    transArticle.setIsZ(Boolean.TRUE);
                }
                if (articleVO.isIsA()) {
                    transArticle.setIsA(Boolean.TRUE);
                }
                if (articleVO.isIsB()) {
                    transArticle.setIsB(Boolean.TRUE);
                }
                transArticle.setTransPromo(transPromo);
                promoArticleList.add(transArticle);
            }


        }
        return promoArticleList;
    }

    public static List<TransPromoFile> getTransPromoFile(TransPromoVO vO, TransPromo promo) {
        List<TransPromoFile> transPromoFileList = new ArrayList<TransPromoFile>();
        if (vO.getTransPromoFileList() != null) {
            for (TransPromoFileVO file : vO.getTransPromoFileList()) {
                TransPromoFile promoFile = new TransPromoFile();
                if (file.getErrorFilePath() != null) {
                    promoFile.setErrorFilePath(file.getErrorFilePath());
                }
                if (file.getRemarks() != null) {
                    promoFile.setRemarks(file.getRemarks());
                }
                promoFile.setFilePath(file.getFilePath());
                promoFile.setSetId(file.getSetId());
                promoFile.setTransPromo(promo);
                promoFile.setIsArticleFile(Boolean.TRUE);
                transPromoFileList.add(promoFile);
            }
        }
        return transPromoFileList;
    }

    public static List<TransPromoConfig> getTransPromoConfig(TransPromoVO vo, TransPromo promo) throws Exception {
        List<TransPromoConfig> configList = new ArrayList<TransPromoConfig>();
        if (vo.getTransPromoConfigList() != null) {
            for (TransPromoConfigVO configVO : vo.getTransPromoConfigList()) {
                TransPromoConfig config = new TransPromoConfig();
                config.setDiscountConfig(configVO.getDiscConfig());
                config.setDiscountValue(configVO.getDiscValue());
                config.setGrowthConversion(configVO.getGrowthCoversion());
                config.setMarginAchivement(configVO.getMarginAchievement());
                config.setQty(configVO.getQty());
                config.setSalesGrowth(configVO.getSalesGrowth());
                config.setSellthruvsQty(configVO.getSellThruQty());
                config.setTicketDiscAmt(configVO.getTicketDiscAmt());
                config.setTicketWorthAmt(configVO.getTicketWorthAmt());
                config.setTicketSizeGrowth(configVO.getTicketSizeGrowth());
                if (configVO.getValidFrom() != null) {
                    config.setValidFrom(CommonUtil.getDBDate(configVO.getValidFrom()));
                }
                if (configVO.getValidTo() != null) {
                    config.setValidTo(CommonUtil.getDBDate(configVO.getValidTo()));
                }
                if (configVO.isIsX()) {
                    config.setIsX(Boolean.TRUE);
                }
                if (configVO.isIsY()) {
                    config.setIsY(Boolean.TRUE);
                }
                if (configVO.isIsZ()) {
                    config.setIsZ(Boolean.TRUE);
                }
                if (configVO.isIsA()) {
                    config.setIsA(Boolean.TRUE);
                }
                if (configVO.isIsB()) {
                    config.setIsB(Boolean.TRUE);
                }
                config.setSetId(configVO.getSetId());
                config.setTransPromo(promo);
                configList.add(config);

            }
        }
        return configList;
    }

    public static TransPromo getTransPromo(TransPromoVO vo, MstPromo mstpromo, MstPromotionType type, MstStatus status, MstZone zone, MstEmployee emp, boolean isUpdate, TransPromo promo) throws Exception {
        TransPromo transpromo = null;
        if (!isUpdate) {
            transpromo = new TransPromo();
        } else {
            transpromo = promo;
        }
        transpromo.setMstPromo(mstpromo);
        transpromo.setMstPromotionType(type);
        transpromo.setUpdatedDate(new Date());
        transpromo.setMstStatus(status);
        transpromo.setMstZone(zone);
        transpromo.setZoneName(zone.getZoneName());
        transpromo.setMstEmployee4(emp);
        transpromo.setRemarks(vo.getRemark());
        transpromo.setIsHo(false);
        transpromo.setValidFrom(CommonUtil.getDBDate(vo.getValidFrom()));
        transpromo.setValidTo(CommonUtil.getDBDate(vo.getValidTo()));
        if (type.getPromoTypeId().equals(new Long("7"))) {
            transpromo.setBuyQty(vo.getBuyQty());
            transpromo.setGetQty(vo.getGetQty());
        }
        return transpromo;
    }

    public static TransPromo getTransPromoWithFile(MstPromo mstpromo, MstStatus status, MstZone zone, MstEmployee emp) throws Exception {
        TransPromo transpromo = new TransPromo();
        transpromo.setMstPromo(mstpromo);
        transpromo.setUpdatedDate(new Date());
        transpromo.setMstStatus(status);
        transpromo.setMstZone(zone);
        transpromo.setZoneName(zone.getZoneName());
        transpromo.setMstEmployee4(emp);
        transpromo.setRemarks(null);
        transpromo.setIsHo(false);

        return transpromo;
    }

    public static List<TransPromoVO> covertTransPromoFromMstPromo(MstPromo promo) {
        logger.info(" === Promotion Covertion from Mst Promo to Trans Promo");
        List<TransPromoVO> list = new ArrayList<TransPromoVO>();
        if (promo.getTransPromoCollection() != null) {
            for (TransPromo transPromo : promo.getTransPromoCollection()) {
                TransPromoVO vo = new TransPromoVO();
                vo.setMstPromoId(promo.getPromoId());
                if (transPromo.getMstPromotionType() != null) {
                    vo.setPromoTypeId(transPromo.getMstPromotionType().getPromoTypeId());
                }
                if (transPromo.getTransPromoArticleCollection() != null) {
                    List<TransPromoConfigVO> configVOList = new ArrayList<TransPromoConfigVO>();
                    for (TransPromoConfig promoConfig : transPromo.getTransPromoConfigCollection()) {
                        TransPromoConfigVO configVO = new TransPromoConfigVO();
                        configVO.setDiscConfig(promoConfig.getDiscountConfig());
                        configVO.setDiscValue(promoConfig.getDiscountValue());
                        configVO.setGrowthCoversion(promoConfig.getGrowthConversion());
                        configVO.setMarginAchievement(promoConfig.getMarginAchivement());
                        configVO.setQty(promoConfig.getQty());
                        configVO.setSalesGrowth(promoConfig.getSalesGrowth());
                        configVO.setSellThruQty(promoConfig.getSellthruvsQty());
                        configVO.setTicketDiscAmt(promoConfig.getTicketDiscAmt());
                        configVO.setTicketSizeGrowth(promoConfig.getTicketSizeGrowth());
                        if (promoConfig.getTicketWorthAmt() != null) {
                            configVO.setTicketWorthAmt(promoConfig.getTicketWorthAmt());
                        }
                        configVO.setTransPromoConfigId(promoConfig.getTransPromoConfigId());
                        if (promoConfig.getValidFrom() != null) {
                            configVO.setValidFrom(promoConfig.getValidFrom().toString());
                        }
                        if (promoConfig.getValidTo() != null) {
                            configVO.setValidTo(promoConfig.getValidTo().toString());
                        }
                        if (promoConfig.getIsX() != null) {
                            configVO.setIsX(promoConfig.getIsX());
                        }
                        if (promoConfig.getIsY() != null) {
                            configVO.setIsY(promoConfig.getIsY());
                        }
                        if (promoConfig.getIsZ() != null) {
                            configVO.setIsZ(promoConfig.getIsZ());
                        }
                        if (promoConfig.getIsA() != null) {
                            configVO.setIsA(promoConfig.getIsA());
                        }
                        if (promoConfig.getIsB() != null) {
                            configVO.setIsB(promoConfig.getIsB());
                        }
                    }
                    vo.setTransPromoConfigList(configVOList);
                }

                if (transPromo.getTransPromoArticleCollection() != null) {
                    List<TransPromoArticleVO> articleList = new ArrayList<TransPromoArticleVO>();
                    for (TransPromoArticle article : transPromo.getTransPromoArticleCollection()) {
                        TransPromoArticleVO articleVO = new TransPromoArticleVO();
                        articleVO.setArtCode(article.getArticle());
                        articleVO.setArtDesc(article.getArticleDesc());
                        articleVO.setMcCode(article.getMcCode());
                        articleVO.setMcDesc(article.getMcDesc());
                        if (article.getIsX() != null) {
                            articleVO.setIsX(article.getIsX());
                        }
                        if (article.getIsY() != null) {
                            articleVO.setIsY(article.getIsY());
                        }
                        if (article.getIsZ() != null) {
                            articleVO.setIsZ(article.getIsZ());
                        }
                        if (article.getIsA() != null) {
                            articleVO.setIsA(article.getIsA());
                        }
                        if (article.getIsB() != null) {
                            articleVO.setIsZ(article.getIsB());
                        }
                        articleVO.setQty(article.getQty());
                        articleVO.setTransPromoArticleId(transPromo.getTransPromoId());
                        articleList.add(articleVO);
                    }
                    vo.setTransPromoArticleList(articleList);
                }
                list.add(vo);

            }

        }
        return list;
    }

    public static TransPromoVO covertTransPromoFromTransPromo(TransPromo promo) {
        logger.info(" === Promotion Covertion from Trans Promo to Trans Promo VO");


        TransPromoVO vo = new TransPromoVO();
        vo.setMstPromoId(promo.getMstPromo().getPromoId());
        vo.setTransPromoId(promo.getTransPromoId());
        //To set zone name:
        vo.setValidFrom(CommonUtil.dispayDateFormat(promo.getValidFrom()));
        vo.setValidTo(CommonUtil.dispayDateFormat(promo.getValidTo()));

        Collection<MapPromoZone> promoZoneCollection = promo.getMstPromo().getMapPromoZoneCollection();
        if (promoZoneCollection != null) {
            List<MstZoneVO> lstMstZoneVOs = new ArrayList<MstZoneVO>();
            for (MapPromoZone mapPromoZone : promoZoneCollection) {
                MstZoneVO zoneVO = new MstZoneVO();
                zoneVO.setZonename(mapPromoZone.getMstZone().getZoneName());
                lstMstZoneVOs.add(zoneVO);
            }
            vo.setZoneDesc(lstMstZoneVOs);
        }
        //To set sitecode and site description.
        Collection<MapPromoStore> promoStoreCollection = promo.getMstPromo().getMapPromoStoreCollection();
        if (promoStoreCollection != null) {
            List<StoreVO> lstStoreVOs = new ArrayList<StoreVO>();
            for (MapPromoStore mapPromoStore : promoStoreCollection) {
                StoreVO store = new StoreVO();
                store.setStoreID(mapPromoStore.getMstStore().getMstStoreId());
                store.setStoreDesc(mapPromoStore.getMstStore().getSiteDescription());
                lstStoreVOs.add(store);
            }
            vo.setLstStoreVOs(lstStoreVOs);
        }
        //To set promotion type and details.
        if (promo.getMstPromotionType() != null) {
            vo.setPromoTypeId(promo.getMstPromotionType().getPromoTypeId());
            vo.setPromotionType(promo.getMstPromotionType().getPromoTypeName());
            vo.setPromoTypeDesc(promo.getMstPromotionType().getPromoTypeName());
        }

        //To set format:
        Collection<MapPromoFormat> lstMapPromoFormats = promo.getMstPromo().getMapPromoFormatCollection();
        if (lstMapPromoFormats != null) {
            List<String> lstformat = new ArrayList<String>();
            for (MapPromoFormat format : lstMapPromoFormats) {
                lstformat.add(format.getFormatDesc());
            }
            vo.setFormat(lstformat);
        }

        if (promo.getTransPromoArticleCollection() != null) {
            List<TransPromoArticleVO> articleList = new ArrayList<TransPromoArticleVO>();
            for (TransPromoArticle article : promo.getTransPromoArticleCollection()) {
                TransPromoArticleVO articleVO = new TransPromoArticleVO();
                articleVO.setArtCode(article.getArticle());
                articleVO.setArtDesc(article.getArticleDesc());
                articleVO.setMcCode(article.getMcCode());
                articleVO.setMcDesc(article.getMcDesc());
                if (article.getMrp() != null) {
                    articleVO.setMrp(article.getMrp());
                } else {
                    articleVO.setMrp("-");
                }

                if (article.getIsX() != null) {
                    articleVO.setIsX(article.getIsX());



                    vo.setRemark(promo.getRemarks());

                }
                if (article.getIsY() != null) {
                    articleVO.setIsY(article.getIsY());
                }
                if (article.getIsZ() != null) {
                    articleVO.setIsZ(article.getIsZ());
                }
                if (article.getIsA() != null) {
                    articleVO.setIsA(article.getIsA());
                }
                if (article.getIsB() != null) {
                    articleVO.setIsB(article.getIsB());
                }

                if (article.getQty() != null) {
                    articleVO.setQty(article.getQty());
                }

                articleVO.setTransPromoArticleId(promo.getTransPromoId());
                articleList.add(articleVO);
                if (article.getIsX() != null && article.getIsX() == true) {
                    articleVO.setGroup("Group1");
                } else if (article.getIsY() != null && article.getIsY() == true) {
                    articleVO.setGroup("Group2");
                } else if (article.getIsZ() != null && article.getIsZ() == true) {
                    articleVO.setGroup("Group3");
                } else if (article.getIsA() != null && article.getIsA() == true) {
                    articleVO.setGroup("Group4");
                } else if (article.getIsB() != null && article.getIsB() == true) {
                    articleVO.setGroup("Group5");
                }
            }
            vo.setTransPromoArticleList(articleList);
        }
        if (promo.getTransPromoArticleCollection() != null) {
            List<TransPromoConfigVO> configVOList = new ArrayList<TransPromoConfigVO>();
            for (TransPromoConfig promoConfig : promo.getTransPromoConfigCollection()) {
                TransPromoConfigVO configVO = new TransPromoConfigVO();
                configVO.setDiscConfig(promoConfig.getDiscountConfig());
                configVO.setDiscValue(promoConfig.getDiscountValue());
                configVO.setGrowthCoversion(promoConfig.getGrowthConversion());
                configVO.setMarginAchievement(promoConfig.getMarginAchivement());
                configVO.setQty(promoConfig.getQty());
                configVO.setSalesGrowth(promoConfig.getSalesGrowth());
                configVO.setSellThruQty(promoConfig.getSellthruvsQty());
                configVO.setTicketDiscAmt(promoConfig.getTicketDiscAmt());
                configVO.setTicketSizeGrowth(promoConfig.getTicketSizeGrowth());
                if (promoConfig.getTicketWorthAmt() != null) {
                    configVO.setTicketWorthAmt(promoConfig.getTicketWorthAmt());
                }
                configVO.setTransPromoConfigId(promoConfig.getTransPromoConfigId());
                if (promoConfig.getValidFrom() != null) {
                    configVO.setValidFrom(CommonUtil.dispayDateFormat(promoConfig.getValidFrom()));
                }
                if (promoConfig.getValidTo() != null) {
                    configVO.setValidTo(CommonUtil.dispayDateFormat(promoConfig.getValidTo()));
                }
                if (promoConfig.getIsX() != null) {
                    configVO.setIsX(promoConfig.getIsX());
                }
                if (promoConfig.getIsY() != null) {
                    configVO.setIsY(promoConfig.getIsY());
                }
                if (promoConfig.getIsZ() != null) {
                    configVO.setIsZ(promoConfig.getIsZ());
                }
                if (promoConfig.getIsA() != null) {
                    configVO.setIsA(promoConfig.getIsA());
                }
                if (promoConfig.getIsB() != null) {
                    configVO.setIsB(promoConfig.getIsB());
                } else if (promoConfig.getIsX() != null && promoConfig.getIsX() == true) {
                    configVO.setGroup("Group1");
                } else if (promoConfig.getIsY() != null && promoConfig.getIsY() == true) {
                    configVO.setGroup("Group2");
                } else if (promoConfig.getIsZ() != null && promoConfig.getIsZ() == true) {
                    configVO.setGroup("Group3");
                } else if (promoConfig.getIsA() != null && promoConfig.getIsA() == true) {
                    configVO.setGroup("Group4");
                } else if (promoConfig.getIsB() != null && promoConfig.getIsB() == true) {
                    configVO.setGroup("Group5");
                }
                configVOList.add(configVO);
            }
            vo.setTransPromoConfigList(configVOList);
        }



        return vo;

    }

    public static List<PromotionVO> convertMstPromoToPromoVO(List<MstPromo> promoList) {
        List<PromotionVO> list = new ArrayList<PromotionVO>();
        if (promoList != null) {
            for (MstPromo mp : promoList) {
                PromotionVO promotionVO = new PromotionVO();
                promotionVO = new PromotionVO();
                promotionVO.setCategory(mp.getCategory());
                promotionVO.setCreatedDate(CommonUtil.dispayDateFormat(mp.getCreatedDate()));
//                    promotionVO.setCreatedEmpName(mp.getMstEmployee1().getEmployeeName());
                promotionVO.setEmpId(mp.getMstEmployee1().getEmpId());
                promotionVO.setEventId(mp.getMstEvent().getEventId());
                promotionVO.setEventName(mp.getMstEvent().getEventName());
                promotionVO.setMktgId(mp.getMstMktg().getMktgId());
                promotionVO.setMktgName(mp.getMstMktg().getMktgName());
                promotionVO.setRemarks(mp.getRemarks());
                promotionVO.setReqName(mp.getRequestName());
                promotionVO.setReqId(mp.getPromoId().toString());
                promotionVO.setSubCategory(mp.getSubCategory());
                promotionVO.setStatusID(mp.getMstStatus().getStatusId());
                if (mp.getUpdatedDate() != null) {
                    promotionVO.setUpdatedDate(CommonUtil.dispayDateFormat(mp.getUpdatedDate()));
                }
                if (mp.getErrorFilePath() != null) {
                    promotionVO.setErrorFilePath(mp.getErrorFilePath());
                } else {
                    promotionVO.setErrorFilePath("-");
                }
                if(mp.getFilePath()!=null){
                    promotionVO.setUploadFilePath(mp.getFilePath());
                }else{
                    promotionVO.setUploadFilePath("-");
                }
                //promotionVO.setStatusName(mp.getMstStatus().getStatusDesc());
                list.add(promotionVO);
            }
        }
        return list;
    }

    static List<TransPromoVO> converTransPromoVOFromTransPromo(List<TransPromo> trans) {
        logger.info(" === Promotion Covertion from Mst Promo to Trans Promo");
        List<TransPromoVO> list = new ArrayList<TransPromoVO>();
        if (trans != null) {
            for (TransPromo transPromo : trans) {
                TransPromoVO vo = new TransPromoVO();
                if (transPromo.getUpdatedDate() != null) {
                    vo.setUpdatedDate(CommonUtil.dispayDateFormat(transPromo.getUpdatedDate()));
                }
                vo.setMstPromoId(transPromo.getMstPromo().getPromoId());
                vo.setRemark(transPromo.getRemarks());
                vo.setMstPromoId(transPromo.getMstPromo().getPromoId());
                if (transPromo.getMstPromotionType() != null) {
                    vo.setPromoTypeId(transPromo.getMstPromotionType().getPromoTypeId());
                }


                if (transPromo.getTransPromoConfigCollection() != null) {
                    List<TransPromoConfigVO> configVOList = new ArrayList<TransPromoConfigVO>();
                    for (TransPromoConfig promoConfig : transPromo.getTransPromoConfigCollection()) {
                        TransPromoConfigVO configVO = new TransPromoConfigVO();
                        configVO.setDiscConfig(promoConfig.getDiscountConfig());
                        configVO.setDiscValue(promoConfig.getDiscountValue());
                        configVO.setGrowthCoversion(promoConfig.getGrowthConversion());
                        configVO.setMarginAchievement(promoConfig.getMarginAchivement());
                        configVO.setQty(promoConfig.getQty());
                        configVO.setSalesGrowth(promoConfig.getSalesGrowth());
                        configVO.setSellThruQty(promoConfig.getSellthruvsQty());
                        configVO.setTicketDiscAmt(promoConfig.getTicketDiscAmt());
                        configVO.setTicketSizeGrowth(promoConfig.getTicketSizeGrowth());
                        if (promoConfig.getTicketWorthAmt() != null) {
                            configVO.setTicketWorthAmt(promoConfig.getTicketWorthAmt());
                        }
                        configVO.setTransPromoConfigId(promoConfig.getTransPromoConfigId());
                        if (promoConfig.getValidFrom() != null) {
                            configVO.setValidFrom(CommonUtil.dispayDateFormat(promoConfig.getValidFrom()));
                        }
                        if (promoConfig.getValidTo() != null) {
                            configVO.setValidTo(CommonUtil.dispayDateFormat(promoConfig.getValidTo()));
                        }
                        configVOList.add(configVO);
                        vo.setValidFrom(CommonUtil.dispayDateFormat(promoConfig.getValidFrom()));
                        vo.setValidTo(CommonUtil.dispayDateFormat(promoConfig.getValidTo()));
                    }
                    vo.setTransPromoConfigList(configVOList);
                }

                // comment the following because article details is not needed when l2,l2,businss exigency
                // promo manager, promo executor dashboards are loaded.
                /*
                if (transPromo.getTransPromoArticleCollection() != null) {
                List<TransPromoArticleVO> articleList = new ArrayList<TransPromoArticleVO>();
                for (TransPromoArticle article : transPromo.getTransPromoArticleCollection()) {
                TransPromoArticleVO articleVO = new TransPromoArticleVO();
                articleVO.setArtCode(article.getArticle());
                articleVO.setArtDesc(article.getArticleDesc());
                articleVO.setMcCode(article.getMcCode());
                articleVO.setMcDesc(article.getMcDesc());
                if (article.getIsX() != null) {
                articleVO.setIsX(article.getIsX());
                }
                if (article.getIsY() != null) {
                articleVO.setIsY(article.getIsY());
                }
                if (article.getIsZ() != null) {
                articleVO.setIsZ(article.getIsZ());
                }
                if (article.getQty() != null) {
                articleVO.setQty(article.getQty());
                }
                articleVO.setTransPromoArticleId(transPromo.getTransPromoId());
                articleList.add(articleVO);
                }
                vo.setTransPromoArticleList(articleList);
                }
                 */
                vo.setTransPromoId(transPromo.getTransPromoId());
                vo.setTeamMemberassigneDate(CommonUtil.dispayDateFormat(transPromo.getUpdatedDate()));
//                vo.setDate(CommonUtil.dispayDateFormat(transPromo.getUpdatedDate()));
//                vo.setTime(CommonUtil.getTime(transPromo.getUpdatedDate()));
                if (transPromo.getMstPromo().getUpdatedDate() != null) {
                    vo.setDate(CommonUtil.dispayDateFormat(transPromo.getMstPromo().getCreatedDate()));
                    vo.setTime(CommonUtil.getTime(transPromo.getMstPromo().getUpdatedDate()));
                } else {
                    vo.setDate("-");
                    vo.setTime("-");
                }

                vo.setInitiatorName(transPromo.getMstPromo().getMstEmployee1().getEmployeeName());
                vo.setContactNo(transPromo.getMstPromo().getMstEmployee1().getContactNo().toString());
                vo.setEmpCode(transPromo.getMstPromo().getMstEmployee1().getEmpCode().toString());
                vo.setInitiatorLocation(transPromo.getMstPromo().getMstEmployee1().getMstStore().getMstLocation().getLocationName());
                vo.setRequestName(transPromo.getMstPromo().getRequestName());
                vo.setEvent(transPromo.getMstPromo().getMstEvent().getEventName());
                vo.setMarketingType(transPromo.getMstPromo().getMstMktg().getMktgName());
                vo.setCategory(transPromo.getMstPromo().getCategory());
                vo.setSubCategory(transPromo.getMstPromo().getSubCategory());
                if (transPromo.getMstPromotionType() != null) {
                    vo.setPromotionType(transPromo.getMstPromotionType().getPromoTypeName());
                } else {
                    vo.setPromotionType("-");
                }
                vo.setStatus(transPromo.getMstStatus().getStatusDesc());
                vo.setStatusId(transPromo.getMstStatus().getStatusId().toString());
                vo.setSubCategory(transPromo.getMstPromo().getSubCategory());
                vo.setRemark(transPromo.getMstPromo().getRemarks());
                if (transPromo.getMstEmployee() != null) {
                    vo.setApproverName(transPromo.getMstEmployee().getEmployeeName());
                    vo.setApproverFrom("L2");
                }
                if (transPromo.getMstEmployee1() != null) {
                    vo.setApproverName(transPromo.getMstEmployee1().getEmployeeName());
                    vo.setApproverFrom("L1");
                }
                if (transPromo.getMstEmployee2() != null) {
                    vo.setApproverName(transPromo.getMstEmployee2().getEmployeeName());
                    vo.setApproverFrom("Business Exigency");
                }

                if (transPromo.getMstEmployee3() != null) {
                    vo.setTeamAssignedTo(transPromo.getMstEmployee3().getEmployeeName());
                    vo.setTeamAssignedToLocation(transPromo.getMstEmployee3().getMstStore().getSiteDescription());
                }

                if (transPromo.getMstEmployee5() != null) {
                    vo.setTeamAssignedBy(transPromo.getMstEmployee5().getEmployeeName());
                    vo.setTeamAssignmentLocation(transPromo.getMstEmployee5().getMstStore().getSiteDescription());
                }
                if (transPromo.getMstPromo().getMstCampaign() != null) {
                    vo.setCampaignName(transPromo.getMstPromo().getMstCampaign().getCampaignName());
                } else {
                    vo.setCampaignName("-");
                }


                list.add(vo);

            }

        }
        return list;
    }

    public static List<PromotionVO> convertMstPromoAndTransPromoToPromoVO(List<TransPromo> promoList) {
        List<PromotionVO> list = new ArrayList<PromotionVO>();
        if (promoList != null) {
            for (TransPromo mp : promoList) {
                PromotionVO promotionVO = new PromotionVO();
//                promotionVO = new PromotionVO();
                promotionVO.setCategory(mp.getMstPromo().getCategory());
                promotionVO.setCreatedDate(CommonUtil.dispayDateFormat(mp.getMstPromo().getCreatedDate()));
                promotionVO.setUpdatedDate(CommonUtil.dispayDateFormat(mp.getUpdatedDate()));
                promotionVO.setTransPromoId(mp.getTransPromoId());
//                    promotionVO.setCreatedEmpName(mp.getMstEmployee1().getEmployeeName());
//                promotionVO.setEmpId(mp.getMstEmployee1().getEmpId());
                if (mp.getMstPromo().getMstCampaign() != null) {
                    promotionVO.setCampaignId(mp.getMstPromo().getMstCampaign().getCampaignId());
                    promotionVO.setCampaignName(mp.getMstPromo().getMstCampaign().getCampaignName());
                } else {
                    promotionVO.setCampaignId(0l);
                    promotionVO.setCampaignName("-");
                }

                promotionVO.setEventId(mp.getMstPromo().getMstEvent().getEventId());
                promotionVO.setEventName(mp.getMstPromo().getMstEvent().getEventName());
                promotionVO.setMktgId(mp.getMstPromo().getMstMktg().getMktgId());
                promotionVO.setMktgName(mp.getMstPromo().getMstMktg().getMktgName());
                //promotionVO.setRemarks(mp.getRemarks());
                promotionVO.setReqName(mp.getMstPromo().getRequestName());
                promotionVO.setReqId(mp.getMstPromo().getPromoId().toString());
                promotionVO.setSubCategory(mp.getMstPromo().getSubCategory());
                promotionVO.setStatusID(mp.getMstStatus().getStatusId());
                promotionVO.setStatusName(mp.getMstStatus().getStatusDesc());
                //promotionVO.setRejectRemarks(mp.getRemarks());
                if (mp.getReasonForRejection() != null) {
                    promotionVO.setReasonRejection(mp.getReasonForRejection());
                } else {
                    promotionVO.setReasonRejection("-");
                }
                if (mp.getMstPromotionType() != null) {
                    promotionVO.setPromoType(mp.getMstPromotionType().getPromoTypeName());
                }
                if (mp.getMstStatus() != null) {
                    if (mp.getMstStatus().getStatusId().equals(CommonStatusConstants.FILE_FAILURE)) {

                        Collection<TransPromoFile> promoFileColelction = mp.getTransPromoFileCollection();
                        if (!promoFileColelction.isEmpty() && promoFileColelction.size() > 0) {
                            for (TransPromoFile file : promoFileColelction) {
                                if (file.getIsArticleFile() != null) {
//                                    if (file.getIsArticleFile() == false) {
                                    promotionVO.setErrorFilePath(file.getErrorFilePath());
                                    break;
//                                    }
                                }
                            }
                        }
                    }
                }
                list.add(promotionVO);
            }
        }
        return list;
    }

    public static MstPromo getMasterPromoReq(MstEvent event, MstMktg mktg, MstEmployee employee, MstCampaign campaign, PromotionVO promotionVO) {
        MstPromo mstPromo = new MstPromo();
        mstPromo.setCategory(promotionVO.getCategorySub());
        mstPromo.setCreatedDate(new Date());
        mstPromo.setMstEmployee1(employee);
        mstPromo.setMstEvent(event);
        mstPromo.setMstMktg(mktg);
        mstPromo.setMstCampaign(campaign);
        mstPromo.setMstStatus(new MstStatus(11l));
        mstPromo.setRemarks(promotionVO.getRemarks());
        mstPromo.setRequestName(promotionVO.getReqName());
        mstPromo.setSubCategory(promotionVO.getSubCategorySub());
        return mstPromo;
    }

    public static String downloadMultipleSubPromoFile(List<TransPromo> transPromoList, Long empId, DownloadEnum downloadOpt) {
        String filePath = null;
        WritableWorkbook book = null;
        try {
            String fileName = empId + "_SubPromotionDtl_" + CommonUtil.getCurrentTimeInMiliSeconds() + ".xls";
            filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.MULTIPLE_SUB_PROMO_DOWNLOAD_FILE) + fileName;
            book = getBlankWorkbook(filePath, downloadOpt);
            switch (downloadOpt) {
                case INITIATOR_TO_EXIGENCY_DOWNLOAD:
                    fillInitiatorToExigencyDtl(book, transPromoList);
                    break;
                case MANAGER_EXECUTOR_DOWNLOAD:
                    fillManagerToExecutorDtl(book, transPromoList);
                    break;
                case COMMUNICATION_DOWNLOAD:
                    fillCommunicationDtl(book, transPromoList);
                    break;
            }

            book.write();
        } catch (Exception ex) {
            System.out.println("------- error : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(TransPromoUtil.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WriteException ex) {
                    java.util.logging.Logger.getLogger(TransPromoUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        return filePath;
    }

    private static void fillInitiatorToExigencyDtl(WritableWorkbook book, List<TransPromo> transPromoList) throws WriteException {

        WritableSheet sheet = book.getSheet(0);

        int maxRowCount = 1;
        int promoRowCount = 1;
        int tempPromoRowCount = 1;
        int maxArticleSetNo = 0;


        for (TransPromo subPromo : transPromoList) {

            //Restore Promo Row Count In Temp Promo Row Count For Sub Promo Index
            tempPromoRowCount = promoRowCount;

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
            sheet.addCell(new Label(DownloadMulitiplePromoConstant.REQUEST_NO, tempPromoRowCount, requestNo));

            //Column Remarks
            String remarks = subPromo.getRemarks();
            if (remarks != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.REMARKS, tempPromoRowCount, remarks));
            }

            //Column Zone
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
                    String zoneDesc = zone.getMstZone().getZoneName();
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.ZONE, promoRowCount, zoneDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Site , Site Desc
            if (storeList != null && storeList.size() > 0) {
                for (MapPromoStore store : storeList) {
                    String storeCode = store.getMstStore().getMstStoreId();
                    String storeDesc = store.getMstStore().getSiteDescription();
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SIE_CODE, promoRowCount, Double.parseDouble(storeCode)));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.SITE_DESC, promoRowCount, storeDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Format
            if (formatList != null && formatList.size() > 0) {
                for (MapPromoFormat format : formatList) {
                    String formatDesc = format.getFormatDesc();
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.FORMAT, promoRowCount, formatDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Promotion Type
            String promoType = subPromo.getMstPromotionType().getPromoTypeName();
            sheet.addCell(new Label(DownloadMulitiplePromoConstant.PROMO_TYPE, promoRowCount, promoType));

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
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE_SET, promoRowCount, setNo));


                    if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE, promoRowCount, Double.parseDouble(articleCode)));
                    }
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE_DESC, promoRowCount, articleDesc));
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.MC, promoRowCount, Double.parseDouble(mcCode)));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.MC_DESC, promoRowCount, mcDesc));
                    if (article.getQty() != null && article.getQty() != 0) {
                        qty = article.getQty();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                    }

                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;


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


                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_FROM, promoRowCount, validFrom));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_TO, promoRowCount, validTo));

                // Column Margin Achivement

                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, promoRowCount, marginAchievement));

                //Column Cashier Trigger
                /*    if (subPromo.getCashierTrigger() != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, promoRowCount, subPromo.getCashierTrigger()));
                } else {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, promoRowCount, "-"));
                }
                 */
                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE, promoRowCount, growthTicketSize));
                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY, promoRowCount, sellThruQty));
                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION, promoRowCount, growthInConversion));
                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE, promoRowCount, salesGrowth));

            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

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
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, configVO.getSetId()));
                        }
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        double discountValue = configVO.getDiscountValue();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                        int qty = 0;
                        if (configVO.getQty() != null && configVO.getQty() != 0) {
                            qty = configVO.getQty();
                        }
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                        promoRowCount++;
                    }
                } else {
                    TransPromoConfig configVO = configList.get(0);

                    String discountType = configVO.getDiscountConfig();
                    if (discountType.equalsIgnoreCase("Percentage Off")) {
                        discountType = "%";
                    }
                    double discountValue = configVO.getDiscountValue();

//                    if (promoTypeId != 1 && promoTypeId != 4 && promoTypeId != 6 && promoTypeId != 7) {
                    if (promoTypeId == 3 || promoTypeId == 5) {
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                    }

//                    if (promoTypeId == 2) {
//                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, maxArticleSetNo));
//                    }

                    if (promoTypeId == 3) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, configVO.getSetId()));
                    }

                    if (promoTypeId == 4) {
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        double buyWorthAmt = configVO.getTicketWorthAmt();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QUALIFYING_AMT, promoRowCount, buyWorthAmt));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, 1));

                        if (promoTypeId == 6) {
                            promoRowCount++;

                            sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                            if (configVO.getQty() != null && configVO.getQty() != 0) {
                                int qty = configVO.getQty();
                                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                            }
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, 2));
                        }
//                        else {
//                            double discValue = configVO.getTicketDiscAmt();
//                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discValue));
//                        }
                    }

                    if (promoTypeId == 7) {
                        int buyQty = subPromo.getBuyQty();
                        int getQty = subPromo.getGetQty();

                        if (buyQty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, buyQty));
                        }
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, 1));
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                        promoRowCount++;
                        if (getQty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, getQty));
                        }
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Multiple Set , Discount Type , Discount Value  For PromoType Id 1
            //Column Repeating Set value And Multiple  Qty , Discount Value  For PromoType Id 4
//            if (configList != null && configList.size() > 0 && (promoTypeId == 1 || promoTypeId == 4)) {
            if (configList != null && configList.size() > 0 && (promoTypeId == 4)) {
                for (TransPromoConfig config : configList) {


                    double discountValue = config.getDiscountValue();

                    if (config.getSetId() != null) {
                        int set = config.getSetId();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, set));
                    }
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));

                    if (promoTypeId == 1) {
                        String discountType = config.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, maxArticleSetNo));

                    } else {
                        int qty = config.getQty();
                        if (qty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                        }
                    }
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            maxRowCount++;

            //Increment PromoRowCount By 2 For Giving One Space Between Two Promotions
//                if (promoTypeId == 1 || promoTypeId == 4) {
//                    maxRowCount++;
//                } else {
//                    maxRowCount += 2;
//                }

            promoRowCount = maxRowCount;

        }
    }

    private static void fillManagerToExecutorDtl(WritableWorkbook book, List<TransPromo> transPromoList) throws WriteException {
        WritableSheet sheet = book.getSheet(0);

        int maxRowCount = 1;
        int promoRowCount = 1;
        int tempPromoRowCount = 1;
        int maxArticleSetNo = 0;


        for (TransPromo subPromo : transPromoList) {

            //Restore Promo Row Count In Temp Promo Row Count For Sub Promo Index
            tempPromoRowCount = promoRowCount;

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
            sheet.addCell(new Label(DownloadMulitiplePromoConstant.REQUEST_NO, tempPromoRowCount, requestNo));

            //Column Remarks
            String remarks = subPromo.getRemarks();
            if (remarks != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.REMARKS - 5, tempPromoRowCount, remarks));
            }

            //Column Zone
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
                    String zoneDesc = zone.getMstZone().getZoneName();
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.ZONE, promoRowCount, zoneDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Site , Site Desc
            if (storeList != null && storeList.size() > 0) {
                for (MapPromoStore store : storeList) {
                    String storeCode = store.getMstStore().getMstStoreId();
                    String storeDesc = store.getMstStore().getSiteDescription();
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SIE_CODE, promoRowCount, Double.parseDouble(storeCode)));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.SITE_DESC, promoRowCount, storeDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Format
            if (formatList != null && formatList.size() > 0) {
                for (MapPromoFormat format : formatList) {
                    String formatDesc = format.getFormatDesc();
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.FORMAT, promoRowCount, formatDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Promotion Type
            String promoType = subPromo.getMstPromotionType().getPromoTypeName();
            sheet.addCell(new Label(DownloadMulitiplePromoConstant.PROMO_TYPE, promoRowCount, promoType));

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
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE_SET, promoRowCount, setNo));


                    if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.ARTICLE, promoRowCount, Double.parseDouble(articleCode)));
                    }
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE_DESC, promoRowCount, articleDesc));
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.MC, promoRowCount, Double.parseDouble(mcCode)));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.MC_DESC, promoRowCount, mcDesc));
                    if (article.getQty() != null && article.getQty() != 0) {
                        qty = article.getQty();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                    }

                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;


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
//                double marginAchievement = configVO.getMarginAchivement();
//                double growthTicketSize = configVO.getTicketSizeGrowth();
//                double sellThruQty = configVO.getSellthruvsQty();
//                double growthInConversion = configVO.getGrowthConversion();
//                double salesGrowth = configVO.getSalesGrowth();


                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_FROM, promoRowCount, validFrom));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_TO, promoRowCount, validTo));

                // Column Margin Achivement

//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, promoRowCount, marginAchievement));

                //Column Cashier Trigger
                /*    if (subPromo.getCashierTrigger() != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, promoRowCount, subPromo.getCashierTrigger()));
                } else {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, promoRowCount, "-"));
                }
                 */
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE - 1, promoRowCount, growthTicketSize));
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY - 1, promoRowCount, sellThruQty));
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION - 1, promoRowCount, growthInConversion));
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE - 1, promoRowCount, salesGrowth));

            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

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
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, configVO.getSetId()));
                        }
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        double discountValue = configVO.getDiscountValue();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                        int qty = 0;
                        if (configVO.getQty() != null && configVO.getQty() != 0) {
                            qty = configVO.getQty();
                        }
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                        promoRowCount++;
                    }
                } else {
                    TransPromoConfig configVO = configList.get(0);

                    String discountType = configVO.getDiscountConfig();
                    if (discountType.equalsIgnoreCase("Percentage Off")) {
                        discountType = "%";
                    }
                    double discountValue = configVO.getDiscountValue();

//                    if (promoTypeId != 1 && promoTypeId != 4 && promoTypeId != 6 && promoTypeId != 7) {
                    if (promoTypeId == 3 || promoTypeId == 5) {
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                    }

                    if (promoTypeId == 2) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, maxArticleSetNo));
                    }

                    if (promoTypeId == 3) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, configVO.getSetId()));
                    }

                    if (promoTypeId == 4) {
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        double buyWorthAmt = configVO.getTicketWorthAmt();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QUALIFYING_AMT, promoRowCount, buyWorthAmt));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, 1));

                        if (promoTypeId == 6) {
                            promoRowCount++;

                            sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                            if (configVO.getQty() != null && configVO.getQty() != 0) {
                                int qty = configVO.getQty();
                                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                            }
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, 2));
                        }
//                        else {
//                            double discValue = configVO.getTicketDiscAmt();
//                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discValue));
//                        }
                    }

                    if (promoTypeId == 7) {
                        int buyQty = subPromo.getBuyQty();
                        int getQty = subPromo.getGetQty();

                        if (buyQty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, buyQty));
                        }
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, 1));
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));
                        promoRowCount++;
                        if (getQty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, getQty));
                        }
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Multiple Set , Discount Type , Discount Value  For PromoType Id 1
            //Column Repeating Set value And Multiple  Qty , Discount Value  For PromoType Id 4
            if (configList != null && configList.size() > 0 && (promoTypeId == 1 || promoTypeId == 4)) {
                for (TransPromoConfig config : configList) {


                    double discountValue = config.getDiscountValue();

                    if (config.getSetId() != null) {
                        int set = config.getSetId();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, set));
                    }
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discountValue));

                    if (promoTypeId == 1) {
                        String discountType = config.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.CONFIG_SET, promoRowCount, maxArticleSetNo));

                    } else {
                        int qty = config.getQty();
                        if (qty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.QTY, promoRowCount, qty));
                        }
                    }
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            maxRowCount++;

            //Increment PromoRowCount By 2 For Giving One Space Between Two Promotions
//                if (promoTypeId == 1 || promoTypeId == 4) {
//                    maxRowCount++;
//                } else {
//                    maxRowCount += 2;
//                }

            promoRowCount = maxRowCount;

        }
    }

    private static void fillCommunicationDtl(WritableWorkbook book, List<TransPromo> transPromoList) throws WriteException {
        WritableSheet sheet = book.getSheet(0);

        int maxRowCount = 1;
        int promoRowCount = 1;
        int tempPromoRowCount = 1;
        int maxArticleSetNo = 0;


        for (TransPromo subPromo : transPromoList) {

            //Restore Promo Row Count In Temp Promo Row Count For Sub Promo Index
            tempPromoRowCount = promoRowCount;

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
            sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_REQUEST_NO, tempPromoRowCount, requestNo));

            //Column Remarks
            String remarks = subPromo.getRemarks();
            if (remarks != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_REMARKS, tempPromoRowCount, remarks));
            }

            //column PromoDtl
            String promoDtl = subPromo.getPromoDetails();
            if (promoDtl != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_DTL, tempPromoRowCount, promoDtl));
            }

            //column Cahier Trigger
            String cashierTrigger = subPromo.getCashierTrigger();
            if (cashierTrigger != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_CASHIER_TRIGGER, tempPromoRowCount, cashierTrigger));
            }

            //Column Zone
            if (zoneList != null && zoneList.size() > 0) {
                for (MapPromoZone zone : zoneList) {
                    String zoneDesc = zone.getMstZone().getZoneName();
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_ZONE, promoRowCount, zoneDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Site , Site Desc
            if (storeList != null && storeList.size() > 0) {
                for (MapPromoStore store : storeList) {
                    String storeCode = store.getMstStore().getMstStoreId();
                    String storeDesc = store.getMstStore().getSiteDescription();
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_SIE_CODE, promoRowCount, Double.parseDouble(storeCode)));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_SITE_DESC, promoRowCount, storeDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Format
            if (formatList != null && formatList.size() > 0) {
                for (MapPromoFormat format : formatList) {
                    String formatDesc = format.getFormatDesc();
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_FORMAT, promoRowCount, formatDesc));
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Promotion Type
            String promoType = subPromo.getMstPromotionType().getPromoTypeName();
            sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_TYPE, promoRowCount, promoType));

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
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, promoRowCount, setNo));


                    if (articleCode != null && !articleCode.equalsIgnoreCase("-")) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, promoRowCount, Double.parseDouble(articleCode)));
                    }
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, promoRowCount, articleDesc));
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_MC, promoRowCount, Double.parseDouble(mcCode)));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, promoRowCount, mcDesc));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, promoRowCount, brandCode));
                    sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, promoRowCount, brandDesc));
                    if (article.getQty() != null && article.getQty() != 0) {
                        qty = article.getQty();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, promoRowCount, qty));
                    }

                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;


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
//                double marginAchievement = configVO.getMarginAchivement();
//                double growthTicketSize = configVO.getTicketSizeGrowth();
//                double sellThruQty = configVO.getSellthruvsQty();
//                double growthInConversion = configVO.getGrowthConversion();
//                double salesGrowth = configVO.getSalesGrowth();


                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_FROM, promoRowCount, validFrom));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_TO, promoRowCount, validTo));

                // Column Margin Achivement

//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, promoRowCount, marginAchievement));

                //Column Cashier Trigger
                /*    if (subPromo.getCashierTrigger() != null) {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, promoRowCount, subPromo.getCashierTrigger()));
                } else {
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, promoRowCount, "-"));
                }
                 */
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE + 1, promoRowCount, growthTicketSize));
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY + 1, promoRowCount, sellThruQty));
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION + 1, promoRowCount, growthInConversion));
//                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE + 1, promoRowCount, salesGrowth));

            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

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
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, configVO.getSetId()));
                        }
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, promoRowCount, discountType));
                        double discountValue = configVO.getDiscountValue();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, promoRowCount, discountValue));
                        int qty = 0;
                        if (configVO.getQty() != null && configVO.getQty() != 0) {
                            qty = configVO.getQty();
                        }
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, promoRowCount, qty));
                        promoRowCount++;
                    }
                } else {
                    TransPromoConfig configVO = configList.get(0);

                    String discountType = configVO.getDiscountConfig();
                    if (discountType.equalsIgnoreCase("Percentage Off")) {
                        discountType = "%";
                    }
                    double discountValue = configVO.getDiscountValue();

                    if (promoTypeId != 1 && promoTypeId != 4 && promoTypeId != 6 && promoTypeId != 7) {
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, promoRowCount, discountValue));
                    }

//                if (promoTypeId == 2) {
//                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, maxArticleSetNo));
//                }

                    if (promoTypeId == 3) {
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, configVO.getSetId()));
                    }

                    if (promoTypeId == 4) {
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, promoRowCount, discountType));
                    }

                    if (promoTypeId == 5 || promoTypeId == 6) {
                        double buyWorthAmt = configVO.getTicketWorthAmt();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT, promoRowCount, buyWorthAmt));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, 1));

                        if (promoTypeId == 6) {
                            promoRowCount++;

                            sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, promoRowCount, discountType));
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, promoRowCount, discountValue));
                            if (configVO.getQty() != null && configVO.getQty() != 0) {
                                int qty = configVO.getQty();
                                sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, promoRowCount, qty));
                            }
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, 2));
                        }
//                        else {
//                            double discValue = configVO.getTicketDiscAmt();
//                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, promoRowCount, discValue));
//                        }
                    }

                    if (promoTypeId == 7) {
                        int buyQty = subPromo.getBuyQty();
                        int getQty = subPromo.getGetQty();

                        if (buyQty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, promoRowCount, buyQty));
                        }
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, 1));
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, promoRowCount, discountValue));
                        promoRowCount++;
                        if (getQty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, promoRowCount, getQty));
                        }
                    }
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            //Reset Promocount To Sub PRomo Starting Row
            promoRowCount = tempPromoRowCount;

            //Column Multiple Set , Discount Type , Discount Value  For PromoType Id 1
            //Column Repeating Set value And Multiple  Qty , Discount Value  For PromoType Id 4
            if (configList != null && configList.size() > 0 && (promoTypeId == 1 || promoTypeId == 4)) {
                for (TransPromoConfig config : configList) {


                    double discountValue = config.getDiscountValue();

                    if (config.getSetId() != null) {
                        int set = config.getSetId();
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, set));
                    }
                    sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, promoRowCount, discountValue));

                    if (promoTypeId == 1) {
                        String discountType = config.getDiscountConfig();
                        if (discountType.equalsIgnoreCase("Percentage Off")) {
                            discountType = "%";
                        }
                        sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, promoRowCount, discountType));
                        sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, promoRowCount, maxArticleSetNo));

                    } else {
                        int qty = config.getQty();
                        if (qty != 0) {
                            sheet.addCell(new jxl.write.Number(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, promoRowCount, qty));
                        }
                    }
                    promoRowCount++;
                }
            }

            //get Max Row Count
            maxRowCount = getMaxRowCount(promoRowCount, maxRowCount);

            maxRowCount++;

            //Increment PromoRowCount By 2 For Giving One Space Between Two Promotions
//                if (promoTypeId == 1 || promoTypeId == 4) {
//                    maxRowCount++;
//                } else {
//                    maxRowCount += 2;
//                }

            promoRowCount = maxRowCount;

        }
    }

    private static int getMaxRowCount(int promoRowCount, int maxRowCount) {
        if (maxRowCount < promoRowCount) {
            maxRowCount = promoRowCount;
        }
        return maxRowCount;
    }

    private static WritableWorkbook getBlankWorkbook(String filePath, DownloadEnum downloadOpt) throws IOException, WriteException {
        File errorFile = new File(filePath);

        WritableWorkbook downloadBook = Workbook.createWorkbook(errorFile);
        WritableSheet sheet = downloadBook.createSheet("Promotion Detail", 0);


        //sheet.addCell(new Label(DownloadMulitiplePromoConstant.CASHIER_TRIGGER, 0, "Cashier Trigger"));
        switch (downloadOpt) {
            case INITIATOR_TO_EXIGENCY_DOWNLOAD:

                sheet.addCell(new Label(DownloadMulitiplePromoConstant.REQUEST_NO, 0, "Request No"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ZONE, 0, "Zone"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SIE_CODE, 0, "Site Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SITE_DESC, 0, "Site Description"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.FORMAT, 0, "Format"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.PROMO_TYPE, 0, "Promo Type"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, 0, "Discount Type"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, 0, "Discount Value"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.QTY, 0, "Qty"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CONFIG_SET, 0, "No Of Set"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.QUALIFYING_AMT, 0, "Qualifying Amt/Buy Worth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE_SET, 0, "Sales Set No"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE, 0, "Article"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE_DESC, 0, "Article Desc"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.MC, 0, "MC Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.MC_DESC, 0, "MC Desc"));

                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_FROM, 0, "Valid Date From"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_TO, 0, "Valid Date To"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, 0, "Expected Margin IN"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE, 0, "Growth IN Ticket Size"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY, 0, "Expected Sales Qty growth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION, 0, "Growth In Conversions"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE, 0, "Expected Sales value growth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.REMARKS, 0, "Remarks"));

                sheet.setColumnView(DownloadMulitiplePromoConstant.VALID_FROM, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.VALID_TO, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.MARGIN_ACHIVEMENT, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.REMARKS, 20);

                sheet.setColumnView(DownloadMulitiplePromoConstant.REQUEST_NO, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ZONE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.SIE_CODE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.SITE_DESC, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.FORMAT, 10);
                sheet.setColumnView(DownloadMulitiplePromoConstant.PROMO_TYPE, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.QTY, 5);
                sheet.setColumnView(DownloadMulitiplePromoConstant.CONFIG_SET, 5);
                sheet.setColumnView(DownloadMulitiplePromoConstant.QUALIFYING_AMT, 10);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ARTICLE_SET, 7);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ARTICLE, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ARTICLE_DESC, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.MC, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.MC_DESC, 35);
                break;
            case MANAGER_EXECUTOR_DOWNLOAD:

                sheet.addCell(new Label(DownloadMulitiplePromoConstant.REQUEST_NO, 0, "Request No"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ZONE, 0, "Zone"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SIE_CODE, 0, "Site Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SITE_DESC, 0, "Site Description"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.FORMAT, 0, "Format"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.PROMO_TYPE, 0, "Promo Type"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, 0, "Discount Type"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, 0, "Discount Value"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.QTY, 0, "Qty"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.CONFIG_SET, 0, "No Of Set"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.QUALIFYING_AMT, 0, "Qualifying Amt/Buy Worth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE_SET, 0, "Sales Set No"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE, 0, "Article"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.ARTICLE_DESC, 0, "Article Desc"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.MC, 0, "MC Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.MC_DESC, 0, "MC Desc"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_FROM, 0, "Valid Date From"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.VALID_TO, 0, "Valid Date To"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE - 1, 0, "Growth IN Ticket Size"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY - 1, 0, "Expected Sales Qty growth"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION - 1, 0, "Growth In Conversions"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE - 1, 0, "Expected Sales value growth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.REMARKS - 5, 0, "Remarks"));

                sheet.setColumnView(DownloadMulitiplePromoConstant.VALID_FROM, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.VALID_TO, 15);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE - 1, 20);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY - 1, 20);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION - 1, 20);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE - 1, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.REMARKS - 5, 20);

                sheet.setColumnView(DownloadMulitiplePromoConstant.REQUEST_NO, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ZONE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.SIE_CODE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.SITE_DESC, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.FORMAT, 10);
                sheet.setColumnView(DownloadMulitiplePromoConstant.PROMO_TYPE, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.DISCOUNT_TYPE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.DISCOUNT_VALUE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.QTY, 5);
                sheet.setColumnView(DownloadMulitiplePromoConstant.CONFIG_SET, 5);
                sheet.setColumnView(DownloadMulitiplePromoConstant.QUALIFYING_AMT, 10);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ARTICLE_SET, 7);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ARTICLE, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.ARTICLE_DESC, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.MC, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.MC_DESC, 35);
                break;
            case COMMUNICATION_DOWNLOAD:
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_REQUEST_NO, 0, "Request No"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_ZONE, 0, "Zone"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_SIE_CODE, 0, "Site Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_SITE_DESC, 0, "Site Description"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_FORMAT, 0, "Format"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_TYPE, 0, "Promo Type"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_DTL, 0, "Promo Detail"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, 0, "Discount Type"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, 0, "Discount Value"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, 0, "Qty"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, 0, "No Of Set"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT, 0, "Qualifying Amt/Buy Worth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, 0, "Sales Set No"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, 0, "Article"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, 0, "Article Desc"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_MC, 0, "MC Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, 0, "MC Desc"));


                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, 0, "Brand Code"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, 0, "Brand Desc"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_FROM, 0, "Valid Date From"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_TO, 0, "Valid Date To"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE + 1, 0, "Growth IN Ticket Size"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY + 1, 0, "Expected Sales Qty growth"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION + 1, 0, "Growth In Conversions"));
//                sheet.addCell(new Label(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE + 1, 0, "Expected Sales value growth"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_REMARKS, 0, "Remarks"));
                sheet.addCell(new Label(DownloadMulitiplePromoConstant.COMMUNICATION_CASHIER_TRIGGER, 0, "Cashier Trigger"));



                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_REQUEST_NO, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_ZONE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_SIE_CODE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_SITE_DESC, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_FORMAT, 10);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_TYPE, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_TYPE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_DISCOUNT_VALUE, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_QTY, 5);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_CONFIG_SET, 5);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_QUALIFYING_AMT, 10);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_SET, 7);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_ARTICLE_DESC, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_MC, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_MC_DESC, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_BRAND_DESC, 35);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_FROM, 15);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_VALID_TO, 15);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.GROWTH_IN_TICKET_SIZE + 1, 20);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.SELL_THRU_VS_QTY + 1, 20);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.GROWTH_IN_CONVERSION + 1, 20);
//                sheet.setColumnView(DownloadMulitiplePromoConstant.SALES_GROWTH_IN_QTY_VALUE + 1, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_REMARKS, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_CASHIER_TRIGGER, 20);
                sheet.setColumnView(DownloadMulitiplePromoConstant.COMMUNICATION_PROMO_DTL, 20);
                break;
        }







        return downloadBook;
    }

    public static String createAndGetDownloadSubPromoFile(TransPromo subPromo) {
        try {
            String LINE_SEPARATOR = System.getProperty("line.separator");
            String COMMA_SEPARATOR = ",";

            String filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.SUB_PROMO_REQUEST_FILE);
            String fileName = "Request_" + subPromo.getTransPromoId() + ".csv";
            String downloadFilePath = filePath + fileName;

            BufferedWriter writer = new BufferedWriter(new FileWriter(downloadFilePath));
            File statusFile = new File(downloadFilePath);
            statusFile.createNewFile();
            StringBuffer sb = new StringBuffer();
            sb.append("PromotionType").append(COMMA_SEPARATOR);
            sb.append("Remarks").append(COMMA_SEPARATOR);
            sb.append("Set").append(COMMA_SEPARATOR);
            sb.append("Articles").append(COMMA_SEPARATOR);
            sb.append("MC").append(COMMA_SEPARATOR);
            sb.append("ArticleQty").append(COMMA_SEPARATOR);
            sb.append("ValidFrom").append(COMMA_SEPARATOR);
            sb.append("ValidTo").append(COMMA_SEPARATOR);
            sb.append("Margin Achievement").append(COMMA_SEPARATOR);
            sb.append("Growth in Ticket size").append(COMMA_SEPARATOR);
            sb.append("Sell thru  vs quantity").append(COMMA_SEPARATOR);
            sb.append("Growth in conversions").append(COMMA_SEPARATOR);
            sb.append("Sales Growth for QTY & value").append(COMMA_SEPARATOR);
            sb.append("DiscountConfiCode").append(COMMA_SEPARATOR);
            sb.append("DiscountConfigValue").append(COMMA_SEPARATOR);
            sb.append("Qty").append(COMMA_SEPARATOR);
            sb.append("Buy Worth Amount").append(COMMA_SEPARATOR);
            sb.append("Buy").append(COMMA_SEPARATOR);
            sb.append("Get").append(COMMA_SEPARATOR);
            sb.append(LINE_SEPARATOR);
            if (subPromo.getMstPromotionType() != null) {
                sb.append(subPromo.getMstPromotionType().getPromoTypeName()).append(COMMA_SEPARATOR);
            } else {
                sb.append("").append(COMMA_SEPARATOR);
            }
            if (subPromo.getRemarks() != null) {
                sb.append(subPromo.getRemarks()).append(COMMA_SEPARATOR);
            } else {
                sb.append("").append(COMMA_SEPARATOR);
            }
            sb.append(LINE_SEPARATOR);
            Collection<TransPromoArticle> listArticle = subPromo.getTransPromoArticleCollection();
            for (TransPromoArticle vo : listArticle) {
                sb.append("").append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR);
                if (subPromo.getMstPromotionType() != null) {
                    if (subPromo.getMstPromotionType().getPromoTypeId().toString().equals("1")
                            || subPromo.getMstPromotionType().getPromoTypeId().toString().equals("2")
                            || subPromo.getMstPromotionType().getPromoTypeId().toString().equals("6")) {
                        if (vo.getSetId() != null) {
                            sb.append(vo.getSetId()).append(COMMA_SEPARATOR);
                        } else {
                            sb.append("").append(COMMA_SEPARATOR);
                        }
                        if (vo.getArticle() != null && !vo.getArticle().equalsIgnoreCase("-")) {
                            sb.append(vo.getArticle()).append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR);
                        } else {
                            sb.append("").append(COMMA_SEPARATOR).append(vo.getMcCode()).append(COMMA_SEPARATOR);
                        }
                        if (vo.getQty() != null) {
                            sb.append(vo.getQty()).append(COMMA_SEPARATOR);
                        } else {
                            sb.append("").append(COMMA_SEPARATOR);
                        }
                    } else {
                        sb.append("").append(COMMA_SEPARATOR);
                        if (vo.getArticle() != null && !vo.getArticle().equalsIgnoreCase("-")) {
                            sb.append(vo.getArticle()).append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR);
                        } else {
                            sb.append("").append(COMMA_SEPARATOR).append(vo.getMcCode()).append(COMMA_SEPARATOR);
                        }
                        if (vo.getQty() != null) {
                            sb.append(vo.getQty()).append(COMMA_SEPARATOR);
                        } else {
                            sb.append("").append(COMMA_SEPARATOR);
                        }
                    }
                }
                sb.append(LINE_SEPARATOR);
            }
            sb.append(LINE_SEPARATOR);
            Collection<TransPromoConfig> configList = subPromo.getTransPromoConfigCollection();
            for (TransPromoConfig vo : configList) {
                sb.append("").append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR).append("").append(COMMA_SEPARATOR);
                if (subPromo.getValidFrom() != null) {
                    sb.append(CommonUtil.dispayFileDateFormat(subPromo.getValidFrom())).append(COMMA_SEPARATOR);
                } else {
                    sb.append("").append(COMMA_SEPARATOR);
                }
                if (subPromo.getValidTo() != null) {
                    sb.append(CommonUtil.dispayFileDateFormat(subPromo.getValidTo())).append(COMMA_SEPARATOR);

                }
                sb.append(vo.getMarginAchivement()).append(COMMA_SEPARATOR);
                sb.append(vo.getTicketSizeGrowth()).append(COMMA_SEPARATOR);
                sb.append(vo.getSellthruvsQty()).append(COMMA_SEPARATOR);
                sb.append(vo.getGrowthConversion()).append(COMMA_SEPARATOR);
                sb.append(vo.getSalesGrowth()).append(COMMA_SEPARATOR);
                sb.append(vo.getDiscountConfig()).append(COMMA_SEPARATOR);
                sb.append(vo.getDiscountValue()).append(COMMA_SEPARATOR);
                sb.append(vo.getQty()).append(COMMA_SEPARATOR);
                sb.append(vo.getTicketWorthAmt()).append(COMMA_SEPARATOR);
                if (subPromo.getBuyQty() != null) {
                    sb.append(subPromo.getBuyQty()).append(COMMA_SEPARATOR);
                } else {
                    sb.append("").append(COMMA_SEPARATOR);
                }
                if (subPromo.getGetQty() != null) {
                    sb.append(subPromo.getGetQty()).append(COMMA_SEPARATOR);
                } else {
                    sb.append("").append(COMMA_SEPARATOR);
                }
                sb.append(LINE_SEPARATOR);
            }

            writer.write(sb.toString());


            writer.flush();
            if (writer != null) {
                writer.close();
            }
            System.out.println("Download File Path = " + downloadFilePath);
            logger.info("====== File Writing is Completed ===== ");
            return downloadFilePath;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ("Exception : " + ex.getMessage());

        }

    }
}
