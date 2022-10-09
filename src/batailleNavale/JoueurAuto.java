package batailleNavale;

public class JoueurAuto extends JoueurAvecGrille {
	
	

	/*
	 * appel du constructeur de la super classe
	 */
	public JoueurAuto(GrilleNavale g, String nom) {

		super(g, nom);
	}

	public JoueurAuto(GrilleNavale g) {

		super(g);
	}

	protected void retourAttaque(Coordonnee c, int etat) {
		// TODO
		/* --- a voir ---- */
		
	}

	protected void retourDefense(Coordonnee c, int etat) {
		// TODO
		/* --- a voir ---- */
	}

	public Coordonnee choixAttaque() {
		Coordonnee c = coordonneeAleatoire();
		
		return c;
	}
	
	public Coordonnee coordonneeAleatoire() {
		int ligneAleatoire, colonneAleatoire;

		ligneAleatoire = (int) (Math.random() * super.getTailleGrille());
		colonneAleatoire = (int) (Math.random() * super.getTailleGrille());
		
		return new Coordonnee(ligneAleatoire, colonneAleatoire);
	}

	public static void main(String[] args) {
		
		String s = "pierre";
		JoueurAuto j1 = new JoueurAuto(new GrilleNavale(4, 4), s);
		System.out.println(j1.choixAttaque());

	}
}
