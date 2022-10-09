package batailleNavale;

import java.util.Scanner;

public class Main {

	final static private int[][] navires = { { 2, 2, 3 }, { 2, 2, 3, 3 }, { 2, 2, 3, 3, 4 }, { 2, 2, 3, 3, 4, 4 } };
	//Tableau avec des tableaux de bateaux pour g�n�rer une grille
	// TODO : mettre plus de bateaux
	
	private static int[] choixNavires(int taille) {
		// Ici on choisit un �l�ment du tableau selon la taille choisie, ce qui va
		// donner un nombre de bateaux et leur taille

		if (taille < 10) {
			return navires[0];
		} else if (taille < 15) {
			return navires[1];
		} else if (taille < 20) {
			return navires[2];
		} else {
			return navires[3];
		}
	}

	public static void main(String[] args) {
		// Main principal (aka main de de main, pour les gouverner tous) qui va ex�cuter
		// les �l�ments principaux

		Scanner sc = new Scanner(System.in);

		System.out.println("Entrez un nom pour jouer : ");
		String nom = sc.nextLine();
		System.out.println("Entrez une taille de grille de jeu entre 1 et 26 : ");
		int tgrille = sc.nextInt();

		GrilleNavale grilleJoueur = new GrilleNavale(tgrille, choixNavires(tgrille)); // G�n�ration grille joueur
		JoueurTexte j1 = new JoueurTexte(grilleJoueur, nom); // G�n�ration joueur 1

		GrilleNavale grilleJoueurAuto = new GrilleNavale(tgrille, choixNavires(tgrille)); // G�n�ration grille Jauto
																							// avec m�mes attributs
		JoueurAuto jAuto = new JoueurAuto(grilleJoueurAuto, "Joueur Auto"); // G�n�ration joueur 2

		System.out.println("Bienvenue " + nom + ", bonne chance !");

		System.out.println(j1.grille.toString()); // TODO : enlever, seulement pour le tester
		System.out.println(jAuto.grille.toString()); // TODO : enlever, seulement pour le tester

		j1.jouerAvec(jAuto); // Lancement du jeu

	}

}
