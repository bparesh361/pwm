/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "mst_report")
@NamedQueries({
    @NamedQuery(name = "MstReport.findAll", query = "SELECT m FROM MstReport m"),
    @NamedQuery(name = "MstReport.findByReportId", query = "SELECT m FROM MstReport m WHERE m.reportId = :reportId"),
    @NamedQuery(name = "MstReport.findByInitiatedFrom", query = "SELECT m FROM MstReport m WHERE m.initiatedFrom = :initiatedFrom"),
    @NamedQuery(name = "MstReport.findByReportDate", query = "SELECT m FROM MstReport m WHERE m.reportDate = :reportDate"),
    @NamedQuery(name = "MstReport.findByInitiationDateFrom", query = "SELECT m FROM MstReport m WHERE m.initiationDateFrom = :initiationDateFrom"),
    @NamedQuery(name = "MstReport.findByInitiationDateTo", query = "SELECT m FROM MstReport m WHERE m.initiationDateTo = :initiationDateTo"),
    @NamedQuery(name = "MstReport.findByEventId", query = "SELECT m FROM MstReport m WHERE m.eventId = :eventId"),
    @NamedQuery(name = "MstReport.findByZoneId", query = "SELECT m FROM MstReport m WHERE m.zoneId = :zoneId"),
    @NamedQuery(name = "MstReport.findByCategory", query = "SELECT m FROM MstReport m WHERE m.category = :category"),
    @NamedQuery(name = "MstReport.findBySubCategory", query = "SELECT m FROM MstReport m WHERE m.subCategory = :subCategory"),
    @NamedQuery(name = "MstReport.findByTicketNumber", query = "SELECT m FROM MstReport m WHERE m.ticketNumber = :ticketNumber"),
    @NamedQuery(name = "MstReport.findBySearchStatusId", query = "SELECT m FROM MstReport m WHERE m.searchStatusId = :searchStatusId"),
    @NamedQuery(name = "MstReport.findBySearchStatusName", query = "SELECT m FROM MstReport m WHERE m.searchStatusName = :searchStatusName"),
    @NamedQuery(name = "MstReport.findByReportStatusId", query = "SELECT m FROM MstReport m WHERE m.reportStatusId = :reportStatusId"),
    @NamedQuery(name = "MstReport.findByFilePath", query = "SELECT m FROM MstReport m WHERE m.filePath = :filePath"),
    @NamedQuery(name = "MstReport.findByCreatedBy", query = "SELECT m FROM MstReport m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MstReport.findByProposalProblemTypeId", query = "SELECT m FROM MstReport m WHERE m.proposalProblemTypeId = :proposalProblemTypeId"),
    @NamedQuery(name = "MstReport.findByProposalId", query = "SELECT m FROM MstReport m WHERE m.proposalId = :proposalId"),
    @NamedQuery(name = "MstReport.findByTaskTypeId", query = "SELECT m FROM MstReport m WHERE m.taskTypeId = :taskTypeId"),
    @NamedQuery(name = "MstReport.findByRemarks", query = "SELECT m FROM MstReport m WHERE m.remarks = :remarks")})
public class MstReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "report_id")
    private Long reportId;
    @Column(name = "initiated_from")
    private String initiatedFrom;
    @Column(name = "report_date")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @Column(name = "initiation_date_from")
    @Temporal(TemporalType.DATE)
    private Date initiationDateFrom;
    @Column(name = "initiation_date_to")
    @Temporal(TemporalType.DATE)
    private Date initiationDateTo;
    @Column(name = "event_id")
    private BigInteger eventId;
    @Column(name = "zone_id")
    private BigInteger zoneId;
    @Column(name = "category")
    private String category;
    @Column(name = "sub_category")
    private String subCategory;
    @Column(name = "ticket_number")
    private String ticketNumber;
    @Column(name = "search_status_id")
    private BigInteger searchStatusId;
    @Column(name = "search_status_name")
    private String searchStatusName;
    @Column(name = "report_status_id")
    private BigInteger reportStatusId;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "proposal_problem_type_id")
    private BigInteger proposalProblemTypeId;
    @Column(name = "proposal_id")
    private String proposalId;
    @Column(name = "task_type_id")
    private BigInteger taskTypeId;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "vendor_backed")
    private String vendorbacked;
    @JoinColumn(name = "mst_report_type_id", referencedColumnName = "mst_report_type_id")
    @ManyToOne
    private MstReportType mstReportType;

    public MstReport() {
    }

    public String getVendorbacked() {
        return vendorbacked;
    }

    public void setVendorbacked(String vendorbacked) {
        this.vendorbacked = vendorbacked;
    }

    public MstReport(Long reportId) {
        this.reportId = reportId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getInitiatedFrom() {
        return initiatedFrom;
    }

    public void setInitiatedFrom(String initiatedFrom) {
        this.initiatedFrom = initiatedFrom;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getInitiationDateFrom() {
        return initiationDateFrom;
    }

    public void setInitiationDateFrom(Date initiationDateFrom) {
        this.initiationDateFrom = initiationDateFrom;
    }

    public Date getInitiationDateTo() {
        return initiationDateTo;
    }

    public void setInitiationDateTo(Date initiationDateTo) {
        this.initiationDateTo = initiationDateTo;
    }

    public BigInteger getEventId() {
        return eventId;
    }

    public void setEventId(BigInteger eventId) {
        this.eventId = eventId;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public BigInteger getSearchStatusId() {
        return searchStatusId;
    }

    public void setSearchStatusId(BigInteger searchStatusId) {
        this.searchStatusId = searchStatusId;
    }

    public String getSearchStatusName() {
        return searchStatusName;
    }

    public void setSearchStatusName(String searchStatusName) {
        this.searchStatusName = searchStatusName;
    }

    public BigInteger getReportStatusId() {
        return reportStatusId;
    }

    public void setReportStatusId(BigInteger reportStatusId) {
        this.reportStatusId = reportStatusId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public BigInteger getProposalProblemTypeId() {
        return proposalProblemTypeId;
    }

    public void setProposalProblemTypeId(BigInteger proposalProblemTypeId) {
        this.proposalProblemTypeId = proposalProblemTypeId;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public BigInteger getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(BigInteger taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public MstReportType getMstReportType() {
        return mstReportType;
    }

    public void setMstReportType(MstReportType mstReportType) {
        this.mstReportType = mstReportType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstReport)) {
            return false;
        }
        MstReport other = (MstReport) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstReport[reportId=" + reportId + "]";
    }


}
