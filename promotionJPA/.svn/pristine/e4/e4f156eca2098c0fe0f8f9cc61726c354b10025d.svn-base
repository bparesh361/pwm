/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.facade;

import com.fks.promo.entity.MapPromoFormat;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ajitn
 */
@Stateless
public class MapPromoFormatFacade extends AbstractFacade<MapPromoFormat> {
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MapPromoFormatFacade() {
        super(MapPromoFormat.class);
    }

}
