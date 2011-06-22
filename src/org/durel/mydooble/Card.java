package org.durel.mydooble;

class Card {
	Item syms[];
	int nb = 0;

	public Card(int aNbItems) {
		syms = new Item[aNbItems];
	}

	public boolean have(int c) {
		for (int i = 0; i < nb; ++i) {
			if (c == syms[i].id)
				return true;
		}
		return false;
	}

	public void add(Item i) {
		syms[nb++] = i;
	}

	public boolean isFull() {
		return nb == syms.length;
	}

	public void toPDF(PDF out) {
		if (!isFull())
			throw new IndexOutOfBoundsException("Card not full");
		out.newCard();
		for (int i = 0; i < nb; ++i)
			syms[i].toPDF(out);
	}
}