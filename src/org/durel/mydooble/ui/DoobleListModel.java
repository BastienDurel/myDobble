package org.durel.mydooble.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;

public class DoobleListModel extends AbstractListModel {
	private static final long serialVersionUID = 3785607490873542718L;
	static final int WIDTH = 32;
	static final int HEIGHT = 32;
	protected ArrayList<String> model = new ArrayList<String>();
	protected Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();

	boolean isGlyph = false;

	@Override
	public int getSize() {
		return model.size();
	}

	@Override
	public Object getElementAt(int index) {
		String key = model.get(index);
		if (isGlyph) {
			if (cache.containsKey(key))
				return cache.get(key);
			File input = new File(model.get(index));
			try {
				BufferedImage image = ImageIO.read(input);
				BufferedImage resized = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = resized.createGraphics();
				g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
				g.dispose();
				cache.put(key, resized);
				return image;
			} catch (IOException e) {
				Logger log = Logger.getLogger("org.durel.mydooble");
				log.warning(e.getLocalizedMessage());
				// e.printStackTrace();
			}
			return null;
		} else
			return key;
	}

	public void clear() {
		int index0 = model.size();
		model.clear();
		cache.clear();
		fireIntervalRemoved(this, index0, 0);
	}

	public void add(String string) {
		int index0 = model.size();
		model.add(string);
		fireIntervalAdded(this, index0, model.size());
	}

}
