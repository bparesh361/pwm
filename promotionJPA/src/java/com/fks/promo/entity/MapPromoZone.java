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
@Table(name = "map_promo_zone")
@NamedQueries({
    @NamedQuery(name = "MapPromoZone.findAll", query = "SELECT m FROM MapPromoZone m"),
    @NamedQuery(name = "MapPromoZone.findByMapPromoZoneId", query = "SELECT m FROM MapPromoZone m WHERE m.mapPromoZoneId = :mapPromoZoneId")})
public class MapPromoZone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_zone_id")
    private Long mapPromoZoneId;
    @JoinColumn(name = "zone_id", referencedColumnName = "zone_id")
    @ManyToOne
    private MstZone mstZone;
    @JoinColumn(name = "map_promo_id", referencedColumnName = "promo_id")
    @ManyToOne
    private MstPromo mstPromo;
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    public MapPromoZone() {
    }

    public MapPromoZone(Long mapPromoZoneId) {
        this.mapPromoZoneId = mapPromoZoneId;
    }

    public Long getMapPromoZoneId() {
        return mapPromoZoneId;
    }

    public void setMapPromoZoneId(Long mapPromoZoneId) {
        this.mapPromoZoneId = mapPromoZoneId;
    }

    public MstZone getMstZone() {
        return mstZone;
    }

    public void setMstZone(MstZone mstZone) {
        this.mstZone = mstZone;
    }

    public MstPromo getMstPromo() {
        return mstPromo;
    }

    public void setMstPromo(MstPromo mstPromo) {
        this.mstPromo = mstPromo;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mapPromoZoneId != null ? mapPromoZoneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoZone)) {
            return false;
        }
        MapPromoZone other = (MapPromoZone) object;
        if ((this.mapPromoZoneId == null && other.mapPromoZoneId != null) || (this.mapPromoZoneId != null && !this.mapPromoZoneId.equals(other.mapPromoZoneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoZone[mapPromoZoneId=" + mapPromoZoneId + "]";
    }

}
