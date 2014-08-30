/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "mst_proposal")
@NamedQueries({
    @NamedQuery(name = "MstProposal.findAll", query = "SELECT m FROM MstProposal m"),
    @NamedQuery(name = "MstProposal.findByProposalId", query = "SELECT m FROM MstProposal m WHERE m.proposalId = :proposalId"),
    @NamedQuery(name = "MstProposal.findByCreatedDate", query = "SELECT m FROM MstProposal m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MstProposal.findByRemarks", query = "SELECT m FROM MstProposal m WHERE m.remarks = :remarks"),
    @NamedQuery(name = "MstProposal.findBySolutionDesc", query = "SELECT m FROM MstProposal m WHERE m.solutionDesc = :solutionDesc"),
    @NamedQuery(name = "MstProposal.findByPromoDesc", query = "SELECT m FROM MstProposal m WHERE m.promoDesc = :promoDesc"),
    @NamedQuery(name = "MstProposal.findByFilePath", query = "SELECT m FROM MstProposal m WHERE m.filePath = :filePath"),
    @NamedQuery(name = "MstProposal.findByFileStatus", query = "SELECT m FROM MstProposal m WHERE m.fileStatus = :fileStatus"),
    @NamedQuery(name = "MstProposal.findByZoneId", query = "SELECT m FROM MstProposal m WHERE m.zoneId = :zoneId"),
    @NamedQuery(name = "MstProposal.findByInitiatorRemarks", query = "SELECT m FROM MstProposal m WHERE m.initiatorRemarks = :initiatorRemarks"),
    @NamedQuery(name = "MstProposal.findByUpdatedDate", query = "SELECT m FROM MstProposal m WHERE m.updatedDate = :updatedDate")})
public class MstProposal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "proposal_id")
    private Long proposalId;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "solution_desc")
    private String solutionDesc;
    @Column(name = "promo_desc")
    private String promoDesc;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_status")
    private Boolean fileStatus;
    @Column(name = "zone_id")
    private String zoneId;
    @Column(name = "other_file_path")
    private String otherFilePath;
    @Column(name = "initiator_remarks")
    private String initiatorRemarks;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstProposal")
    private Collection<TransProposal> transProposalCollection;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "dept_id", referencedColumnName = "mst_dept_id")
    @ManyToOne
    private MstDepartment mstDepartment;
    @JoinColumn(name = "promo_type_id", referencedColumnName = "promo_type_id")
    @ManyToOne
    private MstPromotionType mstPromotionType;
    @JoinColumn(name = "problem_type_id", referencedColumnName = "problem_type_id")
    @ManyToOne
    private MstProblem mstProblem;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee;

    public MstProposal() {
    }

    public MstProposal(Long proposalId) {
        this.proposalId = proposalId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSolutionDesc() {
        return solutionDesc;
    }

    public void setSolutionDesc(String solutionDesc) {
        this.solutionDesc = solutionDesc;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Boolean fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getOtherFilePath() {
        return otherFilePath;
    }

    public void setOtherFilePath(String otherFilePath) {
        this.otherFilePath = otherFilePath;
    }

    public String getInitiatorRemarks() {
        return initiatorRemarks;
    }

    public void setInitiatorRemarks(String initiatorRemarks) {
        this.initiatorRemarks = initiatorRemarks;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Collection<TransProposal> getTransProposalCollection() {
        return transProposalCollection;
    }

    public void setTransProposalCollection(Collection<TransProposal> transProposalCollection) {
        this.transProposalCollection = transProposalCollection;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public MstDepartment getMstDepartment() {
        return mstDepartment;
    }

    public void setMstDepartment(MstDepartment mstDepartment) {
        this.mstDepartment = mstDepartment;
    }

    public MstPromotionType getMstPromotionType() {
        return mstPromotionType;
    }

    public void setMstPromotionType(MstPromotionType mstPromotionType) {
        this.mstPromotionType = mstPromotionType;
    }

    public MstProblem getMstProblem() {
        return mstProblem;
    }

    public void setMstProblem(MstProblem mstProblem) {
        this.mstProblem = mstProblem;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proposalId != null ? proposalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstProposal)) {
            return false;
        }
        MstProposal other = (MstProposal) object;
        if ((this.proposalId == null && other.proposalId != null) || (this.proposalId != null && !this.proposalId.equals(other.proposalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstProposal[proposalId=" + proposalId + "]";
    }
}
