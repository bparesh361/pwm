/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.execution.action;

import com.fks.promo.init.ExecutePromoManagerEnum;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.StausVO;
import com.fks.ui.master.vo.TransPromotionUIVO;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
public class ExecutePromoDashboardAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PromoManagerAprrovalAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID, strEmpID;
    private Map<String, String> teamMemberList;

    public Map<String, String> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(Map<String, String> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promo Manager Dashboard Action Page ----------------");
        try {
            strUserID = getSessionMap().get(WebConstants.USER_ID).toString();
            if (strUserID != null) {
                String storeCode = getSessionMap().get(WebConstants.STORE_CODE).toString();
                setTeamMemberList(PromoExecutionUtil.getTeamMemberList(storeCode));
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion Level1ApprovalAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllTransPromotionDetail() {
        logger.info("======= Inside getAllTransPromotionDetail For Execution DashBoard. Service : getTransPromoService().getExecutePromoDashBoardDtl(req) ======");
        String teamMemberId = null, startDate = null, endDate = null;

        String searchType = request.getParameter("SearchType");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();

            ExecutePromoManagerEnum execPromo = null;
            if (searchType.equalsIgnoreCase("DATE")) {
                execPromo = ExecutePromoManagerEnum.DATE;
                startDate = request.getParameter("startDate");
                endDate = request.getParameter("endDate");
                teamMemberId = getSessionMap().get(WebConstants.EMP_ID).toString();

            } else if (searchType.equalsIgnoreCase("TEAM_MEMBER")) {
                execPromo = ExecutePromoManagerEnum.TEAM_MEMBER;
                teamMemberId = request.getParameter("teamMember");

            } else {
                execPromo = ExecutePromoManagerEnum.ALL;
                teamMemberId = getSessionMap().get(WebConstants.EMP_ID).toString();
            }

            String pageno = request.getParameter("page");
            String rows = request.getParameter("rows");
            String sidx = request.getParameter("sidx");
            String sortorder = request.getParameter("sord");
            Integer pageNo = null;
            float totalCount = 0;
            double pageCount = 1;
            StausVO responseVo = PromotionUtil.validatePageNo(pageno);
            Long startPageIndex = new Long("0");
            List<TransPromotionUIVO> list = null;
            if (responseVo.isStatusFlag()) {
                pageNo = (pageno != null) ? Integer.parseInt(pageno) : null;
                startPageIndex = (Long.parseLong(pageno) - 1) * Long.parseLong(rows);
                list = PromoExecutionUtil.getAllTramsPromotionExcuteDtl(teamMemberId, startDate, endDate, execPromo, startPageIndex, null, null, null, "ALL", null, null);

                totalCount = list.get(0).getTotalCount();

                pageCount = Math.ceil(totalCount / Long.valueOf(rows));

            }

            if (sidx.equals("reqno")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getTransPromoId().toString().compareToIgnoreCase(p2.getTransPromoId().toString());
                    }
                });
            } else if (sidx.equals("ldate")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getLastUpdatedDate().compareToIgnoreCase(p2.getLastUpdatedDate().toString());
                    }
                });
            } else if (sidx.equals("date")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getDate().toString().compareToIgnoreCase(p2.getDate().toString());
                    }
                });
            } else if (sidx.equals("time")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getTime().toString().compareToIgnoreCase(p2.getTime().toString());
                    }
                });
            } else if (sidx.equals("initiatorName")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getInitiatorName().toString().compareToIgnoreCase(p2.getInitiatorName().toString());
                    }
                });
            } else if (sidx.equals("contNo")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getContactNo().toString().compareToIgnoreCase(p2.getContactNo().toString());
                    }
                });
            } else if (sidx.equals("empCode")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getEmpCode().toString().compareToIgnoreCase(p2.getEmpCode().toString());
                    }
                });
            } else if (sidx.equals("initiatorlocation")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getInitiatorLocation().toString().compareToIgnoreCase(p2.getInitiatorLocation().toString());
                    }
                });
            } else if (sidx.equals("reqName")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRequestName().toString().compareToIgnoreCase(p2.getRequestName().toString());
                    }
                });
            } else if (sidx.equals("event")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getEvent().toString().compareToIgnoreCase(p2.getEvent().toString());
                    }
                });
            } else if (sidx.equals("marketingtype")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getMarketingType().toString().compareToIgnoreCase(p2.getMarketingType().toString());
                    }
                });
            } else if (sidx.equals("category")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getCategory().toString().compareToIgnoreCase(p2.getCategory().toString());
                    }
                });
            } else if (sidx.equals("subcategory")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getSubCategory().toString().compareToIgnoreCase(p2.getSubCategory().toString());
                    }
                });
            } else if (sidx.equals("promotype")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getPromotionType().toString().compareToIgnoreCase(p2.getPromotionType().toString());
                    }
                });
            } else if (sidx.equals("validfrom")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getValidFrom().toString().compareToIgnoreCase(p2.getValidFrom().toString());
                    }
                });
            } else if (sidx.equals("validto")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getValidTo().toString().compareToIgnoreCase(p2.getValidTo().toString());
                    }
                });
            } else if (sidx.equals("status")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getStatus().toString().compareToIgnoreCase(p2.getStatus().toString());
                    }
                });
            } else if (sidx.equals("remark")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("approvername")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("approvalfrom")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("teamassignfrom")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("assignby")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            } else if (sidx.equals("teamDate")) {
                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        TransPromotionUIVO p1 = (TransPromotionUIVO) o1;
                        TransPromotionUIVO p2 = (TransPromotionUIVO) o2;
                        return p1.getRemark().toString().compareToIgnoreCase(p2.getRemark().toString());
                    }
                });
            }
            //AFTER FINISH : CHECK THE SORT ORDER
            if (sortorder != null && sortorder.equalsIgnoreCase("desc")) {
                Collections.reverse(list);
            }
            if (list.size() > 0 && !list.isEmpty()) {

                for (TransPromotionUIVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getTransPromoId());
                    //cell.add("R" + PromotionUtil.zeroPad(Integer.parseInt(vo.getTransPromoId()), 8));
                    cell.add("T" + vo.getMstPromoId() + "-R" + vo.getTransPromoId());
                    cell.add(vo.getLastUpdatedDate());
                    cell.add(vo.getDate());
                    cell.add(vo.getTime());
                    cell.add(vo.getInitiatorName());
                    cell.add(vo.getContactNo());
                    cell.add(vo.getEmpCode());
                    cell.add(vo.getInitiatorLocation());
                    cell.add(vo.getRequestName());
                    cell.add(vo.getEvent());
                    cell.add(vo.getMarketingType());
                    cell.add(vo.getCategory());
                    cell.add(vo.getSubCategory());
                    cell.add(vo.getPromotionType());
                    cell.add(vo.getValidFrom());
                    cell.add(vo.getValidTo());
                    cell.add(vo.getStatus());
                    cell.add(vo.getRemark());
                    cell.add(vo.getApproverName());
                    cell.add(vo.getApproverFrom());
                    cell.add(vo.getTeamAssignmentLocation());
//                        cell.add(vo.getTeamAssignedBy());
                    cell.add(vo.getTeamAssignedTO());
                    cell.add(vo.getTeamAssignedDate());

//                    String reject = "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=promoExecuteApprReject_action?isReject=1&transPromoId="
//                            + vo.getTransPromoId() + ">Reject</a>";
//                    cell.add(reject);
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
            logger.fatal(
                    "Exception generated while getAllTransPromotionDetail Execute Dashboard: function getAllTransPromotionDetail() : Exception is :");
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
