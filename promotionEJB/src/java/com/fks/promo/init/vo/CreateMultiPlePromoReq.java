/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

import java.util.List;

/**
 *
 * @author ajitn
 */
public class CreateMultiPlePromoReq {

    private String mstPromoId;
    private List<TransPromoVO> transPromoVO;
    private String zoneId;
    private String empId;
    private String filePath;
    private Boolean errorFlag;
    private String errorPath;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMstPromoId() {
        return mstPromoId;
    }

    public void setMstPromoId(String mstPromoId) {
        this.mstPromoId = mstPromoId;
    }

    public List<TransPromoVO> getTransPromoVO() {
        return transPromoVO;
    }

    public void setTransPromoVO(List<TransPromoVO> transPromoVO) {
        this.transPromoVO = transPromoVO;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(Boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }
}
