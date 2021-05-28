package vue;

import javax.swing.JButton;
import javax.swing.JPanel;

import controlleur.ControleNavBar;
import modele.ChefService;
import modele.Directeur;

@SuppressWarnings("serial")
public class NavBar extends JPanel
{

    public NavBar()
    {
        setBounds(0,50,200,750);
        setLayout(null);
        setBackground(App.defaultBack);

        JButton regGlobal = new JButton("Registre Globale");
        regGlobal.setBounds(5, 10, 190, 30);
        regGlobal.setName("regGlobal");
        regGlobal.addActionListener(new ControleNavBar(1));
        
        JButton regConsigne = new JButton("Registre Consigne");
        regConsigne.setBounds(05, 50, 190, 30);
        regConsigne.addActionListener(new ControleNavBar(2));
 
        JButton resident = new JButton("Résidents");
        resident.setBounds(05, 130, 190, 30);
        resident.addActionListener(new ControleNavBar(4));

        JButton personnel = new JButton("Personnel");
        personnel.setBounds(05, 170, 190, 30);
        personnel.addActionListener(new ControleNavBar(5));

        JButton agenda = new JButton("Agenda");
        agenda.setBounds(05, 210, 190, 30);
        agenda.addActionListener(new ControleNavBar(6));


        add(regGlobal);
        add(regConsigne);
        add(resident);
        add(personnel);
        
        if(App.getConnected().getClass() == Directeur.class || App.getConnected().getClass() == ChefService.class)
        	add(agenda);

        
    }


}