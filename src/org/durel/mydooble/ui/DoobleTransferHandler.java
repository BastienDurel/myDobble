package org.durel.mydooble.ui;

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
		JList l = (JList)c;
		Object v = l.getSelectedValue();
		if (v instanceof DoobleListModel.Image) {
			log.info("create TransferableImage");
			return new TransferableImage((DoobleListModel.Image)v);
		}
		return super.createTransferable(c);
	}
}
