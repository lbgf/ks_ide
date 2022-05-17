package demo.game.pb.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JPanel;

/**
 * ǽ
 *
 */
public class WallPanel extends JPanel {
	Image img;

	public WallPanel() {
		try {
			if (WallPanel.class.getClassLoader().getResource("demo/game/pb/res/wall.gif") == null) {
				URL url = new URL("file:\\" + System.getProperty("user.dir") + "\\demo\\pb\\wall.gif");
				img = Toolkit.getDefaultToolkit().getImage(url);
			} else {
				img = Toolkit.getDefaultToolkit()
						.getImage(Thread.currentThread().getContextClassLoader().getResource("demo/game/pb/res/wall.gif"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, 20, 20, this);
	}
}
