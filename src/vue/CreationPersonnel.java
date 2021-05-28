package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlleur.ControleGestionPersonnel;
import controlleur.Utils;
import modele.ChefService;
import modele.Directeur;
import modele.Employe;
import modele.Personnel;



@SuppressWarnings("serial")
public class CreationPersonnel extends JPanel
{
	private JTextField textNom;
	private JTextField textPrenom;	
	private JTextField poste;
	private JComboBox<String> choixService;
	private JTextField tel;
	private JTextField id;
	private JTextField mdp;
	private JComboBox<String> typeAjout;
	
	/**
	 * 
	 * @param degréHierarchique Lors de la connexion, un int est défini afin de connaitre le niveau hiérarchique de l'utilisateur
	 * 							0. Admin
	 * 							1. directeur
	 * 							2. chef de service
	 * 							3. employé (interdit)
	 * @param employe 			Lors de l'edition, la classe prend en parametre un employe de la classe Personnel
	 */
	public CreationPersonnel(int degreHierarchique, Personnel employe) 
	{

			setLayout(null);
			setSize(1000,750);
			setPreferredSize(new Dimension(1000,750));
			setMinimumSize(new Dimension(1000,750));
			setMaximumSize(new Dimension(1000,750));
			setBackground(Color.darkGray);
			
			//panel 1

			JPanel pan1=new JPanel();
			JPanel pan2=new JPanel();
			JPanel pan3=new JPanel();
			JPanel bouton=new JPanel();
			
			JLabel nom=new JLabel("Nom");
			JLabel prenom=new JLabel("Prénom");
			JLabel type=new JLabel("Niveau hiérarchique");
			JLabel infoposte =new JLabel("Informations poste");
			JLabel numtel=new JLabel("Numéro tel poste");
			JLabel intituleposte=new JLabel("poste");			
			JLabel service=new JLabel("Service");

			JButton valider=new JButton("Valider");
			JButton annuler=new JButton("Annuler");
			
			Vector<String> v = new Vector<String>();
			typeAjout=new JComboBox<String>(v);
			
			CreationPersonnel cp = this;
			valider.addActionListener(new ActionListener() {					
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						new ControleGestionPersonnel(cp, employe);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}                	
			});	

			annuler.addActionListener(new ActionListener() {					
				@Override
				public void actionPerformed(ActionEvent e) {

					App.getContent().removeAll();
					App.getContent().add(new ViewPersonnel());
					App.getContent().repaint();
					App.getContent().revalidate();	
				}                	
			});	
			
			

			
			/*
			 * analyse du degré hiérarchique et du choix permis selon le degré 
			 * Admin
			 * directeur
			 * Chef de service
			 */
			if (degreHierarchique==0)
			{
				v.add("Employe");		
				v.add("Chef Service");	
				v.add("Directeur");		
			}else if (degreHierarchique==1)
			{	
				v.add("Employe");
				v.add("Chef Service");
			}else if (degreHierarchique==2)
			{
				v.add("Employe");
			}
			
			/*
			 * si employe est null, alors il s'agit d'une création d'employe
			 * si employe a un parametre different de null, alors il s'agit d'une édition d'employe
			 */
			if(employe != null) 
			{
				this.textPrenom=new JTextField(employe.getPrenom());	
				this.textNom=new JTextField(employe.getNom());
				this.poste=new JTextField(employe.getIntitulePoste());
				this.choixService=new JComboBox<String>(Utils.getArray("service"));
				this.choixService.setSelectedItem(employe.getService());
				this.tel=new JTextField(Integer.toString(employe.getNumTelPoste()));
				this.id=new JTextField(employe.getIdentifiant());
				this.mdp=new JTextField(employe.getMdp());
				
				
				if (employe.getClass()==Employe.class) typeAjout.setSelectedIndex(0);
				if (employe.getClass()==ChefService.class)typeAjout.setSelectedIndex(1);
				if (employe.getClass()==Directeur.class)typeAjout.setSelectedIndex(2);

			}
			else 
			{
				this.textPrenom=new JTextField();
				this.textNom=new JTextField();
				this.poste=new JTextField();
				this.choixService=new JComboBox<String>(Utils.getArray("service"));
				this.typeAjout.setSelectedItem("Employe");
				this.tel=new JTextField();
				this.id=new JTextField();
				this.mdp=new JTextField();
			}
			
			
			pan1.setLayout(null);
			
			pan1.setBounds(150,25, 650, 50);
			nom.setBounds(25,0,200,25);
			prenom.setBounds(425,0 , 200, 25);

			
			this.textNom.setBounds(25, 25, 200, 25);
			this.textNom.setPreferredSize(new Dimension(200,25));
			this.textNom.setMinimumSize(new Dimension(200,25));
			this.textNom.setMaximumSize(new Dimension(200,25));
			
			this.textPrenom.setBounds(425, 25, 200, 25);
			this.textPrenom.setPreferredSize(new Dimension(200,25));
			this.textPrenom.setMinimumSize(new Dimension(200,25));
			this.textPrenom.setMaximumSize(new Dimension(200,25));
			
			pan1.setBackground(Color.lightGray);
			
			pan1.add(nom);
			pan1.add(this.textNom);
			pan1.add(prenom);
			pan1.add(this.textPrenom);
			
			//panel 2
			pan2.setLayout(null);
			pan2.setBounds(150,100, 650, 50);
			pan2.setBackground(Color.lightGray);

			type.setBounds(75, 0, 200, 25);
			typeAjout.setBounds(75, 25, 500, 25);
		
			pan2.add(type);
			pan2.add(typeAjout);
			
			
			//panel 3
			pan3.setLayout(null);
			pan3.setBounds(100,200, 800, 175);
			pan3.setBackground(Color.lightGray);

			infoposte.setBounds(300,0,00,25);
			numtel.setBounds(25,70,200,25);
			this.tel.setBounds(25,95,200,25);
			intituleposte.setBounds(300,70,200,25);
			this.poste.setBounds(300,95,200,25);
			service.setBounds(575,70,200,25);
			this.choixService.setBounds(575,95,200,25);
			
			pan3.add(infoposte);
			pan3.add(numtel);
			pan3.add(this.tel);
			pan3.add(intituleposte);
			pan3.add(this.poste);
			pan3.add(service);
			pan3.add(this.choixService);
			
			
			//panel 4
			JPanel pan4=new JPanel();
			JLabel infoLog=new JLabel("Informations Log");
			JLabel idTitre=new JLabel("Identifiant");
			JLabel mdpTitre=new JLabel("Mot de passe");

			
			pan4.setLayout(null);			
			pan4.setBackground(Color.lightGray);
			
			pan4.setBounds(100,400, 800, 175);
			infoLog.setBounds(300,0,200,25);
			idTitre.setBounds(50,70,200,25);
			this.id.setBounds(50,95,200,25);
			mdpTitre.setBounds(550,70,200,25);
			mdp.setBounds(550,95,200,25);
			
			pan4.add(infoLog);
			pan4.add(idTitre);
			pan4.add(this.id);
			pan4.add(mdpTitre);
			pan4.add(mdp);
			
			//panel bouton
			bouton.setLayout(null);
			bouton.setBounds(0,600, 1000, 150);
			bouton.setBackground(Color.darkGray);
			
			annuler.setVerticalTextPosition(AbstractButton.BOTTOM);
			annuler.setHorizontalTextPosition(AbstractButton.CENTER);
			valider.setVerticalTextPosition(AbstractButton.BOTTOM);
			valider.setHorizontalTextPosition(AbstractButton.CENTER);
			
			valider.setBounds(700, 50, 200, 25);
			annuler.setBounds(100, 50, 200, 25);
			
			annuler.setBackground(Color.red);
			valider.setBackground(Color.green);
			
			
			bouton.add(annuler);
			bouton.add(valider);
			
			add(pan1);
			add(pan2);
			add(pan3);
			add(pan4);
			add(bouton);		
	}
	public JTextField getTextNom() {
		return textNom;
	}
	public JTextField getTextPrenom() {
		return textPrenom;
	}
	public JComboBox<String> getTypeAjout() {
		return typeAjout;
	}
	public JTextField getPoste() {
		return poste;
	}
	public JComboBox<String> getChoixService() {
		return choixService;
	}
	public JTextField getTel() {
		return tel;
	}
	public JTextField getId() {
		return id;
	}
	public JTextField getMdp() {
		return mdp;
	}
}
