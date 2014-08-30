/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.master.util;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.entity.MstCalender;
import com.fks.promo.entity.MstCampaign;
import com.fks.promo.entity.MstDepartment;
import com.fks.promo.entity.MstEvent;
import com.fks.promo.entity.MstMktg;
import com.fks.promo.entity.MstProblem;
import com.fks.promo.entity.MstPromotionType;
import com.fks.promo.entity.MstReasonRejection;
import com.fks.promo.entity.MstSet;
import com.fks.promo.entity.MstStatus;
import com.fks.promo.entity.MstTask;
import com.fks.promo.entity.MstZone;
import com.fks.promo.master.vo.DepartmentVO;
import com.fks.promo.master.vo.MstCalendarVO;
import com.fks.promo.master.vo.MstCampaignVO;
import com.fks.promo.master.vo.MstEventVO;
import com.fks.promo.master.vo.MstMktgVO;
import com.fks.promo.master.vo.MstPromotionTypeVO;
import com.fks.promo.master.vo.MstReasonRejectionVO;
import com.fks.promo.master.vo.MstStatusVO;
import com.fks.promo.master.vo.MstTaskVO;
import com.fks.promo.master.vo.MstZoneVO;
import com.fks.promo.master.vo.ProblemMasterVO;
import com.fks.promo.master.vo.SetVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paresb
 */
public class VOUtilOtherMaster {

    public static List<SetVO> convertMstSet(List<MstSet> list) {
        List<SetVO> voList = new ArrayList<SetVO>();
        for (MstSet set : list) {
            voList.add(new SetVO(set.getSetId(), set.getSetDesc()));
        }
        return voList;
    }

    public static List<DepartmentVO> convertMstDepartment(List<MstDepartment> list) {
        List<DepartmentVO> voList = new ArrayList<DepartmentVO>();
        for (MstDepartment department : list) {
            DepartmentVO vo = new DepartmentVO(department.getMstDeptId(), department.getDeptName(),department.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstMktgVO> convertMstMktgType(List<MstMktg> list) {
        List<MstMktgVO> voList = new ArrayList<MstMktgVO>();
        for (MstMktg mstMktg : list) {
            MstMktgVO vo = new MstMktgVO(mstMktg.getMktgId(), mstMktg.getMktgName());
            vo.setIsBlocked(mstMktg.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstCampaignVO> convertMstCampaign(List<MstCampaign> list) {
        List<MstCampaignVO> voList = new ArrayList<MstCampaignVO>();
        for (MstCampaign mstCampaign : list) {
            MstCampaignVO vo = new MstCampaignVO(mstCampaign.getCampaignId(), mstCampaign.getCampaignName(), mstCampaign.getIsActive());            
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstReasonRejectionVO> convertMstReasonRejection(List<MstReasonRejection> list) {
        List<MstReasonRejectionVO> voList = new ArrayList<MstReasonRejectionVO>();
        for (MstReasonRejection reason : list) {
            MstReasonRejectionVO vo = new MstReasonRejectionVO(reason.getReasonRejectionId(), reason.getReasonName(), reason.getIsApprover());
            vo.setIsBlocked(reason.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<ProblemMasterVO> convertMstProblem(List<MstProblem> list) {
        List<ProblemMasterVO> voList = new ArrayList<ProblemMasterVO>();
        for (MstProblem problem : list) {
            ProblemMasterVO vo = new ProblemMasterVO(problem.getProblemTypeId(), problem.getProblemName());
            vo.setIsBlocked(problem.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstPromotionTypeVO> convertMstPromotion(List<MstPromotionType> list) {
        List<MstPromotionTypeVO> voList = new ArrayList<MstPromotionTypeVO>();
        for (MstPromotionType promotion : list) {
            MstPromotionTypeVO vo = new MstPromotionTypeVO(promotion.getPromoTypeId(), promotion.getPromoTypeName());
            vo.setIsBlocked(promotion.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstTaskVO> convertTask(List<MstTask> list) {
        List<MstTaskVO> voList = new ArrayList<MstTaskVO>();
        for (MstTask task : list) {
            MstTaskVO vo = new MstTaskVO(task.getTaskId(), task.getTaskName());
            vo.setIsBlocked(task.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstEventVO> convertEvent(List<MstEvent> list) {
        List<MstEventVO> voList = new ArrayList<MstEventVO>();
        for (MstEvent event : list) {
            MstEventVO vo = new MstEventVO(event.getEventId(), event.getEventName());
            vo.setIsBlocked(event.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstStatusVO> convertStatus(List<MstStatus> list) {
        List<MstStatusVO> voList = new ArrayList<MstStatusVO>();
        for (MstStatus status : list) {
            MstStatusVO vo = new MstStatusVO(status.getStatusId(), status.getStatusDesc(), status.getIsLeadTime());
            vo.setL1(status.getL1());
            vo.setL2(status.getL2());
            vo.setL5(status.getL5());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstZoneVO> convertZone(List<MstZone> list) {
        List<MstZoneVO> voList = new ArrayList<MstZoneVO>();
        for (MstZone zone : list) {
            MstZoneVO vo = new MstZoneVO(zone.getZoneId(), zone.getZoneName(), zone.getZoneCode());
            vo.setIsBlocked(zone.getIsBlocked());
            voList.add(vo);
        }
        return voList;
    }

    public static List<MstCalendarVO> getCalendars(List<MstCalender> list) {

        List<MstCalendarVO> listvo = new ArrayList<MstCalendarVO>();

        for (MstCalender calendar : list) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String d = format.format(calendar.getCalDate());
                MstCalendarVO vo = new MstCalendarVO(calendar.getMstCalenderId(), d, calendar.getDateDescription());
                listvo.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        return listvo;
    }
}
