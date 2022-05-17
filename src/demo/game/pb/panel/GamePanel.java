package demo.game.pb.panel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ”Œœ∑√Ê∞Â
 *
 */
public class GamePanel extends JPanel {
	FloorPanel floorPanel;
	WallPanel wallPanel;
	RobotPanel robotPanel;
	GoodsPanel goodsPanel;
	DestPanel destPanel;
	RtoDPanel rtoDPanel;
	GtoDPanel gtoDPanel;
	public CardLayout card;

	public GamePanel() {
		floorPanel = new FloorPanel();
		wallPanel = new WallPanel();
		robotPanel = new RobotPanel();
		goodsPanel = new GoodsPanel();
		destPanel = new DestPanel();
		rtoDPanel = new RtoDPanel();
		gtoDPanel = new GtoDPanel();
		card = new CardLayout();
		// p1.add(new JLabel("1"));
		// p2.add(new JLabel("2"));
		// setBackground(Color.white);
		setLayout(card);
		add("1", floorPanel);
		add("2", wallPanel);
		add("3", robotPanel);
		add("4", goodsPanel);
		add("5", destPanel);
		add("6", rtoDPanel);
		add("7", gtoDPanel);
	}
}
