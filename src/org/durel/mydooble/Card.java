package org.durel.mydooble;

import java.util.ArrayList;
import java.util.Collections;

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

class Card {
	Item syms[];
	int nb = 0;
	int need;

	public Card(int aNbItems) {
		syms = new Item[aNbItems];
		need = aNbItems;
	}

	public boolean have(int c) {
		for (int i = 0; i < nb; ++i) {
			if (c == syms[i].id)
				return true;
		}
		return false;
	}

	public boolean have(Item it) {
		for (int i = 0; i < nb; ++i) {
			if (syms[i].equals(it))
				return true;
		}
		return false;
	}

	public void add(Item i) {
		syms[nb++] = i;
	}

	public boolean isFull() {
		return nb == syms.length && nb == need;
	}

	public void toPDF(PDF out) {
		if (!isFull())
			throw new IndexOutOfBoundsException("Card not full");
		out.newCard();
		ArrayList<Item> cnt = new ArrayList<Item>(nb);
		for (int i = 0; i < nb; ++i)
			cnt.add(syms[i]);
		Collections.shuffle(cnt);
		for (int i = 0; i < nb; ++i)
			cnt.get(i).toPDF(out, i);
	}
}