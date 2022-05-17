/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
 *
 */

package org.sp.util;

import org.sp.core.SerialPortManager;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * 串口通讯辅助.
 * 
 */
public final class SerialPortUtil {
	
	private static SerialPort mSerialport = null;
	

	/**
	 * 打开串口.
	 * 
	 * @param commName String：串口名称
	 * @param baudrate int：波特率
	 * 
	 * @return
	 */
	public static boolean openSerialPort(String commName, int baudrate) {

		try {
			mSerialport = SerialPortManager.openPort(commName, baudrate);
		} catch (PortInUseException e) {
			e.printStackTrace();
			System.out.println("串口已被占用！");
			return false;
		}

		// 添加串口监听
		/*SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {

			@Override
			public void dataAvailable() {
				byte[] data = null;
				try {
					if (mSerialport == null) {
						System.out.println("串口对象为空，监听失败！");
					} else {
						// 读取串口数据
						data = SerialPortManager.readFromPort(mSerialport);

						// 以字符串的形式接收数据
						System.out.println(new String(data));

					}
				} catch (Exception e) {
					System.out.println(e.toString());
					// System.exit(0);
				}
			}
		});*/
		return true;
	}

	/**
	 * 关闭串口
	 * 
	 */
	public static boolean closeSerialPort() {
		if (mSerialport != null) {
			SerialPortManager.closePort(mSerialport);
		}
		return true;
	}

	/**
	 * 发送数据
	 * 
	 * @param data String: 数据
	 */
	public static void sendData(String data) {

		if (mSerialport == null) {
			System.out.println("请先打开串口！");
			return;
		}

		if ("".equals(data) || data == null) {
			System.out.println("请输入要发送的数据！");
			return;
		}

		SerialPortManager.sendToPort(mSerialport, data.getBytes());

	}

	/**
	 * 十进制转十六进制字符串
	 * 
	 * @param num int: 数字
	 * @return
	 */
	public static String toHexString(int num) {

		String str = Integer.toString(num, 16);

		return (str.length() < 2 ? "0" + str:str);

	}

	// test
	public static void main(String[] args) throws Exception {

	}
}
