package controlleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import modele.Directeur;
import vue.App;
import vue.CreationAgenda;

public class ControleGestionAgenda implements MouseListener{
	
	private static String sql;
	
	public ControleGestionAgenda(CreationAgenda ca, boolean isReunion, int id) throws SQLException {
		
		String str = null;
		String date = ca.getDateDebut().getJFormattedTextField().getText();
		String heure = ca.getTimeSpinner().getValue().toString().substring(11, 19);
		
		String datetime = date + " " + heure;
		
		App.getBDD().connect();
		
		if(isReunion) {
			
			str = ca.getJtf().getText();
			if(id==0) {
				sql = "INSERT INTO reunion VALUES (null,'"+datetime+"','"+str+"');";
				
			}else {
				sql = "UPDATE reunion SET `dateReunion` = '"+ datetime +"', `titreReunion` = '"+ str +"' WHERE `idReunion` = "+ id +";";
			
			}
			
		}else {
			
			if(id==0) {
				sql = "INSERT INTO visite  VALUES (null,'"+datetime+"',"+((Directeur) (App.getConnected())).getIdD()  +", "+App.getConnected().getId()+")";
				
			}else {
				sql = "UPDATE visite SET `dateVisite` = '"+ datetime +"' WHERE `idVisite` = "+ id +";";
				
			}
			
		}
		

		
		try {
			App.getBDD().setPreparedStatement(sql);
			App.getBDD().getPreparedStatement().executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		if(isReunion && id == 0) {
			sql = "INSERT INTO "
					+ ( App.getConnected().getClass()==Directeur.class ? "listereuniondirecteur" : "listereunionchef" ) 
					+ "  VALUES ("+ Utils.returnIdinBdd("reunion", "dateReunion" , datetime) +", "+ Utils.returnIdinBdd(
										App.getConnected().getClass()==Directeur.class ? "directeur" : "chef", "idPersonnel", 
										String.valueOf(App.getConnected().getId())) +", "+ App.getConnected().getId() +")";
			
			App.getBDD().connect();
			App.getBDD().setPreparedStatement(sql);
			App.getBDD().getPreparedStatement().executeUpdate();
			
		}
		
		
		
		App.getBDD().disconnect();
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
