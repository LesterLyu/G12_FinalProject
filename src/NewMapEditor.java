import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;


public class NewMapEditor extends JFrame implements ActionListener, ComponentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int read_width = 32, read_height = 32, read_row = 115, read_col = 70;
	static JButton btn[][] = new JButton[read_row][read_col],btn2[][],b1,b2,b3,b4,b5,b6,b7;
	static JLabel id = new JLabel("ID: ");
	int selected=0;
	JScrollPane scrollPane1, scrollPane2;
	int num[][][];
	int sizex=0, sizey=0, pixel = 40;
	File Stored[] = new File[6];
	String Layer[] = { "Layer 0", "Layer 1", "Layer 2", "Layer 3", "Layer 4", "Layer 5" };
	JComboBox<String> layerSelection = new JComboBox<String>(Layer);
	JPanel p2, leftp;
	JTextField text = new JTextField();
	int lines, layer=0;
	//boolean layer1c = true, layer2c = true, layer3c = true, layer4c = true, layer5c = true, layer6c = true;
	final JCheckBox layerc[] = new JCheckBox[6];
	JPanel layerp[]=new JPanel[6];

	public NewMapEditor(String string) {
		super("MapEditor");
		//

		FileDialog fd = new FileDialog(this, "Choose the first map file", FileDialog.LOAD);
		//fd.setDirectory("/src");
		fd.setFile("*.*");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename == null){
			//System.exit(0);
			System.out.println("You cancelled the choice");
			String input = JOptionPane.showInputDialog("Please input a file name you want to create: ","The map name");
			for(int i=0; i<6; i++){
				Stored[i] = new File("src/Map/"+input+i+".txt");
			}
		}
		else{
			String temp[] = filename.split("\\.");
			String out="";
			for(int i=0; i<temp.length-1; i++){
				out = out + temp[i]+".";
			}
			out = out.substring(0, out.length()-2);
			JOptionPane.showMessageDialog(null, "You chose " +out);
			System.out.println("You chose " +out);
			for(int i=0; i<6; i++){
				Stored[i] = new File("src/Map/"+out+i+".txt");
			}
		}
		//
		
		try {
			lines = readLines();
			if(lines==0){
				lines  = Integer.parseInt(JOptionPane.showInputDialog("Input a number: ","Number Only"));	
			}
			pixel = lines;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btn2 = new JButton[lines][lines];
		num = new int[6][lines][lines];
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		Tiles.readPng();
		this.addComponentListener(this);
		//
		p2 = new JPanel();
		JPanel lp = new JPanel();
		lp.setLayout(new OverlayLayout(lp));
		lp.add(p2);
		p2.setOpaque(false);
		//initialize Layered Panel
		layerp[0]=new JPanel(){
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){
						int a = num[0][i][j]/100, b = num[0][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), 0+j*32, 0+i*32,32,32,null);// layer 0
					}
				}
			}
		};
		layerp[1]=new JPanel(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){
						int a = num[1][i][j]/100, b = num[1][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), 0+j*32, 0+i*32,32,32,null);// layer 1
					}
				}
			}
		};
		layerp[2]=new JPanel(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){
						int a = num[2][i][j]/100, b = num[2][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), 0+j*32, 0+i*32,32,32,null);// layer 2
					}
				}
			}
		};
		layerp[3]=new JPanel(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){
						int a = num[3][i][j]/100, b = num[3][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), 0+j*32, 0+i*32,32,32,null);// layer 3
					}
				}
			}
		};
		layerp[4]=new JPanel(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){
						int a = num[4][i][j]/100, b = num[4][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), 0+j*32, 0+i*32,32,32,null);// layer 4
					}
				}
			}
		};
		layerp[5]=new JPanel(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){
						int a = num[5][i][j]/100, b = num[5][i][j]%100;
						g.drawImage(Tiles.getImage(a, b), 0+j*32, 0+i*32,32,32,null);// layer 5
					}
				}
			}
		};
		for(int p=5; p>=0; p--){ //from top layer to bottom layer
			layerp[p].setOpaque(false);
			lp.add(layerp[p]);
		}
		scrollPane2 = new JScrollPane(lp);
		////
		scrollPane2.setPreferredSize(new Dimension(512,384));
		leftp = new JPanel();
		leftp.setLayout(new FlowLayout());
		leftp.add(scrollPane2);
		c.add(leftp, BorderLayout.WEST);
		p2.setLayout(new GridLayout(lines,lines,0,0));
		for(int i=0; i<lines; i++){
			for(int j=0; j<lines; j++){
				btn2[i][j]=new JButton();
				btn2[i][j].setOpaque(false);
				btn2[i][j].setBorderPainted(false);
				btn2[i][j].setContentAreaFilled(false);
				btn2[i][j].addActionListener(this);
				btn2[i][j].setPreferredSize(new Dimension(read_width, read_height));
				p2.add(btn2[i][j]);
			}
		}
		//

		JPanel p1 = new JPanel();
		scrollPane1 = new JScrollPane(p1);
		scrollPane1.setPreferredSize(new Dimension(512,384));
		c.add(scrollPane1, BorderLayout.EAST);
		p1.setLayout(new GridLayout(read_row,read_col,0,0));
		for(int i=0; i<read_row; i++){
			for(int j=0; j<read_col; j++){
				btn[i][j]=new JButton(new ImageIcon(Tiles.getImage(i, j)));
				btn[i][j].setOpaque(false);
				//btn[i][j].setBorderPainted(false);
				btn[i][j].setContentAreaFilled(false);
				btn[i][j].addActionListener(this);
				btn[i][j].setPreferredSize(new Dimension(read_width, read_height));
				p1.add(btn[i][j]);
			}
		}


		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout());
		b1 = new JButton("Load");
		b1.addActionListener(this);
		b2 = new JButton("Save");
		b2.addActionListener(this);
		b3 = new JButton("Exit");
		b3.addActionListener(this);
		b4 = new JButton("Fill with");
		b4.addActionListener(this);
		b5 = new JButton("Show grid");
		b5.addActionListener(this);
		b6 = new JButton("Set pixel");
		b6.addActionListener(this);
		b7 = new JButton("Launch");
		b7.addActionListener(this);

		layerSelection.setSelectedIndex(0);
		layerSelection.addActionListener(this);
		p3.add(text);
		text.setEditable(false);
		text.setPreferredSize(new Dimension(60,30));
		p3.add(b1);
		p3.add(b2);
		p3.add(b3);
		p3.add(b4);
		p3.add(b5);
		p3.add(b6);
		p3.add(b7);
		p3.add(new JLabel("Edit:"));
		p3.add(layerSelection);
		p3.add(id);
		c.add(p3, BorderLayout.SOUTH);


		//layer checkboxs
		for(int i=0; i<6; i++){
			layerc[i] = new JCheckBox("Layer "+i);
			layerc[i].setSelected(true);
			p3.add(layerc[i]);
		}

		layerc[0].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				if(layerc[0].isSelected()){
					layerp[0].setVisible(true);
				}
				else
					layerp[0].setVisible(false);
			}           
		});

		layerc[1].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				if(layerc[1].isSelected()){
					layerp[1].setVisible(true);
				}
				else
					layerp[1].setVisible(false);
			}           
		});

		layerc[2].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				if(layerc[2].isSelected()){
					layerp[2].setVisible(true);
				}
				else
					layerp[2].setVisible(false);
			}           
		});
		layerc[3].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				if(layerc[3].isSelected()){
					layerp[3].setVisible(true);
				}
				else
					layerp[3].setVisible(false);
			}           
		});
		layerc[4].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				if(layerc[4].isSelected()){
					layerp[4].setVisible(true);
				}
				else
					layerp[4].setVisible(false);
			}           
		});
		layerc[5].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				if(layerc[5].isSelected()){
					layerp[5].setVisible(true);
				}
				else
					layerp[5].setVisible(false);
			}           
		});
		layerc[5].setSelected(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//JButton b = (JButton)e.getSource();
		if(e.getSource()==b1){
			try {
				read();
				repaint();
			} catch (IOException e1) {}
		}
		else if(e.getSource()==b2){
			save(pixel);
		}
		else if(e.getSource()==b3){
			System.exit(0);
		}
		else if(e.getSource()==b4){
			String input= JOptionPane.showInputDialog("Please input an ID: ");
			if(isInteger(input)){
				for(int i=0; i<pixel; i++){ //white
					for(int j=0; j<pixel; j++){
						num[layer][i][j]=Integer.parseInt(input);
						//System.out.println(a+" "+b);
					}

					repaint();
				}
			}
		}
		else if(e.getSource()==b5){
			if(b5.getText().equalsIgnoreCase("Show grid")){
				b5.setText("Hide grid");
				for(int i=0; i<pixel; i++)
					for(int j=0; j<pixel; j++)
						btn2[i][j].setBorderPainted(true);
			}
			else{
				b5.setText("Show grid");
				for(int i=0; i<pixel; i++)
					for(int j=0; j<pixel; j++)
						btn2[i][j].setBorderPainted(false);
			}
		}
		else if(e.getSource()==b6){ //set pixel
			text.setText("Loading...");
			String input = JOptionPane.showInputDialog("Please input an number: ");
			String inputID = JOptionPane.showInputDialog("Please input an ID: ");
			if(isInteger(input+inputID)){
				int n = Integer.parseInt(input.trim());
				int id = Integer.parseInt(inputID.trim());
				lines = n;
				pixel = n;
				btn2 = new JButton[n][n];
				int temp[][][] = new int[6][n][n];
				for(int p = 0; p<6; p++)
					for(int i=0; i<Math.min(num[1].length, n); i++){
						for(int j=0; j<Math.min(num[1].length, n); j++){
							temp[p][i][j]=num[p][i][j];	
						}

					}
				num = new int[6][n][n];
				for(int p = 0; p<6; p++)
					for(int i=0; i<n; i++){
						for(int j=0; j<n; j++){
							num[p][i][j] = temp[p][i][j];	
							//System.out.println("num["+i+"]["+j+"]"+num[p][i][j]);
						}
					}
				for(int p = 0; p<6; p++)
					for(int i=0; i<n; i++){
						for(int j=0; j<n; j++){
							if(num[p][i][j]==0)
								num[p][i][j] = id;	
						}

					}
				//num = temp;
				p2.removeAll();
				p2.setLayout(new GridLayout(n,n,0,0));
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						btn2[i][j]=new JButton();
						btn2[i][j].setOpaque(false);
						btn2[i][j].setBorderPainted(false);
						btn2[i][j].setContentAreaFilled(false);
						btn2[i][j].addActionListener(this);
						btn2[i][j].setPreferredSize(new Dimension(read_width, read_height));
						p2.add(btn2[i][j]);
					}
				}
				for(int p = 0; p<6; p++)
					for(int i=0; i<n; i++){ 
						for(int j=0; j<n; j++){
							//num[i][j]=n;
							//int a = num[p][i][j]/100, b = num[p][i][j]%100;
							//System.out.println("num["+i+"]["+j+"]="+num[i][j]);
							//System.out.println(a+" "+b);
							//btn2[i][j].setIcon(new ImageIcon(Tiles.getImage(a, b)));
							repaint();
						}
					}
			}
			text.setText("");
		}
		else if(e.getSource()==b7){
			final Final game = new Final("BETA");  
			game.c.add(game);
			game.frame.pack();
			game.frame.setVisible(true);           
			game.frame.addWindowListener(
					new WindowAdapter() {
						public void windowClosing( WindowEvent e )
						{
							game.stop();
						}
					}
					); //
			game.start();
		}
		else if(e.getSource()==layerSelection){
			layer = Integer.parseInt(((String)layerSelection.getSelectedItem()).substring(6));
			System.out.println("Layer "+layer);
		}
		else{
			for(int i=0; i<read_row; i++){
				for(int j=0; j<read_col; j++){
					if(e.getSource()==btn[i][j]){
						//System.out.println(getID(i, j));
						selected = getID(i, j);
						id.setText("ID: "+selected);
					}

				}
			}
			for(int i=0; i<pixel; i++){
				for(int j=0; j<pixel; j++){
					if(e.getSource()==btn2[i][j]){
						int a = selected/100, b = selected%100;
						//btn2[i][j].setIcon(new ImageIcon(tile[a][b]));
						num[layer][i][j]=selected;
						System.out.println(a+" "+b);
						repaint();
					}
				}
			}
		}
	}

	public int readLines() throws IOException{
		int cnt =0;
		if (Stored[0].exists()){

			Scanner sc = new Scanner(Stored[0]);
			ArrayList <String> line = new ArrayList <String>();
			while(sc.hasNextLine()){
				line.add(sc.nextLine());
				cnt++;
			}
			sc.close();

		}
		return cnt;
	}
	public void read() throws IOException{
		for(int p=0; p<6; p++){
			if (Stored[p].exists()){
				Scanner sc = new Scanner(Stored[p]);
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

			else{
				File dir = new File("src/map/");
				dir.mkdir();
				Stored[p].createNewFile();
				System.out.println("New file created.");
				for(int i=0; i<lines; i++){
					for(int j=0; j<lines; j++){

						num[p][i][j] = 0;

					}
				}

			}
		}
	}
	public void save(int n){

		try{
			for(int p=0; p<6; p++){
				FileWriter fw = new FileWriter(Stored[p].getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						bw.write(num[p][i][j]+",");
						//System.out.println(num[p][i][j]);
					}
					bw.newLine();
				}

				bw.close();
			}
		} catch(IOException evt){}

	}

	public int getID(int a, int b){
		return a*100+b;
	}


	public static void main(String[] args) {
		NewMapEditor lvds = new NewMapEditor("BETA");  
		Insets insets = lvds.getInsets();
		lvds.setSize(800 + insets.bottom+insets.top ,600+insets.left+insets.right );     //25x20 tiles
		lvds.setVisible(true);                
		lvds.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
	}
	public static BufferedImage ImageIconToBufferedImage(ImageIcon imageicon) {
		BufferedImage bufferedimage;
		bufferedimage = new BufferedImage(imageicon.getIconWidth(),imageicon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = bufferedimage.createGraphics();
		imageicon.paintIcon(null, g1, 0, 0);
		g1.dispose();
		return bufferedimage;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		int sizeX = this.getWidth()/2-10, sizeY = this.getHeight()-83;
		if(sizeX>lines*32){
			sizeX = lines*32+3;
		}
		if(sizeY>lines*32){
			sizeY = lines*32+3;
		}
		System.out.println(sizeX);
		scrollPane2.setPreferredSize(new Dimension(sizeX,sizeY));
		System.out.println(scrollPane2.getWidth());
		scrollPane1.setPreferredSize(new Dimension(this.getWidth()/2-10,this.getHeight()/2-10));
		//repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		return true;
	}


}
