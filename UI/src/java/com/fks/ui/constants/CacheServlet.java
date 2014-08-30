/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;

import com.fks.promo.master.service.DepartmentVO;
import com.fks.promo.master.service.LocationVo;
import com.fks.promo.master.service.MstCampaignVO;
import com.fks.promo.master.service.MstEventVO;
import com.fks.promo.master.service.MstMktgVO;
import com.fks.promo.master.service.MstPromotionTypeVO;
import com.fks.promo.master.service.MstStatusVO;
import com.fks.promo.master.service.MstZoneVO;
import com.fks.promo.master.service.ProblemMasterVO;
import com.fks.promo.master.service.ProfileVO;
import com.fks.promo.master.service.RoleVO;
//import com.fks.ui.master.vo.MktgVo;
import com.fks.promo.master.service.SetVO;
import com.fks.promo.master.service.StoreVO;
import com.fks.ui.master.vo.EventTypeVO;
import com.fks.ui.master.vo.MktgVo;
import com.fks.ui.master.vo.ProblemTypeVO;
import com.fks.ui.master.vo.PromotionTypeVO;
import com.fks.ui.master.vo.StoreVoUI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@WebServlet(name = "CacheServlet", urlPatterns = {"/CacheServlet"})
public class CacheServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CacheServlet.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("========= Loading Master Data ========");
        try {

            List<DepartmentVO> listdept = ServiceMaster.getOtherMasterService().getAllDepartments();
            for (DepartmentVO vo : listdept) {
                if (vo.isIsActive() == Boolean.FALSE) {
                    CacheMaster.DeptMap.put(vo.getDepartmentID().toString(), vo.getDepartmentName());
                }
            }
            //StatusMap
            List<MstStatusVO> listStatus = ServiceMaster.getOtherMasterService().getALLStatus();
            for (MstStatusVO vo : listStatus) {
                if (vo.getStatusId() >= 5 && vo.getStatusId() <= 50) {
                    CacheMaster.StatusMap.put(vo.getStatusId().toString(), vo.getStatusName());
                }
            }

            List<MstCampaignVO> listChampaign = ServiceMaster.getOtherMasterService().getAllCampaign();
            for (MstCampaignVO vo : listChampaign) {
                if (vo.isIsActive() == false) {
                    CacheMaster.CampaignMap.put(vo.getCampaignID().toString(), vo.getCampaignName());
                }
            }

            List<SetVO> lstSet = ServiceMaster.getOtherMasterService().getAllSet();
            if (lstSet != null && lstSet.size() > 0) {
                for (SetVO vo : lstSet) {
                    CacheMaster.SetMap.put(vo.getSetID().toString(), vo.getSetDesc());
                }
            } else {
                CacheMaster.SetMap.put("-1", "---Select Set---");
            }
            List<LocationVo> lstLocation = ServiceMaster.getRoleMasterService().getAllLocation();
            if (lstLocation.size() > 0) {
                for (LocationVo vo : lstLocation) {
                    CacheMaster.LocationMap.put(vo.getLocationID().toString(), vo.getLocationName());
                }
            } else {
                CacheMaster.LocationMap.put("-1", "---- Select Location ----");
            }

            List<ProfileVO> profileVoList = ServiceMaster.getRoleMasterService().getAllProfiles();
            if (profileVoList.size() > 0 && !profileVoList.isEmpty()) {
                for (ProfileVO vo : profileVoList) {
                    CacheMaster.ProfileMap.put(vo.getProfileID().toString(), vo.getProfileName());
                }
            } else {
                CacheMaster.ProfileMap.put("-1", "---- Select Profile ----");
            }

            List<StoreVO> listStoreVo = ServiceMaster.getOrganizationMasterService().getAllOrganizationDtl();
            StoreVoUI storeVoUI;
            if (listStoreVo.size() > 0 && !listStoreVo.isEmpty()) {
                for (StoreVO vo : listStoreVo) {
//                    if(vo.isIsActive()==Boolean.TRUE){
//                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& inside : "+vo.getStoreID());
//                    }
                    if (vo.isIsStoreBlocked() == Boolean.FALSE) {
                        if (vo.getLocationID().toString().equals("3")) {
                            CacheMaster.formatSet.add(vo.getFormat());
                        }
                        storeVoUI = new StoreVoUI();
//                    CacheMaster.regionSet.add(vo.getRegion());
//                    CacheMaster.stateSet.add(vo.getState());
//                    CacheMaster.citySet.add(vo.getCity());
                        storeVoUI.setCity(vo.getCity());
                        storeVoUI.setFormat(vo.getFormat());
                        storeVoUI.setRegion(vo.getRegion());
                        storeVoUI.setState(vo.getState());
                        storeVoUI.setZoneName(vo.getZoneName());
                        storeVoUI.setZoneId(vo.getZoneId());
                        storeVoUI.setLocationID(vo.getLocationID());
                        storeVoUI.setLocationName(vo.getLocationName());
                        storeVoUI.setStoreClass(vo.getStoreClass());
                        storeVoUI.setStoreDesc(vo.getStoreDesc());
                        storeVoUI.setStoreID(vo.getStoreID());
                        CacheMaster.StoreVOMap.put(vo.getStoreID(), storeVoUI);

                        String storeDesc;
                        if (vo.getLocationID().toString().equalsIgnoreCase("2")) {
                            storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                            CacheMaster.StoreMap.put(vo.getStoreID().toString(), storeDesc);
                        } else if (vo.getLocationID().toString().equals("1")) {
                            storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                            CacheMaster.HoStoreMap.put(vo.getStoreID().toString(), storeDesc);
                            CacheMaster.ZoneStoreCodeMap.put(vo.getStoreID().toString(), storeDesc);
                        } else if (vo.getLocationID().toString().equalsIgnoreCase("3")) {
                            storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                            CacheMaster.ZoneStoreCodeMap.put(vo.getStoreID().toString(), storeDesc);
                        }
                        CacheMaster.ZoneStoreMap.put("-1", "---- Select Zone Site ---- ");
                    }
                }
            } else {
                CacheMaster.StoreMap.put("-1", "---- Select Site ---- ");
                CacheMaster.HoStoreMap.put("-1", "---- Select Ho Site ---- ");
                CacheMaster.ZoneStoreMap.put("-1", "---- Select Zone Site ---- ");
            }
            
            List<RoleVO> lstRoles = ServiceMaster.getRoleMasterService().getAllRoles();
            if (lstRoles.size() > 0 && !lstRoles.isEmpty()) {
                for (RoleVO rolevo : lstRoles) {
                    if (rolevo.getIsBlocked() == 0) {
                        CacheMaster.RoleMap.put(rolevo.getRoleId().toString(), rolevo.getRoleName());
                    }
                }
            } else {
                logger.info("No Role Available..");

            }
            // CacheMaster.ZoneMap.put("-1", "---- Select Zone ---- ");
            List<MstZoneVO> list = ServiceMaster.getOtherMasterService().getAllZone();
            if (list != null) {
                for (MstZoneVO vo : list) {
                    if (vo.getIsBlocked() == 0 && !vo.getZonename().equalsIgnoreCase("all")) {
                        CacheMaster.ZoneMap.put(vo.getId().toString(), vo.getZonename());
                    }

                }
            }

            List<ProblemMasterVO> problemTypeList = ServiceMaster.getOtherMasterService().getAllProblemMaster();
            if (problemTypeList != null) {
                for (ProblemMasterVO problemVO : problemTypeList) {
                    CacheMaster.problemTypeMap.put(problemVO.getProblemId().toString(), new ProblemTypeVO(problemVO.getProblemId(), problemVO.getProblemName(), problemVO.getIsBlocked()));
                }
            }
            List<MstPromotionTypeVO> promotionTyleList = ServiceMaster.getOtherMasterService().getAllPromotionMaster();
            if (promotionTyleList != null) {
                for (MstPromotionTypeVO promotionVO : promotionTyleList) {
                    CacheMaster.promotionTypeMap.put(promotionVO.getId().toString(), new PromotionTypeVO(promotionVO.getId(), promotionVO.getPromotionName(), promotionVO.getIsBlocked()));
                }
            }


            List<MstEventVO> listevent = ServiceMaster.getOtherMasterService().getAllEventMaster();
            if (list != null) {
                for (MstEventVO vo : listevent) {
                    CacheMaster.EventMap.put(vo.getEventId().toString(), new EventTypeVO(vo.getEventId(), vo.getEventName(), vo.getIsBlocked()));
                }
            }
            List<MstMktgVO> lstMstMktg = ServiceMaster.getOtherMasterService().getAllMarketingType();
            if (lstMstMktg != null && !lstMstMktg.isEmpty()) {
                for (MstMktgVO mktgvo : lstMstMktg) {
                    CacheMaster.MarketingTypeMap.put(mktgvo.getMktgTyped().toString(), new MktgVo(mktgvo.getMktgTyped(), mktgvo.getMktgName(), mktgvo.getIsBlocked()));
                }
            } else {
                logger.info("Marketing Type List is Not Available.");
            }

            logger.info("========= Loading Master Data Finished========");

        } catch (Exception e) {
            logger.info("Exception Occured During Master Data Fetching :" + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CacheServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CacheServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
