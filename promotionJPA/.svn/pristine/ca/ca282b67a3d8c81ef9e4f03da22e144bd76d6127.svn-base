/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mst_reason_rejection")
@NamedQueries({
    @NamedQuery(name = "MstReasonRejection.findAll", query = "SELECT m FROM MstReasonRejection m"),
    @NamedQuery(name = "MstReasonRejection.findByReasonRejectionId", query = "SELECT m FROM MstReasonRejection m WHERE m.reasonRejectionId = :reasonRejectionId"),
    @NamedQuery(name = "MstReasonRejection.findByReasonName", query = "SELECT m FROM MstReasonRejection m WHERE m.reasonName = :reasonName"),
    @NamedQuery(name = "MstReasonRejection.findByIs1", query = "SELECT m FROM MstReasonRejection m WHERE m.is1 = :is1"),
    @NamedQuery(name = "MstReasonRejection.findByIsApprover", query = "SELECT m FROM MstReasonRejection m WHERE m.isApprover = :isApprover"),
    @NamedQuery(name = "MstReasonRejection.findByIsBlocked", query = "SELECT m FROM MstReasonRejection m WHERE m.isBlocked = :isBlocked")})
public class MstReasonRejection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reason_rejection_id")
    private Long reasonRejectionId;
    @Basic(optional = false)
    @Column(name = "reason_name")
    private String reasonName;
    @Basic(optional = false)
    @Column(name = "is_1")
    private short is1;
    @Basic(optional = false)
    @Column(name = "is_approver")
    private short isApprover;
    @Column(name = "is_blocked")
    private Short isBlocked;

    public MstReasonRejection() {
    }

    public MstReasonRejection(Long reasonRejectionId) {
        this.reasonRejectionId = reasonRejectionId;
    }

    public MstReasonRejection(Long reasonRejectionId, String reasonName, short is1, short isApprover) {
        this.reasonRejectionId = reasonRejectionId;
        this.reasonName = reasonName;
        this.is1 = is1;
        this.isApprover = isApprover;
    }

    public Long getReasonRejectionId() {
        return reasonRejectionId;
    }

    public void setReasonRejectionId(Long reasonRejectionId) {
        this.reasonRejectionId = reasonRejectionId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public short getIs1() {
        return is1;
    }

    public void setIs1(short is1) {
        this.is1 = is1;
    }

    public short getIsApprover() {
        return isApprover;
    }

    public void setIsApprover(short isApprover) {
        this.isApprover = isApprover;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reasonRejectionId != null ? reasonRejectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstReasonRejection)) {
            return false;
        }
        MstReasonRejection other = (MstReasonRejection) object;
        if ((this.reasonRejectionId == null && other.reasonRejectionId != null) || (this.reasonRejectionId != null && !this.reasonRejectionId.equals(other.reasonRejectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstReasonRejection[reasonRejectionId=" + reasonRejectionId + "]";
    }

}
