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
@Table(name = "mst_module")
@NamedQueries({
    @NamedQuery(name = "MstModule.findAll", query = "SELECT m FROM MstModule m"),
    @NamedQuery(name = "MstModule.findByModuleId", query = "SELECT m FROM MstModule m WHERE m.moduleId = :moduleId"),
    @NamedQuery(name = "MstModule.findByModuleName", query = "SELECT m FROM MstModule m WHERE m.moduleName = :moduleName"),
    @NamedQuery(name = "MstModule.findByModuleDesc", query = "SELECT m FROM MstModule m WHERE m.moduleDesc = :moduleDesc"),
    @NamedQuery(name = "MstModule.findByIsDisplayed", query = "SELECT m FROM MstModule m WHERE m.isDisplayed = :isDisplayed")})
public class MstModule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "module_id")
    private Long moduleId;
    @Basic(optional = false)
    @Column(name = "module_name")
    private String moduleName;
    @Column(name = "module_desc")
    private String moduleDesc;
    @Column(name = "is_displayed")
    private Boolean isDisplayed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstModule")
    private Collection<MapModuleProfile> mapModuleProfileCollection;

    public MstModule() {
    }

    public MstModule(Long moduleId) {
        this.moduleId = moduleId;
    }

    public MstModule(Long moduleId, String moduleName) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public Boolean getIsDisplayed() {
        return isDisplayed;
    }

    public void setIsDisplayed(Boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public Collection<MapModuleProfile> getMapModuleProfileCollection() {
        return mapModuleProfileCollection;
    }

    public void setMapModuleProfileCollection(Collection<MapModuleProfile> mapModuleProfileCollection) {
        this.mapModuleProfileCollection = mapModuleProfileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleId != null ? moduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstModule)) {
            return false;
        }
        MstModule other = (MstModule) object;
        if ((this.moduleId == null && other.moduleId != null) || (this.moduleId != null && !this.moduleId.equals(other.moduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstModule[moduleId=" + moduleId + "]";
    }

}
