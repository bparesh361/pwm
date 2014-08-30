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
@Table(name = "mst_department")
@NamedQueries({
    @NamedQuery(name = "MstDepartment.findAll", query = "SELECT m FROM MstDepartment m"),
    @NamedQuery(name = "MstDepartment.findByMstDeptId", query = "SELECT m FROM MstDepartment m WHERE m.mstDeptId = :mstDeptId"),
    @NamedQuery(name = "MstDepartment.findByDeptName", query = "SELECT m FROM MstDepartment m WHERE m.deptName = :deptName"),
    @NamedQuery(name = "MstDepartment.findByIsBlocked", query = "SELECT m FROM MstDepartment m WHERE m.isBlocked = :isBlocked")})
public class MstDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mst_dept_id")
    private Long mstDeptId;
    @Basic(optional = false)
    @Column(name = "dept_name")
    private String deptName;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstDepartment")
    private Collection<MapUserDepartment> mapUserDepartmentCollection;
    @OneToMany(mappedBy = "mstDepartment")
    private Collection<MstProposal> mstProposalCollection;

    public MstDepartment() {
    }

    public MstDepartment(Long mstDeptId) {
        this.mstDeptId = mstDeptId;
    }

    public MstDepartment(Long mstDeptId, String deptName) {
        this.mstDeptId = mstDeptId;
        this.deptName = deptName;
    }

    public Long getMstDeptId() {
        return mstDeptId;
    }

    public void setMstDeptId(Long mstDeptId) {
        this.mstDeptId = mstDeptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<MapUserDepartment> getMapUserDepartmentCollection() {
        return mapUserDepartmentCollection;
    }

    public void setMapUserDepartmentCollection(Collection<MapUserDepartment> mapUserDepartmentCollection) {
        this.mapUserDepartmentCollection = mapUserDepartmentCollection;
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
        hash += (mstDeptId != null ? mstDeptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstDepartment)) {
            return false;
        }
        MstDepartment other = (MstDepartment) object;
        if ((this.mstDeptId == null && other.mstDeptId != null) || (this.mstDeptId != null && !this.mstDeptId.equals(other.mstDeptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstDepartment[mstDeptId=" + mstDeptId + "]";
    }

}
