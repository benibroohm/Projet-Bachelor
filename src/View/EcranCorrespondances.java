package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Model.CustomModel;

public class EcranCorrespondances extends JDialog {
	public EcranCorrespondances(Vector<Vector<String>> data) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(new Dimension(555, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Correspondances");
		getContentPane().setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(227, 227, 89, 23);
		getContentPane().add(btnOk);
		
		CustomModel model = new CustomModel(1, 1);
		model.setDataVector(data, data.elementAt(0));
		table = new JTable(model);		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 519, 205);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);		
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
}
