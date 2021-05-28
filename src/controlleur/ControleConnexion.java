package controlleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Administrateur;
import modele.ChefService;
import modele.Directeur;
import modele.Employe;
import modele.Etablissement;
import modele.Personnel;
import vue.App;
import vue.Connection;
import vue.Header;
import vue.NavBar;
import vue.ViewMultiRegistre;

public class ControleConnexion {

	private static String strQueryCheckConnect			=	"SELECT * FROM Personnel WHERE identifiant = ?;";
	/**
	 * 
	 * @param id  String (nom utilisateur)
	 * @param mdp (mot de passe)
	 */
	public ControleConnexion(String id, String mdp)
	{
		//controle de saisie pour empêcher les injections SQL
		if	(!(id.isEmpty() || mdp.isEmpty() || id.isBlank() || mdp.isBlank() )&& 
			(id.matches("^[a-zA-Z]*$") && mdp.matches("^[a-zA-Z0-9]*$")))
		{
			App.getBDD().connect(); 
			ResultSet rsUsers;
			// verificationBdd
			
			try {
				App.getBDD().setPreparedStatement(strQueryCheckConnect);
				App.getBDD().getPreparedStatement().setString(1, id);
				rsUsers = App.getBDD().getPreparedStatement().executeQuery();
				if(rsUsers.next())
				{
					if(rsUsers.getString("password").equals(mdp))
					{
						// connexion ok
						Etablissement e = new Etablissement(1);
						
						App.setEtablissement(e);
						
						Personnel find;

						find = findInListA(e.getListAdmin(), id);
						if(find == null)
						{
							find = findInListD(e.getListDirecteur(), id);
						}
						if(find == null)
						{
							find = findInListC(e.getListChefServices(), id);
						}
						if(find == null)
						{
							find = findInListE(e.getListEmployes(), id);
						}
						App.setConnected(find);
						
						App.getApp().getContentPane().removeAll();
						App.getApp().getContentPane().add(new Header());
						App.getApp().getContentPane().add(new NavBar());
						App.getApp().getContentPane().add(App.getContent());
						
						App.getContent().add(new ViewMultiRegistre((App.getConnected().getClass() == Administrateur.class)));
						
						App.getApp().getContentPane().repaint();
						App.getApp().getContentPane().revalidate();
					}
					else
					{
						// connexion KO MDP
						Connection c = (Connection) App.getApp().getContentPane().getComponent(0);
						c.getError().setText("Erreur identifiant/mot de passe");
					}
				}else
				{
					// connexion KO ID
					Connection c = (Connection) App.getApp().getContentPane().getComponent(0);
					c.getError().setText("Erreur identifiant/mot de passe");
				}
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			App.getBDD().disconnect();
		}
		else
		{
			Connection c = (Connection) App.getApp().getContentPane().getComponent(0);
			c.getError().setText("Erreur identifiant/mot de passe");
		}
		
	}
	/**
	 * Method privee pour la recherche du personnel connecte
	 * @param arrayList
	 * @param id
	 * @return
	 */
	private Personnel findInListA(ArrayList<Administrateur> arrayList, String id)
	{
		Personnel tmp = null;
		for(Personnel p : arrayList)
		{
			if(p.getIdentifiant().equals(id))
				tmp = p;				
		}
		
		return tmp;
	}

	/**
	 * Method privee pour la recherche du personnel connecte
	 * @param arrayList
	 * @param id
	 * @return
	 */
	private Personnel findInListD(ArrayList<Directeur> arrayList, String id)
	{
		Personnel tmp = null;
		for(Personnel p : arrayList)
		{
			if(p.getIdentifiant().equals(id))
				tmp = p;				
		}
		
		return tmp;
	}

	/**
	 * Method privee pour la recherche du personnel connecte
	 * @param arrayList
	 * @param id
	 * @return
	 */
	private Personnel findInListC(ArrayList<ChefService> arrayList, String id)
	{
		Personnel tmp = null;
		for(Personnel p : arrayList)
		{
			if(p.getIdentifiant().equals(id))
				tmp = p;				
		}
		
		return tmp;
	}

	/**
	 * Method privee pour la recherche du personnel connecte
	 * @param arrayList
	 * @param id
	 * @return
	 */
	private Personnel findInListE(ArrayList<Employe> arrayList, String id)
	{
		Personnel tmp = null;
		for(Personnel p : arrayList)
		{
			if(p.getIdentifiant().equals(id))
				tmp = p;				
		}
		
		return tmp;
	}
}
