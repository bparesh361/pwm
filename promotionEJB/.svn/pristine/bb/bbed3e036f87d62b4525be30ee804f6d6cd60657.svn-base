/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init;

import com.fks.promo.comm.service.CommService;
import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.Resp;
import com.fks.promo.common.RespCode;
import com.fks.promo.commonDAO.CommonPromotionDao;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.TransPromo;
import com.fks.promotion.vo.PromotionVO;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;

/**
 *
 * @author Paresb
 */
@Stateless
@LocalBean
@WebService
public class SearchPromotionService {

    Logger logger = Logger.getLogger(SearchPromotionService.class.getName());
    @EJB
    private CommonPromotionDao commonPromotionDao;

    public SearchPromotionResp getAllPromotionRequestdtl(SearchPromotionReq request) {
        logger.info("========= Inside getAllPromotionRequestdtl Service ===== Type " + request.getType() + " Start Index : " + request.getStartIdex() + " User Id " + request.getUserId());
        StringWriter buffer = new StringWriter();
        JAXB.marshal(request, buffer);
        logger.info("------ Payload------ \n" + buffer);
        List<PromotionVO> list = null;
        Long totalCount = new Long("0");
        SearchPromotionResp response = new SearchPromotionResp(new Resp(RespCode.SUCCESS, "success"));
        String searchCriteria = null;

        try {
            if (request.getPromoDashboardCriteria() != null) {
//                if (request.getPromoDashboardCriteria().getStartDate() != null) {
//                    request.getPromoDashboardCriteria().setStartDate(CommonUtil.addSubtractDaysInDate(request.getPromoDashboardCriteria().getStartDate(), -1));
//                }
                if (request.getPromoDashboardCriteria().getEndDate() != null) {
                    request.getPromoDashboardCriteria().setEndDate(CommonUtil.addSubtractDaysInDate(request.getPromoDashboardCriteria().getEndDate(), 1));
                }
                logger.info("------ start Date : " + request.getPromoDashboardCriteria().getStartDate());
                logger.info("------ end Date : " + request.getPromoDashboardCriteria().getEndDate());
            }

        } catch (ParseException ex) {
            logger.info("--------------Start/End Date Parse Exception In  getAllPromotionRequestdtl SearchPromotionService.");
            java.util.logging.Logger.getLogger(CommService.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (request.getType()) {
            case STATUS_USER:
                List<MstPromo> promoList = commonPromotionDao.searchMstPromoByStatusUser(request.getUserId(), request.getStartIdex());
                totalCount = commonPromotionDao.searchMstPromoByStatusUserCount(request.getUserId(), request.getStartIdex());
                list = TransPromoUtil.convertMstPromoToPromoVO(promoList);
                break;
            case ALL:
                searchCriteria = request.getPromoDashboardEnum().toString();
                SearchPromoDashboardCriteria crieteria = request.getPromoDashboardCriteria();
                List<TransPromo> transPromosList = null;
                switch (request.getPromoDashboardEnum()) {
                    case ALL:
                        transPromosList = commonPromotionDao.getAllPromoDetailUserWise(request.getUserId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getAllPromoDetailUserWiseCount(request.getUserId());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case DATE:
                        transPromosList = commonPromotionDao.getViewPromoDetailByUserDate(request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailByUserDateCount(request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case EVENT:
                        transPromosList = commonPromotionDao.getViewPromoDetailByUserEvent(request.getUserId(), crieteria.getEventId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailByUserEventCount(request.getUserId(), crieteria.getEventId());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case MARKETING_TYPE:
                        transPromosList = commonPromotionDao.getViewPromoDetailByUserMarketting(request.getUserId(), crieteria.getMarketingTypeId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailByUserMarkettingCount(request.getUserId(), crieteria.getMarketingTypeId());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case PROMOTION_TYPE:
                        transPromosList = commonPromotionDao.getViewPromoDetailByUserPromoType(request.getUserId(), crieteria.getPromotionTypeId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailByUserPromoTypeCount(request.getUserId(), crieteria.getPromotionTypeId());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case DATE_EVENT:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case DATE_MARKETING_TYPE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case DATE_PROMOTION_TPYE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case SUB_CATEGORY_DATE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case STATUS_DATE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case STATUS_DATE_EVENT_TYPE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case STATUS_DATE_MARKETTING_TYPE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case STATUS_DATE_PROMO_TYPE:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                    case STATUS_DATE_SUB_CATEGORY:
                        transPromosList = commonPromotionDao.getViewPromoDetail(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        totalCount = commonPromotionDao.getViewPromoDetailCount(searchCriteria, request.getUserId(), crieteria.getStartDate(), crieteria.getEndDate(), crieteria.getEventId(), crieteria.getMarketingTypeId(), crieteria.getPromotionTypeId(), crieteria.getSubCategoryName(), crieteria.getStatusId(), request.getStartIdex());
                        list = TransPromoUtil.convertMstPromoAndTransPromoToPromoVO(transPromosList);
                        break;
                }
                break;
            case ORGANIZATION_DATA:
                List<MstPromo> orgpromoList = commonPromotionDao.searchMstPromoByStatusUserOrg(request.getStatusId(), request.getUserId(), request.getStartIdex());
                totalCount = commonPromotionDao.searchMstPromoByStatusUserCountOrg(request.getStatusId(), request.getUserId(), request.getStartIdex());
                list = TransPromoUtil.convertMstPromoToPromoVO(orgpromoList);
                break;

        }
        response.setList(list);
        response.setTotalCount(totalCount);
        return response;

    }
}
