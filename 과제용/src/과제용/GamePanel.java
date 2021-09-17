package ������;

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
   
   int x, y;         //���콺 ��ǥ
   int imgX, imgY;      //���� ��ǥ
   int size=0;
   boolean sizeup = true;
   
   public static final String EAT_SOUND = "/res/jelly1.wav";
   public static final String CRUSH_SOUND = "/res/���ҿ���.wav";

   public static AudioClip eatSound;//������ ���� �� ����
   public static AudioClip crushSound;//���� �浹�� �� ����
   
   Date now = new Date();   //��¥����
   long startTime,endTime; 
   //����� ��ǥ
   int[] aX = new int[10];
   int[] aY = new int[10];
   
   //����� ������
   int[] xDirection = new int[10];
   int[] yDirection = new int[10];
   
   int count = 0 , timeCount=0 , realTime=0;      //���� ���� ����
   
   String user ;
   
   ImageIcon Nimo;
   ImageIcon mamma;
   ImageIcon attacker;   
   ImageIcon   bg = new ImageIcon("Image/���ӹ��.jpg");;
   
   //�������Ḧ �˸��� ������
   JFrame endFrame = new JFrame("�Ļ� ����");
   JPanel endPanel = new JPanel();
   JLabel end = new JLabel(new ImageIcon("src/��������.jpg"));
   JLabel el;

   
   //��� ���� ��ư
   JButton rButton = new JButton("�� �����?");
   Font font = new Font("HY����L", Font.BOLD, 30);
   
   //����� �����ִ� ������
   JPanel recordPanel = new JPanel();
   public static JFrame recordFrame = new JFrame("���!");
   JLabel record;
   JButton endButton = new JButton(new ImageIcon("src/����.png"));
   JLabel endLabel;
   JLabel score;
   
   Timer time = new Timer(50, new timeListener());
   
   int check;      
   
   int mc = 1;            //���� ����
   int shark = 1;         //����� ������
   int a = 1;            //���� ����
   
   public GamePanel() {
      
      //GamePanel�� �̹����� �÷���
      Nimo = new ImageIcon("Image/�ϸ�.png");
      mamma = new ImageIcon("Image/����.png");
      attacker = new ImageIcon("Image/����.jpeg");   
      
      //���ڵ� ������
      
      recordPanel.setLayout(null);
      endButton.setBounds(430, 480, 30, 25);
      record = new JLabel(new ImageIcon("src/�� ���.jpg"));
      record.setBounds(0, 0, 500, 600);
      recordPanel.add(record);
      
      long estime; // ����ð�-���۽ð�
      endTime = (new Date()).getTime(); //���ӳ����ð��޾ƿ���
      estime = endTime - startTime;
      endLabel=new JLabel();
      record.add(endButton);
      endLabel.setText("<html>��¥: " + now + " <br>"
            + "������̸�: "+ user+" / " + "�� ���ӽð�:" + (int)(estime*0.001)+"��</html>");
      endLabel.setFont(new Font("HY��B",Font.BOLD,20));
      record.add(endLabel);
      recordFrame.add(recordPanel);
      recordFrame.getContentPane().add(BorderLayout.NORTH, endLabel);
       
      recordFrame.getContentPane().add(BorderLayout.CENTER, record);
       
     
      
      //���ڵ庸�� ��ư
      rButton.setFont(font);
      rButton.addActionListener(new RecordButton());
      
      endButton.addActionListener(new EndButton());
      
      recordFrame.setVisible(false);
      
      //�� ����  �ʱⰪ
      for(int i=0; i<10; i++){
         aX[i] = (int)(Math.random()*500+1);
         aY[i] = (int)(Math.random()*620+1);
         
         xDirection[i] = 10;
         yDirection[i] = 10;
      }
      
   
      //������ ���� �ʱⰪ�� ����
      imgX = (int)(Math.random()*500+1);
      imgY = (int)(Math.random()*630+1);
      
      addMouseMotionListener(this);
      addMouseListener(this);   
      
      time.start();
      

      try {
         // backgroundSound = JApplet.newAudioClip(new URL("file", "localhost","/res/start.wav"));
         // boomSound = JApplet.newAudioClip(new URL("file", "localhost","/res/boom.wav"));
         // ���� ����� ����θ� ��Ÿ���� ���ϴ� ����̾, jar���Ϸ� �������� ���鶧 ��θ� ã�� ���ϴ�
         // ������ ����. ���� getClass()�� ����Ͽ� ������� URL�� ���ϴ� ����� �Ʒ�ó�� ����ؾ� ��
         // ���⿡�� root��
    	  //�Ǵ� ������ ���� �� ���α׷��� ����Ǵ� ���̴� ���� ������ �־��־�� ��
         eatSound = JApplet.newAudioClip(getClass().getResource(EAT_SOUND));
         crushSound = JApplet.newAudioClip(getClass().getResource(CRUSH_SOUND));
         
      }
      catch(Exception e){
         System.out.println("���� ���� �ε� ����");
      }
      
   }
   
   public void paintComponent(Graphics g) {
      
      Graphics2D g2d = (Graphics2D) g;
      
    
      
      //�ܻ��� ���� �ʰ� �Ź� repaint ���ش�
      g2d.fillRect(0, 0, 570, 700);
      g2d.drawImage(bg.getImage(), 0, 0, 570, 700, null);
      
      //�̹����� ���� ��ġ�� ũ��
      g2d.drawImage(mamma.getImage(), imgX, imgY, 60, 60, null);
      g2d.drawImage(Nimo.getImage(), x, y, 80+size, 80+size, null);
      
      //���� 3���� ��� 1���� �߰�
      check = count / 3 + 1;   
      
      //������ 30�� �Ѱ� �Ծ ���� 10������ ����
      if(check > 10) check = 10;
      
      for(int i=0; i<check; i++){
         g2d.drawImage(attacker.getImage(), aX[i], aY[i], 80, 80, null);
      }
   
      //scoreLabel�� ������ �Է�
      String s = count * 1 + "";
      score.setText(s);
   }

   class timeListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         
         if(shark == 1) {
            
            for(int i=0; i<10; i++){
               
               // x ���Ƿ��� �������� ����
               if (xDirection[i] > 0 &&  aX[i] >= 500) {
                  xDirection[i] = -10;
               }
               
               if (xDirection[i] < 0 &&  aX[i] <= 0) {
                  xDirection[i] = 10;
               }
               
                aX[i] += xDirection[i];
      
               // y ���Ƿ��� �������� ����
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
   
   //���ھ �ٲ�� ���ִ� �޼ҵ�
   public void setLabel(JLabel scoreLabel){
      this.score = scoreLabel;      
   }
   
   //���콺�� Ŭ���ϸ� ������ ��ġ�� �ٲ�� �ϴ� �޼ҵ�
   public void mouseClicked(MouseEvent e) {
      
      if(mc==1) {
         
         int x =   e.getPoint().x;
         int y = e.getPoint().y;      
         
         //������ ��������� Ŭ������ �� �����ϰ� ��
         if(imgX <= x && x <= imgX+40 && imgY <= y && y <= imgY+40){
            
            eatSound.play();
            
            imgX = (int)(Math.random()*500+1);
            imgY = (int)(Math.random()*630+1);   
            
            count ++;
            
            //paintComponent�޼ҵ�� ��
            repaint();
            
         }
      }
   }
   
   //���콺�� �����ӿ� ���� ��ǥ�� ��� �޼ҵ�
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
               
               //���� �´����� ���� ����
               if(p.distance(gp[i]) <= 65) {
                  time.stop();
                  
                  
                  shark = 0;
                  mc = 0;
                  
                  endPanel.setLayout(null);
                  rButton.setBounds(0, 400, 400, 70);
                  end.setBounds(0, 0, 400, 400);
                  endPanel.add(rButton);
                  endPanel.add(end);
                  endLabel.setText("<html>��¥: " + now + " <br>"
                        + "������̸�: "+ user+" / " + "�� ���ӽð�:" + realTime + "��");
                  
                  el = new JLabel(user + "���� �ϸ��  ������ " + count + "���� �Ծ����ϴ�");
                  el.setBounds(0, 300, 400, 20);
                  el.setFont(new Font("HY����B", Font.BOLD, 20));
                  el.setForeground(Color.RED);
                  end.add(el);
                  
                  endFrame.add(endPanel);
                  endFrame.setSize(400, 500);
                  endFrame.setResizable(false);
                  endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  endFrame.setVisible(true);
                  
                  Myframe.gameFrame.setEnabled(false);      // ������ �ϴ� �������� Ű �ȸ԰� ��
                  
                  a = 0;
               }
            }
         }
      }
   }

   //��� ���⺸�� ��ư�� Ŭ������ �� �Ͼ�� �̺�Ʈ
   class RecordButton implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         
         Myframe.gameFrame.setVisible(false);
      
         recordFrame.setVisible(true);
         recordFrame.setResizable(false);
         recordFrame.setSize(500,600);
         
         endFrame.setVisible(false);
      }
   }
   
   //������ ��ư ������ �� �Ͼ�� �̺�Ʈ
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