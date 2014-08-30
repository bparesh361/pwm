/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

import com.fks.promo.common.Resp;

/**
 *
 * @author ajitn
 */
public class ValidateMCResp {

    private Resp resp;
    private String mcCode;
    private String mcDesc;

    public ValidateMCResp() {
    }

    public ValidateMCResp(Resp resp) {
        this.resp = resp;
    }

    public ValidateMCResp(Resp resp, String mcCode, String mcDesc) {
        this.resp = resp;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getMcDesc() {
        return mcDesc;
    }

    public void setMcDesc(String mcDesc) {
        this.mcDesc = mcDesc;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
}
