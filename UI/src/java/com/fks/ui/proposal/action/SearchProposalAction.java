/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.proposal.action;

import com.fks.promo.init.Resp;
import com.fks.promotion.service.ArticleMCVO;
import com.fks.promotion.service.ProposalSearchType;
import com.fks.promotion.service.ProposalVO;
import com.fks.promotion.service.RespCode;
import com.fks.promotion.service.SearchProposalReq;
import com.fks.promotion.service.SearchProposalResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author Paresb
 */
public class SearchProposalAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(ProposalAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String strUserID;

    public String execute() {
        logger.info("------------------ Welcome Proposal SearchProposalAction Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of SearchProposalAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllProposalByUser() {
        logger.info("-----------  Inside Fetching Proposal Details. Service :getProposalSearchService().getAllProposals --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = response.getWriter();
            out = response.getWriter();
            String userId = null;
            // TOODO Adding Pagination...
            String rows = request.getParameter("rows");
            String pageno = request.getParameter("page");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            String status = request.getParameter("status");

            SearchProposalReq req = new SearchProposalReq();
            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            Integer pageNo = null;
            List<ProposalVO> listproposal = new ArrayList<ProposalVO>();
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);

                req.setStartIndex(startPageIndex.intValue());

                userId = getSessionMap().get(WebConstants.EMP_ID).toString();

                req.setSearchType(ProposalSearchType.USER);
                req.setUserId(userId);
                if (status != null) {
                    req.setSearchType(ProposalSearchType.USER_STATUS);
                    req.setStatusId(status);
                }
                SearchProposalResp resp = ServiceMaster.getProposalSearchService().getAllProposals(req);
                if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
                    totalCount = resp.getTotalCount();                    
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                    listproposal = resp.getList();
                }
            }
            
            if (sidx.equals("id")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getProposalId().toString().compareToIgnoreCase(p2.getProposalId().toString());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("type")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("promotype")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("remarks")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("download")) {
                Collections.sort(listproposal, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            }

            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(listproposal);
            }
            if (listproposal != null) {
                for (ProposalVO vo : listproposal) {
                    cellobj.put(WebConstants.ID, vo.getProposalId());
                    cell.add("P" + vo.getProposalId());
                    cell.add(vo.getDate());
                    cell.add(vo.getProblemTypeDesc());
                    cell.add(vo.getPromotionTypeName());
                    if (vo.getStatus().equals("Proposal_Draft")) {
                        cell.add("Save");
                    } else if (vo.getStatus().equals("Proposal_Submit")) {
                        cell.add("Submit");
                    } else {
                        cell.add(vo.getStatus());
                    }
                    cell.add(vo.getStatusId());
                    if (vo.getRemarks() != null) {
                        cell.add(vo.getRemarks());
                    } else {
                        cell.add("-");
                    }
                    String downloadfile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadProposalArticleInfo?proposalId="
                            + vo.getProposalId() + ">Download</a>";
                    cell.add(downloadfile);
                    if (!vo.getStatus().equalsIgnoreCase("Proposal_Submit")) {
                        String delete = "<a id=\"deleteId\" style=\"font-family:arial;color:red;font-size:10px;\" href=deleteProposal?proposalId="
                                + vo.getProposalId() + " onclick=\" \" >Delete</a>";
                        cell.add(delete);
                    } else {
                        cell.add("-");
                    }
                    if (vo.getStatus().equals("Proposal_Draft")) {
                        String submit = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=updateProposalStatus?proposalId="
                                + vo.getProposalId() + ">Submit</a>";
                        cell.add(submit);
                    } else {
                        cell.add("-");
                    }
                    if (vo.getOtherFilePath() != null) {
                        String otherFile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadfile?path=" + vo.getOtherFilePath() + ">Download</a>";
                        cell.add(otherFile);
                    } else {
                        cell.add("");
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
            logger.info("Exception in getAllRoles() method of ProblemAction :");
            e.printStackTrace();
        }

    }
    private String proposalId;

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String updateProposalStatus() {
        logger.info("=== Inside updateProposalStatus. service :getPromotionProposalService().updateProposalStatusToSubmit ");
        try {            
            com.fks.promotion.service.Resp resp = ServiceMaster.getPromotionProposalService().updateProposalStatusToSubmit(proposalId);            
            if (resp.getRespCode() == com.fks.promotion.service.RespCode.SUCCESS) {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception ex) {
            logger.fatal("Exception : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public void downloadProposalArticleInfo() throws IOException {
        logger.info(" ---- Downloading Article Info for the Proposal. Service : getProposalSearchService().getAllProposals ---- ");
        String parameter = request.getParameter("proposalId");        
        SearchProposalReq req = new SearchProposalReq();
        req.setSearchType(ProposalSearchType.ID);
        req.setId(parameter);
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
        req.setUserId(empId);
        SearchProposalResp resp = ServiceMaster.getProposalSearchService().getAllProposals(req);
        if (resp.getResp().getRespCode().equals(RespCode.SUCCESS)) {
            StringBuilder builder = new StringBuilder();
            builder.append("Article Code,Article Description,MC Code,MC Description\n");            
            for (ArticleMCVO articleMCVO : resp.getList().get(0).getArticleList()) {
                if (articleMCVO != null) {
                    builder.append(articleMCVO.getArticleCode());
                }
                builder.append(",");
                if (articleMCVO.getArticleDesc() != null) {
                    builder.append(articleMCVO.getArticleDesc());
                }
                builder.append(",");
                if (articleMCVO.getMcCode() != null) {
                    builder.append(articleMCVO.getMcCode());
                }
                builder.append(",");
                if (articleMCVO.getMcDesc() != null) {
                    builder.append(articleMCVO.getMcDesc());
                }
                builder.append("\n");
            }
            byte[] bytes = builder.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            response.setContentType("text/excel");
            response.setHeader("Content-disposition", "attachment; filename=" + "article-info_" + parameter + ".csv");
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
        }
    }

    public void downloadProposalInfo() throws IOException {
        logger.info(" ---- Downloading  Info for the Proposal. Service :getProposalSearchService().getAllProposals   ---- ");
        String parameter = request.getParameter("proposalId");        
        SearchProposalReq req = new SearchProposalReq();
        req.setSearchType(ProposalSearchType.ID);
        req.setId(parameter);
        String empId = getSessionMap().get(WebConstants.EMP_ID).toString();
        req.setUserId(empId);
        SearchProposalResp resp = ServiceMaster.getProposalSearchService().getAllProposals(req);
        if (resp.getResp().getRespCode().equals(RespCode.SUCCESS)) {
            StringBuilder builder = new StringBuilder();
            //builder.append("Request Number,Request Date,Site Code , Site Desc,Requestor Name,Problem Type,Promo Type,Articles,Article Desc,Mc Code,Mc Desc,Sub Category,Category\n");
            builder.append("Request Number,Request Date,Site Code , Site Desc,Requestor Name,Problem Type,Promo Type,Articles,Article Desc,Mc Code,Mc Desc\n");
            
            List<ProposalVO> proposalVO = resp.getList();

            for (ProposalVO vo : proposalVO) {

                for (ArticleMCVO articleMCVO : vo.getArticleList()) {

                    builder.append("P" + vo.getProposalId());
                    builder.append(",");
                    if (vo.getDate() != null) {
                        builder.append(vo.getDate());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (vo.getSiteCode() != null) {
                        builder.append(vo.getSiteCode());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (vo.getSiteDesc() != null) {
                        builder.append(vo.getSiteDesc());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (vo.getUserName() != null) {
                        builder.append(vo.getUserName());
                    } else {
                        builder.append("-");
                    }

                    builder.append(",");
                    if (vo.getProblemTypeDesc() != null) {
                        builder.append(vo.getProblemTypeDesc());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (vo.getPromotionTypeName() != null) {
                        builder.append(vo.getPromotionTypeName());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");

                    if (articleMCVO != null) {
                        builder.append(articleMCVO.getArticleCode());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (articleMCVO.getArticleDesc() != null) {
                        builder.append(articleMCVO.getArticleDesc());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (articleMCVO.getMcCode() != null) {
                        builder.append(articleMCVO.getMcCode());
                    } else {
                        builder.append("-");
                    }
                    builder.append(",");
                    if (articleMCVO.getMcDesc() != null) {
                        builder.append(articleMCVO.getMcDesc());
                    } else {
                        builder.append("-");
                    }

                    builder.append("\n");
                }

            }

            byte[] bytes = builder.toString().getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            response.setContentType("text/excel");
            response.setHeader("Content-disposition", "attachment; filename=" + "article-info_" + parameter + ".csv");
            byte[] buf = new byte[1024];
            int len;

            while ((len = bis.read(buf)) > 0) {
                response.getOutputStream().write(buf, 0, len);
            }
            bis.close();
            response.getOutputStream().flush();
            logger.info("====== File Writing is Completed ===== ");
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
