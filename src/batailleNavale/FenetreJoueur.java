package batailleNavale;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FenetreJoueur extends JFrame {
	
	//MODIF MERCREDI 15 : TENTATIVE DE REGLEMENT DU PB DE CONSTRUCTEUR
	//int[][] tabChoixNavires = { { 2, 2, 3 }, { 2, 2, 3, 3 }, { 2, 2, 3, 3, 4 }, { 2, 2, 3, 3, 4, 4 } };
	//Tableau avec des tableaux de bateaux pour générer une grille
	// TODO : mettre plus de bateaux
	
	private JPanel contentPane;
	private GrilleNavaleGraphique grilleDefense;
	protected JLabel etatClic;
	private GrilleGraphique grilleTirs;
	private JPanel panelGDef;
	protected JLabel etatClic2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreJoueur frame = new FenetreJoueur();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreJoueur() {
		this("Nom du joueur", 10);
	}
	
	public FenetreJoueur(String nom, int taille) {
		setTitle(nom);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Création automatique de la fenêtre avec WindowBuilder
		setBounds(100, 100, 803, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelGrilles = new JPanel();
		panelGrilles.setBorder(null);
		contentPane.add(panelGrilles, BorderLayout.CENTER);
		panelGrilles.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelAffichage = new JPanel();
		contentPane.add(panelAffichage, BorderLayout.SOUTH);
		panelAffichage.setLayout(new GridLayout(0, 2, 0, 0));
		
		etatClic = new JLabel("");
		etatClic.setHorizontalAlignment(SwingConstants.CENTER);
		etatClic.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelAffichage.add(etatClic);
		
		/* --------------AJOUTE D'Une nouvelle pannel d'affichage pour les deux joueur separement------------------------- */ 
		etatClic2 = new JLabel("");
		etatClic2.setHorizontalAlignment(SwingConstants.CENTER);
		etatClic2.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelAffichage.add(etatClic2);
		
		/* ---------------------FIN MODIFICATION---------------------------------*/
		
		grilleDefense = new GrilleNavaleGraphique(taille); //Génère une grille de défense uniquement visuelle
		grilleDefense.placementAuto(GrilleNavale.choixNavires(taille, GrilleNavale.tabChoixNavires));
		grilleDefense.getGrilleGraphique().setClicActive(false);
		
		grilleTirs = new GrilleGraphique(taille);
		grilleTirs.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Grille de l'adversaire", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelGrilles.add(grilleTirs);
		
		grilleDefense.getGrilleGraphique().setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Votre grille", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelGrilles.add(grilleDefense.getGrilleGraphique());

	}
	
	public GrilleGraphique getGrilleTirs() {
		return grilleTirs;
	}
	
	public GrilleNavaleGraphique getGrilleDefense() {
		return grilleDefense;
	}
}
