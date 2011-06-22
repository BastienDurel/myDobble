/**
 * 
 */
package org.durel.mydooble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * @author bastien
 * 
 */
public class Core {
	private int nbItems;
	private List<Item> itemStock = new Vector<Item>();
	protected static Random rnd = new Random();
	Logger log = Logger.getLogger("org.durel.mydooble");

	protected class Card {
		Item syms[];

		public Card(int aNbItems) {
			syms = new Item[aNbItems];
		}
	}

	public Core(int aItems) {
		nbItems = aItems;
	}

	public void add(Item i) {
		itemStock.add(i);
	}

	public boolean isCoherent() {
		return itemStock.size() * 2 % nbItems != 0;
	}

	public void build() throws Exception {
		if (isCoherent())
			throw new Exception("Incoherent stock");
		int nbCards = itemStock.size() * 2 / nbItems;
		ArrayList<Card> l = new ArrayList<Card>(nbCards);
		for (int i = 0; i < nbCards; ++i) {
			l.add(new Card(nbItems));
		}
		Stack<Item> s = new Stack<Item>();
		for (int i = 0; i < itemStock.size() * 2; ++i) {
			log.info("i: " + (i / 2));
			s.push(itemStock.get(i / 2));
		}
	}
}
