/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.service;

import com.fks.promo.master.vo.RoleProfileMappingResp;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.entity.MapModuleProfile;
import com.fks.promo.entity.MapRoleLocation;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstLocation;
import com.fks.promo.entity.MstModule;
import com.fks.promo.entity.MstProfile;
import com.fks.promo.entity.MstRole;
import com.fks.promo.facade.MapModuleProfileFacade;
import com.fks.promo.facade.MapRoleLocationFacade;
import com.fks.promo.facade.MapRoleProfileFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstLocationFacade;
import com.fks.promo.facade.MstModuleFacade;
import com.fks.promo.facade.MstProfileFacade;
import com.fks.promo.facade.MstRoleFacade;
import com.fks.promo.master.vo.AddUpProfileModuleRequest;
import com.fks.promo.master.vo.LocationVo;
import com.fks.promo.master.vo.ModuleVo;
import com.fks.promo.master.vo.ProfileVO;
import com.fks.promo.master.vo.RoleProfileMappingReq;
import com.fks.promo.master.vo.RoleVO;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
public class RoleMasterService {

    private static final Logger logger = Logger.getLogger(RoleMasterService.class.getName());
    @EJB
    private MstRoleFacade mstRoleFacade;
    @EJB
    private MstLocationFacade mstLocationFacade;
    @EJB
    private MstProfileFacade mstProfileFacade;
    @EJB
    private MapRoleProfileFacade mapRoleProfileFacade;
    @EJB
    private MapRoleLocationFacade mapRoleLocationFacade;
    @EJB
    private MstModuleFacade mstModuleFacade;
    @EJB
    private MapModuleProfileFacade mapModuleProfileFacade;
    @EJB
    private CommonDAO commonDAO;
    @EJB
    private MstEmployeeFacade employeeFacade;

    public List<RoleVO> getAllRoles() {
        try {
            logger.info(" ===== getAllRoles Service. ===== ");
            List<MstRole> mstRoles = mstRoleFacade.findAll();
            List<RoleVO> roles = new ArrayList<RoleVO>();
            RoleVO rvo = null;

            for (MstRole mstRole : mstRoles) {
                rvo = new RoleVO();
                rvo.setRoleId(mstRole.getMstRoleId());
                if (mstRole.getMstEmployee1() != null) {
                    rvo.setCreatedBy(mstRole.getMstEmployee1().getEmployeeName());
                } else {
                    rvo.setCreatedBy(null);
                }
                if (mstRole.getCreatedDate() != null) {
                    rvo.setCreatedDate(CommonUtil.dispayDateFormat(mstRole.getCreatedDate()));
                } else {
                    rvo.setCreatedDate(null);
                }
                rvo.setIsBlocked(mstRole.getIsActive());
                rvo.setRoleName(mstRole.getRoleName());
                if (mstRole.getMstEmployee() != null) {
                    rvo.setUpdatedBy(mstRole.getMstEmployee().getEmployeeName());
                } else {
                    rvo.setUpdatedBy(null);
                }
                if (mstRole.getUpdatedDate() != null) {
                    rvo.setUpdatedDate(CommonUtil.dispayDateFormat(mstRole.getUpdatedDate()));
                } else {
                    rvo.setUpdatedDate(null);
                }

                roles.add(rvo);
            }
            return roles;
        } catch (Exception ex) {
            logger.info("==== Web Service getAllRoles exception : " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public Resp createUpdateRole(RoleVO request) {
        System.out.println("------------- createUpdateRole Service ----- Operation Code : " + request.getOperationCode());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            Calendar calender = Calendar.getInstance();

            if (request.getOperationCode() == 1) {
                MstEmployee employee = employeeFacade.find(Long.valueOf(request.getCreatedBy()));
                List<MstRole> roleList = commonDAO.checkRoleByName(request.getRoleName().trim());
                if (roleList != null && roleList.size() > 0) {
                    return new Resp(RespCode.FAILURE, "Role Name : " + request.getRoleName() + " Already Exists!!");
                }

                MstRole role = new MstRole();
                role.setRoleName(request.getRoleName().trim());
                role.setIsActive(request.getIsBlocked());
                // change created by and updated by
                role.setMstEmployee1(employee);
                role.setCreatedDate(calender.getTime());
                mstRoleFacade.create(role);

                return new Resp(RespCode.SUCCESS, "Role Created Successfully ! !", role.getMstRoleId());
            } else if (request.getOperationCode() == 2) {
                logger.info("---get role id : " + request.getRoleId());
                MstEmployee employee = employeeFacade.find(Long.valueOf(request.getUpdatedBy()));
                MstRole role = commonDAO.getRoleByID(request.getRoleId());
                if (role == null) {
                    return new Resp(RespCode.FAILURE, "Role Id : " + request.getRoleId() + " not Exists!!");
                }
                if (!role.getRoleName().equals(request.getRoleName())) {
                    List<MstRole> roleList = commonDAO.checkRoleByName(request.getRoleName().trim());
                    if (roleList != null && roleList.size() > 0) {
                        return new Resp(RespCode.FAILURE, "Role Name : " + request.getRoleName() + " Already Exists!!");
                    }
                }
                role.setIsActive(request.getIsBlocked());
                role.setRoleName(request.getRoleName());
                role.setUpdatedDate(calender.getTime());
                role.setMstEmployee(employee);

                return new Resp(RespCode.SUCCESS, "Role Updated Successfully ! !");
            } else {
                return new Resp(RespCode.FAILURE, "Invalid Operation Code !");
            }
        } catch (Exception e) {
            System.out.println("========== Web Service createUpdateRole exception : " + e.getMessage());
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + e.getMessage());
        }
    }

    public List<LocationVo> getAllLocation() {
        logger.info("====getAllLocation Service ");
        List<MstLocation> mstLocations = mstLocationFacade.findAll();
        List<LocationVo> listlocation = new ArrayList<LocationVo>();
        LocationVo locationVo = null;
        for (MstLocation ml : mstLocations) {
            locationVo = new LocationVo();
            locationVo.setLocationID(ml.getLocationId());
            locationVo.setLocationName(ml.getLocationName());
            listlocation.add(locationVo);

        }
        return listlocation;

    }

    public List<ProfileVO> getAllProfiles() {
        logger.info("======== getAllProfiles");
        List<MstProfile> mstProfiles = mstProfileFacade.findAll();
        List<ProfileVO> listProfile = new ArrayList<ProfileVO>();
        ProfileVO profileVO = null;
        for (MstProfile mstProfile : mstProfiles) {
            profileVO = new ProfileVO();
            profileVO.setProfileID(mstProfile.getProfileId());
            profileVO.setProfileName(mstProfile.getProfileName());
            profileVO.setLevelAccessName(mstProfile.getLevelAccess());
            listProfile.add(profileVO);
        }
        return listProfile;
    }

    public Resp SubmitRoleProfileMapping(RoleProfileMappingReq req) {
        logger.info("======= Submit role Profile Mapping Service ========");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(req, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            MstRole role = commonDAO.getRoleByID(req.getRoleID());
            if (role == null) {
                return new Resp(RespCode.FAILURE, "Invalid Role !");
            }
            //logger.info("--- Profile Found : "+ profile.getProfileName());
            MstLocation location = mstLocationFacade.find(req.getLocationId());
            if (location == null) {
                return new Resp(RespCode.FAILURE, "Invalid Location !");
            }
            int deleteprofile = commonDAO.deleteRoleProfile(req.getRoleID());
            logger.info("=== deleted Profile!!" + deleteprofile);

            int deletelocation = commonDAO.deleteRoleLocation(req.getRoleID());
            logger.info("=== deleted Location!!" + deletelocation);

            MapRoleProfile mapRoleProfile = null;
            for (Long lProfilelist : req.getProfileList()) {
                MstProfile profile = mstProfileFacade.find(lProfilelist);
                mapRoleProfile = new MapRoleProfile();
                mapRoleProfile.setMstProfile(profile);
                mapRoleProfile.setMstRole(role);
                mapRoleProfileFacade.create(mapRoleProfile);
            }

            MapRoleLocation mapRoleLocation = new MapRoleLocation();
            mapRoleLocation.setMstLocation(location);
            mapRoleLocation.setMstRole(role);
            mapRoleLocationFacade.create(mapRoleLocation);


            return new Resp(RespCode.SUCCESS, "Role & Profile mapped Successfully ! !");
        } catch (Exception e) {
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + e.getMessage());
        }
    }

    public RoleProfileMappingResp getRoleWiseLocationandProfile(Long RoleId) {
        logger.info("======== getRoleWiseLocationandProfile Service");
        try {
            RoleProfileMappingResp mappingResp = new RoleProfileMappingResp();
            List<Long> listProfiles = new ArrayList<Long>();
            MstRole role = commonDAO.getRoleByID(RoleId);
            if (role == null) {
                return new RoleProfileMappingResp(new Resp(RespCode.FAILURE, "Invalid Role !"));
            }
            Collection<MapRoleLocation> mapRoleLocationsList = role.getMapRoleLocationCollection();
            for (MapRoleLocation location : mapRoleLocationsList) {
                mappingResp.setLocationId(location.getMstLocation().getLocationId());
                break;
            }
            Collection<MapRoleProfile> mapRoleProfilesList = role.getMapRoleProfileCollection();
            for (MapRoleProfile profile : mapRoleProfilesList) {
                listProfiles.add(profile.getMstProfile().getProfileId());
            }
            mappingResp.setProfileList(listProfiles);
            mappingResp.setResp(new Resp(RespCode.SUCCESS, "Role Wise Profile and Locataion retrives successfully!"));

            return mappingResp;
        } catch (Exception e) {
            logger.info("Error on getRoleWiseLocationandProfile : " + e.getMessage());
            e.printStackTrace();
            return new RoleProfileMappingResp(new Resp(RespCode.FAILURE, "Error on getRoleWiseLocationandProfile : " + e.getMessage()));
        }
    }

    public List<ModuleVo> getAllModule() {
        logger.info("==== getAllModule Service=== ");
        List<ModuleVo> moduleVoList = new ArrayList<ModuleVo>();
        List<MstModule> mstModules = mstModuleFacade.findAll();
        ModuleVo vo = null;
        for (MstModule module : mstModules) {
            //if (!module.getModuleId().toString().endsWith("00")) {
            if (module.getIsDisplayed() == false) {
                vo = new ModuleVo();
                vo.setModuleID(module.getModuleId());
                vo.setModuleName(module.getModuleName());
                vo.setModuleDesc(module.getModuleDesc());
                moduleVoList.add(vo);
                //}
            }
        }
        return moduleVoList;
    }

    public ProfileVO getModuleProfileMapping(Long profileID) {
        logger.info("=== Inside Module Profile Mapping Service =======");
        try {
            MstProfile profile = mstProfileFacade.find(profileID);
            if (profile != null) {
                List<ModuleVo> ResponseList = new ArrayList<ModuleVo>();
                Collection<MapModuleProfile> profileModuleList = profile.getMapModuleProfileCollection();
                logger.info("============= map module profile List Size : " + profileModuleList.size());
                if (profileModuleList.size() > 0) {
                    for (MapModuleProfile mapModuleProfile : profileModuleList) {
                        ResponseList.add(new ModuleVo(mapModuleProfile.getMstModule().getModuleId(), mapModuleProfile.getMstModule().getModuleName(), mapModuleProfile.getMstModule().getModuleDesc()));
                    }
                }
                return (new ProfileVO(profile.getProfileId(), profile.getProfileName(), ResponseList));
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("==== Web Service getModuleProfileMapping error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Resp submitProfileModuleDtl(AddUpProfileModuleRequest req) {
        logger.info("======== Inside Creating/Updating Profile.  ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(req, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            MstProfile profile = mstProfileFacade.find(req.getProfileId());
            int deleteModule = commonDAO.deleteProfileModule(req.getProfileId());
            logger.info("=== deleted ProfileModule!!" + deleteModule);
            Set<Long> headerSet = new HashSet<Long>();
            Set<Long> subHeaderSet = new HashSet<Long>();

            MapModuleProfile mapModuleProfile = null;
            for (ModuleVo vo : req.getModuleList()) {
                logger.info("===== Module Id :" + vo.getModuleID());
//                if (vo.getModuleID() > 100 && vo.getModuleID() < 200) {
//                    headerSet.add(100l);
//                } else if (vo.getModuleID() > 200 && vo.getModuleID() < 300) {
//                    headerSet.add(200l);
//                } else if (vo.getModuleID() > 300 && vo.getModuleID() < 400) {
//                    headerSet.add(300l);
//                } else if (vo.getModuleID() > 400 && vo.getModuleID() < 500) {
//                    headerSet.add(400l);
//                } else if (vo.getModuleID() > 500 && vo.getModuleID() < 600) {
//                    headerSet.add(500l);
//                } else if (vo.getModuleID() > 600 && vo.getModuleID() < 700) {
//                    headerSet.add(600l);
//                }

                if (vo.getModuleID().toString().startsWith("1")) {
                    headerSet.add(100l);
                } else if (vo.getModuleID().toString().startsWith("2")) {
                    headerSet.add(200l);
                } else if (vo.getModuleID().toString().startsWith("3")) {
                    headerSet.add(300l);
                } else if (vo.getModuleID().toString().startsWith("4")) {
                    headerSet.add(400l);
                } else if (vo.getModuleID().toString().startsWith("5")) {
                    headerSet.add(500l);
                } else if (vo.getModuleID().toString().startsWith("6")) {
                    headerSet.add(600l);
                } else if (vo.getModuleID().toString().startsWith("7")) {
                    headerSet.add(700l);
                }

                if (vo.getModuleID().toString().startsWith("101")) {
                    subHeaderSet.add(101l);
                } else if (vo.getModuleID().toString().startsWith("104")) {
                    subHeaderSet.add(104l);
                } else if (vo.getModuleID().toString().startsWith("106")) {
                    subHeaderSet.add(106l);
                } else if (vo.getModuleID().toString().startsWith("201")) {
                    subHeaderSet.add(201l);
                } else if (vo.getModuleID().toString().startsWith("501")) {
                    subHeaderSet.add(501l);
                } else if (vo.getModuleID().toString().startsWith("502")) {
                    subHeaderSet.add(502l);
                } else if (vo.getModuleID().toString().startsWith("503")) {
                    subHeaderSet.add(503l);
                } else if (vo.getModuleID().toString().startsWith("301")) {
                    subHeaderSet.add(301l);
                }

                if (vo.getModuleID().toString().startsWith("5022")) {
                    subHeaderSet.add(5022l);
                }
                if (vo.getModuleID().toString().startsWith("5032")) {
                    subHeaderSet.add(5032l);
                }

                MstModule module = mstModuleFacade.find(vo.getModuleID());
                mapModuleProfile = new MapModuleProfile();
                mapModuleProfile.setMstModule(module);
                mapModuleProfile.setMstProfile(profile);
                mapModuleProfileFacade.create(mapModuleProfile);
            }
            Iterator<Long> headerIterator = headerSet.iterator();
            while (headerIterator.hasNext()) {
                Long headerId = headerIterator.next();
                MstModule module = mstModuleFacade.find(headerId);
                mapModuleProfile = new MapModuleProfile();
                mapModuleProfile.setMstModule(module);
                mapModuleProfile.setMstProfile(profile);
                mapModuleProfileFacade.create(mapModuleProfile);
            }

            Iterator<Long> subheaderIterator = subHeaderSet.iterator();
            while (subheaderIterator.hasNext()) {

                Long headerId = subheaderIterator.next();
                logger.info("----- Sub Header Set : " + headerId);
                MstModule module = mstModuleFacade.find(headerId);
                mapModuleProfile = new MapModuleProfile();
                mapModuleProfile.setMstModule(module);
                mapModuleProfile.setMstProfile(profile);
                mapModuleProfileFacade.create(mapModuleProfile);
            }
            return new Resp(RespCode.SUCCESS, "Profile & Modules mapped Successfully ! !");
        } catch (Exception e) {
            logger.error("==== Web Service submitProfileModuleDtl error : " + e.getMessage());
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error on submitProfileModuleDtl : " + e.getMessage());
        }
    }
}
