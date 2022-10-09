package batailleNavale;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;

public class BatailleNavale {

	private Joueur joueur1, joueur2;
	private int tailleGrille;
	private JFrame frmBatailleNavale;
	private JTextField txtTaille;
	private JTextField txtJ1;
	private JTextField txtJ2;
	public static final String ANSI_RED = "\u001B[31m"; // agregado
	public static final String ANSI_RESET = "\u001B[0m"; // agregado

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BatailleNavale window = new BatailleNavale();
					window.frmBatailleNavale.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// AGREGADO
	public static JLabel createLabel(String text) {

		return createLabel(text, UIManager.getColor("Label.foreground"));

	}

	// AGREGADO
	public static JLabel createLabel(String text, Color color) {

		JLabel label = new JLabel(text);
		label.setForeground(color);

		return label;

	}

	/**
	 * Create the application.
	 */
	public BatailleNavale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBatailleNavale = new JFrame();
		frmBatailleNavale.setTitle("Bataille Navale");
		frmBatailleNavale.setBounds(100, 100, 577, 574);
		frmBatailleNavale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBatailleNavale.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		frmBatailleNavale.getContentPane().add(panel_1);

		JButton lancerPartie = new JButton("Lancer la partie");
		panel_1.add(lancerPartie);

		JPanel panelHaut = new JPanel();
		frmBatailleNavale.getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));

		JPanel panelTaille = new JPanel();
		panelTaille.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Jeu", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelHaut.add(panelTaille, BorderLayout.NORTH);
		panelTaille.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Taille de grille entre 6 et 26 : ");
		panelTaille.add(lblNewLabel, BorderLayout.WEST);

		txtTaille = new JTextField();
		txtTaille.setText("10");
		txtTaille.setHorizontalAlignment(SwingConstants.LEFT);
		txtTaille.setColumns(10);
		panelTaille.add(txtTaille);

		JPanel panelJoueurs = new JPanel();
		panelHaut.add(panelJoueurs, BorderLayout.SOUTH);
		panelJoueurs.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelJoueur1 = new JPanel();
		panelJoueur1.setToolTipText("Joueur 1");
		panelJoueur1.setBorder(new TitledBorder(null, "Joueur 1", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panelJoueurs.add(panelJoueur1);
		panelJoueur1.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelNom1 = new JPanel();
		panelNom1.setToolTipText("Joueur 1");
		panelJoueur1.add(panelNom1);
		panelNom1.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1_1_1 = new JLabel("Nom : ");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		panelNom1.add(lblNewLabel_1_1_1, BorderLayout.WEST);

		// Agregado
		
		
		JPanel panel = new JPanel();
		panelTaille.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Musique : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_1);
		
		JRadioButton musicButtonOui = new JRadioButton("Oui");
		musicButtonOui.setSelected(true);
		musicButtonOui.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(musicButtonOui);
		
		JRadioButton musicButtonNon = new JRadioButton("Non");
		panel.add(musicButtonNon);
		
		ButtonGroup g0 = new ButtonGroup();
		g0.add(musicButtonNon);
		g0.add(musicButtonOui);

		txtJ1 = new JTextField();
		txtJ1.setText("Joueur 1");
		txtJ1.setHorizontalAlignment(SwingConstants.LEFT);
		txtJ1.setColumns(10);
		panelNom1.add(txtJ1);

		JRadioButton j1Graph = new JRadioButton("Joueur Graphique");
		j1Graph.setSelected(true);
		j1Graph.setHorizontalAlignment(SwingConstants.LEFT);
		panelJoueur1.add(j1Graph);

		JRadioButton j1Texte = new JRadioButton("Joueur Texte");
		j1Texte.setHorizontalAlignment(SwingConstants.LEFT);
		panelJoueur1.add(j1Texte);

		JRadioButton j1Auto = new JRadioButton("Joueur Auto");
		j1Auto.setHorizontalAlignment(SwingConstants.LEFT);
		panelJoueur1.add(j1Auto);

		JRadioButton jAutoMoyen1 = new JRadioButton("Joueur Auto Moyen");
		panelJoueur1.add(jAutoMoyen1);

		ButtonGroup g1 = new ButtonGroup();
		g1.add(j1Graph);
		g1.add(j1Auto);
		g1.add(j1Texte);
		g1.add(jAutoMoyen1);

		JPanel panelJoueur2 = new JPanel();
		panelJoueur2.setBorder(new TitledBorder(null, "Joueur 2", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panelJoueurs.add(panelJoueur2);
		panelJoueur2.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelNom2 = new JPanel();
		panelJoueur2.add(panelNom2);
		panelNom2.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Nom : ");
		panelNom2.add(lblNewLabel_1_1_1_1, BorderLayout.WEST);

		txtJ2 = new JTextField();
		txtJ2.setText("Joueur 2");
		txtJ2.setHorizontalAlignment(SwingConstants.LEFT);
		txtJ2.setColumns(10);
		panelNom2.add(txtJ2);

		JRadioButton j2Graph = new JRadioButton("Joueur Graphique");
		j2Graph.setHorizontalAlignment(SwingConstants.LEFT);
		panelJoueur2.add(j2Graph);

		JRadioButton j2Texte = new JRadioButton("Joueur Texte");
		j2Texte.setHorizontalAlignment(SwingConstants.LEFT);
		panelJoueur2.add(j2Texte);

		JRadioButton j2Auto = new JRadioButton("Joueur Auto");
		j2Auto.setSelected(true);
		j2Auto.setHorizontalAlignment(SwingConstants.LEFT);
		panelJoueur2.add(j2Auto);

		JRadioButton jAutoMoyen2 = new JRadioButton("Joueur Auto Moyen");
		panelJoueur2.add(jAutoMoyen2);

		ButtonGroup g2 = new ButtonGroup();
		g2.add(j2Graph);
		g2.add(j2Auto);
		g2.add(j2Texte);
		g2.add(jAutoMoyen2);

///////////////////////////RECUPERATION INFOS ET CREATION JOUEURS///////////////////////////////

		lancerPartie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					tailleGrille = Integer.valueOf(txtTaille.getText()); // On r�cup�re la taille entr�e
				} catch (java.lang.NumberFormatException e1) {
					JOptionPane.showMessageDialog(frmBatailleNavale, "Entrez un nombre entre 6 et 26 !");
					return;
				}

				if (tailleGrille < 6 || tailleGrille > 26) {
					JOptionPane.showMessageDialog(frmBatailleNavale, "Entrez une taille de grille entre 6 et 26 !");
					return;
				}

				String nomJ1 = txtJ1.getText(); // On r�cup�re les deux noms
				String nomJ2 = txtJ2.getText();

				if (nomJ1.length() == 0 || nomJ2.length() == 0) {
					JOptionPane.showMessageDialog(frmBatailleNavale, "Entrez un nom pour chaque joueur !");
					return;
				}

				if (musicButtonOui.isSelected()) {
					try {
						AudioInputStream input = AudioSystem.getAudioInputStream(new File(
								"src/batailleNavale/noSurrender.wav"));
						Clip clip = AudioSystem.getClip();
						clip.open(input);
						clip.loop(Clip.LOOP_CONTINUOUSLY);
						clip.start();
					} catch (UnsupportedAudioFileException f) {
						f.printStackTrace();
					} catch (IOException f) {
						f.printStackTrace();
					} catch (LineUnavailableException f) {
						f.printStackTrace();
					}

				}

				frmBatailleNavale.setVisible(false);

				if (j1Graph.isSelected()) { // Si le joueur graphique est s�lectionn�
					JPanel f = new JPanel(new GridBagLayout());
					f.add(createLabel("Hello "));
					f.add(createLabel(nomJ1, Color.RED));
					f.add(createLabel(" , welcome to Bataille Navale! Tu es pr�t?"));
					JOptionPane.showMessageDialog(null, f);

					FenetreJoueur FJ1 = new FenetreJoueur(nomJ1, tailleGrille); // On g�n�re la fen�tre du joueur 1
					joueur1 = new JoueurGraphique(FJ1); // On cr�e le J1 avec ses grilles
					FJ1.setVisible(true);
					FJ1.etatClic.setText("A toi de jouer !");

				} else if (j1Texte.isSelected()) { // Si joueur texte est s�lectionn�
					GrilleNavale gt1 = new GrilleNavale(tailleGrille, 15);
					gt1.placementAuto(GrilleNavale.choixNavires(tailleGrille, GrilleNavale.tabChoixNavires));
					joueur1 = new JoueurTexte(gt1, nomJ1);
				} else if (j1Auto.isSelected()) { // Si j auto est s�elctionn�
					GrilleNavale gt1 = new GrilleNavale(tailleGrille, 15);
					gt1.placementAuto(GrilleNavale.choixNavires(tailleGrille, GrilleNavale.tabChoixNavires));
					joueur1 = new JoueurAleatoireSouvenir(gt1);
				} else if (jAutoMoyen1.isSelected()) { // J auto moyen
					GrilleNavale gt1 = new GrilleNavale(tailleGrille, 15);
					int[] tailleNavires = GrilleNavale.choixNavires(tailleGrille, GrilleNavale.tabChoixNavires);
					gt1.placementAuto(tailleNavires);
					joueur1 = new JoueurAutoMeilleur(gt1, nomJ1, tailleNavires);
				}

				if (j2Graph.isSelected()) { // Si le joueur graphique est s�lectionn�
					FenetreJoueur FJ2 = new FenetreJoueur(nomJ2, tailleGrille);
					joueur2 = new JoueurGraphique(FJ2);
					FJ2.setVisible(true);
					FJ2.etatClic.setText("C'est ton adversaire qui commence !");
				} else if (j2Texte.isSelected()) { // Si joueur texte est s�lectionn�
					GrilleNavale gt2 = new GrilleNavale(tailleGrille, 15);
					gt2.placementAuto(GrilleNavale.choixNavires(tailleGrille, GrilleNavale.tabChoixNavires));
					joueur2 = new JoueurTexte(gt2, nomJ2);
				} else if (j2Auto.isSelected()) { // Si j auto est s�elctionn�
					GrilleNavale gt2 = new GrilleNavale(tailleGrille, 15);
					gt2.placementAuto(GrilleNavale.choixNavires(tailleGrille, GrilleNavale.tabChoixNavires));
					joueur2 = new JoueurAleatoireSouvenir(gt2);
				} else if (jAutoMoyen2.isSelected()) {
					GrilleNavale gt2 = new GrilleNavale(tailleGrille, 15);
					int[] tailleNavires = GrilleNavale.choixNavires(tailleGrille, GrilleNavale.tabChoixNavires);
					gt2.placementAuto(tailleNavires);
					joueur2 = new JoueurAutoMeilleur(gt2, nomJ2, tailleNavires);
				}

				demarrerPartie();

				////////////////////////
			}
		});
	}

	private void demarrerPartie() { // Methode qui d�marre la partie
		new Thread() {
			public void run() {
				joueur1.jouerAvec(joueur2);
			}
		}.start();
	}

}
