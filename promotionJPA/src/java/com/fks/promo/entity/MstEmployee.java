/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ajitn
 */
@Entity
@Table(name = "mst_employee")
@NamedQueries({
    @NamedQuery(name = "MstEmployee.findAll", query = "SELECT m FROM MstEmployee m"),
    @NamedQuery(name = "MstEmployee.findByEmpId", query = "SELECT m FROM MstEmployee m WHERE m.empId = :empId"),
    @NamedQuery(name = "MstEmployee.findByEmployeeName", query = "SELECT m FROM MstEmployee m WHERE m.employeeName = :employeeName"),
    @NamedQuery(name = "MstEmployee.findByContactNo", query = "SELECT m FROM MstEmployee m WHERE m.contactNo = :contactNo"),
    @NamedQuery(name = "MstEmployee.findByReportingPersonName", query = "SELECT m FROM MstEmployee m WHERE m.reportingPersonName = :reportingPersonName"),
    @NamedQuery(name = "MstEmployee.findByTaskManager", query = "SELECT m FROM MstEmployee m WHERE m.taskManager = :taskManager"),
    @NamedQuery(name = "MstEmployee.findByEmailId", query = "SELECT m FROM MstEmployee m WHERE m.emailId = :emailId"),
    @NamedQuery(name = "MstEmployee.findByEmpPassword", query = "SELECT m FROM MstEmployee m WHERE m.empPassword = :empPassword"),
    @NamedQuery(name = "MstEmployee.findByUserId", query = "SELECT m FROM MstEmployee m WHERE m.userId = :userId"),
    @NamedQuery(name = "MstEmployee.findByEmpCode", query = "SELECT m FROM MstEmployee m WHERE m.empCode = :empCode"),
    @NamedQuery(name = "MstEmployee.findByIsBlocked", query = "SELECT m FROM MstEmployee m WHERE m.isBlocked = :isBlocked"),
    @NamedQuery(name = "MstEmployee.findByIsF6", query = "SELECT m FROM MstEmployee m WHERE m.isF6 = :isF6")})
public class MstEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_id")
    private Long empId;
    @Column(name = "store_desc")
    private String storeDesc;
    @Basic(optional = false)
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "contact_no")
    private BigInteger contactNo;
    @Column(name = "reporting_person_name")
    private String reportingPersonName;
    @Column(name = "task_manager")
    private String taskManager;
    @Column(name = "email_id")
    private String emailId;
    @Basic(optional = false)
    @Column(name = "emp_password")
    private String empPassword;
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "emp_code")
    private BigInteger empCode;
    @Column(name = "isBlocked")
    private Boolean isBlocked;
    @Column(name = "is_F6")
    private Boolean isF6;
    @Column(name = "is_password_change")
    private Boolean isPasswordChange;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<MstPromo> mstPromoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee1")
    private Collection<MstPromo> mstPromoCollection1;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<TransPromoArticle> transPromoArticleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee")
    private Collection<MapUserDepartment> mapUserDepartmentCollection;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<Mch> mchCollection;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<MstStore> mstStoreCollection;
    @OneToMany(mappedBy = "updatedBy")
    private Collection<TransTask> transTaskCollection;
    @OneToMany(mappedBy = "createdBy")
    private Collection<TransTask> transTaskCollection1;
    @OneToMany(mappedBy = "assignedTo")
    private Collection<TransTask> transTaskCollection2;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<TransPromo> transPromoCollection;
    @OneToMany(mappedBy = "mstEmployee1")
    private Collection<TransPromo> transPromoCollection1;
    @OneToMany(mappedBy = "mstEmployee2")
    private Collection<TransPromo> transPromoCollection2;
    @OneToMany(mappedBy = "mstEmployee3")
    private Collection<TransPromo> transPromoCollection3;
    @OneToMany(mappedBy = "mstEmployee4")
    private Collection<TransPromo> transPromoCollection4;
    @OneToMany(mappedBy = "mstEmployee5")
    private Collection<TransPromo> transPromoCollection5;
    @OneToMany(mappedBy = "mstEmployee6")
    private Collection<TransPromo> transPromoCollection6;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<TransPromoStatus> transPromoStatusCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee")
    private Collection<MapuserMCHF5> mapuserMCHF5Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee")
    private Collection<MstProposal> mstProposalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee")
    private Collection<MapuserMCHF2> mapuserMCHF2Collection;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<TransPromoMc> transPromoMcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee")
    private Collection<MapuserMCHF3> mapuserMCHF3Collection;
    @JoinColumn(name = "mst_store_id", referencedColumnName = "mst_store_id")
    @ManyToOne
    private MstStore mstStore;
    @JoinColumn(name = "mst_role_id", referencedColumnName = "mst_role_id")
    @ManyToOne
    private MstRole mstRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployee")
    private Collection<MapuserMCHF1> mapuserMCHF1Collection;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<MstRole> mstRoleCollection;
    @OneToMany(mappedBy = "mstEmployee1")
    private Collection<MstRole> mstRoleCollection1;
    @OneToMany(mappedBy = "mstEmployee")
    private Collection<MstArticleSearch> mstArticleSearchCollection;

    public MstEmployee() {
    }

    public MstEmployee(Long empId) {
        this.empId = empId;
    }

    public MstEmployee(Long empId, String employeeName, String empPassword, String userId) {
        this.empId = empId;
        this.employeeName = employeeName;
        this.empPassword = empPassword;
        this.userId = userId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public BigInteger getContactNo() {
        return contactNo;
    }

    public void setContactNo(BigInteger contactNo) {
        this.contactNo = contactNo;
    }

    public String getReportingPersonName() {
        return reportingPersonName;
    }

    public void setReportingPersonName(String reportingPersonName) {
        this.reportingPersonName = reportingPersonName;
    }

    public String getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(String taskManager) {
        this.taskManager = taskManager;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigInteger getEmpCode() {
        return empCode;
    }

    public void setEmpCode(BigInteger empCode) {
        this.empCode = empCode;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean getIsF6() {
        return isF6;
    }

    public void setIsF6(Boolean isF6) {
        this.isF6 = isF6;
    }

    public Boolean getIsPasswordChange() {
        return isPasswordChange;
    }

    public void setIsPasswordChange(Boolean isPasswordChange) {
        this.isPasswordChange = isPasswordChange;
    }

    public Collection<MstPromo> getMstPromoCollection() {
        return mstPromoCollection;
    }

    public void setMstPromoCollection(Collection<MstPromo> mstPromoCollection) {
        this.mstPromoCollection = mstPromoCollection;
    }

    public Collection<MstPromo> getMstPromoCollection1() {
        return mstPromoCollection1;
    }

    public void setMstPromoCollection1(Collection<MstPromo> mstPromoCollection1) {
        this.mstPromoCollection1 = mstPromoCollection1;
    }

    public Collection<TransPromoArticle> getTransPromoArticleCollection() {
        return transPromoArticleCollection;
    }

    public void setTransPromoArticleCollection(Collection<TransPromoArticle> transPromoArticleCollection) {
        this.transPromoArticleCollection = transPromoArticleCollection;
    }

    public Collection<MapUserDepartment> getMapUserDepartmentCollection() {
        return mapUserDepartmentCollection;
    }

    public void setMapUserDepartmentCollection(Collection<MapUserDepartment> mapUserDepartmentCollection) {
        this.mapUserDepartmentCollection = mapUserDepartmentCollection;
    }

    public Collection<Mch> getMchCollection() {
        return mchCollection;
    }

    public void setMchCollection(Collection<Mch> mchCollection) {
        this.mchCollection = mchCollection;
    }

    public Collection<MstStore> getMstStoreCollection() {
        return mstStoreCollection;
    }

    public void setMstStoreCollection(Collection<MstStore> mstStoreCollection) {
        this.mstStoreCollection = mstStoreCollection;
    }

    public Collection<TransTask> getTransTaskCollection() {
        return transTaskCollection;
    }

    public void setTransTaskCollection(Collection<TransTask> transTaskCollection) {
        this.transTaskCollection = transTaskCollection;
    }

    public Collection<TransTask> getTransTaskCollection1() {
        return transTaskCollection1;
    }

    public void setTransTaskCollection1(Collection<TransTask> transTaskCollection1) {
        this.transTaskCollection1 = transTaskCollection1;
    }

    public Collection<TransTask> getTransTaskCollection2() {
        return transTaskCollection2;
    }

    public void setTransTaskCollection2(Collection<TransTask> transTaskCollection2) {
        this.transTaskCollection2 = transTaskCollection2;
    }

    public Collection<TransPromo> getTransPromoCollection() {
        return transPromoCollection;
    }

    public void setTransPromoCollection(Collection<TransPromo> transPromoCollection) {
        this.transPromoCollection = transPromoCollection;
    }

    public Collection<TransPromo> getTransPromoCollection1() {
        return transPromoCollection1;
    }

    public void setTransPromoCollection1(Collection<TransPromo> transPromoCollection1) {
        this.transPromoCollection1 = transPromoCollection1;
    }

    public Collection<TransPromo> getTransPromoCollection2() {
        return transPromoCollection2;
    }

    public void setTransPromoCollection2(Collection<TransPromo> transPromoCollection2) {
        this.transPromoCollection2 = transPromoCollection2;
    }

    public Collection<TransPromo> getTransPromoCollection3() {
        return transPromoCollection3;
    }

    public void setTransPromoCollection3(Collection<TransPromo> transPromoCollection3) {
        this.transPromoCollection3 = transPromoCollection3;
    }

    public Collection<TransPromo> getTransPromoCollection4() {
        return transPromoCollection4;
    }

    public void setTransPromoCollection4(Collection<TransPromo> transPromoCollection4) {
        this.transPromoCollection4 = transPromoCollection4;
    }

    public Collection<TransPromo> getTransPromoCollection5() {
        return transPromoCollection5;
    }

    public void setTransPromoCollection5(Collection<TransPromo> transPromoCollection5) {
        this.transPromoCollection5 = transPromoCollection5;
    }

    public Collection<TransPromo> getTransPromoCollection6() {
        return transPromoCollection6;
    }

    public void setTransPromoCollection6(Collection<TransPromo> transPromoCollection6) {
        this.transPromoCollection6 = transPromoCollection6;
    }

    public Collection<TransPromoStatus> getTransPromoStatusCollection() {
        return transPromoStatusCollection;
    }

    public void setTransPromoStatusCollection(Collection<TransPromoStatus> transPromoStatusCollection) {
        this.transPromoStatusCollection = transPromoStatusCollection;
    }

    public Collection<MapuserMCHF5> getMapuserMCHF5Collection() {
        return mapuserMCHF5Collection;
    }

    public void setMapuserMCHF5Collection(Collection<MapuserMCHF5> mapuserMCHF5Collection) {
        this.mapuserMCHF5Collection = mapuserMCHF5Collection;
    }

    public Collection<MstProposal> getMstProposalCollection() {
        return mstProposalCollection;
    }

    public void setMstProposalCollection(Collection<MstProposal> mstProposalCollection) {
        this.mstProposalCollection = mstProposalCollection;
    }

    public Collection<MapuserMCHF2> getMapuserMCHF2Collection() {
        return mapuserMCHF2Collection;
    }

    public void setMapuserMCHF2Collection(Collection<MapuserMCHF2> mapuserMCHF2Collection) {
        this.mapuserMCHF2Collection = mapuserMCHF2Collection;
    }

    public Collection<TransPromoMc> getTransPromoMcCollection() {
        return transPromoMcCollection;
    }

    public void setTransPromoMcCollection(Collection<TransPromoMc> transPromoMcCollection) {
        this.transPromoMcCollection = transPromoMcCollection;
    }

    public Collection<MapuserMCHF3> getMapuserMCHF3Collection() {
        return mapuserMCHF3Collection;
    }

    public void setMapuserMCHF3Collection(Collection<MapuserMCHF3> mapuserMCHF3Collection) {
        this.mapuserMCHF3Collection = mapuserMCHF3Collection;
    }

    public MstStore getMstStore() {
        return mstStore;
    }

    public void setMstStore(MstStore mstStore) {
        this.mstStore = mstStore;
    }

    public MstRole getMstRole() {
        return mstRole;
    }

    public void setMstRole(MstRole mstRole) {
        this.mstRole = mstRole;
    }

    public Collection<MapuserMCHF1> getMapuserMCHF1Collection() {
        return mapuserMCHF1Collection;
    }

    public void setMapuserMCHF1Collection(Collection<MapuserMCHF1> mapuserMCHF1Collection) {
        this.mapuserMCHF1Collection = mapuserMCHF1Collection;
    }

    public Collection<MstRole> getMstRoleCollection() {
        return mstRoleCollection;
    }

    public void setMstRoleCollection(Collection<MstRole> mstRoleCollection) {
        this.mstRoleCollection = mstRoleCollection;
    }

    public Collection<MstRole> getMstRoleCollection1() {
        return mstRoleCollection1;
    }

    public void setMstRoleCollection1(Collection<MstRole> mstRoleCollection1) {
        this.mstRoleCollection1 = mstRoleCollection1;
    }

    public Collection<MstArticleSearch> getMstArticleSearchCollection() {
        return mstArticleSearchCollection;
    }

    public void setMstArticleSearchCollection(Collection<MstArticleSearch> mstArticleSearchCollection) {
        this.mstArticleSearchCollection = mstArticleSearchCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstEmployee)) {
            return false;
        }
        MstEmployee other = (MstEmployee) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fks.promo.entity.MstEmployee[empId=" + empId + "]";
    }
}
