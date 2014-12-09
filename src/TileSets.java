import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TileSets {

	private String path;
	public final int SIZEX, SIZEY;
	public int[] pixels;
	
	public static TileSets tiles = new TileSets("f96651.png",1728, 3682);
	
	public TileSets(String path, int sizeX, int sizeY){
		this.path = path;
		this.SIZEX = sizeX;
		this.SIZEY = sizeY;
		pixels = new int [SIZEX*SIZEY];
		load();
	}
	
	private void load(){
		try {
			BufferedImage image = ImageIO.read(TileSets.class.getResource(path));
			int w = image.getWidth(), h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);//translate image to pixels
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
