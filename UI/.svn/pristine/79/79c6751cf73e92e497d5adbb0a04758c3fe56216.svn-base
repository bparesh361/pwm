/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;

import com.fks.ui.master.vo.StausVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krutij
 */
public class PromotionUtil {

    public static final SimpleDateFormat formatDB = new SimpleDateFormat("yyyy-MM-dd ");
    public static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public static StausVO validatePageNo(String PageNo) {

        //DO NOT TAKE CARE FOR NEGATIVE VALUES : JQGRID AUTOMATICALLY CONVERTS -VE VALUE TO +VA

        StausVO responseVo = null;
        try {
            responseVo = new StausVO();
            responseVo.setStatusFlag(true);
            responseVo.setStatusMsg("VALID PAGE NUMBER");

            //CONVERT LONG VALUE TO SHORT
            if (PageNo == null) {
                responseVo.setStatusFlag(false);
                responseVo.setStatusMsg("Page Number is null");
            } else {
                Short shortPage = new Short(PageNo);
                if (shortPage < 0) {
                    responseVo.setStatusFlag(false);
                    responseVo.setStatusMsg("To Much Large Page Number");
                }
            }

        } catch (Exception ex) {
            //logger.fatal("Eception generated during validating the Page Number : Exception is :" + ex.getMessage());
            responseVo.setStatusFlag(false);
            responseVo.setStatusMsg(ex.getMessage());
        }
        return responseVo;
    }

    public static Map<String, String> sortMapByValue(Map<String, String> map) {
        List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {

            public int compare(Map.Entry<String, String> m1, Map.Entry<String, String> m2) {
                return (m1.getValue()).compareToIgnoreCase(m2.getValue());
            }
        });
        Map<String, String> result = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {

        List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {

            public int compare(Map.Entry<String, String> m1, Map.Entry<String, String> m2) {
                return (m1.getKey()).compareTo(m2.getKey());
            }
        });
        Map<String, String> result = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static String getFileNameWithoutSpace(String fileName) {
        if (fileName != null) {
//            fileName = fileName.replaceAll(" ", "_").toString();
            int lastDotIndex = fileName.lastIndexOf(".");
            fileName = fileName.replaceAll("\\W", "_");
            StringBuilder sb = new StringBuilder(fileName);
            sb.setCharAt(lastDotIndex, '.');
            fileName = sb.toString();
        }
        return fileName;
    }

    public static String zeroPad(int value, int size) {
        String s = "0000000000" + value;
        return s.substring(s.length() - size);
    }

    public static String zeroPad(long value, int size) {
        String s = "0000000000" + value;
        return s.substring(s.length() - size);
    }

    public static String getStringdate(String date) {
        String strDate = "";
        try {
            Date da = format.parse(date);
            strDate = formatDB.format(format.parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(PromotionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Date======================================================= " + strDate);
        return strDate;
    }

    public static void main(String[] args) throws ParseException {
        String str = zeroPad(1, 8);
        str = "R" + str;
        System.out.println("fsdf   " + str);

        String date = "01-02-2013";
        System.out.println("date :" + date);
        Date da = format.parse(date);
        System.out.println("Date : " + da);
        System.out.println("parsed : " + formatDB.format(format.parse(date)));

    }
}
