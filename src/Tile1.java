
public class Tile1 {

	private final int SIZE;
	private int x, y;
	public int[] pixels;
	private TileSets sheet;
	
	public static Tile1 grass = new Tile1(32,0,0,TileSets.tiles); 
	
	public Tile1(int size, int x, int y, TileSets sheet){
		SIZE = size;
		pixels = new int[size*size];
		this.x = x*size;
		this.y = y*size;
		this.sheet = sheet;
		load();
	}
	private void load(){
		for(int y=0; y<SIZE; y++){
			for(int x=0; x<SIZE; x++){
				pixels[x+y*SIZE] = sheet.pixels[(x+this.x)+(y+this.y)*sheet.SIZEX];
			}
		}
	}

}
