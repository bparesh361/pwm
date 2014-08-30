/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.service;

import com.fks.promo.common.CommonConstants;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.entity.MstLocation;
import com.fks.promo.entity.MstStore;
import com.fks.promo.entity.MstZone;
import com.fks.promo.facade.MstStoreFacade;
import com.fks.promo.master.vo.GetOrgDtlResp;
import com.fks.promo.master.vo.StoreVO;
import com.fks.promo.master.vo.SubmitOrganizationResp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@Stateless
@LocalBean
@WebService
public class OrganizationMasterService {

    private static final Logger logger = Logger.getLogger(OrganizationMasterService.class.getName());
    @EJB
    private MstStoreFacade mstStoreFacade;
    @EJB
    private CommonDAO commonDAO;

    public List<StoreVO> getAllOrganizationDtl() {
        logger.info("==== Inside getAllOrganizationDtl of OrgMasterService ===");
        try {
            List<MstStore> listStores = mstStoreFacade.findAll();
            List<StoreVO> storeVOList = new ArrayList<StoreVO>();
            StoreVO storeVO = null;
            for (MstStore ms : listStores) {
                storeVO = new StoreVO();
                storeVO.setCity(ms.getCity());
                if (ms.getMstEmployee() != null) {
                    storeVO.setCreatedByID(ms.getMstEmployee().getEmpId());
                    storeVO.setCreatedByName(ms.getMstEmployee().getEmployeeName());
                }
                storeVO.setFormat(ms.getFormatName());
                if (ms.getMstLocation() != null) {
                    storeVO.setLocationID(ms.getMstLocation().getLocationId());
                    storeVO.setLocationName(ms.getMstLocation().getLocationName());
                }
                storeVO.setRegion(ms.getRegionName());
                storeVO.setState(ms.getState());
                storeVO.setStoreClass(ms.getStoreClass());
                storeVO.setStoreID(ms.getMstStoreId());
                storeVO.setStoreDesc(ms.getSiteDescription());
                if (ms.getMstZone() != null) {
                    storeVO.setZoneId(ms.getMstZone().getZoneId());
                    storeVO.setZoneName(ms.getMstZone().getZoneName());
                    storeVO.setZoneCode(ms.getMstZone().getZoneCode());
                }
                if (ms.getIsBlocked() != null) {
                    storeVO.setIsStoreBlocked(ms.getIsBlocked());
                }
                storeVOList.add(storeVO);
            }
            return storeVOList;
        } catch (Exception e) {
            logger.info("=======  Exception in  getAllOrganizationDtl :");
            e.printStackTrace();
            return null;
        }
    }

    public SubmitOrganizationResp submitOrganizationDtl(List<StoreVO> listStore) {
        logger.info("=== Inside submitOrganizationDtl of OrgMasterService ====");
        try {
            Calendar calender = Calendar.getInstance();
            StringBuilder sb = new StringBuilder();
            boolean isExist = false;

            sb.append("Zone");
            sb.append(",");
            sb.append("Region");
            sb.append(",");
            sb.append("State");
            sb.append(",");
            sb.append("City");
            sb.append(",");
            sb.append("Site Code");
            sb.append(",");
            sb.append("Site Description");
            sb.append(",");
            sb.append("Location");
            sb.append(",");
            sb.append("Store Class");
            sb.append(",");
            sb.append("Format");
            sb.append("\n");
            for (StoreVO vo : listStore) {
                MstStore mstStore = commonDAO.getStoreByCode(vo.getStoreID());
                List<MstLocation> locationList = commonDAO.checkLocationByName(vo.getLocationName());
                List<MstZone> zonelist = commonDAO.checkZoneByCode(vo.getZoneName());
                if (mstStore == null) {
                    if (zonelist.isEmpty() || zonelist == null) {
                        sb.append(vo.getZoneName());
                        sb.append("---- INVALID ZONE CODE !-----");
                        sb.append(",");
                        isExist = true;
                    } else {
                        for (MstZone zone : zonelist) {
                            if (zone.getIsBlocked().toString().endsWith("1")) {
                                logger.info("Zone Type with the same name exist.");
                                sb.append(vo.getZoneName());
                                sb.append("---- ZONE CODE IS BLOCKED !-----");
                                sb.append(",");
                                isExist = true;
                            } else {
                                sb.append(vo.getZoneName());
                                sb.append(",");
                            }
                        }
                    }
                    sb.append(vo.getRegion());
                    sb.append(",");
                    sb.append(vo.getState());
                    sb.append(",");
                    sb.append(vo.getCity());
                    sb.append(",");
                    sb.append(vo.getStoreID());
                    sb.append(",");
                    sb.append(vo.getStoreDesc());
                    sb.append(",");
                    if (locationList.isEmpty() || locationList == null) {
                        sb.append(vo.getLocationName());
                        sb.append("---- INVALID LOCATION!----");
                        sb.append(",");
                        isExist = true;
                    } else {
                        sb.append(vo.getLocationName());
                        sb.append(",");
                    }
                    sb.append(vo.getStoreClass());
                    sb.append(",");
                    sb.append(vo.getFormat());
                    sb.append("\n");

                } else {
                    if (mstStore.getIsBlocked() == Boolean.TRUE) {
                        sb.append("--- STORE IS BLOCKED !---");
                        sb.append(",");
                        isExist = true;
                    }
                    if (zonelist.isEmpty()) {
                        sb.append(vo.getZoneName());
                        sb.append("--- INVALID ZONE CODE !---");
                        sb.append(",");
                        isExist = true;
                    } else {
                        for (MstZone zone : zonelist) {
                            if (zone.getIsBlocked().toString().endsWith("1")) {
                                logger.info("Zone Type with the same name exist.");
                                sb.append(vo.getZoneName());
                                sb.append("---- ZONE CODE IS BLOCKED !-----");
                                sb.append(",");
                                isExist = true;
                            } else {
                                sb.append(vo.getZoneName());
                                sb.append(",");
                            }
                        }
                    }
                    sb.append(vo.getRegion());
                    sb.append(",");
                    sb.append(vo.getState());
                    sb.append(",");
                    sb.append(vo.getCity());
                    sb.append(",");
                    sb.append(vo.getStoreID());
                    sb.append(",");
                    sb.append(vo.getStoreDesc());
                    sb.append(",");
                    if (locationList.isEmpty()) {
                        sb.append(vo.getLocationName());
                        sb.append(" ----INVALID LOCATION!---");
                        sb.append(",");
                        isExist = true;
                    } else {
                        sb.append(vo.getLocationName());
                        sb.append(",");
                    }
                    sb.append(vo.getStoreClass());
                    sb.append(",");
                    sb.append(vo.getFormat());
                    sb.append("\n");
                }

            }

            if (isExist) {
                InputStream is = (InputStream) CategoryMCHService.class.getResourceAsStream(CommonConstants.PROPERTY);
                Properties pro = new Properties();
                pro.load(is);

                String dirName = pro.getProperty("errorFile");
                //System.out.println("Dir Name : " + dirName);
                String aosFileName = "Organization_error_file_" + calender.getTimeInMillis() + ".csv";
                String filePath = dirName + aosFileName;
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                File statusFile = new File(filePath);
                statusFile.createNewFile();
                writer.write(sb.toString());
                writer.flush();
                if (writer != null) {
                    writer.close();
                }
                if (is != null) {
                    is.close();
                }

                return new SubmitOrganizationResp(new Resp(RespCode.FAILURE, "failure"), filePath);

            }

            for (StoreVO vo : listStore) {
                MstStore mstStore = commonDAO.getStoreByCode(vo.getStoreID());
                List<MstLocation> locationList = commonDAO.checkLocationByName(vo.getLocationName());
                List<MstZone> zonelist = commonDAO.checkZoneByCode(vo.getZoneName());

                if (mstStore != null) {
                    mstStore.setIsBlocked(false);
                    mstStore.setCity(vo.getCity());
                    mstStore.setFormatName(vo.getFormat());
                    mstStore.setRegionName(vo.getRegion());
                    mstStore.setState(vo.getState());
                    mstStore.setStoreClass(vo.getStoreClass());
                    mstStore.setSiteDescription(vo.getStoreDesc());
                    mstStore.setUpdatedDate(calender.getTime());
                    if (locationList == null || locationList.isEmpty()) {
                        return new SubmitOrganizationResp(new Resp(RespCode.FAILURE, "Location Name : " + vo.getLocationName() + " not Exists!!"));
                    } else {
                        for (MstLocation location : locationList) {
                            mstStore.setMstLocation(location);
                        }
                    }
                    if (zonelist == null || zonelist.isEmpty()) {
                        return new SubmitOrganizationResp(new Resp(RespCode.FAILURE, "Zone Name : " + vo.getZoneName() + " not Exists!!"));
                    } else {
                        for (MstZone zone : zonelist) {
                            mstStore.setMstZone(zone);
                        }
                    }
                } else {
                    MstStore store = new MstStore();
                    store.setIsBlocked(false);
                    store.setMstStoreId(vo.getStoreID());
                    store.setCity(vo.getCity());
                    store.setFormatName(vo.getFormat());
                    store.setRegionName(vo.getRegion());
                    store.setState(vo.getState());
                    store.setStoreClass(vo.getStoreClass());
                    store.setSiteDescription(vo.getStoreDesc());
                    store.setUpdatedDate(calender.getTime());
                    if (locationList == null || locationList.isEmpty()) {
                        return new SubmitOrganizationResp(new Resp(RespCode.FAILURE, "Location Name : " + vo.getLocationName() + " not Exists!!"));
                    } else {
                        for (MstLocation location : locationList) {
                            store.setMstLocation(location);
                        }
                    }
                    if (zonelist == null || zonelist.isEmpty()) {
                        return new SubmitOrganizationResp(new Resp(RespCode.FAILURE, "Zone Name : " + vo.getZoneName() + " not Exists!!"));
                    } else {
                        for (MstZone zone : zonelist) {
                            store.setMstZone(zone);
                        }
                    }
                    mstStoreFacade.create(store);
                }
            }
            return new SubmitOrganizationResp(new Resp(RespCode.SUCCESS, "Organization Master file uploaded Successfully !"));
        } catch (Exception e) {
            logger.info("=======  Exception in  submitOrganizationDtl :");
            e.printStackTrace();
            return new SubmitOrganizationResp(new Resp(RespCode.FAILURE, "File can not be processed. Please conatct Admin !"));
        }
    }

    public GetOrgDtlResp getOrganizationDtlBySiteCode(String storeId) {
        logger.info("======= Inside getOrganizationDtlBySiteCode Service ====");
        try {
            StoreVO storeVO = new StoreVO();
            MstStore mstStore = commonDAO.getStoreByCode(storeId.trim());
            if (mstStore == null) {
                return new GetOrgDtlResp(new Resp(RespCode.FAILURE, "Invalid site Code!"));
            }
            storeVO.setCity(mstStore.getCity());
            storeVO.setFormat(mstStore.getFormatName());
            storeVO.setStoreDesc(mstStore.getSiteDescription());
            storeVO.setRegion(mstStore.getRegionName());
            storeVO.setLocationName(mstStore.getMstLocation().getLocationName());
            storeVO.setZoneName(mstStore.getMstZone().getZoneName());

            return new GetOrgDtlResp(new Resp(RespCode.SUCCESS, "success"), storeVO);
        } catch (Exception e) {
            logger.info("=======  Exception in  submitOrganizationDtl :");
            e.printStackTrace();
            return new GetOrgDtlResp(new Resp(RespCode.FAILURE, "Exception while fetching data based on SiteCode!"));
        }
    }

    public Resp updateStoreDetail(String storeId, boolean activeflag) {
        try {
            if (storeId == null) {
                return new Resp(RespCode.FAILURE, "Invalid Store Code.");
            }
            MstStore mstStore = mstStoreFacade.find(storeId);
            if (mstStore == null) {
                return new Resp(RespCode.FAILURE, "Invalid Store Code.");
            } else {
                if(mstStore.getMstStoreId().equalsIgnoreCase("901")){

                return new Resp(RespCode.SUCCESS, "You can not block Store 901. ");
                }
                mstStore.setIsBlocked(activeflag);
                return new Resp(RespCode.SUCCESS, "Store Updated succesfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, e.getMessage());
        }
    }
}
