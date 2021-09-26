package ������;
import java.net.*;
public class ballMovingShape extends PosImageIcon{
	
	public ballMovingShape(String imgURL, int x, int y, int margin, double steps, int xBoundary, int yBoundary) {
		// imgPath : �׸� ������ ��θ�
		// x, y : �̹����� ��ġ ��ǥ
		// margin : �� �̹����� ������ ��Ÿ���� ���� (�� �����ȿ� ������ �浹 �� ������ �Ǵ� �ϱ� ����)
		// steps : �̹����� �����϶� �̵��ϴ� ��ǥ ����
		// xBoundary, yBoundary : �׸��� �̵��� �� �ִ� ��ǥ�� �ִ밪
		super (imgURL, x, y, margin, steps, xBoundary, yBoundary);
	}
	
	public ballMovingShape(String imgURL,int margin, double steps, int xBoundary, int yBoundary) {
		super (imgURL, margin, steps, xBoundary, yBoundary);
	}

	public void move() {
		
		
		if (xDirection > 0 && x >= xBoundary) {
			xDirection = -1;
		}
	
		if (yDirection <0 && y>=yBoundary) {
			xDirection = -1;
		}
		
		if (yDirection < 0 && y <= 0) {
			yDirection = 1;
		}
		y += (yDirection * steps);
       if (xDirection < 0 && x <= 0) {
		xDirection = 1;
	      }
	    x += (xDirection * steps);
	
		
	}


}