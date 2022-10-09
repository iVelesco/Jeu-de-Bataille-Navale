package batailleNavale;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class JoueurAvecGrille extends Joueur {

	public GrilleNavale grille; //TODO : repasser en private, public pour les test

	public JoueurAvecGrille(GrilleNavale g, String nom) {
		super(g.getTaille(), nom);

		if (g.getTaille() != getTailleGrille()) {
			throw new IllegalArgumentException(
					"Grille donn�e en argument n'est pas de la m�me taille que l'attribut tailleGrille");
		}

		grille = g;

	}

	public JoueurAvecGrille(GrilleNavale g) {
		this(g, "Joueur");
	}

	public int defendre(Coordonnee c) {
		
		grille.recoitTir(c);
		
		int res = 0;
		if (grille.estTouche(c)) {
			
			
			try {
				AudioInputStream input= AudioSystem.getAudioInputStream(new File("src/batailleNavale/hit.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(input);
				clip.start();
			} catch (UnsupportedAudioFileException f) {
				f.printStackTrace();
			} catch (IOException f) {
				f.printStackTrace();
			} catch (LineUnavailableException f) {
				f.printStackTrace();
			}
			

			res = TOUCHE;
		}

		if (grille.estCoule(c)) {
			
			
			try {
				AudioInputStream input= AudioSystem.getAudioInputStream(new File("src/batailleNavale/submarineDive.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(input);
				clip.start();
			} catch (UnsupportedAudioFileException f) {
				f.printStackTrace();
			} catch (IOException f) {
				f.printStackTrace();
			} catch (LineUnavailableException f) {
				f.printStackTrace();
			}
			

			res = COULE;
		}

		if (grille.estALEau(c)) {

			res = A_L_EAU;
		}
		if (grille.perdu()) {
			
			try {
				AudioInputStream input= AudioSystem.getAudioInputStream(new File("src/batailleNavale/gameOver.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(input);
				clip.start();
			} catch (UnsupportedAudioFileException f) {
				f.printStackTrace();
			} catch (IOException f) {
				f.printStackTrace();
			} catch (LineUnavailableException f) {
				f.printStackTrace();
			}

			res = GAMEOVER;
		}
		return res;
	}

}
