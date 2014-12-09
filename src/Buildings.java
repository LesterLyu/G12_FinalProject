import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Buildings {

	int num[][][], xa, ya;
	int read_width = 32, read_height = 32;
	static int lines;
	File maptxt[] = new File[6];
	public static int width = 250, height = width/4*3, scale = 3;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private Tiles tiles;
	public Buildings(String mapName){
		for(int i=0; i<6; i++){
			maptxt[i] = new File("src/map/"+mapName+i+".txt");
			System.out.println(maptxt[i]);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		xa=256;
		ya=-128;

	}
	public void render(Graphics g){
		for(int i=0; i<pixels.length; i++){
			pixels[i] =  0x000000;
		}
		g.drawImage(image, 0, 0, width*scale, height*scale, null);
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				g.drawImage(tiles.getCombinedTile(i, j), xa+0+j*32, ya+0+i*32,32,32,null);// layers 0-3
			}
		}
		g.drawImage(Final_V0_01.sprite[0].getImage(Final_V0_01.sprite_status), 384,279,32,48,null);
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				int a = num[4][i][j]/100, b = num[4][i][j]%100;
				g.drawImage(tiles.getImage(a, b), xa+0+j*32, ya+0+i*32,32,32,null);// layers 4
			}
		}
	}
	
	public int readLines() throws IOException{
		int cnt =0;
		if (maptxt[0].exists()){
			System.out.println("Map existed");
			Scanner sc = new Scanner(maptxt[0]);
			ArrayList <String> line = new ArrayList <String>();
			while(sc.hasNextLine()){
				line.add(sc.nextLine());
				cnt++;
			}
			sc.close();

		}
		System.out.println("Lines: "+cnt);
		return cnt;
	}
	public void readMap() throws IOException{
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
		}
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				tiles.combineTiles(i, j, num[0][i][j], num[1][i][j], num[2][i][j], num[3][i][j]);
			}
		}
	}

}
