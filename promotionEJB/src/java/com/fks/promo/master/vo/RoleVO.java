/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

import java.util.Date;

/**
 *
 * @author krutij
 */
public class RoleVO {

    private Long roleId;
    private String roleName;
    private Short isBlocked;
    private String createdDate;
    private String CreatedBy;
    private String updatedDate;
    private String updatedBy;
    private int OperationCode;


    public RoleVO() {
    }

    public RoleVO(Long roleId, String roleName, Short isBlocked, String createdDate, String CreatedBy, String updatedDate, String updatedBy, int OperationCode) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isBlocked = isBlocked;
        this.createdDate = createdDate;
        this.CreatedBy = CreatedBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.OperationCode = OperationCode;
    }

   

    public int getOperationCode() {
        return OperationCode;
    }

    public void setOperationCode(int OperationCode) {
        this.OperationCode = OperationCode;
    }

    
    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }
 

    public Short getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Short isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
 
}
