/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promotion.service.util;

import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoStatus;
import java.util.Date;

/**
 *
 * @author krutij
 */

public class StatusUpdateUtil { 

    public  static TransPromoStatus submitTransPromoStatus(MstEmployee employee, MstStatus cuurentStatus,MstStatus previousStatus,String remarks,TransPromo transPromo){
        System.out.println("=== submitTransPromoStatus Service ========");
        TransPromoStatus promoStatus= new TransPromoStatus();
        if(employee!=null){
            promoStatus.setMstEmployee(employee);
        }
        promoStatus.setMstStatus(cuurentStatus);
        promoStatus.setMstStatus1(previousStatus);
        promoStatus.setRemarks(remarks);
        promoStatus.setTransPromo(transPromo);
        promoStatus.setUpdatedDate(new Date());
        return promoStatus;
    }
}
