/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MapPromoCity;
import com.fks.promo.entity.MapPromoFormat;
import com.fks.promo.entity.MapPromoMch;
import com.fks.promo.entity.MapPromoRegion;
import com.fks.promo.entity.MapPromoState;
import com.fks.promo.entity.MapPromoStore;
import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstCampaign;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstEvent;
import com.fks.promo.entity.MstMktg;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstStore;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.facade.MapPromoCityFacade;
import com.fks.promo.facade.MapPromoFormatFacade;
import com.fks.promo.facade.MapPromoMchFacade;
import com.fks.promo.facade.MapPromoRegionFacade;
import com.fks.promo.facade.MapPromoStateFacade;
import com.fks.promo.facade.MapPromoStoreFacade;
import com.fks.promo.facade.MapPromoZoneFacade;
import com.fks.promo.facade.MstCampaignFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstEventFacade;
import com.fks.promo.facade.MstMktgFacade;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promo.facade.MstStoreFacade;
import com.fks.promo.facade.MstZoneFacade;
import com.fks.promo.master.vo.MstZoneVO;
import com.fks.promo.master.vo.StoreVO;
import com.fks.promo.service.CommonPromoMailService;
import com.fks.promo.service.CommonPromoVo;
import com.fks.promo.service.NotificationMessage;
import com.fks.promo.service.NotificationType;
import com.fks.promotion.vo.GetPromotionReqResponse;
import com.fks.promotion.vo.OrganizationDtlResp;
import com.fks.promotion.vo.PromotionVO;
import com.fks.promotion.vo.SubmitPromoOrgdtlReq;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author krutij
 */
@Stateless
@LocalBean
@WebService
public class PromotionInitiateService {

    private static final Logger logger = Logger.getLogger(PromotionInitiateService.class.getName());
    @EJB
    private MstEmployeeFacade employeeFacade;
    @EJB
    private MstEventFacade eventFacade;
    @EJB
    private MstMktgFacade mktgFacade;
    @EJB
    private MstPromoFacade promoFacade;
    @EJB
    private CommonDAO commonDao;
    @EJB
    private MapPromoMchFacade mapPromoMchDao;
    @EJB
    private MapPromoCityFacade promoCityDao;
    @EJB
    private MapPromoFormatFacade promoFormatDao;
    @EJB
    private MapPromoRegionFacade promoRegionDao;
    @EJB
    private MapPromoStateFacade promoStateDao;
    @EJB
    private MstZoneFacade zoneFacade;
    @EJB
    private MapPromoZoneFacade promoZoneDao;
    @EJB
    private MapPromoStoreFacade promoStoreDao;
    @EJB
    private CommonPromotionDao commonPromotionDao;
    @EJB
    private MstStoreFacade mstStoreDao;
    @EJB
    private CommonPromoMailService mailService;
    @EJB
    private MstCampaignFacade mstCampaignFacade;

    public Resp SubmitPromotionDetail(PromotionVO request) {
        logger.info("===== Inside SubmitPromotionDetail Service =====");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            Calendar calender = Calendar.getInstance();
            if (request.getOperationCode() == 1) {
                logger.info("Inside Creating promotion");
                MstEmployee employee = employeeFacade.find(request.getEmpId());
                if (employee == null) {
                    return new Resp(RespCode.FAILURE, "Employee code : " + request.getEmpCode() + " not Exists!!");
                }

                MstCampaign campaign = mstCampaignFacade.find(request.getCampaignId());
                if (campaign == null) {
                    return new Resp(RespCode.FAILURE, "Invalid Objective");
                }

                MstEvent event = eventFacade.find(request.getEventId());
                if (event == null) {
                    return new Resp(RespCode.FAILURE, "Invalid Campaign !");
                }
                MstMktg mktg = mktgFacade.find(request.getMktgId());
                if (mktg == null) {
                    return new Resp(RespCode.FAILURE, "Invalid Marketing Type !");
                }
                MstPromo mstPromo = new MstPromo();
                mstPromo.setCategory(request.getCategorySub());
                mstPromo.setCreatedDate(calender.getTime());
                mstPromo.setMstEmployee1(employee);
                mstPromo.setMstEvent(event);
                mstPromo.setMstCampaign(campaign);
                mstPromo.setMstMktg(mktg);
                mstPromo.setMstStatus(new MstStatus(11l));
                mstPromo.setRemarks(request.getRemarks());
                mstPromo.setRequestName(request.getReqName());
                mstPromo.setSubCategory(request.getSubCategorySub());
                mstPromo.setVendorBacked(request.getVendorBacked());
                promoFacade.create(mstPromo);
                List<Mch> list = commonDao.getAllMCBySubCategoryName(request.getCategory(), request.getSubCategory());
                List<MapPromoMch> listMapPromoMch = new ArrayList<MapPromoMch>();
                for (Mch mch : list) {
                    MapPromoMch mapPromoMch = new MapPromoMch();
                    mapPromoMch.setMch(mch);
                    mapPromoMch.setMstPromo(mstPromo);
                    listMapPromoMch.add(mapPromoMch);
                    mapPromoMchDao.create(mapPromoMch);
                }
                mstPromo.setMapPromoMchCollection(listMapPromoMch);
                mstPromo.setUpdatedDate(calender.getTime());
                String reqId = mstPromo.getPromoId().toString();
                return (new Resp(RespCode.SUCCESS, "Promotion Request Created Successfully ! Request Id is :" + "T" + reqId, mstPromo.getPromoId()));
            } else if (request.getOperationCode() == 2) {
                logger.info("updatae Promotion");
                MstPromo mstPromo = promoFacade.find(new Long(request.getReqId()));
                if (mstPromo == null) {
                    return new Resp(RespCode.FAILURE, "Promotion Request Id : " + request.getReqId() + " not Exists!!");
                }
                MstEmployee employee = employeeFacade.find(request.getEmpId());
                if (employee == null) {
                    return new Resp(RespCode.FAILURE, "Employee code : " + request.getEmpCode() + " not Exists!!");
                }
                MstCampaign campaign = mstCampaignFacade.find(request.getCampaignId());
                if (campaign == null) {
                    return new Resp(RespCode.FAILURE, "Invalid Objective");
                }
                MstEvent event = eventFacade.find(request.getEventId());
                if (event == null) {
                    return new Resp(RespCode.FAILURE, "Invalid Campaign !");
                }
                MstMktg mktg = mktgFacade.find(request.getMktgId());
                if (mktg == null) {
                    return new Resp(RespCode.FAILURE, "Invalid Marketing Type !");
                }


                String[] existingcategoryArr = mstPromo.getCategory().split(",");
                String[] existingSubCategoryArr = mstPromo.getSubCategory().split(",");
                String[] newCategory = request.getCategorySub().split(",");
                String[] newSubCategory = request.getSubCategorySub().split(",");
//                System.out.println("Existing Category : "+mstPromo.getCategory());
//                System.out.println("Existing Sub Category : "+mstPromo.getSubCategory());
//                System.out.println("New Category : "+request.getCategorySub());
//                System.out.println("New Sub Category : "+request.getSubCategorySub());
                StringBuffer categorybuffer = new StringBuffer();
                boolean categoryNotfound = false;
                for (int i = 0; i < existingcategoryArr.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < newCategory.length; j++) {
                        if ((existingcategoryArr[i].trim().equals(newCategory[j].trim()))) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        //System.out.println("Found : " + existingcategoryArr[i]);
                    } else {
                        System.out.println("Not Found : " + existingcategoryArr[i]);
                        categoryNotfound = true;
                        categorybuffer.append(existingcategoryArr[i]).append(",");

                    }
                }
                if (categoryNotfound) {
                    String categoryexist = categorybuffer.substring(0, categorybuffer.lastIndexOf(","));
                    return (new Resp(RespCode.FAILURE, "You can not remove category :" + categoryexist + " for Request Id is :" + "T" + mstPromo.getPromoId(), mstPromo.getPromoId()));
                }
                StringBuffer subcategorybuffer = new StringBuffer();
                boolean subcategoryNotfound = false;
                for (int i = 0; i < existingSubCategoryArr.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < newSubCategory.length; j++) {
                        if ((existingSubCategoryArr[i].trim().equals(newSubCategory[j].trim()))) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        //System.out.println("Found : " + existingSubCategoryArr[i]);
                    } else {
                        System.out.println("Not Found : " + existingSubCategoryArr[i]);
                        subcategoryNotfound = true;
                        subcategorybuffer.append(existingSubCategoryArr[i]).append(",");
                    }
                }
                if (subcategoryNotfound) {
                    String subcategoryexist = subcategorybuffer.substring(0, subcategorybuffer.lastIndexOf(","));
                    return (new Resp(RespCode.FAILURE, "You can not remove sub Category : " + subcategoryexist + " for Request Id is :" + "T" + mstPromo.getPromoId(), mstPromo.getPromoId()));
                }
                Collection mappromoMch = mstPromo.getMapPromoMchCollection();
                if (mappromoMch.size() > 0) {
                    int i = commonDao.deleteAllMCHbyPromoId(mstPromo.getPromoId());
                    logger.info("No of deleted Mapp Promo  : " + i);
                }

                List<Mch> list = commonDao.getAllMCBySubCategoryName(request.getCategory(), request.getSubCategory());
                //System.out.println("MCH list size based on category n sub category : "+list.size());
                List<MapPromoMch> listMapPromoMch = new ArrayList<MapPromoMch>();
                for (Mch mch : list) {
                    MapPromoMch mapPromoMch = new MapPromoMch();
                    mapPromoMch.setMch(mch);
                    mapPromoMch.setMstPromo(mstPromo);
                    listMapPromoMch.add(mapPromoMch);
                    mapPromoMchDao.create(mapPromoMch);
                }
                mstPromo.setCategory(request.getCategorySub());
                mstPromo.setMstEmployee(employee);
                mstPromo.setMstEvent(event);
                mstPromo.setMstCampaign(campaign);
                mstPromo.setMstMktg(mktg);
//                mstPromo.setMstStatus(new MstStatus(11l));
                mstPromo.setRemarks(request.getRemarks());
                mstPromo.setRequestName(request.getReqName());
                mstPromo.setSubCategory(request.getSubCategorySub());
                mstPromo.setUpdatedDate(calender.getTime());
                return (new Resp(RespCode.SUCCESS, "Promotion Request Updated Successfully ! Request Id is :" + "T" + mstPromo.getPromoId(), mstPromo.getPromoId()));
            } else {
                return new Resp(RespCode.FAILURE, "Invalid Operation Code !");
            }
        } catch (Exception e) {
            logger.info("Exception in SubmitPromotionDetail");
            e.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public List<PromotionVO> getAllPromotionRequestdtl() {
        logger.info("========= Inside getAllPromotionRequestdtl Service =====");
        try {
            List<PromotionVO> list = new ArrayList<PromotionVO>();
            PromotionVO promotionVO = null;
            List<MstPromo> mps = promoFacade.findAll();
            for (MstPromo mp : mps) {
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
                //promotionVO.setStatusName(mp.getMstStatus().getStatusDesc());
                list.add(promotionVO);
            }
            return list;

        } catch (Exception e) {
            logger.info("Exception in getAllPromotionRequestdtl service : " + e.getMessage());
            e.printStackTrace();
            return null;

        }
    }

    public GetPromotionReqResponse getPromoReqDtlPromoIDwise(Long promoId) {
        logger.info("========= Inside getPromoReqDtlPromoIDwise Service ======");
        try {
            PromotionVO promotionVO = new PromotionVO();
            MstPromo mp = promoFacade.find(new Long(promoId));
            if (mp == null) {
                return (new GetPromotionReqResponse(new Resp(RespCode.FAILURE, "Promotion Request Id : " + promoId + " not Exists!!")));
            }
            promotionVO = new PromotionVO();
            promotionVO.setCategory(mp.getCategory());
            promotionVO.setCreatedDate(CommonUtil.dispayDateFormat(mp.getCreatedDate()));
            promotionVO.setCreatedEmpName(mp.getMstEmployee1().getEmployeeName());
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
            promotionVO.setStatusName(mp.getMstStatus().getStatusDesc());
            if (mp.getMstCampaign() != null) {
                promotionVO.setCampaignId(mp.getMstCampaign().getCampaignId());
                promotionVO.setCampaignName(mp.getMstCampaign().getCampaignName());
            }

            return (new GetPromotionReqResponse(new Resp(RespCode.SUCCESS, "success"), promotionVO));
        } catch (Exception e) {
            e.printStackTrace();
            return (new GetPromotionReqResponse(new Resp(RespCode.FAILURE, "Exception :" + e.getMessage())));
        }
    }

    public Resp SaveSubmitPromoOrganizationDtl(SubmitPromoOrgdtlReq request) {
        logger.info("========= Inside SaveSubmitPromoOrganizationDtl service =====");
        try {
            Calendar calender = Calendar.getInstance();
            MstPromo mstPromo = promoFacade.find(request.getMstPromoID());
            if (mstPromo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Request Id : " + "R" + CommonUtil.zeroPad(Integer.parseInt(request.getMstPromoID().toString()), 8) + " not Exists!!");
            }

            List<String> listCity = request.getListCity();
            if (request.getListCity() != null) {
                Collection<MapPromoCity> mapPromoCitys = mstPromo.getMapPromoCityCollection();
                if (mapPromoCitys.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoCity(mstPromo.getPromoId());
                    logger.info("No of deleted Cities : " + i);
                }
                MapPromoCity mapPromoCity;
                for (String sCity : listCity) {
                    mapPromoCity = new MapPromoCity();
                    mapPromoCity.setCityDesc(sCity);
                    mapPromoCity.setMstPromo(mstPromo);
                    mapPromoCity.setUpdatedDate(calender.getTime());
                    promoCityDao.create(mapPromoCity);
                }
            }
            List<String> listFormat = request.getListFormat();
            if (request.getListFormat() != null) {
                Collection<MapPromoFormat> mapPromoFormats = mstPromo.getMapPromoFormatCollection();
                if (mapPromoFormats.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoFormat(mstPromo.getPromoId());
                    logger.info("No of deleted formats : " + i);
                }
                MapPromoFormat mapPromoFormat;
                for (String sFormat : listFormat) {
                    mapPromoFormat = new MapPromoFormat();
                    mapPromoFormat.setFormatDesc(sFormat);
                    mapPromoFormat.setMstPromo(mstPromo);
                    mapPromoFormat.setUpdatedDate(calender.getTime());
                    promoFormatDao.create(mapPromoFormat);
                }
            }
            List<String> listRegion = request.getListRegion();
            if (listRegion != null) {
                Collection<MapPromoRegion> mapPromoRegions = mstPromo.getMapPromoRegionCollection();
                if (mapPromoRegions.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoRegion(mstPromo.getPromoId());
                    logger.info("No of deleted Region : " + i);
                }
                MapPromoRegion mapPromoRegion;
                for (String sRegion : listRegion) {
                    mapPromoRegion = new MapPromoRegion();
                    mapPromoRegion.setRegionName(sRegion);
                    mapPromoRegion.setMstPromo(mstPromo);
                    mapPromoRegion.setUpdatedDate(calender.getTime());
                    promoRegionDao.create(mapPromoRegion);
                }
            }
            List<String> listState = request.getListState();
            if (listState != null) {
                Collection<MapPromoState> mapPromoStates = mstPromo.getMapPromoStateCollection();
                if (mapPromoStates.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoState(mstPromo.getPromoId());
                    logger.info("No of deleted State : " + i);
                }
                MapPromoState mapPromoState;
                for (String sState : listState) {
                    mapPromoState = new MapPromoState();
                    mapPromoState.setStateDesc(sState);
                    mapPromoState.setMstPromo(mstPromo);
                    mapPromoState.setUpdatedDate(calender.getTime());
                    promoStateDao.create(mapPromoState);
                }
            }
            List<MstZoneVO> listZone = request.getListMstZoneVo();
            if (listZone != null) {
                Collection<MapPromoZone> mapPromoZones = mstPromo.getMapPromoZoneCollection();
                if (mapPromoZones.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoZone(mstPromo.getPromoId());
                    logger.info("No of deleted Zone : " + i);
                }
                MapPromoZone mapPromoZone;
                MstZone mstZone;
                for (MstZoneVO vo : listZone) {
                    mapPromoZone = new MapPromoZone();
                    mstZone = zoneFacade.find(vo.getId());
                    mapPromoZone.setMstPromo(mstPromo);
                    mapPromoZone.setMstZone(mstZone);
                    mapPromoZone.setUpdatedDate(calender.getTime());
                    promoZoneDao.create(mapPromoZone);
                }
                if (listZone.size() > 1) {
                    logger.info("============ Zone List size : " + listZone.size() + " ================");
                    int isHoFlag = commonPromotionDao.updateAllTransPromoIsHOBasedOnZone(mstPromo.getPromoId());
                    logger.info("=== No. Trans Promo Updated " + isHoFlag);
                }
            }
            List<StoreVO> listStore = request.getListStoreVo();
            if (listStore != null) {
                Collection<MapPromoStore> mapPromoStores = mstPromo.getMapPromoStoreCollection();
                if (mapPromoStores.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoStore(mstPromo.getPromoId());
                    logger.info("No of deleted Stores : " + i);
                }

                for (StoreVO vo : listStore) {
                    MstStore mstStore = mstStoreDao.find(vo.getStoreID());
                    logger.info("Store :--- " + mstStore);

                    if (mstStore != null) {
                        //logger.info("---- Inside---");
                        MapPromoStore mapPromoStore = new MapPromoStore();
                        mapPromoStore.setMstPromo(mstPromo);
                        mapPromoStore.setMstStore(mstStore);
                        mapPromoStore.setUpdatedDate(calender.getTime());
                        promoStoreDao.create(mapPromoStore);
                    }
                }
            }
            //update article , transPromo, mstpromo
            int iArticle, itrasnPromo, ipromo, iPromoMc;
            Collection<TransPromo> transPromos = mstPromo.getTransPromoCollection();
            logger.info("----- status :" + request.getStatus() + "--- common status : " + CommonStatusConstants.PROMO_SUBMITTED);
            if (request.getStatus().equals(CommonStatusConstants.PROMO_SUBMITTED)) {
                if (!transPromos.isEmpty() && transPromos.size() > 0) {
                    for (TransPromo vo : transPromos) {
                        iArticle = commonPromotionDao.changeAllTransPromoMCStatus(vo.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED, CommonStatusConstants.PROMO_DRAFTED);
                        logger.info("=== No. of Trans Promo Articles Affected Are " + iArticle);
                        itrasnPromo = commonPromotionDao.changeAllTransPromoStatus(vo.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
                        logger.info("=== No. of Trans Promo Affected Are " + itrasnPromo);
                        iPromoMc = commonPromotionDao.changeAllTransPromoMCStatus(vo.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
                        logger.info("========= no of Trans Promo MC afftected are  " + iPromoMc);
                    }
                    ipromo = commonPromotionDao.changeMstPromoStatus(mstPromo.getPromoId(), CommonStatusConstants.PROMO_SUBMITTED, request.getEmpID());
                    logger.info("=== No. of Trans Promo Affected Are " + ipromo);
                    logger.info("Master Promostion ID  : " + mstPromo.getPromoId());
                    NotificationMessage msg = new NotificationMessage(NotificationType.REQUEST_SUBMIT, mstPromo.getPromoId().toString());
                    mailService.sendNotificationMessage(msg);
                    return new Resp(RespCode.SUCCESS, "Organization details submited Successfully.  Request Id is :" + "T" + request.getMstPromoID().toString());

                } else {
                    return new Resp(RespCode.SUCCESS, "Organization details saved Successfully.  Request Id is :" + "T" + request.getMstPromoID().toString());
                }
            } else {
                return new Resp(RespCode.SUCCESS, "Organization details saved Successfully.  Request Id is :" + "T" + request.getMstPromoID().toString());
            }
        } catch (Exception e) {
            logger.info("Exception in SubmitPromotionDetail");
            e.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public Resp deletePromotionRequest(Long mstPromoId, Long transPromoId, Long Status) {
        logger.info("=== Inside deleteting Promotion , Trans Promo , Trans Promo Artifact , Trans Config detail,Trans Promo Mcs =====");
        logger.info("MstPromoId : " + mstPromoId + "  TransPromoID : " + transPromoId);
        try {
            commonPromotionDao.deleteAllARticlewithTransPromo(transPromoId);
            logger.info("=== All Existing ARticles Deleted Successfully ======");

            commonPromotionDao.deleteAllConfigwithTransPromo(transPromoId);
            logger.info("=== Config Deleted Successfully === ");

            commonPromotionDao.deleteAllMcwithTransPromo(transPromoId);
            logger.info("=== All Existing MCs Deleted Successfully ====");

            commonPromotionDao.deleteAllFilewithTransPromo(transPromoId);
            logger.info("=== Trans Promo File deleted Successfully .=== ");

            commonPromotionDao.deleteTransPromoRequest(transPromoId, Status);
            logger.info("=== Trans Promo request deleted Successfully .=== ");

            MstPromo mstPromo = null;
            if (mstPromoId != null) {
                mstPromo = promoFacade.find(mstPromoId);
            }
            if (mstPromo != null) {
                Collection<TransPromo> transPromosList = mstPromo.getTransPromoCollection();
                if (transPromosList.isEmpty()) {
                    int i = commonPromotionDao.deleteMapPromoCity(mstPromo.getPromoId());
                    logger.info("No of deleted Cities : " + i);
                    int iformat = commonPromotionDao.deleteMapPromoFormat(mstPromo.getPromoId());
                    logger.info("No of deleted formats : " + iformat);
                    int iregion = commonPromotionDao.deleteMapPromoRegion(mstPromo.getPromoId());
                    logger.info("No of deleted Region : " + iregion);
                    int istate = commonPromotionDao.deleteMapPromoState(mstPromo.getPromoId());
                    logger.info("No of deleted State : " + istate);
                    int izone = commonPromotionDao.deleteMapPromoZone(mstPromo.getPromoId());
                    logger.info("No of deleted Zone : " + izone);
                    int istore = commonPromotionDao.deleteMapPromoStore(mstPromo.getPromoId());
                    logger.info("No of deleted Stores : " + istore);
                    int imch = commonPromotionDao.deleteMapPromoMc(mstPromo.getPromoId());
                    logger.info("No of deleted Promo Mcs : " + imch);
                    commonPromotionDao.deleteMasterPromotion(mstPromoId, Status);
                    logger.info("Mst Promo delete successfully...");
                } else {
                    Long counttrans = commonPromotionDao.getTransPromoCountForDelete(Long.valueOf(mstPromoId));
                    System.out.println("Count Trans Promo :" + counttrans);
                    if (counttrans == 0) {
                        mstPromo.setMstStatus(new MstStatus(Long.valueOf("11")));
                    }
                }
            }
            return (new Resp(RespCode.SUCCESS, "Promotion Deleted Successfully . Request Id : " + "T" + mstPromo.getPromoId() + "-R" + transPromoId.toString()));

        } catch (Exception e) {
            logger.info("Exception in deletePromotionRequest");
            e.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public OrganizationDtlResp getZoneBasedOnFormat(String formatlst, String locationId, String zoneId) {
        logger.info("=== Inside getZoneBasedOnFormat Service =====");
        try {
            List<CommonPromoVo> listZoneReq = new ArrayList<CommonPromoVo>();
            CommonPromoVo mstZoneVO;
            List<MstZone> listZone = new ArrayList<MstZone>();
            if (locationId.equalsIgnoreCase("1")) {
                logger.info("=== HO ===");
                listZone = commonDao.getAllZoneBasedOnFormatForHO(formatlst);
            } else if (locationId.equalsIgnoreCase("3")) {
                logger.info("=== Zone ===");
                listZone = commonDao.getAllZoneBasedOnFormatForZone(formatlst, zoneId);
            }
            //  logger.info("=== Zone Count : "+listZone.size());
            for (MstZone vo : listZone) {
                mstZoneVO = new CommonPromoVo();
                mstZoneVO.setId(vo.getZoneId().toString());
                mstZoneVO.setName(vo.getZoneName());
                listZoneReq.add(mstZoneVO);
            }
            return new OrganizationDtlResp(new Resp(RespCode.SUCCESS, "Success"), listZoneReq);
        } catch (Exception e) {
            e.printStackTrace();
            return new OrganizationDtlResp(new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public OrganizationDtlResp getStateBasedOnFormatAndZone(String formatlst, String zoneList) {
        logger.info("=== Inside getStateBasedOnFormatAndZone Service  =====");
        //logger.info("Format List String : " + formatlst +"  ====Zone : "+ zoneList);
        try {
            List<String> listStateRegionCity = new ArrayList<String>();
            listStateRegionCity = commonDao.getAllStateBasedOnFormatAndZone(formatlst, zoneList);
            return new OrganizationDtlResp(listStateRegionCity, new Resp(RespCode.SUCCESS, "Success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new OrganizationDtlResp(new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public OrganizationDtlResp getRegionBasedOnFormatAndState(String formatlst, String statelist, String zoneList) {
        logger.info("=== Inside getRegionBasedOnFormatAndState Service =====");
        //logger.info("Format List String : " + formatlst+"===== State : "+statelist);
        try {
            List<String> listStateRegionCity = new ArrayList<String>();
            listStateRegionCity = commonDao.getAllRegionBasedOnFormatAndState(formatlst, statelist, zoneList);
            return new OrganizationDtlResp(listStateRegionCity, new Resp(RespCode.SUCCESS, "Success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new OrganizationDtlResp(new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public OrganizationDtlResp getCityBasedOnFormatAndRegion(String formatlst, String region, String statename) {
        logger.info("=== Inside getCityBasedOnFormatAndRegion Service  =====");
        //logger.info("Format List String : " + formatlst+"===== Region :"+region);
        try {
            List<String> listStateRegionCity = new ArrayList<String>();
            listStateRegionCity = commonDao.getAllCityBasedOnFormatAndRegion(formatlst, region, statename);
            return new OrganizationDtlResp(listStateRegionCity, new Resp(RespCode.SUCCESS, "Success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new OrganizationDtlResp(new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public OrganizationDtlResp getStoreBasedOnFormatAndCity(String formatlst, String city, String zonelist) {
        logger.info("=== Inside getStoreBasedOnFormatAndCity Service =====");
        //logger.info("Format List String : " + formatlst+"==== City : "+city);
        try {
            OrganizationDtlResp resp = new OrganizationDtlResp();
            List<CommonPromoVo> listStore = new ArrayList<CommonPromoVo>();
            CommonPromoVo storeVO = null;
            List<MstStore> listmstStore = commonDao.getAllStoreBasedOnFormatAndCity(formatlst, city, zonelist);
            //System.out.println("Store Size : " + listmstStore.size());
            for (MstStore ms : listmstStore) {
                String storename = ms.getMstStoreId().concat(" : ").concat(ms.getSiteDescription());
                storeVO = new CommonPromoVo();
                storeVO.setId(ms.getMstStoreId());
                storeVO.setName(storename);
                listStore.add(storeVO);
            }
            resp.setResp(new Resp(RespCode.SUCCESS, "Success"));
            resp.setStoreVOList(listStore);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return new OrganizationDtlResp(new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public OrganizationDtlResp getAllFormatExceptZoneAndHo() {
        logger.info("=== Inside getAllFormatExceptZoneAndHo Service =====");
        try {
            List<String> listStateRegionCity = new ArrayList<String>();
            listStateRegionCity = commonDao.getAllFormatExceptZoneAndHO();
            return new OrganizationDtlResp(listStateRegionCity, new Resp(RespCode.SUCCESS, "Success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new OrganizationDtlResp(new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }

    public Resp StatusChangedFromDraftOrSubmitOrganizationDtl(SubmitPromoOrgdtlReq request) {
        logger.info("========= Inside StatusChangedFromDraftOrSubmitOrganizationDtl service =====");
        try {
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            logger.info("------ Payload------ \n" + buffer);
            NotificationMessage message = new NotificationMessage(NotificationType.ORGANIZATION_SUBMIT, request);
            mailService.sendStatusUpdateOfOrganizationMessage(message);
            return (new Resp(RespCode.SUCCESS, "Organization Group detail submitted successfully."));
        } catch (Exception e) {
            logger.info("Exception in StatusChangedFromDraftOrSubmitOrganizationDtl");
            e.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error :" + e.getMessage()));
        }
    }
}
