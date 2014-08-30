/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "mst_report_email")
@NamedQueries({
    @NamedQuery(name = "MstReportEmail.findAll", query = "SELECT m FROM MstReportEmail m"),
    @NamedQuery(name = "MstReportEmail.findByMstReportTypeEmailId", query = "SELECT m FROM MstReportEmail m WHERE m.mstReportTypeEmailId = :mstReportTypeEmailId"),
    @NamedQuery(name = "MstReportEmail.findByMstZoneName", query = "SELECT m FROM MstReportEmail m WHERE m.mstZoneName = :mstZoneName"),
    @NamedQuery(name = "MstReportEmail.findByMstZoneId", query = "SELECT m FROM MstReportEmail m WHERE m.mstZoneId = :mstZoneId"),
    @NamedQuery(name = "MstReportEmail.findByEmailId", query = "SELECT m FROM MstReportEmail m WHERE m.emailId = :emailId")})
public class MstReportEmail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mst_report_type_email_id")
    private Long mstReportTypeEmailId;
    @Column(name = "mst_zone_name")
    private String mstZoneName;
    @Column(name = "mst_zone_id")
    private String mstZoneId;
    @Column(name = "email_id")
    private String emailId;
    @JoinColumn(name = "mst_report_type_id", referencedColumnName = "mst_report_type_id")
    @ManyToOne
    private MstReportType mstReportType;

    public MstReportEmail() {
    }

    public MstReportEmail(Long mstReportTypeEmailId) {
        this.mstReportTypeEmailId = mstReportTypeEmailId;
    }

    public Long getMstReportTypeEmailId() {
        return mstReportTypeEmailId;
    }

    public void setMstReportTypeEmailId(Long mstReportTypeEmailId) {
        this.mstReportTypeEmailId = mstReportTypeEmailId;
    }

    public String getMstZoneName() {
        return mstZoneName;
    }

    public void setMstZoneName(String mstZoneName) {
        this.mstZoneName = mstZoneName;
    }

    public String getMstZoneId() {
        return mstZoneId;
    }

    public void setMstZoneId(String mstZoneId) {
        this.mstZoneId = mstZoneId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public MstReportType getMstReportType() {
        return mstReportType;
    }

    public void setMstReportType(MstReportType mstReportType) {
        this.mstReportType = mstReportType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mstReportTypeEmailId != null ? mstReportTypeEmailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstReportEmail)) {
            return false;
        }
        MstReportEmail other = (MstReportEmail) object;
        if ((this.mstReportTypeEmailId == null && other.mstReportTypeEmailId != null) || (this.mstReportTypeEmailId != null && !this.mstReportTypeEmailId.equals(other.mstReportTypeEmailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstReportEmail[mstReportTypeEmailId=" + mstReportTypeEmailId + "]";
    }
}
