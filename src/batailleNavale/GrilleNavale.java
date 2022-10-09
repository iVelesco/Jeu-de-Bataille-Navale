package batailleNavale;

public class GrilleNavale {
	private Navire[] navires;
	private int nbNavires; // nombre de navires d�j� plac�s sur la grille
	private int taille;
	private Coordonnee[] tirsRecus;
	private int nbTirsRecus;
	static protected int[][] tabChoixNavires = { { 2, 2, 3, 3 }, { 2, 2, 3, 4 , 5 }, { 2, 2, 3, 3, 4, 5 }, { 4, 4, 4, 4, 4, 4, 5 } };

	/*----------------------------------CONSTRUCTEURS----------------------------------------------*/

	/**
	 * 	permet d'obtenir une grille navale vide de taille taille pouvant accueillir
	* jusqu'� nbNavires
	 * @param taille
	 * @param nbNavires
	 */
	public GrilleNavale(int taille, int nbNavires) {
		if (taille < 2 || taille > 26)
			throw new IllegalArgumentException("taille doit etre entre 2 et 26");
		if (nbNavires < 1 || nbNavires > 15)
			throw new IllegalArgumentException("nbNavires doit etre entre 1 et 15");
		this.nbNavires = 0;
		navires = new Navire[15]; // navires est rempli de null
		this.taille = taille;
		tirsRecus = new Coordonnee[taille * taille]; // tirsRecus a comme taille le nombre de cases de la grille
		nbTirsRecus = 0;
			
	}

	public GrilleNavale(int taille, int[] taillesNavires) {
		/*
		 * permet d'obtenir une grille navale de taille taille dans laquelle ont �t�
		 * plac�s automatiquement taillesNavires.length navires dont les tailles sont
		 * donn�es dans taillesNavires.
		 */
		this(taille, taillesNavires.length); // Appel au premier constructeur
		for (int i = 0; i < taillesNavires.length; i++)
			if (taillesNavires[i] < 1)
				throw new IllegalArgumentException("taillesNavires doit contenir des entiers sup�rieurs ou �gaux � 1");
		placementAuto(taillesNavires);
	}

	/*--------------------------------METHODES-----------------------------------*/

	public String toString() {
		StringBuffer sb = new StringBuffer("   ");

		// Ecriture de la premi�re ligne
		for (int i = 0; i < taille; i++) {
			sb.append((char) ('A' + i) + "  ");
		}
		sb.append("\n");

		// Remplit le tableau de l'indice de ligne puis de '.'
		for (int i = 1; i <= taille; i++) {
			sb.append(i + " ");
			if (i < 10)
				sb.append(' ');
			for (int j = 1; j <= taille; j++)
				sb.append(".  ");
			sb.append("\n");
		}

		// Remplit la grille � partir des coordonnees i et j
		int ind;// ind sera l'indice de la Buffer qui correspond � la cordonnee (i,j)
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				Coordonnee c = new Coordonnee(i, j);
				String numLigne = Integer.toString(i + 1); // On recupere le charactere du numero de ligne
				ind = sb.indexOf("" + numLigne); // on recupere l'indice du numero de ligne
				ind += (1 + j) * 3; // Chaque "case" de la grille est composee de 3 caracteres

				// On parcourt navires pour savoir s'il occupe la coordonnee (i,j)
				for (int k = 0; k < nbNavires; k++) {
					if (navires[k].contient(c)) { // si un navire occupe la coordonnee (i,j)
						sb.setCharAt(ind, '#');
						// Pour les tests
						// System.out.println("i : " + i + " ,j : " + j + " ,ind : " + ind + " ,numLigne
						// : " + numLigne);
					}
				}
				if (estDansTirsRecus(c)) {
					if (estALEau(c))
						sb.setCharAt(ind, 'O');
					else if (estTouche(c))
						sb.setCharAt(ind, 'X');
				}
			}
		}
		return sb.toString();
	}

	public void placementAuto(int[] taillesNavires) {
		/*
		 * Place automatiquement et al�atoirement taillesNavires.length navires dont les
		 * tailles sont donn�es dans taillesNavire.
		 */
		for (int i = 0; i < taillesNavires.length; i++) {
			Navire n;
			int ligneAleatoire, colonneAleatoire;
			do {
				// Creation al�atoire des attributs du nouveau navire n
				boolean estVertical = (int) (Math.random() * 2) == 0;
				if (estVertical) {
					ligneAleatoire = (int) (Math.random() * (taille - taillesNavires[i]));
					colonneAleatoire = (int) (Math.random() * taille);
				} else {
					colonneAleatoire = (int) (Math.random() * (taille - taillesNavires[i]));
					ligneAleatoire = (int) (Math.random() * taille);
				}
				Coordonnee debut = new Coordonnee(ligneAleatoire, colonneAleatoire);

				// Cr�ation du nouveau navire n
				n = new Navire(debut, taillesNavires[i], estVertical);
			} while (!ajouteNavire(n)); // Cr�e des navires jusqu'a qu'un navire soit compatible et donc ajout�
		}
	}

	public boolean ajouteNavire(Navire n) {
		/*
		 * Retourne true apr�s avoir ajout� n � this si cet ajout est possible. L'ajout
		 * est impossible si n touche ou chevauche un navire d�j� pr�sent dans this, ou
		 * encore si n d�passe les limites de this.
		 */
		if (navires.length == nbNavires) {
			// si la capacit� max de navire sur la grille == au nbr de navire d�j� plac�s
			return false;
		}

		if (!(estDansGrille(n.getDebut()) && estDansGrille(n.getFin())))// teste si la premi�re et la derni�re case
			return false; // du nouveau Navire n rentrent dans la grille
		for (int j = 0; j < nbNavires; j++) {
			if (n.touche(navires[j])) // teste si le nouveau Navire n touche un navire deja pr�sent
				return false;
			if (n.chevauche(navires[j])) // teste si le nouveau Navire n chevauche un navire deja present
				return false;
		}
		navires[nbNavires] = n; // Si on arrive ici, n peut etre ajout�
		nbNavires++; // on incremente le nombre de navires plac�s sur la grille
		return true;
	}

	private boolean estDansGrille(Coordonnee c) {
		// Retourne true si et seulement si c est dans this.
		Coordonnee derniereCase = new Coordonnee(taille - 1, taille - 1);
		return c.compareTo(derniereCase) <= 0; // teste si c est inf�rieur � la derniere case de la grille
	}

	public int getTaille() {
		return taille;
	}

	private boolean estDansTirsRecus(Coordonnee c) {
		// Retourne true si et seulement si c correspond � un tir re�u par this.

		for (int i = 0; i < nbTirsRecus; i++) {
			if (tirsRecus[i].equals(c))
				return true;
		}
		return false;
	}

	private boolean ajouteDansTirsRecus(Coordonnee c) {
		// Ajoute c aux tirs re�us de this si n�cessaire. Retourne true si et seulement
		// si this est modifi�.

		if (this.estDansTirsRecus(c)) {
			return false;
		}
		tirsRecus[nbTirsRecus] = c;// indice de la premi�re case de tir re�u dispo = nb tir re�us
		nbTirsRecus++;
		return true;
	}

	public boolean recoitTir(Coordonnee c) {
		// Ajoute c aux tirs re�us de this si n�cessaire. Retourne true si et seulement
		// si c ne correspondait
		// pas d�j� � un tir re�u et a permis de toucher un navire de this.
		if (!this.ajouteDansTirsRecus(c)) { // On ajoute c dans les tirs re�us. Si le tab
											// de tir re�u contenait d�j� c, on renvoie faux
			return false;
		}
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].recoitTir(c))// renvoie vrai si navires[i] contient c, auquel cas c est ajout� aux
				return true; // parties touch�es du navire
		}
		return false; // pour effacer probl�me de compilation

	}

	public boolean estTouche(Coordonnee c) {
		// Retourne true si et seulement si un des navires pr�sents dans this a �t�
		// touch� en c.
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].estTouche(c))
				return true;
		}
		return false; // On a parcouru tout le tableau de navires sans rencontrer de navire touch� en
						// c
	}

	public boolean estALEau(Coordonnee c) {
		// Retourne true si et seulement si c correspond � un tir re�u dans l'eau par
		// this.
		if (!estDansTirsRecus(c))
			return false;
		else {// c est dans les tirs recus
			if (estTouche(c)) // Un navire est touch� en c
				return false;
			else
				return true; // c est dans les tirs re�us et aucun navire n'est touche en c
		}
	}

	public boolean estCoule(Coordonnee c) {
		// Retourne true si et seulement si un des navires pr�sents dans this a �t�
		// touch� en c et est coul�
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].estTouche(c) && navires[i].estCoule())
				return true;
		}
		return false;// On a parcouru tout le tableau sans trouver de navire touch� en c et coul�
	}

	public boolean perdu() {
		// Retourne true ssi tous les navires de this ont �t� coul�s
		for (int i = 0; i < nbNavires; i++) {
			if (!navires[i].estCoule())
				return false; // Si un bateau n'est pas coul�, on renvoie faux
		}
		return true;
	}
	
	//TODO : MODIF DU MERCREDI 15
	protected static int[] choixNavires(int taille, int [][] tab) {
		// Ici on choisit un �l�ment du tableau selon la taille choisie, ce qui va
		// donner un nombre de bateaux et leur taille

		if (taille < 10) {
			return tab[0];
		} else if (taille < 15) {
			return tab[1];
		} else if (taille < 20) {
			return tab[2];
		} else {
			return tab[3];
		}
		
	}
	//FIN MODIF MERCREDI 15


//	public static void main(String[] args) {
//		int taille = 7;
//		int[] tailleNavires = { 4, 4 };
//		GrilleNavale gn = new GrilleNavale(taille, tailleNavires);
//		for (int i = 0; i < gn.nbNavires; i++)
//			System.out.println("debut : " + gn.navires[i].getDebut() + ", fin : " + gn.navires[i].getFin());
//		for (int i = 0; i < taille - 1; i++)
//			for (int j = 0; j < taille; j++)
//				gn.recoitTir(new Coordonnee(i, j));
//		System.out.println("navires[0] touch� ? : " + gn.navires[0].estTouche());
//		System.out.println("est coul� ? : " + gn.estCoule(new Coordonnee("A1")));
//		System.out.println("perdu ? : " + gn.perdu());
//		System.out.println(gn.toString());
//
//	}

}