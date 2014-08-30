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
@Table(name = "map_promo_format")
@NamedQueries({
    @NamedQuery(name = "MapPromoFormat.findAll", query = "SELECT m FROM MapPromoFormat m"),
    @NamedQuery(name = "MapPromoFormat.findByMapPromoFormatId", query = "SELECT m FROM MapPromoFormat m WHERE m.mapPromoFormatId = :mapPromoFormatId"),
    @NamedQuery(name = "MapPromoFormat.findByFormatDesc", query = "SELECT m FROM MapPromoFormat m WHERE m.formatDesc = :formatDesc"),
    @NamedQuery(name = "MapPromoFormat.findByUpdatedDate", query = "SELECT m FROM MapPromoFormat m WHERE m.updatedDate = :updatedDate")})
public class MapPromoFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_format_id")
    private Long mapPromoFormatId;
    @Basic(optional = false)
    @Column(name = "format_desc")
    private String formatDesc;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @JoinColumn(name = "promo_id", referencedColumnName = "promo_id")
    @ManyToOne(optional = false)
    private MstPromo mstPromo;

    public MapPromoFormat() {
    }

    public MapPromoFormat(Long mapPromoFormatId) {
        this.mapPromoFormatId = mapPromoFormatId;
    }

    public MapPromoFormat(Long mapPromoFormatId, String formatDesc, Date updatedDate) {
        this.mapPromoFormatId = mapPromoFormatId;
        this.formatDesc = formatDesc;
        this.updatedDate = updatedDate;
    }

    public Long getMapPromoFormatId() {
        return mapPromoFormatId;
    }

    public void setMapPromoFormatId(Long mapPromoFormatId) {
        this.mapPromoFormatId = mapPromoFormatId;
    }

    public String getFormatDesc() {
        return formatDesc;
    }

    public void setFormatDesc(String formatDesc) {
        this.formatDesc = formatDesc;
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
        hash += (mapPromoFormatId != null ? mapPromoFormatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoFormat)) {
            return false;
        }
        MapPromoFormat other = (MapPromoFormat) object;
        if ((this.mapPromoFormatId == null && other.mapPromoFormatId != null) || (this.mapPromoFormatId != null && !this.mapPromoFormatId.equals(other.mapPromoFormatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoFormat[mapPromoFormatId=" + mapPromoFormatId + "]";
    }

}
