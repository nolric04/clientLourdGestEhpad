package vue;

import javax.swing.JPanel;

import controlleur.Utils;
import modele.Directeur;
import modele.Personnel;
import utils.ListAgenda;

public class ViewAgenda extends JPanel {

	public ViewAgenda(Personnel p) {
		
		setLayout(null);
		setBounds(0, 0, 1000, 750);
		
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		left.setLayout(null);
		right.setLayout(null);
		
	 
		left.setBounds(0, 0, p.getClass() == Directeur.class ? 500 : 1000, 750);
		right.setBounds(500, 0, p.getClass() == Directeur.class ? 500 : 0, 750);
		
		if ( p.getClass() == Directeur.class) {
			right.add(new ListAgenda(p, "visite", 500));
			add(right);
		}
		
		left.add(new ListAgenda(p, "reunion", p.getClass() == Directeur.class ? 500 : 1000));
		add(left);
		
		Utils.redrawC();
	}


	
	
}
