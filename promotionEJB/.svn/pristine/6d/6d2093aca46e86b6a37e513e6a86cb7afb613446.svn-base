/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ods.service;

import com.eks.ods.article.ArticleSearch;
import com.eks.ods.article.vo.ArticleVO;
import com.eks.ods.article.vo.RespCode;
import com.eks.ods.article.vo.SearchArticleResp;
import com.fks.ods.vo.ArticleSearchReq;
import com.fks.ods.vo.ArticleSearchResp;
import com.fks.ods.vo.ArticleUtil;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.entity.MapPromoMch;
import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promotion.service.ArticleValidateService;
import com.fks.promotion.vo.ArticleMCVO;
import com.fks.promotion.vo.ValidateArticleMCResp;
import com.fks.promotion.vo.ValidateArticleMCVO;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
@Stateless
@LocalBean
@WebService
public class ODSService {

    private static final Logger logger = Logger.getLogger(ODSService.class.getName());
    @EJB
    ArticleValidateService odsArticleService;
    @EJB
    MstPromoFacade mstPromoFacade;
    @EJB
    MchFacade mchFacade;
    @EJB
    ArticleSearch odsArticleSearch;

    public ValidateArticleMCVO searchODSArticle(String articleCode, Long mstPromoId, String IsInitiationFlag) {
        try {
            logger.info("--------- Search ODS Aricle Service ------ " + articleCode);
            logger.info("------ Is Intiation Flag : " + IsInitiationFlag + " mst PromoId : " + mstPromoId);
            MstPromo promo = null;
            if (IsInitiationFlag.equalsIgnoreCase("1")) {
                promo = mstPromoFacade.find(mstPromoId);
            }
            SearchArticleResp resp = odsArticleSearch.searchArticle(articleCode);
            logger.info("------- ODS Service Resp : " + resp.getCode().getCode());
            if (resp.getCode().getCode() == RespCode.SUCCESS) {
                if (resp.getVo() != null && resp.getVo().size() > 0) {
                    ArticleVO article = null;
                    if (IsInitiationFlag.equalsIgnoreCase("1")) {

                        Collection<MapPromoMch> mchList = promo.getMapPromoMchCollection();
                        boolean isMCHFound = false;
                        boolean isMCBlocked = false;
                        for (MapPromoMch mch : mchList) {
                            for (ArticleVO odsVO : resp.getVo()) {
                                if (mch.getMch().getMcCode().equalsIgnoreCase(odsVO.getMcCode())) {
                                    isMCHFound = true;
                                    article = odsVO;
                                    if (mch.getMch().getIsBlocked()) {
                                        isMCBlocked = true;
                                    }
                                    break;
                                }
                            }
                        }
                        if (isMCHFound == true) {
                            if (isMCBlocked) {
                                return (new ValidateArticleMCVO(true, "MC Is Blocked For Entered Article Code : " + articleCode + "."));
                            }
                            return (new ValidateArticleMCVO(false, resp.getCode().getMsg(), article.getArticleCode(), CommonUtil.getStringByReplaceCommaWithSpace(article.getArticleDesc()), article.getMcCode(), CommonUtil.getStringByReplaceCommaWithSpace(article.getMcDesc()), CommonUtil.getStringByReplaceCommaWithSpace(article.getBrand()), CommonUtil.getStringByReplaceCommaWithSpace(article.getBrandDesc())));
                        } else {
//                            return (new ValidateArticleMCVO(true, "Article Code : " + articleCode + " Does Not Belong To Category Name / Sub Category Name : " + promo.getCategory() + " / " + promo.getSubCategory() + "."));
                            return (new ValidateArticleMCVO(true, "Article Code : " + articleCode + " Does Not Belong To selected category/sub category of the request."));
                        }
                    }
                    article = resp.getVo().get(0);
                    // validate article MCs are blocked or not for proposal
                    Mch proposalMC = mchFacade.find(article.getMcCode());
                    if (proposalMC.getIsBlocked()) {
                        return (new ValidateArticleMCVO(true, "MC Is Blocked For Entered Article Code : " + articleCode + "."));
                    }
                    return (new ValidateArticleMCVO(false, resp.getCode().getMsg(), article.getArticleCode(), CommonUtil.getStringByReplaceCommaWithSpace(article.getArticleDesc()), article.getMcCode(), CommonUtil.getStringByReplaceCommaWithSpace(article.getMcDesc()), CommonUtil.getStringByReplaceCommaWithSpace(article.getBrand()), CommonUtil.getStringByReplaceCommaWithSpace(article.getBrandDesc())));

                } else {
                    return (new ValidateArticleMCVO(true, "No Article Found."));
                }

            } else {
                return (new ValidateArticleMCVO(true, resp.getCode().getMsg()));
            }
        } catch (Exception ex) {

            ex.printStackTrace();
            return (new ValidateArticleMCVO(true, ex.getMessage()));
        }

    }

    public ValidateArticleMCResp sendArticleMCFileForvalidate(String filePath, String empID) {
        try {
            logger.info("---------- Inside Send Article File For Validate Service------");
            logger.info("----- Emp ID : " + empID + "----FilePath : " + filePath);
            ValidateArticleMCResp validateResp = odsArticleService.validateInitiationODSArticleListFromFile(filePath, empID, null, null);
            logger.info("-------- validate Article Resp Code : " + validateResp.getResp().getRespCode());
            return (validateResp);
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Error :" + ex.getMessage()));
        }
    }

    public ArticleSearchResp searchArticledtl(ArticleSearchReq req) {
        logger.info("======= Inside Search Article ");
        try {
            StringWriter buffer = new StringWriter();
            JAXB.marshal(req, buffer);
            logger.info("----- payload : \n" + buffer);
            List<ArticleMCVO> articleList = new ArrayList<ArticleMCVO>();
            SearchArticleResp resp = null;

            int maxRecordLength = 1000;
            int pageCount = req.getPageCount();
            int nextPageCount = pageCount + 1;
            long startRange = 1;
            long endRange = 1000;
            int downloadRange = 1000;

            if (!req.isIsDownload()) {
                downloadRange = 15;
                endRange = 15;
                maxRecordLength = 15;
            }
            if (pageCount > 1) {
                startRange = (pageCount * downloadRange) - maxRecordLength;
                startRange++;
            }

            if (pageCount > 1) {
                endRange = (nextPageCount * downloadRange) - maxRecordLength;
            }

            switch (req.getArticleSearchEnum()) {
//                case ARTICLE:
//                    resp = odsArticleSearch.searchArticleList(req.getArticleList());
//                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
//                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
//                    }
//                    break;
                case MC:
                    resp = odsArticleSearch.searchArticleListMCWise(req.getMCList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
                case BRAND:
                    resp = odsArticleSearch.searchArticleListBrandWise(req.getBrandList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
                case SEASON_CODE:
                    resp = odsArticleSearch.searchArticleListSeasonCodeWise(req.getSeasonCodeList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
                case MC_BRAND:
                    resp = odsArticleSearch.searchArticleListMCBrandWise(req.getMCList(), req.getBrandList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
                case MC_SEASON_CODE:
                    resp = odsArticleSearch.searchArticleListMCSeasonWise(req.getMCList(), req.getSeasonCodeList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
                case BRAND_SEASON_CODE:
                    resp = odsArticleSearch.searchArticleListBrandSeasonWise(req.getBrandList(), req.getSeasonCodeList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
                case MC_BRAND_SEASON_CODE:
                    resp = odsArticleSearch.searchArticleListMCBrandSeasonWise(req.getMCList(), req.getBrandList(), req.getSeasonCodeList(), req.isIsDownload(), startRange, endRange);
                    if (resp.getCode().getCode() == RespCode.SUCCESS) {
                        articleList = ArticleUtil.getArticleSearchUtil(resp.getVo());
                    }
                    break;
            }
            logger.info("======= Size : " + articleList.size());

            long totalCount = 0;
//            if (articleList != null && articleList.size() > 0) {
            //totalCount = odsArticleSearch.searchTotalCount(req.getArticleSearchEnum(), req.getMCList(), req.getBrandList(), req.getSeasonCodeList());
//            }
//            if (totalCount < downloadRange) {
//                endRange = totalCount;
//            }            

            if (req.isIsDownload()) {
                //String msg = "Download " + startRange + "-" + endRange + " Records OF " + totalCount;
                String msg = "Download " + startRange + "-" + endRange;
                return new ArticleSearchResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, msg), resp.getFilePath());
            }
            return new ArticleSearchResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "success"), articleList, totalCount);
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new ArticleSearchResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Error :" + ex.getMessage()));
        }
    }
}
