package batailleNavale;

import java.util.Scanner;


public class JoueurTexte extends JoueurAvecGrille {

	private Scanner sc;

	public JoueurTexte(GrilleNavale g, String nom) {
		super(g, nom);
		System.out.println("Bienvenue " + nom + ", bonne chance ! \n");
	}

	public JoueurTexte(GrilleNavale g) {
		this(g, "Joueur");
	}

//////////////////////////METHODES////////////////////////

	protected void retourAttaque(Coordonnee c, int etat) {
		// Retourne les infos de l'attaque é l'attaquant
		
		if(etat == TOUCHE) {
			System.out.println("Vous avez touché un navire en " + c);
		} else if(etat == COULE) {
			System.out.println("Vous avez coulé un navire en " + c);
		} else if(etat == A_L_EAU) {
			System.out.println("Vous avez tiré dans l'eau en " + c);
		} else if(etat == GAMEOVER){
			System.out.println("Gagné, bravo !");
		}

	}

	protected void retourDefense(Coordonnee c, int etat) {
		// Renvoie l'information de l'action de l'attaquant : donc info de navire
		// touché, dans l'eau, navire coulé etc
		
		if(etat == TOUCHE) {
			System.out.println("Vous avez été touché en " + c);
		} else if(etat == COULE) {
			System.out.println("Vous avez été coulé en " + c);
		} else if(etat == A_L_EAU) {
			System.out.println("L'adversaire a tiré dans l'eau en " + c);
		} else if(etat == GAMEOVER) {
			System.out.println("Perdu, nullos !");
		}
		
		System.out.println(grille);
	}

	public Coordonnee choixAttaque() {
		
		Coordonnee coordAttaquee = null; //Creation d'une nouvelle coordonnee a verifier
		
		while (coordAttaquee == null) {
		//on demande a entrer une coordonnee jusqu'a ce que obtient une coordonnee valide 
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrez une coordonnee d'attaque : ");
			String stringCoord = sc.next();
			//on attribue la coordonnee entree a notre attribut et on verifie si elle est dans la limite de notre GrilleNavale
			try {
				coordAttaquee = new Coordonnee(stringCoord);
				if ((coordAttaquee.getColonne() + 1 > this.getTailleGrille()) ||
						coordAttaquee.getLigne() + 1 > this.getTailleGrille()) {
					System.out.println("Hors bataille ! Réessayez..");
					coordAttaquee = null;
				}
			} catch (IllegalArgumentException e ) { // si la coordonnee ne passe pas la verification
				System.out.println("Hors bataille ! Réessayez..");
			}
		}
		
		return coordAttaquee;
	}

}
