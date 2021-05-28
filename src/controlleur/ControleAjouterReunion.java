package controlleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vue.App;
import vue.CreationAgenda;

public class ControleAjouterReunion implements MouseListener {

	public ControleAjouterReunion(int id) throws SQLException, ParseException {
		App.getContent().removeAll();
		//SimpleDateFormat formatter= new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(formatter.format(date));
		if(id!=0)
			date = Utils.getDateAgenda(id, true);
		
		String titre = Utils.getTitreReunion(id);
		
		App.getContent().add(new CreationAgenda(App.getConnected(), true, id, date, titre));
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
