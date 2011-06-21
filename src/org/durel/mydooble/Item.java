package org.durel.mydooble;

public class Item {
	int id = getNextId();

	boolean equals(Item i) {
		return i.id == id;
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Item) && ((Item) o).id == id;
	}

	private static int lastId = 0;

	private synchronized int getNextId() {
		return ++lastId;
	}
}
