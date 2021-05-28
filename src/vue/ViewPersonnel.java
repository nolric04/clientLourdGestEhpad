package vue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import controlleur.ControleListeTreePersonnel;
import controlleur.ControlePersonnel;
import controlleur.ControleRecherchePersonnel;
import modele.Administrateur;
import modele.ChefService;
import modele.Directeur;
import modele.Employe;
import modele.Equipe;
import modele.Personnel;
import utils.TableModelPersonnel;

@SuppressWarnings("serial")
public class ViewPersonnel extends JPanel{
	
	private JTable table;
	private JTextField searchPrenom;
	private JTextField searchNom;
	
	private Personnel selectedPersonnel;

	public ViewPersonnel() {
		
		setLayout(null);
		setVisible(true);
		setBounds(0,0,1000,750);
		setBackground(Color.lightGray);
		
		this.table = new JTable();
		
		JPanel container_tab = new JPanel();
        
		ArrayList<Personnel> ls = App.getEtablissement().getListPersonnel();
        JScrollPane scrollPane = new JScrollPane(this.table);
        
        JLabel textNom = new JLabel("Nom :");
        JLabel textPrenom = new JLabel("Prenom :");
        searchPrenom = new JTextField();
        searchNom = new JTextField();
        
        JButton searchButton = new JButton("Rechercher");
        JButton bouton_add = new JButton("Ajouter");
        JButton bouton_edit = new JButton("Modifier");

		JButton gestEqu =new JButton("Ajouter");
        
        searchButton.addActionListener(new ControleRecherchePersonnel(this));
        
        bouton_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControlePersonnel(App.getConnected(), null);
			}
		});
        
        bouton_edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ControlePersonnel(App.getConnected(), selectedPersonnel);
			}
		});
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent lse) {
        		if (!lse.getValueIsAdjusting()) {
        			changePersonelValue();
			    }
			}
		});
        
		container_tab.setLayout(null);// Position nulle
		
		container_tab.setBounds(200,65,800,650);
        scrollPane.setBounds(0,0,800,520);
        
        textPrenom.setBounds(220,10,100,25);
        searchPrenom.setBounds(220, 35, 200, 25);
        textNom.setBounds(460,10,50,25);
        searchNom.setBounds(460, 35, 200, 25);
        searchButton.setBounds(670,35,100,25);
        
        bouton_add.setBounds(125, 550, 100, 25);
        bouton_edit.setBounds(525, 550, 100, 25);
        
		container_tab.setBackground(Color.lightGray);
		
		setListePersonnel(ls);
        this.table.setAutoCreateRowSorter(true);
        
        scrollPane.setColumnHeaderView(this.table.getTableHeader());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        container_tab.add(scrollPane);
        
        container_tab.add(bouton_add);
        container_tab.add(bouton_edit);
        
        add(textNom);
        add(searchNom);
        add(textPrenom);
        add(searchPrenom);
        add(searchButton);

		add(container_tab);

		JPanel organigramme = new JPanel();
        JLabel titre_organigramme = new JLabel ();
        
        DefaultMutableTreeNode stEtablissement = new DefaultMutableTreeNode(App.getEtablissement().getNomEtablissement());
        DefaultMutableTreeNode nodeAdm = new DefaultMutableTreeNode("Administrateur :");
        DefaultMutableTreeNode nodeDir = new DefaultMutableTreeNode("Direction :");
        DefaultMutableTreeNode nodeEmp = new DefaultMutableTreeNode("Employés non Affecté");

        JTree tree = new JTree(stEtablissement);
        JScrollPane organiScroll = new JScrollPane(tree);
        
        ViewPersonnel vp = this;
        
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                    if (node == null) return;
                    Object nodeInfo = node.getUserObject();
                   // new ControleEquipe(nodeInfo.toString());
                }
                if (e.getClickCount() == 1) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                    if (node == null) return;
                    Object nodeInfo = node.getUserObject();
                    new ControleListeTreePersonnel(nodeInfo.toString(), vp);
                }
            }
        });
        
        organigramme.setLayout(null);
        
		organigramme.setBounds(0,0,200,750);
        organiScroll.setBounds(0,50,200,600);
        titre_organigramme.setBounds(50, 0, 150, 50);
		gestEqu.setBounds(50,660,100,30);
		
		gestEqu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//new ControleEquipe("");
			}
		});
        
		organigramme.setBackground(Color.gray);
		
        titre_organigramme.setText("Organigramme");
        titre_organigramme.setVerticalAlignment(SwingConstants.CENTER);
		
        organigramme.setFont(new Font("Serif",Font.BOLD,20));


        for(Administrateur adm : App.getEtablissement().getListAdmin()) {
        	nodeAdm.add(new DefaultMutableTreeNode( adm.getNom() + " " + adm.getPrenom() ));
        }

        for(Directeur d : App.getEtablissement().getListDirecteur()) {
        	nodeDir.add(new DefaultMutableTreeNode( d.getNom() + " " + d.getPrenom() ));
        }

        stEtablissement.add(nodeAdm);
        stEtablissement.add(nodeDir);
        
        ArrayList<Employe> nonAffecte = new ArrayList<Employe>();
        
        nonAffecte.addAll(App.getEtablissement().getListEmployes());
        
        for(ChefService cs : App.getEtablissement().getListChefServices()) {
        	DefaultMutableTreeNode ceNode =  new DefaultMutableTreeNode( cs.getService() );
        	ceNode.add(new DefaultMutableTreeNode( cs.getNom() + " " + cs.getPrenom() ));
        	for(Equipe eq : cs.getListeEquipe())
        	{
        		DefaultMutableTreeNode eqNode = new DefaultMutableTreeNode("Equipe " + Integer.toString(eq.getNumEquipe()));
        		for(Employe emp : eq.getListeEmploye())
        		{
        			eqNode.add(new DefaultMutableTreeNode( emp.getNom() + " " + emp.getPrenom() ));
        			nonAffecte.remove(emp);
        		}
        		ceNode.add(eqNode);
        	}
        	stEtablissement.add(ceNode);
        }
        
        for(Employe emp : nonAffecte)
        {
        	nodeEmp.add(new DefaultMutableTreeNode( emp.getNom() + " " + emp.getPrenom() ));
        }
        
        

        stEtablissement.add(nodeEmp);
        
        tree.setCellRenderer(new DefaultTreeCellRenderer());
        tree.setShowsRootHandles(false);
        
        tree.expandRow(0);
        
        organigramme.add(organiScroll);
        organigramme.add(titre_organigramme);
        organigramme.add(gestEqu);
        
		add(organigramme);
        
	}

	public void setListePersonnel(ArrayList<Personnel> ls)
	{
        String column[]={"Nom","Prénom","Numéro de poste","Service","Intitulé du poste","Identifiant","Mot de passe"};
		this.table.setModel(new TableModelPersonnel(ls,column));
	}
	

	private void changePersonelValue()
	{
		int selectedIndex = table.convertRowIndexToModel(table.getSelectedRow());
		for(Personnel p : App.getEtablissement().getListPersonnel()) {
          if( (p.getIdentifiant().equals( table.getModel().getValueAt(selectedIndex, 5)) )
           && (p.getNom().equals( table.getModel().getValueAt(selectedIndex, 0)) )
           && (p.getPrenom().equals( table.getModel().getValueAt(selectedIndex, 1)) )
           ){
              selectedPersonnel = p;
          }
      }
	}
	
	public String getTextNom()
	{
		return searchNom.getText();
	}
	
	public String getTextPrenom()
	{
		return searchPrenom.getText();
	}
}
