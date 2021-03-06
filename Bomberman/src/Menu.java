import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.princeton.cs.introcs.StdDraw;

public class Menu{
	/*
	 * valeurs prises par ChoixMenu : "home" , "multiplayers" , "IA" , "exit" , "instructions"
	 * */
	private String ChoixMenu;
	
	public Menu(){
		StdDraw.setCanvasSize(40*30,21*30);
		/*
		 * Modifier les echelles X et Y pour avoir un systeme de coordonnees (X,Y)
		 *  Coordonnees (0,0) coin en bas a gauche et (17,21) coin en haut a droite
		 *  */
        StdDraw.setXscale(0 , 30);
        StdDraw.setYscale(0 , 17);
		this.ChoixMenu = "home";
	}
	
	public String getChoixMenu() {
		return ChoixMenu;
	}


	public void setChoixMenu(String choixMenu) {
		ChoixMenu = choixMenu;
	}
	public Font bombermanFont() throws FileNotFoundException, FontFormatException, IOException{
		Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Bomberman/src/bm.ttf"));
		font = font.deriveFont(20F);
		return font;
	}
	public void game_over(int winner) throws FileNotFoundException, FontFormatException, IOException{
		int nbligne = 2;					//nombre de ligne
		int nbcolonne = 21;					//nombre de colonne
		float centerL = (float)(nbligne/2);
		float centerC = (float)(nbcolonne/2);
		int largeurRect = 3;
		float hauteurRect = 0.5f;
		
		StdDraw.clear(); // On clear la fenetre
		
		StdDraw.setFont(bombermanFont());
		StdDraw.picture(10.5,8.5, "gameover.gif",21,21);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		if(winner!=0){
			StdDraw.text(10, 11, "Le vainqueur est : Joueur " + winner);
		}else{
			StdDraw.text(10, 11, "Match null");
		}
		
		
		/*BOUTON RETOUR AU MENU PRINCIPAL*/			
		this.setChoixMenu("gameover");
				
				if (StdDraw.mouseX() >= centerC-largeurRect && StdDraw.mouseX() <= centerC+largeurRect && StdDraw.mouseY() >= centerL - hauteurRect && StdDraw.mouseY() <= centerL + hauteurRect){
					// Petit effet quand on place la souris sur le bouton
					displayEffect(centerC, centerL, largeurRect, hauteurRect,"Menu");
					if(StdDraw.mousePressed()){
						StdDraw.setCanvasSize(40*30,21*30);
						/*
						 * Modifier les echelles X et Y pour avoir un systeme de coordonnees (X,Y)
						 *  Coordonnees (0,0) coin en bas a gauche et (17,21) coin en haut a droite
						 *  */
				        StdDraw.setXscale(0 , 30);
				        StdDraw.setYscale(0 , 17);
						setChoixMenu("home");
					}
				}else {
					displayRect(centerC, centerL, largeurRect, hauteurRect,"Menu");
				}
				StdDraw.show(30);
	}
	
	public void displayRect(float colonne, float ligne, int largeur,float hauteur, String contenu) throws FileNotFoundException, FontFormatException, IOException{
		StdDraw.setPenColor(StdDraw.BLACK);								//  Couleur noir pour l'ecriture
		StdDraw.filledRectangle(colonne, ligne, largeur, hauteur);		//  Dessine un rectangle
		StdDraw.setFont(bombermanFont());
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);					//  Couleur pour l'ecriture
		StdDraw.text(colonne, ligne, contenu);
	}
	
	public void displayEffect(float colonne, float ligne, int largeur,float hauteur, String contenu) throws FileNotFoundException, FontFormatException, IOException{
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);						//  Couleur noir pour l'ecriture
		StdDraw.filledRectangle(colonne, ligne, largeur, hauteur);		//  Dessine un rectangle
		StdDraw.setFont(bombermanFont());
		StdDraw.setPenColor(StdDraw.RED);			//  Couleur pour l'ecriture
		StdDraw.text(colonne, ligne, contenu);
	}
	
	public void menu() throws FileNotFoundException, FontFormatException, IOException{ // Afficher le menu principal
		int nbligne = 17;					//nombre de ligne
		int nbcolonne = 30;					//nombre de colonne
		float centerL = (float)(nbligne/2);
		float centerC = (float)(nbcolonne/2);
		float decalage = 1.75f;
		int largeurRect = 4;
		float hauteurRect = 0.5f;			
		StdDraw.picture(15, 8, "background2.gif",30,18);
		/*BOUTON JOUER*/
		displayRect(centerC, centerL, largeurRect, hauteurRect,"Multijoueurs");		
		
		/*BOUTON IA*/
		displayRect(centerC, centerL - decalage, largeurRect, hauteurRect, "Mode 1 joueur");	
		
		/*BOUTON Instructions*/
		displayRect(centerC, centerL - 2*decalage, largeurRect, hauteurRect,"Instructions");
		
		/*BOUTON Avatar*/
		displayRect(centerC, centerL - 3*decalage, largeurRect, hauteurRect,"Avatar");
		
		/*BOUTON QUITTER*/
		displayRect(centerC, centerL - 4*decalage, largeurRect, hauteurRect, "Quitter");	

		/*On affiche le logo bomberman*/
		StdDraw.picture(15, 13, "/bomberman_picture/banner.png");
		
		/*A gauche on met le joueur 1*/
		StdDraw.picture(5, 3, "p1.gif");
		
		/*A droite le joueur 2*/
		StdDraw.picture(25, 3, "p2.gif");
		StdDraw.mousePressed(); // il faut remettre le boolean a false 

		
			if (StdDraw.mouseX() >= centerC-largeurRect && StdDraw.mouseX() <= centerC+largeurRect && StdDraw.mouseY() >= centerL - hauteurRect && StdDraw.mouseY() <= centerL + hauteurRect){
				// Petit effet quand on place la souris sur le bouton
				displayEffect(centerC, centerL, largeurRect, hauteurRect,"Jouer");		//  Dessine un rectangle
				if(StdDraw.mousePressed()){
				setChoixMenu("multiplayers");
				}
			}
			else if (StdDraw.mouseX() >= centerC-largeurRect && StdDraw.mouseX() <= centerC+largeurRect && StdDraw.mouseY() >= centerL - hauteurRect - decalage && StdDraw.mouseY() <= centerL + hauteurRect - decalage){
				displayEffect(centerC, centerL - decalage, largeurRect, hauteurRect, "Jouer");	
				if(StdDraw.mousePressed()){
				setChoixMenu("IA"); // ce sera a change
				}
			}
			else if (StdDraw.mouseX() >= centerC-largeurRect && StdDraw.mouseX() <= centerC+largeurRect && StdDraw.mouseY() >= centerL - hauteurRect - 2*decalage && StdDraw.mouseY() <= centerL + hauteurRect - 2*decalage){
				displayEffect(centerC, centerL - 2*decalage, largeurRect, hauteurRect,"Instructions");	
				if(StdDraw.mousePressed()){
				setChoixMenu("instructions"); 
				}
			}
			else if (StdDraw.mouseX() >= centerC-largeurRect && StdDraw.mouseX() <= centerC+largeurRect && StdDraw.mouseY() >= centerL - hauteurRect - 3*decalage && StdDraw.mouseY() <= centerL + hauteurRect - 3*decalage){
				displayEffect(centerC, centerL - 3*decalage, largeurRect, hauteurRect,"Avatar");	
				if(StdDraw.mousePressed()){			
				setChoixMenu("avatar");				
				}
			}
			else if (StdDraw.mouseX() >= centerC-largeurRect && StdDraw.mouseX() <= centerC+largeurRect && StdDraw.mouseY() >= centerL - hauteurRect - 4*decalage && StdDraw.mouseY() <= centerL + hauteurRect - 4*decalage){
				displayEffect(centerC, centerL - 4*decalage, largeurRect, hauteurRect,"Quitter");	
				if(StdDraw.mousePressed()){			
				setChoixMenu("exit");				
				}		
			}
	}
	public int  start_game_multi() throws FileNotFoundException, FontFormatException, IOException, UnsupportedAudioFileException, LineUnavailableException{

        //Dessiner le plateau et les joueurs
		Board game_board = new Board();
        game_board.beginGame();
        Player J1 = new Player(1); // le joueur 1 porte l'id 1 
        Player J2 = new Player(2); // le joueur 2 porte l'id 2 
        // l'objet contient une liste de Bombes vierge
        Bomb bomb_liste = new Bomb();
        Bonus bonus_liste =  new Bonus();
        Animation animation_liste = new Animation();
        // Le jeu doit reboucler a l'infini tant que les joueurs ont plus de 0 vie 
        boolean game_over = false;
		while(!game_over){

	    	   /*Gere les deplacements des joueurs 1 et 2*/
	        	J1.move(game_board,bomb_liste,J2);
	        	J2.move(game_board,bomb_liste,J1);
	        	J1.kick(bomb_liste, game_board,animation_liste,J2);
	        	J2.kick(bomb_liste, game_board,animation_liste,J1);
	        	/*Chaque joueur peut poser des bombes en appuyant soit sur espace ou sur W*/
	        	bomb_liste.putBomb(game_board, J1,bonus_liste,animation_liste,J2);
	        	bomb_liste.putBomb(game_board, J2,bonus_liste,animation_liste,J1);
	        	/*Les bombes explosent 5 secondes apres etre deposee*/
	        	bomb_liste.explose(game_board,J1,J2,bonus_liste,animation_liste);
	        	
	        	/*Les bombes et les bonus sont affichees sur le plateau de jeu */
	        	game_board.show_all_bombs(bomb_liste.getBombs());
	        	animation_liste.bomb_timer(bomb_liste);
	        	game_board.show_bonus(bonus_liste.getBonus());
	        	
	        	/*On collecte les bonus*/
	        	bonus_liste.collect_bonus(J1, J2, game_board,bomb_liste);
	        	
	        	/*On synchronise les listes bombes et bonus*/
	        	bonus_liste.synchro(bomb_liste);
	        	
	        	
	        	/*Les donnees des joueurs sont affichees dans la console (nombre de vies et coordonnees X et Y)*/
	        	//info(J1,J2);
	        	/*Animation */
	        	animation_liste.display_effects(game_board);
	        	StdDraw.show(30);
	        	/*Mise a jour du boolean game_over*/
	        	game_over = (J1.getLife() <= 0 ) || (J2.getLife() <= 0); //la partie est fini si la condition vaut TRUE
	        	game_board.info_players(J1, J2,bomb_liste,bonus_liste);
	        	StdDraw.show(30);
	        	
	        }
	      this.setChoixMenu("gameover");

	      
	      
	     
	      game_board.finalize();
	      int winner = 0 ;
	      if(J1.getLife() == 0 	&& J2.getLife() != 0 ){
	    	   winner = 2;
	    	  
	       }else if(J2.getLife() == 0 && J1.getLife() != 0){
	    	   winner = 1;
	    	   
	       }
	      this.game_over(winner);
	      return winner;
	      
	}
	
	public int  start_game_single() throws FileNotFoundException, FontFormatException, IOException, UnsupportedAudioFileException, LineUnavailableException{

        //Dessiner le plateau et les joueurs
		Board game_board = new Board();
        game_board.beginGame();
        IA J1 = new IA(1); // le joueur 1 porte l'id 1 
        Player J2 = new Player(2); // le joueur 2 porte l'id 2 
        // l'objet contient une liste de Bombes vierge
        Bomb bomb_liste = new Bomb();
        Bonus bonus_liste =  new Bonus();
        Animation animation_liste = new Animation();
        // Le jeu doit reboucler a l'infini tant que les joueurs ont plus de 0 vie 
        boolean game_over = false;
		while(!game_over){

	    	   /*Gere les deplacements des joueurs 1 et 2*/
	        	J1.move(game_board,bomb_liste,J2);
	        	J2.move(game_board,bomb_liste,J1);
	        	J1.kick(bomb_liste, game_board,animation_liste,J2);
	        	J2.kick(bomb_liste, game_board,animation_liste,J1);
	        	/*Chaque joueur peut poser des bombes en appuyant soit sur espace ou sur W*/
	        	bomb_liste.putBomb(game_board, J1,bonus_liste,animation_liste,J2);
	        	J1.add_Bomb(bomb_liste);
	        	bomb_liste.putBomb(game_board, J2,bonus_liste,animation_liste,J1);
	        	/*Les bombes explosent 5 secondes apres etre deposee*/
	        	bomb_liste.explose(game_board,J1,J2,bonus_liste,animation_liste);
	        	
	        	/*Les bombes et les bonus sont affichees sur le plateau de jeu */
	        	game_board.show_all_bombs(bomb_liste.getBombs());
	        	animation_liste.bomb_timer(bomb_liste);
	        	game_board.show_bonus(bonus_liste.getBonus());
	        	
	        	/*On collecte les bonus*/
	        	bonus_liste.collect_bonus(J1, J2, game_board,bomb_liste);
	        	
	        	/*On synchronise les listes bombes et bonus*/
	        	bonus_liste.synchro(bomb_liste);
	        	
	        	
	        	/*Les donnees des joueurs sont affichees dans la console (nombre de vies et coordonnees X et Y)*/
	        	//info(J1,J2);
	        	/*Animation */
	        	animation_liste.display_effects(game_board);
	        	StdDraw.show(30);
	        	/*Mise a jour du boolean game_over*/
	        	game_over = (J1.getLife() <= 0 ) || (J2.getLife() <= 0); //la partie est fini si la condition vaut TRUE
	        	game_board.info_players(J1, J2,bomb_liste,bonus_liste);
	        	StdDraw.show(30);
	        	
	        }
	      this.setChoixMenu("gameover");

	      
	      
	     
	      game_board.finalize();
	      int winner = 0 ;
	      if(J1.getLife() == 0 	&& J2.getLife() != 0 ){
	    	   winner = 2;
	    	  
	       }else if(J2.getLife() == 0 && J1.getLife() != 0){
	    	   winner = 1;
	    	   
	       }
	      this.game_over(winner);
	      return winner;
	      
	}
	public void finalize(){
		
	}
}