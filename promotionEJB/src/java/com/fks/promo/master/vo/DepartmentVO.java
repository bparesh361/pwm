/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.master.vo;

/**
 *
 * @author krutij
 */
public class DepartmentVO {
    private Long departmentID;
    private String departmentName;
    private Boolean isActive;

    public DepartmentVO(Long departmentID, String departmentName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }

    public DepartmentVO(Long departmentID, String departmentName, Boolean isActive) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.isActive = isActive;
    }


    public DepartmentVO() {
    }

    
    public Long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Long departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
