/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.action;

import com.fks.promo.master.service.MstCalendarVO;
import com.fks.promo.master.service.Resp;
import com.fks.promo.master.service.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.master.vo.CalendearErrorFileVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
public class CalenderAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(CalenderAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String year;
    private String month;
    private File calendarFile;
    private static final int BYTES_DOWNLOAD = 1024;
    private String strUserID;

    public void setCalendarFile(File calendarFile) {
        this.calendarFile = calendarFile;
    }

    @Override
    public String execute() {
        logger.info("------------------ Welcome Calender Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Calender Action Master Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public void getAllCalender() {
        logger.info("-----------  Inside Fetching Problem Details --------  ");
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstCalendarVO> list = ServiceMaster.getOtherMasterService().getAllCalendar();
            if (list != null) {
                for (MstCalendarVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getCalendarId());
                    cell.add(vo.getCalendarId());
                    cell.add(vo.getDate());
                    cell.add(vo.getDescription());
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
            }
        } catch (Exception e) {
            logger.info("Exception in getAllCalender() method of CalenderAction");
            e.printStackTrace();
        }
    }

    public String deleteCalander() {
        try {
            logger.info("--------- inside deleting Calander value.-----");
            String calID = request.getParameter("selIdToDelete");
            Resp resp = ServiceMaster.getOtherMasterService().deleteCalanderValue(calID);
            if (resp.getRespCode() == RespCode.FAILURE) {
                addActionError(resp.getMsg());
                return INPUT;
            } else {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return INPUT;
        }

    }

    public String submitCalendar() {
        try {
            logger.info(" --- Submitting Calendar Information --- ");
            String id = request.getParameter("txtid");
            String date = request.getParameter("txtcdate");
            String description = request.getParameter("txtdescription");
            MstCalendarVO vo = new MstCalendarVO();
            if (id != null && !id.trim().isEmpty()) {
                vo.setCalendarId(new Long(id));
            }
            vo.setDate(date);
            vo.setDescription(description);
            Resp resp = ServiceMaster.getOtherMasterService().insertCalendar(vo);
            if (resp.getRespCode() == RespCode.FAILURE) {
                addActionError(resp.getMsg());
                return INPUT;
            } else {
                addActionMessage(resp.getMsg());
                return SUCCESS;
            }
        } catch (Exception e) {
            logger.info("Exception in submitCalendar :" + e.getMessage());
            e.printStackTrace();
            return ERROR;

        }

    }

    public void getAllCalendarByYear() {
        logger.info("-----------  Inside Fetching Calendar By Year Details --------  Year " + year + " : Month " + month);
        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstCalendarVO> list = ServiceMaster.getOtherMasterService().getAllCalendar();
            if (list != null) {
                for (MstCalendarVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getCalendarId());
                    cell.add(vo.getCalendarId());
                    cell.add(vo.getDate());
                    cell.add(vo.getDescription());
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
            }
        } catch (Exception e) {
            logger.info("Exception in getAllCalender() method of CalenderAction");
            e.printStackTrace();
        }
    }

    public void getAllCalendarByYearAndMonth() {
        logger.info(" --- Searching Calendar By Year And Month --- ");
        String year = request.getParameter("year");
        String month = request.getParameter("month");

        try {
            JSONObject responcedata = new JSONObject();
            JSONArray cellarray = new JSONArray();
            JSONArray cell = new JSONArray();
            JSONObject cellobj = new JSONObject();
            out = response.getWriter();
            List<MstCalendarVO> list = ServiceMaster.getOtherMasterService().getAllCalendarByYearMonth(year, month);
            if (list != null) {
                for (MstCalendarVO vo : list) {
                    cellobj.put(WebConstants.ID, vo.getCalendarId());
                    cell.add(vo.getCalendarId());
                    cell.add(vo.getDate());
                    cell.add(vo.getDescription());
                    cellobj.put(WebConstants.CELL, cell);
                    cell.clear();
                    cellarray.add(cellobj);
                }
                responcedata.put(WebConstants.PAGE, "1");
                responcedata.put(WebConstants.RECORDS, list.size());
                responcedata.put(WebConstants.ROWS, cellarray);
                out.print(responcedata);
            }
        } catch (Exception e) {
            logger.info("Exception in getAllCalender() method of CalenderAction");
            e.printStackTrace();
        }
    }

    public String submitCalendarFile() throws IOException {
        logger.info("----- inside submitting calendar file ---- : ");
        boolean validate = true;
        BufferedReader reader = null;
        List<CalendearErrorFileVO> list = new ArrayList<CalendearErrorFileVO>();
        try {
            if (calendarFile != null) {
                if (calendarFile.length() == 0) {
                    addActionError("Empty File cannot be uploaded.");
                    return INPUT;
                } else {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(calendarFile)));
                    String line;
                    //Skip first Line
                    line = reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        StringTokenizer tokenizer = new StringTokenizer(line, ",");
                        CalendearErrorFileVO vo = new CalendearErrorFileVO();
                        if (tokenizer.countTokens() == 2) {

                            String description = tokenizer.nextToken();

                            String calendarDate = tokenizer.nextToken();


                            vo.setDate(calendarDate);
                            vo.setDesc(description);
                            if (!calendarDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                logger.error("Error While Parsing Date : " + calendarDate);
                                vo.setMessage("Error in the Date Format.");
                                validate = false;
                            }
                        } else {
                            vo.setDate(line);
                            validate = false;
                            vo.setMessage("Error in Line Format. No. of Records Should be 2, separated by semi-colon.");
                        }
                        list.add(vo);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (!validate) {
            logger.info(" --- generating error file --- ");
//            String dirPath = PromotionPropertyUtil.getPropertyString(PropertyEnum.COMMON_DIRECTORY_PATH);
//            File file = new File(dirPath, "calerror.csv");
//            BufferedWriter writer = null;
            try {

//                writer = new BufferedWriter(new FileWriter(file));
                for (CalendearErrorFileVO vo : list) {//
                    if (vo.getMessage() != null) {
                        addActionError(vo.getMessage());
                    }
                }
//                writer.close();
//                logger.info(" --- error file successfully created --- ");
//                response.setContentType("text/csv");
//                response.setHeader("Content-Disposition", "attachment;filename=error.csv");
//                int read = 0;
//                byte[] bytes = new byte[BYTES_DOWNLOAD];
//                OutputStream os = response.getOutputStream();
//                FileInputStream is = new FileInputStream(file);
//                while ((read = is.read(bytes)) != -1) {
//                    os.write(bytes, 0, read);
//                }
//                os.flush();
//                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            return ERROR;
        } else {
            for (CalendearErrorFileVO vo : list) {
                MstCalendarVO calvo = new MstCalendarVO();
                calvo.setDate(vo.getDate());
                calvo.setDescription(vo.getDesc());
                Resp resp = ServiceMaster.getOtherMasterService().insertCalendar(calvo);
                if (resp.getRespCode() == RespCode.SUCCESS) {
                    logger.info("Data Successfully Entered.");
                    vo.setMessage(resp.getMsg());
                    addActionMessage(resp.getMsg());
                } else {
                    logger.info("Error While Entering Data Message : " + resp.getMsg());
                    addActionError(resp.getMsg());
                }
            }
            logger.info(" ========   List =====   " + list);
            return SUCCESS;
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
