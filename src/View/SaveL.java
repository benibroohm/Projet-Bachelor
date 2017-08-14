package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import Controller.ControleurSaisie;

public class SaveL implements ActionListener {

	private Start st;
	private JTextField filename = new JTextField(), dir = new JTextField();
	private ControleurSaisie control;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser c = new JFileChooser();
		// Demonstrate "Save" dialog:
		int rVal = c.showSaveDialog(this.st);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			filename.setText(c.getSelectedFile().getName());
			dir.setText(c.getCurrentDirectory().toString());
			st.setSaved();
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			filename.setText("You pressed cancel");
			dir.setText("");
		}
	}
	
	public SaveL(Start st, ControleurSaisie control) {
		this.st = st;
		dir.setEditable(false);
	    filename.setEditable(false);
	    this.control = control;
	}

}
