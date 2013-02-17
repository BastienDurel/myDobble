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

	public static class DeckException extends Exception {
		public DeckException(String string) {
			super(string);
		}

		private static final long serialVersionUID = -6786584519124554014L;
	}

	/**
	 * Compute the deck size for a given card size
	 * 
	 * @param cardSize
	 *          card size
	 * @return maximum deck size
	 * @throws DeckException 
	 */
	static public int computeDeckSize(int cardSize) throws DeckException {
		int p = cardSize - 1;
		if (!isPrime(p))
			throw new DeckException(p + " (" + cardSize + " - 1) is not prime");
		// Remove 1 as we don't count the 'infinity' point in computation
		return p * p + p + 1;
	}

	public static Deck computeDeck(int cardSize) throws DeckException {
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

	/**
	 * @param deck
	 * @throws DeckException
	 */
	public static void checkDeck(Deck deck) throws DeckException {
		Iterator<Card> it1 = deck.iterator();
		while (it1.hasNext()) {
			Card c1 = it1.next();
			Iterator<Card> it2 = deck.iterator();
			while (it2.hasNext()) {
				Card c2 = it2.next();
				if (c1.equals(c2)) {
					continue;
				}
				int count = 0;
				Iterator<Integer> s = c1.iterator();
				while (s.hasNext()) {
					if (c2.contains(s.next()))
						count++;
				}
				if (count == 0)
					throw new DeckException("Card " + c1 + " and card " + c2
							+ " do not contains any match");
				if (count > 1)
					throw new DeckException("Card " + c1 + " and card " + c2 + " contains " + count
							+ " matches");
			}
		}
	}

	/** tests */
	public static void main(String[] args) {
		int s[] = { 3, 4, 6, 8, 12 };
		for (int i = 0; i < s.length; ++i) {
			Deck deck;
			try {
				deck = computeDeck(s[i]);
				checkDeck(deck);
			} catch (DeckException e) {
				System.err.println(e.getLocalizedMessage());
				continue;
			}
			System.out.println("card size: " + s[i]);
			System.out.println(" -> deck size: " + deck.size());
			for (Iterator<Card> it = deck.iterator(); it.hasNext();) {
				System.out.println(it.next());
			}
			System.out.println("*****************************************************************");
		}

	}

}
