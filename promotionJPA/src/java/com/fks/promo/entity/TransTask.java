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
@Table(name = "trans_task")
@NamedQueries({
    @NamedQuery(name = "TransTask.findAll", query = "SELECT t FROM TransTask t"),
    @NamedQuery(name = "TransTask.findByTransTaskId", query = "SELECT t FROM TransTask t WHERE t.transTaskId = :transTaskId"),
    @NamedQuery(name = "TransTask.findByRemarks", query = "SELECT t FROM TransTask t WHERE t.remarks = :remarks"),
    @NamedQuery(name = "TransTask.findByFilePath", query = "SELECT t FROM TransTask t WHERE t.filePath = :filePath"),
    @NamedQuery(name = "TransTask.findByHeaderFilePath", query = "SELECT t FROM TransTask t WHERE t.headerFilePath = :headerFilePath"),
    @NamedQuery(name = "TransTask.findByIsHo", query = "SELECT t FROM TransTask t WHERE t.isHo = :isHo"),
    @NamedQuery(name = "TransTask.findByCreatedTime", query = "SELECT t FROM TransTask t WHERE t.createdTime = :createdTime"),
    @NamedQuery(name = "TransTask.findByUpdatedTime", query = "SELECT t FROM TransTask t WHERE t.updatedTime = :updatedTime")})
public class TransTask implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_task_id")
    private Long transTaskId;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "promo_count")
    private Integer promoCount;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "header_file_path")
    private String headerFilePath;
    @Column(name = "is_ho")
    private Boolean isHo;
    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    @JoinColumn(name = "updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee updatedBy;
    @JoinColumn(name = "created_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee createdBy;
    @JoinColumn(name = "mst_task_id", referencedColumnName = "task_id")
    @ManyToOne
    private MstTask mstTask;
    @JoinColumn(name = "current_status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "assigned_to", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee assignedTo;

    public TransTask() {
    }

    public TransTask(Long transTaskId) {
        this.transTaskId = transTaskId;
    }

    public Long getTransTaskId() {
        return transTaskId;
    }

    public void setTransTaskId(Long transTaskId) {
        this.transTaskId = transTaskId;
    }
    public Integer getPromoCount() {
        return promoCount;
    }

    public void setPromoCount(Integer promoCount) {
        this.promoCount = promoCount;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHeaderFilePath() {
        return headerFilePath;
    }

    public void setHeaderFilePath(String headerFilePath) {
        this.headerFilePath = headerFilePath;
    }

    public Boolean getIsHo() {
        return isHo;
    }

    public void setIsHo(Boolean isHo) {
        this.isHo = isHo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public MstEmployee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MstEmployee createdBy) {
        this.createdBy = createdBy;
    }

    public MstEmployee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(MstEmployee updatedBy) {
        this.updatedBy = updatedBy;
    }  
    public MstTask getMstTask() {
        return mstTask;
    }

    public void setMstTask(MstTask mstTask) {
        this.mstTask = mstTask;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public MstEmployee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(MstEmployee assignedTo) {
        this.assignedTo = assignedTo;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transTaskId != null ? transTaskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransTask)) {
            return false;
        }
        TransTask other = (TransTask) object;
        if ((this.transTaskId == null && other.transTaskId != null) || (this.transTaskId != null && !this.transTaskId.equals(other.transTaskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransTask[transTaskId=" + transTaskId + "]";
    }

}
