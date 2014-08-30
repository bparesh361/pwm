/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.vo;

import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransProposal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author ajitn
 */
public class ProposalReportVO {

    private MstProposal proposal;
    private String zonalInitiatorName;

    public MstProposal getProposal() {
        return proposal;
    }

    public void setProposal(MstProposal proposal) {
        this.proposal = proposal;
    }

    public String getZonalInitiatorName() {
        return zonalInitiatorName;
    }

    public void setZonalInitiatorName(String zonalInitiatorName) {
        this.zonalInitiatorName = zonalInitiatorName;
    }
}
