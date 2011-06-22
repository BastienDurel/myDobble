package org.durel.mydooble;

public class Item {
	int id = getNextId();

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
	
	public void toPDF(PDF out) {
		
	}

	private static int lastId = 0;

	private synchronized int getNextId() {
		return ++lastId;
	}
}
