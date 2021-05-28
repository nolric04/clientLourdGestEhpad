package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlleur.ControleFinEquipe;
import controlleur.ControleGestionEquipe;
import modele.ChefService;
import modele.Employe;
import modele.Equipe;
import utils.DoubleListerEmploye;

public class CreationEquipe extends JPanel{

	DoubleListerEmploye dle;
	JComboBox<String> combochef;
	JTextField jtf;
	public CreationEquipe( int idEquipe ) {
		
		int w = 1000, h = 750;
		Font f = new Font("Arial", Font.BOLD, 15);

		ArrayList<ChefService> listChef = App.getEtablissement().getListChefServices();
		
		setSize(w, h);
		setLayout(null);
		setBackground(Color.DARK_GRAY);

		JLabel LChef = new JLabel("Chef de Service");
		JLabel LEmploye = new JLabel("Employés");
		JLabel LHoraire = new JLabel("Horaire");
		this.jtf = new JTextField();
		JButton retour = new JButton("Retour");
		JButton valider = new JButton("Valider");
		
		JLabel LEquipe = new JLabel("Equipe n°"+idEquipe);
		if(idEquipe==0)
			LEquipe = new JLabel("Nouvelle équipe");
		
		
		//****************************
		//Initialisation lorsque modif 
		int indexToLoad =0;
		Equipe equipe = null;
		ChefService cs = null;
		
		
			
		String[] textObj = new String[App.getEtablissement().getListChefServices().size()];
		for(int i=0; i<App.getEtablissement().getListChefServices().size();i++) {
			textObj[i] = App.getEtablissement().getListChefServices().get(i).getIdentifiant();
			
			for(Equipe e : App.getEtablissement().getListChefServices().get(i).getListeEquipe()) {
				if(e.getNumEquipe() == idEquipe) {
					equipe =e;
					indexToLoad = i;
					cs = App.getEtablissement().getListChefServices().get(i);
					
				}
				
			}
			
		}
		
		this.combochef = new JComboBox<String>(textObj);
		
		if(idEquipe!=0) {
			this.combochef.setSelectedIndex(indexToLoad);
			this.jtf.setText(equipe.getHoraire());
			
			
			ArrayList<Employe> ale = new ArrayList<Employe>();
			ale.addAll(equipe.getListeEmploye());
		}
		
		//******************************
		
		
		LEquipe.setFont(f);
		LEquipe.setForeground(Color.white);
		LEquipe.setBounds(150, 180, 150, 25);
		
		LChef.setFont(f);
		LEmploye.setFont(f);
		LHoraire.setFont(f);
		this.jtf.setFont(f);
		
		LChef.setForeground(Color.white);
		LEmploye.setForeground(Color.white);
		LHoraire.setForeground(Color.white);
		
		LChef.setBounds(150, 100, 150, 25);
		this.combochef.setBounds(300, 100, 175, 25);
		LHoraire.setBounds(550, 100, 100, 25);
		this.jtf.setBounds(650, 100, 250, 25);
		LEmploye.setBounds(175, 250, 250, 25);
		valider.setBounds(175, 650, 150, 25);
		retour.setBounds(650, 650, 150, 25);
		
		valider.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					System.out.println(idEquipe);
					new ControleGestionEquipe(idEquipe ,getDle().getList(), getCombochef().getSelectedItem().toString(), getJtf());
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}                	
		});
		
		retour.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleFinEquipe();
			}                	
		});
		
		add(LChef);
		add(combochef);
		add(LEquipe);
		
		add(LHoraire);
		add(this.jtf);
		add(LEmploye);
		
		add(retour);
		add(valider);
		
		this.dle = new DoubleListerEmploye(50, 270, 900 , 120, App.getEtablissement().getListEmployes(), equipe );
		add(dle);
	
	}
	
	
	public DoubleListerEmploye getDle() {
		return dle;
	}
	
	public JComboBox<String> getCombochef() {
		return combochef;
	}

	public void setCombochef(JComboBox<String> combochef) {
		this.combochef = combochef;
	}
	
	public JTextField getJtf() {
		return jtf;
	}
}
