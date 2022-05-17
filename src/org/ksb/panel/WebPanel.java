/*
 * ==============================================
 * ks ±à¼­Æ÷
 * ==============================================
 *
 * Project Info: ks ±à¼­Æ÷;
 *
 */

package org.ksb.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

/**
 * ä¯ÀÀÆ÷Ö÷Ãæ°å.
 *
 */
@SuppressWarnings("serial")
public class WebPanel extends JPanel{
	
	public JWebBrowser webBrowser = null;

	public WebPanel() {
		this.setLayout(new BorderLayout());
		String path = System.getProperty("user.dir");
		
    JPanel webBrowserPanel = new JPanel(new BorderLayout());
    webBrowser = new JWebBrowser();
    webBrowser.setJavascriptEnabled(true);
    webBrowser.navigate(path + "/design/ksb.html");
   	webBrowser.setMenuBarVisible(false);
    webBrowser.setStatusBarVisible(false);
    webBrowser.setButtonBarVisible(false);
    webBrowser.setBarsVisible(false);
    //webBrowser.setAutoscrolls(false);
    
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
    this.add(webBrowserPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		new WebPanel();
	}
}
