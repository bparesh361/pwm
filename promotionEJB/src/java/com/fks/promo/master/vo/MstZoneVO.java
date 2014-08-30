/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;



/**
 *
 * @author Paresb
 */
public class MstZoneVO  {
    
    private Long id;
    private String zonename;
    private String zonecode;
    private Short isBlocked;

    public MstZoneVO() {
    }

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }    

    public MstZoneVO(Long id, String zonename, String zonecode) {
        this.id = id;
        this.zonename = zonename;
        this.zonecode = zonecode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZonecode() {
        return zonecode;
    }

    public void setZonecode(String zonecode) {
        this.zonecode = zonecode;
    }

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }        
    
}
