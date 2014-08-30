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
@Table(name = "map_role_profile")
@NamedQueries({
    @NamedQuery(name = "MapRoleProfile.findAll", query = "SELECT m FROM MapRoleProfile m"),
    @NamedQuery(name = "MapRoleProfile.findByMapId", query = "SELECT m FROM MapRoleProfile m WHERE m.mapId = :mapId")})
public class MapRoleProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_id")
    private Long mapId;
    @JoinColumn(name = "mst_role_id", referencedColumnName = "mst_role_id")
    @ManyToOne(optional = false)
    private MstRole mstRole;
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    @ManyToOne(optional = false)
    private MstProfile mstProfile;

    public MapRoleProfile() {
    }

    public MapRoleProfile(Long mapId) {
        this.mapId = mapId;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public MstRole getMstRole() {
        return mstRole;
    }

    public void setMstRole(MstRole mstRole) {
        this.mstRole = mstRole;
    }

    public MstProfile getMstProfile() {
        return mstProfile;
    }

    public void setMstProfile(MstProfile mstProfile) {
        this.mstProfile = mstProfile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mapId != null ? mapId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapRoleProfile)) {
            return false;
        }
        MapRoleProfile other = (MapRoleProfile) object;
        if ((this.mapId == null && other.mapId != null) || (this.mapId != null && !this.mapId.equals(other.mapId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapRoleProfile[mapId=" + mapId + "]";
    }

}
