package org.javatribe.calculator.domain;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TabCheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabCheckBoxRenderer() {
		this.setBorderPainted(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		setHorizontalAlignment(JLabel.CENTER);
		return this;
	}
}
