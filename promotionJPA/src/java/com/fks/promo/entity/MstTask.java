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
@Table(name = "mst_task")
@NamedQueries({
    @NamedQuery(name = "MstTask.findAll", query = "SELECT m FROM MstTask m"),
    @NamedQuery(name = "MstTask.findByTaskId", query = "SELECT m FROM MstTask m WHERE m.taskId = :taskId"),
    @NamedQuery(name = "MstTask.findByTaskName", query = "SELECT m FROM MstTask m WHERE m.taskName = :taskName"),
    @NamedQuery(name = "MstTask.findByIsBlocked", query = "SELECT m FROM MstTask m WHERE m.isBlocked = :isBlocked")})
public class MstTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "task_id")
    private Long taskId;
    @Basic(optional = false)
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "is_blocked")
    private Short isBlocked;
    @OneToMany(mappedBy = "mstTask")
    private Collection<TransTask> transTaskCollection;

    public MstTask() {
    }

    public MstTask(Long taskId) {
        this.taskId = taskId;
    }

    public MstTask(Long taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<TransTask> getTransTaskCollection() {
        return transTaskCollection;
    }

    public void setTransTaskCollection(Collection<TransTask> transTaskCollection) {
        this.transTaskCollection = transTaskCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstTask)) {
            return false;
        }
        MstTask other = (MstTask) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstTask[taskId=" + taskId + "]";
    }
}
