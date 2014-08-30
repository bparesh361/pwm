/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mst_article_search")
@NamedQueries({
    @NamedQuery(name = "MstArticleSearch.findAll", query = "SELECT m FROM MstArticleSearch m"),
    @NamedQuery(name = "MstArticleSearch.findByArticleSearchId", query = "SELECT m FROM MstArticleSearch m WHERE m.articleSearchId = :articleSearchId"),
    @NamedQuery(name = "MstArticleSearch.findBySearchDateTime", query = "SELECT m FROM MstArticleSearch m WHERE m.searchDateTime = :searchDateTime"),
    @NamedQuery(name = "MstArticleSearch.findByBrand", query = "SELECT m FROM MstArticleSearch m WHERE m.brand = :brand"),
    @NamedQuery(name = "MstArticleSearch.findBySeasonCode", query = "SELECT m FROM MstArticleSearch m WHERE m.seasonCode = :seasonCode"),
    @NamedQuery(name = "MstArticleSearch.findByMcCode", query = "SELECT m FROM MstArticleSearch m WHERE m.mcCode = :mcCode"),
    @NamedQuery(name = "MstArticleSearch.findByStatus", query = "SELECT m FROM MstArticleSearch m WHERE m.status = :status"),
    @NamedQuery(name = "MstArticleSearch.findByDownloadFilePath", query = "SELECT m FROM MstArticleSearch m WHERE m.downloadFilePath = :downloadFilePath")})
public class MstArticleSearch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "article_search_id")
    private Long articleSearchId;
    @Basic(optional = false)
    @Column(name = "search_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date searchDateTime;
    @Basic(optional = false)
    @Column(name = "brand")
    private String brand;
    @Basic(optional = false)
    @Column(name = "season_code")
    private String seasonCode;
    @Basic(optional = false)
    @Column(name = "mc_code")
    private String mcCode;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "download_file_path")
    private String downloadFilePath;
    @JoinColumn(name = "created_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;

    public MstArticleSearch() {
    }

    public MstArticleSearch(Long articleSearchId) {
        this.articleSearchId = articleSearchId;
    }

    public MstArticleSearch(Long articleSearchId, Date searchDateTime, String brand, String seasonCode, String mcCode, String status) {
        this.articleSearchId = articleSearchId;
        this.searchDateTime = searchDateTime;
        this.brand = brand;
        this.seasonCode = seasonCode;
        this.mcCode = mcCode;
        this.status = status;
    }

    public Long getArticleSearchId() {
        return articleSearchId;
    }

    public void setArticleSearchId(Long articleSearchId) {
        this.articleSearchId = articleSearchId;
    }

    public Date getSearchDateTime() {
        return searchDateTime;
    }

    public void setSearchDateTime(Date searchDateTime) {
        this.searchDateTime = searchDateTime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
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
        hash += (articleSearchId != null ? articleSearchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstArticleSearch)) {
            return false;
        }
        MstArticleSearch other = (MstArticleSearch) object;
        if ((this.articleSearchId == null && other.articleSearchId != null) || (this.articleSearchId != null && !this.articleSearchId.equals(other.articleSearchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstArticleSearch[articleSearchId=" + articleSearchId + "]";
    }

}
