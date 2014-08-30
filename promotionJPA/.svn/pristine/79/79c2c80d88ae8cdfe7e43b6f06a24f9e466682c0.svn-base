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
@Table(name = "mst_calender")
@NamedQueries({
    @NamedQuery(name = "MstCalender.findAll", query = "SELECT m FROM MstCalender m"),
    @NamedQuery(name = "MstCalender.findByMstCalenderId", query = "SELECT m FROM MstCalender m WHERE m.mstCalenderId = :mstCalenderId"),
    @NamedQuery(name = "MstCalender.findByCalDate", query = "SELECT m FROM MstCalender m WHERE m.calDate = :calDate"),
    @NamedQuery(name = "MstCalender.findByDateDescription", query = "SELECT m FROM MstCalender m WHERE m.dateDescription = :dateDescription")})
public class MstCalender implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mst_calender_id")
    private Long mstCalenderId;
    @Basic(optional = false)
    @Column(name = "cal_date")
    @Temporal(TemporalType.DATE)
    private Date calDate;
    @Basic(optional = false)
    @Column(name = "date_description")
    private String dateDescription;

    public MstCalender() {
    }

    public MstCalender(Long mstCalenderId) {
        this.mstCalenderId = mstCalenderId;
    }

    public MstCalender(Long mstCalenderId, Date calDate, String dateDescription) {
        this.mstCalenderId = mstCalenderId;
        this.calDate = calDate;
        this.dateDescription = dateDescription;
    }

    public Long getMstCalenderId() {
        return mstCalenderId;
    }

    public void setMstCalenderId(Long mstCalenderId) {
        this.mstCalenderId = mstCalenderId;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mstCalenderId != null ? mstCalenderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstCalender)) {
            return false;
        }
        MstCalender other = (MstCalender) object;
        if ((this.mstCalenderId == null && other.mstCalenderId != null) || (this.mstCalenderId != null && !this.mstCalenderId.equals(other.mstCalenderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstCalender[mstCalenderId=" + mstCalenderId + "]";
    }

}
