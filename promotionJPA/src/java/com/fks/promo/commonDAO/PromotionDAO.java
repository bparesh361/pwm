/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.commonDAO;

import com.fks.promo.entity.Mch;
import com.fks.promo.entity.MstEmployee;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.vo.RequestRespVO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ajitn
 */
@Stateless
public class PromotionDAO {

    @PersistenceContext(unitName = "promotionJPAPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MstProposal getFileFailureProposalByEmp(String empId) {
        Query qry = em.createNativeQuery("select * from mst_proposal where emp_id=? AND status_id='6'", MstProposal.class);
        qry.setParameter(1, empId);
        List<MstProposal> proposalList = qry.getResultList();
        if (proposalList != null && proposalList.size() > 0) {
            return (proposalList.get(0));
        }
        return null;
    }

    public List getEmployeeEmailIByPromoMC(String mstPromoId) {
        Query qry = em.createNativeQuery("select Distinct e.emp_id from trans_promo t INNER JOIN trans_promo_mc m ON t.trans_promo_id=m.trans_promo_id ,map_user_MCH_F2 f2  Inner join mst_employee e on f2.emp_id=e.emp_id where f2.mc_code=m.mc_code and t.promo_id=?");
        qry.setParameter(1, mstPromoId);
        List list = qry.getResultList();
        return list;
    }

    public List<RequestRespVO> getEmployeeEmailIByPromoMCTransIds(String mstPromoId, BigInteger empId) {
        Query qry = em.createNativeQuery("select Distinct e.emp_id,e.email_id ,t.trans_promo_id "
                + "from trans_promo t INNER JOIN trans_promo_mc m ON t.trans_promo_id=m.trans_promo_id ,map_user_MCH_F2 f2 "
                + "Inner join mst_employee e on f2.emp_id=e.emp_id "
                + "inner join mst_store s on e.mst_store_id=s.mst_store_id  "
                + "where f2.mc_code=m.mc_code and t.promo_id=? and e.emp_id =? and (s.mst_zone_id=t.zone_id or s.mst_store_id=901)");
        qry.setParameter(1, mstPromoId);
        qry.setParameter(2, empId);
        List<RequestRespVO> lstrespVo = new ArrayList<RequestRespVO>();
        List list = qry.getResultList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object[] obj = (Object[]) iterator.next();
            BigInteger empID = (BigInteger) obj[0];
            String emailId = (String) obj[1];
            BigInteger transId = (BigInteger) obj[2];

            RequestRespVO service = new RequestRespVO();
            service.setEmpId(empID);
            service.setEmailId(emailId);
            service.setTransPromoId(transId);
            lstrespVo.add(service);
        }
        return lstrespVo;
    }

    public List getL2EmployeeDtlByPromoMC(String transPromo) {
        Query qry = em.createNativeQuery("select Distinct e.emp_id from trans_promo t INNER JOIN trans_promo_mc m ON t.trans_promo_id=m.trans_promo_id ,map_user_MCH_F3 f2  Inner join mst_employee e on f2.emp_id=e.emp_id where f2.mc_code=m.mc_code and t.trans_promo_id=?");
        qry.setParameter(1, transPromo);
        List list = qry.getResultList();
        return list;
    }


}
