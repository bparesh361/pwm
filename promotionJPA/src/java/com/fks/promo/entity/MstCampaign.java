/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mst_campaign")
@NamedQueries({
    @NamedQuery(name = "MstCampaign.findAll", query = "SELECT m FROM MstCampaign m"),
    @NamedQuery(name = "MstCampaign.findByCampaignId", query = "SELECT m FROM MstCampaign m WHERE m.campaignId = :campaignId"),
    @NamedQuery(name = "MstCampaign.findByCampaignName", query = "SELECT m FROM MstCampaign m WHERE m.campaignName = :campaignName"),
    @NamedQuery(name = "MstCampaign.findByIsActive", query = "SELECT m FROM MstCampaign m WHERE m.isActive = :isActive")})
public class MstCampaign implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "campaign_id")
    private Long campaignId;
    @Column(name = "campaign_name")
    private String campaignName;
    @Column(name = "is_active")
    private Boolean isActive;
    @OneToMany(mappedBy = "mstCampaign")
    private Collection<MstPromo> mstPromoCollection;

    public MstCampaign() {
    }

    public MstCampaign(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Collection<MstPromo> getMstPromoCollection() {
        return mstPromoCollection;
    }

    public void setMstPromoCollection(Collection<MstPromo> mstPromoCollection) {
        this.mstPromoCollection = mstPromoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campaignId != null ? campaignId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstCampaign)) {
            return false;
        }
        MstCampaign other = (MstCampaign) object;
        if ((this.campaignId == null && other.campaignId != null) || (this.campaignId != null && !this.campaignId.equals(other.campaignId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstCampaign[campaignId=" + campaignId + "]";
    }
}
