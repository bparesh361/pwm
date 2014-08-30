/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import com.fks.promo.entity.TransPromoConfig;
import com.fks.promo.vo.ResultCount;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nehabha
 */
@Stateless
@LocalBean
public class CommonApprovalDao {

    public static final Integer MAX_PAGE_RESULT = 30;
    public static final Integer L1_L2_MAX_PAGE_RESULT = 15;
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    public List<TransPromo> setStatusforTransPromoReq(Long transPromoId, Long statusId) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.trans_promo_id = ? and m.status_Id = ?", TransPromo.class);
        query.setParameter(1, transPromoId);
        query.setParameter(2, statusId);
        return query.getResultList();
    }

    public void updateTransPromo(TransPromo transPromo) {
        em.merge(transPromo);
    }

    public int updateTransPromoMCL1(Long transPromoId, Long empid, Long statusId) {
        Query query = em.createNativeQuery("update trans_promo_mc set status_id=? where trans_promo_id=? and mc_code in( select mc_code from map_user_MCH_F2 where emp_id =?)");
        query.setParameter(1, statusId);
        query.setParameter(2, transPromoId);
        query.setParameter(3, empid);
        return query.executeUpdate();
    }

    public int updateTransPromoMCL1ToExigency(Long transPromoId, Long statusId) {
        Query query = em.createNativeQuery("update trans_promo_mc set status_id=? where trans_promo_id=? ");
        query.setParameter(1, statusId);
        query.setParameter(2, transPromoId);
        return query.executeUpdate();
    }

    public int updateTransPromoArticleL1Reject(Long transPromoId, Long empid, Long statusId) {
        Query query = em.createNativeQuery("update trans_promo_article set current_status_id=? where trans_promo_id=?");
        query.setParameter(1, statusId);
        query.setParameter(2, transPromoId);
        return query.executeUpdate();
    }

    public int updateTransPromoMCL2(Long transPromoId, Long empid, Long statusId) {
        Query query = em.createNativeQuery("update trans_promo_mc set status_id=? where trans_promo_id=? and mc_code in( select mc_code from map_user_MCH_F3 where emp_id =?)");
        query.setParameter(1, statusId);
        query.setParameter(2, transPromoId);
        query.setParameter(3, empid);
        return query.executeUpdate();
    }

    public int updateTransPromoArticleL2Reject(Long transPromoId, Long empid, Long statusId) {
        Query query = em.createNativeQuery("update trans_promo_article set current_status_id=? where trans_promo_id=? ");
        query.setParameter(1, statusId);
        query.setParameter(2, transPromoId);
        return query.executeUpdate();
    }

    public int searchTransMCStausReq(Long transPromoId, Long statusId) {
        Query query = em.createNativeQuery("SELECT count(*) FROM trans_promo_mc t where trans_promo_id =? and status_id != ?");
        query.setParameter(1, transPromoId);
        query.setParameter(2, statusId);
        return new Integer(query.getSingleResult().toString());
    }

    public ResultCount getAllTransPromotion(String zoneId, String empId, int page) {
        // System.out.println("----------------- page : " + page);
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code where t.status_id IN (12,13) AND t.zone_id=? AND tm.status_id IN (12,13) AND f2.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
//        query.setParameter(2, CommonStatusConstants.PROMO_SUBMITTED);

        Long pageCount = getAllTransPromotionCount(zoneId, empId);
        query.setFirstResult(page);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return new ResultCount(pageCount, query.getResultList());
    }

    public ResultCount getAllTransPromotionLevel2(String zoneId, String empId, int page) {
        System.out.println("----------------- page : " + page);
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code where t.status_id IN (16,20) AND t.zone_id=? AND tm.status_id IN (16,20) AND f3.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
//        query.setParameter(2, CommonStatusConstants.PROMO_SUBMITTED);

        Long pageCount = getAllTransPromotionLevel2count(empId, zoneId);
        query.setFirstResult(page);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return new ResultCount(pageCount, query.getResultList());
    }

    public ResultCount getZoneAllTransPromotionForDate(String zoneId, String empId, int page, String startDate, String endDate) {
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code  "
                + "where t.zone_id=? AND t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
//        query.setParameter(2, CommonStatusConstants.PROMO_SUBMITTED);

        Long pageCount = getZoneAllTransPromotionForDateCount(zoneId, empId, startDate, endDate);
        query.setFirstResult(page);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return new ResultCount(pageCount, query.getResultList());
    }

    public Long getZoneAllTransPromotionForDateCount(String zoneId, String empId, String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code  "
                + "where t.zone_id=? AND t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc");

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);

        return Long.valueOf(query.getSingleResult().toString());
    }

    public ResultCount getZoneAllTransPromotionForLevel2Date(String zoneId, String empId, int page, String startDate, String endDate) {
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code  "
                + "where t.zone_id=? AND t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f3.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
//        query.setParameter(2, CommonStatusConstants.PROMO_SUBMITTED);

        Long pageCount = getZoneAllTransPromotionForLevel2DateCount(zoneId, empId, startDate, endDate);
        query.setFirstResult(page);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return new ResultCount(pageCount, query.getResultList());
    }

    public Long getZoneAllTransPromotionForLevel2DateCount(String zoneId, String empId, String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code  "
                + "where t.zone_id=? AND t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f3.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc");

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);

        return Long.valueOf(query.getSingleResult().toString());
    }

    public Long getAllTransPromotionCount(String zoneId, String empId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code where  t.zone_id=? AND t.status_id IN (12,13) AND  tm.status_id IN (12,13) AND f2.emp_id=? order by  t.trans_promo_id desc");
        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
//        query.setParameter(2, CommonStatusConstants.PROMO_SUBMITTED);

        return Long.valueOf(query.getSingleResult().toString());
    }

    public int updateTransConfigfortranspromoid(Long transPromoId, Date fromDate, Date toDate) {
        Query query = em.createNativeQuery("update trans_promo_config set valid_from=? and valid_to =? where tras_promo_id=? ", TransPromoConfig.class);
        query.setParameter(1, fromDate);
        query.setParameter(2, toDate);
        query.setParameter(3, transPromoId);
        return query.executeUpdate();
    }

    public List<TransPromoConfig> selectTransConfigfortranspromoid(Long transPromoId) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo_config m where m.tras_promo_id = ?", TransPromoConfig.class);
        query.setParameter(1, transPromoId);
        return query.getResultList();
    }

//    public ResultCount getAllTransPromotionLevel2(String empId, String zoneId, int page) {
////        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo_article ta INNER JOIN trans_promo t ON "
////                + "t.trans_promo_id=ta.trans_promo_id,map_user_MCH_F3 f3 where f3.mc_code=ta.mc_code AND t.zone_id=? AND ta.current_status_id in (16,20,21)", TransPromo.class);
////        query.setParameter(1, new Long(zoneId));
//
//        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
//                + "t.trans_promo_id=tm.trans_promo_id,map_user_MCH_F3 f3 where f3.mc_code=tm.mc_code AND t.zone_id=? AND tm.status_id IN (16,20) AND f3.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);
//
//        query.setParameter(1, zoneId);
//        query.setParameter(2, new Long(empId));
//
//        Long pageCount = getAllTransPromotionLevel2count(empId, zoneId);
//        query.setFirstResult(page);
//        query.setMaxResults(MAX_PAGE_RESULT);
//        return new ResultCount(pageCount, query.getResultList());
//    }
    public Long getAllTransPromotionLevel2count(String empId, String zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code where t.status_id IN (16,20) AND t.zone_id=? AND tm.status_id IN (16,20) AND f3.emp_id=? order by  t.trans_promo_id desc");

        query.setParameter(1, zoneId);
        query.setParameter(2, new Long(empId));
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> setStatusforTransPromoReq(Long transPromoId, Long statusId, Long statusId1) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.trans_promo_id = ? and m.status_Id in (?,?)", TransPromo.class);
        query.setParameter(1, transPromoId);
        query.setParameter(2, statusId);
        query.setParameter(3, statusId1);
        return query.getResultList();
    }

    public ResultCount getAllTransPromotionBusinessExigency(int page) {
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  where  t.status_id in (19,24,22) order by t.trans_promo_id desc", TransPromo.class);

//        int pagecount = query.getResultList().size();
        Long pageCount = getAllTransPromotionBusinessExigencyCount();
        query.setFirstResult(page);
        query.setMaxResults(MAX_PAGE_RESULT);
        return new ResultCount(pageCount, query.getResultList());
    }

    public Long getAllTransPromotionBusinessExigencyCount() {
        Query query = em.createNativeQuery("select count(*) from trans_promo t  where  t.status_id in (19,24,22) order by t.trans_promo_id desc");
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getBusinessExigencyPromoDetailByUserDate(String startDate, String endDate, int startIndex) {
        Query q = em.createNativeQuery("select * from trans_promo t  where t.status_id in (19,24,22) AND  t.updated_date >=? AND t.updated_date<=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, startDate);
        q.setParameter(2, endDate);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getBusinessExigencyPromoDetailByUserDateCount(String startDate, String endDate) {
        Query q = em.createNativeQuery("select count(*) from trans_promo t  where t.status_id in (19,24,22)  AND t.updated_date >=? AND t.updated_date<=? order by trans_promo_id desc");
        q.setParameter(1, startDate);
        q.setParameter(2, endDate);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getBusinessExigencyPromoDetailByUserEvent(Long eventId, int startIndex) {
        Query q = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.status_id in (19,24,22) AND  m.event_id=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, eventId);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getBusinessExigencyPromoDetailByUserEventCount(Long eventId) {
        Query q = em.createNativeQuery("select count(*) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.status_id in (19,24,22)  AND m.event_id=? order by trans_promo_id desc");
        q.setParameter(1, eventId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getBusinessExigencyPromoDetailByUserMarketting(Long markettingID, int startIndex) {
        Query q = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.status_id in (19,24,22)  AND m.mktg_id=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, markettingID);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getBusinessExigencyPromoDetailByUserMarkettingCount(Long markettingID) {
        Query q = em.createNativeQuery("select count(*) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.status_id in (19,24,22) AND  m.mktg_id=? order by trans_promo_id desc");
        q.setParameter(1, markettingID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getBusinessExigencyPromoDetailByUserPromoType(Long promoTypeID, int startIndex) {
        Query q = em.createNativeQuery("select * from trans_promo  where status_id in (19,24,22) AND  promo_type_id=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, promoTypeID);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getBusinessExigencyPromoDetailByUserPromoTypeCount(Long promoTypeId) {
        Query q = em.createNativeQuery("select count(*) from trans_promo  where status_id in (19,24,22) AND  promo_type_id=? order by trans_promo_id desc");
        q.setParameter(1, promoTypeId);
        return new Long(q.getSingleResult().toString());
    }

    public int updateTransPromoMCforbussnessexigency(Long transpromoId, Long statusId) {
        Query query = em.createNativeQuery("update trans_promo_mc set status_id=? where trans_promo_id=?");
        query.setParameter(1, statusId);
        query.setParameter(2, transpromoId);
        return query.executeUpdate();
    }
}
