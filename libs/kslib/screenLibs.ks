
���� ���Ӵ���(����,Ƶ��) {
	SerialPortUtil.openSerialPort(����, Ƶ��);
}

���� ���ʹ�������(�ַ���) {
	SerialPortUtil.sendData(�ַ��� + "@");
}

���� �Ͽ�����() {
	SerialPortUtil.closeSerialPort();
}

���� ȡ�õ�ǰʱ���ַ���() {
	���� ���� = ���� Date();
	���� ʱ���ַ��� = CommonUtil.getTimeStr(����);
	ʱ���ַ���;
}

���� ����() {
	���ʹ�������("c_");
	�ȴ�(5);
}

���� ��������ʾ() {
	���ʹ�������("f_");
	�ȴ�(5);
}

���� �����(x,y,��ɫ) {
	���ʹ�������("p_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + ��ɫ);
	�ȴ�(10);
}

���� ����ı�(x,y,��ɫ,�ַ���) {
	���ʹ�������("s_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + ��ɫ + �ַ���);
	�ȴ�(10);
}

���� �����ֱ��(x,y,��ɫ,����) {
	���ʹ�������("v_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + "" + SerialPortUtil.toHexString(����) + ��ɫ);
	�ȴ�(10);
}

���� ���ˮƽ��(x,y,��ɫ,����) {
	���ʹ�������("h_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + ""+ SerialPortUtil.toHexString(����) + ��ɫ);
	�ȴ�(10);
}
