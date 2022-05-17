/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
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
 * 主窗体.
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
				
		// 得到屏幕的大小
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth(); // 得到宽
		int height = (int)screensize.getHeight(); // 得到高
		
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
		btnNew.setToolTipText("新建");
		// btnNew.setOpaque(false);
		// btnNew.setContentAreaFilled(false); 
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(mainFrame,"确认新建文档？","提示",JOptionPane.YES_NO_CANCEL_OPTION);
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
		btnOpen.setToolTipText("打开");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 JFileChooser chooser = new JFileChooser(); // 创建一个文件对话框
				 chooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // 设置文件对话框的当前目录
				 chooser.setFileFilter(new FileFilter() { // 设置文件对话框的文件过滤器
				    @Override
				    public boolean accept(File file) { // 判断当前文件是否满足过滤条件，只有满足条件的才会显示在对话框中
				        return file.isDirectory() || file.getName().toLowerCase().endsWith(".ksb");
				    }
				 
				    @Override
				    public String getDescription() { // 获取过滤器的描述
				        return "*.ksb(脚本文件)";
				    }
				});
				int result = chooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) { // 单击了确定按钮
        	try {
            // 获取在文件对话框中选择的文件
            File file = chooser.getSelectedFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte b[] = new byte[(int) file.length()];  //定义文件大小的字节数据
      			fileInputStream.read(b);          // 将文件数据存储在b数组
      			String content = new String(b);
      			content = content.replaceAll("\n", "");
      			// System.out.println(content);
      			webPanel.webBrowser.executeJavascript("openKsb('"+content+"');");
      			fileInputStream.close();
      			fPath = file.getAbsolutePath();
      			txtConsole.setText("打开" + file.getAbsolutePath() + "成功！");
        	} catch(Exception ee) {
        		txtConsole.setText("IO异常！");
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
		btnSave.setToolTipText("保存");
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
						txtConsole.setText("保存" + file.getAbsolutePath() + "成功！");
						return;
					} catch (IOException ee) {
						txtConsole.setText("IO异常！");
						ee.printStackTrace();
						return;
					}	
				}
				
				// 弹出文件选择框
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); 
				
				// 后缀名过滤器
				FileNameExtensionFilter filter = new FileNameExtensionFilter("脚本文件(*.ksb)", "ksb");
				chooser.setFileFilter(filter);
				
				// 下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
				int option = chooser.showSaveDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){	// 假如用户选择了保存
					File file = chooser.getSelectedFile();
					
					String fname = chooser.getName(file);	// 从文件名输入框中获取文件名
					
					// 假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
					if(fname.indexOf(".ksb")==-1){
						file = new File(chooser.getCurrentDirectory(), fname + ".ksb");
					}
					
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bCode.getBytes());
						fos.close();
						fPath = file.getAbsolutePath();
						txtConsole.setText("保存" + file.getAbsolutePath() + "成功！");
					} catch (IOException ee) {
						txtConsole.setText("IO异常！");
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
		btnTranslate.setToolTipText("编译");
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtConsole.setText("开始编译...");
				webPanel.webBrowser.executeJavascript("translate();");
				txtConsole.append("\n编译成功！");
			}
		});
		btnTranslate.setSize(25, 25);
		btnTranslate.setLocation(86, 5);
		add(btnTranslate);
		
		btnRun = new JButton("");
		btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/run.png"));
		btnRun.setToolTipText("运行");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = (String)webPanel.webBrowser.executeJavascriptWithResult("return getCode();");
				if (code != null && !code.equals("")) {
					try {
						if (KsTool.isRunning()) {
							KsTool.stopRun();
							txtConsole.append("\n程序结束！");
							btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/run.png"));
							btnRun.setToolTipText("运行");
						} else {
							if (KsTool.getRunType() == KsTool.RUN_INSIDE) {
								KsTool.runKs(mainFrame, code);
							} else if (KsTool.getRunType() == KsTool.RUN_COMMAND) {
								KsTool.runKsFile(mainFrame, code);
							} else {
								throw new Exception("不支持的运行方式");
							}
							btnRun.setIcon(KsbUtil.createImageIcon("/org/ksb/res/stop.png"));
							btnRun.setToolTipText("停止");
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
		btnExport.setToolTipText("导出");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String code = (String)webPanel.webBrowser.executeJavascriptWithResult("return getCode();");
				if (code == null || code.equals("")) {
					return;
				}
				
				// 弹出文件选择框
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); 
				
				// 后缀名过滤器
				FileNameExtensionFilter filter = new FileNameExtensionFilter("脚本文件(*.ks)", "ks");
				chooser.setFileFilter(filter);
				
				// 下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
				int option = chooser.showSaveDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){	// 假如用户选择了保存
					File file = chooser.getSelectedFile();
					
					String fname = chooser.getName(file);	// 从文件名输入框中获取文件名
					
					// 假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
					if(fname.indexOf(".ks")==-1){
						file = new File(chooser.getCurrentDirectory(), fname + ".ks");
					}
					
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(code.getBytes());
						fos.close();
						fPath = file.getAbsolutePath();
						txtConsole.setText("导出" + file.getAbsolutePath() + "成功！");
					} catch (IOException ee) {
						txtConsole.setText("IO异常！");
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
		jlDebug.setToolTipText("调试");
		jlDebug.setSize(25, 25);
		jlDebug.setLocation((width-100)/2+70, 5);
		//add(jlDebug);
		
		// 主面板
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

		// 状态栏
		statusPanel = new StatusPanel();
		statusPanel.setSize(width-60, 25);
		statusPanel.setLocation(5, height-108);
		add(statusPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	

}
