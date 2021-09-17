package 과제용;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer; 

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener {
   
   int x, y;         //마우스 좌표
   int imgX, imgY;      //맘마 좌표
   int size=0;
   boolean sizeup = true;
   
   public static final String EAT_SOUND = "/res/jelly1.wav";
   public static final String CRUSH_SOUND = "/res/비명소오리.wav";

   public static AudioClip eatSound;//맘마를 먹을 때 음향
   public static AudioClip crushSound;//상어와 충돌할 때 음향
   
   Date now = new Date();   //날짜생성
   long startTime,endTime; 
   //상어의 좌표
   int[] aX = new int[10];
   int[] aY = new int[10];
   
   //상어의 움직임
   int[] xDirection = new int[10];
   int[] yDirection = new int[10];
   
   int count = 0 , timeCount=0 , realTime=0;      //맘마 먹은 갯수
   
   String user ;
   
   ImageIcon Nimo;
   ImageIcon mamma;
   ImageIcon attacker;   
   ImageIcon   bg = new ImageIcon("Image/게임배경.jpg");;
   
   //게임종료를 알리는 프레임
   JFrame endFrame = new JFrame("식사 종료");
   JPanel endPanel = new JPanel();
   JLabel end = new JLabel(new ImageIcon("src/게임종료.jpg"));
   JLabel el;

   
   //기록 보기 버튼
   JButton rButton = new JButton("내 기록은?");
   Font font = new Font("HY나무L", Font.BOLD, 30);
   
   //기록을 보여주는 프레임
   JPanel recordPanel = new JPanel();
   public static JFrame recordFrame = new JFrame("기록!");
   JLabel record;
   JButton endButton = new JButton(new ImageIcon("src/종료.png"));
   JLabel endLabel;
   JLabel score;
   
   Timer time = new Timer(50, new timeListener());
   
   int check;      
   
   int mc = 1;            //맘마 먹음
   int shark = 1;         //상어의 움직임
   int a = 1;            //상어와 닿음
   
   public GamePanel() {
      
      //GamePanel에 이미지를 올려줌
      Nimo = new ImageIcon("Image/니모.png");
      mamma = new ImageIcon("Image/지폐.png");
      attacker = new ImageIcon("Image/경찰.jpeg");   
      
      //레코드 프레임
      
      recordPanel.setLayout(null);
      endButton.setBounds(430, 480, 30, 25);
      record = new JLabel(new ImageIcon("src/내 기록.jpg"));
      record.setBounds(0, 0, 500, 600);
      recordPanel.add(record);
      
      long estime; // 종료시간-시작시간
      endTime = (new Date()).getTime(); //게임끝난시간받아오기
      estime = endTime - startTime;
      endLabel=new JLabel();
      record.add(endButton);
      endLabel.setText("<html>날짜: " + now + " <br>"
            + "사용자이름: "+ user+" / " + "총 게임시간:" + (int)(estime*0.001)+"초</html>");
      endLabel.setFont(new Font("HY강B",Font.BOLD,20));
      record.add(endLabel);
      recordFrame.add(recordPanel);
      recordFrame.getContentPane().add(BorderLayout.NORTH, endLabel);
       
      recordFrame.getContentPane().add(BorderLayout.CENTER, record);
       
     
      
      //레코드보기 버튼
      rButton.setFont(font);
      rButton.addActionListener(new RecordButton());
      
      endButton.addActionListener(new EndButton());
      
      recordFrame.setVisible(false);
      
      //상어가 있을  초기값
      for(int i=0; i<10; i++){
         aX[i] = (int)(Math.random()*500+1);
         aY[i] = (int)(Math.random()*620+1);
         
         xDirection[i] = 10;
         yDirection[i] = 10;
      }
      
   
      //맘마가 있을 초기값을 지정
      imgX = (int)(Math.random()*500+1);
      imgY = (int)(Math.random()*630+1);
      
      addMouseMotionListener(this);
      addMouseListener(this);   
      
      time.start();
      

      try {
         // backgroundSound = JApplet.newAudioClip(new URL("file", "localhost","/res/start.wav"));
         // boomSound = JApplet.newAudioClip(new URL("file", "localhost","/res/boom.wav"));
         // 위의 방법은 상대경로를 나타내지 못하는 방법이어서, jar파일로 배포판을 만들때 경로를 찾지 못하는
         // 문제가 생김. 따라서 getClass()를 사용하여 상대적인 URL을 구하는 방법을 아래처럼 사용해야 함
         // 여기에서 root가
    	  //되는 폴더는 현재 이 프로그램이 수행되는 곳이니 같은 레벨에 넣어주어야 함
         eatSound = JApplet.newAudioClip(getClass().getResource(EAT_SOUND));
         crushSound = JApplet.newAudioClip(getClass().getResource(CRUSH_SOUND));
         
      }
      catch(Exception e){
         System.out.println("음향 파일 로딩 실패");
      }
      
   }
   
   public void paintComponent(Graphics g) {
      
      Graphics2D g2d = (Graphics2D) g;
      
    
      
      //잔상이 남지 않게 매번 repaint 해준다
      g2d.fillRect(0, 0, 570, 700);
      g2d.drawImage(bg.getImage(), 0, 0, 570, 700, null);
      
      //이미지가 있을 위치와 크기
      g2d.drawImage(mamma.getImage(), imgX, imgY, 60, 60, null);
      g2d.drawImage(Nimo.getImage(), x, y, 80+size, 80+size, null);
      
      //맘마 3개당 상어 1마리 추가
      check = count / 3 + 1;   
      
      //맘마를 30개 넘게 먹어도 상어는 10마리로 유지
      if(check > 10) check = 10;
      
      for(int i=0; i<check; i++){
         g2d.drawImage(attacker.getImage(), aX[i], aY[i], 80, 80, null);
      }
   
      //scoreLabel에 점수를 입력
      String s = count * 1 + "";
      score.setText(s);
   }

   class timeListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         
         if(shark == 1) {
            
            for(int i=0; i<10; i++){
               
               // x 축의로의 움직임을 결정
               if (xDirection[i] > 0 &&  aX[i] >= 500) {
                  xDirection[i] = -10;
               }
               
               if (xDirection[i] < 0 &&  aX[i] <= 0) {
                  xDirection[i] = 10;
               }
               
                aX[i] += xDirection[i];
      
               // y 축의로의 움직임을 결정
               if (yDirection[i] > 0 && aY[i] >= 620) {
                  yDirection[i] = -10;
               }
               
               if (yDirection[i] < 0 && aY[i] <= 0) {
                  yDirection[i] = 10;
               }
               
               aY[i] += yDirection[i];
            }
            if(count!=0 && (count%3)!=0) sizeup = true;
            if(sizeup)
            {
               if(count!=0 && (count%3)==0)
               {
                  size = size+30;
                  sizeup = false;
               }
               
            }
            repaint();
         
               realTime++;
            
         }
      }
   }
   
   //스코어가 바뀌게 해주는 메소드
   public void setLabel(JLabel scoreLabel){
      this.score = scoreLabel;      
   }
   
   //마우스를 클릭하면 맘마의 위치가 바뀌게 하는 메소드
   public void mouseClicked(MouseEvent e) {
      
      if(mc==1) {
         
         int x =   e.getPoint().x;
         int y = e.getPoint().y;      
         
         //맘마의 어느곳에나 클릭했을 때 반응하게 함
         if(imgX <= x && x <= imgX+40 && imgY <= y && y <= imgY+40){
            
            eatSound.play();
            
            imgX = (int)(Math.random()*500+1);
            imgY = (int)(Math.random()*630+1);   
            
            count ++;
            
            //paintComponent메소드로 감
            repaint();
            
         }
      }
   }
   
   //마우스의 움직임에 따라 좌표를 얻는 메소드
   public void mouseMoved(MouseEvent e) {
      x = e.getX()-40; 
      y = e.getY()-40;
   
      Point p = new Point(x, y);
      Point[] gp = new Point[9];
      
      for(int i=0; i<9; i++) {
         gp[i] = new Point();
         gp[i] = new Point(aX[i], aY[i]);
      }
   
      for(int i=0; i<8; i++) {
         
         if(count >= 3*i) {
            
            if(a == 1) {
               
               //상어와 맞닿으면 게임 종료
               if(p.distance(gp[i]) <= 65) {
                  time.stop();
                  
                  
                  shark = 0;
                  mc = 0;
                  
                  endPanel.setLayout(null);
                  rButton.setBounds(0, 400, 400, 70);
                  end.setBounds(0, 0, 400, 400);
                  endPanel.add(rButton);
                  endPanel.add(end);
                  endLabel.setText("<html>날짜: " + now + " <br>"
                        + "사용자이름: "+ user+" / " + "총 게임시간:" + realTime + "초");
                  
                  el = new JLabel(user + "님의 니모는  맘마를 " + count + "개를 먹었습니다");
                  el.setBounds(0, 300, 400, 20);
                  el.setFont(new Font("HY나무B", Font.BOLD, 20));
                  el.setForeground(Color.RED);
                  end.add(el);
                  
                  endFrame.add(endPanel);
                  endFrame.setSize(400, 500);
                  endFrame.setResizable(false);
                  endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  endFrame.setVisible(true);
                  
                  Myframe.gameFrame.setEnabled(false);      // 게임을 하는 프레임의 키 안먹게 함
                  
                  a = 0;
               }
            }
         }
      }
   }

   //기록 보기보기 버튼을 클릭했을 때 일어나는 이벤트
   class RecordButton implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         
         Myframe.gameFrame.setVisible(false);
      
         recordFrame.setVisible(true);
         recordFrame.setResizable(false);
         recordFrame.setSize(500,600);
         
         endFrame.setVisible(false);
      }
   }
   
   //끝내기 버튼 눌렀을 때 일어나는 이벤트
   class EndButton implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         System.exit(0);
      }
   }
   
   public void mouseDragged (MouseEvent e) {}
   public void mouseEntered (MouseEvent e) {}
   public void mouseExited (MouseEvent e) {}
   public void mousePressed (MouseEvent e) {}  
   public void mouseReleased (MouseEvent e) {}
}