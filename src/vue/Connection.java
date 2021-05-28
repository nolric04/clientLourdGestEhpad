package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controlleur.ControleConnexion;

public class Connection extends JPanel{
	
	private JLabel error;
	
	public Connection() {

		setLayout(null);
		setBounds(0,0,1200,800);
		setBackground(App.defaultBack);
		
		JLabel welcome = new JLabel("Bienvenue");
		JLabel labelId = new JLabel("Identifiant");
		JTextField id = new JTextField();
		JLabel labelMdp = new JLabel("Mot de passe");
		JPasswordField mdp = new JPasswordField();
		JButton conn = new JButton("Connection");
		error = new JLabel("");
		
		
		conn.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	new ControleConnexion(id.getText(), mdp.getText());
		    }
		});
		
		mdp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		    	new ControleConnexion(id.getText(), mdp.getText());
			}
		});

		JPanel contenue = new JPanel();
		contenue.setLayout(null);
		contenue.setBounds(325, 150, 550, 300);
		contenue.setBackground(App.defaultBack);

		welcome.setFont(new Font("Arial", Font.BOLD, 25));
		labelId.setFont(App.commonFont);
		labelMdp.setFont(App.commonFont);
		error.setFont(App.commonFont);
		
		error.setForeground(Color.red);
		
		welcome.setBounds(200, 20, 250, 70);
		labelId.setBounds(150, 100, 250, 25);
		id.setBounds(150, 120, 250, 25);
		labelMdp.setBounds(150, 150, 250, 25);
		mdp.setBounds(150, 170, 250, 25);
		conn.setBounds(200, 225, 150, 25);
		error.setBounds(160, 80, 250, 25);
		

		contenue.add(welcome);
		contenue.add(labelId);
		contenue.add(id);
		contenue.add(labelMdp);
		contenue.add(mdp);
		contenue.add(conn);
		contenue.add(error);
		
		add(contenue);
		
		
	}

	public JLabel getError() {
		return error;
	}
}
