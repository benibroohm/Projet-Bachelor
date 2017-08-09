package View;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;

import Controller.ControleurSaisie;

public class EcranNombre extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int n = 1;
	public EcranNombre(ControleurSaisie control, String choix) {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		String nn = "Nombre de " + choix;
		setTitle(nn);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(450, 230));
		setLocationRelativeTo(null);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		String name = "Entrer le nombre de " + choix + " : ";
		JLabel lblEntrerLeNombre = new JLabel(name);
		springLayout.putConstraint(SpringLayout.NORTH, lblEntrerLeNombre, 53, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblEntrerLeNombre, 26, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblEntrerLeNombre);
		
		JSpinner spinner = new JSpinner();
		spinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				n = ((Integer) spinner.getValue()).intValue();
				if (choix.compareTo("ligne") == 0)
					control.addL(n);
				else
					control.addC(n);
				dispose();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, spinner, 50, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, spinner, 43, SpringLayout.EAST, lblEntrerLeNombre);
		springLayout.putConstraint(SpringLayout.EAST, spinner, -90, SpringLayout.EAST, getContentPane());
		getContentPane().add(spinner);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				n = ((Integer) spinner.getValue()).intValue();
				if (choix.compareTo("ligne") == 0)
					control.addL(n);
				else
					control.addC(n);
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 40, SpringLayout.SOUTH, spinner);
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 163, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnOk, 262, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnOk);
	}
}
