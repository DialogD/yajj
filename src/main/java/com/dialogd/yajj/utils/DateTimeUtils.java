
/*
* 文件名：DateTimeUtils.java
* 版权：Copyright by shuabao
* 修改人：winster
* 修改时间：2018年10月26日
* 修改内容：
*/

package com.dialogd.yajj.utils;

import com.alibaba.druid.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * 时间转换工具类
 * 
 * @author winster
 * @version 2018年10月26日
 * @see DateTimeUtils
 * @since
 */
public class DateTimeUtils {

	public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static String YMD = "yyyy-MM-dd";
	public static String YYYYMMDD = "yyyyMMdd";

	public static String parseDate2String(Date date, String type) {
		if (date == null || StringUtils.isEmpty(type)) {
			return null;
		}

		SimpleDateFormat sdf = null;
		if (YMDHMS.equals(type)) {
			sdf = new SimpleDateFormat(YMDHMS);
		} else if (YYYYMMDDHHMMSS.equals(type)) {
			sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
		} else if (YMD.equals(type)) {
			sdf = new SimpleDateFormat(YMD);
		} else if (YYYYMMDD.equals(type)) {
			sdf = new SimpleDateFormat(YYYYMMDD);
		} else {
			return null;
		}
		return sdf.format(date);
	}

	public static Date parseString2Date(String dateString, String type) {
		if (StringUtils.isEmpty(dateString) || StringUtils.isEmpty(type)) {
			return null;
		}

		SimpleDateFormat sdf = null;
		if (YMDHMS.equals(type)) {
			sdf = new SimpleDateFormat(YMDHMS);
		} else if (YYYYMMDDHHMMSS.equals(type)) {
			sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
		} else if (YMD.equals(type)) {
			sdf = new SimpleDateFormat(YMD);
		} else if (YYYYMMDD.equals(type)) {
			sdf = new SimpleDateFormat(YYYYMMDD);
		} else {
			return null;
		}
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getMonthAdd(String month, int number) {
		String currDate = null;
		DateFormat format2 = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = format2.parse(month);
			calendar.setTime(date1);
			calendar.add(Calendar.MONTH, number);
			currDate = format2.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currDate;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String dateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	public static Date parseDate(String dateValue, String pattern) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(dateValue);
	}

	public static void main(String[] args) {
		// String month = getMonthAdd("2019-01-01 10:20:33", 3);
		// System.out.println(month + "2019-01-01 10:20:33".substring(7));
		System.out
				.println("20190101".substring(0, 4) + "-" + "20190101".substring(4, 6) + "-" + "20190101".substring(6));
	}

	/**
	 * 
	 * 获取当前时间到当天的秒
	 * 
	 * @see
	 */
	public static long getNowDaySecond() {
		long hour = 0;
		Date startDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		Date endOfDate = calendar.getTime();
		hour = (endOfDate.getTime() - startDate.getTime()) / 1000;
		return hour;
	}

	public static String getDayAdd(String date, int number) {
		String currDate = null;
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = format2.parse(date);
			calendar.setTime(date1);
			calendar.add(Calendar.DATE, number);
			currDate = format2.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currDate;
	}
	
	public static String getYYYY(String date) {// 取特定日期字符串(如2003-12-11)的年份
		StringTokenizer st = new StringTokenizer(date, "-/");
		return st.nextToken();
	}

	public static String getMM(String date) {// 取特定日期字符串(如2003-12-11)的月份
		StringTokenizer st = new StringTokenizer(date, "-/");
		st.nextToken();
		String mm = st.nextToken();
		if (mm.length() == 1)
			mm = "0" + mm;
		return mm;
	}

	public static String getDD(String date) {// 取特定日期字符串(如2003-12-11)的号数
		StringTokenizer st = new StringTokenizer(date, "-/");
		st.nextToken();
		st.nextToken();
		String dd = st.nextToken();
		if (dd.length() == 1)
			dd = "0" + dd;
		return dd;
	}
	
	public static String getTime(String date) {
		String yyyy = DateTimeUtils.getYYYY(date);
		String mm = DateTimeUtils.getMM(date);
		String dd = DateTimeUtils.getDD(date);
		String endTime = yyyy + "年" + mm + "月" + dd + "日";
		return endTime;
	}
}
