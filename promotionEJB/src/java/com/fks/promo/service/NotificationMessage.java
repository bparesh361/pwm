/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promotion.vo.SubmitPromoOrgdtlReq;
import java.io.Serializable;

/**
 *
 * @author krutij
 */
public class NotificationMessage implements Serializable {

    private Long zoneId;
    private Long transPromoId;
    private NotificationType notificationType;
    private String id;
    private String empId;
    private Long transPromoFileId;
    private SubmitPromoOrgdtlReq orgdtlReq;
    private String msg;
    private boolean isEscalated;

    public NotificationMessage() {
    }

    public NotificationMessage(String transPromoId, NotificationType notificationType, boolean isEscalated) {
        this.id = transPromoId;
        this.notificationType = notificationType;
        this.isEscalated = isEscalated;
    }

    public NotificationMessage(NotificationType notificationType, String id, String empId) {
        this.notificationType = notificationType;
        this.id = id;
        this.empId = empId;
    }

    public NotificationMessage(Long transPromoId, NotificationType notificationType) {
        this.transPromoId = transPromoId;
        this.notificationType = notificationType;
    }

    public NotificationMessage(NotificationType notificationType, String id) {
        this.notificationType = notificationType;
        this.id = id;
    }

    public NotificationMessage(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationMessage(NotificationType notificationType, SubmitPromoOrgdtlReq orgdtlReq) {
        this.notificationType = notificationType;
        this.orgdtlReq = orgdtlReq;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTransPromoId() {
        return transPromoId;
    }

    public void setTransPromoId(Long transPromoId) {
        this.transPromoId = transPromoId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getTransPromoFileId() {
        return transPromoFileId;
    }

    public void setTransPromoFileId(Long transPromoFileId) {
        this.transPromoFileId = transPromoFileId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public SubmitPromoOrgdtlReq getOrgdtlReq() {
        return orgdtlReq;
    }

    public void setOrgdtlReq(SubmitPromoOrgdtlReq orgdtlReq) {
        this.orgdtlReq = orgdtlReq;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isIsEscalated() {
        return isEscalated;
    }

    public void setIsEscalated(boolean isEscalated) {
        this.isEscalated = isEscalated;
    }
}
