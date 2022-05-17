/*
 * ==============================================
 * ks �༭��
 * ==============================================
 *
 * Project Info: ks �༭��;
 *
 */

package org.sp.util;

import org.sp.core.SerialPortManager;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * ����ͨѶ����.
 * 
 */
public final class SerialPortUtil {
	
	private static SerialPort mSerialport = null;
	

	/**
	 * �򿪴���.
	 * 
	 * @param commName String����������
	 * @param baudrate int��������
	 * 
	 * @return
	 */
	public static boolean openSerialPort(String commName, int baudrate) {

		try {
			mSerialport = SerialPortManager.openPort(commName, baudrate);
		} catch (PortInUseException e) {
			e.printStackTrace();
			System.out.println("�����ѱ�ռ�ã�");
			return false;
		}

		// ��Ӵ��ڼ���
		/*SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {

			@Override
			public void dataAvailable() {
				byte[] data = null;
				try {
					if (mSerialport == null) {
						System.out.println("���ڶ���Ϊ�գ�����ʧ�ܣ�");
					} else {
						// ��ȡ��������
						data = SerialPortManager.readFromPort(mSerialport);

						// ���ַ�������ʽ��������
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
	 * �رմ���
	 * 
	 */
	public static boolean closeSerialPort() {
		if (mSerialport != null) {
			SerialPortManager.closePort(mSerialport);
		}
		return true;
	}

	/**
	 * ��������
	 * 
	 * @param data String: ����
	 */
	public static void sendData(String data) {

		if (mSerialport == null) {
			System.out.println("���ȴ򿪴��ڣ�");
			return;
		}

		if ("".equals(data) || data == null) {
			System.out.println("������Ҫ���͵����ݣ�");
			return;
		}

		SerialPortManager.sendToPort(mSerialport, data.getBytes());

	}

	/**
	 * ʮ����תʮ�������ַ���
	 * 
	 * @param num int: ����
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
