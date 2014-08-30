/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

import com.fks.promo.common.Resp;

/**
 *
 * @author ajitn
 */
public class DownloadSubPromoResp {

    private Resp resp;
    private String downloadFilePath;

    public DownloadSubPromoResp() {
    }

    public DownloadSubPromoResp(Resp resp) {
        this.resp = resp;
    }

    public DownloadSubPromoResp(Resp resp, String downloadFilePath) {
        this.resp = resp;
        this.downloadFilePath = downloadFilePath;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
}
