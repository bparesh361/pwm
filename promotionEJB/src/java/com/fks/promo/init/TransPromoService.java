/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init;

import com.fks.promo.comm.service.CommService;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonApprovalDao;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MapPromoMch;
import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.promo.entity.TransPromoFile;
import com.fks.promo.entity.TransPromoMc;
import com.fks.promo.entity.TransPromoStatus;
import com.fks.promo.facade.MapPromoMchFacade;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promo.facade.MstPromotionTypeFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.MstZoneFacade;
import com.fks.promo.facade.TransPromoArticleFacade;
import com.fks.promo.facade.TransPromoConfigFacade;
import com.fks.promo.facade.TransPromoFacade;
import com.fks.promo.facade.TransPromoFileFacade;
import com.fks.promo.facade.TransPromoMcFacade;
import com.fks.promo.facade.TransPromoStatusFacade;
import com.fks.promo.init.vo.CreateTransPromoReq;
import com.fks.promo.vo.ExecuteDashboardVO;
import com.fks.promo.init.vo.ExecutePromoDashBaordReq;
import com.fks.promo.init.vo.ExecutePromoDashBoardResp;
import com.fks.promo.init.vo.ApprovalLevelDashboardSearchReq;
import com.fks.promo.init.vo.CreateMultiPlePromoReq;
import com.fks.promo.init.vo.CreateTransPromoWithFileReq;
import com.fks.promo.init.vo.DownloadEnum;

import com.fks.promo.init.vo.DownloadSubPromoResp;
import com.fks.promo.init.vo.SearchPromoTransReq;
import com.fks.promo.init.vo.SearchPromotionSearchResp;
import com.fks.promo.init.vo.TransPromoArticleVO;
import com.fks.promo.init.vo.TransPromoConfigVO;
import com.fks.promo.init.vo.TransPromoVO;
import com.fks.promo.init.vo.UpdateTransPromoWithFileReq;
import com.fks.promo.service.CommonPromoMailService;
import com.fks.promo.service.NotificationMessage;
import com.fks.promo.service.NotificationType;
import com.fks.promo.vo.ResultCount;
import com.fks.promotion.service.util.StatusUpdateUtil;
import com.fks.promotion.vo.PromotionVO;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class TransPromoService {

    private static final Logger logger = Logger.getLogger(TransPromoService.class.getName());
    @EJB
    private MstPromoFacade promoDao;
    @EJB
    private MstPromotionTypeFacade promoTypeDao;
    @EJB
    private TransPromoFacade transPromoDao;
    @EJB
    private TransPromoArticleFacade transPromoArticleDao;
    @EJB
    private TransPromoConfigFacade transPromoConfigDao;
    @EJB
    private CommonPromotionDao commonPromotionDao;
    @EJB
    private CommonApprovalDao CommonApprovalDao;
    @EJB
    private MstStatusFacade statusDao;
    @EJB
    private MstZoneFacade zoneDao;
    @EJB
    private MstEmployeeFacade empDao;
    @EJB
    private MchFacade mchDao;
    @EJB
    private MstEmployeeFacade mstEmployeeFacade;
    @EJB
    private CommonDAO commonDao;
    @EJB
    private MapPromoMchFacade mapPromoMchDao;
    @EJB
    private TransPromoFileFacade transPromoFileDao;
    @EJB
    private CommonPromoMailService mailService;
    @EJB
    TransPromoMcFacade transPromoMCFacade;
    @EJB
    DownloadUtil downloadUtil;
    @EJB
    TransPromoStatusFacade transPromoStatusFacade;

    public SearchPromotionSearchResp searchPromotion(SearchPromoTransReq request) {

        logger.info(" --- Searching Trans Promotion Information ---  ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info("------ Payload------ \n" + buffer);
        MstPromo promo = promoDao.find(request.getMstPromoId());
        if (promo == null) {
            return new SearchPromotionSearchResp(new Resp(RespCode.FAILURE, "Promotion Not Found with Id " + request.getMstPromoId()));
        }
        List<TransPromoVO> listTransPromo = TransPromoUtil.covertTransPromoFromMstPromo(promo);
        SearchPromotionSearchResp response = new SearchPromotionSearchResp(new Resp(RespCode.SUCCESS, "success"));
        response.setTransPromoList(listTransPromo);
        return response;

    }

    public SearchPromotionSearchResp searchPromotionByTransReqId(Long transReqId) {

        logger.info(" --- Searching Trans Promotion Information --- Trans Promoiton Id " + transReqId);
        TransPromo promo = transPromoDao.find(transReqId);
        if (promo == null) {
            return new SearchPromotionSearchResp(new Resp(RespCode.FAILURE, "Promotion Not Found with Id " + transReqId));
        }
        TransPromoVO vo = TransPromoUtil.covertTransPromoFromTransPromo(promo);
        SearchPromotionSearchResp response = new SearchPromotionSearchResp(new Resp(RespCode.SUCCESS, "success"));
        List<TransPromoVO> transPromo = new ArrayList<TransPromoVO>();
        transPromo.add(vo);
        response.setTransPromoList(transPromo);
        return response;

    }

    public Resp downloadSubPromoDtl(List<Long> transReqIdList, Long empId, DownloadEnum downloadOpt) {
        try {
            logger.info(" --- Downloading Trans Promotion Information --- ");
            List<TransPromo> transPromoList = new ArrayList<TransPromo>();
            Long tempReqId = 0l;
            if (transReqIdList != null && transReqIdList.size() > 0) {
                for (Long subPromoId : transReqIdList) {
                    TransPromo promo = transPromoDao.find(subPromoId);
                    if (promo == null) {
                        tempReqId = subPromoId;
                        break;
                    }
                    transPromoList.add(promo);
                }
            }
            if (tempReqId != 0) {
                return new Resp(RespCode.FAILURE, "Request No : R" + tempReqId + " Not Found.");
            }
            if (transPromoList.isEmpty()) {
                return new Resp(RespCode.FAILURE, "No Promotion Detail Found.");
            }

//        String downloadPath = TransPromoUtil.downloadMultipleSubPromoFile(transPromoList, empId, downloadOpt);
            String downloadPath = downloadUtil.downloadMultipleSubPromoExcel(transPromoList, empId, downloadOpt, logger);
            if (downloadPath == null) {
                return new Resp(RespCode.FAILURE, "No Promotion Detail Found.");
            }
            return new Resp(RespCode.SUCCESS, downloadPath);
        } catch (Exception ex) {
            System.out.println("------- Exception : " + ex.getMessage());
            ex.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + ex.getMessage());
        }

    }

    //Level one
    public SearchPromotionSearchResp getallTransPromoforApproval(ApprovalLevelDashboardSearchReq request) {
        logger.info(" --- Searching All Trans Promotion Information ---");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info("------ Payload------ \n" + buffer);
        List<TransPromo> trans = new ArrayList<TransPromo>();
        Long totalcount = 0l;
        String searchCrieteia = request.getType().toString();

        try {
//            if (request.getStartDate() != null) {
//                request.setStartDate(CommonUtil.addSubtractDaysInDate(request.getStartDate(), -1));
//            }
            if (request.getEndDate() != null) {
                request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
            }
            logger.info("------- start Date : " + request.getStartDate());
            logger.info("------- end Date : " + request.getEndDate());

        } catch (ParseException ex) {
            logger.info("--------------Start/End Date Parse Exception In  getallTransPromoforApproval getallTransPromoforApproval.");
            java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (request.getType()) {
            case ALL:
                if (!request.isIsL1ByPassForL2()) {
                    if (request.getStorecode().equalsIgnoreCase("901")) {
                        logger.info("Searching L1 Trans Promotion for HO User, All records will be displayed. ");
                        trans = commonPromotionDao.getAllTransPromotion(request.getEmpId(), request.getPage());
                        totalcount = commonPromotionDao.getAllTransPromotionCount(request.getEmpId());
                    } else {
                        logger.info("Searching L1 Trans Promotion for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                        ResultCount count = CommonApprovalDao.getAllTransPromotion(request.getZoneId(), request.getEmpId(), request.getPage());
                        trans = count.getLstTransPromo();
                        totalcount = count.getCount();
                    }
                } else {
                    if (request.getStorecode().equalsIgnoreCase("901")) {
                        logger.info("Searching L1 By Pass Trans Promotion for HO User, All records will be displayed. ");
                        trans = commonPromotionDao.getAllL1ByPassTransPromotion(request.getEmpId(), request.getL2EmpId(), request.getPage());
                        totalcount = commonPromotionDao.getAllL1ByPassTransPromotionCount(request.getEmpId(), request.getL2EmpId());
                    } else {
                        logger.info("Searching L1 By Pass Trans Promotion for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                        trans = commonPromotionDao.getAllZoneL1ByPassTransPromotion(request.getEmpId(), request.getL2EmpId(), request.getZoneId(), request.getPage());
                        totalcount = commonPromotionDao.getAllZoneL1ByPassTransPromotionCount(request.getEmpId(), request.getL2EmpId(), request.getZoneId());
                    }
                }
                break;
            case CATEGORY:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User, ");
                    trans = commonPromotionDao.getAllTransPromotionForCategory(request.getEmpId(), request.getCategoryName(), request.getPage());
                    totalcount = commonPromotionDao.getAllTransPromotionForCategoryCount(request.getEmpId(), request.getCategoryName());
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getZoneAllTransPromotionForCategory(request.getEmpId(), request.getCategoryName(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getZoneAllTransPromotionForCategoryCount(request.getEmpId(), request.getCategoryName(), request.getZoneId());
                }
                break;
            case DATE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getAllTransPromotionForDate(request.getEmpId(), request.getStartDate(), request.getEndDate(), request.getPage());
                    totalcount = commonPromotionDao.getAllTransPromotionForDateCount(request.getEmpId(), request.getStartDate(), request.getEndDate());
                } else {
                    logger.info("Searching L1 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    ResultCount count = CommonApprovalDao.getZoneAllTransPromotionForDate(request.getZoneId(), request.getEmpId(), request.getPage(), request.getStartDate(), request.getEndDate());
                    trans = count.getLstTransPromo();
                    totalcount = count.getCount();
                }
                break;
            case EVENT:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getViewL1HOPromoDetailByUserEvent(new Long(request.getEmpId()), request.getEventId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL1HOPromoDetailByUserEventCount(new Long(request.getEmpId()), request.getEventId());
                } else {
                    logger.info("Searching L1 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getViewL1ZonePromoDetailByUserEvent(new Long(request.getEmpId()), request.getEventId(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL1ZonePromoDetailByUserEventCount(new Long(request.getEmpId()), request.getEventId(), request.getZoneId());
                }
                break;
            case MARKETING_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getViewL1HOPromoDetailByUserMarketting(new Long(request.getEmpId()), request.getMarketingTypeId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL1HOPromoDetailByUserMarkettingCount(new Long(request.getEmpId()), request.getMarketingTypeId());
                } else {
                    logger.info("Searching L1 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getViewL1ZonePromoDetailByUserMarketting(new Long(request.getEmpId()), request.getMarketingTypeId(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL1ZonePromoDetailByUserMarkettingCount(new Long(request.getEmpId()), request.getMarketingTypeId(), request.getZoneId());
                }
                break;

            case PROMOTION_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getViewL1HOPromoDetailByUserPromoType(new Long(request.getEmpId()), request.getPromotionTypeId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL1HOPromoDetailByUserPromoTypeCount(new Long(request.getEmpId()), request.getPromotionTypeId());
                } else {
                    logger.info("Searching L1 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getViewL1ZonePromoDetailByUserPromType(new Long(request.getEmpId()), request.getPromotionTypeId(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL1ZonePromoDetailByUserPromoTypeCount(new Long(request.getEmpId()), request.getPromotionTypeId(), request.getZoneId());
                }
                break;
            //following are new criteria
            case SUB_CATEGORY_DATE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case SUB_CATEGORY_DATE_EVENT_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case SUB_CATEGORY_DATE_MARKETTING_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case SUB_CATEGORY_DATE_PROMO_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            //CR2 Criteria
            case DATE_EVENT_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case DATE_MARKETTING_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case DATE_PROMO_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L1 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL1PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL1PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L1 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL1PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL1PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;

        }
        if (trans == null) {
            return new SearchPromotionSearchResp(new Resp(RespCode.FAILURE, "Promotion Not Found "));
        }

        List<TransPromoVO> listTransPromo = TransPromoUtil.converTransPromoVOFromTransPromo(trans);

        logger.info("Trans list size :" + listTransPromo.size());
        SearchPromotionSearchResp response = new SearchPromotionSearchResp(new Resp(RespCode.SUCCESS, "success"));
        response.setTransPromoList(listTransPromo);
        response.setTotalCount(Integer.parseInt(totalcount.toString()));
        return response;
    }

    //Level two
    public SearchPromotionSearchResp getallTransPromoforApprovalforLevel2(ApprovalLevelDashboardSearchReq request) {
        logger.info(" --- Searching All Trans Promotion Information for level 2---");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info("------ Payload------ \n" + buffer);
        List<TransPromo> trans = new ArrayList<TransPromo>();
        Long totalcount = 0l;
        String searchCrieteia = request.getType().toString();

        try {
//            if (request.getStartDate() != null) {
//                request.setStartDate(CommonUtil.addSubtractDaysInDate(request.getStartDate(), -1));
//            }
            if (request.getEndDate() != null) {
                request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
            }

        } catch (ParseException ex) {
            logger.info("--------------Start/End Date Parse Exception In  getallTransPromoforApproval getallTransPromoforApproval.");
            java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (request.getType()) {
            case ALL:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    trans = commonPromotionDao.getAllTransPromotionLevel2(request.getEmpId(), request.getPage());
                    totalcount = commonPromotionDao.getAllTransPromotionLevel2Count(request.getEmpId());
                } else {
                    ResultCount count = CommonApprovalDao.getAllTransPromotionLevel2(request.getZoneId(), request.getEmpId(), request.getPage());
                    trans = count.getLstTransPromo();
                    totalcount = count.getCount();
                }
                break;
            case CATEGORY:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User, ");
                    trans = commonPromotionDao.getAllTransPromotionForLevel2Category(request.getEmpId(), request.getCategoryName(), request.getPage());
                    totalcount = commonPromotionDao.getAllTransPromotionForLevel2CategoryCount(request.getEmpId(), request.getCategoryName());
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getZoneAllTransPromotionForLevel2Category(request.getEmpId(), request.getCategoryName(), request.getZoneId());
                    totalcount = commonPromotionDao.getZoneAllTransPromotionForLevel2CategoryCount(request.getEmpId(), request.getCategoryName(), request.getZoneId());
                }
                break;
            case DATE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getAllTransPromotionForLevel2Date(request.getEmpId(), request.getStartDate(), request.getEndDate(), request.getPage());
                    totalcount = commonPromotionDao.getAllTransPromotionForLevel2DateCount(request.getEmpId(), request.getStartDate(), request.getEndDate());
                } else {
                    logger.info("Searching L2 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    ResultCount count = CommonApprovalDao.getZoneAllTransPromotionForLevel2Date(request.getZoneId(), request.getEmpId(), request.getPage(), request.getStartDate(), request.getEndDate());
                    trans = count.getLstTransPromo();
                    totalcount = count.getCount();
                }
                break;
            case EVENT:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getViewL2HOPromoDetailByUserEvent(new Long(request.getEmpId()), request.getEventId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL2HOPromoDetailByUserEventCount(new Long(request.getEmpId()), request.getEventId());
                } else {
                    logger.info("Searching L2 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getViewL2ZonePromoDetailByUserEvent(new Long(request.getEmpId()), request.getEventId(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL2ZonePromoDetailByUserEventCount(new Long(request.getEmpId()), request.getEventId(), request.getZoneId());
                }
                break;
            case MARKETING_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getViewL2HOPromoDetailByUserMarketting(new Long(request.getEmpId()), request.getMarketingTypeId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL2HOPromoDetailByUserMarkettingCount(new Long(request.getEmpId()), request.getMarketingTypeId());
                } else {
                    logger.info("Searching L2 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getViewL2ZonePromoDetailByUserMarketting(new Long(request.getEmpId()), request.getMarketingTypeId(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL2ZonePromoDetailByUserMarkettingCount(new Long(request.getEmpId()), request.getMarketingTypeId(), request.getZoneId());
                }
                break;

            case PROMOTION_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User, All records will be displayed. ");
                    trans = commonPromotionDao.getViewL2HOPromoDetailByUserPromoType(new Long(request.getEmpId()), request.getPromotionTypeId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL2HOPromoDetailByUserPromoTypeCount(new Long(request.getEmpId()), request.getPromotionTypeId());
                } else {
                    logger.info("Searching L2 Trans Promotion  for Zone Id " + request.getZoneId() + " and page no " + request.getPage());
                    trans = commonPromotionDao.getViewL2ZonePromoDetailByUserPromType(new Long(request.getEmpId()), request.getPromotionTypeId(), request.getZoneId(), request.getPage());
                    totalcount = commonPromotionDao.getViewL2ZonePromoDetailByUserPromoTypeCount(new Long(request.getEmpId()), request.getPromotionTypeId(), request.getZoneId());
                }
                break;
            //following are new criteria
            case SUB_CATEGORY_DATE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case SUB_CATEGORY_DATE_EVENT_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case SUB_CATEGORY_DATE_MARKETTING_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case SUB_CATEGORY_DATE_PROMO_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            //CR2 criteria
            case DATE_EVENT_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case DATE_MARKETTING_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
            case DATE_PROMO_TYPE:
                if (request.getStorecode().equalsIgnoreCase("901")) {
                    logger.info("Searching L2 Trans Promotion for HO User");
                    trans = commonPromotionDao.getHOViewL2PromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getHOViewL2PromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                } else {
                    logger.info("Searching L2 Trans Promotion for Zone User");
                    trans = commonPromotionDao.getZoneViewL2PromoDetail(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()), request.getPage());
                    totalcount = commonPromotionDao.getZoneViewL2PromoDetailCount(searchCrieteia, request.getZoneId(), request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), new Long(request.getEmpId()));
                }
                break;
        }

        if (trans == null) {
            return new SearchPromotionSearchResp(new Resp(RespCode.FAILURE, "Promotion Not Found "));
        }
        List<TransPromoVO> listTransPromo = TransPromoUtil.converTransPromoVOFromTransPromo(trans);

        logger.info("Trans list size :" + listTransPromo.size());
        SearchPromotionSearchResp response = new SearchPromotionSearchResp(new Resp(RespCode.SUCCESS, "success"));
        response.setTransPromoList(listTransPromo);
        response.setTotalCount(Integer.parseInt(totalcount.toString()));
        return response;
    }

    //Business exigency
    public SearchPromotionSearchResp getallTransPromoforApprovalBusinessEzigency(ApprovalLevelDashboardSearchReq request) {
        logger.info(" --- Searching All Trans Promotion Information For Business Exigency ---");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info("------ Payload------ \n" + buffer);
        List<TransPromo> trans = new ArrayList<TransPromo>();
        Long totalcount = 0l;
        String searchCrieteia = request.getType().toString();

        try {
//            if (request.getStartDate() != null) {
//                request.setStartDate(CommonUtil.addSubtractDaysInDate(request.getStartDate(), -1));
//            }
            if (request.getEndDate() != null) {
                request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
            }

        } catch (ParseException ex) {
            logger.info("--------------Start/End Date Parse Exception In  getallTransPromoforApproval getallTransPromoforApproval.");
            java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (request.getType()) {
            case ALL:
                logger.info("== inside Promotion Dashboard  Search type ALL ====");
                ResultCount resultCount = CommonApprovalDao.getAllTransPromotionBusinessExigency(request.getPage());
                trans = resultCount.getLstTransPromo();
                totalcount = resultCount.getCount();
                break;
            case DATE:
                logger.info("== inside Promotion Dashboard  Search type DATE ====");
                trans = CommonApprovalDao.getBusinessExigencyPromoDetailByUserDate(request.getStartDate(), request.getEndDate(), request.getPage());
                totalcount = CommonApprovalDao.getBusinessExigencyPromoDetailByUserDateCount(request.getStartDate(), request.getEndDate());
                break;
            case EVENT:
                logger.info("== inside Promotion Dashboard  Search type EVENT ====");
                trans = CommonApprovalDao.getBusinessExigencyPromoDetailByUserEvent(request.getEventId(), request.getPage());
                totalcount = CommonApprovalDao.getBusinessExigencyPromoDetailByUserEventCount(request.getEventId());
                break;
            case MARKETING_TYPE:
                logger.info("== inside Promotion Dashboard  Search type MARKETTING ====");
                trans = CommonApprovalDao.getBusinessExigencyPromoDetailByUserMarketting(request.getMarketingTypeId(), request.getPage());
                totalcount = CommonApprovalDao.getBusinessExigencyPromoDetailByUserMarkettingCount(request.getMarketingTypeId());
                break;
            case PROMOTION_TYPE:
                logger.info("== inside Promotion Dashboard  Search type PROMO Type ====");
                trans = CommonApprovalDao.getBusinessExigencyPromoDetailByUserPromoType(request.getPromotionTypeId(), request.getPage());
                totalcount = CommonApprovalDao.getBusinessExigencyPromoDetailByUserPromoTypeCount(request.getPromotionTypeId());
                break;
            //following are new criteria
            case CATEGORY_DATE:
                logger.info("Searching Exigency Trans Promotion , Category Date. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            case SUB_CATEGORY_DATE:
                logger.info("Searching Exigency Trans Promotion ,Sub Category Date. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            case SUB_CATEGORY_DATE_EVENT_TYPE:
                logger.info("Searching Exigency Trans Promotion ,Sub Category Date Event. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            case SUB_CATEGORY_DATE_MARKETTING_TYPE:
                logger.info("Searching Exigency Trans Promotion ,Sub Category Date marketting. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            case SUB_CATEGORY_DATE_PROMO_TYPE:
                logger.info("Searching Exigency Trans Promotion ,Sub Category Date promo type. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            //cr2  criteria
            case DATE_EVENT_TYPE:
                logger.info("Searching Exigency Trans Promotion ,  Date Event. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            case DATE_MARKETTING_TYPE:
                logger.info("Searching Exigency Trans Promotion , Date marketting. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
            case DATE_PROMO_TYPE:
                logger.info("Searching Exigency Trans Promotion , Date promo type. ");
                trans = commonPromotionDao.getExigencyViewPromoDetail(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId(), request.getPage());
                totalcount = commonPromotionDao.getExigencyViewPromoDetailCount(searchCrieteia, request.getSubCategoryName(), request.getStartDate(), request.getEndDate(), request.getEventId(), request.getMarketingTypeId(), request.getPromotionTypeId());
                break;
        }

        if (trans == null) {
            return new SearchPromotionSearchResp(new Resp(RespCode.FAILURE, "Promotion Not Found "));
        }
        List<TransPromoVO> listTransPromo = TransPromoUtil.converTransPromoVOFromTransPromo(trans);
        logger.info("Trans list size :" + listTransPromo.size());
        SearchPromotionSearchResp response = new SearchPromotionSearchResp(new Resp(RespCode.SUCCESS, "success"));
        response.setTransPromoList(listTransPromo);
        response.setTotalCount(Integer.parseInt(totalcount.toString()));
        return response;
    }
    //Promotion Execution Manager

    public ExecutePromoDashBoardResp getExecutePromoDashBoardDtl(ExecutePromoDashBaordReq request) {
        try {
            logger.info("------- Getting Execute Promo Dashbaord Dtl Service -------");
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
//            logger.info("------ Payload------ \n" + buffer);
            System.out.println("------ Payload------ \n" + buffer);
            MstEmployee employee = mstEmployeeFacade.find(Long.valueOf(request.getEmpID()));
            ExecuteDashboardVO qryVo = new ExecuteDashboardVO();
            List<TransPromo> transPromoList = new ArrayList<TransPromo>();
            Long totalcount = new Long("0");
            SearchPromoDashboardCriteria crieteria = request.getPromoDashboardCriteria();

            try {

//                if (request.getStartDate() != null) {
//                    request.setStartDate(CommonUtil.addSubtractDaysInDate(request.getStartDate(), -1));
//                }
                if (request.getEndDate() != null) {
                    request.setEndDate(CommonUtil.addSubtractDaysInDate(request.getEndDate(), 1));
                }

                if (request.getPromoDashboardCriteria() != null) {
//                    if (request.getPromoDashboardCriteria().getStartDate() != null) {
//                        request.getPromoDashboardCriteria().setStartDate(CommonUtil.addSubtractDaysInDate(request.getPromoDashboardCriteria().getStartDate(), -1));
//                    }
                    if (request.getPromoDashboardCriteria().getEndDate() != null) {
                        request.getPromoDashboardCriteria().setEndDate(CommonUtil.addSubtractDaysInDate(request.getPromoDashboardCriteria().getEndDate(), 1));
                    }
                    logger.info("------ start Date : " + request.getPromoDashboardCriteria().getStartDate());
                    logger.info("------ end Date : " + request.getPromoDashboardCriteria().getEndDate());
                }

            } catch (ParseException ex) {
                logger.info("--------------Start/End Date Parse Exception In  getallTransPromoforApproval getallTransPromoforApproval.");
                java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
            }

            String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
            String searchCrieteia = null;
            switch (request.getExecuteEnum()) {
                case APPROVE_REJECT_DATA:
                    logger.info("== inside Promo Manager Search ====");
                    crieteria = request.getPromoDashboardCriteria();
                    System.out.println("----- Sub Category : " + crieteria.getSubCategoryName());
                    searchCrieteia = request.getPromoDashboardEnum().toString();
                    boolean isHO = true;
                    if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                        isHO = true;
                    } else {
                        isHO = false;
                    }
                    switch (request.getPromoDashboardEnum()) {
                        case ALL:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("------ Inside Promo Manager HO Promo Dtl-- ALL ");
                                qryVo = commonPromotionDao.getHOPromoManagerDtl(false, request.getStartIndex());
                            } else {
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                logger.info("------ Inside Promo Manager Zone Promo Dtl... ALL" + zoneId);
                                qryVo = commonPromotionDao.getZonePromoManagerDtl(zoneId, false, request.getStartIndex());
                            }
                            break;
                        case DATE:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside Promo Manager HO Promo Dtl Search type DATE ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByDate(crieteria.getStartDate(), crieteria.getEndDate(), request.getStartIndex());
                            } else {
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                logger.info("== inside Promo Manager Zone Promo Dtl Search type DATE ====");
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByDate(crieteria.getStartDate(), crieteria.getEndDate(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        case EVENT:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside  Promo Manager HO Promo Dtl  Search type EVENT ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByEvent(crieteria.getEventId(), request.getStartIndex());
                                logger.info("################# size : " + qryVo.getTransPromoList().size());
                            } else {
                                logger.info("== inside Promo Manager Zone Promo Dtl  Search type EVENT ====");
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByEvent(crieteria.getEventId(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        case MARKETING_TYPE:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside Promo Manager HO Promo Dtl  Search type MARKETING TYPE ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByMarketting(crieteria.getMarketingTypeId(), request.getStartIndex());
                            } else {
                                logger.info("== inside Promo Manager Zone Promo Dtl  Search type MARKETING TYPE ====");
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByMarketting(crieteria.getMarketingTypeId(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        case PROMOTION_TYPE:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside Promo Manager HO Promo Dtl  Search type PROMOTION TYPE ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByPromoType(crieteria.getPromotionTypeId(), request.getStartIndex());
                            } else {
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                logger.info("== inside Promo Manager Zone Promo Dtl  Search type PROMOTION TYPE ====");
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByPromoType(crieteria.getPromotionTypeId(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        case DATE_EVENT:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside  Promo Manager HO Promo Dtl  Search type EVENT ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByEventAndDate(crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), request.getStartIndex());
                            } else {
                                logger.info("== inside Promo Manager Zone Promo Dtl  Search type EVENT ====");
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByEventDate(crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        case DATE_MARKETING_TYPE:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside Promo Manager HO Promo Dtl  Search type MARKETING TYPE ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByMarkettingDate(crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getMarketingTypeId(), request.getStartIndex());
                            } else {
                                logger.info("== inside Promo Manager Zone Promo Dtl  Search type MARKETING TYPE ====");
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByMarkettingDate(crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getMarketingTypeId(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        case DATE_PROMOTION_TPYE:
                            if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                                logger.info("== inside Promo Manager HO Promo Dtl  Search type PROMOTION TYPE ====");
                                qryVo = commonPromotionDao.getHOPromoManagerDtlByPromoTypeDate(crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getPromotionTypeId(), request.getStartIndex());
                            } else {
//                                String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                                logger.info("== inside Promo Manager Zone Promo Dtl  Search type PROMOTION TYPE ====");
                                qryVo = commonPromotionDao.getZonePromoManagerDtlByPromoTypeDate(crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getPromotionTypeId(), Long.valueOf(zoneId), request.getStartIndex());
                            }
                            break;
                        //following are new criteria
                        case SUB_CATEGORY_DATE:
                            logger.info("Searching Promo Mangager Trans Promotion , Sub category Date. ");
                            transPromoList = commonPromotionDao.getPromoMangerViewPromoDetail(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId, request.getStartIndex());
                            totalcount = commonPromotionDao.getPromoMangerViewPromoDetailCount(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId);
                            qryVo.setTransPromoList(transPromoList);
                            qryVo.setTotalCount(totalcount);
                            break;
                        case SUB_CATEGORY_DATE_EVENT_TYPE:
                            logger.info("Searching Promo Mangager Trans Promotion , Sub Category Date Event. ");
                            transPromoList = commonPromotionDao.getPromoMangerViewPromoDetail(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId, request.getStartIndex());
                            totalcount = commonPromotionDao.getPromoMangerViewPromoDetailCount(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId);
                            qryVo.setTransPromoList(transPromoList);
                            qryVo.setTotalCount(totalcount);
                            break;
                        case SUB_CATEGORY_DATE_MARKETTING_TYPE:
                            logger.info("Searching Promo Mangager Trans Promotion , Sub Category Date Marketting. ");
                            transPromoList = commonPromotionDao.getPromoMangerViewPromoDetail(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId, request.getStartIndex());
                            totalcount = commonPromotionDao.getPromoMangerViewPromoDetailCount(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId);
                            qryVo.setTransPromoList(transPromoList);
                            qryVo.setTotalCount(totalcount);
                            break;
                        case SUB_CATEGORY_DATE_PROMO_TYPE:
                            logger.info("Searching Promo Mangager Trans Promotion , Sub Category Date Promo Type. ");
                            transPromoList = commonPromotionDao.getPromoMangerViewPromoDetail(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId, request.getStartIndex());
                            totalcount = commonPromotionDao.getPromoMangerViewPromoDetailCount(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId);
                            qryVo.setTransPromoList(transPromoList);
                            qryVo.setTotalCount(totalcount);
                            break;
                        case CATEGORY_DATE:
                            logger.info("Searching Promo Mangager Trans Promotion , category Date. ");
                            transPromoList = commonPromotionDao.getPromoMangerViewPromoDetail(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId, request.getStartIndex());
                            totalcount = commonPromotionDao.getPromoMangerViewPromoDetailCount(searchCrieteia, crieteria.getSubCategoryName(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), isHO, zoneId);
                            qryVo.setTransPromoList(transPromoList);
                            qryVo.setTotalCount(totalcount);
                            break;
                    }
                    // logger.info("################# size : " + qryVo.getTransPromoList().size());
                    break;
                case ALL:
                    if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                        logger.info("------ Inside ALL case HO Promo Dtl");
//                        qryVo = commonPromotionDao.getHOPromoManagerDtl(true, request.getStartIndex());
                        qryVo = commonPromotionDao.getHOPromoManagerDtl(false, request.getStartIndex());
                    } else {
//                        String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                        logger.info("------ Inside ALL case Zone Promo Dtl..." + zoneId);
                        qryVo = commonPromotionDao.getZonePromoManagerDtl(zoneId, true, request.getStartIndex());
                    }
                    break;
                case TEAM_MEMBER:
                    searchCrieteia = request.getPromoDashboardEnum().toString();
                    logger.info("------ Inside Team Member  Promo Dtl");
                    crieteria = request.getPromoDashboardCriteria();
                    switch (request.getPromoDashboardEnum()) {
                        case ALL:
                            logger.info("== inside Promo Detailing Promo Dtl Search type ALL ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtl(request.getEmpID(), request.getStartIndex());
                            break;
                        case DATE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type DATE ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtlByDate(request.getEmpID(), crieteria.getStartDate(), crieteria.getEndDate(), request.getStartIndex());
                            break;
                        case EVENT:
                            logger.info("== inside Team Member Promo Detailing Promo Dtl Search type Event ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtlByEvent(request.getEmpID(), crieteria.getEventId(), request.getStartIndex());
                            break;
                        case MARKETING_TYPE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type Marketting Type ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtlByMarketting(request.getEmpID(), crieteria.getMarketingTypeId(), request.getStartIndex());
                            break;
                        case PROMOTION_TYPE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type Promo Type  ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtlByPromoType(request.getEmpID(), crieteria.getPromotionTypeId(), request.getStartIndex());
                            break;
                        //Following are new criteria
                        case DATE_EVENT:
                            logger.info("== inside Promo Detailing Promo Dtl Search type DATE_EVENT  ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtl(searchCrieteia, request.getEmpID(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), request.getStartIndex());
                            break;
                        case DATE_MARKETING_TYPE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type DATE_MARKETING_TYPE  ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtl(searchCrieteia, request.getEmpID(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), request.getStartIndex());
                            break;
                        case DATE_PROMOTION_TPYE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type DATE_PROMOTION_TPYE  ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtl(searchCrieteia, request.getEmpID(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), request.getStartIndex());
                            break;
                        //cr2 criteria
                        case CATEGORY_DATE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type CATEGORY_DATE  ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtl(searchCrieteia, request.getEmpID(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), request.getStartIndex());
                            break;
                        case SUB_CATEGORY_DATE:
                            logger.info("== inside Promo Detailing Promo Dtl Search type SUB_CATEGORY_DATE  ====");
                            qryVo = commonPromotionDao.getTeamMemberTransPromoDtl(searchCrieteia, request.getEmpID(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), request.getStartIndex());
                            break;
                    }
                    break;
                case DATE:
                    logger.info("------ Inside date wise Promo Dtl...." + request.getStartDate() + "....." + request.getEndDate());
                    if (employee.getMstStore().getMstStoreId().equalsIgnoreCase("901")) {
                        logger.info("------ Inside Date HO Promo Dtl");
                        qryVo = commonPromotionDao.getDateWiseTransPromoDtl(request.getStartDate(), request.getEndDate(), null, true, request.getStartIndex());
                    } else {
//                        String zoneId = employee.getMstStore().getMstZone().getZoneId().toString();
                        logger.info("------ Inside Date Zone Promo Dtl..." + zoneId);
                        qryVo = commonPromotionDao.getDateWiseTransPromoDtl(request.getStartDate(), request.getEndDate(), zoneId, false, request.getStartIndex());
                    }

                    break;
            }
            transPromoList = qryVo.getTransPromoList();
            if (transPromoList != null && transPromoList.size() > 0) {
                logger.info("------- Trans Promo List : " + transPromoList.size());
                List<TransPromoVO> listTransPromo = TransPromoUtil.converTransPromoVOFromTransPromo(transPromoList);
                return (new ExecutePromoDashBoardResp(new Resp(RespCode.SUCCESS, "TransPromo List"), listTransPromo, qryVo.getTotalCount()));
            } else {
                return (new ExecutePromoDashBoardResp(new Resp(RespCode.FAILURE, "No Trans Promo Detail Found.")));
            }
        } catch (Exception ex) {
            logger.error("Error : " + ex.getMessage());
            ex.printStackTrace();
            return new ExecutePromoDashBoardResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Error :" + ex.getMessage()));
        }
    }

    public Resp createTransPromo(CreateTransPromoReq request) {
        logger.info("Trans Promo Creation Service.");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info("------ Payload------ \n" + buffer);
        try {
            MstPromo promo = promoDao.find(request.getMstPromoId());
            if (promo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Id Not Correct");
            }
            MstPromotionType type = promoTypeDao.find(request.getTypeId());
            if (type == null) {
                return new Resp(RespCode.FAILURE, "Promotion Type Id Not Correct");
            }
            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_DRAFTED);
            if (request.getZoneId() == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Can not be Null");
            }
            MstZone zone = zoneDao.find(request.getZoneId());
            if (zone == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Not Correct");
            }
            MstEmployee emp = empDao.find(request.getEmpId());
            if (emp == null) {
                return new Resp(RespCode.FAILURE, "Employee Id Not Correct");
            }
            TransPromo transPromo = TransPromoUtil.getTransPromo(request.getTransPromoVO(), promo, type, status, zone, emp, false, null);
            transPromoDao.create(transPromo);
            logger.info("Promotion Sub Request Created Successfully with Id " + transPromo.getTransPromoId());
            List<TransPromoArticle> transArticleList = TransPromoUtil.getTransPromoArticle(request.getTransPromoVO(), transPromo, status, emp);

            boolean isHO = false;

            // TODO inserting in the batch.
            for (TransPromoArticle article : transArticleList) {
                transPromoArticleDao.create(article);
                if (!isHO) {
                    Mch mch = mchDao.find(article.getMcCode());
                    if (mch == null) {
                        return new Resp(RespCode.FAILURE, "MC Code " + article.getMcCode() + " Not Found In Database. Contact Work Flow Manager Admin.");
                    }
                    if (mch != null && mch.getMstLocation() != null && mch.getMstLocation().getLocationId() == 1) {
                        isHO = true;
                        transPromo.setIsHo(isHO);
                    }
                }
            }

            List<TransPromoConfig> configList = TransPromoUtil.getTransPromoConfig(request.getTransPromoVO(), transPromo);
            for (TransPromoConfig config : configList) {
                transPromoConfigDao.create(config);
            }

            // creating set of mcs.
            Set<String> mcset = new HashSet<String>();
            for (TransPromoArticle article : transArticleList) {
                mcset.add(article.getMcCode());
            }

            for (String mc : mcset) {
                commonDao.insertTransPromoMC(transPromo.getTransPromoId(), mc, new Long("11"), emp.getEmpId(), new Date());
            }

            //CR Phase 3 - Adding data into history table. 13-11-13
            TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(emp, status, status, transPromo.getRemarks(), transPromo);
            transPromoStatusFacade.create(promoStatus);

            return new Resp(RespCode.SUCCESS, "Promotion Sub Request Created Successfully with Id " + transPromo.getTransPromoId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Processing Request");
        }
    }

    public Resp createTransPromoWithFile(CreateTransPromoReq request) {
        logger.info("Trans Promo Creation Service using File.");
        try {
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            logger.info("------ Payload------ \n" + buffer);
            MstPromo promo = promoDao.find(request.getMstPromoId());
            if (promo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Id Not Correct");
            }
            MstPromotionType type = promoTypeDao.find(request.getTypeId());
            if (type == null) {
                return new Resp(RespCode.FAILURE, "Promotion Type Id Not Correct");
            }
            MstStatus status = statusDao.find(CommonStatusConstants.FILE_UNDER_PROCESS);
            if (request.getZoneId() == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Can not be Null");
            }
            MstZone zone = zoneDao.find(request.getZoneId());
            if (zone == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Not Correct");
            }
            MstEmployee emp = empDao.find(request.getEmpId());
            if (emp == null) {
                return new Resp(RespCode.FAILURE, "Employee Id Not Correct");
            }
            promo.setMstStatus(status);
            TransPromo transPromo = TransPromoUtil.getTransPromo(request.getTransPromoVO(), promo, type, status, zone, emp, false, null);
            transPromoDao.create(transPromo);
            logger.info("Promotion Sub Request Created Successfully with Id " + transPromo.getTransPromoId());

            List<TransPromoFile> transPromoFileList = TransPromoUtil.getTransPromoFile(request.getTransPromoVO(), transPromo);
            for (TransPromoFile file : transPromoFileList) {
                transPromoFileDao.create(file);
            }
//            List<TransPromoArticle> transArticleList = TransPromoUtil.getTransPromoArticle(request.getTransPromoVO(), transPromo, status, emp);
//
//            boolean isHO = false;
//
//            // TODO inserting in the batch.
//            for (TransPromoArticle article : transArticleList) {
//                transPromoArticleDao.create(article);
//                if (!isHO) {
//                    Mch mch = mchDao.find(article.getMcCode());
//                    if (mch == null) {
//                        return new Resp(RespCode.FAILURE, "MC Code " + article.getMcCode() + " Not Found In Database. Contact Work Flow Manager Admin.");
//                    }
//                    if (mch != null && mch.getMstLocation() != null && mch.getMstLocation().getLocationId() == 1) {
//                        isHO = true;
//                        transPromo.setIsHo(isHO);
//                    }
//                }
//            }

            List<TransPromoConfig> configList = TransPromoUtil.getTransPromoConfig(request.getTransPromoVO(), transPromo);
            for (TransPromoConfig config : configList) {
                transPromoConfigDao.create(config);
            }

//            // creating set of mcs.
//            Set<String> mcset = new HashSet<String>();
//            for (TransPromoArticle article : transArticleList) {
//                mcset.add(article.getMcCode());
//            }
//
//            for (String mc : mcset) {
//                commonDao.insertTransPromoMC(transPromo.getTransPromoId(), mc, new Long("11"), emp.getEmpId(), new Date());
//            }
            transPromo.setIsFileUpload(Boolean.TRUE);
//File processing, please check View /Modify page for status
            NotificationMessage msg = new NotificationMessage(transPromo.getTransPromoId(), NotificationType.ARTICLE_FILE_VALIDATION);
            mailService.sendNotificationMessage(msg);
            return new Resp(RespCode.SUCCESS, "Promotion Sub Request Created Successfully with Id " + transPromo.getTransPromoId() + ". Please Check View /Modify Page for File Status.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Processing Request");
        }
    }

    public Resp updateTransPromo(CreateTransPromoReq request) {
        logger.info("Trans Promo Updation Service.");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
//            MstPromo promo = promoDao.find(request.getMstPromoId());
//            if (promo == null) {
//                return new Resp(RespCode.FAILURE, "Promotion Id Not Correct");
//            }
//            logger.info(" === Promotion Found Successfully == " + promo);
            MstPromotionType type = promoTypeDao.find(request.getTypeId());

            if (type == null) {
                return new Resp(RespCode.FAILURE, "Promotion Type Id Not Correct");
            }

            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_DRAFTED);


//            MstZone zone = zoneDao.find(request.getZoneId());
//            if (zone == null) {
//                return new Resp(RespCode.FAILURE, "Zone Id Not Correct");
//            }
//            MstEmployee emp = empDao.find(request.getEmpId());
//            if (emp == null) {
//                return new Resp(RespCode.FAILURE, "Employee Id Not Correct");
//            }

            // fetching transpromo for updation.
            TransPromo transPromo = transPromoDao.find(request.getTransPromoVO().getTransPromoId());
            if (transPromo == null) {
                return new Resp(RespCode.FAILURE, "Trans Promo Not Found with Id " + request.getTransPromoVO().getTransPromoId());
            }
            transPromo = TransPromoUtil.getTransPromo(request.getTransPromoVO(), transPromo.getMstPromo(), type, status, transPromo.getMstZone(), transPromo.getMstEmployee4(), true, transPromo);
            logger.info("Promotion Sub Request Updated Successfully with Id " + transPromo.getTransPromoId());
            if (request.getTransPromoVO().getTransPromoArticleList() != null && !request.getTransPromoVO().getTransPromoArticleList().isEmpty()) {
                // remove all existing article
                commonPromotionDao.deleteAllARticlewithTransPromo(transPromo.getTransPromoId());
                logger.info("=== All Existing ARticles Deleted Successfully");
                commonPromotionDao.deleteAllMcwithTransPromo(transPromo.getTransPromoId());
                logger.info("=== All Existing Mcs Deleted Successfully");
                List<TransPromoArticle> transArticleList = TransPromoUtil.getTransPromoArticle(request.getTransPromoVO(), transPromo, status, transPromo.getMstEmployee4());
                Set<String> mcSet = new HashSet<String>();

                for (TransPromoArticle article : transArticleList) {
                    transPromoArticleDao.create(article);
                    mcSet.add(article.getMcCode());

                }
                boolean isHO = false;
                for (String mcCode : mcSet) {
                    TransPromoMc mc = new TransPromoMc();
                    mc.setTransPromo(transPromo);
                    Mch mch = mchDao.find(mcCode);
                    mc.setMch(mch);
                    mc.setMstStatus(new MstStatus(CommonStatusConstants.PROMO_DRAFTED));
                    mc.setMstEmployee(transPromo.getMstEmployee4());
                    mc.setUpdatedTime(new Date());
                    transPromoMCFacade.create(mc);

                    if (!isHO) {
                        if (mch != null && mch.getMstLocation() != null && mch.getMstLocation().getLocationId() == 1) {
                            isHO = true;
                            transPromo.setIsHo(isHO);
                        }
                    }
                }
            }
            if (request.getTransPromoVO().getTransPromoConfigList() != null && !request.getTransPromoVO().getTransPromoConfigList().isEmpty()) {
                // remove all config
                List<TransPromoConfig> configList = TransPromoUtil.getTransPromoConfig(request.getTransPromoVO(), transPromo);
                commonPromotionDao.deleteAllConfigwithTransPromo(transPromo.getTransPromoId());
                logger.info("=== Config Deleted Successfully === ");
                for (TransPromoConfig config : configList) {
                    transPromoConfigDao.create(config);
                }
            }
            boolean isStatus = false;
            MstPromo mstPromo = transPromo.getMstPromo();
            Collection<TransPromo> collectionTransPromo = mstPromo.getTransPromoCollection();
            for (TransPromo tp : collectionTransPromo) {
                if (tp.getMstStatus().getStatusId() == CommonStatusConstants.PROMO_DRAFTED) {
                    isStatus = true;
                } else {
                    isStatus = false;
                    break;
                }
            }
            if (isStatus) {
                mstPromo.setMstStatus(new MstStatus(CommonStatusConstants.PROMO_DRAFTED));
            } else {
                mstPromo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_FAILURE));
            }
            return new Resp(RespCode.SUCCESS, "Promotion Sub Request Updated Successfully with Id " + transPromo.getTransPromoId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Processing Request");
        }
    }

    public Resp createCopyOFTransPromo(Long mstReqId, Long transPromoId, Long EmpId) {
        try {
            // Calendar calender = Calendar.getInstance();
            TransPromo transPromo = transPromoDao.find(transPromoId);
            if (transPromo == null) {
                return new Resp(RespCode.FAILURE, "Invalid Promotion Sub Request Id.");
            }
            MstEmployee emp = empDao.find(EmpId);
            if (emp == null) {
                return new Resp(RespCode.FAILURE, "Employee Id Not Correct");
            }
            MstPromo oldMstPromo = transPromo.getMstPromo();
            if (oldMstPromo == null) {
                return new Resp(RespCode.FAILURE, "Invalid Promotion Id.");
            }
            PromotionVO promotionVO = new PromotionVO();
            promotionVO.setCategorySub(oldMstPromo.getCategory());
            promotionVO.setSubCategorySub(oldMstPromo.getSubCategory());
            promotionVO.setRemarks(oldMstPromo.getRemarks());
            promotionVO.setReqName(oldMstPromo.getRequestName());
//master promo req creation
            MstPromo newMstPromo = TransPromoUtil.getMasterPromoReq(transPromo.getMstPromo().getMstEvent(), transPromo.getMstPromo().getMstMktg(), emp, transPromo.getMstPromo().getMstCampaign(), promotionVO);
            promoDao.create(newMstPromo);
            logger.info("Mst Promo Id : " + newMstPromo.getPromoId());
            /*
            String strCategory = null, strSubCategory = null;
            for (String str : oldMstPromo.getCategory().split(",")) {
            logger.info("str Category : " + str);
            strCategory = "'" + str + "'";
            }
            for (String str : oldMstPromo.getSubCategory().split(",")) {
            logger.info("str Sub Category: " + str);
            strSubCategory = "'" + str + "'";
            }
            //            List<Mch> list = commonDao.getAllMCBySubCategoryName(strCategory, strSubCategory);
            List<Mch> list = commonDao.getAllMCBySubCategoryName(strCategory, strSubCategory);
            logger.info("Mch list Size  : " + list.size());
            List<MapPromoMch> newMapPromoMchList = new ArrayList<MapPromoMch>();
            for (Mch mch : list) {
            MapPromoMch mapPromoMch = new MapPromoMch();
            mapPromoMch.setMch(mch);
            mapPromoMch.setMstPromo(newMstPromo);
            newMapPromoMchList.add(mapPromoMch);
            mapPromoMchDao.create(mapPromoMch);
            }
            newMstPromo.setMapPromoMchCollection(newMapPromoMchList);
             */

            // new change start
            Collection<MapPromoMch> oldpromoMCHList = oldMstPromo.getMapPromoMchCollection();
            if (oldpromoMCHList != null && oldpromoMCHList.size() > 0) {
                List<MapPromoMch> newMapPromoMchList = new ArrayList<MapPromoMch>();
                for (MapPromoMch oldMapMCH : oldpromoMCHList) {
                    Mch mch = oldMapMCH.getMch();
                    MapPromoMch mapPromoMch = new MapPromoMch();
                    mapPromoMch.setMch(mch);
                    mapPromoMch.setMstPromo(newMstPromo);
                    newMapPromoMchList.add(mapPromoMch);
                    mapPromoMchDao.create(mapPromoMch);
                }
                newMstPromo.setMapPromoMchCollection(newMapPromoMchList);
                logger.info("-------- new Master Request  MC List Size : " + newMapPromoMchList.size());
            }
            // new change finished

            //master promo req creation
            //trans promo creation
            Collection<TransPromoArticle> transPromoArticlesList = transPromo.getTransPromoArticleCollection();
            Collection<TransPromoConfig> transPromoConfigsList = transPromo.getTransPromoConfigCollection();
            TransPromoVO promoVO = new TransPromoVO();
            promoVO.setRemark(transPromo.getRemarks());
            promoVO.setValidFrom(transPromo.getValidFrom().toString());
            promoVO.setValidTo(transPromo.getValidTo().toString());
            if (transPromo.getMstPromotionType().getPromoTypeId() == 7) {
                promoVO.setBuyQty(transPromo.getBuyQty());
                promoVO.setGetQty(transPromo.getGetQty());
            }
            TransPromoArticleVO articleVO = null;
            List<TransPromoArticleVO> promoArticleVOsList = new ArrayList<TransPromoArticleVO>();
            for (TransPromoArticle article : transPromoArticlesList) {
                articleVO = new TransPromoArticleVO();
                articleVO.setArtCode(article.getArticle());
                articleVO.setArtDesc(article.getArticleDesc());
                articleVO.setMcCode(article.getMcCode());
                articleVO.setMcDesc(article.getMcDesc());
                if (article.getBrandCode() != null) {
                    articleVO.setBrandCode(article.getBrandCode());
                }
                if (article.getBrandDesc() != null) {
                    articleVO.setBrandDesc(article.getBrandDesc());
                }
                if (article.getSetId() != null) {
                    articleVO.setSetId(article.getSetId());
                }
                articleVO.setQty(article.getQty());
                if (article.getIsA() != null) {
                    articleVO.setIsA(article.getIsA());
                }
                if (article.getIsB() != null) {
                    articleVO.setIsB(article.getIsB());
                }
                if (article.getIsX() != null) {
                    articleVO.setIsX(article.getIsX());
                }
                if (article.getIsY() != null) {
                    articleVO.setIsY(article.getIsY());
                }
                if (article.getIsZ() != null) {
                    articleVO.setIsZ(article.getIsZ());
                }
                promoArticleVOsList.add(articleVO);

            }
            promoVO.setTransPromoArticleList(promoArticleVOsList);
            TransPromoConfigVO configVO = null;
            List<TransPromoConfigVO> transPromoConfigVOsList = new ArrayList<TransPromoConfigVO>();
            for (TransPromoConfig vo : transPromoConfigsList) {
                configVO = new TransPromoConfigVO();
                configVO.setDiscConfig(vo.getDiscountConfig());
                configVO.setDiscValue(vo.getDiscountValue());
                configVO.setGrowthCoversion(vo.getGrowthConversion());
                configVO.setMarginAchievement(vo.getMarginAchivement());
                configVO.setQty(vo.getQty());
                configVO.setSalesGrowth(vo.getSalesGrowth());
                configVO.setSellThruQty(vo.getSellthruvsQty());
                configVO.setTicketDiscAmt(vo.getTicketDiscAmt());
                configVO.setTicketWorthAmt(vo.getTicketWorthAmt());
                configVO.setTicketSizeGrowth(vo.getTicketSizeGrowth());
                configVO.setValidFrom(vo.getValidFrom().toString());
                configVO.setValidTo(vo.getValidTo().toString());
                if (vo.getIsA() != null) {
                    configVO.setIsA(vo.getIsA());
                }
                if (vo.getIsB() != null) {
                    configVO.setIsB(vo.getIsB());
                }
                if (vo.getIsX() != null) {
                    configVO.setIsX(vo.getIsX());
                }
                if (vo.getIsY() != null) {
                    configVO.setIsY(vo.getIsY());
                }
                if (vo.getIsZ() != null) {
                    configVO.setIsZ(vo.getIsZ());
                }
                transPromoConfigVOsList.add(configVO);
            }
            promoVO.setTransPromoConfigList(transPromoConfigVOsList);
            TransPromo transPromoNew = TransPromoUtil.getTransPromo(promoVO, newMstPromo, transPromo.getMstPromotionType(), new MstStatus(11l), transPromo.getMstZone(), emp, false, null);
            transPromoDao.create(transPromoNew);
//            logger.info("Old Date : "+transPromoNew.getUpdatedDate());
//
//            transPromoNew.setUpdatedDate(calender.getTime());
//            logger.info("new Date : "+transPromoNew.getUpdatedDate());

            logger.info("Promotion Sub Request Created Successfully with Id " + transPromoNew.getTransPromoId());

            //Article to be copied
            List<TransPromoArticle> transArticleList = TransPromoUtil.getTransPromoArticle(promoVO, transPromoNew, new MstStatus(11l), emp);
            boolean isHO = false;
            // TODO inserting in the batch.
            for (TransPromoArticle article : transArticleList) {
                transPromoArticleDao.create(article);
                if (!isHO) {
                    Mch mch = mchDao.find(article.getMcCode());
                    if (mch == null) {
                        return new Resp(RespCode.FAILURE, "MC Code " + article.getMcCode() + " Not Found In Database. Contact Work Flow Manager Admin.");
                    }
                    if (mch != null && mch.getMstLocation() != null && mch.getMstLocation().getLocationId() == 1) {
                        isHO = true;
                        transPromoNew.setIsHo(isHO);
                    }
                }
            }
            List<TransPromoConfig> configList = TransPromoUtil.getTransPromoConfig(promoVO, transPromoNew);
            for (TransPromoConfig config : configList) {
                transPromoConfigDao.create(config);
            }
            // creating set of mcs.
            Set<String> mcset = new HashSet<String>();
            for (TransPromoArticle article : transArticleList) {
                mcset.add(article.getMcCode());
            }
            for (String mc : mcset) {
                commonDao.insertTransPromoMC(transPromoNew.getTransPromoId(), mc, new Long("11"), emp.getEmpId(), new Date());
            }
            //trans promo creation
            String oldId, newId;
            oldId = "T" + transPromo.getMstPromo().getPromoId() + "-R" + transPromo.getTransPromoId();
            newId = "T" + transPromoNew.getMstPromo().getPromoId() + "-R" + transPromoNew.getTransPromoId();
            return new Resp(RespCode.SUCCESS, "Promotion Sub Request Id " + oldId + " Copied Successfully with Id " + newId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Creating Request");
        }
    }

    public Resp updateTransPromoForFileUpload(UpdateTransPromoWithFileReq request) {
        logger.info("Trans Promo Updation Service.");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            TransPromo transPromo = transPromoDao.find(request.getTransPromoId());
            if (transPromo == null) {
                return new Resp(RespCode.FAILURE, "Trans Promo Not Found with Id " + request.getTransPromoId());
            }
            transPromo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_UNDER_PROCESS));
            transPromo.setUpdatedDate(new Date());

            int delOldFileEntry = commonDao.deleteTransPromoFileByTransPromo(transPromo.getTransPromoId());
            logger.info("-------- Deleted Count : " + delOldFileEntry);

            TransPromoFile promoFile = new TransPromoFile();
            promoFile.setTransPromo(transPromo);
            promoFile.setIsArticleFile(Boolean.FALSE);
            promoFile.setFilePath(request.getUploadFilePath());
            transPromoFileDao.create(promoFile);

            NotificationMessage message = new NotificationMessage();
            message.setTransPromoFileId(promoFile.getMapTransPromoFileId());
            message.setNotificationType(NotificationType.TRANS_PROMO_REQUEST_SUBMIT_FILE);
            mailService.sendCreateUpdateSubPromoMessage(message);

            return new Resp(RespCode.SUCCESS, "Promotion Sub Request File Uploaded Successfully with Id R:" + transPromo.getTransPromoId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Creating Request");
        }
    }

    public DownloadSubPromoResp downloadSubPromoRequest(Long TransPromoId) {
        try {
            TransPromo subPromo = transPromoDao.find(TransPromoId);
            String filePath = TransPromoUtil.createAndGetDownloadSubPromoFile(subPromo);
            return (new DownloadSubPromoResp(new Resp(RespCode.SUCCESS, "Success"), filePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new DownloadSubPromoResp(new Resp(RespCode.FAILURE, "Error While downlaoding Request"));
        }
    }

    public Resp createTransPromoForFileUpload(CreateTransPromoWithFileReq request) {
        logger.info("Trans Promo creation using file upload Service.");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            MstPromo mstPromo = promoDao.find(request.getMstPromoId());
            if (mstPromo == null) {
                return new Resp(RespCode.FAILURE, "Promo Not Found with Id H" + request.getMstPromoId());
            }
            mstPromo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_UNDER_PROCESS));
            mstPromo.setUpdatedDate(new Date());
            MstPromo promo = promoDao.find(request.getMstPromoId());
            if (promo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Id Not Correct.");
            }
            logger.info(" === Promotion Found Successfully == " + promo);
            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_DRAFTED);
            if (request.getZoneId() == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Can not be Null.");
            }
            MstZone zone = zoneDao.find(request.getZoneId());
            if (zone == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Not Correct.");
            }
            MstEmployee emp = empDao.find(request.getEmpId());
            if (emp == null) {
                return new Resp(RespCode.FAILURE, "Employee Id Not Correct.");
            }

            TransPromo transPromo = TransPromoUtil.getTransPromoWithFile(mstPromo, status, zone, emp);
            transPromoDao.create(transPromo);
            logger.info("Promotion Sub Request Created Successfully with Id " + transPromo.getTransPromoId());

            TransPromoFile promoFile = new TransPromoFile();
            promoFile.setTransPromo(transPromo);
            promoFile.setIsArticleFile(Boolean.FALSE);
            promoFile.setFilePath(request.getUploadFilePath());
            transPromoFileDao.create(promoFile);

//            NotificationMessage msg = new NotificationMessage(NotificationType.TRANS_PROMO_REQUEST_SUBMIT_FILE, transPromo.getTransPromoId().toString());
//            mailService.sendCreateSubPromoFileMessage(msg);

            NotificationMessage message = new NotificationMessage();
            message.setTransPromoFileId(promoFile.getMapTransPromoFileId());
            message.setNotificationType(NotificationType.TRANS_PROMO_REQUEST_SUBMIT_FILE);
            mailService.sendCreateUpdateSubPromoMessage(message);

            return new Resp(RespCode.SUCCESS, "Promotion Sub Request File Uploaded Successfully with Id H" + transPromo.getMstPromo().getPromoId() + "-R" + transPromo.getTransPromoId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Creating Request");
        }
    }

    public Resp submitMultipleTransPromoFileUpload(CreateMultiPlePromoReq request) {
        try {
            logger.info("------- Inside Multiple TransPromo File Upload");
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            logger.info(" PAYLOAD ::::::\n " + buffer);

            MstPromo promo = promoDao.find(Long.valueOf(request.getMstPromoId()));
            if (promo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Id Not Correct");
            }
            logger.info(" === Promotion Found Successfully == " + promo);

            if (promo.getMstStatus().getStatusId().equals(CommonStatusConstants.FILE_UNDER_PROCESS)) {
                return (new Resp(RespCode.FAILURE, "Previous Uploaded File Is Under Process."));
            }

            promo.setFilePath(request.getFilePath());

            promo.setMstStatus(new MstStatus(CommonStatusConstants.FILE_UNDER_PROCESS));

            promo.setErrorFilePath("-");

            NotificationMessage message = new NotificationMessage();

            message.setZoneId(Long.valueOf(request.getZoneId()));
            message.setEmpId(request.getEmpId());
            message.setId(request.getMstPromoId());
            message.setNotificationType(NotificationType.MULTIPLE_PROMOTION_FILE);
            mailService.sendCreateUpdateSubPromoMessage(message);

            return (new Resp(RespCode.SUCCESS, "File Uploaded Successfully."));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + ex.getMessage());
        }
    }

    public Resp createTransPromoList(CreateMultiPlePromoReq requestList) {
        //CreateTransPromoReq request
        logger.info("Trans Promo Creation Service.");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(requestList, buffer);
        System.out.println(" PAYLOAD ::::::\n " + buffer);
        try {
            MstPromo promo = promoDao.find(Long.valueOf(requestList.getMstPromoId()));
            if (promo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Id Not Correct");
            }
            logger.info(" === Promotion Found Successfully == " + promo);

            if (requestList.getZoneId() == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Can not be Null");
            }
            MstZone zone = zoneDao.find(Long.valueOf(requestList.getZoneId()));
            if (zone == null) {
                return new Resp(RespCode.FAILURE, "Zone Id Not Correct");
            }

            MstEmployee emp = empDao.find(Long.valueOf(requestList.getEmpId()));
            if (emp == null) {
                return new Resp(RespCode.FAILURE, "Employee Id Not Correct");
            }

            MstStatus status = statusDao.find(CommonStatusConstants.PROMO_DRAFTED);
            List<TransPromoVO> transPromoList = requestList.getTransPromoVO();
            for (TransPromoVO request : transPromoList) {
                MstPromotionType type = promoTypeDao.find(request.getPromoTypeId());
                if (type == null) {
                    return new Resp(RespCode.FAILURE, "Promotion Type Id Not Correct");
                }

                TransPromo transPromo = TransPromoUtil.getTransPromo(request, promo, type, status, zone, emp, false, null);
                transPromoDao.create(transPromo);

                List<TransPromoArticle> transArticleList = TransPromoUtil.getTransPromoArticle(request, transPromo, status, emp);

                boolean isHO = false;

                // TODO inserting in the batch.
                for (TransPromoArticle article : transArticleList) {
                    transPromoArticleDao.create(article);
                    if (!isHO) {
                        Mch mch = mchDao.find(article.getMcCode());
                        if (mch == null) {
                            return new Resp(RespCode.FAILURE, "MC Code " + article.getMcCode() + " Not Found In Database. Contact Work Flow Manager Admin.");
                        }
                        if (mch != null && mch.getMstLocation() != null && mch.getMstLocation().getLocationId() == 1) {
                            isHO = true;
                            transPromo.setIsHo(isHO);
                        }
                    }
                }

                List<TransPromoConfig> configList = TransPromoUtil.getTransPromoConfig(request, transPromo);
                for (TransPromoConfig config : configList) {
                    transPromoConfigDao.create(config);
                }

                // creating set of mcs.
                Set<String> mcset = new HashSet<String>();
                for (TransPromoArticle article : transArticleList) {
                    mcset.add(article.getMcCode());
                }

                for (String mc : mcset) {
                    commonDao.insertTransPromoMC(transPromo.getTransPromoId(), mc, new Long("11"), emp.getEmpId(), new Date());
                }
            }
            return new Resp(RespCode.SUCCESS, "Promotion Sub Request Created Successfully with Id " + requestList.getMstPromoId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error While Processing Request");
            return new Resp(RespCode.FAILURE, "Error While Processing Request");
        }
    }

    public void updateMasterPromoStatus(Long promoId) {
        logger.info("------ Inside Updating Master Promo. ID : " + promoId);
        MstPromo promo = promoDao.find(promoId);
        Collection<TransPromo> transpromoList = promo.getTransPromoCollection();
        if (transpromoList != null && transpromoList.size() > 0) {
            logger.info("--------- Trans Promo Size : " + transpromoList.size());
            boolean noFileFailure = true;
            for (TransPromo subPromo : transpromoList) {
                if (subPromo.getMstStatus().getStatusId().equals(CommonStatusConstants.FILE_FAILURE)) {
                    logger.info("----- Trans Promo status found file failure for ID  : " + subPromo.getTransPromoId());
                    MstStatus status = statusDao.find(Long.valueOf("6"));
                    promo.setMstStatus(status);
                    promo.setUpdatedDate(new Date());
                    logger.info("----- Promo status update to file failure ---- " + promo.getMstStatus().getStatusId());
                    noFileFailure = false;
                    break;
                }
            }
            if (noFileFailure) {
                promo.setMstStatus(new MstStatus(CommonStatusConstants.PROMO_DRAFTED));
                promo.setUpdatedDate(new Date());
                logger.info("----- Promo status update to Promo drafted ---- " + promo.getMstStatus().getStatusId());
            }
        }
    }
}
