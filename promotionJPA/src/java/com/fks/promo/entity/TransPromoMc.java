/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "trans_promo_mc")
@NamedQueries({
    @NamedQuery(name = "TransPromoMc.findAll", query = "SELECT t FROM TransPromoMc t"),
    @NamedQuery(name = "TransPromoMc.findByTransPromoMcId", query = "SELECT t FROM TransPromoMc t WHERE t.transPromoMcId = :transPromoMcId"),
    @NamedQuery(name = "TransPromoMc.findByUpdatedTime", query = "SELECT t FROM TransPromoMc t WHERE t.updatedTime = :updatedTime")})
public class TransPromoMc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_promo_mc_id")
    private Long transPromoMcId;
    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    @JoinColumn(name = "mc_code", referencedColumnName = "mc_code")
    @ManyToOne
    private Mch mch;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;
    @JoinColumn(name = "trans_promo_id", referencedColumnName = "trans_promo_id")
    @ManyToOne
    private TransPromo transPromo;

    public TransPromoMc() {
    }

    public TransPromoMc(Long transPromoMcId) {
        this.transPromoMcId = transPromoMcId;
    }

    public Long getTransPromoMcId() {
        return transPromoMcId;
    }

    public void setTransPromoMcId(Long transPromoMcId) {
        this.transPromoMcId = transPromoMcId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Mch getMch() {
        return mch;
    }

    public void setMch(Mch mch) {
        this.mch = mch;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    public TransPromo getTransPromo() {
        return transPromo;
    }

    public void setTransPromo(TransPromo transPromo) {
        this.transPromo = transPromo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transPromoMcId != null ? transPromoMcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransPromoMc)) {
            return false;
        }
        TransPromoMc other = (TransPromoMc) object;
        if ((this.transPromoMcId == null && other.transPromoMcId != null) || (this.transPromoMcId != null && !this.transPromoMcId.equals(other.transPromoMcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransPromoMc[transPromoMcId=" + transPromoMcId + "]";
    }

}
