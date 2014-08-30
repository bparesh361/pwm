/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init.vo;

/**
 *
 * @author ajitn
 */
public class AcceptRejectProposalVO {

    private String proposalId;
    private String remarks;
    private boolean isAccept;

    public boolean isIsAccept() {
        return isAccept;
    }

    public void setIsAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
