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
@Table(name = "map_user_MCH_F1")
@NamedQueries({
    @NamedQuery(name = "MapuserMCHF1.findAll", query = "SELECT m FROM MapuserMCHF1 m"),
    @NamedQuery(name = "MapuserMCHF1.findByMapuserMCHF1id", query = "SELECT m FROM MapuserMCHF1 m WHERE m.mapuserMCHF1id = :mapuserMCHF1id")})
public class MapuserMCHF1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_user_MCH_F1_id")
    private Long mapuserMCHF1id;
    @JoinColumn(name = "mc_code", referencedColumnName = "mc_code")
    @ManyToOne(optional = false)
    private Mch mch;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee;

    public MapuserMCHF1() {
    }

    public MapuserMCHF1(Long mapuserMCHF1id) {
        this.mapuserMCHF1id = mapuserMCHF1id;
    }

    public Long getMapuserMCHF1id() {
        return mapuserMCHF1id;
    }

    public void setMapuserMCHF1id(Long mapuserMCHF1id) {
        this.mapuserMCHF1id = mapuserMCHF1id;
    }

    public Mch getMch() {
        return mch;
    }

    public void setMch(Mch mch) {
        this.mch = mch;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mapuserMCHF1id != null ? mapuserMCHF1id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapuserMCHF1)) {
            return false;
        }
        MapuserMCHF1 other = (MapuserMCHF1) object;
        if ((this.mapuserMCHF1id == null && other.mapuserMCHF1id != null) || (this.mapuserMCHF1id != null && !this.mapuserMCHF1id.equals(other.mapuserMCHF1id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapuserMCHF1[mapuserMCHF1id=" + mapuserMCHF1id + "]";
    }

}
