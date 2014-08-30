/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mst_status")
@NamedQueries({
    @NamedQuery(name = "MstStatus.findAll", query = "SELECT m FROM MstStatus m"),
    @NamedQuery(name = "MstStatus.findByStatusId", query = "SELECT m FROM MstStatus m WHERE m.statusId = :statusId"),
    @NamedQuery(name = "MstStatus.findByL1", query = "SELECT m FROM MstStatus m WHERE m.l1 = :l1"),
    @NamedQuery(name = "MstStatus.findByStatusDesc", query = "SELECT m FROM MstStatus m WHERE m.statusDesc = :statusDesc"),
    @NamedQuery(name = "MstStatus.findByL2", query = "SELECT m FROM MstStatus m WHERE m.l2 = :l2"),
    @NamedQuery(name = "MstStatus.findByL5", query = "SELECT m FROM MstStatus m WHERE m.l5 = :l5"),
    @NamedQuery(name = "MstStatus.findByUpdatedDate", query = "SELECT m FROM MstStatus m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "MstStatus.findByIsLeadTime", query = "SELECT m FROM MstStatus m WHERE m.isLeadTime = :isLeadTime")})
public class MstStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "status_id")
    private Long statusId;
    @Column(name = "L1")
    private String l1;
    @Basic(optional = false)
    @Column(name = "status_desc")
    private String statusDesc;
    @Column(name = "L2")
    private String l2;
    @Column(name = "L5")
    private String l5;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @Column(name = "is_lead_time")
    private Short isLeadTime;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<MstPromo> mstPromoCollection;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<TransPromoArticle> transPromoArticleCollection;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<TransTask> transTaskCollection;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<TransPromo> transPromoCollection;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<TransPromoStatus> transPromoStatusCollection;
    @OneToMany(mappedBy = "mstStatus1")
    private Collection<TransPromoStatus> transPromoStatusCollection1;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<MstProposal> mstProposalCollection;
    @OneToMany(mappedBy = "mstStatus")
    private Collection<TransPromoMc> transPromoMcCollection;

    public MstStatus() {
    }

    public MstStatus(Long statusId) {
        this.statusId = statusId;
    }

    public MstStatus(Long statusId, String statusDesc, Date updatedDate) {
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.updatedDate = updatedDate;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getL1() {
        return l1;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getL2() {
        return l2;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public String getL5() {
        return l5;
    }

    public void setL5(String l5) {
        this.l5 = l5;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Short getIsLeadTime() {
        return isLeadTime;
    }

    public void setIsLeadTime(Short isLeadTime) {
        this.isLeadTime = isLeadTime;
    }

    public Collection<MstPromo> getMstPromoCollection() {
        return mstPromoCollection;
    }

    public void setMstPromoCollection(Collection<MstPromo> mstPromoCollection) {
        this.mstPromoCollection = mstPromoCollection;
    }

    public Collection<TransPromoArticle> getTransPromoArticleCollection() {
        return transPromoArticleCollection;
    }

    public void setTransPromoArticleCollection(Collection<TransPromoArticle> transPromoArticleCollection) {
        this.transPromoArticleCollection = transPromoArticleCollection;
    }

    public Collection<TransTask> getTransTaskCollection() {
        return transTaskCollection;
    }

    public void setTransTaskCollection(Collection<TransTask> transTaskCollection) {
        this.transTaskCollection = transTaskCollection;
    }

    public Collection<TransPromo> getTransPromoCollection() {
        return transPromoCollection;
    }

    public void setTransPromoCollection(Collection<TransPromo> transPromoCollection) {
        this.transPromoCollection = transPromoCollection;
    }

    public Collection<TransPromoStatus> getTransPromoStatusCollection() {
        return transPromoStatusCollection;
    }

    public void setTransPromoStatusCollection(Collection<TransPromoStatus> transPromoStatusCollection) {
        this.transPromoStatusCollection = transPromoStatusCollection;
    }

    public Collection<TransPromoStatus> getTransPromoStatusCollection1() {
        return transPromoStatusCollection1;
    }

    public void setTransPromoStatusCollection1(Collection<TransPromoStatus> transPromoStatusCollection1) {
        this.transPromoStatusCollection1 = transPromoStatusCollection1;
    }

    public Collection<MstProposal> getMstProposalCollection() {
        return mstProposalCollection;
    }

    public void setMstProposalCollection(Collection<MstProposal> mstProposalCollection) {
        this.mstProposalCollection = mstProposalCollection;
    }

    public Collection<TransPromoMc> getTransPromoMcCollection() {
        return transPromoMcCollection;
    }

    public void setTransPromoMcCollection(Collection<TransPromoMc> transPromoMcCollection) {
        this.transPromoMcCollection = transPromoMcCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstStatus)) {
            return false;
        }
        MstStatus other = (MstStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstStatus[statusId=" + statusId + "]";
    }

}
