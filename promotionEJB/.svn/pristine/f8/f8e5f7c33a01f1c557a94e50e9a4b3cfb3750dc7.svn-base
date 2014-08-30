/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.comm.service;

import com.fks.promo.comm.vo.CommSearchReq;
import com.fks.promo.comm.vo.CommSearchResp;
import com.fks.promo.comm.vo.CommVO;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommunicationDao;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.init.vo.DownloadSubPromoResp;
import com.fks.promo.master.util.VOUtilOtherMaster;
import com.fks.promo.master.vo.MstZoneVO;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
@WebService
public class CommService {

    private static final Logger logger = Logger.getLogger(CommService.class.getName());
    @EJB
    private CommunicationDao communicationDao;
    @EJB
    private CommonDAO commonDAO;
    @EJB
    private TransPromoFacade transPromoDao;

    public CommSearchResp searchComm(CommSearchReq request) {
        logger.info(" === Searching Promotion ===");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        //logger.info("------ Payload------ \n" + buffer);
        System.out.println("------ Payload------ \n" + buffer);
        Long count = new Long("0");
        List<TransPromo> list = new ArrayList<TransPromo>();

        try {
            if (request.getEndDate() != null && request.getStartDate().equalsIgnoreCase(request.getEndDate())) {
//                request.setStartDate(CommonUtil.addSubtractDaysInDate(request.getStartDate(), -1));
                request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
            }
//            if (request.getEndDate() != null) {
//                request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
//            }
            logger.info("-------- start Date : " + request.getStartDate() + " ---- end Date : " + request.getEndDate());

        } catch (ParseException ex) {
            logger.error("--------------Start/End Date Parse Exception In  CommService searchComm.");
            java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (request.getType()) {
            case VALIDITY_DATE:
                list = communicationDao.searchTransPromoByValidityDates(request.getStartDate(), request.getEndDate(), request.getStartIndex());
                count = communicationDao.searchTransPromoByValidityDatesCount(request.getStartDate(), request.getEndDate());
                break;
            case EVENT_TYPE:
                list = communicationDao.searchTransPromoByValidityDatesEvent(request.getStartDate(), request.getEndDate(), request.getStartIndex(), request.getEventTypeId());
                count = communicationDao.searchTransPromoByValidityDatesEventCount(request.getStartDate(), request.getEndDate(), request.getEventTypeId());
                break;
            case CATEGORY:
                list = communicationDao.searchTransPromoByValidityDatesEventCategory(request.getStartDate(), request.getEndDate(), request.getStartIndex(), request.getEventTypeId(), request.getCategoryName());
                count = communicationDao.searchTransPromoByValidityDatesEventCategoryCount(request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName());
                break;
            case MC_TYPE:
                list = communicationDao.searchTransPromoByValidityDatesEventCategoryMC(request.getStartDate(), request.getEndDate(), request.getStartIndex(), request.getEventTypeId(), request.getCategoryName(), request.getMcList());
                count = communicationDao.searchTransPromoByValidityDatesEventCategoryMCCount(request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName(), request.getMcList());
                break;
            case ARTICLE_TYPE:
                list = communicationDao.searchTransPromoByValidityDatesEventCategoryMCArticle(request.getStartDate(), request.getEndDate(), request.getStartIndex(), request.getEventTypeId(), request.getCategoryName(), request.getCategoryName(), request.getArticleList());
                count = communicationDao.searchTransPromoByValidityDatesEventCategoryMCCountArticle(request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName(), request.getCategoryName(), request.getArticleList());
                break;
            case ZONE_TYPE:
                list = communicationDao.searchTransPromoByZone(request.getZoneId(), request.getStartIndex());
                count = communicationDao.searchTransPromoByZonecount(request.getZoneId());
                break;
            //following are new search types
            case ZONE_DATE:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate());
                }
                break;
            case ZONE_DATE_CATEGORY:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getCategoryName(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getCategoryName());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getCategoryName(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getCategoryName());
                }
                break;
            case ZONE_DATE_CATEGORY_MC:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList());
                }
                break;
            case ZONE_DATE_CATEGORY_MC_ARTICLE:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList());
                }
                break;
            case ZONE_DATE_EVENT:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId());

                }
                break;
            case ZONE_DATE_EVENT_CATEGORY:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getCategoryName());
                }
                break;
            case ZONE_DATE_EVENT_CATEGORY_MC:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList());
                }
                break;
            case ZONE_DATE_EVENT_CATEGORY_MC_ARTICLE:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList(), request.getArticleList(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList(), request.getArticleList());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList(), request.getArticleList(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getEventTypeId(), request.getMcList(), request.getArticleList());
                }
                break;
            case ZONE_DATE_MC:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList());
                }
                break;
            case ZONE_DATE_MC_ARTICLE:
                if (request.isIsStore()) {
                    list = communicationDao.searchStoreCommunicationPromo(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList(), request.getStartIndex());
                    count = communicationDao.searchStoreCommunicationPromoCount(request.getStoreId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList());
                } else {
                    list = communicationDao.searchCommunicationPromo(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList(), request.getStartIndex());
                    count = communicationDao.searchCommunicationPromoCount(request.getZoneId(), request.getStartDate(), request.getEndDate(), request.getMcList(), request.getArticleList());
                }
                break;
        }
        if (list != null) {
            logger.info("-------- " + list.size());
        }
        List<CommVO> volist = CommUtil.getCommVOList(list);
        CommSearchResp response = new CommSearchResp(new Resp(RespCode.SUCCESS, "success"));
        response.setTotalCount(count);
        response.setList(volist);
        return response;
    }

    public List<String> getAllCategoryFromMstPromo() {
        List<String> listcategory = commonDAO.getAllCategoryName();
        return listcategory;
    }

    public List<String> getAllMchCategFromPromo(String categoryName) {
        List<String> listMch = communicationDao.getAllMchFromArticle(categoryName);
        return listMch;
    }

    public List<String> getAllSubCategegoryMch(String subCategoryName) {
        return communicationDao.getAllMchBySubCategory(subCategoryName);
    }

    public List<String> getAllArticleMchWise(String mcCode) {
        List<String> listArticle = communicationDao.getAllArticleMcWise(mcCode);
        return listArticle;
    }

    public List<MstZoneVO> getAllZoneUserWise(String locationId, String zoneId) {
        logger.info("--- Getting all zone data  User Wise for Promo Comm--- ");
        List<MstZone> zones = communicationDao.getZoneBasedOnLocationAndZone(locationId, zoneId);
        return VOUtilOtherMaster.convertZone(zones);
    }

    public DownloadSubPromoResp downloadCommArticleMcPromoRequest(Long TransPromoId) {
        try {
            TransPromo subPromo = transPromoDao.find(TransPromoId);
            String filePath = CommUtil.promoCommunicationFileDownload(subPromo);
            return (new DownloadSubPromoResp(new Resp(RespCode.SUCCESS, "Success"), filePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new DownloadSubPromoResp(new Resp(RespCode.FAILURE, "Error While downlaoding Request"));
        }
    }
}
