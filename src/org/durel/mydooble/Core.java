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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;

import org.durel.mydooble.Deck.DeckException;

/**
 * @author Bastien Durel
 * 
 */
public class Core {
	/**
	 * Number of symbols by card
	 */
	private int nbItems;

	private List<Item> itemStock = new Vector<Item>();

	protected static Random rnd = new Random();

	Logger log = Logger.getLogger("org.durel.mydooble");

	protected class LogWriter extends OutputStream {
		Logger log;

		StringBuffer b = new StringBuffer();

		LogWriter(Logger log) {
			this.log = log;
		}

		@Override
		public void write(int b) throws IOException {
			char c = (char) b;
			if (c == '\n')
				flush();
			else
				this.b.append(c);
		}

		@Override
		public void flush() {
			log.info(b.toString());
			b = new StringBuffer();
		}
	}

	public Core(int aItems) {
		nbItems = aItems;
	}

	public void add(Item i) {
		itemStock.add(i);
	}

	public int getNbItemsReq() {
		try {
			return Deck.computeDeckSize(nbItems);
		} catch (DeckException e) {
			log.info(e.getLocalizedMessage());
			return 0;
		}
	}

	public int getNbItemsReq(int m) {
		if (m == 2)
			return getNbItemsReq();
		if (m < 2)
			throw new IndexOutOfBoundsException();
		return 0;
	}

	public boolean isCoherent() {
		if (itemStock.isEmpty())
			return false;
		int lNbItemsReq = getNbItemsReq();
		log.info(lNbItemsReq + " items needed (have " + itemStock.size() + ")");
		return lNbItemsReq == itemStock.size();
	}

	public boolean isCoherentMulti() {
		if (itemStock.isEmpty())
			return false;
		return getNbItemsReq() == itemStock.size();
	}

	static void print(List<Card> c) {
		print(c, System.out);
	}

	static void print(List<Card> l, PrintStream out) {
		ListIterator<Card> it = l.listIterator();
		while (it.hasNext()) {
			StringBuilder str = new StringBuilder();
			Card c = it.next();
			str.append("(" + c.nb + ")");
			for (int i = 0; i < c.nb; ++i) {
				if (i > 0)
					str.append(" - ");
				else
					str.append("|");
				str.append(c.syms[i]);
			}
			out.println(str);
		}
	}

	public void build(String filename) throws Exception {
		build(new File(filename));
	}

	public void build(File file) throws Exception {
		if (!isCoherent())
			throw new Exception("Incoherent stock");

		List<Card> l = null;
		int test = 0;
		do {
			try {
				l = subBuild();
				test = 15;
			} catch (Exception e) {
				if (++test == 15)
					throw e;
			}
		} while (test < 15);
		PDF out = new PDF(nbItems);

		ListIterator<Card> it = l.listIterator();
		while (it.hasNext()) {
			Card c = it.next();
			c.toPDF(out);
		}
		out.save(file.getAbsolutePath());
	}

	private ArrayList<Card> subBuild() throws Exception {
		int nbCards = Deck.computeDeckSize(nbItems);
		ArrayList<Card> l = new ArrayList<Card>(nbCards);
		Deck d = Deck.computeDeck(nbItems);

		if (itemStock.size() < nbCards)
			throw new DeckException("Not enough items in stock");

		ArrayList<Item> items = new ArrayList<Item>(itemStock);
		Collections.shuffle(items);

		for (Iterator<Deck.Card> i = d.iterator(); i.hasNext();) {
			Card card = new Card(nbItems);
			Deck.Card cur = i.next();
			for (int j = 0; j < nbItems; ++j) {
				card.add(items.get(cur.get(j)));
			}
			l.add(card);
		}
		return l;
	}

	public void multiBuild() throws Exception {
		subMultiBuild();
	}

	private void putInCard(Card cur, ArrayList<Card> l, ArrayList<Item> s, int fromItem)
			throws Exception {
		if (cur.isFull())
			throw new Exception("full");
		for (int i = fromItem; i < s.size(); ++i) {
			Item it = s.get(i);
			if (cur.have(it))
				continue;
			boolean bad = false;
			for (int j = 0; j < cur.nb; ++j) {
				Item sec = cur.syms[j];
				for (int k = 0; k < l.size(); ++k) {
					Card cmp = l.get(k);
					if (cmp.have(it) && cmp.have(sec)) {
						bad = true;
						break;
					}
				}
				if (bad)
					break;
			}
			if (!bad) {
				cur.add(it);
				return;
			}
		}
		throw new Exception("Not found");
	}

	private ArrayList<Card> subMultiBuild() throws Exception {
		if (!isCoherentMulti())
			throw new Exception("Incoherent stock");

		int nbCards = 1 + (nbItems - 1) * nbItems;
		ArrayList<Card> l = new ArrayList<Card>(nbCards);
		for (int i = 0; i < nbCards; ++i) {
			l.add(new Card(nbItems));
		}
		ArrayList<Item> s = new ArrayList<Item>();
		for (int i = 0; i < itemStock.size(); ++i) {
			s.add(itemStock.get(i));
		}
		// Collections.shuffle(s);

		// TODO
		Item it = s.get(0);
		int cnt = 1;
		for (int i = 0; i < nbItems; ++i) {
			Card cur = l.get(i);
			cur.add(it);
			while (cur.isFull() == false)
				cur.add(s.get(cnt++));
		}
		try {
			int row = nbItems;
			for (int i = 1; i < nbItems; ++i) {
				it = l.get(0).syms[i];
				for (int j = 1; j < nbItems; ++j) {
					Card cur = l.get(row++);
					cur.add(it);
					while (!cur.isFull()) {
						putInCard(cur, l, s, nbItems);
					}
				}
			}
		} catch (Exception e) {
			print(l, new PrintStream(new LogWriter(log)));
			throw e;
		}

		print(l, new PrintStream(new LogWriter(log)));
		return l;
	}

	public void reset() {
		reset(nbItems);
	}

	public void reset(int value) {
		nbItems = value;
		itemStock.clear();
	}

	public void remove(Item item) {
		for (int i = 0; i < itemStock.size(); ++i) {
			if (item.equals(itemStock.get(i))) {
				itemStock.remove(i);
				break;
			}
		}
	}
}
