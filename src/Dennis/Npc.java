class Npc{
  public int pokeNum;
  public pokemon po[] = new pokemon[6];
}

class You extends Npc{
  public You(){
    pokeNum = 6;
    po[0] = pokegen(1,20);
	po[1] = pokegen(2,17);
	po[2] = pokegen(3,18);
	po[3] = pokegen(4,16);
	po[4] = pokegen(5,18);
	po[5] = pokegen(6,19);
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