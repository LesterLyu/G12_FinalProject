
public class Screen {

	/**
	 * @param args
	 */
	private int width, height, lines;
	public int pixels[];
	public int[][] tiles;
	
	public Screen(int width, int height, int lines){
		this.width = width;
		this.height = height;
		this.lines = lines;
		tiles = new int[lines][lines];
		pixels = new int[width*height];
		
	}

	public void render(){
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				pixels[x+y*width] = Tile1.grass.pixels[(x&31)+(y&31)*32];
			}
		}
		
	}
	public void clear(){
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				pixels[x+y*width] = 0xffffff;
			}
		}
	}
}
