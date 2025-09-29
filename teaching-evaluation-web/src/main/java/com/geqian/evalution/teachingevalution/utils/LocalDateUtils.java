package com.geqian.evalution.teachingevalution.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * @author geqian
 * @date 19:43 2022/12/5
 */
public class LocalDateUtils {

    private static String DEAFULT_FORMAT = "yyyy-MM-dd";

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getNow() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(now);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getNow(String format) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(now);
    }

    /**
     * 日期转字符串
     *
     * @param localDate 日期
     * @param format    转换格式
     * @return
     */
    public static String formatToString(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }

    /**
     * 字符串转换日期
     *
     * @param localDate 日期字符串
     * @param format    转换格式
     * @return
     */
    public static LocalDate formatToLocalDate(String localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDate, formatter);
    }


    /**
     * 获取日期年份
     *
     * @return
     */
    public static int getYear(String localDate,String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDate,formatter).getYear();
    }

    /**
     * 获取日期月份
     *
     * @return
     */
    public static int getMonth(String localDate,String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDate,formatter).getMonthValue();
    }

    /**
     * 获取日期天数
     *
     * @return
     */
    public static int getDay(String localDate,String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDate,formatter).getDayOfMonth();
    }


    /**
     * 增加日期年数
     *
     * @param localDate
     * @param years
     * @param format
     * @return
     */
    public static String addYears(String localDate, long years, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateTime = LocalDate.parse(localDate, formatter).plusYears(years);
        return formatter.format(dateTime);
    }

    /**
     * 增加日期月数
     *
     * @param localDate
     * @param months
     * @param format
     * @return
     */
    public static String addMonths(String localDate, long months, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateTime = LocalDate.parse(localDate, formatter).plusMonths(months);
        return formatter.format(dateTime);
    }


    /**
     * 增加日期天数
     *
     * @param localDate
     * @param days
     * @param format
     * @return
     */
    public static String addDays(String localDate, long days, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateTime = LocalDate.parse(localDate, formatter).plusDays(days);
        return formatter.format(dateTime);
    }


    /**
     * 减少日期年数
     *
     * @param localDate
     * @param years
     * @param format
     * @return
     */
    public static String decreaseYears(String localDate, long years, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateTime = LocalDate.parse(localDate, formatter).minusYears(years);
        return formatter.format(dateTime);
    }

    /**
     * 减少日期月数
     *
     * @param localDate
     * @param months
     * @param format
     * @return
     */
    public static String decreaseMonths(String localDate, long months, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateTime = LocalDate.parse(localDate, formatter).minusMonths(months);
        return formatter.format(dateTime);
    }


    /**
     * 减少日期天数
     *
     * @param localDate
     * @param days
     * @param format
     * @return
     */
    public static String decreaseDays(String localDate, long days, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate dateTime = LocalDate.parse(localDate, formatter).minusDays(days);
        return formatter.format(dateTime);
    }


    /**
     * Date转换LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * LocalDate转换Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay().atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }


    /**
     * 获取两日期间隔天数
     *
     * @param localDate1
     * @param localDate2
     * @return
     */
    public static long intervalDays(LocalDate localDate1, LocalDate localDate2) {
        long days = Duration.between(localDate1, localDate2).toDays();
        return Math.abs(days);
    }


    /**
     * 判断某个日期是否在当前日期之前
     *
     * @param localDate
     * @return
     */
    public static boolean isBefore(String localDate,String format) {
        LocalDate now = LocalDate.now();
        LocalDate date = formatToLocalDate(localDate, format);
        return date.isBefore(now);
    }



    /**
     * 判断某个日期是否在当前日期之后
     *
     * @param localDate
     * @return
     */
    public static boolean isAfter(String localDate,String format) {
        LocalDate now = LocalDate.now();
        LocalDate date = formatToLocalDate(localDate, format);
        return date.isAfter(now);
    }

}
