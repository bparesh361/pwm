/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.reports.common;

/**
 *
 * @author krutij
 */
public class ReportsResp {

    private ReportsRespCode respCode;
    private String msg;
    private String reportFilePath;


    public ReportsResp() {
    }
    
    public ReportsResp(ReportsRespCode respCode, String msg) {
        this.respCode = respCode;
        this.msg = msg;
    }

    public ReportsResp(ReportsRespCode respCode, String msg, String reportFilePath) {
        this.respCode = respCode;
        this.msg = msg;
        this.reportFilePath = reportFilePath;
    }

   
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReportsRespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(ReportsRespCode respCode) {
        this.respCode = respCode;
    }

    public String getReportFilePath() {
        return reportFilePath;
    }

    public void setReportFilePath(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }

}
