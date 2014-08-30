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
@Table(name = "map_user_MCH_F2")
@NamedQueries({
    @NamedQuery(name = "MapuserMCHF2.findAll", query = "SELECT m FROM MapuserMCHF2 m"),
    @NamedQuery(name = "MapuserMCHF2.findByMapuserMCHF2id", query = "SELECT m FROM MapuserMCHF2 m WHERE m.mapuserMCHF2id = :mapuserMCHF2id")})
public class MapuserMCHF2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_user_MCH_F2_id")
    private Long mapuserMCHF2id;
    @JoinColumn(name = "mc_code", referencedColumnName = "mc_code")
    @ManyToOne(optional = false)
    private Mch mch;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee;

    public MapuserMCHF2() {
    }

    public MapuserMCHF2(Long mapuserMCHF2id) {
        this.mapuserMCHF2id = mapuserMCHF2id;
    }

    public Long getMapuserMCHF2id() {
        return mapuserMCHF2id;
    }

    public void setMapuserMCHF2id(Long mapuserMCHF2id) {
        this.mapuserMCHF2id = mapuserMCHF2id;
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
        hash += (mapuserMCHF2id != null ? mapuserMCHF2id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapuserMCHF2)) {
            return false;
        }
        MapuserMCHF2 other = (MapuserMCHF2) object;
        if ((this.mapuserMCHF2id == null && other.mapuserMCHF2id != null) || (this.mapuserMCHF2id != null && !this.mapuserMCHF2id.equals(other.mapuserMCHF2id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapuserMCHF2[mapuserMCHF2id=" + mapuserMCHF2id + "]";
    }

}
