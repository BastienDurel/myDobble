package org.durel.mydooble.ui;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class DoobleCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = -8670403047283283045L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof DoobleListModel.Image) {
			BufferedImage img = ((DoobleListModel.Image) value).picture;
			JLabel j = (JLabel) super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
			j.setIcon(new ImageIcon(img));			
			return j;
		}
		return super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
	}
}
