package batailleNavale;

public abstract class Joueur {
	public final static int TOUCHE = 1;
	public final static int COULE = 2;
	public final static int A_L_EAU = 3;
	public final static int GAMEOVER = 4;
	private Joueur adversaire; // initialisé dans le constructeur joueurAvec(Joueur j)
	private int tailleGrille;// donne la taille de la grille de jeu utilisée par le joueur, celle sur
								// laquelle il place ses navires
	private String nom; // nom de joueur

	// permet d'obtenir un joueur de nom nom et jouant sur une grille de taille
	// taillegrille.
	public Joueur(int tailleGrille, String nom) {
		//TODO : enlever le this. avant les tests d'exceptions sinon erreur car forcément null
		
		if (nom == null) {

			throw new IllegalArgumentException("Impérativement vous devez choisir un nom de Joueur");
		}

		if (tailleGrille < 0) {
			throw new IllegalArgumentException("La taille de grille ne peut pas etre inferieure a 0");
		}

		if (tailleGrille > 26) {
			
			throw new IllegalArgumentException("La taille de grille ne peut pas etre supérieure a 26");
		}

		this.nom = nom;
		this.tailleGrille = tailleGrille;

	}

	//permet d'obtenir un joueur jouant sur une grille de taille taillegrille.
	public Joueur(int tailleGrille) {
		//this(tailleGrille, nom);
		this(tailleGrille, "Joueur"); //changer avec scanner 
	}

	public int getTailleGrille() {
		
		return tailleGrille;
	}

	public String getNom() {
		return nom;
	}
	
	
	//Démarre une partie contre j. Avant de lancer le déroulement du jeu, 
	//il faut veiller à établir le lien entre les 2 joueurs et bien entendu vérifier qu’il puisse être établi.
	public void jouerAvec(Joueur j) {
		
		if(this.tailleGrille != j.tailleGrille) {
			
			throw new IllegalArgumentException("Les tailles doivent être les pareils");
		}
		
		this.adversaire = j;
		j.adversaire = this;
		System.out.println("Début du jeu!");
		deroulementJeu(this, j);	
	}

	private static void deroulementJeu(Joueur attaquant, Joueur defenseur) {
		int res = 0;
		while (res != GAMEOVER) {
			Coordonnee c = attaquant.choixAttaque();
			res = defenseur.defendre(c);
			attaquant.retourAttaque(c, res);
			defenseur.retourDefense(c, res);
			Joueur x = attaquant;
			attaquant = defenseur;
			defenseur = x;
		}
	}
	
	/*Cette méthode est invoquée sur le joueur attaquant à la fin d’un tour de jeu. 
	 * c est la coordonnée à laquelle le tir a eu lieu et etat le résultat de l'attaque. 
	 * etat ne peut être que TOUCHE, COULE, A_L_EAU, ou GAMEOVER.*/
	
	protected abstract void retourAttaque(Coordonnee c, int etat);
	
	
	
	/*Cette méthode est invoquée sur le joueur défenseur à la fin d’un tour de jeu. 
	 * c est la coordonnée à laquelle le tir a eu lieu et etat le résultat de ce tir. 
	 * etat ne peut être que TOUCHE, COULE, A_L_EAU, ou GAMEOVER.*/
	 
	protected abstract void retourDefense(Coordonnee c, int etat);
	
	
	/*Cette méthode est invoquée sur le joueur attaquant au début d’un tour de jeu. 
	Elle retourne la coordonnée cible du tir effectué par l’attaquant.*/
	public abstract Coordonnee choixAttaque();

	
	
	/*Cette méthode est invoquée sur le joueur défenseur après le choix de l’attaquant, 
	c est la coordonnée à laquelle l’attaquant a choisi d’effectuer un tir. 
	Elle retourne le résultat du tir qui ne peut être que TOUCHE, COULE, A_L_EAU, ou GAMEOVER.*/
	
	public abstract int defendre(Coordonnee c);
}
