/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.facade;

import com.fks.promo.entity.TransPromoFile;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author krutij
 */
@Stateless
public class TransPromoFileFacade extends AbstractFacade<TransPromoFile> {
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TransPromoFileFacade() {
        super(TransPromoFile.class);
    }

}
