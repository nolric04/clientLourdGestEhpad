package controlleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import vue.App;
import vue.CreationAgenda;
import vue.ViewAgenda;

public class ControleFinAgenda implements MouseListener{
	

	public ControleFinAgenda(CreationAgenda ca) {

		App.getContent().remove(ca);
		Utils.reloadAll();
	
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
