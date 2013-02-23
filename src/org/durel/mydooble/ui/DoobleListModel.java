package org.durel.mydooble.ui;

/*
  	Copyright © 2011 Bastien Durel

    This file is part of myDobble.

    myDobble is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    myDobble is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myDobble.  If not, see <http://www.gnu.org/licenses/>.
 */

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

@SuppressWarnings("rawtypes")
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
	Logger log = Logger.getLogger("org.durel.mydooble");

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
				log.info("Loading file " + input.getName());
				BufferedImage image = ImageIO.read(input);
				BufferedImage resized = new BufferedImage(WIDTH, HEIGHT,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = resized.createGraphics();
				g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
				g.dispose();
				cache.put(key, new Image(resized, input.getName()));
				return cache.get(key);
			} catch (Exception e) {
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
		fireIntervalRemoved(this, 0, index0);
	}

	public boolean add(String string) {
		for (int i = 0; i < model.size(); ++i) {
			if (model.get(i).equalsIgnoreCase(string)) {
				log.warning("ignoring redondent entry");
				return false;
			}
		}
		int index0 = model.size();
		model.add(string);
		fireIntervalAdded(this, index0, model.size());
		return true;
	}

	public boolean add(Image img) {
		if (cache.containsKey(img.name)) {
			log.warning("ignoring redondent entry");
			return false;
		}
		int index0 = model.size();
		model.add(img.name);
		cache.put(img.name, img);
		fireIntervalAdded(this, index0, model.size());
		return true;
	}

	public void remove(int selectedIndex) {
		model.remove(selectedIndex);
		fireIntervalRemoved(this, selectedIndex, selectedIndex);
	}

	public void init(ObjectInputStream i) throws IOException,
			ClassNotFoundException {
		@SuppressWarnings("unchecked")
		ArrayList<String> readObject = (ArrayList<String>) i.readObject();
		model = readObject;
	}

	public boolean save(Preferences prefs) {
		ByteArrayOutputStream o = new ByteArrayOutputStream(model.size() * 96);
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
