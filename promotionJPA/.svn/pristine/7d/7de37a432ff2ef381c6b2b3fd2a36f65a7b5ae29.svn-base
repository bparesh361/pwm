/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import java.util.ArrayList;
import java.util.Arrays;
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
public class CommunicationDao {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public static final Integer MAX_PAGE_RESULT = 30;
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    public List<TransPromo> searchTransPromoByValidityDates(String startDate, String endDate, int startIndex) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tp.valid_from >= ? and tp.valid_from <= ? order by tp.promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchTransPromoByValidityDatesCount(String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tp.valid_from >= ? and tp.valid_from <= ?");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchTransPromoByValidityDatesEvent(String startDate, String endDate, int startIndex, long eventTypeId) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tp.valid_from >= ? and tp.valid_from <= ? and mp.event_id=? order by tp.promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, eventTypeId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchTransPromoByValidityDatesEventCount(String startDate, String endDate, long eventTypeId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tp.valid_from >= ? and tp.valid_from <= ? and mp.event_id=? ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, eventTypeId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchTransPromoByValidityDatesEventCategory(String startDate, String endDate, int startIndex, long eventTypeId, String category) {
        Query query = em.createNativeQuery("select tp.* from trans_promo tp inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tp.valid_from >= ? and tp.valid_from <= ? and mp.event_id=? and mp.category=? order by tp.promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, eventTypeId);
        query.setParameter(4, category);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchTransPromoByValidityDatesEventCategoryCount(String startDate, String endDate, long eventTypeId, String category) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tp.valid_from >= ? and tp.valid_from <= ? and mp.event_id=? and mp.category=?  ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, eventTypeId);
        query.setParameter(4, category);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchTransPromoByValidityDatesEventCategoryMC(String startDate, String endDate, int startIndex, long eventTypeId, String category, String McList) {

        //Query query = em.createNativeQuery("select tp.* from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN ("+McList+") and mp.category=? and mp.event_id=? and tp.valid_from >= ? and tp.valid_to <= ?", TransPromo.class);
        Query query = em.createNativeQuery("select tp.* from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN (" + McList + ")  and mp.event_id=? and tp.valid_from >= ? and tp.valid_from <= ?", TransPromo.class);
//        query.setParameter(1, McList);
//        query.setParameter(1, category);
        query.setParameter(1, eventTypeId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchTransPromoByValidityDatesEventCategoryMCCount(String startDate, String endDate, long eventTypeId, String category, String McList) {

//        Query query = em.createNativeQuery("select count(*) from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN ("+McList+") and mp.category=? and mp.event_id=? and tp.valid_from >= ? and tp.valid_to <= ?");
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN (" + McList + ")  and mp.event_id=? and tp.valid_from >= ? and tp.valid_from <= ?");
        //  query.setParameter(1, McList);
//        query.setParameter(1, category);
        query.setParameter(1, eventTypeId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        //  System.out.println("Count : "+query.getSingleResult().toString());
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchTransPromoByValidityDatesEventCategoryMCArticle(String startDate, String endDate, int startIndex, long eventTypeId, String category, String McList, String articleList) {

//        Query query = em.createNativeQuery("select tp.* from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN (" + McList + ") and tpa.article IN (" + articleList + ") and mp.category=? and mp.event_id=? and tp.valid_from >= ? and tp.valid_to <= ?", TransPromo.class);
        Query query = em.createNativeQuery("select tp.* from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN (" + McList + ") and tpa.article IN (" + articleList + ")  and mp.event_id=? and tp.valid_from >= ? and tp.valid_from <= ?", TransPromo.class);
//        query.setParameter(1, McList);
//        query.setParameter(1, category);
        query.setParameter(1, eventTypeId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchTransPromoByValidityDatesEventCategoryMCCountArticle(String startDate, String endDate, long eventTypeId, String category, String McList, String articleList) {

//        Query query = em.createNativeQuery("select count(*) from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN (" + McList + ") and tpa.article IN (" + articleList + ") and mp.category=? and mp.event_id=? and tp.valid_from >= ? and tp.valid_to <= ?");
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo_article tpa inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  inner join mst_promo mp on tp.promo_id=mp.promo_id where tp.status_id=31 and tpa.mc_code IN (" + McList + ") and tpa.article IN (" + articleList + ")  and mp.event_id=? and tp.valid_from >= ? and tp.valid_from <= ?");
        //  query.setParameter(1, McList);
//        query.setParameter(1, category);
        query.setParameter(1, eventTypeId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        //  System.out.println("Count : "+query.getSingleResult().toString());
        return new Long(query.getSingleResult().toString());
    }

    public Long searchTransPromoByZonecount(String zoneid) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo_article tpa "
                + "inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  "
                + "inner join map_promo_zone mz on map_promo_id=tp.promo_id where tp.status_id=31 and mz.zone_id=?");
        query.setParameter(1, zoneid);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchTransPromoByZone(String zoneid, int startIndex) {
        Query query = em.createNativeQuery("select * from trans_promo_article tpa "
                + "inner join trans_promo tp on tpa.trans_promo_id =tp.trans_promo_id  "
                + "inner join map_promo_zone mz on map_promo_id=tp.promo_id where tp.status_id=31 and mz.zone_id=?", TransPromo.class);
        query.setParameter(1, zoneid);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public List<String> getAllCategoryFromPromo() {
        Query query = em.createNativeQuery("select distinct category from mst_promo");
        return query.getResultList();
    }

    public List<String> getAllMchFromArticle(String categoryName) {
//        Query query = em.createNativeQuery("select distinct mc_code from trans_promo_article a,trans_promo t , mst_promo m where a.trans_promo_id= t.trans_promo_id and t.promo_id=m.promo_id and category=?");
        Query query = em.createNativeQuery("select  mc_code from mch where category_name=?");
        query.setParameter(1, categoryName);
        return query.getResultList();
    }

    public List<String> getAllMchBySubCategory(String subCategoryName) {
//        Query query = em.createNativeQuery("select distinct mc_code from trans_promo_article a,trans_promo t , mst_promo m where a.trans_promo_id= t.trans_promo_id and t.promo_id=m.promo_id and category=?");
        Query query = em.createNativeQuery("select distinct  mc_code from mch where sub_category_name=?");
        query.setParameter(1, subCategoryName);
        return query.getResultList();
    }

    public List<String> getAllArticleMcWise(String mcCode) {
        Query query = em.createNativeQuery("select distinct article from trans_promo_article a where mc_code IN (" + mcCode + ") and article <> '-'");
        //query.setParameter(1, mcCode);
        return query.getResultList();
    }

    public List<MstZone> getZoneBasedOnLocationAndZone(String location, String zone) {
        Query query;
        if (location.equalsIgnoreCase("1")) {
            query = em.createNativeQuery("select distinct z.* from mst_zone z inner join mst_store s on s.mst_zone_id=z.zone_id where z.is_blocked=0 and s.mst_location_id <> 1", MstZone.class);
//            query = em.createNativeQuery("select distinct z.* from mst_zone z where is_blocked=0 ", MstZone.class);
        } else {
            query = em.createNativeQuery("select distinct z.* from mst_zone z where is_blocked=0 and  z.zone_id=(" + zone + ") ", MstZone.class);
        }
        //query.setParameter(1, mcCode);
        return query.getResultList();
    }

    public List<TransPromo> searchCommunicationPromo(String zoneid, String startDate, String endDate, int startIndex) {
//        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
//                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ?) and mz.zone_id IN (?) order by trans_promo_id desc", TransPromo.class);
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to))  and mz.zone_id IN (" + zoneid + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        //query.setParameter(5, zoneid);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchCommunicationPromoCount(String zoneid, String startDate, String endDate) {
//        Query query = em.createNativeQuery("select count(*) from trans_promo tp "
//                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ?) and mz.zone_id IN (?) ");
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to)) and mz.zone_id IN (" + zoneid + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        // query.setParameter(5, zoneid);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchCommunicationPromo(String zoneid, String startDate, String endDate, String category, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to)) and mz.zone_id IN (" + zoneid + ") and tc.mc_code IN (" + category + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchCommunicationPromoCount(String zoneid, String startDate, String endDate, String category) {

        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and tc.mc_code IN (" + category + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchCommunicationPromo(String zoneid, String startDate, String endDate, String category, String article, int startIndex) {

        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to)) and mz.zone_id IN (" + zoneid + ") and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchCommunicationPromoCount(String zoneid, String startDate, String endDate, String category, String article) {

        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchCommunicationPromo(String zoneid, String startDate, String endDate, Long eventId, int startIndex) {

        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and mp.event_id=(?) order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, eventId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchCommunicationPromoCount(String zoneid, String startDate, String endDate, Long eventId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and mp.event_id=(?)");

        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchCommunicationPromo(String zoneid, String startDate, String endDate, Long eventId, String category, int startIndex) {

        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and mp.event_id=(?) and tc.mc_code IN (" + category + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, eventId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchCommunicationPromoCount(String zoneid, String startDate, String endDate, Long eventId, String category) {

        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and mp.event_id=(?) and tc.mc_code IN (" + category + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchCommunicationPromo(String zoneid, String startDate, String endDate, Long eventId, String category, String article, int startIndex) {

        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and mp.event_id=(?) and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, eventId);

        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchCommunicationPromoCount(String zoneid, String startDate, String endDate, Long eventId, String category, String article) {

        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_zone mz on mz.map_promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and mz.zone_id IN (" + zoneid + ") and mp.event_id=(?) and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchStoreCommunicationPromo(String storeid, String startDate, String endDate, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to)) and ms.mst_store_id IN (?) order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeid);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchStoreCommunicationPromoCount(String storeid, String startDate, String endDate) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to)) and ms.mst_store_id IN (?) ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeid);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchStoreCommunicationPromo(String storeId, String startDate, String endDate, String category, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and tc.mc_code IN (" + category + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);

        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchStoreCommunicationPromoCount(String storeId, String startDate, String endDate, String category) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and tc.mc_code IN (" + category + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchStoreCommunicationPromo(String storeId, String startDate, String endDate, String category, String article, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchStoreCommunicationPromoCount(String storeId, String startDate, String endDate, String category, String article) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);

        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchStoreCommunicationPromo(String storeId, String startDate, String endDate, Long eventId, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and mp.event_id=(?) order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setParameter(8, eventId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchStoreCommunicationPromoCount(String storeId, String startDate, String endDate, Long eventId) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and mp.event_id=(?)");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setParameter(8, eventId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchStoreCommunicationPromo(String storeId, String startDate, String endDate, Long eventId, String category, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and mp.event_id=(?) and tc.mc_code IN (" + category + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setParameter(8, eventId);

        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchStoreCommunicationPromoCount(String storeId, String startDate, String endDate, Long eventId, String category) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and mp.event_id=(?) and tc.mc_code IN (" + category + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setParameter(8, eventId);

        return new Long(query.getSingleResult().toString());
    }

    public List<TransPromo> searchStoreCommunicationPromo(String storeId, String startDate, String endDate, Long eventId, String category, String article, int startIndex) {
        Query query = em.createNativeQuery("select DISTINCT tp.* from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and mp.event_id=(?) and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") order by trans_promo_id desc", TransPromo.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setParameter(8, eventId);

        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long searchStoreCommunicationPromoCount(String storeId, String startDate, String endDate, Long eventId, String category, String article) {
        Query query = em.createNativeQuery("select count(DISTINCT tp.trans_promo_id) from trans_promo tp "
                + " inner join mst_promo mp  on mp.promo_id=tp.promo_id "
                + " inner join trans_promo_mc tc  on tc.trans_promo_id =tp.trans_promo_id "
                + " inner join trans_promo_article ta  on ta.trans_promo_id =tp.trans_promo_id "
                + "inner join map_promo_store ms on ms.promo_id=tp.promo_id where tp.status_id=31 and (tp.valid_from BETWEEN ? and ? or tp.valid_to BETWEEN ? and ? or (? > tp.valid_from and  ? < tp.valid_to) ) and ms.mst_store_id IN (?) and mp.event_id=(?) and tc.mc_code IN (" + category + ") and ta.article IN (" + article + ") ");
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, startDate);
        query.setParameter(4, endDate);
        query.setParameter(5, startDate);
        query.setParameter(6, endDate);
        query.setParameter(7, storeId);
        query.setParameter(8, eventId);

        return new Long(query.getSingleResult().toString());
    }
}
