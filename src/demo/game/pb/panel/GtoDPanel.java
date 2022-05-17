package demo.game.pb.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JPanel;

/**
 * 物品移动目的地
 */
public class GtoDPanel extends JPanel {
	Image img;

	public GtoDPanel() {
		try {
			if (GtoDPanel.class.getClassLoader().getResource("demo/game/pb/res/em05.gif") == null) {
				URL url = new URL("file:\\" + System.getProperty("user.dir") + "\\demo\\pb\\em05.gif");
				img = Toolkit.getDefaultToolkit().getImage(url);
			} else {
				img = Toolkit.getDefaultToolkit()
					.getImage(GtoDPanel.class.getClassLoader().getResource("demo/game/pb/res/em05.gif"));
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
