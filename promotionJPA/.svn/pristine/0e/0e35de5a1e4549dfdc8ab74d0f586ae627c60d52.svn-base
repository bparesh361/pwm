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
@Table(name = "map_promo_mch")
@NamedQueries({
    @NamedQuery(name = "MapPromoMch.findAll", query = "SELECT m FROM MapPromoMch m"),
    @NamedQuery(name = "MapPromoMch.findByMapPromoMchId", query = "SELECT m FROM MapPromoMch m WHERE m.mapPromoMchId = :mapPromoMchId")})
public class MapPromoMch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_mch_id")
    private Long mapPromoMchId;
    @JoinColumn(name = "mch_code", referencedColumnName = "mc_code")
    @ManyToOne
    private Mch mch;
    @JoinColumn(name = "mst_promo_id", referencedColumnName = "promo_id")
    @ManyToOne
    private MstPromo mstPromo;

    public MapPromoMch() {
    }

    public MapPromoMch(Long mapPromoMchId) {
        this.mapPromoMchId = mapPromoMchId;
    }

    public Long getMapPromoMchId() {
        return mapPromoMchId;
    }

    public void setMapPromoMchId(Long mapPromoMchId) {
        this.mapPromoMchId = mapPromoMchId;
    }

    public Mch getMch() {
        return mch;
    }

    public void setMch(Mch mch) {
        this.mch = mch;
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
        hash += (mapPromoMchId != null ? mapPromoMchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoMch)) {
            return false;
        }
        MapPromoMch other = (MapPromoMch) object;
        if ((this.mapPromoMchId == null && other.mapPromoMchId != null) || (this.mapPromoMchId != null && !this.mapPromoMchId.equals(other.mapPromoMchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoMch[mapPromoMchId=" + mapPromoMchId + "]";
    }

}
