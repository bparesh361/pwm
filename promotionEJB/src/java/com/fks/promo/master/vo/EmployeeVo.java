/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.vo;

import java.util.List;

/**
 *
 * @author krutij
 */
public class EmployeeVo {

    private String EmpId;
    private String EmpName;
    private String storeId;
    private String StoreDesc;
    private String RoleId;
    private String RoleName;
    private String contactno;
    private String emailId;
    private String reportingTo;
    private String taskmanager;
    private String UserID;
    private String password;
    private List<Long> deptIdList;
    private String empCode;
    // private EmployeeSearchEnum employeeSearchEnum;
    private String deptName;
    private StoreVO storeVO;
    private Boolean isBlocked;
    private Boolean isPasswordChanged;
    private String profileName;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String EmpId) {
        this.EmpId = EmpId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String RoleId) {
        this.RoleId = RoleId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public String getStoreDesc() {
        return StoreDesc;
    }

    public void setStoreDesc(String StoreDesc) {
        this.StoreDesc = StoreDesc;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }

    public String getTaskmanager() {
        return taskmanager;
    }

    public void setTaskmanager(String taskmanager) {
        this.taskmanager = taskmanager;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

//    public EmployeeSearchEnum getEmployeeSearchEnum() {
//        return employeeSearchEnum;
//    }
//
//    public void setEmployeeSearchEnum(EmployeeSearchEnum employeeSearchEnum) {
//        this.employeeSearchEnum = employeeSearchEnum;
//    }
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public StoreVO getStoreVO() {
        return storeVO;
    }

    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean getIsPasswordChanged() {
        return isPasswordChanged;
    }

    public void setIsPasswordChanged(Boolean isPasswordChanged) {
        this.isPasswordChanged = isPasswordChanged;
    }
}
