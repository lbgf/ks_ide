package demo.game.pb.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JPanel;

/**
 * ŒÔ∆∑
 *
 */
public class GoodsPanel extends JPanel {
	Image img;

	public GoodsPanel() {
		try {
			if (GoodsPanel.class.getClassLoader().getResource("demo/game/pb/res/em04.gif") == null) {
			URL url = new URL("file:\\" + System.getProperty("user.dir") + "\\demo\\pb\\em04.gif");
				img = Toolkit.getDefaultToolkit().getImage(url);
			} else {
				img = Toolkit.getDefaultToolkit()
					.getImage(GoodsPanel.class.getClassLoader().getResource("demo/game/pb/res/em04.gif"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 20, 20);
		g.drawImage(img, 0, 0, 15, 15, this);
	}
}
