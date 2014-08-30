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
@Table(name = "trans_promo_article")
@NamedQueries({
    @NamedQuery(name = "TransPromoArticle.findAll", query = "SELECT t FROM TransPromoArticle t"),
    @NamedQuery(name = "TransPromoArticle.findByTransPromoArticleId", query = "SELECT t FROM TransPromoArticle t WHERE t.transPromoArticleId = :transPromoArticleId"),
    @NamedQuery(name = "TransPromoArticle.findByArticle", query = "SELECT t FROM TransPromoArticle t WHERE t.article = :article"),
    @NamedQuery(name = "TransPromoArticle.findByArticleDesc", query = "SELECT t FROM TransPromoArticle t WHERE t.articleDesc = :articleDesc"),
    @NamedQuery(name = "TransPromoArticle.findByMcCode", query = "SELECT t FROM TransPromoArticle t WHERE t.mcCode = :mcCode"),
    @NamedQuery(name = "TransPromoArticle.findByMcDesc", query = "SELECT t FROM TransPromoArticle t WHERE t.mcDesc = :mcDesc"),
    @NamedQuery(name = "TransPromoArticle.findByBrandCode", query = "SELECT t FROM TransPromoArticle t WHERE t.brandCode = :brandCode"),
    @NamedQuery(name = "TransPromoArticle.findByBrandDesc", query = "SELECT t FROM TransPromoArticle t WHERE t.brandDesc = :brandDesc"),
    @NamedQuery(name = "TransPromoArticle.findByQty", query = "SELECT t FROM TransPromoArticle t WHERE t.qty = :qty"),
    @NamedQuery(name = "TransPromoArticle.findBySetId", query = "SELECT t FROM TransPromoArticle t WHERE t.setId = :setId"),
    @NamedQuery(name = "TransPromoArticle.findByIsX", query = "SELECT t FROM TransPromoArticle t WHERE t.isX = :isX"),
    @NamedQuery(name = "TransPromoArticle.findByIsY", query = "SELECT t FROM TransPromoArticle t WHERE t.isY = :isY"),
    @NamedQuery(name = "TransPromoArticle.findByIsZ", query = "SELECT t FROM TransPromoArticle t WHERE t.isZ = :isZ"),
    @NamedQuery(name = "TransPromoArticle.findByIsA", query = "SELECT t FROM TransPromoArticle t WHERE t.isA = :isA"),
    @NamedQuery(name = "TransPromoArticle.findByIsB", query = "SELECT t FROM TransPromoArticle t WHERE t.isB = :isB"),
    @NamedQuery(name = "TransPromoArticle.findByUpdatedDate", query = "SELECT t FROM TransPromoArticle t WHERE t.updatedDate = :updatedDate"),
    @NamedQuery(name = "TransPromoArticle.findByMrp", query = "SELECT t FROM TransPromoArticle t WHERE t.mrp = :mrp")})
public class TransPromoArticle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_promo_article_id")
    private Long transPromoArticleId;
    @Column(name = "article")
    private String article;
    @Column(name = "article_desc")
    private String articleDesc;
    @Column(name = "mc_code")
    private String mcCode;
    @Column(name = "mc_desc")
    private String mcDesc;
    @Column(name = "brand_code")
    private String brandCode;
    @Column(name = "brand_desc")
    private String brandDesc;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "set_id")
    private Integer setId;
    @Column(name = "is_x")
    private Boolean isX;
    @Column(name = "is_y")
    private Boolean isY;
    @Column(name = "is_z")
    private Boolean isZ;
    @Column(name = "is_a")
    private Boolean isA;
    @Column(name = "is_b")
    private Boolean isB;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "mrp")
    private String mrp;
    @JoinColumn(name = "current_status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "trans_promo_id", referencedColumnName = "trans_promo_id")
    @ManyToOne
    private TransPromo transPromo;
    @JoinColumn(name = "updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;

    public TransPromoArticle() {
    }

    public TransPromoArticle(Long transPromoArticleId) {
        this.transPromoArticleId = transPromoArticleId;
    }

    public Long getTransPromoArticleId() {
        return transPromoArticleId;
    }

    public void setTransPromoArticleId(Long transPromoArticleId) {
        this.transPromoArticleId = transPromoArticleId;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getMcDesc() {
        return mcDesc;
    }

    public void setMcDesc(String mcDesc) {
        this.mcDesc = mcDesc;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Boolean getIsX() {
        return isX;
    }

    public void setIsX(Boolean isX) {
        this.isX = isX;
    }

    public Boolean getIsY() {
        return isY;
    }

    public void setIsY(Boolean isY) {
        this.isY = isY;
    }

    public Boolean getIsZ() {
        return isZ;
    }

    public void setIsZ(Boolean isZ) {
        this.isZ = isZ;
    }

    public Boolean getIsA() {
        return isA;
    }

    public void setIsA(Boolean isA) {
        this.isA = isA;
    }

    public Boolean getIsB() {
        return isB;
    }

    public void setIsB(Boolean isB) {
        this.isB = isB;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public TransPromo getTransPromo() {
        return transPromo;
    }

    public void setTransPromo(TransPromo transPromo) {
        this.transPromo = transPromo;
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
        hash += (transPromoArticleId != null ? transPromoArticleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransPromoArticle)) {
            return false;
        }
        TransPromoArticle other = (TransPromoArticle) object;
        if ((this.transPromoArticleId == null && other.transPromoArticleId != null) || (this.transPromoArticleId != null && !this.transPromoArticleId.equals(other.transPromoArticleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransPromoArticle[transPromoArticleId=" + transPromoArticleId + "]";
    }
}
