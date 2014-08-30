/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.proposal.action;

import com.fks.promo.init.TransPromoArticleVO;
import com.fks.promo.init.TransPromoConfigVO;
import com.fks.promo.init.TransPromoVO;
import com.fks.promotion.service.ArticleMCVO;
import com.fks.promotion.service.ProposalEnum;
import com.fks.promotion.service.ProposalVO;
import com.fks.ui.constants.PromotionUtil;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.form.vo.BxGyFormVo;
import com.fks.ui.form.vo.CreateBXGYFormVO;
import com.fks.ui.form.vo.CreateProposalFormVO;
import com.fks.ui.master.vo.ArticleMcVo;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author ajitn
 */
public class ProposalUtil {

    public static ProposalVO getManualCreateProposalRequest(CreateProposalFormVO formVO, String empID, String zoneID) {

        ProposalVO request = new ProposalVO();

        request.setEmpId(empID);
        request.setZoneId(zoneID);
        request.setIsSaveDraft(formVO.getIsSaveDraft());

        request.setProEnum(ProposalEnum.MANUAL_ENTRY);
        if (formVO.getManualDeptId() != null) {
            request.setDeptId(formVO.getManualDeptId());
        }
        request.setProblemTypeId(formVO.getManualProblemTypeId());
        if (formVO.getManualPromoDesc() != null) {
            request.setPromoDesc(formVO.getManualPromoDesc());
        }
        if (formVO.getManualPromoType() != null) {
            request.setPromoType(formVO.getManualPromoType());
        }
        request.setRemarks(formVO.getManualRemarks());
        request.setSolutionDesc(formVO.getManualSolutionDesc());

        if (formVO.getManualArticleData() != null) {
            System.out.println("------------ article data : " + formVO.getManualArticleData());
            String[] articleGridData = formVO.getManualArticleData().split(",");
            ArticleMCVO articleVo = null;
            for (int i = 0; i < articleGridData.length / 4; i++) {

                articleVo = new ArticleMCVO();
                articleVo.setArticleCode(articleGridData[(i * 4)]);
                articleVo.setArticleDesc(articleGridData[(i * 4) + 1]);
                articleVo.setMcCode(articleGridData[(i * 4) + 2]);
                articleVo.setMcDesc(articleGridData[(i * 4) + 3]);

                request.getArticleList().add(articleVo);
            }
        }

        return request;
    }

    public static ProposalVO getUploadFileCreateProposalRequest(CreateProposalFormVO formVO, String empID, String filePath, String fileName, String zoneID) {

        ProposalVO request = new ProposalVO();

        request.setEmpId(empID);
        request.setZoneId(zoneID);
        request.setProEnum(ProposalEnum.UPLOAD_FILE);
        request.setFileName(fileName);
        request.setFilePath(filePath);
        if (formVO.getUploadDeptId() != null) {
            request.setDeptId(formVO.getUploadDeptId());
        }
        request.setProblemTypeId(formVO.getUploadProblemTypeId());
        if (formVO.getUploadPromoDesc() != null) {
            request.setPromoDesc(formVO.getUploadPromoDesc());
        }
        if (formVO.getUploadPromoType() != null) {
            request.setPromoType(formVO.getUploadPromoType());
        }
        request.setRemarks(formVO.getUploadRemarks());
        request.setSolutionDesc(formVO.getUploadSolutionDesc());

        return request;
    }

    public static ProposalVO getUploadFileCreateProposalRequest(CreateProposalFormVO formVO, String empID, String zoneID) {

        ProposalVO request = new ProposalVO();

        request.setProposalId(formVO.getProposalID());
        request.setEmpId(empID);
        request.setZoneId(zoneID);
//        request.setProEnum(ProposalEnum.UPDATE_PROPOSAL_WITHOUT_FILE);
        request.setDeptId(formVO.getUploadDeptId());
        request.setProblemTypeId(formVO.getUploadProblemTypeId());
        request.setPromoDesc(formVO.getUploadPromoDesc());
        request.setPromoType(formVO.getUploadPromoType());
        request.setRemarks(formVO.getUploadRemarks());
        request.setSolutionDesc(formVO.getUploadSolutionDesc());

        return request;
    }

    public static ProposalVO getProposalUploadFileUpdateRequest(CreateProposalFormVO formVO, String empID, String zoneID) {

//        ProposalVO request = new ProposalVO();
//
//        request.setEmpId(empID);
//        request.setIsSaveDraft(formVO.getIsSaveDraft());
//
//        request.setProEnum(ProposalEnum.UPLOAD_UPDATE_FILE);
//
//        return request;

        ProposalVO request = new ProposalVO();

        request.setEmpId(empID);
        request.setZoneId(zoneID);
        request.setIsSaveDraft(formVO.getIsUploadSaveDraft());
        request.setProEnum(ProposalEnum.UPDATE_PROPOSAL_WITHOUT_FILE);
        if (formVO.getUploadDeptId() != null) {
            request.setDeptId(formVO.getUploadDeptId());
        }
        request.setProblemTypeId(formVO.getUploadProblemTypeId());
        if (formVO.getUploadPromoDesc() != null) {
            request.setPromoDesc(formVO.getUploadPromoDesc());
        }
        if (formVO.getUploadPromoType() != null) {
            request.setPromoType(formVO.getUploadPromoType());
        }
        request.setRemarks(formVO.getUploadRemarks());
        request.setSolutionDesc(formVO.getUploadSolutionDesc());

        return request;
    }

    public static TransPromoVO getManualCreateBXGXRequest(CreateBXGYFormVO formVO, List<ArticleMcVo> list) throws ParseException {

        TransPromoVO promoVO = new TransPromoVO();
        TransPromoConfigVO configVO;
        TransPromoArticleVO articleVO;

        promoVO.setMstPromoId(new Long(formVO.getPromoreqID()));
        promoVO.setPromoTypeId(WebConstants.BUY_X_GET_Y);
        promoVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYFrom())));
        promoVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYTo())));
        promoVO.setRemark(formVO.getTxtRemarks());

        //create list of config

        if (formVO.getManualdiscGridData() != null) {
            String[] articleGridData = formVO.getManualdiscGridData().split(",");
            System.out.println("Grid : " + formVO.getManualdiscGridData());
            for (int i = 0; i < articleGridData.length / 5; i++) {
                configVO = new TransPromoConfigVO();
                configVO.setDiscConfig(articleGridData[(i * 5) + 3]);
                System.out.println("Article Data :" + articleGridData[(i * 5) + 1]);
                configVO.setDiscValue(Double.parseDouble(articleGridData[(i * 5) + 4]));
                if (articleGridData[(i * 5)].equals("1")) {
                    configVO.setIsX(true);
                } else if (articleGridData[(i * 5)].equals("2")) {
                    configVO.setIsY(true);
                } else if (articleGridData[(i * 5)].equals("3")) {
                    configVO.setIsZ(true);
                } else if (articleGridData[(i * 5)].equals("4")) {
                    configVO.setIsA(true);
                } else if (articleGridData[(i * 5)].equals("5")) {
                    configVO.setIsB(true);
                }
                configVO.setTicketSizeGrowth(Double.parseDouble(formVO.getTxtBXGYgrowth()));
                 configVO.setSellThruQty(Double.parseDouble(formVO.getTxtBXGYsellQty()));
                
                configVO.setMarginAchievement(Double.parseDouble(formVO.getTxtBXGYmargin()));
                configVO.setGrowthCoversion(Double.parseDouble(formVO.getTxtBXGYgrowthConver()));
                configVO.setSalesGrowth(Double.parseDouble(formVO.getTxtBXGYsalegrowth()));
                configVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYFrom())));
                configVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYTo())));
                promoVO.getTransPromoConfigList().add(configVO);
            }
        }
        // }


//        configVO.setDiscConfig(formVO.getDisConfig());
//        configVO.setDiscValue(Double.parseDouble(formVO.getTxtdisValue()));
//        configVO.setTicketSizeGrowth(Double.parseDouble(formVO.getTxtBXGYgrowth()));
//        configVO.setSellThruQty(Integer.parseInt(formVO.getTxtBXGYsellQty()));
//        configVO.setMarginAchievement(Double.parseDouble(formVO.getTxtBXGYmargin()));
//        configVO.setGrowthCoversion(Double.parseDouble(formVO.getTxtBXGYgrowthConver()));
//        configVO.setSalesGrowth(Double.parseDouble(formVO.getTxtBXGYsalegrowth()));
//        configVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYFrom())));
//        configVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYTo())));
//        promoVO.getTransPromoConfigList().add(configVO);


        if ((formVO.getIsZManualEntry() == null ? "0" == null : formVO.getIsZManualEntry().equals("0"))
                || (formVO.getIsXManualEntry() == null ? "0" == null : formVO.getIsXManualEntry().equals("0"))
                || (formVO.getIsYManualEntry() == null ? "0" == null : formVO.getIsYManualEntry().equals("0"))
                || (formVO.getIsSet4ManualEntry() == null ? "0" == null : formVO.getIsSet4ManualEntry().equals("0"))
                || (formVO.getIsSet5ManualEntry() == null ? "0" == null : formVO.getIsSet5ManualEntry().equals("0"))) {
            if (list.size() > 0 && !list.isEmpty()) {
                //file upload -get data from session
                for (ArticleMcVo vo : list) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(vo.getArticleCode());
                    articleVO.setArtDesc(vo.getArticleDesc());
                    articleVO.setMcCode(vo.getMcCode());
                    articleVO.setMcDesc(vo.getMcDesc());
                    articleVO.setQty(vo.getQty());
//                    System.out.println("X Value : " + vo.isIs_x() + " --" + vo.getArticleCode());
//                    System.out.println("Y Value : " + vo.isIs_y() + " --" + vo.getArticleCode());
//                    System.out.println("Z Value : " + vo.isIs_z() + " --" + vo.getArticleCode());
                    articleVO.setIsX(vo.isIs_x());
                    articleVO.setIsY(vo.isIs_y());
                    articleVO.setIsZ(vo.isIs_z());
                    articleVO.setIsA(vo.isIs_a());
                    articleVO.setIsB(vo.isIs_b());
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsXManualEntry() == null ? "1" == null : formVO.getIsXManualEntry().equals("1")) {
            if (formVO.getManualXArticleData() != null) {
                String[] articleGridData = formVO.getManualXArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(true);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(false);
                    articleVO.setIsB(false);
//                    System.out.println("grid data : " + articleGridData[(i * 5) + 4]);
//                    System.out.println(" Actual :" + articleVO.getQty());

                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }


        if (formVO.getIsYManualEntry() == null ? "1" == null : formVO.getIsYManualEntry().equals("1")) {
            if (formVO.getManualYArticleData() != null) {
                String[] articleGridData = formVO.getManualYArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(true);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(false);
                    articleVO.setIsB(false);
//                    System.out.println("grid data : " + articleGridData[(i * 5) + 4]);
//                    System.out.println(" Actual :" + articleVO.getQty());
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsZManualEntry() == null ? "1" == null : formVO.getIsZManualEntry().equals("1")) {
            if (formVO.getZarticleGridData() != null) {
                String[] articleGridData = formVO.getZarticleGridData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(true);
                    articleVO.setIsA(false);
                    articleVO.setIsB(false);

                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsSet4ManualEntry() == null ? "1" == null : formVO.getIsSet4ManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual Set 4 Data -------");
            if (formVO.getManualSet4GridData() != null) {
                String[] articleGridData = formVO.getManualSet4GridData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(true);
                    articleVO.setIsB(false);
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsSet5ManualEntry() == null ? "1" == null : formVO.getIsSet5ManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual Set 5 Data -------");
            if (formVO.getManualSet5GridData() != null) {
                String[] articleGridData = formVO.getManualSet5GridData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(false);
                    articleVO.setIsB(true);
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        return promoVO;
    }

    public static TransPromoVO getManualCreateBXGYAtDiscountedRequest(CreateBXGYFormVO formVO, List<ArticleMcVo> list) throws ParseException {

        TransPromoVO promoVO = new TransPromoVO();
        TransPromoConfigVO configVO = new TransPromoConfigVO();
        TransPromoArticleVO articleVO;

        promoVO.setMstPromoId(new Long(formVO.getPromoreqID()));
        promoVO.setPromoTypeId(WebConstants.BUY_X_AND_Y_AT_DISCOUNTED_PRICE);
        promoVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYFrom())));
        promoVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYTo())));
        promoVO.setRemark(formVO.getTxtRemarks());

        if (formVO.getDisConfig().equalsIgnoreCase("0")) {
            configVO.setDiscConfig("Value off");
        } else if (formVO.getDisConfig().equalsIgnoreCase("1")) {
            configVO.setDiscConfig("Percentage Off");
        } else if (formVO.getDisConfig().equalsIgnoreCase("2")) {
            configVO.setDiscConfig("Flat Price");
        }

        configVO.setDiscValue(Double.parseDouble(formVO.getTxtdisValue()));
        configVO.setTicketSizeGrowth(Double.parseDouble(formVO.getTxtBXGYgrowth()));
        configVO.setSellThruQty(Double.parseDouble(formVO.getTxtBXGYsellQty()));
        configVO.setMarginAchievement(Double.parseDouble(formVO.getTxtBXGYmargin()));
        configVO.setGrowthCoversion(Double.parseDouble(formVO.getTxtBXGYgrowthConver()));
        configVO.setSalesGrowth(Double.parseDouble(formVO.getTxtBXGYsalegrowth()));
        configVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYFrom())));
        configVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getTxtBXGYTo())));
        promoVO.getTransPromoConfigList().add(configVO);



        if ((formVO.getIsZManualEntry() == null ? "0" == null : formVO.getIsZManualEntry().equals("0"))
                || (formVO.getIsXManualEntry() == null ? "0" == null : formVO.getIsXManualEntry().equals("0"))
                || (formVO.getIsYManualEntry() == null ? "0" == null : formVO.getIsYManualEntry().equals("0"))
                || (formVO.getIsSet4ManualEntry() == null ? "0" == null : formVO.getIsSet4ManualEntry().equals("0"))
                || (formVO.getIsSet5ManualEntry() == null ? "0" == null : formVO.getIsSet5ManualEntry().equals("0"))) {
            System.out.println("-- Inside setting file uploaded data --for X or Y or Z or Set 4 ---------");
            if (list.size() > 0 && !list.isEmpty()) {
                //file upload -get data from session
                for (ArticleMcVo vo : list) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(vo.getArticleCode());
                    articleVO.setArtDesc(vo.getArticleDesc());
                    articleVO.setMcCode(vo.getMcCode());
                    articleVO.setMcDesc(vo.getMcDesc());
                    articleVO.setQty(vo.getQty());
                    System.out.println("X Value : " + vo.isIs_x() + " --" + vo.getArticleCode());
                    System.out.println("Y Value : " + vo.isIs_y() + " --" + vo.getArticleCode());
                    System.out.println("Z Value : " + vo.isIs_z() + " --" + vo.getArticleCode());
                    articleVO.setIsX(vo.isIs_x());
                    articleVO.setIsY(vo.isIs_y());
                    articleVO.setIsZ(vo.isIs_z());
                    articleVO.setIsA(vo.isIs_a());
                    articleVO.setIsB(vo.isIs_b());
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsXManualEntry() == null ? "1" == null : formVO.getIsXManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual X Data -------");
            if (formVO.getManualXArticleData() != null) {
                String[] articleGridData = formVO.getManualXArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(true);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(false);
                    articleVO.setIsB(false);
                    System.out.println("grid data : " + articleGridData[(i * 5) + 4]);
                    System.out.println(" Actual :" + articleVO.getQty());
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }


        if (formVO.getIsYManualEntry() == null ? "1" == null : formVO.getIsYManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual Y Data -------");
            if (formVO.getManualYArticleData() != null) {
                String[] articleGridData = formVO.getManualYArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(true);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(false);
                    articleVO.setIsB(false);
                    System.out.println("grid data : " + articleGridData[(i * 5) + 4]);
                    System.out.println(" Actual :" + articleVO.getQty());
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsZManualEntry() == null ? "1" == null : formVO.getIsZManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual Z Data -------");
            if (formVO.getZarticleGridData() != null) {
                String[] articleGridData = formVO.getZarticleGridData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(true);
                    articleVO.setIsA(false);
                    articleVO.setIsB(false);
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsSet4ManualEntry() == null ? "1" == null : formVO.getIsSet4ManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual Set 4 Data -------");
            if (formVO.getManualSet4GridData() != null) {
                String[] articleGridData = formVO.getManualSet4GridData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(true);
                    articleVO.setIsB(false);
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        if (formVO.getIsSet5ManualEntry() == null ? "1" == null : formVO.getIsSet5ManualEntry().equals("1")) {
            System.out.println("------ Inside seting manual Set 5 Data -------");
            if (formVO.getManualSet5GridData() != null) {
                String[] articleGridData = formVO.getManualSet5GridData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(false);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    articleVO.setIsA(false);
                    articleVO.setIsB(true);
                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }

        return promoVO;
    }

    public static TransPromoVO getCreateManualBXGYPromo(BxGyFormVo formVO) throws ParseException {

        TransPromoVO promoVO = new TransPromoVO();
        TransPromoConfigVO configVO = new TransPromoConfigVO();
        TransPromoArticleVO articleVO;

        promoVO.setMstPromoId(new Long(formVO.getMstPromoId()));
        promoVO.setPromoTypeId(WebConstants.BXGY);
        promoVO.setBuyQty(Integer.parseInt(formVO.getTxtbuy()));
        promoVO.setGetQty(Integer.parseInt(formVO.getTxtget()));
        promoVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVO.setRemark(formVO.getTxtRemarks());
        System.out.println("Get qty :" + promoVO.getGetQty() + " buy qty : " + promoVO.getBuyQty());


        if (formVO.getDiscountConfig().equalsIgnoreCase("0")) {
            configVO.setDiscConfig("Value off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("1")) {
            configVO.setDiscConfig("Percentage Off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("2")) {
            configVO.setDiscConfig("Flat Price");
        }
        //configVO.setDiscConfig(formVO.getDiscountConfig());
        configVO.setDiscValue(Double.parseDouble(formVO.getDiscountConfigValue()));
        configVO.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
        configVO.setSellThruQty(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        configVO.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
        configVO.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
        configVO.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        configVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        configVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVO.getTransPromoConfigList().add(configVO);


        if (formVO.getIsManualEntry() == null ? "1" == null : formVO.getIsManualEntry().equals("1")) {
            if (formVO.getManualArticleData() != null) {
                String[] articleGridData = formVO.getManualArticleData().split(",");
                for (int i = 0; i < articleGridData.length / 5; i++) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(articleGridData[(i * 5)]);
                    articleVO.setArtDesc(articleGridData[(i * 5) + 1]);
                    articleVO.setMcCode(articleGridData[(i * 5) + 2]);
                    articleVO.setMcDesc(articleGridData[(i * 5) + 3]);
                    articleVO.setQty(Integer.parseInt(articleGridData[(i * 5) + 4]));
                    articleVO.setIsX(true);
                    articleVO.setIsY(false);
                    articleVO.setIsZ(false);
                    System.out.println("grid data : " + articleGridData[(i * 5) + 4]);
                    System.out.println(" Actual :" + articleVO.getQty());

                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }



        return promoVO;
    }

    public static TransPromoVO getCreateFileBXGYPromo(BxGyFormVo formVO, List<com.fks.promo.master.service.ValidateArticleMCVO> list) throws ParseException {

        TransPromoVO promoVO = new TransPromoVO();
        TransPromoConfigVO configVO = new TransPromoConfigVO();
        TransPromoArticleVO articleVO;

        promoVO.setMstPromoId(new Long(formVO.getMstPromoId()));
        promoVO.setPromoTypeId(WebConstants.BXGY);
        promoVO.setBuyQty(Integer.parseInt(formVO.getTxtbuy()));
        promoVO.setGetQty(Integer.parseInt(formVO.getTxtget()));
        System.out.println("Get qty :" + promoVO.getGetQty() + " buy qty : " + promoVO.getBuyQty());


        if (formVO.getDiscountConfig().equalsIgnoreCase("0")) {
            configVO.setDiscConfig("Value off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("1")) {
            configVO.setDiscConfig("Percentage Off");
        } else if (formVO.getDiscountConfig().equalsIgnoreCase("2")) {
            configVO.setDiscConfig("Flat Price");
        }
        //configVO.setDiscConfig(formVO.getDiscountConfig());
        configVO.setDiscValue(Double.parseDouble(formVO.getDiscountConfigValue()));
        configVO.setTicketSizeGrowth(Double.parseDouble(formVO.getTicketSizeGrowth()));
        configVO.setSellThruQty(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        configVO.setMarginAchievement(Double.parseDouble(formVO.getMarginAchivement()));
        configVO.setGrowthCoversion(Double.parseDouble(formVO.getConversionGrowth()));
        configVO.setSalesGrowth(Double.parseDouble(formVO.getQtyValueSellGrowth()));
        configVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        configVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));

        promoVO.setValidFrom(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidFrom())));
        promoVO.setValidTo(PromotionUtil.formatDB.format(PromotionUtil.format.parse(formVO.getValidTo())));
        promoVO.getTransPromoConfigList().add(configVO);

//        System.out.println("Valid From Date : " + configVO.getValidFrom());
//        System.out.println("Valid To Date : " + configVO.getValidTo());

        if (formVO.getIsManualEntry() == null ? "0" == null : formVO.getIsManualEntry().equals("0")) {
            if (list.size() > 0 && !list.isEmpty()) {
                //file upload -get data from session
                for (com.fks.promo.master.service.ValidateArticleMCVO vo : list) {
                    articleVO = new TransPromoArticleVO();
                    articleVO.setArtCode(vo.getArticleCode());
                    articleVO.setArtDesc(vo.getArticleDesc());
                    articleVO.setMcCode(vo.getMcCode());
                    articleVO.setMcDesc(vo.getMcDesc());
                    articleVO.setQty(vo.getQty());
                    articleVO.setIsX(true);

                    promoVO.getTransPromoArticleList().add(articleVO);
                }
            }
        }



        return promoVO;
    }
}
