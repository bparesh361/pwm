/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.MstArticleSearch;
import com.fks.promo.entity.MstEmployee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author krutij
 */
@Stateless
public class CommonArticleDAO {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public static final Integer MAX_PAGE_RESULT = 30;
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    public void refresh(Object obj) {
        em.refresh(obj);
    }

    public void merge(Object obj) {
        em.merge(obj);
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public List<MstArticleSearch> getArticleSearchDtlBasedOnEmp(Long empID, int startIndex) {
        Query query = em.createNativeQuery("select * from mst_article_search where created_by=? order by search_date_time desc", MstArticleSearch.class);
        query.setParameter(1, empID);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getArticleSearchDtlBasedOnEmpCount(Long empID) {
        Query query = em.createNativeQuery("select count(*) from mst_article_search where created_by=?");
        query.setParameter(1, empID);
        return new Long(query.getSingleResult().toString());
    }
}
