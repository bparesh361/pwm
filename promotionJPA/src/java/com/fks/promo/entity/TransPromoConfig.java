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
@Table(name = "trans_promo_config")
@NamedQueries({
    @NamedQuery(name = "TransPromoConfig.findAll", query = "SELECT t FROM TransPromoConfig t"),
    @NamedQuery(name = "TransPromoConfig.findByTransPromoConfigId", query = "SELECT t FROM TransPromoConfig t WHERE t.transPromoConfigId = :transPromoConfigId"),
    @NamedQuery(name = "TransPromoConfig.findByDiscountConfig", query = "SELECT t FROM TransPromoConfig t WHERE t.discountConfig = :discountConfig"),
    @NamedQuery(name = "TransPromoConfig.findByDiscountValue", query = "SELECT t FROM TransPromoConfig t WHERE t.discountValue = :discountValue"),
    @NamedQuery(name = "TransPromoConfig.findByQty", query = "SELECT t FROM TransPromoConfig t WHERE t.qty = :qty"),
    @NamedQuery(name = "TransPromoConfig.findByMarginAchivement", query = "SELECT t FROM TransPromoConfig t WHERE t.marginAchivement = :marginAchivement"),
    @NamedQuery(name = "TransPromoConfig.findByTicketSizeGrowth", query = "SELECT t FROM TransPromoConfig t WHERE t.ticketSizeGrowth = :ticketSizeGrowth"),
    @NamedQuery(name = "TransPromoConfig.findBySellthruvsQty", query = "SELECT t FROM TransPromoConfig t WHERE t.sellthruvsQty = :sellthruvsQty"),
    @NamedQuery(name = "TransPromoConfig.findByGrowthConversion", query = "SELECT t FROM TransPromoConfig t WHERE t.growthConversion = :growthConversion"),
    @NamedQuery(name = "TransPromoConfig.findBySalesGrowth", query = "SELECT t FROM TransPromoConfig t WHERE t.salesGrowth = :salesGrowth"),
    @NamedQuery(name = "TransPromoConfig.findByValidFrom", query = "SELECT t FROM TransPromoConfig t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransPromoConfig.findByValidTo", query = "SELECT t FROM TransPromoConfig t WHERE t.validTo = :validTo"),
    @NamedQuery(name = "TransPromoConfig.findByTicketWorthAmt", query = "SELECT t FROM TransPromoConfig t WHERE t.ticketWorthAmt = :ticketWorthAmt"),
    @NamedQuery(name = "TransPromoConfig.findByTicketDiscAmt", query = "SELECT t FROM TransPromoConfig t WHERE t.ticketDiscAmt = :ticketDiscAmt"),
    @NamedQuery(name = "TransPromoConfig.findBySetId", query = "SELECT t FROM TransPromoConfig t WHERE t.setId = :setId"),
    @NamedQuery(name = "TransPromoConfig.findByIsX", query = "SELECT t FROM TransPromoConfig t WHERE t.isX = :isX"),
    @NamedQuery(name = "TransPromoConfig.findByIsY", query = "SELECT t FROM TransPromoConfig t WHERE t.isY = :isY"),
    @NamedQuery(name = "TransPromoConfig.findByIsZ", query = "SELECT t FROM TransPromoConfig t WHERE t.isZ = :isZ"),
    @NamedQuery(name = "TransPromoConfig.findByIsA", query = "SELECT t FROM TransPromoConfig t WHERE t.isA = :isA"),
    @NamedQuery(name = "TransPromoConfig.findByIsB", query = "SELECT t FROM TransPromoConfig t WHERE t.isB = :isB")})
public class TransPromoConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_promo_config_id")
    private Long transPromoConfigId;
    @Basic(optional = false)
    @Column(name = "discount_config")
    private String discountConfig;
    @Basic(optional = false)
    @Column(name = "discount_value")
    private double discountValue;
    @Column(name = "qty")
    private Integer qty;
    @Basic(optional = false)
    @Column(name = "margin_achivement")
    private double marginAchivement;
    @Basic(optional = false)
    @Column(name = "ticket_size_growth")
    private double ticketSizeGrowth;
    @Basic(optional = false)
    @Column(name = "sell_thru_vs_Qty")
    private double sellthruvsQty;
    @Basic(optional = false)
    @Column(name = "growth_conversion")
    private double growthConversion;
    @Basic(optional = false)
    @Column(name = "sales_growth")
    private double salesGrowth;
    @Column(name = "valid_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;
    @Column(name = "ticket_worth_amt")
    private Double ticketWorthAmt;
    @Column(name = "ticket_disc_amt")
    private Double ticketDiscAmt;
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
    @JoinColumn(name = "tras_promo_id", referencedColumnName = "trans_promo_id")
    @ManyToOne
    private TransPromo transPromo;

    public TransPromoConfig() {
    }

    public TransPromoConfig(Long transPromoConfigId) {
        this.transPromoConfigId = transPromoConfigId;
    }

    public TransPromoConfig(Long transPromoConfigId, String discountConfig, double discountValue, double marginAchivement, double ticketSizeGrowth, int sellthruvsQty, double growthConversion, double salesGrowth) {
        this.transPromoConfigId = transPromoConfigId;
        this.discountConfig = discountConfig;
        this.discountValue = discountValue;
        this.marginAchivement = marginAchivement;
        this.ticketSizeGrowth = ticketSizeGrowth;
        this.sellthruvsQty = sellthruvsQty;
        this.growthConversion = growthConversion;
        this.salesGrowth = salesGrowth;
    }

    public Long getTransPromoConfigId() {
        return transPromoConfigId;
    }

    public void setTransPromoConfigId(Long transPromoConfigId) {
        this.transPromoConfigId = transPromoConfigId;
    }

    public String getDiscountConfig() {
        return discountConfig;
    }

    public void setDiscountConfig(String discountConfig) {
        this.discountConfig = discountConfig;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public double getMarginAchivement() {
        return marginAchivement;
    }

    public void setMarginAchivement(double marginAchivement) {
        this.marginAchivement = marginAchivement;
    }

    public double getTicketSizeGrowth() {
        return ticketSizeGrowth;
    }

    public void setTicketSizeGrowth(double ticketSizeGrowth) {
        this.ticketSizeGrowth = ticketSizeGrowth;
    }

    public double getSellthruvsQty() {
        return sellthruvsQty;
    }

    public void setSellthruvsQty(double sellthruvsQty) {
        this.sellthruvsQty = sellthruvsQty;
    }

    public double getGrowthConversion() {
        return growthConversion;
    }

    public void setGrowthConversion(double growthConversion) {
        this.growthConversion = growthConversion;
    }

    public double getSalesGrowth() {
        return salesGrowth;
    }

    public void setSalesGrowth(double salesGrowth) {
        this.salesGrowth = salesGrowth;
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

    public Double getTicketWorthAmt() {
        return ticketWorthAmt;
    }

    public void setTicketWorthAmt(Double ticketWorthAmt) {
        this.ticketWorthAmt = ticketWorthAmt;
    }

    public Double getTicketDiscAmt() {
        return ticketDiscAmt;
    }

    public void setTicketDiscAmt(Double ticketDiscAmt) {
        this.ticketDiscAmt = ticketDiscAmt;
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

    public TransPromo getTransPromo() {
        return transPromo;
    }

    public void setTransPromo(TransPromo transPromo) {
        this.transPromo = transPromo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transPromoConfigId != null ? transPromoConfigId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransPromoConfig)) {
            return false;
        }
        TransPromoConfig other = (TransPromoConfig) object;
        if ((this.transPromoConfigId == null && other.transPromoConfigId != null) || (this.transPromoConfigId != null && !this.transPromoConfigId.equals(other.transPromoConfigId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransPromoConfig[transPromoConfigId=" + transPromoConfigId + "]";
    }
}
