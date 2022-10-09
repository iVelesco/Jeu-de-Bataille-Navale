package batailleNavale;

public class Navire {
	private Coordonnee debut;
	private Coordonnee fin;
	private Coordonnee[] partiesTouchees;
	private int nbTouchees;

	// permet d'obtenir un navire débutant en debut et de taille longueur.
	// Ce navire est disposé verticalement si estVertical vaut true, horizontalement
	// sinon.
	public Navire(Coordonnee debut, int longueur, boolean estVertical) {

		this.debut = debut;

		this.partiesTouchees = new Coordonnee[longueur];
		this.nbTouchees = 0;

		if (longueur < 2 || longueur > 7) {
			throw new IllegalArgumentException(
					"La longueur n'est pas valide. Elle doit etre comprise entre [2, 7]. " + longueur);
		}

		if (estVertical == true) {

			fin = new Coordonnee(debut.getLigne() + longueur - 1, debut.getColonne());

		} else {

			fin = new Coordonnee(debut.getLigne(), debut.getColonne() + longueur - 1);
		}
	}

	// Retourne une String représentant this.
	// On souhaite obtenir une représentation de la forme "Navire(B1, 4,
	// horizontal)"
	// (pour un navire de taille 4 placé horizontalement par exemple).
	public String toString() {

		if (estVertical() == true) {

			return new String("Navire( " + debut + ", " + partiesTouchees.length + ", " + "vertical)");

		} else {
			return new String("Navire( " + debut + ", " + partiesTouchees.length + ", " + "horizontal)");
		}
	}

	public Coordonnee getDebut() {
		return debut;
	}

	public Coordonnee getFin() {
		return fin;
	}

	private boolean estVertical() {

		if (debut.getColonne() == fin.getColonne()) {
			return true;
		}
		return false;
	}

	// Retourne true si et seulement si this occupe c.
	public boolean contient(Coordonnee c) {

		return c.getLigne() <= this.fin.getLigne() && c.getLigne() >= this.debut.getLigne()
				&& c.getColonne() <= this.fin.getColonne() && c.getColonne() >= this.debut.getColonne();
	}

	public boolean recoitTir(Coordonnee c) {

		// si c est une coordonnee occupee par this

		if (this.contient(c)) {
			// et pas deja dans tab

			if (estTouche(c)) { // On teste s'il a déjà été touché à ces coordonnées
				return true; // Si oui on sort direct
			} else {
				partiesTouchees[nbTouchees] = c; // Si non on met la coordonnée dans le tableau de touches

				nbTouchees += 1; // Et on incrémente le nb de touches pour la cohérence et accéder à l'élément
									// suivant du tab
				return true;
			}
		}
		return false; //Si le navire ne contient pas c, on renvoie false
	}

	// Retourne true si et seulement si this a été touché par un tir en c.
	public boolean estTouche(Coordonnee c) {

		for (int i = 0; i < nbTouchees; i++) {
			if (partiesTouchees[i].equals(c)) {
				return true;
			}
		}
		return false;
	}

	public boolean estTouche() {

		return nbTouchees > 0;
	}

	public boolean estCoule() {

	return nbTouchees == partiesTouchees.length;
	}

	public boolean touche(Navire n) { // TODO : normalement fonctionne

		if (((this.fin.getLigne() >= n.debut.getLigne()) && (n.fin.getLigne() >= this.debut.getLigne()))
				&& ((this.fin.getColonne() + 1 == n.debut.getColonne())
						|| (n.fin.getColonne() + 1 == this.debut.getColonne()))

				|| ((this.fin.getColonne() >= n.debut.getColonne()) && (n.fin.getColonne() >= this.debut.getColonne()))
						&& (this.fin.getLigne() + 1 == n.debut.getLigne())
				|| (n.fin.getLigne() + 1 == this.debut.getLigne())) {
			return true;
		} else {
			return false;
		}
	}

	// Retourne true si et seulement si this chevauche n, c'est-à-dire que this et n
	// occupent au moins une coordonnée en commun.
	public boolean chevauche(Navire n) { // TODO : normalement fonctionne

		if (((this.fin.getLigne() >= n.debut.getLigne()) && (n.fin.getLigne() >= this.debut.getLigne()))
				&& ((this.fin.getColonne() >= n.debut.getColonne())
						&& (n.fin.getColonne() >= this.debut.getColonne()))) {

			// Ce if permet de tester si le début d'un bateau est avant la fin de l'autre et
			// inversement
			// Et ce test est appliqué sur les lignes et les colonnes, donc si à un moment
			// on a cette situation dans les deux sens, c'est qu'on a un chevauchement
			return true;
		} else {
			return false;
		}
	}
}