/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.vo;

import com.fks.promo.common.Resp;

/**
 *
 * @author ajitn
 */
public class ErrorFileProposalResp {

    private Resp resp;
    private ProposalVO proposalVo;

    public ErrorFileProposalResp() {
    }

    public ErrorFileProposalResp(Resp resp) {
        this.resp = resp;
    }

    public ErrorFileProposalResp(Resp resp, ProposalVO proposalVo) {
        this.resp = resp;
        this.proposalVo = proposalVo;
    }

    public ProposalVO getProposalVo() {
        return proposalVo;
    }

    public void setProposalVo(ProposalVO proposalVo) {
        this.proposalVo = proposalVo;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
}
