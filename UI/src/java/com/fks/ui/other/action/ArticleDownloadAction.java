/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.other.action;

import com.fks.article.service.ArticleDownloadResp;
import com.fks.article.service.ArticleDownloadVo;
import com.fks.article.service.ArticleSearchEnum;
import com.fks.article.service.ArticleSearchReq;
import com.fks.article.service.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
public class ArticleDownloadAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(ArticleSearchAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    PrintWriter out;
    private String strUserID;

    public String viewArticleMCSearch() {
        logger.info("------------------ Inside Article Download Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {                
                return SUCCESS;
            } else {                
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in viewArticleMCSearch Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getArticleDownlodDtl() {
        logger.info("======== Inside getArticleDownlodDtl ==========");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String rows = request.getParameter("rows");
            String pageno = request.getParameter("page");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            

            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            Integer pageNo = null;
            List<ArticleDownloadVo> lstSearchData = null;
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);

                ArticleDownloadResp resp = ServiceMaster.getDownloadArticleService().getArticleDownloaddtl(Long.valueOf(strUserID), Integer.parseInt(startPageIndex.toString()));
                
                if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    lstSearchData = resp.getArticleDownloadVoList();
                
                    totalCount = resp.getTotalCount();
                
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            } else {
                lstSearchData = Collections.<ArticleDownloadVo>emptyList();
            }

            if (sidx.equals("no")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getArticleDownloadId().toString().compareToIgnoreCase(p2.getArticleDownloadId().toString());
                    }
                });
            } else if (sidx.equals("mc")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getMcList().toString().compareToIgnoreCase(p2.getMcList().toString());
                    }
                });
            } else if (sidx.equals("bname")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getBrandname().toString().compareToIgnoreCase(p2.getBrandname().toString());
                    }
                });
            } else if (sidx.equals("scode")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getSeacode().toString().compareToIgnoreCase(p2.getSeacode().toString());
                    }
                });
            } else if (sidx.equals("rdate")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("rtime")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getTime().toString().compareToIgnoreCase(p2.getTime().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleDownloadVo p1 = (ArticleDownloadVo) o1;
                        ArticleDownloadVo p2 = (ArticleDownloadVo) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            }
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }
            if (lstSearchData != null) {
                for (ArticleDownloadVo vo : lstSearchData) {
                    cellobj.put(WebConstants.ID, vo.getArticleDownloadId());
                    cell.add("SR" + vo.getArticleDownloadId());
                    cell.add(vo.getMcList());
                    cell.add(vo.getBrandname());
                    cell.add(vo.getSeacode());
                    cell.add(vo.getDate());
                    cell.add(vo.getTime());
                    cell.add(vo.getStatus());
                    if (vo.getDownlaodFilePath() != null) {
                        String articleFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadfileartilcle?path=" + vo.getDownlaodFilePath() + ">Download</a>";
                        cell.add(articleFile);
                    } else {
                        cell.add("-");
                    }

                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);

                }
                responcedata.put(WebConstants.TOTAL, pageCount);
                responcedata.put(WebConstants.PAGE, pageNo);
                responcedata.put(WebConstants.RECORDS, totalCount);
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
            }

        } catch (Exception e) {
            logger.info("Exception in getArticleDownlodDtl Action ----- ");
            e.printStackTrace();
        }
    }
    private String mccode, brand, seasoncode, searchType;

    public String submitArticleDownloadReq() {
        logger.info("====== Inside submitArticleDownloadReq ======");
        try {

            ArticleSearchReq req = new ArticleSearchReq();
            strUserID = getSessionMap().get(WebConstants.EMP_ID).toString();
            req.setCreatedBy(Long.valueOf(strUserID));
            
            if (searchType.trim().equalsIgnoreCase("MC")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC);
                req.setMCList(mccode);
            } else if (searchType.trim().equalsIgnoreCase("BRAND")) {
                req.setArticleSearchEnum(ArticleSearchEnum.BRAND);
                req.setBrandList(brand.toUpperCase());
            } else if (searchType.trim().equalsIgnoreCase("SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.SEASON_CODE);
                req.setSeasonCodeList(seasoncode);
            } else if (searchType.trim().equalsIgnoreCase("MC_BRAND")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC_BRAND);
                req.setMCList(mccode);
                req.setBrandList(brand.toUpperCase());
            } else if (searchType.trim().equalsIgnoreCase("MC_SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC_SEASON_CODE);
                req.setMCList(mccode);
                req.setSeasonCodeList(seasoncode);
            } else if (searchType.trim().equalsIgnoreCase("BRAND_SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.BRAND_SEASON_CODE);
                req.setBrandList(brand.toUpperCase());
                req.setSeasonCodeList(seasoncode);
            } else if (searchType.trim().equalsIgnoreCase("MC_BRAND_SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC_BRAND_SEASON_CODE);
                req.setMCList(mccode);
                req.setBrandList(brand.toUpperCase());
                req.setSeasonCodeList(seasoncode);
            } else {
                addActionError("Invalid Search Type.");
                return INPUT;
            }
            Resp resp = ServiceMaster.getDownloadArticleService().submitArticleSearchDtl(req);
            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError("Error while genrating article search request.");
                return INPUT;
            }

        } catch (Exception e) {
            logger.info("Exception in submitArticleDownloadReq Action ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMccode() {
        return mccode;
    }

    public void setMccode(String mccode) {
        this.mccode = mccode;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSeasoncode() {
        return seasoncode;
    }

    public void setSeasoncode(String seasoncode) {
        this.seasoncode = seasoncode;
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
