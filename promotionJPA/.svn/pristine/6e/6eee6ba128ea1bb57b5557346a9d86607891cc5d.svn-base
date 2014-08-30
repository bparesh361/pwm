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
@Table(name = "mst_profile")
@NamedQueries({
    @NamedQuery(name = "MstProfile.findAll", query = "SELECT m FROM MstProfile m"),
    @NamedQuery(name = "MstProfile.findByProfileId", query = "SELECT m FROM MstProfile m WHERE m.profileId = :profileId"),
    @NamedQuery(name = "MstProfile.findByProfileName", query = "SELECT m FROM MstProfile m WHERE m.profileName = :profileName"),
    @NamedQuery(name = "MstProfile.findByLevelAccess", query = "SELECT m FROM MstProfile m WHERE m.levelAccess = :levelAccess")})
public class MstProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "profile_id")
    private Long profileId;
    @Basic(optional = false)
    @Column(name = "profile_name")
    private String profileName;
    @Basic(optional = false)
    @Column(name = "level_access")
    private String levelAccess;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstProfile")
    private Collection<MapRoleProfile> mapRoleProfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstProfile")
    private Collection<MapModuleProfile> mapModuleProfileCollection;

    public MstProfile() {
    }

    public MstProfile(Long profileId) {
        this.profileId = profileId;
    }

    public MstProfile(Long profileId, String profileName, String levelAccess) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.levelAccess = levelAccess;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getLevelAccess() {
        return levelAccess;
    }

    public void setLevelAccess(String levelAccess) {
        this.levelAccess = levelAccess;
    }

    public Collection<MapRoleProfile> getMapRoleProfileCollection() {
        return mapRoleProfileCollection;
    }

    public void setMapRoleProfileCollection(Collection<MapRoleProfile> mapRoleProfileCollection) {
        this.mapRoleProfileCollection = mapRoleProfileCollection;
    }

    public Collection<MapModuleProfile> getMapModuleProfileCollection() {
        return mapModuleProfileCollection;
    }

    public void setMapModuleProfileCollection(Collection<MapModuleProfile> mapModuleProfileCollection) {
        this.mapModuleProfileCollection = mapModuleProfileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profileId != null ? profileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstProfile)) {
            return false;
        }
        MstProfile other = (MstProfile) object;
        if ((this.profileId == null && other.profileId != null) || (this.profileId != null && !this.profileId.equals(other.profileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstProfile[profileId=" + profileId + "]";
    }

}
