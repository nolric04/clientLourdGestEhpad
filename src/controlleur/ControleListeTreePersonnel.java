package controlleur;


import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import modele.ChefService;
import modele.Employe;
import modele.Equipe;
import modele.Personnel;
import vue.App;
import vue.ViewPersonnel;

public class ControleListeTreePersonnel {
	/**
	 * détermine l'arbre hiérarchique du personnel
	 * @param filtre 	(admin, directeur, chefservice, employé)
	 * @param vp		viewPersonnel pour actualiser la liste du personnel
	 */
	public ControleListeTreePersonnel(String filtre, ViewPersonnel vp)
	{
		ArrayList<Personnel> ls = new ArrayList<>();
		
		if(filtre.equals("Administrateur :"))
		{
			ls.addAll(App.getEtablissement().getListAdmin());
			vp.setListePersonnel(ls);
			vp.repaint();
			vp.revalidate();
			return;
		}
		if(filtre.equals("Direction :"))
		{
			ls.addAll(App.getEtablissement().getListDirecteur());
			vp.setListePersonnel(ls);
			vp.repaint();
			vp.revalidate();
			return;
		}
		
		if(filtre.equals("Employés non Affecté"))
		{
			ArrayList<Employe> nonAffecte = new ArrayList<Employe>();
	        
	        nonAffecte.addAll(App.getEtablissement().getListEmployes());
	        
	        for(ChefService cs : App.getEtablissement().getListChefServices()) {
	        	for(Equipe eq : cs.getListeEquipe())
	        	{
	        		for(Employe emp : eq.getListeEmploye())
	        		{
	        			nonAffecte.remove(emp);
	        		}
	        	}
	        }
			ls.addAll(nonAffecte);
			vp.setListePersonnel(ls);
			vp.repaint();
			vp.revalidate();
			return;
		}
		
		for(ChefService cs : App.getEtablissement().getListChefServices()) {
        	if(filtre.equals(cs.getService()))
			{
        		ls.add(cs);
        		for(Equipe eq : cs.getListeEquipe())
        			ls.addAll(eq.getListeEmploye());
       
    			vp.setListePersonnel(ls);
    			vp.repaint();
    			vp.revalidate();
        		return ;
			}
        	
        	for(Equipe eq : cs.getListeEquipe())
        	{
        		if(filtre.equals("Equipe " + Integer.toString(eq.getNumEquipe())))
        		{
        			ls.addAll(eq.getListeEmploye());
        			vp.setListePersonnel(ls);
        			vp.repaint();
        			vp.revalidate();
        			return;
        		}
        	}
        }
	}

}
