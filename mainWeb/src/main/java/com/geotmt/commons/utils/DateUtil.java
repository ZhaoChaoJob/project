package com.geotmt.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cheng on 16/6/29.
 * 日期工具类,通用日期工具方法放到这里
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 获得当前月
     *
     * @return 格式 20160706
     */

    public static SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");

    public static SimpleDateFormat sdfYYYYMM = new SimpleDateFormat("yyyyMM");

    public static  SimpleDateFormat sdfYYYY_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

    public static  SimpleDateFormat sdfYYYY_MM = new SimpleDateFormat("yyyy-MM");

    public static SimpleDateFormat sdfYYYYMMdd = new SimpleDateFormat("yyyyMMdd");

    public static SimpleDateFormat sdfHHmmss = new SimpleDateFormat("HH:mm:ss");

    public  static SimpleDateFormat sdfYYYY_MM_DD_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public  static SimpleDateFormat sdfYYYYMMDDHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 获得当前年
     * @return yyyy
     */
    public static <T> T getCurrentYear(Class<T> clazz) {
        if(clazz.isAssignableFrom(String.class)){
            return (T)sdfYYYY.format(new Date());
        }else{
            return (T)(Long.valueOf(sdfYYYY.format(new Date())));
        }
    }

    /**
     * 获得当前月
     * @return yyyyMM
     */
    public static <T> T getCurrentDate(Class<T> clazz) {
    	if(clazz.isAssignableFrom(String.class)){
    		return (T)sdfYYYYMM.format(new Date());
    	}else{
    		return (T)(Long.valueOf(sdfYYYYMM.format(new Date())));
    	}
    }

    /**
     * 获得当前日
     * @return yyyyMM
     */
    public static <T> T getCurrentDateDay(Class<T> clazz) {
        if(clazz.isAssignableFrom(String.class)){
            return (T)sdfYYYYMMdd.format(new Date());
        }else{
            return (T)(Long.valueOf(sdfYYYYMMdd.format(new Date())));
        }
    }

    /**
     * 获取近6个月的月份
     *
     * @return list<String> [yyyyMMdd,yyyyMMdd]
     */
    public static <T> List<T> getPreSixMonth(Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        Calendar cal = Calendar.getInstance();
        list.add(getCurrentDate(clazz));
        for (int i = 0; i < 5; i++) {
            cal.add(Calendar.MONTH, -1);
            String dataMonth = sdfYYYYMM.format(cal.getTime());
            if(clazz.isAssignableFrom(String.class)){
            	T dataMonth2 = (T)dataMonth ;
            	list.add(dataMonth2);
            }else{
            	T dataMonth2 = (T)Long.valueOf(dataMonth) ;
            	list.add(dataMonth2);
            }
        }
        return list;
    }

    /**
     * 获取近6个月的月份
     *
     * @return list<String> [yyyyMMdd,yyyyMMdd]
     */
    public static <T> List<T> getPreSixMonthZ(Class<T> clazz) {
        List<T> list = getPreSixMonth(clazz) ;
        Collections.reverse(list); // 正序
        return list ;
    }

    /**
     * 获取月分的第一天
     *
     * @param dateMonth yyyyMM
     * @return yyyyMMdd
     */
    public static String getFirstDayInMonth(String dateMonth) {
        return dateMonth+"01";
    }

    /**
     * 获取月份的最后一天
     * @param dataMonth yyyyMM
     * @return yyyyMMdd
     */
    public static  String getEndDayInMonth(String dataMonth){
        try {
            Date date = sdfYYYYMM.parse(dataMonth);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            return sdfYYYYMMdd.format(cal.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 日期格式转换工具
     * @param dateStr yyyyMMdd
     * @return        yyyy-MM-dd
     */
    public static String dateConvert(String dateStr){
        try {
            Date date = sdfYYYYMMdd.parse(dateStr);
            return  sdfYYYY_MM_dd.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 日期格式转换工具
     * @param dateStr yyyyMMdd
     * @return        yyyy-MM-dd
     */
    public static String dateConvertCallTime(String dateStr){
        try {
            Date date = sdfYYYYMMDDHHmmss.parse(dateStr);
            return  sdfYYYY_MM_DD_HH_mm_ss.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Integer timeToSecond(String time){
        String[] my =time.split(":");
        int hour =Integer.parseInt(my[0]);
        int min =Integer.parseInt(my[1]);
        int sec =Integer.parseInt(my[2]);

        int zong =hour*3600+min*60+sec;
        return  zong;
    }

    /**
     * 格式化日期
     * @param date yyyy-MM-dd HH:mm:ss
     * @return yyyyMMddHHmmss
     */
    public static String formatYyyyMMddHHmmss(String date){
        return date.replaceAll("-","").
                replaceAll(":","").replaceAll(" ","") ;
    }

    public static Date castStrToDate(String date,String model){
        SimpleDateFormat formatter = new SimpleDateFormat(model);
        Date dt = null;
        try {
            dt = formatter.parse(date);
        } catch (ParseException e) {
            logger.info("",e);
            e.printStackTrace();
        }
        return dt ;
    }
    public static void main(String[] args) {
        System.out.print(dateConvertCallTime("2017-08-29 15:45:54".replaceAll("[^0-9]","")));
    }
}
