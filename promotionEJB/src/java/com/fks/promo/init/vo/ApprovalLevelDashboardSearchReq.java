/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

import com.fks.promo.init.ApprovalSearchType;

/**
 *
 * @author ajitn
 */
public class ApprovalLevelDashboardSearchReq {

    private String zoneId;
    private String storecode;
    private String empId;
    private int page;
    private String categoryName;
    private String subCategoryName;
    private String startDate;
    private String endDate;
    private ApprovalSearchType type;
    private boolean isL1ByPassForL2;
    private String l2EmpId;
    private Long promotionTypeId;
    private Long eventId;
    private Long marketingTypeId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public ApprovalSearchType getType() {
        return type;
    }

    public void setType(ApprovalSearchType type) {
        this.type = type;
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

    public boolean isIsL1ByPassForL2() {
        return isL1ByPassForL2;
    }

    public void setIsL1ByPassForL2(boolean isL1ByPassForL2) {
        this.isL1ByPassForL2 = isL1ByPassForL2;
    }

    public String getL2EmpId() {
        return l2EmpId;
    }

    public void setL2EmpId(String l2EmpId) {
        this.l2EmpId = l2EmpId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getMarketingTypeId() {
        return marketingTypeId;
    }

    public void setMarketingTypeId(Long marketingTypeId) {
        this.marketingTypeId = marketingTypeId;
    }

    public Long getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(Long promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
