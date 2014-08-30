/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promo.master.vo;
import com.fks.promo.common.Resp;

/**
 *
 * @author krutij
 */
public class GetUserInfoResp {
    private Resp resp;
    private EmployeeVo employeeVo;

    public GetUserInfoResp() {
    }
    

    public GetUserInfoResp(Resp resp, EmployeeVo employeeVo) {
        this.resp = resp;
        this.employeeVo = employeeVo;
    }

    public GetUserInfoResp(Resp resp) {
        this.resp = resp;
    }

    
    public EmployeeVo getEmployeeVo() {
        return employeeVo;
    }

    public void setEmployeeVo(EmployeeVo employeeVo) {
        this.employeeVo = employeeVo;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

}
