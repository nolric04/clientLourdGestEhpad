package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vue.App;

public class Reunion {
	private Date date;
	private String titre;

	private static String strQueryGetReunion = "SELECT * FROM Reunion WHERE idReunion = ?;";
	
	public Reunion(int id)
	{
		ResultSet rsUsers;
		
		try {
			App.getBDD().setPreparedStatement(strQueryGetReunion);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
			{
				this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rsUsers.getString("dateReunion"));
				this.titre = rsUsers.getString("titreReunion");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getDate() {
		return date;
	}

	public String getTitre() {
		return titre;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
}
