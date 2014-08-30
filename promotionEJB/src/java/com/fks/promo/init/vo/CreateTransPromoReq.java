/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

/**
 *
 * @author Paresb
 */
public class CreateTransPromoReq {

    private Long mstPromoId;
    private Long typeId;
    private TransPromoVO transPromoVO;
    private Long zoneId;
    private Long empId;
    private boolean isError;
    private String errorFilePath;
    private String errorMsg;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getMstPromoId() {
        return mstPromoId;
    }

    public void setMstPromoId(Long mstPromoId) {
        this.mstPromoId = mstPromoId;
    }

    public TransPromoVO getTransPromoVO() {
        return transPromoVO;
    }

    public void setTransPromoVO(TransPromoVO transPromoVO) {
        this.transPromoVO = transPromoVO;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public boolean isIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
