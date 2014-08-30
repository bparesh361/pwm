/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

import com.fks.promo.init.SearchPromoDashboardCriteria;
import com.fks.promo.init.SearchPromoDashboardEnum;

/**
 *
 * @author ajitn
 */
public class ExecutePromoDashBaordReq {

    private String empID;
    private String startDate;
    private String endDate;
    private int startIndex;
    private ExecutePromoManagerEnum executeEnum;
    private SearchPromoDashboardEnum promoDashboardEnum;
    private SearchPromoDashboardCriteria promoDashboardCriteria;
    private Long zoneId;
    private Long eventId;
    private Long markettingId;
    private Long promoTypeId;
    private String subCategoryName;

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public ExecutePromoManagerEnum getExecuteEnum() {
        return executeEnum;
    }

    public void setExecuteEnum(ExecutePromoManagerEnum executeEnum) {
        this.executeEnum = executeEnum;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public SearchPromoDashboardCriteria getPromoDashboardCriteria() {
        return promoDashboardCriteria;
    }

    public void setPromoDashboardCriteria(SearchPromoDashboardCriteria promoDashboardCriteria) {
        this.promoDashboardCriteria = promoDashboardCriteria;
    }

    public SearchPromoDashboardEnum getPromoDashboardEnum() {
        return promoDashboardEnum;
    }

    public void setPromoDashboardEnum(SearchPromoDashboardEnum promoDashboardEnum) {
        this.promoDashboardEnum = promoDashboardEnum;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getMarkettingId() {
        return markettingId;
    }

    public void setMarkettingId(Long markettingId) {
        this.markettingId = markettingId;
    }

    public Long getPromoTypeId() {
        return promoTypeId;
    }

    public void setPromoTypeId(Long promoTypeId) {
        this.promoTypeId = promoTypeId;
    }
}
