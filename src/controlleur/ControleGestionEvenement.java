package controlleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import modele.Administrateur;
import modele.Evenement;
import modele.Personnel;
import modele.Resident;
import vue.App;
import vue.CreationEvenement;
import vue.ViewEvenement;
import vue.ViewMultiRegistre;
import vue.ViewResident;

public class ControleGestionEvenement 
{	
	
	/**
	 * 
	 * @param e va permettre de recuperer les getters de CreationEvenement
	 * @param event va récuperer l'Evenement à modifier, si null création evenement
	 * @throws ParseException
	 * @throws SQLException
	 */
	

	public ControleGestionEvenement(CreationEvenement e,Evenement event ) throws ParseException, SQLException
	{
		
		String titreText = e.getTitreTexte().getText();
		String description = e.getDescriptionTexte().getText();
		String date = e.getDateLabel().getText();
		int indexRegistre = e.getSelectRegistre().getSelectedIndex();		
		String registre = e.getSelectRegistre().getItemAt(indexRegistre).toString();
		String importance = e.getSelectImportance().getSelectedItem().toString();
		int idevent=0;

		
		/*
		 * gestion Si event= null, alors création evenement
		 */
		if(event==null)
			{
			idevent=0;
			}
		else 
		{
			idevent = Utils.returnIdinBdd("evenement", "descriptionEvent",event.getDescription());
		}
		
		ArrayList <Resident> listRes = new ArrayList<Resident>();
		

		/*
		 * integre les résidents selectionnés(CreationEvenement) dans listRes
		 */
		for(int i=0 ; i<e.getDl().getList().size() ; i++) 
		{		
			listRes.add((Resident) e.getDl().getList().get(i));
			
		}
		System.out.println(listRes);
			//TODO A boucler pour recuperation de la liste 
		
			if (titreText != "" && description != "" && date != ""&& registre != "" && importance != "") 
			{
				
				
				/*******************************************************************************
				 * injecter evenement dans bdd uniquement
				 *******************************************************************************/
				
				String strQuery=null;				
				App.getBDD().connect(); 				
				/*******************************************************************************
				 * Modification d'un evenement
				 *******************************************************************************/
				if (idevent !=0) 
				{
					/*
					 * On modifie les valeurs de l'évenement
					 */
					event.setTitre(titreText);
					event.setDescription(description);
					event.setDateEmission(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
					event.setGravite(Integer.parseInt(importance));
					event.setListeResident(listRes);

					/*******************************************************************************
					 *  modifie informations de l'event
					 *******************************************************************************/
					System.out.println( event.getGravite());
					System.out.println(event.getTitre());
					System.out.println(event.getDescription());
					System.out.println(date);
					System.out.println(idevent);
					/*******************************************************************************
					 *  Mise à jour de l'affichage, retour à la liste de résidents
					 *******************************************************************************/
					String nomRegistre=(String) e.getSelectRegistre().getSelectedItem();
					for (int i=0; i<App.getEtablissement().getListRegistre().size();i++)
					{
						if (e.getSelectRegistre().getSelectedItem().equals(App.getEtablissement().getListRegistre().get(i).getService()))
						{
							nomRegistre=App.getEtablissement().getListRegistre().get(i).getService();
						}
					}
					/*******************************************************************************
					 *  récupère idregistre en BDD
					 **************************************************************************/
					
					int idRegistre = 0;
					strQuery = "SELECT idService FROM service WHERE nomService = '" + nomRegistre + "';";
					App.getBDD().setPreparedStatement(strQuery);
					ResultSet rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
					idRegistre = rs.getInt(1);
					}
					
					strQuery = "UPDATE evenement SET graviteEvent = '" + event.getGravite()
									+  "' , titreEvent = '" + event.getTitre()
									+ "' , descriptionEvent = '"+event.getDescription()
									+"' , idRegistre = '"+idRegistre
									+ "' WHERE idEvenement = " + idevent  + ";";
					
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
					
					/*******************************************************************************
					 *  modifie les residents concernés par l'event
					 *******************************************************************************/

					try {
					strQuery = "DELETE FROM `listresidentevent` WHERE idEvent = "+idevent;						
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
					}catch (Exception e1) {
					}
					
					for(int j=0;j<listRes.size();j++) 
					{
						strQuery="INSERT INTO listresidentevent (idResident, idEvent) VALUES ('" 
								+ listRes.get(j).getId() + "', '" + idevent + "');";
						App.getBDD().setPreparedStatement(strQuery);
						App.getBDD().getPreparedStatement().executeUpdate();
						
					}	
					
				}
				/*************************************************************************************************************************************************************
				 * Création d'un résident
				 *************************************************************************************************************************************************************/
				else 
				{		
		
					/*******************************************************************************
					 *  Mise à jour de l'affichage, retour à la liste de résidents
					 *******************************************************************************/
					String nomRegistre=(String) e.getSelectRegistre().getSelectedItem();
					for (int i=0; i<App.getEtablissement().getListRegistre().size();i++)
					{
						if (e.getSelectRegistre().getSelectedItem().equals(App.getEtablissement().getListRegistre().get(i).getService()))
						{
							nomRegistre=App.getEtablissement().getListRegistre().get(i).getService();
						}
					}
					/*******************************************************************************
					 *  récupère idregistre en BDD
					 **************************************************************************/
					int idRegistre = 0;
					strQuery = "SELECT idService FROM service WHERE nomService = '" + nomRegistre + "';";
					App.getBDD().setPreparedStatement(strQuery);
					ResultSet rs = App.getBDD().getPreparedStatement().executeQuery();
					while(rs.next()) {
					idRegistre = rs.getInt(1);
					}
					System.out.println("");
					
					
					/*******************************************************************************
					 * insère les valeurs dans evenement
					 *******************************************************************************/


					strQuery = "INSERT INTO evenement (graviteEvent, dateEmission, titreEvent, descriptionEvent, idRegistre, idPersonnel) "
							+ "VALUES ('" + importance + "', '" + date +  "', '" +titreText + "', '" +description+"','"
							+ idRegistre+"','"+App.getConnected().getId()+"');";
					
					App.getBDD().setPreparedStatement(strQuery);
					App.getBDD().getPreparedStatement().executeUpdate();
					
					/*******************************************************************************
					 *  insere id resident et idEvent dans listresidentEvent
					 *******************************************************************************/
					System.out.println();
					for(int j=0;j<listRes.size();j++) 
					{
						
						strQuery="INSERT INTO listresidentevent (idResident, idEvent) VALUES ('" 
								+ listRes.get(j).getId() + "', '" + Utils.returnIdinBdd("evenement", "dateEmission", date) + "');";		
						App.getBDD().connect();
						App.getBDD().setPreparedStatement(strQuery);
						App.getBDD().getPreparedStatement().executeUpdate();
						
					}					
				}


				/*******************************************************************************
				 *  Mise à jour de l'affichage, retour à la liste de résidents
				 *******************************************************************************/
				String nomRegistre=(String) e.getSelectRegistre().getSelectedItem();
				for (int i=0; i<App.getEtablissement().getListRegistre().size();i++)
				{
					if (e.getSelectRegistre().getSelectedItem().equals(App.getEtablissement().getListRegistre().get(i).getService()))
					{
						nomRegistre=App.getEtablissement().getListRegistre().get(i).getService();
					}
				}
				
				/*******************************************************************************
				 *  récupère idregistre en BDD
				 **************************************************************************/
				int idRegistre = 0;
				strQuery = "SELECT idService FROM service WHERE nomService = '" + nomRegistre + "';";
				App.getBDD().setPreparedStatement(strQuery);
				ResultSet rs = App.getBDD().getPreparedStatement().executeQuery();
				while(rs.next()) {
				idRegistre = rs.getInt(1);
				}				
				
				App.getEtablissement().getListRegistre().get(idRegistre-1).getListEvenements().add(event);				
				/********************************************************************************
				 *  Déconnexion de la base de données
				 *******************************************************************************/
				Utils.reloadAll();
				App.getBDD().disconnect();
				
				/*******************************************************************************
				 * changement de frame
				 *******************************************************************************/
				App.getContent().removeAll();
				App.getContent().add(new ViewMultiRegistre((App.getConnected().getClass() == Administrateur.class)));
				App.getContent().repaint();
				App.getContent().revalidate();	
			}
			else 
			{
				System.out.println("Un des champs est vide");
			}
		
	}
}