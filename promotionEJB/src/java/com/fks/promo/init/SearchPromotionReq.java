/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init;

/**
 *
 * @author Paresb
 */
public class SearchPromotionReq {

    private PromoSearchType type;
    private SearchPromoDashboardEnum promoDashboardEnum;
    private SearchPromoDashboardCriteria promoDashboardCriteria;
    private int startIdex;
    private Long statusId;
    private Long zoneId;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public int getStartIdex() {
        return startIdex;
    }

    public void setStartIdex(int startIdex) {
        this.startIdex = startIdex;
    }

    public PromoSearchType getType() {
        return type;
    }

    public void setType(PromoSearchType type) {
        this.type = type;
    }

    public SearchPromoDashboardEnum getPromoDashboardEnum() {
        return promoDashboardEnum;
    }

    public void setPromoDashboardEnum(SearchPromoDashboardEnum promoDashboardEnum) {
        this.promoDashboardEnum = promoDashboardEnum;
    }

    public SearchPromoDashboardCriteria getPromoDashboardCriteria() {
        return promoDashboardCriteria;
    }

    public void setPromoDashboardCriteria(SearchPromoDashboardCriteria promoDashboardCriteria) {
        this.promoDashboardCriteria = promoDashboardCriteria;
    }
}
