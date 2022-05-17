/*
 * ==============================================
 * ks �༭��
 * ==============================================
 *
 * Project Info: ks �༭��;
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
 * ͨ�ø�����.
 */
public final class CommonUtil {

	/**
	 * ���캯��.
	 */
	private CommonUtil() {

	}
	
	/**
	 * �ϲ�����
	 * 
	 * @param firstArray ��һ������
	 * @param secondArray �ڶ�������
	 * 
	 * @return �ϲ��������
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
	 * ʮ�������ַ���תbyte[]
	 * 
	 * @param hex ʮ�������ַ���
	 * 
	 * @return byte[]
	 */
	public static byte[] hexStr2Byte(String hex) {
		if (hex == null) {
			return new byte[] {};
		}

		// ����λ��0
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
	 * byte[]תʮ�������ַ���
	 * 
	 * @param array byte[]
	 * 
	 * @return ʮ�������ַ���
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
	 * byteתʮ�������ַ�
	 * 
	 * @param b byte
	 * 
	 * @return ʮ�������ַ�
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
	 * ȡ����ˮ��.
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
	 * ȡ�������.
	 * 
	 * @return
	 */
	public static int getRandom(int maxValue) {
		Random random = new Random();
		return Math.abs(random.nextInt()) % maxValue;
	}

	/**
	 * ȡ�õ�ǰ����.
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
	 * ȡ������.
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
	 * ��֤�ǲ�������(û��С����)
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		return match("^[0-9]*$", number);
	}

	/**
	 * ִ��������ʽ
	 * 
	 * @param pattern
	 *          ���ʽ
	 * @param str
	 *          ����֤�ַ���
	 * @return ���� <b>true </b>,����Ϊ <b>false </b>
	 */
	private static boolean match(String pattern, String str) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * ����key��ȡvalue.
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
	 * д��properties��Ϣ.
	 */
	public static void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// ���������ж�ȡ�����б�����Ԫ�ضԣ�
			prop.load(fis);
			// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
			// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
			// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName
					+ " value error");
		}
	}

	/**
	 * ������ת�ַ���.
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
	 * �����ַ�����ֵ.
	 * 
	 * @param value: �ַ���.
	 * @return String: �ַ���.
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
	 * ������ֵ��ֵ.
	 * 
	 * @param value: ��ֵ.
	 * @return String: �ַ���.
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
	 * ������ֵ��ֵ.
	 * 
	 * @param value: ��ֵ.
	 * @return String: �ַ���.
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
	 * �������ڿ�ֵ.
	 * 
	 * @param value: ����.
	 * @return String: �ַ���.
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
	 * �������ڿ�ֵ.
	 * 
	 * @param value: ����.
	 * @return String: �ַ���.
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
	 * �ַ���ת����.
	 * @param dateStr��
	 * @param formatStr��
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
