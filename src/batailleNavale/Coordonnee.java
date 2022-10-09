package batailleNavale;

public class Coordonnee implements Comparable<Coordonnee> {

	private int ligne;
	private int colonne;

	/////////////////////////////////// CONSTRUCTEURS///////////////

	public Coordonnee(int ligne, int colonne) {
		// permet d'obtenir une coordonn�e de ligne i et de colonne j (indices Java).

		if (ligne > 25 || ligne < 0 || colonne > 25 || colonne < 0) { // v�rifie si les indices sont bien entre 0 et 25
			throw new IllegalArgumentException("Indices non valides : " + ligne + " " + colonne);
		}

		this.ligne = ligne;
		this.colonne = colonne;

	}

	public Coordonnee(String s) {
		// permet d'obtenir une coordonn�e d'apr�s son expression donn�e par s dans le
		// syst�me de coordonn�es de la bataille navale.

		if (s == "" || s.length() == 1) {
			throw new IllegalArgumentException("La coordonnee n'est pas valide : " + s);
		}

		char sccol = s.charAt(0); // On r�cup�re le premier car de l'expression, donc le car de colonne

		colonne = ((int) (sccol)) - 65; // Ici on transtype le caract�re de ligne en entier et on soustrait la valeur de
										// 'A' en ASCII pour bien avoir quelque chose entre 0 et 25

		if (colonne < 0 || colonne > 25) { // Si le transtypage a donn� un indice non valide, erreur
			throw new IllegalArgumentException("Nom de colonne non valide : " + sccol);
		}

		try {
			ligne = Integer.valueOf(s.substring(1)) - 1; // utilisation de valueof pour prendre un nbr sous forme de
															// chaine et le transfo en int
		} catch (NumberFormatException e) { // v�rifie si on a bien un nombre et pas un caract�re
			throw new IllegalArgumentException("Pas un num�ro de ligne valide : " + s.substring(1));
		}
		// Si possible mtn ligne vaut le nombre entr� en cha�ne - 1, pour que l'indice
		// soit bien compris entre 0 et 25 (et pas entre 1 et 26)

		if (ligne < 0 || ligne > 25) { // On v�rifie que l'indice est bien valide
			throw new IllegalArgumentException("Num�ro de ligne non valide : " + ligne);
		}
	}

	//////////////////////////////////////////// METHODES////////////////////

	public String toString() {
		// Retourne une String exprimant this dans le syst�me de coordonn�e de la
		// bataille navale (exemple : "C6").

		char col = ((char) (colonne + 65)); // On retranstype l'indice de colonne en caract�re via la table ASCII

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
		// Retourne true si et seulement si this est �quivalent � obj.

		return obj instanceof Coordonnee && this.compareTo((Coordonnee) obj) == 0;

	}

	public boolean voisine(Coordonnee c) {
		// Retourne true si et seulement si this est une coordonn�e voisine
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
		// Retourne le r�sultat de la comparaison de this et de c. Une coordonn�e est
		// consid�r�e inf�rieure � une autre, si elle se trouve sur une ligne inf�rieure
		// ou si elle se trouve sur la m�me ligne mais sur une colonne inf�rieure

		if (c.ligne != this.ligne) {
			return this.ligne - c.ligne;
		} else if (c.colonne != this.colonne) {
			return this.colonne - c.colonne;
		} else {
			return 0;
		}
	}
}
