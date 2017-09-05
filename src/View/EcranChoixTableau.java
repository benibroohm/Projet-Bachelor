package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import Controller.ControleurSaisie;

/**
 * Classe qui présente une interface pour le choix des lignes/colonnes à ajouter.
 * 
 * @author Beni Broohm
 *
 */
public class EcranChoixTableau extends JDialog {
	/**
	 * Par défaut, on ajoute une ligne et une colonne si l'utilisateur n'entre rien.
	 */
	private static final long serialVersionUID = 1L;
	private int nc = 1;
	private int nl = 1;
	public EcranChoixTableau(ControleurSaisie control) {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(new Dimension(480, 250));
		setLocationRelativeTo(null);
		setTitle("Cr\u00E9er tableau");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblEntrerLeNombre = new JLabel("Entrer le nombre de colonnes : ");
		springLayout.putConstraint(SpringLayout.NORTH, lblEntrerLeNombre, 39, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblEntrerLeNombre, 40, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblEntrerLeNombre);
		
		JSpinner spinner = new JSpinner();
		springLayout.putConstraint(SpringLayout.NORTH, spinner, -3, SpringLayout.NORTH, lblEntrerLeNombre);
		springLayout.putConstraint(SpringLayout.WEST, spinner, 290, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, spinner, -26, SpringLayout.EAST, getContentPane());
		spinner.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		getContentPane().add(spinner);
		
		JLabel lblEntrerLeNombres = new JLabel("Entrer le nombres de lignes :");
		springLayout.putConstraint(SpringLayout.NORTH, lblEntrerLeNombres, 92, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblEntrerLeNombres, 40, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblEntrerLeNombres);
		
		JSpinner spinner_1 = new JSpinner();
		springLayout.putConstraint(SpringLayout.NORTH, spinner_1, -3, SpringLayout.NORTH, lblEntrerLeNombres);
		springLayout.putConstraint(SpringLayout.WEST, spinner_1, 0, SpringLayout.WEST, spinner);
		springLayout.putConstraint(SpringLayout.EAST, spinner_1, 438, SpringLayout.WEST, getContentPane());
		spinner_1.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		getContentPane().add(spinner_1);
		
		JButton btnNewButton = new JButton("Cr\u00E9er");
		
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 41, SpringLayout.SOUTH, lblEntrerLeNombres);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 172, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 287, SpringLayout.WEST, getContentPane());
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// On prend les entrées du spinner.
				nc = ((Integer) spinner.getValue()).intValue();
				nl = ((Integer) spinner_1.getValue()).intValue();
				control.createTableau(nc+1, nl); // On crée notre tableau
				control.getFenetre().refreshUI();
				dispose();
			}
		});
		
		getContentPane().add(btnNewButton);
	}
	
}
