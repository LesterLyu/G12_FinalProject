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
  int num = -1,i;
  public PokemonFrame(){
    d = getSize();
	d1 = getSize();
	setSize(500,400);
	System.out.println(d.width+" "+d.height);
    c = getContentPane();
	addComponentListener(this);
	c.setLayout(new GridLayout(7,1,10,0));
	you = Battle.you;
	for (i=0;i<you.pokeNum;i++){
	  b[i] = new JButton(){
		public void paintComponent(Graphics g){
		  super.paintComponent(g);
		  g.setColor(Color.BLACK);
		  g.fillRect(300,15,180,20);
		  g.setColor(Color.YELLOW);
		  System.out.println(i);
		  g.fillRect(300,15,180*you.po[0].health/you.po[0].stamina,20);
		}
	  };
	  if (you.po[i].attribute == 1)
	    b[i].setBackground(Color.RED);
	  else if (you.po[i].attribute == 2)
	    b[i].setBackground(Color.GREEN);
	  else if (you.po[i].attribute == 3)
	    b[i].setBackground(Color.BLUE);
	  b[i].setLayout(new BorderLayout());
	  Image img = you.po[i].image.getImage() ;  
	  Image newimg = img.getScaledInstance( 75, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
	  l1 = new JLabel(new ImageIcon(newimg));
	  b[i].add(l1,BorderLayout.WEST);
	  l1.setPreferredSize(new Dimension(100,100));
	  l2 = new JLabel(you.po[i].name);
	  l3 = new JLabel("Level "+you.po[i].level);
	  p1 = new JPanel();
	  p1.setLayout(new GridLayout(2,1));
	  p1.add(l2);
	  p1.add(l3);
	  l2.setHorizontalAlignment(SwingConstants.CENTER);
	  l3.setHorizontalAlignment(SwingConstants.CENTER);
	  b[i].add(p1,BorderLayout.CENTER);
	  p1.setOpaque(false);
	  l4 = new JLabel("Life: "+you.po[i].health+"/"+you.po[i].stamina);
	  b[i].add(l4,BorderLayout.EAST);
	  l4.setPreferredSize(new Dimension(250,50));
	  c.add(b[i]);
	  b[i].addActionListener(this);
	}
	l1 = new JLabel();
	l2 = new JLabel();
	l3 = new JLabel();
	p2 = new JPanel();
	p2.setLayout(new GridLayout(1,4));
	p2.add(l1);
	p2.add(l2);
	p2.add(l3);
	bb = new JButton("Back");
	bb.addActionListener(this);
	p2.add(bb);
	c.add(p2);
  }
  public void actionPerformed(ActionEvent e){
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
	}
	setVisible(false);
	Battle.j.setVisible(true);
	if (num != -1){
	    Battle.j.p[1] = Battle.you.po[num];
		MyPanel.health[1] = MyFrame.p[1].health/(double)MyFrame.p[1].stamina;
		Battle.j.AI(true,true);
	}
	for (int i=0;i<you.pokeNum;i++){
	  c.remove(b[i]);
	}
	c.remove(p2);
	for (i=0;i<you.pokeNum;i++){
	  b[i] = new JButton();
	  if (you.po[i].attribute == 1)
	    b[i].setBackground(Color.RED);
	  else if (you.po[i].attribute == 2)
	    b[i].setBackground(Color.GREEN);
	  else if (you.po[i].attribute == 3)
	    b[i].setBackground(Color.BLUE);
	  b[i].setLayout(new BorderLayout());
	  Image img = you.po[i].image.getImage() ;  
	  Image newimg = img.getScaledInstance( 75, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
	  l1 = new JLabel(new ImageIcon(newimg));
	  b[i].add(l1,BorderLayout.WEST);
	  l1.setPreferredSize(new Dimension(100,100));
	  l2 = new JLabel(you.po[i].name);
	  l3 = new JLabel("Level "+you.po[i].level);
	  p1 = new JPanel();
	  p1.setLayout(new GridLayout(2,1));
	  p1.add(l2);
	  p1.add(l3);
	  l2.setHorizontalAlignment(SwingConstants.CENTER);
	  l3.setHorizontalAlignment(SwingConstants.CENTER);
	  b[i].add(p1,BorderLayout.CENTER);
	  p1.setOpaque(false);
	  l4 = new JLabel("Life: "+you.po[i].health+"/"+you.po[i].stamina);
	  b[i].add(l4,BorderLayout.EAST);
	  l4.setVerticalAlignment(SwingConstants.BOTTOM);
	  l4.setPreferredSize(new Dimension(d.width/2,50));
	  c.add(b[i]);
	  b[i].addActionListener(this);
	}
	c.add(p2);
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


  