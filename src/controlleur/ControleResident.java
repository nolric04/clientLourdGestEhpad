package controlleur;


import modele.Resident;
import vue.App;
import vue.CreationResident;
import vue.FicheResident;


public class ControleResident {

	
	public ControleResident(Resident resid,boolean lectureResident)
	{
		try
		{

			if ((lectureResident==true)&&(resid!=null))
			{
				App.getContent().removeAll();
				App.getContent().add(new CreationResident(	Utils.getList("Pathologie"), 
															Utils.getList("Allergie"), 
															Utils.getList("Regime"),
															resid,true));
				
			}
			else if(!lectureResident && resid != null)
			{
				App.getContent().removeAll();
				App.getContent().add(new CreationResident(	Utils.getList("Pathologie"), 
															Utils.getList("Allergie"), 
															Utils.getList("Regime"),
															resid,false));
			}
			else
			{
				App.getContent().removeAll();
				App.getContent().add(new CreationResident(	Utils.getList("Pathologie"), 
															Utils.getList("Allergie"), 
															Utils.getList("Regime"),
															null,false));
			}
			App.getContent().repaint();
			App.getContent().revalidate();
		}catch (Exception e)
		{
			//todo popup
		}

	}

}
