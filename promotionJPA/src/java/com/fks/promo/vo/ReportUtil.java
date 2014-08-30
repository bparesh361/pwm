/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Paresb
 */
public class ReportUtil {

    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat fileformat = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat formatDateTime = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    public static final SimpleDateFormat formatDB = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat formatDBTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Calendar cal = new GregorianCalendar();
    private static Calendar c = Calendar.getInstance();

    public static String dispayDateFormat(Date date) {
        return format.format(date);
    }

    public static String dispayFileDateFormat(Date date) {
        return fileformat.format(date);
    }

    public static String dispayDateTimeFormat(Date date) {
        return formatDateTime.format(date);
    }

    public static String getDBDateformat(Date date) {
        return formatDB.format(date);
    }

    public static String getDBDateTimeformat(Date date) {
        return formatDBTime.format(date);
    }

    public static Date getDBDate(String date) throws Exception {
        return formatDB.parse(date);
    }

    public static Date convertStringToDBDate(String dateStr) throws Exception {
        if (dateStr.contains("/")) {
            dateStr = dateStr.replaceAll("/", "-");
        }

        return format.parse(dateStr);
    }

    public static String zeroPad(int value, int size) {
        String s = "0000000000" + value;
        return s.substring(s.length() - size);
    }

    public static List getSubList(int pageNo, List mainList) {
        int recordCount = 15;
        int startIndex = 0, lastIndex = 0;
        if (pageNo > 1) {
            startIndex = ((pageNo - 1) * recordCount);
        }

        ///  check record count

        lastIndex = startIndex + recordCount;
        if (lastIndex > mainList.size()) {
            lastIndex = mainList.size();
        }


        List subColl = mainList.subList(startIndex, lastIndex);
        return subColl;
    }

    public static String getTime(Date date) {
        String strDate = formatDBTime.format(date);

        String[] dtSplitter = strDate.split("\\s");
        return dtSplitter[1];
    }

    public static String getStringByReplaceCommaWithSpace(String str) {
        if (str != null) {
            str = str.replaceAll(",", " ").toString();
        }
        return str;
    }

    public static Long getCurrentTimeInMiliSeconds() {
        return cal.getTimeInMillis();
    }

    public static String addSubtractDaysInDate(String date, int days) throws ParseException {
        Date dt = formatDB.parse(date);
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        dt = c.getTime();
        return (formatDB.format(dt));
    }

    public static String addSubtractDaysInDate(Date date, int days) {
        c.setTime(date);
        c.add(Calendar.DATE, days);
        date = c.getTime();
        return (formatDB.format(date));
    }

    public static void main(String[] args) throws Exception {
//        String a = getStringByReplaceCommaWithSpace("A,B");
//        System.out.println(a);

//        System.out.println(getDBDate("2012-01-01"));


        System.out.println(new Date());
        System.out.println("----- " + getTime(new Date()));


    }
    static final String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    public static String randomPasswordGenrates(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
