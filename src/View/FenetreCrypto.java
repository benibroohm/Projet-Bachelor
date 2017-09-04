package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import Controller.ControleurSaisie;

public class FenetreCrypto extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	public FenetreCrypto(ControleurSaisie control, int option) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("Crypter");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(516, 300));
		setLocationRelativeTo(null);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblEntrerLeMot = new JLabel("Entrer le mot de passe :");
		springLayout.putConstraint(SpringLayout.NORTH, lblEntrerLeMot, 32, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblEntrerLeMot, 25, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblEntrerLeMot);
		
		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 32, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 38, SpringLayout.EAST, lblEntrerLeMot);
		springLayout.putConstraint(SpringLayout.EAST, passwordField, 221, SpringLayout.EAST, lblEntrerLeMot);
		getContentPane().add(passwordField);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					dispose();
					if (option == 1)
						try {
							control.saveEncrypted(new String(passwordField.getPassword()));
						} catch (InvalidKeyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else if (option == 2)
						control.openEncrypted(new String(passwordField.getPassword()));
					else if (option == 3) {
						try {
							control.saveLink(new String(passwordField.getPassword()));
						} catch (InvalidKeyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (option == 4)
						control.openLink(new String(passwordField.getPassword()));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 203, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -26, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -199, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnOk);
		
		JTextArea txtrNbVous = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, txtrNbVous, 25, SpringLayout.SOUTH, passwordField);
		springLayout.putConstraint(SpringLayout.SOUTH, txtrNbVous, -18, SpringLayout.NORTH, btnOk);
		txtrNbVous.setBackground(Color.LIGHT_GRAY);
		springLayout.putConstraint(SpringLayout.WEST, txtrNbVous, 0, SpringLayout.WEST, lblEntrerLeMot);
		springLayout.putConstraint(SpringLayout.EAST, txtrNbVous, -32, SpringLayout.EAST, getContentPane());
		txtrNbVous.setFont(new Font("Arial Black", Font.ITALIC, 11));
		txtrNbVous.setText("N.B : Vous devez crypter le fichier contenant\r\nles donn\u00E9es initiales avant d'appliquer l'anonymisation.\r\n\r\nSi vous enregistrez les correspondances, veuillez utiliser\r\nla pseudonymisation uniquement.");
		getContentPane().add(txtrNbVous);
	}
}
