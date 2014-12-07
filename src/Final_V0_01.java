//name: Lester Lyu
//teacher: Ms.Strelkovska
//class: ICS3U-03
//time: February 25, 2014
//Slot Machine 
//Double Buffered code is from web

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

class Final_V0_01 extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static int lines = 0;
	int xa=0,ya=-384;

	static int cnt_step;

	int sprite_status=1;

	int cnt1 = 0;

	int lastChecked = -1;
	int totalFrameCount = 0, fps=0;
	static File maptxt[] = new File[6];
	public boolean leftMove=false, rightMove=false, upMove=false, downMove=false, walking=false, pressed=false;
	//private static Insets insets;
	static int read_width = 32, read_height = 32, read_row = 115, read_col = 54, step = 0;
	Sprites sprite[]=new Sprites[12];
	int num[][][], keyPressed = -1;
	JFrame frame;
	public static int width = 250, height = width/4*3, scale = 3;
	private Thread thread;
	public boolean running = false, firstTime = true;
	public Container c;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	KeyBoard k;
	//public Screen screen;
	boolean temp = true, showConversation = false, showMenu = false;;
	String text_con = "";
	int text_cnt = 0, menu_p = 0;
	boolean zFirst = true, enterFirst = true,upFirst = true, downFirst = true;;
	//debug only
	JFrame debugFrame = new JFrame();
	JLabel dl[] = new JLabel[10];

	public Final_V0_01(String title){

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
		frame.setTitle(title);
		c = frame.getContentPane();

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

		try {
			readMap();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


		for(int i=0; i<12; i++)
			sprite[i]= new Sprites();

		k = new KeyBoard();
		this.addKeyListener(k);
		frame.addKeyListener(k);

		Container c1 = debugFrame.getContentPane();
		c1.setLayout(new FlowLayout());
		for(int i=0; i<6; i++){
			dl[i] = new JLabel("       ");
			c1.add(dl[i]);
		}

		debugFrame.setSize(100,150);
		debugFrame.setLocationRelativeTo(null);
		debugFrame.setVisible(true);

		//
		StartCon("Welcome to the Pokemon World!\nPress \"Z\" to contine.");
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
			while(delta >= 1){ //call 60 times per second
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

		if(!showMenu&&!showConversation){//Check Keys
			if(k.leftKey){
				if(keyPressed!='u'&&keyPressed!='d'&&keyPressed!='r')
					keyPressed='l';
			}
			else if(k.rightKey){
				if(keyPressed!='u'&&keyPressed!='d'&&keyPressed!='l')
					keyPressed='r';
			}
			else if(k.upKey){
				if(keyPressed!='l'&&keyPressed!='r'&&keyPressed!='d')
					keyPressed='u';
			}
			else if(k.downKey){
				if(keyPressed!='l'&&keyPressed!='r'&&keyPressed!='u')
					keyPressed='d';
			}
		}//end keys
		else if(showMenu){
			if(k.upKey){
				if(upFirst){
					upFirst = false;
					if(menu_p>0)
						menu_p --;
				}

			}
			else if(!k.upKey){
				upFirst = true;
			}

			if(k.downKey){
				if(downFirst){
					downFirst = false;
					if(menu_p<4)
						menu_p ++;
				}

			}
			else if(!k.downKey){
				downFirst = true;
			}
			if(k.zKey){//menu
				switch(menu_p){
				case 0:
					
					break;
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;

				case 4:
					System.exit(0);
					break;
				}
			}
			if(k.xKey)
				showMenu=false;
		}
		else{
			//other keys
			if(k.zKey){
				if(zFirst){
					zFirst = false;

					switch(text_cnt){
					case 0:
						StartCon("It's like a normal Pokemon game.");
						break;
					case 1:
						StartCon("You can press \"Enter\" to open the menu.");
						break;
					case 2:
						StartCon("Let's explore the world!");
						break;
					case 3:
						showConversation = false;
						break;
					}
					text_cnt++;
				}
			}
			else{
				zFirst=true;
			}
		}
		if(k.enterKey){
			if(enterFirst){
				enterFirst = false;
				if(showMenu)
					showMenu = false;
				else
					showMenu = true;
			}
		}
		else{
			enterFirst=true;
		}
		//End other keys


		//moving
		if(keyPressed=='l'){
			temp = isWalkabel('l');
			walking = temp;
			if(!temp){
				keyPressed=-1;
				sprite_status=4;
				walking = false;	
			}
			else walking = true;
		}
		else if(keyPressed=='r'){
			temp = isWalkabel('r');
			walking = temp;
			if(!temp){
				keyPressed=-1;
				sprite_status=7;
				walking = false;	
			}
			else walking = true;
		}
		else if(keyPressed=='u'){
			temp = isWalkabel('u');
			walking = temp;
			if(!temp){
				keyPressed=-1;
				sprite_status=10;
				walking = false;	
			}
			else walking = true;
		}
		else if(keyPressed=='d'){
			temp = isWalkabel('d');
			walking = temp;
			if(!temp){
				keyPressed=-1;
				sprite_status=1;
				walking = false;	
			}
			else walking = true;
		}

		if(walking){
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
				if(cnt1%32==0&&!k.leftKey){
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
				if(cnt1%32==0)
					lastChecked = -1;
				if(cnt1%32==0&&!k.rightKey){
					cnt1=0;
					keyPressed=-1;
					walking = false;
				}
			}
			else if(keyPressed=='u'&&walking&&temp){
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
				if(cnt1%32==0)
					lastChecked = -1;
				if(cnt1%32==0&&!k.upKey){
					cnt1=0;
					keyPressed=-1;
					walking = false;
				}
			}
			else if(keyPressed=='d'&&walking&&temp){
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
				if(cnt1%32==0)
					lastChecked = -1;
				if(cnt1%32==0&&!k.downKey){
					cnt1=0;
					keyPressed=-1;
					walking = false;
				}
			}
		}


		dl[0].setText("x: "+getLocationX());
		dl[1].setText("y: "+getLocationY());
		dl[2].setText("walking: "+walking);
		dl[3].setText("keyPressed: "+(char)keyPressed);
		dl[4].setText("xa= "+xa+" ya= "+ya);
		dl[5].setText("xa/= "+xa/32.0+" ya/= "+ya/32.0);

	}
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3); //triple buffering 
			return;
		}


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
		g.drawImage(sprite[0].getImage(sprite_status), 376,250,48,64,null);
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				int a = num[4][i][j]/100, b = num[4][i][j]%100;
				g.drawImage(Tiles.getImage(a, b), xa+0+j*32, ya+0+i*32,32,32,null);// layers 4
			}
		}
		Graphics2D g2d = (Graphics2D)g;
		//System.out.println(xa+" "+ya);  //coordinate
		if(showConversation){

			g2d.setStroke(new BasicStroke(10));
			g2d.setColor(new Color(0,248,152));
			//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 9 * 0.1f)); //slow
			g2d.drawRoundRect(0+15, height*scale*3/4-5, width*scale-30, height*scale/4, 30, 30);
			//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 10 * 0.1f));
			g2d.setColor(new Color(0,200,184));
			g2d.setStroke(new BasicStroke(1));
			//	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 9 * 0.1f));
			g2d.fillRoundRect(0+15, height*scale*3/4-5, width*scale-30, height*scale/4, 30, 30);
			g2d.setColor(new Color(248,248,248));
			//	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 9 * 0.1f));
			g2d.fillRoundRect(0+40, height*scale*3/4, width*scale-30-50, height*scale/4-10, 30, 30);
			//text_con = "aaaa";
			g2d.setColor(Color.black);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			drawString(g2d, text_con, 0+50, height*scale*3/4+0);

		}
		if(!showConversation&&showMenu){
			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(new Color(86,83,113));
			g2d.drawRoundRect(width*scale-120-4, 100-4, 100+8, height*scale-200+8, 5, 5);
			g2d.setStroke(new BasicStroke(6));
			g2d.setColor(new Color(107,100,125));
			g2d.drawRoundRect(width*scale-120-1, 100-1, 100+2, height*scale-200+2, 5, 5);
			g2d.setColor(new Color(248,248,248));
			g2d.setStroke(new BasicStroke(2));
			g2d.fillRoundRect(width*scale-120, 100, 100, height*scale-200, 5, 5);
			g2d.setColor(new Color(107,100,125));
			g2d.fillOval(width*scale-120+10, 122+menu_p*30, 10, 10);
			g2d.setFont(new Font("", Font.PLAIN, 20)); 
			g2d.drawString("¹ÖÊÞ", width*scale-120+20, 135);
			g2d.drawString("Bag", width*scale-120+20, 135+30);
			g2d.drawString("Money", width*scale-120+20, 135+60);
			g2d.drawString("Save", width*scale-120+20, 135+90);
			g2d.drawString("Exit", width*scale-120+20, 135+120);
		}
		g2d.dispose();
		g.dispose();
		bs.show();
	}

	public void StartCon(String s){
		showConversation = true;
		text_con = s;
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
		boolean res = false;

		if(lastChecked==n&&walking)
			return true;
		switch(n){
		case 'l':
			if(num[5][getLocationY()][getLocationX()-1]==351)
				res = true;
			break;	
		case 'r':
			if(num[5][getLocationY()][getLocationX()+1]==351)
				res = true;
			break;
		case 'u':
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
	public void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
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
		Final_V0_01 game = new Final_V0_01("BETA");  
		game.c.add(game);
		game.frame.pack();
		game.frame.setVisible(true);                
		game.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
		game.start();

	}


	//public Rectangle getRect(){
	//    return new Rectangle((int)x,  (int)y,  (int)(2*radius),  (int)(2*radius));
	//}


}