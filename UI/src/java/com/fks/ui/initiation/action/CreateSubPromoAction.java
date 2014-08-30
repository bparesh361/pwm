/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.initiation.action;

import com.fks.promo.init.Resp;
import com.fks.promo.init.RespCode;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.CachedMapsList;
import com.fks.ui.constants.MapEnum;
import com.fks.ui.constants.PromotionPropertyUtil;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.PropertyEnum;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.CreatePromoInitiateFormVO;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class CreateSubPromoAction extends ActionBase implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(PowerPricingAction.class.getName());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private String strUserID;
    private CachedMapsList maps;
    private CreatePromoInitiateFormVO formVO;
    private Map<String, String> promoMap, setNoMap;

    @Override
    public String execute() {
        logger.info("------------------ Welcome Promotion Initiate Action Page ----------------");
        try {
            Object strUserID = getSessionMap().get(WebConstants.USER_ID);
            if (strUserID != null) {
                maps = new CachedMapsList();
                promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
                setNoMap = maps.getActiveMap(MapEnum.SET_NO_LIST);

                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            logger.info("Exception in excute() of Promotion PowerPricingAction Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }
    private String setNo;

    public void fillSet() {
        try {
            logger.info("================ Inside filling set  for no: " + setNo);
            JSONObject responseData = new JSONObject();
            JSONObject cellObject = new JSONObject();
            maps = new CachedMapsList();
            Map<String, String> setMap = maps.getActiveMap(MapEnum.SET_LIST);

            Iterator setIterator = setMap.entrySet().iterator();
            if (setIterator.hasNext()) {
                List<String> cellsetdecArray = new ArrayList<String>();
                List<String> cellsetIdArray = new ArrayList<String>();
                int ReqestedSetNo = Integer.parseInt(setNo);
                int mapSetNo = 0;
                while (setIterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) setIterator.next();
                    mapSetNo = Integer.parseInt(entry.getKey().toString());
//                    logger.info("--------- Req set No : "+ReqestedSetNo);
//                    logger.info("--------- Map set No : "+mapSetNo);
                    if (mapSetNo <= ReqestedSetNo) {
                        cellsetdecArray.add(entry.getValue().toString());
                        cellsetIdArray.add(entry.getKey().toString());
                    }
                }
                cellObject.put("setdecList", cellsetdecArray);
                cellObject.put("setIdList", cellsetIdArray);
                responseData.put("rows", cellObject);
                out = response.getWriter();
                out.println(responseData);
            }
        } catch (Exception ex) {
            logger.fatal("-------- Error Inside Fetching set : " + ex.getMessage());
            ex.printStackTrace();
        }

    }
    private File articleFileUpload;
    private String articleFileUploadFileName;

    public File getArticleFileUpload() {
        return articleFileUpload;
    }

    public void setArticleFileUpload(File articleFileUpload) {
        this.articleFileUpload = articleFileUpload;
    }

    public String getArticleFileUploadFileName() {
        return articleFileUploadFileName;
    }

    public void setArticleFileUploadFileName(String articleFileUploadFileName) {
        this.articleFileUploadFileName = articleFileUploadFileName;
    }

    public String uploadFile() {
        logger.info("---------- Inside uploading Trans Promo file-----------");
        try {
            maps = new CachedMapsList();
            promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
            setNoMap = maps.getActiveMap(MapEnum.SET_NO_LIST);

            if ((articleFileUpload == null) || (articleFileUpload != null && articleFileUpload.length() == 0)) {
                addActionError("Empty Article file can not be uploaded.");
                return INPUT;
            }

            //Get Initiate Article File Sever path
            String promotionFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.INITIATE_ARTICLE_FILE_PATH);

            //Create Master Promo Request Folder
            String strMasterPromoFolderPath = promotionFilePath + "MstPromo-" + formVO.getFileUploadMstPromoId();

            File masterPromoDir = new File(strMasterPromoFolderPath);
            if (!masterPromoDir.exists()) {
                masterPromoDir.mkdir();
            }

            //Create Trans Promo Request Folder
            String strTransPromoFolderPath = strMasterPromoFolderPath + "/TransPromo-" + formVO.getFileUploadMstPromoTypeId();
            File transPromoDir = new File(strTransPromoFolderPath);
            if (!transPromoDir.exists()) {
                transPromoDir.mkdir();
            }

            //Create File Name With Current Time stamp
            Calendar cal = new GregorianCalendar();
            Long currentTime = cal.getTimeInMillis();

            String promotionFileName = currentTime.toString() + "_" + PromotionUtil.getFileNameWithoutSpace(articleFileUploadFileName);
            String filePath = strTransPromoFolderPath + "/" + promotionFileName;


            /*Copy the File On Defined File Path
             */
            File bfile = new File(filePath);
            FileUtils.copyFile(articleFileUpload.getAbsoluteFile(), bfile);
            logger.info("File is copied successful!");

//            String promoTypeId=formVO.getFileUploadMstPromoTypeId();
//            if(promoTypeId.equalsIgnoreCase("7") || promoTypeId.equalsIgnoreCase("3") || promoTypeId.equalsIgnoreCase("4")|| promoTypeId.equalsIgnoreCase("5")){
//            }
            String returnUIFileUploadGridData = formVO.getFileUploadsetId() + "," + formVO.getFileUploadsetName() + "," + filePath;
            if (formVO.getFileUploadGridData() != null && formVO.getFileUploadGridData().length() > 0) {
                logger.info("------- Inside Next Time Upload File Grid Data.");
                formVO.setFileUploadGridData(formVO.getFileUploadGridData() + "," + returnUIFileUploadGridData);
            } else {
                logger.info("------- Inside First Time Upload File Grid Data.");
                formVO.setFileUploadGridData(returnUIFileUploadGridData);
            }

            return SUCCESS;
        } catch (Exception ex) {
            logger.fatal("-------- Error Inside uploadFile subpromo  : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String createSubPromo() {
        logger.info("---------- Inside Creating Trans Promo Request-----------");

        try {
            maps = new CachedMapsList();
            promoMap = maps.getActiveMap(MapEnum.PROMOTION_TYPE);
            setNoMap = maps.getActiveMap(MapEnum.SET_NO_LIST);

            String empID = getSessionMap().get(WebConstants.EMP_ID).toString();
            String zoneId = getSessionMap().get(WebConstants.ZONE_ID).toString();

            Resp resp = SubPromoInitiationUtil.getSubPromoRequest(zoneId, empID, formVO);

            if (resp.getRespCode() == RespCode.SUCCESS) {
                addActionMessage(resp.getMsg());
                formVO = new CreatePromoInitiateFormVO();
                return SUCCESS;
            } else {
                addActionError(resp.getMsg());
                return INPUT;
            }
        } catch (Exception ex) {
            logger.fatal("-------- Error Inside createSubPromo  : " + ex.getMessage());
            ex.printStackTrace();
            return ERROR;
        }
    }

    public String getSetNo() {
        return setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }

    public CreatePromoInitiateFormVO getFormVO() {
        return formVO;
    }

    public void setFormVO(CreatePromoInitiateFormVO formVO) {
        this.formVO = formVO;
    }

    public Map<String, String> getPromoMap() {
        return promoMap;
    }

    public Map<String, String> getSetNoMap() {
        return setNoMap;
    }

    public void setSetNoMap(Map<String, String> setNoMap) {
        this.setNoMap = setNoMap;
    }

    public void setPromoMap(Map<String, String> promoMap) {
        this.promoMap = promoMap;
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
