package batailleNavale;

public class GrilleNavale {
	private Navire[] navires;
	private int nbNavires; // nombre de navires déjà placés sur la grille
	private int taille;
	private Coordonnee[] tirsRecus;
	private int nbTirsRecus;
	static protected int[][] tabChoixNavires = { { 2, 2, 3, 3 }, { 2, 2, 3, 4 , 5 }, { 2, 2, 3, 3, 4, 5 }, { 4, 4, 4, 4, 4, 4, 5 } };

	/*----------------------------------CONSTRUCTEURS----------------------------------------------*/

	/**
	 * 	permet d'obtenir une grille navale vide de taille taille pouvant accueillir
	* jusqu'à nbNavires
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
		 * permet d'obtenir une grille navale de taille taille dans laquelle ont été
		 * placés automatiquement taillesNavires.length navires dont les tailles sont
		 * données dans taillesNavires.
		 */
		this(taille, taillesNavires.length); // Appel au premier constructeur
		for (int i = 0; i < taillesNavires.length; i++)
			if (taillesNavires[i] < 1)
				throw new IllegalArgumentException("taillesNavires doit contenir des entiers supérieurs ou égaux à 1");
		placementAuto(taillesNavires);
	}

	/*--------------------------------METHODES-----------------------------------*/

	public String toString() {
		StringBuffer sb = new StringBuffer("   ");

		// Ecriture de la première ligne
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

		// Remplit la grille à partir des coordonnees i et j
		int ind;// ind sera l'indice de la Buffer qui correspond à la cordonnee (i,j)
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
		 * Place automatiquement et aléatoirement taillesNavires.length navires dont les
		 * tailles sont données dans taillesNavire.
		 */
		for (int i = 0; i < taillesNavires.length; i++) {
			Navire n;
			int ligneAleatoire, colonneAleatoire;
			do {
				// Creation aléatoire des attributs du nouveau navire n
				boolean estVertical = (int) (Math.random() * 2) == 0;
				if (estVertical) {
					ligneAleatoire = (int) (Math.random() * (taille - taillesNavires[i]));
					colonneAleatoire = (int) (Math.random() * taille);
				} else {
					colonneAleatoire = (int) (Math.random() * (taille - taillesNavires[i]));
					ligneAleatoire = (int) (Math.random() * taille);
				}
				Coordonnee debut = new Coordonnee(ligneAleatoire, colonneAleatoire);

				// Création du nouveau navire n
				n = new Navire(debut, taillesNavires[i], estVertical);
			} while (!ajouteNavire(n)); // Crée des navires jusqu'a qu'un navire soit compatible et donc ajouté
		}
	}

	public boolean ajouteNavire(Navire n) {
		/*
		 * Retourne true après avoir ajouté n à this si cet ajout est possible. L'ajout
		 * est impossible si n touche ou chevauche un navire déjà présent dans this, ou
		 * encore si n dépasse les limites de this.
		 */
		if (navires.length == nbNavires) {
			// si la capacité max de navire sur la grille == au nbr de navire déjà placés
			return false;
		}

		if (!(estDansGrille(n.getDebut()) && estDansGrille(n.getFin())))// teste si la première et la dernière case
			return false; // du nouveau Navire n rentrent dans la grille
		for (int j = 0; j < nbNavires; j++) {
			if (n.touche(navires[j])) // teste si le nouveau Navire n touche un navire deja présent
				return false;
			if (n.chevauche(navires[j])) // teste si le nouveau Navire n chevauche un navire deja present
				return false;
		}
		navires[nbNavires] = n; // Si on arrive ici, n peut etre ajouté
		nbNavires++; // on incremente le nombre de navires placés sur la grille
		return true;
	}

	private boolean estDansGrille(Coordonnee c) {
		// Retourne true si et seulement si c est dans this.
		Coordonnee derniereCase = new Coordonnee(taille - 1, taille - 1);
		return c.compareTo(derniereCase) <= 0; // teste si c est inférieur à la derniere case de la grille
	}

	public int getTaille() {
		return taille;
	}

	private boolean estDansTirsRecus(Coordonnee c) {
		// Retourne true si et seulement si c correspond à un tir reçu par this.

		for (int i = 0; i < nbTirsRecus; i++) {
			if (tirsRecus[i].equals(c))
				return true;
		}
		return false;
	}

	private boolean ajouteDansTirsRecus(Coordonnee c) {
		// Ajoute c aux tirs reçus de this si nécessaire. Retourne true si et seulement
		// si this est modifié.

		if (this.estDansTirsRecus(c)) {
			return false;
		}
		tirsRecus[nbTirsRecus] = c;// indice de la première case de tir reçu dispo = nb tir reçus
		nbTirsRecus++;
		return true;
	}

	public boolean recoitTir(Coordonnee c) {
		// Ajoute c aux tirs reçus de this si nécessaire. Retourne true si et seulement
		// si c ne correspondait
		// pas déjà à un tir reçu et a permis de toucher un navire de this.
		if (!this.ajouteDansTirsRecus(c)) { // On ajoute c dans les tirs reçus. Si le tab
											// de tir reçu contenait déjà c, on renvoie faux
			return false;
		}
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].recoitTir(c))// renvoie vrai si navires[i] contient c, auquel cas c est ajouté aux
				return true; // parties touchées du navire
		}
		return false; // pour effacer problème de compilation

	}

	public boolean estTouche(Coordonnee c) {
		// Retourne true si et seulement si un des navires présents dans this a été
		// touché en c.
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].estTouche(c))
				return true;
		}
		return false; // On a parcouru tout le tableau de navires sans rencontrer de navire touché en
						// c
	}

	public boolean estALEau(Coordonnee c) {
		// Retourne true si et seulement si c correspond à un tir reçu dans l'eau par
		// this.
		if (!estDansTirsRecus(c))
			return false;
		else {// c est dans les tirs recus
			if (estTouche(c)) // Un navire est touché en c
				return false;
			else
				return true; // c est dans les tirs reçus et aucun navire n'est touche en c
		}
	}

	public boolean estCoule(Coordonnee c) {
		// Retourne true si et seulement si un des navires présents dans this a été
		// touché en c et est coulé
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].estTouche(c) && navires[i].estCoule())
				return true;
		}
		return false;// On a parcouru tout le tableau sans trouver de navire touché en c et coulé
	}

	public boolean perdu() {
		// Retourne true ssi tous les navires de this ont été coulés
		for (int i = 0; i < nbNavires; i++) {
			if (!navires[i].estCoule())
				return false; // Si un bateau n'est pas coulé, on renvoie faux
		}
		return true;
	}
	
	//TODO : MODIF DU MERCREDI 15
	protected static int[] choixNavires(int taille, int [][] tab) {
		// Ici on choisit un élément du tableau selon la taille choisie, ce qui va
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
//		System.out.println("navires[0] touché ? : " + gn.navires[0].estTouche());
//		System.out.println("est coulé ? : " + gn.estCoule(new Coordonnee("A1")));
//		System.out.println("perdu ? : " + gn.perdu());
//		System.out.println(gn.toString());
//
//	}

}