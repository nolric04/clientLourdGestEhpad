package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controlleur.ControleGestionEvenement;
import controlleur.Utils;
import modele.Evenement;
import utils.DoubleLister;


public class CreationEvenement extends JPanel
{
	
	private JTextField titreTexte;
	private JTextArea descriptionTexte;
	private JLabel dateLabel;
	private JComboBox<String> selectRegistre;
	private JComboBox<String> selectImportance;
	private DoubleLister dl;
	

	public CreationEvenement(Evenement e, String service)
	{
		//***********JPanel Principale***************//
		
		setBounds(0,0,1000,750);
		setBackground(Color.gray);
		this.setLayout(null);// Layout obligatoire pour chaque Panel
		
		
		
		this.dl = new DoubleLister(50, 450, 900, 200, App.getEtablissement().getListResident(), e);
		add(this.dl);
		
		//***********JPanel niv2*****************//
		
		
		
		JPanel selecteurRegistre = new JPanel();
		JPanel selecteurImportance = new JPanel();
		JPanel date_heure = new JPanel();
		JPanel titre = new JPanel();
		JPanel description= new JPanel();

		JButton valider = new JButton("Envoyer");

		selecteurRegistre.setLayout(null);
		selecteurImportance.setLayout(null);
		date_heure.setLayout(null);
		titre.setLayout(null);
		description.setLayout(null);
		valider.setLayout(null);
		

		selecteurRegistre.setBounds(7,20,293,40);
		selecteurImportance.setBounds(322,20,400,40);
		date_heure.setBounds(744,20,230,40);
		titre.setBounds(145,70,730,50);
		description.setBounds(0,140,1000,300);
		valider.setBounds(450,675,85,30);

		selecteurRegistre.setBackground(Color.gray);
		selecteurImportance.setBackground(Color.gray);
		date_heure.setBackground(Color.gray);
		titre.setBackground(Color.gray);
		description.setBackground(Color.gray);
		
		
		add(selecteurRegistre);
		add(selecteurImportance);
		add(date_heure);
		add(titre);
		add(description);
		add(valider);
				
		//***********niv3 remplissage des Jpanel this.selectRegistre - this.selectImportance - date_heure*****************//
		
		String [] imp = {"1","2","3"};
		
		/**
		 * JComboBox : Instanciation de liste deroulantes
		 * 
		 */
		
		this.selectRegistre= new JComboBox<String>(Utils.getArray("Service"));
		this.selectImportance= new JComboBox<String>(imp);
		
		if(e != null) {
			this.selectImportance.setSelectedIndex(e.getGravite()-1);
		}
		this.selectRegistre.setSelectedItem(service);
		
		
		/**
		 * DateFormat : Instanciation d'une date
		 */
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String dateSt=df.format(d);	
		
		this.dateLabel=new JLabel(dateSt);
		
		this.selectImportance.setBounds(0,0,400,30);
		this.selectRegistre.setBounds(0,0,293,30);
		this.dateLabel.setBounds(30,0,170,30);

		this.dateLabel.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,18));
		
		this.dateLabel.setForeground(Color.white);
		
		
		
		
		selecteurRegistre.add(this.selectRegistre);
		selecteurImportance.add(this.selectImportance);
		date_heure.add(this.dateLabel);
		
		//*************niv3 remplissage titre + description ************************//

		
		
		if(e==null) 
		{
			this.titreTexte= new JTextField("");
			CreationEvenement ce=this;
			valider.addActionListener(new ActionListener() 
			{			
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					try {
						new ControleGestionEvenement(ce,e);
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}					
				}
			});
		}		
		else 
		{
			this.titreTexte= new JTextField(e.getTitre());
			CreationEvenement ce=this;
			valider.addActionListener(new ActionListener() 
			{			
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					try {
						new ControleGestionEvenement(ce,e);
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}					
				}
			});
		}
		
		this.titreTexte.addKeyListener(new KeyAdapter()
		
				
		/**
		 * keyTiped : Methode permettant de limiter le nombre max de mot saisis
		 * Boucle if : si le texte depasse 44 caracteres, la suite n'apparait plus
		 */
		{
	        @Override
	        public void keyTyped(KeyEvent e) 
	        {
		        if (titreTexte.getText().length() >= 45 ) //limit to 45 characters
		        e.consume();
	        }
        });		

		if(e==null) 
		{
			this.descriptionTexte = new JTextArea("écrivez votre texte ..");
		}
		
		else 
		{
			this.descriptionTexte= new JTextArea(e.getDescription());
		}
		
		this.titreTexte.addKeyListener(new KeyAdapter() 
		{
	        @Override
	        public void keyTyped(KeyEvent e) 
	        {
		        if (descriptionTexte.getText().length() >= 500 ) //limit to 500 characters
		        e.consume();
	        }
        });
		
		
		/**
		 * JScrollPane : conteneur qui si le composant affiché est trop permet un defilement
		 * horizontal/verticel de ce dernier
		 */
		
		JScrollPane descriptionPan = new JScrollPane(this.descriptionTexte);

		this.descriptionTexte.setLineWrap(true);

		
		descriptionPan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		descriptionPan.setBounds(7,0,970,300);
		this.titreTexte.setBounds(10,5,700,40);
		this.descriptionTexte.setBounds(7,0,970,00);
		
 
		
		
		this.titreTexte.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,22));
		this.descriptionTexte.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,16));
		//this.descriptionTexte.setFont(App.font);
		
		titre.add(this.titreTexte);
		description.add(descriptionPan);
		descriptionPan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		

		
	}
	
	public JTextField getTitreTexte() {
		return titreTexte;
	}

	public JTextArea getDescriptionTexte() {
		return descriptionTexte;
	}

	public JLabel getDateLabel() {
		return dateLabel;
	}

	public JComboBox<String> getSelectRegistre() {
		return selectRegistre;
	}

	public JComboBox<String> getSelectImportance() {
		return selectImportance;
	}

	public DoubleLister getDl() {
		return dl;
	}


}
