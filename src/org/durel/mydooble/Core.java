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
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * @author Bastien Durel
 * 
 */
public class Core {
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

	public boolean isCoherent() {
		if (itemStock.isEmpty())
			return false;
		int lNbItemsReq = 0;
		for (int i = 1; i <= nbItems; ++i) {
			lNbItemsReq += i;
		}
		log.info(lNbItemsReq + " items needed");
		return lNbItemsReq == itemStock.size();
	}

	static void print(List<Card> c) {
		print(c, System.out);
	}

	static void print(List<Card> l, PrintStream out) {
		ListIterator<Card> it = l.listIterator();
		while (it.hasNext()) {
			StringBuilder str = new StringBuilder();
			Card c = it.next();
			for (int i = 0; i < c.nb; ++i) {
				if (i > 0)
					str.append(" - ");
				else
					str.append("(" + c.nb + ")|");
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
		PDF out = new PDF();

		ListIterator<Card> it = l.listIterator();
		while (it.hasNext()) {
			Card c = it.next();
			c.toPDF(out);
		}
		out.save(file.getAbsolutePath());
	}

	private ArrayList<Card> subBuild() throws Exception {
		int nbCards = nbItems + 1;
		ArrayList<Card> l = new ArrayList<Card>(nbCards);
		for (int i = 0; i < nbCards; ++i) {
			l.add(new Card(nbItems));
		}
		ArrayList<Item> s = new ArrayList<Item>();
		for (int i = 0; i < itemStock.size(); ++i) {
			s.add(itemStock.get(i));
		}
		Collections.shuffle(s);
		int row;
		int itc = 0;
		for (row = 0; row < nbCards; ++row) {
			Card curcard = l.get(row);
			for (int col = row; col < nbItems; ++col) {
				Item curit = s.get(itc++);
				curcard.syms[col] = curit;
				curcard.nb++;
				Card secCard = l.get(col + 1);
				secCard.syms[row] = curit;
				secCard.nb++;
				log.info("add item(" + curit + ") to [" + row + "][" + col
						+ "] and [" + (col + 1) + "][" + row + "]");
			}
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
}
