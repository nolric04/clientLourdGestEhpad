package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modele.Personnel;
import vue.App;
import vue.ViewPersonnel;

public class ControleRecherchePersonnel implements ActionListener{
	
	private ViewPersonnel vp;

	public ControleRecherchePersonnel(ViewPersonnel source) {
		vp = source;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ArrayList<Personnel> ls = new ArrayList<>();
		
		if(vp.getTextNom().isBlank() && vp.getTextPrenom().isBlank())
		{
			vp.setListePersonnel(App.getEtablissement().getListPersonnel());
			vp.repaint();
			vp.revalidate();
			return;
		}
		
		for(Personnel p : App.getEtablissement().getListPersonnel())
		{
			if((!vp.getTextNom().isBlank() && p.getNom().toLowerCase().contains(vp.getTextNom().toLowerCase())) || (!vp.getTextPrenom().isBlank() && p.getPrenom().toLowerCase().contains(vp.getTextPrenom().toLowerCase())))
				ls.add(p);
		}		
		
		vp.setListePersonnel(ls);
		vp.repaint();
		vp.revalidate();
	}

}
