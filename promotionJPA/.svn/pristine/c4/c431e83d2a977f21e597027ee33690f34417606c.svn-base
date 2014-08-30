/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mch")
@NamedQueries({
    @NamedQuery(name = "Mch.findAll", query = "SELECT m FROM Mch m"),
    @NamedQuery(name = "Mch.findByMcCode", query = "SELECT m FROM Mch m WHERE m.mcCode = :mcCode"),
    @NamedQuery(name = "Mch.findByCategoryName", query = "SELECT m FROM Mch m WHERE m.categoryName = :categoryName"),
    @NamedQuery(name = "Mch.findBySubCategoryName", query = "SELECT m FROM Mch m WHERE m.subCategoryName = :subCategoryName"),
    @NamedQuery(name = "Mch.findByMcName", query = "SELECT m FROM Mch m WHERE m.mcName = :mcName"),
    @NamedQuery(name = "Mch.findByUpdatedDate", query = "SELECT m FROM Mch m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "Mch.findByIsBlocked", query = "SELECT m FROM Mch m WHERE m.isBlocked = :isBlocked")})
public class Mch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mc_code")
    private String mcCode;
    @Basic(optional = false)
    @Column(name = "category_name")
    private String categoryName;
    @Basic(optional = false)
    @Column(name = "sub_category_name")
    private String subCategoryName;
    @Basic(optional = false)
    @Column(name = "mc_name")
    private String mcName;
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mch")
    private Collection<MapRoleMch> mapRoleMchCollection;
    @OneToMany(mappedBy = "mch")
    private Collection<MapPromoMch> mapPromoMchCollection;
    @JoinColumn(name = "created_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne
    private MstLocation mstLocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mch")
    private Collection<MapuserMCHF5> mapuserMCHF5Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mch")
    private Collection<MapuserMCHF2> mapuserMCHF2Collection;
    @OneToMany(mappedBy = "mch")
    private Collection<TransPromoMc> transPromoMcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mch")
    private Collection<MapuserMCHF3> mapuserMCHF3Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mch")
    private Collection<MapuserMCHF1> mapuserMCHF1Collection;

    public Mch() {
    }

    public Mch(String mcCode) {
        this.mcCode = mcCode;
    }

    public Mch(String mcCode, String categoryName, String subCategoryName, String mcName) {
        this.mcCode = mcCode;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.mcName = mcName;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<MapRoleMch> getMapRoleMchCollection() {
        return mapRoleMchCollection;
    }

    public void setMapRoleMchCollection(Collection<MapRoleMch> mapRoleMchCollection) {
        this.mapRoleMchCollection = mapRoleMchCollection;
    }

    public Collection<MapPromoMch> getMapPromoMchCollection() {
        return mapPromoMchCollection;
    }

    public void setMapPromoMchCollection(Collection<MapPromoMch> mapPromoMchCollection) {
        this.mapPromoMchCollection = mapPromoMchCollection;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    public MstLocation getMstLocation() {
        return mstLocation;
    }

    public void setMstLocation(MstLocation mstLocation) {
        this.mstLocation = mstLocation;
    }

    public Collection<MapuserMCHF5> getMapuserMCHF5Collection() {
        return mapuserMCHF5Collection;
    }

    public void setMapuserMCHF5Collection(Collection<MapuserMCHF5> mapuserMCHF5Collection) {
        this.mapuserMCHF5Collection = mapuserMCHF5Collection;
    }

    public Collection<MapuserMCHF2> getMapuserMCHF2Collection() {
        return mapuserMCHF2Collection;
    }

    public void setMapuserMCHF2Collection(Collection<MapuserMCHF2> mapuserMCHF2Collection) {
        this.mapuserMCHF2Collection = mapuserMCHF2Collection;
    }

    public Collection<TransPromoMc> getTransPromoMcCollection() {
        return transPromoMcCollection;
    }

    public void setTransPromoMcCollection(Collection<TransPromoMc> transPromoMcCollection) {
        this.transPromoMcCollection = transPromoMcCollection;
    }

    public Collection<MapuserMCHF3> getMapuserMCHF3Collection() {
        return mapuserMCHF3Collection;
    }

    public void setMapuserMCHF3Collection(Collection<MapuserMCHF3> mapuserMCHF3Collection) {
        this.mapuserMCHF3Collection = mapuserMCHF3Collection;
    }

    public Collection<MapuserMCHF1> getMapuserMCHF1Collection() {
        return mapuserMCHF1Collection;
    }

    public void setMapuserMCHF1Collection(Collection<MapuserMCHF1> mapuserMCHF1Collection) {
        this.mapuserMCHF1Collection = mapuserMCHF1Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mcCode != null ? mcCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mch)) {
            return false;
        }
        Mch other = (Mch) object;
        if ((this.mcCode == null && other.mcCode != null) || (this.mcCode != null && !this.mcCode.equals(other.mcCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.Mch[mcCode=" + mcCode + "]";
    }
}
