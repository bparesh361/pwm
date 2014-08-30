/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.other.action;

import com.fks.ods.service.ValidateArticleMCVO;
import com.fks.promo.init.DownloadEnum;
import com.fks.promo.init.DownloadSubPromoResp;
import com.fks.promo.init.MstZoneVO;
import com.fks.promo.init.Resp;
import com.fks.promo.init.StoreVO;
import com.fks.promo.init.TransPromoArticleVO;
import com.fks.promo.init.TransPromoConfigVO;
import com.fks.promo.init.TransPromoVO;
import com.fks.promo.master.service.RespCode;
import com.fks.promo.master.service.ValidateMCResp;
import com.fks.promo.task.TeamMemberVO;
import com.fks.promotion.service.ProposalDtlResp;
import com.fks.promotion.service.ProposalVO;
import com.fks.ui.approval.action.ApprovalUtil;
import com.fks.ui.common.action.ActionBase;

import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
 * @author ajitn
 */
public class OtherAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(OtherAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    PrintWriter out;
    private Object strUserID;

    public String viewArticleMCSearch() {
        logger.info("------------------ Inside Other ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in viewArticleMCSearch Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String viewInitiationHelp() {
        logger.info("------------------ Inside Help Search Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in viewArticleMCSearch Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String viewSubPromoInitiationHelp() {
        logger.info("------------------ Inside Help Search Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                logger.info("Valid User .");
                return SUCCESS;
            } else {
                logger.info("InValid User .");
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in viewSubPromoInitiationHelp Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void validateArticleORMCCode() {
        logger.info("------ Inside validating Article OR MC Code.--------");
        String mcCode = "", articleCode = "";
        Long mstPromoId = Long.valueOf(request.getParameter("mstPromoId"));
        logger.info("promo id : " + mstPromoId);
        String isArticleEntered = request.getParameter("isArticleEntered");
        logger.info("------- Is Article entered -------- " + isArticleEntered);
        String IsInitiationFlag = request.getParameter("IsInitiationFlag");
        logger.info(" initiation :" + IsInitiationFlag);
        JSONObject responseData = new JSONObject();
        if (isArticleEntered.equalsIgnoreCase("0")) {
            mcCode = request.getParameter("mcCode");
            logger.info("------- calling MC CODE Validate Service -------- " + mcCode);
            ValidateMCResp serviceResp = ServiceMaster.getOtherMasterService().validateMC(mcCode, mstPromoId, IsInitiationFlag);
            logger.info("----- Validate MC Resp : " + serviceResp.getResp().getRespCode());

            if (serviceResp.getResp().getRespCode() == RespCode.FAILURE) {
                responseData.put("respCode", "0");
                responseData.put("respMsg", serviceResp.getResp().getMsg());
            } else {
                responseData.put("respCode", "1");
                responseData.put("respMsg", serviceResp.getResp().getMsg());
                responseData.put("articleCode", "-");
                responseData.put("articleDesc", "-");
                responseData.put("mcCode", serviceResp.getMcCode());
                responseData.put("mcDesc", serviceResp.getMcDesc().replaceAll(",", " "));
                responseData.put("brandCode", "-");
                responseData.put("brandDesc", "-");
            }
        } else {
            logger.info("------- calling ARTICLE CODE ODS Validate Service -------- ");
            articleCode = request.getParameter("articleCode");
            logger.info("article COde " + articleCode);



            ValidateArticleMCVO serviceResp = ServiceMaster.getOdsService().searchODSArticle(articleCode, mstPromoId, IsInitiationFlag);
            logger.info("----- Validate ARTICLE Resp : " + serviceResp.isIsErrorStatus());
            logger.info("----- Validate ARTICLE Resp : " + serviceResp.getErrorMsg());
            if (serviceResp.isIsErrorStatus() == true) {
                responseData.put("respCode", "0");
                responseData.put("respMsg", serviceResp.getErrorMsg());
            } else {
                responseData.put("respCode", "1");
                responseData.put("respMsg", serviceResp.getErrorMsg());
                responseData.put("articleCode", serviceResp.getArticleCode());
                responseData.put("articleDesc", serviceResp.getArticleDesc().replaceAll(",", " "));
                responseData.put("mcCode", serviceResp.getMcCode());
                responseData.put("mcDesc", serviceResp.getMcDesc().replaceAll(",", " "));
                responseData.put("brandCode", serviceResp.getBrandCode());
                responseData.put("brandDesc", serviceResp.getBrandDesc().replaceAll(",", " "));
//                System.out.println("------ Brand Code : " + serviceResp.getBrandCode());
//                System.out.println("------ Brand Desc : " + serviceResp.getBrandDesc());
            }
        }

        try {
            out = response.getWriter();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(OtherAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.println(responseData);
    }

    public void flexBoxTeamMember() {
        logger.info("=== Inside Team Member Flex Box search ====");
        JSONObject responseData = null;
        JSONObject cellObject = null;
        JSONArray cellArray = null;
        try {
            out = response.getWriter();
            cellArray = new JSONArray();
            responseData = new JSONObject();
            String empname = request.getParameter("txtuserempName");
            String empNameCode;
            String storeCode = (String) getSessionMap().get(WebConstants.STORE_CODE);
            List<TeamMemberVO> lstEmployee = ServiceMaster.getTaskService().getAllTeamMembers(storeCode);
            if (!lstEmployee.isEmpty() && lstEmployee != null) {
                for (TeamMemberVO vo : lstEmployee) {
                    empNameCode = vo.getEmpName().concat(" : ").concat(vo.getEmpCode().toString());
                    if (empNameCode.toLowerCase().startsWith(empname)) {
                        cellObject = new JSONObject();
                        cellObject.put("id", vo.getEmpCode());
                        cellObject.put("name", empNameCode);
                        cellArray.add(cellObject);
                    }
                }
                responseData.put("results", cellArray);
                out.println(responseData);
            }
        } catch (Exception e) {
            logger.fatal("Exception in flexBoxTeamMember() method of OtherAction.");
            e.printStackTrace();
        }
    }

    public void downloadSampleArticleMCFile() {
        logger.info("==== Inside Download Sample Article MC Code File..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "ArticleCode_MCCode_sample.csv";
            sb.append("PLEASE ENTER ARTICLE CODE OR MC CODE FIELD.");
            sb.append("\n");
            sb.append("\n");
            sb.append("Article Code");
            sb.append(",");
            sb.append("MC Code");
            sb.append("\n");

            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

//            response.setContentType("text/csv");
            response.setContentType("application/txt");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }

    }

    public void downloadProposalArticleMCFile() {
        String proposalId = request.getParameter("proposalId");
        logger.info("==== Inside Download Proposal Article MC Code File For ID : " + proposalId);
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Proposal_Request_P" + proposalId + ".csv";
            sb.append("PLEASE ENTER ARTICLE CODE OR MC CODE AND QTY FIELD.");
            sb.append("\n");
            sb.append("\n");
            sb.append("Article Code");
            sb.append(",");
            sb.append("MC Code");
            sb.append("\n");

            ProposalDtlResp proposalResp = ServiceMaster.getPromotionProposalService().getProposalDtl(Long.valueOf(proposalId), true);
            if (proposalResp.getResp().getRespCode().value().equalsIgnoreCase("SUCCESS")) {
                ProposalVO vo = proposalResp.getProposal();
                List<com.fks.promotion.service.ArticleMCVO> list = vo.getArticleList();
                if (list != null && list.size() > 0) {
                    for (com.fks.promotion.service.ArticleMCVO articleVO : list) {
                        if (articleVO.getArticleCode().equalsIgnoreCase("-")) {
                            sb.append(",");
                            sb.append(articleVO.getMcCode());
                        } else {
                            sb.append(articleVO.getArticleCode());
                            sb.append(",");
                        }
                        sb.append("\n");
                    }
                } else {
                    sb.append("No Article/MC Detail Found.");
                }
            }
            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }

    }

    public void downloadIntiationSampleArticleMCFile() {
        logger.info("==== Inside Download Intitation Sample Article MC Code File..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "ArticleCode_MCCode_sample.csv";
            sb.append("PLEASE ENTER ARTICLE CODE OR MC CODE AND QTY FIELD.");
            sb.append("\n");
            sb.append("\n");
            sb.append("Article Code");
            sb.append(",");
            sb.append("MC Code");
            sb.append(",");
            sb.append("QTY");
            sb.append("\n");

            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }

    }
    private String errorFilePath;

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public void downloadErrorArticleMCFile() {
        logger.info("@@@@ Download File @@@@");
        try {
            File file = new File(errorFilePath);
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
            fin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void downloadFileFromFilePath() {
        try {
            System.out.println("----------- Inside Download File.--------");
            File file = new File(filePath);
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
        } catch (Exception ex) {
        }
    }

    public void downloadSubPromotionRequestForUpdate() {
        try {
            String transpromoID = request.getParameter("id");
            logger.info("Trans promo id : " + transpromoID);
            DownloadSubPromoResp subPromoDownload = ServiceMaster.getTransPromoService().downloadSubPromoRequest(Long.valueOf(transpromoID));
            if (subPromoDownload.getResp().getRespCode() == com.fks.promo.init.RespCode.SUCCESS) {
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

        } catch (Exception ex) {
            logger.error("Error While Downloading SubPromo Request For Update");
            ex.printStackTrace();
        }
    }

    public void downloadPromontiondtl() {
        logger.info("@@@@@@@@@ Inside downloadPromontiondtl ======");
        String transpromoID = request.getParameter("id");
        logger.info("Trans promo id : " + transpromoID);
        try {
            ApprovalUtil util = new ApprovalUtil();
            List<TransPromoVO> listPromo = util.getAllTramsPromotionReqWithID(transpromoID);
            StringBuffer sb = new StringBuffer();

            writeHeader(sb);//write the header

            writecontent(sb, listPromo);

            //opening the file
            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
            String currentDate = getDateString(new Date());
            logger.info("Date is " + currentDate);
            String fileName = "PromotionArticalDetail" + currentDate + ".csv";
            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("======file opening is completed=====");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadMultiPromontiondtl() {
        logger.info("@@@@@@@@@ Inside downloadMultiPromontiondtl ======");
        String transpromoID = request.getParameter("id");
        logger.info("Trans promo id : " + transpromoID);
        try {
            ApprovalUtil util = new ApprovalUtil();
            List<TransPromoVO> listPromo = util.getAllTramsPromotionReqWithID(transpromoID);
            StringBuffer sb = new StringBuffer();

//            DownloadFileUtil.writeHeader(sb);//write the header
//
//            DownloadFileUtil.writecontent(sb, listPromo);

            //opening the file
            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
            String currentDate = getDateString(new Date());
            logger.info("Date is " + currentDate);
            String fileName = "PromotionArticalDetail" + currentDate + ".csv";
            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("======file opening is completed=====");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeHeader(StringBuffer sb) throws Exception {
        sb.append("Zone Description ");//1
        sb.append(",");
        sb.append("Site code");//2
        sb.append(",");
        sb.append("Site Description");//3
        sb.append(",");
        sb.append("Format");//4
        sb.append(",");
        sb.append("Promotion Type");//5
        sb.append(",");
        sb.append("Promo Details");//6
        sb.append(",");
        sb.append("Group Details");//7
        sb.append(",");
        sb.append("Article QTY");//8
        sb.append(",");
        sb.append("Articles");//9
        sb.append(",");
        sb.append("Article Desc");//10
        sb.append(",");
        sb.append("MRP ");//11
        sb.append(",");
        sb.append("MC Code");//12
        sb.append(",");
        sb.append("MC description");//13
        sb.append(",");
        sb.append("Percentage Value discount for combination ");//14
        sb.append(",");
        sb.append("Validity Date FROM");//15
        sb.append(",");
        sb.append("Validity Date TO");//16
        sb.append(",");
        sb.append("Net Margin Achievement");//17
        sb.append(",");
        sb.append("Growth in Ticket size");//18
        sb.append(",");
        sb.append("Sell thru  vs quantity");//19
        sb.append(",");
        sb.append("Growth in conversions");//20
        sb.append(",");
        sb.append("Sales Growth  for value");//21
        sb.append(",");
        sb.append("Sales Growth for QTY");//22
        sb.append("\n");

        logger.info("Header write in a file is completed successfully.......");
    }

    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }

    private void writecontent(StringBuffer sb, List<TransPromoVO> listPromo) throws Exception {
        logger.info("@@@@ WriteContent =======");
        int lineCount = 1;
        int lineCountnew = 1;
        for (TransPromoVO tpvo : listPromo) {
            //first coloum zine
            int x = 1;
            for (MstZoneVO zoneVo : tpvo.getZoneDesc()) {
                //logger.info(zoneVo.getZonename());
                sb.append(zoneVo.getZonename());
                if (tpvo.getZoneDesc().size() != x) {
                    sb.append("|");
                }
                x++;
            }
            sb.append(",");
            //Second coloum sitecode
            x = 1;
            for (StoreVO store : tpvo.getLstStoreVOs()) {
                sb.append(store.getStoreID());
                if (tpvo.getLstStoreVOs().size() != x) {
                    sb.append("|");
                }
                x++;
            }
            sb.append(",");
            //Third coloum site desc
            x = 1;
            for (StoreVO store : tpvo.getLstStoreVOs()) {
                sb.append(store.getStoreDesc());
                if (tpvo.getLstStoreVOs().size() != x) {
                    sb.append("|");
                }
                x++;
            }
            sb.append(",");
            //Forth coloum  format
            x = 1;
            for (String format : tpvo.getFormat()) {
                sb.append(format);
                if (tpvo.getFormat().size() != x) {
                    sb.append("|");
                }
                x++;
            }

            sb.append(",");
            //fifth coloum  promotion dtl
            sb.append(tpvo.getPromotionType());
            sb.append(",");
            //six coloum promotion desc
            sb.append(tpvo.getPromoTypeDesc());
            sb.append(",");
//            }else{
//               sb.append("- ");//7: set group
//                sb.append(",");
//                sb.append("- ");//7: set group
//                sb.append(",");
//                sb.append(" -");//7: set group
//                sb.append(",");
//                sb.append(" -");//7: set group
//                sb.append(",");
//                sb.append("- ");//7: set group
//                sb.append(",");
//                sb.append(" -");//7: set group
//                sb.append(",");
//            }

            sb.append("\n");
            String strArticle = writeArticle(tpvo.getTransPromoArticleList());
            sb.append(strArticle);

            String strconfig = writeConfig(tpvo.getTransPromoConfigList());
            sb.append(strconfig);
            sb.append("\n");
            /*
            for (TransPromoArticleVO articleVO : tpvo.getTransPromoArticleList()) {

            logger.info("=======================================================" + lineCount);
            if (lineCount > 1) {
            sb.append(" ");//7: set group
            sb.append(",");
            sb.append(" ");//7: set group
            sb.append(",");
            sb.append(" ");//7: set group
            sb.append(",");
            sb.append(" ");//7: set group
            sb.append(",");
            sb.append(" ");//7: set group
            sb.append(",");
            sb.append(" ");//7: set group
            sb.append(",");
            }

            sb.append(articleVO.getGroup());//7: set group
            sb.append(",");
            sb.append(articleVO.getQty());//8:articleqty
            sb.append(",");
            sb.append(articleVO.getArtCode());//9 article code
            sb.append(",");
            sb.append(articleVO.getArtDesc());//10 article disc
            sb.append(",");
            sb.append(articleVO.getMrp());//11: Mrp
            sb.append(",");
            sb.append(articleVO.getMcCode());//12: MC code
            sb.append(",");
            sb.append(articleVO.getMcDesc());// 13: MC desc
            sb.append(",");
            lineCount++;
            }
             */
//            for (TransPromoConfigVO configVO : tpvo.getTransPromoConfigList()) {
//                System.out.println("Line : " + lineCount + "   New: " + lineCountnew);
//                if (lineCountnew >= 7) {
//                    logger.info(" #############  Listcount : " + lineCount);
//                    sb.append("");
//                    sb.append(",");
//                }
//                sb.append(configVO.getDiscValue());//14disc value
//                sb.append(",");
//                sb.append(configVO.getValidFrom());//15 valid date
//                sb.append(",");
//                sb.append(configVO.getValidTo());// 16 valid to
//                sb.append(",");
//                sb.append(configVO.getMarginAchievement());//17 margin achiement
//                sb.append(",");
//                sb.append(configVO.getTicketSizeGrowth());//18 growth ticket size
//                sb.append(",");
//                sb.append(configVO.getSellThruQty());// 19 : sell thry qty
//                sb.append(",");
//                sb.append(configVO.getGrowthCoversion());// 20 :Growth conversion
//                sb.append(",");
//                sb.append(configVO.getSalesGrowth());// 21 : sell growth value
//                sb.append(",");
//                sb.append(configVO.getQty());//22 : sell growth qty
//            }
//            sb.append("\n");
//
//            lineCountnew++;


            //  sb.append("\n");
        }//end of TransPromoVO for loop
    }

    public String writeArticle(List<TransPromoArticleVO> listPromo) {
        StringBuffer sb = new StringBuffer();
        int lineCount = 1;
        for (TransPromoArticleVO articleVO : listPromo) {

            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");

            if (articleVO.getGroup() != null) {
                sb.append(articleVO.getGroup());//7: set group
            } else {
                sb.append("");
            }
            sb.append(",");
            sb.append(articleVO.getQty());//8:articleqty
            sb.append(",");
            sb.append(articleVO.getArtCode());//9 article code
            sb.append(",");
            sb.append(articleVO.getArtDesc());//10 article disc
            sb.append(",");
            sb.append(articleVO.getMrp());//11: Mrp
            sb.append(",");
            sb.append(articleVO.getMcCode());//12: MC code
            sb.append(",");
            sb.append(articleVO.getMcDesc());// 13: MC desc
            sb.append("\n");
//            sb.append(",");
//            sb.append("- ");//7: set group
//            sb.append(",");
//            sb.append("- ");//7: set group
//            sb.append(",");
//            sb.append(" -");//7: set group
//            sb.append(",");
//            sb.append(" -");//7: set group
//            sb.append(",");
//            sb.append("- ");//7: set group
//            sb.append(",");
//            sb.append(" -");//7: set group
//            sb.append(",");
//            sb.append("- ");//7: set group
//            sb.append(",");
//            sb.append("- ");//7: set group
//            sb.append(",");
//            sb.append(" -");//7: set group

            lineCount++;

        }
        return sb.toString();
    }

    public String writeConfig(List<TransPromoConfigVO> listPromo) {
        int lineCountnew = 1;
        StringBuffer sb = new StringBuffer();

        for (TransPromoConfigVO configVO : listPromo) {
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append(" ");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append("");//7: set group
            sb.append(",");
            sb.append(configVO.getDiscValue());//14disc value
            sb.append(",");
            sb.append(configVO.getValidFrom());//15 valid date
            sb.append(",");
            sb.append(configVO.getValidTo());// 16 valid to
            sb.append(",");
            sb.append(configVO.getMarginAchievement());//17 margin achiement
            sb.append(",");
            sb.append(configVO.getTicketSizeGrowth());//18 growth ticket size
            sb.append(",");
            sb.append(configVO.getSellThruQty());// 19 : sell thry qty
            sb.append(",");
            sb.append(configVO.getGrowthCoversion());// 20 :Growth conversion
            sb.append(",");
            sb.append(configVO.getSalesGrowth());// 21 : sell growth value
            sb.append(",");
            sb.append(configVO.getQty());//22 : sell growth qty
            sb.append("\n");
            lineCountnew++;
        }
        return sb.toString();
    }

    public void downloadSamplePromotionFile() {
        logger.info("==== Inside downloadSamplePromotionFile ..");
        try {
            StringBuilder sb = new StringBuilder();
            String fileName = "";
            fileName = "Promotion_Template.csv";
            sb.append("PromotionType");
            sb.append(",");
            sb.append("Remarks");
            sb.append(",");
            sb.append("Set");
            sb.append(",");
            sb.append("Articles");
            sb.append(",");
            sb.append("MC");
            sb.append(",");
            sb.append("ArticleQty");
            sb.append(",");
            sb.append("ValidFrom");
            sb.append(",");
            sb.append("ValidTo");
            sb.append(",");
            sb.append("Margin Achievement");
            sb.append(",");
            sb.append("Growth in Ticket size");
            sb.append(",");
            sb.append("Sell thru  vs quantity");
            sb.append(",");
            sb.append("Growth in conversions");
            sb.append(",");
            sb.append("Sales Growth for QTY & value");
            sb.append(",");
            sb.append("DiscountConfiCode");
            sb.append(",");
            sb.append("DiscountConfigValue");
            sb.append(",");
            sb.append("Qty");
            sb.append(",");
            sb.append("Buy Worth Amount");
            sb.append(",");
            sb.append("Buy");
            sb.append(",");
            sb.append("Get");

            byte requestBytes[] = sb.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);

            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
        } catch (Exception ex) {
            logger.info("Exception :" + ex.getMessage());
        }

    }
    private String transId, empID;

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void downloadMultipleTransPromoINExcel() {
        logger.info("@@@@@@@ downloadMultipleTransPromoINExcel @@@@@@");
        try {
            System.out.println("Trans Id : " + transId);
            System.out.println("Emp Id : " + empID);
            List<Long> transReqIdList = new ArrayList<Long>();
            String[] transIdArr = transId.split(",");
            for (String s : transIdArr) {
                transReqIdList.add(Long.valueOf(s));
            }
            String downloadType = request.getParameter("downloadType");
            DownloadEnum downEnum = null;
            if (downloadType.equalsIgnoreCase("INITIATOR_TO_EXIGENCY_DOWNLOAD")) {
                downEnum = DownloadEnum.INITIATOR_TO_EXIGENCY_DOWNLOAD;
            } else if (downloadType.equalsIgnoreCase("MANAGER_EXECUTOR_DOWNLOAD")) {
                downEnum = DownloadEnum.MANAGER_EXECUTOR_DOWNLOAD;
            } else {
                downEnum = DownloadEnum.COMMUNICATION_DOWNLOAD;
            }
            Resp resp = ServiceMaster.getTransPromoService().downloadSubPromoDtl(transReqIdList, Long.valueOf(empID), downEnum);
            if (resp.getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                System.out.println("File Resp : " + resp.getMsg());
                if (resp.getMsg() != null) {
                    FileInputStream fin = null;
                    DataInputStream din = null;
                    File file = null;
                    byte[] buffer = null;
                    try {
                        buffer = new byte[1024];

                        file = new File(resp.getMsg());
                        fin = new FileInputStream(file);
                        din = new DataInputStream(fin);
                        logger.info("Din..." + din);
                        ServletOutputStream sout = response.getOutputStream();
                        response.setContentType("application/vnd.ms-excel");
                        response.setContentLength((int) file.length());
                        String contentDisposition = " :attachment;";
                        response.setHeader("Content-Disposition", String.valueOf((new StringBuffer(String.valueOf(contentDisposition))).append("filename=").append(file.getName())));

                        while (din != null && din.read(buffer) != -1) {
                            sout.write(buffer);
                        }

                    } catch (IOException ex) {
                        logger.fatal("Exception generated while downoading file : Exception is " + ex.getLocalizedMessage());
                        throw new FileNotFoundException("File Not Found: " + resp.getMsg());
                    } finally {
                        if (din != null) {
                            din.close();
                            din = null;
                        }

                        if (fin != null) {
                            fin.close();
                            fin = null;
                        }

                        buffer = null;
                        file = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downloadMultipleProposalsINExcel() {
        logger.info("@@@@@@@ downloadMultipleProposalsINExcel @@@@@@");
        try {
            System.out.println("Proposal Id : " + transId);
            System.out.println("Emp Id : " + empID);
            List<Long> ReqIdList = new ArrayList<Long>();
            String[] transIdArr = transId.split(",");
            for (String s : transIdArr) {
                ReqIdList.add(Long.valueOf(s));
            }
            com.fks.promotion.service.Resp resp = ServiceMaster.getPromotionProposalService().downloadMultipleProposalDtl(ReqIdList, Long.valueOf(empID));

            if (resp.getRespCode().value().toString().equalsIgnoreCase(WebConstants.success)) {
                System.out.println("File Resp : " + resp.getMsg());
                if (resp.getMsg() != null) {
                    FileInputStream fin = null;
                    DataInputStream din = null;
                    File file = null;
                    byte[] buffer = null;
                    try {
                        buffer = new byte[1024];

                        file = new File(resp.getMsg());
                        fin = new FileInputStream(file);
                        din = new DataInputStream(fin);
                        logger.info("Din..." + din);
                        ServletOutputStream sout = response.getOutputStream();
                        response.setContentType("application/vnd.ms-excel");
                        response.setContentLength((int) file.length());
                        String contentDisposition = " :attachment;";
                        response.setHeader("Content-Disposition", String.valueOf((new StringBuffer(String.valueOf(contentDisposition))).append("filename=").append(file.getName())));

                        while (din != null && din.read(buffer) != -1) {
                            sout.write(buffer);
                        }

                    } catch (IOException ex) {
                        logger.fatal("Exception generated while downoading file : Exception is " + ex.getLocalizedMessage());
                        throw new FileNotFoundException("File Not Found: " + resp.getMsg());
                    } finally {
                        if (din != null) {
                            din.close();
                            din = null;
                        }

                        if (fin != null) {
                            fin.close();
                            fin = null;
                        }

                        buffer = null;
                        file = null;
                    }
                }
            }
        } catch (Exception e) {
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
}
