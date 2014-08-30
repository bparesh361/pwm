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
@Table(name = "map_promo_store")
@NamedQueries({
    @NamedQuery(name = "MapPromoStore.findAll", query = "SELECT m FROM MapPromoStore m"),
    @NamedQuery(name = "MapPromoStore.findByMapPromoStoreId", query = "SELECT m FROM MapPromoStore m WHERE m.mapPromoStoreId = :mapPromoStoreId"),
    @NamedQuery(name = "MapPromoStore.findByUpdatedDate", query = "SELECT m FROM MapPromoStore m WHERE m.updatedDate = :updatedDate")})
public class MapPromoStore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_store_id")
    private Long mapPromoStoreId;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @JoinColumn(name = "mst_store_id", referencedColumnName = "mst_store_id")
    @ManyToOne(optional = false)
    private MstStore mstStore;
    @JoinColumn(name = "promo_id", referencedColumnName = "promo_id")
    @ManyToOne(optional = false)
    private MstPromo mstPromo;

    public MapPromoStore() {
    }

    public MapPromoStore(Long mapPromoStoreId) {
        this.mapPromoStoreId = mapPromoStoreId;
    }

    public MapPromoStore(Long mapPromoStoreId, Date updatedDate) {
        this.mapPromoStoreId = mapPromoStoreId;
        this.updatedDate = updatedDate;
    }

    public Long getMapPromoStoreId() {
        return mapPromoStoreId;
    }

    public void setMapPromoStoreId(Long mapPromoStoreId) {
        this.mapPromoStoreId = mapPromoStoreId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public MstStore getMstStore() {
        return mstStore;
    }

    public void setMstStore(MstStore mstStore) {
        this.mstStore = mstStore;
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
        hash += (mapPromoStoreId != null ? mapPromoStoreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoStore)) {
            return false;
        }
        MapPromoStore other = (MapPromoStore) object;
        if ((this.mapPromoStoreId == null && other.mapPromoStoreId != null) || (this.mapPromoStoreId != null && !this.mapPromoStoreId.equals(other.mapPromoStoreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoStore[mapPromoStoreId=" + mapPromoStoreId + "]";
    }

}
