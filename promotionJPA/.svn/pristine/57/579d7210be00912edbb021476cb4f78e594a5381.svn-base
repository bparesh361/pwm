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
@Table(name = "mst_role")
@NamedQueries({
    @NamedQuery(name = "MstRole.findAll", query = "SELECT m FROM MstRole m"),
    @NamedQuery(name = "MstRole.findByMstRoleId", query = "SELECT m FROM MstRole m WHERE m.mstRoleId = :mstRoleId"),
    @NamedQuery(name = "MstRole.findByRoleName", query = "SELECT m FROM MstRole m WHERE m.roleName = :roleName"),
    @NamedQuery(name = "MstRole.findByIsActive", query = "SELECT m FROM MstRole m WHERE m.isActive = :isActive"),
    @NamedQuery(name = "MstRole.findByCreatedDate", query = "SELECT m FROM MstRole m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MstRole.findByUpdatedDate", query = "SELECT m FROM MstRole m WHERE m.updatedDate = :updatedDate")})
public class MstRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mst_role_id")
    private Long mstRoleId;
    @Basic(optional = false)
    @Column(name = "role_name")
    private String roleName;
    @Basic(optional = false)
    @Column(name = "is_active")
    private short isActive;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstRole")
    private Collection<MapRoleMch> mapRoleMchCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstRole")
    private Collection<MapRoleZone> mapRoleZoneCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstRole")
    private Collection<MapRoleProfile> mapRoleProfileCollection;
    @OneToMany(mappedBy = "mstRole")
    private Collection<MapRoleLocation> mapRoleLocationCollection;
    @OneToMany(mappedBy = "mstRole")
    private Collection<MstEmployee> mstEmployeeCollection;
    @JoinColumn(name = "updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;
    @JoinColumn(name = "created_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee1;

    public MstRole() {
    }

    public MstRole(Long mstRoleId) {
        this.mstRoleId = mstRoleId;
    }

    public MstRole(Long mstRoleId, String roleName, short isActive) {
        this.mstRoleId = mstRoleId;
        this.roleName = roleName;
        this.isActive = isActive;
    }

    public Long getMstRoleId() {
        return mstRoleId;
    }

    public void setMstRoleId(Long mstRoleId) {
        this.mstRoleId = mstRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Collection<MapRoleMch> getMapRoleMchCollection() {
        return mapRoleMchCollection;
    }

    public void setMapRoleMchCollection(Collection<MapRoleMch> mapRoleMchCollection) {
        this.mapRoleMchCollection = mapRoleMchCollection;
    }

    public Collection<MapRoleZone> getMapRoleZoneCollection() {
        return mapRoleZoneCollection;
    }

    public void setMapRoleZoneCollection(Collection<MapRoleZone> mapRoleZoneCollection) {
        this.mapRoleZoneCollection = mapRoleZoneCollection;
    }

    public Collection<MapRoleProfile> getMapRoleProfileCollection() {
        return mapRoleProfileCollection;
    }

    public void setMapRoleProfileCollection(Collection<MapRoleProfile> mapRoleProfileCollection) {
        this.mapRoleProfileCollection = mapRoleProfileCollection;
    }

    public Collection<MapRoleLocation> getMapRoleLocationCollection() {
        return mapRoleLocationCollection;
    }

    public void setMapRoleLocationCollection(Collection<MapRoleLocation> mapRoleLocationCollection) {
        this.mapRoleLocationCollection = mapRoleLocationCollection;
    }

    public Collection<MstEmployee> getMstEmployeeCollection() {
        return mstEmployeeCollection;
    }

    public void setMstEmployeeCollection(Collection<MstEmployee> mstEmployeeCollection) {
        this.mstEmployeeCollection = mstEmployeeCollection;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    public MstEmployee getMstEmployee1() {
        return mstEmployee1;
    }

    public void setMstEmployee1(MstEmployee mstEmployee1) {
        this.mstEmployee1 = mstEmployee1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mstRoleId != null ? mstRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstRole)) {
            return false;
        }
        MstRole other = (MstRole) object;
        if ((this.mstRoleId == null && other.mstRoleId != null) || (this.mstRoleId != null && !this.mstRoleId.equals(other.mstRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstRole[mstRoleId=" + mstRoleId + "]";
    }

}
