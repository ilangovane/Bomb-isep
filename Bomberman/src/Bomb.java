import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.princeton.cs.introcs.StdDraw;


public class Bomb {
	public int owner_id; // id de celui qui a d�pos� la bombe id:1<=>J1 | id:2<=>J2 | id:3 <=> IA
	public long t_explosion ; // le timestamps de l'explosion
	public int X; // coordonn�e X de la bombe
	public int Y; //coordonn�e Y de la bombe
	Set<Bomb> Bombs = new HashSet<Bomb>();
	//choix de deux Constructeurs
	//lors de la cr�ation d'une bombe
	public Bomb(int id,int line, int column){
		this.owner_id = id;
		this.t_explosion = System.currentTimeMillis() + 5000 ; // la bombe explose 5 secondes apr�s �tre d�pos�e
		this.X = column;
		this.Y = line;
	}
	
	//lors de la cr�ation de la liste de bombs
	public Bomb(){
		
	}
	
	/*Getters et Setters*/
	
	public int getOwner_id() {
		return owner_id;
	}



	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}



	public long getT_explosion() {
		return t_explosion;
	}



	public void setT_explosion(long t_explosion) {
		this.t_explosion = t_explosion;
	}



	public int getX() {
		return X;
	}



	public void setX(int x) {
		X = x;
	}



	public int getY() {
		return Y;
	}



	public void setY(int y) {
		Y = y;
	}



	public Set<Bomb> getBombs() {
		return Bombs;
	}

	public void setBombs(Set<Bomb> bombs) {
		Bombs = bombs;
	}

	public int getNbBombs(int id) {// compte le nombre de bombes dans la  liste Bombs contenant l'owner_id = 2
		Iterator<Bomb> it = this.Bombs.iterator();
		int c = 0;//nb de bombe du joueur J1
		while (it.hasNext()){//parcours la liste de bombe
			Bomb bo = it.next();
			if(bo.getOwner_id() == id){
				c++;
			}
		}
		return c;
	}

	/*public void setNb_J1(int nb_J1) {
		this.nb_J1 = nb_J1;
	}

	public int getNb_J2() {// compte le nombre de bombes dans la  liste Bombs contenant l'owner_id = 2
		Iterator<Bomb> it = this.Bombs.iterator();
		int c = 0;//nb de bombe du joueur J1
		while (it.hasNext()){//parcours la liste de bombe
			Bomb bo = it.next();
			if(bo.getOwner_id() == 2){
				c++;
			}
		}
		return c;
	}

	public void setNb_J2(int nb_J2) {
		this.nb_J2 = nb_J2;
	}*/

	
	/*Ajoute une bombe � la liste HashSet Bombs*/
	public void addBomb(int id , int x , int y){
		Bombs.add(new Bomb(id,y,x));
		this.setBombs(Bombs);
	}
	/*Lorsque la touche espace ou W est enfonc�e les bombes s'ajoutent � la liste aucun doublons n'est tol�r�
	 * Un doublons => bombes au m�me emplacement aux coordonn�es (X,Y) d'ou la m�thode this.is_bomb_already_exists(x, y)*/
	public void putBomb(Board b ,int id , int x , int y){
		if(StdDraw.isKeyPressed(KeyEvent.VK_W) && id == 1){//touche W press�e
			if(!this.is_bomb_already_exists(x, y) && this.getNbBombs(1) <4){
				addBomb(id , x , y);
				
				b.setBomb(x,y);
			}
			
		}
		
		if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && id == 2){//touche ESPACE press�e
   		 
			if(!this.is_bomb_already_exists(x, y) && this.getNbBombs(2) <4){
				addBomb(id , x , y);
				
				b.setBomb(x,y);
				
			}
		}
		
	}
	
	//avant d'ajouter la liste � la bombe il faut v�rifier qu'elle n'existe pas pour �viter les doublons 
	public boolean is_bomb_already_exists(int x,int y){
		Iterator<Bomb> it = Bombs.iterator();
		boolean exist = false;
		while (it.hasNext()){//parcours la liste de bombes
			Bomb bo = it.next();
			if((int) bo.getX() == x && (int) bo.getY() == y){
				exist = true;
			}
		
		}
		
		return exist;
	}
	
	/*Renvoie la bombe ayant les coordonn�es suivantes : (X,Y) = (x,y)*/
	public Bomb find_Bomb(int x , int y){
		Iterator<Bomb> it = Bombs.iterator();
		Bomb find = new Bomb();//bombe null
		while (it.hasNext()){//parcours la liste de bombes
			
			Bomb bo = it.next();
			if((int) bo.getX() == x && (int) bo.getY() == y){
				find =  bo;
			}
		
		}
		return find;
		
	}

	//modifie le timer de l'explosion d'une bombe � "maintenant" 
	public void explose_bomb_around(Bomb bo , Board b , Player J1 , Player J2){
		bo.setT_explosion(System.currentTimeMillis());
		bo.Bombs.add(bo);
		bo.explose(b, J1, J2);
	}
	

	/*Le joeurs qui vient de perdre la vie ne risque pas de perdre une vie d�s qu'il sera replac� sur une case de d�part
	 * Le joueur est positionn� sur le recoin (X,Y) = (0,0) si des bombes sont a proximit�s
	 * Cela permettra d'�viter de predre 2 ou 3 vie au lieu d'une*/
	public void avoid_kill_player_two_times(Board b,Player J){
		int i = 0;
		boolean safe = true;
		if(J.getId() == 1 ){
			while(i<=3){
				if(J.getX() == -1  && J.getY() == -1 && ( this.is_bomb_already_exists(1, 1+i) ||this.is_bomb_already_exists(i+1,1))  ){

					safe = false;
				}
				i++;
			}
			if(safe == true && J.getX() == -1  && J.getY() == -1 && !this.is_bomb_already_exists(1, 1+i) && !this.is_bomb_already_exists(i+1,1)){
				J.setX(1);  
				J.setY(1);
				b.setArea(0,0, "grey");
			}
		}else if(J.getId() ==2){

			while(i<=3){
				if(J.getX() == -1  && J.getY() == -1 && ( this.is_bomb_already_exists(19, 15-i) ||this.is_bomb_already_exists(19-i,15))  ){
					safe = false;
				}
				i++;
			}
			if(safe == true && J.getX() == -1  && J.getY() == -1 &&  !this.is_bomb_already_exists(1, 1+i) && !this.is_bomb_already_exists(i+1,1)) {
				J.setX(19);  
				J.setY(15);
				b.setArea(0,0, "grey");
			}
		}
		

	}
	/*Retire les bombes de la liste lors de l'explosion
	 * D�truit les murs jusqu'au mur cassable */
	public void explose(Board b,Player J1, Player J2){ 
		Iterator<Bomb> it = Bombs.iterator();
		int i;
		while (it.hasNext()){//parcours la liste de bombe

			Bomb bo = it.next();
		
			if( bo.getT_explosion() <  System.currentTimeMillis() ){ // le minuteur prend fin
				i=1;
				// la bomb a une port�e de 3 et s'arrete au mur incassable dans toute les directions
				
				while(b.isDestructible(bo.getY()+i, bo.getX()) && i<=3){// soit case verte un mur cassable
					if(b.isWallDestructible(bo.getY()+i, bo.getX()) ){
						b.setArea(bo.getY() +i, bo.getX(), "green");// coloration case verte 
						b.setElementMatrice(bo.getY() +i, bo.getX(), 3); // matrice mis � jour 
						break ;// on sort de la boucle 
					}
					if(J1.is_at_point(bo.getX(), bo.getY()+i)){
			
						J1.kill();
						b.setPlayer(1,1,1);
						J1.setX(-1);
						J1.setY(-1);
						b.setArea(bo.getY()+i, bo.getX(), "green");
					}
					if(J2.is_at_point(bo.getX(), bo.getY()+i) ){
						J2.kill();
		
						b.setPlayer(2,19,15);
						J2.setX(-1);
						J2.setY(-1);
						b.setArea(bo.getY()+i, bo.getX(), "green");
					}
					
					this.explose_bomb_around(this.find_Bomb(bo.getX(),bo.getY()+ i),b, J1, J2);
					i ++;
				

				}
				
				i=1;
				while(b.isDestructible(bo.getY()-i, bo.getX()) && i<=3){	
					if(b.isWallDestructible(bo.getY()-i, bo.getX()) ){
						b.setArea(bo.getY()-i, bo.getX(), "green");
						b.setElementMatrice(bo.getY()-i, bo.getX(), 3);
						i=10; ;// on sort de la boucle 
					}
					if(J1.is_at_point(bo.getX(), bo.getY()-i) ){
		
						J1.kill();
						b.setPlayer(1,1,1);
						J1.setX(-1);
						J1.setY(-1);
						b.setArea(bo.getY()-i, bo.getX(), "green");
					}
					if(J2.is_at_point(bo.getX(), bo.getY()-i) ){
						J2.kill();
	
						b.setPlayer(2,19,15);
						J2.setX(-1);
						J2.setY(-1);
						b.setArea(bo.getY()-i, bo.getX(), "green");
					}
					this.explose_bomb_around(this.find_Bomb(bo.getX(),bo.getY()-i),b, J1, J2);
					i++;
				
					
				}
				
				i=1;
				while(b.isDestructible(bo.getY(), bo.getX()+i) && i<=3){
					if(b.isWallDestructible(bo.getY(), bo.getX()+i) ){
						b.setArea(bo.getY(), bo.getX()+i, "green");
						b.setElementMatrice(bo.getY(), bo.getX()+i, 3);
						i=10; ;// on sort de la boucle 
					}
					if(J1.is_at_point(bo.getX()+i, bo.getY()) ){
						J1.kill();
		
						b.setPlayer(1,1,1);
						J1.setX(-1);
						J1.setY(-1);
						b.setArea(bo.getY(), bo.getX()+i, "green");
					}
					if(J2.is_at_point(bo.getX()+i, bo.getY()) ){
						J2.kill();

						b.setPlayer(2,19,15);
						J2.setX(-1);
						J2.setY(-1);
						b.setArea(bo.getY(), bo.getX()+i, "green");
					}
					this.explose_bomb_around(this.find_Bomb(bo.getX()+i,bo.getY()),b, J1, J2);
					i++;
					
					
				}
				i=1;
				while(b.isDestructible(bo.getY(), bo.getX()-i) && i<=3){
					if(b.isWallDestructible(bo.getY(), bo.getX()-i) ){
						b.setArea(bo.getY(), bo.getX()-i, "green");
						b.setElementMatrice(bo.getY(), bo.getX()-i, 3);
						i=10 ;// on sort de la boucle 
					}
					if(J1.is_at_point(bo.getX()-i, bo.getY()) ){
						J1.kill();

						b.setPlayer(1,1,1);
						J1.setX(-1);
						J1.setY(-1);
						b.setArea(bo.getY(), bo.getX()-i, "green");
					}
					if(J2.is_at_point(bo.getX()-i, bo.getY()) ){
						J2.kill();

						b.setPlayer(2,19,15);
						J2.setX(-1);
						J2.setY(-1);
						b.setArea(bo.getY(), bo.getX()-i, "green");
					}
					this.explose_bomb_around(this.find_Bomb(bo.getX()-i,bo.getY()),b, J1, J2);
					i++;
				
				}

				it.remove(); // bombe supprim�e de la liste Bombs
	        	this.avoid_kill_player_two_times(b,J1);
	        	this.avoid_kill_player_two_times(b,J2);
				b.setArea(bo.getY(), bo.getX(), "green");// il faut faire disparaitre la bombe de l'�cran en recoloriant la case en verte
			}

			
			

		}

		
	}
	
}
