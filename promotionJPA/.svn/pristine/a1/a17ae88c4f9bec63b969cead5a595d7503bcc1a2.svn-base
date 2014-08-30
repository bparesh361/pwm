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
@Table(name = "mst_event")
@NamedQueries({
    @NamedQuery(name = "MstEvent.findAll", query = "SELECT m FROM MstEvent m"),
    @NamedQuery(name = "MstEvent.findByEventId", query = "SELECT m FROM MstEvent m WHERE m.eventId = :eventId"),
    @NamedQuery(name = "MstEvent.findByEventName", query = "SELECT m FROM MstEvent m WHERE m.eventName = :eventName"),
    @NamedQuery(name = "MstEvent.findByIsBlocked", query = "SELECT m FROM MstEvent m WHERE m.isBlocked = :isBlocked")})
public class MstEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_id")
    private Long eventId;
    @Basic(optional = false)
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "is_blocked")
    private Short isBlocked;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEvent")
    private Collection<MstPromo> mstPromoCollection;

    public MstEvent() {
    }

    public MstEvent(Long eventId) {
        this.eventId = eventId;
    }

    public MstEvent(Long eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<MstPromo> getMstPromoCollection() {
        return mstPromoCollection;
    }

    public void setMstPromoCollection(Collection<MstPromo> mstPromoCollection) {
        this.mstPromoCollection = mstPromoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstEvent)) {
            return false;
        }
        MstEvent other = (MstEvent) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstEvent[eventId=" + eventId + "]";
    }

}
