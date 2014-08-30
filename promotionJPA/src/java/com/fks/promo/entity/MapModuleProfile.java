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
@Table(name = "map_module_profile")
@NamedQueries({
    @NamedQuery(name = "MapModuleProfile.findAll", query = "SELECT m FROM MapModuleProfile m"),
    @NamedQuery(name = "MapModuleProfile.findByMapProfileModuleId", query = "SELECT m FROM MapModuleProfile m WHERE m.mapProfileModuleId = :mapProfileModuleId")})
public class MapModuleProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_profile_module_id")
    private Long mapProfileModuleId;
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    @ManyToOne(optional = false)
    private MstProfile mstProfile;
    @JoinColumn(name = "module_id", referencedColumnName = "module_id")
    @ManyToOne(optional = false)
    private MstModule mstModule;

    public MapModuleProfile() {
    }

    public MapModuleProfile(Long mapProfileModuleId) {
        this.mapProfileModuleId = mapProfileModuleId;
    }

    public Long getMapProfileModuleId() {
        return mapProfileModuleId;
    }

    public void setMapProfileModuleId(Long mapProfileModuleId) {
        this.mapProfileModuleId = mapProfileModuleId;
    }

    public MstProfile getMstProfile() {
        return mstProfile;
    }

    public void setMstProfile(MstProfile mstProfile) {
        this.mstProfile = mstProfile;
    }

    public MstModule getMstModule() {
        return mstModule;
    }

    public void setMstModule(MstModule mstModule) {
        this.mstModule = mstModule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mapProfileModuleId != null ? mapProfileModuleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapModuleProfile)) {
            return false;
        }
        MapModuleProfile other = (MapModuleProfile) object;
        if ((this.mapProfileModuleId == null && other.mapProfileModuleId != null) || (this.mapProfileModuleId != null && !this.mapProfileModuleId.equals(other.mapProfileModuleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapModuleProfile[mapProfileModuleId=" + mapProfileModuleId + "]";
    }

}
