package com.puyixiaowo.tnews.common.utils;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils implements Converter {
	public static String dateFormatStr = "yyyy-MM-dd";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			dateFormatStr);

	public static String dateTimeFormatStr = dateFormatStr + " HH:mm:ss";
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			dateTimeFormatStr);

	public static String dateTimeNumFormatStr = "yyyyMMddHHmmss";
	private static SimpleDateFormat dateTimeNumFormat = new SimpleDateFormat(
			dateTimeNumFormatStr);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null) {
			return null;
		}
		String className = arg1.getClass().getName();
		// java.sql.Timestamp
		if (arg1 instanceof Date) {
			return (arg1);
		}
		if ("java.sql.Timestamp".equalsIgnoreCase(className)) {
			try {
				SimpleDateFormat df = new SimpleDateFormat(dateFormatStr
						+ " HH:mm:ss");
				return df.parse(dateTimeFormat.format(arg1));
			} catch (Exception e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
					return df.parse(dateFormat.format(arg1));
				} catch (ParseException ex) {
					e.printStackTrace();
					return null;
				}
			}
		} else {// java.util.Date,java.sql.Date
			String p = (String) arg1;
			if (p == null || p.trim().length() == 0) {
				return null;
			}
			try {
				SimpleDateFormat df = new SimpleDateFormat(dateFormatStr
						+ " HH:mm:ss");
				return df.parse(p.trim());
			} catch (Exception e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
					return df.parse(p.trim());
				} catch (ParseException ex) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	public static String formatDate(Object obj) {
		if (obj != null)
			return dateFormat.format(obj);
		else
			return "";
	}

	public static String formatDateTime(Object obj) {
		if (obj != null)
			return dateTimeFormat.format(obj);
		else
			return "";
	}

	public static String formatNumDateTime(Object obj) {
		if (obj != null)
			return dateTimeNumFormat.format(obj);
		else
			return "";
	}

	public static Date parseDate(String dateStr) {
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date parseDateTime(String dateStr) {
		try {
			return dateTimeFormat.parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * 获取前后日期
	 * @param n
	 * 		n为正数 向后推迟n天，负数时向前提前n天
	 * @param iszero
	 * 		是否零点
	 * @return
	 */
	public static Date getDate(int n, boolean iszero) {
		Date dat = null;
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, n);
		if (iszero) {
			cd.set(Calendar.HOUR, 0);
			cd.set(Calendar.MINUTE, 0);
			cd.set(Calendar.SECOND, 0);
		}
		dat = cd.getTime();
		//Timestamp date = Timestamp.valueOf(dateTimeFormat.format(dat));
		return dat;
	}

	/**
	 * 判断是否是合法的时间格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidDateTime(String str) {

		boolean convertSuccess = true;
		try {
			dateTimeFormat.setLenient(false);
			dateTimeFormat.parse(str);
		} catch (Exception e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	public static void main(String[] args) {
		Date date = getDate(-7, true);

		System.out.println(DateUtils.formatDateTime(date));
	}
}
