package org.durel.mydooble.ui;

/*
  	Copyright © 2011 Bastien Durel

    This file is part of myDobble.

    myDobble is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    myDobble is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with myDobble.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class DoobleCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = -8670403047283283045L;

	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel j = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		if (value instanceof DoobleListModel.Image) {
			BufferedImage img = ((DoobleListModel.Image) value).picture;
			j.setIcon(new ImageIcon(img));
		}
		return j;
	}
}
