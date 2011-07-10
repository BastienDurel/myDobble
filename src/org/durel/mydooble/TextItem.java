package org.durel.mydooble;

import com.itextpdf.text.pdf.PdfContentByte;

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
	public void toPDF(PDF out, int i) {
		COLS = 1;
		super.toPDF(out, i);
		
		float x = bx + (c * (w + m)) + m;
		float y = by + (r * (h + m)) + m;
		// TODO: center: add (sw - w) / 2 to x
		
		PdfContentByte cb = out.writer.getDirectContent();
		cb.beginText();
		cb.moveText(x, y);
		cb.showText(name);
		cb.endText();
	}

}
