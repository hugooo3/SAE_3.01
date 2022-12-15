package metier;

public class Noeud 
{
	protected String nom;
	protected int x;
	protected int y;

	public Noeud(String nom, int x, int y)
	{
		this.nom = nom;
		this.x = x;
		this.y = y;
	}

	public String getNom() {return this.nom;}
	public int getX() {return this.x;}
	public int getY() {return this.y;}

	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setNom(String nom) {this.nom = nom;}

	@Override
	public String toString() {
		return this.nom;
	}
}