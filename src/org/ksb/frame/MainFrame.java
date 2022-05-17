/*
 * ==============================================
 * ks �༭��
 * ==============================================
 *
 * Project Info: ks �༭��;
 *
 */

package org.ksb.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.ksb.panel.WebPanel;
import org.ksb.panel.StatusPanel;
import org.ksb.util.KsTool;
import org.ksb.util.KsbUtil;
import org.ksb.util.SysUtil;

/**
 * ������.
 */
public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static MainFrame mainFrame = null;
	public JButton btnVisit = null;
	public JButton btnFresh = null;
	private JLabel jlDebug = null;
	public JCheckBox ckDebug = null;
	
	
	private WebPanel webPanel = null;
	public StatusPanel statusPanel = null;
	public static List<String[]> dataList = null;
	
	private JButton btnNew = null;
	private JButton btnOpen = null;
	private JButton btnSave = null;
	private JButton btnTranslate = null;
	public JButton btnRun = null;
	private JButton btnExport = null;
	private JButton btnBt = null;
	private JScrollPane jsPane = null;
	public JTextArea txtConsole = new JTextArea(10, 42);
	
	private static String fPath = "";
	
	public MainFrame() {
		
		mainFrame = this;
		setTitle(SysUtil.TITLE);
		setLayout(null);
				
		// �õ���Ļ�Ĵ�С
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth(); // �õ���
		int height = (int)screensize.getHeight(); // �õ���
		
		setSize(width-50, height-50);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/org/ksb/res/logo.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setUndecorated(true);
		// getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		txtConsole.setText("");
		
		btnNew = new JButton("");
		btnNew.setIcon(KsbUtil.createImageIcon("/org/ksb/res/new.png"));
		btnNew.setToolTipText("�½�");
		// btnNew.setOpaque(false);
		// btnNew.setContentAreaFilled(false); 
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(mainFrame,"ȷ���½��ĵ���","��ʾ",JOptionPane.YES_NO_CANCEL_OPTION);
        if(r == JOptionPane.YES_OPTION){
        	String path = System.getProperty("user.dir");
        	webPanel.webBrowser.navigate(path + "/design/ksb.html");
        	fPath = "";
        }
			}
		});
		btnNew.setSize(25, 25);
		btnNew.setLocation(5, 5);
		add(btnNew);
		
		btnOpen = new JButton("");
		btnOpen.setIcon(KsbUtil.createImageIcon("/org/ksb/res/open.png"));
		btnOpen.setToolTipText("��");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 JFileChooser chooser = new JFileChooser(); // ����һ���ļ��Ի���
				 chooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // �����ļ��Ի���ĵ�ǰĿ¼
				 chooser.setFileFilter(new FileFilter() { // �����ļ��Ի�����ļ�������
				    @Override
				    public boolean accept(File file) { // �жϵ�ǰ�ļ��Ƿ��������������ֻ�����������ĲŻ���ʾ�ڶԻ�����
				        return file.isDirectory() || file.getName().toLowerCase().endsWith(".ksb");
				    }
				 
				    @Override
				    public String getDescription() { // ��ȡ������������
				        return "*.ksb(�ű��ļ�)";
				    }
				});
				int result = chooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) { // ������ȷ����ť
        	try {
            // ��ȡ���ļ��Ի�����ѡ����ļ�
            File file = chooser.getSelectedFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte b[] = new byte[(int) file.length()];  //�����ļ���С���ֽ�����
      			fileInputStream.read(b);          // ���ļ����ݴ洢��b����
      			String content = new String(b);
      			content = content.replaceAll("\n", "");
      			// System.out.println(content);
      			webPanel.webBrowser.executeJavascript("openKsb('"+content+"');");
      			fileInputStream.close();
      			fPath = file.getAbsolutePath();
      			txtConsole.setText("��" + file.getAbsolutePath() + "�ɹ���");
        	} catch(Exception ee) {
        		txtConsole.setText("IO�쳣��");
        		ee.printStackTrace();
        	}
        }
				 
			}
		});
		btnOpen.setSize(25, 25);
		btnOpen.setLocation(32, 5);
		add(btnOpen);
		
		btnSave = new JButton("");
		btnSave.setIcon(KsbUtil.createImageIcon("/org/ksb/res/save.png"));
		btnSave.setToolTipText("����");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bCode = (String)webPanel.webBrowser.executeJavascriptWithResult("return getBCode();");
				if (bCode == null || bCode.equals("")) {
					return;
				}
				
				if (!fPath.equals("")) {
					try {
						File file = new File(fPath);
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bCode.getBytes());
						fos.close();
						fPath = file.getAbsolutePath();
						txtConsole.setText("����" + file.getAbsolutePath() + "�ɹ���");
						return;
					} catch (IOException ee) {
						txtConsole.setText("IO�쳣��");
						ee.printStackTrace();
						return;
					}	
				}
				
				// �����ļ�ѡ���
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); 
				
				// ��׺��������
				FileNameExtensionFilter filter = new FileNameExtensionFilter("�ű��ļ�(*.ksb)", "ksb");
				chooser.setFileFilter(filter);
				
				// ����ķ�����������ֱ�����û����±��水ť�ҡ��ļ������ı���Ϊ�ա����û�����ȡ����ť��
				int option = chooser.showSaveDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){	// �����û�ѡ���˱���
					File file = chooser.getSelectedFile();
					
					String fname = chooser.getName(file);	// ���ļ���������л�ȡ�ļ���
					
					// �����û���д���ļ������������ƶ��ĺ�׺������ô���Ǹ������Ϻ�׺
					if(fname.indexOf(".ksb")==-1){
						file = new File(chooser.getCurrentDirectory(), fname + ".ksb");
					}
					
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bCode.getBytes());
						fos.close();
						fPath = file.getAbsolutePath();
						txtConsole.setText("����" + file.getAbsolutePath() + "�ɹ���");
					} catch (IOException ee) {
						txtConsole.setText("IO�쳣��");
						ee.printStackTrace();
					}	
				}
			}
		});
		btnSave.setSize(25, 25);
		btnSave.setLocation(59, 5);
		add(btnSave);
		
		btnTranslate = new JButton("");
		btnTranslate.setIcon(KsbUtil.createImageIcon("/org/ksb/res/translate.png"));
		btnTranslate.setToolTipText("����");
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtConsole.setText("��ʼ����...");
				webPanel.webBrowser.executeJavascript("translate();");
				txtConsole.append("\n����ɹ���");
			}
		});
		btnTranslate.setSize(25, 25);
		btnTranslate.setLocation(86, 5);
		add(btnTranslate);
		
		btnRun = new JButton("");
		btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/run.png"));
		btnRun.setToolTipText("����");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = (String)webPanel.webBrowser.executeJavascriptWithResult("return getCode();");
				if (code != null && !code.equals("")) {
					try {
						if (KsTool.isRunning()) {
							KsTool.stopRun();
							txtConsole.append("\n���������");
							btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/run.png"));
							btnRun.setToolTipText("����");
						} else {
							if (KsTool.getRunType() == KsTool.RUN_INSIDE) {
								KsTool.runKs(mainFrame, code);
							} else if (KsTool.getRunType() == KsTool.RUN_COMMAND) {
								KsTool.runKsFile(mainFrame, code);
							} else {
								throw new Exception("��֧�ֵ����з�ʽ");
							}
							btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/stop.png"));
							btnRun.setToolTipText("ֹͣ");
						}
					} catch(Exception ee) {
						txtConsole.setText(ee.getMessage());
					}
				}
			}
		});
		btnRun.setSize(25, 25);
		btnRun.setLocation(113, 5);
		add(btnRun);
		
		btnExport = new JButton("");
		btnExport.setIcon(KsbUtil.createImageIcon("/org/ksb/res/export.png"));
		btnExport.setToolTipText("����");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String code = (String)webPanel.webBrowser.executeJavascriptWithResult("return getCode();");
				if (code == null || code.equals("")) {
					return;
				}
				
				// �����ļ�ѡ���
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); 
				
				// ��׺��������
				FileNameExtensionFilter filter = new FileNameExtensionFilter("�ű��ļ�(*.ks)", "ks");
				chooser.setFileFilter(filter);
				
				// ����ķ�����������ֱ�����û����±��水ť�ҡ��ļ������ı���Ϊ�ա����û�����ȡ����ť��
				int option = chooser.showSaveDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){	// �����û�ѡ���˱���
					File file = chooser.getSelectedFile();
					
					String fname = chooser.getName(file);	// ���ļ���������л�ȡ�ļ���
					
					// �����û���д���ļ������������ƶ��ĺ�׺������ô���Ǹ������Ϻ�׺
					if(fname.indexOf(".ks")==-1){
						file = new File(chooser.getCurrentDirectory(), fname + ".ks");
					}
					
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(code.getBytes());
						fos.close();
						fPath = file.getAbsolutePath();
						txtConsole.setText("����" + file.getAbsolutePath() + "�ɹ���");
					} catch (IOException ee) {
						txtConsole.setText("IO�쳣��");
						ee.printStackTrace();
					}	
				}
			}
		});
		btnExport.setSize(25, 25);
		btnExport.setLocation(140, 5);
		add(btnExport);
		
		btnBt = new JButton("");
		btnBt.setIcon(KsbUtil.createImageIcon("/org/ksb/res/b_c.png"));
		btnBt.setToolTipText("COM");
		btnBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						new SpMainFrame().setVisible(true);
					}
				});
				
			}
		});
		btnBt.setSize(25, 25);
		btnBt.setLocation(167, 5);
		add(btnBt);
		
		ckDebug = new JCheckBox();
		ckDebug.setSize(30, 25);
		ckDebug.setLocation((width-100)/2+40, 5);
		//add(ckDebug);
		jlDebug = new JLabel("");
		jlDebug.setIcon(KsbUtil.createImageIcon("/org/ksb/res/debug.png"));
		jlDebug.setToolTipText("����");
		jlDebug.setSize(25, 25);
		jlDebug.setLocation((width-100)/2+70, 5);
		//add(jlDebug);
		
		// �����
		webPanel = new WebPanel();
    webPanel.setSize(width-60, height-205);
    webPanel.setLocation(5, 30);
    add(webPanel);
    
    txtConsole.setEditable(false);
    txtConsole.setLineWrap(true);
    jsPane = new JScrollPane(txtConsole);
		jsPane.setSize(width-60, 70);
		jsPane.setLocation(5, height-175);
		jsPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(jsPane);

		// ״̬��
		statusPanel = new StatusPanel();
		statusPanel.setSize(width-60, 25);
		statusPanel.setLocation(5, height-108);
		add(statusPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	

}
