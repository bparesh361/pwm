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
 * @author krutij
 */
@Entity
@Table(name = "map_user_MCH_F5")
@NamedQueries({
    @NamedQuery(name = "MapuserMCHF5.findAll", query = "SELECT m FROM MapuserMCHF5 m"),
    @NamedQuery(name = "MapuserMCHF5.findByMapuserMCHF5id", query = "SELECT m FROM MapuserMCHF5 m WHERE m.mapuserMCHF5id = :mapuserMCHF5id")})
public class MapuserMCHF5 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_user_MCH_F5_id")
    private Long mapuserMCHF5id;
    @JoinColumn(name = "mc_code", referencedColumnName = "mc_code")
    @ManyToOne(optional = false)
    private Mch mch;
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee;

    public MapuserMCHF5() {
    }

    public MapuserMCHF5(Long mapuserMCHF5id) {
        this.mapuserMCHF5id = mapuserMCHF5id;
    }

    public Long getMapuserMCHF5id() {
        return mapuserMCHF5id;
    }

    public void setMapuserMCHF5id(Long mapuserMCHF5id) {
        this.mapuserMCHF5id = mapuserMCHF5id;
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
        hash += (mapuserMCHF5id != null ? mapuserMCHF5id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MapuserMCHF5)) {
            return false;
        }
        MapuserMCHF5 other = (MapuserMCHF5) object;
        if ((this.mapuserMCHF5id == null && other.mapuserMCHF5id != null) || (this.mapuserMCHF5id != null && !this.mapuserMCHF5id.equals(other.mapuserMCHF5id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MapuserMCHF5[mapuserMCHF5id=" + mapuserMCHF5id + "]";
    }

}
