package View;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import Controller.ControleurSaisie;

public class SaveR implements ActionListener {

	private Component st;
	private ControleurSaisie control;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser c;
		if (control.isPathSet()) {
			c = new JFileChooser(new File(control.getFolderPath()+"/Correspondances"));
		}
		else
			c = new JFileChooser();
		// Demonstrate "Save" dialog:
		int rVal = c.showSaveDialog(this.st);
		
		if (rVal == JFileChooser.APPROVE_OPTION) {
			control.getFilename().setText(c.getSelectedFile().getName());
			control.getDir().setText(c.getCurrentDirectory().toString());
			control.launchKeyGet(3);
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			control.getFilename().setText("You pressed cancel");
			control.getDir().setText("");
		}
	}
	
	public SaveR(Component st, ControleurSaisie control) {
		this.st = (Start) st;
	    this.control = control;
	}

}
