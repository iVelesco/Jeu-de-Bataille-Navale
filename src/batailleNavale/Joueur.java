package batailleNavale;

public abstract class Joueur {
	public final static int TOUCHE = 1;
	public final static int COULE = 2;
	public final static int A_L_EAU = 3;
	public final static int GAMEOVER = 4;
	private Joueur adversaire; // initialis� dans le constructeur joueurAvec(Joueur j)
	private int tailleGrille;// donne la taille de la grille de jeu utilis�e par le joueur, celle sur
								// laquelle il place ses navires
	private String nom; // nom de joueur

	// permet d'obtenir un joueur de nom nom et jouant sur une grille de taille
	// taillegrille.
	public Joueur(int tailleGrille, String nom) {
		//TODO : enlever le this. avant les tests d'exceptions sinon erreur car forc�ment null
		
		if (nom == null) {

			throw new IllegalArgumentException("Imp�rativement vous devez choisir un nom de Joueur");
		}

		if (tailleGrille < 0) {
			throw new IllegalArgumentException("La taille de grille ne peut pas etre inferieure a 0");
		}

		if (tailleGrille > 26) {
			
			throw new IllegalArgumentException("La taille de grille ne peut pas etre sup�rieure a 26");
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
	
	
	//D�marre une partie contre j. Avant de lancer le d�roulement du jeu, 
	//il faut veiller � �tablir le lien entre les 2 joueurs et bien entendu v�rifier qu�il puisse �tre �tabli.
	public void jouerAvec(Joueur j) {
		
		if(this.tailleGrille != j.tailleGrille) {
			
			throw new IllegalArgumentException("Les tailles doivent �tre les pareils");
		}
		
		this.adversaire = j;
		j.adversaire = this;
		System.out.println("D�but du jeu!");
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
	
	/*Cette m�thode est invoqu�e sur le joueur attaquant � la fin d�un tour de jeu. 
	 * c est la coordonn�e � laquelle le tir a eu lieu et etat le r�sultat de l'attaque. 
	 * etat ne peut �tre que TOUCHE, COULE, A_L_EAU, ou GAMEOVER.*/
	
	protected abstract void retourAttaque(Coordonnee c, int etat);
	
	
	
	/*Cette m�thode est invoqu�e sur le joueur d�fenseur � la fin d�un tour de jeu. 
	 * c est la coordonn�e � laquelle le tir a eu lieu et etat le r�sultat de ce tir. 
	 * etat ne peut �tre que TOUCHE, COULE, A_L_EAU, ou GAMEOVER.*/
	 
	protected abstract void retourDefense(Coordonnee c, int etat);
	
	
	/*Cette m�thode est invoqu�e sur le joueur attaquant au d�but d�un tour de jeu. 
	Elle retourne la coordonn�e cible du tir effectu� par l�attaquant.*/
	public abstract Coordonnee choixAttaque();

	
	
	/*Cette m�thode est invoqu�e sur le joueur d�fenseur apr�s le choix de l�attaquant, 
	c est la coordonn�e � laquelle l�attaquant a choisi d�effectuer un tir. 
	Elle retourne le r�sultat du tir qui ne peut �tre que TOUCHE, COULE, A_L_EAU, ou GAMEOVER.*/
	
	public abstract int defendre(Coordonnee c);
}
