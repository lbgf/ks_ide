/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
 *
 */

package org.ksb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

import org.comm.util.CommonUtil;
import org.comm.util.DirUtil;
import org.ks.core.KsRunner;
import org.ksb.frame.MainFrame;

/**
 * ks ide工具类.
 *
 */
public final class KsTool {
	
	public final static byte RUN_INSIDE = 0;
	public final static byte RUN_COMMAND = 1;
	
	private final static String exeFile = "\\ks.exe";
	
	private static byte runType = RUN_INSIDE;
	private static Process process = null;
	private static boolean isRunning = false;
	private static Thread runThread = null;
	private static Thread printThread = null;

	/**
	 * 构造函数.
	 */
	private KsTool() {
	}
	
	/**
	 * 运行ks.
	 * 
	 * @param mf MainFrame:窗体
	 * @param content String:内容
	 * 
	 * @throws Exception
	 */
	public static void runKs(MainFrame mf, String content) throws Exception {

		mf.txtConsole.setText("");

		runThread = new Thread(new Runnable() {
			public void run() {
				try {

					/*
					if (mf.ckDebug.isSelected()) {
						cmdLine += " -d";
					}
					*/
					
					mf.txtConsole.append("\n" + "开始执行...");

					try {
						isRunning = true;
						
						mf.txtConsole.append("\n执行结果：\n");

						KsRunner kr = new KsRunner(content, null);
						kr.exec();

					} catch (Exception e) {
						throw new Exception(e);
					}
				} catch (Exception e) {
					e.printStackTrace();
					mf.txtConsole.append("\n" + e.getCause().getMessage());
				} finally {
					isRunning = false;
					mf.txtConsole.append("\n程序结束！");
					mf.btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/run.png"));
					mf.btnRun.setToolTipText("运行");
					if (printThread != null) {
						printThread.stop();
						printThread = null;
					}
					if (runThread != null) {
						runThread.stop();
						runThread = null;
					}
				}

			}
		});
		runThread.start();

	}

	/**
	 * 运行ks文件.
	 * 
	 * @param mf MainFrame:窗体
	 * @param content String:内容
	 * 
	 * @throws Exception
	 */
	public static void runKsFile(MainFrame mf, String content) throws Exception {

		mf.txtConsole.setText("");

		String root = DirUtil.OS_ARCHIVE_PATH+ "\\";//"out\\";
		if (!(new File(root).isDirectory())) {
			new File(root).mkdir();
		}

		String outputFile = root + CommonUtil.formatString(new Date(), "yyyyMMdd") + ".ks";

		OutputStream os = new FileOutputStream(outputFile);
		os.write(content.getBytes());
		os.flush();
		os.close();

		new Thread(new Runnable() {
			public void run() {
				try {
					mf.txtConsole.setText("生成脚本文件：");
					mf.txtConsole.append("\n" + outputFile);

					String cmdLine = DirUtil.OS_ARCHIVE_PATH + exeFile +" " + outputFile;

					if (mf.ckDebug.isSelected()) {
						cmdLine += " -d";
					}

					Runtime runtime = Runtime.getRuntime();
					// System.out.println(cmdLine);

					mf.txtConsole.append("\n" + "开始执行...");
					mf.txtConsole.append("\n" + cmdLine);

					try {
						isRunning = true;
						process = runtime.exec(cmdLine);

						final InputStream is1 = process.getInputStream();
						mf.txtConsole.append("\n执行结果：\n");

						// 进程中完成命令执行
						new Thread(new Runnable() {
							public void run() {
								BufferedReader br = new BufferedReader(new InputStreamReader(is1));
								try {
									String s;
									while ((s = br.readLine()) != null) {
										mf.txtConsole.append("\n" + s);
										// System.out.println(s);
									}
								} catch (Exception e) {
									e.printStackTrace();
									mf.txtConsole.append("\n" + e.getCause().getMessage());
								}
							}
						}).start();

						// 出错处理
						InputStream is2 = process.getErrorStream();
						BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
						String line = null;
						while ((line = br2.readLine()) != null) {
							mf.txtConsole.append("\n" + line);
							// System.out.println(line);
						}

						process.waitFor();

						mf.txtConsole.setCaretPosition(mf.txtConsole.getText().length());

					} catch (Exception e) {
						throw new Exception(e);
					}
				} catch (Exception e) {
					e.printStackTrace();
					mf.txtConsole.append("\n" + e.getCause().getMessage());
				} finally {
					isRunning = false;
					mf.txtConsole.append("\n程序结束！");
					if (process != null) {
						process.destroy();
					}
					mf.btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/run.png"));
					mf.btnRun.setToolTipText("运行");
				}

			}
		}).start();

	}
	
	public static void stopRun() {
		if (KsTool.getRunType() == KsTool.RUN_INSIDE) {
			if (runThread != null) {
				//runThread.interrupt();
				runThread.stop();
				runThread = null;			
			}
			isRunning = false;
			if (printThread != null) {
				//printThread.interrupt();
				printThread.stop();
				printThread = null;
			}
		} else if (KsTool.getRunType() == KsTool.RUN_COMMAND) {
			isRunning = false;
			if (process != null) {
				process.destroy();
			}
		} else {
			
		}
	}
	
	public static Process getProcess() {
		return process;
	}

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		KsTool.isRunning = isRunning;
	}
	
	public static void setRunType(byte runType) {
		KsTool.runType = runType;
	}
	
	public static byte getRunType() {
		return runType;
	}

	public static void main(String[] args) throws Exception {

	}
}
