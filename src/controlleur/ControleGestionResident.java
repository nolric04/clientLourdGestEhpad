package controlleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import modele.Resident;
import vue.App;
import vue.CreationResident;
import vue.ViewResident;

//TODO Ajouter les requetes SQL

public class ControleGestionResident
{
	/**
	 * création ou modification résident
	 * @param cr		parametres résident à créer		
	 * @param res		si null, création résident
	 * @throws SQLException
	 */
	public ControleGestionResident(CreationResident cr, Resident res) throws SQLException
	{
		
		String prenomRes = cr.getPrenomRes().getText();
		String nomRes = cr.getNomRes().getText();
		String numSSRes = cr.getNumSSRes().getText();
		String numChambreRes = cr.getNumChambreRes().getText();
		String nomContact = cr.getNomContact().getText();
		String numTelContact = cr.getNumTelContact().getText();
		String lienContact = cr.getLienContact().getText();
		String dateNaissance = cr.getDateDebut().getJFormattedTextField().getText();
		String texture = (String) cr.getTexture().getSelectedItem();
		
		int id = res.getId();

	
		if (dateNaissance != "" && prenomRes != "" && nomRes != "" && numSSRes != ""&& numChambreRes != "" && nomContact != "" &&  numTelContact != "" && lienContact != "") {
			res.setPrenom(prenomRes);
			res.setNom(nomRes);
			res.setNumSecu(numSSRes);
			res.setNumChambre(Integer.parseInt(numChambreRes));
			res.setNomUrgence(nomContact);
			res.setNumUrgence(numTelContact);
			res.setRelationUrgence(lienContact);
			res.setDateNaissance(dateNaissance);
			res.setNomRegime(texture);
			res.setNomPathologie(cr.getPathologies().getList());
			res.setNomAllergie(cr.getAllergies().getList());
			
			String strQuery;
			
			App.getBDD().connect(); 
			
			ResultSet rs;
			
			// récupère idRegime en base
			int idRegime = 0;
			strQuery = "SELECT idRegime FROM regime WHERE nomRegime = '" + res.getNomRegime() + "';";
			App.getBDD().setPreparedStatement(strQuery);
			rs = App.getBDD().getPreparedStatement().executeQuery();
			while(rs.next()) {
				idRegime = rs.getInt(1);
			}

			//Modification d'un résident
			if (id !=0) {
				
				// récupère idDossierMedical en base
				int idDossierMedical = 0;
				strQuery = "SELECT idDossierMedical FROM dossiermedical WHERE idResident = " + id + ";";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idDossierMedical = rs.getInt(1);
				}
				
				// si le résident n'a pas de dossier médical
				if(idDossierMedical == 0) {
					
					// insère un dossier médical
					strQuery="INSERT INTO dossiermedical (idRegime, idResident) VALUES (" 
							+ idRegime + ", " + id + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
					
					// récupère idDossierMedical en base
					strQuery = "SELECT idDossierMedical FROM dossiermedical WHERE idResident = " + id + ";";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idDossierMedical = rs.getInt(1);
					}
				}
				
				// modifie les pathologies du résident
				// efface les pathologies du résident
				strQuery = "DELETE FROM listepathologie WHERE idDossierMedical = " + idDossierMedical + ";";						
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				// crée les pathologies du résident
				for(int j=0;j<res.getNomPathologie().size();j++) 
				{
					// récupère idPathologie en base
					int idPathologie = 0;
					strQuery = "SELECT idPathologie FROM pathologie WHERE nomPathologie = '" + res.getNomPathologie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idPathologie = rs.getInt(1);
					}
					// insère les pathologies du résident en base
					strQuery="INSERT INTO listepathologie (idPathologie, idDossierMedical) VALUES (" 
							+ idPathologie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
				
				// modifie les allergies du résident
				// efface les allergies du résident
				strQuery = "DELETE FROM listeallergie WHERE idDossierMedical = " + idDossierMedical + ";";						
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				// crée les pathologies du résident
				for(int j=0;j<res.getNomAllergie().size();j++) 
				{
					// récupère idAllergie en base
					int idAllergie = 0;
					strQuery = "SELECT idAllergie FROM allergie WHERE nomAllergie = '" + res.getNomAllergie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idAllergie = rs.getInt(1);
					}
					// insère les allergies du résident en base
					strQuery="INSERT INTO listeallergie (idAllergie, idDossierMedical) VALUES (" 
							+ idAllergie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
				
				// modifie régime du résident
				strQuery = "UPDATE dossiermedical SET idRegime = " + idRegime + " WHERE idResident = " + res.getId() + ";";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// récupère idContact en base
				int idContact = 0;
				strQuery = "SELECT idContactUrgence FROM resident WHERE idResident = '" + id + "';";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idContact = rs.getInt(1);
				}
				
				// modifie informations du contact du résident
				strQuery = "UPDATE contacturgence SET nomContact = '" + res.getNomUrgence() + "' , numContact = '" + res.getNumUrgence()
								+  "', relationUrgence = '" + res.getRelationUrgence() 
								+ "' WHERE idContactUrgence = " + idContact + ";";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// modifie informations du résident
				strQuery = "UPDATE resident SET nomResident = '" + res.getNom() + "' , prenomResident = '" + res.getPrenom() + "' , idEtablissement = 1,"
								+ " naissanceResident = '" + res.getDateNaissance() + "', " 
								+ "numSecuResident = '" + res.getNumSecu() + "', numChambreResident = " + res.getNumChambre() 
								+ " WHERE idResident = " + res.getId() + ";";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				
				
			}
			
			//Création d'un résident
			else {
				// insère le contact d'urgence du résident
				strQuery = "INSERT INTO contacturgence (nomContact, numContact, relationUrgence) VALUES ('" 
						+ res.getNomUrgence() + "', '" + res.getNumUrgence() + "', '" + res.getRelationUrgence() + "');";		
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// récupère idContact en base
				int idContact = 0;
				strQuery = "SELECT idContactUrgence FROM contacturgence WHERE numContact = '" + res.getNumUrgence() + "';";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idContact = rs.getInt(1);
				}
				
				// insère le nouveau résident en base
				strQuery = "INSERT INTO resident (nomResident, prenomResident, naissanceResident, idEtablissement, numSecuResident, numChambreResident, idContactUrgence) VALUES ('" 
							+ res.getNom() + "', '" + res.getPrenom() + "', '" + res.getDateNaissance() + "', 1,"
							+ "'" + res.getNumSecu() + "', " + res.getNumChambre() + ", " + idContact + ");";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// récupère idResident en base
				int idRes = 0;
				strQuery = "SELECT idResident FROM resident WHERE numSecuResident = '" + res.getNumSecu() + "';";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idRes = rs.getInt(1);
				}
				
				// insère le régime du résident, création dossier médical
				strQuery = "INSERT INTO dossiermedical (idRegime, idResident) VALUES (" 
						+ idRegime + ", " + idRes + ");";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// récupère idDossierMedical en base
				int idDossierMedical = 0;
				strQuery = "SELECT idDossierMedical FROM dossiermedical WHERE idResident = " + id + ";";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idDossierMedical = rs.getInt(1);
				}				
				
				// crée les pathologies du résident
				for(int j=0;j<res.getNomPathologie().size();j++) 
				{
					
					// récupère idPathologie en base
					int idPathologie = 0;
					strQuery = "SELECT idPathologie FROM pathologie WHERE nomPathologie = '" + res.getNomPathologie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idPathologie = rs.getInt(1);
					}
					
					// insère les pathologies du résident en base
					strQuery="INSERT INTO listepathologie (idPathologie, idDossierMedical) VALUES (" 
							+ idPathologie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
				
				// crée les pathologies du résident
				for(int j=0;j<res.getNomAllergie().size();j++) 
				{
					// récupère idPathologie en base
					int idAllergie = 0;
					strQuery = "SELECT idAllergie FROM allergie WHERE nomAllergie = '" + res.getNomAllergie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idAllergie = rs.getInt(1);
					}
					
					// insère les allergies du résident en base
					strQuery="INSERT INTO listeallergie (idAllergie, idDossierMedical) VALUES (" 
							+ idAllergie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
			}
			
			// Déconnexion de la base de données
			App.getBDD().disconnect();
			
			App.getEtablissement().getListResident().add(res);
			
			// Mise à jour de l'affichage, retour à la liste de résidents
			App.getContent().removeAll();
			App.getContent().add(new ViewResident());
			App.getContent().repaint();
			App.getContent().revalidate();	
			
		}
		else {
			System.out.println("Un des champs est vide");
		}
	}
}
