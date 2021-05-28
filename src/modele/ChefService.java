package modele;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vue.App;

public class ChefService extends Personnel{
	


	private ArrayList<Equipe>	listeEquipe = new ArrayList<>();
	private ArrayList<Reunion>	listeReunion = new ArrayList<>();

	private static String strQueryGetChefEquipe		=	"SELECT * FROM Equipe WHERE idChef = ? ;";
	private static String strQueryGetChefReunion	=	"SELECT * FROM ListeReunionChef WHERE idChef = ? ;";
	
	public ChefService(int idC, int idP, ArrayList<Employe> lEmployes)
	{
		super(idP);
		
		ResultSet rsUsers;
		
		try {
			App.getBDD().setPreparedStatement(strQueryGetChefEquipe);
			App.getBDD().getPreparedStatement().setInt(1, idC);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listeEquipe.add(new Equipe(rsUsers.getInt("idEquipe"), lEmployes));

			App.getBDD().setPreparedStatement(strQueryGetChefReunion);
			App.getBDD().getPreparedStatement().setInt(1, idC);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listeReunion.add(new Reunion(rsUsers.getInt("idReunion")));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}

	public ArrayList<Equipe> getListeEquipe() {
		return listeEquipe;
	}
	
	public ArrayList<Reunion> getListeReunion() {
		return listeReunion;
	}
	
	@Override
	public String toString() {
		return this.getIdentifiant();
	}
}
