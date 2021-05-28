package controlleur;

import modele.Administrateur;
import modele.ChefService;
import modele.Directeur;
import modele.Employe;
import modele.Personnel;

import vue.App;
import vue.CreationPersonnel;

public class ControlePersonnel {
	
	public ControlePersonnel(Personnel connect,Personnel emp)
	{
		try
		{
		
			int degreHierarchique=3;
			for(int i =0;i<App.getEtablissement().getListEmployes().size();i++)
			{
				if(connect.getClass()==Employe.class) 
				{
					degreHierarchique=3;
				}
				if(connect.getClass()==ChefService.class) 
				{
					degreHierarchique=2;
				}
				if(connect.getClass()==Directeur.class) 
				{
					degreHierarchique=1;
				}
				if(connect.getClass()==Administrateur.class) 
				{
					degreHierarchique=0;
				}
			}
			App.getContent().removeAll();
			App.getContent().add(new CreationPersonnel(degreHierarchique,emp));

			App.getContent().repaint();
			App.getContent().revalidate();
		}
		catch (Exception e)
		{

		}

	}

}
