package ������;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.net.*;
import java.awt.image.*;
import java.awt.*;

public class PosImageIcon extends ImageIcon {
	public int x;				// ����� ��ġ ��ǥ
	public int y;				// ����� ��ġ ��ǥ
	private int initX, initY; 	// �ʱ���� x, y��ǥ
	protected int xDirection;
	protected int yDirection;
	protected int xBoundary;    //�׸��� �̵��� �� �ִ� x��ǥ�� �ִ�
	protected int yBoundary;    //�׸��� �̵��� �� �ִ� y��ǥ�� �ִ�
    protected double steps;
	protected int margin;		// �� ����� ������ ���ԵǴ� ������ ��Ÿ���� ����
	
	public PosImageIcon(String imgURL, int x, int y, int margin, double steps, int xBoundary, int yBoundary) {
		// imgURL : �׸� ������ ��θ�
		// x, y : �̹����� ���� ��ġ ��ǥ
		// margin : �� �̹����� ������ ��Ÿ���� ���� (�� �����ȿ� ������ �浹 �� ������ �Ǵ� �ϱ� ����)
		// steps : �̹����� �����϶� �̵��ϴ� ��ǥ ����
		// xBoundary, yBoundary : �׸��� �̵��� �� �ִ� ��ǥ�� �ִ밪
		super(imgURL);
		this.x = x;
		this.initX = x;
		this.y = y;
		this.initY = y;
		this.margin = margin;
		this.xDirection = 1;
		this.yDirection = 1;
		this.steps = steps;
		this.xBoundary = xBoundary;
		this.yBoundary = yBoundary;
	}
	
	// ���� ��ġ�� ������ ����Ʈ�� �ִ� ������
	public PosImageIcon(String imgURL, int margin, double  steps, int xBoundary, int yBoundary) {
		this (imgURL, 0, 0, margin, steps, xBoundary, yBoundary);
		x= (int) (Math.random() * xBoundary+1);
		y= (int) (Math.random() * yBoundary+1);
	}
	
	// ������ġ�� �־��� �ٿ���� �߾ӿ� ��ġ��Ű�� ������
	public PosImageIcon(String imgURL, int margin, int xBoundary, int yBoundary) {
		this (imgURL, xBoundary/2, yBoundary, margin, margin, xBoundary, yBoundary);
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
	}
	
	public int getMargin() {
		return margin;
	}
	
	// �ϳ��� ���� �� ���� �浹�Ͽ����� (����� margin �Ÿ��ȿ� �ִ���)�� �Ǵ��ϴ� �Լ�
	public boolean collide (Point p2) {
		Point p = new Point(this.x, this.y);
		if (p.distance(p2) <= margin) return true;
		return false;
	}
	
	public void reset() {
		x = initX; y= initY;
	}
	
	// �ش� ����� g�� ������ִ� �޼ҵ�
	public void draw(Graphics g, ImageObserver io) {
		((Graphics2D)g).drawImage(this.getImage(), x, y, margin, margin, io);
	}

	// �� �κ��� ����� �پ��� ��ü�� ����� �Ͼ �� �ֵ��� �����ϱ�
	public void move() {};
}
