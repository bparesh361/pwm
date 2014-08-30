/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.facade;

import com.fks.promo.entity.MapuserMCHF5;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author krutij
 */
@Stateless
public class MapuserMCHF5Facade extends AbstractFacade<MapuserMCHF5> {
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MapuserMCHF5Facade() {
        super(MapuserMCHF5.class);
    }

}
