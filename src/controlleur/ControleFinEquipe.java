package controlleur;

import vue.App;
import vue.ViewAgenda;
import vue.ViewPersonnel;

public class ControleFinEquipe {

	public ControleFinEquipe() {
		App.getContent().removeAll();
		App.getContent().add(new ViewPersonnel());
		Utils.redrawC();
	}
}
