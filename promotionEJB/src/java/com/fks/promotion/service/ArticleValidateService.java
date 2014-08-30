/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.service;

import com.eks.ods.article.ArticleSearch;
import com.eks.ods.article.vo.ArticleVO;
import com.eks.ods.article.vo.RespCode;
import com.eks.ods.article.vo.SearchArticleResp;
import com.fks.promotion.vo.ValidateArticleMCVO;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.common.Resp;
import com.fks.promo.commonDAO.CommonDAO;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.entity.MstProposal;
import com.fks.promo.entity.TransProposal;
import com.fks.promo.facade.MstProposalFacade;
import com.fks.promo.facade.TransProposalFacade;
import com.fks.promo.master.service.OtherMasterService;
import com.fks.promo.master.vo.ValidateMCResp;
import com.fks.promotion.vo.CreateProposalResp;
import com.fks.promotion.vo.ValidateArticleMCResp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author ajitn
 */
@Stateless
@LocalBean
public class ArticleValidateService {

    private static final Logger logger = Logger.getLogger(ArticleValidateService.class.getName());
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String COMMA_SEPARATOR = ",";
    @EJB
    OtherMasterService otherService;
    @EJB
    MstProposalFacade mstProposalFacade;
    @EJB
    TransProposalFacade proposalTrnsFacade;
    @EJB
    ArticleSearch odsArticleSearch;
    @EJB
    CommonDAO commonDao;

    public CreateProposalResp validateProposalArticleListFromFile(MstProposal proposal, String filePath) {
        logger.info("------- Inside Validating ODS Article Service--------");
        List<ValidateArticleMCVO> validArticleMCList = new ArrayList<ValidateArticleMCVO>();
        boolean isErrorFile = false;
        FileInputStream fileIn = null;
        try {
            String statusFilePath = null;
            File articleFile = new File(filePath);
            fileIn = new FileInputStream(articleFile);
            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            String articleCode, mcCode;
            int counter = 3;
            //Skip First Message Line
            strLine = br.readLine();
            System.out.println("1st Line :" + strLine);
            //Skip Space Line
            strLine = br.readLine();
            System.out.println("2nd Line :" + strLine);
            //Skip Header Line
            strLine = br.readLine();
            System.out.println("3rd Line :" + strLine);
            while ((strLine = br.readLine()) != null) {
                String[] strValue = strLine.split(",");

                //Check User Value Validations
                counter++;

                if (strValue.length >= 1 && strValue[0] != null) {
                    articleCode = strValue[0].trim().toString();
                    if (articleCode.length() > 0) {
                        logger.info("-------- Validate Article Code : " + articleCode);
                        SearchArticleResp serviceResp = odsArticleSearch.searchArticle(articleCode);
                        if (serviceResp.getCode().getCode() == RespCode.SUCCESS) {
                            if (serviceResp.getVo() != null && serviceResp.getVo().size() > 0) {
                                ArticleVO article = serviceResp.getVo().get(0);
                                validArticleMCList.add(new ValidateArticleMCVO(false, "SUCESS", article.getArticleCode(), article.getArticleDesc(), article.getMcCode(), article.getMcDesc(), article.getBrand(), article.getBrandDesc()));
                            } else {
                                isErrorFile = true;
                                validArticleMCList.add(new ValidateArticleMCVO(true, "Article Not Found.", articleCode, "-", "-", "-", "-", "-"));
                            }

                        } else {
                            isErrorFile = true;
                            validArticleMCList.add(new ValidateArticleMCVO(true, serviceResp.getCode().getMsg(), articleCode, "-", "-", "-", "-", "-"));
                        }
                    }

                }

                if (strValue.length == 2 && strValue[1] != null) {
                    mcCode = strValue[1].trim().toString();
                    logger.info("-------- Validate MC Code : " + mcCode);
                    ValidateMCResp mcResp = otherService.validateMC(mcCode, null, "0");
                    if (mcResp.getResp().getRespCode() == com.fks.promo.common.RespCode.SUCCESS) {
                        validArticleMCList.add(new ValidateArticleMCVO(false, "SUCEESS", "-", "-", mcResp.getMcCode(), mcResp.getMcDesc(), "-", "-"));
                    } else {
                        isErrorFile = true;
                        validArticleMCList.add(new ValidateArticleMCVO(true, "MC Not Found.", "-", "-", mcCode, "-", "-", "-"));
                    }
                }
            }
            logger.info("--------- Proposal Article File Validated Successfully.");


            if (isErrorFile == true) {

                logger.info("----------Creating Status File. --------");
                String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ERROR_FILE);
//                logger.info("--------------Error File Path : " + errorFilePath);
                String fileName = "Request_" + proposal.getProposalId() + "_ArticleCode_MCCode_Status_File.csv";
                statusFilePath = errorFilePath + fileName;
//                logger.info("------------ Status File Path : " + statusFilePath);
                BufferedWriter writer = new BufferedWriter(new FileWriter(statusFilePath));
                File statusFile = new File(statusFilePath);
                statusFile.createNewFile();
                writer.write("ARTICLE CODE,ARTICLE DESC,MC CODE,MC DESC,STATUS,MESSAGE");
                writer.write(LINE_SEPARATOR);
                writer.write(LINE_SEPARATOR);
                for (ValidateArticleMCVO vo : validArticleMCList) {
                    if (vo.isIsErrorStatus() == true) {
                        writer.write(COMMA_SEPARATOR + vo.getArticleCode() + COMMA_SEPARATOR + vo.getArticleDesc() + COMMA_SEPARATOR + vo.getMcCode() + COMMA_SEPARATOR + vo.getMcDesc() + "FAILURE" + COMMA_SEPARATOR + vo.getErrorMsg());
                    } else {
                        writer.write(vo.getErrorMsg() + COMMA_SEPARATOR + vo.getArticleCode() + COMMA_SEPARATOR + vo.getArticleDesc() + COMMA_SEPARATOR + vo.getMcCode() + COMMA_SEPARATOR + vo.getMcDesc() + "SUCCESS" + COMMA_SEPARATOR);
                    }
                    writer.write(LINE_SEPARATOR);
                }

                writer.flush();
                if (writer != null) {
                    writer.close();
                }
                logger.info("====== File Writing is Completed ===== ");

                proposal.setFileStatus(false);
                return (new CreateProposalResp(new Resp(com.fks.promo.common.RespCode.FAILURE, "File Validated With Failure.", proposal.getProposalId()), statusFilePath));

            } else {
                logger.info("----------All Article/MC Are Valid. Store In DB. --------");
                proposal.setFileStatus(true);
                int delArticleCnt = commonDao.deleteTransProposalDtl(proposal.getProposalId());
                logger.info("----------Proposal Articles Deleted successfully. Count : " + delArticleCnt);
                TransProposal proposalTrns = null;
                for (ValidateArticleMCVO vo : validArticleMCList) {
                    proposalTrns = new TransProposal();
                    proposalTrns.setMcCode(vo.getMcCode());
                    proposalTrns.setMcDesc(vo.getMcDesc());
                    proposalTrns.setMstProposal(proposal);
                    proposalTrns.setArtCode(vo.getArticleCode());
                    proposalTrns.setArtDesc(vo.getArticleDesc());

                    proposalTrnsFacade.create(proposalTrns);
                }
                return (new CreateProposalResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Promotion proposal created successfully.", proposal.getProposalId()), statusFilePath));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ValidateArticleMCResp validateInitiationODSArticleListFromFileWithoutQty(String filePath, String empID, Map<String, String> mcMap, MstPromo promo) {
        logger.info("------- Inside Validating ODS Article Service--------");
        List<ValidateArticleMCVO> validArticleMCList = new ArrayList<ValidateArticleMCVO>();
        boolean isErrorFile = false;
        FileInputStream fileIn = null;
        try {
            String statusFilePath = null;
            File articleFile = new File(filePath);
            fileIn = new FileInputStream(articleFile);
            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            String articleCode, mcCode;
            int counter = 3;
            //Skip First Message Line
            strLine = br.readLine();
            System.out.println("1st Line :" + strLine);
            //Skip Space Line
            strLine = br.readLine();
            System.out.println("2nd Line :" + strLine);
            //Skip Header Line
            strLine = br.readLine();
            System.out.println("3rd Line :" + strLine);
            while ((strLine = br.readLine()) != null) {
                String[] strValue = strLine.split(",");
                //Check User Value Validations
                counter++;

                if (strValue.length >= 1 && strValue[0] != null && strValue[0].trim().toString().length() > 0) {
                    articleCode = strValue[0].trim().toString();
                    if (articleCode.length() > 0) {
                        logger.info("-------- Validate Article Code : " + articleCode);
                        SearchArticleResp serviceResp = odsArticleSearch.searchArticle(articleCode);
                        if (serviceResp.getCode().getCode() == RespCode.SUCCESS) {
                            if (serviceResp.getVo() != null && serviceResp.getVo().size() > 0) {
                                ArticleVO article = serviceResp.getVo().get(0);
                                if (mcMap != null && mcMap.containsKey(article.getMcCode()) == false) {
                                    isErrorFile = true;
                                    validArticleMCList.add(new ValidateArticleMCVO(true, "Article Code : " + articleCode + " Does Not Belong To Category Name / Sub Category Name : " + promo.getCategory() + " / " + promo.getSubCategory() + ".", article.getArticleCode(), article.getArticleDesc(), article.getMcCode(), article.getMcDesc(), article.getBrand(), article.getBrandDesc()));
                                } else {
                                    validArticleMCList.add(new ValidateArticleMCVO(false, "SUCESS", article.getArticleCode(), article.getArticleDesc(), article.getMcCode(), article.getMcDesc(), article.getBrand(), article.getBrandDesc()));
                                }

                            } else {
                                isErrorFile = true;
                                validArticleMCList.add(new ValidateArticleMCVO(true, "Article Not Found.", articleCode, "-", "-", "-", "-", "-"));
                            }

                        } else {
                            isErrorFile = true;
                            validArticleMCList.add(new ValidateArticleMCVO(true, serviceResp.getCode().getMsg(), articleCode, "-", "-", "-", "-", "-"));
                        }
                    }

                }

                if (strValue.length == 2 && strValue[1] != null && strValue[1].trim().toString().length() > 0) {
                    mcCode = strValue[1].trim().toString();
                    logger.info("-------- Validate MC Code : " + mcCode);
                    ValidateMCResp mcResp = otherService.validateMC(mcCode, promo.getPromoId(), "1");
                    if (mcResp.getResp().getRespCode() == com.fks.promo.common.RespCode.SUCCESS) {
                        validArticleMCList.add(new ValidateArticleMCVO(false, "SUCEESS", "-", "-", mcResp.getMcCode(), mcResp.getMcDesc(), "-", "-"));
                    } else {
                        isErrorFile = true;
                        validArticleMCList.add(new ValidateArticleMCVO(true, mcResp.getResp().getMsg(), "-", "-", mcCode, "-", "-", "-"));
                    }
                }
            }
            logger.info("--------- Promotion Initiation Article File Validated Successfully.");


            if (isErrorFile == true) {

                logger.info("----------Creating Status File. --------");
                String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ERROR_FILE);
                logger.info("--------------Error File Path : " + errorFilePath);
                Calendar cal = new GregorianCalendar();
                long timestamp = cal.getTimeInMillis();
                logger.info("------- Time stamp : " + timestamp);
                String fileName = empID + "_" + timestamp + "_ArticleCode_MCCode_Status_File.csv";
                statusFilePath = errorFilePath + fileName;
                logger.info("------------ Status File Path : " + statusFilePath);
                BufferedWriter writer = new BufferedWriter(new FileWriter(statusFilePath));
                File statusFile = new File(statusFilePath);
                statusFile.createNewFile();
                writer.write("ARTICLE CODE,ARTICLE DESC,MC CODE,MC DESC,QTY,STATUS,MESSAGE");
                writer.write(LINE_SEPARATOR);
                writer.write(LINE_SEPARATOR);
                for (ValidateArticleMCVO vo : validArticleMCList) {
                    if (vo.isIsErrorStatus() == true) {
                        writer.write(vo.getArticleCode() + COMMA_SEPARATOR + vo.getArticleDesc() + COMMA_SEPARATOR + vo.getMcCode() + COMMA_SEPARATOR + vo.getMcDesc() + COMMA_SEPARATOR + vo.getQty() + COMMA_SEPARATOR + "FAILURE" + COMMA_SEPARATOR + vo.getErrorMsg());
                    } else {
                        writer.write(vo.getArticleCode() + COMMA_SEPARATOR + vo.getArticleDesc() + COMMA_SEPARATOR + vo.getMcCode() + COMMA_SEPARATOR + vo.getMcDesc() + COMMA_SEPARATOR + vo.getQty() + COMMA_SEPARATOR + "SUCCESS" + COMMA_SEPARATOR + vo.getErrorMsg());
                    }
                    writer.write(LINE_SEPARATOR);
                }

                writer.flush();
                if (writer != null) {
                    writer.close();
                }
                logger.info("====== File Writing is Completed ===== ");

                return (new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.FAILURE, "File Validated With Failure."), validArticleMCList, statusFilePath));

            }
            logger.info("----------All Article/MC Are Valid. --------");
            return (new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "All Articles/MCs are validated successfully."), validArticleMCList, statusFilePath));
//            return (new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Promotion proposal created successfully.",validArticleMCList, statusFilePath));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ValidateArticleMCResp validateInitiationODSArticleListFromFile(String filePath, String empID, Map<String, String> mcMap, MstPromo promo) {
        logger.info("------- Inside Validating ODS Article Service--------");
        List<ValidateArticleMCVO> validArticleMCList = new ArrayList<ValidateArticleMCVO>();
        boolean isErrorFile = false;
        FileInputStream fileIn = null;
        try {
            String statusFilePath = null;
            File articleFile = new File(filePath);
            fileIn = new FileInputStream(articleFile);
            DataInputStream in = new DataInputStream(fileIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null;
            String articleCode, mcCode;
            int counter = 3, qty = 0;
            //Skip First Message Line
            strLine = br.readLine();
            System.out.println("1st Line :" + strLine);
            //Skip Space Line
            strLine = br.readLine();
            System.out.println("2nd Line :" + strLine);
            //Skip Header Line
            strLine = br.readLine();
            System.out.println("3rd Line :" + strLine);
            while ((strLine = br.readLine()) != null) {
                //Check User Value Validations
                counter++;
                String[] strValue = strLine.split(",");
                if (strValue.length == 0) {
                    logger.info("-------continue with No Line Found.");
                    continue;
                } else if (strValue.length >= 2 && strValue[0] == null && strValue[1] == null) {
                    logger.info("-------continue with No Article Code OR MC CODE entered.");
                    continue;
                } else if (strValue[0] != null && strValue[0].trim().toString().length() > 0) {
                    qty = Integer.parseInt(strValue[2]);
                    articleCode = strValue[0].trim().toString();
                    if (articleCode.length() > 0) {
                        logger.info("-------- Validate Article Code : " + articleCode);
                        SearchArticleResp serviceResp = odsArticleSearch.searchArticle(articleCode);
                        if (serviceResp.getCode().getCode() == RespCode.SUCCESS) {
                            if (serviceResp.getVo() != null && serviceResp.getVo().size() > 0) {
                                ArticleVO article = serviceResp.getVo().get(0);
                                if (mcMap != null && mcMap.containsKey(article.getMcCode()) == false) {
                                    isErrorFile = true;
                                    validArticleMCList.add(new ValidateArticleMCVO(true, "Article Code : " + articleCode + " Does Not Belong To Category Name / Sub Category Name : " + promo.getCategory() + " / " + promo.getSubCategory() + ".", article.getArticleCode(), article.getArticleDesc(), article.getMcCode(), article.getMcDesc(), qty, article.getBrand(), article.getBrandDesc()));
                                } else {
                                    validArticleMCList.add(new ValidateArticleMCVO(false, "SUCESS", article.getArticleCode(), article.getArticleDesc(), article.getMcCode(), article.getMcDesc(), qty, article.getBrand(), article.getBrandDesc()));
                                }

                            } else {
                                isErrorFile = true;
                                validArticleMCList.add(new ValidateArticleMCVO(true, "Article Not Found.", articleCode, "-", "-", "-", qty, "-", "-"));
                            }

                        } else {
                            isErrorFile = true;
                            validArticleMCList.add(new ValidateArticleMCVO(true, serviceResp.getCode().getMsg(), articleCode, "-", "-", "-", qty, "-", "-"));
                        }
                    }

                } else if (strValue[1] != null && strValue[1].trim().toString().length() > 0) {
                    mcCode = strValue[1].trim().toString();
                    qty = Integer.parseInt(strValue[2]);
                    logger.info("-------- Validate MC Code : " + mcCode);
                    ValidateMCResp mcResp = otherService.validateMC(mcCode, promo.getPromoId(), "1");
                    if (mcResp.getResp().getRespCode() == com.fks.promo.common.RespCode.SUCCESS) {
                        validArticleMCList.add(new ValidateArticleMCVO(false, "SUCEESS", "-", "-", mcResp.getMcCode(), mcResp.getMcDesc(), qty, "-", "-"));
                    } else {
                        isErrorFile = true;
                        validArticleMCList.add(new ValidateArticleMCVO(true, mcResp.getResp().getMsg(), "-", "-", mcCode, "-", qty, "-", "-"));
                    }
                }
            }
            logger.info("--------- Proposal Article File Validated Successfully.");


            if (isErrorFile == true) {

                logger.info("----------Creating Status File. --------");
                String errorFilePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.PROPOSAL_ERROR_FILE);
                logger.info("--------------Error File Path : " + errorFilePath);
                Calendar cal = new GregorianCalendar();
                long timestamp = cal.getTimeInMillis();
                logger.info("------- Time stamp : " + timestamp);
                String fileName = empID + "_" + timestamp + "_ArticleCode_MCCode_Status_File.csv";
                statusFilePath = errorFilePath + fileName;
                logger.info("------------ Status File Path : " + statusFilePath);
                BufferedWriter writer = new BufferedWriter(new FileWriter(statusFilePath));
                File statusFile = new File(statusFilePath);
                statusFile.createNewFile();
                writer.write("ARTICLE CODE,ARTICLE DESC,MC CODE,MC DESC,QTY,STATUS,MESSAGE");
                writer.write(LINE_SEPARATOR);
                writer.write(LINE_SEPARATOR);
                for (ValidateArticleMCVO vo : validArticleMCList) {
                    if (vo.isIsErrorStatus() == true) {
                        writer.write(vo.getArticleCode() + COMMA_SEPARATOR + vo.getArticleDesc() + COMMA_SEPARATOR + vo.getMcCode() + COMMA_SEPARATOR + vo.getMcDesc() + COMMA_SEPARATOR + vo.getQty() + COMMA_SEPARATOR + "FAILURE" + COMMA_SEPARATOR + vo.getErrorMsg());
                    } else {
                        writer.write(vo.getArticleCode() + COMMA_SEPARATOR + vo.getArticleDesc() + COMMA_SEPARATOR + vo.getMcCode() + COMMA_SEPARATOR + vo.getMcDesc() + COMMA_SEPARATOR + vo.getQty() + COMMA_SEPARATOR + "SUCCESS" + COMMA_SEPARATOR + vo.getErrorMsg());
                    }
                    writer.write(LINE_SEPARATOR);
                }

                writer.flush();
                if (writer != null) {
                    writer.close();
                }
                logger.info("====== File Writing is Completed ===== ");

                return (new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.FAILURE, "File Validated With Failure."), validArticleMCList, statusFilePath));

            }
            logger.info("----------All Article/MC Are Valid. --------");
            return (new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "All Articles/MCs are validated successfully."), validArticleMCList, statusFilePath));
//            return (new ValidateArticleMCResp(new Resp(com.fks.promo.common.RespCode.SUCCESS, "Promotion proposal created successfully.",validArticleMCList, statusFilePath));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
