/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

import com.fks.promo.common.Resp;

/**
 *
 * @author krutij
 */
public class SubmitOrganizationResp {

    private Resp resp;
    private String filePath;

    public SubmitOrganizationResp(Resp resp, String filePath) {
        this.resp = resp;
        this.filePath = filePath;
    }

    public SubmitOrganizationResp() {
    }
    

    public SubmitOrganizationResp(Resp resp) {
        this.resp = resp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
}
