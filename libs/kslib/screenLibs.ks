
函数 连接串口(名称,频率) {
	SerialPortUtil.openSerialPort(名称, 频率);
}

函数 发送串口数据(字符口) {
	SerialPortUtil.sendData(字符口 + "@");
}

函数 断开串口() {
	SerialPortUtil.closeSerialPort();
}

函数 取得当前时间字符串() {
	变量 日期 = 创建 Date();
	变量 时间字符串 = CommonUtil.getTimeStr(日期);
	时间字符串;
}

函数 清屏() {
	发送串口数据("c_");
	等待(5);
}

函数 结束并显示() {
	发送串口数据("f_");
	等待(5);
}

函数 输出点(x,y,颜色) {
	发送串口数据("p_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + 颜色);
	等待(10);
}

函数 输出文本(x,y,颜色,字符串) {
	发送串口数据("s_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + 颜色 + 字符串);
	等待(10);
}

函数 输出垂直线(x,y,颜色,长度) {
	发送串口数据("v_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + "" + SerialPortUtil.toHexString(长度) + 颜色);
	等待(10);
}

函数 输出水平线(x,y,颜色,长度) {
	发送串口数据("h_" + SerialPortUtil.toHexString(x) + "" + SerialPortUtil.toHexString(y) + ""+ SerialPortUtil.toHexString(长度) + 颜色);
	等待(10);
}
