/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.MstProposal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
public class CommonProposalDAO {

    public static final Integer MAX_PAGE_RESULT = 30;
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    public List<MstProposal> getAllProposal(int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal order by proposal_id desc", MstProposal.class);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setFirstResult(startIndex);
        return query.getResultList();
    }

    public Long getAllProposalCount() {
        Query query = em.createNativeQuery("select count(*) from mst_proposal");
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalById(long id, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal where proposal_id=? order by proposal_id desc", MstProposal.class);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setFirstResult(startIndex);
        query.setParameter(1, id);
        return query.getResultList();
    }

    public Long getAllProposalByIdCount(long id) {
        Query query = em.createNativeQuery("select count(*) from mst_proposal where proposal_id=? order by proposal_id desc");

        query.setParameter(1, id);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByDate(String startDate, String endDate, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal where created_date>=? and created_date<=? order by proposal_id desc", MstProposal.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return query.getResultList();
    }

    public Long getAllProposalByDateCount(String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(*) from mst_proposal where created_date>=? and created_date<=? order by proposal_id desc");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByDateZone(String startDate, String endDate, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal where zone_id = ? and created_date>=? and created_date<=? and status_id in (4,55,54) order by proposal_id desc", MstProposal.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setParameter(1, zoneId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return query.getResultList();
    }

    public Long getAllProposalByDateZoneCount(String startDate, String endDate, Long zoneId) {
        Query query = em.createNativeQuery("select count(*) from mst_proposal where zone_id = ? and created_date>=? and created_date<=? and status_id in (4,55,54) order by proposal_id desc");
        query.setParameter(1, zoneId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByDateZoneStatus(String startDate, String endDate, Long zoneId, String statusId, Long empId, int startIndex) {
//        Query query = em.createNativeQuery("select * from mst_proposal where zone_id = ? and created_date>=? and created_date<=? and status_id = ? order by proposal_id desc", MstProposal.class);
        Query query = em.createNativeQuery("select distinct p.* from mst_proposal p "
                + " INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                + " INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                + "where p.zone_id=? and created_date>=? and created_date<=?  and p.status_id = ? and f1.emp_id=? order by p.proposal_id desc", MstProposal.class);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setFirstResult(startIndex);
        query.setParameter(1, zoneId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        query.setParameter(4, statusId);
        query.setParameter(5, empId);
        return query.getResultList();
    }

    public Long getAllProposalByDateZoneStatusCount(String startDate, String endDate, Long zoneId, String statusId, Long empId) {
//        Query query = em.createNativeQuery("select count(*) from mst_proposal where zone_id = ? and created_date>=? and created_date<=? and status_id = ? order by proposal_id desc");
        Query query = em.createNativeQuery("select count(DISTINCT p.proposal_id) from mst_proposal p "
                + " INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                + " INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                + "where p.zone_id=? and created_date>=? and created_date<=?  and p.status_id = ? and f1.emp_id=? order by p.proposal_id desc");
        query.setParameter(1, zoneId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        query.setParameter(4, statusId);
        query.setParameter(5, empId);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByUser(String userId, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal where emp_id = ? order by proposal_id desc", MstProposal.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public Long getAllProposalByUserCount(String userId) {
        Query query = em.createNativeQuery("select count(*) from mst_proposal where emp_id = ?");
        query.setParameter(1, userId);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByUserStatus(String userId, String statusId, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal where emp_id = ? and status_id = ? order by proposal_id desc", MstProposal.class);

        query.setParameter(1, userId);
        query.setParameter(2, statusId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getAllProposalByUserStatusCount(String userId, String statusId) {
        Query query = em.createNativeQuery("select count(*) from mst_proposal where emp_id = ? and status_id = ?");
        query.setParameter(1, userId);
        query.setParameter(2, statusId);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByStatus(String statusId, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_proposal where status_id = ? order by proposal_id desc", MstProposal.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setParameter(1, statusId);
        return query.getResultList();
    }

    public Long getAllProposalByStatusCount(String statusId) {
        Query query = em.createNativeQuery("select count(*) from mst_proposal where status_id = ? order by proposal_id ");
        query.setParameter(1, statusId);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getAllProposalByStatusZoneStatus(String statusId, Long zoneId, Long empId, int startIndex) {
//        Query query = em.createNativeQuery("select * from mst_proposal where status_id = ? and zone_id=? order by proposal_id desc", MstProposal.class);
        Query query = em.createNativeQuery("select distinct p.* from mst_proposal p "
                + " INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                + " INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                + " where p.status_id = ? and p.zone_id=? and f1.emp_id=? order by p.proposal_id desc", MstProposal.class);

        query.setParameter(1, statusId);
        query.setParameter(2, zoneId);
        query.setParameter(3, empId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getAllProposalByStatusZoneStatusCount(String statusId, Long zoneId, Long empId) {
//        Query query = em.createNativeQuery("select count(*) from mst_proposal where status_id = ? and zone_id=? order by proposal_id");
        Query query = em.createNativeQuery("select count(DISTINCT p.proposal_id) from mst_proposal p "
                + " INNER JOIN trans_proposal t ON t.proposal_id=p.proposal_id "
                + " INNER JOIN map_user_MCH_F1 f1 ON t.mc_code=f1.mc_code "
                + " where p.status_id = ? and p.zone_id=? and f1.emp_id=? order by p.proposal_id desc");

        query.setParameter(1, statusId);
        query.setParameter(2, zoneId);
        query.setParameter(3, empId);
        return new Long(query.getSingleResult().toString());
    }

    public List<MstProposal> getProposalForArchival(String startDate) {        
        Query query = em.createNativeQuery("select * from mst_proposal where  DATE_FORMAT(created_date,'%Y-%m-%d')=?", MstProposal.class);
        query.setParameter(1, startDate);
        return query.getResultList();
    }
}
