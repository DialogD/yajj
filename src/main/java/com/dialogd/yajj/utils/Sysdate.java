package com.dialogd.yajj.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 统一时间工具类
 * 
 * @author chenliang
 * 
 */
public class Sysdate {
	private static int gap = 0; // 毫秒
	private static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	private static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	private static String YMD = "yyyy-MM-dd";
	private static String YYYYMMDD = "yyyyMMdd";
	private static String YM = "yyyy-MM";

	/**
	 * 可以每天调整一次时钟 或者每次启动时调整
	 * 
	 * @param databaseTime
	 * @throws Exception
	 * @see
	 */
	public static void init(Date databaseTime) throws Exception {
		Date time2 = new Date();
		long ll = databaseTime.getTime() - time2.getTime();
		if (Math.abs(ll) > 1000 * 3600 * 24 * 700)
			throw new Exception("调整的时间太大[" + databaseTime + "],超过700天");

		gap = (int) ll;

	}

	/**
	 * 同步时间
	 * 
	 * @param databaseTime
	 * @throws Exception
	 * @see
	 */
	public static void initLocalTime(Date databaseTime) throws Exception {
		try {
			String time = DateFormatUtils.format(databaseTime, YYYYMMDDHHMMSS);
			// String time = DateUtil.getDateTimeStr(databaseTime, false);
			String year = time.substring(0, 4);
			String month = time.substring(4, 6);
			String day = time.substring(6, 8);
			String hours = time.substring(8, 10);
			String minutes = time.substring(10, 12);
			String seconds = time.substring(12, 14);
			String osName = System.getProperty("os.name");
			if (osName.matches("^(?i)Windows.*$")) {
				Runtime.getRuntime().exec("cmd /c date " + year + "-" + month + "-" + day);
				Runtime.getRuntime().exec("cmd /c time " + hours + ":" + minutes + ":" + seconds);
			} else
			// linux or solaris;
			{
				// date 091815302010.59 ， 9月18日 15点30分 2010年，59秒 ，
				Runtime.getRuntime().exec("date " + month + day + hours + minutes + year + "." + seconds);
			}
		} catch (Exception e) {
			throw new Exception("调整时间出现错误");
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @see
	 */
	public static Date getDate() {
		return DateUtils.addMilliseconds(new Date(), (int) gap);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static Date getDayDate(boolean isYYYYMMDD) throws Exception {
		return parseDate(getDateStr(isYYYYMMDD), isYYYYMMDD);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @see
	 */
	public static Date parseDate(String dateStr, boolean isYYYYMMDDHHMMSS) throws Exception {
		if (isYYYYMMDDHHMMSS) {
			return DateUtils.parseDate(dateStr, YYYYMMDDHHMMSS);
		} else {
			return DateUtils.parseDate(dateStr, YMDHMS);
		}
	}

	/**
	 * 获取当前日期
	 * 
	 * @param isYYYYMMDD
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getDateStr(boolean isYYYYMMDD) {
		if (isYYYYMMDD) {
			return DateFormatUtils.format(getDate(), YYYYMMDD);
		} else {
			return DateFormatUtils.format(getDate(), YMD);
		}
	}

	/**
	 * 获取日期
	 * 
	 * @param isYYYYMMDD
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getDateStr(Date date, boolean isYYYYMMDD) {
		if (isYYYYMMDD) {
			return DateFormatUtils.format(date, YYYYMMDD);
		} else {
			return DateFormatUtils.format(date, YMD);
		}
	}

	/**
	 * 获取几天后或者几天前(负数)的日期
	 * 
	 * @param day
	 * @param isYYYYMMDD
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getDateStr(int day, boolean isYYYYMMDD) {
		if (isYYYYMMDD) {
			return DateFormatUtils.format(DateUtils.addDays(getDate(), day), YYYYMMDD);
		} else {
			return DateFormatUtils.format(DateUtils.addDays(getDate(), day), YMD);
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @param isYYYYMMDDHHMMSS
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getTimeStr(boolean isYYYYMMDDHHMMSS) {
		if (isYYYYMMDDHHMMSS) {
			return DateFormatUtils.format(getDate(), YYYYMMDDHHMMSS);
		} else {
			return DateFormatUtils.format(getDate(), YMDHMS);
		}

	}

	/**
	 * 获取时间戳
	 * 
	 * @param day
	 * @param isYYYYMMDDHHMMSS
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getTimeStr(Date date, boolean isYYYYMMDDHHMMSS) {
		if (isYYYYMMDDHHMMSS) {
			return DateFormatUtils.format(date, YYYYMMDDHHMMSS);
		} else {
			return DateFormatUtils.format(date, YMDHMS);
		}

	}

	/**
	 * 获取几天后或者几天前(负数)的时间
	 * 
	 * @param day
	 * @param isYYYYMMDDHHMMSS
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getTimeStr(int day, boolean isYYYYMMDDHHMMSS) {
		if (isYYYYMMDDHHMMSS) {
			return DateFormatUtils.format(DateUtils.addDays(getDate(), day), YYYYMMDDHHMMSS);
		} else {
			return DateFormatUtils.format(DateUtils.addDays(getDate(), day), YMDHMS);
		}

	}

	/**
	 * 获取几个月后或者几个月前(负数)的月份
	 *
	 * @param month
	 * @param parsePatterns
	 * @return
	 */
	public static String getMonthStr(int month, String parsePatterns) {
		return DateFormatUtils.format(DateUtils.addMonths(getDate(), month), parsePatterns);
	}

	/**
	 * 获取几天后的时间
	 * 
	 * @param isYYYYMMDDHHMMSS
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getTimeStrBegin(boolean isYYYYMMDDHHMMSS) {
		if (isYYYYMMDDHHMMSS) {
			return getDateStr(true) + "000000";
		} else {
			return getDateStr(false) + " 00:00:00";
		}

	}

	public static int getGap() {
		return gap;
	}

	/**
	 * 比较两个时间之间间隔天数 YMDHMS
	 * 
	 * @param stime
	 * @param etime
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static long getBetweenDaysByTime(String stime, String etime) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(YMDHMS);
		Date sdate = df.parse(stime);
		Date eDate = df.parse(etime);
		long betweendays = (long) ((eDate.getTime() - sdate.getTime()) / (1000 * 60 * 60 * 24) + 0.5);// 天数间隔
		return betweendays;
	}

	/**
	 * 比较两个时间之间间隔天数 YMDHMS
	 * 
	 * @param stime
	 * @param etime
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static long getBetweenDaysByDate(String sdate, String edate, boolean isYYYYMMDD) throws Exception {
		SimpleDateFormat df = null;
		if (isYYYYMMDD) {
			df = new SimpleDateFormat(YYYYMMDD);
		} else {
			df = new SimpleDateFormat(YMD);
		}
		Date sDate = df.parse(sdate);
		Date eDate = df.parse(edate);
		long betweendays = (long) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24) + 0.5);// 天数间隔
		return betweendays;
	}

	/**
	 * 获取两个日期之间的所有日期
	 * 
	 * @param startTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @return
	 */
	public static List<String> getBetweenDays(String startTime, String endTime, boolean isYYYYMMDD) throws Exception {
		// 返回的日期集合
		List<String> days = new ArrayList<String>();

		SimpleDateFormat df = null;
		if (isYYYYMMDD) {
			df = new SimpleDateFormat(YYYYMMDD);
		} else {
			df = new SimpleDateFormat(YMD);
		}
		Date start = df.parse(startTime);
		Date end = df.parse(endTime);

		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
		while (tempStart.before(tempEnd)) {
			days.add(df.format(tempStart.getTime()));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return days;
	}

	/**
	 * 获取两个日期之间的所有月份
	 * 
	 * @param startMonth
	 *            开始(包含)
	 * @param endMonth
	 *            结束(包含)
	 * @return
	 */
	public static List<String> getBetweenMonths(String startMonth, String endMonth) throws Exception {
		// 返回的日期集合
		List<String> months = new ArrayList<String>();

		SimpleDateFormat df = new SimpleDateFormat(YM);
		Date start = df.parse(startMonth);
		Date end = df.parse(endMonth);

		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		tempEnd.add(Calendar.MONTH, +1);// 月份加1(包含结束)
		while (tempStart.before(tempEnd)) {
			months.add(df.format(tempStart.getTime()));
			tempStart.add(Calendar.MONTH, 1);
		}
		return months;
	}

	// 获取当前月最后一天
	public static Date getCurMothLastDay(Date curDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(curDate);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	// 获取当前月最后一天
	public static String getCurMothLastDay(String curDate, String parsePatterns) throws Exception {
		Calendar ca = Calendar.getInstance();
		ca.setTime(parseDate(curDate, parsePatterns));
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(ca.getTime(), parsePatterns);
	}

	// 获取当前月第一天
	public static String getCurMothFirstDay(String curDate, String parsePatterns) throws Exception {
		Calendar ca = Calendar.getInstance();
		ca.setTime(parseDate(curDate, parsePatterns));
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(ca.getTime(), parsePatterns);
	}

	/**
	 * 日期格式化
	 *
	 * @return
	 * @see
	 */
	public static Date parseDate(String str, String parsePatterns) throws Exception {
		return DateUtils.parseDate(str, parsePatterns);
	}

	/**
	 * 
	 * 获取到次日0点的秒数
	 * 
	 * @return
	 * @see
	 */
	public static int getSecondsNextDayStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Long seconds = (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		int ret = seconds.intValue();
		return ret;
	}

	/**
	 * 获取几天后或者几天前(负数)的日期
	 * 
	 * @param date
	 * @param day
	 * @param isYYYYMMDD
	 *            是否为全字符串格式
	 * @return
	 * @see
	 */
	public static String getDateAddStr(Date date, int day, boolean isYYYYMMDD) {
		if (isYYYYMMDD) {
			return DateFormatUtils.format(DateUtils.addDays(date, day), YYYYMMDD);
		} else {
			return DateFormatUtils.format(DateUtils.addDays(date, day), YMD);
		}
	}
}
