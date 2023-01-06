package ihm;

import application.Application;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;

import java.io.File;
import java.util.ArrayList;


public class FrameChoixJoueur extends JFrame implements ActionListener {
	
	private Application appli;

	private final int largeur;
	private final int hauteur;

	private File mappeXML;

	private JPanel panelImportXML;
	private JPanel panelCreationJoueurs;

	private JButton btnImport;
	private JButton btnConfirmer;
	private JButton btnMenu;
	private JButton btnJouer;
	private JButton btnRetour;

	private JComboBox<Integer> cbNbJoueur;
	private JCheckBox cbJeuSolo;

	private JTextField txtJoueurSolo;
	private JTextField txtJoueur1;
	private JTextField txtJoueur2;
	private JTextField txtJoueur3;
	private JTextField txtJoueur4;
	private JTextField txtJoueur5;

	private JLabel lblJoueurSolo;

	private ArrayList<JTextField> alTxtJoueur;
	private ArrayList<JLabel> alLblJoueur;

	private JLabel lblPathXML;

	public FrameChoixJoueur(Application appli) {
		this.appli = appli;

		// Paramètres Frame
		this.setTitle("Import Mappe XML");
		this.setLayout(null);
		this.setResizable(false);
		this.setMinimumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 450,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 350));

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2 - 25);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.hauteur = (int) this.getSize().getHeight();
		this.largeur = (int) this.getSize().getWidth();


		// Paramètres panels
		this.panelImportXML = new JPanel();
		this.panelImportXML.setLayout(null);
		this.panelImportXML.setBackground(Color.GRAY);

		this.panelCreationJoueurs = new JPanel();
		this.panelCreationJoueurs.setLayout(null);
		this.panelCreationJoueurs.setBackground(Color.LIGHT_GRAY);


		// Contenu Panel Import XML
		this.btnMenu = new JButton("Retour menu");
		this.btnMenu.setFocusPainted(false);
		this.btnMenu.addActionListener(this);
		this.panelImportXML.add(this.btnMenu);
		this.btnMenu.setBounds(20, 20, 150, 30);

		this.btnImport = new JButton("Ouvrir l'explorateur ...");
		this.btnImport.setFocusPainted(false);
		this.btnImport.addActionListener(this);
		this.panelImportXML.add(this.btnImport);
		this.btnImport.setBounds(this.largeur/4 - 75, this.hauteur/2, 150, 30);

		this.btnConfirmer = new JButton("Confirmer");
		this.btnConfirmer.setFocusPainted(false);
		this.btnConfirmer.addActionListener(this);
		this.panelImportXML.add(this.btnConfirmer);
		this.btnConfirmer.setBounds(this.largeur/4 - 75, this.hauteur/2 + 100, 150, 30);

		this.lblPathXML = new JLabel("", JLabel.CENTER);
		this.panelImportXML.add(this.lblPathXML);
		this.lblPathXML.setBounds(0, this.hauteur/2 + 30, this.largeur/2, 30);



		// Contenu Panel Création Joueurs
		this.cbNbJoueur = new JComboBox<Integer>(new Integer[] { 2, 3, 4, 5 });
		this.cbNbJoueur.setFocusable(false);
		this.cbNbJoueur.addActionListener(this);
		this.panelCreationJoueurs.add(this.cbNbJoueur);
		this.cbNbJoueur.setBounds(50, 150, 150, 30);

		this.cbJeuSolo = new JCheckBox("\tJouer en solo");
		this.cbJeuSolo.setFocusable(false);
		this.cbJeuSolo.addActionListener(this);
		this.panelCreationJoueurs.add(this.cbJeuSolo);
		this.cbJeuSolo.setBounds(50, 50, 150, 30);

		this.lblJoueurSolo = new JLabel("Nom du joueur :", JLabel.LEFT);
		this.panelCreationJoueurs.add(this.lblJoueurSolo);
		this.lblJoueurSolo.setBounds(this.largeur/2 - 250, 20, 150, 30);
		
		this.txtJoueurSolo = new JTextField(10);
		this.txtJoueurSolo.setEnabled(false);
		this.panelCreationJoueurs.add(this.txtJoueurSolo);
		this.txtJoueurSolo.setBounds(this.largeur/2 - 250, 50, 150, 30);

		this.alLblJoueur = new ArrayList<JLabel>();
		this.alTxtJoueur = new ArrayList<JTextField>();
		for(int i=0; i < 5; i++) {
			JLabel lblJoueur = new JLabel("Joueur " + (i+1) + " :", JLabel.LEFT);
			this.panelCreationJoueurs.add(lblJoueur);
			lblJoueur.setBounds(this.largeur/2 - 250, 105 + i*50, 150, 30);

			JTextField txtJoueur = new JTextField(10);
			txtJoueur.setEnabled(false);
			this.panelCreationJoueurs.add(txtJoueur);
			txtJoueur.setBounds(this.largeur/2 - 250, 130 + i*50, 150, 30);

			this.alLblJoueur.add(lblJoueur);
			this.alTxtJoueur.add(txtJoueur);
		}

		this.btnJouer = new JButton("Lancer le jeu !");
		this.btnJouer.setFocusPainted(false);
		this.btnJouer.addActionListener(this);
		this.panelCreationJoueurs.add(this.btnJouer);
		this.btnJouer.setBounds(this.largeur/2 - 225, this.hauteur - 100, 150, 30);

		this.btnRetour = new JButton("Retour XML");
		this.btnRetour.setFocusPainted(false);
		this.btnRetour.addActionListener(this);
		this.panelCreationJoueurs.add(this.btnRetour);
		this.btnRetour.setBounds(50, this.hauteur - 100, 150, 30);


		// Ajout à la frame
		for(int i=0; i < this.panelCreationJoueurs.getComponentCount(); i++)
			this.panelCreationJoueurs.getComponent(i).setEnabled(false);

		this.add(this.panelImportXML);
		this.panelImportXML.setBounds(0, 0, this.largeur/2, this.hauteur);
		this.add(this.panelCreationJoueurs);
		this.panelCreationJoueurs.setBounds(this.largeur/2, 0, this.largeur/2, this.hauteur);
		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnMenu) {
			new FrameManager(appli);
			this.dispose();
		}

		if(e.getSource() == this.btnImport) {
			this.ouvrirExploreur();
		}

		if(e.getSource() == this.btnJouer) {
			new FrameJeu(this.appli);
			this.dispose();
		}

		if(e.getSource() == this.btnConfirmer && this.mappeXML != null) {
			
			for(int i=0; i < this.panelImportXML.getComponentCount(); i++)
				if(this.panelImportXML.getComponent(i) != this.lblPathXML)
					this.panelImportXML.getComponent(i).setEnabled(false);

			this.cbNbJoueur.setEnabled(true);
			this.cbJeuSolo.setEnabled(true);
			int nbJoueur = (int) this.cbNbJoueur.getSelectedItem();
			for(int i=0; i < nbJoueur; i++) {
				this.alLblJoueur.get(i).setEnabled(true);
				this.alTxtJoueur.get(i).setEnabled(true);
			}

			this.btnRetour.setEnabled(true);
			this.btnJouer.setEnabled(true);

			this.setTitle("Création des joueurs");
		}

		if(e.getSource() == this.btnRetour) {

			for(int i=0; i < this.panelImportXML.getComponentCount(); i++)
				this.panelImportXML.getComponent(i).setEnabled(true);

			for(int i=0; i < this.panelCreationJoueurs.getComponentCount(); i++)
				if(this.panelCreationJoueurs.getComponent(i) != this.lblPathXML)
					this.panelCreationJoueurs.getComponent(i).setEnabled(false);

			this.setTitle("Import XML");
		}

		if(e.getSource() == this.cbJeuSolo || e.getSource() == this.cbNbJoueur) {
			if(!this.cbJeuSolo.isSelected()) {
				this.lblJoueurSolo.setEnabled(false);
				this.txtJoueurSolo.setEnabled(false);
				this.cbNbJoueur.setEnabled(true);
				for(int i=0; i < 5; i++) {
					this.alLblJoueur.get(i).setEnabled(false);
					this.alTxtJoueur.get(i).setEnabled(false);
				}
				int nbJoueur = (int) this.cbNbJoueur.getSelectedItem();

				for(int i=0; i < nbJoueur; i++) {
					this.alLblJoueur.get(i).setEnabled(true);
					this.alTxtJoueur.get(i).setEnabled(true);
				}
				
			} else {
				this.cbNbJoueur.setEnabled(false);

				for(JLabel lbl : this.alLblJoueur)
					lbl.setEnabled(false);

				for(JTextField txt : this.alTxtJoueur)
					txt.setEnabled(false);

				this.cbNbJoueur.setEnabled(!this.cbJeuSolo.isSelected());
				this.txtJoueurSolo.setEnabled(this.cbJeuSolo.isSelected());
				this.lblJoueurSolo.setEnabled(this.cbJeuSolo.isSelected());
			}
		}


	}


	public void ouvrirExploreur() {
		String path = System.getProperty("user.dir");
		String pathTest = path + "/sortie/";

		JFileChooser fileChooser = new JFileChooser(new File(pathTest).exists() ? pathTest: path);

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int retour = fileChooser.showOpenDialog(this);

		if(retour == JFileChooser.APPROVE_OPTION) {
			if(fileChooser.getSelectedFile().getName().endsWith(".xml") && fileChooser.getSelectedFile().exists()) {
				this.mappeXML = fileChooser.getSelectedFile();
				this.lblPathXML.setText(this.mappeXML.getPath());
			}
		}
	}

}