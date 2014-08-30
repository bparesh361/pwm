/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.facade;

import com.fks.promo.entity.TransPromoStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ajitn
 */
@Stateless
public class TransPromoStatusFacade extends AbstractFacade<TransPromoStatus> {
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TransPromoStatusFacade() {
        super(TransPromoStatus.class);
    }

}
