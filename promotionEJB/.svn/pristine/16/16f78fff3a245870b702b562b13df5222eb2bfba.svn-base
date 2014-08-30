/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.vo;

import com.fks.promo.common.Resp;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ajitn
 */
public class FilePromoCloseResp {

    private Resp resp;
    private List<PromoCloseVO> promoCloseList;
//    private List<Long> promoCloseEmailIDList;
    private Map<Long, Long> promoCloseEmailIDList;

    public FilePromoCloseResp() {
    }

    public FilePromoCloseResp(Resp resp) {
        this.resp = resp;
    }

    public FilePromoCloseResp(Resp resp, Map<Long, Long> promoCloseEmailIDList) {
        this.resp = resp;
        this.promoCloseEmailIDList = promoCloseEmailIDList;
    }

    public FilePromoCloseResp(Resp resp, List<PromoCloseVO> promoCloseList, Map<Long, Long> promoCloseEmailIDList) {
        this.resp = resp;
        this.promoCloseList = promoCloseList;
        this.promoCloseEmailIDList = promoCloseEmailIDList;
    }

    public List<PromoCloseVO> getPromoCloseList() {
        return promoCloseList;
    }

    public void setPromoCloseList(List<PromoCloseVO> promoCloseList) {
        this.promoCloseList = promoCloseList;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public Map<Long, Long> getPromoCloseEmailIDList() {
        return promoCloseEmailIDList;
    }

    public void setPromoCloseEmailIDList(Map<Long, Long> promoCloseEmailIDList) {
        this.promoCloseEmailIDList = promoCloseEmailIDList;
    }
}
