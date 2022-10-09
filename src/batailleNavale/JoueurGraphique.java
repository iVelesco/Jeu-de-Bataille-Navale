package batailleNavale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

/**
 * @author Maxime
 *
 */
public class JoueurGraphique extends JoueurAvecGrille {

	private GrilleGraphique grilleTirs;
	private FenetreJoueur fenetre;

/////////////////////////CONSTRUCTEURS///////////////////////////////////////

	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs, String nom) {
		// permet d'obtenir un joueur graphique de nom nom qui effectue des tirs en
		// cliquant sur grilleTirs et dont la flotte est plac�e sur grilleDefense.

		super(grilleDefense, nom);

		this.grilleTirs = grilleTirs;

	}

	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs) {
		// permet d'obtenir un joueur graphique qui effectue des tirs en cliquant sur
		// grilleTirs et dont la flotte est plac�e sur grilleDefense.

		super(grilleDefense);

		this.grilleTirs = grilleTirs;
	}

	/**
	 * Constructeur qui cr�� un joueur graphique � partir d'une FenetreJoueur, pour
	 * lier les deux classes
	 * 
	 * @param fenetre
	 */
	public JoueurGraphique(FenetreJoueur fenetre) {
		super(fenetre.getGrilleDefense(), "Nom du joueur");

		this.grilleTirs = fenetre.getGrilleTirs();
		this.fenetre = fenetre;
	}

	public Coordonnee choixAttaque() {
		// Consiste � r�cup�rer la coordonn�e choisie depuis grilleTirs

		return grilleTirs.getCoordonneeSelectionnee();

	}

	protected void retourDefense(Coordonnee c, int etat) {
		// Affichage d'un JOptionPane lorsque le tir a touch� ou coul� un navire, ou
		// lorsque la partie est perdue.

		switch (etat) {
		case TOUCHE:
	
			
			fenetre.etatClic2.setText("Vous avez �t� touch� en " + c + " !"); // Met � jour le message sous la fen�tre du
																				// d�fenseur
			
			
			
			break;
		case A_L_EAU:
			fenetre.etatClic2.setText("Ouf, dans l'eau en " + c + " !"); //Affiche quand c'est dans l'eau
			break;
		case COULE:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez un navire coul� en  " + c);
			break;
		case GAMEOVER:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu !!!");		
		}
	}

	protected void retourAttaque(Coordonnee c, int etat) {
		Color couleur = etat == A_L_EAU ? Color.BLUE : Color.RED;
		grilleTirs.colorie(c, couleur);
		switch (etat) {
		case TOUCHE:
			fenetre.etatClic.setText("Vous avez touch� un navire en " + c + " !"); // Met � jour le message sous la
																					// fenetre du d�fenseur
			break;
		case A_L_EAU:
			fenetre.etatClic.setText("Dans l'eau en " + c + " !"); //Affiche quand c'est dans l'eau
			break;
		case COULE:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez coul� un navire en " + c + " !");
			break;
		case GAMEOVER:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagn� !!!");
		}
	}

}
