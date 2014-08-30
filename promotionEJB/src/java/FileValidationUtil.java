
import com.fks.ods.service.ODSService;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.init.vo.TransPromoArticleVO;
import com.fks.promo.init.vo.TransPromoConfigVO;
import com.fks.promo.init.vo.TransPromoVO;
import com.fks.promo.master.service.OtherMasterService;
import com.fks.promo.master.vo.ValidateMCResp;
import com.fks.promotion.vo.ValidateArticleMCVO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Paresb
 */
@Stateless
public class FileValidationUtil {

    @EJB
    private ODSService search;
    @EJB
    private OtherMasterService otherSearch;

    public static void main(String[] args) throws Exception {

        validateFile("c:/test.csv");

    }

    public static String validateFile(String fileName) throws Exception {
//        BufferedReader bis = new BufferedReader(new FileReader(fileName));        
        int lastIndex = fileName.lastIndexOf("/");
        String dirName = fileName.substring(0, lastIndex);
        String file = fileName.substring(lastIndex + 1);
        int csvIndex = fileName.indexOf(".csv");
        System.out.println(" Directory Name " + dirName);
        System.out.println(" File Name " + file);
        String errorFileName = fileName.substring(lastIndex + 1, csvIndex) + "_error.csv";
        System.out.println(" Error File Name " + errorFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(dirName + "/" + errorFileName));
        if (writer == null) {
            throw new Exception("File Name is Not Correct " + fileName);
        }
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String str = null;
        List<String> list = new ArrayList<String>();
        while ((str = reader.readLine()) != null) {
            System.out.println("Line " + str);
            list.add(str);
        }
        if (list.size() < 5) {
            System.out.println("No. of Lines Must be atleast four");
            writer.write("Each Promotion Must Have Atleast Header Promo Type Article/MC and Configuration Parameters");
            writer.close();
            return dirName + "/" + errorFileName;
        }
        int counter = 1;
        for (String l : list) {
            StringTokenizer tokenizer = new StringTokenizer(l, ",");
            System.out.println("Checking Line " + l);
            System.out.println(tokenizer.countTokens());
            if (counter == 1 && tokenizer.countTokens() != 20) {
                writer.write("Line No. " + counter + " is Not Correct");
                System.out.println("Line No. " + counter + " is Not Correct");
                writer.close();
                return dirName + "/" + errorFileName;
            }
            if (counter == 2 && tokenizer.countTokens() != 2) {
                writer.write("Line No. " + counter++ + " is Not Correct");
                System.out.println("Line No. " + counter + " is Not Correct");
                writer.close();
                return dirName + "/" + errorFileName;
            } else {
            }
            if (counter == 3 && tokenizer.countTokens() <= 3 && tokenizer.countTokens() >= 7) {
            }
            counter++;
        }
        if (writer != null) {
            writer.close();
        }
        if (reader != null) {
            reader.close();
        }
        return dirName + "/" + errorFileName;
    }

    public int getTransPromoType(String promoTypeName) {
        if (promoTypeName == null || promoTypeName.trim().length() == 0) {
            return 0;
        }
        if (promoTypeName.trim().equalsIgnoreCase("bxgx")) {
            return 7;
        } else if (promoTypeName.trim().equalsIgnoreCase("Buy X Get Y Discount At Set Level")) {
            return 1;
        } else if (promoTypeName.trim().equalsIgnoreCase("Buy X and Y @ Discounted price")) {
            return 2;
        } else if (promoTypeName.trim().equalsIgnoreCase("Flat Discount")) {
            return 3;
        } else if (promoTypeName.trim().equalsIgnoreCase("Power Pricing")) {
            return 4;
        } else if (promoTypeName.trim().equalsIgnoreCase("Ticket Size ( Bill Level)")) {
            return 5;
        } else if (promoTypeName.trim().equalsIgnoreCase("Ticket Size ( Pool Reward)")) {
            return 6;
        }
        return 0;
    }

    public TransPromoConfigVO getTransPromoConfigVO(String str, int transPromoType, MstPromo mstPromop, int lineno, TransPromoVO transPromoVO) {
        TransPromoConfigVO vo = new TransPromoConfigVO();
        String[] tokenStrings = str.split(",");
        String validFrom = "";
        String validTo = "";
        String marginAchievement = "";
        String growthTicketSize = "";
        String sellThru = "";
        String growthInConversion = "";
        String sellGrowth = "";
        String discConfig = "";
        String discConfigValue = "";
        String discConfigQty = "";
        String buyWorthAmt = "";
        String buy = "";
        String get = "";
        String set = "";
        set = tokenStrings[2];
        validFrom = tokenStrings[6];
        validTo = tokenStrings[7];
        marginAchievement = tokenStrings[8];
        growthTicketSize = tokenStrings[9];
        sellThru = tokenStrings[10];
        growthInConversion = tokenStrings[11];
        sellGrowth = tokenStrings[12];

        if (tokenStrings.length == 19) {
            discConfig = tokenStrings[13];
            discConfigValue = tokenStrings[14];
            discConfigQty = tokenStrings[15];
            buyWorthAmt = tokenStrings[16];           
            buy = tokenStrings[17];
            get = tokenStrings[18];            
        } else if (tokenStrings.length == 15) {
            discConfig = tokenStrings[13];
            discConfigValue = tokenStrings[14];            
        } 
        
        if(!set.trim().isEmpty()){
            try {
                int setInt = Integer.parseInt(set);
                vo.setSetId(setInt);
            } catch(Exception e){
                e.printStackTrace();
                vo.setErrorMessage("Set Value must be Numberic.");
            }
        }     
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(!validFrom.trim().isEmpty()){
            try {
                Date fromDate = format.parse(validFrom);
                vo.setValidFrom(validFrom);
            } catch(Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Incorrect Date Format for Valid From.");
            }
        }
        if(!validTo.trim().isEmpty()){
            try {
                Date toDate = format.parse(validTo);
                vo.setValidTo(validTo);
            } catch(Exception e) {
                e.printStackTrace();
                vo.setErrorMessage("Incorrect Date Format for Valid To.");
            }
        }
        if(!marginAchievement.trim().isEmpty()){
            try {
            double dMarginAchievement = Double.parseDouble(marginAchievement);
            vo.setMarginAchievement(dMarginAchievement);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!growthTicketSize.trim().isEmpty()){
            try {
            double dgrowthTicketSize = Double.parseDouble(growthTicketSize);
            vo.setTicketSizeGrowth(dgrowthTicketSize);            
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!sellThru.trim().isEmpty()){
            try {
            int dsellThru = Integer.parseInt(sellThru);
            vo.setSellThruQty(dsellThru);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!growthInConversion.trim().isEmpty()){
            try {
            double dgrowthInConversion = Double.parseDouble(growthInConversion);
            vo.setGrowthCoversion(dgrowthInConversion);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!sellGrowth.trim().isEmpty()){
            try {
            double dsellGrowth = Double.parseDouble(sellGrowth);
            vo.setSalesGrowth(dsellGrowth);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(!discConfig.trim().isEmpty()){                        
            vo.setDiscConfig(discConfig);            
        }
        
        if(!discConfigQty.trim().isEmpty()){
            try {
            int idiscConfigQty = Integer.parseInt(discConfigQty);
            vo.setQty(idiscConfigQty);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(!discConfigValue.trim().isEmpty()){
            try {
            double idiscConfigValue = Double.parseDouble(discConfigValue);
            vo.setDiscValue(idiscConfigValue);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(!buy.trim().isEmpty()){
            try {
            int ibuy = Integer.parseInt(buy);
            transPromoVO.setBuyQty(ibuy);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(!get.trim().isEmpty()){
            try {
            int ibuy = Integer.parseInt(get);
            transPromoVO.setBuyQty(ibuy);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if(!buyWorthAmt.trim().isEmpty()){
            try {
            double dbuyworth = Double.parseDouble(buyWorthAmt);
            vo.setTicketWorthAmt(dbuyworth);
            } catch(Exception e){
                e.printStackTrace();
            }
        }      
        
        return vo;

    }

    public TransPromoArticleVO getTransPromoArticleVO(String str, int transPromoType, MstPromo mstPromo, int lineno) {
        TransPromoArticleVO vo = new TransPromoArticleVO();
        String[] tokenizer = str.split(",");
        String article = "";
        String mcCode = "";
        String qty = "";
        String set = "";
        if (tokenizer.length == 4) {
            set = tokenizer[2];
            article = tokenizer[3];
        } else if (tokenizer.length == 5) {
            set = tokenizer[2];
            mcCode = tokenizer[4];
        } else if (tokenizer.length == 6) {
            set = tokenizer[2];
            article = tokenizer[3];
            mcCode = tokenizer[4];
            qty = tokenizer[5];
        }
        if (qty != null) {
            try {
                int q = Integer.parseInt(qty);
                vo.setQty(q);
            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Qty Must be Numberic");
            }
        }
        if (article.trim().isEmpty() && mcCode.trim().isEmpty() && qty.trim().isEmpty() && set.trim().isEmpty()) {
            vo.setErrMsg("Incorrect Line No. " + lineno);
        }

        if (article != null) {
            try {
                int a = Integer.parseInt(article);
                ValidateArticleMCVO articleMCVO = search.searchODSArticle(article, mstPromo.getPromoId(), "1");
                if (!articleMCVO.isIsErrorStatus()) {
                    vo.setArtCode(String.valueOf(a));
                    vo.setArtDesc(articleMCVO.getArticleDesc());
                    vo.setMcCode(articleMCVO.getMcCode());
                    vo.setMcDesc(articleMCVO.getMcDesc());
                } else {
                    vo.setErrMsg(articleMCVO.getErrorMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Article Code Must be Numberic");
            }
        } else {
            try {
                int m = Integer.parseInt(mcCode);
                ValidateMCResp mCResp = otherSearch.validateMC(mcCode, mstPromo.getPromoId(), "1");
                if (mCResp.getResp().getRespCode() == com.fks.promo.common.RespCode.SUCCESS) {
                    vo.setMcCode(mCResp.getMcCode());
                    vo.setMcDesc(mCResp.getMcDesc());
                } else {
                    vo.setErrMsg(mCResp.getResp().getMsg());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vo.setErrMsg("Article Code Must be Numberic");
            }
        }
        return vo;

    }

    public static String checkHeader(String header) {
        StringTokenizer tokenizer = new StringTokenizer(header, ",");
        int count = tokenizer.countTokens();
        if (count != 20) {
            System.out.println("Error !!! No. of Columns are Not equals to 20 for #### " + header);
            return "Invalid Header";
        }
        return null;
    }
}
