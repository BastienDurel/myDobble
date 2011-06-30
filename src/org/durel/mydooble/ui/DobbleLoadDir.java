package org.durel.mydooble.ui;

import java.awt.Cursor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

class DobbleLoadDir implements Runnable {
	private Cursor old;
	private File dir;
	private MyDooble dobble;

	public DobbleLoadDir(Cursor old, File dir, MyDooble dobble) {
		this.old = old;
		this.dir = dir;
		this.dobble = dobble;
	}

	@Override
	public void run() {
		try {
			String[] children = dir.list();
			if (children != null) {
				dobble.fromModel.clear();
				for (int i = 0; i < children.length; ++i) {
					if (children[i].toLowerCase().endsWith(".jpg")
							|| children[i].toLowerCase().endsWith(".tiff")) {
						final String img = dir.getAbsolutePath() + File.separatorChar
								+ children[i];
						try {
							SwingUtilities.invokeAndWait(new Runnable() {
								@Override
								public void run() {
									dobble.fromModel.add(img);
									dobble.log.info(img);
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} finally {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					dobble.setCursor(old);
				}
			});
		}
	}
}