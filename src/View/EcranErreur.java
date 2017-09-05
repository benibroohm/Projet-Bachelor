package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class EcranErreur extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public EcranErreur(String error) {
		setTitle("Erreur");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(new Dimension(450, 305));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		textField = new JTextField(error);
		textField.setBounds(10, 25, 414, 180);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(170, 232, 89, 23);
		getContentPane().add(btnOk);
	}

}
