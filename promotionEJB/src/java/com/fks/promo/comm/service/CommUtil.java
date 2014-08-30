/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.comm.service;

import com.fks.promo.comm.vo.CommVO;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.entity.MapPromoCity;
import com.fks.promo.entity.MapPromoRegion;
import com.fks.promo.entity.MapPromoState;
import com.fks.promo.entity.MapPromoStore;
import com.fks.promo.entity.MapPromoZone;
import com.fks.promo.entity.TransPromo;
import com.fks.promo.entity.TransPromoArticle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Paresb
 */
public class CommUtil {

    public static List<CommVO> getCommVOList(List<TransPromo> list) {
        List<CommVO> volist = new ArrayList<CommVO>();
        for (TransPromo promo : list) {
            CommVO vo = new CommVO();
            if (promo.getCashierTrigger() != null) {
                vo.setCashierTriggerCode(promo.getCashierTrigger());
            }
            vo.setTransPromoId(promo.getTransPromoId());
            vo.setEventType(promo.getMstPromo().getMstEvent().getEventName());
            vo.setPromoDetails(promo.getPromoDetails());
            vo.setPromoType(promo.getMstPromotionType().getPromoTypeName());
            vo.setValidFrom(promo.getValidFrom().toString());
            vo.setValidTo(promo.getValidTo().toString());
            vo.setCategory(promo.getMstPromo().getCategory());
            vo.setSubCategory(promo.getMstPromo().getSubCategory());
            vo.setMstPromoId(promo.getMstPromo().getPromoId().toString());
            vo.setBonusBy(promo.getBonusBuy());
            Collection<MapPromoCity> citylist = promo.getMstPromo().getMapPromoCityCollection();            
            if (!citylist.isEmpty() && citylist.size() > 0) {
                StringBuilder sbBufferCity = new StringBuilder();
                for (MapPromoCity voCity : citylist) {
                    sbBufferCity.append(voCity.getCityDesc()).append(",");
                }
                String sbcity = sbBufferCity.substring(0, sbBufferCity.lastIndexOf(","));
                vo.setCity(sbcity);
            } else {
                vo.setCity("-");
            }

            Collection<MapPromoStore> sitelist = promo.getMstPromo().getMapPromoStoreCollection();
            //System.out.println("Site size : " + sitelist.size());
            if (!sitelist.isEmpty() && sitelist.size() > 0) {
                StringBuilder sbBuffer = new StringBuilder();
                for (MapPromoStore vos : sitelist) {
                    sbBuffer.append(vos.getMstStore().getMstStoreId()).append(",");
                }
                String sbsite = sbBuffer.substring(0, sbBuffer.lastIndexOf(","));

                vo.setSite(sbsite);
            } else {
                vo.setSite("-");
            }

            Collection<MapPromoState> stateList = promo.getMstPromo().getMapPromoStateCollection();            
            if (!stateList.isEmpty() && stateList.size() > 0) {
                StringBuilder sbBuffer = new StringBuilder();
                for (MapPromoState vos : stateList) {
                    sbBuffer.append(vos.getStateDesc()).append(",");
                }
                String sbsite = sbBuffer.substring(0, sbBuffer.lastIndexOf(","));

                vo.setState(sbsite);
            } else {
                vo.setState("-");
            }

            Collection<MapPromoRegion> regionList = promo.getMstPromo().getMapPromoRegionCollection();           
            if (!regionList.isEmpty() && regionList.size() > 0) {
                StringBuilder sbBuffer = new StringBuilder();
                for (MapPromoRegion vos : regionList) {
                    sbBuffer.append(vos.getRegionName()).append(",");
                }
                String sbsite = sbBuffer.substring(0, sbBuffer.lastIndexOf(","));
                vo.setRegion(sbsite);
            } else {
                vo.setRegion("-");
            }

            Collection<MapPromoZone> zoneList = promo.getMstPromo().getMapPromoZoneCollection();            
            if (!zoneList.isEmpty() && zoneList.size() > 0) {
                StringBuffer sbBuffer = new StringBuffer();
                for (MapPromoZone vos : zoneList) {
                    sbBuffer.append(vos.getMstZone().getZoneName()).append(",");
                }
                String sbsite = sbBuffer.substring(0, sbBuffer.lastIndexOf(","));
                vo.setZone(sbsite);
            } else {
                vo.setZone("-");
            }
            volist.add(vo);
        }
        return volist;
    }

    public static String promoCommunicationFileDownload(TransPromo subPromo) {
        try {
            String LINE_SEPARATOR = System.getProperty("line.separator");
            String COMMA_SEPARATOR = ",";

            String filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.COMM_FILE);
            String fileName = "Communication_" + subPromo.getTransPromoId() + ".csv";
            String downloadFilePath = filePath + fileName;

            BufferedWriter writer = new BufferedWriter(new FileWriter(downloadFilePath));
            File statusFile = new File(downloadFilePath);
            statusFile.createNewFile();
            StringBuilder sb = new StringBuilder();
            sb.append("Article No").append(COMMA_SEPARATOR);
            sb.append("Article Description").append(COMMA_SEPARATOR);
            sb.append("MC Code").append(COMMA_SEPARATOR);
            sb.append("MC Description").append(COMMA_SEPARATOR);
            sb.append("Article/Mc Qty").append(COMMA_SEPARATOR);
            sb.append(LINE_SEPARATOR);

            Collection<TransPromoArticle> listArticle = subPromo.getTransPromoArticleCollection();
            for (TransPromoArticle vo : listArticle) {
                sb.append(vo.getArticle()).append(COMMA_SEPARATOR);
                sb.append(vo.getArticleDesc()).append(COMMA_SEPARATOR);
                sb.append(vo.getMcCode()).append(COMMA_SEPARATOR);
                sb.append(vo.getMcDesc()).append(COMMA_SEPARATOR);
                sb.append(vo.getQty()).append(COMMA_SEPARATOR);
                sb.append(LINE_SEPARATOR);
            }
            sb.append(LINE_SEPARATOR);
            writer.write(sb.toString());
            writer.flush();
            if (writer != null) {
                writer.close();
            }
            System.out.println("Download File Path = " + downloadFilePath);
            System.out.println("====== File Writing is Completed ===== ");
            return downloadFilePath;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ("Exception : " + ex.getMessage());

        }

    }
}
