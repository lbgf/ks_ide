
变量 pf = 创建 PbFrame();

类 地图 {

	pf = null;

	函数 初始化(pf) {
  	this.pf = pf;
  }
  
  函数 设置名称(名称) {
  	this.pf.setTitle(名称);
  }
  
  函数 设置机器人方向控制(上, 下, 左, 右) {
  	this.pf.setControl(上, 下, 左, 右);
  }
  
  函数 设置机器人位置(x, y) {
  	this.pf.setRobotPos(x, y);
  }
  
  函数 添加障碍(x, y) {
  	this.pf.addWallPos(x, y);
  }
  
  函数 添加多个水平障碍(y, x1, x2) {
  	this.pf.addWallPosV(y, x1, x2);
  }
  
  函数 添加多个垂直障碍(x, y1, y2) {
  	this.pf.addWallPosH(x, y1, y2);
  }
  
  函数 添加物品(x, y) {
  	this.pf.addGoodsPos(x, y);
  }
  
  函数 添加目标位置(x, y) {
  	this.pf.addDestPos(x, y);
  }
  
  函数 展示() {
  	this.pf.initMap();
    this.pf.setVisible(true);
  }
}

变量 游戏地图 = 创建 地图();
游戏地图.初始化(pf);
