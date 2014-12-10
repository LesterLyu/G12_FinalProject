import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class skills{
  String name;
  String explaination;
  int damage;
  int attribute;
  int maxPP;
  int currentPP;
  int level;
  ImageIcon skillImage;
  int width;
  int height;
  static double chart[][] = new double[4][4];
  public static void chart(){
    for (int i=0;i<4;i++){
	  chart[0][i] = 1;
	  chart[i][0] = 1;
	  if (i!=0)
	    chart[i][i] = 0.5;
	}
	chart[1][2] = 2;
	chart[1][3] = 0.5;
	chart[2][1] = 0.5;
	chart[2][3] = 2;
	chart[3][1] = 2;
	chart[3][2] = 0.5;
  }
  public void start(){
	  System.out.println(name);
    skillImage = new ImageIcon(getClass().getResource(name+".png"));
	currentPP = maxPP;
  }
  //int requiredLevel;
  //int requiredAttribute;
}

class Tackle extends skills{
  public Tackle(){
    name = "Tackle";
	explaination = "none";
	level = 1;
    damage = 50;
    attribute = 0;
	maxPP = 35;
	width = 29;
	height = 27;
	start();
  }
}

class Facade extends skills{
  public Facade(){
    name = "Facade";
	explaination = "none";
	level = 15;
	damage = 70;
	attribute = 0;
	maxPP = 15;
	width = 45;
	height = 35;
	start();
  }
}

class Strength extends skills{
  public Strength(){
    name = "Strength";
	explaination = "none";
	level = 15;
	damage = 80;
	attribute = 0;
	maxPP = 10;
	width = 67;
	height = 50;
	start();
  }
}

class Ember extends skills{
  public Ember(){
    name = "Ember";
	explaination = "none";
	level = 15;
	damage = 40;
	attribute = 1;
	maxPP = 35;
	width = 54;
	height = 54;
	start();
  }
}

class Incinerate extends skills{
  public Incinerate(){
    name = "Incinerate";
	explaination = "none";
	level = 20;
	damage = 60;
	attribute = 1;
	maxPP = 10;
	width = 60;
	height = 55;
	start();
  }
}

class Flamethrower extends skills{
  public Flamethrower(){
    name = "Flamethrower";
	explaination = "none";
	level = 50;
	damage = 95;
	attribute = 1;
	maxPP = 5;
	width = 70;
	height = 55;
	start();
  }
}

class VineWhip extends skills{
  public VineWhip(){
    name = "VineWhip";
	explaination = "none";
	level = 15;
	damage = 35;
	attribute = 2;
	maxPP = 35;
	width = 70;
	height = 55;
	start();
  }
}

class Razorleaf extends skills{
  public Razorleaf() {
    name = "Razorleaf";
	explaination = "none";
	level = 15;
	damage = 55;
	attribute = 2;
	maxPP = 35;
	width = 70;
	height = 55;
	//start();
  }
}

	