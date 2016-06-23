package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间格式化
 * 
 * @author DLHT
 *
 */
public class DateUtils {
	/**
	 * 2015-09-23 14:23:40
	 */
	public static final String DIGIT14DATE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 2015-09-23
	 */
	public static final String DIGIT8DATE = "yyyy-MM-dd";
	/**
	 * 23/09/2015:14:23:40
	 */
	public static final String DATEFORMAT = "dd/MM/yyyy:HH:mm:ss";

	/**
	 * 09101423
	 */
	public static final String DATEFORMAT_MMDDHHMM = "MMddHHmm";
	/**
	 * 09-10 14:23:40
	 */
	public static final String DATEFORMAT_WIDGETLOG = "MM-dd HH:mm:ss";

	/**
	 * 月份英文简写与数据对应关系
	 * 
	 * @return map Jan:01
	 */
	public static final Map<String, String> monthMap = setupMonth();

	/**
	 * 月份英文简写与数据对应关系
	 * 
	 * @return map Jan:01
	 */
	public static Map<String, String> setupMonth() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Jan", "01");
		map.put("Feb", "02");
		map.put("Mar", "03");
		map.put("Apr", "04");
		map.put("May", "05");
		map.put("Jun", "06");
		map.put("Jul", "07");
		map.put("Aug", "08");
		map.put("Sep", "09");
		map.put("Oct", "10");
		map.put("Nov", "11");
		map.put("Dec", "12");
		return map;
	}

	/**
	 * 获得系统当前日期
	 *
	 * @param format
	 *            日期转换格式
	 * @return 当前日期转换好的字符串
	 */
	public static String getNowDate(String format) {
		Date dateNow = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(dateNow);
	}

	/**
	 * 日期加，减运算
	 *
	 * @param date
	 *            日期
	 * @param count
	 *            加，减的数量(day)
	 * @param format
	 *            日期转换格式
	 * @return 结果日期转换好的字符串
	 */
	public static String addDateDay(Date date, int count, String format) {
		return parseDate(addDateDay(date, count), format);
	}

	/**
	 * 日期加，减运算
	 *
	 * @param date
	 *            日期
	 * @param count
	 *            加，减的数量(day)
	 * @return 加减后的日期
	 */
	public static Date addDateDay(Date date, int count) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, count);
		return calendar.getTime();
	}

	/**
	 * 日期格式转换为字符串
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            日期转换格式
	 * @return 根据转换格式转换完成的字符串
	 */
	public static String parseDate(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            字符串的装换格式
	 * @return 根据转换格式转换完毕的日期
	 */
	public static Date parserText(String date, String format) {
		SimpleDateFormat simpleDateFormat = null;
		if (format != null && !"".equals(format))
			simpleDateFormat = new SimpleDateFormat(format);
		else {
			simpleDateFormat = new SimpleDateFormat();
		}
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 根据短英文月份获取月份数字
	 * 
	 * @param month
	 *            Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
	 * @return 01 ,02 ,03, 04 ,05 ,06 ,07 ,08 ,09 ,10 ,11 ,12
	 */
	public static String getDigitDate(String month) {
		return (String) monthMap.get(month);
	}

	/**
	 * 获取该日期为当月的第几天
	 * 
	 * @param date
	 *            Fri Jun 19 11:14:04 CST 2015
	 * @return 19
	 */
	public static int getDayOfMonth(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		return c1.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取该日期的月份
	 * 
	 * @param date
	 *            Fri Jun 19 11:14:04 CST 2015
	 * @return 6
	 */
	public static int getMonth(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		return c1.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取该日期的月份共有多少天
	 * 
	 * @param date
	 *            Fri Jun 19 11:14:04 CST 2015
	 * @return 30
	 */
	public static int getDayCountInMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.roll(Calendar.DATE, false);
		return calendar.get(Calendar.DATE);
	}

	public static void main(String[] args) throws Exception {
		Date d1 = new Date();

		System.out.println(d1);
		System.out.println(DateUtils.getDayOfMonth(d1));
		System.out.println(DateUtils.getDayCountInMonth(d1));

		Date d2 = addDateDay(d1, 3);

		System.out.println(d1.getTime());

		System.out.println(d2.getTime());

		System.out.println(d2.getTime() - d1.getTime());

		System.out.println();
	}
}
