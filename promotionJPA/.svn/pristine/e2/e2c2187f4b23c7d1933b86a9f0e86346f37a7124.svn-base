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
@Table(name = "mst_promotion_type")
@NamedQueries({
    @NamedQuery(name = "MstPromotionType.findAll", query = "SELECT m FROM MstPromotionType m"),
    @NamedQuery(name = "MstPromotionType.findByPromoTypeId", query = "SELECT m FROM MstPromotionType m WHERE m.promoTypeId = :promoTypeId"),
    @NamedQuery(name = "MstPromotionType.findByPromoTypeName", query = "SELECT m FROM MstPromotionType m WHERE m.promoTypeName = :promoTypeName"),
    @NamedQuery(name = "MstPromotionType.findByIsBlocked", query = "SELECT m FROM MstPromotionType m WHERE m.isBlocked = :isBlocked")})
public class MstPromotionType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promo_type_id")
    private Long promoTypeId;
    @Basic(optional = false)
    @Column(name = "promo_type_name")
    private String promoTypeName;
    @Column(name = "is_blocked")
    private Short isBlocked;
    @OneToMany(mappedBy = "mstPromotionType")
    private Collection<TransPromo> transPromoCollection;
    @OneToMany(mappedBy = "mstPromotionType")
    private Collection<MstProposal> mstProposalCollection;

    public MstPromotionType() {
    }

    public MstPromotionType(Long promoTypeId) {
        this.promoTypeId = promoTypeId;
    }

    public MstPromotionType(Long promoTypeId, String promoTypeName) {
        this.promoTypeId = promoTypeId;
        this.promoTypeName = promoTypeName;
    }

    public Long getPromoTypeId() {
        return promoTypeId;
    }

    public void setPromoTypeId(Long promoTypeId) {
        this.promoTypeId = promoTypeId;
    }

    public String getPromoTypeName() {
        return promoTypeName;
    }

    public void setPromoTypeName(String promoTypeName) {
        this.promoTypeName = promoTypeName;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<TransPromo> getTransPromoCollection() {
        return transPromoCollection;
    }

    public void setTransPromoCollection(Collection<TransPromo> transPromoCollection) {
        this.transPromoCollection = transPromoCollection;
    }

    public Collection<MstProposal> getMstProposalCollection() {
        return mstProposalCollection;
    }

    public void setMstProposalCollection(Collection<MstProposal> mstProposalCollection) {
        this.mstProposalCollection = mstProposalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promoTypeId != null ? promoTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstPromotionType)) {
            return false;
        }
        MstPromotionType other = (MstPromotionType) object;
        if ((this.promoTypeId == null && other.promoTypeId != null) || (this.promoTypeId != null && !this.promoTypeId.equals(other.promoTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstPromotionType[promoTypeId=" + promoTypeId + "]";
    }

}
