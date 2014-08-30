/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "mst_set")
@NamedQueries({
    @NamedQuery(name = "MstSet.findAll", query = "SELECT m FROM MstSet m"),
    @NamedQuery(name = "MstSet.findBySetId", query = "SELECT m FROM MstSet m WHERE m.setId = :setId"),
    @NamedQuery(name = "MstSet.findBySetDesc", query = "SELECT m FROM MstSet m WHERE m.setDesc = :setDesc")})
public class MstSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "set_id")
    private Long setId;
    @Column(name = "set_desc")
    private String setDesc;
    

    public MstSet() {
    }

    public MstSet(Long setId) {
        this.setId = setId;
    }

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (setId != null ? setId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstSet)) {
            return false;
        }
        MstSet other = (MstSet) object;
        if ((this.setId == null && other.setId != null) || (this.setId != null && !this.setId.equals(other.setId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstSet[setId=" + setId + "]";
    }

}
