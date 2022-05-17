/*
 * ==============================================
 * ks 编辑器
 * ==============================================
 *
 * Project Info: ks 编辑器;
 *
 */

package org.ksb.panel;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * 超连接.
 */
@SuppressWarnings("serial")
public class LinkLabel extends JLabel {
	private String text, url;
	private boolean isSupported;

	public LinkLabel(String text, String url) {
		this.text = text;
		this.url = url;
		try {
			this.isSupported = Desktop.isDesktopSupported()
					&& Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
		} catch (Exception e) {
			this.isSupported = false;
		}
		setText(false);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setText(isSupported);
				if (isSupported)
					setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				setText(false);
			}

			public void mouseClicked(MouseEvent e) {
				try {
					// System.out.println(isSupported);
					Desktop.getDesktop().browse(new java.net.URI(LinkLabel.this.url));
				} catch (Exception ex) {
					// ex.printStackTrace();
					try {
						Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + LinkLabel.this.url);
						// System.Diagnostics.Process.Start(new ProcessStartInfo("Iexplore.exe", "http://www.163.com"));
					} catch (Exception exx) {
						exx.printStackTrace();
					}
				}
			}
		});
	}

	private void setText(boolean b) {
		if (!b)
			setText("<html><font color=blue><u>" + text);
		else
			setText("<html><font color=red><u>" + text);
	}

	public static void main(String[] args) {
		/*JFrame jf = new JFrame("一个超链接实现的例子");
		JPanel jp = new JPanel();
		jp.add(new LinkLabel("xxx", "http://www.baidu.com"));
		jf.setContentPane(jp);
		jf.pack();
		jf.setVisible(true);*/
	}
}
