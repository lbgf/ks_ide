/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
 *
 */

package org.ksb.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ksb.util.SysUtil;

/**
 * 状态面板.
 */
@SuppressWarnings("serial")
public class StatusPanel extends JPanel {
	
	public JLabel versionInformation = null; // 版本信息
	public JLabel webSiteStatus = null; // 网址

	public StatusPanel() {

		setLayout(null);
		// setBackground(Color.white);
		versionInformation = new JLabel("版本信息：" + SysUtil.VERSION); 
		versionInformation.setSize(200, 25);
		versionInformation.setLocation(10, 0);
		add(versionInformation);

		
		webSiteStatus = new JLabel("技术支持：");
		webSiteStatus.setSize(200, 25);
		webSiteStatus.setLocation(470, 0);
		add(webSiteStatus);
		
		LinkLabel linkLabel = new LinkLabel("gitee", SysUtil.GITEE);
		linkLabel.setSize(100, 20);
		linkLabel.setLocation(550, 3);
		add(linkLabel);

	}
	
}
