package ������;




import java.net.URL;




public class VerticallyMovingShape extends Shape {
	public VerticallyMovingShape(String  imgURL, int x, int y, int margin, int steps, int xBoundary, int yBoundary) {
		// imgPath : �׸� ������ ��θ�
		// x, y : �̹����� ��ġ ��ǥ
		// margin : �� �̹����� ������ ��Ÿ���� ���� (�� �����ȿ� ������ �浹 �� ������ �Ǵ� �ϱ� ����)
		// steps : �̹����� �����϶� �̵��ϴ� ��ǥ ����
		// xBoundary, yBoundary : �׸��� �̵��� �� �ִ� ��ǥ�� �ִ밪
		super (imgURL, x, y, margin, steps, xBoundary, yBoundary);
	}
	
	public VerticallyMovingShape(String imgURL, int margin, int steps, int xBoundary, int yBoundary) {
		super (imgURL, margin, steps, xBoundary, yBoundary);
	}

	public void move() {
		if (xDirection > 0 && x >= xBoundary) {
			xDirection = -1;
		}
		if (xDirection < 0 && x <= 0) {
			xDirection = 1;
		}

		if (yDirection > 0 && y >= yBoundary) {
			yDirection = -1;
			x += (xDirection * steps * 5);
		}
		if (yDirection < 0 && y <= 0) {
			yDirection = 1;
			x += (xDirection * steps * 5);
		}

		y += (yDirection * steps);
	}
}
