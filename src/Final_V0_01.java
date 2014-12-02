//name: Lester Lyu
//teacher: Ms.Strelkovska
//class: ICS3U-03
//time: February 25, 2014
//Slot Machine 
//Double Buffered code is from web

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

class Final_V0_01 extends Canvas implements Runnable, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int lines = 0;
	BufferedImage bufferedimage;
	ImageIcon n1;
	Image mImage;
	int xa=0,ya=-384,cnt_step,sprite_status=1, cnt1 = 0, lastChecked = -1;
	int totalFrameCount = 0, fps=0;
	static File maptxt[] = new File[6];
	public boolean upKey=false,leftKey=false,rightKey=false,downKey=false, leftMove=false, rightMove=false, upMove=false, downMove=false, walking=false, pressed=false;
	private static Insets insets;
	static int read_width = 32, read_height = 32, read_row = 115, read_col = 54, step = 0;
	Sprites sprite[]=new Sprites[12];
	int num[][][], keyPressed = -1;//0 left, 1 right, 2 up, 3 down
	//Timer timer1 = new Timer(13,this);
	JFrame frame;
	public static int width = 250, height = width/4*3, scale = 3;
	private Thread thread;
	public boolean running = false, firstTime = true;
	public Container c;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	//public Screen screen;
	boolean temp = true;
	//debug only
	JFrame debugFrame = new JFrame();
	JLabel dl[] = new JLabel[10];

	public Final_V0_01(String title){

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
		frame.setTitle(title);
		c = frame.getContentPane();
		//screen = new Screen(width * scale, height * scale, lines);
		//c.setBackground(Color.black);
		//c.setLayout(null);

		//
		for(int i=0; i<6; i++){
			maptxt[i] = new File("src/Map"+i+".txt");
		}
		try {
			lines = readLines();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Tiles.readPng();
		num = new int[6][lines][lines];

		Timer updateFPS = new Timer(1000,null);

		updateFPS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fps = totalFrameCount;
				totalFrameCount = 0;
			}
		});
		updateFPS.start();

		try {
			readMap();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


		this.addKeyListener(this);
		for(int i=0; i<12; i++)
			sprite[i]= new Sprites();


		Container c1 = debugFrame.getContentPane();
		c1.setLayout(new FlowLayout());
		for(int i=0; i<5; i++){
			dl[i] = new JLabel("       ");
			c1.add(dl[i]);
		}

		debugFrame.setSize(100,150);
		debugFrame.setLocationRelativeTo(null);
		debugFrame.setVisible(true);


		//timer1.start();
	}
	public synchronized void start(){
		running = true;
		thread = new Thread(this, "main");
		thread.start();
	}
	public synchronized void stop(){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long lastTime2 = System.currentTimeMillis();
		final double ns = 1000000000.0/60.0;  //60 times per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while(running){
			//System.out.println("!!!!!!");
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				delta--;
				updates++;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - lastTime2 >= 1000){
				lastTime2 += 1000;
				//System.out.println("ups: "+updates+" fps: "+frames);
				frame.setTitle("BETA | ups: "+updates+" fps: "+frames);
				updates = 0; 
				frames = 0;
			}

		}

	}
	public void update(){
		cnt_step++;
		dl[3].setText("keyPressed: "+(char)keyPressed);
		{//Check Keys
			if(leftKey){
				if(keyPressed!='u'&&keyPressed!='d'&&keyPressed!='r')
					keyPressed='l';
			}
			else if(rightKey){
				if(keyPressed!='u'&&keyPressed!='d'&&keyPressed!='l')
					keyPressed='r';
			}
			else if(upKey){
				if(keyPressed!='l'&&keyPressed!='r'&&keyPressed!='d')
					keyPressed='u';
			}
			else if(downKey){
				if(keyPressed!='l'&&keyPressed!='r'&&keyPressed!='u')
					keyPressed='d';
			}
		}//end keys


		if(!walking&&keyPressed!=-1)
			walking = true;

		dl[3].setText("keyPressed: "+(char)keyPressed);
//		if(keyPressed=='l'&&firstTime){
//			temp = isWalkabel('l');
//			walking = temp;
//			firstTime = false;
//			if(!temp){
//				keyPressed=-1;
//				walking = false;	
//				cnt1=0;
//				//return;
//				//firstTime = true;
//			}
//		}
//		else 
//			if(keyPressed=='r'&&firstTime){
//				temp = isWalkabel('r');
//				walking = temp;
//				firstTime = false;
//				if(!temp){
//					keyPressed=-1;
//					walking = false;	
//					cnt1=0;
//					//return;
//					//firstTime = true;
//				}
//			}

		//if(walking)
		if(keyPressed=='l'&&walking&&temp){
			walking = true;
			cnt1+=2;
			xa+=2;
			if(cnt_step<=1)
				sprite_status=4;
			if(cnt_step>=10&&cnt_step<20){
				sprite_status=5;
			}
			else if(cnt_step>=20){
				sprite_status=6;
				cnt_step=1;

			}
			if(cnt1%32==0){
				lastChecked = -1;
			}
			if(cnt1%32==0&&!leftKey){
				cnt1=0;
				keyPressed=-1;
				walking = false;
				//firstTime = true;
			}
		}
		else if(keyPressed=='r'&&walking&&temp){
			walking = true;
			cnt1+=2;
			xa-=2;
			if(cnt_step<=1)
				sprite_status=7;
			if(cnt_step>=10&&cnt_step<20){
				sprite_status=8;
			}
			else if(cnt_step>=20){
				sprite_status=9;
				cnt_step=1;

			}
			if(cnt1%32==0&&!rightKey){
				cnt1=0;
				keyPressed=-1;
				walking = false;
			}
		}
		else if(keyPressed=='u'&&walking&&isWalkabel('u')){
			walking = true;
			cnt1+=2;
			ya+=2;
			if(cnt_step<=1)
				sprite_status=10;
			if(cnt_step>=10&&cnt_step<20){
				sprite_status=11;
			}
			else if(cnt_step>=20){
				sprite_status=12;
				cnt_step=1;
			}
			if(cnt1%32==0&&!upKey){
				cnt1=0;
				keyPressed=-1;
				walking = false;
			}
		}
		else if(keyPressed=='d'&&walking&&isWalkabel('d')){
			walking = true;
			cnt1+=2;
			ya-=2;
			if(cnt_step<=1)
				sprite_status=1;
			if(cnt_step>=10&&cnt_step<20){
				sprite_status=2;
			}
			else if(cnt_step>=20){
				sprite_status=3;
				cnt_step=1;
			}
			if(cnt1%32==0&&!downKey){
				cnt1=0;
				keyPressed=-1;
				walking = false;
			}
		}

		dl[0].setText("x: "+getLocationX());
		dl[1].setText("y: "+getLocationY());
		dl[2].setText("walking: "+walking);
		dl[3].setText("keyPressed: "+(char)keyPressed);

	}
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}

		//screen.clear();
		//screen.render();

		for(int i=0; i<pixels.length; i++){
			pixels[i] =  0xffffff;
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width*scale, height*scale, null);
		//System.out.println(insets);

		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				g.drawImage(Tiles.getCombinedTile(i, j), xa+0+j*32, ya+0+i*32,32,32,null);// layers 0-3
			}
		}

		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				int a = num[4][i][j]/100, b = num[4][i][j]%100;
				g.drawImage(Tiles.getImage(a, b), xa+0+j*32, ya+0+i*32,32,32,null);// layers 4
			}
		}
		g.drawImage(sprite[0].getImage(sprite_status), 376,250,48,64,null);
		//System.out.println(xa+" "+ya);  //coordinate



		//g.setColor(Color.black);
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.dispose();
		bs.show();
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getExtendedKeyCode();
		pressed = true;
		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftKey=true;
		}
		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
			rightKey=true;

		}
		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
			downKey=true;

		}
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
			upKey=true;

		}
		dl[3].setText("keyPressed: "+(char)keyPressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getExtendedKeyCode();
		pressed = false;
		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftKey=false;
			leftMove=false;
			cnt_step=4;
			//keyPressed='l';
		}
		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
			rightKey=false;
			rightMove=false;
			cnt_step=7;
			//keyPressed='r';
		}
		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
			downKey=false;
			upMove=false;
			cnt_step=10;
			//keyPressed='d';
		}
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
			upKey=false;
			downMove=false;
			cnt_step=1;
			//keyPressed='u';
		}
		//repaint();

	}
	public static int readLines() throws IOException{
		int cnt =0;
		if (maptxt[0].exists()){

			Scanner sc = new Scanner(maptxt[0]);
			ArrayList <String> line = new ArrayList <String>();
			while(sc.hasNextLine()){
				line.add(sc.nextLine());
				cnt++;
			}
			sc.close();

		}
		return cnt;
	}

	public void readMap() throws FileNotFoundException{

		for(int p=0; p<6; p++){
			if (maptxt[p].exists()){
				Scanner sc = new Scanner(maptxt[p]);
				ArrayList <String> line = new ArrayList <String>();
				int cnt=0;
				while(sc.hasNextLine()){
					line.add(sc.nextLine());
					cnt++;
				}

				sc.close();
				String m[][] = new String[cnt][cnt];
				for(int i=0; i<cnt; i++){
					m[i]=line.get(i).split(",");
				}
				for(int i=0; i<cnt; i++){
					for(int j=0; j<cnt; j++){

						num[p][i][j] = Integer.parseInt(m[i][j]);
					}
				}
				sc.close();
			}
			else{
				System.out.println("Map doesn't exist. Please check the src folder.");
				JOptionPane.showMessageDialog( null, "Map doesn't exist. Please check the src folder. This program will exit.", "ERROR", JOptionPane.ERROR_MESSAGE );
				System.exit(0);
			}
		}
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				Tiles.combineTiles(i, j, num[0][i][j], num[1][i][j], num[2][i][j], num[3][i][j]);
			}
		}
	}


	public boolean isWalkabel(char n){//l left, r right, u up, d down
		//System.out.println(n);
		boolean res = false;

		//if(walking)
		//	return true;
		if(lastChecked==n)
			return true;
		switch(n){
		case 'l':
			//System.out.print(getLocationY()+" "+(getLocationX()-1)+" "+(num[5][getLocationY()][getLocationX()-1]));
			if(num[5][getLocationY()][getLocationX()-1]==351){
				System.out.println(" "+true);
				res = true;
			}

			break;	
		case 'r':
			//System.out.println(getLocationY()+" "+(getLocationX()+1)+" "+(num[5][getLocationY()][getLocationX()+1]));
			if(num[5][getLocationY()][getLocationX()+1]==351)
				res = true;
			break;
		case 'u':
			//System.out.println("*"+getLocationY());
			//System.out.print(getLocationY()-1+" "+(getLocationX())+" "+(num[5][getLocationY()-1][getLocationX()]));
			if(num[5][getLocationY()-1][getLocationX()]==351)
				res = true;
			break;
		case 'd':
			if(num[5][getLocationY()+1][getLocationX()]==351)
				res = true;
			break;

		}
		System.out.println(" "+res);
		lastChecked = n;
		return res;


	}

	public int getLocationX(){
		//System.out.println(-xa/32+12);
		return -xa/32 +12;
	}
	public int getLocationY(){
		//System.out.println(-ya/32+9);
		return -ya/32+9;
	}


	public static void main(String args[]){
		Final_V0_01 lvds = new Final_V0_01("BETA");  
		//Insets insets = lvds.frame.getInsets();
		//lvds.frame.setSize(800 + insets.bottom+insets.top ,640+insets.left+insets.right );     //25x20 tiles
		//lvds.frame.setResizable( false );
		lvds.c.add(lvds);
		lvds.frame.pack();
		lvds.frame.setVisible(true);                
		lvds.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
		lvds.start();


	}


	//public Rectangle getRect(){
	//    return new Rectangle((int)x,  (int)y,  (int)(2*radius),  (int)(2*radius));
	//}


}