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
@Table(name = "map_user_MCH_F3")
@NamedQueries({
    @NamedQuery(name = "MapuserMCHF3.findAll", query = "SELECT m FROM MapuserMCHF3 m"),
    @NamedQuery(name = "MapuserMCHF3.findByMapuserMCHF3id", query = "SELECT m FROM MapuserMCHF3 m WHERE m.mapuserMCHF3id = :mapuserMCHF3id")})
public class MapuserMCHF3 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_user_MCH_F3_id")
    private Long mapuserMCHF3id;
    @JoinColumn(name = "mc_code", referencedColumnName = "mc_code")
    @ManyToOne(optional = false)
    private Mch mch;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee;

    public MapuserMCHF3() {
    }

    public MapuserMCHF3(Long mapuserMCHF3id) {
        this.mapuserMCHF3id = mapuserMCHF3id;
    }

    public Long getMapuserMCHF3id() {
        return mapuserMCHF3id;
    }

    public void setMapuserMCHF3id(Long mapuserMCHF3id) {
        this.mapuserMCHF3id = mapuserMCHF3id;
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
        hash += (mapuserMCHF3id != null ? mapuserMCHF3id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapuserMCHF3)) {
            return false;
        }
        MapuserMCHF3 other = (MapuserMCHF3) object;
        if ((this.mapuserMCHF3id == null && other.mapuserMCHF3id != null) || (this.mapuserMCHF3id != null && !this.mapuserMCHF3id.equals(other.mapuserMCHF3id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapuserMCHF3[mapuserMCHF3id=" + mapuserMCHF3id + "]";
    }

}
