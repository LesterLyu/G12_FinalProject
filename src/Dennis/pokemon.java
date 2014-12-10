import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

class pokemon{
  public skills sk[] = new skills[3];
  public String name;
  public int level;
  public int experience;
  public int maxExperience;
  public int attack;
  public int defense;
  public int health;
  public int stamina;
  public int speed;
  public int attribute;
  public boolean sex;
  public static int maxAttack;
  public static int minAttack;
  public static int maxDefense;
  public static int maxStamina;
  public static int minStamina;
  public static int maxSpeed;
  public int FImageW,FImageH,BImageW, BImageH;
  public ImageIcon front,back,image1,image2;
  //public abstract void attack(int n){}
  //public abstract void levelup(){}
  public void initialize(){
    for (int i=0;i<3;i++){
	  boolean flag = true;
	  int randomSkill = 0;
	  while (flag){
	    flag = false;
	    randomSkill = (int)(Math.random()*8)+1;
		for (int j=0;j<i;j++){
		  if (getSkills(randomSkill).getClass().equals(sk[j].getClass())){
		    flag = true;
		  }
		}
		if (getSkills(randomSkill).level>level){
		  flag = true;
		}
		if (getSkills(randomSkill).attribute != attribute && getSkills(randomSkill).attribute != 0){
		  flag = true;
		}
	  }
	  sk[i] = getSkills(randomSkill);//randomSkill);
	}
	int random = (int)(Math.random()*2);
	if (random==0)
	  sex = true;
	else 
	  sex = false;
    attack = minAttack+maxAttack*level/50;
	defense = maxDefense*level/50;
	stamina = minStamina+maxStamina*level/50;
	health = stamina;
	speed = maxSpeed*level/50;
	maxExperience = 50;
	for (int i=0;i<level;i++){
	  maxExperience += 10;
	}
	experience = maxExperience;
	ImageIcon image = new ImageIcon(getClass().getResource(name+".png"));
	image1 = new ImageIcon(ImageIconToBufferedImage(image).getSubimage(0,0,64,64));
	image2 = new ImageIcon(ImageIconToBufferedImage(image).getSubimage(64,0,64,64));
	front = new ImageIcon(getClass().getResource(name+"Front.png"));
	back = new ImageIcon(getClass().getResource(name+"Back.png"));
	FImageW = FImageH = BImageW = BImageH = 64;
  }
  public skills getSkills (int type){
    switch(type){
	  case 1: return new Tackle();
	  case 2: return new Facade();
	  case 3: return new Strength();
	  case 4: return new Ember();
	  case 5: return new Incinerate();
	  case 6: return new Flamethrower();
	  case 7: return new VineWhip();
	  case 8: return new Razorleaf();
	}
	return new Tackle();
  }
  public int getAttacked(pokemon enemy, int num){
    double multiply = multiplier(enemy.sk[num].attribute,attribute);
    int damage = (int)(enemy.attack * enemy.sk[num].damage/100*multiply) - defense/3;
	if (damage<=0)
	  damage = 1;
	health = health - damage;
	return (int)(multiply*2);
  }
  public double multiplier(int a1,int a2){
    skills.chart();
    return skills.chart[a1][a2];
  }
  public boolean levelUp(){
    if (experience>=maxExperience){
	  experience = 0;
	  level++;
	  attack = minAttack+maxAttack*level/50;
	  defense = maxDefense*level/50;
	  stamina = minStamina+maxStamina*level/50;
	  health = (int)(MyPanel.health[1]*stamina);
	  speed = maxSpeed*level/50;
	  maxExperience+=10;
	  return true;
	}
	else{
	  return false;
	}
  }
  public BufferedImage ImageIconToBufferedImage(ImageIcon imageicon) {
		BufferedImage bufferedimage;
		bufferedimage = new BufferedImage(imageicon.getIconWidth(),imageicon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = bufferedimage.createGraphics();
		imageicon.paintIcon(null, g1, 0, 0);
		g1.dispose();
		return bufferedimage;
  }
}

class Charmander extends pokemon{
  public Charmander(int l){
	name = "Charmander";
	level = l;
	maxAttack = 42;
	minAttack = 10;
	maxDefense = 43;
	maxStamina = 79;
	minStamina = 20;
	maxSpeed = 65;
	attribute = 1;
	initialize();
  }
}

class Bulbasaur extends pokemon{
  public Bulbasaur(int l){
    name = "Bulbasaur";
	level = l;
	maxAttack = 40;
	minAttack = 9;
	maxDefense = 49;
	maxStamina = 85;
	minStamina = 25;
	maxSpeed = 43;
	attribute = 2;
	initialize();
  }
}

class Squirtle extends pokemon{
  public Squirtle(int l){
    name = "Squirtle";
	level = l;
	maxAttack = 39;
	minAttack = 9;
	maxDefense = 63;
	maxStamina = 84;
	minStamina = 24;
	maxSpeed = 43;
	attribute = 3;
	initialize();
  }
}

class Chikorita extends pokemon{
  public Chikorita(int l){
    name = "Chikorita";
	level = l;
	maxAttack = 40;
	minAttack = 9;
	maxDefense = 63;
	maxStamina = 85;
	minStamina = 20;
	maxSpeed = 45;
	attribute = 2;
	initialize();
  }
}

class Cyndaquil extends pokemon{
  public Cyndaquil(int l){
    name = "Cyndaquil";
	level = l;
	maxAttack = 40;
	minAttack = 11;
	maxDefense = 43;
	maxStamina = 81;
	minStamina = 18;
	maxSpeed = 63;
	attribute = 1;
	initialize();
  }
}

class Totodile extends pokemon{
  public Totodile(int l){
    name = "Totodile";
	level = l;
	maxAttack = 50;
	minAttack = 13;
	maxDefense = 62;
	maxStamina = 90;
	minStamina = 20;
	maxSpeed = 43;
	attribute = 3;
	initialize();
  }
}