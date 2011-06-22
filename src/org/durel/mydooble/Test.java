package org.durel.mydooble;

import java.util.logging.Level;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Core t = new Core(2);
		t.log.setLevel(Level.WARNING);
		t.log.info("test ...");
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		try {
			t.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
