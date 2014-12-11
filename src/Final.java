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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

class Final extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static int lines = 0;
	static int xa=0, ya=-384;

	static int cnt_step;

	static int sprite_status=1;

	int cnt1 = 0;

	int lastChecked = -1;
	int totalFrameCount = 0, fps=0;
	//static File maptxt[] = new File[6];
	BufferedReader maptxt[] = new BufferedReader[6];
	public boolean leftMove=false, rightMove=false, upMove=false, downMove=false, walking=false, pressed=false;
	//private static Insets insets;
	static int read_width = 32, read_height = 32, read_row = 115, read_col = 70, step = 0;
	static Sprites sprite[]=new Sprites[12];
	int num[][][], keyPressed = -1;
	static JFrame frame;
	public static int width = 250, height = width/4*3, scale = 3;
	private Thread thread;
	public boolean running = false, firstTime = true;
	public Container c;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	static KeyBoard k;
	//public Screen screen;
	boolean temp = true, showConversation = false, showMenu = false;;
	String text_con = "";
	int text_cnt = 0, menu_p = 0;
	static boolean zFirst = true, enterFirst = true,upFirst = true, downFirst = true, zFirst2 = true,inBuilding=false,inPokeFrame=false;
	Tiles tiles;
	Buildings home = new Buildings("Home");

	//Dennis
	Battle battle;
	//debug only
	JFrame debugFrame = new JFrame();
	JLabel dl[] = new JLabel[10];


	public Final(String title){

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
		frame.setTitle(title);
		c = frame.getContentPane();

		//
		for(int i=0; i<6; i++){
			try{
				maptxt[i]=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("map/Map"+i+".txt")));
			} catch(NullPointerException e){
				System.out.println("Map doesn't exist. Please check the src folder.");
				JOptionPane.showMessageDialog( null, "Map doesn't exist. Please check the src folder. This program will exit.", "ERROR", JOptionPane.ERROR_MESSAGE );
				System.exit(0);
			}

		}
		try {
			lines = readLines();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		tiles = new Tiles(lines);

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
			dl[i] = new JLabel("¡ú.¡ú       ");
			c1.add(dl[i]);
		}

		debugFrame.setSize(100,150);
		debugFrame.setLocationRelativeTo(null);
		debugFrame.setVisible(true);

		//
		StartCon("Welcome to the Pokemon World!\nPress \"Z\" to contine.");

		//
		battle = new Battle();
		battle.p.addKeyListener(k);
		battle.j.addKeyListener(k);
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
				if(zFirst){
					zFirst = false;
					switch(menu_p){
					case 0:
						battle.toPokeFrame();
						inPokeFrame=true;
						//k.zKey=false;
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
			}
			else if(!zFirst)
				zFirst = true;

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
				if(showMenu&&!showConversation)
					showMenu = false;
				else if(!showMenu&&!showConversation)
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
				//keyPressed=-1;
				sprite_status=4;
				walking = false;	
			}
			else walking = true;
		}
		else if(keyPressed=='r'){
			temp = isWalkabel('r');
			walking = temp;
			if(!temp){
				//keyPressed=-1;
				sprite_status=7;
				walking = false;	
			}
			else walking = true;
		}
		else if(keyPressed=='u'){
			temp = isWalkabel('u');
			walking = temp;
			if(!temp){
				//keyPressed=-1;
				sprite_status=10;
				walking = false;	
			}
			else walking = true;
		}
		else if(keyPressed=='d'){
			temp = isWalkabel('d');
			walking = temp;
			if(!temp){
				//keyPressed=-1;
				sprite_status=1;
				walking = false;	
			}
			else walking = true;
		}

		if(walking){
			cnt_step++;
			//System.out.println("Walking");
			if(keyPressed=='l'&&temp){
				walking = true;
				cnt1+=2;
				if(!inBuilding)
					xa+=2;
				else
					home.xa+=2;
				if(cnt_step<=1)
					sprite_status=4;
				if(cnt_step>=10&&cnt_step<20){
					sprite_status=5;
				}
				if(cnt_step>=20){
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
			else if(keyPressed=='r'&&temp){
				walking = true;
				cnt1+=2;
				if(!inBuilding)
					xa-=2;
				else
					home.xa-=2;
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
			else if(keyPressed=='u'&&temp){
				walking = true;
				cnt1+=2;
				if(!inBuilding)
					ya+=2;
				else
					home.ya+=2;
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
			else if(keyPressed=='d'&&temp){
				walking = true;
				cnt1+=2;
				if(!inBuilding)
					ya-=2;
				else
					home.ya-=2;
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
		else{

			if(sprite_status==5||sprite_status==6)
				sprite_status=4;
			if(sprite_status==2||sprite_status==3)
				sprite_status=1;
			if(sprite_status==8||sprite_status==9)
				sprite_status=7;
			if(sprite_status==12||sprite_status==11)
				sprite_status=10;
			//System.out.println(nextLocationX()+" "+nextLocationY());
			if(!inBuilding){
				if(nextLocationX()==10&&nextLocationY()==16){
					//num[3][17][10] = 2116;

					//tiles.combineTiles(17, 10, num[0][17][10], num[1][17][10], num[2][17][10], num[3][17][10]);
					//num[5][17][10] = 351;
					inBuilding=true;

				}
				else if(nextLocationX()==14&&nextLocationY()==17&&(k.upKey||k.zKey)){
					if(zFirst2){
						if(!showConversation)
							StartCon("Your friend's home.\nPress \"Z\" to contine.");
						else if(!k.upKey)
							showConversation=false;
						zFirst2=false;
					}
				}
				else if(nextLocationX()==14&&nextLocationY()==27&&(k.upKey||k.zKey)){
					if(zFirst2){
						if(!showConversation)
							StartCon("Your home.\nPress \"Z\" to contine.");
						else if(!k.upKey)
							showConversation=false;
						zFirst2=false;
					}
				}
				else if(!k.zKey) 
					zFirst2=true;

			}
			if(inBuilding){
				System.out.println(nextLocationX()+" "+nextLocationY());
				if(nextLocationX()==4&&nextLocationY()==14&&keyPressed=='d'){
					System.out.println("out");
					//num[3][17][10] = 2116;

					//tiles.combineTiles(17, 10, num[0][17][10], num[1][17][10], num[2][17][10], num[3][17][10]);
					//num[5][17][10] = 351;
					inBuilding=false;

				}
			}
			keyPressed=-1; //reset

		}
		// End walking





		dl[0].setText("x: "+getLocationX());
		dl[1].setText("y: "+getLocationY());
		dl[2].setText("walking: "+walking);
		dl[3].setText("keyPressed: "+(char)keyPressed);
		dl[4].setText("xa= "+xa+" ya= "+ya);
		dl[5].setText("xa/= "+xa/32.0+" ya/= "+ya/32.0);


		//Dennis
		if(inPokeFrame){
			if(k.xKey){
				battle.p.bb.doClick();
			}
		}

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
		if(!inBuilding){
			g.drawImage(image, 0, 0, width*scale, height*scale, null);
			//System.out.println(insets);

			for(int i=getLocationY()-12; i<getLocationY()+13; i++){
				for(int j=getLocationX()-12; j<getLocationX()+13; j++){
					if(i>=0&&j>=0)
						g.drawImage(tiles.getCombinedTile(i, j), xa+0+j*32, ya+0+i*32,32,32,null);// layers 0-3
				}
			}
			g.drawImage(sprite[0].getImage(sprite_status), 384,279,32,48,null);
			for(int i=getLocationY()-12; i<getLocationY()+13; i++){
				for(int j=getLocationX()-12; j<getLocationX()+13; j++){
					if(i>=0&&j>=0){
						int a = num[4][i][j]/100, b = num[4][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), xa+0+j*32, ya+0+i*32,32,32,null);// layers 4
					}
				}
			}
		}
		else{
			home.render(g);
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
			g2d.drawString("Poke", width*scale-120+20, 135);
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
	public int readLines() throws IOException{
		int cnt =0;
		String text;
		ArrayList <String> line = new ArrayList <String>();
		while((text=maptxt[0].readLine())!=null){
			line.add(text);
			cnt++;
		}
		System.out.println("lines:"+cnt);
		maptxt[0]=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("map/Map0.txt")));

		return cnt;
	}

	public void readMap() throws FileNotFoundException{
		String text;
		for(int p=0; p<6; p++){
			ArrayList <String> line = new ArrayList <String>();
			int cnt=0;
			try {
				while((text=maptxt[p].readLine())!=null){
					line.add(text);
					cnt++;
				}
			} catch (IOException e) {}
			String m[][] = new String[cnt][cnt];
			for(int i=0; i<cnt; i++){
				m[i]=line.get(i).split(",");
			}
			for(int i=0; i<cnt; i++){
				for(int j=0; j<cnt; j++){

					num[p][i][j] = Integer.parseInt(m[i][j]);
				}
			}
		}
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				tiles.combineTiles(i, j, num[0][i][j], num[1][i][j], num[2][i][j], num[3][i][j]);
			}
		}
	}


	public boolean isWalkabel(char n){//l left, r right, u up, d down
		boolean res = false;


		if(lastChecked==n&&walking)
			return true;
		if(!inBuilding){
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
		}
		else{
			switch(n){
			case 'l':
				if(home.num[5][getLocationY()][getLocationX()-1]==351)
					res = true;
				break;	
			case 'r':
				if(home.num[5][getLocationY()][getLocationX()+1]==351)
					res = true;
				break;
			case 'u':
				if(home.num[5][getLocationY()-1][getLocationX()]==351)
					res = true;
				break;
			case 'd':
				if(home.num[5][getLocationY()+1][getLocationX()]==351)
					res = true;
				break;

			}
		}
		//System.out.println(" "+res);
		lastChecked = n;
		return res;

	}
	public int nextLocationX(){
		int res = 0;
		if(sprite_status==4||sprite_status==5||sprite_status==6){ //l
			res = getLocationX()-1;
		}
		else if(sprite_status==7||sprite_status==8||sprite_status==9){ //r
			res = getLocationX()+1;
		}
		else if(sprite_status==10||sprite_status==11||sprite_status==12){ //u
			res = getLocationX();
		}
		else if(sprite_status==1||sprite_status==2||sprite_status==3){ //d
			res = getLocationX();
		}

		return res;
	}
	public int nextLocationY(){
		int res = 0;
		if(sprite_status==4||sprite_status==5||sprite_status==6){ //l
			res = getLocationY();
		}
		else if(sprite_status==7||sprite_status==8||sprite_status==9){ //r
			res = getLocationY();
		}
		else if(sprite_status==10||sprite_status==11||sprite_status==12){ //u
			res = getLocationY()-1;
		}
		else if(sprite_status==1||sprite_status==2||sprite_status==3){ //d
			res = getLocationY()+1;
		}

		return res;
	}
	public void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	public int getLocationX(){
		//System.out.println(-xa/32+12);
		if(!inBuilding)
			return -xa/32 +12;
		else
			return -home.xa/32 +12;
	}
	public int getLocationY(){
		//System.out.println(-ya/32+9);
		if(!inBuilding)
			return -ya/32+9;
		else
			return -home.ya/32+9;
	}


	public static void main(String args[]){
		Final game = new Final("BETA");  
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