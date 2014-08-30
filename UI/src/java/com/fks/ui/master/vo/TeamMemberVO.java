/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.master.vo;

/**
 *
 * @author ajitn
 */
public class TeamMemberVO {

    private String empID;
    private String empName;

    public TeamMemberVO(String empID, String empName) {
        this.empID = empID;
        this.empName = empName;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
