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
@Table(name = "mst_location")
@NamedQueries({
    @NamedQuery(name = "MstLocation.findAll", query = "SELECT m FROM MstLocation m"),
    @NamedQuery(name = "MstLocation.findByLocationId", query = "SELECT m FROM MstLocation m WHERE m.locationId = :locationId"),
    @NamedQuery(name = "MstLocation.findByLocationName", query = "SELECT m FROM MstLocation m WHERE m.locationName = :locationName")})
public class MstLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "location_id")
    private Long locationId;
    @Basic(optional = false)
    @Column(name = "location_name")
    private String locationName;
    @OneToMany(mappedBy = "mstLocation")
    private Collection<Mch> mchCollection;
    @OneToMany(mappedBy = "mstLocation")
    private Collection<MstStore> mstStoreCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstLocation")
    private Collection<MapRoleLocation> mapRoleLocationCollection;

    public MstLocation() {
    }

    public MstLocation(Long locationId) {
        this.locationId = locationId;
    }

    public MstLocation(Long locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Collection<Mch> getMchCollection() {
        return mchCollection;
    }

    public void setMchCollection(Collection<Mch> mchCollection) {
        this.mchCollection = mchCollection;
    }

    public Collection<MstStore> getMstStoreCollection() {
        return mstStoreCollection;
    }

    public void setMstStoreCollection(Collection<MstStore> mstStoreCollection) {
        this.mstStoreCollection = mstStoreCollection;
    }

    public Collection<MapRoleLocation> getMapRoleLocationCollection() {
        return mapRoleLocationCollection;
    }

    public void setMapRoleLocationCollection(Collection<MapRoleLocation> mapRoleLocationCollection) {
        this.mapRoleLocationCollection = mapRoleLocationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstLocation)) {
            return false;
        }
        MstLocation other = (MstLocation) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstLocation[locationId=" + locationId + "]";
    }

}
