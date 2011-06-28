package org.durel.mydooble;

public class TextItem extends Item {

	private String name;

	public TextItem(String item) {
		name = item;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public void toPDF(PDF out) {
		// TODO Auto-generated method stub
		super.toPDF(out);
	}

}
