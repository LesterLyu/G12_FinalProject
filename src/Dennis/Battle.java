import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Battle{
	static You you;
	static MyFrame j;
	static PokemonFrame p;
	static int frameNum = 1;
	public static void main(String[] args){
		you = new You();
		j = new MyFrame();
		p = new PokemonFrame();
		p.setSize(800,600);
		p.setVisible(false);
		p.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		j.setSize(800,600);
		j.setVisible(true);
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	//Lester added
	public Battle(){
		you = new You();
		j = new MyFrame();
		p = new PokemonFrame();
		p.setSize(800,600);
		p.setVisible(false);
		p.addWindowListener(
				new WindowAdapter() {
					public void windowClosing( WindowEvent e )
					{
						p.setVisible(false);
						p.t.stop();
						Final.frame.setVisible(true);
						Final.inPokeFrame=false;
					}
				}
				); //
		p.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		j.setSize(800,600);
		j.setVisible(false);
		j.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	public void toPokeFrame() {
		frameNum=2;
		p.t.start();
		p.setVisible(true);
		Final.frame.setVisible(false);
		Final.k.zKey=false;
	}
	//End Lester added
}

class MyFrame extends JFrame implements ComponentListener,KeyListener,ActionListener{
	public JPanel p2,p3,p4,p5,p6;
	public MyPanel p1;
	public JLabel l1,l2,l3,l4,l5,l11,l12,l13,label1,label2,label3,label4;
	public JButton b1,b2,b3,b4,b5,b6,b7,b8;
	public String s[] = new String[3];
	public Container c;
	public int count=0, second = 0, count2=0;
	public int n,n1,n2,num,skillNum = 1,pp,m,interval,interval2,interval3;
	public boolean f,f1,f2,dead;
	public double dps,eps;
	public static Dimension d;
	public static pokemon p[] = new pokemon[5];
	public MyFrame(){
		c = getContentPane();
		c.setLayout(new BorderLayout());
		c.addComponentListener(this);
		c.addKeyListener(this);
		d = getSize();
		//panel 1 initializing
		//initialize pokemon
		int randomPokemon = (int)(Math.random()*6)+1;
		p[0] = pokegen(4,15);//enemy pokemon
		p[0].sk[0] = new Razorleaf();
		p[0].sk[1] = new VineWhip();
		p[0].sk[2] = new Tackle();
		p[1] = Battle.you.po[0];//my pokemon
		s[0] = p[1].sk[0].name;
		s[1] = p[1].sk[1].name;
		s[2] = p[1].sk[2].name;
		p1 = new MyPanel();
		c.add(p1,BorderLayout.CENTER);
		//panel 2 initializing
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setPreferredSize(new Dimension(500,100));
		//panel 3 initializing
		p3 = new JPanel();
		p3.setLayout(new GridLayout(2,2));
		l1 = new JLabel ("What do you want to do?");
		p3.add(l1);
		p2.add(p3,BorderLayout.CENTER);
		//panel 4 initializing
		p4 = new JPanel();
		p4.setLayout(new GridLayout(2,2));
		b1 = new JButton ("Fight");
		b1.addActionListener(this);
		b2 = new JButton ("Bag");
		b2.addActionListener(this);
		b3 = new JButton ("Pokemon");
		b3.addActionListener(this);
		b4 = new JButton ("Run");
		b4.addActionListener(this);
		p4.add(b1);
		p4.add(b2);
		p4.add(b3);
		p4.add(b4);
		p4.setPreferredSize(new Dimension(250,100));
		p2.add(p4,BorderLayout.EAST);
		c.add(p2,BorderLayout.SOUTH);
		//add pokemon
	}
	public void componentResized(ComponentEvent e){
		d = p1.getSize();
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
	public void actionPerformed(ActionEvent e){
		s[0] = p[1].sk[0].name;
		s[1] = p[1].sk[1].name;
		s[2] = p[1].sk[2].name;
		if (e.getSource() == b1){
			p3.remove(l1);
			b5 = new JButton (s[0]);
			b6 = new JButton (s[1]);
			b7 = new JButton (s[2]);
			b8 = new JButton ("Exit");
			p3.add(b5);
			p3.add(b6);
			p3.add(b7);
			p3.add(b8);
			b5.addActionListener(this);
			b6.addActionListener(this);
			b7.addActionListener(this);
			b8.addActionListener(this);
			p4.remove(b1);
			p4.remove(b2);
			p4.remove(b3);
			p4.remove(b4);
			p3.setVisible(false);
			p3.setVisible(true);
			panel(-1);
		}
		else if (e.getSource() == b2){
		}
		else if (e.getSource() == b3){
			setVisible(false);
			Battle.frameNum = 1;
			Battle.p.t.start();
			Battle.p.setVisible(true);
		}
		else if (e.getSource() == b4){
			//System.out.println(p[1].FImageW+" "+p[1].FImageH+" "+p[1].BImageW+" "+p[1].BImageH);
		}
		else if (e.getSource() == b5){
			if (skillNum == 1){
				panel(0);
				if(!noPP(0))
					performSkill(0);
			}
			else {
				skillNum = 1;
				panel(0);
			}
		}
		else if (e.getSource() == b6){
			if (skillNum == 2){
				panel(1);
				if(!noPP(1))
					performSkill(1);
			}
			else {
				skillNum = 2;
				panel(1);
			}
		}
		else if (e.getSource() == b7){
			if (skillNum == 3){
				panel(2);
				if(!noPP(2))
					performSkill(2);
			}
			else {
				skillNum = 3;
				panel(2);
			}
		}
		else if (e.getSource() == b8){
			p3.remove(b5);
			p3.remove(b6);
			p3.remove(b7);
			p3.remove(b8);
			p3.setLayout(new GridLayout(2,2));
			p3.add(l1);
			p4.remove(label1);
			p4.remove(label2);
			p4.remove(label3);
			p4.remove(label4);
			p4.setLayout(new GridLayout(2,2));
			p4.add(b1);
			p4.add(b2);
			p4.add(b3);
			p4.add(b4);
			p3.setVisible(false);
			p3.setVisible(true);
			p4.setVisible(false);
			p4.setVisible(true);
			skillNum = 1;
		}
	}
	public void skillEffect(String s, int num1, int num2, boolean flag, boolean flag1){
		final Timer t1 = new Timer(50,null);
		n = num2;
		n1 = 1-n;
		num = num1;
		f = flag;
		f2 = flag1;
		t1.start();
		/*if (p[n].sk[num].currentPP == 0){
	  t1.stop();
	  return;
	}*/
		p[n].sk[num].currentPP--;
		if (n == 0)
			l11 = new JLabel("The enemy "+p[n].name);
		else
			l11 = new JLabel("Your pokemon "+p[n].name);
		l12 = new JLabel("Uses skill "+ s);
		if(f && f2){
			p3.remove(l1);
			p3.setLayout(new FlowLayout());
			p3.add(l11);
			p3.add(l12);
			p3.setVisible(false);
			p3.setVisible(true);
		}else if (f){
			p3.remove(b5);
			p3.remove(b6);
			p3.remove(b7);
			p3.remove(b8);
			p3.setLayout(new FlowLayout());
			p3.add(l11);
			p3.add(l12);
			p3.setVisible(false);
			p3.setVisible(true);
		}else{
			p3.remove(l11);
			p3.remove(l12);
			p3.add(l11);
			p3.add(l12);
			p3.setVisible(false);
			p3.setVisible(true);
		}
		m = p[n1].getAttacked(p[n], num);
		dps = (p[n1].stamina*MyPanel.health[n1]-p[n1].health)/(p[n1].stamina*10);
		if (m == 2)
			interval = 0;
		else 
			interval = 10;
		count = 0;
		t1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				count++;
				if (count <= interval){
				}
				else if (count <= 14){
					MyPanel.skillNum = num;
					MyPanel.showSkill[n] = true;
				} else if (count <= 21){
					MyPanel.showSkill[n] = false;
					if (count%2==0){
						MyPanel.show[n1] = false;
					}else {
						MyPanel.show[n1] = true;
					}
				} else if (count <= 31){
					MyPanel.health[n1] = MyPanel.health[n1] - dps;
				} else if (count > 31 && count <= 31+interval){
					if (count == 32){
						p3.remove(l11);
						p3.remove(l12);
						if (m == 1){
							l11 = new JLabel("It's very ineffective.");
							l12 = new JLabel();
						}else if (m == 4){
							l11 = new JLabel("It's very effective.");
							l12 = new JLabel();
						}
						p3.add(l11);
						p3.add(l12);
						p3.setVisible(false);
						p3.setVisible(true);
					}
				}
				else if (f2 && f){
					p3.remove(l11);
					p3.remove(l12);
					p3.setLayout(new GridLayout(2,2));
					p3.add(l1);
					p3.setVisible(false);
					p3.setVisible(true);
				}else if (f2){
					p3.remove(l11);
					p3.remove(l12);
					p3.setLayout(new GridLayout(2,2));
					p3.add(b5);
					p3.add(b6);
					p3.add(b7);
					p3.add(b8);
					p3.setVisible(false);
					p3.setVisible(true);
				}
				else if (!f2){
					p3.remove(l11);
					p3.remove(l12);
					p3.setVisible(false);
					p3.setVisible(true);
				}
				if (count >= interval+32){
					t1.stop();
				}
			}
		});
	}
	public void AI(boolean flag,boolean flag1){
		double dps[] = new double[3];
		double max = -1;
		int index = -1;
		for (int i=0;i<3;i++){
			double multiply = p[1].multiplier(p[0].sk[num].attribute,p[1].attribute);
			dps[i] = (p[0].attack * p[0].sk[i].damage/100*multiply) - p[1].defense/2;
			if (dps[i]<=0)
				dps[i] = 1;
			if (dps[i]>max && p[0].sk[i].currentPP!=0){
				max = dps[i];
				index = i;
			}
		}
		System.out.println("Enemy attack!");
		skillEffect(p[0].sk[index].name,index,0,flag,flag1);
	}
	public void performSkill(int num){
		n2 = num;
		dead = false;
		final Timer t2 = new Timer(50,this);
		t2.start();
		if (p[1].speed >= p[0].speed){
			skillEffect(s[n2],n2,1,true,false);
			f1 = true;
		}else {
			AI(true,false);
			f1 = false;
		}
		second = 0;
		interval3 = interval;
		t2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				second++;
				if (second>interval3+33 && second<=interval3+34){
					dead = die();
					if (!dead){
						if (f1){
							AI(false,true);
						}
						else {
							skillEffect(s[n2],n2,1,false,true);
						}
					}
				}
				else if (second>66){
					if (!dead)
						dead = die();
					t2.stop();
				}
			}
		});
	}
	public void panel(int n){
		int num;
		String s = "";
		if (n == -1){
			num = 0;
		} else {
			num = n;
			p4.remove(label1);
			p4.remove(label2);
			p4.remove(label3);
			p4.remove(label4);
		}
		label1 = new JLabel();
		label1.setOpaque(true);
		if (p[1].sk[num].attribute == 1){
			s = "Fire";
			label1.setBackground(Color.RED);
			repaint();
		} else if (p[1].sk[num].attribute == 2){
			s = "Grass";
			label1.setBackground(Color.GREEN);
			repaint();
		} else if (p[1].sk[num].attribute == 3){
			s = "Water";
			label1.setBackground(Color.BLUE);
			repaint();
		} else{
			s = "Normal";
		}
		label1.setText("Attribute: "+s);
		label2 = new JLabel("PP: "+p[1].sk[num].currentPP+"/"+p[1].sk[num].maxPP);
		label3 = new JLabel("Power: "+p[1].sk[num].damage+"/100");
		label4 = new JLabel("Special Effect: "+p[1].sk[num].explaination);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label4.setHorizontalAlignment(SwingConstants.CENTER);
		p4.setLayout(new GridLayout(4,1));
		p4.add(label1);
		p4.add(label2);
		p4.add(label3);
		p4.add(label4);
		p4.setVisible(false);
		p4.setVisible(true);
	}
	public boolean die(){
		interval2 = 0;
		count2 = 0;
		f = false;
		if (MyPanel.health[0]>=0 && MyPanel.health[1]>=0){
			return false;
		}
		else if (MyPanel.health[1]<=0){
			num = 1;
			l11 = new JLabel("Your pokemon "+p[num].name);
			interval2 = 0;
		}
		else if (MyPanel.health[0]<=0){
			num = 0;
			l11 = new JLabel("The enemy "+p[num].name);
			eps = p[0].maxStamina/10.0;
			interval2 = 10;
			//p[1].levelUp();
		}
		l12 = new JLabel("is dead.");
		p3.remove(b5);
		p3.remove(b6);
		p3.remove(b7);
		p3.remove(b8);
		p3.setLayout(new FlowLayout());
		p3.add(l11);
		p3.add(l12);
		p3.setVisible(false);
		p3.setVisible(true);
		final Timer t1 = new Timer(50,this);
		t1.start();
		t1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				count2++;
				if (count2>5 && count2<=35){
					MyPanel.y[num+2] += 10;
				} 
				else if (count2>35 && count2<=35+interval2){ 
					p[1].experience += (int)eps;
					boolean b = p[1].levelUp();
					if (b){
						p3.remove(l11);
						p3.remove(l12);
						l11 = new JLabel("Your pokemon has leveled up to ");
						l12 = new JLabel("Level "+p[1].level);
						p3.add(l11);
						p3.add(l12);
						p3.setVisible(false);
						p3.setVisible(true);
						f = true;
					}
				}
				else if (f && count2<=50+interval2){
				}
				else if (count2>50+interval2){
					t1.stop();
					//restart();
					wild();
				}
			}
		});
		return true;
	}
	public boolean noPP(int num){
		if (p[1].sk[num].currentPP == 0){
			p3.remove(b5);
			p3.remove(b6);
			p3.remove(b7);
			p3.remove(b8);
			p3.setLayout(new FlowLayout());
			l11 = new JLabel("You have no PP left for this skill");
			p3.add(l11);
			p3.setVisible(false);
			p3.setVisible(true);
			final Timer t1 = new Timer(50,this);
			t1.start();
			pp = 0;
			t1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					pp++;
					if (pp>10){
						p3.remove(l11);
						p3.setLayout(new GridLayout(2,2));
						p3.add(b5);
						p3.add(b6);
						p3.add(b7);
						p3.add(b8);
						p3.setVisible(false);
						p3.setVisible(true);
						t1.stop();
					}
				}
			});
			return true;
		}
		else {
			return false;
		}
	}
	/*public void restart(){
    setVisible(false);
    MyFrame j1 = new MyFrame();
	j1.setVisible(true);
	j1.setSize(500,400);
	j1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }*/
	public void wild(){
		p[0] = pokegen((int)(Math.random()*6)+1,(int)(Math.random()*50)+1);
		MyPanel.health[0] = 1;
		MyPanel.y[2] = 0;
		p3.remove(l11);
		p3.remove(l12);
		p3.setLayout(new GridLayout(2,2));
		p3.add(l1);
		p4.remove(label1);
		p4.remove(label2);
		p4.remove(label3);
		p4.remove(label4);
		p4.setLayout(new GridLayout(2,2));
		p4.add(b1);
		p4.add(b2);
		p4.add(b3);
		p4.add(b4);
		p3.setVisible(false);
		p3.setVisible(true);
		p4.setVisible(false);
		p4.setVisible(true);
		skillNum = 1;
	}
	public pokemon pokegen(int type, int level){
		switch (type){
		case 1: return new Charmander(level);
		case 2: return new Bulbasaur(level);
		case 3: return new Squirtle(level);
		case 4: return new Chikorita(level);
		case 5: return new Cyndaquil(level);
		case 6: return new Totodile(level);
		}
		return new Charmander(level);
	}
}

class MyPanel extends JPanel implements ActionListener,ComponentListener{
	public static int skillNum = 0;
	public double x[] = new double[10];
	public static double y[] = new double[10];
	public ImageIcon i1,i2,i3,i4;
	public double p1,p2;
	public static double health[] = new double[2];
	public static Timer t;
	public static boolean show[] = new boolean[2];
	public static boolean showSkill[] = new boolean[2];
	public MyPanel(){
		i1 = new ImageIcon(getClass().getResource("background.png"));
		i2 = MyFrame.p[0].front;
		i3 = MyFrame.p[1].back;
		show[0] = true;
		show[1] = true;
		showSkill[0] = false;
		showSkill[1] = false;
		health[0] = MyFrame.p[0].health/(double)MyFrame.p[0].stamina;
		health[1] = MyFrame.p[1].health/(double)MyFrame.p[1].stamina;
		addComponentListener(this);
		t = new Timer(10,this);
		t.start();
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		i2 = MyFrame.p[0].front;
		i3 = MyFrame.p[1].back;
		g.drawImage(i1.getImage(), 0, 0 ,MyFrame.d.width,MyFrame.d.height, null);
		if(show[0])
			g.drawImage(i2.getImage(),(int)x[2],(int)y[2],MyFrame.d.width/2*MyFrame.p[0].FImageW/100,MyFrame.d.height*MyFrame.p[0].FImageH/100,null);
		if (show[1])
			g.drawImage(i3.getImage(),(int)x[3],(int)(y[3]-50),MyFrame.d.width/2*MyFrame.p[1].BImageW/100,MyFrame.d.height*MyFrame.p[1].BImageH/100,null);
		if (showSkill[1]){
			g.drawImage(MyFrame.p[1].sk[skillNum].skillImage.getImage(),(int)x[2],(int)y[2],MyFrame.d.width/2*MyFrame.p[1].sk[skillNum].width/100,MyFrame.d.height*MyFrame.p[1].sk[skillNum].height/100,null);
		}
		if (showSkill[0]){
			g.drawImage(MyFrame.p[0].sk[skillNum].skillImage.getImage(),(int)x[3],(int)y[3],MyFrame.d.width/2*MyFrame.p[0].sk[skillNum].width/100,MyFrame.d.height*MyFrame.p[0].sk[skillNum].height/100,null);
		}
		g.setColor(Color.WHITE);
		for (int i=0;i<2;i++){
			int x1=0,y1 = 0;
			if (i==0){
				x1 = 20;
				y1 = 20;
			}else{
				x1 = MyFrame.d.width/5*3;
				y1 = MyFrame.d.height/5*3;
			}
			g.drawImage(new ImageIcon(getClass().getResource("battleBar.png")).getImage(),x1,y1,MyFrame.d.width/5*2,90,null);
			g.setColor(Color.YELLOW);
			if (health[i]>0){
				g.fillRect(x1+160,y1+40,(int)((120)*health[i]),8);
			}
			if (i==1){
				g.setColor(Color.BLUE);
				g.fillRect(x1+48,y1+80,(230)*MyFrame.p[i].experience/MyFrame.p[i].maxExperience,5);
			}
			if (MyFrame.p[i].sex)
				g.drawImage(new ImageIcon(getClass().getResource("male.png")).getImage(),x1+60,y1+40,35,35,null);
			else
				g.drawImage(new ImageIcon(getClass().getResource("female.png")).getImage(),x1+60,y1+40,25,25,null);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(MyFrame.p[0].name,80,50);
		g.drawString("Level "+MyFrame.p[0].level+"",230,50);
		g.drawString(MyFrame.p[1].name,MyFrame.d.width/5*3+80,MyFrame.d.height/5*3+30);
		g.drawString("Level "+MyFrame.p[1].level+"",MyFrame.d.width/5*3+230,MyFrame.d.height/5*3+30);
		g.drawString(MyFrame.p[1].health+"/"+MyFrame.p[1].stamina,MyFrame.d.width/5*3+200,MyFrame.d.height/5*3+70);
	}
	public void componentResized(ComponentEvent e){
		x[2] = MyFrame.d.width/5*3; 
		y[2] = 0;
		x[3] = MyFrame.d.width/5;
		y[3] = (int)(MyFrame.d.height/5*2.5);
	}
	public void componentMoved(ComponentEvent e){
	}
	public void componentShown(ComponentEvent e){
	}
	public void componentHidden(ComponentEvent e){
	}
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == t){
			p1+=1;
			p2+=1;
			double in1 = 0;
			double in2 = 0;
			if (health[0]>0)
				in1 = health[0];
			else 
				in1 = 0;
			if (health[1]>0)
				in2 = health[1];
			else 
				in2 = 0;
			if (p1<=5){
				y[2]-=in1;
			}
			else if (p1<=10){
				y[2]+=in1;
			}
			else{
				p1-=11;
			}
			if (p2<=5){
				y[3]-=in2;
			}
			else if (p2<=10){
				y[3]+=in2;
			}
			else{
				p2-=11;
			}
			repaint();
		}
	}
}