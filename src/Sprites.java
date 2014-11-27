import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Sprites{
	BufferedImage img;
	static ImageIcon spiritsi = new ImageIcon(Sprites.class.getResource("sprites.png"));
	static BufferedImage spirits = ImageIconToBufferedImage(spiritsi);
	Graphics2D g2;
	public Sprites(){
		img = new BufferedImage(48,64,BufferedImage.TYPE_INT_ARGB);
		g2 = img.createGraphics();
	}
	public BufferedImage getImage(int number){
		if(number==1){
			img = spirits.getSubimage(1*48, 0*64, 48, 64);
		}
		else if(number==2){
			img = spirits.getSubimage(0*48, 0*64, 48, 64);
		}
		else if(number==3){
			img = spirits.getSubimage(2*48, 0*64, 48, 64);
		}
		else if(number==4){
			img = spirits.getSubimage(1*48, 1*64, 48, 64);
		}
		else if(number==5){
			img = spirits.getSubimage(0*48, 1*64, 48, 64);
		}
		else if(number==6){
			img = spirits.getSubimage(2*48, 1*64, 48, 64);
		}
		else if(number==7){
			img = spirits.getSubimage(1*48, 2*64, 48, 64);
		}
		else if(number==8){
			img = spirits.getSubimage(0*48, 2*64, 48, 64);
		}
		else if(number==9){
			img = spirits.getSubimage(2*48, 2*64, 48, 64);
		}
		else if(number==10){
			img = spirits.getSubimage(1*48, 3*64, 48, 64);
		}
		else if(number==11){
			img = spirits.getSubimage(0*48, 3*64, 48, 64);
		}
		else if(number==12){
			img = spirits.getSubimage(2*48, 3*64, 48, 64);
		}
		return img;
	}
	public static BufferedImage ImageIconToBufferedImage(ImageIcon imageicon) {
		BufferedImage bufferedimage;
		bufferedimage = new BufferedImage(imageicon.getIconWidth(),imageicon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = bufferedimage.createGraphics();
		imageicon.paintIcon(null, g1, 0, 0);
		g1.dispose();
		return bufferedimage;
	}

}
