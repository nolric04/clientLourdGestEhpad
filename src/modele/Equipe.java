package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vue.App;

public class Equipe {
	private int					numEquipe;
	private String				horaire;
	private ArrayList<Employe>	listeEmploye = new ArrayList<>();

	private static String strQueryGetEquipe			=	"SELECT * FROM Equipe WHERE idEquipe = ? ;";
	private static String strQueryGetEquipeMembers	=	"SELECT * FROM Employe WHERE idEquipe = ? ;";
	
	public Equipe(int id, ArrayList<Employe> lEmployes)
	{
		this.numEquipe = id;
		
		try {
			ResultSet rsUsers;
			
			App.getBDD().setPreparedStatement(strQueryGetEquipe);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while( rsUsers.next() )
				this.horaire = rsUsers.getString("horaireEquipe");
			
			App.getBDD().setPreparedStatement(strQueryGetEquipeMembers);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
			{
				for(Employe e : lEmployes)
				{
					if(e.getIdE() == rsUsers.getInt("idEmploye"))
						listeEmploye.add(e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getNumEquipe() {
		return numEquipe;
	}

	public String getHoraire() {
		return horaire;
	}

	public ArrayList<Employe> getListeEmploye() {
		return listeEmploye;
	}

	public void setNumEquipe(int numEquipe) {
		this.numEquipe = numEquipe;
	}

	public void setHoraire(String horaire) {
		this.horaire = horaire;
	}

	@Override
	public String toString() {
		return "Equipe [listeEmploye=" + listeEmploye + "]";
	}
	
	
}
