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
import java.io.File;
import java.io.FileInputStream;

/**
 * 目录辅助类.
 * 
 */
public final class DirUtil {

	public static String OS_ARCHIVE_PATH = System.getProperty("user.dir");

	/**
	 * 构造函数.
	 */
	private DirUtil() {
	}

	/**
	 * 创建绝对路径(包含多级).
	 * 
	 * @param header String：绝对路径的前半部分(已存在).
	 * @param tail   String：绝对路径的后半部分(第一个和最后一个字符不能是/，格式：123/258/456).
	 * @return String：新创建的绝对路径.
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
	 * 判断当前操作系统是不是window.
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
	 * 取得文件编码格式
	 * 
	 * @param sourceFile File：文件.
	 * @return String:编码格式
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
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
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
