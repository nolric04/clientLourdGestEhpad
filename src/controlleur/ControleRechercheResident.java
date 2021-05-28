package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modele.Resident;
import vue.App;
import vue.ViewResident;

public class ControleRechercheResident implements ActionListener{
	
	private ViewResident vr;
	
	public ControleRechercheResident(ViewResident vr) {
		this.vr = vr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Resident> res = new ArrayList<>();
		
		if(vr.getNom().getText().isBlank() && vr.getPrenom().getText().isBlank() && vr.getNumSecu().getText().isBlank() && vr.getChambre().getText().isBlank())
		{
			vr.setListe(App.getEtablissement().getListResident());
			vr.repaint();
			vr.revalidate();
			
			return;
		}
		
		for(Resident re : App.getEtablissement().getListResident())
		{
			if((re.getNom().contains(vr.getNom().getText()) && !vr.getNom().getText().isBlank())
				&& (re.getPrenom().contains(vr.getPrenom().getText()) && !vr.getPrenom().getText().isBlank())
				&& (re.getNumSecu().contains(vr.getNumSecu().getText()) && !vr.getNumSecu().getText().isBlank())
				&& (Integer.toString(re.getNumChambre()).contains(vr.getChambre().getText()) && !vr.getChambre().getText().isBlank()))
			{
				res.add(re);
			}
		}
		
		vr.setListe(res);
		vr.repaint();
		vr.revalidate();
	}

}
