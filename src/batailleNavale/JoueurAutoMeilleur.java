package batailleNavale;

public class JoueurAutoMeilleur extends JoueurAleatoireSouvenir {

	/*-----------------------CONSTANTES--------------------------*/
	
	public final static int DESSUS = 0;
	public final static int DROITE = 1;
	public final static int DESSOUS = 2;
	public final static int GAUCHE = 3;

	/*-----------------------ATTRIBUTS---------------------------*/
	
	boolean navireAFinir;
	Coordonnee toucheeInitiale;
	/** Repr�sente � combien de cases par rapport � la case initiale touch�e va-t-on tirer
	 */
	int decalageInitial; 	
	
	/**Direction courante
	 */
	int direction;
	boolean[] directionsVerifiees = new boolean[4];
	Coordonnee[] bateauxTouches;
	int nbBateauxTouches;
	Coordonnee[] bateauxCoules;
	int nbBateauxCoules;
	int[] tailleNavires;
	
	/*----------------------CONSTRUCTEUR----------------------*/
	
	public JoueurAutoMeilleur(GrilleNavale g, String nom, int[] tailleNavires) {
		super(g, nom);
		decalageInitial = 1;
		bateauxTouches = new Coordonnee[50]; //50 arbitraire
		nbBateauxTouches = 0;
		bateauxCoules = new Coordonnee[50]; //50 arbitraire
		nbBateauxCoules = 0;
		this.tailleNavires = tailleNavires;
		// Tous les booleens sont � false, c'est ce qu'il faut
	}

	
	/*-------------------------METHODES------------------------*/
	
	protected void retourAttaque(Coordonnee c, int etat) {
		//TOUCHE
		if (etat == TOUCHE) {
			//Si on touche un nouveau navire
			if (!navireAFinir) {
				navireAFinir = true;
				toucheeInitiale = c;
			} else { 
				decalageInitial++;
				
				//Si on touche en dessus ou dessous de la case initiale, on sait que le navire est vertical
				if (direction == DESSUS || direction == DESSOUS) 
					directionsVerifiees[DROITE] = directionsVerifiees[GAUCHE] = true;
				else // Le navire est horizontal
					directionsVerifiees[DESSUS] = directionsVerifiees[DESSOUS] = true;
			}
			toucheBord(c);
			ajouteBateauxTouches(c);
		}
		//COULE
		else if (etat == COULE) {
			reinitialise();
			ajouteBateauxTouches(c);
			modifieTailleNavires();
			bateauCoule();		
		} 
		//A L'EAU
		else if (etat == A_L_EAU) {
			if (navireAFinir) 
				directionVerifiee();
		}
	}
	
	/** Modifie le tableau tailleNavires afin que le joueur auto connaisse la plus petite taille de bateau restante
	 */
	private void modifieTailleNavires() {
		int tailleNavire = nbBateauxTouches - nbBateauxCoules; 
		int i; 
		for (i = 0 ; tailleNavires[i] != tailleNavire ; i++) {
		} // i est un indice de tailleNavires correspondant au bateau coul�
		decalerGauche(tailleNavires, i);
	}
	
	/** Supprime t[i] et d�cale tous les �l�ments � sa droite vers la gauche
	 */
	private void decalerGauche(int[] t, int i) {
		for (; i < t.length - 1 ; i++)
			t[i] = t[i+1];
	}
	
	/**Ajoute c au tableau des bateaux touch�s
	 */
	private void ajouteBateauxTouches(Coordonnee c) {
		bateauxTouches[nbBateauxTouches] = c;
		nbBateauxTouches++;
	}
	
	/**
	 * Met � jour le tableau de bateaux coul�s
	 */
	private void bateauCoule() {
		for ( int i = nbBateauxCoules ; i < nbBateauxTouches ; i++) {
			bateauxCoules[i] = bateauxTouches[i];
			nbBateauxCoules++;
		}
	}
	
	/**Met � jour les directions � verifier si on touche un bord en c
	 */
	private void toucheBord(Coordonnee c) {
		// Touche bord dessus
		if (c.getLigne() == 0) {
			directionsVerifiees[DESSUS] = true;
			if (direction == DESSUS)
				decalageInitial = 1;
		}
		// Touche bord dessous
		else if (c.getLigne() == grille.getTaille() - 1) {
			directionsVerifiees[DESSOUS] = true;
			if (direction == DESSOUS)
				decalageInitial = 1;
		}
		// Touche bord gauche
		else if (c.getColonne() == 0) {
			directionsVerifiees[GAUCHE] = true;
			if (direction == GAUCHE)
				decalageInitial = 1;
		}
		// Touche bord droite
		else if (c.getColonne() == grille.getTaille()-1) {
			directionsVerifiees[DROITE] = true;
			if (direction == DROITE)
				decalageInitial = 1;
		}
	}
	
	/**
	 * Consid�re la direction actuelle comme v�rifi�e
	 */
	private void directionVerifiee() {
		
		//int i;
		//for (i = 0; i < 4 && directionsVerifiees[i]; i++) {
		//} // i vaut d�sormais la premiere direction non v�rifi�e
		
		//TODO Nouvelle version
		directionsVerifiees[direction] = true; 
		decalageInitial=1;
	}

	/**
	 * R�initialise les attributs permettant de terminer un bateau entam�
	 */
	private void reinitialise() {
		for (int i = 0; i < 4; i++) {
			directionsVerifiees[i] = false;
			navireAFinir = false;
			decalageInitial = 1;
			direction = 0; //TODO n�cessaire?
		}

	}

	public Coordonnee choixAttaque() {
		Coordonnee choix;
		int ligne, col;
		
		//CAS DE NAVIRE NON ENTAME
		if (!navireAFinir) {
			do {
				choix = coordonneeAleatoireSouvenir();
				
				//Cens� optimiser la recherche al�atoire
				if (!bateauContenable(choix))
					tabCoordPrecedents[choix.getLigne()][choix.getColonne()] = true;
				
			} while (toucheBateauCoule(choix) || !bateauContenable(choix));
		} 
		//CAS DE NAVIRE ENTAME
		else {		
			do {
				for (direction = 0; direction < 4 && directionsVerifiees[direction] ; direction++) {
				} // direction vaut d�sormais la premiere direction non v�rifi�e

				//CREATION DE LA NOUVELLE COORDONNEE A TESTER
				ligne = toucheeInitiale.getLigne();
				col = toucheeInitiale.getColonne();
				if (direction == DESSUS) {
					ligne = ligne - decalageInitial;
				} else if (direction == DROITE) {
					col = col + decalageInitial;
				} else if (direction == DESSOUS) {
					ligne = ligne + decalageInitial;
				} else if (direction == GAUCHE) {
					col = col - decalageInitial;
				}
				choix = new Coordonnee(ligne, col);
				///////////////////////////////////
				
				//Si on a d�j� tir� dans cette direction, ou qu'on serait en contact avec un bateau coul� dans cette direction, 
				//on passe � la direction suivante
				if (estDejaTire(choix)) {
					directionVerifiee();
				} 
				if (toucheBateauCoule(choix))
					directionVerifiee();
				
			} while (estDejaTire(choix) || toucheBateauCoule(choix));
			
			tabCoordPrecedents[ligne][col] = true;
		}
		return choix;	
	}
	
	/**
	 * @return vrai si le plus petit bateau restant parmi ceux � couler peut rentrer en c
	 */
	private boolean bateauContenable(Coordonnee c) {
		//regarde pour chaque direction si tailleNavires[0] peut rentrer
		int tailleMinimum = tailleNavires[0];
		int bloque = 0; // nombre de directions bloqu�es
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 1 ; j < tailleMinimum ; j++) {
				Coordonnee c2;
				if (i == DESSUS) {
					if (c.getLigne() - j >= 0) {
						c2 = new Coordonnee(c.getLigne() - j, c.getColonne());
						if (estDejaTire(c2) || toucheBateauCoule(c2)) {
							bloque++;
							break;
						}
					} else {
						bloque++;
						break;
						}
				} else if (i == DROITE){
					if (c.getColonne() + j < getTailleGrille()) {
						c2 = new Coordonnee(c.getLigne(), c.getColonne() + j);
						if (estDejaTire(c2) || toucheBateauCoule(c2)) {
							bloque++;
							break;
						}
					}  else {
						bloque++;
						break;
					}
				} else if (i == DESSOUS){
					if (c.getLigne() + j < getTailleGrille()) {
						c2 = new Coordonnee(c.getLigne() + j, c.getColonne());
						if (estDejaTire(c2) || toucheBateauCoule(c2)) {
							bloque++;
							break;
						}
					} else {
						bloque++;
						break;
					}
				} else if (i == GAUCHE){
					if (c.getColonne() - j >= 0) {
						c2 = new Coordonnee(c.getLigne(), c.getColonne() - j);
						if (estDejaTire(c2) || toucheBateauCoule(c2)) {
							bloque++;			
							break;
						}
					}  else {
						bloque++;
						break;
					}
				}
			}
		}
		return bloque != 4;
	}
	
	/**
	 * @return vrai si c touche un bateau coul� 
	 */
	private boolean toucheBateauCoule(Coordonnee c) {
		Coordonnee cVoisine;
		for (int i = 0 ; i < nbBateauxCoules ; i++) {
			for (int j = -1 ; j <= 1 ; j+=2) {
				if (c.getLigne() >= 1 && c.getLigne() <= getTailleGrille()-1) {
					cVoisine = new Coordonnee(c.getLigne()+j, c.getColonne());
					if (cVoisine.equals(bateauxCoules[i]))
						return true;
				}
			}
			for (int j = -1 ; j <= 1 ; j+=2) {
				if (c.getColonne() >= 1 && c.getColonne() <= getTailleGrille()-1) {
					cVoisine = new Coordonnee(c.getLigne(), c.getColonne() + j);
					if (cVoisine.equals(bateauxCoules[i]))
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return vrai si le joueur auto a d�j� tir� en c, ou si c est en dehors des limites
	 */
	public boolean estDejaTire(Coordonnee c) {
		boolean horsLimite;
		horsLimite = c.getLigne() < 0 || c.getLigne() >= getTailleGrille() || c.getColonne() < 0 || c.getColonne() >= getTailleGrille();
		return horsLimite || tabCoordPrecedents[c.getLigne()][c.getColonne()]; 
	}

	public static void main(String[] args) {
		

	}

}
