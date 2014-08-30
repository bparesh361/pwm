/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promotion.service.CommonPromoVo;
import com.fks.promotion.service.OrganizationDtlResp;
import com.fks.promotion.service.Resp;
import com.fks.promotion.service.SubmitPromoOrgdtlReq;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.OrganizationGroupFormVo;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class OrganizationAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(OrganizationAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private CachedMapsList maps;
    private List<String> stateList, formatList, regionList, cityList;
    private Map<String, String> zoneList, storeList;
    private OrganizationGroupFormVo orgFormVo;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate OrganizationAction Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            if (strUserID != null) {
                //formatList = maps.getActiveList(MapEnum.FORMAT_LIST_ORG, null);
                formatList = new ArrayList<String>();
                zoneList = new HashMap<String, String>();
                //storeList = maps.getActiveMap(MapEnum.STORE_TYPE);
                storeList = new HashMap<String, String>();
                stateList = new ArrayList<String>();
                regionList = new ArrayList<String>();
                //cityList = maps.getActiveList(MapEnum.CITY_lIST, null, "gujarat");
                cityList = new ArrayList<String>();
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllFormatExceptZoneAndHo() {
        logger.info("===== Inside getAllFormatExceptZoneAndHo Action ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> celldescdecArray = new ArrayList<String>();

            maps = new CachedMapsList();
            celldescdecArray = maps.getActiveList(MapEnum.FORMAT_LIST_ORG, null);
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("formatList", celldescdecArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in getstateBasedonZoneAndFormat() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void getZoneBasedonFormat() {
        logger.info("===== Inside getZoneBasedonFormat ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellZonedecArray = new ArrayList<String>();
            List<String> cellZoneIDArray = new ArrayList<String>();
            String txtformat = request.getParameter("txtformat").toLowerCase();
            // logger.info("format : " + txtformat);
            String locationID = getSessionMap().get(WebConstants.LOCATION_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();
            OrganizationDtlResp resp = ServiceMaster.getPromotionInitiateService().getZoneBasedOnFormat(txtformat, locationID, zoneId);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                if (resp.getZoneList().size() > 0) {
                    for (CommonPromoVo vo : resp.getZoneList()) {
                        cellZoneIDArray.add(vo.getId());
                        cellZonedecArray.add(vo.getName());
                    }
                }
            }
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("zoneDescList", cellZonedecArray);
            cellObject.put("zoneIdList", cellZoneIDArray);
            responseData.put("rows", cellObject);
            out.println(responseData);
        } catch (Exception e) {
            logger.info("Exception in getZoneBasedonFormat() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void getstateBasedonZoneAndFormat() {
        logger.info("===== Inside getstateBasedonZoneAndFormat Action ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellStatedecArray = new ArrayList<String>();
            String txtformat = request.getParameter("txtformat").toLowerCase();
            String zoneName = request.getParameter("zonename").toLowerCase();

            OrganizationDtlResp resp = ServiceMaster.getPromotionInitiateService().getStateBasedOnFormatAndZone(txtformat, zoneName);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                if (resp.getStateRegionCityList().size() > 0) {
                    for (String vo : resp.getStateRegionCityList()) {
                        cellStatedecArray.add(vo);
                    }
                }
            }
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("StatenameList", cellStatedecArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in getstateBasedonZoneAndFormat() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void getRegionBasedOnstateAndFormat() {
        logger.info("===== Inside getRegionBasedOnstateAndFormat Action ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> celldecArray = new ArrayList<String>();
            String txtformat = request.getParameter("txtformat").toLowerCase();
            String state = request.getParameter("statename").toLowerCase();
            String zoneName = request.getParameter("zonename").toLowerCase();

            OrganizationDtlResp resp = ServiceMaster.getPromotionInitiateService().getRegionBasedOnFormatAndState(txtformat, state, zoneName);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                if (resp.getStateRegionCityList().size() > 0) {
                    for (String vo : resp.getStateRegionCityList()) {
                        celldecArray.add(vo);
                    }
                }
            }
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("regionnameList", celldecArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in getstateBasedonZoneAndFormat() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void getCitybasedonRegionAndformat() {
        logger.info("===== Inside getCitybasedonRegionAndformat Action ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellCitydecArray = new ArrayList<String>();
            String txtformat = request.getParameter("txtformat").toLowerCase();
            String region = request.getParameter("regionname").toLowerCase();
            String state = request.getParameter("statename").toLowerCase();

            OrganizationDtlResp resp = ServiceMaster.getPromotionInitiateService().getCityBasedOnFormatAndRegion(txtformat, region, state);
            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                if (resp.getStateRegionCityList().size() > 0) {
                    for (String vo : resp.getStateRegionCityList()) {
                        cellCitydecArray.add(vo);
                    }
                }
            }
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("citynameList", cellCitydecArray);
            responseData.put("rows", cellObject);
            out.println(responseData);


        } catch (Exception e) {
            logger.info("Exception in getstateAndRegionbasedonZone() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
        }
    }

    public void getStorebasedonCityAndFormat() {
        logger.info("===== Inside getStorebasedonCityAndFormat Action ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellstoredecArray = new ArrayList<String>();
            List<String> cellstoreIdArray = new ArrayList<String>();

            String txtformat = request.getParameter("txtformat").toLowerCase();
            String city = request.getParameter("cityname").toLowerCase();

            String zoneName = request.getParameter("zonename").toLowerCase();

            OrganizationDtlResp resp = ServiceMaster.getPromotionInitiateService().getStoreBasedOnFormatAndCity(txtformat, city, zoneName);

            if (resp.getResp().getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                if (resp.getStoreVOList().size() > 0) {
                    for (CommonPromoVo vo : resp.getStoreVOList()) {
                        cellstoredecArray.add(vo.getName());
                        cellstoreIdArray.add(vo.getId());
                    }
                }
            }
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();
            cellObject.put("storenameList", cellstoredecArray);
            cellObject.put("storeIdList", cellstoreIdArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in getstateAndRegionbasedonZone() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
        }
    }

    public String saveSubmitOragnizationdetail() {
        logger.info("======= Inside saveSubmitOragnizationdetail ==== ");
        try {

            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            SubmitPromoOrgdtlReq req = new SubmitPromoOrgdtlReq();
            req.setEmpID(new Long(strUserID));
            req.setMstPromoID(new Long(orgFormVo.getMstPromoId()));
            req.setStatus(new Long(orgFormVo.getStatusId()));

            String allgridDataCity = orgFormVo.getMstCityList();
            if (!allgridDataCity.isEmpty() && allgridDataCity != null) {
                String[] strArr = allgridDataCity.split(",");
                req.getListCity().addAll(Arrays.asList(strArr));
            }
            String allgridDataRegion = orgFormVo.getMstRegionList();
            if (!allgridDataRegion.isEmpty() && allgridDataRegion != null) {
                String[] strArr = allgridDataRegion.split(",");
                req.getListRegion().addAll(Arrays.asList(strArr));
            }
            String allgridDatazonS = orgFormVo.getMstZoneList();
            if (!allgridDatazonS.isEmpty() && allgridDatazonS != null) {
                String[] strArr = allgridDatazonS.split(",");
                CommonPromoVo vo;
                for (String s : strArr) {
                    vo = new CommonPromoVo();
                    vo.setId(s);
                    req.getListMstZoneVo1().add(vo);
                }
            }
            String allgridDataFormat = orgFormVo.getMstFormatlist();
            if (!allgridDataFormat.isEmpty() && allgridDataFormat != null) {
                String[] strArr = allgridDataFormat.split(",");
                req.getListFormat().addAll(Arrays.asList(strArr));
            }
            String allgridDatastate = orgFormVo.getMststateList();
            if (!allgridDatastate.isEmpty() && allgridDatastate != null) {
                String[] strArr = allgridDatastate.split(",");
                req.getListState().addAll(Arrays.asList(strArr));
            }
            String allgridDatastore = orgFormVo.getMstStoreList();
            if (!allgridDatastore.isEmpty() && allgridDatastore != null) {
                String[] strArr = allgridDatastore.split(",");
                CommonPromoVo vo = null;
                for (String s : strArr) {
                    vo = new CommonPromoVo();
                    vo.setId(s);
                    req.getListStoreVo1().add(vo);
                }
            }
            
            Resp resp = ServiceMaster.getPromotionInitiateService().statusChangedFromDraftOrSubmitOrganizationDtl(req);
            if (resp.getRespCode().value().equals(WebConstants.success)) {
                //on sucess
                addActionMessage(resp.getMsg());

                formatList = new ArrayList<String>();
                zoneList = new HashMap<String, String>();
                storeList = new HashMap<String, String>();
                stateList = new ArrayList<String>();
                regionList = new ArrayList<String>();
                cityList = new ArrayList<String>();
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                //on failure

                formatList = new ArrayList<String>();
                zoneList = new HashMap<String, String>();
                storeList = new HashMap<String, String>();
                stateList = new ArrayList<String>();
                regionList = new ArrayList<String>();
                cityList = new ArrayList<String>();
                return INPUT;
            }

        } catch (Exception e) {
            logger.info("Exception in saveSubmitOragnizationdetail() of Promotion OrganizationAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<String> getStateList() {
        return stateList;
    }

    public void setStateList(List<String> stateList) {
        this.stateList = stateList;
    }

    public List<String> getFormatList() {
        return formatList;
    }

    public void setFormatList(List<String> formatList) {
        this.formatList = formatList;
    }

    public OrganizationGroupFormVo getOrgFormVo() {
        return orgFormVo;
    }

    public void setOrgFormVo(OrganizationGroupFormVo orgFormVo) {
        this.orgFormVo = orgFormVo;
    }

    public Map<String, String> getZoneList() {
        return zoneList;
    }

    public void setZoneList(Map<String, String> zoneList) {
        this.zoneList = zoneList;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public List<String> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String> regionList) {
        this.regionList = regionList;
    }

    public Map<String, String> getStoreList() {
        return storeList;
    }

    public void setStoreList(Map<String, String> storeList) {
        this.storeList = storeList;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }
}
