package vue;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlleur.ControleDeconnexion;

@SuppressWarnings("serial")
public class Header extends JPanel {

	public Header() {	    
		setSize(1200,50);
		setLayout(null);

		Font f = new Font("Arial", Font.BOLD, 25);

		JLabel bvn = new JLabel("Bienvenue "+ App.getConnected().getNom()+ " "+ App.getConnected().getPrenom());
		JButton deco = new JButton("Deconnexion");
		JLabel gestv = new JLabel("GestV");
		
		bvn.setFont(f);
		deco.setFont(f = new Font("Arial", Font.BOLD, 10));
		gestv.setFont(f = new Font("Papyrus", Font.BOLD, 30));
		
		bvn.setBounds(250, 5, 500, 25);
		deco.setBounds(1080, 12, 100, 25);
		gestv.setBounds(10, 15, 100, 35);
		
		deco.addActionListener(new ControleDeconnexion());
		
		add(bvn);
		add(deco);
		add(gestv);


	}
}



