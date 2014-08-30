/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;

/**
 *
 * @author ajitn
 */
public class FileResp {

    private Boolean isError;
    private String errorMsg;

    public FileResp() {
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
}
