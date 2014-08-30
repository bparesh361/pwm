/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.pwm.phase3;

import java.util.List;

/**
 *
 * @author itadmin
 */
public class PromoStoreUploadReq {

    private String promoId;
    private List<String> storeCodes;

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public List<String> getStoreCodes() {
        return storeCodes;
    }

    public void setStoreCodes(List<String> storeCodes) {
        this.storeCodes = storeCodes;
    }




}
