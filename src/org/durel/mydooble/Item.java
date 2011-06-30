package org.durel.mydooble;

import java.util.logging.Logger;

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

public class Item {
	int id = getNextId();
	
	protected static Logger log = Logger.getLogger("org.durel.mydooble");

	@Override
	public String toString() {
		return "Item[" + id + "]";
	}
	
	boolean equals(Item i) {
		return i.id == id;
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Item) && ((Item) o).id == id;
	}
	
	public void toPDF(PDF out, int i) {
	}

	private static int lastId = 0;

	private synchronized int getNextId() {
		return ++lastId;
	}
}
