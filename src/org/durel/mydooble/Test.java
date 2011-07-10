package org.durel.mydooble;

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

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Test {

	static public class PlainFormatter extends java.util.logging.Formatter {

		@Override
		public String format(LogRecord record) {
			return record.getMessage() + "\n";
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Core t = new Core(6);
		t.log.setLevel(Level.INFO);
		t.log.setUseParentHandlers(false);
		ConsoleHandler h = new ConsoleHandler();
		h.setFormatter(new PlainFormatter());
		t.log.addHandler(h);
		t.log.info("test ...");
		/*
		 * t.add(new Item()); t.add(new Item()); t.add(new Item()); t.add(new
		 * Item()); t.add(new Item()); t.add(new Item()); t.add(new Item());
		 * t.add(new Item()); t.add(new Item()); t.add(new Item()); t.add(new
		 * Item()); t.add(new Item()); t.add(new Item()); t.add(new Item());
		 * t.add(new Item()); t.add(new Item()); t.add(new Item()); t.add(new
		 * Item()); t.add(new Item()); t.add(new Item()); t.add(new Item());
		 * t.add(new Item()); t.add(new Item()); t.add(new Item()); t.add(new
		 * Item()); t.add(new Item()); t.add(new Item()); t.add(new Item());
		 * t.add(new Item()); t.add(new Item()); t.add(new Item()); t.add(new
		 * Item()); t.add(new Item()); t.add(new Item()); t.add(new Item());
		 * t.add(new Item());
		 * 
		 * try { t.build("/tmp/test.pdf"); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		/*
		 * for (int i = 0; i < 57; ++i) t.add(new Item()); try { t.multiBuild(); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
		String files[] = new String[] { "/home/bastien/projects/myDobble/test/astronaut.jpg",
				"/home/bastien/projects/myDobble/test/cat-eye.jpg",
				"/home/bastien/projects/myDobble/test/chess.jpg",
				"/home/bastien/projects/myDobble/test/coffee.jpg",
				"/home/bastien/projects/myDobble/test/dice.jpg",
				"/home/bastien/projects/myDobble/test/energy-arc.jpg",
				"/home/bastien/projects/myDobble/test/fish.jpg",
				"/home/bastien/projects/myDobble/test/flake.jpg",
				"/home/bastien/projects/myDobble/test/flower.jpg",
				"/home/bastien/projects/myDobble/test/grapes.jpg",
				"/home/bastien/projects/myDobble/test/guitar.jpg",
				"/home/bastien/projects/myDobble/test/launch.jpg",
				"/home/bastien/projects/myDobble/test/leaf.jpg",
				"/home/bastien/projects/myDobble/test/lightning.jpg",
				"/home/bastien/projects/myDobble/test/penguin.jpg",
				"/home/bastien/projects/myDobble/test/puppy.jpg",
				"/home/bastien/projects/myDobble/test/sky.jpg",
				"/home/bastien/projects/myDobble/test/sunflower.jpg",
				"/home/bastien/projects/myDobble/test/sunset.jpg",
				"/home/bastien/projects/myDobble/test/yellow-rose.jpg",
				"/home/bastien/projects/myDobble/test/astronaut-r.jpg", };
		int j = 0;
		for (int i = 0; i < 21; ++i) {
			try {
				t.add(new TextItem("item " + i));
				//t.add(new ImageItem(files[j++]));
				if (j == files.length)
					j = 0;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		try {
			t.build("/tmp/test.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
