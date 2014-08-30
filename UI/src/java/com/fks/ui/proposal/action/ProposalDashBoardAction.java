/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.proposal.action;

import com.fks.promotion.service.AcceptRejectProposalVO;
import com.fks.promotion.service.ProposalSearchType;
import com.fks.promotion.service.ProposalVO;
import com.fks.promotion.service.Resp;
import com.fks.promotion.service.RespCode;
import com.fks.promotion.service.SearchProposalReq;
import com.fks.promotion.service.SearchProposalResp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
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
public class ProposalDashBoardAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(ProposalAction.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String strUserID;
    private String txtBXGYFrom;
    private String txtBXGYTo;

    public String execute() {
        logger.info("------------------ Welcome Proposal Action Page ----------------");
        try {
            if (getSessionMap() == null || getSessionMap().get(WebConstants.USER_ID) == null) {
                return LOGIN;
            }
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Proposal Action Master Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllProposalDashboardByStatus() {
        logger.info("-----------  Inside Fetching Proposal Dasbboard Details. Service : getProposalSearchService().getAllProposals --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = response.getWriter();
            out = response.getWriter();
            SearchProposalReq req = new SearchProposalReq();
            req.setSearchType(ProposalSearchType.ZONE_STATUS);
            req.setStatusId("4");
            String userId = (String) getSessionMap().get(WebConstants.EMP_ID);
            String location = (String) getSessionMap().get(WebConstants.LOCATION);
            if (location.equalsIgnoreCase("HO")) {
                req.setSearchType(ProposalSearchType.ALL);
            }

            req.setUserId(userId);
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
            }
            List<ProposalVO> listdata = new ArrayList<ProposalVO>();
            SearchProposalResp resp = ServiceMaster.getProposalSearchService().getAllProposals(req);
            if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
                if (resp.getList() != null) {
                    listdata = resp.getList();
                    totalCount = resp.getTotalCount();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            }
            if (sidx.equals("id")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getProposalId().toString().compareToIgnoreCase(p2.getProposalId().toString());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("sitecode")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getSiteCode().toString().compareToIgnoreCase(p2.getSiteCode().toString());
                    }
                });
            } else if (sidx.equals("sitedesc")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getSiteDesc().toString().compareToIgnoreCase(p2.getSiteDesc().toString());
                    }
                });
            } else if (sidx.equals("requestorname")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getUserName().toString().compareToIgnoreCase(p2.getUserName().toString());
                    }
                });
            } else if (sidx.equals("contactno")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getContactNo().toString().compareToIgnoreCase(p2.getContactNo().toString());
                    }
                });
            } else if (sidx.equals("empCode")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getEmpId().toString().compareToIgnoreCase(p2.getEmpId().toString());
                    }
                });
            } else if (sidx.equals("dept")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDepartment().toString().compareToIgnoreCase(p2.getDepartment().toString());
                    }
                });
            } else if (sidx.equals("type")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getProblemTypeDesc().toString().compareToIgnoreCase(p2.getProblemTypeDesc().toString());
                    }
                });
            } else if (sidx.equals("promoType")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getPromotionTypeName().toString().compareToIgnoreCase(p2.getPromotionTypeName().toString());
                    }
                });
            } else if (sidx.equals("remarks")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getRemarks().toString().compareToIgnoreCase(p2.getRemarks().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            }
            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(listdata);
            }

            for (ProposalVO vo : listdata) {
                cellobj.put(WebConstants.ID, vo.getProposalId());
                cell.add("P" + vo.getProposalId());
                cell.add(vo.getDate());
                cell.add(vo.getSiteCode());
                cell.add(vo.getSiteDesc());
                cell.add(vo.getUserName());
                cell.add(vo.getContactNo());
                cell.add(vo.getEmpId());
                cell.add(vo.getDepartment());
                cell.add(vo.getProblemTypeDesc());
                cell.add(vo.getPromotionTypeName());
                cell.add(vo.getRemarks());
                if (vo.getStatus().equals("Proposal_Draft")) {
                    cell.add("Save");
                } else if (vo.getStatus().equals("Proposal_Submit")) {
                    cell.add("Submit");
                } else {
                    cell.add(vo.getStatus());
                }
                cell.add(vo.getStatusId());
                String downloadfile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadProposalInfo?proposalId="
                        + vo.getProposalId() + ">Download</a>";
                cell.add(downloadfile);
                String delete = "<a style=\"font-family:arial;color:red;font-size:10px;\" href=deleteProposal?proposalId="
                        + vo.getProposalId() + ">Delete</a>";
                cell.add(delete);
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

        } catch (Exception e) {
            logger.info("Exception in getAllRoles() method of ProblemAction :");
            e.printStackTrace();
        }
    }

    public void searchProposalDashboardByDate() {
        logger.info("--- Inside getting All Proposal By Status Zone And Date. Service : getProposalSearchService().getAllProposals  --- ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            PrintWriter out = response.getWriter();
            out = response.getWriter();
            String fromDate = request.getParameter("txtBXGYFrom");
            String toDate = request.getParameter("txtBXGYTo");
            String status = request.getParameter("status");

            SearchProposalReq req = new SearchProposalReq();

            String userId = (String) getSessionMap().get(WebConstants.EMP_ID);
            req.setUserId(userId);
            String location = (String) getSessionMap().get(WebConstants.LOCATION);

            if (status != null && (fromDate.length() == 0 && toDate.length() == 0)) {
                req.setSearchType(ProposalSearchType.ZONE_STATUS);
                req.setStatusId(status);

            } else if ((status == null || status.length() == 0) && (fromDate.length() > 0 && toDate.length() > 0)) {
                req.setSearchType(ProposalSearchType.DATE_ZONE);
                req.setStartDate(fromDate);
                req.setEndDate(toDate);
            } else {
                req.setSearchType(ProposalSearchType.DATE_ZONE_STATUS);
                req.setStatusId(status);
                req.setStartDate(fromDate);
                req.setEndDate(toDate);
            }

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
            }
            List<ProposalVO> listdata = new ArrayList<ProposalVO>();
            SearchProposalResp resp = ServiceMaster.getProposalSearchService().getAllProposals(req);
            if (resp.getResp().getRespCode() == RespCode.SUCCESS) {
                if (resp.getList() != null) {
                    listdata = resp.getList();
                    totalCount = resp.getTotalCount();
                    pageCount = Math.ceil(totalCount / Long.valueOf(rows));
                }
            }
            if (sidx.equals("id")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getProposalId().toString().compareToIgnoreCase(p2.getProposalId().toString());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("sitecode")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getSiteCode().toString().compareToIgnoreCase(p2.getSiteCode().toString());
                    }
                });
            } else if (sidx.equals("sitedesc")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getSiteDesc().toString().compareToIgnoreCase(p2.getSiteDesc().toString());
                    }
                });
            } else if (sidx.equals("requestorname")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getUserName().toString().compareToIgnoreCase(p2.getUserName().toString());
                    }
                });
            } else if (sidx.equals("contactno")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getContactNo().toString().compareToIgnoreCase(p2.getContactNo().toString());
                    }
                });
            } else if (sidx.equals("empCode")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getEmpId().toString().compareToIgnoreCase(p2.getEmpId().toString());
                    }
                });
            } else if (sidx.equals("dept")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getDepartment().toString().compareToIgnoreCase(p2.getDepartment().toString());
                    }
                });
            } else if (sidx.equals("type")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getProblemTypeDesc().toString().compareToIgnoreCase(p2.getProblemTypeDesc().toString());
                    }
                });
            } else if (sidx.equals("promoType")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getPromotionTypeName().toString().compareToIgnoreCase(p2.getPromotionTypeName().toString());
                    }
                });
            } else if (sidx.equals("remarks")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getRemarks().toString().compareToIgnoreCase(p2.getRemarks().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(listdata, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        ProposalVO p1 = (ProposalVO) o1;
                        ProposalVO p2 = (ProposalVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            }
            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(listdata);
            }

            if (listdata != null) {
                for (ProposalVO vo : listdata) {
                    cellobj.put(WebConstants.ID, vo.getProposalId());
                    cell.add("P" + vo.getProposalId());
                    cell.add(vo.getDate());
                    cell.add(vo.getSiteCode());
                    cell.add(vo.getSiteDesc());
                    cell.add(vo.getUserName());
                    cell.add(vo.getContactNo());
                    cell.add(vo.getEmpId());
                    cell.add(vo.getDepartment());
                    cell.add(vo.getProblemTypeDesc());
                    cell.add(vo.getPromotionTypeName());
                    cell.add(vo.getRemarks());
                    if (vo.getStatus().equals("Proposal_Draft")) {
                        cell.add("Save");
                    } else if (vo.getStatus().equals("Proposal_Submit")) {
                        cell.add("Submit");
                    } else {
                        cell.add(vo.getStatus());
                    }
                    cell.add(vo.getStatusId());
                    String downloadfile = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadProposalInfo?proposalId="
                            + vo.getProposalId() + ">Download</a>";
                    cell.add(downloadfile);
                    String delete = "<a style=\"font-family:arial;color:red;font-size:10px;\" href=deleteProposal?proposalId="
                            + vo.getProposalId() + ">Delete</a>";
                    cell.add(delete);
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
            }

            out.print(responcedata);

        } catch (Exception e) {
            logger.info("Exception in getAllRoles() method of ProblemAction :");
            e.printStackTrace();
        }

    }

    public String deleteProposal() {
        logger.info("===== Inside deleteProposal. Service : getProposalSearchService().deleteProposal()");
        try {
            String proposalId = request.getParameter("proposalId");
            String[] idSplit = proposalId.split(",");
            List<Long> longIds = new ArrayList<Long>();
            for (int i = 0; i < idSplit.length; i++) {
                longIds.add(Long.valueOf(idSplit[i]));
            }
            Resp resp = ServiceMaster.getProposalSearchService().deleteProposal(longIds);
            if (resp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception e) {

            logger.info("Error while deleting Proposal " + e.getMessage());
            return ERROR;
        }
    }
    private String manualRemarks;

    public String getManualRemarks() {
        return manualRemarks;
    }

    public void setManualRemarks(String manualRemarks) {
        this.manualRemarks = manualRemarks;
    }

    public String acceptProposal() {
        logger.info("==== Inside Accepting Proposal. Service : getPromotionProposalService().updateProposalStatusToAcceptReject");
        try {
            String proposalId = request.getParameter("proposalId");
            String[] idSplit = proposalId.split(",");
            List<AcceptRejectProposalVO> list = new ArrayList<AcceptRejectProposalVO>();
            AcceptRejectProposalVO req;
            for (int i = 0; i < idSplit.length; i++) {
                req = new AcceptRejectProposalVO();
                req.setIsAccept(true);
                req.setRemarks(manualRemarks);
                req.setProposalId(idSplit[i]);
                list.add(req);
            }
            Resp resp = ServiceMaster.getPromotionProposalService().updateProposalStatusToAcceptReject(list);
            if (resp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            addActionError("Exception : " + e.getMessage());
            return ERROR;
        }
    }

    public String rejectProposal() {
        logger.info("==== Inside rejectProposal. Service : getPromotionProposalService().updateProposalStatusToAcceptReject");
        try {
            String proposalId = request.getParameter("proposalId");
            String[] idSplit = proposalId.split(",");
            List<AcceptRejectProposalVO> list = new ArrayList<AcceptRejectProposalVO>();
            AcceptRejectProposalVO req;
            for (int i = 0; i < idSplit.length; i++) {
                req = new AcceptRejectProposalVO();
                req.setIsAccept(false);
                req.setRemarks(manualRemarks);
                req.setProposalId(idSplit[i]);
                list.add(req);
            }
            Resp resp = ServiceMaster.getPromotionProposalService().updateProposalStatusToAcceptReject(list);
            if (resp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception e) {
            addActionError("Exception : " + e.getMessage());
            return ERROR;
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

    public String getTxtBXGYFrom() {
        return txtBXGYFrom;
    }

    public void setTxtBXGYFrom(String txtBXGYFrom) {
        this.txtBXGYFrom = txtBXGYFrom;
    }

    public String getTxtBXGYTo() {
        return txtBXGYTo;
    }

    public void setTxtBXGYTo(String txtBXGYTo) {
        this.txtBXGYTo = txtBXGYTo;
    }
}
