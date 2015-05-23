package chinse_radical_learning;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;

public class Radio_Button_List_Renderer extends JRadioButton implements ListCellRenderer{
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		setEnabled(list.isEnabled());
		setSelected(((RadioListItem)value).isSelected());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setText(value.toString());
		return this;
	}
}
