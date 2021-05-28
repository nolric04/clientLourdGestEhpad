package vue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controlleur.ControleFinAgenda;
import controlleur.ControleGestionAgenda;
import controlleur.Utils;
import modele.Personnel;
import utils.DateLabelFormatter;

public class CreationAgenda extends JPanel {

	JDatePickerImpl dateDebut;
	JTextField jtf;
	JSpinner timeSpinner;
	Date date;
	String titreField;


	public CreationAgenda(Personnel p, boolean isReunion, int id, Date date2) throws SQLException, ParseException {
		setLayout(null);
		setBounds(0,0,400,250);
		
		this.date = Utils.getDateAgenda(id, isReunion);
		if(this.date == null) {
			this.date = new Date();
		}
		
		JLabel what = null;
		JLabel titre;
		
		JLabel labelDate = new JLabel("Date");
		JButton valider = new JButton("Valider");
		JButton annuler = new JButton("Annuler");
		
		UtilDateModel model = new UtilDateModel();
		Properties prop = new Properties();
		prop.put("text.today", "Today");
		prop.put("text.month", "Month");
		prop.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, prop);
		this.dateDebut = new JDatePickerImpl(datePanel, new DateLabelFormatter() );
		
		SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		sdm.setValue(date);
		
		this.timeSpinner = new JSpinner( sdm );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this.timeSpinner, "HH:mm:ss");
		this.timeSpinner.setEditor(timeEditor);
		
		if(isReunion) {
			titre = new JLabel("Titre");
			this.jtf = new JTextField();
			this.jtf.setBounds(50, 125, 150, 25);
			titre.setBounds(50, 100, 150, 25);
			what = new JLabel("Création Réunion");
			add(titre);
			add(this.jtf);
		}else {
			what = new JLabel("Création Visite");
		}
		
		what.setFont(new Font("Arial", Font.BOLD,25) );
		what.setBounds(80, 0, 225, 50);
		labelDate.setBounds(50, 50, 150, 25);
		this.dateDebut.setBounds(50, 75, 150, 25);
		this.timeSpinner.setBounds(200,75,150,25);
		valider.setBounds(50, 175, 100, 25);
		annuler.setBounds(225 , 175, 100, 25);

		//this.date = Utils.getDateAgenda(id, isReunion);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		datePanel.getModel().setDate(Integer.parseInt( sdf.format(this.date).substring(0, 4)), Integer.parseInt(  sdf.format(this.date).substring(5, 7))-1, Integer.parseInt(  sdf.format(this.date).substring(8, 10)));
		datePanel.getModel().setSelected(true);
		
		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ControleGestionAgenda((CreationAgenda) App.getContent().getComponent(0), isReunion, id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleFinAgenda((CreationAgenda) App.getContent().getComponent(0));
			}
			
		});

		
		
		add(what);
		add(labelDate);
		add(this.dateDebut);
		add(this.timeSpinner);
		add(valider);
		add(annuler);
	
	}
	
	
	public CreationAgenda(Personnel p, boolean isReunion, int id, Date date2, String titreField) throws SQLException, ParseException {
		
		setLayout(null);
		setBounds(0,0,400,250);
		
		this.date = Utils.getDateAgenda(id, isReunion);
		this.titreField = titreField;
		JLabel what = null;
		JLabel titre;
		
		JLabel labelDate = new JLabel("Date");
		JButton valider = new JButton("Valider");
		JButton annuler = new JButton("Annuler");
		
		UtilDateModel model = new UtilDateModel();
		Properties prop = new Properties();
		prop.put("text.today", "Today");
		prop.put("text.month", "Month");
		prop.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, prop);
		this.dateDebut = new JDatePickerImpl(datePanel, new DateLabelFormatter() );
		
		if(this.date == null) {
			this.date = new Date();
		}
		
		SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		sdm.setValue(date);
		
		this.timeSpinner = new JSpinner( sdm );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this.timeSpinner, "HH:mm:ss");
		this.timeSpinner.setEditor(timeEditor);
		
		if(isReunion) {
			titre = new JLabel("Titre");
			this.jtf = new JTextField();
			this.jtf.setBounds(50, 125, 150, 25);
			titre.setBounds(50, 100, 150, 25);
			what = new JLabel("Création Réunion");
			add(titre);
			add(this.jtf);
		}else {
			what = new JLabel("Création Visite");
		}
		
		what.setFont(new Font("Arial", Font.BOLD,25) );
		what.setBounds(80, 0, 225, 50);
		labelDate.setBounds(50, 50, 150, 25);
		this.dateDebut.setBounds(50, 75, 150, 25);
		this.timeSpinner.setBounds(200,75,150,25);
		valider.setBounds(50, 175, 100, 25);
		annuler.setBounds(225 , 175, 100, 25);
		
		
		if(id!=0) {
			if(isReunion) {
				this.titreField= Utils.getTitreReunion(id);
				this.jtf.setText(this.titreField);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			datePanel.getModel().setDate(Integer.parseInt( sdf.format(this.date).substring(0, 4)), Integer.parseInt(  sdf.format(this.date).substring(5, 7))-1, Integer.parseInt(  sdf.format(this.date).substring(8, 10)));
			datePanel.getModel().setSelected(true);
			
			
		}
		
		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ControleGestionAgenda((CreationAgenda) App.getContent().getComponent(0), isReunion, id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControleFinAgenda((CreationAgenda) App.getContent().getComponent(0));
			}
			
		});

		
		
		add(what);
		add(labelDate);
		add(this.dateDebut);
		add(this.timeSpinner);
		add(valider);
		add(annuler);
	
	}



	public JSpinner getTimeSpinner() {
		return timeSpinner;
	}


	public JDatePickerImpl getDateDebut() {
		return dateDebut;
	}

	public JTextField getJtf() {
		return jtf;
	}
	
}
