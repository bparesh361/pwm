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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "map_user_department")
@NamedQueries({
    @NamedQuery(name = "MapUserDepartment.findAll", query = "SELECT m FROM MapUserDepartment m"),
    @NamedQuery(name = "MapUserDepartment.findByMapUserDeptId", query = "SELECT m FROM MapUserDepartment m WHERE m.mapUserDeptId = :mapUserDeptId")})
public class MapUserDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_user_dept_id")
    private Long mapUserDeptId;
    @JoinColumn(name = "mst_dept_id", referencedColumnName = "mst_dept_id")
    @ManyToOne(optional = false)
    private MstDepartment mstDepartment;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee;

    public MapUserDepartment() {
    }

    public MapUserDepartment(Long mapUserDeptId) {
        this.mapUserDeptId = mapUserDeptId;
    }

    public Long getMapUserDeptId() {
        return mapUserDeptId;
    }

    public void setMapUserDeptId(Long mapUserDeptId) {
        this.mapUserDeptId = mapUserDeptId;
    }

    public MstDepartment getMstDepartment() {
        return mstDepartment;
    }

    public void setMstDepartment(MstDepartment mstDepartment) {
        this.mstDepartment = mstDepartment;
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
        hash += (mapUserDeptId != null ? mapUserDeptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapUserDepartment)) {
            return false;
        }
        MapUserDepartment other = (MapUserDepartment) object;
        if ((this.mapUserDeptId == null && other.mapUserDeptId != null) || (this.mapUserDeptId != null && !this.mapUserDeptId.equals(other.mapUserDeptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapUserDepartment[mapUserDeptId=" + mapUserDeptId + "]";
    }

}
