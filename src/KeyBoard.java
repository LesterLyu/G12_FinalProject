import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyBoard implements KeyListener {
	
	
	public boolean pressed = false;
	public boolean upKey=false,leftKey=false,rightKey=false,downKey=false,zKey=false;
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
		if(key==KeyEvent.VK_Z){
			zKey=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getExtendedKeyCode();
		pressed = false;
		if(key==KeyEvent.VK_LEFT||key==KeyEvent.VK_A){
			leftKey=false;
			Final_V0_01.cnt_step=4;
		}
		if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_D){
			rightKey=false;
			Final_V0_01.cnt_step=7;
		}
		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_S){
			downKey=false;
			Final_V0_01.cnt_step=10;
		}
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_W){
			upKey=false;
			Final_V0_01.cnt_step=1;
		}
		if(key==KeyEvent.VK_Z){
			zKey=false;
		}
	}
}