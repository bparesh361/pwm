/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.util;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.MapUserDepartment;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstRole;
import com.fks.promo.entity.MstStore;
import com.fks.promo.master.vo.EmployeeVo;
import com.fks.promo.master.vo.StoreVO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.fks.promo.master.util.UserMasterVO;

/**
 *
 * @author krutij
 */
public class EmployeeUtil {

    public static EmployeeVo convertEmployee(MstEmployee employee) {
        EmployeeVo employeeVo = new EmployeeVo();
        StoreVO storeVO = new StoreVO();
        List<Long> deptIdlist = new ArrayList<Long>();
        employeeVo.setContactno(employee.getContactNo().toString());
        employeeVo.setEmailId(employee.getEmailId());
        employeeVo.setEmpCode(employee.getEmpCode().toString());
        employeeVo.setEmpId(employee.getEmpId().toString());
        employeeVo.setEmpName(employee.getEmployeeName());
        employeeVo.setPassword(employee.getEmpPassword());
        employeeVo.setStoreDesc(employee.getStoreDesc());
        boolean isExist = false;
        StringBuilder deptExist = new StringBuilder("");
        if (employee.getMapUserDepartmentCollection() != null && !employee.getMapUserDepartmentCollection().isEmpty()) {
            for (MapUserDepartment mapdept : employee.getMapUserDepartmentCollection()) {
                MstDepartment department = mapdept.getMstDepartment();
                if (department != null || department != null) {
                    deptExist.append(department.getDeptName()).append(",");
                    deptIdlist.add(department.getMstDeptId());
                    isExist = true;
                }
            }
            if (isExist) {
                String deptExistname = deptExist.substring(0, deptExist.lastIndexOf(","));
                employeeVo.setDeptName(deptExistname);
            }
            employeeVo.setDeptIdList(deptIdlist);
        }
        employeeVo.setReportingTo(employee.getReportingPersonName());
        employeeVo.setRoleId(employee.getMstRole().getMstRoleId().toString());
        employeeVo.setRoleName(employee.getMstRole().getRoleName());
        employeeVo.setTaskmanager(employee.getTaskManager());
        employeeVo.setUserID(employee.getUserId());
        storeVO.setCity(employee.getMstStore().getCity());
        storeVO.setFormat(employee.getMstStore().getFormatName());
        storeVO.setLocationName(employee.getMstStore().getMstLocation().getLocationName());
        storeVO.setLocationID(employee.getMstStore().getMstLocation().getLocationId());
        storeVO.setRegion(employee.getMstStore().getRegionName());
        storeVO.setState(employee.getMstStore().getState());
        storeVO.setStoreClass(employee.getMstStore().getStoreClass());
        storeVO.setZoneId(employee.getMstStore().getMstZone().getZoneId());
        storeVO.setZoneName(employee.getMstStore().getMstZone().getZoneName());
        storeVO.setStoreDesc(employee.getMstStore().getSiteDescription());
        storeVO.setStoreID(employee.getMstStore().getMstStoreId());
        employeeVo.setStoreVO(storeVO);
        return employeeVo;
    }

    public static UserMasterVO convertEmployeeTosubmit(EmployeeVo employeeVo, MstRole role, MstStore store, MstEmployee employee) {
        //System.out.println("--- inside updating emp name : "+employeeVo.getEmpName());

        employee.setContactNo(new BigInteger(employeeVo.getContactno()));
        // employee.setEmpId(req.getEmpId());
        employee.setEmployeeName(employeeVo.getEmpName());
        employee.setEmailId(employeeVo.getEmailId());
        if (employeeVo.getReportingTo() != null) {
            employee.setReportingPersonName(employeeVo.getReportingTo());
        }
        if (employeeVo.getTaskmanager() != null) {
            employee.setTaskManager(employeeVo.getTaskmanager());
        }
        employee.setMstRole(role);
        role.getMstEmployeeCollection().add(employee);
        employee.setMstStore(store);
        if (employeeVo.getStoreDesc() != null) {
            employee.setStoreDesc(employeeVo.getStoreDesc());
        } else {
            employee.setStoreDesc(store.getSiteDescription());
        }

        // employee.setEmpPassword(employeeVo.getPassword());
        String randomPass = CommonUtil.randomPasswordGenrates(8);
        employee.setEmpPassword(randomPass);
        employee.setIsPasswordChange(Boolean.TRUE);
        employee.setUserId(employeeVo.getUserID());
        employee.setIsBlocked(Boolean.FALSE);
        employee.setEmpCode(new BigInteger(employeeVo.getEmpCode()));
        boolean isredirect = false;
        Collection<MapRoleProfile> mapRoleProfiles = role.getMapRoleProfileCollection();
        if (mapRoleProfiles != null) {
            for (MapRoleProfile mrp : mapRoleProfiles) {
                if (mrp.getMstProfile().getProfileId().toString().equals("7")) {
                    employee.setIsF6(Boolean.TRUE);
                } else {
                    employee.setIsF6(Boolean.FALSE);
                }
                if (mrp.getMstProfile().getProfileId().toString().equals("2") || mrp.getMstProfile().getProfileId().toString().equals("3") || mrp.getMstProfile().getProfileId().toString().equals("4")) {
                    isredirect = true;
                }
            }
        }
        UserMasterVO uservo = new UserMasterVO();
        uservo.setEmployee(employee);
        uservo.setIsredirect(isredirect);
        return uservo;
    }
}
