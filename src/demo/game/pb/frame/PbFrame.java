package demo.game.pb.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import demo.game.pb.panel.GamePanel;

/**
 * 游戏主窗体
 *
 */
public class PbFrame extends JFrame implements KeyListener, ActionListener {
	JMenuItem jmExit;
	JMenuItem jmCurrStart;
	JMenuItem jmFirstStart;
	JMenuItem jmNext;
	static int level = 1;// 等级
	GamePanel gp[];// 游戏面板
	int wallPos[];// 墙的位置
	int robotPosRes;// 机器人原位置
	int robotPos;// 机器人位置
	int destPos[];// 目的地位置
	int wtCount = 0;// 物品或目的地数目
	boolean isWin = false;// 是否赢了
	String[] controls = new String[]{"W","S","A","D"};
	List<Integer> goodsList = new ArrayList<Integer>();
	List<Integer> destList = new ArrayList<Integer>();
	List<Integer> wallList = new ArrayList<Integer>();
	
	public PbFrame pbFrame = null;

	public PbFrame() {
		
		pbFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); 
		setSize(410, 410);
		setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/demo/game/pb/res/logo.png")));
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation( (int) (width - this.getWidth()) / 2,
                  (int) (height - this.getHeight()) / 2);
	
		gp = new GamePanel[400];
		wallPos = new int[400];
		JPanel jpTb = new JPanel();
		JPanel jpGame = new JPanel();// 游戏总面板
		jpGame.setLayout(new GridLayout(20, 20));

		for (int i = 0; i < 400; i++)
			jpGame.add(gp[i] = new GamePanel());

		jpTb.setLayout(new GridLayout(1, 0));

		getContentPane().add(jpTb, BorderLayout.NORTH);
		getContentPane().add(jpGame, BorderLayout.CENTER);

		JMenu jm = new JMenu("操作");
		// JMenu jmlevel = new JMenu("关数");
		jmExit = new JMenuItem("退出");
		jmCurrStart = new JMenuItem("重新开始");
		//jmFirstStart = new JMenuItem("第一关开始");
		//jmNext = new JMenuItem("下一关");
		jmExit.addActionListener(this);
		jmCurrStart.addActionListener(this);
		//jmFirstStart.addActionListener(this);
		//jmNext.addActionListener(this);
		jm.add(jmExit);
		jm.add(jmCurrStart);
		//jmlevel.add(jmCurrStart);
		//jmlevel.add(jmFirstStart);
		//jmlevel.add(jmNext);
		JMenuBar jmb = new JMenuBar();
		jmb.add(jm);
		//jmb.add(jmlevel);
		setJMenuBar(jmb);

		addKeyListener(this);
		// setVisible(true);

		// selectLevel(level);
		requestFocus();
	}

	public void keyTyped(KeyEvent e) {
		//
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == controls[3].toLowerCase().charAt(0)) {
			if (robotPos + 1 > 399 || ((robotPos+1)%20) == 0) {
				return;
			}
			if(wallPos[robotPos + 1] == 2 && ((robotPos+2)%20) == 0) {
				return;
			}
			
			if (wallPos[robotPos + 1] == 0 || wallPos[robotPos + 1] == 2 || wallPos[robotPos + 1] == 3
					|| wallPos[robotPos + 1] == 4) {
				if (wallPos[robotPos + 1] == 0)// 没有障碍物时
				{
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}
					robotPos = robotPos + 1;// 机器人的位置改变
					gp[robotPos].card.show(gp[robotPos], "3");
				} else if (wallPos[robotPos + 1] == 2 || wallPos[robotPos + 1] == 4)// 前面是物品时
				{
					if (wallPos[robotPos + 2] == 0) {
						if (wallPos[robotPos] == 3)// 如果是目的地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos + 2].card.show(gp[robotPos + 2], "4");
						wallPos[robotPos + 2] = 2;// 没物品的位置变为有物品

						if (wallPos[robotPos + 1] == 2)// 物品原来的地
						{
							wallPos[robotPos + 1] = 0;
							robotPos = robotPos + 1;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos + 1] == 4) {
							wallPos[robotPos + 1] = 3;
							robotPos = robotPos + 1;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					} else if (wallPos[robotPos + 2] == 3) {
						if (wallPos[robotPos] == 3)// 如果是目的地,机器人原地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos + 2].card.show(gp[robotPos + 2], "7");
						wallPos[robotPos + 2] = 4;// end

						if (wallPos[robotPos + 1] == 2)// 物品原地
						{
							wallPos[robotPos + 1] = 0;
							robotPos = robotPos + 1;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos + 1] == 4) {
							wallPos[robotPos + 1] = 3;
							robotPos = robotPos + 1;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					}
				} else if (wallPos[robotPos + 1] == 3)// 前面是目的地时
				{
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}
					robotPos = robotPos + 1;// 机器人的位置改变
					gp[robotPos].card.show(gp[robotPos], "6");
				}
			}
		} else if (e.getKeyChar() == controls[2].toLowerCase().charAt(0)) {
			if (robotPos - 1 < 0 || ((robotPos-1)%20) == -1 || ((robotPos-1)%20) == 19) {
				return;
			}
			if(wallPos[robotPos - 1] == 2 && (robotPos - 2 < 0 || ((robotPos-12)%20) == -1 || ((robotPos-2)%20) == 19)) {
				return;
			}
			
			if (wallPos[robotPos - 1] == 0 || wallPos[robotPos - 1] == 2 || wallPos[robotPos - 1] == 3
					|| wallPos[robotPos - 1] == 4) {
				if (wallPos[robotPos - 1] == 0) {
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}
					robotPos = robotPos - 1;
					gp[robotPos].card.show(gp[robotPos], "3");
				} else if (wallPos[robotPos - 1] == 2 || wallPos[robotPos - 1] == 4) {
					if (wallPos[robotPos - 2] == 0) {
						if (wallPos[robotPos] == 3)// 如果是目的地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos - 2].card.show(gp[robotPos - 2], "4");
						wallPos[robotPos - 2] = 2;// 没物品的位置变为有物品

						if (wallPos[robotPos - 1] == 2)// 物品原来的地
						{
							wallPos[robotPos - 1] = 0;
							robotPos = robotPos - 1;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos - 1] == 4) {
							wallPos[robotPos - 1] = 3;
							robotPos = robotPos - 1;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					} else if (wallPos[robotPos - 2] == 3) {
						if (wallPos[robotPos] == 3)// 如果是目的地,机器人原地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos - 2].card.show(gp[robotPos - 2], "7");
						wallPos[robotPos - 2] = 4;// end

						if (wallPos[robotPos - 1] == 2)// 物品原地
						{
							wallPos[robotPos - 1] = 0;
							robotPos = robotPos - 1;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos - 1] == 4) {
							wallPos[robotPos - 1] = 3;
							robotPos = robotPos - 1;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					}
				} else if (wallPos[robotPos - 1] == 3)// 前面是目的地时
				{
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}
					robotPos = robotPos - 1;// 机器人的位置改变
					gp[robotPos].card.show(gp[robotPos], "6");
				}
			}
		} else if (e.getKeyChar() == controls[0].toLowerCase().charAt(0)) {
			
			if (robotPos - 20 < 0) {
				return;
			}
			if(wallPos[robotPos - 20] == 2 && robotPos - 40 < 0) {
				return;
			}
			
			
			if (wallPos[robotPos - 20] == 0 || wallPos[robotPos - 20] == 2 || wallPos[robotPos - 20] == 3
					|| wallPos[robotPos - 20] == 4) {
				if (wallPos[robotPos - 20] == 0) {
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}

					robotPos = robotPos - 20;
					gp[robotPos].card.show(gp[robotPos], "3");
				} else if (wallPos[robotPos - 20] == 2 || wallPos[robotPos - 20] == 4) {
					if (wallPos[robotPos - 40] == 0) {
						if (wallPos[robotPos] == 3)// 如果是目的地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos - 40].card.show(gp[robotPos - 40], "4");
						wallPos[robotPos - 40] = 2;// 没物品的位置变为有物品

						if (wallPos[robotPos - 20] == 2)// 物品原来的地
						{
							wallPos[robotPos - 20] = 0;
							robotPos = robotPos - 20;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos - 20] == 4) {
							wallPos[robotPos - 20] = 3;
							robotPos = robotPos - 20;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					} else if (wallPos[robotPos - 40] == 3) {
						if (wallPos[robotPos] == 3)// 如果是目的地,机器人原地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos - 40].card.show(gp[robotPos - 40], "7");
						wallPos[robotPos - 40] = 4;// end

						if (wallPos[robotPos - 20] == 2)// 物品原地
						{
							wallPos[robotPos - 20] = 0;
							robotPos = robotPos - 20;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos - 20] == 4) {
							wallPos[robotPos - 20] = 3;
							robotPos = robotPos - 20;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					}
				} else if (wallPos[robotPos - 20] == 3)// 前面是目的地时
				{
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}
					robotPos = robotPos - 20;// 机器人的位置改变
					gp[robotPos].card.show(gp[robotPos], "6");
				}
			}
		} else if (e.getKeyChar() == controls[1].toLowerCase().charAt(0)) {
			if (robotPos + 20 > 399) {
				return;
			}
			if(wallPos[robotPos + 20] == 2 && robotPos + 40 > 399) {
				return;
			}
			
			if (wallPos[robotPos + 20] == 0 || wallPos[robotPos + 20] == 2 || wallPos[robotPos + 20] == 3
					|| wallPos[robotPos + 20] == 4) {
				if (wallPos[robotPos + 20] == 0) {
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}

					robotPos = robotPos + 20;
					gp[robotPos].card.show(gp[robotPos], "3");
				} else if (wallPos[robotPos + 20] == 2 || wallPos[robotPos + 20] == 4) {
					if (wallPos[robotPos + 40] == 0) {
						if (wallPos[robotPos] == 3)// 如果是目的地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos + 40].card.show(gp[robotPos + 40], "4");
						wallPos[robotPos + 40] = 2;// 没物品的位置变为有物品

						if (wallPos[robotPos + 20] == 2)// 物品原来的地
						{
							wallPos[robotPos + 20] = 0;
							robotPos = robotPos + 20;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos + 20] == 4) {
							wallPos[robotPos + 20] = 3;
							robotPos = robotPos + 20;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					} else if (wallPos[robotPos + 40] == 3) {
						if (wallPos[robotPos] == 3)// 如果是目的地,机器人原地
						{
							gp[robotPos].card.show(gp[robotPos], "5");
						} else {
							gp[robotPos].card.show(gp[robotPos], "1");
						}

						gp[robotPos + 40].card.show(gp[robotPos + 40], "7");
						wallPos[robotPos + 40] = 4;// end

						if (wallPos[robotPos + 20] == 2)// 物品原地
						{
							wallPos[robotPos + 20] = 0;
							robotPos = robotPos + 20;
							gp[robotPos].card.show(gp[robotPos], "3");
						} else if (wallPos[robotPos + 20] == 4) {
							wallPos[robotPos + 20] = 3;
							robotPos = robotPos + 20;
							gp[robotPos].card.show(gp[robotPos], "6");
						}
					}
				} else if (wallPos[robotPos + 20] == 3)// 前面是目的地时
				{
					if (wallPos[robotPos] == 3)// 如果是目的地
					{
						gp[robotPos].card.show(gp[robotPos], "5");
					} else {
						gp[robotPos].card.show(gp[robotPos], "1");
					}
					robotPos = robotPos + 20;// 机器人的位置改变
					gp[robotPos].card.show(gp[robotPos], "6");
				}
			}
		}

		isWin = true;
		for (int i = 0; i < wtCount; i++) {
			if (wallPos[destPos[i]] != 4) {
				isWin = false;
				break;
			}
		}
		if (isWin == true) {
			/*JDialog jd = new JDialog(new JFrame(), "提示", true);
			jd.getContentPane().add(new Label("you win!!"));
			jd.setSize(100, 100);
			jd.setVisible(true);
			isWin = false;
			level = level + 1;
			if (level > 2)
				level = 1;
			selectlevel(level);*/
			JOptionPane.showMessageDialog(pbFrame, "你赢了！！！", "提示",JOptionPane.INFORMATION_MESSAGE); 
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
		//
	}

	public void actionPerformed(ActionEvent e) {
		String strtemp = ((JMenuItem) e.getSource()).getText();

		if (strtemp == "退出")
			System.exit(0);
		else if (strtemp == "重新开始") {
			initMap();
			// selectLevel(level);
		} /*else if (strtemp == "第一关开始") {
			level = 1;
			selectLevel(level);
		} else if (strtemp == "下一关") {
			level = level + 1;
			if (level > 2)
				level = 1;
			selectLevel(level);
		}*/
	}

	// 选关
	/*void selectLevel(int levelTemp) {

		switch (levelTemp) {
		case 1:
			int hpencil1[][] = { { 24, 36 }, { 285, 295 }, { 107, 108 }, { 127, 128 }, { 112, 113 }, { 132, 133 },
					{ 208, 212 } };// 画水平墙
			int vpencil1[][] = { { 44, 284 }, { 56, 296 } };// 画垂直墙
			int goodsPostemp1[] = { 66, 130, 207 };// 物品位置
			int destPostemp1[] = { 69, 71, 215 };// 目的地位置
			destPos = new int[3];
			wtCount = 3;
			//buildMap(7, hpencil1, 2, vpencil1);
			//setMap(45, wtCount, goodsPostemp1, destPostemp1);
			break;
		case 2:
			int hpencil2[][] = { { 24, 32 }, { 202, 203 }, { 193, 195 }, { 65, 67 }, { 263, 265 }, { 252, 254 },
					{ 303, 314 } };// 画水平墙
			int vpencil2[][] = { { 44, 204 }, { 222, 302 }, { 52, 192 }, { 215, 315 } };// 画垂直墙
			int goodsPostemp2[] = { 106, 130, 207, 206 };// 物品位置
			int destPostemp2[] = { 214, 283, 293, 294 };// 目的地位置
			destPos = new int[4];
			wtCount = 4;
			//buildMap(7, hpencil2, 4, vpencil2);
			//setMap(45, wtCount, goodsPostemp2, destPostemp2);
			break;
		}
	}*/

	// 建地图
	void buildMap() {
		for (int i = 0; i < 400; i++)
			wallPos[i] = 0;
		for (int i = 0; i < 400; i++)
			gp[i].card.show(gp[i], "1");
	}

	// 设置各事物位置
	// wtCounttemp:物品或目的地的数目
	void setMap(int wtCounttemp, int wallPostegp[], int goodsPostegp[], int destPostegp[]) {
		
		for (int i = 0; i < wallPostegp.length; i++) {
			wallPos[wallPostegp[i]] = 1;// 墙位置
			gp[wallPostegp[i]].card.show(gp[wallPostegp[i]], "2");
		}
		
		if (wallPos[robotPos] != 0) {
			JOptionPane.showMessageDialog(pbFrame, "机器人设置位置时冲突", "提示",JOptionPane.ERROR_MESSAGE); 
			System.exit(0);
		}
		gp[robotPos].card.show(gp[robotPos], "3"); // 机器人位置

		for (int i = 0; i < wtCounttemp; i++) {
			if (wallPos[goodsPostegp[i]] != 0) {
				JOptionPane.showMessageDialog(pbFrame, "物品设置位置时冲突", "提示",JOptionPane.ERROR_MESSAGE); 
				System.exit(0);
			}
			wallPos[goodsPostegp[i]] = 2;// 物品位置
			gp[goodsPostegp[i]].card.show(gp[goodsPostegp[i]], "4");
		}

		for (int i = 0; i < wtCounttemp; i++) {
			if (wallPos[destPos[i]] != 0) {
				JOptionPane.showMessageDialog(pbFrame, "目标设置位置时冲突", "提示",JOptionPane.ERROR_MESSAGE); 
				System.exit(0);
			}
			destPos[i] = destPostegp[i];// 目的地位置
			wallPos[destPos[i]] = 3;
			gp[destPos[i]].card.show(gp[destPos[i]], "5");
		}
	}
	
	/**
	 * 
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 */
	public void setControl(String up, String down, String left, String right) {
		controls[0] = up;
		controls[1] = down;
		controls[2] = left;
		controls[3] = right;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setRobotPos(int x, int y) {
		robotPosRes = getPos(x ,y);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void addGoodsPos(int x, int y) {
		goodsList.add(getPos(x ,y));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void addDestPos(int x, int y) {
		destList.add(getPos(x ,y));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void addWallPos(int x, int y) {
		wallList.add(getPos(x ,y));
	}
	
	/**
	 * 
	 * @param y
	 * @param x1
	 * @param x2
	 */
	public void addWallPosV(int y, int x1, int x2) {
		if (x1 >= x2) {
			JOptionPane.showMessageDialog(pbFrame, "x2必须大于x1", "提示",JOptionPane.ERROR_MESSAGE); 
			System.exit(0);
		}
		int cnt = x2 - x1;
		for (int i = 0; i <= cnt; i++) {
			wallList.add(getPos(x1 + i ,y));
		}
		
	}
	
	/**
	 * 
	 * @param x
	 * @param y1
	 * @param y2
	 */
	public void addWallPosH(int x, int y1, int y2) {
		if (y1 >= y2) {
			JOptionPane.showMessageDialog(pbFrame, "y2必须大于y1", "提示",JOptionPane.ERROR_MESSAGE); 
			System.exit(0);
		}
		int cnt = y2 - y1;
		for (int i = 0; i <= cnt; i++) {
			wallList.add(getPos(x ,y1 + i));
		}
	}
	
	/**
	 * 
	 */
	public void initMap() {
		
		robotPos = robotPosRes;
		
		// 墙的位置
		int wallPostemp1[] = wallList.stream().mapToInt(Integer::intValue).toArray(); 
		// 物品位置
		int goodsPostemp1[] = goodsList.stream().mapToInt(Integer::intValue).toArray(); 
		// 目的地位置
		int destPostemp1[] = destList.stream().mapToInt(Integer::intValue).toArray();
		
		if (goodsPostemp1.length != destPostemp1.length) {
			JOptionPane.showMessageDialog(pbFrame, "目标和物品的数量对不上", "提示",JOptionPane.ERROR_MESSAGE); 
			System.exit(0);
		}
		destPos = new int[destPostemp1.length];
		wtCount = destPostemp1.length;
		buildMap();
		setMap(wtCount, wallPostemp1, goodsPostemp1, destPostemp1);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public int getPos(int x, int y) {
		return x+20*y;
	}
	
	// test
	public static void main(String args[]) throws Exception {
		new PbFrame();
	}
}