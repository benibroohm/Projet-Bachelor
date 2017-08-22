package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import Controller.ControleurSaisie;

public class OpenC implements ActionListener {

	private Start st;
	private ControleurSaisie control;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFileChooser c = new JFileChooser();
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(st);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			control.getFilename().setText(c.getSelectedFile().getName());
			control.getDir().setText(c.getCurrentDirectory().toString());
			control.launchKeyGet(2);
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			control.getFilename().setText("You pressed cancel");
			control.getDir().setText("");
		}
	}
	
	public OpenC(Start st, ControleurSaisie control) {
		this.st = st;
	    this.control = control;
	}
}
