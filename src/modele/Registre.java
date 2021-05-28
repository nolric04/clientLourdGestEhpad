package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vue.App;

public class Registre {
	
	private ArrayList<Evenement>	listEvenements = new ArrayList<>();
	private String					service;

	private static String strQueryGetEvenements = "SELECT * FROM Evenement where idRegistre = ?;";
	
	public Registre(int id, String nomService, ArrayList<Personnel> lPersonnel, ArrayList<Resident> lResident)
	{
		this.service = nomService;
		
		try {
			ResultSet rsUsers;
			App.getBDD().setPreparedStatement(strQueryGetEvenements);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listEvenements.add(new Evenement(rsUsers.getInt("idEvenement"), lPersonnel, lResident));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<Evenement> getListEvenements() {
		return listEvenements;
	}

	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	
}
