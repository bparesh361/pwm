/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.form.vo;

/**
 *
 * @author ajitn
 */
public class PoolTicketSizeFormVO {

    private String mstPromoId;
    /*x & y mst promoid are taken 
     *for setting the master request back again to UI form 
     *after file validate post call      
     */
    public String xMstPromoId;
    public String yMstPromoId;
    private String buyWorthAmt;
    private String manualArticleData;
    private String manualYArticleData;
    private String discConfig;
    private String discountConfigValue;
    private String discountConfigQty;
    private String marginAchivement;
    private String ticketSizeGrowth;
    private String sellQty;
    private String growthConversion;
    private String qtyValueSellGrowth;
    private String validFrom;
    private String validTo;
    private String errorfilePath;
    private String errorYfilePath;
    private String isUploadFileError;
    public String xFileHistoryFlag;
    public String xManualGridData;
    private String isUploadYFileError;
    private String isManualEntry;
    private String isYManualEntry;
    private String isXFileUploaded;
    private String isYFileUploaded;
    private String txtRemarks;
    private String isInitiatorRedirect,SessionmstPromoId;

    public String getSessionmstPromoId() {
        return SessionmstPromoId;
    }

    public void setSessionmstPromoId(String SessionmstPromoId) {
        this.SessionmstPromoId = SessionmstPromoId;
    }

    public String getIsInitiatorRedirect() {
        return isInitiatorRedirect;
    }

    public void setIsInitiatorRedirect(String isInitiatorRedirect) {
        this.isInitiatorRedirect = isInitiatorRedirect;
    }
    

    public String getBuyWorthAmt() {
        return buyWorthAmt;
    }

    public void setBuyWorthAmt(String buyWorthAmt) {
        this.buyWorthAmt = buyWorthAmt;
    }

    public String getDiscConfig() {
        return discConfig;
    }

    public void setDiscConfig(String discConfig) {
        this.discConfig = discConfig;
    }

    public String getDiscountConfigQty() {
        return discountConfigQty;
    }

    public void setDiscountConfigQty(String discountConfigQty) {
        this.discountConfigQty = discountConfigQty;
    }

    public String getDiscountConfigValue() {
        return discountConfigValue;
    }

    public void setDiscountConfigValue(String discountConfigValue) {
        this.discountConfigValue = discountConfigValue;
    }

    public String getErrorYfilePath() {
        return errorYfilePath;
    }

    public void setErrorYfilePath(String errorYfilePath) {
        this.errorYfilePath = errorYfilePath;
    }

    public String getErrorfilePath() {
        return errorfilePath;
    }

    public void setErrorfilePath(String errorfilePath) {
        this.errorfilePath = errorfilePath;
    }

    public String getGrowthConversion() {
        return growthConversion;
    }

    public void setGrowthConversion(String growthConversion) {
        this.growthConversion = growthConversion;
    }

    public String getIsManualEntry() {
        return isManualEntry;
    }

    public void setIsManualEntry(String isManualEntry) {
        this.isManualEntry = isManualEntry;
    }

    public String getIsUploadFileError() {
        return isUploadFileError;
    }

    public void setIsUploadFileError(String isUploadFileError) {
        this.isUploadFileError = isUploadFileError;
    }

    public String getIsUploadYFileError() {
        return isUploadYFileError;
    }

    public void setIsUploadYFileError(String isUploadYFileError) {
        this.isUploadYFileError = isUploadYFileError;
    }

    public String getIsXFileUploaded() {
        return isXFileUploaded;
    }

    public void setIsXFileUploaded(String isXFileUploaded) {
        this.isXFileUploaded = isXFileUploaded;
    }

    public String getIsYFileUploaded() {
        return isYFileUploaded;
    }

    public void setIsYFileUploaded(String isYFileUploaded) {
        this.isYFileUploaded = isYFileUploaded;
    }

    public String getIsYManualEntry() {
        return isYManualEntry;
    }

    public void setIsYManualEntry(String isYManualEntry) {
        this.isYManualEntry = isYManualEntry;
    }

    public String getManualArticleData() {
        return manualArticleData;
    }

    public void setManualArticleData(String manualArticleData) {
        this.manualArticleData = manualArticleData;
    }

    public String getManualYArticleData() {
        return manualYArticleData;
    }

    public void setManualYArticleData(String manualYArticleData) {
        this.manualYArticleData = manualYArticleData;
    }

    public String getMarginAchivement() {
        return marginAchivement;
    }

    public void setMarginAchivement(String marginAchivement) {
        this.marginAchivement = marginAchivement;
    }

    public String getMstPromoId() {
        return mstPromoId;
    }

    public void setMstPromoId(String mstPromoId) {
        this.mstPromoId = mstPromoId;
    }

    public String getQtyValueSellGrowth() {
        return qtyValueSellGrowth;
    }

    public void setQtyValueSellGrowth(String qtyValueSellGrowth) {
        this.qtyValueSellGrowth = qtyValueSellGrowth;
    }

    public String getSellQty() {
        return sellQty;
    }

    public void setSellQty(String sellQty) {
        this.sellQty = sellQty;
    }

    public String getTicketSizeGrowth() {
        return ticketSizeGrowth;
    }

    public void setTicketSizeGrowth(String ticketSizeGrowth) {
        this.ticketSizeGrowth = ticketSizeGrowth;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getxMstPromoId() {
        return xMstPromoId;
    }

    public void setxMstPromoId(String xMstPromoId) {
        this.xMstPromoId = xMstPromoId;
    }

    public String getyMstPromoId() {
        return yMstPromoId;
    }

    public void setyMstPromoId(String yMstPromoId) {
        this.yMstPromoId = yMstPromoId;
    }

    public String getxFileHistoryFlag() {
        return xFileHistoryFlag;
    }

    public void setxFileHistoryFlag(String xFileHistoryFlag) {
        this.xFileHistoryFlag = xFileHistoryFlag;
    }

    public String getxManualGridData() {
        return xManualGridData;
    }

    public void setxManualGridData(String xManualGridData) {
        this.xManualGridData = xManualGridData;
    }

    public String getTxtRemarks() {
        return txtRemarks;
    }

    public void setTxtRemarks(String txtRemarks) {
        this.txtRemarks = txtRemarks;
    }
    
}
