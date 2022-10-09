package batailleNavale;

import java.awt.Color;

public class GrilleNavaleGraphique extends GrilleNavale {

	private GrilleGraphique grille;
	
	//permet d'obtenir une grille de taille taille.
	public GrilleNavaleGraphique(int taille) { //MODIF MERCREDI 15
		//demander si on doit ajouter nbNavires comme parametre
		
		super(taille, 6);
		grille = new GrilleGraphique(taille);
		
	}

	public GrilleGraphique getGrilleGraphique() {
		
		return grille;
	}
	
	//Sp�cialisation de la m�thode h�rit�e de GrilleNavale. 
	//Les cases correspondant au navire ajout� doivent �tre colori�es en Color.GREEN.
	public boolean ajouteNavire(Navire n) {
			
		//super.ajouteNavire(n); //TODO : test
		
		if(super.ajouteNavire(n)) {
			
			grille.colorie(n.getDebut(),n.getFin(), Color.GREEN);
			
			return true;
		}
		return false;
		
	}
	
	//Sp�cialisation de la m�thode h�rit�e de GrilleNavale. 
	//La case correspondant au tir doit �tre colori�e en Color.RED si le tir a touch� un navire ou en Color.BLUE s'il est � l'eau.
	public boolean recoitTir(Coordonnee c) {
		
		if(super.recoitTir(c)) {
			if(super.estTouche(c)) {
				
				grille.colorie(c, Color.RED);
			}
		}else
			if(super.estALEau(c)) {
				grille.colorie(c, Color.BLUE);
			}
		
			return false;
		
	}
}
