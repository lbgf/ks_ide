
���� pf = ���� PbFrame();

�� ��ͼ {

	pf = null;

	���� ��ʼ��(pf) {
  	this.pf = pf;
  }
  
  ���� ��������(����) {
  	this.pf.setTitle(����);
  }
  
  ���� ���û����˷������(��, ��, ��, ��) {
  	this.pf.setControl(��, ��, ��, ��);
  }
  
  ���� ���û�����λ��(x, y) {
  	this.pf.setRobotPos(x, y);
  }
  
  ���� ����ϰ�(x, y) {
  	this.pf.addWallPos(x, y);
  }
  
  ���� ��Ӷ��ˮƽ�ϰ�(y, x1, x2) {
  	this.pf.addWallPosV(y, x1, x2);
  }
  
  ���� ��Ӷ����ֱ�ϰ�(x, y1, y2) {
  	this.pf.addWallPosH(x, y1, y2);
  }
  
  ���� �����Ʒ(x, y) {
  	this.pf.addGoodsPos(x, y);
  }
  
  ���� ���Ŀ��λ��(x, y) {
  	this.pf.addDestPos(x, y);
  }
  
  ���� չʾ() {
  	this.pf.initMap();
    this.pf.setVisible(true);
  }
}

���� ��Ϸ��ͼ = ���� ��ͼ();
��Ϸ��ͼ.��ʼ��(pf);
