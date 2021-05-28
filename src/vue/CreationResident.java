package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controlleur.ControleGestionResident;
import controlleur.ControleNavBar;
import modele.Resident;
import utils.DateLabelFormatter;
import utils.DoubleListerString;



public class CreationResident extends JPanel {
	
	JTextField prenomRes;
	JTextField nomRes;
	JTextField numSSRes;
	JTextField numChambreRes;
	JTextField nomContact;
	JTextField numTelContact;
	JTextField lienContact;
	JDatePickerImpl dateDebut;
	java.sql.Date dateNaissance;
	JComboBox<String> textureCombo;
	DoubleListerString pathologies;
	DoubleListerString allergies;

	public CreationResident(ArrayList<String> listPatho, ArrayList<String> listAllergie, ArrayList<String> text, Resident res, boolean affichage) { // juste ajouter un boolean affichage 
		
		int w = 1000, h = 750;
		Font f = new Font("Arial", Font.BOLD, 15);

		
		setSize(w, h);
		setLayout(null);
		setBackground(Color.DARK_GRAY);

		// affichage DatePicker//TODO lors d'un click sur un bouton hors du datePicker le supprimer
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.dateDebut = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		
		// affichage des titres
				JLabel LDateNaissance = new JLabel("Date de naissance");
				JLabel LnomRes = new JLabel("Nom Résident");
				JLabel LprenomRes = new JLabel("Prenom Résident");
				JLabel LnumSS = new JLabel("Numero Securité Social");
				JLabel LnumChambre = new JLabel("Numéro de chambre");
				JLabel lNomContact = new JLabel("Nom");
				JLabel lNumTelContact = new JLabel("Numéro téléphone");
				JLabel lLienContact = new JLabel("Lien parenté");
				JLabel texture = new JLabel("Texture");
				JLabel contact = new JLabel("Contact");
				JButton retour = new JButton("Retour");
				JButton valider = new JButton("Valider");
		
		
		String[] textObj = new String[text.size()];
		for(int i=0; i<text.size();i++) {
			textObj[i] = text.get(i);
		}
	
		// instanciation des éléments de saisie de l'instance
		this.textureCombo = new JComboBox<String>(textObj);		
		this.prenomRes = new JTextField();
		this.nomRes = new JTextField();
		this.numSSRes = new JTextField();
		this.numChambreRes = new JTextField();
		this.nomContact = new JTextField();
		this.numTelContact = new JTextField();
		this.lienContact = new JTextField();
		this.pathologies = new DoubleListerString(50, 270, 900 , 120, listPatho, null);
		this.allergies = new DoubleListerString(50, 410, 900 , 120, listAllergie, null);


		// Dans le cas d'une modification de résident
		if(res != null) {
			// instanciation des éléments de saisie de l'instance avec les données du résident
			this.prenomRes = new JTextField(res.getPrenom());
			this.nomRes = new JTextField(res.getNom());
			this.numSSRes = new JTextField(res.getNumSecu());
			this.numChambreRes = new JTextField( Integer.toString(res.getNumChambre()) );
			this.nomContact = new JTextField(res.getNomUrgence());
			this.numTelContact = new JTextField(res.getNumUrgence());
			this.lienContact = new JTextField(res.getRelationUrgence());
			this.pathologies = new DoubleListerString(50, 270, 900 , 120, listPatho, res.getNomPathologie());
			this.allergies = new DoubleListerString(50, 410, 900 , 120, listAllergie, res.getNomAllergie());
			
			//recuperation des information contenue dans le DatePicker pour les mettre dans un String pour l'affichage
			Calendar cd = new GregorianCalendar(Integer.parseInt(res.getDateNaissance().substring(0, 4)), Integer.valueOf(res.getDateNaissance().substring(5, 7) ), Integer.parseInt(res.getDateNaissance().substring(8, 10)));
			String mois = String.valueOf(cd.get(Calendar.MONTH)).toString();
			String annee = String.valueOf(cd.get(Calendar.YEAR)).toString();
			String jour = String.valueOf(cd.get(Calendar.DAY_OF_MONTH)).toString();
			String date_Naissance_affichage=jour +"-"+mois+"-"+annee;

			datePanel.getModel().setYear(	Integer.parseInt(res.getDateNaissance().substring(0, 4) ));
			datePanel.getModel().setMonth( 	Integer.valueOf(res.getDateNaissance().substring(5, 7) )-1);
			datePanel.getModel().setDay( 	Integer.parseInt(res.getDateNaissance().substring(8, 10) ));
			datePanel.getModel().setSelected(true);
			
			for(int i=0; i<textObj.length ; i++) {
				if(textObj[i].equalsIgnoreCase(res.getNomRegime())) {
					this.textureCombo.setSelectedIndex(i);
				}
			}
			//Si il s'agit de la lecture d'une fiche résident 
			if(affichage==true) {
				this.prenomRes.setEditable(false);
				this.nomRes.setEditable(false);
				this.numSSRes.setEditable(false);
				this.numChambreRes.setEditable(false);
				this.nomContact.setEditable(false);
				this.numTelContact.setEditable(false);
				this.lienContact.setEditable(false);
				datePanel.removeAll();
				dateDebut.removeAll();
				JLabel dateNaissanceAffichage= new JLabel(date_Naissance_affichage);
				dateNaissanceAffichage.setBounds(150, 90, 250, 25);
				add(dateNaissanceAffichage);
				valider.removeAll();
			}
			
			CreationResident cr = this;
			//actionListener pour le bouton valider lors modification
			valider.addActionListener(new ActionListener() {					
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						new ControleGestionResident(cr, res);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}                	
			});		
			
		}
		
		
		// Dans le cas d'une création de résident
		else {
			
			
			System.out.println(this.dateDebut.getJFormattedTextField().getText());
			Resident resident = new Resident("", "", "", "", 0, "", "", "", "", null, null);
			
			CreationResident cr = this;
		
			valider.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ControleGestionResident(cr, resident);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}                	
		});		
		}
		
		
		LDateNaissance.setFont(f);
		LnomRes.setFont(f);
		this.nomRes.setFont(f);
		LprenomRes.setFont(f);
		this.prenomRes.setFont(f);
		LnumSS.setFont(f);
		this.numSSRes.setFont(f);
		LnumChambre.setFont(f);
		this.numChambreRes.setFont(f);
		lLienContact.setFont(f);
		lNumTelContact.setFont(f);
		lNomContact.setFont(f);
		this.nomContact.setFont(f);
		this.numTelContact.setFont(f);
		this.lienContact.setFont(f);
		texture.setFont(f);
		contact.setFont(new Font("Arial", Font.BOLD, 25));
		
		
		
		LDateNaissance.setForeground(Color.white);
		LnomRes.setForeground(Color.white);
		LprenomRes.setForeground(Color.white);
		LnumSS.setForeground(Color.white);
		LnumChambre.setForeground(Color.white);
		lLienContact.setForeground(Color.white);
		lNumTelContact.setForeground(Color.white);
		lNomContact.setForeground(Color.white);
		texture.setForeground(Color.white);
		contact.setForeground(Color.white);
		
		
		
		
		LnomRes.setBounds(150, 10, 250, 25);
		this.nomRes.setBounds(150, 30, 250, 25);
		LDateNaissance.setBounds(150, 70, 250, 25);
		dateDebut.setBounds(150, 90, 250, 25);
		LprenomRes.setBounds(600, 10, 250, 25);
		this.prenomRes.setBounds(600, 30, 250, 25);
		LnumSS.setBounds(600, 70, 250, 25);
		this.numSSRes.setBounds(600, 90, 250, 25);
		LnumChambre.setBounds(600, 130, 250, 25);
		this.numChambreRes.setBounds(600, 150, 250, 25);
		contact.setBounds(450,175,200,25);
		lNomContact.setBounds(700, 200, 200, 25);
		this.nomContact.setBounds(100, 220, 200, 25);
		lNumTelContact.setBounds(400, 200, 200, 25);
		this.numTelContact.setBounds(400, 220, 200, 25);
		lLienContact.setBounds(100, 200, 200, 25);
		this.lienContact.setBounds(700, 220, 200, 25);
		retour.setBounds(50, 650, 100, 25);
		valider.setBounds(850, 650 , 100, 25);
		texture.setBounds(450,625,100,25);
		textureCombo.setBounds(450, 650, 100,25);
		

		
		add(LnomRes);
		add(this.nomRes);
		add(LprenomRes);
		add(this.prenomRes);
		add(LnumSS);
		add(this.numSSRes);
		add(LnumChambre);
		add(this.numChambreRes);
		add(LDateNaissance);
		add(this.dateDebut);
		add(lLienContact);
		add(lNumTelContact);
		add(lNomContact);
		add(this.nomContact);
		add(this.numTelContact);
		add(this.lienContact);
		add(retour);
		add(valider);
		add(texture);
		add(this.textureCombo);
		add(contact);
		
		
		//Pour reetourner à la page viewResident
		retour.addActionListener(new ControleNavBar(4));
		
		
		if(res != null) {
			
			
			if(affichage==false) {
				add(this.pathologies);
				add(this.allergies);	
			}
			
			
			//modif apporter à la page lecture resident
			if(affichage==true) {
				//suppression du bouton valider et du combobox
				this.remove(valider);
				this.remove(textureCombo);	
				
				//récuperation des pathologie et allergie pour l'affichage
				Vector<String> path = new Vector<String>();
				for(int i=0 ; i < res.getNomPathologie().size();i++) 
				{
						path.add(res.getNomPathologie().get(i));					
				}
				
				
				Vector<String> allergie = new Vector<String>();
				for(int i=0 ; i < res.getNomAllergie().size();i++) 
				{
					allergie.add(res.getNomAllergie().get(i));					
				}
				
							
				JLabel pathologie=new JLabel("liste des pathologie");
				JLabel allerg=new JLabel("liste des allergie");
				JLabel nomTexture= new JLabel(res.getNomRegime(),SwingConstants.CENTER);
				
				JList<String> ListPatho= new JList<String>(path);
				JList<String> ListAllergie= new JList<String>(allergie);
				
				
				ListPatho.setBounds(50, 290, 900 , 120);
				pathologie.setBounds(400, 260, 900 , 20);
				ListAllergie.setBounds(50, 450, 900 , 120);
				allerg.setBounds(400, 430, 900 , 20);
				nomTexture.setBounds(430, 650, 100,25);
				
				
				pathologie.setForeground(Color.white);
				pathologie.setFont(new Font("Arial", Font.BOLD, 25));
				allerg.setForeground(Color.white);
				allerg.setFont(new Font("Arial", Font.BOLD, 25));
				nomTexture.setOpaque(true);
				nomTexture.setBackground(Color.lightGray);
				ListPatho.setBackground(Color.lightGray);
				ListAllergie.setBackground(Color.lightGray);
				
				add(pathologie);
				add(ListPatho);
				add(allerg);
				add(ListAllergie);
				add(nomTexture);
			}
		}
		
		
		//pour création des resident
		else {
			add(this.pathologies);
			add(this.allergies);
		}

	}
	
	public DoubleListerString getPathologies() {
		return this.pathologies;
	}
	
	public DoubleListerString getAllergies() {
		return this.allergies;
	}
	
	public JComboBox<String> getTexture() {
		return this.textureCombo;
	}
	
	public JDatePickerImpl getDateDebut() {
		return this.dateDebut;
	}

	public JTextField getPrenomRes()
	{
		return this.prenomRes;
	}
	
	public JTextField getNomRes()
	{
		return this.nomRes;
	}
	
	public JTextField getNumSSRes()
	{
		return this.numSSRes;
	}
	
	public JTextField getNumChambreRes()
	{
		return this.numChambreRes;
	}
	
	public JTextField getNomContact()
	{
		return this.nomContact;
	}
	
	public JTextField getNumTelContact()
	{
		return this.numTelContact;
	}
	
	public JTextField getLienContact()
	{
		return this.lienContact;
	}
}
	
	