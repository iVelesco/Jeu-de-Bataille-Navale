package batailleNavale;

public class JoueurAleatoireSouvenir extends JoueurAuto {

	public boolean[][] tabCoordPrecedents;

	public JoueurAleatoireSouvenir(GrilleNavale g, String nom) {
		super(g, nom);
		tabCoordPrecedents = new boolean[super.getTailleGrille()][super.getTailleGrille()];
	}

	public JoueurAleatoireSouvenir(GrilleNavale g) {
		super(g);
		tabCoordPrecedents = new boolean[super.getTailleGrille()][super.getTailleGrille()];
	}

	public Coordonnee choixAttaque() {
		return coordonneeAleatoireSouvenir();
	}
	
	public Coordonnee coordonneeAleatoireSouvenir() {
		int i, j;
		Coordonnee choix;

		choix = coordonneeAleatoire();
		i = choix.getLigne();
		j = choix.getColonne();
		for (; tabCoordPrecedents[i][j]; j++) {
			if (j == getTailleGrille() - 1) {
				j = 0;
				if (i == getTailleGrille() - 1)
					i = 0;
				else
					i++;
			}
		}

		tabCoordPrecedents[i][j] = true;
		return new Coordonnee(i, j);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}