package com.dialogd.yajj.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	private static ThreadLocal<SimpleDateFormat> formatThreadLocal = new ThreadLocal<SimpleDateFormat>();
	public static final String START_TIME = " 00:00:00";
	public static final String END_TIME = " 23:59:59";

	/**
	 * 从ThreadLocal里获取SimpleFormat对象
	 * 
	 * @return
	 */
	private static SimpleDateFormat getSimpleFormatInstance() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = formatThreadLocal.get();
		if (format != null) {
			return format;
		}
		format = new SimpleDateFormat(pattern);
		formatThreadLocal.set(format);
		return format;
	}
	private static SimpleDateFormat getSimpleYyyyMmmFormatInstance() {
		String pattern = "yyyy-MM";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format;
	}

	public static Date getDate(String fullTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(fullTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 获距离今天前后n个月的日期 yyyy-MM-dd
	 * 
	 * @param month
	 *            月数 前 -month
	 * @return
	 */
	public static String getXMonthYYYYMMDD(int month) {
		SimpleDateFormat format = getSimpleFormatInstance();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		Date d = c.getTime();
		return format.format(d);
	}

	private static String sysChar;// 日期分隔符
	static {
		sysChar = "-";
	}

	public static synchronized long getCurrentTime() {
		return System.currentTimeMillis();
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

	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getDateMMDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	public static String getDateYYMMDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(new Date());
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

	/**
	 * 字符串转换成日期
	 *
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(str);
		return date;
	}

	public static Timestamp getStatBeginDate(Timestamp workDate) {
		return getFirstDayInMonth(workDate);
	}

	public static Timestamp getStatEndDate(Timestamp workDate) {
		Timestamp endDate = getLastDayInMonth(workDate);
		if (endDate.after(getCurrTime())) {
			return getCurrTime();
		} else {
			return getLastDayInMonth(workDate);
		}
	}

	public static Timestamp getFirstDayInMonth(Timestamp currDate) {
		String yyyy = getYYYY(currDate);
		String mm = getMM(currDate);
		return getTimestamp(Integer.parseInt(yyyy), Integer.parseInt(mm), 1);
	}

	public static Timestamp getLastDayInMonth(Timestamp currDate) {
		String yyyy = getYYYY(currDate);
		String mm = getMM(currDate);
		Timestamp t = getTimestamp(Integer.parseInt(yyyy), Integer.parseInt(mm), 31);
		if (Integer.parseInt(getMM(currDate)) != Integer.parseInt(getMM(t))) {
			t = getTimestamp(Integer.parseInt(yyyy), Integer.parseInt(mm), 30);
			if (Integer.parseInt(getMM(currDate)) != Integer.parseInt(getMM(t))) {
				t = getTimestamp(Integer.parseInt(yyyy), Integer.parseInt(mm), 29);
				if (Integer.parseInt(getMM(currDate)) != Integer.parseInt(getMM(t))) {
					t = getTimestamp(Integer.parseInt(yyyy), Integer.parseInt(mm), 28);
					if (Integer.parseInt(getMM(currDate)) != Integer.parseInt(getMM(t))) {
						t = getTimestamp(Integer.parseInt(yyyy), Integer.parseInt(mm), 27);
					}
				}
			}
		}
		return t;
	}

	/** **********************取特定时间函数*********************** */
	public static Timestamp getSomeDate(int space) {// 根据时间间隔来取得某个时间,例如：参数为1时，得到明天的日期，参数为30时，得到一个月后的日期，参数为0时，得到今天的日期，参数为-1时，得到昨天的日期，参数为-30时，得到一个月前的日期，
		return getSomeDate(getCurrTime(), space);
	}

	public static Timestamp getSomeDate(Timestamp t, int space) {// 比上面的方法多一个日期参数，上面方法默认为当天，所以没此参数
		Date someDate = (Date) t;
		int sign = space < 0 ? -1 : 1;
		space = space < 0 ? -space : space;
		int s = space / 10;
		int y = space % 10;
		for (int i = 0; i < s; i++) {
			someDate = new Date(someDate.getTime() + 3600 * 24 * 1000 * 10 * sign);
		}
		someDate = new Date(someDate.getTime() + 3600 * 24 * 1000 * y * sign);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
		int someDateYear = Integer.parseInt(formatter.format(someDate));// 求一个月前是什么年
		formatter = new SimpleDateFormat("M", Locale.getDefault());
		int someDateMonth = Integer.parseInt(formatter.format(someDate));// 求一个月前是几月
		formatter = new SimpleDateFormat("dd", Locale.getDefault());
		int someDateDay = Integer.parseInt(formatter.format(someDate));// 求一个月前是几号
		return getTimestamp(someDateYear, someDateMonth, someDateDay);
	}

	public static String getYYYY(Timestamp t) {// 取特定时间的年份，比如2003年
		if (isEmptyTime(t))
			return "";
		String yyyy = getFM("yyyy", t);
		yyyy = "0000" + yyyy;
		return yyyy.substring(yyyy.length() - 4);
	}

	public static String getYYYY(String date) {// 取特定日期字符串(如2003-12-11)的年份
		StringTokenizer st = new StringTokenizer(date, "-/");
		return st.nextToken();
	}

	public static String getMM(Timestamp t) {// 取特定时间的月份，比如4月
		if (isEmptyTime(t))
			return "";
		String mm = getFM("M", t);
		mm = "00" + mm;
		return mm.substring(mm.length() - 2);
	}

	public static String getMM(String date) {// 取特定日期字符串(如2003-12-11)的月份
		StringTokenizer st = new StringTokenizer(date, "-/");
		st.nextToken();
		String mm = st.nextToken();
		if (mm.length() == 1)
			mm = "0" + mm;
		return mm;
	}

	public static String getDD(Timestamp t) {// 取特定时间的日，比如3号
		if (isEmptyTime(t))
			return "";
		String dd = getFM("dd", t);
		dd = "00" + dd;
		return dd.substring(dd.length() - 2);
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

	public static String getDD2(String date) {// 取特定日期字符串(如2003-12-11)的号数
		StringTokenizer st = new StringTokenizer(date, "-/");
		st.nextToken();
		st.nextToken();
		String dd = st.nextToken();
		if (dd.length() == 1)
			dd = "0" + dd;
		return dd.substring(0, 2);
	}

	public static String getYYYYMM(Timestamp t) {// 取特定时间的年月份，比如2003-04
		if (isEmptyTime(t))
			return "";
		return getYYYY(t) + sysChar + getMM(t);
	}

	public static String getYYYYMMDD(Timestamp t) {// 取特定时间的年月日，比如2003-04-03
		if (isEmptyTime(t))
			return "";
		return getYYYYMM(t) + sysChar + getDD(t);
	}

	public static String getHH(Timestamp t) {
		if (isEmptyTime(t))
			return "";
		String hh = getFM("H", t);
		hh = "00" + hh;
		return hh.substring(hh.length() - 2);
	}

	public static String getMI(Timestamp t) {
		if (isEmptyTime(t))
			return "";
		String mi = getFM("m", t);
		mi = "00" + mi;
		return mi.substring(mi.length() - 2);
	}

	public static String getSS(Timestamp t) {
		if (isEmptyTime(t))
			return "";
		String ss = getFM("s", t);
		ss = "00" + ss;
		return ss.substring(ss.length() - 2);
	}

	public static String getHHMISS(Timestamp t) {// 取特定时间的时分秒，比如12：45：30
		if (isEmptyTime(t))
			return "";
		return getHH(t) + ":" + getMI(t) + ":" + getSS(t);
	}

	public static String getYYYYMMDDHHMISS(Timestamp t) {// 取特定时间的年月日时分秒，比如2003-04-03
		// 12:45:30
		if (isEmptyTime(t))
			return "";
		return getYYYYMMDD(t) + " " + getHHMISS(t);
	}

	public static Timestamp getTimestamp(int year, int month, int day, int hour, int minute, int second) {// 通过代入年月日时分秒构造Timestamp时间对象
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.set(year, month - 1, day, hour, minute, second);
		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp getTimestamp(int year, int month, int day) {// 通过代入年月日构造Timestamp时间对象
		return getTimestamp(year, month, day, 0, 0, 0);
	}

	public static Timestamp getTimestamp(String date) {
		return getTimestamp(Integer.parseInt(getYYYY(date)), Integer.parseInt(getMM(date)),
				Integer.parseInt(getDD(date)), 0, 0, 0);
	}

	public static String DateToString(Timestamp t) {
		if (t == null) {
			return "1970" + sysChar + "01" + sysChar + "01" + " " + "00" + ":" + "00" + ":" + "00";
		} else {
			return getYYYYMMDDHHMISS(t);
		}
	}

	/** **********************取当前时间函数*********************** */
	public static Timestamp getCurrTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getYYYY() {// 取当前年份，比如2003年
		return getYYYY(getCurrTime());
	}

	public static String getMM() {// 取当前月份，比如4月
		return getMM(getCurrTime());
	}

	public static String getDD() {// 取当前日，比如03号
		return getDD(getCurrTime());
	}

	public static String getYYYYMM() {// 取当前年月份，比如2003-04
		return getYYYYMM(getCurrTime());
	}

	public static String getYYYYMMDD() {// 取当前年月日，比如2003-04-03
		return getYYYYMMDD(getCurrTime());
	}

	public static String getHH() {
		return getHH(getCurrTime());
	}

	public static String getMI() {
		return getMI(getCurrTime());
	}

	public static String getSS() {
		return getSS(getCurrTime());
	}

	public static String getHHMISS() {// 取当前时分秒，比如12：45：30
		return getHHMISS(getCurrTime());
	}

	public static String getYYYYMMDDHHMISS() {// 取当前年月日时分秒，比如2003-04-03
		// 12:45:30
		return getYYYYMMDDHHMISS(getCurrTime());
	}

	public static boolean isEmptyTime(Timestamp t) {
		return getTimestamp(1970, 01, 01, 00, 00, 00).getTime() == t.getTime();
	}

	/**
	 *
	 * @Description 当日剩余时间--秒
	 * @param currentDate
	 * @return
	 */
	public static Integer getRemainSecondsToday(Date currentDate) {
		Calendar midnight = Calendar.getInstance();
		midnight.setTime(currentDate);
		midnight.add(Calendar.DAY_OF_MONTH, 1);
		midnight.set(Calendar.HOUR_OF_DAY, 0);
		midnight.set(Calendar.MINUTE, 0);
		midnight.set(Calendar.SECOND, 0);
		midnight.set(Calendar.MILLISECOND, 0);
		Integer seconds = (int) ((midnight.getTime().getTime() - currentDate.getTime()) / 1000);
		return seconds;
	}

	/** **********************其它方法*********************** */
	public static boolean isDate(String s) {// 检查日期格式是否正确
		StringTokenizer st = new StringTokenizer(s, "-");
		try {
			if (st.hasMoreElements()) {
				String strYear = (String) st.nextElement();
				if (strYear.length() == 4) {
					int year = Integer.parseInt(strYear);
					if (year >= 1970 && year <= 2470) {
						if (st.hasMoreElements()) {
							String strMonth = (String) st.nextElement();
							if (strMonth.length() == 1 || strMonth.length() == 2) {
								int month = Integer.parseInt(strMonth);
								if (month > 0 && month <= 12) {
									if (st.hasMoreElements()) {
										String strDay = (String) st.nextElement();
										if (strDay.length() == 1 || strDay.length() == 2) {
											int day = Integer.parseInt(strDay);
											if (day > 0 && day <= 31) {
												if (!st.hasMoreElements()) {
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isTime(String s) {// 检查时间格式是否正确
		StringTokenizer st = new StringTokenizer(s, ":");
		try {
			if (st.hasMoreElements()) {
				String strHour = (String) st.nextElement();
				if (strHour.length() == 2) {
					int hour = Integer.parseInt(strHour);
					if (hour >= 0 && hour < 24) {
						if (st.hasMoreElements()) {
							String strMinute = (String) st.nextElement();
							if (strMinute.length() == 2) {
								int minute = Integer.parseInt(strMinute);
								if (minute >= 0 && minute < 60) {
									if (st.hasMoreElements()) {
										String strSecond = (String) st.nextElement();
										if (strSecond.length() == 2) {
											int second = Integer.parseInt(strSecond);
											if (second >= 0 && second < 60) {
												if (!st.hasMoreElements()) {
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static Timestamp getLastMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(DateUtil.getYYYY()), Integer.parseInt(DateUtil.getMM()), 1, 0, 0, 0);
		calendar.add(Calendar.MONTH, -2);
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Timestamp getCurrMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(DateUtil.getYYYY()), Integer.parseInt(DateUtil.getMM()), 1, 0, 0, 0);
		calendar.add(Calendar.MONTH, -1);
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Timestamp getNextMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(DateUtil.getYYYY()), Integer.parseInt(DateUtil.getMM()), 1, 0, 0, 0);
		calendar.add(Calendar.MONTH, 0);
		return new Timestamp(calendar.getTime().getTime());
	}

	/** **********************私有方法*********************** */
	private static String getFM(String flag, Date date) {
		Date currentDate = date;
		SimpleDateFormat formatter = new SimpleDateFormat(flag, Locale.getDefault());
		String result = formatter.format(currentDate);
		return result;
	}
	/*
	 * 获取给定日期字符串的小时数
	 */
	public static int getHourOfDay(String giveDateStr){
		Calendar c = Calendar.getInstance();
		try {
			Date date = DateUtil.strToDate(giveDateStr);
			c.setTime(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.get(Calendar.HOUR_OF_DAY);
	}
	/*
	 * 判断给定时间字符串不超过当前时间一个月内
	 */
	public static boolean withinMonth(String giveDateStr) {
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 1);
			if (DateUtil.strToDate(giveDateStr).compareTo(c.getTime()) > 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 日期相减YYY
	 */
	public static long getDaySub2(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);

			if ((endDate.getTime() - beginDate.getTime()) % (24 * 3600 * 1000) > 30000) {// 超过30秒以内
				day = day + 1;
			}
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	/*
	 * 日期相减
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	/*
	 * 月份相减
	 */
	public static long getMonthSub(String beginMonthStr, String endMonthStr) {
		long month = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date beginMonth;
		Date endMonth;
		try {
			beginMonth = format.parse(beginMonthStr);
			endMonth = format.parse(endMonthStr);

			Calendar begin = Calendar.getInstance();
			begin.setTime( beginMonth );
			Calendar end = Calendar.getInstance();
			end.setTime( endMonth );
			month = end.get(Calendar.MONTH) - begin.get(Calendar.MONTH) + ((end.get(Calendar.YEAR) - begin.get(Calendar.YEAR))*12);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return month;
	}

	public static Date getExpireByHour(int hour) {
		Date d = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d);
		rightNow.add(Calendar.HOUR, hour);
		return rightNow.getTime();
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

	public static String getTodayStartDatetime() {
		return getSimpleFormatInstance().format(new Date()) + " 00:00:00";
	}

	public static String getTodayEndDatetime() {
		return getSimpleFormatInstance().format(new Date()) + " 23:59:59";
	}

	public static String getYesterdayDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		return getSimpleFormatInstance().format(calendar.getTime());
	}

	public static String getYesterdayStartDateTime() {
		return getYesterdayDate() + " 00:00:00";
	}

	public static String getYesterdayEndDateTime() {
		return getYesterdayDate() + " 23:59:59";
	}

	public static String getYesterdayDate(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getSimpleFormatInstance().parse(date));
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		return getSimpleFormatInstance().format(calendar.getTime());
	}

	public static String getYesterdayStartDateTime(String date) throws ParseException {
		return getYesterdayDate(date) + " 00:00:00";
	}

	public static String getYesterdayEndDateTime(String date) throws ParseException {
		return getYesterdayDate(date) + " 23:59:59";
	}

	// 获取本周的第一天（从周一开始）
	public static String getCurrWeekFirstDate() {
		Calendar cal = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (weekday == 1) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getSimpleFormatInstance().format(cal.getTime());
	}

	// 获取本周的最后一天（从周一开始）
	public static String getCurrWeekLastDate() {
		return DateUtil.getDayAdd(getCurrWeekFirstDate(), 6);
	}

	public static String getCurrMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getSimpleFormatInstance().format(calendar.getTime());
	}
	public static String getCurrMonthDate() {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.MONTH, 1);
		return getSimpleYyyyMmmFormatInstance().format(new Date());
	}
	public static String getCurrMonthFirstDateTime() {
		return getCurrMonthFirstDate() + " 00:00:00";
	}

	public static String getCurrMonthLastDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getSimpleFormatInstance().format(calendar.getTime());
	}

	public static String getCurrMonthLastDateTime() {
		return getCurrMonthLastDate() + " 23:59:59";
	}

	public static String getLastMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getSimpleFormatInstance().format(calendar.getTime());
	}

	public static String getLastMonthFirstDateTime() {
		return getLastMonthFirstDate() + " 00:00:00";
	}

	public static String getLastMonthLastDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return getSimpleFormatInstance().format(calendar.getTime());
	}

	public static String getLastMonthLastDateTime() {
		return getLastMonthLastDate() + " 23:59:59";
	}

	public static boolean between(String curentTime, String startTime, String endTime) {
		if (startTime.compareTo(endTime) > 0) {
			endTime = String.format("%d%s", Integer.valueOf(endTime.substring(0, 2)) + 24, endTime.substring(2));
		}

		return (curentTime.compareTo(startTime) >= 0 && curentTime.compareTo(endTime) <= 0);
	}

	public static Date parseDate(String dateStr, String dateFormat) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.parse(dateStr);
	}

	public static String getChineseMMDD(String date) { // 返回特定日期字符串(如2003-12-11)的几月几号
		StringTokenizer st = new StringTokenizer(date, "-/");
		st.nextToken();
		String mm = st.nextToken();
		if (mm.startsWith("0")) {
			mm = mm.substring(1);
		}

		String dd = st.nextToken();
		if (dd.startsWith("0")) {
			dd = dd.substring(1);
		}

		return mm + "月" + dd + "日";
	}

	public static String getCurrYearFirst() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return simpleDateFormat.format(getYearFirst(currentYear));
	}

	public static String getCurrYearLast() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return simpleDateFormat.format(getYearLast(currentYear));
	}

	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}


	/**
	 * 比较相隔天数
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}

	// 获取日期为星期几
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String parseUTCTimestamp(String timestamp) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = df.parse(timestamp);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (Exception e) {
			return "0000-00-00 00:00:00";
		}
	}

	public static String getMinuteAdd(String dateTime, int minute) {
		String currDate = null;
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = format2.parse(dateTime);
			calendar.setTime(date1);
			calendar.add(Calendar.MINUTE, minute);
			currDate = format2.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currDate;
	}

	public static String getHourAdd(String dateTime, int hour) {
		String currDate = null;
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = format2.parse(dateTime);
			calendar.setTime(date1);
			calendar.add(Calendar.HOUR_OF_DAY, hour);
			currDate = format2.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currDate;
	}

	/*
	 * 日期相减
	 */
	public static long getMinuteSub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 获取指定年月的第一天
	 * @param yyyyMM
	 * @return
	 */
	public static String getFirstDayOfMonth(String yyyyMM) {
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.setTime( format.parse(yyyyMM) );
			//获取某月最小天数
			int firstDay = cal.getMinimum(Calendar.DATE);
			//设置日历中月份的最小天数
			cal.set(Calendar.DAY_OF_MONTH,firstDay);
			//格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(cal.getTime());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定年月的第一天
	 * @param yyyyMM
	 * @return
	 */
	public static String getLastDayOfMonth(String yyyyMM) {
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.setTime( format.parse(yyyyMM) );
			//获取某月最大天数
			int lastDay = cal.getActualMaximum(Calendar.DATE);
			//设置日历中月份的最大天数
			cal.set(Calendar.DAY_OF_MONTH,lastDay);
			//格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(cal.getTime());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取本周第一天
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getFirstDayOfWeek(String yyyyMMdd) {
		String firstDay = "";
		try {
			Calendar curStartCal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dfWeek = new SimpleDateFormat("ww");
			Calendar cal = (Calendar) curStartCal.clone();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			firstDay = df.format(cal.getTime());
			System.out.println(firstDay);
		} catch (Exception e){
			e.printStackTrace();
		}
		return firstDay;
	}
	/**
	 * 获取本周最后一天
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getLastDayOfWeek(String yyyyMMdd) {
		String lastDay = "";
		try {
			Calendar curStartCal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dfWeek = new SimpleDateFormat("ww");
			Calendar cal = (Calendar) curStartCal.clone();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.add(Calendar.DATE, 6);
			lastDay = df.format(cal.getTime());
		} catch (Exception e){
			e.printStackTrace();
		}
		return lastDay;
	}
//	public static void main(String[] args) {
//		System.out.println(DateUtil.getYYYYMM());
//		System.out.println(DateUtil.getFirstDayOfMonth(DateUtil.getYYYYMM()));
//		System.out.println(DateUtil.getLastDayOfMonth(DateUtil.getYYYYMM()));
//	System.out.println(getLastDayOfWeek("2020-01-03"));
//	}

	/**
	 * startTime是否在endTime之前（时间格式yyyy-MM-dd HH:mm:ss）
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static boolean beforeEndTime(String startTime, String endTime) throws Exception{
		boolean isBefore = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = simpleDateFormat.parse(startTime);
		Date endDate = simpleDateFormat.parse(endTime);
		long startTimeMillis = startDate.getTime(); // 开始时间龊
		long endTimeMillis = endDate.getTime(); // 结束时间龊
		if(startTimeMillis<endTimeMillis){
			isBefore = true;
		}
		return isBefore;
	}
	public static boolean beforeEndDate(String startDateStr, String endDateStr) throws Exception{
		boolean isBefore = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(startDateStr);
		Date endDate = simpleDateFormat.parse(endDateStr);
		long startTimeMillis = startDate.getTime(); // 开始时间龊
		long endTimeMillis = endDate.getTime(); // 结束时间龊
		if(startTimeMillis<endTimeMillis){
			isBefore = true;
		}
		return isBefore;
	}

}
