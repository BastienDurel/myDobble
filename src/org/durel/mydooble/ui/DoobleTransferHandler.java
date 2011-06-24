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
	
	public static class Transferable implements java.awt.datatransfer.Transferable {
		Image img;
		
		public Transferable(Image v) {
			img = v;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			// TODO Auto-generated method stub
			log.info("getTransferDataFlavors");
			return null;
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			// TODO Auto-generated method stub
			log.info("isDataFlavorSupported");
			return false;
		}

		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			// TODO Auto-generated method stub
			log.info("getTransferData");
			return null;
		}
		
	}
	
	public DoobleTransferHandler() {
		log.info("DoobleTransferHandler");
	}
	
	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}
	
	@Override
	protected java.awt.datatransfer.Transferable createTransferable(JComponent c) {
		log.info("c: " + c);
		JList l = (JList)c;
		Object v = l.getSelectedValue();
		log.info("v: " + v);
		if (v instanceof DoobleListModel.Image) {
			return new Transferable((DoobleListModel.Image)v);
		}
		return super.createTransferable(c);
	}
}
