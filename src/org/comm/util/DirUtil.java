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
import java.io.File;
import java.io.FileInputStream;

/**
 * Ŀ¼������.
 * 
 */
public final class DirUtil {

	public static String OS_ARCHIVE_PATH = System.getProperty("user.dir");

	/**
	 * ���캯��.
	 */
	private DirUtil() {
	}

	/**
	 * ��������·��(�����༶).
	 * 
	 * @param header String������·����ǰ�벿��(�Ѵ���).
	 * @param tail   String������·���ĺ�벿��(��һ�������һ���ַ�������/����ʽ��123/258/456).
	 * @return String���´����ľ���·��.
	 */
	public static String makeDir(String header, String tail) {
		String[] sub = tail.split("/");
		File dir = new File(header);
		for (int i = 0; i < sub.length; i++) {
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dir2 = new File(dir + File.separator + sub[i]);
			if (!dir2.exists()) {
				dir2.mkdir();
			}
			dir = dir2;
		}
		return dir.toString();
	}

	/**
	 * �жϵ�ǰ����ϵͳ�ǲ���window.
	 * 
	 * @return boolean
	 */
	public static boolean isWindows() {
		boolean flag = false;
		if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ȡ���ļ������ʽ
	 * 
	 * @param sourceFile File���ļ�.
	 * @return String:�����ʽ
	 */
	public static String getFilecharset(File sourceFile) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				bis.close();
				return charset; // �ļ�����Ϊ ANSI
			} else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // �ļ�����Ϊ Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // �ļ�����Ϊ Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // �ļ�����Ϊ UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // ��������BF���µģ�Ҳ����GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // ˫�ֽ� (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),Ҳ������GB������
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// Ҳ�п��ܳ������Ǽ��ʽ�С
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	// test
	public static void main(String[] args) throws Exception {

	}
}
