/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.vo;

/**
 *
 * @author ajitn
 */
public class PromoCloseVO {

    private String line;
    private String msg;

    public PromoCloseVO() {
    }

    public PromoCloseVO(String line, String msg) {
        this.line = line;
        this.msg = msg;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
