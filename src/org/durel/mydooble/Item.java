package org.durel.mydooble;

import java.util.logging.Logger;

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

public class Item {
	int id = getNextId();
	
	protected int COLS = -1;
	
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
		
		if (COLS < 0) {
			if (out.nbItems <= 3) COLS = 1;
			else if (out.nbItems <= 8) COLS = 2;
			else if (out.nbItems <= 8) COLS = 3;
		}

		int nvm = COLS + 1;
		if (COLS == 1) {
			int nhr = (out.nbItems + 1);
			m = out.MARGIN / Math.max(nhr, nvm);
			h -= m * nhr;
			w -= m * nvm;
			h /= out.nbItems;
		} else if (COLS == 2) {
			int nhr = (out.nbItems / 2) + 1;
			m = out.MARGIN / Math.max(nhr, nvm);
			h -= m * nhr;
			w -= m * nvm;
			c = order % 2;
			r = order / 2;
			w /= 2;
			h /= (int) (out.nbItems / 2);
		} else if (COLS == 3) {
			int nhr = (out.nbItems / 3) + 1;
			m = out.MARGIN / Math.max(nhr, nvm);
			h -= m * nhr;
			w -= m * nvm;
			c = order % 3;
			r = order / 3;
			w /= 3;
			h /= (int) (out.nbItems / 3);
		} else {
			throw new RuntimeException("invalid COLS: " + COLS);
		}
	}

	private static int lastId = 0;

	private synchronized int getNextId() {
		return ++lastId;
	}
}
