package org.durel.mydooble.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;

public class DoobleListModel extends AbstractListModel {

	public static class Image {
		BufferedImage picture;
		String name;

		Image(BufferedImage i, String n) {
			picture = i;
			name = n;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private static final long serialVersionUID = 3785607490873542718L;
	static final int WIDTH = 32;
	static final int HEIGHT = 32;
	protected ArrayList<String> model = new ArrayList<String>();
	protected Map<String, Image> cache = new HashMap<String, Image>();

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
				BufferedImage resized = new BufferedImage(WIDTH, HEIGHT,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = resized.createGraphics();
				g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
				g.dispose();
				cache.put(key, new Image(resized, input.getName()));
				return cache.get(key);
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

	public void add(Image img) {
		int index0 = model.size();
		model.add(img.name);
		cache.put(img.name, img);
		fireIntervalAdded(this, index0, model.size());
	}

	public void init(ObjectInputStream i) throws IOException,
			ClassNotFoundException {
		@SuppressWarnings("unchecked")
		ArrayList<String> readObject = (ArrayList<String>) i.readObject();
		model = readObject;
	}

	public boolean save(Preferences prefs) {
		ByteArrayOutputStream o = new ByteArrayOutputStream(model.size() * 24);
		try {
			ObjectOutputStream s = new ObjectOutputStream(o);
			save(s);
			prefs.putByteArray("labels", o.toByteArray());
			prefs.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(ObjectOutputStream o) {
		try {
			o.writeObject(model);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
