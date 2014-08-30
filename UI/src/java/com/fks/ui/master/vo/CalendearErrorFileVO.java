/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.vo;

/**
 *
 * @author Paresb
 */
public class CalendearErrorFileVO {

    private String date;
    private String desc;
    private String message;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CalendearErrorFileVO(String date, String desc, String message) {
        this.date = date;
        this.desc = desc;
        this.message = message;
    }

    public CalendearErrorFileVO() {
    }

    @Override
    public String toString() {
        return "CalendearErrorFileVO{" + "date=" + date + ", desc=" + desc + ", message=" + message + '}';
    }   
    
}
