package controlleur;

import java.sql.SQLException;

import modele.ChefService;
import modele.Directeur;
import modele.Employe;
import modele.Personnel;
import vue.App;
import vue.CreationPersonnel;
import vue.ViewPersonnel;

public class ControleGestionPersonnel {
	
	private static String strQueryUpdatePersonnel	= "UPDATE personnel SET nomPersonnel = ? , prenomPersonnel = ? , identifiant = ? , password = ? , intitulePoste = ? , numTelPoste = ? , idService = ? WHERE personnel.idPersonnel = ?;";
	private static String strQueryInsertPersonnel	= "INSERT INTO personnel (nomPersonnel, prenomPersonnel, identifiant, password, intitulePoste, numTelPoste, idEtablissement, idService) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static String strQueryInsertDir			= "INSERT INTO directeur (idPersonnel) VALUES ( ? );";
	private static String strQueryInsertChef		= "INSERT INTO chef (idPersonnel) VALUES ( ? );";
	private static String strQueryInsertEmp			= "INSERT INTO employe (idPersonnel) VALUES ( ? );";
	
	private static String strQueryDeleteDir			= "DELETE FROM directeur WHERE idPersonnel = ? ;";
	private static String strQueryDeleteChef		= "DELETE FROM chef WHERE idPersonnel = ? ;";
	private static String strQueryDeleteEmp			= "DELETE FROM employe WHERE idPersonnel = ? ;";
	
	public ControleGestionPersonnel(CreationPersonnel cp, Personnel p) throws SQLException
	{
		String nomP = cp.getTextNom().getText();
		String prenomP = cp.getTextPrenom().getText();
		String identifiantP = cp.getId().getText();
		String passwordP = cp.getMdp().getText();
		String posteP = cp.getPoste().getText();
		int numTelP = Integer.parseInt(cp.getTel().getText());
		String serviceP = cp.getChoixService().getSelectedItem().toString();
		
		if(!(nomP.isBlank() || prenomP.isBlank() || identifiantP.isBlank() || passwordP.isBlank() || posteP.isBlank() || serviceP.isBlank()))
		{				
			if(p == null)
			{
				//creation Personnel
				
				int idService = Utils.returnIdinBdd("Service", "nomService", serviceP);

				App.getBDD().connect();
				
				App.getBDD().setPreparedStatement(strQueryInsertPersonnel);
				App.getBDD().getPreparedStatement().setString(1, nomP);
				App.getBDD().getPreparedStatement().setString(2, prenomP);
				App.getBDD().getPreparedStatement().setString(3, identifiantP);
				App.getBDD().getPreparedStatement().setString(4, passwordP);
				App.getBDD().getPreparedStatement().setString(5, posteP);
				App.getBDD().getPreparedStatement().setInt(6, numTelP);
				App.getBDD().getPreparedStatement().setInt(7, App.getEtablissement().getId());
				App.getBDD().getPreparedStatement().setInt(8, idService);

				App.getBDD().getPreparedStatement().executeUpdate();

				App.getBDD().disconnect();
				int id = Utils.returnIdinBdd("personnel", "identifiant", identifiantP);
				App.getBDD().connect();
				
				if(cp.getTypeAjout().getSelectedItem().toString().equals("Employe"))
				{
					App.getBDD().setPreparedStatement(strQueryInsertEmp);
					App.getBDD().getPreparedStatement().setInt(1, id);
					App.getBDD().getPreparedStatement().executeUpdate();

					App.getBDD().disconnect();
					int idD = Utils.returnIdinBdd("directeur", "idPersonnel", Integer.toString(id));
					App.getBDD().connect();
					App.getEtablissement().getListDirecteur().add(new Directeur(idD, id));
				}	
				
				else if(cp.getTypeAjout().getSelectedItem().toString().equals("Chef Service"))
				{
					App.getBDD().setPreparedStatement(strQueryInsertChef);
					App.getBDD().getPreparedStatement().setInt(1, id);
					App.getBDD().getPreparedStatement().executeUpdate();

					App.getBDD().disconnect();
					int idD = Utils.returnIdinBdd("directeur", "idPersonnel", Integer.toString(id));
					App.getBDD().connect();
					App.getEtablissement().getListDirecteur().add(new Directeur(idD, id));
				}	
				
				else if(cp.getTypeAjout().getSelectedItem().toString().equals("Directeur"))
				{
					App.getBDD().setPreparedStatement(strQueryInsertDir);
					App.getBDD().getPreparedStatement().setInt(1, id);
					App.getBDD().getPreparedStatement().executeUpdate();

					App.getBDD().disconnect();
					int idD = Utils.returnIdinBdd("directeur", "idPersonnel", Integer.toString(id));
					App.getBDD().connect();
					App.getEtablissement().getListDirecteur().add(new Directeur(idD, id));
				}	
				
					
				
			}else
			{
				//modification Personnel

				int id = p.getId();
				
				int idService = Utils.returnIdinBdd("Service", "nomService", serviceP);

				App.getBDD().connect();

				
				App.getBDD().setPreparedStatement(strQueryUpdatePersonnel);
				App.getBDD().getPreparedStatement().setString(1, nomP);
				App.getBDD().getPreparedStatement().setString(2, prenomP);
				App.getBDD().getPreparedStatement().setString(3, identifiantP);
				App.getBDD().getPreparedStatement().setString(4, passwordP);
				App.getBDD().getPreparedStatement().setString(5, posteP);
				App.getBDD().getPreparedStatement().setInt(6, numTelP);
				App.getBDD().getPreparedStatement().setInt(7, idService);
				App.getBDD().getPreparedStatement().setInt(8, id);
				
				App.getBDD().getPreparedStatement().executeUpdate();
				
				
				if(changeType(p, cp.getTypeAjout().getSelectedItem().toString()))
				{
					if(p.getClass() == Directeur.class)
					{
						App.getBDD().setPreparedStatement(strQueryDeleteDir);
						App.getBDD().getPreparedStatement().setInt(1, id);
						App.getBDD().getPreparedStatement().executeUpdate();
						
						App.getEtablissement().getListDirecteur().remove(p);
					}
					else if(p.getClass() == ChefService.class)
					{
						App.getBDD().setPreparedStatement(strQueryDeleteChef);
						App.getBDD().getPreparedStatement().setInt(1, id);
						App.getBDD().getPreparedStatement().executeUpdate();
						
						App.getEtablissement().getListChefServices().remove(p);
					}
					else if(p.getClass() == Employe.class)
					{
						App.getBDD().setPreparedStatement(strQueryDeleteEmp);
						App.getBDD().getPreparedStatement().setInt(1, id);
						App.getBDD().getPreparedStatement().executeUpdate();
						
						App.getEtablissement().getListEmployes().remove(p);
					}
					if(cp.getTypeAjout().getSelectedItem().toString().equals("Directeur"))
					{
						App.getBDD().setPreparedStatement(strQueryInsertDir);
						App.getBDD().getPreparedStatement().setInt(1, id);
						App.getBDD().getPreparedStatement().executeUpdate();

						App.getBDD().disconnect();
						int idD = Utils.returnIdinBdd("directeur", "idPersonnel", Integer.toString(id));
						App.getBDD().connect();
						
						App.getEtablissement().getListDirecteur().add(new Directeur(idD, id));
					}
					else if(cp.getTypeAjout().getSelectedItem().toString().equals("Chef Service"))
					{
						App.getBDD().setPreparedStatement(strQueryInsertChef);
						App.getBDD().getPreparedStatement().setInt(1, id);
						App.getBDD().getPreparedStatement().executeUpdate();

						App.getBDD().disconnect();
						int idC = Utils.returnIdinBdd("chef", "idPersonnel", Integer.toString(id));
						App.getBDD().connect();
						
						App.getEtablissement().getListChefServices().add(new ChefService(idC, id, App.getEtablissement().getListEmployes()));
					}
					else if(cp.getTypeAjout().getSelectedItem().toString().equals("Employe"))
					{
						App.getBDD().setPreparedStatement(strQueryInsertEmp);
						App.getBDD().getPreparedStatement().setInt(1, id);
						App.getBDD().getPreparedStatement().executeUpdate();

						App.getBDD().disconnect();
						int idE = Utils.returnIdinBdd("employe", "idPersonnel", Integer.toString(id));
						App.getBDD().connect();
						
						App.getEtablissement().getListEmployes().add(new Employe(idE, id));
					}
					
				}else
				{
					p.actualise();
				}
			}
			App.getBDD().disconnect();

			App.getContent().removeAll();
			App.getContent().add(new ViewPersonnel());
			App.getContent().repaint();
			App.getContent().revalidate();	
		}else
		{
			System.out.println("Un des champs est vide");
		}
	}
	
	private boolean changeType(Personnel p, String newType)
	{
		if(p.getClass() == Directeur.class && newType.equals("Directeur"))
			return false;

		if(p.getClass() == ChefService.class && newType.equals("Chef Service"))
			return false;

		if(p.getClass() == Employe.class && newType.equals("Employe"))
			return false;
		
		return true;
	}
}
