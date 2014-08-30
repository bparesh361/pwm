package com.fks.ui.other.action;

import com.fks.ui.common.action.ActionBase;

import com.fks.ods.service.ArticleMCVO;
import com.fks.ods.service.ArticleSearchEnum;
import com.fks.ods.service.ArticleSearchReq;
import com.fks.ods.service.ArticleSearchResp;
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
public class ArticleSearchAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(ArticleSearchAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    PrintWriter out;
    private String strUserID;

    public String viewArticleMCSearch() {
        logger.info("------------------ Inside Article Search Page ----------------");
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
    private String hdnPageGrid;

    public String getHdnPageGrid() {
        return hdnPageGrid;
    }

    public void setHdnPageGrid(String hdnPageGrid) {
        this.hdnPageGrid = hdnPageGrid;
    }

    public void getArticleSearchDetail() {
        logger.info("=========== Inside Search ===========");
        JSONObject responcedata = new JSONObject();
        JSONArray cellarray = new JSONArray();
        JSONArray cell = new JSONArray();
        JSONObject cellobj = new JSONObject();
        try {
            ArticleSearchReq req = new ArticleSearchReq();
            out = response.getWriter();
            //String artNo = request.getParameter("articleCode");

            String searchType = request.getParameter("searchType");
            String MCode = request.getParameter("mccode");
            String brand = request.getParameter("brand");
            String scode = request.getParameter("seasoncode");

            StringBuilder sbMc = new StringBuilder();
            StringBuilder sbBrand = new StringBuilder();
            StringBuilder sbSeasonCode = new StringBuilder();
            String strMC, strBrand, strSeasonCode = "";
            if (searchType.trim().equalsIgnoreCase("MC")) {
                String allgridData = MCode;
                if (!allgridData.isEmpty() && allgridData != null) {
                    String[] strArr = allgridData.split(",");
                    for (String sb : strArr) {
                        sbMc.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.MC);
                strMC = sbMc.substring(0, sbMc.lastIndexOf(","));
                req.setMCList(strMC);
                //} else if (brand != null && !brand.isEmpty()) {
            } else if (searchType.trim().equalsIgnoreCase("BRAND")) {
                String allgridData = brand;
                if (!allgridData.isEmpty() && allgridData != null) {
                    String[] strArr = allgridData.split(",");
                    for (String sb : strArr) {
                        sbBrand.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.BRAND);
                strBrand = sbBrand.substring(0, sbBrand.lastIndexOf(","));
                req.setBrandList(strBrand);
                //} else if (scode != null && !scode.isEmpty()) {
            } else if (searchType.trim().equalsIgnoreCase("SEASON_CODE")) {
                String allgridData = scode;
                if (!allgridData.isEmpty() && allgridData != null) {
                    String[] strArr = allgridData.split(",");
                    for (String sb : strArr) {
                        sbSeasonCode.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.SEASON_CODE);
                strSeasonCode = sbSeasonCode.substring(0, sbSeasonCode.lastIndexOf(","));
                req.setSeasonCodeList(strSeasonCode);
            } else if (searchType.trim().equalsIgnoreCase("MC_BRAND")) {
                String allgridData = MCode;
                if (!allgridData.isEmpty() && allgridData != null) {
                    String[] strArr = allgridData.split(",");
                    for (String sb : strArr) {
                        sbMc.append("'").append(sb).append("'").append(",");
                    }
                }
                String allgridDataBrnad = brand;
                if (!allgridDataBrnad.isEmpty() && allgridDataBrnad != null) {
                    String[] strArr = allgridDataBrnad.split(",");
                    for (String sb : strArr) {
                        sbBrand.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.MC_BRAND);
                strMC = sbMc.substring(0, sbMc.lastIndexOf(","));
                req.setMCList(strMC);
                strBrand = sbBrand.substring(0, sbBrand.lastIndexOf(","));
                req.setBrandList(strBrand);
            } else if (searchType.trim().equalsIgnoreCase("MC_SEASON_CODE")) {
                String allgridData = MCode;
                if (!allgridData.isEmpty() && allgridData != null) {
                    String[] strArr = allgridData.split(",");
                    for (String sb : strArr) {
                        sbMc.append("'").append(sb).append("'").append(",");
                    }
                }
                String allgridDataseason = scode;
                if (!allgridDataseason.isEmpty() && allgridDataseason != null) {
                    String[] strArr = allgridDataseason.split(",");
                    for (String sb : strArr) {
                        sbSeasonCode.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.MC_SEASON_CODE);
                strMC = sbMc.substring(0, sbMc.lastIndexOf(","));
                req.setMCList(strMC);
                strSeasonCode = sbSeasonCode.substring(0, sbSeasonCode.lastIndexOf(","));
                req.setSeasonCodeList(strSeasonCode);
            } else if (searchType.trim().equalsIgnoreCase("BRAND_SEASON_CODE")) {
                String allgridDataBrnad = brand;
                if (!allgridDataBrnad.isEmpty() && allgridDataBrnad != null) {
                    String[] strArr = allgridDataBrnad.split(",");
                    for (String sb : strArr) {
                        sbBrand.append("'").append(sb).append("'").append(",");
                    }
                }
                String allgridDataseason = scode;
                if (!allgridDataseason.isEmpty() && allgridDataseason != null) {
                    String[] strArr = allgridDataseason.split(",");
                    for (String sb : strArr) {
                        sbSeasonCode.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.BRAND_SEASON_CODE);
                strBrand = sbBrand.substring(0, sbBrand.lastIndexOf(","));
                req.setBrandList(strBrand);
                strSeasonCode = sbSeasonCode.substring(0, sbSeasonCode.lastIndexOf(","));
                req.setSeasonCodeList(strSeasonCode);
            } else if (searchType.trim().equalsIgnoreCase("MC_BRAND_SEASON_CODE")) {
                String allgridData = MCode;
                if (!allgridData.isEmpty() && allgridData != null) {
                    String[] strArr = allgridData.split(",");
                    for (String sb : strArr) {
                        sbMc.append("'").append(sb).append("'").append(",");
                    }
                }
                String allgridDataBrnad = brand;
                if (!allgridDataBrnad.isEmpty() && allgridDataBrnad != null) {
                    String[] strArr = allgridDataBrnad.split(",");
                    for (String sb : strArr) {
                        sbBrand.append("'").append(sb).append("'").append(",");
                    }
                }
                String allgridDataseason = scode;
                if (!allgridDataseason.isEmpty() && allgridDataseason != null) {
                    String[] strArr = allgridDataseason.split(",");
                    for (String sb : strArr) {
                        sbSeasonCode.append("'").append(sb).append("'").append(",");
                    }
                }
                req.setArticleSearchEnum(ArticleSearchEnum.MC_BRAND_SEASON_CODE);
                strMC = sbMc.substring(0, sbMc.lastIndexOf(","));
                req.setMCList(strMC);
                strBrand = sbBrand.substring(0, sbBrand.lastIndexOf(","));
                req.setBrandList(strBrand);
                strSeasonCode = sbSeasonCode.substring(0, sbSeasonCode.lastIndexOf(","));
                req.setSeasonCodeList(strSeasonCode);

            } else {
                logger.info("Invalid Search type..");
            }
            req.setIsDownload(false);
            List<ArticleMCVO> lstSearchData = null;
            String rows = request.getParameter("rows");



            String pageno = hdnPageGrid;

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

                req.setPageCount(pageNo);

                ArticleSearchResp searchResponse = ServiceMaster.getOdsService().searchArticledtl(req);

                if (searchResponse.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                    lstSearchData = searchResponse.getArticleMCList();

                    totalCount = searchResponse.getTotalRecordCount();

                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            } else {
                lstSearchData = Collections.<ArticleMCVO>emptyList();
            }

            if (sidx.equals("scode")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleMCVO p1 = (ArticleMCVO) o1;
                        ArticleMCVO p2 = (ArticleMCVO) o2;
                        return p1.getSeasonCode().toString().compareToIgnoreCase(p2.getSeasonCode().toString());
                    }
                });
            } else if (sidx.equals("ano")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleMCVO p1 = (ArticleMCVO) o1;
                        ArticleMCVO p2 = (ArticleMCVO) o2;
                        return p1.getArticleCode().toString().compareToIgnoreCase(p2.getArticleCode().toString());
                    }
                });
            } else if (sidx.equals("adesc")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleMCVO p1 = (ArticleMCVO) o1;
                        ArticleMCVO p2 = (ArticleMCVO) o2;
                        return p1.getArticleDesc().toString().compareToIgnoreCase(p2.getArticleDesc().toString());
                    }
                });
            } else if (sidx.equals("mchid")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleMCVO p1 = (ArticleMCVO) o1;
                        ArticleMCVO p2 = (ArticleMCVO) o2;
                        return p1.getMcCode().toString().compareToIgnoreCase(p2.getMcCode().toString());
                    }
                });
            } else if (sidx.equals("mcname")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleMCVO p1 = (ArticleMCVO) o1;
                        ArticleMCVO p2 = (ArticleMCVO) o2;
                        return p1.getMcDesc().compareToIgnoreCase(p2.getMcDesc().toString());
                    }
                });
            } else if (sidx.equals("brand")) {
                Collections.sort(lstSearchData, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ArticleMCVO p1 = (ArticleMCVO) o1;
                        ArticleMCVO p2 = (ArticleMCVO) o2;
                        return p1.getBrand().toString().compareToIgnoreCase(p2.getBrand().toString());
                    }
                });
            }

            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(lstSearchData);
            }
            for (ArticleMCVO vo : lstSearchData) {
                if (vo.getArticleCode() != null) {
                    cell.add(vo.getArticleCode());
                } else {
                    cell.add("-");
                }

                if (vo.getArticleDesc() != null) {
                    cell.add(vo.getArticleDesc());
                } else {
                    cell.add("-");
                }

                if (vo.getMcCode() != null) {
                    cell.add(vo.getMcCode());
                } else {
                    cell.add("-");
                }

                if (vo.getMcDesc() != null) {
                    cell.add(vo.getMcDesc());
                } else {
                    cell.add("-");
                }

                if (vo.getBrand() != null && vo.getBrand().length() > 0) {
                    cell.add(vo.getBrand());
                } else {
                    cell.add("-");
                }

                if (vo.getPrice() != null) {
                    cell.add(vo.getPrice());
                } else {
                    cell.add("-");
                }

                if (vo.getSeasonCode() != null && vo.getSeasonCode().length() > 0) {
                    cell.add(vo.getSeasonCode());
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
            logger.info("Response Sent!!");


        } catch (Exception e) {
            logger.info("Exception :" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void downloadArticleSearchdtl() {
        logger.info("=========== Inside Download Article Search ===========");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        try {
            ArticleSearchReq req = new ArticleSearchReq();

            String searchType = request.getParameter("searchType");
            String MCode = request.getParameter("mccode");
            String brand = request.getParameter("brand");
            String scode = request.getParameter("seasoncode");
            String pageCount = request.getParameter("pageno");

            StringBuilder sbMc = new StringBuilder();
            StringBuilder sbBrand = new StringBuilder();
            StringBuilder sbSeasonCode = new StringBuilder();
            String strMC, strBrand, strSeasonCode = "";
            if (searchType.trim().equalsIgnoreCase("MC")) {

                req.setMCList(MCode);

            } else if (searchType.trim().equalsIgnoreCase("BRAND")) {

                req.setArticleSearchEnum(ArticleSearchEnum.BRAND);

                req.setBrandList(brand);

            } else if (searchType.trim().equalsIgnoreCase("SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.SEASON_CODE);

                req.setSeasonCodeList(scode);
            } else if (searchType.trim().equalsIgnoreCase("MC_BRAND")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC_BRAND);

                req.setMCList(MCode);

                req.setBrandList(brand);
            } else if (searchType.trim().equalsIgnoreCase("MC_SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC_SEASON_CODE);

                req.setMCList(MCode);

                req.setSeasonCodeList(scode);
            } else if (searchType.trim().equalsIgnoreCase("BRAND_SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.BRAND_SEASON_CODE);

                req.setBrandList(brand);

                req.setSeasonCodeList(scode);
            } else if (searchType.trim().equalsIgnoreCase("MC_BRAND_SEASON_CODE")) {
                req.setArticleSearchEnum(ArticleSearchEnum.MC_BRAND_SEASON_CODE);

                req.setMCList(MCode);

                req.setBrandList(brand);

                req.setSeasonCodeList(scode);

            } else {
                logger.info("Invalid Search type..");
            }
            req.setPageCount(Integer.parseInt(pageCount));
            req.setIsDownload(true);

            out = response.getWriter();
            cellObject = new JSONObject();
            responseData = new JSONObject();

            ArticleSearchResp resp = ServiceMaster.getOdsService().searchArticledtl(req);
            System.out.println("Resp : " + resp.getResp());
            if (resp.getResp().getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                System.out.println("Success");
                cellObject.put("code", "SUCCESS");
                cellObject.put("msg", resp.getResp().getMsg());
                cellObject.put("filePath", resp.getFilePath());
            } else {
                System.out.println("faile");
                cellObject.put("code", "FAIL");
                //cellObject.put("msg", "Fail");
            }

            logger.info("====== File Writing is Completed ===== ");
            responseData.put("rows", cellObject);
            out.println(responseData);
            //return null;
        } catch (Exception e) {
            logger.info("Exception :" + e.getMessage());
            e.printStackTrace();
            //addActionError("Error : "+ e.getMessage());
            //return null;
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
}
