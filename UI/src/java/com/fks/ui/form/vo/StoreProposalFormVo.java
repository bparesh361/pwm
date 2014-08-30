/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.ui.form.vo;

/**
 *
 * @author krutij
 */
public class StoreProposalFormVo {
    private String startDate;
    private String endDate;
    private String ZoneSel;
    private String tbProposalNo;
    private String problemType;

    public String getZoneSel() {
        return ZoneSel;
    }

    public void setZoneSel(String ZoneSel) {
        this.ZoneSel = ZoneSel;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTbProposalNo() {
        return tbProposalNo;
    }

    public void setTbProposalNo(String tbProposalNo) {
        this.tbProposalNo = tbProposalNo;
    }
    

}
