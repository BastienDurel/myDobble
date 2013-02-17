/*
  	Copyright © 2013 Bastien Durel

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
package org.durel.mydooble;

import java.util.Iterator;
import java.util.Vector;

/**
 * @see http://melusine.eu.org/syracuse/luatex/dobble/
 * @see http://images.math.cnrs.fr/Dobble-et-la-geometrie-finie.html
 * @author Bastien Durel
 */
public class Deck extends Vector<Deck.Card> {
	private static final long serialVersionUID = 130983178179553938L;

	public static class Card extends Vector<Integer> {
		private static final long serialVersionUID = 7196714861302421609L;

		public Card(int init) {
			super();
			add(init);
		}

		public Card() {
			super();
		}

		public String toString() {
			StringBuilder out = new StringBuilder();
			for (Iterator<Integer> it = this.iterator(); it.hasNext();) {
				out.append(it.next());
				if (it.hasNext())
					out.append(", ");
			}
			return out.toString();
		}
	}

	/**
	 * Compute the deck size for a given card size
	 * 
	 * @param cardSize
	 *          card size
	 * @return maximum deck size
	 */
	static public int computeDeckSize(int cardSize) {
		int p = cardSize - 1;
		if (!isPrime(p))
			throw new RuntimeException(p + " (" + cardSize + " - 1) is not prime");
		// Remove 1 as we don't count the 'infinity' point in computation
		return p * p + p + 1;
	}

	public static Deck computeDeck(int cardSize) {
		computeDeckSize(cardSize);
		int p = cardSize - 1;

		Deck cards = new Deck();

		// Create symbols
		int cnt = 0;
		// a
		Integer a = cnt++;
		// b
		Integer b[] = new Integer[p];
		for (int i = 0; i < p; ++i)
			b[i] = cnt++;
		// c
		Integer c[][] = new Integer[p][p];
		for (int i = 0; i < p; ++i)
			for (int j = 0; j < p; ++j)
				c[i][j] = cnt++;

		// A
		Card card = new Card(a);
		for (int s = 0; s < p; ++s)
			card.add(b[s]);
		cards.add(card);

		// B
		for (int i = 0; i < p; ++i) {
			card = new Card(a);
			for (int s = 0; s < p; ++s)
				card.add(c[i][s]);
			cards.add(card);
		}

		// C
		for (int i = 0; i < p; ++i) {
			for (int j = 0; j < p; ++j) {
				card = new Card(b[i]);
				for (int s = 0; s < p; ++s)
					card.add(c[s][((s * i) + j) % p]);
				cards.add(card);
			}
		}

		return cards;
	}

	protected static boolean isPrime(long n) {
		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		long sqrtN = (long) Math.sqrt(n) + 1;
		for (long i = 6L; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		}
		return true;
	}

	/** tests */
	public static void main(String[] args) {
		// System.out.println("card size: 3 -> " + computeDeckSize(3));
		// System.out.println("card size: 8 -> " + computeDeckSize(8));
		// System.out.println("card size: 4 -> " + computeDeckSize(4));
		// System.out.println("card size: 6 -> " + computeDeckSize(6));

		int s[] = { 3, 4, 6, 8, 12 };
		for (int i = 0; i < s.length; ++i) {
			Deck deck = computeDeck(s[i]);
			System.out.println("card size: " + s[i]);
			System.out.println(" -> deck size: " + deck.size());
			for (Iterator<Card> it = deck.iterator(); it.hasNext();) {
				System.out.println(it.next());
			}
			System.out.println("*****************************************************************");
		}
	}

}
