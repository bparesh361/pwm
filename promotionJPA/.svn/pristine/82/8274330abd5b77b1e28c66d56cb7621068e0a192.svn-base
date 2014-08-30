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
@Table(name = "map_promo_region")
@NamedQueries({
    @NamedQuery(name = "MapPromoRegion.findAll", query = "SELECT m FROM MapPromoRegion m"),
    @NamedQuery(name = "MapPromoRegion.findByMapPromoRegionId", query = "SELECT m FROM MapPromoRegion m WHERE m.mapPromoRegionId = :mapPromoRegionId"),
    @NamedQuery(name = "MapPromoRegion.findByRegionName", query = "SELECT m FROM MapPromoRegion m WHERE m.regionName = :regionName"),
    @NamedQuery(name = "MapPromoRegion.findByUpdatedDate", query = "SELECT m FROM MapPromoRegion m WHERE m.updatedDate = :updatedDate")})
public class MapPromoRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_region_id")
    private Long mapPromoRegionId;
    @Basic(optional = false)
    @Column(name = "region_name")
    private String regionName;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @JoinColumn(name = "promo_id", referencedColumnName = "promo_id")
    @ManyToOne(optional = false)
    private MstPromo mstPromo;

    public MapPromoRegion() {
    }

    public MapPromoRegion(Long mapPromoRegionId) {
        this.mapPromoRegionId = mapPromoRegionId;
    }

    public MapPromoRegion(Long mapPromoRegionId, String regionName, Date updatedDate) {
        this.mapPromoRegionId = mapPromoRegionId;
        this.regionName = regionName;
        this.updatedDate = updatedDate;
    }

    public Long getMapPromoRegionId() {
        return mapPromoRegionId;
    }

    public void setMapPromoRegionId(Long mapPromoRegionId) {
        this.mapPromoRegionId = mapPromoRegionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public MstPromo getMstPromo() {
        return mstPromo;
    }

    public void setMstPromo(MstPromo mstPromo) {
        this.mstPromo = mstPromo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mapPromoRegionId != null ? mapPromoRegionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoRegion)) {
            return false;
        }
        MapPromoRegion other = (MapPromoRegion) object;
        if ((this.mapPromoRegionId == null && other.mapPromoRegionId != null) || (this.mapPromoRegionId != null && !this.mapPromoRegionId.equals(other.mapPromoRegionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoRegion[mapPromoRegionId=" + mapPromoRegionId + "]";
    }

}
