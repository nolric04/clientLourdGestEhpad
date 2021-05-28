package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vue.App;

public class Evenement {

	private int					gravite;
	private Personnel			declarant;
	private Date				dateEmission;
	private String				titre;
	private String				description;
	private ArrayList<Resident>	listeResident = new ArrayList<>();

	private static String strQueryGetEvenement			= "SELECT * FROM Evenement where idEvenement = ?;";
	private static String strQueryGetResidentConcerne	= "SELECT * FROM ListResidentEvent where idEvent = ?;";
	
	public Evenement(int id, ArrayList<Personnel> lPersonnel, ArrayList<Resident> lResident)
	{
		try {
			ResultSet rsUsers;
			ResultSet rsUsers2;
			
			App.getBDD().setPreparedStatement(strQueryGetEvenement);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
			{
				this.gravite = rsUsers.getInt("graviteEvent");
				this.dateEmission = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rsUsers.getString("dateEmission"));
				this.titre = rsUsers.getString("titreEvent");
				this.description = rsUsers.getString("descriptionEvent");
				for(Personnel p : lPersonnel)
				{
					if(p.getId() == rsUsers.getInt("idPersonnel"))
						this.declarant = p;
				}
				
				App.getBDD().setPreparedStatement(strQueryGetResidentConcerne);
				App.getBDD().getPreparedStatement().setInt(1, rsUsers.getInt("idEvenement"));
				rsUsers2 = App.getBDD().getPreparedStatement().executeQuery();
				while(rsUsers2.next())
				{
					for(Resident r : lResident)
					{
						if(r.getId() == rsUsers2.getInt("idResident"))
							this.listeResident.add(r);
					}
				}				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setListeResident(ArrayList<Resident> listeResident) {
		this.listeResident = listeResident;
	}

	public int getGravite() {
		return gravite;
	}

	public Personnel getDeclarant() {
		return declarant;
	}

	public Date getDateEmission() {
		return dateEmission;
	}

	public String getTitre() {
		return titre;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Resident> getListeResident() {
		return listeResident;
	}

	public void setGravite(int gravite) {
		this.gravite = gravite;
	}

	public void setDeclarant(Personnel declarant) {
		this.declarant = declarant;
	}

	public void setDateEmission(Date dateEmmission) {
		this.dateEmission = dateEmmission;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
