/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.facade;

import com.fks.promo.entity.TransPromoArticle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ajitn
 */
@Stateless
public class TransPromoArticleFacade extends AbstractFacade<TransPromoArticle> {
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TransPromoArticleFacade() {
        super(TransPromoArticle.class);
    }

}
