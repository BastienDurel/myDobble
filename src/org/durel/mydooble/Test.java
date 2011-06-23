package org.durel.mydooble;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Test {
	
	static class PlainFormatter extends java.util.logging.Formatter {

		@Override
		public String format(LogRecord record) {
			return record.getMessage() + "\n";
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Core t = new Core(8);
		t.log.setLevel(Level.INFO);
		t.log.setUseParentHandlers(false);
		ConsoleHandler h = new ConsoleHandler();
		h.setFormatter(new PlainFormatter());
		t.log.addHandler(h);
		t.log.info("test ...");

		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		t.add(new Item());
		
		try {
			t.build("/tmp/test.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
