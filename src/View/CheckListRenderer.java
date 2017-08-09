package View;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckListRenderer extends JCheckBox implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7650849570571020205L;

	public Component getListCellRendererComponent(final JList list, final Object value,
			final int index, final boolean isSelected, final boolean hasFocus) {
		setEnabled(list.isEnabled());
		setSelected(((CheckListItem) value).isSelected());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setText(value.toString());
		return this;
	}

}
