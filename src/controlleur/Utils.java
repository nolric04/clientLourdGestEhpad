package controlleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import modele.Administrateur;
import modele.Etablissement;
import vue.App;
import vue.Header;
import vue.NavBar;
import vue.ViewMultiRegistre;

public class Utils {

	
	public static ArrayList<String> getList(String str){
		String strQueryGetChefEquipe = "select * from "+str;
		ResultSet rsUsers;
		ArrayList<String> ls = new ArrayList<String>();
		
		try {
			App.getBDD().connect();
			
			App.getBDD().setPreparedStatement(strQueryGetChefEquipe);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			
			while(rsUsers.next())
				ls.add(rsUsers.getString("nom"+str));

			App.getBDD().disconnect();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;	
	}
	
	

	public static String[] getArray(String str){
		String strQueryGetVal = "select * from "+str;
		String strQueryGetnb = "select Count(*) nb from "+str;
		ResultSet rsUsers;
		String[] ls = null;
		
		try {
			App.getBDD().connect();
			
			App.getBDD().setPreparedStatement(strQueryGetnb);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			
			rsUsers.next();
			int nb = rsUsers.getInt("nb");
			
			ls = new String[nb];
			

			App.getBDD().setPreparedStatement(strQueryGetVal);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			
			int i = 0;
			while(rsUsers.next())
				ls[i++] = rsUsers.getString("nom"+str);

			App.getBDD().disconnect();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;	
	}
	
	
	public static void redrawC() {
		App.getContent().repaint();
		App.getContent().revalidate();
		
	}
	
	
	public static int returnIdinBdd(String tableName, String columnName, String what) throws SQLException {
		
		int id = 0;
		ResultSet rs = null;
		String primaryColumn = null;
		
		
		App.getBDD().connect();
		String sql = "SHOW KEYS FROM "+tableName+" WHERE Key_name = 'PRIMARY'";
		App.getBDD().setPreparedStatement(sql);
		rs = App.getBDD().getPreparedStatement().executeQuery();
		while(rs.next()) {
			primaryColumn=rs.getString("Column_name");
			break;
		}
		
		sql = "select "+primaryColumn+" from "+ tableName +" where "+ columnName +" = '"+ what +"';" ;
		
		App.getBDD().setPreparedStatement(sql);
		rs = App.getBDD().getPreparedStatement().executeQuery();
		while(rs.next()) {
			id=rs.getInt(primaryColumn);
		}
		
		App.getBDD().disconnect();
		return id;
	}
	
	public static String getTitreReunion(int id) throws SQLException {
		String str = null;
		ResultSet rs;
		App.getBDD().connect();
		
		String sql = "select titreReunion from reunion where `idReunion` = "+id+";";
		App.getBDD().setPreparedStatement(sql);
		rs = App.getBDD().getPreparedStatement().executeQuery();
		while(rs.next()) {
			str = rs.getString("titreReunion");
			System.out.println(rs.getString("titreReunion"));
		}
		App.getBDD().disconnect();
		return str;
	}
	public static Date getDateAgenda(int id, boolean isReunion) throws SQLException, ParseException {
		
		
		String table = "visite";
		String column ="dateVisite";
		String primaryColumn = "idVisite";
		Date date = null;
		
		if(isReunion) {
			table = "reunion";
			column ="dateReunion";
			primaryColumn = "idReunion";
		}
		
		ResultSet rs;
		App.getBDD().connect();
		
		String sql = "select "+ column +" from "+ table +" where "+ primaryColumn +" = "+id+";";
		App.getBDD().setPreparedStatement(sql);
		rs = App.getBDD().getPreparedStatement().executeQuery();
		
		while(rs.next()) {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(column));
		}
		
		App.getBDD().disconnect();
		
		return date;
	}
	
	public static void reloadAll() {		
	
		App.getBDD().connect();
		App.setEtablissement(null);
		System.gc();
		Etablissement e = new Etablissement(1);
		new ControleConnexion(App.getConnected().getIdentifiant(), App.getConnected().getMdp());
		App.setEtablissement(e);
				
		App.getApp().getContentPane().removeAll();
		
		App.getApp().getContentPane().add(new Header());
		App.getApp().getContentPane().add(new NavBar());
		App.resetContent();
		App.getApp().getContentPane().add(App.getContent());
		App.getContent().add(new ViewMultiRegistre((App.getConnected().getClass() == Administrateur.class)));

		App.getBDD().disconnect();
		
	}
	
}
