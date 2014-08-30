/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

/**
 *
 * @author Paresb
 */
public class MstCalendarVO {
    
    private Long calendarId;
    private String Date;
    private String description;

    public MstCalendarVO(Long calendarId, String Date, String description) {
        this.calendarId = calendarId;
        this.Date = Date;
        this.description = description;
    }

    public MstCalendarVO() {
    }    
    
    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
