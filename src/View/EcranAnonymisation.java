package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import Controller.ControleurSaisie;

public class EcranAnonymisation extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EcranAnonymisation(DefaultListModel<String> noms, ControleurSaisie control) {
		setSize(new Dimension(450, 350));
		setLocationRelativeTo(null);
		setTitle("S\u00E9lection m\u00E9thode");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblColonneAnonymiser = new JLabel("Colonne \u00E0 anonymiser");
		springLayout.putConstraint(SpringLayout.NORTH, lblColonneAnonymiser, 25, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblColonneAnonymiser, 157, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblColonneAnonymiser, 49, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblColonneAnonymiser, -145, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblColonneAnonymiser);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 19, SpringLayout.SOUTH, lblColonneAnonymiser);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 138, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 156, SpringLayout.SOUTH, lblColonneAnonymiser);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, lblColonneAnonymiser);
		getContentPane().add(scrollPane);
		
		JRadioButton rdbtnPseudonymisation = new JRadioButton("Pseudonymisation");
		rdbtnPseudonymisation.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnPseudonymisation, 22, SpringLayout.SOUTH, scrollPane);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list = new JList(noms);
		scrollPane.setViewportView(list);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnPseudonymisation, 68, SpringLayout.WEST, getContentPane());
		getContentPane().add(rdbtnPseudonymisation);
		
		JRadioButton rdbtnKanonymisation = new JRadioButton("K-anonymisation");
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnKanonymisation, 0, SpringLayout.NORTH, rdbtnPseudonymisation);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnKanonymisation, -178, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, rdbtnKanonymisation, -73, SpringLayout.EAST, getContentPane());
		getContentPane().add(rdbtnKanonymisation);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnPseudonymisation);
		group.add(rdbtnKanonymisation);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int option = 0;
				if (rdbtnPseudonymisation.isSelected())
					option = 1;
				if (rdbtnKanonymisation.isSelected())
					option = 2;
				control.anonymize(control.getSelected(), option);
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 16, SpringLayout.SOUTH, rdbtnPseudonymisation);
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 171, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, 40, SpringLayout.SOUTH, rdbtnPseudonymisation);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, 255, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnOk);
	}
}
