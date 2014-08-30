/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.commonDAO.CommonStatusConstants;
import com.fks.promo.entity.MapPromoCity;
import com.fks.promo.entity.MapPromoFormat;
import com.fks.promo.entity.MapPromoRegion;
import com.fks.promo.entity.MapPromoState;
import com.fks.promo.entity.MapPromoStore;
import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstStore;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoStatus;
import com.fks.promo.facade.MapPromoCityFacade;
import com.fks.promo.facade.MapPromoFormatFacade;
import com.fks.promo.facade.MapPromoRegionFacade;
import com.fks.promo.facade.MapPromoStateFacade;
import com.fks.promo.facade.MapPromoStoreFacade;
import com.fks.promo.facade.MapPromoZoneFacade;
import com.fks.promo.facade.MstPromoFacade;
import com.fks.promo.facade.MstStatusFacade;
import com.fks.promo.facade.MstStoreFacade;
import com.fks.promo.facade.MstZoneFacade;
import com.fks.promo.facade.TransPromoStatusFacade;
import com.fks.promotion.service.util.StatusUpdateUtil;
import com.fks.promotion.vo.SubmitPromoOrgdtlReq;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@LocalBean
@Stateless
public class OrganizationUtil {

    @EJB
    private MstPromoFacade promoFacade;
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
    private CommonDAO commonDAO;
    @EJB
    private CommonPromoMailService mailService;
    @EJB
    TransPromoStatusFacade transPromoStatusFacade;
    @EJB
    MstStatusFacade mstStatusFacade;
    private static final Logger logger = Logger.getLogger(OrganizationUtil.class);

    public Resp SaveSubmitPromoOrganizationDtl(SubmitPromoOrgdtlReq request) {
        logger.info("========= Inside SaveSubmitPromoOrganizationDtl service =====");
        try {
            StringWriter buffer = new StringWriter();
            JAXB.marshal(request, buffer);
            logger.info("------ Payload------ \n" + buffer);
            Calendar calender = Calendar.getInstance();
            if (request.getMstPromoID() == null) {
                return new Resp(RespCode.FAILURE, "Invalid Promotion Request Id.");
            }
            Long lPromoId = request.getMstPromoID();
            MstPromo mstPromo = promoFacade.find(lPromoId);
            if (mstPromo == null) {
                return new Resp(RespCode.FAILURE, "Promotion Request Id : " + "T" + request.getMstPromoID() + " not Exists!!");
            }
            List<String> listFormat = request.getListFormat();
            if (listFormat != null && listFormat.size() > 0) {
                Collection<MapPromoFormat> mapPromoFormats = mstPromo.getMapPromoFormatCollection();
                if (mapPromoFormats.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoFormat(mstPromo.getPromoId());
                }
                MapPromoFormat mapPromoFormat;
                for (String sFormat : listFormat) {
                    mapPromoFormat = new MapPromoFormat();
                    mapPromoFormat.setFormatDesc(sFormat);
                    mapPromoFormat.setMstPromo(mstPromo);
                    mapPromoFormat.setUpdatedDate(calender.getTime());
                    promoFormatDao.create(mapPromoFormat);
                }
            } else {
                logger.info("No Format Selected.");
                return new Resp(RespCode.FAILURE, "No Format Selected.");
            }
            List<CommonPromoVo> listZone = request.getListMstZoneVo1();
            if (listZone != null && listZone.size() > 0) {
                Collection<MapPromoZone> mapPromoZones = mstPromo.getMapPromoZoneCollection();
                if (mapPromoZones.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoZone(mstPromo.getPromoId());
                }
                MapPromoZone mapPromoZone;
                MstZone mstZone;
                for (CommonPromoVo vo : listZone) {
                    mapPromoZone = new MapPromoZone();
                    mstZone = zoneFacade.find(Long.valueOf(vo.getId()));
                    mapPromoZone.setMstPromo(mstPromo);
                    mapPromoZone.setMstZone(mstZone);
                    mapPromoZone.setUpdatedDate(calender.getTime());
                    promoZoneDao.create(mapPromoZone);
                }
                if (listZone.size() > 1) {
                    int isHoFlag = commonPromotionDao.updateAllTransPromoIsHOBasedOnZone(mstPromo.getPromoId());
                }
            } else {
                logger.info("No Zone Selected.");
                return new Resp(RespCode.FAILURE, "No Zone Selected.");
            }
            List<String> listState = request.getListState();
            if (listState != null && listState.size() > 0) {
                Collection<MapPromoState> mapPromoStates = mstPromo.getMapPromoStateCollection();
                if (mapPromoStates.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoState(mstPromo.getPromoId());
                }
                MapPromoState mapPromoState;
                for (String sState : listState) {
                    mapPromoState = new MapPromoState();
                    mapPromoState.setStateDesc(sState);
                    mapPromoState.setMstPromo(mstPromo);
                    mapPromoState.setUpdatedDate(calender.getTime());
                    promoStateDao.create(mapPromoState);
                }
            } else {
                //insert state based on zone and format
            }
            List<String> listRegion = request.getListRegion();
            if (listRegion != null && listRegion.size() > 0) {
                Collection<MapPromoRegion> mapPromoRegions = mstPromo.getMapPromoRegionCollection();
                if (mapPromoRegions.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoRegion(mstPromo.getPromoId());
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


            List<String> listCity = request.getListCity();
            if (listCity != null && listCity.size() > 0) {
                Collection<MapPromoCity> mapPromoCitys = mstPromo.getMapPromoCityCollection();
                if (mapPromoCitys.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoCity(mstPromo.getPromoId());
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


            List<CommonPromoVo> listStore = request.getListStoreVo1();
            if (listStore != null && listStore.size() > 0) {
                Collection<MapPromoStore> mapPromoStores = mstPromo.getMapPromoStoreCollection();
                if (mapPromoStores.size() > 0) {
                    int i = commonPromotionDao.deleteMapPromoStore(mstPromo.getPromoId());
                }
                for (CommonPromoVo vo : listStore) {
                    MstStore mstStore = mstStoreDao.find(vo.getId());
                    //logger.info("Store :--- " + mstStore);
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

//            if ((listState == null && listState.isEmpty()) || (listRegion == null && listRegion.isEmpty())
//                    || (listCity == null && listCity.isEmpty()) || (listFormat == null && listFormat.isEmpty())) {
            if (listState == null || listRegion == null || listCity == null || listFormat == null) {

                List<String> listFormatstate = request.getListFormat();
                StringBuilder sbState = new StringBuilder();
                for (String s : listFormatstate) {
                    sbState.append("'").append(s).append("'");
                    sbState.append(",");
                }
                String formatState = sbState.substring(0, sbState.lastIndexOf(","));

                List<CommonPromoVo> listZonestate = request.getListMstZoneVo1();
                StringBuilder sbZone = new StringBuilder();
                for (CommonPromoVo s : listZonestate) {
                    sbZone.append(s.getId());
                    sbZone.append(",");
                }
                String zoneState = sbZone.substring(0, sbZone.lastIndexOf(","));

                //state
                if (listState == null) {
                    logger.info("====@ Inside Inserting State Data @====");
                    List<String> lstFormatZonestate = commonDAO.getAllStateBasedOnFormatAndZone(formatState, zoneState);
                    if (lstFormatZonestate != null) {
                        Collection<MapPromoState> mapPromoStates = mstPromo.getMapPromoStateCollection();
                        if (mapPromoStates.size() > 0) {
                            int i = commonPromotionDao.deleteMapPromoState(mstPromo.getPromoId());
                        }
                        MapPromoState mapPromoState;
                        for (String sState : lstFormatZonestate) {
                            mapPromoState = new MapPromoState();
                            mapPromoState.setStateDesc(sState);
                            mapPromoState.setMstPromo(mstPromo);
                            mapPromoState.setUpdatedDate(calender.getTime());
                            promoStateDao.create(mapPromoState);
                        }
                    }
                }

                //fetch region
                if (listRegion == null) {
                    logger.info("====@ Inside Inserting Region Data @====");
                    StringBuffer sbZoneRegion = new StringBuffer();
                    if (listState != null) {
                        for (String sState : listState) {
                            sbZoneRegion.append("'").append(sState).append("'");
                            sbZoneRegion.append(",");
                        }
                    } else {
                        List<String> lstFormatZonestate = commonDAO.getAllStateBasedOnFormatAndZone(formatState, zoneState);
                        for (String sState : lstFormatZonestate) {
                            sbZoneRegion.append("'").append(sState).append("'");
                            sbZoneRegion.append(",");
                        }
                    }
                    String formatStateRegion = sbZoneRegion.substring(0, sbZoneRegion.lastIndexOf(","));
                    List<String> listFormatStateRegion = commonDAO.getAllRegionBasedOnFormatAndState(formatState, formatStateRegion, zoneState);
                    if (listFormatStateRegion != null) {
                        Collection<MapPromoRegion> mapPromoRegions = mstPromo.getMapPromoRegionCollection();
                        if (mapPromoRegions.size() > 0) {
                            int i = commonPromotionDao.deleteMapPromoRegion(mstPromo.getPromoId());
                        }
                        MapPromoRegion mapPromoRegion;
                        for (String sRegion : listFormatStateRegion) {
                            mapPromoRegion = new MapPromoRegion();
                            mapPromoRegion.setRegionName(sRegion);
                            mapPromoRegion.setMstPromo(mstPromo);
                            mapPromoRegion.setUpdatedDate(calender.getTime());
                            promoRegionDao.create(mapPromoRegion);
                        }
                    }
                }

                //insert city
                if (listCity == null) {
                    logger.info("====@ Inside Inserting City Data @====");
                    StringBuffer sbcityRegion = new StringBuffer();
                    String formatStateRegion = null;
                    StringBuffer sbZoneRegionstate = new StringBuffer();
                    if (listState != null) {
                        for (String sState : listState) {
                            sbZoneRegionstate.append("'").append(sState).append("'");
                            sbZoneRegionstate.append(",");
                        }
                    } else {
                        List<String> lstFormatZonestate = commonDAO.getAllStateBasedOnFormatAndZone(formatState, zoneState);
                        for (String sState : lstFormatZonestate) {
                            sbZoneRegionstate.append("'").append(sState).append("'");
                            sbZoneRegionstate.append(",");
                        }
                    }
                    formatStateRegion = sbZoneRegionstate.substring(0, sbZoneRegionstate.lastIndexOf(","));
                    //System.out.println("***** state : \n" + formatStateRegion);
                    if (listRegion == null) {
                        List<String> lstFormatRegionCity = commonDAO.getAllRegionBasedOnFormatAndState(formatState, formatStateRegion, zoneState);
                        for (String s : lstFormatRegionCity) {
                            sbcityRegion.append("'").append(s).append("'");
                            sbcityRegion.append(",");
                        }
                    } else {
                        for (String sRegion : listRegion) {
                            sbcityRegion.append("'").append(sRegion).append("'");
                            sbcityRegion.append(",");
                        }
                    }

                    String formatRegionCity = sbcityRegion.substring(0, sbcityRegion.lastIndexOf(","));
                    //System.out.println("***** Region : \n" + formatRegionCity);
                    List<String> listFormatRegionCity = commonDAO.getAllCityBasedOnFormatAndRegion(formatState, formatRegionCity, formatStateRegion);
                    //System.out.println("***** City Size : \n" + listFormatRegionCity.size());
                    if (listFormatRegionCity != null) {
                        Collection<MapPromoCity> mapPromoCitys = mstPromo.getMapPromoCityCollection();
                        if (mapPromoCitys.size() > 0) {
                            int i = commonPromotionDao.deleteMapPromoCity(mstPromo.getPromoId());

                        }
                        MapPromoCity mapPromoCity;
                        for (String sCity : listFormatRegionCity) {
                            mapPromoCity = new MapPromoCity();
                            mapPromoCity.setCityDesc(sCity);
                            mapPromoCity.setMstPromo(mstPromo);
                            mapPromoCity.setUpdatedDate(calender.getTime());
                            promoCityDao.create(mapPromoCity);
                        }
                    }

                }
                //fetch store
                if (listStore == null) {
                    logger.info("====@ Inside Inserting Store Data @====");
                    StringBuffer sbZoneRegionstate = new StringBuffer();
                    if (listState != null) {
                        for (String sState : listState) {
                            sbZoneRegionstate.append("'").append(sState).append("'");
                            sbZoneRegionstate.append(",");
                        }
                    } else {
                        List<String> lstFormatZonestate = commonDAO.getAllStateBasedOnFormatAndZone(formatState, zoneState);
                        for (String sState : lstFormatZonestate) {
                            sbZoneRegionstate.append("'").append(sState).append("'");
                            sbZoneRegionstate.append(",");
                        }
                    }
                    String formatStateRegion = sbZoneRegionstate.substring(0, sbZoneRegionstate.lastIndexOf(","));

                    StringBuilder sbcityRegion = new StringBuilder();
                    if (listRegion != null) {
                        for (String s : listRegion) {
                            sbcityRegion.append("'").append(s).append("'");
                            sbcityRegion.append(",");
                        }
                    } else {
                        List<String> lstFormatRegionCity = commonDAO.getAllRegionBasedOnFormatAndState(formatState, formatStateRegion, zoneState);
                        for (String s : lstFormatRegionCity) {
                            sbcityRegion.append("'").append(s).append("'");
                            sbcityRegion.append(",");
                        }
                    }
                    String formatRegionCity = sbcityRegion.substring(0, sbcityRegion.lastIndexOf(","));

                    StringBuilder sbCitystate = new StringBuilder();
                    if (listCity != null) {
                        for (String s : listCity) {
                            sbCitystate.append("'").append(s).append("'");
                            sbCitystate.append(",");
                        }
                    } else {
                        List<String> lstFormatRegionCity = commonDAO.getAllCityBasedOnFormatAndRegion(formatState, formatRegionCity, formatStateRegion);
                        for (String s : lstFormatRegionCity) {
                            sbCitystate.append("'").append(s).append("'");
                            sbCitystate.append(",");
                        }
                    }
                    String formatRegionstateCity = sbCitystate.substring(0, sbCitystate.lastIndexOf(","));

                    List<MstStore> listFormatRegionCitystore = commonDAO.getAllStoreBasedOnFormatAndCity(formatState, formatRegionstateCity, zoneState);
                    if (listFormatRegionCitystore != null) {
                        Collection<MapPromoStore> mapPromoStores = mstPromo.getMapPromoStoreCollection();
                        if (mapPromoStores.size() > 0) {
                            int i = commonPromotionDao.deleteMapPromoStore(mstPromo.getPromoId());
                        }
                        List<CommonPromoVo> list = new ArrayList<CommonPromoVo>();
                        CommonPromoVo storeVO = null;
                        for (MstStore vo : listFormatRegionCitystore) {
                            storeVO = new CommonPromoVo();
                            storeVO.setId(vo.getMstStoreId());
                            list.add(storeVO);
                        }
                        for (CommonPromoVo vo : list) {
                            MstStore mstStore = mstStoreDao.find(vo.getId());
                            if (mstStore != null) {
                                MapPromoStore mapPromoStore = new MapPromoStore();
                                mapPromoStore.setMstPromo(mstPromo);
                                mapPromoStore.setMstStore(mstStore);
                                mapPromoStore.setUpdatedDate(calender.getTime());
                                promoStoreDao.create(mapPromoStore);
                            }
                        }
                    }
                }
            }
            //update article , transPromo, mstpromo
            int iArticle, itrasnPromo, ipromo, iPromoMc;
            Collection<TransPromo> transPromos = mstPromo.getTransPromoCollection();

            logger.info("===============Oraganization Util Finish.===========");
            MstEmployee employee;

            if (request.getStatus().equals(CommonStatusConstants.PROMO_SUBMITTED)) {
                if (!transPromos.isEmpty() && transPromos.size() > 0) {
                    for (TransPromo vo : transPromos) {
                        //CR Phase 3 - Adding data into history table. 13-11-13
                        employee = vo.getMstEmployee4();
                        TransPromoStatus promoStatus = StatusUpdateUtil.submitTransPromoStatus(employee,new MstStatus(CommonStatusConstants.PROMO_SUBMITTED),vo.getMstStatus(), "Promotion submited.", vo);
                        transPromoStatusFacade.create(promoStatus);

                        iArticle = commonPromotionDao.changeAllTransPromoMCStatus(vo.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED, CommonStatusConstants.PROMO_DRAFTED);
                        logger.info("=== No. of Trans Promo Articles Affected Are " + iArticle);
                        itrasnPromo = commonPromotionDao.changeAllTransPromoStatus(vo.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
                        logger.info("=== No. of Trans Promo Affected Are " + itrasnPromo);
                        iPromoMc = commonPromotionDao.changeAllTransPromoMCStatus(vo.getTransPromoId(), CommonStatusConstants.PROMO_SUBMITTED);
                        logger.info("========= no of Trans Promo MC afftected are  " + iPromoMc);

                    }
                    ipromo = commonPromotionDao.changeMstPromoStatus(mstPromo.getPromoId(), CommonStatusConstants.PROMO_SUBMITTED, request.getEmpID());
                    logger.info("=== No. of Trans Promo Affected Are " + ipromo);
                    logger.info("Master Promotion ID  : " + mstPromo.getPromoId());

                    NotificationMessage msg = new NotificationMessage(NotificationType.REQUEST_SUBMIT, mstPromo.getPromoId().toString());
                    mailService.sendNotificationMessage(msg);


                    return new Resp(RespCode.SUCCESS, "Organization details submitted Successfully.  Request Id is :" + "T" + request.getMstPromoID().toString());

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
}
