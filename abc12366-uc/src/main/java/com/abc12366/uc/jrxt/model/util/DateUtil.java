package com.abc12366.uc.jrxt.model.util;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by ljun on 5/5/15.
 */
public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";
    public static final String BUNDLE_KEY = "messages";
    public static final String yyyy_MM_dd_HH_mm_ss="yyyy-MM-dd hh:mm:ss";
    /*所有月份的天数*/
    private static int[] days = new int[]{31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private DateUtil() {
    }
    public static Date addMonths(Date paramDate,
                                           int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.add(2, paramInt);
        return localCalendar.getTime();
    }
    public static String addYears(Date paramDate,
                                           int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.add(1, paramInt);
        return formatDateTime(localCalendar.getTime(), "yyyy");
    }

    public static Date addDays(Date paramDate, int paramInt)
            throws Exception {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        int i = localCalendar.get(6);
        localCalendar.set(6, i + paramInt);
        return localCalendar.getTime();
    }

    /**
     * 返回日期模式，默认为 (MM/dd/yyyy)
     *
     * @return 用字符串来表示的日期模式
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(BUNDLE_KEY, locale)
                    .getString("date.format");
        } catch (MissingResourceException mse) {
            log.error(mse.getMessage());
            defaultDatePattern = "MM/dd/yyyy";
        }

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * 此方法根据输入时的指定的格式解析字符串，返回一个日期对象。
     *
     * @param aMask   日期格式
     * @param strDate 日期的字符串表示
     * @return 一个转换后的日期对象
     * @throws ParseException 当字符串不符合指定的格式时
     * @see SimpleDateFormat
     */
    public static Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

//        if (log.isDebugEnabled()) {
//            log.debug("转换 '" + strDate + "' 到日期，采用格式 '" + aMask + "'");
//        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            log.error(pe.getMessage());
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * 根据格式 MM/dd/yyyy HH:MM 返回当前时间
     *
     * @param theTime 当前时间
     * @return 当前时间
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * 此方法根据输入时的指定的格式解析字符串，返回一个日期/时间的字符串表示。
     *
     * @param aMask 日期模式
     * @param aDate 日期对象
     * @return 表示此日期的格式化的字符串
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 获取传入年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //年
        cal.set(Calendar.YEAR, year);
        //月，因为Calendar里的月是从0开始，所以要-1
        cal.set(Calendar.MONTH, month - 1);
        //日，设为一号
        cal.set(Calendar.DATE, 1);
        //月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        //下一个月减一为本月最后一天
        cal.add(Calendar.DATE, -1);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));//获得月末是几号
    }

    /**
     * 获取当前时间,精确到秒
     * 格式:获取指定格式时间
     *
     * @return _format:String
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat _format = new SimpleDateFormat(format);
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        return _format.format(currentDate);
    }


    /**
     * 获取日期返回格式为YYYY-MM-DD
     *
     * @return
     */
    public static Date StringtoDate(String date) throws ParseException {
        return convertStringToDate("yyyyMMdd", date);
    }

    /**
     * 获取日期返回格式为YYYY-MM-DD
     *
     * @return
     */
    public static Date StringtoDateHMS(String date) throws ParseException {
        return convertStringToDate("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * 获取当前日期返回格式为YYYY年MM月DD日
     *
     * @return
     */
    public static Date getStringtoDateYMD(String date) throws ParseException {
        return convertStringToDate("yyyyMMdd", date);
    }

    public static String getCurrentDate() {
        String now = getCurrentTime("yyyyMMdd");
        String year = now.substring(0, 4);
        String month = now.substring(4, 6);
        String day = now.substring(6, 8);

        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 获取当前日期返回格式为YYYY-MM-DD
     *
     * @return
     */
    public static String getStrDate() {


        return getCurrentTime("yyyy-MM-dd");
    }


    /**
     * 获取当前日期返回格式为YYYY年MM月DD日
     *
     * @return
     */
    public static String getCurrentDateStr() {
        return getCurrentTime("yyyyMMdd");
    }

    /**
     * @return String 返回字符型时间序列，格式为：yyyyMMddHHmmssSS
     */
    public static String getSequence() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @return String 返回字符型时间序列，格式为：yyyyMMddHHmmssSS
     */
    public static String getSequenceStr() {
        return getCurrentTime("yyyyMMdd HH:mm");
    }



    /**
     * 获取当前时间为java.sql.Date的日期格式
     *
     * @return
     */
    public static Date getCurrentSqlDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static int getCurrentYear_int() {
        return Integer.parseInt(getYear());
    }

    public static int getCurrentMonth_int() {
        return Integer.parseInt(getMonth());
    }

    /**
     * 取年份
     *
     * @return 当前年份
     */
    public static String getYear() {
        return formatDateTime("yyyy");
    }

    /**
     * 取当前月份
     *
     * @return 当前月份
     */
    public static String getMonth() {
        return formatDateTime("MM");
    }

    /**
     * 将格式为'yyyyMMdd'的日期格式化为'yyyy?MM?dd'的日期格式'?'则参数传入
     *
     * @param date   YYYYMMDD格式的日期
     * @param petter 年月日的分隔符
     * @return String 加入分隔符的日期
     */
    public static String formatDateTime(String date, String petter) {
        return date.substring(0, 4) + petter + date.substring(4, 6) + petter + date.substring(6, 8);
    }

    /**
     * 按指定格式,格式化当前时间和日期
     *
     * @param patter 日期时间格式
     */
    public static String formatDateTime(Date d, String patter) {
        if (patter == null || patter.trim().equals("")) return "";
        SimpleDateFormat format = new SimpleDateFormat(patter);
        return format.format(d);
    }

    /**
     * 按指定格式,格式化当前时间和日期
     *
     * @param patter 日期时间格式
     */
    public static String formatDateTime(String patter) {
        if (patter == null || patter.trim().equals("")) return "";
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat(patter);
        return format.format(d);
    }

    /**
     * 取指定月份的前一个月
     *
     * @param month 指定月份
     * @return 指定月份的前一个月
     */
    public static String getFrontMonth(String month) {
        return caleFrontMonth(month, 1);
    }

    /**
     * 计算指定月份的第前几个月
     *
     * @param month 月份
     * @param i     指点N个月份号
     * @return 前几个月份
     */
    public static String caleFrontMonth(String month, int i) {
        int m = Integer.parseInt(month);
        if (m == 1) m = 12;
        else m = m - i;
        if (m < 10) return "0" + m;
        return String.valueOf(m);
    }

    /**
     * 取指定月份的最后一天
     *
     * @param year  年
     * @param month 月
     * @return int 指定月份的最后一天
     */
    public static int getLastMonthDay(String year, String month) {
        return getLastMonthDay(Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * 取指定月份的最后一天
     *
     * @param year  年
     * @param month 月
     * @return int 指定月份的最后一天
     */
    public static int getLastMonthDay(int year, int month) {
        if (2 == month &&
                0 == (year % 4) &&
                (0 != (year % 100) || 0 == (year % 400)))
            days[2] = 29;
        else
            days[2] = 28;
        return days[month];
    }


    /**
     * 判断日期1,日期2是否相等，精确到日期进行比较，不考虑时间
     * @param d1 日期1
     * @param d2 日期2
     * @return  相等true 不相等false
     */
    public static boolean compare(Date d1,Date d2){
        if(d1==null ||d2==null)
            return false;
      d1 = DateUtils.truncate(d1, Calendar.DATE);
      d2 = DateUtils.truncate(d2, Calendar.DATE);
      return d1.compareTo(d2)==0;
    }

    /**
     * 判断日期1是否大于日期2，精确到日期进行比较，不考虑时间
     * @param d1 日期1
     * @param d2 日期2
     * @return  大于true 小于等于false
     */
    public static boolean compareBig(Date d1,Date d2){
        if(d1==null ||d2==null)
            return false;
        d1 = DateUtils.truncate(d1, Calendar.DATE);
        d2 = DateUtils.truncate(d2, Calendar.DATE);
        return d1.compareTo(d2)==1;
    }

    /**
     * 判断日期1是否大于等于日期2，精确到日期进行比较，不考虑时间
     * @param d1 日期1
     * @param d2 日期2
     * @return  大于等于true 小于false
     */
    public static boolean compareBigOf(Date d1,Date d2){
        if(d1==null ||d2==null)
            return false;
        d1 = DateUtils.truncate(d1, Calendar.DATE);
        d2 = DateUtils.truncate(d2, Calendar.DATE);
        return (d1.compareTo(d2)==1) || (d1.compareTo(d2)==0);
    }

    /**
     * 判断日期1是否小于日期2，精确到日期进行比较，不考虑时间
     * @param d1 日期1
     * @param d2 日期2
     * @return  小于true 大于等于false
     */
    public static boolean compareSmall(Date d1,Date d2){
        if(d1==null ||d2==null)
            return false;
        d1 = DateUtils.truncate(d1, Calendar.DATE);
        d2 = DateUtils.truncate(d2, Calendar.DATE);
        return d1.compareTo(d2)==-1;
    }

    /**
     * 判断日期1是否小于等于日期2，精确到日期进行比较，不考虑时间
     * @param d1 日期1
     * @param d2 日期2
     * @return  小于等于true 大于false
     */
    public static boolean compareSmallOf(Date d1,Date d2){
        if(d1==null ||d2==null)
            return false;
        d1 = DateUtils.truncate(d1, Calendar.DATE);
        d2 = DateUtils.truncate(d2, Calendar.DATE);
        return (d1.compareTo(d2)==-1) || (d1.compareTo(d2)==0);
    }

    /**
     * 获取指定日期月份的最后一天，patter为返回的日期的字符串格式
     * @param date
     * @param patter
     * @return
     */
    public static String getlastDayOfMonth(Date date,String patter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat(patter);
        return format.format(cal.getTime());
    }

    /**
     * 获取上个月的最后一天
     * @param date
     * @return
     */
    public static Date getlastDayOfMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取指定日期月份的第一天， patter为返回的的字符串日期格式
     * @param date
     * @param patter
     * @return
     */
    public static String getFirstDayOfMonth(Date date,String patter){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat(patter);
        return format.format(cal.getTime());
    }

    /**
     * 获取上个月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonthDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH-1));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 得到指定时间的上个季度的第一天
     * @return
     */
    public static String getFirstDayOfLastSeason(Date date,String format){
        Calendar lastSeasonSssqq= Calendar.getInstance();
        lastSeasonSssqq.setTime(date);
        int currentMonth = lastSeasonSssqq.get(Calendar.MONTH) + 1;
        int currentYear =  lastSeasonSssqq.get(Calendar.YEAR);
        if(1 <= currentMonth && currentMonth <= 3){
            lastSeasonSssqq.set(currentYear - 1, 9, 1);
        }else if(4 <= currentMonth && currentMonth <= 6){
            lastSeasonSssqq.set(currentYear, 0, 1) ;
        }else if(7 <= currentMonth && currentMonth <= 9){
            lastSeasonSssqq.set(currentYear, 3, 1) ;
        }else if(10 <= currentMonth && currentMonth <= 12){
            lastSeasonSssqq.set(currentYear, 6, 1) ;
        }
        return getDateTime(format,lastSeasonSssqq.getTime());
    }

    public static Date getFirstDayOfLastSeasonDate(Date date){
        Calendar lastSeasonSssqq= Calendar.getInstance();
        lastSeasonSssqq.setTime(date);
        int currentMonth = lastSeasonSssqq.get(Calendar.MONTH) + 1;
        int currentYear =  lastSeasonSssqq.get(Calendar.YEAR);
        if(1 <= currentMonth && currentMonth <= 3){
            lastSeasonSssqq.set(currentYear - 1, 9, 1);
        }else if(4 <= currentMonth && currentMonth <= 6){
            lastSeasonSssqq.set(currentYear, 0, 1) ;
        }else if(7 <= currentMonth && currentMonth <= 9){
            lastSeasonSssqq.set(currentYear, 3, 1) ;
        }else if(10 <= currentMonth && currentMonth <= 12){
            lastSeasonSssqq.set(currentYear, 6, 1) ;
        }
        return lastSeasonSssqq.getTime();
    }
    /**
     * 得到指定时间的上个季度的最后一天
     * @return
     */
    public static String getLastDayOfLastSeason(Date date,String format){
        Calendar lastSeasonSssqz= Calendar.getInstance();
        lastSeasonSssqz.setTime(date);
        int currentMonth = lastSeasonSssqz.get(Calendar.MONTH) + 1;
        int currentYear =  lastSeasonSssqz.get(Calendar.YEAR);
        if(1 <= currentMonth && currentMonth <= 3){
            lastSeasonSssqz.set(currentYear - 1, 11, 31);
        }else if(4 <= currentMonth && currentMonth <= 6){
            lastSeasonSssqz.set(currentYear, 2, 31);
        }else if(7 <= currentMonth && currentMonth <= 9){
            lastSeasonSssqz.set(currentYear, 5, 30);
        }else if(10 <= currentMonth && currentMonth <= 12){
            lastSeasonSssqz.set(currentYear, 8, 30);
        }
        return getDateTime(format,lastSeasonSssqz.getTime());
    }

    public static Date getLastDayOfLastSeasonDate(Date date){
        Calendar lastSeasonSssqz= Calendar.getInstance();
        lastSeasonSssqz.setTime(date);
        int currentMonth = lastSeasonSssqz.get(Calendar.MONTH) + 1;
        int currentYear =  lastSeasonSssqz.get(Calendar.YEAR);
        if(1 <= currentMonth && currentMonth <= 3){
            lastSeasonSssqz.set(currentYear - 1, 11, 31);
        }else if(4 <= currentMonth && currentMonth <= 6){
            lastSeasonSssqz.set(currentYear, 2, 31);
        }else if(7 <= currentMonth && currentMonth <= 9){
            lastSeasonSssqz.set(currentYear, 5, 30);
        }else if(10 <= currentMonth && currentMonth <= 12){
            lastSeasonSssqz.set(currentYear, 8, 30);
        }
        return lastSeasonSssqz.getTime();
    }

    /**
     * 得到指定时间的上年第一天
     */
    public static String getFirstDayOfLastYear(Date date,String format){
        Calendar lastYearSssqz= Calendar.getInstance();
        lastYearSssqz.setTime(date);
        int currentYear = lastYearSssqz.get(Calendar.YEAR);
        lastYearSssqz.set(currentYear - 1, 0, 1);
        return getDateTime(format,lastYearSssqz.getTime());
    }


    public static Date getFirstDayOfLastYearDate(Date date){
        Calendar lastYearSssqz= Calendar.getInstance();
        lastYearSssqz.setTime(date);
        int currentYear = lastYearSssqz.get(Calendar.YEAR);
        lastYearSssqz.set(currentYear - 1, 0, 1);
        return lastYearSssqz.getTime();
    }

    /**
     * 得到指定时间的上年最后一天
     */
    public static String getLastDayOfLastYear(Date date,String format){
        Calendar lastYearSssqz= Calendar.getInstance();
        lastYearSssqz.setTime(date);
        int currentYear = lastYearSssqz.get(Calendar.YEAR);
        lastYearSssqz.set(currentYear - 1, 11, 31);
        return getDateTime(format,lastYearSssqz.getTime());
    }

    public static Date getLastDayOfLastYear(Date date){
        Calendar lastYearSssqz= Calendar.getInstance();
        lastYearSssqz.setTime(date);
        int currentYear = lastYearSssqz.get(Calendar.YEAR);
        lastYearSssqz.set(currentYear - 1, 11, 31);
        return lastYearSssqz.getTime();
    }

    public static String longToDate(String dateFormat,Long millSec){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date= new Date(millSec);
        return sdf.format(date);

    }

    /**
     * 计算年份差
     * @param strDate 计算的起始年费
     * @param paramInt 间隔值
     * @return 差额年份
     */
    public static String betweenYear(String strDate,int paramInt){
        String year="";
        try {
            year= DateUtil.addYears(convertStringToDate("yyyy",strDate),paramInt);
        } catch (ParseException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return year;
    }

 /*
    获取当前时间之前或之后几分钟 minute
    */
    public static String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }

}
