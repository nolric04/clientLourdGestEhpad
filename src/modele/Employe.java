package modele;

public class Employe extends Personnel{
	
	private int ide;
	

	public Employe(int idE, int idP)
	{
		super(idP);
		this.ide = idE;
	}
	
	public int getIdE() {
		return ide;
	}
	

	@Override
	public String toString() {
		return this.getNom() + " " + this.getPrenom();
	}
	
}
