/*
 * ==============================================
 * ks �༭��
 * ==============================================
 *
 * Project Info: ks �༭��;
 *
 */

package org.ksb.frame;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.ksb.util.KsbUtil;
import org.sp.core.SerialPortManager;
import org.comm.util.CommonUtil;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * ������
 * 
 */
public class SpMainFrame extends JFrame {

	private SpMainFrame spMainFrame = null;

	// ���������
	public final int WIDTH = 530;
	// �������߶�
	public final int HEIGHT = 390;

	// ������ʾ��
	private JTextArea mDataView = new JTextArea();
	private JScrollPane mScrollDataView = new JScrollPane(mDataView);

	// �����������
	private JPanel mSerialPortPanel = new JPanel();
	private JLabel mSerialPortLabel = new JLabel("����");
	private JLabel mBaudrateLabel = new JLabel("������");
	private JComboBox mCommChoice = new JComboBox();
	private JComboBox mBaudrateChoice = new JComboBox();
	private ButtonGroup mDataChoice = new ButtonGroup();
	private JRadioButton mDataASCIIChoice = new JRadioButton("ASCII", true);
	private JRadioButton mDataHexChoice = new JRadioButton("Hex");

	// �������
	private JPanel mOperatePanel = new JPanel();
	private JTextArea mDataInput = new JTextArea();
	private JButton mSerialPortOperate = new JButton("�򿪴���");
	private JButton mSendData = new JButton("��������");

	// �����б�
	private List<String> mCommList = null;
	// ���ڶ���
	private SerialPort mSerialport;

	public SpMainFrame() {
		spMainFrame = this;

		initView();
		initComponents();
		actionListener();
		initData();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				closeSerialPort(null);
			}
		});

		/*addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
			}

			public void windowLostFocus(WindowEvent e) {
				spMainFrame.toFront();
			}
		});*/
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		// �رճ���
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		// ��ֹ�������
		setResizable(false);
		
		setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/org/ksb/res/logo.png")));

		// ���ó��򴰿ھ�����ʾ
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setBounds(p.x - WIDTH / 2, p.y - HEIGHT / 2, WIDTH, HEIGHT);
		this.setLayout(null);

		setTitle("����ͨ��");
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initComponents() {
		// ������ʾ
		mDataView.setFocusable(false);
		mScrollDataView.setBounds(10, 10, 505, 200);
		add(mScrollDataView);

		// ��������
		mSerialPortPanel.setBorder(BorderFactory.createTitledBorder("��������"));
		mSerialPortPanel.setBounds(10, 220, 170, 130);
		mSerialPortPanel.setLayout(null);
		add(mSerialPortPanel);

		mSerialPortLabel.setForeground(Color.gray);
		mSerialPortLabel.setBounds(10, 25, 40, 20);
		mSerialPortPanel.add(mSerialPortLabel);

		mCommChoice.setFocusable(false);
		mCommChoice.setBounds(60, 25, 100, 20);
		mSerialPortPanel.add(mCommChoice);

		mBaudrateLabel.setForeground(Color.gray);
		mBaudrateLabel.setBounds(10, 60, 40, 20);
		mSerialPortPanel.add(mBaudrateLabel);

		mBaudrateChoice.setFocusable(false);
		mBaudrateChoice.setBounds(60, 60, 100, 20);
		mSerialPortPanel.add(mBaudrateChoice);

		mDataASCIIChoice.setBounds(20, 95, 55, 20);
		mDataHexChoice.setBounds(95, 95, 55, 20);
		mDataChoice.add(mDataASCIIChoice);
		mDataChoice.add(mDataHexChoice);
		mSerialPortPanel.add(mDataASCIIChoice);
		mSerialPortPanel.add(mDataHexChoice);

		// ����
		mOperatePanel.setBorder(BorderFactory.createTitledBorder("����"));
		mOperatePanel.setBounds(200, 220, 315, 130);
		mOperatePanel.setLayout(null);
		add(mOperatePanel);

		mDataInput.setBounds(25, 25, 265, 50);
		mDataInput.setLineWrap(true);
		mDataInput.setWrapStyleWord(true);
		mOperatePanel.add(mDataInput);

		mSerialPortOperate.setFocusable(false);
		mSerialPortOperate.setBounds(45, 95, 90, 20);
		mOperatePanel.add(mSerialPortOperate);

		mSendData.setFocusable(false);
		mSendData.setBounds(180, 95, 90, 20);
		mOperatePanel.add(mSendData);
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		mCommList = SerialPortManager.findPorts();
		// ����Ƿ��п��ô��ڣ��������ѡ����
		if (mCommList == null || mCommList.size() < 1) {
			KsbUtil.warningMessage("û����������Ч���ڣ�");
		} else {
			for (String s : mCommList) {
				mCommChoice.addItem(s);
			}
		}

		mBaudrateChoice.addItem("9600");
		mBaudrateChoice.addItem("19200");
		mBaudrateChoice.addItem("38400");
		mBaudrateChoice.addItem("57600");
		mBaudrateChoice.addItem("115200");
	}

	/**
	 * ��ť�����¼�
	 */
	private void actionListener() {
		// ����
		mCommChoice.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				mCommList = SerialPortManager.findPorts();
				// ����Ƿ��п��ô��ڣ��������ѡ����
				if (mCommList == null || mCommList.size() < 1) {
					KsbUtil.warningMessage("û����������Ч���ڣ�");
				} else {
					int index = mCommChoice.getSelectedIndex();
					mCommChoice.removeAllItems();
					for (String s : mCommList) {
						mCommChoice.addItem(s);
					}
					mCommChoice.setSelectedIndex(index);
				}
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// NO OP
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// NO OP
			}
		});

		// ��|�رմ���
		mSerialPortOperate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ("�򿪴���".equals(mSerialPortOperate.getText()) && mSerialport == null) {
					openSerialPort(e);
				} else {
					closeSerialPort(e);
				}
			}
		});

		// ��������
		mSendData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendData(e);
			}
		});
	}

	/**
	 * �򿪴���
	 * 
	 * @param evt ����¼�
	 */
	private void openSerialPort(java.awt.event.ActionEvent evt) {
		// ��ȡ��������
		String commName = (String) mCommChoice.getSelectedItem();
		// ��ȡ�����ʣ�Ĭ��Ϊ9600
		int baudrate = 9600;
		String bps = (String) mBaudrateChoice.getSelectedItem();
		baudrate = Integer.parseInt(bps);

		// ��鴮�������Ƿ��ȡ��ȷ
		if (commName == null || commName.equals("")) {
			KsbUtil.warningMessage("û����������Ч���ڣ�");
		} else {
			try {
				mSerialport = SerialPortManager.openPort(commName, baudrate);
				if (mSerialport != null) {
					mDataView.setText("�����Ѵ�" + "\r\n");
					mSerialPortOperate.setText("�رմ���");
				}
			} catch (PortInUseException e) {
				KsbUtil.warningMessage("�����ѱ�ռ�ã�");
			}
		}

		// ��Ӵ��ڼ���
		SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {

			@Override
			public void dataAvailable() {
				byte[] data = null;
				try {
					if (mSerialport == null) {
						KsbUtil.errorMessage("���ڶ���Ϊ�գ�����ʧ�ܣ�");
					} else {
						// ��ȡ��������
						data = SerialPortManager.readFromPort(mSerialport);

						// ���ַ�������ʽ��������
						if (mDataASCIIChoice.isSelected()) {
							mDataView.append(new String(data) + "\r\n");
						}

						// ��ʮ�����Ƶ���ʽ��������
						if (mDataHexChoice.isSelected()) {
							mDataView.append(CommonUtil.byteArrayToHexString(data) + "\r\n");
						}
					}
				} catch (Exception e) {
					KsbUtil.errorMessage(e.toString());
					// ������ȡ����ʱ��ʾ������Ϣ���˳�ϵͳ
					System.exit(0);
				}
			}
		});
	}

	/**
	 * �رմ���
	 * 
	 * @param evt ����¼�
	 */
	private void closeSerialPort(java.awt.event.ActionEvent evt) {
		SerialPortManager.closePort(mSerialport);
		mDataView.setText("�����ѹر�" + "\r\n");
		mSerialPortOperate.setText("�򿪴���");
		mSerialport = null;
	}

	/**
	 * ��������
	 * 
	 * @param evt ����¼�
	 */
	private void sendData(java.awt.event.ActionEvent evt) {
		// ����������
		String data = mDataInput.getText().toString();

		if (mSerialport == null) {
			KsbUtil.warningMessage("���ȴ򿪴��ڣ�");
			return;
		}

		if ("".equals(data) || data == null) {
			KsbUtil.warningMessage("������Ҫ���͵����ݣ�");
			return;
		}

		// ���ַ�������ʽ��������
		if (mDataASCIIChoice.isSelected()) {
			SerialPortManager.sendToPort(mSerialport, data.getBytes());
		}

		// ��ʮ�����Ƶ���ʽ��������
		if (mDataHexChoice.isSelected()) {
			SerialPortManager.sendToPort(mSerialport, CommonUtil.hexStr2Byte(data));
		}
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SpMainFrame().setVisible(true);
			}
		});
	}
}