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
@Table(name = "trans_promo_file")
@NamedQueries({
    @NamedQuery(name = "TransPromoFile.findAll", query = "SELECT t FROM TransPromoFile t"),
    @NamedQuery(name = "TransPromoFile.findByMapTransPromoFileId", query = "SELECT t FROM TransPromoFile t WHERE t.mapTransPromoFileId = :mapTransPromoFileId"),
    @NamedQuery(name = "TransPromoFile.findByFilePath", query = "SELECT t FROM TransPromoFile t WHERE t.filePath = :filePath"),
    @NamedQuery(name = "TransPromoFile.findBySetId", query = "SELECT t FROM TransPromoFile t WHERE t.setId = :setId"),
    @NamedQuery(name = "TransPromoFile.findByErrorFilePath", query = "SELECT t FROM TransPromoFile t WHERE t.errorFilePath = :errorFilePath"),
    @NamedQuery(name = "TransPromoFile.findByRemarks", query = "SELECT t FROM TransPromoFile t WHERE t.remarks = :remarks"),
    @NamedQuery(name = "TransPromoFile.findByIsArticleFile", query = "SELECT t FROM TransPromoFile t WHERE t.isArticleFile = :isArticleFile")})
public class TransPromoFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "map_trans_promo_file_id")
    private Long mapTransPromoFileId;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "set_id")
    private Integer setId;
    @Column(name = "error_file_path")
    private String errorFilePath;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "is_article_file")
    private Boolean isArticleFile;
    @JoinColumn(name = "trans_promo_id", referencedColumnName = "trans_promo_id")
    @ManyToOne
    private TransPromo transPromo;

    public TransPromoFile() {
    }

    public TransPromoFile(Long mapTransPromoFileId) {
        this.mapTransPromoFileId = mapTransPromoFileId;
    }

    public Long getMapTransPromoFileId() {
        return mapTransPromoFileId;
    }

    public void setMapTransPromoFileId(Long mapTransPromoFileId) {
        this.mapTransPromoFileId = mapTransPromoFileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsArticleFile() {
        return isArticleFile;
    }

    public void setIsArticleFile(Boolean isArticleFile) {
        this.isArticleFile = isArticleFile;
    }

    public TransPromo getTransPromo() {
        return transPromo;
    }

    public void setTransPromo(TransPromo transPromo) {
        this.transPromo = transPromo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mapTransPromoFileId != null ? mapTransPromoFileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransPromoFile)) {
            return false;
        }
        TransPromoFile other = (TransPromoFile) object;
        if ((this.mapTransPromoFileId == null && other.mapTransPromoFileId != null) || (this.mapTransPromoFileId != null && !this.mapTransPromoFileId.equals(other.mapTransPromoFileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransPromoFile[mapTransPromoFileId=" + mapTransPromoFileId + "]";
    }
}
