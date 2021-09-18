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
	int count = 0;
	int check ;
	public  JFrame startFrame = new JFrame(" 돈을 갖고 튀어라 ");
	public  JFrame charFrame = new JFrame(" 캐릭터를 고르시오 ");
	public static  JFrame gameFrame = new JFrame("돈을 갖고 튀어라 ");
	public  JFrame endFrame = new JFrame("게임 종료 ");
	public JFrame setFrame = new JFrame(" 소리설정 ");
	private final int OBSTACLE_INTERVAL= 10;	// 새로운 공격자가 나타나는 주기
	private final int DRINK_INTERVAL= 15;
	private final int GOLD_INTERVAL= 10;
	private final int POLICE_INTERVAL= 10;
	final int MOVE =100;
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
	private final String MEN = "IMAGE/남자캐릭터.png";
	private final String GIRL = "IMAGE/여자캐릭터.png";
	private final String OBSTACLE= "IMAGE/덫.png";
	private final String DRINK= "IMAGE/에너지 드링크.jpeg";
	private final String MONEY ="Image/지폐.png";
	private final String GOLD= "Image/금.png";

	ImageIcon gamestartbutton = new ImageIcon("Image/게임시작버튼.jpg");
	ImageIcon soundsetting = new ImageIcon("Image/소리설정.png");
	ImageIcon menimage = new ImageIcon("Image/남자캐릭터.png");
	ImageIcon girlimage = new ImageIcon("Image/여자캐릭터.png");
	ImageIcon gamebackground= new ImageIcon("Image/게임화면.png");
	ImageIcon money = new ImageIcon("Image/지폐.png");
	ImageIcon gold= new ImageIcon("Image/금.png");


	public JButton start;
	public JButton setting;
	public JButton boy;
	public JButton girl;

	JButton go =new JButton("시작");		// 시작버튼
	JButton end=new JButton("종료");			// 종료버튼
	JButton suspend=new JButton("일시중지");	// 일시중지 버튼
	JButton cont=new JButton("계속");		// 계속 버튼
	JLabel timing=new JLabel("도망시간 : 0분 0초");
	JLabel score = new JLabel("지폐수:");
	public JSlider bs;
	public JSlider es;

	Timer gametime;
	Timer gamemove;

	ArrayList<Shape> policelist =new ArrayList<Shape>();			// 게임에 사용되는 (일반) 공격자 객체를 담는 리스트
	ArrayList<Shape> drinklist =new ArrayList<Shape>();	
	ArrayList<Shape> obstaclelist =new ArrayList<Shape>();
	ArrayList<Shape> moneylist =new ArrayList<Shape>();
	ArrayList<Shape> goldlist =new ArrayList<Shape>();


	ClockListener clockListener;
	int mX =640;
	int mY =640;
	int realwidth=670,realheight=565;

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
		scorePanel.add(go);
		scorePanel.add(suspend);
		scorePanel.add(cont);
		scorePanel.add(end);
		scorePanel.add(timing);
		scorePanel.add(new JLabel(" Player : "));
		scorePanel.add(new JLabel(playerName));
		scorePanel.add(score);
		score.setFont(new Font("AR CARTER", Font.BOLD, 22));
		JLabel scoreLabel =new JLabel();
		scorePanel.add(scoreLabel);
		gamePanel.setLabel(scoreLabel);
		scoreLabel.setFont(new Font("AR CARTER", Font.BOLD, 22));
		scorePanel.setOpaque(false);

		gamemainPanel.setLayout(null);
		gamemainPanel.add(gamePanel);
		gamemainPanel.add(scorePanel);





		clockListener = new ClockListener();

		gametime = new Timer(1000, clockListener);	
		gamemove = new Timer(MOVE, new MyClass());
		gamePanel.addKeyListener(new DirectionListener());	// 키보드 리스너 설치
		gamePanel.setFocusable(false);		
		//메인 패널에 추가 


		gameFrame.add(gamemainPanel);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);


		go.addActionListener(new SuspendListener());
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
			// 현재 활동 중이 아니면 하나 추가	




			for (Shape s : obstaclelist) {
				if (s.collide(new Point(player.x, player.y))) {
					finishGame();						// 게임 중단
					return;
				}
			}

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
			for (Shape s : drinklist) {
				s.move();
			}

			for(Shape s : moneylist)
		     {
				if (s.collide(new Point(player.x, player.y)))
			{
                     
					
						mX= (int)(Math.random()*650+1);

						mY= (int)(Math.random()*650+1); 

						count ++;
           
					

				}

			gamePanel.repaint();
			
				}

		}

	}


	class GamePanel extends JPanel {



		private JLabel score;


		public void paintComponent(Graphics g) {

			//잔상이 남지 않게 매번 repaint 
			g.drawImage(gamebackground.getImage(), 0, 0, 700, 630, null);

			for (Shape s : policelist) {
				s.draw(g, this);
			}
			for (Shape s : drinklist) {

				s.draw(g, this);
			}

			for (Shape s : obstaclelist) {
				s.draw(g, this);
			}

			for (Shape s : moneylist) {
				s.draw(g, this);
			}
			for (Shape s : goldlist) {
				s.draw(g, this);
			}

			player.draw(g, this);





			String s = count * 1 + "";
			score.setText(s);



		}


		public void setLabel(JLabel scoreLabel) {
			this.score = scoreLabel;
		}



	}

	private void prepareAttackers() {
		drinklist = new ArrayList<Shape>();
		policelist.add(new DiagonallyMovingShape(POLICE, S_MARGIN, STEPS, realwidth, realheight));
		moneylist.add(new Shape(MONEY, S_MARGIN, 0, mX,  mY));
		moneylist.add(new Shape(MONEY, S_MARGIN, 0, mX,  mY));
		moneylist.add(new Shape(MONEY, S_MARGIN, 0, mX,  mY));
		moneylist.add(new Shape(MONEY, S_MARGIN, 0, mX,  mY));
		moneylist.add(new Shape(MONEY, S_MARGIN, 0, mX,  mY));
	}



	private Shape getRandomAttacker(String pic, int margin, int steps) {
		int rand = (int)(Math.random() * 3) + 1;
		Shape newAttacker;
		switch (rand) {
		case 1 :
			newAttacker =  new DiagonallyMovingShape(pic, margin, steps, realwidth, realheight);
			break;
		case 2 :
			newAttacker=  new HorizontallyMovingShape(pic, margin, steps, realwidth, realheight);
			break;
		case 3 :
			newAttacker=  new VerticallyMovingShape(pic, margin, steps, realwidth, realheight);
			break;
		default :	
			newAttacker =  new ballMovingShape(pic, margin, steps, realwidth, realheight);
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
	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 배경음악 시작
			gametime.start();								// 그림객체 움직임을 위한 시작
			clockListener.reset();							// 타이머의 시작값 초기화
			timing.setText("시간  : 0분 0초");	
			gametime.start();								// 시간 디스플레이 타이머시작

			prepareAttackers();	
			gamePanel.setFocusable(true);					// 게임 프레임 키 먹게 함
			gamePanel.requestFocus();
			buttonToggler(SUSPEND+END);	
			gamePanel.repaint();// 활성화된 버튼의 조정								// 화면을 다시 디스플레이 되게 함
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
			System.exit(0);
		}
	}

	public class charbutton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == boy ) {

				playerName = JOptionPane.showInputDialog("이름을 입력하시오:");
				if(playerName != null) {  

					player = new Shape(MEN, B_MARGIN, realwidth, realheight);

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
			if (e.getSource() == girl ) {

				playerName = JOptionPane.showInputDialog("이름을 입력하시오:");

				if(playerName != null) { 

					player = new Shape(GIRL, B_MARGIN, realwidth, realheight);
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



	private class ClockListener implements ActionListener {
		int times = 0;
		public void actionPerformed (ActionEvent event) {		
			times++;						
			timing.setText("도망시간  : "+times/60+"분 "+times%60+"초");

			if (times % GOLD_INTERVAL == 0)
				if (goldlist.isEmpty())
					goldlist.add(getRandomAttacker(GOLD, S_MARGIN, STEPS));

			// 시간이 일정시간 지나면 새로운 덫을 출현시킴
			// 시간이 일정시간 지나면 bigAttacker 출현/소멸 시킴
			if (times % DRINK_INTERVAL == 0) {
				if (drinklist.isEmpty())			// 현재 활동 중이 아니면 하나 추가	
					drinklist.add(getRandomAttacker(DRINK, B_MARGIN, STEPS));
				else									// 현재 활동 중이면 리스트 비우기
					drinklist = new ArrayList<Shape>();
			}

			if (times %POLICE_INTERVAL == 0) {
				policelist.add(getRandomAttacker(POLICE, S_MARGIN, STEPS));

			}

			if (count/5 == 0)
				obstaclelist.add(getRandomAttacker(OBSTACLE, S_MARGIN, STEPS));


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

			if(e.getKeyCode()== KeyEvent.VK_UP)
				if (player.y >= 0)
					player.y -= STEPS;

			if (e.getKeyCode()== KeyEvent.VK_DOWN)
				if (player.y <= realheight)
					player.y += STEPS;

			if (e.getKeyCode()== KeyEvent.VK_LEFT)
				if (player.x >= 0)
					player.x -= STEPS;

			if (e.getKeyCode()== KeyEvent.VK_RIGHT)

				if (player.x <= realwidth)
					player.x += STEPS;


		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}


	}


}

