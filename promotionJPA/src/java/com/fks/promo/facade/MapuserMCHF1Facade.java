/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.facade;

import com.fks.promo.entity.MapuserMCHF1;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ajitn
 */
@Stateless
public class MapuserMCHF1Facade extends AbstractFacade<MapuserMCHF1> {
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MapuserMCHF1Facade() {
        super(MapuserMCHF1.class);
    }

}
