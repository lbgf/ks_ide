/*
 * ==============================================
 * ks �༭��
 * ==============================================
 *
 * Project Info: ks �༭��;
 *
 */

package org.ksb.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ksb.util.SysUtil;

/**
 * ״̬���.
 */
@SuppressWarnings("serial")
public class StatusPanel extends JPanel {
	
	public JLabel versionInformation = null; // �汾��Ϣ
	public JLabel webSiteStatus = null; // ��ַ

	public StatusPanel() {

		setLayout(null);
		// setBackground(Color.white);
		versionInformation = new JLabel("�汾��Ϣ��" + SysUtil.VERSION); 
		versionInformation.setSize(200, 25);
		versionInformation.setLocation(10, 0);
		add(versionInformation);

		
		webSiteStatus = new JLabel("����֧�֣�");
		webSiteStatus.setSize(200, 25);
		webSiteStatus.setLocation(470, 0);
		add(webSiteStatus);
		
		LinkLabel linkLabel = new LinkLabel("gitee", SysUtil.GITEE);
		linkLabel.setSize(100, 20);
		linkLabel.setLocation(550, 3);
		add(linkLabel);

	}
	
}
