/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eks.ods.article;

import com.eks.ods.article.vo.ArticleVO;
import com.eks.ods.article.vo.Resp;
import com.eks.ods.article.vo.RespCode;
import com.eks.ods.article.vo.SearchArticleResp;
import com.fks.ods.vo.ArticleSearchEnum;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
public class ArticleSearch {

    private static final Logger logger = Logger.getLogger(ArticleSearch.class.getName());
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    static Context ctx = null;
    static DataSource ds = null;
    static String sapClientECC6 = " and sap_client='900' ";

    static {
        try {
            if (ds == null) {
                ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("jdbc/ods");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SearchArticleResp searchArticle(String articleCode) {
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            if (articleCode != null && articleCode.length() > 18) {
                return new SearchArticleResp(new Resp(RespCode.FAILURE, "Article Can not be greater than 18 characters."));
            }
            if (articleCode != null && articleCode.length() < 18) {
                articleCode = StringUtils.leftPad(articleCode, 18, "0");
                logger.info("Article Code : " + articleCode);
            }
//            rs = stmt.executeQuery("select art_desc,article.mc_code,mc_desc from  article, mch where art_code='" + articleCode + "' and article.mc_code = mch.mc_code " + sapClientECC6 + " order by article.load_date desc");
            rs = stmt.executeQuery("select art_desc,article.mc_code,mc_desc,brand.brand_code,brand.brand_desc "
                    + " from  article, mch ,brand  where art_code='" + articleCode + "' "
                    + "and article.mc_code = mch.mc_code  and article.brand_code=brand.brand_code"
                    + sapClientECC6 + " order by article.load_date desc");
            if (rs != null) {
            }
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            while (rs.next()) {
                String artDesc = rs.getString("art_desc").replaceAll(",", " ");
                String mcCode = rs.getString("mc_code");
                String mcDesc = rs.getString("mc_desc").replaceAll(",", " ");
                String mrp = "10.20";
                String brandCode = rs.getString("brand_code").replaceAll(",", " ");
                String brandDesc = rs.getString("brand_desc").replaceAll(",", " ");
                ArticleVO artVo = new ArticleVO(articleCode, artDesc, mcCode, mcDesc);
                artVo.setMrp(mrp);
                artVo.setBrand(brandCode);
                artVo.setBrandDesc(brandDesc);
                list.add(artVo);
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "Success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleList(List<String> articleCodes) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            con = ds.getConnection();
            stmt = con.prepareStatement("select art_desc,article.mc_code,mc_desc ,brand_code,season_code from  article, mch where art_code=? and article.mc_code = mch.mc_code " + sapClientECC6 + " order by article.load_date desc");
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            for (String articleCode : articleCodes) {
                ArticleVO vo = null;
                if (articleCode != null && articleCode.length() > 18) {
                    vo = new ArticleVO(RespCode.FAILURE, "Article Can not be greater than 18 characters.");
                    list.add(vo);
                    continue;
                }
                if (articleCode != null && articleCode.length() < 18) {
                    articleCode = StringUtils.leftPad(articleCode, 18, "0");
                    logger.info("Article Code : " + articleCode);
                }
                stmt.setString(1, articleCode);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";
                    vo = new ArticleVO(articleCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);
                } else {
                    vo = new ArticleVO(RespCode.FAILURE, "Article Code Not Found.");
                    list.add(vo);

                }
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String writeArticleFile(ResultSet rs) throws IOException, Exception {

        BufferedWriter writer = null;

        String filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.ARTICLE_DOWNLOAD_FILE);
        Calendar cal = new GregorianCalendar();
        String fileName = "ArticleDtl" + cal.getTimeInMillis() + ".csv";
        String downloadFilePath = filePath + fileName;
        writer = new BufferedWriter(new FileWriter(downloadFilePath));
        if (writer == null) {
            throw new Exception("File Name is Not Correct " + fileName);
        }
        writer.write("Article Code");
        writer.write(",");
        writer.write("Article Description");
        writer.write(",");
        writer.write("MC Code");
        writer.write(",");
        writer.write("MC Description");
        writer.write(",");
        writer.write("Brand");
        writer.write(",");
        writer.write("Price");
        writer.write(",");
        writer.write("Season Code");
        writer.write("\n");

        while (rs.next()) {
            String artCode = rs.getString("art_code");
            String artDesc = rs.getString("art_desc");
            String mcCode = rs.getString("mc_code");
            String mcDesc = rs.getString("mc_desc");
            String brand = rs.getString("brand_code");
            String price = "-";
            String seasoncode = rs.getString("season_code");
            String mrp = "-";

            artCode = artCode.replaceAll(",", "");
            artDesc = artDesc.replaceAll(",", "");
            mcCode = mcCode.replaceAll(",", "");
            mcDesc = mcDesc.replaceAll(",", "");
            brand = brand.replaceAll(",", "");
            if (seasoncode != null) {
                seasoncode = seasoncode.replaceAll(",", "");
            } else {
                seasoncode = "-";
            }

            writer.write(artCode);
            writer.write(",");
            writer.write(artDesc);
            writer.write(",");
            writer.write(mcCode);
            writer.write(",");
            writer.write(mcDesc);
            writer.write(",");
            writer.write(brand);
            writer.write(",");
            writer.write(price);
            writer.write(",");
            if (seasoncode != null) {
                writer.write(seasoncode);
            }
            writer.write("\n");
        }

        writer.flush();

        if (writer != null) {
            writer.close();
        }
        return downloadFilePath;
    }

    public long searchTotalCount(ArticleSearchEnum articleSearchEnum, String mcCodes, String brandCodes, String seasonCodes) {
        long totalCount = 0;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            con = ds.getConnection();
            switch (articleSearchEnum) {
                case MC:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where article.mc_code IN (" + mcCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
                case BRAND:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where  article.BRAND_CODE IN (" + brandCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
                case SEASON_CODE:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where  article.SEASON_CODE IN (" + seasonCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
                case MC_BRAND:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where  article.mc_code IN (" + mcCodes + ") and article.BRAND_CODE IN (" + brandCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
                case MC_SEASON_CODE:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where article.mc_code IN (" + mcCodes + ") and article.SEASON_CODE IN (" + seasonCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
                case BRAND_SEASON_CODE:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where article.BRAND_CODE IN (" + brandCodes + ") and article.SEASON_CODE IN (" + seasonCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
                case MC_BRAND_SEASON_CODE:
                    stmt = con.prepareStatement("select count(*) from  ODS.article where  article.mc_code IN (" + mcCodes + ") and article.BRAND_CODE IN (" + brandCodes + ") and article.SEASON_CODE IN (" + seasonCodes + ") " + sapClientECC6);
                    rs = stmt.executeQuery();
                    break;
            }
            if (rs.next()) {
                totalCount = Integer.parseInt(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public SearchArticleResp searchArticleListMCWise(String mcCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();
            logger.info("Search Article Mc : " + mcCodes);
            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and mch.mc_code IN (" + mcCodes + ") and rownum <= " + endRange + "" + sapClientECC6 + " )"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListBrandWise(String brandCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();
            logger.info("Search Article By Brand : " + brandCodes);
            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and article.BRAND_CODE IN (" + brandCodes + ") and rownum <= " + endRange + " " + sapClientECC6 + ")"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListSeasonCodeWise(String seasonCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();
            logger.info("Search Article By Brand : " + seasonCodes);
            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and article.SEASON_CODE IN (" + seasonCodes + ") and rownum <= " + endRange + " " + sapClientECC6 + ")"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListMCBrandWise(String mcCodes, String brandCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();
            logger.info("Search Article Mc : " + mcCodes);
            logger.info("Search Article Brand : " + brandCodes);
            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and mch.mc_code IN (" + mcCodes + ") and article.BRAND_CODE IN (" + brandCodes + ") and rownum <= " + endRange + " " + sapClientECC6 + ")"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListMCSeasonWise(String mcCodes, String seasonCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();
            logger.info("Search Article Mc : " + mcCodes);
            logger.info("Search Article Season Code : " + seasonCodes);
            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and mch.mc_code IN (" + mcCodes + ") and article.SEASON_CODE IN (" + seasonCodes + ") and rownum <= " + endRange + " " + sapClientECC6 + ")"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListBrandSeasonWise(String brandCodes, String seasonCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();
            logger.info("Search Article Brand : " + brandCodes);
            logger.info("Search Article Season Code : " + seasonCodes);

            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and article.BRAND_CODE IN (" + brandCodes + ") and article.SEASON_CODE IN (" + seasonCodes + ") and rownum <= " + endRange + " " + sapClientECC6 + ")"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListMCBrandSeasonWise(String mcCodes, String brandCodes, String seasonCodes, boolean isDownload, Long startRange, Long endRange) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            con = ds.getConnection();

            logger.info("Search Article MC : " + mcCodes);
            logger.info("Search Article Brand : " + brandCodes);
            logger.info("Search Article Season Code : " + seasonCodes);
            stmt = con.prepareStatement("select * from  ( "
                    + "select rownum sr_no, art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code "
                    + "from  ODS.article, ODS.mch where article.mc_code = mch.mc_code and mch.mc_code IN (" + mcCodes + ") and article.BRAND_CODE IN (" + brandCodes + ") and article.SEASON_CODE IN (" + seasonCodes + ") and rownum <= " + endRange + " " + sapClientECC6 + ")"
                    + "where sr_no>=" + startRange + " and  sr_no<=" + endRange);
            rs = stmt.executeQuery();

            if (isDownload) {
                String downloadFilePath = writeArticleFile(rs);
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), downloadFilePath);
            } else {
                List<ArticleVO> list = new ArrayList<ArticleVO>();
                ArticleVO vo = null;

                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";

                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);


                }

                logger.info("Article List Size Mc Wise : " + list.size());
                return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleByMCCode(String mcCode) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            if (mcCode == null && mcCode.trim().isEmpty()) {
                return new SearchArticleResp(new Resp(RespCode.FAILURE, "MC Can not be null or empty."));
            }
            rs = stmt.executeQuery("select art_code,art_desc,mc_code from  article where mc_code='" + mcCode + "' " + sapClientECC6 + " and rownum<=15");
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            while (rs.next()) {
                String artCode = rs.getString("art_code");
                String artDesc = rs.getString("art_desc");
                String mcCodedb = rs.getString("mc_code");
                ArticleVO artVo = new ArticleVO(artCode, artDesc, mcCodedb);
                list.add(artVo);
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "Success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {
                {

                    if (stmt != null) {
                        stmt.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListBrandWise(List<String> brands) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.prepareStatement("select art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code from  ODS.article,mch where article.BRAND_CODE=? " + sapClientECC6 + " and rownum<=15");
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            for (String br : brands) {
                ArticleVO vo = null;

                stmt.setString(1, br);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";
                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);
                }
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {

                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleByBrand(String brand) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            if (brand == null && brand.trim().isEmpty()) {
                return new SearchArticleResp(new Resp(RespCode.FAILURE, "Brand Can not be null or empty."));
            }
            rs = stmt.executeQuery("select art_code,art_desc,mc_code from  article where brand_code='" + brand + "' and " + sapClientECC6 + " rownum<=15");
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            while (rs.next()) {
                String artCode = rs.getString("art_code");
                String artDesc = rs.getString("art_desc");
                String mcCodedb = rs.getString("mc_code");
                ArticleVO artVo = new ArticleVO(artCode, artDesc, mcCodedb);
                list.add(artVo);
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "Success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleListSeasonCodeWise(List<String> scode) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.prepareStatement("select art_code,art_desc,mch.mc_code,mch.MC_DESC ,brand_code,season_code from  ODS.article,mch where article.SEASON_CODE=? " + sapClientECC6 + " and rownum<=15");
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            for (String sc : scode) {
                ArticleVO vo = null;

                stmt.setString(1, sc);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String artCode = rs.getString("art_code");
                    String artDesc = rs.getString("art_desc");
                    String mcCode = rs.getString("mc_code");
                    String mcDesc = rs.getString("mc_desc");
                    String brand = rs.getString("brand_code");
                    String seasoncode = rs.getString("season_code");
                    String mrp = "-";
                    vo = new ArticleVO(artCode, artDesc, mcCode, mcDesc, brand, seasoncode);
                    vo.setMrp(mrp);
                    vo.setRespCode(RespCode.SUCCESS);
                    vo.setMsg("success");
                    list.add(vo);
                }
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {

                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SearchArticleResp searchArticleBySeasonCode(String seasoncode) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            if (seasoncode == null && seasoncode.trim().isEmpty()) {
                return new SearchArticleResp(new Resp(RespCode.FAILURE, "Season Code Can not be null or empty."));
            }
            rs = stmt.executeQuery("select art_code,art_desc,mc_code from  article where season_code='" + seasoncode + "' " + sapClientECC6 + " and rownum<=15");
            List<ArticleVO> list = new ArrayList<ArticleVO>();
            while (rs.next()) {
                String artCode = rs.getString("art_code");
                String artDesc = rs.getString("art_desc");
                String mcCodedb = rs.getString("mc_code");
                ArticleVO artVo = new ArticleVO(artCode, artDesc, mcCodedb);
                list.add(artVo);
            }
            return new SearchArticleResp(new Resp(RespCode.SUCCESS, "Success"), list);
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchArticleResp(new Resp(RespCode.FAILURE, "Error While Retrieving Article"));
        } finally {
            try {

                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
