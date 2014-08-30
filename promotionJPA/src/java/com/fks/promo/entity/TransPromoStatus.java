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
 * @author krutij
 */
@Entity
@Table(name = "trans_promo_status")
@NamedQueries({
    @NamedQuery(name = "TransPromoStatus.findAll", query = "SELECT t FROM TransPromoStatus t"),
    @NamedQuery(name = "TransPromoStatus.findByTransPromoStatusId", query = "SELECT t FROM TransPromoStatus t WHERE t.transPromoStatusId = :transPromoStatusId"),
    @NamedQuery(name = "TransPromoStatus.findByUpdatedDate", query = "SELECT t FROM TransPromoStatus t WHERE t.updatedDate = :updatedDate"),
    @NamedQuery(name = "TransPromoStatus.findByRemarks", query = "SELECT t FROM TransPromoStatus t WHERE t.remarks = :remarks")})
public class TransPromoStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_promo_status_id")
    private Long transPromoStatusId;
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @Column(name = "remarks")
    private String remarks;
    @JoinColumn(name = "updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;
    @JoinColumn(name = "new_status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "previous_status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus1;
    @JoinColumn(name = "trans_promo_id", referencedColumnName = "trans_promo_id")
    @ManyToOne
    private TransPromo transPromo;

    public TransPromoStatus() {
    }

    public TransPromoStatus(Long transPromoStatusId) {
        this.transPromoStatusId = transPromoStatusId;
    }

    public Long getTransPromoStatusId() {
        return transPromoStatusId;
    }

    public void setTransPromoStatusId(Long transPromoStatusId) {
        this.transPromoStatusId = transPromoStatusId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public MstStatus getMstStatus1() {
        return mstStatus1;
    }

    public void setMstStatus1(MstStatus mstStatus1) {
        this.mstStatus1 = mstStatus1;
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
        hash += (transPromoStatusId != null ? transPromoStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransPromoStatus)) {
            return false;
        }
        TransPromoStatus other = (TransPromoStatus) object;
        if ((this.transPromoStatusId == null && other.transPromoStatusId != null) || (this.transPromoStatusId != null && !this.transPromoStatusId.equals(other.transPromoStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransPromoStatus[transPromoStatusId=" + transPromoStatusId + "]";
    }

}
