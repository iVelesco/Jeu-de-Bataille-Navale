package batailleNavale;

public class Coordonnee implements Comparable<Coordonnee> {

	private int ligne;
	private int colonne;

	/////////////////////////////////// CONSTRUCTEURS///////////////

	public Coordonnee(int ligne, int colonne) {
		// permet d'obtenir une coordonnée de ligne i et de colonne j (indices Java).

		if (ligne > 25 || ligne < 0 || colonne > 25 || colonne < 0) { // vérifie si les indices sont bien entre 0 et 25
			throw new IllegalArgumentException("Indices non valides : " + ligne + " " + colonne);
		}

		this.ligne = ligne;
		this.colonne = colonne;

	}

	public Coordonnee(String s) {
		// permet d'obtenir une coordonnée d'après son expression donnée par s dans le
		// système de coordonnées de la bataille navale.

		if (s == "" || s.length() == 1) {
			throw new IllegalArgumentException("La coordonnee n'est pas valide : " + s);
		}

		char sccol = s.charAt(0); // On récupère le premier car de l'expression, donc le car de colonne

		colonne = ((int) (sccol)) - 65; // Ici on transtype le caractère de ligne en entier et on soustrait la valeur de
										// 'A' en ASCII pour bien avoir quelque chose entre 0 et 25

		if (colonne < 0 || colonne > 25) { // Si le transtypage a donné un indice non valide, erreur
			throw new IllegalArgumentException("Nom de colonne non valide : " + sccol);
		}

		try {
			ligne = Integer.valueOf(s.substring(1)) - 1; // utilisation de valueof pour prendre un nbr sous forme de
															// chaine et le transfo en int
		} catch (NumberFormatException e) { // vérifie si on a bien un nombre et pas un caractère
			throw new IllegalArgumentException("Pas un numéro de ligne valide : " + s.substring(1));
		}
		// Si possible mtn ligne vaut le nombre entré en chaîne - 1, pour que l'indice
		// soit bien compris entre 0 et 25 (et pas entre 1 et 26)

		if (ligne < 0 || ligne > 25) { // On vérifie que l'indice est bien valide
			throw new IllegalArgumentException("Numéro de ligne non valide : " + ligne);
		}
	}

	//////////////////////////////////////////// METHODES////////////////////

	public String toString() {
		// Retourne une String exprimant this dans le système de coordonnée de la
		// bataille navale (exemple : "C6").

		char col = ((char) (colonne + 65)); // On retranstype l'indice de colonne en caractère via la table ASCII

		return "" + col + (ligne + 1);

		// ATTENTION quand tu veux F3 pour l'ordi c'est 52 ou 25
	}

	public int getColonne() {
		// Accesseur en lecture pour l'indice de colonne.

		return colonne;

	}

	public int getLigne() {
		// Accesseur en lecture pour l'indice de ligne.

		return ligne;

	}

	public boolean equals(Object obj) {
		// Retourne true si et seulement si this est équivalent à obj.

		return obj instanceof Coordonnee && this.compareTo((Coordonnee) obj) == 0;

	}

	public boolean voisine(Coordonnee c) {
		// Retourne true si et seulement si this est une coordonnée voisine
		// (verticalement ou horizontalement) de c
		
		if (this.ligne == c.ligne && (this.colonne - c.colonne == -1 || this.colonne - c.colonne == 1)) {
			return true;
		} else if (this.colonne == c.colonne && (this.ligne - c.ligne == -1 || this.ligne - c.ligne == 1)) {
			return true;
		} else {
			return false;
		}
	}

	public int compareTo(Coordonnee c) {
		// Retourne le résultat de la comparaison de this et de c. Une coordonnée est
		// considérée inférieure à une autre, si elle se trouve sur une ligne inférieure
		// ou si elle se trouve sur la même ligne mais sur une colonne inférieure

		if (c.ligne != this.ligne) {
			return this.ligne - c.ligne;
		} else if (c.colonne != this.colonne) {
			return this.colonne - c.colonne;
		} else {
			return 0;
		}
	}
}
