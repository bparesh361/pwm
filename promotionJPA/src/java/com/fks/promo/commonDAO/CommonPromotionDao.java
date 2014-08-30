/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.vo.ExecuteDashboardVO;
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
public class CommonPromotionDao {

    public static final Integer MAX_PAGE_RESULT = 30;
    public static final Integer L1_L2_MAX_PAGE_RESULT = 15;
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    public List<MstPromo> searchMstPromoByZoneStatusUser(String zoneId, Long statusId) {
        return null;
    }

    public int getAllTransPromoMaxArticleSetNo(long trans_promo_id) {
        Query query = em.createNativeQuery("select MAX(set_id) from trans_promo_article where trans_promo_id=?");
        query.setParameter(1, trans_promo_id);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    public int getTransPromoArticleForDownload(long trans_promo_id, String filePath_Name) {
        String str_query = "select set_id,article,article_desc,mc_code,mc_desc,brand_code,brand_desc,qty from trans_promo_article where trans_promo_id=" + trans_promo_id
                + " INTO OUTFILE  '" + filePath_Name + "' "
                + " FIELDS TERMINATED BY ',' "
                + " LINES TERMINATED BY '\\n'";
        Query query = em.createNativeQuery(str_query);
//        query.setParameter(1, trans_promo_id);
        System.out.println("################ article db query : " + str_query);
        List list = query.getResultList();
        System.out.println(list);
        return list.size();
    }

    public List<MstPromo> searchMstPromoByStatusUser(Long userId, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_promo where status_id IN (5,6,11) and created_by=? order by promo_id desc", MstPromo.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        //query.setParameter(1, statusId);
        query.setParameter(1, userId);
        return query.getResultList();

    }

    public Long searchMstPromoByStatusUserCount(Long userId, int startIndex) {
        Query query = em.createNativeQuery("select count(*) from mst_promo where status_id IN (5,6,11) and created_by=?");
        //  query.setParameter(1, statusId);
        query.setParameter(1, userId);
        return new Long(query.getSingleResult().toString());

    }

    public List<MstPromo> searchMstPromoByStatusUserOrg(Long statusId, Long userId, int startIndex) {
//        Query query = em.createNativeQuery("select * from mst_promo where status_id=? and created_by=? order by promo_id desc", MstPromo.class);
        Query query = em.createNativeQuery("select distinct m.* from mst_promo m INNER JOIN trans_promo tp ON m.promo_id=tp.promo_id where m.status_id=? and m.created_by=? order by m.promo_id desc", MstPromo.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        query.setParameter(1, statusId);
        query.setParameter(2, userId);
        return query.getResultList();

    }

    public Long searchMstPromoByStatusUserCountOrg(Long statusId, Long userId, int startIndex) {
        Query query = em.createNativeQuery("select distinct m.* from mst_promo m INNER JOIN trans_promo tp ON m.promo_id=tp.promo_id where m.status_id=? and m.created_by=? order by m.promo_id desc", MstPromo.class);
        query.setParameter(1, statusId);
        query.setParameter(2, userId);
        return new Long(query.getResultList().size());

    }

    public List<TransPromo> getAllZoneL1ByPassTransPromotion(String l1empId, String l2empId, String zoneId, int pageNo) {

        Query query = em.createNativeQuery("select  DISTINCT t.* from trans_promo t "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " inner join map_user_MCH_F3 f3 on f2.mc_code=f3.mc_code "
                + " inner join map_user_MCH_F3 f31 on f31.mc_code=tm.mc_code "
                + " where "
                + " t.status_id IN (12,13) AND  "
                + " tm.status_id IN (12,13) AND "
                + " f2.emp_id=? AND "
                + " f31.emp_id=? AND "
                + " t.zone_id=? "
                + " order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(l1empId));
        query.setParameter(2, new Long(l2empId));
        query.setParameter(3, new Long(zoneId));

        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getAllZoneL1ByPassTransPromotionCount(String l1empId, String l2empId, String zoneId) {

        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " inner join map_user_MCH_F3 f3 on f2.mc_code=f3.mc_code "
                + " inner join map_user_MCH_F3 f31 on f31.mc_code=tm.mc_code "
                + " where "
                + " t.status_id IN (12,13) AND  "
                + " tm.status_id IN (12,13) AND "
                + " f2.emp_id=? AND "
                + " f31.emp_id=? AND "
                + " t.zone_id=? "
                + " order by  t.trans_promo_id desc");

        query.setParameter(1, new Long(l1empId));
        query.setParameter(2, new Long(l2empId));
        query.setParameter(3, new Long(zoneId));


        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getAllL1ByPassTransPromotion(String l1empId, String l2empId, int pageNo) {

        Query query = em.createNativeQuery("select  DISTINCT t.* from trans_promo t "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " inner join map_user_MCH_F3 f3 on f2.mc_code=f3.mc_code "
                + " inner join map_user_MCH_F3 f31 on f31.mc_code=tm.mc_code "
                + " where "
                + " t.status_id IN (12,13) AND  "
                + " tm.status_id IN (12,13) AND "
                + " f2.emp_id=? AND "
                + " f31.emp_id=? "
                + " order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(l1empId));
        query.setParameter(2, new Long(l2empId));
        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getAllL1ByPassTransPromotionCount(String l1empId, String l2empId) {

        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " inner join map_user_MCH_F3 f3 on f2.mc_code=f3.mc_code "
                + " inner join map_user_MCH_F3 f31 on f31.mc_code=tm.mc_code "
                + " where "
                + " t.status_id IN (12,13) AND  "
                + " tm.status_id IN (12,13) AND "
                + " f2.emp_id=? AND "
                + " f31.emp_id=? "
                + " order by  t.trans_promo_id desc");

        query.setParameter(1, new Long(l1empId));
        query.setParameter(2, new Long(l2empId));

        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getAllTransPromotion(String empId, int pageNo) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(empId));
        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public List<TransPromo> getAllTransPromotionLevel2(String empId, int pageNo) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f3.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(empId));
        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public List<TransPromo> getAllTransPromotionForDate(String empId, String startDate, String endDate, int pageNo) {
        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code  "
                + "where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(empId));
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);

        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);

        return query.getResultList();
    }

    public Long getAllTransPromotionForDateCount(String empId, String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code  "
                + "where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc");

        query.setParameter(1, new Long(empId));
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getViewL1HOPromoDetailByUserEvent(Long empId, Long eventId, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.event_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL1HOPromoDetailByUserEventCount(Long empId, Long eventId) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.event_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL1ZonePromoDetailByUserEvent(Long empId, Long eventId, String zoneID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.event_id=? AND t.zone_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setParameter(3, zoneID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL1ZonePromoDetailByUserEventCount(Long empId, Long eventId, String zoneID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.event_id=? AND t.zone_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setParameter(3, zoneID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL1HOPromoDetailByUserMarketting(Long empId, Long markettingId, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.mktg_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL1HOPromoDetailByUserMarkettingCount(Long empId, Long markettingId) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.mktg_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL1ZonePromoDetailByUserMarketting(Long empId, Long markettingId, String zoneID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.mktg_id=? AND t.zone_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        q.setParameter(3, zoneID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL1ZonePromoDetailByUserMarkettingCount(Long empId, Long markettingId, String zoneID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.mktg_id=? AND t.zone_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        q.setParameter(3, zoneID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL1HOPromoDetailByUserPromoType(Long empId, Long promoTypeID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND t.promo_type_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL1HOPromoDetailByUserPromoTypeCount(Long empId, Long promoTypeID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND t.promo_type_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL1ZonePromoDetailByUserPromType(Long empId, Long promoTypeID, String zoneID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND t.promo_type_id=? AND t.zone_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setParameter(3, zoneID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL1ZonePromoDetailByUserPromoTypeCount(Long empId, Long promoTypeID, String zoneID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=?  AND t.promo_type_id=? AND t.zone_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setParameter(3, zoneID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getAllTransPromotionForLevel2Date(String empId, String startDate, String endDate, int pageNo) {

        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code  "
                + "where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f3.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(empId));
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);

        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);

        return query.getResultList();
    }

    public Long getAllTransPromotionForLevel2DateCount(String empId, String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                + "INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id "
                + "inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code  "
                + "where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f3.emp_id=? AND t.updated_date>=? AND t.updated_date<=? order by  t.trans_promo_id desc");

        query.setParameter(1, new Long(empId));
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getAllTransPromotionForCategory(String empId, String categoryName, int pageNo) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select distinct * from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F2 where emp_id=?) "
                + " AND tp.status_id in (12,13) AND tm.status_id in (12,13) order by tp.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));

        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getAllTransPromotionForCategoryCount(String empId, String categoryName) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F2 where emp_id=?) "
                + " AND tp.status_id in (12,13) AND tm.status_id in (12,13) order by tp.trans_promo_id desc");

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getZoneAllTransPromotionForCategory(String empId, String categoryName, String zoneId, int pageNo) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select distinct * from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F2 where emp_id=?) "
                + " AND tp.status_id in (12,13) AND tm.status_id in (12,13) AND  tp.zone_id=? order by tp.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, zoneId);
        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getZoneAllTransPromotionForCategoryCount(String empId, String categoryName, String zoneId) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F2 where emp_id=?) "
                + " AND tp.status_id in (12,13) AND tm.status_id in (12,13) AND  tp.zone_id=? order by tp.trans_promo_id desc");

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, zoneId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getAllTransPromotionForLevel2Category(String empId, String categoryName, int pageNo) {

        Query query = em.createNativeQuery("select distinct tp.* from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F3 where emp_id=?) "
                + " AND tp.status_id in (16,20) AND tm.status_id in (16,20) order by tp.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));

        query.setFirstResult(pageNo);
        query.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return query.getResultList();

    }

    public Long getAllTransPromotionForLevel2CategoryCount(String empId, String categoryName) {
        Query query = em.createNativeQuery("select  count(*) from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F3 where emp_id=?) "
                + " AND tp.status_id in (16,20) AND tm.status_id in (16,20) order by tp.trans_promo_id desc");

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getZoneAllTransPromotionForLevel2Category(String empId, String categoryName, String zoneId) {
        Query query = em.createNativeQuery("select distinct tp.* from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F3 where emp_id=?) "
                + " AND tp.status_id in (16,20) AND tm.status_id in (16,20) AND tp.zone_id=? order by tp.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, zoneId);

        return query.getResultList();
    }

    public Long getZoneAllTransPromotionForLevel2CategoryCount(String empId, String categoryName, String zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp  "
                + " inner join trans_promo_mc tm on tm.trans_promo_id = tp.trans_promo_id "
                + " where tm.mc_code in (select mc_code from mch where mch.category_name=?) "
                + " AND tm.mc_code in (select mc_code from map_user_MCH_F3 where emp_id=?) "
                + " AND tp.status_id in (16,20) AND tm.status_id in (16,20) AND tp.zone_id=? order by tp.trans_promo_id desc");


        query.setParameter(1, categoryName);
        query.setParameter(2, new Long(empId));
        query.setParameter(3, zoneId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getAllTransPromotionForCategoryZone(String empId, String categoryName, String zoneCode) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select distinct t.* from trans_promo t inner join trans_promo_mc tm on "
                + "t.trans_promo_id=tm.trans_promo_id, map_user_MCH_F2 f2 where f2.mc_code in (select mc_code from mch where category_name = ?)"
                + "AND tm.status_id IN (12,13) AND f2.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);

        query.setParameter(1, new Long(empId));
        query.setParameter(2, categoryName);
        return query.getResultList();
    }

    public Long getAllTransPromotionForCategoryZoneCount(String empId, String categoryName, String zoneCode) {
//        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (12,13) order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t inner join trans_promo_mc tm on "
                + "t.trans_promo_id=tm.trans_promo_id, map_user_MCH_F2 f2 where f2.mc_code in (select mc_code from mch where category_name = ?)"
                + "AND tm.status_id IN (12,13) AND f2.emp_id=? order by  t.trans_promo_id desc");

        query.setParameter(1, new Long(empId));
        query.setParameter(2, categoryName);
        return new Long(query.getSingleResult().toString());
    }

//    public List<TransPromo> getAllTransPromotionLevel2(String empId) {
//        //Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (16,20,21) order by m.trans_promo_id desc", TransPromo.class);
//        //return query.getResultList();
//
//        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
//                + "t.trans_promo_id=tm.trans_promo_id,map_user_MCH_F3 f3 where f3.mc_code=tm.mc_code  AND tm.status_id IN (16,20) AND f3.emp_id=? order by  t.trans_promo_id desc", TransPromo.class);
//
//        query.setParameter(1, new Long(empId));
//        return query.getResultList();
//    }
    public Long getAllTransPromotionLevel2Count(String empId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f3.emp_id=? order by  t.trans_promo_id desc");
        query.setParameter(1, new Long(empId));
        return new Long(query.getSingleResult().toString());
    }

    public Long getAllTransPromotionCount(String empId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  INNER JOIN trans_promo_mc tm  ON "
                + "t.trans_promo_id=tm.trans_promo_id inner join map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code where t.status_id IN (12,13) AND tm.status_id IN (12,13) AND f2.emp_id=? order by  t.trans_promo_id desc");

        query.setParameter(1, new Long(empId));

        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getViewL2HOPromoDetailByUserEvent(Long empId, Long eventId, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.event_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL2HOPromoDetailByUserEventCount(Long empId, Long eventId) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.event_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL2ZonePromoDetailByUserEvent(Long empId, Long eventId, String zoneID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.event_id=? AND t.zone_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setParameter(3, zoneID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL2ZonePromoDetailByUserEventCount(Long empId, Long eventId, String zoneID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.event_id=? AND t.zone_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setParameter(3, zoneID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL2HOPromoDetailByUserMarketting(Long empId, Long markettingId, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.mktg_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL2HOPromoDetailByUserMarkettingCount(Long empId, Long markettingId) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.mktg_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL2ZonePromoDetailByUserMarketting(Long empId, Long markettingId, String zoneID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.mktg_id=? AND t.zone_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        q.setParameter(3, zoneID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL2ZonePromoDetailByUserMarkettingCount(Long empId, Long markettingId, String zoneID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND m.mktg_id=? AND t.zone_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, markettingId);
        q.setParameter(3, zoneID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL2HOPromoDetailByUserPromoType(Long empId, Long promoTypeID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND t.promo_type_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL2HOPromoDetailByUserPromoTypeCount(Long empId, Long promoTypeID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND t.promo_type_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewL2ZonePromoDetailByUserPromType(Long empId, Long promoTypeID, String zoneID, int startIndex) {
        Query q = em.createNativeQuery("select DISTINCT t.* from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND t.promo_type_id=? AND t.zone_id=? order by  t.trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setParameter(3, zoneID);
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewL2ZonePromoDetailByUserPromoTypeCount(Long empId, Long promoTypeID, String zoneID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " inner join map_user_MCH_F3 f2 on f2.mc_code=tm.mc_code "
                + " where t.status_id IN (16,20) AND tm.status_id IN (16,20) AND f2.emp_id=?  AND t.promo_type_id=? AND t.zone_id=? order by  t.trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setParameter(3, zoneID);
        return new Long(q.getSingleResult().toString());
    }

//    public List<TransPromo> getAllTransPromotion(String zoneId) {
//        Query query = em.createNativeQuery("select DISTINCT t.* from trans_promo_article ta INNER JOIN trans_promo t ON "
//                + "t.trans_promo_id=ta.trans_promo_id,map_user_MCH_F1 f1 where f1.mc_code=ta.mc_code AND t.zone_id=? AND ta.current_status_id=?", TransPromo.class);
//        query.setParameter(1, new Long(zoneId));
//        query.setParameter(2, CommonStatusConstants.PROMO_SUBMITTED);
//        return query.getResultList();
//    }
    public int changeAllTransPromoMCStatus(Long transPromoId, Long statusId, Long fromStatusId) {
        //Query query = em.createNativeQuery("update trans_promo_article set current_status_id=?, updated_date=now() where trans_promo_id=? and current_status_id=?");
        Query query = em.createNativeQuery("update trans_promo_mc set status_id=? where trans_promo_id=?");
        query.setParameter(1, statusId);
        query.setParameter(2, transPromoId);
        //query.setParameter(3, fromStatusId);
        return query.executeUpdate();
    }

    public int updateAllTransPromoIsHOBasedOnZone(Long promoId) {
        Query query = em.createNativeQuery("update trans_promo set is_ho=1 where promo_id=?");
        query.setParameter(1, promoId);
        return query.executeUpdate();
    }

    public List<TransPromo> getAllTransPromotionByStatusId(Long statusId) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id = ? and m.updated_date<current_date order by m.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, statusId);
        return query.getResultList();
    }

    public List<TransPromo> getAllTransPromotionByStatusIdForL2PendingEscalation() {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (?,?) and m.updated_date<current_date order by m.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, CommonStatusConstants.PROMO_L1_ESCALATED);
        query.setParameter(2, CommonStatusConstants.PROMO_L1_HOLD_ESCALATED);
        return query.getResultList();
    }

    public int changeAllTransPromoStatus(Long mstPromoId, Long statusID) {
        Query query = em.createNativeQuery("update trans_promo set status_id=?, updated_date=now() where trans_promo_id=?");
        query.setParameter(1, statusID);
        query.setParameter(2, mstPromoId);
        return query.executeUpdate();
    }

    public int changeAllTransPromoMCStatus(Long mstPromoId, Long statusID) {
        Query query = em.createNativeQuery("update trans_promo_mc set status_id=? where trans_promo_id=?");
        query.setParameter(1, statusID);
        query.setParameter(2, mstPromoId);
        return query.executeUpdate();
    }

    public int changeMstPromoStatus(Long mstPromoId, Long statusID, Long EmpId) {
        Query query = em.createNativeQuery("update mst_promo set status_id=? , updated_date=now() , updated_by=? where promo_id=?");
        query.setParameter(1, statusID);
        query.setParameter(2, EmpId);
        query.setParameter(3, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoCity(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_city where promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoFormat(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_format where promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoRegion(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_region where promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoState(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_state where promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoZone(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_zone where map_promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoStore(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_store where promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public int deleteMapPromoMc(Long mstPromoId) {
        Query query = em.createNativeQuery("DELETE from map_promo_mch where mst_promo_id=?");
        query.setParameter(1, mstPromoId);
        return query.executeUpdate();
    }

    public Long getTransPromoCountForDelete(Long promoID) {
        Query q = em.createNativeQuery("select count(*) from trans_promo where status_id in(5,6) and promo_id=?");
        q.setParameter(1, promoID);
        return new Long(q.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtl(boolean getIsExecPromoDashboardDtl, int startIndex) {
        Query query = null;
        if (getIsExecPromoDashboardDtl == false) {
            query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (14,17,25,27,32) AND m.is_ho=1  order by m.trans_promo_id desc", TransPromo.class);
        } else {
            query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id =27  order by m.trans_promo_id desc", TransPromo.class);
        }
        Long totalCount = getHOTransPromoDtlCount(getIsExecPromoDashboardDtl);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlCount(boolean getIsExecPromoDashboardDtl) {
        Query query = null;
        if (getIsExecPromoDashboardDtl == false) {
            //query = em.createNativeQuery("SELECT count(*) FROM trans_promo m where m.status_Id IN (14,17,25) ");
            query = em.createNativeQuery("SELECT count(*) FROM trans_promo m where m.status_Id IN (14,17,25,27,32) AND m.is_ho=1");
        } else {
            query = em.createNativeQuery("SELECT count(*) FROM trans_promo m where m.status_Id =27");
        }
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByDate(String startDate, String endDate, int startIndex) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo  where status_Id IN (14,17,25,27,32) AND is_ho=1  AND updated_date >=? AND updated_date<=? order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        Long totalCount = getHOTransPromoDtlByDateCount(startDate, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByDateCount(String startDate, String endDate) {
        Query query = em.createNativeQuery("SELECT count(*) FROM trans_promo  where status_Id IN (14,17,25,27,32) AND is_ho=1 AND updated_date >=? AND updated_date<=?");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByDate(String startDate, String endDate, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("SELECT tp.* FROM trans_promo tp Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where (tp.zone_id=? OR mz.zone_id=?)  AND tp.is_ho=0 AND "
                + "status_Id IN (14,17,25,27,32) AND tp.updated_date >=? AND tp.updated_date<=?  order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        Long totalCount = getZoneTransPromoDtlByDateCount(startDate, endDate, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByDateCount(String startDate, String endDate, Long zoneID) {
        Query query = em.createNativeQuery("SELECT count(DISTINCT tp.trans_promo_id) FROM trans_promo tp Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where (tp.zone_id=? OR mz.zone_id=?)  AND tp.is_ho=0 AND "
                + "status_Id IN (14,17,25,27,32) AND tp.updated_date >=? AND tp.updated_date<=?  order by trans_promo_id desc");
        query.setParameter(1, zoneID);
        query.setParameter(2, zoneID);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByEvent(Long eventId, int startIndex) {
        //System.out.println("############## Event Id : " + eventId + "--- ######## startIndex : " + startIndex);
        Query query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.event_id=? order by t.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, eventId);
        Long totalCount = getHOTransPromoDtlByEventCount(eventId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByEventCount(Long eventId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.event_id=? ");
        query.setParameter(1, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByEventAndDate(String startDate, String endDate, Long eventId, int startIndex) {
        //System.out.println("############## Event Id : " + eventId + "--- ######## startIndex : " + startIndex);
        Query query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32)  AND  m.event_id=? AND t.updated_date >=? AND t.updated_date<=? order by t.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, eventId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        Long totalCount = getHOTransPromoDtlByEventAndCount(startDate, endDate, eventId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByEventAndCount(String startDate, String endDate, Long eventId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.event_id=? AND t.updated_date >=? AND t.updated_date<=? ");
        query.setParameter(1, eventId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByEvent(Long eventId, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.event_id=? order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, eventId);
        Long totalCount = getZoneTransPromoDtlByEventCount(eventId, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByEventCount(Long eventId, Long zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + "Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.event_id=?");
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByEventDate(String startDate, String endDate, Long eventId, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.event_id=? AND tp.updated_date >=? AND tp.updated_date<=? order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, eventId);
        query.setParameter(4, startDate);
        query.setParameter(5, endDate);

        Long totalCount = getZoneTransPromoDtlByEventDateCount(startDate, endDate, eventId, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByEventDateCount(String startDate, String endDate, Long eventId, Long zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + "Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.event_id=? AND tp.updated_date >=? AND tp.updated_date<=? ");
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, eventId);
        query.setParameter(4, startDate);
        query.setParameter(5, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByMarketting(Long markettingId, int startIndex) {
        Query query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.mktg_id=? order by t.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, markettingId);
        Long totalCount = getHOTransPromoDtlByMarkeetingCount(markettingId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByMarkeetingCount(Long markettingId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.mktg_id=? ");
        query.setParameter(1, markettingId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByMarkettingDate(String startDate, String endDate, Long markettingId, int startIndex) {
        Query query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.mktg_id=? AND t.updated_date >=? AND t.updated_date<=? order by t.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, markettingId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        Long totalCount = getHOTransPromoDtlByMarkeetingDateCount(startDate, endDate, markettingId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByMarkeetingDateCount(String startDate, String endDate, Long markettingId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where  t.is_ho=1 AND t.status_id in (14,17,25,27,32) AND  m.mktg_id=? AND t.updated_date >=? AND t.updated_date<=?");
        query.setParameter(1, markettingId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByMarketting(Long markettingId, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.mktg_id=?  order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, markettingId);
        Long totalCount = getZoneTransPromoDtlByMarkettingCount(markettingId, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByMarkettingCount(Long markettignId, Long zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.mktg_id=?");
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, markettignId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByMarkettingDate(String startDate, String endDate, Long markettingId, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.mktg_id=? AND tp.updated_date >=? AND tp.updated_date<=? order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, markettingId);
        query.setParameter(4, startDate);
        query.setParameter(5, endDate);
        Long totalCount = getZoneTransPromoDtlByMarkettingDateCount(startDate, endDate, markettingId, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByMarkettingDateCount(String startDate, String endDate, Long markettignId, Long zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  m.mktg_id=? AND tp.updated_date >=? AND tp.updated_date<=? ");
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, markettignId);
        query.setParameter(4, startDate);
        query.setParameter(5, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByPromoType(Long promoTypeId, int startIndex) {
        Query query = em.createNativeQuery("select * from trans_promo  where is_ho=1 AND status_id in (14,17,25,27,32) AND promo_type_id=? order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, promoTypeId);
        Long totalCount = getHOTransPromoDtlByPromoTypeCount(promoTypeId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByPromoTypeCount(Long promoTypeId) {
        Query query = em.createNativeQuery("select count(*) from trans_promo  where is_ho=1 AND status_id in (14,17,25,27,32) AND promo_type_id=? ");
        query.setParameter(1, promoTypeId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getHOPromoManagerDtlByPromoTypeDate(String startDate, String endDate, Long promoTypeId, int startIndex) {
        Query query = em.createNativeQuery("select * from trans_promo  where is_ho=1 AND status_id in (14,17,25,27,32) AND promo_type_id=? AND updated_date >=? AND updated_date<=? order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, promoTypeId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        Long totalCount = getHOTransPromoDtlByPromoTypeDateCount(startDate, endDate, promoTypeId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getHOTransPromoDtlByPromoTypeDateCount(String startDate, String endDate, Long promoTypeId) {
        Query query = em.createNativeQuery("select count(*) from trans_promo  where is_ho=1 AND status_id in (14,17,25,27,32) AND promo_type_id=? AND updated_date >=? AND updated_date<=? ");
        query.setParameter(1, promoTypeId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByPromoType(Long promoTypeId, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  tp.promo_type_id=?  order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, promoTypeId);

        Long totalCount = getZoneTransPromoDtlByPromoTypeCount(promoTypeId, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByPromoTypeCount(Long promoTypeId, Long zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  tp.promo_type_id=? ");
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, promoTypeId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getZonePromoManagerDtlByPromoTypeDate(String startDate, String endDate, Long promoTypeId, Long zoneId, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  tp.promo_type_id=?  AND tp.updated_date >=? AND tp.updated_date<=? order by tp.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, promoTypeId);
        query.setParameter(4, startDate);
        query.setParameter(5, endDate);

        Long totalCount = getZoneTransPromoDtlByPromoTypeCountDate(startDate, endDate, promoTypeId, zoneId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlByPromoTypeCountDate(String startDate, String endDate, Long promoTypeId, Long zoneId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp INNER JOIN  mst_promo m ON  tp.promo_id=m.promo_id "
                + " Left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                + "where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) "
                + "and tp.status_id in (14,17,25,27,32) AND  tp.promo_type_id=? AND tp.updated_date >=? AND tp.updated_date<=? ");
        query.setParameter(1, zoneId);
        query.setParameter(2, zoneId);
        query.setParameter(3, promoTypeId);
        query.setParameter(4, startDate);
        query.setParameter(5, endDate);
        return new Long(query.getSingleResult().toString());
    }

    // promo manager exe
    public ExecuteDashboardVO getZonePromoManagerDtl(String zoneId, boolean getIsExecPromoDashboardDtl, int startIndex) {
        Query query = null;

        if (getIsExecPromoDashboardDtl == false) {
//            query = em.createNativeQuery("select tp.* from trans_promo tp "
//                    + "inner join mst_promo mp on tp.promo_id = mp.promo_id "
//                    + "inner join map_promo_zone mpz on mpz.map_promo_id = mp.promo_id "
//                    + "where tp.is_ho=0 and "
//                    + "tp.zone_id in (select zone_id from map_promo_zone where zone_id = ?) "
//                    + "and tp.status_id in (14,17,25)", TransPromo.class);
            query = em.createNativeQuery("select tp.* from trans_promo tp "
                    + " left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                    + " where tp.is_ho=0 and "
                    + " (tp.zone_id=? OR mz.zone_id=?) "
                    + " and tp.status_id in (14,17,25,27,32) order by tp.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, zoneId);
            query.setParameter(2, zoneId);
        } else {
            //query = em.createNativeQuery("SELECT m.* FROM trans_promo m where m.status_Id =27 AND m.is_ho='0' AND m.zone_id=? order by m.trans_promo_id desc", TransPromo.class);
            query = em.createNativeQuery("select tp.* from trans_promo tp inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) and tp.status_id in (27) order by tp.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, zoneId);
            query.setParameter(2, zoneId);
        }

        Long totalCount = getZoneTransPromoDtlCount(zoneId, getIsExecPromoDashboardDtl);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getZoneTransPromoDtlCount(String zoneId, boolean getIsExecPromoDashboardDtl) {
        Query query = null;
        if (getIsExecPromoDashboardDtl == false) {

//            query = em.createNativeQuery("select count(*) from trans_promo tp "
//                    + "inner join mst_promo mp on tp.promo_id = mp.promo_id "
//                    + "inner join map_promo_zone mpz on mpz.map_promo_id = mp.promo_id "
//                    + "where tp.is_ho=0 and "
//                    + "tp.zone_id in (select zone_id from map_promo_zone where zone_id = ?) "
//                    + "and tp.status_id in (14,17,25)");

            query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                    + " left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
                    + " where tp.is_ho=0 and "
                    + " (tp.zone_id=? OR mz.zone_id=?) "
                    + " and tp.status_id in (14,17,25,27,32)");
            query.setParameter(1, zoneId);
            query.setParameter(2, zoneId);
        } else {
            //query = em.createNativeQuery("SELECT count(*) FROM trans_promo m where m.status_Id =27 AND m.is_ho='0' AND m.zone_id=?");
            query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.is_ho=0 and (tp.zone_id=? OR mz.zone_id=?) and tp.status_id in (27)");
            query.setParameter(1, zoneId);
            query.setParameter(2, zoneId);
        }


        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getTeamMemberTransPromoDtl(String teamMemberId, int startIndex) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (27,29) AND executive_id=?  order by m.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, teamMemberId);
        Long totalCount = getTeamMemberTransPromoDtlCount(teamMemberId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));

    }

    public Long getTeamMemberTransPromoDtlCount(String teamMemberId) {
        Query query = em.createNativeQuery("SELECT count(*) FROM trans_promo m where m.status_Id IN (27,29) AND executive_id=?");
        query.setParameter(1, teamMemberId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getTeamMemberTransPromoDtlByDate(String teamMemberId, String startDate, String endDate, int startIndex) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo  where status_Id IN (27,29) AND executive_id=? AND updated_date >=? AND updated_date<=?  order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, teamMemberId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        Long totalCount = getTeamMemberTransPromoDtlByDateCount(teamMemberId, startDate, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));

    }

    public Long getTeamMemberTransPromoDtlByDateCount(String teamMemberId, String startDate, String endDate) {
        Query query = em.createNativeQuery("SELECT count(*) FROM trans_promo m where m.status_Id IN (27,29) AND executive_id=? AND updated_date >=? AND updated_date<=?");
        query.setParameter(1, teamMemberId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getTeamMemberTransPromoDtlByEvent(String teamMemberId, Long eventId, int startIndex) {
        //Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (27,29) AND executive_id=?  order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND m.event_id=? order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, teamMemberId);
        query.setParameter(2, eventId);
        Long totalCount = getTeamMemberTransPromoDtlByEventCount(teamMemberId, eventId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getTeamMemberTransPromoDtlByEventCount(String teamMemberId, Long eventId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND m.event_id=? ");
        query.setParameter(1, teamMemberId);
        query.setParameter(2, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getTeamMemberTransPromoDtlByMarketting(String teamMemberId, Long markettingId, int startIndex) {

        //Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (27,29) AND executive_id=?  order by m.trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND m.mktg_id=? order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, teamMemberId);
        query.setParameter(2, markettingId);
        Long totalCount = getTeamMemberTransPromoDtlByEventCount(teamMemberId, markettingId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));

    }

    public Long getTeamMemberTransPromoDtlByMarkettingCount(String teamMemberId, Long markettingId) {
        Query query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND m.mktg_id=? ");
        query.setParameter(1, teamMemberId);
        query.setParameter(2, markettingId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getTeamMemberTransPromoDtlByPromoType(String teamMemberId, Long promoTypeId, int startIndex) {

        Query query = em.createNativeQuery("select * from trans_promo  where executive_id=? AND status_id in (27,29) AND  promo_type_id=? order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, teamMemberId);
        query.setParameter(2, promoTypeId);
        Long totalCount = getTeamMemberTransPromoDtlByPromoTypeCount(teamMemberId, promoTypeId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));

    }

    public Long getTeamMemberTransPromoDtlByPromoTypeCount(String teamMemberId, Long promoTypeId) {
        Query query = em.createNativeQuery("select count(*) from trans_promo  where executive_id=? AND status_id in (27,29) AND  promo_type_id=?  ");
        query.setParameter(1, teamMemberId);
        query.setParameter(2, promoTypeId);
        return new Long(query.getSingleResult().toString());
    }

    public ExecuteDashboardVO getDateWiseTransPromoDtl(String startDate, String endDate, String zoneId, boolean isHO, int startIndex) {
        Query query = null;
        if (isHO == true) {
            query = em.createNativeQuery("SELECT t.* FROM trans_promo t where t.status_Id =27  AND t.updated_date >=? AND t.updated_date<=? order by t.trans_promo_id desc", TransPromo.class);
        } else {
//            query = em.createNativeQuery("SELECT t.* FROM trans_promo t  where tp.is_ho=0 AND t.status_Id =27  AND t.updated_date >=? AND t.updated_date<=? AND t.zone_id=? order by t.trans_promo_id desc", TransPromo.class);
            query = em.createNativeQuery("select tp.* from trans_promo tp inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.is_ho=0 AND tp.updated_date >=? AND tp.updated_date<=? and (tp.zone_id=? OR mz.zone_id=?) and tp.status_id in (27)", TransPromo.class);
            query.setParameter(3, zoneId);
            query.setParameter(4, zoneId);
        }
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        Long totalCount = getDateWiseTransPromoDtlCount(startDate, endDate, zoneId, isHO);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getDateWiseTransPromoDtlCount(String startDate, String endDate, String zoneId, boolean isHO) {
        Query query = null;
        if (isHO == true) {
            query = em.createNativeQuery("SELECT count(*) FROM trans_promo t where t.status_Id =27  AND t.updated_date >=? AND t.updated_date<=? ");
        } else {
//            query = em.createNativeQuery("select count(*) from trans_promo tp inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.is_ho=0 AND DATE_FORMAT(tp.updated_date,'%Y-%d-%m') >=? AND DATE_FORMAT(tp.updated_date,'%Y-%d-%m')<=? and (tp.zone_id=? OR mz.zone_id=?) and tp.status_id in (27)");
            query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.is_ho=0 AND tp.updated_date >=? AND tp.updated_date<=? and (tp.zone_id=? OR mz.zone_id=?) and tp.status_id in (27)");
            query.setParameter(3, zoneId);
            query.setParameter(4, zoneId);

//            query = em.createNativeQuery("SELECT count(*) FROM trans_promo t  where t.status_Id =27  AND t.updated_date >=? AND t.updated_date<=? AND t.zone_id=?");
//            query.setParameter(3, zoneId);
        }
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getAllPromoDetailUserWise(Long empId, int startIndex) {
        Query q = em.createNativeQuery("select * from trans_promo t  where t.created_by=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getAllPromoDetailUserWiseCount(Long empId) {
        Query q = em.createNativeQuery("select count(*) from trans_promo t  where t.created_by=?");
        q.setParameter(1, empId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewPromoDetailByUserDate(Long empId, String startDate, String endDate, int startIndex) {
        Query q = em.createNativeQuery("select * from trans_promo t  where t.created_by=? AND t.updated_date >=? AND t.updated_date<=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewPromoDetailByUserDateCount(Long empId, String startDate, String endDate, int startIndex) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t  where t.created_by=? AND t.updated_date >=? AND t.updated_date<=? order by trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, startDate);
        q.setParameter(3, endDate);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewPromoDetailByUserEvent(Long empId, Long eventId, int startIndex) {
        Query q = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND m.event_id=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewPromoDetailByUserEventCount(Long empId, Long eventId) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND m.event_id=? order by trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, eventId);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewPromoDetailByUserMarketting(Long empId, Long markettingID, int startIndex) {
        Query q = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND m.mktg_id=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, markettingID);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewPromoDetailByUserMarkettingCount(Long empId, Long markettingID) {
        Query q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND m.mktg_id=? order by trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, markettingID);
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getViewPromoDetailByUserPromoType(Long empId, Long promoTypeID, int startIndex) {
        Query q = em.createNativeQuery("select * from trans_promo  where created_by=? AND promo_type_id=? order by trans_promo_id desc", TransPromo.class);
        q.setParameter(1, empId);
        q.setParameter(2, promoTypeID);
        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getViewPromoDetailByUserPromoTypeCount(Long empId, Long markettingID) {
        Query q = em.createNativeQuery("select count(*) from trans_promo  where created_by=? AND promo_type_id=? order by trans_promo_id desc");
        q.setParameter(1, empId);
        q.setParameter(2, markettingID);
        return new Long(q.getSingleResult().toString());
    }

    public void deleteAllARticlewithTransPromo(Long transPromoId) {
        Query query = em.createNativeQuery("delete from trans_promo_article where trans_promo_id=?");
        query.setParameter(1, transPromoId);
        query.executeUpdate();
    }

    public void deleteAllMcwithTransPromo(Long transPromoId) {
        Query query = em.createNativeQuery("delete from trans_promo_mc where trans_promo_id=?");
        query.setParameter(1, transPromoId);
        query.executeUpdate();
    }

    public void deleteAllConfigwithTransPromo(Long transPromoId) {
        Query query = em.createNativeQuery("delete from trans_promo_config where tras_promo_id=?");
        query.setParameter(1, transPromoId);
        query.executeUpdate();
    }

    public void deleteTransPromoRequest(Long transPromoId, Long statusId) {
        Query query = em.createNativeQuery("delete from trans_promo where trans_promo_id =? and status_id=?");
        query.setParameter(1, transPromoId);
        query.setParameter(2, statusId);
        query.executeUpdate();
    }

    public void deleteMasterPromotion(Long transPromoId, Long statusId) {
        Query query = em.createNativeQuery("delete from mst_promo where promo_id=? and status_id=?");
        query.setParameter(1, transPromoId);
        query.setParameter(2, statusId);
        query.executeUpdate();
    }

    public void deleteAllFilewithTransPromo(Long transPromoId) {
        Query query = em.createNativeQuery("delete from trans_promo_file where trans_promo_id=?");
        query.setParameter(1, transPromoId);
        query.executeUpdate();
    }

    public List<TransPromo> getHOViewL1PromoDetail(String searchType, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID, int startIndex) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?   order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        }

        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getHOViewL1PromoDetailCount(String searchType, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(3, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?   order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        }

        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getZoneViewL1PromoDetail(String searchType, String zoneID, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID, int startIndex) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            System.out.println("------- SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_PROMO_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_PROMO_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        }
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getZoneViewL1PromoDetailCount(String searchType, String zoneID, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            System.out.println("------- SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_PROMO_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            System.out.println("------- SUB_CATEGORY_DATE_PROMO_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F2 f2 on f2.mc_code=tm.mc_code "
                    + " where t.status_id IN (12,13) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (12,13) AND f2.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        }
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getHOViewL2PromoDetail(String searchType, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID, int startIndex) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (16,20) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        }
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getHOViewL2PromoDetailCount(String searchType, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (16,20) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        }

        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getZoneViewL2PromoDetail(String searchType, String zoneID, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID, int startIndex) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        }
        q.setFirstResult(startIndex);
        q.setMaxResults(L1_L2_MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getZoneViewL2PromoDetailCount(String searchType, String zoneID, String subCategoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, Long empID) {
        Query q = null;
        if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  AND m.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, eventTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=? AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
            q.setParameter(4, markettingTypeID);
            q.setParameter(5, empID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            System.out.println("------------ L2 Zone SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN map_user_MCH_F3 f3 on f3.mc_code=tm.mc_code "
                    + " where t.status_id IN (16,20) AND  t.zone_id=? AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND tm.status_id IN (16,20) AND f3.emp_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, zoneID);
            q.setParameter(2, promoTypeID);
            q.setParameter(3, startDate);
            q.setParameter(4, endDate);
            q.setParameter(5, empID);
        }
        return new Long(q.getSingleResult().toString());
    }

    public List<TransPromo> getExigencyViewPromoDetail(String searchType, String categoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, int startIndex) {
        Query q = null;
        if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
            System.out.println("------------ Exigency CATEGORY_DATE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=?  AND mc.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=?  AND mc.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND m.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=?  AND m.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE_PROMO_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (19,24,22) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mc.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            System.out.println("------------ Exigency DATE_EVENT_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=?  order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            System.out.println("------------ Exigency DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=?   order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            System.out.println("------------ Exigency DATE_PROMO_TYPE");
            q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?   order by  t.trans_promo_id desc", TransPromo.class);
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
        }

        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getExigencyViewPromoDetailCount(String searchType, String categoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID) {
        Query q = null;
        if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=?  AND mc.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=?  AND mc.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE_EVENT_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND m.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=?  AND m.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
        } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
            System.out.println("------------ Exigency SUB_CATEGORY_DATE_PROMO_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.status_id IN (19,24,22) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mc.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
        } else if (searchType.equalsIgnoreCase("DATE_EVENT_TYPE")) {
            System.out.println("------------ Exigency DATE_EVENT_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=?  order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, eventTypeID);
        } else if (searchType.equalsIgnoreCase("DATE_MARKETTING_TYPE")) {
            System.out.println("------------ Exigency DATE_MARKETTING_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=?   order by  t.trans_promo_id desc");
            q.setParameter(1, startDate);
            q.setParameter(2, endDate);
            q.setParameter(3, markettingTypeID);
        } else if (searchType.equalsIgnoreCase("DATE_PROMO_TYPE")) {
            System.out.println("------------ Exigency DATE_PROMO_TYPE");
            q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " where t.status_id IN (19,24,22) AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?   order by  t.trans_promo_id desc");
            q.setParameter(1, promoTypeID);
            q.setParameter(2, startDate);
            q.setParameter(3, endDate);
        }

        return new Long(q.getSingleResult().toString());
    }

    public ExecuteDashboardVO getTeamMemberTransPromoDtl(String searchCriteria, String teamMemberId, String startDate, String endDate, Long eventId, Long markettingTypeId, Long promoTypeId, String categoryName, int startIndex) {
        Query query = null;
        System.out.println("-------- Search Criteria : " + searchCriteria);
        if (searchCriteria.equalsIgnoreCase("CATEGORY_DATE")) {
            query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                    + " INNER JOIN mst_promo mp ON mp.promo_id=t.promo_id "
                    + " where t.status_id IN (27,29) AND t.executive_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mp.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
        } else if (searchCriteria.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                    + " INNER JOIN mst_promo mp ON mp.promo_id=t.promo_id "
                    + " where t.status_id IN (27,29) AND t.executive_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
        } else if (searchCriteria.equalsIgnoreCase("DATE_EVENT")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND t.updated_date >=? AND t.updated_date<=? AND m.event_id=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, eventId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_MARKETING_TYPE")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND t.updated_date >=? AND t.updated_date<=? AND m.mktg_id=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, markettingTypeId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_PROMOTION_TPYE")) {
            query = em.createNativeQuery("select * from trans_promo  where executive_id=? AND status_id in (27,29) AND updated_date >=? AND updated_date<=? AND  promo_type_id=? order by trans_promo_id desc", TransPromo.class);
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, promoTypeId);
        }

        Long totalCount = getTeamMemberTransPromoDtlCount(searchCriteria, teamMemberId, startDate, endDate, eventId, markettingTypeId, promoTypeId, categoryName);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return (new ExecuteDashboardVO(query.getResultList(), totalCount));
    }

    public Long getTeamMemberTransPromoDtlCount(String searchCriteria, String teamMemberId, String startDate, String endDate, Long eventId, Long markettingTypeId, Long promoTypeId, String categoryName) {
        Query query = null;
        System.out.println("-------- Search Criteria : " + searchCriteria);
        if (searchCriteria.equalsIgnoreCase("CATEGORY_DATE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                    + " INNER JOIN mst_promo mp ON mp.promo_id=t.promo_id "
                    + " where t.status_id IN (27,29) AND t.executive_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mp.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
        } else if (searchCriteria.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                    + " INNER JOIN mst_promo mp ON mp.promo_id=t.promo_id "
                    + " where t.status_id IN (27,29) AND t.executive_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
        } else if (searchCriteria.equalsIgnoreCase("DATE_EVENT")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND t.updated_date >=? AND t.updated_date<=? AND m.event_id=? ");
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, eventId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_MARKETING_TYPE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.executive_id=? AND t.status_Id IN (27,29) AND t.updated_date >=? AND t.updated_date<=? AND m.mktg_id=? ");
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, markettingTypeId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_PROMOTION_TPYE")) {
            query = em.createNativeQuery("select count(DISTINCT trans_promo_id) from trans_promo  where executive_id=? AND status_id in (27,29) AND updated_date >=? AND updated_date<=? AND  promo_type_id=? ");
            query.setParameter(1, teamMemberId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, promoTypeId);
        }
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getViewPromoDetail(String searchCriteria, Long empId, String startDate, String endDate, Long eventId, Long markettingTypeId, Long promoTypeId, String subCategoryName, Long statusId, int startIndex) {
        Query query = null;

        System.out.println("-------- Search Criteria : " + searchCriteria);
        if (searchCriteria.equalsIgnoreCase("DATE_EVENT")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.updated_date >=? AND t.updated_date<=? AND m.event_id=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, eventId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_MARKETING_TYPE")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=?  AND t.updated_date >=? AND t.updated_date<=? AND m.mktg_id=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, markettingTypeId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_PROMOTION_TPYE")) {
            query = em.createNativeQuery("select * from trans_promo  where created_by=? AND  updated_date >=? AND updated_date<=? AND  promo_type_id=? order by trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, promoTypeId);
        } else if (searchCriteria.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
//            query = em.createNativeQuery("select * from trans_promo  where created_by=? AND  updated_date >=? AND updated_date<=? AND  promo_type_id=? order by trans_promo_id desc", TransPromo.class);
//            query.setParameter(1, empId);
//            query.setParameter(2, startDate);
//            query.setParameter(3, endDate);
//            query.setParameter(4, promoTypeId);

            query = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.created_by=? AND t.updated_date>=? AND t.updated_date<=?  AND mc.sub_category LIKE '%" + subCategoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_EVENT_TYPE")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? AND m.event_id=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
            query.setParameter(5, eventId);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_MARKETTING_TYPE")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? AND m.mktg_id=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
            query.setParameter(5, markettingTypeId);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_PROMO_TYPE")) {
            query = em.createNativeQuery("select t.* from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=? AND  t.promo_type_id=?  AND t.updated_date >=? AND t.updated_date<=? order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, promoTypeId);
            query.setParameter(4, startDate);
            query.setParameter(5, endDate);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_SUB_CATEGORY")) {
            query = em.createNativeQuery("select t.* from trans_promo t "
                    + "INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + "where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? AND mc.sub_category LIKE '%" + subCategoryName + "%' order by t.trans_promo_id desc", TransPromo.class);
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
        }


        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getViewPromoDetailCount(String searchCriteria, Long empId, String startDate, String endDate, Long eventId, Long markettingTypeId, Long promoTypeId, String subCategoryName, Long statusId, int startIndex) {
        Query query = null;

        System.out.println("-------- Search Criteria : " + searchCriteria);
        if (searchCriteria.equalsIgnoreCase("DATE_EVENT")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.updated_date >=? AND t.updated_date<=? AND m.event_id=? ");
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, eventId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_MARKETING_TYPE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=?  AND t.updated_date >=? AND t.updated_date<=? AND m.mktg_id=? ");
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, markettingTypeId);
        } else if (searchCriteria.equalsIgnoreCase("DATE_PROMOTION_TPYE")) {
            query = em.createNativeQuery("select count(DISTINCT trans_promo_id) from trans_promo  where created_by=? AND  updated_date >=? AND updated_date<=? AND  promo_type_id=? ");
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
            query.setParameter(4, promoTypeId);
        } else if (searchCriteria.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + " where t.created_by=? AND t.status_id IN (12,13) AND t.updated_date>=? AND t.updated_date<=? AND tm.status_id IN (12,13)  AND mc.sub_category LIKE '%" + subCategoryName + "%'");
            query.setParameter(1, empId);
            query.setParameter(2, startDate);
            query.setParameter(3, endDate);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? ");
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_EVENT_TYPE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? AND m.event_id=? ");
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
            query.setParameter(5, eventId);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_MARKETTING_TYPE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? AND m.mktg_id=? ");
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
            query.setParameter(5, markettingTypeId);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_PROMO_TYPE")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id where t.created_by=? AND t.status_id=? AND  t.promo_type_id=?  AND t.updated_date >=? AND t.updated_date<=? ");
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, promoTypeId);
            query.setParameter(4, startDate);
            query.setParameter(5, endDate);
        } else if (searchCriteria.equalsIgnoreCase("STATUS_DATE_SUB_CATEGORY")) {
            query = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                    + "INNER JOIN  mst_promo m ON  t.promo_id=m.promo_id "
                    + " INNER JOIN mst_promo mc on mc.promo_id=t.promo_id "
                    + "where t.created_by=? AND t.status_id=?  AND t.updated_date >=? AND t.updated_date<=? AND mc.sub_category LIKE '%" + subCategoryName + "%' ");
            query.setParameter(1, empId);
            query.setParameter(2, statusId);
            query.setParameter(3, startDate);
            query.setParameter(4, endDate);
        }

        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> getPromoMangerViewPromoDetail(String searchType, String categoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, boolean isHO, String zoneId, int startIndex) {
        Query q = null;
        System.out.println("--------------------- Category : " + categoryName);
        String isHoZone = "AND t.is_ho=";
        if (isHO) {
            isHoZone += "1";
            if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=?  AND  mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=? AND mp.event_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
                q.setParameter(3, eventTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=? AND mp.mktg_id=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
                q.setParameter(3, markettingTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, promoTypeID);
                q.setParameter(2, startDate);
                q.setParameter(3, endDate);
            }
        } else {
            isHoZone += "0";
            /*
            select tp.* from trans_promo tp "
            + " left join map_promo_zone mz on mz.map_promo_id=tp.promo_id "
            + " where tp.is_ho=0 and "
            + " (tp.zone_id=? OR mz.zone_id=?) "
            + " and tp.status_id in (14,17,25,27,32) order by tp.trans_promo_id desc
             */
            if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
                /* q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=? AND m.event_id=? AND mc.category_name=? order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
                q.setParameter(3, eventTypeID);
                q.setParameter(4, categoryName);
                 */
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.event_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
                q.setParameter(5, eventTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
                /*q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + " INNER JOIN mst_promo m ON  t.promo_id=m.promo_id "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=? AND m.mktg_id=?  AND mc.category_name=? order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
                q.setParameter(3, markettingTypeID);
                q.setParameter(4, categoryName);
                 */
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.mktg_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
                q.setParameter(5, markettingTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
                /*q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                + " INNER JOIN trans_promo_mc tm  ON t.trans_promo_id=tm.trans_promo_id  "
                + " INNER JOIN mch mc on tm.mc_code=mc.mc_code "
                + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mc.category_name=? order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, promoTypeID);
                q.setParameter(2, startDate);
                q.setParameter(3, endDate);
                q.setParameter(4, categoryName);
                 */
                q = em.createNativeQuery("select DISTINCT t.* from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone
                        + " AND t.promo_type_id=?"
                        + " AND (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.mktg_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc", TransPromo.class);
                q.setParameter(1, promoTypeID);
                q.setParameter(2, zoneId);
                q.setParameter(3, zoneId);
                q.setParameter(4, startDate);
                q.setParameter(5, endDate);

            }
        }


        q.setFirstResult(startIndex);
        q.setMaxResults(MAX_PAGE_RESULT);
        return q.getResultList();
    }

    public Long getPromoMangerViewPromoDetailCount(String searchType, String categoryName, String startDate, String endDate, Long eventTypeID, Long markettingTypeID, Long promoTypeID, boolean isHO, String zoneId) {
        Query q = null;
        String isHoZone = "AND t.is_ho=";
        if (isHO) {
            isHoZone += "1";
            if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=?  AND  mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=? AND mp.event_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
                q.setParameter(3, eventTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.updated_date>=? AND t.updated_date<=? AND mp.mktg_id=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, startDate);
                q.setParameter(2, endDate);
                q.setParameter(3, markettingTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND t.promo_type_id=? AND t.updated_date>=? AND t.updated_date<=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, promoTypeID);
                q.setParameter(2, startDate);
                q.setParameter(3, endDate);
            }
        } else {
            isHoZone += "0";

            if (searchType.equalsIgnoreCase("CATEGORY_DATE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_EVENT_TYPE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.event_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
                q.setParameter(5, eventTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_MARKETTING_TYPE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone + " AND "
                        + " (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.mktg_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, zoneId);
                q.setParameter(2, zoneId);
                q.setParameter(3, startDate);
                q.setParameter(4, endDate);
                q.setParameter(5, markettingTypeID);
            } else if (searchType.equalsIgnoreCase("SUB_CATEGORY_DATE_PROMO_TYPE")) {
                q = em.createNativeQuery("select count(DISTINCT t.trans_promo_id) from trans_promo t "
                        + " INNER JOIN mst_promo mp  ON mp.promo_id=t.promo_id "
                        + " left join map_promo_zone mz on mz.map_promo_id=t.promo_id "
                        + " where t.status_id IN (14,17,25,27,32) " + isHoZone
                        + " AND t.promo_type_id=?"
                        + " AND (t.zone_id=? OR mz.zone_id=?) "
                        + " AND t.updated_date>=? AND t.updated_date<=?  AND mp.mktg_id=? AND mp.sub_category LIKE '%" + categoryName + "%' order by  t.trans_promo_id desc");
                q.setParameter(1, promoTypeID);
                q.setParameter(2, zoneId);
                q.setParameter(3, zoneId);
                q.setParameter(4, startDate);
                q.setParameter(5, endDate);

            }
        }
        return new Long(q.getSingleResult().toString());
    }
}
