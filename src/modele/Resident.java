package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vue.App;


public class Resident {
	
	// Dossier General
	private int 				id;
	private String				nom;
	private String				prenom;
	private String				dateNaissance;
	private String				numSecu;
	private int					numChambre;
	private String				numUrgence;
	private String				nomUrgence;
	private String				relationUrgence;
	
	// Dossier Medical
	private String				nomRegime;
	private ArrayList<String>	nomAllergie = new ArrayList<>();
	private ArrayList<String>	nomPathologie = new ArrayList<>();
	
	/*
	 * Liste des Requetes SQL pour la creation de l'objet Resident
	 */
	private static String strQueryGetGeneralData	=	"SELECT * FROM Resident JOIN ContactUrgence USING (idContactUrgence) WHERE idResident = ? ;";
	private static String strQueryGetMedicalRegime	=	"SELECT * FROM Regime r join DossierMedical d using (idRegime) where d.idResident = ?;";
	private static String strQueryGetMedicalAllergie	=	"SELECT * FROM DossierMedical m join ListeAllergie la using (idDossierMedical) join Allergie a using (idAllergie) where m.idResident = ?;";
	private static String strQueryGetMedicalPathologie	=	"SELECT * FROM DossierMedical m join ListePathologie lp using (idDossierMedical) join Pathologie p using (idPathologie) where m.idResident = ?;";
	
	public Resident(int id)
	{
		this.id = id;
		try {
			ResultSet rsUsers;
			
			App.getBDD().setPreparedStatement(strQueryGetGeneralData);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
			{
				this.nom = rsUsers.getString("nomResident");
				this.prenom = rsUsers.getString("prenomResident");
				this.dateNaissance = rsUsers.getString("naissanceResident");
				this.numSecu = rsUsers.getString("numSecuResident");
				this.numChambre = rsUsers.getInt("numChambreResident");
				

				this.numUrgence = rsUsers.getString("numContact");
				this.nomUrgence = rsUsers.getString("nomContact");
				this.relationUrgence = rsUsers.getString("relationUrgence");
			}

			App.getBDD().setPreparedStatement(strQueryGetMedicalRegime);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				this.nomRegime = rsUsers.getString("nomRegime");

			App.getBDD().setPreparedStatement(strQueryGetMedicalAllergie);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				this.nomAllergie.add(rsUsers.getString("nomAllergie"));

			App.getBDD().setPreparedStatement(strQueryGetMedicalPathologie);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				this.nomPathologie.add(rsUsers.getString("nomPathologie"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public Resident(String nom, String prenom, String dateNaissance, String numSecu, int numChambre, String numUrgence, String nomUrgence, String relationUrgence, String nomRegime, ArrayList<String>	nomAllergie, ArrayList<String> nomPathologie)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.numSecu = numSecu;
		this.numChambre = numChambre;
		this.numUrgence = numUrgence;
		this.nomUrgence = nomUrgence;
		this.relationUrgence = relationUrgence;
		this.nomRegime = nomRegime;
		this.nomAllergie = nomAllergie;
		this.nomPathologie = nomPathologie;
	}
	
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public int getNumChambre() {
		return numChambre;
	}

	public String getNumUrgence() {
		return numUrgence;
	}

	public String getNomUrgence() {
		return nomUrgence;
	}

	public String getRelationUrgence() {
		return relationUrgence;
	}

	public String getNomRegime() {
		return nomRegime;
	}

	public ArrayList<String> getNomAllergie() {
		return nomAllergie;
	}

	public ArrayList<String> getNomPathologie() {
		return nomPathologie;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public void setNumChambre(int numChambre) {
		this.numChambre = numChambre;
	}

	public void setNumUrgence(String numUrgence) {
		this.numUrgence = numUrgence;
	}

	public void setNomUrgence(String nomUrgence) {
		this.nomUrgence = nomUrgence;
	}

	public void setRelationUrgence(String relationUrgence) {
		this.relationUrgence = relationUrgence;
	}

	public void setNomRegime(String nomRegime) {
		this.nomRegime = nomRegime;
	}

	public void setNomPathologie(ArrayList<String> nomPathologie) {
		this.nomPathologie = nomPathologie;
	}
	
	public void setNomAllergie(ArrayList<String> nomAllergie) {
		this.nomAllergie = nomAllergie;
	}

	public String getNumSecu() {
		return numSecu;
	}

	public void setNumSecu(String numSecu) {
		this.numSecu = numSecu;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return nom + " " + prenom;
	}

	
}
