package 과제용;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ball {
	int pX;				// 洹몃┝�쓽 X醫뚰몴
	int pY;				// 洹몃┝�쓽 y醫뚰몴
	int width;			// 洹몃┝�쓽 �꼻�씠
	int height;			// 洹몃┝�쓽 �넂�씠
	int moveX=1, moveY=1; // 洹몃┝�쓽 �씠�룞諛⑺뼢 諛� 蹂댄룺
	Color color = Color.green;		// �쁽�옱 怨듭쓽 �깋. 泥섏쓬�뿉�뒗 Green�쑝濡� �떆�옉

	// �깮�꽦�옄
	public Ball(int x, int y, int width, int height) {
		pX=x;
		pY=y;
		this.width = width;
		this.height = height;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	// 洹몃┝�쓽 �씠�룞
	public void move(int x, int y) {
		pX += x;
		pY += y;
	}
	// 洹몃┝���떊 �썝 洹몃━湲� (�쁽�옱 媛앹껜�쓽 �깋�쑝濡� 梨꾩�)
	public void draw (Graphics g) {
		g.setColor(color);
		g.fillOval(pX, pY, width, height);
	}
	// �뼱�뼡 二쇱뼱吏� 醫뚰몴 p �� 洹몃┝�쓽 以묒젏怨쇱쓽 嫄곕━ (洹몃┝�쓽 醫뚰몴�뒗 �쇊履� 紐⑥꽌由ъ씠誘�濡�... �룾怨� �넂�씠瑜� 媛먯븞�븯�뿬 以묒젏�쓣 �궗�슜)
	public double distance (Point p) {
		return Math.sqrt(Math.pow((pX+width/2)-p.x, 2)+ Math.pow((pY+height/2)-p.y, 2));
	}
}


