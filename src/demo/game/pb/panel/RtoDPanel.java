package demo.game.pb.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JPanel;

/**
 * 机器人移动目的地
 */
public class RtoDPanel extends JPanel {
	Image img;

	public RtoDPanel() {
		try {
			if (GtoDPanel.class.getClassLoader().getResource("demo/game/pb/res/em03.gif") == null) {
				URL url = new URL("file:\\" + System.getProperty("user.dir") + "\\demo\\pb\\em03.gif");
				img = Toolkit.getDefaultToolkit().getImage(url);
			} else {
				img = Toolkit.getDefaultToolkit()
						.getImage(RtoDPanel.class.getClassLoader().getResource("demo/game/pb/res/em03.gif"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, 20, 20);
		g.drawImage(img, 0, 0, 15, 15, this);
	}
}
