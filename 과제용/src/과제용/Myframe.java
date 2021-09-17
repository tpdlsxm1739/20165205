package 과제용;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.Border;










public class Myframe{

	int S_MARGIN = 40;  		// 그림에 얼마 범위에 들어왔을 때 충돌로 결정할 것인지의 값(작은 그림)
	int B_MARGIN = 80;  
	int sec =0;
	int SPEED =200;
	final int BALL_SIZE = 50;

	public  JFrame startFrame = new JFrame(" 돈을 갖고 튀어라 ");
	public  JFrame charFrame = new JFrame(" 캐릭터를 고르시오 ");
	public static  JFrame gameFrame = new JFrame("돈을 갖고 튀어라 ");
	public  JFrame endFrame = new JFrame("게임 종료 ");
	public JFrame setFrame = new JFrame(" 소리설정 ");
	private final int NEW_ATTACKER_INTERVAL= 10;	// 새로운 공격자가 나타나는 주기
	private final int BIG_ATTACKER_INTERVAL= 10;
	final int INTERVAL =50;
	private final int STEPS = 10;
	private  JPanel startPanel;
	public   JPanel setPanel;
	private  JPanel charPanel;
	private  JPanel chartopPanel;
	private JPanel charcenterPanel;
	public  JPanel gamemainPanel;
	public  JPanel scorePanel;
	public  JPanel endPanel;

	GamePanel gamePanel;

	private final int SUSPEND = 2;
	private final int CONT = 4;
	private final int END = 8;

	private final String POLICE = "Image/경찰.jpeg";
	private final String MONEY= "Image/지폐.png";
	private final String PLAYER = "IMAGE/남자캐릭터.png";
	private final String PLAYER2 = "IMAGE/여자캐릭터.png";
	private final String GOLD = "IMAGE/금.png";
	private final String OBTACLE= "IMAGE/덫.png";


	ImageIcon gamestartbutton = new ImageIcon("Image/게임시작버튼.jpg");
	ImageIcon soundsetting = new ImageIcon("Image/소리설정.png");
	ImageIcon menimage = new ImageIcon("Image/남자캐릭터.png");
	ImageIcon girlimage = new ImageIcon("Image/여자캐릭터.png");
	ImageIcon gamebackground= new ImageIcon("Image/게임화면.png");

	public JButton start;
	public JButton setting;
	public JButton boy;
	public JButton girl;

	JButton go =new JButton("시작");		// 시작버튼
	JButton end=new JButton("종료");			// 종료버튼
	JButton suspend=new JButton("일시중지");	// 일시중지 버튼
	JButton cont=new JButton("계속");		// 계속 버튼
	JLabel timing=new JLabel("시간  : 0분 0초");

	public JSlider bs;
	public JSlider es;

	Timer gametime;
	Timer gamemove;

	ArrayList<Shape> policelist =new ArrayList<Shape>();			// 게임에 사용되는 (일반) 공격자 객체를 담는 리스트
	ArrayList<Shape> drinklist =new ArrayList<Shape>();	
	ArrayList<Shape> moneylist =new ArrayList<Shape>();			// 게임에 사용되는 (일반) 공격자 객체를 담는 리스트
	ArrayList<Shape> goldlist = new ArrayList<Shape>();	
	ArrayList<Shape> obstaclelist =new ArrayList<Shape>();
	ArrayList<Ball> list =new ArrayList<Ball>();

	ClockListener clockListener;

	int realwidth=700,realheight=600;

	Shape player;
    Shape player2;

	JLabel background;
	String playerName;

	Clip bgmClip;

	public Myframe() {


		startPanel = new JPanel();
		endPanel = new JPanel();


		setting = new JButton();


		//시작프레임 설정 
		startFrame.setResizable(false);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.add(startPanel);
		startFrame.setSize(700, 680);

		//게임시작배경화면 만들기 
		background = new JLabel (new ImageIcon("Image/경찰과 도둑 배경화면.jpg"));
		background.setBounds(0, 0, 700, 700);
		startPanel.add(background);

		//시작버튼지정 
		start = new JButton(gamestartbutton);
		start.setBounds(380, 50, 350, 100);
		start.setContentAreaFilled(false);
		start.setFocusPainted(false);
		start.setBorderPainted(false);
		start.addActionListener(new StartButton());
		background.add(start);

		//소리버튼지정 
		setting = new JButton(soundsetting);
		setting.setBounds(730, 400, 120, 120);
		setting.setContentAreaFilled(false);
		setting.setFocusPainted(false);
		setting.setBorderPainted(false);
		background.add(setting);
		setting.addActionListener(new SettingButton());

		//소리프레임
		setFrame.setResizable(false);
		setFrame.setSize(300,300);
		setFrame.setVisible(false);

		//소리패널
		setPanel =new JPanel();
		setPanel.setLayout(new GridLayout(0,1));

		//슬라이더 표시

		JLabel bstext =new JLabel ("배경음");
		setPanel.add(bstext);
		bs = new JSlider(0,100,50);
		bs.setMajorTickSpacing(10);
		bs.setMajorTickSpacing(1);
		bs.setPaintTicks(true);
		bs.addChangeListener(null);
		setPanel.add(bs);
		JLabel estext =new JLabel ("효과음");
		setPanel.add(estext);
		es = new JSlider(0,100,50);
		es = new JSlider(0,100,50);
		es.setMajorTickSpacing(10);
		es.setMajorTickSpacing(1);
		es.setPaintTicks(true);
		es.addChangeListener(null);
		setPanel.add(es);

		setFrame.add(setPanel);


		//캐릭터프레임
		charFrame.setResizable(false);
		charFrame.setSize(700,700);
		charFrame.setVisible(false);


		//캐릭터패널

		JLabel charex = new JLabel("캐릭터를 고르시오 ");
		charex.setBounds(300,100,10,10);
		charex.setFont(new Font("HY나무B", Font.BOLD, 26));

		charPanel =new JPanel();
		chartopPanel =new JPanel();
		chartopPanel.setBounds(0,100,700,100);
		charcenterPanel =new JPanel();
		charcenterPanel.setBounds(0,300,700,600);
		boy =new JButton(menimage);
		girl =new JButton(girlimage);


		charPanel.setLayout(null);
		charPanel.add(chartopPanel);
		charPanel.add(charcenterPanel);

		chartopPanel.add(charex);
		charcenterPanel.add(boy);
		boy.setPreferredSize(new Dimension(300, 300));
		boy.addActionListener(new charbutton());
		charcenterPanel.add(girl);
		girl.setPreferredSize(new Dimension(300, 300));
		girl.addActionListener(new charbutton());
		Border border =BorderFactory.createTitledBorder("캐릭터");
		charcenterPanel.setBorder(border);
		charFrame.add(charPanel);


		//게임프레임 
		gameFrame.setSize(700, 700);
		gameFrame.setVisible(false);

		//backPanel을 레이아웃 시켜줌
		JPanel gamemainPanel = new JPanel();

		// gamePanel 패널
		gamePanel =new GamePanel();
		gamePanel.setBounds(0,0,700,630);


		// scorePanel 패널 
		scorePanel = new JPanel();
		scorePanel.setBackground(Color.white);
		scorePanel.setBounds(0, 630, 700, 70);
		scorePanel.add(suspend);
		scorePanel.add(cont);
		scorePanel.add(end);
		scorePanel.add(timing);
		scorePanel.add(new JLabel(" Player : "));
		scorePanel.add(new JLabel(playerName));
		scorePanel.add(new JLabel("지폐수 : "));
		scorePanel.setOpaque(false);

		gamemainPanel.setLayout(null);
		gamemainPanel.add(gamePanel);
		gamemainPanel.add(scorePanel);



		// 키보드로 움직일 player 개체 생성
		player = new Shape(PLAYER, B_MARGIN, realwidth, realheight);
		player2 = new Shape(PLAYER2, B_MARGIN, realwidth, realheight);
		clockListener = new ClockListener();

		gametime = new Timer(1000, clockListener);	
		gamemove = new Timer(INTERVAL, new MyClass());
		gamePanel.addKeyListener(new DirectionListener());	// 키보드 리스너 설치
		gamePanel.setFocusable(false);		
		//메인 패널에 추가 


		gameFrame.add(gamemainPanel);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);



		suspend.addActionListener(new SuspendListener());
		cont.addActionListener(new ContListener());
		end.addActionListener(new EndListener());



	}


	public static void main(String[] args) {
		new Myframe();
	}

	private void buttonToggler(int flags) {

		if ((flags & SUSPEND) != 0)
			suspend.setEnabled(true);
		else
			suspend.setEnabled(false);
		if ((flags & CONT) != 0)
			cont.setEnabled(true);
		else
			cont.setEnabled(false);
		if ((flags & END) != 0)
			end.setEnabled(true);
		else
			end.setEnabled(false);
	}

	private void finishGame() {
		// 시간 디스플에이 멈춤
		gametime.stop();
		gamemove.stop();	// 그림객체 움직임 멈춤
		gamePanel.setFocusable(false);	

	}
	public  class MyClass implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			for (Shape s : policelist) {
				if (s.collide(new Point(player.x, player.y))) {
					// 충돌의 음향
					finishGame();						// 게임 중단
					return;
				}
			}


			for (Shape s : policelist) {
				s.move();
			}

			gamePanel.repaint();

		}

	}

	class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {

			//잔상이 남지 않게 매번 repaint 
			g.drawImage(gamebackground.getImage(), 0, 0, 700, 630, null);

			for (Shape s : policelist) {
				s.draw(g, this);
			}
			for (Shape s : moneylist) {
				s.draw(g, this);
			}
			
			player.draw(g, this);
			player2.draw(g, this);

		}
		
		
		
		
		

	}
	private void prepareAttackers() {
		// 공격자 3으로 시작
		policelist.add(new DiagonallyMovingShape(POLICE, S_MARGIN, STEPS, realwidth, realheight));
		policelist.add(new HorizontallyMovingShape(POLICE, S_MARGIN, STEPS, realwidth, realheight));
		policelist.add(new VerticallyMovingShape(POLICE, S_MARGIN, STEPS, realwidth, realheight));
	}


	private Shape getRandomAttacker(String pic, int margin, int steps) {
		int rand = (int)(Math.random() * 3) + 1;
		Shape newAttacker;
		switch (rand) {
		case 1 :
			newAttacker =  new DiagonallyMovingShape(pic, margin, steps, realwidth, realheight);
			break;
		case 2 :
			newAttacker =  new HorizontallyMovingShape(pic, margin, steps, realwidth, realheight);
			break;
		case 3 :
			newAttacker =  new VerticallyMovingShape(pic, margin, steps, realwidth, realheight);
			break;
		default :	
			newAttacker =  new DiagonallyMovingShape(pic, margin, steps, realwidth, realheight);
		}
		return newAttacker;
	}


	//시작 버튼을 누를 때 일어나는 이벤트 
	public class StartButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == start) {

				startFrame.setVisible(false);
				charFrame.setVisible(true);


				try {
					bgmClip = AudioSystem.getClip();
					bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
					bgmClip.start();
				} catch (Exception ex) {
					System.out.println("test");
				}
			}
		}
	}
	public class SettingButton implements ActionListener {


		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == setting) {

				startFrame.setVisible(true);
				setFrame.setVisible(true);

			}
		}

	}
	class SuspendListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gametime.stop();		
			gamemove.stop();
			gamePanel.setFocusable(false);					// 게임 프레임에 키 안먹게 함
			buttonToggler(CONT+END);						// 활성화 버튼의 조정
		}
	}

	class ContListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gametime.restart();
			gamemove.restart();
			gamePanel.setFocusable(true);					// 게임 프레임 키 먹게 함
			gamePanel.requestFocus();						// 전체 프레밍에 포커싱해서 키 먹게 함
			buttonToggler(SUSPEND+END);						// 활성화 버튼의 조정
		}
	}

	// 종료버튼을 위한 감청자
	class EndListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			finishGame();
		}
	}

	public class charbutton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == boy ) {
				playerName = JOptionPane.showInputDialog("이름을 입력하시오:");
				if(playerName != null) {  
					charFrame.setVisible(false);
					gameFrame.setVisible(true);
					gamemove.start();
					gamePanel.setFocusable(true);					// gamePanel이 포커싱될 수 있게 함
					gamePanel.requestFocus();	
					gametime.start();

					prepareAttackers();

					buttonToggler(SUSPEND+END);	

				}
				else {
					
					playerName = JOptionPane.showInputDialog("이름을 입력하시오:");
				
					if(playerName != null) {  
					charFrame.setVisible(false);
					gameFrame.setVisible(true);
					gamemove.start();
					gamePanel.setFocusable(true);					// gamePanel이 포커싱될 수 있게 함
					gamePanel.requestFocus();	
					gametime.start();

					prepareAttackers();

					buttonToggler(SUSPEND+END);	

				}
			
				}
		}
	}
	}



	private class ClockListener implements ActionListener {
		int times = 0;
		public void actionPerformed (ActionEvent event) {		
			times++;						
			timing.setText("시간  : "+times/60+"분 "+times%60+"초");


		}
		public void reset() {
			times = 0;
		}
		public int getElaspedTime() {
			return times;
		}
	}

	class DirectionListener implements KeyListener {
		public void keyPressed (KeyEvent e) {
			switch (e.getKeyCode()){
			case KeyEvent.VK_UP:
				if (player.y >= 0)
					player.y -= STEPS;
				break;
			case KeyEvent.VK_DOWN:
				if (player.y <= realheight)
					player.y += STEPS;
				break;
			case KeyEvent.VK_LEFT:
				if (player.x >= 0)
					player.x -= STEPS;
				break;
			case KeyEvent.VK_RIGHT:
				if (player.x <= realwidth)
					player.x += STEPS;
				break;
			}
		}
		public void keyTyped (KeyEvent event) {}
		public void keyReleased (KeyEvent event) {}
	}


}

