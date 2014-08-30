/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.init;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 *
 * @author Paresb
 * 
 * Using JODA Time
 * 
 */
public class Test {

    public static void main(String[] args) throws Exception {


        String validFrom = "2013-01-05 20:08:09";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
        Date validFromDate = format.parse(validFrom);
        System.out.println(validFromDate);
//        List<String> holidays = new ArrayList<String>();
//        holidays.add("2013-01-05");        
//        int hours = getDiffHoursWithHolidays(validFrom,holidays);
//        System.out.println(" Hours " + hours);

    }

    public static boolean checkLeadTimeValidity(String validFrom, int leadTimeHours) throws Exception {
        validFrom = validFrom + " 23:59:59";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date validFromDate = format.parse(validFrom);
        System.out.println("Valid From Date " + validFromDate);
        Date currentDate = new Date();
        System.out.println("Current Date " + currentDate);
        Period p = new Period(validFromDate.getTime(), new Date().getTime(), PeriodType.hours());
        int hoursDiff = p.getHours();
        System.out.println("Hours Difference is " + hoursDiff);
        if (hoursDiff <= leadTimeHours) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDiffHoursWithHolidays(String validFrom, List<String> holidayList) throws Exception {
        validFrom = validFrom + " 23:59:59";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date validFromDate = format.parse(validFrom);
        System.out.println("Valid From Date " + validFromDate);
        Date currentDate = new Date();
        System.out.println("Current Date " + currentDate);
        Period p = new Period(new Date().getTime(), validFromDate.getTime(), PeriodType.hours());
        int hours = p.getHours();
        System.out.println("Hours is " + hours);
        double diff = (double) hours / 24;
        System.out.println(diff);
        int days = (int) Math.ceil(diff);
        System.out.println("No. of Days from Date is " + days);
        int holidays = 0;
        for (int x = 1; x <= days; x++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, x);
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sFormat.format(cal.getTime());
            System.out.println("Checking Date " + dateStr + " For Holiday");
            if (holidayList.contains(dateStr)) {
                holidays += 1;
                System.out.println("Holiday Added");
            }
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                holidays += 1;
                System.out.println("Sunday : Holiday Added");
            }
        }
        System.out.println("No. of HOliday Are " + holidays);
        hours = hours - (holidays * 24);
        System.out.println("Difference Hours Is " + hours);
        return hours;
    }
}
