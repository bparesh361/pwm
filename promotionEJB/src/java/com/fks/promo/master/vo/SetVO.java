/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

/**
 *
 * @author krutij
 */
public class SetVO {

    private Long setID;
    private String setDesc;

    public SetVO() {
    }

    public SetVO(Long setID, String setDesc) {
        this.setID = setID;
        this.setDesc = setDesc;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }

    public Long getSetID() {
        return setID;
    }

    public void setSetID(Long setID) {
        this.setID = setID;
    }
}
