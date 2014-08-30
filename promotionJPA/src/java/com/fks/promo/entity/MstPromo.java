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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mst_promo")
@NamedQueries({
    @NamedQuery(name = "MstPromo.findAll", query = "SELECT m FROM MstPromo m"),
    @NamedQuery(name = "MstPromo.findByPromoId", query = "SELECT m FROM MstPromo m WHERE m.promoId = :promoId"),
    @NamedQuery(name = "MstPromo.findByCreatedDate", query = "SELECT m FROM MstPromo m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MstPromo.findByCategory", query = "SELECT m FROM MstPromo m WHERE m.category = :category"),
    @NamedQuery(name = "MstPromo.findBySubCategory", query = "SELECT m FROM MstPromo m WHERE m.subCategory = :subCategory"),
    @NamedQuery(name = "MstPromo.findByRequestName", query = "SELECT m FROM MstPromo m WHERE m.requestName = :requestName"),
    @NamedQuery(name = "MstPromo.findByRemarks", query = "SELECT m FROM MstPromo m WHERE m.remarks = :remarks"),
    @NamedQuery(name = "MstPromo.findByUpdatedDate", query = "SELECT m FROM MstPromo m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "MstPromo.findByFilePath", query = "SELECT m FROM MstPromo m WHERE m.filePath = :filePath")})
public class MstPromo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "promo_id")
    private Long promoId;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "category")
    private String category;
    @Basic(optional = false)
    @Column(name = "sub_category")
    private String subCategory;
    @Basic(optional = false)
    @Column(name = "request_name")
    private String requestName;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "vendor_backed")
    private String vendorBacked;
    @Column(name = "error_file_path")
    private String errorFilePath;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;
    @JoinColumn(name = "created_by", referencedColumnName = "emp_id")
    @ManyToOne(optional = false)
    private MstEmployee mstEmployee1;
    @JoinColumn(name = "mktg_id", referencedColumnName = "mktg_id")
    @ManyToOne(optional = false)
    private MstMktg mstMktg;
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @ManyToOne(optional = false)
    private MstEvent mstEvent;
    @JoinColumn(name = "campaign_id", referencedColumnName = "campaign_id")
    @ManyToOne
    private MstCampaign mstCampaign;
    @OneToMany(mappedBy = "mstPromo")
    private Collection<MapPromoZone> mapPromoZoneCollection;
    @OneToMany(mappedBy = "mstPromo")
    private Collection<MapPromoMch> mapPromoMchCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstPromo")
    private Collection<MapPromoFormat> mapPromoFormatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstPromo")
    private Collection<MapPromoRegion> mapPromoRegionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstPromo")
    private Collection<TransPromo> transPromoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstPromo")
    private Collection<MapPromoState> mapPromoStateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstPromo")
    private Collection<MapPromoStore> mapPromoStoreCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstPromo")
    private Collection<MapPromoCity> mapPromoCityCollection;

    public MstPromo() {
    }

    public MstPromo(Long promoId) {
        this.promoId = promoId;
    }

    public MstPromo(Long promoId, Date createdDate, String category, String subCategory, String requestName) {
        this.promoId = promoId;
        this.createdDate = createdDate;
        this.category = category;
        this.subCategory = subCategory;
        this.requestName = requestName;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public MstEmployee getMstEmployee() {
        return mstEmployee;
    }

    public void setMstEmployee(MstEmployee mstEmployee) {
        this.mstEmployee = mstEmployee;
    }

    public MstEmployee getMstEmployee1() {
        return mstEmployee1;
    }

    public void setMstEmployee1(MstEmployee mstEmployee1) {
        this.mstEmployee1 = mstEmployee1;
    }

    public MstMktg getMstMktg() {
        return mstMktg;
    }

    public void setMstMktg(MstMktg mstMktg) {
        this.mstMktg = mstMktg;
    }

    public MstEvent getMstEvent() {
        return mstEvent;
    }

    public void setMstEvent(MstEvent mstEvent) {
        this.mstEvent = mstEvent;
    }

    public MstCampaign getMstCampaign() {
        return mstCampaign;
    }

    public void setMstCampaign(MstCampaign mstCampaign) {
        this.mstCampaign = mstCampaign;
    }

    public Collection<MapPromoZone> getMapPromoZoneCollection() {
        return mapPromoZoneCollection;
    }

    public void setMapPromoZoneCollection(Collection<MapPromoZone> mapPromoZoneCollection) {
        this.mapPromoZoneCollection = mapPromoZoneCollection;
    }

    public Collection<MapPromoMch> getMapPromoMchCollection() {
        return mapPromoMchCollection;
    }

    public void setMapPromoMchCollection(Collection<MapPromoMch> mapPromoMchCollection) {
        this.mapPromoMchCollection = mapPromoMchCollection;
    }

    public Collection<MapPromoFormat> getMapPromoFormatCollection() {
        return mapPromoFormatCollection;
    }

    public void setMapPromoFormatCollection(Collection<MapPromoFormat> mapPromoFormatCollection) {
        this.mapPromoFormatCollection = mapPromoFormatCollection;
    }

    public Collection<MapPromoRegion> getMapPromoRegionCollection() {
        return mapPromoRegionCollection;
    }

    public void setMapPromoRegionCollection(Collection<MapPromoRegion> mapPromoRegionCollection) {
        this.mapPromoRegionCollection = mapPromoRegionCollection;
    }

    public Collection<TransPromo> getTransPromoCollection() {
        return transPromoCollection;
    }

    public void setTransPromoCollection(Collection<TransPromo> transPromoCollection) {
        this.transPromoCollection = transPromoCollection;
    }

    public Collection<MapPromoState> getMapPromoStateCollection() {
        return mapPromoStateCollection;
    }

    public void setMapPromoStateCollection(Collection<MapPromoState> mapPromoStateCollection) {
        this.mapPromoStateCollection = mapPromoStateCollection;
    }

    public Collection<MapPromoStore> getMapPromoStoreCollection() {
        return mapPromoStoreCollection;
    }

    public void setMapPromoStoreCollection(Collection<MapPromoStore> mapPromoStoreCollection) {
        this.mapPromoStoreCollection = mapPromoStoreCollection;
    }

    public Collection<MapPromoCity> getMapPromoCityCollection() {
        return mapPromoCityCollection;
    }

    public void setMapPromoCityCollection(Collection<MapPromoCity> mapPromoCityCollection) {
        this.mapPromoCityCollection = mapPromoCityCollection;
    }

    public String getVendorBacked() {
        return vendorBacked;
    }

    public void setVendorBacked(String vendorBacked) {
        this.vendorBacked = vendorBacked;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promoId != null ? promoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstPromo)) {
            return false;
        }
        MstPromo other = (MstPromo) object;
        if ((this.promoId == null && other.promoId != null) || (this.promoId != null && !this.promoId.equals(other.promoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstPromo[promoId=" + promoId + "]";
    }
}