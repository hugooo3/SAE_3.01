package metier;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class Metier 
{
	private static int RADIUS = 20;
	private String versoCartePath;

	private ArrayList<Noeud> alNoeuds;
	private ArrayList<Arete> alAretes;
	private ArrayList<CarteDestination> alCartesDestination;
	private ArrayList<CarteWagon> alCartesWagon;

	private Image imgMappe;
	private int nbJoueurMin;
	private int nbJoueurMax;
	private int nbJoueurDoubleVoies;
	private int nbWagonJoueur;
	private int nbFin;

	public Metier() 
	{
		this.versoCartePath = "/images/ArriereCarte.png";

		this.alNoeuds = new ArrayList<Noeud>();
		this.alAretes = new ArrayList<Arete>();
		this.alCartesDestination = new ArrayList<CarteDestination>();
		this.alCartesWagon = new ArrayList<CarteWagon>();

		// Carte Wagon par défaut
		this.alCartesWagon.add(new CarteWagon("Neutre"	, Color.GRAY	, null						, null					, 20));
		this.alCartesWagon.add(new CarteWagon("Blanc"	, Color.WHITE	, "/images/carteBlanche.png", this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Bleu"	, Color.BLUE	, "/images/carteBleu.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Jaune"	, Color.YELLOW	, "/images/carteJaune.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Noire"	, Color.BLACK	, "/images/carteNoire.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Orange"	, Color.ORANGE	, "/images/carteOrange.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Rouge"	, Color.RED		, "/images/carteRouge.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Verte"	, Color.GREEN	, "/images/carteVerte.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Violet"	, Color.MAGENTA	, "/images/carteViolet.png"	, this.versoCartePath	, 20));
		this.alCartesWagon.add(new CarteWagon("Joker"	, Color.PINK	, "/images/carteJoker.png"	, this.versoCartePath	, 20));
	}

	/***************************/
	/* Accesseurs et Mutateurs */
	/***************************/
	public ArrayList<Noeud> getAlNoeuds() {return this.alNoeuds;}
	public ArrayList<Arete> getAlAretes() {return this.alAretes;}
	public ArrayList<CarteDestination> getAlCartesDestination() {return this.alCartesDestination;}
	public ArrayList<CarteWagon> getAlCartesWagon() {return this.alCartesWagon;}
	public Image getImgMappe() {return this.imgMappe;}
	public int getNbJoueurMin() {return this.nbJoueurMin;}
	public int getNbJoueurMax() {return this.nbJoueurMax;}
	public int getNbJoueurDoubleVoies() {return this.nbJoueurDoubleVoies;}
	public int getNbWagonJoueur() {return this.nbWagonJoueur;}
	public String getVersoCartePath() {return this.versoCartePath;}

	public boolean setImgMappe(Image imgMappe)                      	{ this.imgMappe = imgMappe; return true; }
	public boolean setNbJoueurMin(int nbJoueurMin)                 	{ this.nbJoueurMin = nbJoueurMin; return true; }
	public boolean setNbJoueurMax(int nbJoueurMax)                 	{ this.nbJoueurMax = nbJoueurMax; return true; }
	public boolean setNbJoueurDoubleVoies(int nbJoueurDoubleVoies) 	{ this.nbJoueurDoubleVoies = nbJoueurDoubleVoies; return true; }
	public boolean setNbWagonJoueur(int nbWagonJoueur)             	{ this.nbWagonJoueur = nbWagonJoueur; return true; }
	public boolean setNbFin(int nbFin)								{ this.nbFin = nbFin; return true; }
	
	public void setVersoCarte(String versoCartePath)				
	{
		this.versoCartePath = versoCartePath;
		for (int i = 1; i < this.alCartesWagon.size(); i++)
			this.alCartesWagon.get(i).setImgVerso(versoCartePath);
	}

	/**********************************************************/
	/* Parcours des ArrayLists et ecriture dans le fichier XML*/
	/**********************************************************/
	public void ecrireXml() 
	{
		PrintWriter pw;

		// Tri des noeuds par ordre alphabetique
		this.alNoeuds.sort(new Comparator<Noeud>() 
		{
			@Override
			public int compare(Noeud o1, Noeud o2) {return o1.getNom().compareTo(o2.getNom());}
		});

		try 
		{
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("sortie/Mappe.xml"), "UTF-8"));

			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			pw.println("<liste>");

			/* Generation XML : Parametre */

			pw.println("\t<parametre>");
			pw.println("\t\t<nbJoueurMin>" + this.nbJoueurMin + "</nbJoueurMin>");
			pw.println("\t\t<nbJoueurMax>" + this.nbJoueurMax + "</nbJoueurMax>");
			pw.println("\t\t<nbJoueurDoubleVoies>" + this.nbJoueurDoubleVoies + "</nbJoueurDoubleVoies>");
			pw.println("\t\t<nbWagonJoueur>" + this.nbWagonJoueur + "</nbWagonJoueur>");
			pw.println("\t\t<nbFin>" + this.nbFin + "</nbFin>");
			pw.println("\t</parametre>");

			/* Generation XML : Noeud */
			for (Noeud noeud : this.alNoeuds) 
			{
				pw.println("\t<noeud nom=\"" + noeud.getNom() + "\">");
				pw.println("\t\t<x>" + noeud.getX() + "</x>");
				pw.println("\t\t<y>" + noeud.getY() + "</y>");
				pw.println("\t\t<nomDeltaX>" + noeud.getNomDeltaX() + "</nomDeltaX>");
				pw.println("\t\t<nomDeltaY>" + noeud.getNomDeltaY() + "</nomDeltaY>");
				pw.println("\t</noeud>");
			}

			/* Generation XML : Arete */
			for (Arete arete : this.alAretes) 
			{
				pw.println("\t<arete>");
				pw.println("\t\t<noeud1>" + arete.getNoeud1().getNom() + "</noeud1>");
				pw.println("\t\t<noeud2>" + arete.getNoeud2().getNom() + "</noeud2>");
				pw.println("\t\t<couleur>" + arete.getCouleur() + "</couleur>");
				pw.println("\t\t<troncons>" + arete.getTroncons() + "</troncons>");
				pw.println("\t\t<voieDouble>" + arete.getVoieDouble() + "</voieDouble>");
				pw.println("\t\t<tronconsVoieDouble>" + arete.getTronconsDoubleVoie() + "</tronconsVoieDouble>");
				pw.println("\t\t<couleurVoieDouble>" + arete.getCouleurDoubleVoie() + "</couleurVoieDouble>");
				pw.println("\t</arete>");
			}

			/* Generation XML : CarteDestination */
			for (CarteDestination carteDestination : this.alCartesDestination) 
			{
				pw.println("\t<carteDestination>");
				pw.println("\t\t<noeud1>" + carteDestination.getNoeud1().getNom() + "</noeud1>");
				pw.println("\t\t<noeud2>" + carteDestination.getNoeud2().getNom() + "</noeud2>");
				pw.println("\t\t<points>" + carteDestination.getPoints() + "</points>");
				pw.println("\t\t<imgRecto>" + carteDestination.getImgRecto() + "</imgRecto>");
				pw.println("\t\t<imgVerso>" + carteDestination.getImgVerso() + "</imgVerso>");
				pw.println("\t</carteDestination>");
			}

			/* Generation XML : CarteWagon */
			for (CarteWagon carteWagon : this.alCartesWagon) 
			{
				pw.println("\t<carteWagon>");
				pw.println("\t\t<nomCouleur>" + carteWagon.getNomCouleur() + "</nomCouleur>");
				pw.println("\t\t<couleur>" + carteWagon.getCouleur().getRGB() + "</couleur>");
				pw.println("\t\t<imgRectoPath>" + carteWagon.getImgRecto() + "</imgRectoPath>");
				pw.println("\t\t<imgVersoPath>" + carteWagon.getImgVerso() + "</imgVersoPath>");
				pw.println("\t\t<nbCarteWagon>" + carteWagon.getNbCarteWagon() + "</nbCarteWagon>");
				pw.println("\t</carteWagon>");
			}

			pw.println("</liste>");

			pw.close();
		} 
		catch (Exception e) {e.printStackTrace();}
	}

	/*****************************************/
	/* Copie une image donnée en paramètres  */
	/* vers le dossier sortie/ du concepteur */
	/*****************************************/
	public void copierImage(String nom, Image image) // Sera bientot supp, les images seront stockes dans le xml
	{
		System.out.println("1");
		try 
		{
			ImageIO.write((RenderedImage) image, "png", new File("sortie/" + nom + ".png"));
			System.out.println("2");
		} 
		catch (IOException e) {e.printStackTrace();}
	}

	/**************************************/
	/* Reinitialisation du dossier sortie */
	/**************************************/
	public boolean reinitialiserDossierSortie()
	{
		File dossierSortie = new File("sortie");
		if (dossierSortie.exists()) 
		{ 
			if (dossierSortie.list().length ==0) { dossierSortie.delete(); }
			else
			{
				String files[] = dossierSortie.list();
     
				for (String tmp : files) {
				   File file = new File(dossierSortie, tmp);
				   //suppression récursive
				   file.delete();
				}
			}
		}
		return dossierSortie.mkdir();
	}

	/**************************************/
	/* Importation d'une carte depuis un  */
	/* dossier                            */
	/**************************************/
	public boolean importMappe(File cheminDossier)
	{
		try 
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(cheminDossier);

			doc.getDocumentElement().normalize();

			/* Paramètres */
			NodeList nList = doc.getElementsByTagName("parametres");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element e = (Element) nNode;
				this.nbJoueurMin = Integer.parseInt(e.getElementsByTagName("nbJoueurMin").item(0).getTextContent());
				this.nbJoueurMax = Integer.parseInt(e.getElementsByTagName("nbJoueurMax").item(0).getTextContent());
				this.nbJoueurDoubleVoies = Integer.parseInt(e.getElementsByTagName("nbJoueurDoubleVoies").item(0).getTextContent());
				this.nbWagonJoueur = Integer.parseInt(e.getElementsByTagName("nbWagonJoueur").item(0).getTextContent());
				this.nbFin = Integer.parseInt(e.getElementsByTagName("nbFin").item(0).getTextContent());
			}

			/* Noeuds */
			nList = doc.getElementsByTagName("noeud");
			for (int i = 0; i < nList.getLength(); i++)
			{
				nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element e = (Element) nNode;
					Noeud noeud = new Noeud(e.getAttribute("nom"), 
								  Integer.parseInt(e.getElementsByTagName("x").item(0).getTextContent()), 
								  Integer.parseInt(e.getElementsByTagName("y").item(0).getTextContent()),
								  Metier.RADIUS);
					noeud.setNomDeltaX(Integer.parseInt(e.getElementsByTagName("nomDeltaX").item(0).getTextContent()) + noeud.getX()); 
					noeud.setNomDeltaY(Integer.parseInt(e.getElementsByTagName("nomDeltaY").item(0).getTextContent()) + noeud.getY());
					this.alNoeuds.add(noeud);
				}
			}			

			/*CarteDestination */
			nList = doc.getElementsByTagName("carteDestination");
			for (int i = 0; i < nList.getLength(); i++)
			{
				nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element e = (Element) nNode;
					Noeud noeud1 = null;
					Noeud noeud2 = null;
					for (Noeud noeud : this.alNoeuds)
					{
						if (noeud.getNom().equals(e.getElementsByTagName("noeud1").item(0).getTextContent())) { noeud1 = noeud; }
						if (noeud.getNom().equals(e.getElementsByTagName("noeud2").item(0).getTextContent())) { noeud2 = noeud; }
					}
					CarteDestination carteDestination = new CarteDestination(noeud1, noeud2, Integer.parseInt(e.getElementsByTagName("points").item(0).getTextContent()),
																			 new ImageIcon(e.getElementsByTagName("imgRecto").item(0).getTextContent()),
																			 new ImageIcon(e.getElementsByTagName("imgVerso").item(0).getTextContent()));
					this.alCartesDestination.add(carteDestination);
				}
			}

			/*CarteWagon */
			nList = doc.getElementsByTagName("carteWagon");
			for (int i = 0; i < nList.getLength(); i++)
			{
				nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element e = (Element) nNode;
					Color couleur = new Color(Integer.parseInt(e.getElementsByTagName("couleur").item(0).getTextContent()));

					CarteWagon carteWagon = new CarteWagon(e.getElementsByTagName("nomCouleur").item(0).getTextContent(),
														   couleur,
														   e.getElementsByTagName("imgRecto").item(0).getTextContent(),
														   e.getElementsByTagName("imgVerso").item(0).getTextContent(),
														   Integer.parseInt(e.getElementsByTagName("nbCarteWagon").item(0).getTextContent()));

					this.alCartesWagon.add(carteWagon);
				}
			}

			/*Arete */
			nList = doc.getElementsByTagName("arete");
			for (int i = 0; i < nList.getLength(); i++)
			{
				nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element e = (Element) nNode;
					Noeud noeud1 = null;
					Noeud noeud2 = null;
					CarteWagon carteWagon = null;
					CarteWagon couleurVoieDouble = null;

					for (Noeud noeud : this.alNoeuds)
					{
						if (noeud.getNom().equals(e.getElementsByTagName("noeud1").item(0).getTextContent())) { noeud1 = noeud; }
						if (noeud.getNom().equals(e.getElementsByTagName("noeud2").item(0).getTextContent())) { noeud2 = noeud; }
					}
					for (CarteWagon carteWagonTemp : this.alCartesWagon)
					{
						if (carteWagonTemp.getNomCouleur().equals(e.getElementsByTagName("couleur").item(0).getTextContent())) { carteWagon = carteWagonTemp; }
						if (carteWagonTemp.getNomCouleur().equals(e.getElementsByTagName("couleurVoieDouble").item(0).getTextContent())) {couleurVoieDouble = carteWagonTemp;}
					}
					Arete arete = new Arete(noeud1, noeud2, carteWagon, 
											Integer.parseInt(e.getElementsByTagName("troncons").item(0).getTextContent()),
											e.getElementsByTagName("voieDouble").item(0).getTextContent().equals("true"),
											couleurVoieDouble,
											Integer.parseInt(e.getElementsByTagName("tronconsVoieDouble").item(0).getTextContent()));
					this.alAretes.add(arete);
				}
			}
			return true;
		}
		catch (Exception e) {e.printStackTrace(); return false;}
	}
}