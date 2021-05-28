package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vue.App;

public class Directeur extends Personnel{
	
	private ArrayList<Date> listVisites = new ArrayList<>();
	private ArrayList<Reunion> listReunions = new ArrayList<>();
	private int idD;
	public ArrayList<Date> getListVisites() {
		return listVisites;
	}


	public ArrayList<Reunion> getListReunions() {
		return listReunions;
	}

	private static String strQueryGetVisite 	= "SELECT * FROM Visite WHERE idDirecteur = ?;";
	private static String strQueryGetReunion	= "SELECT * FROM ListeReunionDirecteur WHERE idDirecteur = ?;";
	
	public Directeur(int idD, int idP)
	{
		super(idP);
		this.idD = idD;
		ResultSet rsUsers;
		
		try {
			App.getBDD().setPreparedStatement(strQueryGetVisite);
			App.getBDD().getPreparedStatement().setInt(1, idD);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listVisites.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rsUsers.getString("dateVisite")));
			

			App.getBDD().setPreparedStatement(strQueryGetReunion);
			App.getBDD().getPreparedStatement().setInt(1, idD);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listReunions.add(new Reunion(rsUsers.getInt("idReunion")));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}
	

	public int getIdD() {
		return idD;
	}


	

}
