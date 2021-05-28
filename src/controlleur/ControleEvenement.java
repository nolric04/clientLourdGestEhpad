package controlleur;

import modele.Evenement;
import vue.App;
import vue.CreationEvenement;
import vue.ViewEvenement;

public class ControleEvenement 
{
	/**
	 *  controleur passant de ViewRegistre à ViewEvenement/CreationEvenement
	 * @param event			un Evenement
	 * @param lectureEvent	lecture ou écriture
	 * @param service		nom du service
	 */
	public ControleEvenement(Evenement event,boolean lectureEvent, String service)
	{
		try
		{
			if ((lectureEvent==true)&&(event!=null))
			{
				App.getContent().getComponent(0).setVisible(false);
				App.getContent().add(new ViewEvenement(event));
			}
			else
			{
				App.getContent().removeAll();
				App.getContent().add(new CreationEvenement(event,service));		
			}
			App.getContent().repaint();
			App.getContent().revalidate();
			
		}catch (Exception e)
		{

		}

	}

}
