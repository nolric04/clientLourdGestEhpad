package vue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import utils.BoxTable;

public class ViewMultiRegistre extends JPanel 
{
	private JPanel registre1, registre2, registre3;
	JComboBox<String> selectRegistre = null;
	
	/**
	 * 
	 * @param isAdmin lors de la connexion, un booléen défini si l'utilisateur est un admin ou non
	 */
	public ViewMultiRegistre(Boolean isAdmin)
	{

		//***********JPanel Principale***************//
		setBounds(0,0,1000,750);
		setBackground(Color.gray);
		this.setLayout(null);// Layout obligatoire pour chaque Panel
		
		//***********JPanel niv2*****************//
		
		/*
		 * si l'utilisateur est admin alors création de la vue du registre admin 
		 */
		if(isAdmin) 
		{
			registre1 = new JPanel();
			JScrollPane registre1pan = new JScrollPane(registre1);
			JLabel titreRegistre1 = new JLabel("Administrateur");
			
			registre1.setLayout(null);
			
			registre1pan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			registre1pan.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			registre1pan.setBounds(0,50,325,660);
			titreRegistre1.setBounds(125,10,150,25);
			
			titreRegistre1.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,18));
			titreRegistre1.setForeground(Color.white);
			
			registre1.setBackground(Color.darkGray);
			
			add(registre1pan);
			this.add(titreRegistre1);
		}

		//Création de l'en-tête en fonction du connecté
		JLabel registreService = null;
		/*
		 * controle sur la session utilisateur (admin ou non)
		 * si admin, alors on charge dans un vecteur l'ensemble des services sur la BDD
		 * hormis Admin et general
		 * 
		 * sinon affichage du service de l'employe connecté seulement
		 */
		if(isAdmin) 
		{
			Vector<String> v = new Vector<String>();
			for(int i=0 ; i < App.getEtablissement().getListRegistre().size();i++) 
			{
				if( !(App.getEtablissement().getListRegistre().get(i).getService().equalsIgnoreCase("Admin")) && !(App.getEtablissement().getListRegistre().get(i).getService().equalsIgnoreCase("Global")))
				{
					v.add(App.getEtablissement().getListRegistre().get(i).getService());
				}						
			}
			selectRegistre= new JComboBox<String>(v);							
			selectRegistre.setBounds(400,10,170,25);
			
			selectRegistre.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					changeRegistre(selectRegistre.getSelectedItem().toString());
				}
			});
			
			
		}else 
		{					
			registreService = new JLabel("Service : "+App.getConnected().getService());
			registreService.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,18));
			registreService.setForeground(Color.darkGray);
			registreService.setBounds(200,10,170,25);
		}
		
		registre2 = new JPanel();
		registre3 = new JPanel();
		
		registre2.setBackground(Color.darkGray);
		registre3.setBackground(Color.darkGray);
		
		registre2.setLayout(null);
		registre3.setLayout(null);
		
		JScrollPane registre2pan = new JScrollPane(registre2);
		JScrollPane registre3pan = new JScrollPane(registre3);
		
		registre2pan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		registre2pan.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		registre3pan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		registre3pan.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
		JLabel titreRegistre3 = new JLabel("Global");
		titreRegistre3.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,18));
		titreRegistre3.setForeground(Color.darkGray);
		/*
		 * affichage de 2 ou 3 registres en fonction de si l'utilisateur est admin ou non$
		 * 
		 */
		if(isAdmin) 
		{
			registre2pan.setBounds(327,50,325,660);		        
			registre3pan.setBounds(654,50,325,660);	
			titreRegistre3.setBounds(800,10,100,25);
		}else 
		{
			registre2pan.setBounds(0,50,500,660);		        
			registre3pan.setBounds(500,50,500,660);
			titreRegistre3.setBounds(700,10,100,25);						
		}
	
		add(registre2pan);
		add(registre3pan);
		
		if(isAdmin)
			this.add(selectRegistre);
		else
			this.add(registreService);
		this.add(titreRegistre3);
		
		
		
		
//			//****************Jpanel niv3*****************//
		
		int iR1=0,iR2=0,iR3=0;
		
//						//***********************Registre1************************//
		
		/*
		 * si l'utilisateur est un admin , 
		 * parcours la liste des registres
		 * si registre == registre admin
		 * alors afficher tous les evenements de ce registre
		 */
		if(isAdmin) 
		{
			for(int i=0 ; i < App.getEtablissement().getListRegistre().size() ; i++) 
			{
				if(App.getEtablissement().getListRegistre().get(i).getService().equalsIgnoreCase("Admin")) 
				{
					for(iR1=0 ; iR1 < App.getEtablissement().getListRegistre().get(i).getListEvenements().size() ; iR1++) 
					{
						registre1.add(new BoxTable(App.getEtablissement().getListRegistre().get(i).getListEvenements().get(iR1) ,iR1, isAdmin));
					}
				}
			}
			
			Dimension dR1 = new Dimension(300,iR1*50+5+5*iR1);	
			registre1.setPreferredSize(dR1);
		}
		
				//***********************Registre2************************//
		/* 
		 * parcours la liste des registres
		 * si registre == registre utilisateur
		 * alors afficher tous les evenements de ce registre
		 */
		if(isAdmin)
		{
			changeRegistre(selectRegistre.getSelectedItem().toString());
		}
		else
		{
			for(int i=0 ; i < App.getEtablissement().getListRegistre().size() ; i++) 
			{
				if(App.getEtablissement().getListRegistre().get(i).getService().equalsIgnoreCase(App.getConnected().getService())) 
				{
					for(iR2=0 ; iR2 < App.getEtablissement().getListRegistre().get(i).getListEvenements().size() ; iR2++) 
					{
						registre2.add(new BoxTable(App.getEtablissement().getListRegistre().get(i).getListEvenements().get(iR2) ,iR2, isAdmin));
					}
				}
			}
			Dimension dR2 = new Dimension(isAdmin ? 300 : 500,iR2*50+5+5*iR2); 	// <si isAdmin==true => dimension=300,iR2*50+5+5*iR2 / si false dimension =500,iR2*50+5+5*iR2>
			registre2.setPreferredSize(dR2);
		}
		
//						//***********************Registre3************************//

		/*
		 * parcours la liste des registres
		 * si registre == registre Global
		 * alors afficher tous les evenements de ce registre
		 */
		for(int i=0 ; i < App.getEtablissement().getListRegistre().size() ; i++) 
		{
			if(App.getEtablissement().getListRegistre().get(i).getService().equalsIgnoreCase("Global")) 
			{
				for(iR3=0 ; iR3 < App.getEtablissement().getListRegistre().get(i).getListEvenements().size() ; iR3++) 
				{
					registre3.add(new BoxTable(App.getEtablissement().getListRegistre().get(i).getListEvenements().get(iR3) ,iR3, isAdmin));
				}
			}
		}
		Dimension dR3 = new Dimension(isAdmin ? 300 : 500,iR3*50+5+5*iR3);// <si isAdmin==true => dimension=300,iR2*50+5+5*iR2 / si false dimension =500,iR2*50+5+5*iR2>
		registre3.setPreferredSize(dR3);
		
	}
	
	private void changeRegistre(String nomRegistre)
	{
		registre2.removeAll();
		int iR2 = 0;
		for(int i=0 ; i < App.getEtablissement().getListRegistre().size() ; i++) 
		{
			if(App.getEtablissement().getListRegistre().get(i).getService().equalsIgnoreCase(nomRegistre)) 
			{
				for(iR2=0 ; iR2 < App.getEtablissement().getListRegistre().get(i).getListEvenements().size() ; iR2++) 
				{
					registre2.add(new BoxTable(App.getEtablissement().getListRegistre().get(i).getListEvenements().get(iR2) ,iR2, true));
				}
			}
		}
		Dimension dR2 = new Dimension(300,iR2*50+5+5*iR2); 	// <si isAdmin==true => dimension=300,iR2*50+5+5*iR2 / si false dimension =500,iR2*50+5+5*iR2>
		registre2.setPreferredSize(dR2);
		
		App.getApp().getContentPane().repaint();
		App.getApp().getContentPane().revalidate();
	}

	/**
	 * 
	 * @return registre admin
	 */
	public JPanel getRegistre1() {
		return registre1;
	}

	/**
	 * 
	 * @return registre service
	 */
	public JPanel getRegistre2() {
		return registre2;
	}

	/**
	 * 
	 * @return registre global
	 */
	public JPanel getRegistre3() {
		return registre3;
	}

}
