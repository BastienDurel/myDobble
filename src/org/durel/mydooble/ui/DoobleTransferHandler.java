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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import org.durel.mydooble.ui.DoobleListModel.Image;

public class DoobleTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 4212253826837426082L;
	
	static Logger log = Logger.getLogger("org.durel.mydooble");
	
	public static class TransferableString implements java.awt.datatransfer.Transferable {
		String str;
		
		TransferableString(String s) {
			str = s;
		}
		
		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor f = new DataFlavor(String.class, "String");
			return new DataFlavor[] { f };
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.getRepresentationClass() == String.class;
		}

		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor.getRepresentationClass() != String.class)
				throw new UnsupportedFlavorException(flavor);
			return str;
		}
	}
	
	public static class TransferableImage implements java.awt.datatransfer.Transferable {
		Image img;
		
		public TransferableImage(Image v) {
			img = v;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor f = new DataFlavor(Image.class, "Image");
			return new DataFlavor[] { f };
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.getRepresentationClass() == Image.class;
		}

		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor.getRepresentationClass() != Image.class)
				throw new UnsupportedFlavorException(flavor);
			return img;
		}
		
	}
	
	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}
	
	@Override
	protected java.awt.datatransfer.Transferable createTransferable(JComponent c) {
		@SuppressWarnings("rawtypes")
		JList l = (JList)c;
		Object v = l.getSelectedValue();
		if (v instanceof DoobleListModel.Image) {
			log.info("create TransferableImage");
			return new TransferableImage((DoobleListModel.Image)v);
		}
		if (v instanceof String) {
			log.info("create TransferableString");
			return new TransferableString((String)v);
		}
		return super.createTransferable(c);
	}
}
