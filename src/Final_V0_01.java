//name: Lester Lyu
//teacher: Ms.Strelkovska
//class: ICS3U-03
//time: February 25, 2014
//Slot Machine 
//Double Buffered code is from web
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

class Final_V0_01 extends JFrame implements ActionListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int lines = 0;
	BufferedImage bufferedimage;
	ImageIcon n1;
	Image mImage;
	int xa=0,ya=-384,cnt_step,sprite_status=1;
	int totalFrameCount = 0, fps=0;
	static File maptxt[] = new File[6];
	public boolean upKey=false,leftKey=false,rightKey=false,downKey=false, walking=false;
	private static Insets insets;
	static int read_width = 32, read_height = 32, read_row = 115, read_col = 54, step = 0;
	Sprites sprite[]=new Sprites[12];
	int num[][][], lastKey = -1;//0 left, 1 right, 2 up, 3 down
	Timer timer1 = new Timer(13,this);

	public Final_V0_01(String title)
	{
		super(title);
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
		Container c = getContentPane();
		//c.setBackground(Color.black);
		c.setLayout(null);





		timer1.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getExtendedKeyCode();

		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftKey=true;
		}
		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D)
			rightKey=true;
		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S)
			downKey=true;
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W)
			upKey=true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getExtendedKeyCode();
		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftKey=false;
			cnt_step=4;
			lastKey=0;
		}
		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
			rightKey=false;
			cnt_step=7;
			lastKey=1;
		}
		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
			downKey=false;
			cnt_step=10;
			lastKey=3;
		}
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
			upKey=false;
			cnt_step=1;
			lastKey=2;
		}
		repaint();

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
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==timer1){
			getLocationX();
			getLocationY();
			totalFrameCount++;
			cnt_step++;
			if(leftKey){

				if(xa<=-854)
					xa=-852;
				if(xa<384)
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
			}

			else if(rightKey==true){
				if(xa>=383)
					xa=382;
				if(xa>-854)
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
			}

			else if(upKey==true){
				if(ya<=-964)
					ya=-963;
				if(ya<258)
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
			}
			else if(downKey==true){
				if(ya>=258)
					ya=259;
				if(ya>-964)
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

			}
			else if(!leftKey&&!rightKey&&!upKey&&!downKey&&lastKey==0){
				if(xa%32!=0){
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
				}
			}
			else if(!leftKey&&!rightKey&&!upKey&&!downKey&&lastKey==1){
				if(xa%32!=0){
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
				}
			}
			else if(!leftKey&&!rightKey&&!upKey&&!downKey&&lastKey==2){
				if(ya%32!=0){
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
				}
			}
			else if(!leftKey&&!rightKey&&!upKey&&!downKey&&lastKey==3){
				if(ya%32!=0){
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
				}
			}

			repaint();
		}
	}	
	public int getLocationX(){
		System.out.println(-xa/32+12);
		return -xa/32 +12;
	}
	public int getLocationY(){
		System.out.println(-ya/32+9);
		return -ya/32;
	}

	public boolean isWalkabel(){
		
		return false;

	}
	public void paintOffscreen(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g);	
		if(insets==null)
			insets = this.getInsets();
		//System.out.println(insets);

		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				g.drawImage(Tiles.getCombinedTile(i, j), xa+0+j*32+insets.left, ya+0+i*32+insets.top,32,32,null);// layers 0-3
			}
		}

		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				int a = num[4][i][j]/100, b = num[4][i][j]%100;
				g.drawImage(Tiles.getImage(a, b), xa+0+j*32+insets.left, ya+0+i*32+insets.top,32,32,null);// layers 4
			}
		}
		g2.drawImage(sprite[0].getImage(sprite_status), 376+insets.left,250+insets.top,48,64,null);
		//System.out.println(xa+" "+ya);  //coordinate


		g2.drawString("FPS:"+fps, 5+insets.left, 14+insets.top);
	}

	public void paint(Graphics g) {
		// Clear the offscreen image.
		Dimension d = getSize();
		checkOffscreenImage();
		Graphics offG = mImage.getGraphics();
		offG.setColor(getBackground());
		offG.fillRect(0, 0, d.width, d.height);
		// Draw into the offscreen image.
		paintOffscreen(mImage.getGraphics());
		// Put the offscreen image on the screen.
		g.drawImage(mImage, 0, 0, null);
	}

	private void checkOffscreenImage() {
		Dimension d = getSize();
		if (mImage == null || mImage.getWidth(null) != d.width || mImage.getHeight(null) != d.height) 
		{
			mImage = createImage(d.width, d.height);
		}
	}

	public static void main(String args[]){
		Final_V0_01 lvds = new Final_V0_01("BETA");  
		Insets insets = lvds.getInsets();
		lvds.setSize(800 + insets.bottom+insets.top ,640+insets.left+insets.right );     //25x20 tiles
		lvds.setVisible(true);                
		lvds.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
		//lvds.setResizable( false );

	}

	//public Rectangle getRect(){
	//    return new Rectangle((int)x,  (int)y,  (int)(2*radius),  (int)(2*radius));
	//}


}