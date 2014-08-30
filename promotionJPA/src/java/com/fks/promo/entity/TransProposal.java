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
 * @author ajitn
 */
@Entity
@Table(name = "trans_proposal")
@NamedQueries({
    @NamedQuery(name = "TransProposal.findAll", query = "SELECT t FROM TransProposal t"),
    @NamedQuery(name = "TransProposal.findByTransProposalId", query = "SELECT t FROM TransProposal t WHERE t.transProposalId = :transProposalId"),
    @NamedQuery(name = "TransProposal.findByArtCode", query = "SELECT t FROM TransProposal t WHERE t.artCode = :artCode"),
    @NamedQuery(name = "TransProposal.findByArtDesc", query = "SELECT t FROM TransProposal t WHERE t.artDesc = :artDesc"),
    @NamedQuery(name = "TransProposal.findByMcCode", query = "SELECT t FROM TransProposal t WHERE t.mcCode = :mcCode"),
    @NamedQuery(name = "TransProposal.findByMcDesc", query = "SELECT t FROM TransProposal t WHERE t.mcDesc = :mcDesc")})
public class TransProposal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trans_proposal_id")
    private Long transProposalId;
    @Column(name = "art_code")
    private String artCode;
    @Column(name = "art_desc")
    private String artDesc;
    @Column(name = "mc_code")
    private String mcCode;
    @Column(name = "mc_desc")
    private String mcDesc;
    @JoinColumn(name = "proposal_id", referencedColumnName = "proposal_id")
    @ManyToOne(optional = false)
    private MstProposal mstProposal;

    public TransProposal() {
    }

    public TransProposal(Long transProposalId) {
        this.transProposalId = transProposalId;
    }

    public Long getTransProposalId() {
        return transProposalId;
    }

    public void setTransProposalId(Long transProposalId) {
        this.transProposalId = transProposalId;
    }

    public String getArtCode() {
        return artCode;
    }

    public void setArtCode(String artCode) {
        this.artCode = artCode;
    }

    public String getArtDesc() {
        return artDesc;
    }

    public void setArtDesc(String artDesc) {
        this.artDesc = artDesc;
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

    public MstProposal getMstProposal() {
        return mstProposal;
    }

    public void setMstProposal(MstProposal mstProposal) {
        this.mstProposal = mstProposal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transProposalId != null ? transProposalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransProposal)) {
            return false;
        }
        TransProposal other = (TransProposal) object;
        if ((this.transProposalId == null && other.transProposalId != null) || (this.transProposalId != null && !this.transProposalId.equals(other.transProposalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.TransProposal[transProposalId=" + transProposalId + "]";
    }

}
