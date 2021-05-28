package controlleur;

import vue.App;
import vue.CreationEquipe;

public class ControleEquipe {

	public ControleEquipe(String source)
	{
		try
		{
			int nbE = 0;

			if(!source.isEmpty())
			{
				String a = source.replaceAll("[A-Za-z :]", "");

				if(a.isEmpty())
					return;
			
				nbE = Integer.parseInt(a);
			}
				
			
			App.getContent().removeAll();
			App.getContent().add(new CreationEquipe(nbE));
			
			App.getContent().repaint();
			App.getContent().revalidate();		
			
		}catch (Exception e)
		{
			
		}

	}
	
}
