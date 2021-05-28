package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import vue.App;

public class Etablissement {
	private String 						nomEtablissement;
	private int							id;
	
	private ArrayList<Employe> 			listEmployes = new ArrayList<>();
	private ArrayList<ChefService> 		listChefServices = new ArrayList<>();
	private ArrayList<Directeur> 		listDirecteur = new ArrayList<>();
	private ArrayList<Administrateur> 	listAdmin = new ArrayList<>();
	
	private ArrayList<Resident>			listResident = new ArrayList<>();
	
	private ArrayList<Registre>			listRegistre = new ArrayList<>();

	private static String strQueryGetName			=	"SELECT nomEtablissement FROM Etablissement WHERE idEtablissement = ? ;";
	
	private static String strQueryGetAdmin			=	"SELECT * FROM Admin JOIN Personnel USING (idPersonnel) WHERE idEtablissement = ?;";
	private static String strQueryGetDirecteur		=	"SELECT * FROM Directeur JOIN Personnel USING (idPersonnel) WHERE idEtablissement = ?;";
	private static String strQueryGetEmploye		=	"SELECT * FROM Employe JOIN Personnel USING (idPersonnel) WHERE idEtablissement = ?;";
	private static String strQueryGetChefService	=	"SELECT * FROM Chef JOIN Personnel USING (idPersonnel) WHERE idEtablissement = ?;";
	
	private static String strQueryGetResident		=	"SELECT idResident FROM Resident WHERE idEtablissement = ?;";
	
	private static String strQueryGetRegistre		=	"SELECT * FROM Registre r join Service using (idService) Where r.idEtablissement = ?;";

	public Etablissement(int id)
	{
		this.id = id;
		try {
			ResultSet rsUsers;
			
			App.getBDD().setPreparedStatement(strQueryGetName);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				this.nomEtablissement = rsUsers.getString(1);
			
			App.getBDD().setPreparedStatement(strQueryGetAdmin);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listAdmin.add(new Administrateur(rsUsers.getInt("idPersonnel")));	
			
			App.getBDD().setPreparedStatement(strQueryGetDirecteur);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listDirecteur.add(new Directeur(rsUsers.getInt("idDirecteur"),rsUsers.getInt("idPersonnel")));
			
			App.getBDD().setPreparedStatement(strQueryGetEmploye);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listEmployes.add(new Employe(rsUsers.getInt("idEmploye"),rsUsers.getInt("idPersonnel")));	
			
			App.getBDD().setPreparedStatement(strQueryGetResident);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listResident.add(new Resident(rsUsers.getInt(1)));
			
			App.getBDD().setPreparedStatement(strQueryGetChefService);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listChefServices.add(new ChefService(rsUsers.getInt("idChef"),rsUsers.getInt("idPersonnel"), listEmployes));
			
			ArrayList<Personnel> tmpPersonnel = new ArrayList<>();
			for(Administrateur a : listAdmin)
				tmpPersonnel.add(a);
			for(Directeur d : listDirecteur)
				tmpPersonnel.add(d);
			for(ChefService c : listChefServices)
				tmpPersonnel.add(c);
			for(Employe e : listEmployes)
				tmpPersonnel.add(e);
			
			App.getBDD().setPreparedStatement(strQueryGetRegistre);
			App.getBDD().getPreparedStatement().setInt(1, id);
			rsUsers = App.getBDD().getPreparedStatement().executeQuery();
			while(rsUsers.next())
				listRegistre.add(new Registre(rsUsers.getInt("idRegistre"), rsUsers.getString("nomService"), tmpPersonnel, listResident));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getNomEtablissement() {
		return nomEtablissement;
	}

	public ArrayList<Employe> getListEmployes() {
		return listEmployes;
	}

	public ArrayList<ChefService> getListChefServices() {
		return listChefServices;
	}

	public ArrayList<Directeur> getListDirecteur() {
		return listDirecteur;
	}
	
	public ArrayList<Administrateur> getListAdmin() {
		return listAdmin;
	}
	
	public ArrayList<Personnel> getListPersonnel()
	{
		ArrayList<Personnel> ret = new ArrayList<Personnel>();

		ret.addAll(listAdmin);
		ret.addAll(listDirecteur);
		ret.addAll(listChefServices);
		ret.addAll(listEmployes);
		
		return ret;
	}

	public ArrayList<Resident> getListResident() {
		return listResident;
	}

	public ArrayList<Registre> getListRegistre() {
		return listRegistre;
	}

	public void setNomEtablissement(String nomEtablissement) {
		this.nomEtablissement = nomEtablissement;
	}

	public int getId() {
		return id;
	}
}
