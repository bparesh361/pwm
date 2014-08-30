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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mst_lead_time")
@NamedQueries({
    @NamedQuery(name = "MstLeadTime.findAll", query = "SELECT m FROM MstLeadTime m"),
    @NamedQuery(name = "MstLeadTime.findById", query = "SELECT m FROM MstLeadTime m WHERE m.id = :id"),
    @NamedQuery(name = "MstLeadTime.findByMstLeadTimeDesc", query = "SELECT m FROM MstLeadTime m WHERE m.mstLeadTimeDesc = :mstLeadTimeDesc"),
    @NamedQuery(name = "MstLeadTime.findByValue", query = "SELECT m FROM MstLeadTime m WHERE m.value = :value")})
public class MstLeadTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "mst_lead_time__desc")
    private String mstLeadTimeDesc;
    @Basic(optional = false)
    @Column(name = "value")
    private int value;

    public MstLeadTime() {
    }

    public MstLeadTime(Long id) {
        this.id = id;
    }

    public MstLeadTime(Long id, String mstLeadTimeDesc, int value) {
        this.id = id;
        this.mstLeadTimeDesc = mstLeadTimeDesc;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMstLeadTimeDesc() {
        return mstLeadTimeDesc;
    }

    public void setMstLeadTimeDesc(String mstLeadTimeDesc) {
        this.mstLeadTimeDesc = mstLeadTimeDesc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstLeadTime)) {
            return false;
        }
        MstLeadTime other = (MstLeadTime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstLeadTime[id=" + id + "]";
    }

}
