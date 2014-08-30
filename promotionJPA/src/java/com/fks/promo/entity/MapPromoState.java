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
@Table(name = "map_promo_state")
@NamedQueries({
    @NamedQuery(name = "MapPromoState.findAll", query = "SELECT m FROM MapPromoState m"),
    @NamedQuery(name = "MapPromoState.findByMapPromoStateId", query = "SELECT m FROM MapPromoState m WHERE m.mapPromoStateId = :mapPromoStateId"),
    @NamedQuery(name = "MapPromoState.findByStateDesc", query = "SELECT m FROM MapPromoState m WHERE m.stateDesc = :stateDesc"),
    @NamedQuery(name = "MapPromoState.findByUpdatedDate", query = "SELECT m FROM MapPromoState m WHERE m.updatedDate = :updatedDate")})
public class MapPromoState implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_promo_state_id")
    private Long mapPromoStateId;
    @Basic(optional = false)
    @Column(name = "state_desc")
    private String stateDesc;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @JoinColumn(name = "promo_id", referencedColumnName = "promo_id")
    @ManyToOne(optional = false)
    private MstPromo mstPromo;

    public MapPromoState() {
    }

    public MapPromoState(Long mapPromoStateId) {
        this.mapPromoStateId = mapPromoStateId;
    }

    public MapPromoState(Long mapPromoStateId, String stateDesc, Date updatedDate) {
        this.mapPromoStateId = mapPromoStateId;
        this.stateDesc = stateDesc;
        this.updatedDate = updatedDate;
    }

    public Long getMapPromoStateId() {
        return mapPromoStateId;
    }

    public void setMapPromoStateId(Long mapPromoStateId) {
        this.mapPromoStateId = mapPromoStateId;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
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
        hash += (mapPromoStateId != null ? mapPromoStateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapPromoState)) {
            return false;
        }
        MapPromoState other = (MapPromoState) object;
        if ((this.mapPromoStateId == null && other.mapPromoStateId != null) || (this.mapPromoStateId != null && !this.mapPromoStateId.equals(other.mapPromoStateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapPromoState[mapPromoStateId=" + mapPromoStateId + "]";
    }

}
