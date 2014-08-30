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
@Table(name = "mst_problem")
@NamedQueries({
    @NamedQuery(name = "MstProblem.findAll", query = "SELECT m FROM MstProblem m"),
    @NamedQuery(name = "MstProblem.findByProblemTypeId", query = "SELECT m FROM MstProblem m WHERE m.problemTypeId = :problemTypeId"),
    @NamedQuery(name = "MstProblem.findByProblemName", query = "SELECT m FROM MstProblem m WHERE m.problemName = :problemName"),
    @NamedQuery(name = "MstProblem.findByIsBlocked", query = "SELECT m FROM MstProblem m WHERE m.isBlocked = :isBlocked")})
public class MstProblem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "problem_type_id")
    private Long problemTypeId;
    @Basic(optional = false)
    @Column(name = "problem_name")
    private String problemName;
    @Column(name = "is_blocked")
    private Short isBlocked;
    @OneToMany(mappedBy = "mstProblem")
    private Collection<MstProposal> mstProposalCollection;

    public MstProblem() {
    }

    public MstProblem(Long problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    public MstProblem(Long problemTypeId, String problemName) {
        this.problemTypeId = problemTypeId;
        this.problemName = problemName;
    }

    public Long getProblemTypeId() {
        return problemTypeId;
    }

    public void setProblemTypeId(Long problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Collection<MstProposal> getMstProposalCollection() {
        return mstProposalCollection;
    }

    public void setMstProposalCollection(Collection<MstProposal> mstProposalCollection) {
        this.mstProposalCollection = mstProposalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (problemTypeId != null ? problemTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstProblem)) {
            return false;
        }
        MstProblem other = (MstProblem) object;
        if ((this.problemTypeId == null && other.problemTypeId != null) || (this.problemTypeId != null && !this.problemTypeId.equals(other.problemTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstProblem[problemTypeId=" + problemTypeId + "]";
    }

}
