/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.service;

import com.fks.promo.common.CommonConstants;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.entity.MapuserMCHF1;
import com.fks.promo.entity.MapuserMCHF2;
import com.fks.promo.entity.MapuserMCHF3;
import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstLocation;
import com.fks.promo.facade.MchFacade;
import com.fks.promo.facade.MstEmployeeFacade;
import com.fks.promo.master.vo.GetCategoryResp;
import com.fks.promo.master.vo.MCHVo;
import com.fks.promo.master.vo.SubmitWorkflowResp;
import com.fks.promo.master.vo.UserMCSubCategoryResp;
import com.fks.promo.master.vo.getCategorySubCategoryDtlReq;
import com.fks.reports.excel.UserMCHExcel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
public class CategoryMCHService {

    private static final Logger logger = Logger.getLogger(CategoryMCHService.class.getName());
    @EJB
    private MchFacade mchFacade;
    @EJB
    private MstEmployeeFacade empFacade;
    @EJB
    private CommonDAO commonDAO;
    @EJB
    private UserMCHExcel userMChExcel;

    public UserMCSubCategoryResp getCategoryList() {
        logger.info("======== Inside getUserCategoryList =========");
        List<MCHVo> categoryList = new ArrayList<MCHVo>();
        Collection<Mch> mchList = mchFacade.findAll();
        Set<String> categorySet = new HashSet<String>();
        if (mchList != null && mchList.size() > 0) {
            for (Mch mc : mchList) {
                if (categorySet.add(mc.getCategoryName())) {
                    MCHVo vo = new MCHVo();
                    vo.setCategoryName(mc.getCategoryName());
                    categoryList.add(vo);
                }
            }
        }
        if (categoryList != null && categoryList.size() > 0) {
            categorySet.clear();
            return new UserMCSubCategoryResp(new Resp(RespCode.SUCCESS, "Category List."), categoryList);
        } else {
            return new UserMCSubCategoryResp(new Resp(RespCode.FAILURE, "No Category List Found."));
        }
    }

    public UserMCSubCategoryResp getUserSubcategoryList(Long empId, boolean isInitiator, boolean isL1, boolean isL2) {
        logger.info("======== Inside getUserSubcategoryList =========");
        logger.info("---- Emp Id : " + empId + "    -------- isL1 : " + isL1);
        MstEmployee emp = empFacade.find(empId);
        if (emp != null) {

            Set<String> subCategorySet = new HashSet<String>();
            if (isInitiator) {
                List<MapuserMCHF1> userMchF1List = (List<MapuserMCHF1>) emp.getMapuserMCHF1Collection();
                if (userMchF1List != null && userMchF1List.size() > 0) {
                    for (MapuserMCHF1 f1MCH : userMchF1List) {
                        Mch mch = f1MCH.getMch();
                        subCategorySet.add(mch.getSubCategoryName());
                    }
                }
            } else if (isL1) {
                List<MapuserMCHF2> userMchF2List = (List<MapuserMCHF2>) emp.getMapuserMCHF2Collection();
                if (userMchF2List != null && userMchF2List.size() > 0) {
                    for (MapuserMCHF2 f2MCH : userMchF2List) {
                        Mch mch = f2MCH.getMch();
                        subCategorySet.add(mch.getSubCategoryName());
                    }
                }
            } else if (isL2) {
                List<MapuserMCHF3> userMchF3List = (List<MapuserMCHF3>) emp.getMapuserMCHF3Collection();
                if (userMchF3List != null && userMchF3List.size() > 0) {
                    for (MapuserMCHF3 f3MCH : userMchF3List) {
                        Mch mch = f3MCH.getMch();
                        subCategorySet.add(mch.getSubCategoryName());
                    }
                }
            }

            if (subCategorySet.size() > 0) {
                List<MCHVo> subCategoryList = new ArrayList<MCHVo>();
                Iterator subCategoryItreator = subCategorySet.iterator();
                while (subCategoryItreator.hasNext()) {
                    subCategoryList.add(new MCHVo((String) subCategoryItreator.next()));
                }
                return new UserMCSubCategoryResp(new Resp(RespCode.SUCCESS, "Sub Category List."), subCategoryList);
            } else {
                return new UserMCSubCategoryResp(new Resp(RespCode.FAILURE, "No SubCategory Found."));
            }
        } else {
            return new UserMCSubCategoryResp(new Resp(RespCode.FAILURE, "Invalid Emp ID : " + empId));
        }

    }

    public List<MCHVo> getAllCategoryMCHdtl() {
        logger.info("==== Inside getAllCategoryMCHdtl method of CategoryMCHService ==== ");
        try {
            List<Mch> list = mchFacade.findAll();
            List<MCHVo> listMCH = new ArrayList<MCHVo>();
            MCHVo mCHVo = null;
            for (Mch mch : list) {
                mCHVo = new MCHVo();
                mCHVo.setCategoryName(mch.getCategoryName());
                mCHVo.setMCCode(mch.getMcCode());
                mCHVo.setMCName(mch.getMcName());
                mCHVo.setSubCategoryName(mch.getSubCategoryName());
                if (mch.getMstLocation() != null) {
                    mCHVo.setLocationID(mch.getMstLocation().getLocationId());
                    mCHVo.setLocationName(mch.getMstLocation().getLocationName());
                }
                mCHVo.setIsMCActive(mch.getIsBlocked());
                listMCH.add(mCHVo);
            }
            return listMCH;
        } catch (Exception e) {
            logger.info("=======  Exception in  getAllCategoryMCHdtl :");
            e.printStackTrace();
            return null;
        }
    }

    public Resp blockUnblockMCH(String mcCode, Boolean isActive) throws Exception {
        logger.info("======= Inside blockUnblockMCH Service ========");
        if (mcCode == null) {
            return new Resp(RespCode.FAILURE, "Mc Code is Blank.");
        }
        Mch mch = mchFacade.find(mcCode);
        if (mch == null) {
            return new Resp(RespCode.FAILURE, "Invalid MC Code.");
        }
        mch.setIsBlocked(isActive);
        return new Resp(RespCode.SUCCESS, "MCH Updated Successfully.");
    }

    public Resp submitCategoryMCHDetails(List<MCHVo> listMCH) {
        logger.info("=== Inside submitCategoryMCHDetails method of CategoryMCHService ==== ");
        try {
            Calendar calender = Calendar.getInstance();
            for (MCHVo reqVO : listMCH) {
                if (reqVO.getMCCode() != null && reqVO.getMCCode().length() > 0) {
                    Mch mch = mchFacade.find(reqVO.getMCCode());
                    if (mch != null) {
                        mch.setCategoryName(reqVO.getCategoryName());
                        mch.setMcCode(reqVO.getMCCode());
                        mch.setMcName(reqVO.getMCName());
                        mch.setSubCategoryName(reqVO.getSubCategoryName());
                        mch.setUpdatedDate(calender.getTime());
                    } else {
                        mch = new Mch();
                        mch.setCategoryName(reqVO.getCategoryName());
                        mch.setMcCode(reqVO.getMCCode());
                        mch.setMcName(reqVO.getMCName());
                        mch.setSubCategoryName(reqVO.getSubCategoryName());
                        mch.setUpdatedDate(calender.getTime());
                        mchFacade.create(mch);
                    }
                }
            }
            return new Resp(RespCode.SUCCESS, "Category Master file uploaded Successfully !");
        } catch (Exception e) {
            logger.error("==== Web Service submitCategoryMCHDetails error : " + e.getMessage());
            e.printStackTrace();
            return new Resp(RespCode.FAILURE, "File can not be processed. Please conatct Admin !");
        }
    }

    public SubmitWorkflowResp submitWorkFlowDetail(List<MCHVo> listMCH) {
        logger.info("======== Inside submitWorkFlowDetail Service ==== ");
        StringBuilder sb = new StringBuilder();
        boolean isExist = false;


        try {
            sb.append("Category");
            sb.append(",");
            sb.append("Sub Category");
            sb.append(",");
            sb.append("MC Code");
            sb.append(",");
            sb.append("MC Decription");
            sb.append(",");
            sb.append("Location");
            sb.append("\n");
            for (MCHVo reqVO : listMCH) {
                Mch checkMCH = commonDAO.getMCHByCode(reqVO.getMCCode());
                List<MstLocation> location = commonDAO.checkLocationByName(reqVO.getLocationName());
                if (checkMCH != null) {
                    if (location.isEmpty()) {
                        sb.append(reqVO.getCategoryName());
                        sb.append(",");
                        sb.append(reqVO.getSubCategoryName());
                        sb.append(",");
                        sb.append(reqVO.getMCCode());
                        sb.append(",");
                        sb.append(reqVO.getMCName());
                        sb.append(",");
                        sb.append(reqVO.getLocationName());
                        sb.append(",");
                        sb.append("INVALID LOCATION !");
                        sb.append("\n");
                        isExist = true;
                    } else {
                        sb.append(reqVO.getCategoryName());
                        sb.append(",");
                        sb.append(reqVO.getSubCategoryName());
                        sb.append(",");
                        sb.append(reqVO.getMCCode());
                        sb.append(",");
                        sb.append(reqVO.getMCName());
                        sb.append(",");
                        sb.append(reqVO.getLocationName());
                        sb.append("\n");
                    }
                } else {
                    sb.append(reqVO.getCategoryName());
                    sb.append(",");
                    sb.append(reqVO.getSubCategoryName());
                    sb.append(",");
                    sb.append(reqVO.getMCCode());
                    sb.append(",");
                    sb.append(reqVO.getMCName());
                    sb.append(",");
                    sb.append(reqVO.getLocationName());
                    sb.append(",");
                    sb.append("INVALID MC-CODE !");
                    sb.append("\n");
                    isExist = true;
                }

            }
            String errorfiledata = sb.toString();
            // logger.info("========= error file contain : \n" + sb.toString());
            if (isExist) {
                try {

                    InputStream is = (InputStream) CategoryMCHService.class.getResourceAsStream(CommonConstants.PROPERTY);
                    Properties pro = new Properties();
                    pro.load(is);
                    Calendar calender = Calendar.getInstance();
                    // logger.info("========= error file contain : \n" + errorfiledata);
                    //String dirName = ("c:\\promotion\\error\\");
//                    String dirName = pro.getProperty("errorFile");
//                    String aosFileName = ("workflow_error_file.csv");
//                    File aosFileDir = new File(dirName);
//                    FileWriter fileWriter = new FileWriter(dirName + aosFileName);
//                    BufferedWriter buffWriter = new BufferedWriter(fileWriter);
//                    if (!aosFileDir.exists()) {
//                        aosFileDir.mkdir();
//                    } else if (!aosFileDir.isDirectory()) {
//                        System.out.println("The Directory does not exist");
//                        
//                    }
//                    File aosFile = new File(dirName, aosFileName);
//                    aosFile.createNewFile();
//                    buffWriter.write(errorfiledata);
//                    buffWriter.flush();
//                    buffWriter.close();

                    String dirName = pro.getProperty("errorFile");
                    System.out.println("Dir Name : " + dirName);
                    String aosFileName = "workflow_error_file_" + calender.getTimeInMillis() + ".csv";
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
                    return new SubmitWorkflowResp(new Resp(RespCode.FAILURE, "failure"), dirName + aosFileName);
                } catch (Exception e) {
                    logger.info("Exception while writing error file  :");
                    e.printStackTrace();

                }
            }

            for (MCHVo reqVO : listMCH) {
                Mch checkMCH = commonDAO.getMCHByCode(reqVO.getMCCode());
                List<MstLocation> location = commonDAO.checkLocationByName(reqVO.getLocationName());
                if (checkMCH != null && location != null) {
                    for (MstLocation ml : location) {
                        checkMCH.setMstLocation(ml);
                        logger.info("MCH updated succesfully !");
                    }
                }
            }

            return new SubmitWorkflowResp(new Resp(RespCode.SUCCESS, "Work-flow Detail updated successfully !"));

        } catch (Exception e) {
            logger.error("==== Web Service submitCategoryMCHDetails error : " + e.getMessage());
            e.printStackTrace();
            return new SubmitWorkflowResp(new Resp(RespCode.FAILURE, "Exception :" + e.getMessage()));
        }
    }

    public GetCategoryResp getCategorySubCategoryDtl() {
        logger.info("==== inside getCategorySubCategoryDtl Serice ======");
        try {
            GetCategoryResp categoryResp = new GetCategoryResp();
            List<String> listcategory = commonDAO.getAllCategoryName();
            //List<String> listSubCategory= commonDAO.getAllSubCategoryName();

            categoryResp.setListCategory(listcategory);
            //categoryResp.setListSubCategory(listSubCategory);
            //categoryResp.setResp(new Resp(RespCode.SUCCESS, "Category & Sub-Category Detail Retrive Successfully!"));
            categoryResp.setResp(new Resp(RespCode.SUCCESS, "Category Detail Retrive Successfully!"));
            return categoryResp;
        } catch (Exception e) {
            logger.error("==== Web Service getCategorySubCategoryDtl error : " + e.getMessage());
            e.printStackTrace();
            return new GetCategoryResp(new Resp(RespCode.FAILURE, "Exception :" + e.getMessage()));
        }

    }

    public GetCategoryResp getCategoryDtl(getCategorySubCategoryDtlReq request) {
        logger.info("==== inside getCategorySubCategoryDtl Serice ======");
        try {
            GetCategoryResp categoryResp = new GetCategoryResp();
            List<String> listcategory = commonDAO.getAllCategoryName(request.getUserId());
            categoryResp.setListCategory(listcategory);
            categoryResp.setResp(new Resp(RespCode.SUCCESS, "Category Retrive Successfully!"));
            return categoryResp;
        } catch (Exception e) {
            logger.error("==== Web Service getCategorySubCategoryDtl error : " + e.getMessage());
            e.printStackTrace();
            return new GetCategoryResp(new Resp(RespCode.FAILURE, "Exception :" + e.getMessage()));
        }

    }

    public GetCategoryResp getSubCategoryDtl(getCategorySubCategoryDtlReq request) {
        logger.info("==== inside getSubCategoryDtl Serice ======");
        try {
            GetCategoryResp categoryResp = new GetCategoryResp();
            System.out.println("Category Name : " + request.getCategoryName());
            String allgridData = request.getCategoryName();
            List strArray = new ArrayList();
            if (!allgridData.isEmpty() && allgridData != null) {
                String[] strArr = allgridData.split(",");
                strArray.addAll(Arrays.asList(strArr));
            }
            List<String> listcategory = commonDAO.getAllSubCategoryName(request.getUserId(), request.getCategoryName());
            System.out.println("Sub Category List size :" + listcategory.size());
            categoryResp.setListSubCategory(listcategory);
            categoryResp.setResp(new Resp(RespCode.SUCCESS, "Sub-Category Retrive Successfully!"));
            return categoryResp;
        } catch (Exception e) {
            logger.error("==== Web Service getSubCategoryDtl error : " + e.getMessage());
            e.printStackTrace();
            return new GetCategoryResp(new Resp(RespCode.FAILURE, "Exception :" + e.getMessage()));
        }

    }

    public GetCategoryResp getSubCategoryBycategory(getCategorySubCategoryDtlReq request) {
        logger.info("==== inside get subcategory by category ===");
        try {
            GetCategoryResp categoryResp = new GetCategoryResp();
            System.out.println("Category Name : " + request.getCategoryName());
            List<String> listcategory = commonDAO.getAllSubCategoryNameByCategory(request.getCategoryName());
            categoryResp.setListSubCategory(listcategory);
            categoryResp.setResp(new Resp(RespCode.SUCCESS, "Sub-Category Retrive Successfully!"));
            return categoryResp;
        } catch (Exception e) {
            logger.error("==== Web Service getSubCategoryBycategory error : " + e.getMessage());
            e.printStackTrace();
            return new GetCategoryResp(new Resp(RespCode.FAILURE, "Exception :" + e.getMessage()));
        }
    }

    public Resp getUserMCHDtlExcel() {
        try {
            System.out.println("-------- User MCH Excel Creation -----");
            List<MstEmployee> initiatorEmpList = commonDAO.getIntiatorEmployeeList("initiator");
            List<MstEmployee> l1EmpList = commonDAO.getIntiatorEmployeeList("l1");
            List<MstEmployee> l2EmpList = commonDAO.getIntiatorEmployeeList("l2");
            String filePath = userMChExcel.generateReport(initiatorEmpList, l1EmpList, l2EmpList);
            System.out.println("----- File Path : " + filePath);
            return new Resp(RespCode.SUCCESS, filePath);
        } catch (Exception ex) {
            System.out.println("------- error getUserMCHDtlExcel : " + ex.getMessage());
            ex.printStackTrace();
            return new Resp(RespCode.FAILURE, "Error while downloading User MC Mapping.");
        }
    }
}
