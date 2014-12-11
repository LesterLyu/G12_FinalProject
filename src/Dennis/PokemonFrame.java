import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PokemonFrame extends JFrame implements ActionListener,ComponentListener{
	JButton b[] = new JButton[6],bb;
	JLabel l1,l2,l3,l4;
	JPanel p1,p2;
	Container c;
	Npc you;
	Dimension d,d1;
	Timer t;
	int num = -1,i,fight = 0,count = 0;
	boolean state = true;
	ImageIcon rect = new ImageIcon(getClass().getResource("Rect.png"));
	
	
	ImageIcon male =new ImageIcon(getClass().getResource("male.png"));
	
	
	ImageIcon ball = new ImageIcon(getClass().getResource("Ball.png"));
	
	
	ImageIcon female = new ImageIcon(getClass().getResource("female.png"));
	
	
	ImageIcon hp = new ImageIcon(getClass().getResource("HP.png"));
	public PokemonFrame(){
		setSize(800,600);
		d = getSize();
		d1 = getSize();
		c = getContentPane();
		addComponentListener(this);
		c.setLayout(new BorderLayout());
		you = Battle.you;
		t = new Timer(100,this);
		generateButton();
		l1 = new JLabel();
		l2 = new JLabel();
		l3 = new JLabel();
		p2 = new JPanel();
		p2.setLayout(new GridLayout(1,4));
		p2.add(l1);
		p2.add(l2);
		p2.add(l3);
		bb = new JButton("Back");
		bb.setFocusable(false);//Lester added
		bb.addActionListener(this);
		p2.add(bb);
		c.add(p1,BorderLayout.CENTER);
		c.add(p2,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e){
		//System.out.println(e.getSource());
		num = -2;
		if (e.getSource() == b[0]){
			num = 0;
		}else if (e.getSource() == b[1]){
			num = 1;
		}else if (e.getSource() == b[2]){
			num = 2;
		}else if (e.getSource() == b[3]){
			num = 3;
		}else if (e.getSource() == b[4]){
			num = 4;
		}else if (e.getSource() == b[5]){
			num = 5;
		}else if (e.getSource() == bb){
			
			num = -1;
		}else if (e.getSource() == t){
			
			count++;
			System.out.println(count);
			if (count%2 != 0){
				state = false;
			} else {
				state = true;
				count = 0;
			}
			repaint();
		}
		if (Battle.frameNum == 1 && num!=-2){
			if (num != -1){
				Battle.j.p[1] = Battle.you.po[num];
				MyPanel.health[1] = MyFrame.p[1].health/(double)MyFrame.p[1].stamina;
				Battle.j.AI(true,true);
			}
			for (int i=0;i<you.pokeNum;i++){
				c.remove(b[i]);
			}
			c.remove(p2);
			c.remove(p1);
			generateButton();
			c.add(p1,BorderLayout.CENTER);
			c.add(p2,BorderLayout.SOUTH);
			setVisible(false);
			Battle.j.setVisible(true);
			t.stop();
		}
		else if (Battle.frameNum == 2 && num!=-2){
			//Lester changed
			if (num == -1){ //=2 before
				setVisible(false);
				t.stop();
				Final.inPokeFrame=false;
				Final.frame.setVisible(true);
			}
			//End Lester changed
		}
	}
	/*public void paint(Graphics g){
    super.paint(g);
	for (int i=0;i<you.pokeNum;i++){
	  g.setColor(Color.BLACK);
	  g.fillRect(d.width/2,d.height/8*i+50,d.width/2-30,20);
	  g.setColor(Color.YELLOW);
	  g.fillRect(d.width/2,d.height/8*i+50,(d.width/2-30)*you.po[i].health/you.po[i].stamina,20);
	}
  }*/
	public void componentResized(ComponentEvent e){
		d = getSize();
		d1 = b[0].getSize();
	}
	public void generateButton(){
		
		int n = you.pokeNum;
		p1 = new JPanel();
		p1.setLayout(new GridLayout(3,2,10,10));
		if (n>0){
			b[0] = new JButton(){
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					draw(g,0);
				}
			};}
		if (n>1){
			b[1] = new JButton(){
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					g.setColor(Color.BLACK);
					draw(g,1);
				}
			};}
		if (n>2){
			b[2] = new JButton(){
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					draw(g,2);
				}
			};}
		if (n>3){
			b[3] = new JButton(){
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					draw(g,3);
				}
			};}
		if (n>4){
			b[4] = new JButton(){
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					draw(g,4);
				}
			};}
		if (n>5){
			b[5] = new JButton(){
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					draw(g,5);
				}
			};}
		for (i=0;i<you.pokeNum;i++){
			b[i].setBorder(null);
			p1.add(b[i]);
			b[i].addActionListener(this);
			b[i].setFocusable(false);//Lester added
		}
	}
	public void draw(Graphics g, int n){
		g.drawImage(rect.getImage(),0,0,d1.width,d1.height,null);
		g.drawImage(ball.getImage(),20,0,60,75,null);
		g.drawImage(hp.getImage(),120,65,250,30,null);
		if (state){
			g.drawImage(Battle.you.po[n].image1.getImage(),35,15,100,100,null);
		}else{
			g.drawImage(Battle.you.po[n].image2.getImage(),35,15,100,100,null);
		}
		if (Battle.you.po[n].sex){
			g.drawImage(male.getImage(),325,25,45,45,null);
		}else{
			g.drawImage(female.getImage(),325,25,35,35,null);
		}
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(Color.BLACK);
		g.drawString(you.po[n].name,120,50);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Level "+you.po[n].level,45,135);
		g.drawString("Life: "+you.po[n].health+"/"+you.po[n].stamina,225,115);
		g.setColor(Color.YELLOW);
		g.fillRect(177,70,(175)*you.po[n].health/you.po[n].stamina,15);
	}
	public void componentMoved(ComponentEvent e){
	}
	public void componentShown(ComponentEvent e){
	}
	public void componentHidden(ComponentEvent e){
	}
	public void keyPressed(KeyEvent e){
	}
	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}
}