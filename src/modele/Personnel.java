package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import vue.App;

public abstract class Personnel {
	
	private int		id;
	private String	nom;
	private String	prenom;
	private String	intitulePoste;
	private int		numTelPoste;
	private String	identifiant;
	private String	mdp;
	private String  service;

	private static String strQueryGetPersonel = "SELECT * FROM Personnel join Service USING (idService) WHERE idPersonnel = ?;";
	
	public Personnel(int id)
	{
		this.id = id;

		actualise();
	}
	
	public void actualise()
	{
		ResultSet rsUsers;
	
		try {
			App.getBDD().setPreparedStatement(strQueryGetPersonel);
			App.getBDD().getPreparedStatement().setInt(1, this.id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
			{
				this.nom = rsUsers.getString("nomPersonnel");
				this.prenom = rsUsers.getString("prenomPersonnel");
				this.intitulePoste = rsUsers.getString("intitulePoste");
				this.numTelPoste = rsUsers.getInt("numTelPoste");
				this.identifiant = rsUsers.getString("identifiant");
				this.mdp = rsUsers.getString("password");
				this.service = rsUsers.getString("nomService");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getIntitulePoste() {
		return intitulePoste;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public String getMdp() {
		return mdp;
	}

	public int getNumTelPoste() {
		return numTelPoste;
	}

	public void setNumTelPoste(int numTelPoste) {
		this.numTelPoste = numTelPoste;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setIntitulePoste(String intitulePoste) {
		this.intitulePoste = intitulePoste;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public int getId() {
		return id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
	

}
