/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "trans_promo")
@NamedQueries({
    @NamedQuery(name = "TransPromo.findAll", query = "SELECT t FROM TransPromo t"),
    @NamedQuery(name = "TransPromo.findByTransPromoId", query = "SELECT t FROM TransPromo t WHERE t.transPromoId = :transPromoId"),
    @NamedQuery(name = "TransPromo.findByZoneName", query = "SELECT t FROM TransPromo t WHERE t.zoneName = :zoneName"),
    @NamedQuery(name = "TransPromo.findByUpdatedDate", query = "SELECT t FROM TransPromo t WHERE t.updatedDate = :updatedDate"),
    @NamedQuery(name = "TransPromo.findByRemarks", query = "SELECT t FROM TransPromo t WHERE t.remarks = :remarks"),
    @NamedQuery(name = "TransPromo.findByReasonForRejection", query = "SELECT t FROM TransPromo t WHERE t.reasonForRejection = :reasonForRejection"),
    @NamedQuery(name = "TransPromo.findByIsHo", query = "SELECT t FROM TransPromo t WHERE t.isHo = :isHo"),
    @NamedQuery(name = "TransPromo.findByBuyQty", query = "SELECT t FROM TransPromo t WHERE t.buyQty = :buyQty"),
    @NamedQuery(name = "TransPromo.findByGetQty", query = "SELECT t FROM TransPromo t WHERE t.getQty = :getQty"),
    @NamedQuery(name = "TransPromo.findByValidFrom", query = "SELECT t FROM TransPromo t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransPromo.findByValidTo", query = "SELECT t FROM TransPromo t WHERE t.validTo = :validTo"),
    @NamedQuery(name = "TransPromo.findByTeamMemberRemarks", query = "SELECT t FROM TransPromo t WHERE t.teamMemberRemarks = :teamMemberRemarks"),
    @NamedQuery(name = "TransPromo.findByBonusBuy", query = "SELECT t FROM TransPromo t WHERE t.bonusBuy = :bonusBuy"),
    @NamedQuery(name = "TransPromo.findByPromoDetails", query = "SELECT t FROM TransPromo t WHERE t.promoDetails = :promoDetails"),
    @NamedQuery(name = "TransPromo.findByCashierTrigger", query = "SELECT t FROM TransPromo t WHERE t.cashierTrigger = :cashierTrigger"),
    @NamedQuery(name = "TransPromo.findByLsmwFilePath", query = "SELECT t FROM TransPromo t WHERE t.lsmwFilePath = :lsmwFilePath"),
    @NamedQuery(name = "TransPromo.findByRejectionRemarks", query = "SELECT t FROM TransPromo t WHERE t.rejectionRemarks = :rejectionRemarks")})
public class TransPromo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_promo_id")
    private Long transPromoId;
    @Column(name = "zone_name")
    private String zoneName;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "reason_for_rejection")
    private String reasonForRejection;
    @Column(name = "is_ho")
    private Boolean isHo;
    @Column(name = "buy_qty")
    private Integer buyQty;
    @Column(name = "get_qty")
    private Integer getQty;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @Column(name = "team_member_remarks")
    private String teamMemberRemarks;
    @Column(name = "bonus_buy")
    private String bonusBuy;
    @Column(name = "promo_details")
    private String promoDetails;
    @Column(name = "cashier_trigger")
    private String cashierTrigger;
    @Column(name = "is_file_upload")
    private Boolean isFileUpload;
    @Column(name = "lsmw_file_path")
    private String lsmwFilePath;
    @Column(name = "rejection_remarks")
    private String rejectionRemarks;
    @OneToMany(mappedBy = "transPromo")
    private Collection<TransPromoFile> transPromoFileCollection;
    @OneToMany(mappedBy = "transPromo")
    private Collection<TransPromoArticle> transPromoArticleCollection;
    @JoinColumn(name = "L2_id", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee;
    @JoinColumn(name = "L1_id", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee1;
    @JoinColumn(name = "business_exigency_id", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee2;
    @JoinColumn(name = "executive_id", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee3;
    @JoinColumn(name = "created_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee4;
    @JoinColumn(name = "zone_id", referencedColumnName = "zone_id")
    @ManyToOne
    private MstZone mstZone;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private MstStatus mstStatus;
    @JoinColumn(name = "promo_type_id", referencedColumnName = "promo_type_id")
    @ManyToOne
    private MstPromotionType mstPromotionType;
    @JoinColumn(name = "promo_id", referencedColumnName = "promo_id")
    @ManyToOne(optional = false)
    private MstPromo mstPromo;
    @JoinColumn(name = "promo_mgr_id", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee5;
    @JoinColumn(name = "last_updated_by", referencedColumnName = "emp_id")
    @ManyToOne
    private MstEmployee mstEmployee6;
    @OneToMany(mappedBy = "transPromo")
    private Collection<TransPromoStatus> transPromoStatusCollection;
    @OneToMany(mappedBy = "transPromo")
    private Collection<TransPromoConfig> transPromoConfigCollection;

    public TransPromo() {
    }

    public TransPromo(Long transPromoId) {
        this.transPromoId = transPromoId;
    }

    public TransPromo(Long transPromoId, Date updatedDate) {
        this.transPromoId = transPromoId;
        this.updatedDate = updatedDate;
    }

    public Long getTransPromoId() {
        return transPromoId;
    }

    public void setTransPromoId(Long transPromoId) {
        this.transPromoId = transPromoId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(String reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }

    public Boolean getIsHo() {
        return isHo;
    }

    public void setIsHo(Boolean isHo) {
        this.isHo = isHo;
    }

    public Integer getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(Integer buyQty) {
        this.buyQty = buyQty;
    }

    public Integer getGetQty() {
        return getQty;
    }

    public void setGetQty(Integer getQty) {
        this.getQty = getQty;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getBonusBuy() {
        return bonusBuy;
    }

    public void setBonusBuy(String bonusBuy) {
        this.bonusBuy = bonusBuy;
    }

    public String getCashierTrigger() {
        return cashierTrigger;
    }

    public void setCashierTrigger(String cashierTrigger) {
        this.cashierTrigger = cashierTrigger;
    }

    public Boolean getIsFileUpload() {
        return isFileUpload;
    }

    public void setIsFileUpload(Boolean isFileUpload) {
        this.isFileUpload = isFileUpload;
    }

    public String getLsmwFilePath() {
        return lsmwFilePath;
    }

    public void setLsmwFilePath(String lsmwFilePath) {
        this.lsmwFilePath = lsmwFilePath;
    }

    public String getRejectionRemarks() {
        return rejectionRemarks;
    }

    public void setRejectionRemarks(String rejectionRemarks) {
        this.rejectionRemarks = rejectionRemarks;
    }

    public String getPromoDetails() {
        return promoDetails;
    }

    public void setPromoDetails(String promoDetails) {
        this.promoDetails = promoDetails;
    }

    public String getTeamMemberRemarks() {
        return teamMemberRemarks;
    }

    public void setTeamMemberRemarks(String teamMemberRemarks) {
        this.teamMemberRemarks = teamMemberRemarks;
    }

    public Collection<TransPromoFile> getTransPromoFileCollection() {
        return transPromoFileCollection;
    }

    public void setTransPromoFileCollection(Collection<TransPromoFile> transPromoFileCollection) {
        this.transPromoFileCollection = transPromoFileCollection;
    }

    public Collection<TransPromoArticle> getTransPromoArticleCollection() {
        return transPromoArticleCollection;
    }

    public void setTransPromoArticleCollection(Collection<TransPromoArticle> transPromoArticleCollection) {
        this.transPromoArticleCollection = transPromoArticleCollection;
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

    public MstEmployee getMstEmployee2() {
        return mstEmployee2;
    }

    public void setMstEmployee2(MstEmployee mstEmployee2) {
        this.mstEmployee2 = mstEmployee2;
    }

    public MstEmployee getMstEmployee3() {
        return mstEmployee3;
    }

    public void setMstEmployee3(MstEmployee mstEmployee3) {
        this.mstEmployee3 = mstEmployee3;
    }

    public MstEmployee getMstEmployee4() {
        return mstEmployee4;
    }

    public void setMstEmployee4(MstEmployee mstEmployee4) {
        this.mstEmployee4 = mstEmployee4;
    }

    public MstZone getMstZone() {
        return mstZone;
    }

    public void setMstZone(MstZone mstZone) {
        this.mstZone = mstZone;
    }

    public MstStatus getMstStatus() {
        return mstStatus;
    }

    public void setMstStatus(MstStatus mstStatus) {
        this.mstStatus = mstStatus;
    }

    public MstPromotionType getMstPromotionType() {
        return mstPromotionType;
    }

    public void setMstPromotionType(MstPromotionType mstPromotionType) {
        this.mstPromotionType = mstPromotionType;
    }

    public MstPromo getMstPromo() {
        return mstPromo;
    }

    public void setMstPromo(MstPromo mstPromo) {
        this.mstPromo = mstPromo;
    }

    public MstEmployee getMstEmployee5() {
        return mstEmployee5;
    }

    public void setMstEmployee5(MstEmployee mstEmployee5) {
        this.mstEmployee5 = mstEmployee5;
    }

    public MstEmployee getMstEmployee6() {
        return mstEmployee6;
    }

    public void setMstEmployee6(MstEmployee mstEmployee6) {
        this.mstEmployee6 = mstEmployee6;
    }

    public Collection<TransPromoStatus> getTransPromoStatusCollection() {
        return transPromoStatusCollection;
    }

    public void setTransPromoStatusCollection(Collection<TransPromoStatus> transPromoStatusCollection) {
        this.transPromoStatusCollection = transPromoStatusCollection;
    }

    public Collection<TransPromoConfig> getTransPromoConfigCollection() {
        return transPromoConfigCollection;
    }

    public void setTransPromoConfigCollection(Collection<TransPromoConfig> transPromoConfigCollection) {
        this.transPromoConfigCollection = transPromoConfigCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transPromoId != null ? transPromoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransPromo)) {
            return false;
        }
        TransPromo other = (TransPromo) object;
        if ((this.transPromoId == null && other.transPromoId != null) || (this.transPromoId != null && !this.transPromoId.equals(other.transPromoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransPromo[transPromoId=" + transPromoId + "]";
    }
}
