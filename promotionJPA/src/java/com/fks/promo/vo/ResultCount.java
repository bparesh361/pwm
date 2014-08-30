/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.vo;

import com.fks.promo.entity.TransPromo;
import java.util.List;

/**
 *
 * @author nehabha
 */
public class ResultCount {

    private Long count;
    List<TransPromo> lstTransPromo;

    public ResultCount() {
    }

    public ResultCount(Long count, List<TransPromo> lstTransPromo) {
        this.count = count;
        this.lstTransPromo = lstTransPromo;
    }

    
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<TransPromo> getLstTransPromo() {
        return lstTransPromo;
    }

    public void setLstTransPromo(List<TransPromo> lstTransPromo) {
        this.lstTransPromo = lstTransPromo;
    }

    

}
