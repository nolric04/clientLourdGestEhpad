package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modele.Evenement;
import vue.ViewRegistre;

public class ControleRechercheRegistre implements ActionListener{
	
	private ViewRegistre vr;
	
	public ControleRechercheRegistre(ViewRegistre vr)
	{
		this.vr = vr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ArrayList<Evenement> events = new ArrayList<>();
		
		if(vr.getSelectDeclarant().getSelectedItem().equals("Déclarant") && vr.getNvImportance().getSelectedItem().equals("Niveau Importance") && vr.getMotClef().getText().isBlank())
		{
			vr.setListe(vr.getRegistre().getListEvenements());
			vr.repaint();
			vr.revalidate();
			
			return;
		}
		
		for(Evenement ev : vr.getRegistre().getListEvenements())
		{
			if(vr.getSelectDeclarant().getSelectedItem().equals(ev.getDeclarant().getIdentifiant())
					|| vr.getNvImportance().getSelectedItem().equals(Integer.toString(ev.getGravite())))
			{
				String segments[] = vr.getMotClef().getText().split(" ");
				if(segments.length > 0)
				{
					for(String mot : segments)
					{
						if(ev.getTitre().contains(mot) || ev.getDescription().contains(mot))
							events.add(ev);
					}
				}
				else
				{
					events.add(ev);
				}
			}
		}

		vr.setListe(events);
		vr.repaint();
		vr.revalidate();
	}

}
