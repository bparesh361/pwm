/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.communication.action;

import com.fks.promo.comm.service.CommSearchReq;
import com.fks.promo.comm.service.CommSearchResp;
import com.fks.promo.comm.service.CommSearchType;
import com.fks.promo.comm.service.CommVO;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class PromoCommunicationAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PromoCommunicationAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strEmpID;
    private String startDate, endDate, eventId, categoryName, mccode, article, zonesel;
    CachedMapsList maps;
    private List<String> mchList, articleList, categoryMap;
    private Map<String, String> eventMap;
    private Map<String, String> zoneMap;
    private String locationID, ZoneID;
    private List<String> subcategoryMap;
    private List<String> fillSubCategoryList = new ArrayList<String>();

    @Override
    public String execute() {
        logger.info("@------------------ Welcome Promo communication Dashboard Action Page ----------------@");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                
                maps = new CachedMapsList();
                eventMap = maps.getActiveMap(MapEnum.EVENT);
                categoryMap = maps.getActiveCommList(MapEnum.CATEGORY_LIST, null);
                mchList = new ArrayList<String>();
                articleList = new ArrayList<String>();
                locationID = getSessionMap().get(WebConstants.LOCATION_ID).toString();
                ZoneID = getSessionMap().get(WebConstants.ZONE_ID).toString();
                zoneMap = maps.getZoneUserWiseForComm(locationID, ZoneID);
                subcategoryMap = fillSubCategoryList;
                //setZoneMap(CacheMaster.ZoneMap);
                return SUCCESS;
            } else {                
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion communication Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private String searchType;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void getPromotionCommunicationGridData() {
        logger.info("@=========== Inside fetching data for Promotion Communication dashboard. Service :  getCommService().searchComm();========@");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            
            String locationId = getSessionMap().get(WebConstants.LOCATION_ID).toString();
            String storeId = getSessionMap().get(WebConstants.STORE_CODE).toString();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            CommSearchReq req = new CommSearchReq();

            if (searchType.equalsIgnoreCase("ZONE_DATE")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE);
                req.setZoneId(zonesel);

            } else if (searchType.equalsIgnoreCase("ZONE_DATE_EVENT")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_EVENT);
                req.setZoneId(zonesel);
                req.setEventTypeId(new Long(eventId));

            } else if (searchType.equalsIgnoreCase("ZONE_DATE_EVENT_CATEGORY")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_EVENT_CATEGORY);
                req.setZoneId(zonesel);
                req.setCategoryName(categoryName);
                req.setEventTypeId(new Long(eventId));

            } else if (searchType.equalsIgnoreCase("ZONE_DATE_EVENT_CATEGORY_MC")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_EVENT_CATEGORY_MC);
                req.setZoneId(zonesel);
                req.setMcList(mccode);
                req.setEventTypeId(new Long(eventId));
                
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_EVENT_CATEGORY_MC_ARTICLE")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_EVENT_CATEGORY_MC_ARTICLE);
                req.setZoneId(zonesel);
                req.setMcList(mccode);
                req.setArticleList(article);
                req.setEventTypeId(new Long(eventId));
                
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_CATEGORY")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_CATEGORY);
                req.setZoneId(zonesel);
                req.setCategoryName(categoryName);
                
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_CATEGORY_MC")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_CATEGORY_MC);
                req.setZoneId(zonesel);
                req.setCategoryName(categoryName);
                req.setMcList(mccode);
                
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_CATEGORY_MC_ARTICLE")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_CATEGORY_MC_ARTICLE);
                req.setZoneId(zonesel);
                req.setCategoryName(categoryName);
                req.setMcList(mccode);
                req.setArticleList(article);
                
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_MC")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_MC);
                req.setZoneId(zonesel);
                req.setMcList(mccode);
               
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_MC_ARTICLE")) {
                req.setEndDate(endDate);
                req.setStartDate(startDate);
                req.setType(CommSearchType.ZONE_DATE_MC_ARTICLE);
                req.setZoneId(zonesel);
                req.setMcList(mccode);
                req.setArticleList(article);
                
            } else if (searchType.equalsIgnoreCase("ZONE_DATE_DEFAULT")) {
                req.setEndDate(dateFormat.format(cal.getTime()));
                req.setStartDate(dateFormat.format(cal.getTime()));
                
                req.setZoneId(ZoneID);
                req.setType(CommSearchType.ZONE_DATE);
                
            }
            if (locationId.equalsIgnoreCase("2")) {
                req.setIsStore(true);
                req.setStoreId(storeId);
            }
            List<CommVO> lstSearchData = null;
            String rows = request.getParameter("rows");
            
            String pageno = request.getParameter("page");
            
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");

            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            Integer pageNo = null;
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);
                               
                req.setStartIndex(startPageIndex.intValue());
                CommSearchResp resp = ServiceMaster.getCommService().searchComm(req);
                
                if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    lstSearchData = resp.getList();
                    
                    totalCount = resp.getTotalCount();
                    
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                    
                }
            } else {
                lstSearchData = Collections.<CommVO>emptyList();
            }
            
            if (sidx.equals("reqId")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getTransPromoId().toString().compareToIgnoreCase(p2.getTransPromoId().toString());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getEventType().compareToIgnoreCase(p2.getEventType());
                    }
                });
            } else if (sidx.equals("site")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getSite().compareToIgnoreCase(p2.getSite());
                    }
                });
            } else if (sidx.equals("city")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getCity().compareToIgnoreCase(p2.getCity());
                    }
                });
            } else if (sidx.equals("state")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getState().compareToIgnoreCase(p2.getState());
                    }
                });
            } else if (sidx.equals("region")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getRegion().compareToIgnoreCase(p2.getRegion());
                    }
                });
            } else if (sidx.equals("zone")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getZone().compareToIgnoreCase(p2.getZone());
                    }
                });
            } else if (sidx.equals("promotype")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getPromoType().compareToIgnoreCase(p2.getPromoType());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getPromoDetails().compareToIgnoreCase(p2.getPromoDetails());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getCategory().compareToIgnoreCase(p2.getCategory());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getSubCategory().compareToIgnoreCase(p2.getSubCategory());
                    }
                });
            } else if (sidx.equals("validfrom")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getValidFrom().compareToIgnoreCase(p2.getValidFrom());
                    }
                });
            } else if (sidx.equals("validto")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getValidTo().compareToIgnoreCase(p2.getValidTo());
                    }
                });
            } else if (sidx.equals("cachTriCode")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getCashierTriggerCode().compareToIgnoreCase(p2.getCashierTriggerCode());
                    }
                });
            } else if (sidx.equals("bonusBuy")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        CommVO p1 = (CommVO) o1;
                        CommVO p2 = (CommVO) o2;
                        return p1.getBonusBy().compareToIgnoreCase(p2.getBonusBy());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }
            String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
            for (CommVO vo : lstSearchData) {
                cellobj.put(WebConstants.ID, vo.getTransPromoId());
                cell.add("T" + vo.getMstPromoId() + "-R" + vo.getTransPromoId());
                cell.add(vo.getEventType());
                cell.add(vo.getSite());
                cell.add(vo.getCity());
                cell.add(vo.getState());
                cell.add(vo.getRegion());
                cell.add(vo.getZone());
                cell.add(vo.getPromoType());
                cell.add(vo.getPromoDetails());
                cell.add(vo.getCategory());
                cell.add(vo.getSubCategory());
                cell.add(vo.getValidFrom());
                cell.add(vo.getValidTo());
                cell.add(vo.getCashierTriggerCode());
                cell.add(vo.getBonusBy());
                String articleFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadArticleMcComm?id=" + vo.getTransPromoId() + ">Download</a>";
                cell.add(articleFile);
//                String downloadreq = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadTransPromoDtl?id="
//                        + vo.getTransPromoId() + ">Download</a>";
//                cell.add(downloadreq);
                String downloadreq = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadMultipleExcel?transId="
                        + vo.getTransPromoId() + "&empID=" + empId + ">Download</a>";
                cell.add(downloadreq);
                cellobj.put(WebConstants.CELL, cell);
                cell.clear();
                cellarray.add(cellobj);

            }
            responcedata.put(WebConstants.TOTAL, pageCount);
            responcedata.put(WebConstants.PAGE, pageNo);
            responcedata.put(WebConstants.RECORDS, totalCount);
            responcedata.put(WebConstants.ROWS, cellarray);
            out.print(responcedata);

        } catch (Exception e) {
            logger.fatal("Exception in getPromotionCommunicationGridData : ");
            e.printStackTrace();
        }
    }

    public void downloadArticleMcCommunicationFile() {
        logger.info("===== Inside downloadArticleMcCommunicationFile. Service :getCommService().downloadCommArticleMcPromoRequest();====");
        try {
            String transpromoID = request.getParameter("id");
            
            com.fks.promo.comm.service.DownloadSubPromoResp subPromoDownload = ServiceMaster.getCommService().downloadCommArticleMcPromoRequest(Long.valueOf(transpromoID));
            if (subPromoDownload.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                File file = new File(subPromoDownload.getDownloadFilePath());
                byte[] buffer = new byte[1024];
                FileInputStream fin = new FileInputStream(file);
                DataInputStream din = new DataInputStream(fin);
                ServletOutputStream sout = response.getOutputStream();
                response.setContentType("application/csv");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
                while (din != null && din.read(buffer) != -1) {
                    sout.write(buffer);
                }
                din.close();
                sout.close();
            }
        } catch (Exception e) {
            logger.info("Exception in downloadArticleMcCommunicationFile() of Promotion Communication Page ----- ");
            e.printStackTrace();
        }
    }

    public void getMcBasedonCategory() {

        logger.info("===== Inside get all Mch based on Category. Service :getCommService().getAllMchCategFromPromo()  ====");

        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellMCHdecArray = new ArrayList<String>();

            String categoryname = request.getParameter("categoryname").toLowerCase();
            maps = new CachedMapsList();
            cellMCHdecArray = maps.getActiveCommList(MapEnum.MCH_LIST, categoryname);
            
            subcategoryMap = maps.getActiveList(MapEnum.SUB_CATEGORY, null, categoryname);
            List<String> cellSubCategoryArray = new ArrayList<String>();
            cellSubCategoryArray = subcategoryMap;

            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();

            cellObject.put("mchnameList", cellMCHdecArray);
            cellObject.put("subcategroylist", cellSubCategoryArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("@=== Exception in getMcBasedonCategory() of Promotion Communication Page ----- ");
            e.printStackTrace();
        }

    }

     public void getMcBasedonSubCategory() {
        logger.info("===== Inside get all Mch based on Category. Service :getCommService().getAllMchCategFromPromo()  ====");

        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellMCHdecArray = new ArrayList<String>();

            String categoryname = request.getParameter("subcategoryname").toLowerCase();
            maps = new CachedMapsList();
            cellMCHdecArray = maps.getActiveCommList(MapEnum.MC_LIST_SUB_CATEGORY, categoryname);
            
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();

            cellObject.put("mchnameList", cellMCHdecArray);

            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("@=== Exception in getMcBasedonCategory() of Promotion Communication Page ----- ");
            e.printStackTrace();
        }

    }

    public void getZoneBasedonUser() {

        logger.info("@===== Inside getZoneBasedonUser. Service : getCommService().getAllZoneUserWise ====");

        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> zoneDescList = new ArrayList<String>();
            List<String> zoneIDList = new ArrayList<String>();

            locationID = getSessionMap().get(WebConstants.LOCATION_ID).toString();
            ZoneID = getSessionMap().get(WebConstants.ZONE_ID).toString();
            //System.out.println("Location : "+locationID+"--- zone : "+ZoneID);
            maps = new CachedMapsList();
            zoneMap = maps.getZoneUserWiseForComm(locationID, ZoneID);
            Iterator iterator = zoneMap.entrySet().iterator();

            if (iterator.hasNext()) {
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    zoneDescList.add(entry.getValue().toString());
                    zoneIDList.add(entry.getKey().toString());
                }
            }
            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();

            cellObject.put("zoneDescList", zoneDescList);
            cellObject.put("zoneIdList", zoneIDList);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in getMcBasedonCategory() of Promotion Communication Page ----- ");
            e.printStackTrace();
        }

    }

    public void getArticleBasedonMCH() {

        logger.info("===== Inside get all Mch based on Category . Service: getCommService().getAllArticleMchWise ====");

        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            List<String> cellArticledecArray = new ArrayList<String>();

            String mcname = request.getParameter("mcname").toLowerCase();

            maps = new CachedMapsList();
            cellArticledecArray = maps.getActiveCommList(MapEnum.ARTICLE_LIST, mcname);

            System.out.println("Article List Size : " + cellArticledecArray.size());

            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();

            cellObject.put("articlenameList", cellArticledecArray);
            responseData.put("rows", cellObject);
            out.println(responseData);

        } catch (Exception e) {
            logger.info("Exception in getArticleBasedonMCH() of Promotion Communication Page ----- ");
            e.printStackTrace();
        }

    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Map<String, String> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, String> eventMap) {
        this.eventMap = eventMap;
    }

    public List<String> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(List<String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getMccode() {
        return mccode;
    }

    public void setMccode(String mccode) {
        this.mccode = mccode;
    }

    public List<String> getMchList() {
        return mchList;
    }

    public void setMchList(List<String> mchList) {
        this.mchList = mchList;
    }

    public List<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<String> articleList) {
        this.articleList = articleList;
    }

    public Map<String, String> getZoneMap() {
        return zoneMap;
    }

    public void setZoneMap(Map<String, String> zoneMap) {
        this.zoneMap = zoneMap;
    }

    public String getZonesel() {
        return zonesel;
    }

    public void setZonesel(String zonesel) {
        this.zonesel = zonesel;
    }

    public List<String> getSubcategoryMap() {
        return subcategoryMap;
    }

    public void setSubcategoryMap(List<String> subcategoryMap) {
        this.subcategoryMap = subcategoryMap;
    }

    
}
