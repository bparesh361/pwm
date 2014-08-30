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
@Table(name = "mst_report_type")
@NamedQueries({
    @NamedQuery(name = "MstReportType.findAll", query = "SELECT m FROM MstReportType m"),
    @NamedQuery(name = "MstReportType.findByMstReportTypeId", query = "SELECT m FROM MstReportType m WHERE m.mstReportTypeId = :mstReportTypeId"),
    @NamedQuery(name = "MstReportType.findByMstReportTypeDesc", query = "SELECT m FROM MstReportType m WHERE m.mstReportTypeDesc = :mstReportTypeDesc")})
public class MstReportType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mst_report_type_id")
    private Long mstReportTypeId;
    @Column(name = "mst_report_type_desc")
    private String mstReportTypeDesc;
    @OneToMany(mappedBy = "mstReportType")
    private Collection<MstReportEmail> mstReportEmailCollection;
    @OneToMany(mappedBy = "mstReportType")
    private Collection<MstReport> mstReportCollection;

    public MstReportType() {
    }

    public MstReportType(Long mstReportTypeId) {
        this.mstReportTypeId = mstReportTypeId;
    }

    public Long getMstReportTypeId() {
        return mstReportTypeId;
    }

    public void setMstReportTypeId(Long mstReportTypeId) {
        this.mstReportTypeId = mstReportTypeId;
    }

    public String getMstReportTypeDesc() {
        return mstReportTypeDesc;
    }

    public void setMstReportTypeDesc(String mstReportTypeDesc) {
        this.mstReportTypeDesc = mstReportTypeDesc;
    }

    public Collection<MstReportEmail> getMstReportEmailCollection() {
        return mstReportEmailCollection;
    }

    public void setMstReportEmailCollection(Collection<MstReportEmail> mstReportEmailCollection) {
        this.mstReportEmailCollection = mstReportEmailCollection;
    }

    public Collection<MstReport> getMstReportCollection() {
        return mstReportCollection;
    }

    public void setMstReportCollection(Collection<MstReport> mstReportCollection) {
        this.mstReportCollection = mstReportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mstReportTypeId != null ? mstReportTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstReportType)) {
            return false;
        }
        MstReportType other = (MstReportType) object;
        if ((this.mstReportTypeId == null && other.mstReportTypeId != null) || (this.mstReportTypeId != null && !this.mstReportTypeId.equals(other.mstReportTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstReportType[mstReportTypeId=" + mstReportTypeId + "]";
    }
}
