/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.common;

/**
 *
 * @author Paresb
 */
public class Resp {

    private RespCode respCode;
    private String msg;
    private Long pk;
    private String value;
    private String suggestedDate;
    private boolean redirect;

    public Resp() {
    }

    public Resp(RespCode respCode, String msg) {
        this.respCode = respCode;
        this.msg = msg;
    }

    public Resp(RespCode respCode, String msg, String suggestedDate) {
        this.respCode = respCode;
        this.msg = msg;
        this.suggestedDate = suggestedDate;
    }

    public Resp(RespCode respCode, String msg, Long pk) {
        this.respCode = respCode;
        this.msg = msg;
        this.pk = pk;
    }

    public Resp(RespCode respCode, String msg, Long pk, String value) {
        this.respCode = respCode;
        this.msg = msg;
        this.pk = pk;
        this.value = value;
    }

    public Resp(RespCode respCode, String msg, Long pk, String value, boolean redirect) {
        this.respCode = respCode;
        this.msg = msg;
        this.pk = pk;
        this.value = value;
        this.redirect = redirect;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(RespCode respCode) {
        this.respCode = respCode;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuggestedDate() {
        return suggestedDate;
    }

    public void setSuggestedDate(String suggestedDate) {
        this.suggestedDate = suggestedDate;
    }
}
