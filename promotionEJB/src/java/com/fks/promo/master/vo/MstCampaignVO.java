/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

/**
 *
 * @author ajitn
 */
public class MstCampaignVO {

    private  Long campaignID;
    private String campaignName;
    private boolean isActive;

    public MstCampaignVO() {
    }

    public MstCampaignVO(Long campaignID, String campaignName, boolean isActive) {
        this.campaignID = campaignID;
        this.campaignName = campaignName;
        this.isActive = isActive;
    }

    public Long getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(Long campaignID) {
        this.campaignID = campaignID;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
