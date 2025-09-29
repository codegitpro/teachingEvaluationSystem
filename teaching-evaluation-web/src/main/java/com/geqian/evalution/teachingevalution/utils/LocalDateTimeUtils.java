package com.geqian.evalution.teachingevalution.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author geqian
 * @date 19:43 2022/12/5
 */
public class LocalDateTimeUtils {

    private static String DEAFULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static String getNow(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(now);
    }

    /**
     * 日期时间转字符串
     *
     * @param localDateTime 日期时间
     * @param format        转换格式
     * @return
     */
    public static String formatToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDateTime);
    }

    /**
     * 字符串转换日期时间
     *
     * @param localDateTime 日期时间字符串
     * @param format        转换格式
     * @return
     */
    public static LocalDateTime formatToLocalDateTime(String localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(localDateTime, formatter);
    }


    /**
     * 获取日期时间年份
     *
     * @return
     */
    public static int getYear(String localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDateTime, formatter).getYear();
    }

    /**
     * 获取日期时间月份
     *
     * @return
     */
    public static int getMonth(String localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDateTime, formatter).getMonthValue();
    }

    /**
     * 获取日期时间天数
     *
     * @return
     */
    public static int getDay(String localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(localDateTime, formatter).getDayOfMonth();
    }


    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getNowTimeStamp() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 时间戳转日期时间
     *
     * @param timestamp 时间戳
     * @return
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }


    /**
     * 日期时间字符串转时间戳
     *
     * @param localDateTime 日期时间字符串
     * @param formatter     转换格式
     * @return
     */
    public static long toTimeStamp(String localDateTime, String formatter) {
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(formatter)).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    /**
     * 增加日期时间年数
     *
     * @param localDateTime
     * @param years
     * @param format
     * @return
     */
    public static String addYears(String localDateTime, long years, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter).plusYears(years);
        return formatter.format(dateTime);
    }


    /**
     * 增加日期时间月数
     *
     * @param localDateTime
     * @param months
     * @param format
     * @return
     */
    public static String addMonths(String localDateTime, long months, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter).plusMonths(months);
        return formatter.format(dateTime);
    }


    /**
     * 增加日期时间天数
     *
     * @param localDateTime
     * @param days
     * @param format
     * @return
     */
    public static String addDays(String localDateTime, long days, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter).plusDays(days);
        return formatter.format(dateTime);
    }


    /**
     * 减少日期时间年数
     *
     * @param localDateTime
     * @param years
     * @param format
     * @return
     */
    public static String decreaseYears(String localDateTime, long years, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter).minusYears(years);
        return formatter.format(dateTime);
    }

    /**
     * 减少日期时间月数
     *
     * @param localDateTime
     * @param months
     * @param format
     * @return
     */
    public static String decreaseMonths(String localDateTime, long months, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter).minusMonths(months);
        return formatter.format(dateTime);
    }


    /**
     * 减少日期时间天数
     *
     * @param localDateTime
     * @param days
     * @param format
     * @return
     */
    public static String decreaseDays(String localDateTime, long days, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter).minusDays(days);
        return formatter.format(dateTime);
    }


    /**
     * Date转换LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime转换Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }


    /**
     * 获取两日期时间间隔天数
     *
     * @param localDateTime1
     * @param localDateTime2
     * @return
     */
    public static long intervalDays(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        long days = Duration.between(localDateTime1, localDateTime2).toDays();
        return Math.abs(days);
    }


    /**
     * 判断某个日期是否在当前日期之前
     *
     * @param localDateTime
     * @return
     */
    public static boolean isBefore(String localDateTime, String format) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = formatToLocalDateTime(localDateTime, format);
        return date.isBefore(now);
    }


    /**
     * 判断某个日期是否在当前日期之后
     *
     * @param localDateTime
     * @return
     */
    public static boolean isAfter(String localDateTime, String format) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime date = formatToLocalDateTime(localDateTime, format);
        System.out.println(date);
        return date.isAfter(now);
    }


    /**
     * 获取日期时间开始时间
     * @param localDateTime
     * @param format
     * @return
     */
    public static String startDateTime(String localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDateTime.parse(localDateTime, dateTimeFormatter).toLocalDate();
        return dateTimeFormatter.format(LocalDateTime.of(localDate, LocalTime.MIN));
    }


    /**
     * 获取日期时间结束时间
     * @param localDateTime
     * @param format
     * @return
     */
    public static String endDateTime(String localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDateTime.parse(localDateTime, dateTimeFormatter).toLocalDate();
        return dateTimeFormatter.format(LocalDateTime.of(localDate, LocalTime.MAX));
    }


    /**
     * 获取日期时间开始时间
     * @param localDateTime
     * @return
     */
    public static LocalDateTime startDateTime(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }


    /**
     * 获取日期时间结束时间
     * @param localDateTime
     * @return
     */
    public static LocalDateTime endDateTime(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

}
