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
@Table(name = "mst_zone")
@NamedQueries({
    @NamedQuery(name = "MstZone.findAll", query = "SELECT m FROM MstZone m"),
    @NamedQuery(name = "MstZone.findByZoneId", query = "SELECT m FROM MstZone m WHERE m.zoneId = :zoneId"),
    @NamedQuery(name = "MstZone.findByZoneName", query = "SELECT m FROM MstZone m WHERE m.zoneName = :zoneName"),
    @NamedQuery(name = "MstZone.findByZoneCode", query = "SELECT m FROM MstZone m WHERE m.zoneCode = :zoneCode"),
    @NamedQuery(name = "MstZone.findByIsBlocked", query = "SELECT m FROM MstZone m WHERE m.isBlocked = :isBlocked")})
public class MstZone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "zone_id")
    private Long zoneId;
    @Basic(optional = false)
    @Column(name = "zone_name")
    private String zoneName;
    @Basic(optional = false)
    @Column(name = "zone_code")
    private String zoneCode;
    @Column(name = "is_blocked")
    private Short isBlocked;
    @OneToMany(mappedBy = "mstZone")
    private Collection<MapPromoZone> mapPromoZoneCollection;
    @OneToMany(mappedBy = "mstZone")
    private Collection<MstStore> mstStoreCollection;
    @OneToMany(mappedBy = "mstZone")
    private Collection<TransPromo> transPromoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstZone")
    private Collection<MapRoleZone> mapRoleZoneCollection;

    public MstZone() {
    }

    public MstZone(Long zoneId) {
        this.zoneId = zoneId;
    }

    public MstZone(Long zoneId, String zoneName, String zoneCode) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.zoneCode = zoneCode;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<MapPromoZone> getMapPromoZoneCollection() {
        return mapPromoZoneCollection;
    }

    public void setMapPromoZoneCollection(Collection<MapPromoZone> mapPromoZoneCollection) {
        this.mapPromoZoneCollection = mapPromoZoneCollection;
    }

    public Collection<MstStore> getMstStoreCollection() {
        return mstStoreCollection;
    }

    public void setMstStoreCollection(Collection<MstStore> mstStoreCollection) {
        this.mstStoreCollection = mstStoreCollection;
    }

    public Collection<TransPromo> getTransPromoCollection() {
        return transPromoCollection;
    }

    public void setTransPromoCollection(Collection<TransPromo> transPromoCollection) {
        this.transPromoCollection = transPromoCollection;
    }

    public Collection<MapRoleZone> getMapRoleZoneCollection() {
        return mapRoleZoneCollection;
    }

    public void setMapRoleZoneCollection(Collection<MapRoleZone> mapRoleZoneCollection) {
        this.mapRoleZoneCollection = mapRoleZoneCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zoneId != null ? zoneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstZone)) {
            return false;
        }
        MstZone other = (MstZone) object;
        if ((this.zoneId == null && other.zoneId != null) || (this.zoneId != null && !this.zoneId.equals(other.zoneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstZone[zoneId=" + zoneId + "]";
    }
}
