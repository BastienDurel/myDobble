package org.durel.mydooble;

public class TextItem extends Item {

	private String name;

	public TextItem(String item) {
		super();
		name = item;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void toPDF(PDF out) {
		// TODO Auto-generated method stub
		super.toPDF(out);
	}

}
