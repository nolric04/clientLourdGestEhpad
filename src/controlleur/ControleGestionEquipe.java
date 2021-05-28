package controlleur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JTextField;

import modele.Employe;
import vue.App;

public class ControleGestionEquipe {

	/**
	 * permet de 	créer une équipe
	 * 				modifier une équipe
	 * 				ajouter un personnel dans une équipe
	 * 
	 * @param idEquipe			
	 * @param al				objet classe implicite mere de tous les objets de java
	 * @param csIdentifiant		identifiant chef de Service
	 * @param jtf				récupère les éléments présents dans le JTextField
	 * @throws SQLException		
	 */
	public ControleGestionEquipe(int idEquipe, ArrayList<Object> al, String csIdentifiant, JTextField jtf) throws SQLException {
		
		ArrayList<Integer> alIntPer = new ArrayList<Integer>();
		ArrayList<Integer> alIntEmp = new ArrayList<Integer>();
		String horaire = jtf.getText();
		int idChef = 0;
		int idChefPer = 0;
		String sql = "";
		int currentEquipe = idEquipe;
		
		ResultSet rs;
		
		
		System.out.println(al);
		
		
		for(Object o : al ) {
			alIntPer.add( ((Employe)(o)).getId());
			alIntEmp.add( Utils.returnIdinBdd( "employe", "idEmploye", String.valueOf(((Employe)(o)).getId()) ) );
		}
		System.out.println(alIntEmp);
		
		idChefPer = Utils.returnIdinBdd("Personnel", "identifiant", csIdentifiant);
		idChef = Utils.returnIdinBdd("chef", "idPersonnel" , String.valueOf(idChefPer));
		
		App.getBDD().connect();
		
		
		if ( currentEquipe == 0 ) {
			sql = "INSERT INTO equipe (`idEquipe`, `horaireEquipe`, `idChef`, `idPersonnel`)\r\n"
					+ "VALUES\r\n"
					+ "(null, '"+ horaire +"', "+idChef+", "+idChefPer+");";
			System.out.println(sql);
			App.getBDD().setPreparedStatement(sql);
			App.getBDD().getPreparedStatement().executeUpdate();
			
			sql = "select max(idEquipe) as max from equipe";
			App.getBDD().setPreparedStatement(sql);
			rs = App.getBDD().getPreparedStatement().executeQuery();
			while(rs.next()) {
				currentEquipe = rs.getInt("max");
			}
		}else {
			sql = "UPDATE equipe SET\r\n"
					+ "`horaireEquipe` = '"+ horaire +"',\r\n"
					+ "`idChef` = "+ idChef +",\r\n"
					+ "`idPersonnel` = "+ idChefPer +" \r\n"
					+ "WHERE `idEquipe` = "+currentEquipe+";";
			System.out.println(sql);
			App.getBDD().setPreparedStatement(sql);
			App.getBDD().getPreparedStatement().executeUpdate();
			
			
			
		}
		
		
		for ( int idPer : alIntPer ) {
			sql = "UPDATE employe SET `idEquipe` = "+ currentEquipe +" WHERE `idPersonnel` = "+ idPer +";";
			System.out.println(sql);
			App.getBDD().setPreparedStatement(sql);
			App.getBDD().getPreparedStatement().executeUpdate();
		}
		
		
		boolean first = true;
		sql = "UPDATE employe SET `idEquipe` = NULL WHERE `idEquipe` = "+idEquipe+" AND NOT (";
	
		
		
		//TODO changer en idemploye
		for (int idPer : alIntPer) {
			if(!first) {
				sql += " OR";
			}
			sql += " `idPersonnel` = "+idPer;
			first = false;
			
		}
		sql += ");";
		if (sql.contains("`idPersonnel`")) {
			App.getBDD().setPreparedStatement(sql);
			App.getBDD().getPreparedStatement().executeUpdate();
		}
		
		System.out.println(sql);
		App.getBDD().disconnect();
		
		Utils.reloadAll();
		
	}
	
	
}
