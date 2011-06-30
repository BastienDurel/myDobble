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
		Core t = new Core(8);
		t.log.setLevel(Level.INFO);
		t.log.setUseParentHandlers(false);
		ConsoleHandler h = new ConsoleHandler();
		h.setFormatter(new PlainFormatter());
		t.log.addHandler(h);
		t.log.info("test ...");
/*
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		
		try {
			t.build("/tmp/test.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		for (int i = 0; i < 57; ++i) t.add(new Item());
		try {
			t.multiBuild();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
