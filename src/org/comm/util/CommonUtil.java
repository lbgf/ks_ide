/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
 *
 */

package org.comm.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

/**
 * 通用辅助类.
 */
public final class CommonUtil {

	/**
	 * 构造函数.
	 */
	private CommonUtil() {

	}
	
	/**
	 * 合并数组
	 * 
	 * @param firstArray 第一个数组
	 * @param secondArray 第二个数组
	 * 
	 * @return 合并后的数组
	 */
	public static byte[] concat(byte[] firstArray, byte[] secondArray) {
		if (firstArray == null || secondArray == null) {
			return null;
		}
		byte[] bytes = new byte[firstArray.length + secondArray.length];
		System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
		System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
		return bytes;
	}
	
	/**
	 * 十六进制字符串转byte[]
	 * 
	 * @param hex 十六进制字符串
	 * 
	 * @return byte[]
	 */
	public static byte[] hexStr2Byte(String hex) {
		if (hex == null) {
			return new byte[] {};
		}

		// 奇数位补0
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}

		int length = hex.length();
		ByteBuffer buffer = ByteBuffer.allocate(length / 2);
		for (int i = 0; i < length; i++) {
			String hexStr = hex.charAt(i) + "";
			i++;
			hexStr += hex.charAt(i);
			byte b = (byte) Integer.parseInt(hexStr, 16);
			buffer.put(b);
		}
		return buffer.array();
	}

	/**
	 * byte[]转十六进制字符串
	 * 
	 * @param array byte[]
	 * 
	 * @return 十六进制字符串
	 */
	public static String byteArrayToHexString(byte[] array) {
		if (array == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buffer.append(byteToHex(array[i]));
		}
		return buffer.toString();
	}

	/**
	 * byte转十六进制字符
	 * 
	 * @param b byte
	 * 
	 * @return 十六进制字符
	 */
	public static String byteToHex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase(Locale.getDefault());
	}
	
	public static String getTimeStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
	}
	
	public static String getDateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
	}
	
	/**
	 * 取得流水号.
	 * 
	 * @return
	 */
	public static String getSeqNum() {
		Date date = new Date();
		String format = "yyyyMMddHHmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);
		Random random = new Random();
		int ran = random.nextInt(9000) + 1000;
		String num = time + ran;
		return num;
	}

	/**
	 * 取得随机数.
	 * 
	 * @return
	 */
	public static int getRandom(int maxValue) {
		Random random = new Random();
		return Math.abs(random.nextInt()) % maxValue;
	}

	/**
	 * 取得当前日期.
	 * 
	 * @return
	 */
	public static String getCurrDate() {
		Date date = new Date();
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 取得日期.
	 * 
	 * @return
	 */
	public static String getDate(long d) {
		Date date = new Date(d);
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 验证是不是数字(没有小数点)
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		return match("^[0-9]*$", number);
	}

	/**
	 * 执行正则表达式
	 * 
	 * @param pattern
	 *          表达式
	 * @param str
	 *          待验证字符串
	 * @return 返回 <b>true </b>,否则为 <b>false </b>
	 */
	private static boolean match(String pattern, String str) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 根据key读取value.
	 */
	public static String readPropValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			// System.out.println(key + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写入properties信息.
	 */
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName
					+ " value error");
		}
	}

	/**
	 * 输入流转字符串.
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 处理字符串空值.
	 * 
	 * @param value: 字符串.
	 * @return String: 字符串.
	 * @throws Exception
	 */
	public static String formatString(String value) throws Exception {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	/**
	 * 处理数值空值.
	 * 
	 * @param value: 数值.
	 * @return String: 字符串.
	 * @throws Exception
	 */
	public static String formatString(Long value) throws Exception {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	/**
	 * 处理数值空值.
	 * 
	 * @param value: 数值.
	 * @return String: 字符串.
	 * @throws Exception
	 */
	public static String formatString(Double value) throws Exception {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	/**
	 * 处理日期空值.
	 * 
	 * @param value: 日期.
	 * @return String: 字符串.
	 * @throws Exception
	 */
	public static String formatString(Date value, String format) throws Exception {
		if (value == null) {
			return "";
		} else {
			// System.out.println(format + "," + value);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(value);
		}
	}

	/**
	 * 处理日期空值.
	 * 
	 * @param value: 日期.
	 * @return String: 字符串.
	 */
	public static String formatString(Timestamp time, String pattern) {
		if (time == null) {
			// throw new IllegalArgumentException("Timestamp is null");
			return "";
		}

		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 字符串转日期.
	 * @param dateStr：
	 * @param formatStr：
	 * @return
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

}
