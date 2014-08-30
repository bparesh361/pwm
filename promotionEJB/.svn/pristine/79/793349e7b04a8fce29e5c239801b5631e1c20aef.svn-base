/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.service;

import com.fks.promo.common.CommonConstants;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.MailUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.entity.MapModuleProfile;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.MapUserDepartment;
import com.fks.promo.entity.MapuserMCHF1;
import com.fks.promo.entity.MapuserMCHF2;
import com.fks.promo.entity.MapuserMCHF3;
import com.fks.promo.entity.MapuserMCHF5;

import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstLocation;
import com.fks.promo.entity.MstProfile;
import com.fks.promo.entity.MstRole;
import com.fks.promo.entity.MstStore;
import com.fks.promo.entity.MstZone;
import com.fks.promo.facade.MapUserDepartmentFacade;
import com.fks.promo.facade.MapuserMCHF1Facade;
import com.fks.promo.facade.MapuserMCHF2Facade;
import com.fks.promo.facade.MapuserMCHF3Facade;
import com.fks.promo.facade.MapuserMCHF5Facade;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.facade.MstDepartmentFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.facade.MstLocationFacade;
import com.fks.promo.facade.MstRoleFacade;
import com.fks.promo.facade.MstStoreFacade;
import com.fks.promo.facade.MstZoneFacade;
import com.fks.promo.master.util.EmployeeUtil;
import com.fks.promo.master.util.UserMasterVO;
import com.fks.promo.master.vo.AuthResponse;
import com.fks.promo.master.vo.CreateUserMCHProfileDtlResp;
import com.fks.promo.master.vo.CreateUserMCHProfileReq;
import com.fks.promo.master.vo.EmployeeVo;
import com.fks.promo.master.vo.GetEmployeeDetailRequest;
import com.fks.promo.master.vo.GetEmployeeDetailResponse;
import com.fks.promo.master.vo.GetUserInfoResp;
import com.fks.promo.master.vo.GetUserWiseProfileResp;
import com.fks.promo.master.vo.MCHVo;
import com.fks.promo.master.vo.MCUserResp;
import com.fks.promo.master.vo.MCUserVO;
import com.fks.promo.master.vo.ModuleVo;
import com.fks.promo.master.vo.ProfileVO;
import com.fks.promo.master.vo.StoreVO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
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
public class UserMasterService {

    private static final Logger logger = Logger.getLogger(UserMasterService.class.getName());
    @EJB
    private MstEmployeeFacade employeeFacade;
    @EJB
    private CommonDAO commonDAO;
    @EJB
    private MstDepartmentFacade departmentFacade;
    @EJB
    private MapUserDepartmentFacade userDeptFacade;
    @EJB
    private MstStoreFacade mstStoreFacade;
    @EJB
    private MstLocationFacade mstLocationFacade;
    @EJB
    private MstZoneFacade mstZoneFacade;
    @EJB
    private MapuserMCHF1Facade mCHF1Facade;
    @EJB
    private MapuserMCHF2Facade mCHF2Facade;
    @EJB
    private MapuserMCHF3Facade mCHF3Facade;
    @EJB
    private MapuserMCHF5Facade mCHF5Facade;
    @EJB
    private MchFacade mchFacade;
    @EJB
    private MstRoleFacade roleFacade;

    public AuthResponse authorizeUser(Long Uname, String pwd) {
        try {
            logger.info("Request Received for Authenticating User Name with " + Uname.toString() + " pwd " + pwd);

            List<ModuleVo> moduleList = new ArrayList<ModuleVo>();
            List<ProfileVO> profileList = new ArrayList<ProfileVO>();
            EmployeeVo userVO = null;
            AuthResponse response = new AuthResponse();
            //MstEmployee employee = employeeFacade.find(Uname);
            List<MstEmployee> mstemployee = commonDAO.getEmployeeByEmpCode(Uname.toString());
            if (mstemployee == null || mstemployee.isEmpty()) {
                return (new AuthResponse(new Resp(RespCode.FAILURE, "Invalid UserName/Password.")));
            }
            for (MstEmployee employee : mstemployee) {
                if (employee == null) {
                    return new AuthResponse(new Resp(RespCode.FAILURE, "Invalid User : " + Uname + " Id!"));
                }
                //MstEmployee employee = employeeFacade.find(Uname);
                //  MstEmployee employee = commonDAO.getEmployeeByID(Uname);
                //         logger.info("---------------------------"+employee.getEmployeeName());
                if (employee != null) {
                    commonDAO.refresh(employee);
                }
                if (Uname == null || pwd == null || pwd.trim().isEmpty()) {
                    return new AuthResponse(new Resp(RespCode.FAILURE, "User Name Or Password is Empty"));
                }
                if (employee == null) {
                    return new AuthResponse(new Resp(RespCode.FAILURE, "User Name " + Uname + " is Not Correct."));
                }
                if (!pwd.trim().equals(employee.getEmpPassword())) {
                    return new AuthResponse(new Resp(RespCode.FAILURE, "Password Not Correct."));
                }
                if (employee.getIsBlocked() == true) {
                    return new AuthResponse(new Resp(RespCode.FAILURE, "User is Block."));
                }
                if (employee.getMstStore().getIsBlocked() == Boolean.TRUE) {
                    return new AuthResponse(new Resp(RespCode.FAILURE, "Store is Blocked. Please Contact Admin."));
                }
                userVO = new EmployeeVo();
                userVO.setContactno(employee.getContactNo().toString());
                userVO.setEmailId(employee.getEmailId());
                userVO.setEmpCode(employee.getEmpCode().toString());
                userVO.setEmpId(employee.getEmpId().toString());
                userVO.setEmpName(employee.getEmployeeName());
                userVO.setIsBlocked(employee.getIsBlocked());
                userVO.setReportingTo(employee.getReportingPersonName());
                userVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                userVO.setStoreId(employee.getMstStore().getMstStoreId());
                userVO.setTaskmanager(employee.getTaskManager());
                userVO.setRoleName(employee.getMstRole().getRoleName());
                userVO.setIsPasswordChanged(employee.getIsPasswordChange());

                MstStore store = employee.getMstStore();
                StoreVO storeVO = new StoreVO();
                storeVO.setCity(store.getCity());
                storeVO.setFormat(store.getFormatName());
                storeVO.setLocationID(store.getMstLocation().getLocationId());
                storeVO.setLocationName(store.getMstLocation().getLocationName());
                storeVO.setRegion(store.getRegionName());
                storeVO.setState(store.getState());
                storeVO.setStoreClass(store.getStoreClass());
                storeVO.setStoreDesc(store.getSiteDescription());
                storeVO.setStoreID(store.getMstStoreId());
                storeVO.setZoneId(store.getMstZone().getZoneId());
                storeVO.setZoneName(store.getMstZone().getZoneName());

                userVO.setStoreVO(storeVO);

                ModuleVo moduleVo = null;
                ProfileVO profileVo = null;
                if (employee.getMstRole() != null) {
                    MstRole mstRole = commonDAO.getRoleByID(employee.getMstRole().getMstRoleId());
                    Collection<MapRoleProfile> mapRoleProfiles = mstRole.getMapRoleProfileCollection();
                    for (MapRoleProfile mrp : mapRoleProfiles) {
                        MstProfile mstProfile = mrp.getMstProfile();

                        //Profile VO Added
                        profileVo = new ProfileVO();
                        profileVo.setProfileID(mstProfile.getProfileId());
                        profileVo.setProfileName(mstProfile.getProfileName());
                        profileList.add(profileVo);

                        Collection<MapModuleProfile> mapModuleProfiles = mstProfile.getMapModuleProfileCollection();
                        for (MapModuleProfile moduleProfile : mapModuleProfiles) {
                            moduleVo = new ModuleVo();
                            moduleVo.setModuleID(moduleProfile.getMstModule().getModuleId());
                            moduleVo.setModuleName(moduleProfile.getMstModule().getModuleName());
                            moduleList.add(moduleVo);
                        }
                        // userVO.setProfileName(mstProfile.getProfileName());
                    }
                }
            }
            response.setEmployeeVo(userVO);
            response.setModuleVos(moduleList);
            response.setProfileVos(profileList);
            response.setResp(new Resp(RespCode.SUCCESS, "Authentication Successfull."));
            return response;
        } catch (Exception ex) {
            logger.error("==== Web Service authorizeUser error : " + ex.getMessage());
            ex.printStackTrace();
            return new AuthResponse(new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }
    }

    public Resp createUser(EmployeeVo req) {
        logger.info("========== createUser ========== Role Id:" + req.getRoleId());
        logger.info("Contact No : " + req.getContactno() + " ---- Emp Code : " + req.getEmpCode());
        Resp resp = new Resp();
        try {
            MstRole mstRole = commonDAO.getRoleByID(Long.valueOf(req.getRoleId()));
            if (mstRole == null) {
                return new Resp(RespCode.FAILURE, "Role Id is incorrect !");
            }
            MstStore mstStore = commonDAO.getStoreByCode(req.getStoreId());
            if (mstStore == null) {
                return new Resp(RespCode.FAILURE, "Site Code is incorrect !");
            }
//            MstEmployee mstEmployee = employeeFacade.find(req.getEmpId());
//            if(mstEmployee!=null){
//                return new Resp(RespCode.FAILURE, "User "+req.getEmpId()+" already exist!");
//            }


            List<MstEmployee> mstemployee = commonDAO.getEmployeeByEmpCode(req.getEmpCode());
            for (MstEmployee employee : mstemployee) {
                if (employee != null) {
                    return new Resp(RespCode.FAILURE, "User with " + req.getEmpCode() + " already exist!");
                }
            }


            List<MstEmployee> mstemployeeNum = commonDAO.getEmployeeBycontactNum(req.getContactno());
            for (MstEmployee employee : mstemployeeNum) {
                if (employee != null) {
                    return new Resp(RespCode.FAILURE, "User with Contact Number : " + req.getContactno() + " already exist!");
                }
            }
            List<MstEmployee> mstemployeeEmail = commonDAO.getEmployeeByEmailId(req.getEmailId());
            for (MstEmployee employee : mstemployeeEmail) {
                if (employee != null) {
                    return new Resp(RespCode.FAILURE, "User with Email Id : " + req.getEmailId() + " already exist!");
                }
            }

            MstEmployee employee = new MstEmployee();
            UserMasterVO masterVO = EmployeeUtil.convertEmployeeTosubmit(req, mstRole, mstStore, employee);
            employee = masterVO.getEmployee();
            employeeFacade.create(employee);

            MapUserDepartment department1;
            if (req.getDeptIdList() != null && !req.getDeptIdList().isEmpty()) {
                for (Long lDeptId : req.getDeptIdList()) {
                    MstDepartment department = departmentFacade.find(lDeptId);
                    if (department == null) {
                        return new Resp(RespCode.FAILURE, "Department is incorrect !");
                    }
                    department1 = new MapUserDepartment();
                    department1.setMstDepartment(department);
                    department1.setMstEmployee(employee);
                    userDeptFacade.create(department1);
                    logger.info("----- User Dept Mapping Created ----");
                }
            }

            String emailAddr = employee.getEmailId();
            String empcode = employee.getEmpCode().toString();
            String empPass = employee.getEmpPassword();
            try {
                MailUtil.sendUserCreationMail(emailAddr, empcode, empPass, true);
                resp.setMsg("User Created Successfully. User Id is  : " + employee.getEmpCode());
                resp.setRespCode(RespCode.SUCCESS);
                resp.setRedirect(masterVO.isIsredirect());
                resp.setPk(employee.getEmpId());
                resp.setValue(employee.getEmployeeName());
                return resp;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(" === Error While Sending Mail === " + e.getMessage());
                resp.setMsg("Error while sending the Email Notification for User Creation.");
                resp.setRespCode(RespCode.FAILURE);
                return resp;
            }

        } catch (Exception e) {
            logger.error("==== Web Service createUser error : " + e.getMessage());
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + e.getMessage());
        }

    }

    public Resp forgotPassword(Long userName) {
        try {
            String password = null;
            String emailAddr = null, UserName = null;
            logger.info(" === Sending Forgot password for User Name " + userName);
            List<MstEmployee> mstemployee = commonDAO.getEmployeeByEmpCode(userName.toString());
            if (mstemployee.isEmpty()) {
                return new Resp(RespCode.FAILURE, "Incorrect User Id");
            }
            for (MstEmployee employee : mstemployee) {
                if (employee == null) {
                    return new Resp(RespCode.FAILURE, "Incorrect User Id");
                }
                String randomPass = CommonUtil.randomPasswordGenrates(8);
                employee.setEmpPassword(randomPass);
                employee.setIsPasswordChange(Boolean.TRUE);
                password = employee.getEmpPassword();
                emailAddr = employee.getEmailId();
                UserName = employee.getEmployeeName();
            }
            logger.info(" ==== Password re-set Successfully !");
            logger.info(" ==== Sending forgot password to employee " + emailAddr);
            try {
                MailUtil.sendforgetPasswordMail(emailAddr, password, UserName);
                return new Resp(RespCode.SUCCESS, "Password Sent Successfully at " + emailAddr);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(" === Error While Sending Mail === " + e.getMessage());
                return new Resp(RespCode.FAILURE, "Error while sending the Email Notification.");
            }

        } catch (Exception ex) {
            logger.error("==== Web Service forgotPassword error : " + ex.getMessage());
            ex.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }

    }

    public Resp changePassword(Long userName, String oldPassword, String newPassword) {
        try {
            logger.info(" ==== Changing Password for the User Name " + userName + " with new Password " + newPassword);
            MstEmployee employee = employeeFacade.find(userName);
            if (employee == null) {
                return new Resp(RespCode.FAILURE, "Incorrect User Id");
            }
            if (!oldPassword.equals(employee.getEmpPassword())) {
                return new Resp(RespCode.FAILURE, "Current Password Is Incorrect.");
            }
            employee.setEmpPassword(newPassword);
            employee.setIsPasswordChange(Boolean.FALSE);
            return new Resp(RespCode.SUCCESS, "Password Changed Successfully.");
        } catch (Exception ex) {
            logger.error("==== Web Service changePassword error : " + ex.getMessage());
            ex.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }
    }

    public Resp validateUserID(String userId) {
        logger.info("==== Inside Validate User Id Service  ==== " + userId);
        try {
            List<MstEmployee> mstemployee = commonDAO.getEmployeeByEmpCode(userId);
            for (MstEmployee employee : mstemployee) {
                if (employee != null) {
                    commonDAO.refresh(employee);
                    logger.info("employee role " + employee.getMstRole().getMstRoleId());
                    return new Resp(RespCode.FAILURE, "User " + userId + " already exist!");
                }
            }
            return new Resp(RespCode.SUCCESS, "Available.");
        } catch (Exception e) {
            logger.error("==== Web Service validateUserID error : " + e.getMessage());
            e.printStackTrace();
            return (new Resp(RespCode.FAILURE, "Error : " + e.getMessage()));
        }
    }

    public GetEmployeeDetailResponse searchEmployeeDetail(GetEmployeeDetailRequest request) {
        logger.info("========== Inside searchEmployeeDetail ======  ");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            int EmployeeCnt = 0;
            int empCounter = 0;

            EmployeeVo employeeVo = null;
            List<EmployeeVo> employeevoList = new ArrayList<EmployeeVo>();
            List<Long> deptIdlist = new ArrayList<Long>();
            switch (request.getEmployeeSearchEnum()) {
                case EMP_ROLE:
                    logger.info("==== Inside getting role wise employee Detail .." + request.getRoleId());
                    MstRole role = roleFacade.find(Long.valueOf(request.getRoleId()));
                    List<MstEmployee> downloadRoleEmployeeList = (List<MstEmployee>) role.getMstEmployeeCollection();
                    if (downloadRoleEmployeeList != null && downloadRoleEmployeeList.size() > 0) {
                        EmployeeCnt = downloadRoleEmployeeList.size();
                        for (MstEmployee employee : downloadRoleEmployeeList) {
                            employeeVo = new EmployeeVo();
                            employeeVo.setContactno(employee.getContactNo().toString());
                            employeeVo.setEmailId(employee.getEmailId());
                            employeeVo.setEmpCode(employee.getEmpCode().toString());
                            employeeVo.setEmpId(employee.getEmpId().toString());
                            employeeVo.setEmpName(employee.getEmployeeName());
                            employeeVo.setIsBlocked(employee.getIsBlocked());
                            boolean isExist = false;
                            StringBuilder deptExist = new StringBuilder("");
                            if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                                for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                    MstDepartment department = mapdept.getMstDepartment();
                                    if (department != null) {
                                        deptExist.append(department.getDeptName()).append(",");
                                        deptIdlist.add(department.getMstDeptId());
                                        isExist = true;
                                    }
                                }
                                if (isExist) {
                                    String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                    employeeVo.setDeptName(deptExistname);
                                }
                                employeeVo.setDeptIdList(deptIdlist);
                            }

                            employeeVo.setReportingTo(employee.getReportingPersonName());
                            employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                            employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                            employeeVo.setTaskmanager(employee.getTaskManager());
                            employeeVo.setUserID(employee.getUserId());
                            StoreVO storeVO = new StoreVO();
                            storeVO.setCity(employee.getMstStore().getCity());
                            storeVO.setFormat(employee.getMstStore().getFormatName());
                            storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                            storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                            storeVO.setRegion(employee.getMstStore().getRegionName());
                            storeVO.setState(employee.getMstStore().getState());
                            storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                            storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                            storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                            storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                            storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                            employeeVo.setStoreVO(storeVO);
                            employeevoList.add(employeeVo);
                        }
                    }
                    break;
                case ALL_DOWNLOAD:
                    logger.info("==== Inside getting all employee Detail ..");
                    List<MstEmployee> downloadEmployeeList = employeeFacade.findAll();
                    EmployeeCnt = commonDAO.getAllEmployeeCount();
                    for (MstEmployee employee : downloadEmployeeList) {
                        employeeVo = new EmployeeVo();
                        employeeVo.setContactno(employee.getContactNo().toString());
                        employeeVo.setEmailId(employee.getEmailId());
                        employeeVo.setEmpCode(employee.getEmpCode().toString());
                        employeeVo.setEmpId(employee.getEmpId().toString());
                        employeeVo.setEmpName(employee.getEmployeeName());
                        employeeVo.setIsBlocked(employee.getIsBlocked());
                        boolean isExist = false;
                        StringBuilder deptExist = new StringBuilder("");
                        if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                            for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                MstDepartment department = mapdept.getMstDepartment();
                                if (department != null) {
                                    deptExist.append(department.getDeptName()).append(",");
                                    deptIdlist.add(department.getMstDeptId());
                                    isExist = true;
                                }
                            }
                            if (isExist) {
                                String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                employeeVo.setDeptName(deptExistname);
                            }
                            employeeVo.setDeptIdList(deptIdlist);
                        }

                        employeeVo.setReportingTo(employee.getReportingPersonName());
                        employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                        employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                        employeeVo.setTaskmanager(employee.getTaskManager());
                        employeeVo.setUserID(employee.getUserId());
                        StoreVO storeVO = new StoreVO();
                        storeVO.setCity(employee.getMstStore().getCity());
                        storeVO.setFormat(employee.getMstStore().getFormatName());
                        storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                        storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                        storeVO.setRegion(employee.getMstStore().getRegionName());
                        storeVO.setState(employee.getMstStore().getState());
                        storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                        storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                        storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                        storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                        storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                        employeeVo.setStoreVO(storeVO);
                        employeevoList.add(employeeVo);
                    }
                    break;
                case ALL:
                    logger.info("==== Inside getting all employee Detail ..");
                    List<MstEmployee> employeeList = commonDAO.getAllEmployeeDetail(request.getStartIndex());
                    EmployeeCnt = commonDAO.getAllEmployeeCount();
                    for (MstEmployee employee : employeeList) {
                        employeeVo = new EmployeeVo();
                        employeeVo.setContactno(employee.getContactNo().toString());
                        employeeVo.setEmailId(employee.getEmailId());
                        employeeVo.setEmpCode(employee.getEmpCode().toString());
                        employeeVo.setEmpId(employee.getEmpId().toString());
                        employeeVo.setEmpName(employee.getEmployeeName());
                        employeeVo.setIsBlocked(employee.getIsBlocked());
                        boolean isExist = false;
                        StringBuilder deptExist = new StringBuilder("");
                        if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                            for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                MstDepartment department = mapdept.getMstDepartment();
                                if (department != null) {
                                    deptExist.append(department.getDeptName()).append(",");
                                    deptIdlist.add(department.getMstDeptId());
                                    isExist = true;
                                }
                            }
                            if (isExist) {
                                String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                employeeVo.setDeptName(deptExistname);
                            }
                            employeeVo.setDeptIdList(deptIdlist);
                        }

                        employeeVo.setReportingTo(employee.getReportingPersonName());
                        employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                        employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                        employeeVo.setTaskmanager(employee.getTaskManager());
                        employeeVo.setUserID(employee.getUserId());
                        StoreVO storeVO = new StoreVO();
                        storeVO.setCity(employee.getMstStore().getCity());
                        storeVO.setFormat(employee.getMstStore().getFormatName());
                        storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                        storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                        storeVO.setRegion(employee.getMstStore().getRegionName());
                        storeVO.setState(employee.getMstStore().getState());
                        storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                        storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                        storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                        storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                        storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                        employeeVo.setStoreVO(storeVO);
                        employeevoList.add(employeeVo);
                    }
                    break;
                case EMP_ID:
                    logger.info("======== Inside Search Based on Employee Code ==== Emp Code :" + request.getEmpName());
                    if (request.getEmpName() == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Please Enter Employee!"));
                    }
                    List<MstEmployee> employeeListByEmpCodeAndID = commonDAO.getEmployeeByEmpId(request.getEmpName());
                    EmployeeCnt = employeeListByEmpCodeAndID.size();
                    for (MstEmployee employee : employeeListByEmpCodeAndID) {
                        employeeVo = new EmployeeVo();
                        employeeVo.setContactno(employee.getContactNo().toString());
                        employeeVo.setEmailId(employee.getEmailId());
                        employeeVo.setEmpCode(employee.getEmpCode().toString());
                        employeeVo.setEmpId(employee.getEmpId().toString());
                        employeeVo.setEmpName(employee.getEmployeeName());

                        boolean isExist = false;
                        StringBuilder deptExist = new StringBuilder("");
                        if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                            for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                MstDepartment department = mapdept.getMstDepartment();
                                if (department != null) {
                                    deptExist.append(department.getDeptName()).append(",");
                                    deptIdlist.add(department.getMstDeptId());
                                    isExist = true;
                                }
                            }
                            if (isExist) {
                                String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                employeeVo.setDeptName(deptExistname);
                            }
                            employeeVo.setDeptIdList(deptIdlist);
                        }
                        employeeVo.setReportingTo(employee.getReportingPersonName());
                        employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                        employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                        employeeVo.setTaskmanager(employee.getTaskManager());
                        employeeVo.setUserID(employee.getUserId());
                        employeeVo.setIsBlocked(employee.getIsBlocked());
                        StoreVO storeVO = new StoreVO();
                        storeVO.setCity(employee.getMstStore().getCity());
                        storeVO.setFormat(employee.getMstStore().getFormatName());
                        storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                        storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                        storeVO.setRegion(employee.getMstStore().getRegionName());
                        storeVO.setState(employee.getMstStore().getState());
                        storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                        storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                        storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                        storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                        storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                        employeeVo.setStoreVO(storeVO);

                        employeevoList.add(employeeVo);
                    }
                    break;
                case EMP_SITE:
                    logger.info("======= Inside search based on Site Code ======= Site Code : " + request.getStoreCode());
                    if (request.getStoreCode() == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Please Enter Store Code!"));
                    }
                    MstStore mstStore = mstStoreFacade.find(request.getStoreCode());
                    if (mstStore == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Invalid Store Code :" + request.getStoreCode()));
                    }
                    List<MstEmployee> employeeListByStore = commonDAO.getEmployeeByStoreCode(request.getStoreCode().trim(), request.getStartIndex());
                    // List<MstEmployee> employeeSubList = CommonUtil.getSubList(request.getPage(), employeeListByStore);
                    EmployeeCnt = commonDAO.getEmployeeByStoreCount(request.getStoreCode().trim());
                    for (MstEmployee employee : employeeListByStore) {
                        employeeVo = new EmployeeVo();
                        employeeVo.setContactno(employee.getContactNo().toString());
                        employeeVo.setEmailId(employee.getEmailId());
                        employeeVo.setEmpCode(employee.getEmpCode().toString());
                        employeeVo.setEmpId(employee.getEmpId().toString());
                        employeeVo.setEmpName(employee.getEmployeeName());
                        employeeVo.setIsBlocked(employee.getIsBlocked());
                        boolean isExist = false;
                        StringBuilder deptExist = new StringBuilder("");
                        if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                            for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                MstDepartment department = mapdept.getMstDepartment();
                                if (department != null) {
                                    deptExist.append(department.getDeptName()).append(",");
                                    deptIdlist.add(department.getMstDeptId());
                                    isExist = true;
                                }
                            }
                            if (isExist) {
                                String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                employeeVo.setDeptName(deptExistname);
                            }
                            employeeVo.setDeptIdList(deptIdlist);
                        }
                        employeeVo.setReportingTo(employee.getReportingPersonName());
                        employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                        employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                        employeeVo.setTaskmanager(employee.getTaskManager());
                        employeeVo.setUserID(employee.getUserId());
                        StoreVO storeVO = new StoreVO();
                        storeVO.setCity(employee.getMstStore().getCity());
                        storeVO.setFormat(employee.getMstStore().getFormatName());
                        storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                        storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                        storeVO.setRegion(employee.getMstStore().getRegionName());
                        storeVO.setState(employee.getMstStore().getState());
                        storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                        storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                        storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                        storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                        storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                        employeeVo.setStoreVO(storeVO);

                        employeevoList.add(employeeVo);
                    }
                    break;
                case EMP_LOCATION:
                    logger.info("===== Inside Serch based on Emp location ====== ");
                    if (request.getLocationID() == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Please Enter Location !"));
                    }
                    MstLocation mstLocation = mstLocationFacade.find(Long.valueOf(request.getLocationID()));
                    if (mstLocation == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Invalid Location  :" + request.getLocationName()));
                    }

                    Collection<MstStore> mstStoresList = mstLocation.getMstStoreCollection();


                    StringBuilder storeIdList = new StringBuilder();
                    if (mstStoresList.size() > 0 && !mstStoresList.isEmpty()) {
                        for (MstStore ms : mstStoresList) {
                            storeIdList.append(ms.getMstStoreId().toString()).append(",");
                        }

                        String storeList = storeIdList.substring(0, storeIdList.lastIndexOf(","));
                        // logger.info("Store List : " + storeList);
                        //for (MstStore ms : mstStoresList) {
                        //Collection<MstEmployee> mstEmployeesList = ms.getMstEmployeeCollection();
                        List<MstEmployee> mstEmployeesListByLocation = commonDAO.getEmployeeByZoneLocation(storeList, request.getStartIndex());
                        empCounter = commonDAO.getEmployeeByZoneLocationCount(storeList);


                        //  List<MstEmployee> employeeSubListbyLocation = CommonUtil.getSubList(request.getPage(), (List) mstEmployeesList);
                        for (MstEmployee employee : mstEmployeesListByLocation) {
                            employeeVo = new EmployeeVo();
                            employeeVo.setContactno(employee.getContactNo().toString());
                            employeeVo.setEmailId(employee.getEmailId());
                            employeeVo.setEmpCode(employee.getEmpCode().toString());
                            employeeVo.setEmpId(employee.getEmpId().toString());
                            employeeVo.setEmpName(employee.getEmployeeName());
                            employeeVo.setIsBlocked(employee.getIsBlocked());
                            boolean isExist = false;
                            StringBuilder deptExist = new StringBuilder("");
                            if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                                for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                    MstDepartment department = mapdept.getMstDepartment();
                                    if (department != null) {
                                        deptExist.append(department.getDeptName()).append(",");
                                        deptIdlist.add(department.getMstDeptId());
                                        isExist = true;
                                    }
                                }
                                if (isExist) {
                                    String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                    employeeVo.setDeptName(deptExistname);
                                }
                                employeeVo.setDeptIdList(deptIdlist);
                            }
                            employeeVo.setReportingTo(employee.getReportingPersonName());
                            employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                            employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                            employeeVo.setTaskmanager(employee.getTaskManager());
                            employeeVo.setUserID(employee.getUserId());
                            StoreVO storeVO = new StoreVO();
                            storeVO.setCity(employee.getMstStore().getCity());
                            storeVO.setFormat(employee.getMstStore().getFormatName());
                            storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                            storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                            storeVO.setRegion(employee.getMstStore().getRegionName());
                            storeVO.setState(employee.getMstStore().getState());
                            storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                            storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                            storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                            storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                            storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                            employeeVo.setStoreVO(storeVO);
                            employeevoList.add(employeeVo);
                            // }
                        }
                    }
                    logger.info("location Counter :" + empCounter);
                    EmployeeCnt = empCounter;
                    break;
                case EMP_ZONE:
                    logger.info("==== inside Search Based on Emp Zone ========");
                    if (request.getZoneId() == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Please Enter Zone !"));
                    }
                    MstZone mstZone = mstZoneFacade.find(Long.valueOf(request.getZoneId()));
                    if (mstZone == null) {
                        return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Invalid Zone  :" + request.getZoneName()));
                    }

                    Collection<MstStore> mstStoresListbyZone = mstZone.getMstStoreCollection();

                    StringBuilder storeIdListZone = new StringBuilder();
                    if (mstStoresListbyZone.size() > 0 && !mstStoresListbyZone.isEmpty()) {
                        for (MstStore ms : mstStoresListbyZone) {
                            storeIdListZone.append(ms.getMstStoreId().toString()).append(",");
                        }

                        String storeListZone = storeIdListZone.substring(0, storeIdListZone.lastIndexOf(","));
                        //logger.info("Store List : " + storeListZone);
                        //  for (MstStore ms : mstStoresListbyZone) {
//                        Collection<MstEmployee> mstEmployeesList = ms.getMstEmployeeCollection();
//                        EmployeeCnt += mstEmployeesList.size();
//                        List<MstEmployee> employeeSubListbyLocation = CommonUtil.getSubList(request.getPage(), (List) mstEmployeesList);
                        List<MstEmployee> mstEmployeesListByZone = commonDAO.getEmployeeByZoneLocation(storeListZone, request.getStartIndex());
                        empCounter = commonDAO.getEmployeeByZoneLocationCount(storeListZone);
                        for (MstEmployee employee : mstEmployeesListByZone) {
                            employeeVo = new EmployeeVo();
                            employeeVo.setContactno(employee.getContactNo().toString());
                            employeeVo.setEmailId(employee.getEmailId());
                            employeeVo.setEmpCode(employee.getEmpCode().toString());
                            employeeVo.setEmpId(employee.getEmpId().toString());
                            employeeVo.setEmpName(employee.getEmployeeName());
                            employeeVo.setIsBlocked(employee.getIsBlocked());
                            boolean isExist = false;
                            StringBuilder deptExist = new StringBuilder("");
                            if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
                                for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                                    MstDepartment department = mapdept.getMstDepartment();
                                    if (department != null) {
                                        deptExist.append(department.getDeptName()).append(",");
                                        deptIdlist.add(department.getMstDeptId());
                                        isExist = true;
                                    }
                                }
                                if (isExist) {
                                    String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                                    employeeVo.setDeptName(deptExistname);
                                }
                                employeeVo.setDeptIdList(deptIdlist);
                            }
                            employeeVo.setReportingTo(employee.getReportingPersonName());
                            employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
                            employeeVo.setRoleName(employee.getMstRole().getRoleName());
//                        employeeVo.setStoreDesc(employee.getMstStore().getMstStoreId());
//                        employeeVo.setStoreId(employee.getMstStore().getSiteDescription());
                            employeeVo.setTaskmanager(employee.getTaskManager());
                            employeeVo.setUserID(employee.getUserId());
                            StoreVO storeVO = new StoreVO();
                            storeVO.setCity(employee.getMstStore().getCity());
                            storeVO.setFormat(employee.getMstStore().getFormatName());
                            storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
                            storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
                            storeVO.setRegion(employee.getMstStore().getRegionName());
                            storeVO.setState(employee.getMstStore().getState());
                            storeVO.setStoreClass(employee.getMstStore().getStoreClass());
                            storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
                            storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
                            storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
                            storeVO.setStoreID(employee.getMstStore().getMstStoreId());
                            employeeVo.setStoreVO(storeVO);

                            employeevoList.add(employeeVo);
                            //}
                        }
                    }
                    logger.info("Zone Counter :" + empCounter);
                    EmployeeCnt = empCounter;
                    break;
            }
            GetEmployeeDetailResponse detailResponse = new GetEmployeeDetailResponse();
            detailResponse.setEmployeeVoList(employeevoList);
            detailResponse.setEmployeeListCount(EmployeeCnt);
            detailResponse.setResp(new Resp(RespCode.SUCCESS, "Employee List size : " + detailResponse.getEmployeeVoList().size()));
            return detailResponse;
        } catch (Exception e) {
            logger.error("==== Web Service searchEmployeeDetail error : " + e.getMessage());
            e.printStackTrace();
            return new GetEmployeeDetailResponse(new Resp(RespCode.FAILURE, "Error : " + e.getMessage()));
        }
    }

    public List<EmployeeVo> getAllEmployee() {
        logger.info("======= Inside getAllEmployee Service  ==== ");
        EmployeeVo employeeVo = null;
        List<EmployeeVo> employeeVoList = new ArrayList<EmployeeVo>();
        List<MstEmployee> mstEmployeeList = employeeFacade.findAll();
        if (mstEmployeeList != null && !mstEmployeeList.isEmpty()) {
            for (MstEmployee employee : mstEmployeeList) {
                employeeVo = new EmployeeVo();
                employeeVo.setEmpId(employee.getEmpId().toString());
                employeeVo.setEmpCode(employee.getEmpCode().toString());
                employeeVo.setEmpName(employee.getEmployeeName());
                employeeVo.setIsBlocked(employee.getIsBlocked());
                employeeVoList.add(employeeVo);
            }
        }
        return employeeVoList;
    }

    public Resp deleteUser(List<EmployeeVo> listemployee) {
        logger.info("====== Inside Update User Service =====");
        try {
            if (listemployee == null && listemployee.isEmpty()) {
                return new Resp(RespCode.FAILURE, "No Employee to Update!");
            }
            boolean isExist = false;
            StringBuilder employeeExist = new StringBuilder("");
            logger.info("Emp List size : " + listemployee.size());
            for (EmployeeVo vo : listemployee) {
                List<MstEmployee> mstEmp = commonDAO.getEmployeeByEmpId(vo.getEmpId());
                logger.info("======= EmpId : " + vo.getEmpId());
                if (mstEmp.isEmpty()) {
                    isExist = true;
                    employeeExist.append(vo.getEmpId()).append(",");
                }
            }
            if (isExist) {
                String employeeExistMsg = employeeExist.substring(0, employeeExist.lastIndexOf(","));
                String msg = "Employee ID : " + employeeExistMsg + " not exists.";
                return (new Resp(RespCode.FAILURE, msg));
            }
            logger.info("========== Register Multiple Employee Validation With DB Completed.");
            for (EmployeeVo vo : listemployee) {
                List<MstEmployee> mstEmp = commonDAO.getEmployeeByEmpId(vo.getEmpId());
                logger.info("======= EmpId : " + vo.getEmpId());
                if (!mstEmp.isEmpty()) {
                    for (MstEmployee me : mstEmp) {
                        me.setIsBlocked(Boolean.TRUE);
                        logger.info("====== Emp Blocked : " + vo.getEmpId() + "Status :" + me.getIsBlocked());
                    }
                }
            }
            return new Resp(RespCode.SUCCESS, "User/s blocked Successfully !");
        } catch (Exception e) {
            logger.info("======== Exception in UpdateUser : " + e.getMessage());
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + e.getMessage());
        }
    }

    public Resp updateUserInfo(EmployeeVo req) {
        logger.info("------ Inside updateUserInfo --------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(req, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            Resp resp = new Resp();
            String emailAddr = null, empcode = null, empPass = null;
            MstRole mstRole = commonDAO.getRoleByID(Long.valueOf(req.getRoleId()));
            if (mstRole == null) {
                return new Resp(RespCode.FAILURE, "Role Id is incorrect !");
            }
            MstStore mstStore = commonDAO.getStoreByCode(req.getStoreId());
            if (mstStore == null) {
                return new Resp(RespCode.FAILURE, "Site Code is incorrect !");
            }
            MstEmployee employee = employeeFacade.find(Long.valueOf(req.getEmpId()));
            if (employee == null) {
                return new Resp(RespCode.FAILURE, "User not exist.");
            }
            List<MstEmployee> employeeCodeList = commonDAO.getEmployeeByEmpCode(req.getEmpCode());
            if (employeeCodeList != null && employeeCodeList.size() > 0) {
                for (MstEmployee empCode : employeeCodeList) {
                    if (!employee.getEmpId().equals(empCode.getEmpId())) {
                        return new Resp(RespCode.FAILURE, "Employee Code : " + req.getEmpCode() + " already exist.");
                    }
                }
            }

            List<MstEmployee> employeeNumList = commonDAO.getEmployeeBycontactNum(req.getContactno());
            if (employeeNumList != null && employeeNumList.size() > 0) {
                for (MstEmployee empNum : employeeNumList) {
                    if (!employee.getEmpId().equals(empNum.getEmpId())) {
                        return new Resp(RespCode.FAILURE, "Contact No : " + req.getContactno() + " already exist.");
                    }
                }
            }

            List<MstEmployee> employeeEmailList = commonDAO.getEmployeeByEmailId(req.getEmailId());
            if (employeeEmailList != null && employeeEmailList.size() > 0) {
                for (MstEmployee empEmail : employeeEmailList) {
                    if (!employee.getEmpId().equals(empEmail.getEmpId())) {
                        return new Resp(RespCode.FAILURE, "Email ID : " + req.getEmailId() + " already exist.");
                    }
                }
            }


//            List<MstEmployee> mstemployee = commonDAO.getEmployeeByEmpCode(req.getEmpCode());
//            for (MstEmployee employee : mstemployee) {
//            if (employee == null) {
//                return new Resp(RespCode.FAILURE, "User with " + req.getEmpCode() + " not exist!");
//            }

            UserMasterVO masterVO = EmployeeUtil.convertEmployeeTosubmit(req, mstRole, mstStore, employee);
            employee = masterVO.getEmployee();
            System.out.println("Emp Name : " + employee.getEmployeeName());
            Collection<MapUserDepartment> departmentslist = employee.getMapUserDepartmentCollection();
            if (departmentslist != null && !departmentslist.isEmpty()) {
                int i = commonDAO.deleteMapUserDept(employee.getEmpId());
                logger.info("Deleted dept count : " + i);
            }
            MapUserDepartment department1;
            if (req.getDeptIdList() != null && !req.getDeptIdList().isEmpty()) {
                for (Long lDeptId : req.getDeptIdList()) {
                    MstDepartment department = departmentFacade.find(lDeptId);
                    if (department == null) {
                        return new Resp(RespCode.FAILURE, "Department is incorrect !");
                    }
                    department1 = new MapUserDepartment();
                    department1.setMstDepartment(department);
                    department1.setMstEmployee(employee);
                    userDeptFacade.create(department1);
                    logger.info("----- User Dept Mapping Created ----");
                }
            }

            emailAddr = employee.getEmailId();
            empcode = employee.getEmpCode().toString();
            empPass = employee.getEmpPassword();

            resp.setMsg("User Updated Successfully. User Id is  : " + employee.getEmpCode());
            resp.setRespCode(RespCode.SUCCESS);
            resp.setRedirect(masterVO.isIsredirect());
            resp.setPk(employee.getEmpId());
            resp.setValue(employee.getEmployeeName());
//            }

            try {
                MailUtil.sendUserCreationMail(emailAddr, empcode, empPass, false);
                return resp;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(" === Error While Sending Mail === " + e.getMessage());
                resp.setMsg("Error while sending the Email Notification for User updation.");
                resp.setRespCode(RespCode.FAILURE);
                return resp;
            }
        } catch (Exception e) {
            logger.error("==== Web Service updateUserInfo error : " + e.getMessage());
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error : " + e.getMessage());
        }
    }

    public GetUserWiseProfileResp getUserWiseProfileDtl(String empID) {

        try {
            List<ProfileVO> listProfile = new ArrayList<ProfileVO>();
            ProfileVO profileVO = null;
            List<MstEmployee> employeeList = commonDAO.getEmployeeByEmpId(empID);
            if (employeeList.isEmpty()) {
                return new GetUserWiseProfileResp(new Resp(RespCode.FAILURE, "Invalid Employee :" + empID));
            }
            for (MstEmployee me : employeeList) {
                Collection<MapRoleProfile> mapRoleProfileList = me.getMstRole().getMapRoleProfileCollection();
                if (mapRoleProfileList.isEmpty()) {
                    return new GetUserWiseProfileResp(new Resp(RespCode.FAILURE, "No Profile Mapped with Employee :" + empID));
                }
                for (MapRoleProfile mrp : mapRoleProfileList) {
                    profileVO = new ProfileVO();
                    profileVO.setProfileID(mrp.getMstProfile().getProfileId());
                    profileVO.setProfileName(mrp.getMstProfile().getProfileName());
                    listProfile.add(profileVO);
                }
            }
//            List<MCHVo> listUserMCHF1 = new ArrayList<MCHVo>();
//            List<MCHVo> listUserMCHF2 = new ArrayList<MCHVo>();
//            List<MCHVo> listUserMCHF3 = new ArrayList<MCHVo>();
//            List<MCHVo> listUserMCHF4 = new ArrayList<MCHVo>();
//            MCHVo mchv = null;
//            for (MstEmployee me : employeeList) {
//                Collection<MapuserMCHF1> mapuserMCHF1s = me.getMapuserMCHF1Collection();
//                if (!mapuserMCHF1s.isEmpty()) {
//                    for (MapuserMCHF1 hF1 : mapuserMCHF1s) {
//                        mchv = new MCHVo();
//                        mchv.setMCCode(hF1.getMch().getMcCode());
//                        mchv.setMCName(hF1.getMch().getMcName());
//                        listUserMCHF1.add(mchv);
//                    }
//                }
//                Collection<MapuserMCHF2> mapuserMCHF2s = me.getMapuserMCHF2Collection();
//                if (!mapuserMCHF2s.isEmpty()) {
//                    for (MapuserMCHF2 hF : mapuserMCHF2s) {
//                        mchv = new MCHVo();
//                        mchv.setMCCode(hF.getMch().getMcCode());
//                        mchv.setMCName(hF.getMch().getMcName());
//                        listUserMCHF2.add(mchv);
//                    }
//                }
//                Collection<MapuserMCHF3> mapuserMCHF3s = me.getMapuserMCHF3Collection();
//                if (!mapuserMCHF3s.isEmpty()) {
//                    for (MapuserMCHF3 hF : mapuserMCHF3s) {
//                        mchv = new MCHVo();
//                        mchv.setMCCode(hF.getMch().getMcCode());
//                        mchv.setMCName(hF.getMch().getMcName());
//                        listUserMCHF3.add(mchv);
//                    }
//                }
//                Collection<MapuserMCHF4> mapuserMCHF4s = me.getMapuserMCHF4Collection();
//                if (!mapuserMCHF4s.isEmpty()) {
//                    for (MapuserMCHF4 hF : mapuserMCHF4s) {
//                        mchv = new MCHVo();
//                        mchv.setMCCode(hF.getMch().getMcCode());
//                        mchv.setMCName(hF.getMch().getMcName());
//                        listUserMCHF4.add(mchv);
//                    }
//                }
//            }
            GetUserWiseProfileResp resp = new GetUserWiseProfileResp();
//            resp.setListUserMchF1(listUserMCHF1);
//            resp.setListUserMchF2(listUserMCHF2);
//            resp.setListUserMchF3(listUserMCHF3);
//            resp.setListUserMchF4(listUserMCHF4);
            resp.setProfileVOLIst(listProfile);
            resp.setResp(new Resp(RespCode.SUCCESS, "success"));
            return resp;
            //return new GetUserWiseProfileResp(new Resp(RespCode.SUCCESS, "success"), listProfile);
        } catch (Exception e) {
            logger.info("Exception in getUserWiseProfileDtl service : " + e.getMessage());
            return new GetUserWiseProfileResp(new Resp(RespCode.FAILURE, "Error : " + e.getMessage()));
        }
    }

    public GetUserWiseProfileResp getUserAndProfileWiseMchDtl(String empID, String profileId) {
        logger.info("===== Inside getUserAndProfileWiseMchDtl service ===== Emp Id : " + empID + "======= Profile Id :" + profileId);
        try {
            List<MCHVo> listUserMCHF1 = new ArrayList<MCHVo>();
            List<MCHVo> listUserMCHF2 = new ArrayList<MCHVo>();
            List<MCHVo> listUserMCHF3 = new ArrayList<MCHVo>();
            List<MCHVo> listUserMCHF5 = new ArrayList<MCHVo>();
            GetUserWiseProfileResp resp = new GetUserWiseProfileResp();
            MCHVo mchv = null;
            List<MstEmployee> employeeList = commonDAO.getEmployeeByEmpId(empID);
            if (employeeList.isEmpty()) {
                return new GetUserWiseProfileResp(new Resp(RespCode.FAILURE, "Invalid Employee :" + empID));
            }
            for (MstEmployee me : employeeList) {
                if (profileId.equalsIgnoreCase("2")) {
                    Collection<MapuserMCHF1> mapuserMCHF1s = me.getMapuserMCHF1Collection();
                    if (!mapuserMCHF1s.isEmpty()) {
                        for (MapuserMCHF1 hF1 : mapuserMCHF1s) {
                            mchv = new MCHVo();
                            mchv.setMCCode(hF1.getMch().getMcCode());
                            mchv.setMCName(hF1.getMch().getMcName());
                            listUserMCHF1.add(mchv);
                        }
                    }
                    resp.setListUserMchF1(listUserMCHF1);
                }
                if (profileId.equalsIgnoreCase("3")) {
                    Collection<MapuserMCHF2> mapuserMCHF2s = me.getMapuserMCHF2Collection();
                    if (!mapuserMCHF2s.isEmpty()) {
                        for (MapuserMCHF2 hF : mapuserMCHF2s) {
                            mchv = new MCHVo();
                            mchv.setMCCode(hF.getMch().getMcCode());
                            mchv.setMCName(hF.getMch().getMcName());
                            listUserMCHF2.add(mchv);
                        }
                    }
                    resp.setListUserMchF2(listUserMCHF2);
                }
                if (profileId.equalsIgnoreCase("4")) {
                    Collection<MapuserMCHF3> mapuserMCHF3s = me.getMapuserMCHF3Collection();
                    if (!mapuserMCHF3s.isEmpty()) {
                        for (MapuserMCHF3 hF : mapuserMCHF3s) {
                            mchv = new MCHVo();
                            mchv.setMCCode(hF.getMch().getMcCode());
                            mchv.setMCName(hF.getMch().getMcName());
                            listUserMCHF3.add(mchv);
                        }
                    }
                    resp.setListUserMchF3(listUserMCHF3);
                } //
                if (profileId.equalsIgnoreCase("6")) {
                    Collection<MapuserMCHF5> mapuserMCHF4s = me.getMapuserMCHF5Collection();
                    if (!mapuserMCHF4s.isEmpty()) {
                        for (MapuserMCHF5 hF : mapuserMCHF4s) {
                            mchv = new MCHVo();
                            mchv.setMCCode(hF.getMch().getMcCode());
                            mchv.setMCName(hF.getMch().getMcName());
                            listUserMCHF5.add(mchv);
                        }
                    }
                    resp.setListUserMchF5(listUserMCHF5);
                }

            }


            resp.setResp(new Resp(RespCode.SUCCESS, "success"));
            return resp;
        } catch (Exception e) {
            logger.info("Exception in getUserWiseProfileDtl service : " + e.getMessage());
            return new GetUserWiseProfileResp(new Resp(RespCode.FAILURE, "Error : " + e.getMessage()));
        }
    }

    public CreateUserMCHProfileDtlResp submitUserMCHDetail(CreateUserMCHProfileReq req) {
        logger.info("-------- Inside submitUserMCHDetail ------");
        StringWriter buffer = new StringWriter();
        JAXB.marshal(req, buffer);
        logger.info(" PAYLOAD ::::::\n " + buffer);
        try {
            List<MstEmployee> employeeList = commonDAO.getEmployeeByEmpId(req.getEmpID());
            if (employeeList.isEmpty()) {
                return new CreateUserMCHProfileDtlResp(new Resp(RespCode.FAILURE, "Invalid Employee :" + req.getEmpID()));
            }
            StringBuilder sb = new StringBuilder();
            boolean isExist = false;
            //   int returnCde = 0;
            sb.append("MC Code");
            sb.append("\n");
            if (req.getMchvoList().size() > 0 && !req.getMchvoList().isEmpty()) {
                for (MCHVo vo : req.getMchvoList()) {
                    logger.info("MC Code : " + vo.getMCCode());
                    Mch checkMCH = commonDAO.getMCHByCode(vo.getMCCode());
                    if (checkMCH == null) {
                        sb.append(vo.getMCCode());
                        sb.append(",");
                        sb.append("INVALID MC-CODE !");
                        sb.append("\n");
                        isExist = true;
                    } else if (checkMCH.getIsBlocked() == Boolean.TRUE) {
                        sb.append(vo.getMCCode());
                        sb.append(",");
                        sb.append("MC-CODE is Blocked !");
                        sb.append("\n");
                        isExist = true;
                    } else {
                        sb.append(vo.getMCCode());
                        sb.append("\n");
                    }
                }
            } else {
                return new CreateUserMCHProfileDtlResp(new Resp(RespCode.FAILURE, "User MC Code List is Empty. "));
            }
            logger.info("SB : " + sb.toString());
            String errorfiledata = sb.toString();
            if (isExist) {
                try {
                    InputStream is = (InputStream) CategoryMCHService.class.getResourceAsStream(CommonConstants.PROPERTY);
                    Properties pro = new Properties();
                    pro.load(is);

                    // logger.info("========= error file contain : \n" + errorfiledata);
                    //String dirName = ("c:\\promotion\\error\\");
                    String dirName = pro.getProperty("errorFile");
                    String aosFileName = ("User_" + req.getEmpID() + "_MCH_error_file.csv");
                    File aosFileDir = new File(dirName);
                    FileWriter fileWriter = new FileWriter(dirName + aosFileName);
                    BufferedWriter buffWriter = new BufferedWriter(fileWriter);
                    if (!aosFileDir.exists()) {
                        aosFileDir.mkdir();
                    } else if (!aosFileDir.isDirectory()) {
                        System.out.println("The Directory does not exist");
                        //  returnCde = 1;
                    }
                    File aosFile = new File(dirName, aosFileName);
                    aosFile.createNewFile();
                    buffWriter.write(errorfiledata);
                    buffWriter.flush();
                    buffWriter.close();
                    return new CreateUserMCHProfileDtlResp(
                            new Resp(RespCode.FAILURE, "failure"), dirName + aosFileName);
                } catch (Exception e) {
                    logger.info("Exception while writing error file  :");
                    e.printStackTrace();

                }
            }
            for (MstEmployee me : employeeList) {
                if (req.getProfileId() == null ? "2" == null : req.getProfileId().equals("2")) {
                    logger.info("======= Profile Id is 2 : F1");
                    if (!me.getMapuserMCHF1Collection().isEmpty()) {
                        //logger.info("======= Delete Mch F1 mapping =====");
                        int deleteMChF1 = commonDAO.deleteMapUserMCHF1(req.getEmpID());
                        logger.info("=== deleted User MCH F1!!" + deleteMChF1);
                    }
                }
                if (req.getProfileId() == null ? "3" == null : req.getProfileId().equals("3")) {
                    logger.info("======= Profile Id is 3 : F2");
                    if (!me.getMapuserMCHF2Collection().isEmpty()) {
                        //logger.info("======= Delete Mch F2 mapping =====");
                        int deleteMChF2 = commonDAO.deleteMapUserMCHF2(req.getEmpID());
                        logger.info("=== deleted User MCH F2!!" + deleteMChF2);
                    }
                }
                if (req.getProfileId() == null ? "4" == null : req.getProfileId().equals("4")) {
                    logger.info("======= Profile Id is 4 : F3");
                    if (!me.getMapuserMCHF3Collection().isEmpty()) {
                        //  logger.info("======= Delete Mch F3 mapping =====");
                        int deleteMChF3 = commonDAO.deleteMapUserMCHF3(req.getEmpID());
                        logger.info("=== deleted User MCH F3!!" + deleteMChF3);
                    }
                }

                if (req.getProfileId() == null ? "6" == null : req.getProfileId().equals("6")) {
                    logger.info("======= Profile Id is 6 : F5");
                    if (!me.getMapuserMCHF5Collection().isEmpty()) {
                        //  logger.info("======= Delete Mch F3 mapping =====");
                        int deleteMChF5 = commonDAO.deleteMapUserMCHF5(req.getEmpID());
                        logger.info("=== deleted User MCH F4!!" + deleteMChF5);
                    }
                }
                for (MCHVo vo : req.getMchvoList()) {
                    Mch checkMCH = commonDAO.getMCHByCode(vo.getMCCode());
                    if (checkMCH != null) {
                        if (req.getProfileId() == null ? "2" == null : req.getProfileId().equals("2")) {
                            logger.info("======= Profile Id is 2 : F1");
                            MapuserMCHF1 f1 = new MapuserMCHF1();
                            f1.setMch(checkMCH);
                            f1.setMstEmployee(me);
                            mCHF1Facade.create(f1);
                        }
                        if (req.getProfileId() == null ? "3" == null : req.getProfileId().equals("3")) {
                            logger.info("======= Profile Id is 3 : F2");
                            MapuserMCHF2 f2 = new MapuserMCHF2();
                            f2.setMch(checkMCH);
                            f2.setMstEmployee(me);
                            mCHF2Facade.create(f2);
                        }
                        if (req.getProfileId() == null ? "4" == null : req.getProfileId().equals("4")) {
                            logger.info("======= Profile Id is 4 : F3");
                            MapuserMCHF3 f3 = new MapuserMCHF3();
                            f3.setMch(checkMCH);
                            f3.setMstEmployee(me);
                            mCHF3Facade.create(f3);
                        }
                        if (req.getProfileId() == null ? "6" == null : req.getProfileId().equals("6")) {
                            logger.info("======= Profile Id is 6 : F5");
                            MapuserMCHF5 f5 = new MapuserMCHF5();
                            f5.setMch(checkMCH);
                            f5.setMstEmployee(me);
                            mCHF5Facade.create(f5);
                        }
                    }
                    logger.info("USer MC-Mapping Created Successfully !");
                }
            }
            CreateUserMCHProfileDtlResp resp = new CreateUserMCHProfileDtlResp();
            resp.setResp(new Resp(RespCode.SUCCESS, "User MC-Mapping Created Successfully !"));
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception in submitUserMCHDetail service : " + e.getMessage());
            return new CreateUserMCHProfileDtlResp(new Resp(RespCode.FAILURE, "Error : " + e.getMessage()));
        }
    }

    public GetUserInfoResp getUserDetailBasedOnId(Long empId) {
        logger.info("======== Inside getUserDetailBasedOnId : Emp Id : " + empId);
        EmployeeVo employeeVo = new EmployeeVo();
        MstEmployee employee = employeeFacade.find(empId);
        if (employee == null) {
            return new GetUserInfoResp(new Resp(RespCode.FAILURE, "Invalid User Id."));
        }
        employeeVo = EmployeeUtil.convertEmployee(employee);
        return new GetUserInfoResp(new Resp(RespCode.SUCCESS, "success"), employeeVo);
    }

    public MCUserResp getMCUsers(List<String> mcCodeList) {
        logger.info("---------- Inside getMCUsers  ");
        try {
            //List<Mch> mchList = new ArrayList<Mch>();
            List<MCUserVO> userMCList = new ArrayList<MCUserVO>();
//            Collection<MapuserMCHF1> f1UserList = null;
            Collection<MapuserMCHF2> f2UserList = null;
            Collection<MapuserMCHF3> f3UserList = null;
            for (String mcCode : mcCodeList) {
                Mch mch = mchFacade.find(mcCode);
                if (mch == null) {
                    MCUserVO userVo = new MCUserVO();
                    userVo.setMccode(mcCode);
                    userVo.setMcStatus("Invalid Mc");
                    userMCList.add(userVo);
                } else {
                    // mchList.add(mch);

                    logger.info("-------------- All MC Validated.");

                    //for (Mch mch : mchList) {
//                f1UserList = mch.getMapuserMCHF1Collection();
//                if (f1UserList != null && f1UserList.size() > 0) {
//                    for (MapuserMCHF1 f1 : f1UserList) {
//                        MCUserVO userVo = new MCUserVO();
//                        userVo.setEmpCode(f1.getMstEmployee().getEmpCode().toString());
//                        userVo.setEmpId(f1.getMstEmployee().getEmpId());
//                        userVo.setEmpName(f1.getMstEmployee().getEmployeeName());
//                        userVo.setIsF1User(true);
//                        userVo.setIsF2User(false);
//                        userVo.setIsF3User(false);
//                        userMCList.add(userVo);
//                    }
//                }
                    f2UserList = mch.getMapuserMCHF2Collection();
                    if (f2UserList != null && f2UserList.size() > 0) {
                        for (MapuserMCHF2 f2 : f2UserList) {
                            MCUserVO userVo = new MCUserVO();
                            userVo.setEmpCode(f2.getMstEmployee().getEmpCode().toString());
                            userVo.setEmpId(f2.getMstEmployee().getEmpId());
                            userVo.setEmpName(f2.getMstEmployee().getEmployeeName());
                            userVo.setEmpContactNo(f2.getMstEmployee().getContactNo().toString());
                            userVo.setEmpEmailId(f2.getMstEmployee().getEmailId());
                            if (f2.getMstEmployee().getMstStore() != null) {
                                userVo.setZone(f2.getMstEmployee().getMstStore().getMstZone().getZoneName());
                            }
                            userVo.setMccode(mch.getMcCode());
                            userVo.setIsF1User(false);
                            userVo.setIsF2User(true);
                            userVo.setIsF3User(false);
                            userMCList.add(userVo);
                        }
                    }
                    f3UserList = mch.getMapuserMCHF3Collection();
                    if (f3UserList != null && f3UserList.size() > 0) {
                        for (MapuserMCHF3 f3 : f3UserList) {
                            MCUserVO userVo = new MCUserVO();
                            userVo.setEmpCode(f3.getMstEmployee().getEmpCode().toString());
                            userVo.setEmpId(f3.getMstEmployee().getEmpId());
                            userVo.setEmpName(f3.getMstEmployee().getEmployeeName());
                            userVo.setEmpContactNo(f3.getMstEmployee().getContactNo().toString());
                            userVo.setEmpEmailId(f3.getMstEmployee().getEmailId());
                            if (f3.getMstEmployee().getMstStore() != null) {
                                userVo.setZone(f3.getMstEmployee().getMstStore().getMstZone().getZoneName());
                            }
                            userVo.setMccode(mch.getMcCode());
                            userVo.setIsF1User(false);
                            userVo.setIsF2User(false);
                            userVo.setIsF3User(true);
                            userMCList.add(userVo);
                        }
                    }
                }
                // }
            }
            if (userMCList.size() > 0) {
                return new MCUserResp(new Resp(RespCode.SUCCESS, "MC User List."), userMCList);
            } else {
                return new MCUserResp(new Resp(RespCode.FAILURE, "No MC user Found"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new MCUserResp(new Resp(RespCode.FAILURE, "Error : " + ex.getMessage()));
        }
    }

    public void getMCHUsersDownlodFile(List<String> mcCodeList) {
        logger.info("---------- Inside getMCUsers  ");
        try {

            List<MCUserVO> userMCList = new ArrayList<MCUserVO>();
            Collection<MapuserMCHF2> f2UserList = null;
            Collection<MapuserMCHF3> f3UserList = null;
            for (String mcCode : mcCodeList) {
                Mch mch = mchFacade.find(mcCode);
                if (mch == null) {
                    MCUserVO userVo = new MCUserVO();
                    userVo.setMccode(mcCode);
                    userVo.setMcStatus("Invalid Mc");
                    userMCList.add(userVo);
                } else {
                    logger.info("-------------- All MC Validated.");
                    f2UserList = mch.getMapuserMCHF2Collection();
                    if (f2UserList != null && f2UserList.size() > 0) {
                        for (MapuserMCHF2 f2 : f2UserList) {
                            MCUserVO userVo = new MCUserVO();
                            userVo.setEmpCode(f2.getMstEmployee().getEmpCode().toString());
                            userVo.setEmpId(f2.getMstEmployee().getEmpId());
                            userVo.setEmpName(f2.getMstEmployee().getEmployeeName());
                            userVo.setEmpContactNo(f2.getMstEmployee().getContactNo().toString());
                            userVo.setEmpEmailId(f2.getMstEmployee().getEmailId());
                            if (f2.getMstEmployee().getMstStore() != null) {
                                userVo.setZone(f2.getMstEmployee().getMstStore().getMstZone().getZoneName());
                            }
                            userVo.setMccode(mch.getMcCode());
                            userVo.setIsF1User(false);
                            userVo.setIsF2User(true);
                            userVo.setIsF3User(false);
                            userMCList.add(userVo);
                        }
                    }
                    f3UserList = mch.getMapuserMCHF3Collection();
                    if (f3UserList != null && f3UserList.size() > 0) {
                        for (MapuserMCHF3 f3 : f3UserList) {
                            MCUserVO userVo = new MCUserVO();
                            userVo.setEmpCode(f3.getMstEmployee().getEmpCode().toString());
                            userVo.setEmpId(f3.getMstEmployee().getEmpId());
                            userVo.setEmpName(f3.getMstEmployee().getEmployeeName());
                            userVo.setEmpContactNo(f3.getMstEmployee().getContactNo().toString());
                            userVo.setEmpEmailId(f3.getMstEmployee().getEmailId());
                            if (f3.getMstEmployee().getMstStore() != null) {
                                userVo.setZone(f3.getMstEmployee().getMstStore().getMstZone().getZoneName());
                            }
                            userVo.setMccode(mch.getMcCode());
                            userVo.setIsF1User(false);
                            userVo.setIsF2User(false);
                            userVo.setIsF3User(true);
                            userMCList.add(userVo);
                        }
                    }
                }
                StringBuffer sb = new StringBuffer();
                sb.append("MC Code").append(",");
                sb.append("Name").append(",");
                sb.append("Email Id").append(",");
                sb.append("Zone").append(",");
                sb.append("Approver From").append(",");
                sb.append("\n");
                for (MCUserVO vo : userMCList) {

                    sb.append(vo.getMccode()).append(",");
                    //sb.append(vo.getEmpCode()).append(",");
                    if (vo.getEmpName() != null) {
                        sb.append(vo.getEmpName()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.getEmpEmailId() != null) {
                        sb.append(vo.getEmpEmailId()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.getZone() != null) {
                        sb.append(vo.getZone()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.isIsF2User()) {
                        sb.append("L 1").append(",");
                    } else if (vo.isIsF3User()) {
                        sb.append("L 2").append(",");
                    } else {
                        sb.append("-").append(",");
                    }
                    if (vo.getMcStatus() != null) {
                        sb.append(vo.getMcStatus()).append(",");
                    } else {
                        sb.append("-").append(",");
                    }

                    sb.append("\n");
                }
                String dirName = "C://";
                System.out.println("Dir Name : " + dirName);
                String aosFileName = "MC_WISE_DATA_.csv";
                String filePath = dirName + aosFileName;
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                File statusFile = new File(filePath);
                statusFile.createNewFile();

                System.out.println("File Path : " + filePath);

                writer.write(sb.toString());
                writer.flush();
                if (writer != null) {
                    writer.close();
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public static void main(String[] args) {
    }
}
