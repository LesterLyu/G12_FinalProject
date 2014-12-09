import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Tiles {
	BufferedImage img;
	static ImageIcon basei = new ImageIcon(Tiles.class.getResource("f96651.png"));
	static BufferedImage base = ImageIconToBufferedImage(basei);
	static int read_width = 32, read_height = 32, read_row = 115, read_col = 70;
	static BufferedImage tile[][] = new BufferedImage[read_row][read_col];
	BufferedImage combinedTile[][];
	Graphics2D g;
	
	public Tiles(int mapLines){
		combinedTile = new BufferedImage[mapLines][mapLines];
		if(tile[0][0]==null)
			readPng();
	}
	
	public static BufferedImage getImage(int x, int y){
		return tile[x][y];
	}
	public BufferedImage getCombinedTile(int x, int y){
		return combinedTile[x][y];
	}
	public static void readPng(){
		for(int i=0; i<read_row; i++){
			for(int j=0; j<read_col; j++){
				tile[i][j] = base.getSubimage(j*read_width, i*read_height, read_width, read_height);
			}
		}

	}
	public static BufferedImage ImageIconToBufferedImage(ImageIcon imageicon) {
		BufferedImage bufferedimage;
		bufferedimage = new BufferedImage(imageicon.getIconWidth(),imageicon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = bufferedimage.createGraphics();
		imageicon.paintIcon(null, g1, 0, 0);
		g1.dispose();
		return bufferedimage;
	}
	public void combineTiles(int i, int j, int a, int b, int c, int d){
		int ax = a/100, ay = a%100, bx = b/100, by = b%100, cx = c/100, cy = c%100, dx = d/100, dy = d%100;
		BufferedImage bufferedimage;
		bufferedimage = new BufferedImage(read_width,read_height,BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = bufferedimage.createGraphics();
		g1.drawImage(tile[ax][ay], 0, 0, read_width, read_height,null);
		g1.drawImage(tile[bx][by], 0, 0, read_width, read_height,null);
		g1.drawImage(tile[cx][cy], 0, 0, read_width, read_height,null);
		g1.drawImage(tile[dx][dy], 0, 0, read_width, read_height,null);
		g1.dispose();
		combinedTile[i][j] = bufferedimage;
	}
}
