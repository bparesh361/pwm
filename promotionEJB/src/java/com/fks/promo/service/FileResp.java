/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

/**
 *
 * @author ajitn
 */
public class FileResp {

    private Boolean isError;
    private String errorMsg;
    private String errorFilePath;

    public FileResp() {
    }

    public FileResp(Boolean isError, String errorMsg, String errorFilePath) {
        this.isError = isError;
        this.errorMsg = errorMsg;
        this.errorFilePath = errorFilePath;
    }


    public FileResp(Boolean isError, String errorMsg) {
        this.isError = isError;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }
     public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

}
