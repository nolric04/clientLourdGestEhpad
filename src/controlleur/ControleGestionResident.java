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
	 * cr�ation ou modification r�sident
	 * @param cr		parametres r�sident � cr�er		
	 * @param res		si null, cr�ation r�sident
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
			
			// r�cup�re idRegime en base
			int idRegime = 0;
			strQuery = "SELECT idRegime FROM regime WHERE nomRegime = '" + res.getNomRegime() + "';";
			App.getBDD().setPreparedStatement(strQuery);
			rs = App.getBDD().getPreparedStatement().executeQuery();
			while(rs.next()) {
				idRegime = rs.getInt(1);
			}

			//Modification d'un r�sident
			if (id !=0) {
				
				// r�cup�re idDossierMedical en base
				int idDossierMedical = 0;
				strQuery = "SELECT idDossierMedical FROM dossiermedical WHERE idResident = " + id + ";";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idDossierMedical = rs.getInt(1);
				}
				
				// si le r�sident n'a pas de dossier m�dical
				if(idDossierMedical == 0) {
					
					// ins�re un dossier m�dical
					strQuery="INSERT INTO dossiermedical (idRegime, idResident) VALUES (" 
							+ idRegime + ", " + id + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
					
					// r�cup�re idDossierMedical en base
					strQuery = "SELECT idDossierMedical FROM dossiermedical WHERE idResident = " + id + ";";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idDossierMedical = rs.getInt(1);
					}
				}
				
				// modifie les pathologies du r�sident
				// efface les pathologies du r�sident
				strQuery = "DELETE FROM listepathologie WHERE idDossierMedical = " + idDossierMedical + ";";						
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				// cr�e les pathologies du r�sident
				for(int j=0;j<res.getNomPathologie().size();j++) 
				{
					// r�cup�re idPathologie en base
					int idPathologie = 0;
					strQuery = "SELECT idPathologie FROM pathologie WHERE nomPathologie = '" + res.getNomPathologie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idPathologie = rs.getInt(1);
					}
					// ins�re les pathologies du r�sident en base
					strQuery="INSERT INTO listepathologie (idPathologie, idDossierMedical) VALUES (" 
							+ idPathologie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
				
				// modifie les allergies du r�sident
				// efface les allergies du r�sident
				strQuery = "DELETE FROM listeallergie WHERE idDossierMedical = " + idDossierMedical + ";";						
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				// cr�e les pathologies du r�sident
				for(int j=0;j<res.getNomAllergie().size();j++) 
				{
					// r�cup�re idAllergie en base
					int idAllergie = 0;
					strQuery = "SELECT idAllergie FROM allergie WHERE nomAllergie = '" + res.getNomAllergie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idAllergie = rs.getInt(1);
					}
					// ins�re les allergies du r�sident en base
					strQuery="INSERT INTO listeallergie (idAllergie, idDossierMedical) VALUES (" 
							+ idAllergie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
				
				// modifie r�gime du r�sident
				strQuery = "UPDATE dossiermedical SET idRegime = " + idRegime + " WHERE idResident = " + res.getId() + ";";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// r�cup�re idContact en base
				int idContact = 0;
				strQuery = "SELECT idContactUrgence FROM resident WHERE idResident = '" + id + "';";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idContact = rs.getInt(1);
				}
				
				// modifie informations du contact du r�sident
				strQuery = "UPDATE contacturgence SET nomContact = '" + res.getNomUrgence() + "' , numContact = '" + res.getNumUrgence()
								+  "', relationUrgence = '" + res.getRelationUrgence() 
								+ "' WHERE idContactUrgence = " + idContact + ";";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// modifie informations du r�sident
				strQuery = "UPDATE resident SET nomResident = '" + res.getNom() + "' , prenomResident = '" + res.getPrenom() + "' , idEtablissement = 1,"
								+ " naissanceResident = '" + res.getDateNaissance() + "', " 
								+ "numSecuResident = '" + res.getNumSecu() + "', numChambreResident = " + res.getNumChambre() 
								+ " WHERE idResident = " + res.getId() + ";";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				
				
			}
			
			//Cr�ation d'un r�sident
			else {
				// ins�re le contact d'urgence du r�sident
				strQuery = "INSERT INTO contacturgence (nomContact, numContact, relationUrgence) VALUES ('" 
						+ res.getNomUrgence() + "', '" + res.getNumUrgence() + "', '" + res.getRelationUrgence() + "');";		
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// r�cup�re idContact en base
				int idContact = 0;
				strQuery = "SELECT idContactUrgence FROM contacturgence WHERE numContact = '" + res.getNumUrgence() + "';";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idContact = rs.getInt(1);
				}
				
				// ins�re le nouveau r�sident en base
				strQuery = "INSERT INTO resident (nomResident, prenomResident, naissanceResident, idEtablissement, numSecuResident, numChambreResident, idContactUrgence) VALUES ('" 
							+ res.getNom() + "', '" + res.getPrenom() + "', '" + res.getDateNaissance() + "', 1,"
							+ "'" + res.getNumSecu() + "', " + res.getNumChambre() + ", " + idContact + ");";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// r�cup�re idResident en base
				int idRes = 0;
				strQuery = "SELECT idResident FROM resident WHERE numSecuResident = '" + res.getNumSecu() + "';";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idRes = rs.getInt(1);
				}
				
				// ins�re le r�gime du r�sident, cr�ation dossier m�dical
				strQuery = "INSERT INTO dossiermedical (idRegime, idResident) VALUES (" 
						+ idRegime + ", " + idRes + ");";
				App.getBDD().setPreparedStatement(strQuery);
				App.getBDD().getPreparedStatement().executeUpdate();
				
				// r�cup�re idDossierMedical en base
				int idDossierMedical = 0;
				strQuery = "SELECT idDossierMedical FROM dossiermedical WHERE idResident = " + id + ";";
				App.getBDD().setPreparedStatement(strQuery);
				rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
					idDossierMedical = rs.getInt(1);
				}				
				
				// cr�e les pathologies du r�sident
				for(int j=0;j<res.getNomPathologie().size();j++) 
				{
					
					// r�cup�re idPathologie en base
					int idPathologie = 0;
					strQuery = "SELECT idPathologie FROM pathologie WHERE nomPathologie = '" + res.getNomPathologie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idPathologie = rs.getInt(1);
					}
					
					// ins�re les pathologies du r�sident en base
					strQuery="INSERT INTO listepathologie (idPathologie, idDossierMedical) VALUES (" 
							+ idPathologie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
				
				// cr�e les pathologies du r�sident
				for(int j=0;j<res.getNomAllergie().size();j++) 
				{
					// r�cup�re idPathologie en base
					int idAllergie = 0;
					strQuery = "SELECT idAllergie FROM allergie WHERE nomAllergie = '" + res.getNomAllergie().get(j) + "';";
					App.getBDD().setPreparedStatement(strQuery);
					rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
						idAllergie = rs.getInt(1);
					}
					
					// ins�re les allergies du r�sident en base
					strQuery="INSERT INTO listeallergie (idAllergie, idDossierMedical) VALUES (" 
							+ idAllergie + ", " + idDossierMedical + ");";	
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
				}	
			}
			
			// D�connexion de la base de donn�es
			App.getBDD().disconnect();
			
			App.getEtablissement().getListResident().add(res);
			
			// Mise � jour de l'affichage, retour � la liste de r�sidents
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
