/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.MapModuleProfile;
import com.fks.promo.entity.MapPromoMch;
import com.fks.promo.entity.MapRoleLocation;
import com.fks.promo.entity.MapRoleProfile;
import com.fks.promo.entity.MapUserDepartment;
import com.fks.promo.entity.MapuserMCHF1;
import com.fks.promo.entity.MapuserMCHF2;
import com.fks.promo.entity.MapuserMCHF3;
import com.fks.promo.entity.MapuserMCHF5;

import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstCalender;
import com.fks.promo.entity.MstCampaign;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstEvent;
import com.fks.promo.entity.MstLeadTime;
import com.fks.promo.entity.MstLocation;
import com.fks.promo.entity.MstMktg;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstReasonRejection;
import com.fks.promo.entity.MstRole;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstStore;
import com.fks.promo.entity.MstTask;
import com.fks.promo.entity.MstZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransTask;
import com.fks.promo.vo.TeamMemberVO;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author krutij
 */
@Stateless
public class CommonDAO {

    public static final Integer MAX_PAGE_RESULT = 30;
    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    public void refresh(Object obj) {
        em.refresh(obj);
    }

    public void merge(Object obj) {
        em.merge(obj);
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public MstRole getRoleByID(Long RoleID) {
        Query qry = em.createNamedQuery("MstRole.findByMstRoleId", MstRole.class);
        qry.setParameter("mstRoleId", RoleID);
        List<MstRole> mapList = qry.getResultList();
        if (mapList != null && mapList.size() > 0) {
            return (mapList.get(0));
        } else {
            return null;
        }
    }

    public MstPromo getMstPromoByID(Long id) {
        Query qry = em.createNativeQuery("select * from mst_promo where promo_id=?", MstPromo.class);
        qry.setParameter(1, id);
        List<MstPromo> mapList = qry.getResultList();
        if (mapList != null && mapList.size() > 0) {
            return (mapList.get(0));
        } else {
            return null;
        }
    }

    public List<MstEmployee> getEmployeeByEmpCode(String EmpCode) {
        Query qry = em.createNativeQuery("select * from mst_employee where emp_code =? ", MstEmployee.class);
        //  qry.setParameter("userId", EmpCode);
        qry.setParameter(1, EmpCode);

        return qry.getResultList();
    }

    public List<MstEmployee> getEmployeeByEmailId(String emailId) {
        Query qry = em.createNativeQuery("select * from mst_employee where email_id =?", MstEmployee.class);
        qry.setParameter(1, emailId);
        return qry.getResultList();
    }

    public List<MstEmployee> getEmployeeBycontactNum(String contactno) {
        Query qry = em.createNativeQuery("select * from mst_employee where contact_no =? ", MstEmployee.class);
        qry.setParameter(1, contactno);
        return qry.getResultList();
    }

    public List<MstEmployee> getAllEmployeeDetail(int startIndex) {
        Query qry = em.createNativeQuery("select * from mst_employee", MstEmployee.class);
        qry.setFirstResult(startIndex);
        qry.setMaxResults(MAX_PAGE_RESULT);
        return qry.getResultList();
    }

    public int getAllEmployeeCount() {
        Query qry = em.createNativeQuery("select * from mst_employee", MstEmployee.class);
        return qry.getResultList().size();
    }

    public List<MstEmployee> getEmployeeByEmpId(String EmpID) {
        Query qry = em.createNativeQuery("select * from mst_employee where emp_id=?", MstEmployee.class);
        qry.setParameter(1, EmpID);
//        qry.setFirstResult(startIdex);
//        qry.setMaxResults(MAX_PAGE_RESULT);
        return qry.getResultList();
    }

    public List<MstEmployee> getEmployeeByStoreCode(String storeCode, int startIdex) {
        Query qry = em.createNativeQuery("select * from mst_employee as e, mst_store as s where e.mst_store_id =s.mst_store_id and e.mst_store_id=?", MstEmployee.class);
        qry.setParameter(1, storeCode);
        qry.setFirstResult(startIdex);
        qry.setMaxResults(MAX_PAGE_RESULT);
        return qry.getResultList();
    }

    public List<MstEmployee> getEmployeeByZoneLocation(String storeCode, int startIdex) {
        Query qry = em.createNativeQuery("select * from mst_employee where mst_store_id IN (" + storeCode + ")", MstEmployee.class);
        // qry.setParameter(1, storeCode);
        qry.setFirstResult(startIdex);
        qry.setMaxResults(MAX_PAGE_RESULT);
        return qry.getResultList();
    }

    public int getEmployeeByZoneLocationCount(String storeCode) {
        Query query = em.createNativeQuery("select * from mst_employee where mst_store_id IN (" + storeCode + ")", MstEmployee.class);
        // query.setParameter(1, storeCode);
        return query.getResultList().size();
    }

    public int getEmployeeByStoreCount(String storeCode) {
        Query query = em.createNativeQuery("select * from mst_employee as e, mst_store as s where e.mst_store_id =s.mst_store_id and e.mst_store_id=?", MstEmployee.class);
        query.setParameter(1, storeCode);
        return query.getResultList().size();
    }

    public Mch getMCHByCode(String mcCode) {
        Query qry = em.createNamedQuery("Mch.findByMcCode", Mch.class);
        qry.setParameter("mcCode", mcCode);
        List<Mch> mapList = qry.getResultList();
        if (mapList != null && mapList.size() > 0) {
            return (mapList.get(0));
        } else {
            return null;
        }
    }

    public MstStore getStoreByCode(String storeCode) {
        Query qry = em.createNamedQuery("MstStore.findByMstStoreId", MstStore.class);
        qry.setParameter("mstStoreId", storeCode);
        List<MstStore> mapList = qry.getResultList();
        if (mapList != null && mapList.size() > 0) {
            return (mapList.get(0));
        } else {
            return null;
        }
    }

     public int blockUnblcoStore(Long profileId) {
        Query qry = em.createNativeQuery("delete from map_module_profile where profile_id =?", MapModuleProfile.class);
        qry.setParameter(1, profileId);
        return qry.executeUpdate();
    }

    public List<MstEmployee> getEmployeeByUserID(String userID) {
        Query qry = em.createNativeQuery("select * from mst_employee where user_id=?", MstEmployee.class);
        //  qry.setParameter("userId", userID);
        qry.setParameter(1, userID);

        return qry.getResultList();
    }

    public List<MstZone> checkZoneByName(String zoneName) {
        Query qry = em.createNamedQuery("MstZone.findByZoneName", MstZone.class);
        qry.setParameter("zoneName", zoneName);
        return qry.getResultList();
    }

    public List<MstLocation> checkLocationByName(String locationName) {
        Query qry = em.createNamedQuery("MstLocation.findByLocationName", MstLocation.class);
        qry.setParameter("locationName", locationName);
        return qry.getResultList();
    }

    public int deleteRoleProfile(Long roleProfileid) {
        Query qry = em.createNativeQuery("delete from map_role_profile where mst_role_id=?", MapRoleProfile.class);
        qry.setParameter(1, roleProfileid);
        return qry.executeUpdate();
    }

    public int deleteProfileModule(Long profileId) {
        Query qry = em.createNativeQuery("delete from map_module_profile where profile_id =?", MapModuleProfile.class);
        qry.setParameter(1, profileId);
        return qry.executeUpdate();
    }

    public int deleteMapUserMCHF1(String EmpID) {
        Query qry = em.createNativeQuery("delete from map_user_MCH_F1 where emp_id=?", MapuserMCHF1.class);
        qry.setParameter(1, EmpID);
        return qry.executeUpdate();
    }

    public int deleteMapUserMCHF2(String EmpID) {
        Query qry = em.createNativeQuery("delete from map_user_MCH_F2 where emp_id=?", MapuserMCHF2.class);
        qry.setParameter(1, EmpID);
        return qry.executeUpdate();
    }

    public int deleteMapUserMCHF3(String EmpID) {
        Query qry = em.createNativeQuery("delete from map_user_MCH_F3 where emp_id=?", MapuserMCHF3.class);
        qry.setParameter(1, EmpID);
        return qry.executeUpdate();
    }

    public int deleteMapUserMCHF5(String EmpID) {
        Query qry = em.createNativeQuery("delete from map_user_MCH_F5 where emp_id=?", MapuserMCHF5.class);
        qry.setParameter(1, EmpID);
        return qry.executeUpdate();
    }

    public int deleteRoleLocation(Long roleProfileid) {
        Query qry = em.createNativeQuery("delete from map_role_location where mst_role_id =?", MapRoleLocation.class);
        qry.setParameter(1, roleProfileid);
        return qry.executeUpdate();
    }

    public List<MstRole> checkRoleByName(String roleName) {
        Query qry = em.createNamedQuery("MstRole.findByRoleName", MstRole.class);
        qry.setParameter("roleName", roleName);
        return qry.getResultList();
    }

    public List<MstCampaign> checkCampaignByName(String campaignName) {
        Query qry = em.createNativeQuery("select * from mst_campaign where campaign_name= '" + campaignName + "'", MstCampaign.class);
        return qry.getResultList();
    }

    public List<MstDepartment> checkDepartmentByName(String deptName) {
        Query qry = em.createNativeQuery("select * from mst_department where dept_name= ?", MstDepartment.class);
        qry.setParameter(1, deptName);
        return qry.getResultList();
    }

    public List<MstMktg> checkMarketingByName(String mktgName, Short isBlocked) {
        Query qry = em.createNativeQuery("select * from mst_mktg where mktg_name = '" + mktgName + "' and is_blocked=" + isBlocked, MstMktg.class);
        return qry.getResultList();
    }

    public List<MstReasonRejection> checkReasonByName(String reasonName, Short isBlocked) {
        Query qry = em.createNativeQuery("select * from mst_reason_rejection where reason_name = '" + reasonName + "' and is_blocked=" + isBlocked, MstReasonRejection.class);
        return qry.getResultList();
    }

    public List<MstReasonRejection> getAllReasonRejection(String isApprover) {
        Query qry = em.createNativeQuery("select * from mst_reason_rejection where is_approver = '" + isApprover + "'", MstReasonRejection.class);
        return qry.getResultList();
    }

    public List<MstProblem> checkProblemByName(String problemName, Short isBlocked) {
        Query qry = em.createNativeQuery("select * from mst_problem where problem_name = '" + problemName + "' and is_blocked=" + isBlocked, MstProblem.class);
        return qry.getResultList();
    }

    public List<MstPromotionType> checkPromotionTypeByName(String promotionName, Short isBlocked) {
        Query qry = em.createNativeQuery("select * from mst_promotion_type where promo_type_name = '" + promotionName + "' and is_blocked=" + isBlocked, MstPromotionType.class);
        return qry.getResultList();
    }

    public List<MstTask> checkTaskkTypeByName(String taskName, Short isBlocked) {
        Query qry = em.createNativeQuery("select * from mst_task where task_name = '" + taskName + "' and is_blocked=" + isBlocked, MstTask.class);
        return qry.getResultList();
    }

    public List<MstEvent> checkEventTypeByName(String eventName, Short isBlocked) {
        Query qry = em.createNativeQuery("select * from mst_event where event_name = '" + eventName + "' and is_blocked=" + isBlocked, MstEvent.class);
        return qry.getResultList();
    }

    public List<MstZone> checkZoneByCode(String zonecode) {
        Query qry = em.createNativeQuery("select * from mst_zone where zone_code = '" + zonecode + "'", MstZone.class);
        return qry.getResultList();
    }

    public List<MstStatus> getStatusForLeadTimes() {
        Query qry = em.createNativeQuery("select * from mst_status where is_lead_time=1", MstStatus.class);
        return qry.getResultList();
    }

    public MstLeadTime getMstLeadTime() {
        Query qry = em.createNativeQuery("select * from mst_lead_time where id=1", MstLeadTime.class);
        return (MstLeadTime) qry.getSingleResult();
    }

    public MstRole findRoleById(Long roleId) {
        Query query = em.createNamedQuery("MstRole.findByMstRoleId", MstRole.class);
        query.setParameter("mstRoleId", roleId);
        return (MstRole) query.getSingleResult();
    }

    public List<MstCalender> getCaledarsByDate(String calDate) {
        Query query = em.createNativeQuery("select * from mst_calender where cal_date='" + calDate + "'");
        return query.getResultList();
    }

    public List<MstCalender> getCaledarsByYear(String calYear) {
        Query query = em.createNativeQuery("select * from mst_calender where YEAR(cal_date) = '" + calYear + "'", MstCalender.class);
        return query.getResultList();
    }

     public int deleteCalanderValueByID(Long calID) {
        Query qry = em.createNativeQuery("delete from mst_calender where mst_calender_id=?");
        qry.setParameter(1, calID);
        return qry.executeUpdate();
    }
    public List<MstCalender> getCaledarsByYearAndMonth(String calYear, String calMonth) {
        Query query = em.createNativeQuery("select * from mst_calender where YEAR(cal_date) = '" + calYear + "' and MONTH(cal_date) =  '" + calMonth + "'", MstCalender.class);
        return query.getResultList();
    }

    public List<String> getAllCategoryName() {
        Query query = em.createNativeQuery("select DISTINCT  category_name from mch");
        return query.getResultList();
    }

    public List<String> getAllCategoryName(Long userId) {
        Query query = em.createNativeQuery("select distinct category_name from mch where mc_code in(select mc_code from map_user_MCH_F1 where emp_id=?)");
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public List getAllSubCategoryName(Long userId, String categoryName) {
        Query query = em.createNativeQuery("select distinct m.sub_category_name from mch m INNER JOIN map_user_MCH_F1 f ON m.mc_code=f.mc_code where f.emp_id=? AND m.category_name IN (" + categoryName + ") ");
        query.setParameter(1, userId);
        //query.setParameter(2, categoryName);
        return query.getResultList();
    }

    public List<String> getAllSubCategoryName() {
        Query query = em.createNativeQuery("select DISTINCT  sub_category_name from mch");
        return query.getResultList();
    }

    public List<String> getAllSubCategoryNameByCategory(String categoryName) {
        Query query = em.createNativeQuery("select DISTINCT  sub_category_name from mch where category_name=?");
        query.setParameter(1, categoryName);
        return query.getResultList();
    }

    public List<Date> getAllHolidayFromCalendar() {
        Query query = em.createNativeQuery("Select cal_date from mst_calender where cal_date >= current_date");
        return query.getResultList();
    }

    public List<Date> getAllHolidayFromCalendar(Date updatedDate) {
        Query query = em.createNativeQuery("Select cal_date from mst_calender where cal_date >= ?");
        query.setParameter(1, updatedDate);
        return query.getResultList();
    }

    public List<Mch> getAllMCBySubCategoryName(String categoryName, String subCategoryName) {
        Query query = em.createNativeQuery("select * from mch where category_name IN (" + categoryName + ") and sub_category_name IN (" + subCategoryName + ")", Mch.class);
        //query.setParameter(1, categoryName);
        //query.setParameter(2, subCategoryName);
        return query.getResultList();

    }

    public int deleteAllMCHbyPromoId(Long promoId) {
        Query qry = em.createNativeQuery("delete from map_promo_mch where mst_promo_id=?", MapPromoMch.class);
        qry.setParameter(1, promoId);
        return qry.executeUpdate();
    }

    public List<MstEmployee> searchEmployeeByStoreCode(String storeCode) {
        Query query = em.createNativeQuery("select * from mst_employee where mst_store_id=? and isblocked=0 and is_f6=1", MstEmployee.class);
        query.setParameter(1, storeCode);
        return query.getResultList();
    }

    public List<MstEmployee> l1TeamMemberForL1ByPass(String storeId, String l2EmpId) {
        Query query = em.createNativeQuery("select DISTINCT e.* from mst_employee e INNER JOIN map_user_MCH_F2 f2 ON e.emp_id=f2.emp_id INNER JOIN  map_user_MCH_F3 f3 ON f2.mc_code=f3.mc_code where e.mst_store_id=? AND f3.emp_id=?", MstEmployee.class);
        query.setParameter(1, new Long(storeId));
        query.setParameter(2, new Long(l2EmpId));
        return query.getResultList();
    }

    public List<MstEmployee> l1TeamMemberForL1ByPass(String l2EmpId) {
        Query query = em.createNativeQuery("select DISTINCT e.* from mst_employee e INNER JOIN map_user_MCH_F2 f2 ON e.emp_id=f2.emp_id INNER JOIN  map_user_MCH_F3 f3 ON f2.mc_code=f3.mc_code where f3.emp_id=?", MstEmployee.class);
        query.setParameter(1, new Long(l2EmpId));
        return query.getResultList();
    }

    public int assignTeamMember(Long promoReqId, Long promoMgrId, Long memberId) {
        Query query = em.createNativeQuery("update trans_promo set status_id=27, promo_mgr_id=?, executive_id=?, updated_date=now() where trans_promo_id=?");
        query.setParameter(1, promoMgrId);
        query.setParameter(2, memberId);
        query.setParameter(3, promoReqId);

        Query query1 = em.createNativeQuery("update trans_promo_article set current_status_id=27, updated_by=?, updated_date=now() where trans_promo_id=?");
        query1.setParameter(1, promoMgrId);
        query1.setParameter(2, promoReqId);
        query1.executeUpdate();

        return query.executeUpdate();

    }

    public int rejectTransPromoByPromoMgr(Long promoReqId, Long statusId, Long empId, String reasonRejction, String rejectRemark) {
        Query query = em.createNativeQuery("update trans_promo set status_id=?, last_updated_by=?, updated_date=now(),reason_for_rejection=?,promo_mgr_id=?,rejection_remarks=? where trans_promo_id=?");
        query.setParameter(1, statusId);
        query.setParameter(2, empId);
        query.setParameter(3, reasonRejction);
        query.setParameter(4, empId);
        query.setParameter(5, rejectRemark);
        query.setParameter(6, promoReqId);

        Query query1 = em.createNativeQuery("update trans_promo_article set current_status_id=?, updated_by=?, updated_date=now() where trans_promo_id=?");
        query1.setParameter(1, statusId);
        query1.setParameter(2, empId);
        query1.setParameter(3, promoReqId);
        query1.executeUpdate();

        return query.executeUpdate();
    }

    public int updateTransPromoArticleStatus(Long promoReqId, Long statusId, Long empId) {
        Query query1 = em.createNativeQuery("update trans_promo_article set current_status_id=?, updated_by=?, updated_date=now() where trans_promo_id=?");
        query1.setParameter(1, statusId);
        query1.setParameter(2, empId);
        query1.setParameter(3, promoReqId);
        return query1.executeUpdate();
    }

    public List<TransTask> getTransTaskAssignerList(int startIndex, long assignerId) {
        Query query = em.createNativeQuery("select * from trans_task where assigned_to=? order by trans_task_id desc", TransTask.class);
        query.setParameter(1, assignerId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getTransTaskAssignerListCount(long assignerId) {
        Query query = em.createNativeQuery("select count(*) from trans_task where assigned_to=?");
        query.setParameter(1, assignerId);
        return new Long(query.getSingleResult().toString());
    }

    public List<TransTask> getTransTaskListByCreator(int startIndex, long creatorId) {
        Query query = em.createNativeQuery("select * from trans_task where created_by=? order by trans_task_id desc", TransTask.class);
        query.setParameter(1, creatorId);
        query.setFirstResult(startIndex);
        query.setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    public Long getTransTaskListByCreatorCount(long creatorId) {
        Query query = em.createNativeQuery("select count(*) from trans_task where created_by=? ");
        query.setParameter(1, creatorId);
        return new Long(query.getSingleResult().toString());
    }

    public void insertTransPromoMC(Long transPromoId, String mcCode, Long statusId, Long creatorId, Date date) {
        Query query = em.createNativeQuery("insert into trans_promo_mc(trans_promo_id,mc_code,status_id,updated_by,updated_time) values(?,?,?,?,?)");
        query.setParameter(1, transPromoId);
        query.setParameter(2, mcCode);
        query.setParameter(3, statusId);
        query.setParameter(4, creatorId);
        query.setParameter(5, new Date());
        query.executeUpdate();
    }

    public List<MstEmployee> getAllTeamMember() {
        Query query = em.createNativeQuery("select * from mst_employee where is_F6 = 1", MstEmployee.class);
        return query.getResultList();
    }

    public List<MstEmployee> getAllTeamMemberByZone(String storecode) {
        Query query = em.createNativeQuery("select * from mst_employee where is_F6 = 1 AND mst_store_id IN (?,?)", MstEmployee.class);
        query.setParameter(1, storecode);
        query.setParameter(2, "901");
        return query.getResultList();
    }

    public int deleteMapUserDept(Long empId) {
        Query qry = em.createNativeQuery("delete from map_user_department where emp_id=?", MapUserDepartment.class);
        qry.setParameter(1, empId);
        return qry.executeUpdate();
    }

    public List<String> getCategoryListForLevel1Approver(long empId) {
        Query qry = em.createNativeQuery("select DISTINCT mc.category_name from mch mc INNER JOIN map_user_MCH_F2 f2 ON f2.mc_code=mc.mc_code where f2.emp_id=?");
        qry.setParameter(1, empId);
        return qry.getResultList();
    }

    public List<String> getCategoryListForLevel2Approver(long empId) {
        Query qry = em.createNativeQuery("select DISTINCT mc.category_name from mch mc INNER JOIN map_user_MCH_F3 f3 ON f3.mc_code=mc.mc_code where f3.emp_id=?");
        qry.setParameter(1, empId);
        return qry.getResultList();
    }

    public int deleteTransPromoFileByTransPromo(Long transPromoId) {
        Query qry = em.createNativeQuery("delete from trans_promo_file where trans_promo_id=?");
        qry.setParameter(1, transPromoId);
        return qry.executeUpdate();
    }

    public int deleteTransProposalDtl(Long proposalId) {
        Query qry = em.createNativeQuery("delete from trans_proposal where proposal_id=?");
        qry.setParameter(1, proposalId);
        return qry.executeUpdate();
    }

    public List<MstZone> getAllZoneBasedOnFormatForHO(String formatname) {
        Query query = em.createNativeQuery("select distinct z.* from mst_zone z inner join mst_store s on s.mst_zone_id=z.zone_id where s.format_name in (" + formatname + ") and s.mst_location_id=2 and s.is_blocked=0", MstZone.class);
        return query.getResultList();
    }

    public List<MstZone> getAllZoneBasedOnFormatForZone(String formatname, String zoneId) {
        Query query = em.createNativeQuery("select distinct z.* from mst_zone z inner join mst_store s on s.mst_zone_id=z.zone_id where s.format_name in (" + formatname + ") and s.mst_zone_id=?", MstZone.class);
        query.setParameter(1, zoneId);
        return query.getResultList();
    }

    public List getAllStateBasedOnFormatAndZone(String formatname, String zoneid) {
        Query query = em.createNativeQuery("select distinct s.state from  mst_store s where s.format_name in (" + formatname + ") and s.mst_zone_id in (" + zoneid + ") and s.mst_location_id=2 and s.is_blocked=0");
        return query.getResultList();
    }

    public List getAllRegionBasedOnFormatAndState(String formatname, String statename, String zoneList) {
        Query query = em.createNativeQuery("select distinct s.region_name from  mst_store s where s.format_name in (" + formatname + ") and s.state in (" + statename + ") and  s.mst_zone_id in (" + zoneList + ") and s.mst_location_id=2 and s.is_blocked=0");
        return query.getResultList();
    }

    public List getAllCityBasedOnFormatAndRegion(String formatname, String region, String statename) {
        Query query = em.createNativeQuery("select distinct s.city from  mst_store s where s.format_name in (" + formatname + ") and s.region_name in (" + region + ") and  s.state in (" + statename + ") and s.mst_location_id=2 and s.is_blocked=0");
        return query.getResultList();
    }

    public List<MstStore> getAllStoreBasedOnFormatAndCity(String formatname, String city, String zonelist) {
        Query query = em.createNativeQuery("select distinct s.* from  mst_store s where s.format_name in (" + formatname + ") and s.city in (" + city + ") and s.mst_location_id= 2 and  s.mst_zone_id in (" + zonelist + ") and s.is_blocked=0", MstStore.class);
        return query.getResultList();
    }

    public List getAllFormatExceptZoneAndHO() {
        Query query = em.createNativeQuery("select distinct s.format_name from mst_store s where s.mst_location_id=2 and s.is_blocked=0 ");
        return query.getResultList();
    }

    public List<TransPromo> getExecutorTransPromoForCloser(Long teamMemberId) {
        Query query = em.createNativeQuery("SELECT * FROM trans_promo m where m.status_Id IN (27,29) AND executive_id=?  order by m.trans_promo_id desc", TransPromo.class);
        query.setParameter(1, teamMemberId);
        return query.getResultList();
    }

    public List<MstEmployee> getIntiatorEmployeeList(String employeeType) {
        if (employeeType.equalsIgnoreCase("initiator")) {
            Query query = em.createNativeQuery("select distinct e.* from mst_employee e inner join map_user_MCH_F1 f1 on e.emp_id=f1.emp_id", MstEmployee.class);
            return query.getResultList();
        } else if (employeeType.equalsIgnoreCase("l1")) {
            Query query = em.createNativeQuery("select distinct e.* from mst_employee e inner join map_user_MCH_F2 f2 on e.emp_id=f2.emp_id", MstEmployee.class);
            return query.getResultList();
        } else if (employeeType.equalsIgnoreCase("l2")) {
            Query query = em.createNativeQuery("select distinct e.* from mst_employee e inner join map_user_MCH_F3 f3 on e.emp_id=f3.emp_id", MstEmployee.class);
            return query.getResultList();
        } else {
            return null;
        }
    }
}
