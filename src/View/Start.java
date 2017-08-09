package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import Controller.ControleurSaisie;
import net.miginfocom.swing.MigLayout;

public class Start extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ControleurSaisie control;
	public JScrollPane scrollPane;
	public Start(ControleurSaisie controleur) {
		URL iconURL = getClass().getResource("../Ressources/logo.jpg");
		// iconURL is null when not found
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setMinimumSize(new Dimension(800, 500));
		setPreferredSize(new Dimension(500, 500));
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setLocation(new Point(350, 100));
		setTitle("Nascon.Unige");
		this.setControleur(controleur);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				
		scrollPane = new JScrollPane(controleur.getTable());
		scrollPane.setPreferredSize(new Dimension(20, 20));
		scrollPane.setViewportBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		scrollPane.setMinimumSize(new Dimension(600, 23));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 672, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.addGap(368)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		JLabel lblColonne = new JLabel("Colonne");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblColonne, 10, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblColonne, 43, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblColonne, 31, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblColonne, -29, SpringLayout.EAST, panel_1);
		panel_1.add(lblColonne);
		
		JButton btnAjouterColonne = new JButton("+1 colonne");
		sl_panel_1.putConstraint(SpringLayout.NORTH, btnAjouterColonne, 6, SpringLayout.SOUTH, lblColonne);
		sl_panel_1.putConstraint(SpringLayout.WEST, btnAjouterColonne, 10, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, btnAjouterColonne, -6, SpringLayout.EAST, panel_1);
		panel_1.add(btnAjouterColonne);
		btnAjouterColonne.setMnemonic(KeyEvent.VK_ENTER);
		
		JButton btnnColonnes = new JButton("+N colonnes");
		sl_panel_1.putConstraint(SpringLayout.NORTH, btnnColonnes, 6, SpringLayout.SOUTH, btnAjouterColonne);
		sl_panel_1.putConstraint(SpringLayout.WEST, btnnColonnes, 0, SpringLayout.WEST, btnAjouterColonne);
		sl_panel_1.putConstraint(SpringLayout.EAST, btnnColonnes, -6, SpringLayout.EAST, panel_1);
		panel_1.add(btnnColonnes);
		btnnColonnes.setMnemonic(KeyEvent.VK_ENTER);
		
		JButton btnColonne = new JButton("-1 colonne");
		btnColonne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				control.removeColumns();
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, btnColonne, 6, SpringLayout.SOUTH, btnnColonnes);
		sl_panel_1.putConstraint(SpringLayout.WEST, btnColonne, 0, SpringLayout.WEST, btnAjouterColonne);
		sl_panel_1.putConstraint(SpringLayout.EAST, btnColonne, -1, SpringLayout.EAST, btnAjouterColonne);
		panel_1.add(btnColonne);
		btnnColonnes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				control.launchAjout("colonne");
			}
		});
		btnAjouterColonne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				control.addC(1);
				System.out.println("Table control : " + control.getTable().getColumnCount());
				refreshUI();
				//updateTable(controleur.getTable());
				System.out.println("Table fenetre : " + control.getTable().getColumnCount());
			}
		});
		
		JButton btnAjouterLigne = new JButton("+1 ligne");
		btnAjouterLigne.setMnemonic(KeyEvent.VK_ENTER);
		btnAjouterLigne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				control.addL(1);
			}
		});
		panel.setLayout(new MigLayout("", "[109px,grow]", "[][23px][][][][][][][23px][][][grow][grow][]"));
		
		JLabel lblLigne = new JLabel("Ligne");
		panel.add(lblLigne, "cell 0 0,alignx center");
		panel.add(btnAjouterLigne, "cell 0 1,growx,aligny top");
		
		JButton btnnLignes = new JButton("+N lignes");
		btnnLignes.setMnemonic(KeyEvent.VK_ENTER);
		btnnLignes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				control.launchAjout("ligne");
			}
		});
		panel.add(btnnLignes, "cell 0 2,growx");
		
		JButton btnLigne = new JButton("-1 ligne");
		btnLigne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeRows();
			}
		});
		panel.add(btnLigne, "cell 0 3,growx");
		
		getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		
		JMenuItem mntmOuvrir = new JMenuItem("Ouvrir");
		mnFichier.add(mntmOuvrir);
		
		JMenuItem mntmNouveau = new JMenuItem("Nouvelle feuille");
		mntmNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controleur.launchChoice();
			}
		});
		mnFichier.add(mntmNouveau);
		
		// A faire !!!
		JMenuItem mntmEnregistrer = new JMenuItem("Enregistrer");
		mnFichier.add(mntmEnregistrer);
		
		JMenuItem mntmEnregistrerSous = new JMenuItem("Enregistrer sous");
		mnFichier.add(mntmEnregistrerSous);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mnFichier.add(mntmQuitter);
		
		JMenu mnDonnes = new JMenu("Donn\u00E9es");
		menuBar.add(mnDonnes);
		
		JMenuItem mntmChiffer = new JMenuItem("Chiffer");
		mnDonnes.add(mntmChiffer);
		
		JMenuItem mntmAnonymiser = new JMenuItem("Anonymiser");
		mntmAnonymiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.launchColumnSelection();
			}
		});
		mnDonnes.add(mntmAnonymiser);
		
		JMenu mnOutils = new JMenu("Outils");
		menuBar.add(mnOutils);
		
		JMenuItem mntmRafraichir = new JMenuItem("Rafraichir");
		mntmRafraichir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//updateTable(control.getTable());
				refreshUI();
			}
		});
		mnOutils.add(mntmRafraichir);
	}
	public ControleurSaisie getControleur() {
		return control;
	}
	public void setControleur(ControleurSaisie control) {
		this.control = control;
	}
	
	public void refreshUI() {
		this.scrollPane.setViewportView(control.getTable());
		this.scrollPane.getViewport().revalidate();
		this.scrollPane.revalidate();
	
		//CustomModel model = table.getModel();
		//Vector titre = (Vector) model.getDataVector().get(0);
		//model.setColumnIdentifiers(titre);
	}
	
	public void removeRows() {
		this.control.removeRows();
	}
}
