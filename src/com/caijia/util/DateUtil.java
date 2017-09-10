package com.caijia.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
  *
  * @version 1.0
  * @author liuxinyuan
  * @Description 
  * @date 2014-3-4
  */ 
public class DateUtil {

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式化指定pattern</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return "";
		if (pattern == null)
			pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return (sdf.format(date));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:yyyy年MM月dd日HH时mm分ss秒</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDateTimeCN(Date date) {
		return (formatDate(date, "yyyy年MM月dd日HH时mm分ss秒"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:yyyy-MM-dd HH:mm:ss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间为yyyy-MM-dd HH:mm:ss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:转换日期字符串如19990101为1999-01-01格式</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String DateStringFormatter(String strDate) {
		String dateStr = "";
		if (strDate != null && strDate.length() >= 8) {
			dateStr = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
		}
		return dateStr;
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:yyyy年MM月dd日</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDateCN(Date date) {
		return (formatDate(date, "yyyy年MM月dd日"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:yyyy-MM-dd</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate(Date date) {
		return (formatDate(date, "yyyy-MM-dd"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间为yyyy-MM-dd</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间为yyyyMMdd</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate2() {
		return (formatDate(now(), "yyyyMMdd"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间为yyyyMMddHHmmss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate3() {
		return (formatDate(now(), "yyyyMMddHHmmss"));
	}
	
	//精确到毫秒
	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间为yyyyMMddHHmmssSS</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate4() {
		return (formatDate(now(), "yyyyMMddHHmmssSS"));
	}
	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间为yyyy-MM-dd HH:mm:ss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatDate5() {
		return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
	}
	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的年份为yyyy</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatYear() {
		return (formatDate(now(), "yyyy"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的月份为MM</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatMonth() {
		return (formatDate(now(), "MM"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:HH:mm:ss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatTime(Date date) {
		return (formatDate(date, "HH:mm:ss"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间HH:mm:ss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatTime() {
		return (formatDate(now(), "HH:mm:ss"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:格式现在的时间HHmmss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static String formatTime2() {
		return (formatDate(now(), "HHmmss"));
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:获取现在的时间</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date now() {
		return (new Date());
	}

	public static String formatDateWithseparator(String date, int length) {
		if (date == null || "".equals(date)) {
			return "";
		}
		if (date.length() >= 8 && length == 8) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
		}
		if (date.length() >= 14 && length == 14) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " "
					+ date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
		}
		if (date.length() >= 12 && length == 12) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " "
					+ date.substring(8, 10) + ":" + date.substring(10, 12);
		}
		return "";
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description: 返回日期时间型为yyyy-MM-dd HH:mm:ss</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date parseDateTime(String datetime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ((datetime == null) || (datetime.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(datetime);
			} catch (ParseException e) {
				return parseDate(datetime);
			}
		}
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:返回日期型为yyyy-MM-dd</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:返回日期型为yyyyMMdd</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date parseDate2(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		if ((date == null) || (date.equals(""))) {
			return null;
		} else {
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				return null;
			}
		}
	}


	public static String formatDate(Object o) {
		if (o == null)
			return "";
		if (o.getClass() == String.class)
			return formatDate((String) o);
		else if (o.getClass() == Date.class)
			return formatDate((Date) o);
		else if (o.getClass() == Timestamp.class) {
			return formatDate(new Date(((Timestamp) o).getTime()));
		} else
			return o.toString();
	}
	public static String formatDateTime(Object o) {
		if (o.getClass() == String.class)
			return formatDateTime((String) o);
		else if (o.getClass() == Date.class)
			return formatDateTime((Date) o);
		else if (o.getClass() == Timestamp.class) {
			return formatDateTime(new Date(((Timestamp) o).getTime()));
		} else
			return o.toString();
	}

	/**
	 * 返回年份
	 *
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	

	/**
	 *
	 *
	 *
	 * @return
	 */
	/**
	 * <p>Title: ****</p>
	 * <p>Description:加时间</p>
	 * @param field
	 *            Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param date
	 * @param amount
	 * @return 
	 * @throw
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:加毫秒</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description：加秒</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:加分</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addMiunte(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:加时</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:加天</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:加月</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * <p>Title: ****</p>
	 * <p>Description:加年</p>
	 * @param 
	 * @return 
	 * @throw
	 */
	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	public static Date getDate() {
		return parseDate(formatDate());
	}

	public static Date getDateTime() {
		return parseDateTime(formatDateTime());
	}

	/**
	 * 用指定的格式将字符串转化为Date对象
	 *
	 * @param stringDate
	 * @param format
	 * @return
	 */
	public static Date getDateFromString(String stringDate, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(stringDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 将int转化成string，如果是一位，则前面加"0"
	 *
	 * @param i
	 * @return
	 */
	public static String valueOfString(int i) {
		if (i < 10)
			return "0" + i;
		return String.valueOf(i);
	}

	/**
	 * 从calendar对象中拿到年月日（yyyyMMdd）字符串
	 *
	 * @param cal
	 * @return
	 */
	public static String getYMD(Calendar cal) {
		return getYM(cal) + valueOfString(cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * date 从calendar对象中拿到年月日（yyyyMMdd）字符串
	 *
	 * @param cal
	 * @return
	 */
	public static String getYMD(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return getYMD(c);
	}

	/**
	 * 从calendar对象中拿到年月（yyyyMM）字符串
	 *
	 * @param cal
	 * @return
	 */
	public static String getYM(Calendar cal) {
		return cal.get(Calendar.YEAR) + valueOfString(cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 从date对象中拿到年月（yyyyMM）字符串
	 *
	 * @param cal
	 * @return
	 */
	public static String getYM(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return getYM(c);
	}
	/**
	 * 计算当前日期前(后)几天的日期
	 * 
	 * @param strdate
	 *            String
	 * @param countdate
	 *            String
	 * @return String
	 */
	public static String todateStr(int countdate) {
		SimpleDateFormat wew = new SimpleDateFormat("yyyy-MM-dd"); // 实例化日前格式化类
		java.util.Date aa = new Date();// 声明一个日前变量
		long yihou;// 输入的日前转换成的long型系统计算默认是毫秒
		String dfdfd = "";// 返回的字符串时间
		try {
			yihou = aa.getTime();// 把这个时间转化成long型时间戳 系统计算默认是毫秒
			long tianshu = countdate * 24 * 60 * 60 * 1000;// 转换天数为long
															// 系统计算默认是毫秒
			long jieguo = yihou - tianshu; // 得到相加后的时间戳
			java.util.Date ddfdf = new Date(jieguo); // 声明日前变量，并初试化值
			dfdfd = wew.format(ddfdf);// 格式化时间
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dfdfd;// 返回字符传
	}

	public static Date todateDate(int countdate) {
		SimpleDateFormat wew = new SimpleDateFormat("yyyy-MM-dd"); // 实例化日前格式化类
		java.util.Date aa = new Date();// 声明一个日前变量
		long yihou;// 输入的日前转换成的long型系统计算默认是毫秒
		java.util.Date ddfdf = null;
		try {
			yihou = aa.getTime();// 把这个时间转化成long型时间戳 系统计算默认是毫秒
			long tianshu = countdate * 24 * 60 * 60 * 1000;// 转换天数为long
															// 系统计算默认是毫秒
			long jieguo = yihou - tianshu; // 得到相加后的时间戳
			ddfdf = new Date(jieguo); // 声明日前变量，并初试化值
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ddfdf;// 返回字符传
	}

}
