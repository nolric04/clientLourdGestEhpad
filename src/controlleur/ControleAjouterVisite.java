package controlleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import vue.App;
import vue.CreationAgenda;

public class ControleAjouterVisite implements MouseListener {
	
	public ControleAjouterVisite(int id) throws SQLException, ParseException{
		App.getContent().removeAll();
		Date date= Utils.getDateAgenda(id, false);
	
		App.getContent().add(new CreationAgenda(App.getConnected(), false, id, date));
		Utils.redrawC();
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
