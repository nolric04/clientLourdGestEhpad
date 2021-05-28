package controlleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.App;
import vue.FicheResident;

public class ControleFinResident implements ActionListener {
	
	FicheResident f;
	
/**
 * 
 * @param f sauvegarde la vue précédente
 */
	public ControleFinResident(FicheResident f) {
		this.f=f;
		
	}

	@Override
	//appelé quand clic button
	public void actionPerformed(ActionEvent e) {
		
		try {
			App.getContent().remove(f);
			App.getContent().getComponent(0).setVisible(true);
			App.getContent().repaint();
			App.getContent().revalidate();
		}
		catch (Exception ee) {
			
		}
	}
}
