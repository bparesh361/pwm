/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "mst_mktg")
@NamedQueries({
    @NamedQuery(name = "MstMktg.findAll", query = "SELECT m FROM MstMktg m"),
    @NamedQuery(name = "MstMktg.findByMktgId", query = "SELECT m FROM MstMktg m WHERE m.mktgId = :mktgId"),
    @NamedQuery(name = "MstMktg.findByMktgName", query = "SELECT m FROM MstMktg m WHERE m.mktgName = :mktgName"),
    @NamedQuery(name = "MstMktg.findByIsBlocked", query = "SELECT m FROM MstMktg m WHERE m.isBlocked = :isBlocked")})
public class MstMktg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mktg_id")
    private Long mktgId;
    @Basic(optional = false)
    @Column(name = "mktg_name")
    private String mktgName;
    @Column(name = "is_blocked")
    private Short isBlocked;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstMktg")
    private Collection<MstPromo> mstPromoCollection;

    public MstMktg() {
    }

    public MstMktg(Long mktgId) {
        this.mktgId = mktgId;
    }

    public MstMktg(Long mktgId, String mktgName) {
        this.mktgId = mktgId;
        this.mktgName = mktgName;
    }

    public Long getMktgId() {
        return mktgId;
    }

    public void setMktgId(Long mktgId) {
        this.mktgId = mktgId;
    }

    public String getMktgName() {
        return mktgName;
    }

    public void setMktgName(String mktgName) {
        this.mktgName = mktgName;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
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
        hash += (mktgId != null ? mktgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstMktg)) {
            return false;
        }
        MstMktg other = (MstMktg) object;
        if ((this.mktgId == null && other.mktgId != null) || (this.mktgId != null && !this.mktgId.equals(other.mktgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstMktg[mktgId=" + mktgId + "]";
    }

}
