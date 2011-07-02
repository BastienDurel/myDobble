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

	/**
	 * base x
	 */
	protected float bx;
	/**
	 * base y
	 */
	protected float by;
	/**
	 * height
	 */
	protected float h;
	/**
	 * width
	 */
	protected float w;
	/**
	 * margins
	 */
	protected float m;
	/**
	 * row in card
	 */
	protected int r;
	/**
	 * column in card
	 */
	protected int c;
	
	/**
	 * Put item on card in PDF.
	 * Base method computes the sub-rect where item should be draw
	 * 
	 * @param out PDF file to write into
	 * @param order order of item in card
	 */
	public void toPDF(PDF out, int order) {
		bx = out.cardBox.getLeft();
		by = out.cardBox.getBottom();
		h = out.cardBox.getHeight();
		w = out.cardBox.getWidth();
		m = 0;
		r = order;
		c = 0;

		if (out.nbItems <= 3) {
			h /= out.nbItems;
			m = out.MARGIN / (out.nbItems - 1);
			h -= m;
			w -= m;
		} else if (out.nbItems <= 8) {
			w /= 2;
			m = out.MARGIN / (((int) (out.nbItems / 2)) - 1);
			h /= (int) (out.nbItems / 2);
			h -= m;
			w -= m;
			c = order % 2;
			r = order / 2;
		} else {
			w /= 3;
			m = out.MARGIN / (((int) (out.nbItems / 3)) - 1);
			h /= (int) (out.nbItems / 3);
			h -= m;
			w -= m;
			c = order % 3;
			r = order / 3;
		}
	}

	private static int lastId = 0;

	private synchronized int getNextId() {
		return ++lastId;
	}
}
