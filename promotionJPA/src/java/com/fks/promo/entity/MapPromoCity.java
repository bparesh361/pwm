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
@Table(name = "map_promo_city")
@NamedQueries({
    @NamedQuery(name = "MapPromoCity.findAll", query = "SELECT m FROM MapPromoCity m"),
    @NamedQuery(name = "MapPromoCity.findByMapPromoCityId", query = "SELECT m FROM MapPromoCity m WHERE m.mapPromoCityId = :mapPromoCityId"),
    @NamedQuery(name = "MapPromoCity.findByCityDesc", query = "SELECT m FROM MapPromoCity m WHERE m.cityDesc = :cityDesc"),
    @NamedQuery(name = "MapPromoCity.findByUpdatedDate", query = "SELECT m FROM MapPromoCity m WHERE m.updatedDate = :updatedDate")})
public class MapPromoCity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_city_id")
    private Long mapPromoCityId;
    @Basic(optional = false)
    @Column(name = "city_desc")
    private String cityDesc;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @JoinColumn(name = "promo_id", referencedColumnName = "promo_id")
    @ManyToOne(optional = false)
    private MstPromo mstPromo;

    public MapPromoCity() {
    }

    public MapPromoCity(Long mapPromoCityId) {
        this.mapPromoCityId = mapPromoCityId;
    }

    public MapPromoCity(Long mapPromoCityId, String cityDesc, Date updatedDate) {
        this.mapPromoCityId = mapPromoCityId;
        this.cityDesc = cityDesc;
        this.updatedDate = updatedDate;
    }

    public Long getMapPromoCityId() {
        return mapPromoCityId;
    }

    public void setMapPromoCityId(Long mapPromoCityId) {
        this.mapPromoCityId = mapPromoCityId;
    }

    public String getCityDesc() {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
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
        hash += (mapPromoCityId != null ? mapPromoCityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoCity)) {
            return false;
        }
        MapPromoCity other = (MapPromoCity) object;
        if ((this.mapPromoCityId == null && other.mapPromoCityId != null) || (this.mapPromoCityId != null && !this.mapPromoCityId.equals(other.mapPromoCityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoCity[mapPromoCityId=" + mapPromoCityId + "]";
    }

}
